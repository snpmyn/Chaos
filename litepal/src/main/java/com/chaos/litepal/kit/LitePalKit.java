package com.chaos.litepal.kit;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Collection;
import java.util.List;

/**
 * Created on 2020/12/18
 *
 * @author zsp
 * @desc LitePal 配套元件
 */
public class LitePalKit {
    private static LitePalKit instance;

    public static LitePalKit getInstance() {
        if (null == instance) {
            synchronized (LitePalKit.class) {
                if (null == instance) {
                    instance = new LitePalKit();
                }
            }
        }
        return instance;
    }

    /**
     * 单个保存
     *
     * @param t   T
     * @param <T> <T>
     * @return 保存成功否
     */
    public <T extends LitePalSupport> boolean singleSave(@NotNull T t) {
        return t.save();
    }

    /**
     * 多个保存
     * <p>
     * {@link LitePal#saveAll(Collection)} 返 true / false 两种返回值。
     * true 表集合中所有数据都存储到数据库；false 表存储过程中发生异常，无任何数据存储到数据库。
     * {@link LitePal#saveAll(Collection)} 内部开启了事务，全部存储成功或失败，无部分存储成功场景。
     *
     * @param collection Collection<? extends T>
     * @param <T>        <T>
     * @return 保存成功否
     */
    public <T extends LitePalSupport> boolean multiSave(Collection<? extends T> collection) {
        return LitePal.saveAll(collection);
    }

    /**
     * 单个删除
     *
     * @param modelClass 模型类
     * @param id         ID
     * @param <T>        <T>
     */
    public <T extends LitePalSupport> void singleDelete(Class<T> modelClass, long id) {
        LitePal.delete(modelClass, id);
    }

    /**
     * 多个删除
     *
     * @param modelClass 模型类
     * @param conditions 条件
     * @param <T>        <T>
     */
    public <T extends LitePalSupport> void multiDelete(Class<T> modelClass, String... conditions) {
        LitePal.deleteAll(modelClass, conditions);
    }

    /**
     * 删除数据库
     *
     * @param dbName 数据库名
     * @return 删除成功否
     */
    public boolean deleteDatabase(String dbName) {
        return LitePal.deleteDatabase(dbName);
    }

    /**
     * 单个更新
     *
     * @param t   T
     * @param id  ID
     * @param <T> <T>
     */
    public <T extends LitePalSupport> void singleUpdate(@NotNull T t, long id) {
        t.update(id);
    }

    /**
     * 多个更新
     *
     * @param t          T
     * @param conditions 条件
     * @param <T>        <T>
     */
    public <T extends LitePalSupport> void multiUpdate(@NotNull T t, String... conditions) {
        t.updateAll(conditions);
    }

    /**
     * 查询头条
     *
     * @param modelClass 模型类
     * @param <T>        <T>
     * @return 头条结果
     */
    public <T extends LitePalSupport> T findFirst(Class<T> modelClass) {
        return LitePal.findFirst(modelClass);
    }

    /**
     * 查询全部
     *
     * @param modelClass 模型类
     * @param <T>        <T>
     * @return 结果集合
     */
    public <T extends LitePalSupport> List<T> findAll(Class<T> modelClass) {
        return LitePal.findAll(modelClass);
    }

    /**
     * 条件查询
     *
     * @param modelClass 模型类
     * @param conditions 条件
     * @param <T>        <T>
     * @return 结果集合
     */
    public <T extends LitePalSupport> List<T> queryByWhere(Class<T> modelClass, String... conditions) {
        return LitePal.where(conditions).find(modelClass);
    }

    /**
     * 选择并且排序查询
     * <p>
     * asc 正序、desc 倒序。
     *
     * @param modelClass 模型类
     * @param column     列
     * @param columns    列
     * @param <T>        <T>
     * @return 结果集合
     */
    public <T extends LitePalSupport> List<T> queryBySelectAndOrder(Class<T> modelClass, String column, String... columns) {
        return LitePal.select(columns).order(column).find(modelClass);
    }
}
