package mysql;

import com.edriving.commons.dal.jdbc.JdbcConnectionPool;

import dal.DalTripTestClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLTripTest extends DalTripTestClass {
    public MySQLTripTest() {
        super(myFactory);
    }

    private static MysqlDAOFactory myFactory;
    private static String CREATE_WRM_TRIP_TABLE =
            "CREATE TABLE wrm_trips ("+
                    "tripID VARCHAR(45) PRIMARY KEY,"+
                    "driverID VARCHAR(45),"+
                    "tripStartTime BIGINT,"+
                    "tripEndTime BIGINT,"+
                    "tripDate VARCHAR(45)"+")";
    private static String CREATE_WRM_TRIP_EVENTS_TABLE =
            "CREATE TABLE wrm_trip_events ("+
                    "tripID VARCHAR(45),"+
                    "eventID VARCHAR(45),"+
                    "startTime VARCHAR(45),"+
                    "endTime VARCHAR(45),"+
                    "startingLocation VARCHAR(45),"+
                    "endingLocation VARCHAR(45),"+
                    "eventType VARCHAR(45),"+
                    "FOREIGN KEY (tripID) REFERENCES wrm_trips(tripID)"+
                    ")";
    private static String CREATE_WRM_TRIP_LOCATION_TABLE=
            "CREATE TABLE wrm_trip_locations ("+
                    "tripID VARCHAR(45),"+
                    "time BIGINT,"+
                    "location VARCHAR(45),"+
                    "FOREIGN KEY (tripID) REFERENCES wrm_trips(tripID)"+
                    ")";
    ;
    private static String CREATE_WRM_TRIP_EVENT_COUNT_TABLE =
            "CREATE TABLE wrm_trip_event_counts ("+
                    "tripID VARCHAR(45),"+
                    "eventType VARCHAR(45),"+
                    "eventCount INT,"+
                    "FOREIGN KEY (tripID) REFERENCES wrm_trips(tripID)"+
                    ")";

    private static final String DB_POOL_NAME = "sql-test";


    @BeforeClass
    public static void beforeclass () throws SQLException {
        DataSource dataSource = JdbcConnectionPool.getInstance().initializeDataSource(DB_POOL_NAME);
        myFactory = new MysqlDAOFactory(DB_POOL_NAME);
        Connection conn = dataSource.getConnection();
        conn.prepareStatement(CREATE_WRM_TRIP_TABLE).executeUpdate();
        conn.prepareStatement(CREATE_WRM_TRIP_EVENTS_TABLE).executeUpdate();
        conn.prepareStatement(CREATE_WRM_TRIP_LOCATION_TABLE).executeUpdate();
        conn.prepareStatement(CREATE_WRM_TRIP_EVENT_COUNT_TABLE).executeUpdate();
        conn.close();
    }

    @AfterClass
    public static void afterclass () {
        System.out.println("test finished");
    }

}
