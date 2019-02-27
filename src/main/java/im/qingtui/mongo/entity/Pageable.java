package im.qingtui.mongo.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * 分页对象
 *
 * @author duanyu
 */
@Data
public class Pageable extends PageAbstract {

    // 页码，从1开始
    private int page;

    public Pageable() {
    }

    public Pageable(int page, int size, Order... orders) {
        this.page = page;
        super.setSize(size);
        super.setOrders(orders);
    }

    public Pageable(int page, int size) {
        this.page = page;
        super.setSize(size);
    }

    public int skip() {
        return (page - 1) * getSize();
    }
}