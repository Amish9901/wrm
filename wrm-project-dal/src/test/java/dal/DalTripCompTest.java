package dal;

import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dal.model.TripsModelDO;
import org.junit.Assert;


public class DalTripCompTest {
     static void compareTrip(TripsModelDO actual, TripsModelDO expected) {
//         System.out.println(expected.getTripEventCount().get(0).getEventType());
//         System.out.println(expected.getTripEventCount().get(1).getEventType());
         for(TripsEventsDO events : expected.getTripEvents()) {
             System.out.println(events.getEventType());
         }
         for(TripEventCountDO eventCount : expected.getTripEventCount()){
             System.out.println(eventCount.getEventType());
         }
         for(TripLocationDO locationDO :expected.getTripEventLocation()){
             System.out.println(locationDO.getLocation());
         }


        Assert.assertEquals(expected.getTripID(), actual.getTripID());
        Assert.assertEquals(expected.getDriverID(), actual.getDriverID());
        Assert.assertEquals(expected.getTripStartTime(), actual.getTripStartTime());
        Assert.assertEquals(expected.getTripEndTime(), actual.getTripEndTime());
        Assert.assertEquals(expected.getTripDate(), actual.getTripDate());
        for (int i = 0; i < expected.getTripEvents().size(); i++) {
            Assert.assertEquals(expected.getTripEvents().get(i).getEventID(), actual.getTripEvents().get(i).getEventID());

            Assert.assertEquals(expected.getTripEvents().get(i).getEventType(), actual.getTripEvents().get(i).getEventType());
            Assert.assertEquals(expected.getTripEvents().get(i).getStartTime(), actual.getTripEvents().get(i).getStartTime());
            Assert.assertEquals(expected.getTripEvents().get(i).getEndTime(), actual.getTripEvents().get(i).getEndTime());
            Assert.assertEquals(expected.getTripEvents().get(i).getStartingLocation(), actual.getTripEvents().get(i).getStartingLocation());
            Assert.assertEquals(expected.getTripEvents().get(i).getEndingLocation(), actual.getTripEvents().get(i).getEndingLocation());
        }
        for (int i = 0; i < expected.getTripEventCount().size(); i++) {
            Assert.assertEquals(expected.getTripEventCount().get(i).getEventType(), actual.getTripEventCount().get(i).getEventType());
            Assert.assertEquals(expected.getTripEventCount().get(i).getEventCount(), actual.getTripEventCount().get(i).getEventCount());
        }
        for (int i = 0; i < expected.getTripEventLocation().size(); i++) {
            Assert.assertEquals(expected.getTripEventLocation().get(i).getLocation(), actual.getTripEventLocation().get(i).getLocation());
            Assert.assertEquals(expected.getTripEventLocation().get(i).getTime(), actual.getTripEventLocation().get(i).getTime());
        }
         System.out.println(expected);
         System.out.println(actual);

    }
}
