package dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import dal.TripsModelDAO;
import dal.model.TripsModelDO;
import dynamodb.TripCountCalculate.TripCountCalculator;
import dynamodb.converters.DataConverterDynamoMapperToDal;
import dynamodb.converters.DataConverterFromDalToDynamoMapper;
import dynamodb.model.TripsModelDODynamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBTripsModelDAO implements TripsModelDAO {

    private static DynamoDBMapper mapper;

    DynamoDBTripsModelDAO(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void postData(TripsModelDO trips) {
        TripsModelDODynamo tripsModelDODynamo = new TripsModelDODynamo();
        tripsModelDODynamo.setTripID(trips.getTripID());
        tripsModelDODynamo.setDriverID(trips.getDriverID());
        tripsModelDODynamo.setTripStartTime(trips.getTripStartTime());
        tripsModelDODynamo.setTripEndTime(trips.getTripEndTime());
        tripsModelDODynamo.setTripDate(trips.getTripDate());
        tripsModelDODynamo.setTripEvents(DataConverterFromDalToDynamoMapper.DalTripEventToDynamoTripEvent(trips.getTripEvents()));
        tripsModelDODynamo.setTripEventLocation(DataConverterFromDalToDynamoMapper.DalTripLocationToDynamoTripLocation(trips.getTripEventLocation()));
        tripsModelDODynamo.setTripEventCount(TripCountCalculator.TripCount(trips.getTripEvents()));
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
            record.setTripEvents(DataConverterDynamoMapperToDal.DynamoTripsEventsToDalTripEvents(item.getTripEvents()));
            record.setTripEventLocation(DataConverterDynamoMapperToDal.DynamoTripsLocationToDalTripLocation(item.getTripEventLocation()));
            record.setTripEventCount(DataConverterDynamoMapperToDal.DynamoTripEventCountToDalTripEventCount(item.getTripEventCount()));
        }
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
            record.setTripEvents(DataConverterDynamoMapperToDal.DynamoTripsEventsToDalTripEvents(item.getTripEvents()));
            record.setTripEventLocation(DataConverterDynamoMapperToDal.DynamoTripsLocationToDalTripLocation(item.getTripEventLocation()));
            record.setTripEventCount(DataConverterDynamoMapperToDal.DynamoTripEventCountToDalTripEventCount(item.getTripEventCount()));
        }
        return results;

    }
}




