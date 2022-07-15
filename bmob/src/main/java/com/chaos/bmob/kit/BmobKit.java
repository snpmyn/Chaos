package com.chaos.bmob.kit;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2021/2/5
 *
 * @author zsp
 * @desc Bmob 配套元件
 * 为提供更稳定服务，后端启用 QPS 限制。推荐采用批量数据操作替换于循环多次提交请求操作，否则 QPS 达到限制报错。
 * 1. 批量操作每次只支持最大 50 条记录操作。
 * 2. 批量操作不支持对 User 表操作。
 * <p>
 * insertBatch	批量添加数据（返所添加数据 objectId 字段）
 * updateBatch	批量更新数据
 * deleteBatch	批量删除数据
 * doBatch	批量添加、批量更新、批量删除同时操作
 */
public class BmobKit {
    public static BmobKit getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 单个保存
     *
     * @param t            T
     * @param saveListener 保存监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void singleSave(@NotNull T t, SaveListener<String> saveListener) {
        t.save(saveListener);
    }

    /**
     * 多个保存
     *
     * @param bmobObjectList    数据集合
     * @param queryListListener 查询集合监听
     */
    public void multiSave(List<BmobObject> bmobObjectList, QueryListListener<BatchResult> queryListListener) {
        new BmobBatch().insertBatch(bmobObjectList).doBatch(queryListListener);
    }

    /**
     * 单个删除
     *
     * @param t              T
     * @param objectId       数据唯一标示
     * @param updateListener 更新监听
     * @param <T>            <T>
     */
    public <T extends BmobObject> void singleDelete(@NotNull T t, String objectId, UpdateListener updateListener) {
        t.delete(objectId, updateListener);
    }

    /**
     * 多个删除
     *
     * @param bmobObjectList    数据集合（所含对象仅需设被删数据之 objectId）
     * @param queryListListener 查询集合监听
     */
    public void multiDelete(List<BmobObject> bmobObjectList, QueryListListener<BatchResult> queryListListener) {
        new BmobBatch().deleteBatch(bmobObjectList).doBatch(queryListListener);
    }

    /**
     * 单个更新
     *
     * @param t              T
     * @param objectId       数据唯一标示
     * @param updateListener 更新监听
     * @param <T>            <T>
     */
    public <T extends BmobObject> void singleUpdate(@NotNull T t, String objectId, UpdateListener updateListener) {
        t.update(objectId, updateListener);
    }

    /**
     * 多个更新
     *
     * @param bmobObjectList    数据集合
     * @param queryListListener 查询集合监听
     */
    public void multiUpdate(List<BmobObject> bmobObjectList, QueryListListener<BatchResult> queryListListener) {
        new BmobBatch().updateBatch(bmobObjectList).doBatch(queryListListener);
    }

    /**
     * 单个查询
     *
     * @param objectId      对象 ID
     * @param queryListener 查询监听
     * @param <T>           <T>
     */
    public <T extends BmobObject> void singleQuery(String objectId, QueryListener<T> queryListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, queryListener);
    }

    /**
     * 多个查询
     *
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void multiQuery(FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(findListener);
    }

    /**
     * 等于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereEqualTo(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo(key, value);
        bmobQuery.findObjects(findListener);
    }

    /**
     * 并且等于查询
     *
     * @param key          并且等于查询
     * @param values       值数组
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereEqualToWithAnd(String key, @NonNull Object[] values, FindListener<T> findListener) {
        List<BmobQuery<T>> bmobQueryList = new ArrayList<>(values.length);
        for (Object value : values) {
            BmobQuery<T> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo(key, value);
            bmobQueryList.add(bmobQuery);
        }
        BmobQuery<T> multiBmobQuery = new BmobQuery<>();
        multiBmobQuery.and(bmobQueryList);
        multiBmobQuery.findObjects(findListener);
    }

    /**
     * 或等于查询
     *
     * @param key          并且等于查询
     * @param values       值数组
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereEqualToWithOr(String key, @NonNull Object[] values, FindListener<T> findListener) {
        List<BmobQuery<T>> bmobQueryList = new ArrayList<>(values.length);
        for (Object value : values) {
            BmobQuery<T> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo(key, value);
            bmobQueryList.add(bmobQuery);
        }
        BmobQuery<T> multiBmobQuery = new BmobQuery<>();
        multiBmobQuery.or(bmobQueryList);
        multiBmobQuery.findObjects(findListener);
    }

    /**
     * 不等于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereNotEqualTo(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereNotEqualTo(key, value);
        bmobQuery.findObjects(findListener);
    }

    /**
     * 小于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereLessThan(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereLessThan(key, value);
        bmobQuery.findObjects(findListener);
    }

    /**
     * 小于等于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereLessThanOrEqualTo(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereLessThanOrEqualTo(key, value);
        bmobQuery.findObjects(findListener);
    }

    /**
     * 大于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereGreaterThan(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereGreaterThan(key, value);
        bmobQuery.findObjects(findListener);
    }

    /**
     * 大于等于查询
     *
     * @param key          键
     * @param value        值
     * @param findListener 查找监听
     * @param <T>          <T>
     */
    public <T extends BmobObject> void queryByWhereGreaterThanOrEqualTo(String key, Object value, FindListener<T> findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereGreaterThanOrEqualTo(key, value);
        bmobQuery.findObjects(findListener);
    }

    private static final class InstanceHolder {
        static final BmobKit INSTANCE = new BmobKit();
    }
}
