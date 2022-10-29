package com.chaos.widget.dialog.sweetalertdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.core.content.ContextCompat;

import com.chaos.widget.R;

import value.WidgetMagic;

/**
 * Created on 2017/11/3.
 *
 * @author xxx
 * @desc SuccessTickView
 */
public class SuccessTickView extends View {
    private float mDensity = -1;
    private final float CONST_RADIUS = dip2px(1.2F);
    private final float CONST_RECT_WEIGHT = dip2px(3);
    private final float CONST_LEFT_RECT_W = dip2px(15);
    private final float CONST_RIGHT_RECT_W = dip2px(25);
    private final float MAX_RIGHT_RECT_W = CONST_RIGHT_RECT_W + dip2px(6.7F);
    private final float MIN_LEFT_RECT_W = dip2px(3.3F);
    private Paint mPaint;
    private float mMaxLeftRectWidth;
    private float mLeftRectWidth;
    private float mRightRectWidth;
    private boolean mLeftRectGrowMode;

    public SuccessTickView(Context context) {
        super(context);
        init();
    }

    public SuccessTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.widget_sweet_alert_dialog_success_sign));
        mLeftRectWidth = CONST_LEFT_RECT_W;
        mRightRectWidth = CONST_RIGHT_RECT_W;
        mLeftRectGrowMode = false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int totalW = getWidth();
        int totalH = getHeight();
        // rotate canvas first
        canvas.rotate(45.0F, Integer.valueOf(totalW / 2).floatValue(), Integer.valueOf(totalH / 2).floatValue());
        totalW /= 1.2;
        totalH /= 1.4;
        mMaxLeftRectWidth = ((totalW + CONST_LEFT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1);
        RectF leftRect = new RectF();
        if (mLeftRectGrowMode) {
            leftRect.left = 0;
            leftRect.right = leftRect.left + mLeftRectWidth;
        } else {
            leftRect.right = ((totalW + CONST_LEFT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1);
            leftRect.left = leftRect.right - mLeftRectWidth;
        }
        leftRect.top = (totalH + CONST_RIGHT_RECT_W) / 2;
        leftRect.bottom = leftRect.top + CONST_RECT_WEIGHT;
        canvas.drawRoundRect(leftRect, CONST_RADIUS, CONST_RADIUS, mPaint);
        RectF rightRect = new RectF();
        rightRect.bottom = ((totalH + CONST_RIGHT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1);
        rightRect.left = (totalW + CONST_LEFT_RECT_W) / 2;
        rightRect.right = (rightRect.left + CONST_RECT_WEIGHT);
        rightRect.top = (rightRect.bottom - mRightRectWidth);
        canvas.drawRoundRect(rightRect, CONST_RADIUS, CONST_RADIUS, mPaint);
    }

    public float dip2px(float dpValue) {
        if (mDensity == -1) {
            mDensity = getResources().getDisplayMetrics().density;
        }
        return dpValue * mDensity + 0.5F;
    }

    public void startTickAnim() {
        // hide tick
        mLeftRectWidth = 0;
        mRightRectWidth = 0;
        invalidate();
        Animation tickAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if (WidgetMagic.FLOAT_ZERO_DOT_FIVE_FOUR < interpolatedTime && WidgetMagic.FLOAT_ZERO_DOT_SEVEN >= interpolatedTime) {
                    // grow left and right rect to right
                    mLeftRectGrowMode = true;
                    mLeftRectWidth = mMaxLeftRectWidth * ((interpolatedTime - 0.54F) / 0.16F);
                    if (WidgetMagic.FLOAT_ZERO_DOT_SIX_FIVE < interpolatedTime) {
                        mRightRectWidth = MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65F) / 0.19F);
                    }
                    invalidate();
                } else if (WidgetMagic.FLOAT_ZERO_DOT_SEVEN < interpolatedTime && WidgetMagic.FLOAT_ZERO_DOT_EIGHT_FOUR >= interpolatedTime) {
                    // shorten left rect from right
                    // still grow right rect
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = mMaxLeftRectWidth * (1 - ((interpolatedTime - 0.7F) / 0.14F));
                    mLeftRectWidth = Math.max(mLeftRectWidth, MIN_LEFT_RECT_W);
                    mRightRectWidth = MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65F) / 0.19F);
                    invalidate();
                } else if (WidgetMagic.FLOAT_ZERO_DOT_EIGHT_FOUR < interpolatedTime && 1 >= interpolatedTime) {
                    // restore left rect width
                    // shorten right rect to const
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = (MIN_LEFT_RECT_W + (CONST_LEFT_RECT_W - MIN_LEFT_RECT_W) * ((interpolatedTime - 0.84F) / 0.16F));
                    mRightRectWidth = (CONST_RIGHT_RECT_W + (MAX_RIGHT_RECT_W - CONST_RIGHT_RECT_W) * (1 - ((interpolatedTime - 0.84F) / 0.16F)));
                    invalidate();
                }
            }
        };
        tickAnim.setDuration(750);
        tickAnim.setStartOffset(100);
        startAnimation(tickAnim);
    }
}
