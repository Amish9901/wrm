package api;

import api.converter.ApiModelConverter;
import api.log.ResponseStatusHelper;
import api.service.DataService;
import dal.TripsModelDAO;
import dal.model.TripsModelDO;
import model.TripsModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
public class TripModelResources {
    @GET
    @Path("/tripid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TripsModel getTripsById(@PathParam("id") String id) {
        TripsModelDAO tripsModelDAO = DataService.getInstance().getDAOFactory().getTripsModelDAO();
        if(tripsModelDAO == null){
            ResponseStatusHelper.setStatus(Response.Status.NOT_FOUND);
            return null;
        }
        assert tripsModelDAO != null;
        TripsModelDO tripModel = tripsModelDAO.getTripByID(id);
        return ApiModelConverter.convertToApiModel(tripModel);
    }
    @GET
    @Path("/driverid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TripsModel> getTripByDriverID(@PathParam("id") String id) {
        TripsModelDAO tripsModelDAO = DataService.getInstance().getDAOFactory().getTripsModelDAO();
        if(tripsModelDAO == null){
                ResponseStatusHelper.setStatus(Response.Status.NOT_FOUND);
            return null;
        }
        assert tripsModelDAO != null;
        List<TripsModelDO> tripModel = tripsModelDAO.getTripByDriverID(id);
        return ApiModelConverter.convertToApiModel(tripModel);
    }

    @POST
    @Path("/postdata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String var(TripsModelDO trips) {
        TripsModelDAO tripsModelDAO = DataService.getInstance().getDAOFactory().getTripsModelDAO();
        System.out.println(trips.getTripID());
        if(tripsModelDAO == null){
            ResponseStatusHelper.setStatus(Response.Status.NOT_FOUND);
            System.out.println("no trips model");
            return null;
        }
        assert tripsModelDAO != null;
         tripsModelDAO.postData(trips);
        return "data has been saved successfully";
    }


}
