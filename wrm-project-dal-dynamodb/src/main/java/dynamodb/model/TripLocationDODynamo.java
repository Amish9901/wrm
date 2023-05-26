package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TripLocationDODynamo {
    @DynamoDBAttribute(attributeName = "time")
    private Long time;
    @DynamoDBAttribute(attributeName = "location")
    private String location;
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }
    public void setTime(Long time){
        this.time = time;
    }
    public Long getTime(){
        return time;
    }
}
