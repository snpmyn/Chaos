package com.chaos.bmob.kit;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.listener.QueryListListener;
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
    private static BmobKit instance;

    public static BmobKit getInstance() {
        if (null == instance) {
            synchronized (BmobKit.class) {
                if (null == instance) {
                    instance = new BmobKit();
                }
            }
        }
        return instance;
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
}
