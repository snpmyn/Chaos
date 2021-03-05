package com.chaos.fairy.widget.toast;

import android.widget.Toast;

import com.chaos.basic.BasicApp;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc ToastKit
 */
public class ToastKit {
    private static Toast toast;

    public static void showShort(CharSequence msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void showShortWithGravity(CharSequence msg, int gravity) {
        show(msg, Toast.LENGTH_SHORT, gravity);
    }

    public static void showLong(CharSequence msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void showLongWithGravity(CharSequence msg, int gravity) {
        show(msg, Toast.LENGTH_LONG, gravity);
    }

    private static void show(CharSequence msg, int time) {
        if (null != toast) {
            toast.cancel();
        }
        toast = CommonToast.makeText(BasicApp.getInstance(), msg, time);
        toast.show();
    }

    private static void show(CharSequence msg, int time, int gravity) {
        if (null != toast) {
            toast.cancel();
        }
        toast = CommonToast.makeText(BasicApp.getInstance(), msg, time);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }
}
