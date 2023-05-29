package dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TripLocationDODynamo {
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_LOCATION_TIME)
    private Long time;
    @DynamoDBAttribute(attributeName = SCHEMA.COL_TRIP_LOCATION_LOCATION)
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
