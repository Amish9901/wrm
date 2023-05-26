package dal.model;

import java.util.List;


public class TripsModelDO {
    private String tripID;
    private String driverID;
    private Long tripStartTime;
    private Long tripEndTime;
    private String tripDate;
    private List<TripsEventsDO> tripEvents;
    private List<TripEventCountDO> tripEventCount;
    private List<TripLocationDO> tripEventLocation;


    public void setTripID(String tripID){
        this.tripID = tripID;
    }

    public String getTripID(){
        return tripID;
    }
    public void setDriverID(String driverID){
        this.driverID = driverID;
    }
    public String getDriverID(){
        return driverID;
    }
    public void setTripStartTime(Long tripStartTime){
        this.tripStartTime = tripStartTime;
    }
    public Long getTripStartTime(){
        return tripStartTime;
    }
    public void setTripEndTime(Long tripEndTime){
        this.tripEndTime = tripEndTime;
    }
    public Long getTripEndTime(){
        return tripEndTime;
    }
    public void setTripDate(String tripDate){
        this.tripDate = tripDate;
    }
    public String getTripDate(){
        return tripDate;
    }
    public void setTripEvents(List<TripsEventsDO> tripEvents){
        this.tripEvents = tripEvents;
    }
    public List<TripsEventsDO> getTripEvents(){
        return tripEvents;
    }
    public void setTripEventCount(List<TripEventCountDO> tripEventCount){
        this.tripEventCount = tripEventCount;
    }
    public List<TripEventCountDO> getTripEventCount(){
        return tripEventCount;
    }
    public void setTripEventLocation(List<TripLocationDO> locstions){
        this.tripEventLocation = locstions;
    }
    public List<TripLocationDO> getTripEventLocation(){
        return tripEventLocation;
    }
}
