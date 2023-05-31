package mysql;

import com.edriving.commons.dal.jdbc.BaseDao;
import com.edriving.commons.dal.jdbc.JdbcConnectionPool;
import dal.TripsModelDAO;
import dal.model.TripEventCountDO;
import dal.model.TripLocationDO;
import dal.model.TripsEventsDO;
import dal.model.TripsModelDO;

import java.sql.SQLException;
import java.util.*;

public class MysqlTripModelDAO extends BaseDao implements TripsModelDAO {

    private static final String SQL_GET_TRIP_BY_ID = "" +
            "select tripID,driverID,tripStartTime,tripEndTime,tripDate from wrm_trips where tripID = ?";
    private static final String SQL_GET_EVENTS_BY_TRIP_ID = "" +
            "select eventID,eventType,startTime,endTime,startingLocation,endingLocation from wrm_trip_events where tripID=?";
    private static final String SQL_GET_EVENT_COUNT_BY_TRIP_ID = "" +
            " select eventType  ,eventCount from wrm_trip_event_counts where tripID=?";
    private static final String SQL_GET_LOCATION_BY_TRIP_ID = "" +
            " select location ,time from wrm_trip_locations where tripID=?";
    private static final String SQL_GET_TRIPS_BY_DRIVER_ID = "" +
            "select tripID,driverID,tripStartTime,tripEndTime,tripDate from wrm_trips where driverID =?";
    private static final String SQL_SAVE_TRIP_DATA = "" +
            "insert into wrm_trips (tripID,driverID,tripStartTime,tripEndTime,tripDate) values(?,?,?,?,?)";
    private static final String SQL_SAVE_TRIP_EVENTS = "" +
            "insert into wrm_trip_events (tripID,eventID,eventType,startTime,endTime,startingLocation,endingLocation) values (?,?,?,?,?,?,?)";
    private static final String SQL_SAVE_TRIP_EVENT_COUNT = "" +
            "insert into wrm_trip_event_counts (tripID, eventType,eventCount) values (?,?,?)";
    private static final String SQL_SAVE_TRIP_LOCATION = "" +
            "insert into wrm_trip_locations (tripID,time,location) values (?,?,?)";

    MysqlTripModelDAO(String dataSourceName) {
        super(JdbcConnectionPool.getInstance().initializeDataSource(dataSourceName));
    }


    @Override
    public void postData(TripsModelDO trips)  {
        System.out.println("2");
        System.out.println(trips.getDriverID());
        try {
            update(SQL_SAVE_TRIP_DATA,
                    trips.getTripID(),
                    trips.getDriverID(),
                    trips.getTripStartTime(),
                    trips.getTripEndTime(),
                    trips.getTripDate()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("3");
        insertTripEventCounts(trips.getTripID(), trips.getTripEvents());
        insertTripEvents(trips.getTripID(), trips.getTripEvents());
        insertTripLocations(trips.getTripID(), trips.getTripEventLocation());
    }



    private void insertTripEventCounts(String tripId, List<TripsEventsDO> tripEvents) {
        if (tripId == null || tripEvents == null) {
            return;
        }
        Map<String, Integer> eventCountMap = new HashMap<>();
        for (TripsEventsDO tripEvent : tripEvents) {
            if (tripEvent == null) {
                continue;
            }
            Integer eventCount = eventCountMap.computeIfAbsent(tripEvent.getEventType(), k -> 0);
            eventCountMap.put(tripEvent.getEventType(), eventCount + 1);
        }
        if (eventCountMap.isEmpty()) {
            return;
        }
        List<Object[]> params = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : eventCountMap.entrySet()) {
            List<Object> paramsRow = new ArrayList<>();
            paramsRow.add(tripId);
            paramsRow.add(entry.getKey());
            paramsRow.add(entry.getValue());
            params.add(paramsRow.toArray());
        }
        try {
            updateBatch(SQL_SAVE_TRIP_EVENT_COUNT, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insertTripEvents(String tripId, List<TripsEventsDO> tripEvents) {
        if (tripId == null || tripEvents == null) {
            return;
        }
        List<Object[]> params = new ArrayList<>();
        for (TripsEventsDO tripEvent : tripEvents) {
            if (tripEvent == null) {
                continue;
            }
            if (tripEvent.getEventID() == null) {
                tripEvent.setEventID(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            List<Object> paramsRow = new ArrayList<>();
            paramsRow.add(tripId);
            paramsRow.add(tripEvent.getEventID());
            paramsRow.add(tripEvent.getEventType());
            paramsRow.add(tripEvent.getStartTime());
            paramsRow.add(tripEvent.getEndTime());
            paramsRow.add(tripEvent.getStartingLocation());
            paramsRow.add(tripEvent.getEndingLocation());
            params.add(paramsRow.toArray());
        }
        try {
            updateBatch(SQL_SAVE_TRIP_EVENTS, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insertTripLocations(String tripId, List<TripLocationDO> locations) {
        if (tripId == null || locations == null) {
            return;
        }
        List<Object[]> params = new ArrayList<>();
        for (TripLocationDO tripLocation : locations) {
            List<Object> paramsRow = new ArrayList<>();
            paramsRow.add(tripId);
            paramsRow.add(tripLocation.getTime());
            paramsRow.add(tripLocation.getLocation());
            params.add(paramsRow.toArray());
        }
        try {
            updateBatch(SQL_SAVE_TRIP_LOCATION, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public TripsModelDO getTripByID(String trip_id) {
        System.out.println("inside the getbyid of mysql");
        List<TripsModelDO> result = new ArrayList<>();
        try {
            List<Map<String, Object>> rows;
            try {
                rows = query(SQL_GET_TRIP_BY_ID, trip_id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Map<String, Object> row : rows) {
                TripsModelDO record = new TripsModelDO();
                try {
                    record.setTripID(BaseDao.cast(row.get("tripID".toUpperCase()), String.class));
                    record.setDriverID(BaseDao.cast(row.get("driverID".toUpperCase()), String.class));
                    record.setTripStartTime( BaseDao.cast(row.get("tripStartTime".toUpperCase()), Long.class));
                    record.setTripEndTime(BaseDao.cast(row.get("tripEndTime".toUpperCase()), Long.class));
                    record.setTripDate(BaseDao.cast(row.get("tripDate".toUpperCase()), String.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<Map<String, Object>> newrow;
                try {
                    newrow = query(SQL_GET_EVENTS_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {

                    throw new RuntimeException(e);
                }
                List<TripsEventsDO> res = new ArrayList<>();
                for (Map<String, Object> event : newrow) {
                    TripsEventsDO eventsModelDO = new TripsEventsDO();
                    res.add(eventsModelDO);
                    eventsModelDO.setEventID(BaseDao.cast(event.get("eventID".toUpperCase()), String.class));
                    eventsModelDO.setEventType(BaseDao.cast(event.get("eventType".toUpperCase()), String.class));
                    eventsModelDO.setStartTime(BaseDao.cast(event.get("startTime".toUpperCase()), String.class));
                    eventsModelDO.setEndTime(BaseDao.cast(event.get("endTime".toUpperCase()), String.class));
                    eventsModelDO.setStartingLocation(BaseDao.cast(event.get("startingLocation".toUpperCase()), String.class));
                    eventsModelDO.setEndingLocation(BaseDao.cast(event.get("endingLocation".toUpperCase()), String.class));
                }
                record.setTripEvents(res);
                List<Map<String, Object>> newObj;
                try {
                    newObj = query(SQL_GET_EVENT_COUNT_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<TripEventCountDO> countResult = new ArrayList<>();

                for (Map<String, Object> event : newObj) {
                    TripEventCountDO eventsCountsModelDO = new TripEventCountDO();
                    countResult.add(eventsCountsModelDO);
                    eventsCountsModelDO.setEventType(BaseDao.cast(event.get("eventType".toUpperCase()), String.class));
                    eventsCountsModelDO.setEventCount(BaseDao.cast(event.get("eventCount".toUpperCase()), Integer.class));
                }

                record.setTripEventCount(countResult);
                List<Map<String, Object>> roww;
                try {
                    roww = query(SQL_GET_LOCATION_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<TripLocationDO> locationResult = new ArrayList<>();
                for (Map<String, Object> event : roww) {
                    TripLocationDO locationModelDO = new TripLocationDO();
                    locationResult.add(locationModelDO);
                    locationModelDO.setTime(BaseDao.cast(event.get("time".toUpperCase()), Long.class));
                    locationModelDO.setLocation(BaseDao.cast(event.get("location".toUpperCase()), String.class));
                }
                record.setTripEventLocation(locationResult);
                result.add(record);
            }
            if (result.size() == 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0);
    }


    @Override
    public List<TripsModelDO> getTripByDriverID(String driver_id) {
        List<TripsModelDO> result = new ArrayList<>();
        try {
            String trip_id;
            List<Map<String, Object>> rows;
            try {
                rows = query(SQL_GET_TRIPS_BY_DRIVER_ID, driver_id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Map<String, Object> row : rows) {
                TripsModelDO record = new TripsModelDO();
                trip_id = BaseDao.cast(row.get("tripID".toUpperCase()), String.class);
                try {
                    record.setTripID(BaseDao.cast(row.get("tripID".toUpperCase()), String.class));
                    record.setDriverID(BaseDao.cast(row.get("driverID".toUpperCase()), String.class));
                    record.setTripStartTime(BaseDao.cast(row.get("tripStartTime".toUpperCase()), Long.class));
                    record.setTripEndTime(BaseDao.cast(row.get("tripEndTime".toUpperCase()), Long.class));
                    record.setTripDate(BaseDao.cast(row.get("tripDate".toUpperCase()), String.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<Map<String, Object>> newrow;
                try {
                    newrow = query(SQL_GET_EVENTS_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {

                    throw new RuntimeException(e);
                }
                List<TripsEventsDO> res = new ArrayList<>();
                for (Map<String, Object> event : newrow) {
                    TripsEventsDO eventsModelDO = new TripsEventsDO();
                    res.add(eventsModelDO);
                    eventsModelDO.setEventID(BaseDao.cast(event.get("eventID".toUpperCase()), String.class));
                    eventsModelDO.setEventType(BaseDao.cast(event.get("eventType".toUpperCase()), String.class));
                    eventsModelDO.setStartTime(BaseDao.cast(event.get("startTime".toUpperCase()), String.class));
                    eventsModelDO.setEndTime(BaseDao.cast(event.get("endTime".toUpperCase()), String.class));
                    eventsModelDO.setStartingLocation(BaseDao.cast(event.get("startingLocation".toUpperCase()), String.class));
                    eventsModelDO.setEndingLocation(BaseDao.cast(event.get("endingLocation".toUpperCase()), String.class));
                }
                record.setTripEvents(res);
                List<Map<String, Object>> newObj;
                try {
                    newObj = query(SQL_GET_EVENT_COUNT_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<TripEventCountDO> countResult = new ArrayList<>();

                for (Map<String, Object> event : newObj) {
                    TripEventCountDO eventsCountsModelDO = new TripEventCountDO();
                    countResult.add(eventsCountsModelDO);
                    eventsCountsModelDO.setEventType(BaseDao.cast(event.get("eventType".toUpperCase()), String.class));
                    eventsCountsModelDO.setEventCount(BaseDao.cast(event.get("eventCount".toUpperCase()), Integer.class));
                }
                record.setTripEventCount(countResult);
                List<Map<String, Object>> roww;
                try {
                    roww = query(SQL_GET_LOCATION_BY_TRIP_ID, trip_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<TripLocationDO> locationResult = new ArrayList<>();
                for (Map<String, Object> event : roww) {
                    TripLocationDO locationModelDO = new TripLocationDO();
                    locationResult.add(locationModelDO);
                    locationModelDO.setTime(BaseDao.cast(event.get("time".toUpperCase()), Long.class));
                    locationModelDO.setLocation(BaseDao.cast(event.get("location".toUpperCase()), String.class));
                }
                record.setTripEventLocation(locationResult);
                result.add(record);
            }
            if (result.size() == 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
