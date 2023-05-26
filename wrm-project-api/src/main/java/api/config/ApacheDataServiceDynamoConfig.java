package api.config;

import dynamodb.DynamoDbConfig;

public class ApacheDataServiceDynamoConfig extends ApacheDataServiceAbstractConfig implements DynamoDbConfig {
    private static final String DynamoDB_URL = "dynamo.local_url";

    public ApacheDataServiceDynamoConfig(String prefix) {
        super(prefix);
    }


    @Override
    public String getLocalUrl() {
        return getConfig().getString(DynamoDB_URL);
    }
}
