package im.qingtui.mongo.entity;

import lombok.Data;

/**
 * 分页对象
 *
 * @author duanyu
 */
@Data
public class OffsetPageable extends PageAbstract {

    // 跳过的数据
    private int offset;

    public OffsetPageable() {
    }

    public OffsetPageable(int offset, int size, Order... orders) {
        this.offset = offset;
        super.setSize(size);
        super.setOrders(orders);
    }

    public OffsetPageable(int offset, int size) {
        this.offset = offset;
        super.setSize(size);
    }

    public int skip() {
        return offset;
    }
}