package com.chaos.widget.adapttemplate.bean;

/**
 * Created on 2021/3/29
 *
 * @author zsp
 * @desc 模块
 */
public class ModuleBean {
    /**
     * 模块 ID
     */
    private final int functionId;
    /**
     * 模块图标资源 ID
     */
    private final int functionIconResId;
    /**
     * 模块名称
     */
    private final int functionName;

    /**
     * constructor
     *
     * @param functionId        模块 ID
     * @param functionIconResId 模块图标资源 ID
     * @param functionName      模块名称
     */
    public ModuleBean(int functionId, int functionIconResId, int functionName) {
        this.functionId = functionId;
        this.functionIconResId = functionIconResId;
        this.functionName = functionName;
    }

    public int getFunctionId() {
        return functionId;
    }

    public int getFunctionIconResId() {
        return functionIconResId;
    }

    public int getFunctionName() {
        return functionName;
    }
}
