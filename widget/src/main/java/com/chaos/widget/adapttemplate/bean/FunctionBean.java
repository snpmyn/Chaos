package com.chaos.widget.adapttemplate.bean;

/**
 * Created on 2021/3/29
 *
 * @author zsp
 * @desc 功能
 */
public class FunctionBean {
    /**
     * 功能 ID
     */
    private final int functionId;
    /**
     * 功能图标资源 ID
     */
    private final int functionIconResId;
    /**
     * 功能名称资源 ID
     */
    private final int functionNameResId;

    /**
     * constructor
     *
     * @param functionId        功能 ID
     * @param functionIconResId 功能图标资源 ID
     * @param functionNameResId 功能名称资源 ID
     */
    public FunctionBean(int functionId, int functionIconResId, int functionNameResId) {
        this.functionId = functionId;
        this.functionIconResId = functionIconResId;
        this.functionNameResId = functionNameResId;
    }

    public int getFunctionId() {
        return functionId;
    }

    public int getFunctionIconResId() {
        return functionIconResId;
    }

    public int getFunctionNameResId() {
        return functionNameResId;
    }
}
