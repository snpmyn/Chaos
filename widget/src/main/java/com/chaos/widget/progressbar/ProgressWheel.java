package com.chaos.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.data.FloatUtils;
import com.chaos.widget.R;

import org.jetbrains.annotations.Contract;

import value.WidgetMagic;

/**
 * @decs: ProgressWheel
 * @author: 郑少鹏
 * @date: 2019/8/12 15:53
 */
public class ProgressWheel extends View {
    private final int barLength = 16;
    /**
     * paints
     */
    private final Paint barPaint = new Paint();
    private final Paint rimPaint = new Paint();
    /**
     * sizes (with defaults in dp)
     */
    private int circleRadius = 28;
    private int barWidth = 4;
    private int rimWidth = 4;
    private boolean fillRadius = false;
    private double timeStartGrowing = 0;
    private double barSpinCycleTime = 460;
    private float barExtraLength = 0;
    private boolean barGrowingFromFront = true;
    private long pausedTimeWithoutGrowing = 0;
    /**
     * colors (with defaults)
     */
    private int barColor = 0xAA000000;
    private int rimColor = 0x00FFFFFF;
    /**
     * rectangles
     */
    private RectF circleBounds = new RectF();
    /**
     * animation
     * <p>
     * The amount of degrees per second.
     * <p>
     * private float spinSpeed = 120.0F;
     */
    private float spinSpeed = 230.0F;
    /**
     * the last time the spinner was animated
     */
    private long lastTimeAnimated = 0;
    private boolean linearProgress;
    private float mProgress = 0.0F;
    private float mTargetProgress = 0.0F;
    private boolean areSpinning = false;
    private ProgressCallback progressCallback;
    private boolean shouldAnimate;

    /**
     * The constructor for the ProgressWheel.
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
        setAnimationEnabled();
    }

    /**
     * The constructor for the ProgressWheel.
     *
     * @param context Context
     */
    public ProgressWheel(Context context) {
        super(context);
        setAnimationEnabled();
    }

    private void setAnimationEnabled() {
        int currentApiVersion = Build.VERSION.SDK_INT;
        float animationValue;
        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            animationValue = Settings.Global.getFloat(getContext().getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1);
        } else {
            animationValue = Settings.System.getFloat(getContext().getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1);
        }
        shouldAnimate = (animationValue != 0);
    }

    /**
     * --------------------------
     * setting up stuff
     * --------------------------
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = (circleRadius + this.getPaddingLeft() + this.getPaddingRight());
        int viewHeight = (circleRadius + this.getPaddingTop() + this.getPaddingBottom());
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        // measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            // must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // can't be bigger than...
            width = Math.min(viewWidth, widthSize);
        } else {
            // be whatever you want
            width = viewWidth;
        }
        // measure Height
        if ((heightMode == MeasureSpec.EXACTLY) || (widthMode == MeasureSpec.EXACTLY)) {
            // must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // can't be bigger than...
            height = Math.min(viewHeight, heightSize);
        } else {
            // be whatever you want
            height = viewHeight;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view, because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        setupBounds(width, height);
        setupPaints();
        invalidate();
    }

    /**
     * Set the properties of the paints we're using to draw the progress wheel.
     */
    private void setupPaints() {
        barPaint.setColor(barColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Style.STROKE);
        barPaint.setStrokeWidth(barWidth);
        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Style.STROKE);
        rimPaint.setStrokeWidth(rimWidth);
    }

    /**
     * Set the bounds of the component
     *
     * @param layoutWidth  布局宽
     * @param layoutHeight 布局高
     */
    private void setupBounds(int layoutWidth, int layoutHeight) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (!fillRadius) {
            // width should equal to height
            // find the min value to setup the circle
            int minValue = Math.min(layoutWidth - paddingLeft - paddingRight, layoutHeight - paddingBottom - paddingTop);
            int circleDiameter = Math.min(minValue, circleRadius * 2 - barWidth * 2);
            // calc the Offset if needed for centering the wheel in the available space
            int xOffset = ((layoutWidth - paddingLeft - paddingRight - circleDiameter) / 2 + paddingLeft);
            int yOffset = ((layoutHeight - paddingTop - paddingBottom - circleDiameter) / 2 + paddingTop);
            circleBounds = new RectF(xOffset + barWidth, yOffset + barWidth, xOffset + circleDiameter - barWidth, yOffset + circleDiameter - barWidth);
        } else {
            circleBounds = new RectF(paddingLeft + barWidth, paddingTop + barWidth, layoutWidth - paddingRight - barWidth, layoutHeight - paddingBottom - barWidth);
        }
    }

    /**
     * Parse the attributes passed to the view from the xml.
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(@NonNull TypedArray a) {
        // we transform the default values from dip to pixels
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        barWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth, metrics);
        rimWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rimWidth, metrics);
        circleRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, circleRadius, metrics);
        circleRadius = (int) a.getDimension(R.styleable.ProgressWheel_progressWheelCircleRadius, circleRadius);
        fillRadius = a.getBoolean(R.styleable.ProgressWheel_progressWheelFillRadius, false);
        barWidth = (int) a.getDimension(R.styleable.ProgressWheel_progressWheelBarWidth, barWidth);
        rimWidth = (int) a.getDimension(R.styleable.ProgressWheel_progressWheelRimWidth, rimWidth);
        float baseSpinSpeed = a.getFloat(R.styleable.ProgressWheel_progressWheelSpinSpeed, spinSpeed / 360.0F);
        spinSpeed = baseSpinSpeed * 360;
        barSpinCycleTime = a.getInt(R.styleable.ProgressWheel_progressWheelBarSpinCycleTime, (int) barSpinCycleTime);
        barColor = a.getColor(R.styleable.ProgressWheel_progressWheelBarColor, barColor);
        rimColor = a.getColor(R.styleable.ProgressWheel_progressWheelRimColor, rimColor);
        linearProgress = a.getBoolean(R.styleable.ProgressWheel_progressWheelLinearProgress, false);
        if (a.getBoolean(R.styleable.ProgressWheel_progressWheelProgressIndeterminate, false)) {
            spin();
        }
        // recycle
        a.recycle();
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
        if (!areSpinning) {
            runCallback();
        }
    }

    /**
     * --------------------------
     * animation stuff
     * --------------------------
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        boolean mustInvalidate = false;
        if (!shouldAnimate) {
            return;
        }
        if (areSpinning) {
            // draw the spinning bar
            mustInvalidate = true;
            long deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated);
            float deltaNormalized = deltaTime * spinSpeed / 1000.0F;
            updateBarLength(deltaTime);
            mProgress += deltaNormalized;
            if (mProgress > WidgetMagic.INT_THREE_HUNDRED_SIXTY) {
                mProgress -= 360.0F;
                // a full turn has been completed
                // we run the callback with -1 in case we want to
                // do something, like changing the color
                if (null != progressCallback) {
                    progressCallback.onProgressUpdate(-1.0F);
                }
            }
            lastTimeAnimated = SystemClock.uptimeMillis();
            float from = (mProgress - 90);
            float length = (barLength + barExtraLength);
            if (isInEditMode()) {
                from = 0;
                length = 135;
            }
            canvas.drawArc(circleBounds, from, length, false, barPaint);
        } else {
            float oldProgress = mProgress;
            if (!FloatUtils.equal(mProgress, mTargetProgress)) {
                // we smoothly increase the progress bar
                mustInvalidate = true;
                float deltaTime = (float) (SystemClock.uptimeMillis() - lastTimeAnimated) / 1000;
                float deltaNormalized = deltaTime * spinSpeed;
                mProgress = Math.min(mProgress + deltaNormalized, mTargetProgress);
                lastTimeAnimated = SystemClock.uptimeMillis();
            }
            if (!FloatUtils.equal(oldProgress, mProgress)) {
                runCallback();
            }
            float offset = 0.0F;
            float progress = mProgress;
            if (!linearProgress) {
                float factor = 2.0F;
                offset = (float) (1.0F - Math.pow(1.0F - mProgress / 360.0F, 2.0F * factor)) * 360.0F;
                progress = (float) (1.0F - Math.pow(1.0F - mProgress / 360.0F, factor)) * 360.0F;
            }
            if (isInEditMode()) {
                progress = 360;
            }
            canvas.drawArc(circleBounds, offset - 90, progress, false, barPaint);
        }
        if (mustInvalidate) {
            invalidate();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            lastTimeAnimated = SystemClock.uptimeMillis();
        }
    }

    private void updateBarLength(long deltaTimeInMilliSeconds) {
        long pauseGrowingTime = 200;
        if (pausedTimeWithoutGrowing >= pauseGrowingTime) {
            timeStartGrowing += deltaTimeInMilliSeconds;
            if (timeStartGrowing > barSpinCycleTime) {
                // we completed a size change cycle (growing or shrinking)
                timeStartGrowing -= barSpinCycleTime;
                //if(barGrowingFromFront) {
                pausedTimeWithoutGrowing = 0;
                //}
                barGrowingFromFront = !barGrowingFromFront;
            }
            float distance = ((float) Math.cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI) / 2 + 0.5F);
            int barMaxLength = 270;
            float destLength = (barMaxLength - barLength);
            if (barGrowingFromFront) {
                barExtraLength = distance * destLength;
            } else {
                float newLength = destLength * (1 - distance);
                mProgress += (barExtraLength - newLength);
                barExtraLength = newLength;
            }
        } else {
            pausedTimeWithoutGrowing += deltaTimeInMilliSeconds;
        }
    }

    /**
     * Check if the wheel is currently spinning.
     */
    public boolean areAreSpinning() {
        return areSpinning;
    }

    /**
     * Reset the count (in increment mode).
     */
    public void resetCount() {
        mProgress = 0.0F;
        mTargetProgress = 0.0F;
        invalidate();
    }

    /**
     * Turn off spin mode.
     */
    public void stopSpinning() {
        areSpinning = false;
        mProgress = 0.0F;
        mTargetProgress = 0.0F;
        invalidate();
    }

    /**
     * Puts the view on spin mode.
     */
    public void spin() {
        lastTimeAnimated = SystemClock.uptimeMillis();
        areSpinning = true;
        invalidate();
    }

    private void runCallback() {
        if (null != progressCallback) {
            float normalizedProgress = (float) Math.round(mProgress * 100 / 360.0F) / 100;
            progressCallback.onProgressUpdate(normalizedProgress);
        }
    }

    /**
     * Set the progress to a specific value, the bar will be set instantly to that value.
     *
     * @param progress the progress between 0 and 1
     */
    public void setInstantProgress(float progress) {
        if (areSpinning) {
            mProgress = 0.0F;
            areSpinning = false;
        }
        if (progress > WidgetMagic.FLOAT_ONE_DOT_ZERO) {
            progress -= 1.0F;
        } else if (progress < 0) {
            progress = 0;
        }
        if (FloatUtils.equal(progress, mTargetProgress)) {
            return;
        }
        mTargetProgress = Math.min(progress * 360.0F, 360.0F);
        mProgress = mTargetProgress;
        lastTimeAnimated = SystemClock.uptimeMillis();
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        WheelSavedState wheelSavedState = new WheelSavedState(superState);
        // we save everything that can be changed at runtime
        wheelSavedState.mProgress = this.mProgress;
        wheelSavedState.mTargetProgress = this.mTargetProgress;
        wheelSavedState.areSpinning = this.areSpinning;
        wheelSavedState.spinSpeed = this.spinSpeed;
        wheelSavedState.barWidth = this.barWidth;
        wheelSavedState.barColor = this.barColor;
        wheelSavedState.rimWidth = this.rimWidth;
        wheelSavedState.rimColor = this.rimColor;
        wheelSavedState.circleRadius = this.circleRadius;
        wheelSavedState.linearProgress = this.linearProgress;
        wheelSavedState.fillRadius = this.fillRadius;
        return wheelSavedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof WheelSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        WheelSavedState wheelSavedState = (WheelSavedState) state;
        super.onRestoreInstanceState(wheelSavedState.getSuperState());
        this.mProgress = wheelSavedState.mProgress;
        this.mTargetProgress = wheelSavedState.mTargetProgress;
        this.areSpinning = wheelSavedState.areSpinning;
        this.spinSpeed = wheelSavedState.spinSpeed;
        this.barWidth = wheelSavedState.barWidth;
        this.barColor = wheelSavedState.barColor;
        this.rimWidth = wheelSavedState.rimWidth;
        this.rimColor = wheelSavedState.rimColor;
        this.circleRadius = wheelSavedState.circleRadius;
        this.linearProgress = wheelSavedState.linearProgress;
        this.fillRadius = wheelSavedState.fillRadius;
        this.lastTimeAnimated = SystemClock.uptimeMillis();
    }

    /**
     * Get progress.
     *
     * @return The current progress between 0.0 and 1.0, if the wheel is indeterminate, then the result is -1.
     */
    public float getProgress() {
        return areSpinning ? -1 : mProgress / 360.0F;
    }

    //----------------------------------
    // getters + setters
    //----------------------------------

    /**
     * Set the progress to a specific value, the bar will smoothly animate until that value.
     *
     * @param progress The progress between 0 and 1.
     */
    public void setProgress(float progress) {
        if (areSpinning) {
            mProgress = 0.0F;
            areSpinning = false;
            runCallback();
        }
        if (progress > WidgetMagic.FLOAT_ONE_DOT_ZERO) {
            progress -= 1.0F;
        } else if (progress < 0) {
            progress = 0;
        }
        if (FloatUtils.equal(progress, mTargetProgress)) {
            return;
        }
        // if we are currently in the right position
        // we set again the last time animated so the animation starts smooth from here
        if (FloatUtils.equal(mProgress, mTargetProgress)) {
            lastTimeAnimated = SystemClock.uptimeMillis();
        }
        mTargetProgress = Math.min(progress * 360.0f, 360.0F);
        invalidate();
    }

    /**
     * Sets the determinate progress mode.
     *
     * @param isLinear if the progress should increase linearly
     */
    public void setLinearProgress(boolean isLinear) {
        linearProgress = isLinear;
        if (!areSpinning) {
            invalidate();
        }
    }

    /**
     * Get Circle radius.
     *
     * @return the radius of the wheel in pixels
     */
    public int getCircleRadius() {
        return circleRadius;
    }

    /**
     * Sets the radius of the wheel.
     *
     * @param circleRadius the expected radius, in pixels
     */
    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        if (!areSpinning) {
            invalidate();
        }
    }

    /**
     * Get bar width.
     *
     * @return the width of the spinning bar
     */
    public int getBarWidth() {
        return barWidth;
    }

    /**
     * Sets the width of the spinning bar.
     *
     * @param barWidth the spinning bar width in pixels
     */
    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        if (!areSpinning) {
            invalidate();
        }
    }

    /**
     * Get bar color.
     *
     * @return the color of the spinning bar
     */
    public int getBarColor() {
        return barColor;
    }

    /**
     * Sets the color of the spinning bar.
     *
     * @param barColor the spinning bar color
     */
    public void setBarColor(int barColor) {
        this.barColor = barColor;
        setupPaints();
        if (!areSpinning) {
            invalidate();
        }
    }

    /**
     * Get rim color.
     *
     * @return the color of the wheel's contour
     */
    public int getRimColor() {
        return rimColor;
    }

    /**
     * Sets the color of the wheel's contour.
     *
     * @param rimColor the color for the wheel
     */
    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
        setupPaints();
        if (!areSpinning) {
            invalidate();
        }
    }

    /**
     * Get spin speed.
     *
     * @return The base spinning speed, in full circle turns per second 1.0 equals on full turn in one second), this value also is applied for the smoothness when setting a progress.
     */
    public float getSpinSpeed() {
        return spinSpeed / 360.0F;
    }

    /**
     * Sets the base spinning speed, in full circle turns per second (1.0 equals on full turn in one second), this value also is applied for the smoothness when setting a progress.
     *
     * @param spinSpeed the desired base speed in full turns per second
     */
    public void setSpinSpeed(float spinSpeed) {
        this.spinSpeed = spinSpeed * 360.0F;
    }

    /**
     * Get rim width.
     *
     * @return the width of the wheel's contour in pixels
     */
    public int getRimWidth() {
        return rimWidth;
    }

    /**
     * Sets the width of the wheel's contour.
     *
     * @param rimWidth the width in pixels
     */
    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;
        if (!areSpinning) {
            invalidate();
        }
    }

    public interface ProgressCallback {
        /**
         * Method to call when the progress reaches a value in order to avoid float precision issues, the progress is rounded to a float with two decimals.
         * <p>
         * In indeterminate mode, the callback is called each time the wheel completes an animation cycle, with, the progress value is -1.0F.
         *
         * @param progress a double value between 0.00 and 1.00 both included
         */
        void onProgressUpdate(float progress);
    }

    static class WheelSavedState extends BaseSavedState {
        // required field that makes Parcelables from a Parcel
        public static final Creator<WheelSavedState> CREATOR =
                new Creator<WheelSavedState>() {
                    @NonNull
                    @Contract("_ -> new")
                    @Override
                    public WheelSavedState createFromParcel(Parcel in) {
                        return new WheelSavedState(in);
                    }

                    @NonNull
                    @Contract(value = "_ -> new", pure = true)
                    @Override
                    public WheelSavedState[] newArray(int size) {
                        return new WheelSavedState[size];
                    }
                };
        float mProgress;
        float mTargetProgress;
        boolean areSpinning;
        float spinSpeed;
        int barWidth;
        int barColor;
        int rimWidth;
        int rimColor;
        int circleRadius;
        boolean linearProgress;
        boolean fillRadius;

        WheelSavedState(Parcelable superState) {
            super(superState);
        }

        private WheelSavedState(Parcel in) {
            super(in);
            this.mProgress = in.readFloat();
            this.mTargetProgress = in.readFloat();
            this.areSpinning = (in.readByte() != 0);
            this.spinSpeed = in.readFloat();
            this.barWidth = in.readInt();
            this.barColor = in.readInt();
            this.rimWidth = in.readInt();
            this.rimColor = in.readInt();
            this.circleRadius = in.readInt();
            this.linearProgress = (in.readByte() != 0);
            this.fillRadius = (in.readByte() != 0);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.mProgress);
            out.writeFloat(this.mTargetProgress);
            out.writeByte((byte) (areSpinning ? 1 : 0));
            out.writeFloat(this.spinSpeed);
            out.writeInt(this.barWidth);
            out.writeInt(this.barColor);
            out.writeInt(this.rimWidth);
            out.writeInt(this.rimColor);
            out.writeInt(this.circleRadius);
            out.writeByte((byte) (linearProgress ? 1 : 0));
            out.writeByte((byte) (fillRadius ? 1 : 0));
        }
    }
}
