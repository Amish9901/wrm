package model;



public class TripEventCount {
    private String eventType;
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
