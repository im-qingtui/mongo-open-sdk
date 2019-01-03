package im.qingtui.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author duanyu
 */
public class MongoOperation {

    private static Properties properties = new Properties();

    private static volatile MongoClient mongoClient;

    static {
        try {
            InputStream mongodbConfig = MongoOperation.class.getClassLoader().getResourceAsStream("mongodb.properties");
            if (mongodbConfig == null) {
                throw new IOException("加载 mongodb.properties 配置文件失败");
            }
            properties.load(mongodbConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     */
    public static MongoClient connect() {
        if (mongoClient == null) {
            doConnect();
        }
        return mongoClient;
    }

    /**
     * 创建连接并选择指定库
     */
    public static MongoDatabase connectDatabase() {
        MongoClient mongoClient = connect();
        return mongoClient.getDatabase(properties.getProperty("mongo.database"));
    }

    private static synchronized void doConnect() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(properties.getProperty("mongo.url"));
        }
    }
}