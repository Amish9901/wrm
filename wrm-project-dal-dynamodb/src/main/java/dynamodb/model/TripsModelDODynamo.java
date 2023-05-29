package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.swing.plaf.synth.SynthCheckBoxMenuItemUI;
import java.util.List;

@DynamoDBTable(tableName = SCHEMA.TABLE_NAME)
public class TripsModelDODynamo {
    @DynamoDBHashKey(attributeName = SCHEMA.COL_TRIP_ID)

    private String tripID;


    private String driverID;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_START_TIME)

    private Long tripStartTime;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_END_TIME)

    private Long tripEndTime;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_DATE)

    private String tripDate;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS)

    private List<TripsEventsDODynamo> tripEvents;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENT_COUNT)

    private List<TripEventCountDODynamo> tripEventCount;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENT_LOCATION)

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
    @DynamoDBIndexHashKey(globalSecondaryIndexName = SCHEMA.GSI_INDEX_NAME ,attributeName = SCHEMA.COL_DRIVER_ID)
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
