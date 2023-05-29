package dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dal.DAOAbstractFactory;
import dal.TripsModelDAO;


public class DynamodbDAOFactory implements DAOAbstractFactory {
    static final String DUMMY_REGION = "us-west-2";
    private DynamoDBTripsModelDAO dynamoDBTripsModelDAO;

    public DynamodbDAOFactory(DynamoDbConfig serviceConfig ) {
        AmazonDynamoDB amazonDynamoDB = createDynamoClient(serviceConfig);
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
         dynamoDBTripsModelDAO = new DynamoDBTripsModelDAO(mapper);
    }


    private static AmazonDynamoDB createDynamoClient(DynamoDbConfig serviceConfig) {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        if (serviceConfig.getLocalUrl() != null) {
            builder = builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceConfig.getLocalUrl(), DUMMY_REGION));
            builder = builder.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("any", "any")));
        } else {
            builder = builder.withCredentials(new DefaultAWSCredentialsProviderChain());
            builder = builder.withRegion(DUMMY_REGION);
        }
        return builder.build();
    }


    @Override
    public TripsModelDAO getTripsModelDAO() {
        return dynamoDBTripsModelDAO;
    }


}
