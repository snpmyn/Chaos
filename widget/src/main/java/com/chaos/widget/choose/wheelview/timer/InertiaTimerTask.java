package com.chaos.widget.choose.wheelview.timer;

import com.chaos.widget.choose.wheelview.view.WheelView;

import java.util.TimerTask;

import value.WidgetMagic;

/**
 * Created on 2018/4/3.
 *
 * @author 郑少鹏
 * @desc InertiaTimerTask
 */
public final class InertiaTimerTask extends TimerTask {
    /**
     * 手指离屏时初始速
     */
    private final float mFirstVelocityY;
    private final WheelView mWheelView;
    /**
     * 当前滑速
     */
    private float yCurrentVelocity;

    /**
     * InertiaTimerTask
     *
     * @param wheelView 滚轮对象
     * @param yVelocity Y 轴滑速
     */
    public InertiaTimerTask(WheelView wheelView, float yVelocity) {
        super();
        this.mWheelView = wheelView;
        this.mFirstVelocityY = yVelocity;
        yCurrentVelocity = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {
        // 防闪动（对速度限制）
        if (yCurrentVelocity == Integer.MAX_VALUE) {
            if (Math.abs(mFirstVelocityY) > WidgetMagic.FLOAT_TWO_THOUSAND_DOT_ZERO) {
                yCurrentVelocity = ((mFirstVelocityY > 0) ? 2000.0F : -2000.0F);
            } else {
                yCurrentVelocity = mFirstVelocityY;
            }
        }
        // 发 Handler 消息
        // 处理平顺停止滚动逻辑
        if ((Math.abs(yCurrentVelocity) >= WidgetMagic.FLOAT_ZERO_DOT_ZERO) && (Math.abs(yCurrentVelocity) <= WidgetMagic.FLOAT_TWENTY_DOT_ZERO)) {
            mWheelView.cancelFuture();
            mWheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }
        int dy = (int) (yCurrentVelocity / 100.0F);
        mWheelView.setyTotalScroll(mWheelView.getyTotalScroll() - dy);
        if (mWheelView.areLoop()) {
            float itemHeight = mWheelView.getItemHeight();
            float top = (-mWheelView.getInitPosition()) * itemHeight;
            float bottom = ((mWheelView.getItemsCount() - 1 - mWheelView.getInitPosition()) * itemHeight);
            if ((mWheelView.getyTotalScroll() - itemHeight * WidgetMagic.FLOAT_ZERO_DOT_TWO_FIVE) < top) {
                top = (mWheelView.getyTotalScroll() + dy);
            } else if ((mWheelView.getyTotalScroll() + itemHeight * WidgetMagic.FLOAT_ZERO_DOT_TWO_FIVE) > bottom) {
                bottom = (mWheelView.getyTotalScroll() + dy);
            }
            if (mWheelView.getyTotalScroll() <= top) {
                yCurrentVelocity = 40.0F;
                mWheelView.setyTotalScroll((int) top);
            } else if (mWheelView.getyTotalScroll() >= bottom) {
                mWheelView.setyTotalScroll((int) bottom);
                yCurrentVelocity = -40.0F;
            }
        }
        if (yCurrentVelocity < WidgetMagic.FLOAT_ZERO_DOT_ZERO) {
            yCurrentVelocity = (yCurrentVelocity + 20.0F);
        } else {
            yCurrentVelocity = (yCurrentVelocity - 20.0F);
        }
        // 刷 UI
        mWheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }
}

