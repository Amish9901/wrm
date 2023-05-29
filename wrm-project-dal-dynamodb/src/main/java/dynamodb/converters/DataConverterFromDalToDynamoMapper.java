package dynamodb.converters;

import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dynamodb.model.TripLocationDODynamo;
import dynamodb.model.TripsEventsDODynamo;

import java.util.ArrayList;
import java.util.List;

public class DataConverterFromDalToDynamoMapper {

    public static List<TripsEventsDODynamo> DalTripEventToDynamoTripEvent (List<TripsEventsDO> trips) {
        List<TripsEventsDODynamo> events = new ArrayList<>();

        for (TripsEventsDO event : trips) {
            TripsEventsDODynamo tripsEventsDODynamo = new TripsEventsDODynamo();
            tripsEventsDODynamo.setEventID(event.getEventID());
            tripsEventsDODynamo.setEventType(event.getEventType());
            tripsEventsDODynamo.setStartTime(event.getStartTime());
            tripsEventsDODynamo.setEndTime(event.getEndTime());
            tripsEventsDODynamo.setStartingLocation(event.getStartingLocation());
            tripsEventsDODynamo.setEndingLocation(event.getEndingLocation());
            events.add(tripsEventsDODynamo);
        }
        return events;
    }
    public static List<TripLocationDODynamo> DalTripLocationToDynamoTripLocation (List<TripLocationDO> trips) {
        List<TripLocationDODynamo> tripsLocation = new ArrayList<>();
        for (TripLocationDO tripLocation : trips) {
            TripLocationDODynamo tripLocationDODynamo = new TripLocationDODynamo();
            tripLocationDODynamo.setLocation(tripLocation.getLocation());
            tripLocationDODynamo.setTime(tripLocation.getTime());
            tripsLocation.add(tripLocationDODynamo);
        }
        return tripsLocation;
    }
}
