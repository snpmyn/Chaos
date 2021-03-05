package com.chaos.fairy.utils.window;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.chaos.fairy.R;
import com.chaos.fairy.utils.system.HuaWeiUtils;
import com.chaos.fairy.utils.system.MeiZuUtils;
import com.chaos.fairy.utils.system.QiHoo360Utils;
import com.chaos.fairy.utils.system.RomUtils;
import com.chaos.fairy.utils.system.XiaoMiUtils;
import com.chaos.fairy.widget.dialog.message.RoundCornerMessageDialog;

/**
 * Created on 2020-09-10
 *
 * @author zsp
 * @desc 系统悬浮窗工具类
 */
public class SystemAlertWindowUtils {
    private static volatile SystemAlertWindowUtils instance;

    public static SystemAlertWindowUtils getInstance() {
        if (null == instance) {
            synchronized (SystemAlertWindowUtils.class) {
                if (null == instance) {
                    instance = new SystemAlertWindowUtils();
                }
            }
        }
        return instance;
    }

    public boolean execute(Context context) {
        if (checkSystemAlertWindowPermission(context)) {
            return true;
        }
        dialogHint(context);
        return false;
    }

    private boolean checkSystemAlertWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 6.0+ 谷歌增加管理悬浮窗权限，方式统一。
            return Settings.canDrawOverlays(context);
        } else if (RomUtils.xiaoMiRom()) {
            return XiaoMiUtils.checkSystemAlertWindowPermission(context);
        } else if (RomUtils.meiZuRom()) {
            return MeiZuUtils.checkSystemAlertWindowPermission(context);
        } else if (RomUtils.huaWeiRom()) {
            return HuaWeiUtils.checkSystemAlertWindowPermission(context);
        } else if (RomUtils.qiHoo360Rom()) {
            return QiHoo360Utils.checkSystemAlertWindowPermission(context);
        } else {
            return true;
        }
    }

    private void dialogHint(final Context context) {
        RoundCornerMessageDialog roundCornerMessageDialog = new RoundCornerMessageDialog.Builder(context, 0)
                .setContent(context.getString(R.string.openSuspensionWindowPermissionHint))
                .setContentHorizontalCenter()
                .setLeftButtonText(context.getString(R.string.waitNotToOpen))
                .setRightButtonText(context.getString(R.string.nowToOpen))
                .setOnRoundCornerMessageDialogLeftButtonClickListener((view, roundCornerMessageDialog1) -> {
                    roundCornerMessageDialog1.handle(roundCornerMessageDialog1.getClass());
                    handle(context, false);
                })
                .setOnRoundCornerMessageDialogRightButtonClickListener((view, roundCornerMessageDialog12) -> {
                    roundCornerMessageDialog12.handle(roundCornerMessageDialog12.getClass());
                    handle(context, true);
                }).build();
        roundCornerMessageDialog.setCancelable(false);
        roundCornerMessageDialog.show();
    }

    private void handle(Context context, boolean bool) {
        if (!bool) {
            // do something
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } else if (RomUtils.xiaoMiRom()) {
            XiaoMiUtils.requestPermission(context);
        } else if (RomUtils.meiZuRom()) {
            MeiZuUtils.requestPermission(context);
        } else if (RomUtils.huaWeiRom()) {
            HuaWeiUtils.requestPermission(context);
        } else if (RomUtils.qiHoo360Rom()) {
            QiHoo360Utils.requestPermission(context);
        }
    }
}
