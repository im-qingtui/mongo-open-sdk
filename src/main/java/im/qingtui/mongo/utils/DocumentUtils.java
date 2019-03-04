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
    public static List<Document> removeObjectId(List<Document> documents) {
        for (Document document : documents) {
            document.remove(MONGO_ID_KEY);
        }
        return documents;
    }

    /**
     * 移除 mongodb 的 "_id" 属性
     */
    public static Document removeObjectId(Document document) {
        document.remove(MONGO_ID_KEY);
        return document;
    }

    /**
     * 替换 mongodb 的 "_id" 属性为 "id"，且类型 value 类型为 String
     */
    public static Document replaceObjectId(Document document) {
        return replaceObjectId(document, ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为指定 newIdKey，且类型 value 类型为 String
     */
    public static Document replaceObjectId(Document document, String newIdKey) {
        ObjectId objectId = document.getObjectId(MONGO_ID_KEY);
        document.put(newIdKey, objectId.toString());
        document.remove(MONGO_ID_KEY);
        return document;
    }

    /**
     * 替换 mongodb 的 "_id" 属性为 "id"，且类型 value 类型为 String
     */
    public static List<Document> replaceObjectId(List<Document> documents) {
        return replaceObjectId(documents, ID_KEY);
    }

    /**
     * 替换 mongodb 的 "_id" 属性为指定 newIdKey，，且类型 value 类型为 String
     */
    public static List<Document> replaceObjectId(List<Document> documents, String newIdKey) {
        for (Document document : documents) {
            replaceObjectId(document, newIdKey);
        }
        return documents;
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

    /**
     * 将聚合函数的结果转换为document对象
     *
     * @param documents 待转换的集合
     * @param fileName 列名
     */
    public static Document aggregateResultToDocument(List<Document> documents, String fileName) {
        Document result = new Document();
        for (Document document : documents) {
            Object key = document.get(MONGO_ID_KEY);
            if (key != null) {
                result.append(key.toString(), document.get(fileName));
            }
        }
        return result;
    }
}