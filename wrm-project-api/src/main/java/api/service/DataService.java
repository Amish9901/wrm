package api.service;

import api.config.ApacheConfigBase;
import api.config.ApacheDataServiceAbstractConfig;
import api.config.ApacheDataServiceDynamoConfig;
import api.config.ApcheDataServiceConfig;
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

        // use "trip" to run the dynamo
        // use "driver" to run the mysql

        if(instance == null) {
            instance = new DataService(new ApcheDataServiceConfig("driver"));
        }
    }

    private  DataService(ApacheDataServiceAbstractConfig serviceConfig ) {
        if(serviceConfig.isEnabled()) {
            if("dynamo".equalsIgnoreCase(serviceConfig.getDataSource())){
               daoFactory = new DynamodbDAOFactory((DynamoDbConfig)serviceConfig);
            }
            else{
                daoFactory = new MysqlDAOFactory(serviceConfig.getDataBaseName());
            }
        }
    }

    public  DAOAbstractFactory getDAOFactory() {
        return daoFactory;
    }
}
