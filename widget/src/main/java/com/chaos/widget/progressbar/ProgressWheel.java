package com.chaos.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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
 * Created on 2022/8/11
 *
 * @author zsp
 * @desc ProgressWheel
 */
public class ProgressWheel extends View {
    private final int barLength = 16;
    private final Paint barPaint = new Paint();
    private final Paint rimPaint = new Paint();
    private int circleRadius = 28;
    private int barWidth = 4;
    private int rimWidth = 4;
    private boolean fillRadius = false;
    private double timeStartGrowing = 0;
    private double barSpinCycleTime = 460;
    private float barExtraLength = 0;
    private boolean barGrowingFromFront = true;
    private long pausedTimeWithoutGrowing = 0;
    private int barColor = 0xAA000000;
    private int rimColor = 0x00FFFFFF;
    private RectF circleBounds = new RectF();
    private float spinSpeed = 230.0F;
    private long lastTimeAnimated = 0;
    private boolean linearProgress;
    private float progress = 0.0F;
    private float targetProgress = 0.0F;
    private boolean areSpinning = false;
    private ProgressCallback callback;
    private boolean shouldAnimate;

    /**
     * The constructor for the ProgressWheel.
     */
    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
        setAnimationEnabled();
    }

    /**
     * The constructor for the ProgressWheel.
     */
    public ProgressWheel(Context context) {
        super(context);
        setAnimationEnabled();
    }

    private void setAnimationEnabled() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        float animationValue;
        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            animationValue = Settings.Global.getFloat(getContext().getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1);
        } else {
            animationValue = Settings.System.getFloat(getContext().getContentResolver(), Settings.System.ANIMATOR_DURATION_SCALE, 1);
        }
        shouldAnimate = (animationValue != 0);
    }

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
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(viewWidth, widthSize);
        } else {
            width = viewWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(viewHeight, heightSize);
        } else {
            height = viewHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupBounds(w, h);
        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        barPaint.setColor(barColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(barWidth);
        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Paint.Style.STROKE);
        rimPaint.setStrokeWidth(rimWidth);
    }

    private void setupBounds(int layoutWidth, int layoutHeight) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (!fillRadius) {
            int minValue = Math.min(layoutWidth - paddingLeft - paddingRight, layoutHeight - paddingBottom - paddingTop);
            int circleDiameter = Math.min(minValue, circleRadius * 2 - barWidth * 2);
            int xOffset = ((layoutWidth - paddingLeft - paddingRight - circleDiameter) / 2 + paddingLeft);
            int yOffset = ((layoutHeight - paddingTop - paddingBottom - circleDiameter) / 2 + paddingTop);
            circleBounds = new RectF(xOffset + barWidth, yOffset + barWidth, xOffset + circleDiameter - barWidth, yOffset + circleDiameter - barWidth);
        } else {
            circleBounds = new RectF(paddingLeft + barWidth, paddingTop + barWidth, layoutWidth - paddingRight - barWidth, layoutHeight - paddingBottom - barWidth);
        }
    }

    private void parseAttributes(@NonNull TypedArray typedArray) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        barWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth, metrics);
        rimWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rimWidth, metrics);
        circleRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, circleRadius, metrics);
        circleRadius = (int) typedArray.getDimension(R.styleable.ProgressWheel_progressWheelCircleRadius, circleRadius);
        fillRadius = typedArray.getBoolean(R.styleable.ProgressWheel_progressWheelFillRadius, false);
        barWidth = (int) typedArray.getDimension(R.styleable.ProgressWheel_progressWheelBarWidth, barWidth);
        rimWidth = (int) typedArray.getDimension(R.styleable.ProgressWheel_progressWheelRimWidth, rimWidth);
        float baseSpinSpeed = typedArray.getFloat(R.styleable.ProgressWheel_progressWheelSpinSpeed, spinSpeed / 360.0F);
        spinSpeed = baseSpinSpeed * 360;
        barSpinCycleTime = typedArray.getInt(R.styleable.ProgressWheel_progressWheelBarSpinCycleTime, (int) barSpinCycleTime);
        barColor = typedArray.getColor(R.styleable.ProgressWheel_progressWheelBarColor, barColor);
        rimColor = typedArray.getColor(R.styleable.ProgressWheel_progressWheelRimColor, rimColor);
        linearProgress = typedArray.getBoolean(R.styleable.ProgressWheel_progressWheelLinearProgress, false);
        if (typedArray.getBoolean(R.styleable.ProgressWheel_progressWheelProgressIndeterminate, false)) {
            spin();
        }
        typedArray.recycle();
    }

    public void setCallback(ProgressCallback progressCallback) {
        callback = progressCallback;
        if (!areSpinning) {
            runCallback();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        boolean mustInvalidate = false;
        if (!shouldAnimate) {
            return;
        }
        if (areSpinning) {
            mustInvalidate = true;
            long deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated);
            float deltaNormalized = deltaTime * spinSpeed / 1000.0F;
            updateBarLength(deltaTime);
            progress += deltaNormalized;
            if (progress > WidgetMagic.INT_THREE_HUNDRED_SIXTY) {
                progress -= 360.0F;
                runCallbackTwo();
            }
            lastTimeAnimated = SystemClock.uptimeMillis();
            float from = progress - 90;
            float length = barLength + barExtraLength;
            if (isInEditMode()) {
                from = 0;
                length = 135;
            }
            canvas.drawArc(circleBounds, from, length, false, barPaint);
        } else {
            float oldProgress = progress;
            if (!FloatUtils.equal(progress, targetProgress)) {
                mustInvalidate = true;
                float deltaTime = (float) (SystemClock.uptimeMillis() - lastTimeAnimated) / 1000;
                float deltaNormalized = deltaTime * spinSpeed;
                progress = Math.min(progress + deltaNormalized, targetProgress);
                lastTimeAnimated = SystemClock.uptimeMillis();
            }
            if (!FloatUtils.equal(oldProgress, progress)) {
                runCallback();
            }
            float offset = 0.0F;
            float progress = this.progress;
            if (!linearProgress) {
                float factor = 2.0F;
                offset = (float) (1.0F - Math.pow(1.0F - this.progress / 360.0F, 2.0F * factor)) * 360.0F;
                progress = (float) (1.0F - Math.pow(1.0F - this.progress / 360.0F, factor)) * 360.0F;
            }
            if (isInEditMode()) {
                progress = 360.0F;
            }
            canvas.drawArc(circleBounds, offset - 90, progress, false, barPaint);
        }
        if (mustInvalidate) {
            invalidate();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
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
                timeStartGrowing -= barSpinCycleTime;
                pausedTimeWithoutGrowing = 0;
                barGrowingFromFront = !barGrowingFromFront;
            }
            float distance = (float) Math.cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI) / 2 + 0.5F;
            int barMaxLength = 270;
            float destLength = (barMaxLength - barLength);
            if (barGrowingFromFront) {
                barExtraLength = distance * destLength;
            } else {
                float newLength = destLength * (1 - distance);
                progress += (barExtraLength - newLength);
                barExtraLength = newLength;
            }
        } else {
            pausedTimeWithoutGrowing += deltaTimeInMilliSeconds;
        }
    }

    public boolean isAreSpinning() {
        return areSpinning;
    }

    public void resetCount() {
        progress = 0.0F;
        targetProgress = 0.0F;
        invalidate();
    }

    public void stopSpinning() {
        areSpinning = false;
        progress = 0.0F;
        targetProgress = 0.0F;
        invalidate();
    }

    public void spin() {
        lastTimeAnimated = SystemClock.uptimeMillis();
        areSpinning = true;
        invalidate();
    }

    private void runCallbackTwo() {
        if (null != callback) {
            callback.onProgressUpdate((float) -1.0);
        }
    }

    private void runCallback() {
        if (null != callback) {
            float normalizedProgress = (float) Math.round(progress * 100 / 360.0F) / 100;
            callback.onProgressUpdate(normalizedProgress);
        }
    }

    public void setInstantProgress(float progress) {
        if (areSpinning) {
            this.progress = 0.0F;
            areSpinning = false;
        }
        if (progress > WidgetMagic.FLOAT_ONE_DOT_ZERO) {
            progress -= 1.0F;
        } else if (progress < 0) {
            progress = 0;
        }
        if (Float.compare(progress, targetProgress) == 0) {
            return;
        }
        targetProgress = Math.min(progress * 360.0F, 360.0F);
        this.progress = targetProgress;
        lastTimeAnimated = SystemClock.uptimeMillis();
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        WheelSavedState wheelSavedState = new WheelSavedState(superState);
        wheelSavedState.mProgress = this.progress;
        wheelSavedState.mTargetProgress = this.targetProgress;
        wheelSavedState.isSpinning = this.areSpinning;
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
        this.progress = wheelSavedState.mProgress;
        this.targetProgress = wheelSavedState.mTargetProgress;
        this.areSpinning = wheelSavedState.isSpinning;
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

    public float getProgress() {
        return areSpinning ? -1 : progress / 360.0F;
    }

    public void setProgress(float progress) {
        if (areSpinning) {
            this.progress = 0.0F;
            areSpinning = false;
            runCallback();
        }
        if (progress > WidgetMagic.FLOAT_ONE_DOT_ZERO) {
            progress -= 1.0F;
        } else if (progress < 0) {
            progress = 0;
        }
        if (Float.compare(progress, targetProgress) == 0) {
            return;
        }
        if (this.progress == targetProgress) {
            lastTimeAnimated = SystemClock.uptimeMillis();
        }
        targetProgress = Math.min(progress * 360.0F, 360.0F);
        invalidate();
    }

    public void setLinearProgress(boolean areLinear) {
        linearProgress = areLinear;
        if (!areSpinning) {
            invalidate();
        }
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        if (!areSpinning) {
            invalidate();
        }
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        if (!areSpinning) {
            invalidate();
        }
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        setupPaints();
        if (!areSpinning) {
            invalidate();
        }
    }

    public int getRimColor() {
        return rimColor;
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
        setupPaints();
        if (!areSpinning) {
            invalidate();
        }
    }

    public float getSpinSpeed() {
        return spinSpeed / 360.0F;
    }

    public void setSpinSpeed(float spinSpeed) {
        this.spinSpeed = spinSpeed * 360.0F;
    }

    public int getRimWidth() {
        return rimWidth;
    }

    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;
        if (!areSpinning) {
            invalidate();
        }
    }

    public interface ProgressCallback {
        /**
         * 进度更新
         *
         * @param progress 进度
         */
        void onProgressUpdate(float progress);
    }

    static class WheelSavedState extends BaseSavedState {
        public static final Parcelable.Creator<WheelSavedState> CREATOR = new Parcelable.Creator<WheelSavedState>() {
            @Override
            @NonNull
            @Contract("_ -> new")
            public WheelSavedState createFromParcel(Parcel in) {
                return new WheelSavedState(in);
            }

            @Override
            @NonNull
            @Contract(value = "_ -> new", pure = true)
            public WheelSavedState[] newArray(int size) {
                return new WheelSavedState[size];
            }
        };
        float mProgress;
        float mTargetProgress;
        boolean isSpinning;
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
            this.isSpinning = in.readByte() != 0;
            this.spinSpeed = in.readFloat();
            this.barWidth = in.readInt();
            this.barColor = in.readInt();
            this.rimWidth = in.readInt();
            this.rimColor = in.readInt();
            this.circleRadius = in.readInt();
            this.linearProgress = in.readByte() != 0;
            this.fillRadius = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.mProgress);
            out.writeFloat(this.mTargetProgress);
            out.writeByte((byte) (isSpinning ? 1 : 0));
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
