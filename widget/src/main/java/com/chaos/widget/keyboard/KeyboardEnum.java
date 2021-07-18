package com.chaos.widget.keyboard;

/**
 * Created on 2021/7/12
 *
 * @author zsp
 * @desc 键盘枚举
 */
public enum KeyboardEnum {
    /**
     * 一
     */
    ONE(ActionEnum.ADD, "1"),
    /**
     * 二
     */
    TWO(ActionEnum.ADD, "2"),
    /**
     * 三
     */
    THREE(ActionEnum.ADD, "3"),
    /**
     * 四
     */
    FOUR(ActionEnum.ADD, "4"),
    /**
     * 五
     */
    FIVE(ActionEnum.ADD, "5"),
    /**
     * 六
     */
    SIX(ActionEnum.ADD, "6"),
    /**
     * 七
     */
    SEVEN(ActionEnum.ADD, "7"),
    /**
     * 八
     */
    EIGHT(ActionEnum.ADD, "8"),
    /**
     * 九
     */
    NINE(ActionEnum.ADD, "9"),
    /**
     * 点
     */
    DOT(ActionEnum.ADD, "."),
    /**
     * 零
     */
    ZERO(ActionEnum.ADD, "0"),
    /**
     * 零零
     */
    DOUBLE_ZERO(ActionEnum.ADD, "00"),
    /**
     * 删除
     */
    DELETE(ActionEnum.DELETE, "delete"),
    /**
     * 完成
     */
    COMPLETE(ActionEnum.COMPLETE, "complete"),
    /**
     * 长按删除
     */
    LONG_CLICK_TO_DELETE(ActionEnum.LONG_CLICK_TO_DELETE, "LongClickToDelete");

    public enum ActionEnum {
        /**
         * 添加
         */
        ADD,
        /**
         * 删除
         */
        DELETE,
        /**
         * 完成
         */
        COMPLETE,
        /**
         * 长按删除
         */
        LONG_CLICK_TO_DELETE
    }

    private ActionEnum type;
    private String value;

    KeyboardEnum(ActionEnum type, String value) {
        this.type = type;
        this.value = value;
    }

    public ActionEnum getType() {
        return type;
    }

    public void setType(ActionEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
