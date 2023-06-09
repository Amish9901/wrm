package api.service;

import api.config.ApacheDataServiceAbstractConfig;
import api.config.ApacheDataServiceTripsConfig;
import api.config.ApcheDataServiceConfig;
import api.config.ApcheDataServiceDriverConfig;
import dal.DAOAbstractFactory;
import dynamodb.DynamoDbConfig;
import dynamodb.DynamodbDAOFactory;
import mysql.MysqlDAOFactory;

public class DataService {
    private static DataService instance;
    private static DAOAbstractFactory daoFactory;
    public static DataService getInstance() {
        return instance;
    }

    public static void initalize(){

        // no use
//        ApcheDataServiceDriverConfig serviceConfigDriver = new ApcheDataServiceDriverConfig();
//        new DataService(serviceConfigDriver);
//
        // no use
//        ApacheDataServiceTripsConfig serviceConfigTrip = new ApacheDataServiceTripsConfig("trip");
//        new DataService(serviceConfigTrip);
        // just trying to implement new thing but failed
//
//        ApacheDataServiceAbstractConfig serviceConfig = new ApacheDataServiceAbstractConfig("driver");
//        new DataService(serviceConfig);

        // use "trip" to run the dynamo
        // use "driver" to run the mysql

        ApcheDataServiceConfig serviceConfig = new ApcheDataServiceConfig("driver");
        new DataService(serviceConfig);


    }

    private  DataService(ApacheDataServiceAbstractConfig serviceConfig ) {
        if(serviceConfig.isEnabled()) {
            if("dynamo".equalsIgnoreCase(serviceConfig.getDataSource())){
               daoFactory = new DynamodbDAOFactory((DynamoDbConfig)serviceConfig);
            }
            else{
                daoFactory = new MysqlDAOFactory(serviceConfig.getDataSource());
            }
        }
    }

    public static DAOAbstractFactory getDAOFactory() {
        return daoFactory;
    }
}
