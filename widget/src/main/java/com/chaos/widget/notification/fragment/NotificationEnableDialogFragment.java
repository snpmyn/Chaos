package com.chaos.widget.notification.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.chaos.widget.R;
import com.chaos.widget.notification.listener.NotificationEnableDialogOnClickListener;
import com.google.android.material.button.MaterialButton;

/**
 * Created on 2019/8/8.
 *
 * @author 郑少鹏
 * @desc 通知允对话框碎片
 */
public class NotificationEnableDialogFragment extends DialogFragment {
    public final String TAG = this.getClass().getName();
    /**
     * 视图
     */
    private View view;
    /**
     * 通知允对话框点监听
     */
    private NotificationEnableDialogOnClickListener notificationEnableDialogOnClickListener;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>A default View can be returned by calling {@link androidx.fragment.app.Fragment#Fragment(int)} in your
     * constructor. Otherwise, this method returns null.
     *
     * <p>It is recommended to <strong>only</strong> inflate the layout in this method and move
     * logic that operates on the returned View to {@link #onViewCreated(View, Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_enable_dialog, container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 对话框
        Dialog dialog = getDialog();
        if (null == dialog) {
            return;
        }
        // 窗体
        Window window = dialog.getWindow();
        if (null == window) {
            return;
        }
        // 背景
        if (null == getContext()) {
            return;
        }
        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.white_solid_r10));
        // 控件
        MaterialButton fragmentNotificationEnableDialogMbTalkAboutItLater = view.findViewById(R.id.fragmentNotificationEnableDialogMbTalkAboutItLater);
        fragmentNotificationEnableDialogMbTalkAboutItLater.setOnClickListener(v -> {
            dismiss();
            notificationEnableDialogOnClickListener.talkAboutItLater();
        });
        MaterialButton fragmentNotificationEnableDialogMbOk = view.findViewById(R.id.fragmentNotificationEnableDialogMbOk);
        fragmentNotificationEnableDialogMbOk.setOnClickListener(v -> {
            dismiss();
            notificationEnableDialogOnClickListener.ok();
        });
        // 取消
        setCancelable(false);
    }

    public void setNotificationEnableDialogOnClickListener(NotificationEnableDialogOnClickListener notificationEnableDialogOnClickListener) {
        this.notificationEnableDialogOnClickListener = notificationEnableDialogOnClickListener;
    }

    /**
     * Remove dialog.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        notificationEnableDialogOnClickListener = null;
    }
}
