package com.chaos.fairy.utils.system;

import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;

import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.value.FairyMagic;
import com.chaos.fairy.widget.toast.ToastKit;

import java.lang.reflect.Method;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc 华为工具类
 */
public class HuaWeiUtils {
    /**
     * 检测系统悬浮窗权限
     *
     * @param context 上下文
     * @return 是否被授予系统悬浮窗权限
     */
    public static boolean checkSystemAlertWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= FairyMagic.INT_NINETEEN) {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = AppOpsManager.class.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(appOpsManager, 24, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                LogUtils.exception(e);
            }
            return false;
        } else {
            ToastKit.showShort("Below API 19 cannot invoke!");
        }
        return true;
    }

    /**
     * 请求权限
     *
     * @param context 上下文
     */
    public static void requestPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
            intent.setComponent(componentName);
            if (RomUtils.getEmuiVersionCode() != FairyMagic.FLOAT_THREE_DOT_ONE) {
                // 适配 ENUI 3.0
                componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
                intent.setComponent(componentName);
            }
            context.startActivity(intent);
        } catch (SecurityException e) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 跳转到本应用权限管理页，需华为接口权限，未解决。
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // 手机管家版本较低 HUAWEI SC-UL 10
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 权限管理页 android 4.4
            ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");
            intent.setComponent(comp);
            context.startActivity(intent);
            e.printStackTrace();
        } catch (Exception e) {
            ToastKit.showShort("进入设置页面失败，请手动设置");
        }
    }
}
