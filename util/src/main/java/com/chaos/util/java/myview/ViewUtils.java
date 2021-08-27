package com.chaos.util.java.myview;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created on 2021/8/27
 *
 * @author zsp
 * @desc 视图工具类
 */
public class ViewUtils {
    /**
     * 触摸指定 View 否（过滤控件）
     *
     * @param views       视图
     * @param motionEvent 手势事件
     * @return boolean
     */
    public static boolean isTouchView(View[] views, MotionEvent motionEvent) {
        if ((null == views) || (views.length == 0)) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            boolean flag = ((motionEvent.getX() > x) && (motionEvent.getX() < (x + view.getWidth())) && (motionEvent.getY() > y && motionEvent.getY() < (y + view.getHeight())));
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 触摸指定 View 否（过滤控件）
     *
     * @param appCompatActivity 活动
     * @param ids               控件数组
     * @param motionEvent       手势事件
     * @return boolean
     */
    public static boolean isTouchView(AppCompatActivity appCompatActivity, @NonNull int[] ids, MotionEvent motionEvent) {
        WeakReference<AppCompatActivity> weakReference = new WeakReference<>(appCompatActivity);
        int[] location = new int[2];
        for (int id : ids) {
            View view = weakReference.get().findViewById(id);
            if (null == view) {
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            boolean flag = ((motionEvent.getX() > x) && (motionEvent.getX() < (x + view.getWidth())) && (motionEvent.getY() > y && motionEvent.getY() < (y + view.getHeight())));
            if (flag) {
                return true;
            }
        }
        return false;
    }
}
