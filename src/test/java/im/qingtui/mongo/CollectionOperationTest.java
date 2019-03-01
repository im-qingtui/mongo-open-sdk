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
    public void createCollectionMany() {
        int total = 5000;
        for (int i = 0; i < total; i++) {
            System.out.println(i);
            CollectionOperation.createCollection("testMany" + i);
        }
    }

    @Test
    public void getCollection() {
        MongoCollection<Document> test = CollectionOperation.getCollection("students");
        Assert.assertNotNull(test);
    }

    @Test
    public void drop() {
        int total = 5000;
        for (int i = 0; i < total; i++) {
            CollectionOperation.drop("testMany" + i);
        }
    }
}