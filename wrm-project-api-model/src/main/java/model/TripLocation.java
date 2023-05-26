package model;

public class TripLocation {
    private Long time;
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
