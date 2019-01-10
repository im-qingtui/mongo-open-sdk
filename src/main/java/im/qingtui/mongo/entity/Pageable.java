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
public class Pageable {

    // 页码，从1开始
    private int page;
    // 每页数量
    private int size;
    // 排序字段
    private List<Order> orders;

    public Pageable() {
    }

    public Pageable(int page, int size, Order... orders) {
        this.page = page;
        this.size = size;
        this.orders = Arrays.asList(orders);
    }

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int skip() {
        return (page - 1) * size;
    }

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

    @Data
    public static class Order {

        private String orderName;
        private OrderEnum orderEnum;

        public int getOrderValue() {
            return getOrderEnum().getValue();
        }

        private Order(String orderName, OrderEnum orderEnum) {
            this.orderName = orderName;
            this.orderEnum = orderEnum;
        }

        public static Order asc(String orderName) {
            return new Order(orderName, OrderEnum.ASC);
        }

        public static Order desc(String orderName) {
            return new Order(orderName, OrderEnum.DESC);
        }
    }

    public enum OrderEnum {
        ASC(1),
        DESC(-1);

        private int value;

        public int getValue() {
            return this.value;
        }

        OrderEnum(int value) {
            this.value = value;
        }
    }
}