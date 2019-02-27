package im.qingtui.mongo.entity;

import lombok.Data;

@Data
public class Order {

    private String orderName;
    private OrderEnum orderEnum;

    public static Order asc(String orderName) {
        return new Order(orderName, OrderEnum.ASC);
    }

    public static Order desc(String orderName) {
        return new Order(orderName, OrderEnum.DESC);
    }

    private Order(String orderName, OrderEnum orderEnum) {
        this.orderName = orderName;
        this.orderEnum = orderEnum;
    }

    public int getOrderValue() {
        return getOrderEnum().getValue();
    }
}