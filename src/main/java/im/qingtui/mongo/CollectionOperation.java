package im.qingtui.mongo;

import static com.mongodb.assertions.Assertions.notNull;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import im.qingtui.mongo.entity.Student;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * 集合操作
 *
 * @author duanyu
 */
public class CollectionOperation {

    private static MongoDatabase mongoDatabase = MongoOperation.connectDatabase();

    /**
     * 创建集合
     *
     * @param collectionName 集合名称
     */
    public static void createCollection(String collectionName) {
        notNull("collectionName", collectionName);
        mongoDatabase.createCollection(collectionName);
    }

    /**
     * 获取集合对象
     *
     * @param collectionName 集合名称
     * @return 集合对象
     */
    public static MongoCollection<Document> getCollection(String collectionName) {
        notNull("collectionName", collectionName);
        return mongoDatabase.getCollection(collectionName);
    }

    /**
     * 获取集合对象
     *
     * @param collectionName 集合名称
     * @return 集合对象
     */
    public static <T> MongoCollection<T> getCollection(String collectionName, Class<T> documentClass) {
        notNull("collectionName", collectionName);
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(new Student());
        return mongoDatabase.getCollection(collectionName, documentClass).withCodecRegistry(codecRegistry);
    }

    /**
     * 删除集合
     *
     * @param collectionName 集合名称
     */
    public static void drop(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.drop();
    }
}