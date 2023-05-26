package dynamodb.model;

import java.util.ArrayList;
import java.util.List;

public class TripSaveData {
    static List<TripsEventsDODynamo> SaveEvents(){
        List<TripsEventsDODynamo> eventList = new ArrayList<TripsEventsDODynamo>();
        TripsEventsDODynamo tripsEvents1 = new TripsEventsDODynamo();
        tripsEvents1.setEventID("e1234");
        tripsEvents1.setEventType("HARD_BRAKING");
        tripsEvents1.setEndTime("1679674893000");
        tripsEvents1.setStartTime("1679674896000");
        tripsEvents1.setStartingLocation("032.837223,087.325821");
        tripsEvents1.setEndingLocation("032.837250,087.326004");

        eventList.add(tripsEvents1);

        TripsEventsDODynamo tripsEvents2 = new TripsEventsDODynamo();
        tripsEvents2.setEventID("e1235");
        tripsEvents2.setEventType("OVER_SPEEDING");
        tripsEvents2.setEndTime("1679674893000");
        tripsEvents2.setStartTime("1679674896000");
        tripsEvents2.setStartingLocation("032.837223,087.325821");
        tripsEvents2.setEndingLocation("032.837250,087.326004");

        eventList.add(tripsEvents2);
//        for (TripsEventsDO event : eventList){
//            System.out.println(event.getEventID());
//            System.out.println(event.getEventType());
//            System.out.println(event.getTripEndTime());
//            System.out.println(event.getTripStartTime());
//            System.out.println(event.getStartingLocation());
//            System.out.println(event.getEndingLocation());
//        }

        return eventList;

    }
    static List<TripLocationDODynamo> SaveTripLocation(){
        List<TripLocationDODynamo> locationList = new ArrayList<TripLocationDODynamo>();
        TripLocationDODynamo tripLocation1 = new TripLocationDODynamo();
        tripLocation1.setLocation("032.837250,-087.326004");
        tripLocation1.setTime(1679674895000L);

        locationList.add(tripLocation1);

        TripLocationDODynamo tripLocation2 = new TripLocationDODynamo();
        tripLocation2.setLocation("032.837243,-087.325821");
        tripLocation2.setTime(1679674896000L);
        locationList.add(tripLocation2);
//        for(TripLocationDO location : locationList){
//            System.out.println(location.getLocation());
//            System.out.println(location.getTime());
//        }
        return locationList;
    }
    static List<TripEventCountDODynamo> SaveTripEventCount(){
        List<TripEventCountDODynamo> eventCountList = new ArrayList<TripEventCountDODynamo>();
        TripEventCountDODynamo tripEvents1 = new TripEventCountDODynamo();
        tripEvents1.setEventType("OVER_SPEEDING");
        tripEvents1.setEventCount(1);
        eventCountList.add(tripEvents1);
        TripEventCountDODynamo tripEvents2 = new TripEventCountDODynamo();
        tripEvents2.setEventType("HARD_BRAKING");
        tripEvents2.setEventCount(1);
        eventCountList.add(tripEvents2);
//        for(TripEventCountDO eventCount : eventCountList){
//            System.out.println(eventCount.getEventType());
//            System.out.println(eventCount.getEventCount());
//        }
        return eventCountList;

    }
    public static TripsModelDODynamo CreateTrips(String TripId){
        TripsModelDODynamo tripsModel = new TripsModelDODynamo();
        tripsModel.setTripID("t12345-0001");
        tripsModel.setDriverID("d12345");
        tripsModel.setTripDate("2023-03-24");
        tripsModel.setTripStartTime(1679674236000L);
        tripsModel.setTripEndTime(1679675436000L);
        tripsModel.setTripEvents(SaveEvents());
        tripsModel.setTripEventLocation(SaveTripLocation());
        tripsModel.setTripEventCount(SaveTripEventCount());
        return tripsModel;

    }
}
