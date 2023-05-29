package dynamodb.converters;

import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dynamodb.model.TripEventCountDODynamo;
import dynamodb.model.TripLocationDODynamo;
import dynamodb.model.TripsEventsDODynamo;

import java.util.ArrayList;
import java.util.List;

public class DataConverterDynamoMapperToDal {
    public static List<TripsEventsDO> DynamoTripsEventsToDalTripEvents (List<TripsEventsDODynamo> trips) {
        List<TripsEventsDO> events = new ArrayList<TripsEventsDO>();

        for (TripsEventsDODynamo event : trips) {
            TripsEventsDO tripsEventsDO = new TripsEventsDO();
            tripsEventsDO.setEventID(event.getEventID());
            tripsEventsDO.setEventType(event.getEventType());
            tripsEventsDO.setStartTime(event.getStartTime());
            tripsEventsDO.setEndTime(event.getEndTime());
            tripsEventsDO.setStartingLocation(event.getStartingLocation());
            tripsEventsDO.setEndingLocation(event.getEndingLocation());
            events.add(tripsEventsDO);
        }
        return events;
    }

    public static List<TripLocationDO> DynamoTripsLocationToDalTripLocation (List<TripLocationDODynamo> trips) {
        List<TripLocationDO> tripsLocation = new ArrayList<TripLocationDO>();
        for (TripLocationDODynamo tripLocation : trips) {
            TripLocationDO tripLocationDO = new TripLocationDO();
            tripLocationDO.setLocation(tripLocation.getLocation());
            tripLocationDO.setTime(tripLocation.getTime());
            tripsLocation.add(tripLocationDO);
        }
        return tripsLocation;
    }

    public static List<TripEventCountDO> DynamoTripEventCountToDalTripEventCount (List<TripEventCountDODynamo> trips) {
        List<TripEventCountDO> tripEventCountDO = new ArrayList<TripEventCountDO>();
        for (TripEventCountDODynamo tripLocation : trips) {
            TripEventCountDO tripEventCount = new TripEventCountDO();
            tripEventCount.setEventType(tripLocation.getEventType());
            tripEventCount.setEventCount(tripLocation.getEventCount());
            tripEventCountDO.add(tripEventCount);
        }
        return tripEventCountDO;
    }
}
