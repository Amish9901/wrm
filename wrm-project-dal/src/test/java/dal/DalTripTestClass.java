package dal;

import dal.model.TripsModelDO;
import org.junit.Test;

import java.util.List;


public class DalTripTestClass {
    private DAOAbstractFactory daoFactory;
    public DalTripTestClass() {
    }

    public DalTripTestClass(DAOAbstractFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    @Test
    public void testGetByTripID() {
        TripsModelDAO tripsModelDao = daoFactory.getTripsModelDAO();
        String tripId = "t12345-0001";
        TripsModelDO tripOriginal = DalTripSaveData.CreateTrips(tripId);
        tripsModelDao.postData(tripOriginal);
        TripsModelDO tripRetrived = tripsModelDao.getTripByID(tripId);
        DalTripCompTest.compareTrip(tripOriginal, tripRetrived);
    }

    @Test
    public void testGetByDriverId() {
        TripsModelDAO tripsModelDao = daoFactory.getTripsModelDAO();
        String tripID1 = "t12345-0012";
        String tripID2 = "t12345-0013";
        String driverID = "d12345";
        TripsModelDO tripOriginal1 = DalTripSaveData.CreateTrips(tripID1);
        TripsModelDO tripOriginal2 = DalTripSaveData.CreateTrips(tripID2);
        tripsModelDao.postData(tripOriginal1);
        tripsModelDao.postData(tripOriginal2);
        List<TripsModelDO> tripRetrived = tripsModelDao.getTripByDriverID(driverID);
        DalTripCompTest.compareTrip(tripOriginal1, tripRetrived.get(1));
        DalTripCompTest.compareTrip(tripOriginal2, tripRetrived.get(2));
    }
}
