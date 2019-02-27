package im.qingtui.mongo.entity;

public enum OrderEnum {
    /**
     * 正序
     */
    ASC(1),
    /**
     * 倒序
     */
    DESC(-1);

    private int value;

    public int getValue() {
        return this.value;
    }

    OrderEnum(int value) {
        this.value = value;
    }
}