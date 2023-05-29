package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TripEventCountDODynamo {
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENT_COUNT_EVENT_TYPE )
    private String eventType;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_EVENT_COUNT_EVENT_COUNT )
    private int eventCount;
    public void setEventType(String eventType){

        this.eventType = eventType;
    }
    public String getEventType(){
        return eventType;
    }
    public void setEventCount(int eventCount){
        this.eventCount = eventCount;
    }
    public int getEventCount(){
        return eventCount;
    }
}
