package im.qingtui.mongo.utils;

import static im.qingtui.mongo.MongoOperator.ID_KEY;
import static im.qingtui.mongo.MongoOperator.MONGO_ID_KEY;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * 文档工具类
 *
 * @author duanyu
 */
public final class DocumentUtils {

    private DocumentUtils() {
    }

    /**
     * 移除 mongodb 的 "_id" 属性
     */
    public static void removeObjectId(List<Document> documents) {
        for (Document document : documents) {
            document.remove(MONGO_ID_KEY);
        }
    }

    /**
     * 移除 mongodb 的 "_id" 属性
     */
    public static void removeObjectId(Document document) {
        document.remove(MONGO_ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为 "id"，且类型 value 类型为 String
     */
    public static void replaceObjectId(Document document) {
        replaceObjectId(document, ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为指定 newIdKey，且类型 value 类型为 String
     */
    public static void replaceObjectId(Document document, String newIdKey) {
        ObjectId objectId = document.getObjectId(MONGO_ID_KEY);
        document.put(newIdKey, objectId.toString());
        document.remove(MONGO_ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为 "id"，且类型 value 类型为 String
     */
    public static void replaceObjectId(List<Document> documents) {
        replaceObjectId(documents, ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为指定 newIdKey，，且类型 value 类型为 String
     */
    public static void replaceObjectId(List<Document> documents, String newIdKey) {
        for (Document document : documents) {
            replaceObjectId(document, newIdKey);
        }
    }

    /**
     * 解析数组集合
     */
    public static <T> List<T> parseArray(Object documentListObj, Class<T> clazz) {
        List<Document> documentList = (List<Document>) documentListObj;
        return parseArray(documentList, clazz);
    }

    /**
     * 解析数组集合
     */
    public static <T> List<T> parseArray(List<Document> documentList, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Document document : documentList) {
            result.add(JSONObject.parseObject(document.toJson(), clazz));
        }
        return result;
    }
}