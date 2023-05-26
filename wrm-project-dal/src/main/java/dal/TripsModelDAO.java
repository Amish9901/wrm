package dal;



import dal.model.TripsModelDO;

import java.util.List;

public interface TripsModelDAO {
    void postData(TripsModelDO trips);
    TripsModelDO getTripByID(String trip_id);
    List<TripsModelDO> getTripByDriverID(String driver_id);
}
