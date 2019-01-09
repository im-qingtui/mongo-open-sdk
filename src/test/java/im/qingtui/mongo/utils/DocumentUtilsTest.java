package im.qingtui.mongo.utils;

import static im.qingtui.mongo.MongoOperator.MONGO_ID_KEY;

import im.qingtui.mongo.DocumentOperation;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

public class DocumentUtilsTest {

    @Test
    public void removeObjectId() {
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        DocumentUtils.removeObjectId(document);
        Assert.assertNull(document.getObjectId(MONGO_ID_KEY));
    }

    @Test
    public void removeObjectId1() {
        List<Document> documentList = new ArrayList<>();
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        documentList.add(document);

        DocumentUtils.removeObjectId(documentList);
        Assert.assertNull(documentList.get(0).getObjectId(MONGO_ID_KEY));
    }

    @Test
    public void replaceObjectId() {
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        DocumentUtils.replaceObjectId(document);
        Assert.assertNull(document.getObjectId(MONGO_ID_KEY));
    }

    @Test
    public void replaceObjectId1() {
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        DocumentUtils.replaceObjectId(document, "idid");
        Assert.assertNull(document.getObjectId(MONGO_ID_KEY));
    }

    @Test
    public void replaceObjectId2() {
        List<Document> documentList = new ArrayList<>();
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        documentList.add(document);

        DocumentUtils.replaceObjectId(documentList);
        Assert.assertNull(documentList.get(0).getObjectId(MONGO_ID_KEY));
    }

    @Test
    public void replaceObjectId3() {
        List<Document> documentList = new ArrayList<>();
        Document document = DocumentOperation.findById("students", "5c20cb04b4ce27dac78c5911");
        documentList.add(document);

        DocumentUtils.replaceObjectId(documentList, "idid");
        Assert.assertNull(documentList.get(0).getObjectId(MONGO_ID_KEY));
    }
}