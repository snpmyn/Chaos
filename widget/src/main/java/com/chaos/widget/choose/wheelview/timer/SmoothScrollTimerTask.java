package com.chaos.widget.choose.wheelview.timer;

import com.chaos.widget.choose.wheelview.view.WheelView;

import java.util.TimerTask;

/**
 * Created on 2018/4/3.
 *
 * @author 郑少鹏
 * @desc SmoothScrollTimerTask
 */
public final class SmoothScrollTimerTask extends TimerTask {
    private final WheelView wheelView;
    private final int offset;
    private int realTotalOffset;
    private int realOffset;

    public SmoothScrollTimerTask(WheelView wheelView, int offset) {
        this.wheelView = wheelView;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        // 将要滚动范围细分 10 小份
        // 按 10 小份单位重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);
        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }
        if (Math.abs(realTotalOffset) <= 1) {
            wheelView.cancelFuture();
            wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            wheelView.setyTotalScroll(wheelView.getyTotalScroll() + realOffset);
            // 这里非循环模式则点空白位需回滚
            // 否出选到 －1 Item 情况
            if (wheelView.areLoop()) {
                float itemHeight = wheelView.getItemHeight();
                float top = (float) (-wheelView.getInitPosition()) * itemHeight;
                float bottom = ((float) (wheelView.getItemsCount() - 1 - wheelView.getInitPosition()) * itemHeight);
                if ((wheelView.getyTotalScroll() <= top) || (wheelView.getyTotalScroll() >= bottom)) {
                    wheelView.setyTotalScroll(wheelView.getyTotalScroll() - realOffset);
                    wheelView.cancelFuture();
                    wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = (realTotalOffset - realOffset);
        }
    }
}
