package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TripsEventsDODynamo {
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_EVENT_ID)
    private String eventID;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_EVENT_TYPE)
    private String eventType;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_STARTING_LOCATION)
    private String startingLocation;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_ENDING_LOCATION)
    private String endingLocation;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_START_TIME)
    private String startTime;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENTS_END_TIME)
    private String endTime;

    public void setEventID(String eventID){
        this.eventID = eventID;
    }
    public String getEventID(){
        return eventID;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    public String getEventType(){
        return eventType;
    }
    public void setStartingLocation(String startingLocation){
        this.startingLocation = startingLocation;
    }
    public String getStartingLocation(){
        return startingLocation;
    }
    public void setEndingLocation(String endingLocation){
        this.endingLocation = endingLocation;
    }
    public String getEndingLocation(){
        return endingLocation;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public String getStartTime(){
        return startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    public String getEndTime(){
        return endTime;
    }
}
