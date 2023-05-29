package api.converter;

import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dal.model.TripsModelDO;
import model.TripEventCount;
import model.TripLocation;
import model.TripsEvents;
import model.TripsModel;

import java.util.ArrayList;
import java.util.List;

public class ApiModelConverter {
    public static TripsModel convertToApiModel(TripsModelDO tripsModel) {

        TripsModel apiTripsModel = new TripsModel();
        apiTripsModel.setTripID(tripsModel.getTripID());
        apiTripsModel.setDriverID(tripsModel.getDriverID());
        apiTripsModel.setTripStartTime(tripsModel.getTripStartTime());
        apiTripsModel.setTripEndTime(tripsModel.getTripEndTime());
        apiTripsModel.setTripDate(tripsModel.getTripID());
        apiTripsModel.setTripEvents(ApiModelConverter.ModelTripEventsToDalTripEvents(tripsModel.getTripEvents()));
        apiTripsModel.setTripEventLocation(ApiModelConverter.ModelTripLocationToDalTripLocation(tripsModel.getTripEventLocation()));
        apiTripsModel.setTripEventCount(ApiModelConverter.ModelTripEventCountToDalTripEventCount(tripsModel.getTripEventCount()));
        return apiTripsModel;
    }
    public static List<TripsModel> convertToApiModel(List<TripsModelDO> tripsModel) {
        List<TripsModel> apiTripsModel = new ArrayList<>();
        for (TripsModelDO trips : tripsModel) {
            TripsModel tripmodel = new TripsModel();
            tripmodel.setTripID(trips.getTripID());
            tripmodel.setDriverID(trips.getDriverID());
            tripmodel.setTripStartTime(trips.getTripStartTime());
            tripmodel.setTripEndTime(trips.getTripEndTime());
            tripmodel.setTripDate(trips.getTripID());
            tripmodel.setTripEvents(ApiModelConverter.ModelTripEventsToDalTripEvents(trips.getTripEvents()));
            tripmodel.setTripEventLocation(ApiModelConverter.ModelTripLocationToDalTripLocation(trips.getTripEventLocation()));
            tripmodel.setTripEventCount(ApiModelConverter.ModelTripEventCountToDalTripEventCount(trips.getTripEventCount()));
            apiTripsModel.add(tripmodel);
        }
        return apiTripsModel;
    }

    private static List<TripsEvents> ModelTripEventsToDalTripEvents(List<TripsEventsDO> trips ) {
        List<TripsEvents> events = new ArrayList<>();

        for (TripsEventsDO event : trips) {
            TripsEvents tripsEvents = new TripsEvents();
            tripsEvents.setEventID(event.getEventID());
            tripsEvents.setEventType(event.getEventType());
            tripsEvents.setStartTime(event.getStartTime());
            tripsEvents.setEndTime(event.getEndTime());
            tripsEvents.setStartingLocation(event.getStartingLocation());
            tripsEvents.setEndingLocation(event.getEndingLocation());
            events.add(tripsEvents);
        }
        return events;
    }

    private static List<TripLocation> ModelTripLocationToDalTripLocation(List<TripLocationDO> trips ) {
        List<TripLocation> tripsLocation = new ArrayList<>();
        for (TripLocationDO Location : trips) {
            TripLocation tripLocation = new TripLocation();
            tripLocation.setLocation(Location.getLocation());
            tripLocation.setTime(Location.getTime());
            tripsLocation.add(tripLocation);
        }
        return tripsLocation;
    }

    private static List<TripEventCount> ModelTripEventCountToDalTripEventCount(List<TripEventCountDO> trips ) {
        List<TripEventCount> tripEventCountDO = new ArrayList<>();
        for (TripEventCountDO eventCount : trips) {
            TripEventCount tripEventCount = new TripEventCount();
            tripEventCount.setEventType(eventCount.getEventType());
            tripEventCount.setEventCount(eventCount.getEventCount());
            tripEventCountDO.add(tripEventCount);
        }
        return tripEventCountDO;
    }






}
