package dal;

import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dal.model.TripsModelDO;

import java.util.ArrayList;
import java.util.List;

public class DalTripSaveData {
    static List<TripsEventsDO> SaveEvents(){
        List<TripsEventsDO> eventList = new ArrayList<TripsEventsDO>();
        TripsEventsDO tripsEvents1 = new TripsEventsDO();
        tripsEvents1.setEventID("e1234");
        tripsEvents1.setEventType("HARD_BRAKING");
        tripsEvents1.setEndTime("1679674893000");
        tripsEvents1.setStartTime("1679674896000");
        tripsEvents1.setStartingLocation("032.837223,087.325821");
        tripsEvents1.setEndingLocation("032.837250,087.326004");

        eventList.add(tripsEvents1);

        TripsEventsDO tripsEvents2 = new TripsEventsDO();
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
    static List<TripLocationDO> SaveTripLocation(){
        List<TripLocationDO> locationList = new ArrayList<TripLocationDO>();
        TripLocationDO tripLocation1 = new TripLocationDO();
        tripLocation1.setLocation("032.837250,-087.326004");
        tripLocation1.setTime(1679674895000L);

        locationList.add(tripLocation1);

        TripLocationDO tripLocation2 = new TripLocationDO();
        tripLocation2.setLocation("032.837243,-087.325821");
        tripLocation2.setTime(1679674896000L);
        locationList.add(tripLocation2);
//        for(TripLocationDO location : locationList){
//            System.out.println(location.getLocation());
//            System.out.println(location.getTime());
//        }
        return locationList;
    }
    static List<TripEventCountDO> SaveTripEventCount(){
        List<TripEventCountDO> eventCountList = new ArrayList<TripEventCountDO>();
        TripEventCountDO tripEvents1 = new TripEventCountDO();
        tripEvents1.setEventType("OVER_SPEEDING");
        tripEvents1.setEventCount(1);
        eventCountList.add(tripEvents1);
        TripEventCountDO tripEvents2 = new TripEventCountDO();
        tripEvents2.setEventType("HARD_BRAKING");
        tripEvents2.setEventCount(1);
        eventCountList.add(tripEvents2);
//        for(TripEventCountDO eventCount : eventCountList){
//            System.out.println(eventCount.getEventType());
//            System.out.println(eventCount.getEventCount());
//        }
        return eventCountList;

    }
    static TripsModelDO CreateTrips(String TripId){
        TripsModelDO tripsModel = new TripsModelDO();
        tripsModel.setTripID(TripId);
        tripsModel.setDriverID("d12345");
        tripsModel.setTripDate("2023-03-24");
        tripsModel.setTripStartTime(1679674236000L);
        tripsModel.setTripEndTime(1679675436000L);
        tripsModel.setTripEvents(SaveEvents());
        tripsModel.setTripEventLocation(SaveTripLocation());
        tripsModel.setTripEventCount(SaveTripEventCount());
        return tripsModel;

    }
    static TripsModelDO CreateTrip2 ( String TripId ) {
        TripsModelDO tripsModel = new TripsModelDO();
        tripsModel.setTripID(TripId);
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
