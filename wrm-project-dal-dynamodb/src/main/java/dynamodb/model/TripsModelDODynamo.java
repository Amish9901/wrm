package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "wrm_trips")
public class TripsModelDODynamo {
    @DynamoDBHashKey(attributeName = "tripID")

    private String tripID;


    private String driverID;
    @DynamoDBAttribute(attributeName = "tripStartTime")

    private Long tripStartTime;
    @DynamoDBAttribute(attributeName = "tripEndTime")

    private Long tripEndTime;
    @DynamoDBAttribute(attributeName = "tripDate")

    private String tripDate;
    @DynamoDBAttribute(attributeName = "tripEvents")

    private List<TripsEventsDODynamo> tripEvents;
    @DynamoDBAttribute(attributeName = "event_count")

    private List<TripEventCountDODynamo> tripEventCount;
    @DynamoDBAttribute(attributeName = "tripEventLocation")

    private List<TripLocationDODynamo> tripEventLocation;

    public void setTripID(String tripID){
        this.tripID = tripID;
    }
    public String getTripID(){
        return tripID;
    }
    public void setDriverID(String driverID){
        this.driverID = driverID;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "driverDetails" ,attributeName ="driverID")
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
    public void setTripEvents(List<TripsEventsDODynamo> tripEvents){
        this.tripEvents = tripEvents;
    }
    public List<TripsEventsDODynamo> getTripEvents(){
        return tripEvents;
    }
    public void setTripEventCount(List<TripEventCountDODynamo> tripEventCount){
        this.tripEventCount = tripEventCount;
    }
    public List<TripEventCountDODynamo> getTripEventCount(){
        return tripEventCount;
    }
    public void setTripEventLocation(List<TripLocationDODynamo> tripEventLocation){
        this.tripEventLocation = tripEventLocation;
    }
    public List<TripLocationDODynamo> getTripEventLocation(){
        return tripEventLocation;
    }
}
