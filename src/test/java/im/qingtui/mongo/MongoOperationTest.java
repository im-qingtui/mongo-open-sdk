package im.qingtui.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Assert;
import org.testng.annotations.Test;

public class MongoOperationTest {

    @Test(threadPoolSize = 30, invocationCount = 20, timeOut = 2000)
    public void connect() {
        MongoClient connect = MongoOperation.connect();
        Assert.assertNotNull(connect);
    }

    @Test
    public void connectDatabase() {
        MongoDatabase connect = MongoOperation.connectDatabase();
        Assert.assertNotNull(connect);
    }
}