package dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import dal.TripsModelDAO;
import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dal.model.TripsModelDO;
import dynamodb.model.TripEventCountDODynamo;
import dynamodb.model.TripLocationDODynamo;
import dynamodb.model.TripsEventsDODynamo;
import dynamodb.model.TripsModelDODynamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBTripsModelDAO implements TripsModelDAO {

    private static DynamoDBMapper mapper;
    private static DynamoDB client;


    DynamoDBTripsModelDAO(DynamoDBMapper mapper, com.amazonaws.services.dynamodbv2.document.DynamoDB client) {
        this.mapper = mapper;
        this.client = client;
    }

    @Override
    public void postData(TripsModelDO trips) {
        TripsModelDODynamo tripsModelDODynamo = new TripsModelDODynamo();
        tripsModelDODynamo.setTripID(trips.getTripID());
        tripsModelDODynamo.setDriverID(trips.getDriverID());
        tripsModelDODynamo.setTripStartTime(trips.getTripStartTime());
        tripsModelDODynamo.setTripEndTime(trips.getTripEndTime());
        tripsModelDODynamo.setTripDate(trips.getTripDate());

        //converted the TripsEventDO to TripsEventDynamo
        List<TripsEventsDODynamo> events = new ArrayList<>();

        for (TripsEventsDO event : trips.getTripEvents()) {
            TripsEventsDODynamo tripsEventsDODynamo = new TripsEventsDODynamo();
            tripsEventsDODynamo.setEventID(event.getEventID());
            tripsEventsDODynamo.setEventType(event.getEventType());
            tripsEventsDODynamo.setStartTime(event.getStartTime());
            tripsEventsDODynamo.setEndTime(event.getEndTime());
            tripsEventsDODynamo.setStartingLocation(event.getStartingLocation());
            tripsEventsDODynamo.setEndingLocation(event.getEndingLocation());
            events.add(tripsEventsDODynamo);
        }
        tripsModelDODynamo.setTripEvents(events);
        // converted the TripsLocationDO to TripsLocationDynamo

        List<TripLocationDODynamo> tripsLocation = new ArrayList<>();
        for (TripLocationDO tripLocation : trips.getTripEventLocation()) {
            TripLocationDODynamo tripLocationDODynamo = new TripLocationDODynamo();
            tripLocationDODynamo.setLocation(tripLocation.getLocation());
            tripLocationDODynamo.setTime(tripLocation.getTime());
            tripsLocation.add(tripLocationDODynamo);
        }
        tripsModelDODynamo.setTripEventLocation(tripsLocation);
        // fetching the event count

        List<TripEventCountDODynamo> eventCounts = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        int count = 0;
        while ((count < trips.getTripEvents().size())) {
            String eventType = trips.getTripEvents().get(count).getEventType();
            if (map.containsKey(eventType)) {
                map.put(eventType, map.get(eventType) + 1);
            } else {
                map.put(eventType, 1);
            }
            count++;
        }
        for (String event_type : map.keySet()) {
            int value = map.get(event_type);
            System.out.println("Key: " + event_type + ", Value: " + value);
            TripEventCountDODynamo eventCount = new TripEventCountDODynamo();
            eventCount.setEventType(event_type);
            eventCount.setEventCount(value);
            eventCounts.add(eventCount);
        }

        tripsModelDODynamo.setTripEventCount(eventCounts);
        mapper.save(tripsModelDODynamo);
    }

    @Override
    public TripsModelDO getTripByID(String trip_id) {
        String expression = "tripID = :v1";
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":v1", new AttributeValue().withS(trip_id));

        DynamoDBQueryExpression<TripsModelDODynamo> queryExpression = new DynamoDBQueryExpression<TripsModelDODynamo>()
                .withConsistentRead(false)
                .withKeyConditionExpression(expression)
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<TripsModelDODynamo> recordList = mapper.query(TripsModelDODynamo.class, queryExpression);

        List<TripsModelDO> results = new ArrayList<>();
        for (TripsModelDODynamo item : recordList) {
            TripsModelDO record = new TripsModelDO();
            results.add(record);
            record.setTripID(item.getTripID());
            record.setDriverID(item.getDriverID());
            record.setTripDate(item.getTripDate());
            record.setTripStartTime(item.getTripStartTime());
            record.setTripEndTime(item.getTripEndTime());

            List<TripsEventsDO> events = new ArrayList<TripsEventsDO>();

            for (TripsEventsDODynamo event : item.getTripEvents()) {
                TripsEventsDO tripsEventsDO = new TripsEventsDO();
                tripsEventsDO.setEventID(event.getEventID());
                tripsEventsDO.setEventType(event.getEventType());
                tripsEventsDO.setStartTime(event.getStartTime());
                tripsEventsDO.setEndTime(event.getEndTime());
                tripsEventsDO.setStartingLocation(event.getStartingLocation());
                tripsEventsDO.setEndingLocation(event.getEndingLocation());
                events.add(tripsEventsDO);
            }
            record.setTripEvents(events);
            // converted the TripsLocationDO to TripsLocationDynamo

            List<TripLocationDO> tripsLocation = new ArrayList<TripLocationDO>();
            for (TripLocationDODynamo tripLocation : item.getTripEventLocation()) {
                TripLocationDO tripLocationDO = new TripLocationDO();
                tripLocationDO.setLocation(tripLocation.getLocation());
                tripLocationDO.setTime(tripLocation.getTime());
                tripsLocation.add(tripLocationDO);
            }
            record.setTripEventLocation(tripsLocation);

            List<TripEventCountDO> tripEventCountDO = new ArrayList<TripEventCountDO>();
            for (TripEventCountDODynamo tripLocation : item.getTripEventCount()) {
                TripEventCountDO tripEventCount = new TripEventCountDO();
                tripEventCount.setEventType(tripLocation.getEventType());
                tripEventCount.setEventCount(tripLocation.getEventCount());
                tripEventCountDO.add(tripEventCount);
            }
            record.setTripEventCount(tripEventCountDO);


        }
        System.out.println("5");

        if (results.size() == 0) {
            return null;
        }
        return results.get(0);

    }

    @Override
    public List<TripsModelDO> getTripByDriverID(String driver_id) {
        String expression = "driverID = :v1";
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":v1", new AttributeValue().withS(driver_id));


        DynamoDBQueryExpression<TripsModelDODynamo> queryExpression = new DynamoDBQueryExpression<TripsModelDODynamo>()
                .withIndexName("driverDetails")
                .withScanIndexForward(false) //desc order
                .withConsistentRead(false)
                .withKeyConditionExpression(expression)
                .withExpressionAttributeValues(valueMap);
        List<TripsModelDODynamo> items = mapper.query(TripsModelDODynamo.class, queryExpression);

        List<TripsModelDO> results = new ArrayList<>();
        for (TripsModelDODynamo item : items) {
            TripsModelDO record = new TripsModelDO();
            results.add(record);
            record.setTripID(item.getTripID());
            record.setDriverID(item.getDriverID());
            record.setTripDate(item.getTripDate());
            record.setTripStartTime(item.getTripStartTime());
            record.setTripEndTime(item.getTripEndTime());

            List<TripsEventsDO> events = new ArrayList<TripsEventsDO>();

            for (TripsEventsDODynamo event : item.getTripEvents()) {
                TripsEventsDO tripsEventsDO = new TripsEventsDO();
                tripsEventsDO.setEventID(event.getEventID());
                tripsEventsDO.setEventType(event.getEventType());
                tripsEventsDO.setStartTime(event.getStartTime());
                tripsEventsDO.setEndTime(event.getEndTime());
                tripsEventsDO.setStartingLocation(event.getStartingLocation());
                tripsEventsDO.setEndingLocation(event.getEndingLocation());
                events.add(tripsEventsDO);
            }
            record.setTripEvents(events);
            // converted the TripsLocationDO to TripsLocationDynamo

            List<TripLocationDO> tripsLocation = new ArrayList<TripLocationDO>();
            for (TripLocationDODynamo tripLocation : item.getTripEventLocation()) {
                TripLocationDO tripLocationDO = new TripLocationDO();
                tripLocationDO.setLocation(tripLocation.getLocation());
                tripLocationDO.setTime(tripLocation.getTime());
                tripsLocation.add(tripLocationDO);
            }
            record.setTripEventLocation(tripsLocation);

            List<TripEventCountDO> tripEventCountDO = new ArrayList<TripEventCountDO>();
            for (TripEventCountDODynamo tripLocation : item.getTripEventCount()) {
                TripEventCountDO tripEventCount = new TripEventCountDO();
                tripEventCount.setEventType(tripLocation.getEventType());
                tripEventCount.setEventCount(tripLocation.getEventCount());
                tripEventCountDO.add(tripEventCount);
            }
            record.setTripEventCount(tripEventCountDO);
        }
        return results;

    }
}
// cheching the change in the new branch



