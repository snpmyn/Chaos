package com.chaos.fairy.utils.system;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;

import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.value.FairyMagic;
import com.chaos.fairy.widget.toast.ToastKit;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc 魅族工具类
 */
public class MeiZuUtils {
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
    public static void requestPermission(@NotNull Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
