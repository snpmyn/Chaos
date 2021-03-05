package com.chaos.fairy.utils.system;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;

import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.widget.toast.ToastKit;

import java.lang.reflect.Method;

import static com.chaos.fairy.value.FairyMagic.INT_NINETEEN;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc 奇虎 360 工具类
 */
public class QiHoo360Utils {
    /**
     * 检测系统悬浮窗权限
     *
     * @param context 上下文
     * @return 是否被授予系统悬浮窗权限
     */
    public static boolean checkSystemAlertWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= INT_NINETEEN) {
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
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (areIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            if (areIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                ToastKit.showShort("can't open permission page with particular name, please use " +
                        "\"adb shell dumpsys activity\" command and tell me the name of the float window permission page");
            }
        }
    }

    /**
     * 意图可用否
     *
     * @param intent  意图
     * @param context 上下文
     * @return 可用否
     */
    private static boolean areIntentAvailable(Intent intent, Context context) {
        if (null == intent) {
            return false;
        }
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
