package com.chaos.fairy.utils.system;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;

import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.value.FairyMagic;
import com.chaos.fairy.widget.toast.ToastKit;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Created on 2020-09-10
 *
 * @author zsp
 * @desc 小米工具类
 */
public class XiaoMiUtils {
    private static final String TAG = "XiaoMiUtils";

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
        int versionCode = RomUtils.getMiuiVersion();
        if (versionCode == FairyMagic.INT_FIVE) {
            goToMiuiPermissionActivityOnV5(context);
        } else if (versionCode == FairyMagic.INT_SIX) {
            goToMiuiPermissionActivityOnV6(context);
        } else if (versionCode == FairyMagic.INT_SEVEN) {
            goToMiuiPermissionActivityOnV7(context);
        } else if (versionCode == FairyMagic.INT_EIGHT) {
            goToMiuiPermissionActivityOnV8(context);
        } else {
            LogUtils.d(TAG, "this is a special MIUI rom version, its version code " + versionCode);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private static boolean isIntentAvailable(Intent intent, Context context) {
        if (intent == null) {
            return false;
        }
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    /**
     * 跳转 V5 版 MIUI 权限页
     *
     * @param context 上下文
     */
    private static void goToMiuiPermissionActivityOnV5(@NotNull Context context) {
        Intent intent;
        String packageName = context.getPackageName();
        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            LogUtils.d(TAG, "intent is not available!");
        }
    }

    /**
     * 跳转 V6 版 MIUI 权限页
     *
     * @param context 上下文
     */
    private static void goToMiuiPermissionActivityOnV6(@NotNull Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            LogUtils.d(TAG, "Intent is not available!");
        }
    }

    /**
     * 跳转 V7 版 MIUI 权限页
     *
     * @param context 上下文
     */
    private static void goToMiuiPermissionActivityOnV7(@NotNull Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            LogUtils.d(TAG, "Intent is not available!");
        }
    }

    /**
     * 跳转 V8 版 MIUI 权限页
     *
     * @param context 上下文
     */
    private static void goToMiuiPermissionActivityOnV8(@NotNull Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setPackage("com.miui.securitycenter");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                LogUtils.d(TAG, "Intent is not available!");
            }
        }
    }
}
