package dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import dal.DAOAbstractFactory;
import dal.DalTripTestClass;
import dynamodb.model.SCHEMA;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class DynamoTest extends DalTripTestClass {

    private static DynamoDbConfig DYNAMO_CONFIG = new DynamoDbConfig() {
        @Override
        public String getLocalUrl() {
            return "http://localhost:8000";
        }

    };

    private static DAOAbstractFactory daoFactory;
    private static DynamoDBProxyServer server;

    public DynamoTest() {
        super(daoFactory);
    }

    @BeforeClass
    public static void before() {

        try {
            System.setProperty("sqlite4java.library.path", "target/test-classes/native-libs");
            server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory"});
            server.start();
            System.out.println("Server created");

            AmazonDynamoDB amazonDb = DynamoTestUtil.createLocalClient(DYNAMO_CONFIG.getLocalUrl());
            DynamoDB dynamoDB = new DynamoDB(amazonDb);
            System.out.println("Dynamo DB created");


            String tableName = SCHEMA.TABLE_NAME;
            DynamoTestUtil.createTable(dynamoDB, tableName, SCHEMA.COL_TRIP_ID, "S");
            System.out.println("Table created '" + tableName + "'");

            String indexName = SCHEMA.GSI_INDEX_NAME;
            DynamoTestUtil.createIndex(dynamoDB, tableName, indexName, SCHEMA.COL_DRIVER_ID, "S");
            System.out.println("Index created '" + indexName + "@" + tableName + "'");


            daoFactory = new DynamodbDAOFactory(DYNAMO_CONFIG);
            System.out.println("Test - dao factory created");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test() {
        System.out.println("running the dynamodb test");
    }

    @AfterClass
    public static void after() {

        if (server == null) {
            return;
        }
        try {
            server.stop();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
