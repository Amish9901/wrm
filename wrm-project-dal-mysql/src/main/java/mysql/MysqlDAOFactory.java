package mysql;


import dal.DAOAbstractFactory;
import dal.TripsModelDAO;

public class MysqlDAOFactory implements DAOAbstractFactory {


    private MysqlTripModelDAO mysqlCarMakeModelDAO;

    public MysqlDAOFactory(String dataSourceName) {
        System.out.println("inside the mysql");

        mysqlCarMakeModelDAO = new MysqlTripModelDAO(dataSourceName);
    }


    @Override
    public TripsModelDAO getTripsModelDAO() {
        return mysqlCarMakeModelDAO;
    }
}
