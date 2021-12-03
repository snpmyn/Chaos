package com.chaos.widget.adapttemplate.bean;

/**
 * Created on 2021/12/2
 *
 * @author zsp
 * @desc 功能
 */
public class FunctionBean {
    /**
     * 功能 ID
     */
    private final int functionModuleId;
    /**
     * 功能名称
     */
    private final String functionModuleName;
    /**
     * 功能显示
     */
    private boolean functionModuleShow;

    /**
     * constructor
     *
     * @param functionModuleId   功能 ID
     * @param functionModuleName 功能名称
     * @param functionModuleShow 功能显示
     */
    public FunctionBean(int functionModuleId, String functionModuleName, boolean functionModuleShow) {
        this.functionModuleId = functionModuleId;
        this.functionModuleName = functionModuleName;
        this.functionModuleShow = functionModuleShow;
    }

    public int getFunctionModuleId() {
        return functionModuleId;
    }

    public String getFunctionModuleName() {
        return functionModuleName;
    }

    public boolean isFunctionModuleShow() {
        return functionModuleShow;
    }

    public void setFunctionModuleShow(boolean functionModuleShow) {
        this.functionModuleShow = functionModuleShow;
    }
}
