package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TripEventCountDODynamo {
    @DynamoDBAttribute(attributeName = "eventType")
    private String eventType;
    @DynamoDBAttribute(attributeName = "eventCount")
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
