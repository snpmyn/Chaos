package com.chaos.widget.dialog.materialalertdialog;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created on 2021/3/1
 *
 * @author zsp
 * @desc 单选材料对话框配套元件
 */
public class SingleChooseMaterialAlertDialogKit {
    private static SingleChooseMaterialAlertDialogKit instance;

    public static SingleChooseMaterialAlertDialogKit getInstance() {
        if (null == instance) {
            synchronized (SingleChooseMaterialAlertDialogKit.class) {
                if (null == instance) {
                    instance = new SingleChooseMaterialAlertDialogKit();
                }
            }
        }
        return instance;
    }

    /**
     * 显示
     *
     * @param appCompatActivity                           活动
     * @param items                                       条目
     * @param singleChooseMaterialAlertDialogKitInterface 单选材料对话框配套元件接口
     */
    public void show(AppCompatActivity appCompatActivity, CharSequence[] items, SingleChooseMaterialAlertDialogKitInterface singleChooseMaterialAlertDialogKitInterface) {
        WeakReference<AppCompatActivity> weakReference = new WeakReference<>(appCompatActivity);
        new MyMaterialAlertDialogBuilder(weakReference.get())
                .setItems(items, (dialog, which) -> singleChooseMaterialAlertDialogKitInterface.onItemClick((String) items[which])).show();
    }

    /**
     * 单选材料对话框配套元件接口
     */
    public interface SingleChooseMaterialAlertDialogKitInterface {
        /**
         * 条目点击
         *
         * @param value 值
         */
        void onItemClick(String value);
    }
}
