package im.qingtui.mongo.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * 分页抽象类
 *
 * @author duanyu
 */
@Data
public abstract class PageAbstract {

    // 每页数量
    private int size;
    // 排序字段
    private List<Order> orders;

    public abstract int skip();

    public void setOrders(Order... orders) {
        this.orders = Arrays.asList(orders);
    }

    public Bson getBsonOrder() {
        Document orderDocument = new Document();
        if (orders != null) {
            for (Order order : orders) {
                orderDocument.append(order.getOrderName(), order.getOrderValue());
            }
        }
        return orderDocument;
    }
}