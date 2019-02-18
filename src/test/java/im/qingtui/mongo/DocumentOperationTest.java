package im.qingtui.mongo;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;
import static im.qingtui.mongo.MongoOperator.getDocumentKey;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import im.qingtui.mongo.entity.PageResult;
import im.qingtui.mongo.entity.Pageable;
import im.qingtui.mongo.entity.Pageable.Order;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Assert;
import org.junit.Test;

public class DocumentOperationTest {

    String collectionName = "students";
    Document document = new Document("name", "段誉").append("sex", "男").append("age", 26);

    @Test
    public void insertOne() {
        String s = DocumentOperation.insertOne(collectionName, document);
        Assert.assertNotNull(s);
    }

    @Test
    public void insertMany() {
        List<Document> list = new ArrayList<Document>();
        list.add(new Document("name", "段誉"));
        List<String> s = DocumentOperation.insertMany(collectionName, list);
        Assert.assertNotNull(s);
        Assert.assertTrue(s.size() > 0);
    }

    @Test
    public void findByNullId() {
        Document document = DocumentOperation.findById(collectionName, "5c2095b430439274c4d04ae2");
        Assert.assertNotNull(document);
    }

    @Test
    public void findByIdProjection() {
        Document document = DocumentOperation.findById(collectionName, "5c2095b430439274c4d04ae2", include("name"));
        System.out.println(document);
        Assert.assertNotNull(document);
    }

    @Test
    public void findById() {
        Document document = DocumentOperation.findById(collectionName, "5c2095b430439274c4d04ae2");
        Assert.assertNotNull(document);
    }

    @Test
    public void findByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("5c2095b430439274c4d04ae2");
        ids.add("5c2087803043926d745250c2");
        List<Document> document = DocumentOperation.findById(collectionName, ids);
        System.out.println(document);
        Assert.assertNotNull(document);
    }

    @Test
    public void find() {
        BasicDBObject test = new BasicDBObject("$regex",".*");
        List<Document> documents = DocumentOperation.find(collectionName, eq("name", test));
        List<Document> documents2 = DocumentOperation.find(collectionName, eq("name", "2 }, { c : /.*/"));
        Assert.assertNotNull(documents);
        Assert.assertTrue(documents.size() > 0);
    }

    @Test
    public void findCode() {
        List<Document> documents = DocumentOperation.find(collectionName, eq("name", "段誉1"));
        Assert.assertNotNull(documents);
        Assert.assertTrue(documents.size() > 0);
    }

    @Test
    public void findProjection() {
        List<Document> documents = DocumentOperation.find(collectionName, eq("name", "段誉"), include("name"));
        System.out.println(documents);
        Assert.assertNotNull(documents);
        Assert.assertTrue(documents.size() > 0);
    }

    @Test
    public void find1() {
        List<Document> documents = DocumentOperation.findAll(collectionName);
        Assert.assertNotNull(documents);
        Assert.assertTrue(documents.size() > 0);
    }

    @Test
    public void find1Projection() {
        List<Document> documents = DocumentOperation.findAll(collectionName, include("name"));
        System.out.println(documents);
        Assert.assertNotNull(documents);
        Assert.assertTrue(documents.size() > 0);
    }

    @Test
    public void find2() {
        Pageable pageable = new Pageable();
        pageable.setPage(1);
        pageable.setSize(5);
        pageable.setOrders(Order.desc("age"));
        PageResult pageResult = DocumentOperation.find(collectionName, eq("name", "段誉1"), pageable);
        System.out.println(pageResult);
        Assert.assertNotNull(pageResult);
        Assert.assertTrue(pageResult.getList().size() > 0);
    }

    @Test
    public void find2Projection() {
        Pageable pageable = new Pageable();
        pageable.setPage(1);
        pageable.setSize(5);
        pageable.setOrders(Order.desc("age"));
        PageResult pageResult = DocumentOperation.find(collectionName, eq("address", "重庆蔡家"), pageable, include("name"));
        System.out.println(pageResult);
        Assert.assertNotNull(pageResult);
        Assert.assertTrue(pageResult.getList().size() > 0);
    }

    @Test
    public void find3() {
        Document document = DocumentOperation.findOne(collectionName, eq("address", "重庆蔡家"));
        Assert.assertNotNull(document);
    }

    @Test
    public void find3Projection() {
        Document document = DocumentOperation.findOne(collectionName, eq("name", "段誉"), Projections.include("name"));
        Assert.assertNotNull(document);
    }

    @Test
    public void findAndSort() {
        List<Document> document = DocumentOperation.findAndSort(collectionName, eq("address", "重庆蔡家"), Sorts.ascending("name"));
        System.out.println(document);
        Assert.assertNotNull(document);
        Assert.assertTrue(document.size() > 0);
    }

    @Test
    public void findAndProjection() {
        List<Document> document = DocumentOperation.find(collectionName, eq("address", "重庆蔡家"), Projections.include("name"), Sorts.ascending("name"));
        System.out.println(document);
        Assert.assertNotNull(document);
        Assert.assertTrue(document.size() > 0);
    }

    @Test
    public void aggregate() {
        List<Bson> pip = new ArrayList<Bson>();
        Bson group = group(getDocumentKey("age"), avg("age", getDocumentKey("age")));
        pip.add(group);

        List<Document> aggregate = DocumentOperation.aggregate(collectionName, pip);
        Assert.assertNotNull(aggregate);
        System.out.println(aggregate);
        Assert.assertTrue(aggregate.size() > 0);
    }

    @Test
    public void aggregateField() {
        List<Document> aggregate = DocumentOperation.aggregateCount(collectionName, eq("address", "重庆蔡家"), "age", false);
        System.out.println(aggregate);
        Assert.assertNotNull(aggregate);
        Assert.assertTrue(aggregate.size() > 0);
    }

    @Test
    public void countDocuments() {
        long l = DocumentOperation.countDocuments(collectionName, eq("name", "段誉"));
        Assert.assertTrue(l > 0);
    }

    @Test
    public void countDocumentsAll() {
        long l = DocumentOperation.countDocuments(collectionName);
        Assert.assertTrue(l > 0);
    }

    @Test
    public void deleteById() {
        String id = "5c20cb04b4ce27dac78c59f8";
        DocumentOperation.deleteById(collectionName, id);
    }

    @Test
    public void deleteByIdMany() {
        List<String> idList = new ArrayList<>();
        idList.add("5c22e40c30439283fafa1156");
        idList.add("5c24342ee192046ba50ce306");
        DocumentOperation.deleteById(collectionName, idList);
    }

    @Test
    public void deleteMany() {
        DocumentOperation.deleteMany(collectionName, eq("name", "段誉1"));
    }

    @Test
    public void replaceOne() {
        Document query = new Document("name", "段誉");
        DocumentOperation.replaceOne(collectionName, "5c20cb05b4ce27dac78c59fa", query);
    }

    @Test
    public void updateMany() {
        DocumentOperation.updateMany(collectionName, eq("name", "段誉"), set("name", "段誉1"));
    }
}