package com.chaos.util.java.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

import static android.content.Context.ACTIVITY_SERVICE;

import com.chaos.util.java.datetime.CurrentTimeMillisClock;
import com.chaos.util.java.toast.ToastKit;

/**
 * Created on 2017/9/19.
 *
 * @author 郑少鹏
 * @desc ActivitySuperviseManager
 * Application：
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)} 之 onActivityCreated 当 {@link AppCompatActivity#onCreate(Bundle, PersistableBundle)} 时执行，android:launchMode="singleTask" 时不执行。
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)} 之 onActivityDestroyed 当 {@link AppCompatActivity#finish()(Bundle, PersistableBundle)} 时执行，android:launchMode="singleTask" 时不执行。
 * 基类：
 * 基类之 {@link AppCompatActivity#onCreate(Bundle, PersistableBundle)} 推当前 Activity 至 Activity 管理容器，需时遍历容器并 finish 所有 Activity。
 */
public class ActivitySuperviseManager {
    private static ActivitySuperviseManager instance;
    private final List<Activity> ACTIVITIES = Collections.synchronizedList(new LinkedList<>());
    private static final long WAIT_TIME = 2000L;
    private long touchDownTime = 0L;

    public static ActivitySuperviseManager getInstance() {
        if (null == instance) {
            synchronized (ActivitySuperviseManager.class) {
                if (null == instance) {
                    instance = new ActivitySuperviseManager();
                }
            }
        }
        return instance;
    }

    /**
     * 推 Activity 至堆栈
     *
     * @param activity Activity
     */
    public void pushActivity(Activity activity) {
        ACTIVITIES.add(activity);
        Timber.d("推入：%s", activity.getClass().getSimpleName());
        Timber.d("活动数：%s", ACTIVITIES.size());
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            Timber.d("概览：%s", ACTIVITIES.get(i).getClass().getSimpleName());
        }
    }

    /**
     * 从堆栈去 Activity
     *
     * @param activity Activity
     */
    public void removeActivity(Activity activity) {
        ACTIVITIES.remove(activity);
        Timber.d("去除：%s", activity.getClass().getSimpleName());
        Timber.d("活动数：%s", ACTIVITIES.size());
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            Timber.d("概览：%s", ACTIVITIES.get(i).getClass().getSimpleName());
        }
    }

    /**
     * 当前 Activity 名
     * <p>
     * info.topActivity.getShortClassName() Activity 名
     * info.topActivity.getClassName() 类名
     * info.topActivity.getPackageName() 包名
     * info.topActivity.getClass() 类实例
     *
     * @param context 上下文
     * @return 当前 Activity 名
     */
    public String getCurrentRunningActivityName(@NotNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo runningTaskInfo = ((null != activityManager) ? activityManager.getRunningTasks(1).get(0) : null);
        String currentRunningActivityName;
        if ((null != runningTaskInfo) && (null != runningTaskInfo.topActivity)) {
            currentRunningActivityName = runningTaskInfo.topActivity.getShortClassName();
        } else {
            currentRunningActivityName = null;
        }
        Timber.d("当前活动名：%s", currentRunningActivityName);
        return currentRunningActivityName;
    }

    /**
     * 栈顶 Activity 实例
     *
     * @return 栈顶 Activity 实例
     */
    public @Nullable Activity getTopActivityInstance() {
        Activity topActivityInstance;
        synchronized (ACTIVITIES) {
            final int size = (ACTIVITIES.size() - 1);
            if (size < 0) {
                return null;
            }
            topActivityInstance = ACTIVITIES.get(size);
        }
        return topActivityInstance;
    }

    /**
     * 结束指定 Activity
     *
     * @param activity Activity
     */
    private void finishActivity(Activity activity) {
        if (ACTIVITIES.isEmpty()) {
            return;
        }
        if (null != activity) {
            Timber.d("结束：%s", activity.getClass().getSimpleName());
            ACTIVITIES.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名 Activity
     *
     * @param cls Class<?>
     */
    public void finishActivity(Class<?> cls) {
        if (ACTIVITIES.isEmpty()) {
            return;
        }
        for (Activity activity : ACTIVITIES) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    private void finishAllActivity() {
        for (Activity activity : ACTIVITIES) {
            activity.finish();
        }
        ACTIVITIES.clear();
    }

    /**
     * 双击退出
     *
     * @param exitHint 退出提示
     */
    public void twoClickToExit(String exitHint) {
        if ((CurrentTimeMillisClock.getInstance().now() - touchDownTime) < WAIT_TIME) {
            appExit();
        } else {
            touchDownTime = CurrentTimeMillisClock.getInstance().now();
            ToastKit.showShort(exitHint);
        }
    }

    /**
     * 退应用
     */
    public void appExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
