package im.qingtui.mongo.entity;

import java.util.List;
import lombok.Data;
import org.bson.Document;

/**
 * 分页结果
 *
 * @author duanyu
 */
@Data
public class PageResult {

    private List<Document> list;

    private long total;

    public PageResult(List<Document> list, long total) {
        this.list = list;
        this.total = total;
    }
}