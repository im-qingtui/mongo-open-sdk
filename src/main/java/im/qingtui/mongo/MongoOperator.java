package im.qingtui.mongo;

/**
 * mongo操作符
 *
 * @author duanyu
 */
public class MongoOperator {

    /**
     * 基本符号
     */
    public static final String SYMBOL = "$";

    /**
     * mongodb的id
     */
    public static final String MONGO_ID_KEY = "_id";

    /**
     * 获取文档属性的变量
     *
     * @param documentKey 文档key
     */
    public static String getDocumentKey(String documentKey) {
        return MongoOperator.SYMBOL + documentKey;
    }
}