package model;

import java.util.List;


public class TripsModel {
    private String tripID;
    private String driverID;
    private Long tripStartTime;
    private Long tripEndTime;
    private String tripDate;
    private List<TripsEvents> tripEvents;
    private List<TripEventCount> tripEventCount;
    private List<TripLocation> tripEventLocation;

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
    public void setTripEvents(List<TripsEvents> events){
        this.tripEvents = events;
    }
    public List<TripsEvents> getTripEvents(){
        return tripEvents;
    }
    public void setTripEventCount(List<TripEventCount> tripEventCount){
        this.tripEventCount = tripEventCount;
    }
    public List<TripEventCount> getTripEventCount(){
        return tripEventCount;
    }
    public void setTripEventLocation(List<TripLocation> locations){
        this.tripEventLocation = locations;
    }
    public List<TripLocation> getTripEventLocation(){
        return tripEventLocation;
    }
}
