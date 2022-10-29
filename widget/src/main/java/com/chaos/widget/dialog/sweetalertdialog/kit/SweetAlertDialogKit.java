package com.chaos.widget.dialog.sweetalertdialog.kit;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.widget.R;
import com.chaos.widget.dialog.sweetalertdialog.DialogValue;
import com.chaos.widget.dialog.sweetalertdialog.SweetAlertDialog;

import value.WidgetMagic;

/**
 * Created on 2022/8/15
 *
 * @author zsp
 * @desc SweetAlertDialog 配套原件
 */
public class SweetAlertDialogKit implements DialogValue {
    private SweetAlertDialog sweetAlertDialog;

    public static SweetAlertDialogKit getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 初始弹框
     *
     * @param appCompatActivity 活动
     * @param titleText         标题文本
     * @param contentText       内容文本
     * @param confirmText       确认文本
     * @param cancelText        取消文本
     * @param customImageResId  自定图资源 ID
     */
    public void stepDialog(AppCompatActivity appCompatActivity, String titleText, String contentText, String confirmText, String cancelText, int customImageResId) {
        sweetAlertDialog = new SweetAlertDialog(appCompatActivity, 4).setTitleText(titleText).setContentText(contentText).showCancelButton(true).setCustomImage(customImageResId).setConfirmText(confirmText).setCancelText(cancelText).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                int alertType = sweetAlertDialog.getAlertType();
                if ((alertType == WidgetMagic.INT_TWO) || (alertType == WidgetMagic.INT_THREE)) {
                    sweetAlertDialog.dismiss();
                } else {
                    sweetAlertDialog.showContentText(false);
                    sweetAlertDialog.showCancelButton(false);
                    dialogChange(appCompatActivity, 5, titleText);
                }
            }
        }).setCancelClickListener(Dialog::dismiss);
        sweetAlertDialog.setListener(this);
        sweetAlertDialog.show();
    }

    /**
     * 弹框变化
     *
     * @param context 上下文
     * @param type    类型
     * @param hint    提示
     */
    private void dialogChange(Context context, int type, String hint) {
        if (null == sweetAlertDialog) {
            return;
        }
        switch (type) {
            case 1:
                sweetAlertDialog.setTitleText(hint).setConfirmText(context.getString(R.string.ok)).changeAlertType(1);
                break;
            case 2:
                sweetAlertDialog.setTitleText(hint).setConfirmText(context.getString(R.string.ok)).changeAlertType(2);
                break;
            case 3:
                sweetAlertDialog.setTitleText(hint).setConfirmText(context.getString(R.string.ok)).changeAlertType(3);
                break;
            case 5:
                sweetAlertDialog.setTitleText(hint).changeAlertType(5);
            default:
                break;
        }
    }

    /**
     * Dismiss widget.dialog.
     *
     * @param flag flag
     */
    @Override
    public void dialogDismiss(int flag) {
        if (flag == 1) {
            sweetAlertDialog.dismiss();
            sweetAlertDialog = null;
        }
    }

    private static final class InstanceHolder {
        static final SweetAlertDialogKit INSTANCE = new SweetAlertDialogKit();
    }
}
