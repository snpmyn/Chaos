package com.chaos.widget.choose.wheelview.timer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.chaos.widget.choose.wheelview.view.WheelView;

/**
 * Created on 2018/4/3.
 *
 * @author 郑少鹏
 * @desc MessageHandler
 */
public final class MessageHandler extends Handler {
    static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    static final int WHAT_SMOOTH_SCROLL = 2000;
    static final int WHAT_ITEM_SELECTED = 3000;
    private final WheelView wheelView;

    /**
     * Use the provided {@link Looper} instead of the default one.
     *
     * @param looper    The looper, must not be null.
     * @param wheelView WheelView
     */
    public MessageHandler(@NonNull Looper looper, WheelView wheelView) {
        super(looper);
        this.wheelView = wheelView;
    }

    @Override
    public final void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                wheelView.invalidate();
                break;
            case WHAT_SMOOTH_SCROLL:
                wheelView.smoothScroll(WheelView.ACTION.FLING);
                break;
            case WHAT_ITEM_SELECTED:
                wheelView.onItemSelected();
                break;
            default:
                break;
        }
    }
}

