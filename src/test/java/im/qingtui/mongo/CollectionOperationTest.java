package im.qingtui.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

public class CollectionOperationTest {

    @Test
    public void createCollection() {
        CollectionOperation.createCollection("test");
    }

    @Test
    public void getCollection() {
        MongoCollection<Document> test = CollectionOperation.getCollection("students");
        Assert.assertNotNull(test);
    }

    @Test
    public void drop() {
        CollectionOperation.drop("test");
    }
}