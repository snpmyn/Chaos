package com.chaos.widget.dialog.sweetalertdialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;
import com.chaos.widget.progressbar.ProgressWheel;

/**
 * Created on 2017/11/3.
 *
 * @author xxx
 * @desc ProgressHelper
 */
public class ProgressHelper {
    private ProgressWheel mProgressWheel;
    private boolean mToSpin;
    private float mSpinSpeed;
    private int mBarWidth;
    private int mBarColor;
    private int mRimWidth;
    private int mRimColor;
    private boolean mIsInstantProgress;
    private float mProgressVal;
    private int mCircleRadius;

    ProgressHelper(@NonNull Context ctx) {
        mToSpin = true;
        mSpinSpeed = 0.75F;
        mBarWidth = ctx.getResources().getDimensionPixelSize(R.dimen.dp_3) + 1;
        mBarColor = ContextCompat.getColor(ctx, R.color.widget_sweet_alert_dialog_success_sign);
        mRimWidth = 0;
        mRimColor = 0x00000000;
        mIsInstantProgress = false;
        mProgressVal = -1;
        mCircleRadius = ctx.getResources().getDimensionPixelOffset(R.dimen.dp_34);
    }

    public ProgressWheel getProgressWheel() {
        return mProgressWheel;
    }

    public void setProgressWheel(ProgressWheel progressWheel) {
        mProgressWheel = progressWheel;
        updatePropsIfNeed();
    }

    private void updatePropsIfNeed() {
        if (null != mProgressWheel) {
            if (!mToSpin && mProgressWheel.isAreSpinning()) {
                mProgressWheel.stopSpinning();
            } else if (mToSpin && !mProgressWheel.isAreSpinning()) {
                mProgressWheel.spin();
            }
            if (mSpinSpeed != mProgressWheel.getSpinSpeed()) {
                mProgressWheel.setSpinSpeed(mSpinSpeed);
            }
            if (mBarWidth != mProgressWheel.getBarWidth()) {
                mProgressWheel.setBarWidth(mBarWidth);
            }
            if (mBarColor != mProgressWheel.getBarColor()) {
                mProgressWheel.setBarColor(mBarColor);
            }
            if (mRimWidth != mProgressWheel.getRimWidth()) {
                mProgressWheel.setRimWidth(mRimWidth);
            }
            if (mRimColor != mProgressWheel.getRimColor()) {
                mProgressWheel.setRimColor(mRimColor);
            }
            if (mProgressVal != mProgressWheel.getProgress()) {
                if (mIsInstantProgress) {
                    mProgressWheel.setInstantProgress(mProgressVal);
                } else {
                    mProgressWheel.setProgress(mProgressVal);
                }
            }
            if (mCircleRadius != mProgressWheel.getCircleRadius()) {
                mProgressWheel.setCircleRadius(mCircleRadius);
            }
        }
    }

    public void resetCount() {
        if (null != mProgressWheel) {
            mProgressWheel.resetCount();
        }
    }

    public boolean isSpinning() {
        return mToSpin;
    }

    public void spin() {
        mToSpin = true;
        updatePropsIfNeed();
    }

    public void stopSpinning() {
        mToSpin = false;
        updatePropsIfNeed();
    }

    public float getProgress() {
        return mProgressVal;
    }

    public void setProgress(float progress) {
        mIsInstantProgress = false;
        mProgressVal = progress;
        updatePropsIfNeed();
    }

    public void setInstantProgress(float progress) {
        mProgressVal = progress;
        mIsInstantProgress = true;
        updatePropsIfNeed();
    }

    public int getCircleRadius() {
        return mCircleRadius;
    }

    /**
     * 设圆半径
     *
     * @param circleRadius units using pixel
     */
    public void setCircleRadius(int circleRadius) {
        mCircleRadius = circleRadius;
        updatePropsIfNeed();
    }

    public int getBarWidth() {
        return mBarWidth;
    }

    public void setBarWidth(int barWidth) {
        mBarWidth = barWidth;
        updatePropsIfNeed();
    }

    public int getBarColor() {
        return mBarColor;
    }

    public void setBarColor(int barColor) {
        mBarColor = barColor;
        updatePropsIfNeed();
    }

    public int getRimWidth() {
        return mRimWidth;
    }

    public void setRimWidth(int rimWidth) {
        mRimWidth = rimWidth;
        updatePropsIfNeed();
    }

    public int getRimColor() {
        return mRimColor;
    }

    public void setRimColor(int rimColor) {
        mRimColor = rimColor;
        updatePropsIfNeed();
    }

    public float getSpinSpeed() {
        return mSpinSpeed;
    }

    public void setSpinSpeed(float spinSpeed) {
        mSpinSpeed = spinSpeed;
        updatePropsIfNeed();
    }
}
