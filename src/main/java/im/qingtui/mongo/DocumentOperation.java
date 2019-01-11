package im.qingtui.mongo;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static im.qingtui.mongo.MongoOperator.MONGO_ID_KEY;
import static im.qingtui.mongo.MongoOperator.getDocumentKey;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import im.qingtui.mongo.entity.PageResult;
import im.qingtui.mongo.entity.Pageable;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * 文档操作
 *
 * @author duanyu
 */
public class DocumentOperation {

    /**
     * 插入文档数据
     *
     * @param collectionName 集合名称
     * @param document 文档数据
     * @return 插入后文档数据的id
     */
    public static String insertOne(String collectionName, Document document) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        collection.insertOne(document);
        return document.getObjectId(MongoOperator.MONGO_ID_KEY).toString();
    }

    /**
     * 批量插入文档数据
     *
     * @param collectionName 集合名称
     * @param documents 文档数据集合
     * @return 插入后文档数据集合的id
     */
    public static List<String> insertMany(String collectionName, List<Document> documents) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        collection.insertMany(documents);

        List<String> ids = new ArrayList<>();
        for (Document document : documents) {
            ids.add(document.getObjectId(MongoOperator.MONGO_ID_KEY).toString());
        }
        return ids;
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     */
    public static List<Document> find(String collectionName, Bson filter) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find(filter).iterator());
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param sort 排序规则
     */
    public static List<Document> findAndSort(String collectionName, Bson filter, Bson sort) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find(filter).iterator());
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param projection 需要投影的字段
     */
    public static List<Document> find(String collectionName, Bson filter, Bson projection) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find(filter).projection(projection).iterator());
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param projection 需要投影的字段
     * @param sort 排序规则
     */
    public static List<Document> find(String collectionName, Bson filter, Bson projection, Bson sort) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find(filter).projection(projection).sort(sort).iterator());
    }

    /**
     * 通过id获取文档数据
     *
     * @param collectionName 集合名称
     * @param id 文档数据的id
     */
    public static Document findById(String collectionName, String id) {
        List<Document> documents = find(collectionName, eq(new ObjectId(id)));

        if (documents == null || documents.size() <= 0) {
            return null;
        }

        return documents.get(0);
    }

    /**
     * 通过id获取文档数据
     *
     * @param collectionName 集合名称
     * @param id 文档数据的id
     * @param projection 需要投影的字段
     */
    public static Document findById(String collectionName, String id, Bson projection) {
        List<Document> documents = find(collectionName, eq(new ObjectId(id)), projection);

        if (documents == null || documents.size() <= 0) {
            return null;
        }

        return documents.get(0);
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     */
    public static Document findOne(String collectionName, Bson filter) {
        List<Document> documents = find(collectionName, filter);

        if (documents == null || documents.size() <= 0) {
            return null;
        }

        return documents.get(0);
    }

    /**
     * 查询文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param projection 需要投影的字段
     */
    public static Document findOne(String collectionName, Bson filter, Bson projection) {
        List<Document> documents = find(collectionName, filter, projection);

        if (documents == null || documents.size() <= 0) {
            return null;
        }

        return documents.get(0);
    }

    /**
     * 获取集合的所以文档数据
     *
     * @param collectionName 集合名称
     */
    public static List<Document> findAll(String collectionName) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find().iterator());
    }

    /**
     * 获取集合的所以文档数据
     *
     * @param collectionName 集合名称
     * @param projection 需要投影的字段
     */
    public static List<Document> findAll(String collectionName, Bson projection) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return toList(collection.find().projection(projection).iterator());
    }

    /**
     * 获取分页文档信息
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param pageable 分页对象
     * @return 分页结果
     */
    public static PageResult find(String collectionName, Bson filter, Pageable pageable) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);

        FindIterable<Document> filterCondition = collection.find(filter)
            .skip(pageable.skip())
            .limit(pageable.getSize());

        if (pageable.getOrders() != null) {
            filterCondition.sort(pageable.getBsonOrder());
        }

        MongoCursor<Document> documents = filterCondition.iterator();
        long total = countDocuments(collectionName, filter);

        return new PageResult(toList(documents), total);
    }

    /**
     * 获取分页文档信息
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param pageable 分页对象
     * @return 分页结果
     */
    public static PageResult find(String collectionName, Bson filter, Pageable pageable, Bson projection) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);

        FindIterable<Document> filterCondition = collection.find(filter)
            .projection(projection)
            .skip(pageable.skip())
            .limit(pageable.getSize());

        if (pageable.getOrders() != null) {
            filterCondition.sort(pageable.getBsonOrder());
        }

        MongoCursor<Document> documents = filterCondition.iterator();
        long total = countDocuments(collectionName, filter);

        return new PageResult(toList(documents), total);
    }

    /**
     * 聚合
     *
     * @param collectionName 集合名称
     * @param pipeline 聚合条件
     */
    public static List<Document> aggregate(String collectionName, List<? extends Bson> pipeline) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        MongoCursor<Document> aggregate = collection.aggregate(pipeline).iterator();
        return toList(aggregate);
    }

    /**
     * 聚合统计某个属性的值总共出现的次数
     *
     * @param collectionName 集合名称
     * @param filter 查询条件
     * @param fieldName 属性名
     * @param unwind 是否平铺属性值，只对于属性值为数组时有效
     */
    public static List<Document> aggregateCount(String collectionName, Bson filter, String fieldName, boolean unwind) {
        List<Bson> pipeline = new ArrayList<>();

        if (filter != null) {
            pipeline.add(match(filter));
        }

        if (unwind) {
            pipeline.add(unwind(getDocumentKey(fieldName)));
        }

        pipeline.add(group(getDocumentKey(fieldName), sum(MongoOperator.COUNT, 1)));

        return aggregate(collectionName, pipeline);
    }

    /**
     * 统计文档的数量
     *
     * @param collectionName 集合名称
     * @param filter 查询条件
     */
    public static long countDocuments(String collectionName, Bson filter) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return collection.countDocuments(filter);
    }

    /**
     * 统计文档的数量
     *
     * @param collectionName 集合名称
     */
    public static long countDocuments(String collectionName) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        return collection.countDocuments();
    }

    /**
     * 通过文档数据id删除文档
     *
     * @param collectionName 集合名称
     * @param id 文件数据id
     */
    public static void deleteById(String collectionName, String id) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        collection.deleteOne(eq(new ObjectId(id)));
    }

    /**
     * 通过文档数据id删除文档
     *
     * @param collectionName 集合名称
     * @param ids 文件数据id
     */
    public static void deleteById(String collectionName, List<String> ids) {
        List<ObjectId> objectIds = new ArrayList<>();
        for (String id : ids) {
            objectIds.add(new ObjectId(id));
        }
        deleteMany(collectionName, in(MONGO_ID_KEY, objectIds));
    }

    /**
     * 通过查询对象删除文档
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     */
    public static void deleteMany(String collectionName, Bson filter) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        collection.deleteMany(filter);
    }

    /**
     * 替换文档集合内的数据
     *
     * @param collectionName 集合名称
     * @param id 文档集合id
     * @param newDocument 新文档集合对象
     */
    public static void replaceOne(String collectionName, String id, Document newDocument) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        newDocument.remove(MongoOperator.MONGO_ID_KEY);
        collection.replaceOne(eq(new ObjectId(id)), newDocument);
    }

    /**
     * 更新文档集合内的数据
     *
     * @param collectionName 集合名称
     * @param filter 查询对象
     * @param update 更新语句
     */
    public static void updateMany(String collectionName, Bson filter, Bson update) {
        MongoCollection<Document> collection = CollectionOperation.getCollection(collectionName);
        collection.updateMany(filter, update);
    }

    private static List<Document> toList(MongoCursor<Document> mongoCursor) {
        List<Document> documents = new ArrayList<>();
        if (mongoCursor != null) {
            Document next;
            while (mongoCursor.hasNext()) {
                next = mongoCursor.next();
                documents.add(next);
            }
        }

        return documents;
    }
}