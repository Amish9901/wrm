package dynamodb;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;

class DynamoTestUtil {
    static AmazonDynamoDB createLocalClient(String localUrl) {

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localUrl, DynamodbDAOFactory.DUMMY_REGION))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("any", "any")))
                .build();
    }

    private static Long readCapacityUnits = 1L;
    private static Long writeCapacityUnits = 1L;

    static void createTable(DynamoDB dynamoDB, String tableName, String partitionKeyName, String partitionKeyType) {
        createTable(dynamoDB, tableName, partitionKeyName, partitionKeyType, null, null);
    }

    static void createTable(DynamoDB dynamoDB, String tableName, String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {

        try {

            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
            // key

            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
            attributeDefinitions
                    .add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

            if (sortKeyName != null) {
                keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
                // key
                attributeDefinitions
                        .add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
            }

            CreateTableRequest request = new CreateTableRequest()
                    .withTableName(tableName)
                    .withKeySchema(keySchema)
                    .withProvisionedThroughput(
                            new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits).withWriteCapacityUnits(writeCapacityUnits)
                    );
            request.setAttributeDefinitions(attributeDefinitions);

            System.out.println("Issuing CreateTable request for " + tableName);
            Table table = dynamoDB.createTable(request);
            System.out.println(table.getDescription());
            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
            table.waitForActive();

        } catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }
    static void createIndex(DynamoDB dynamoDB, String tableName, String indexName, String partitionKeyName, String partitionKeyType) {

        Table table = dynamoDB.getTable(tableName);

        Index index = table.createGSI(
                new CreateGlobalSecondaryIndexAction()
                        .withIndexName(indexName)
                        .withKeySchema(new KeySchemaElement(partitionKeyName, KeyType.HASH))
                        .withProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits))
                        .withProjection(new Projection().withProjectionType(ProjectionType.ALL)),
                new AttributeDefinition(partitionKeyName, partitionKeyType));

        try {
            index.waitForActive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
