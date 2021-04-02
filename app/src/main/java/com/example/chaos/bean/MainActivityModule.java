package com.example.chaos.bean;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc 主页模块
 */
public class MainActivityModule {
    /**
     * 模块 ID
     */
    private final int moduleId;
    /**
     * 模块名称
     */
    private final String moduleName;
    /**
     * 模块注释
     */
    private final String moduleAnnotation;

    /**
     * constructor
     *
     * @param moduleId         模块 ID
     * @param moduleName       模块名称
     * @param moduleAnnotation 模块注释
     */
    public MainActivityModule(int moduleId, String moduleName, String moduleAnnotation) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleAnnotation = moduleAnnotation;
    }

    public int getModuleId() {
        return moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleAnnotation() {
        return moduleAnnotation;
    }
}
