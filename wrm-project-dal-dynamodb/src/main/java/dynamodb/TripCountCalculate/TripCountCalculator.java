package dynamodb.TripCountCalculate;

import dal.model.TripsEventsDO;
import dynamodb.model.TripEventCountDODynamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripCountCalculator {
    public static List<TripEventCountDODynamo> TripCount (List<TripsEventsDO> trips) {
        List<TripEventCountDODynamo> eventCounts = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        int count = 0;
        while ((count < trips.size())) {
            String eventType = trips.get(count).getEventType();
            if (map.containsKey(eventType)) {
                map.put(eventType, map.get(eventType) + 1);
            } else {
                map.put(eventType, 1);
            }
            count++;
        }
        for (String event_type : map.keySet()) {
            TripEventCountDODynamo eventCount = new TripEventCountDODynamo();
            eventCount.setEventType(event_type);
            eventCount.setEventCount(map.get(event_type));
            eventCounts.add(eventCount);
        }

        return eventCounts;
    }
}
