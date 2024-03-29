package com.chaos.widget.picture.pulse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created on 2021/8/30
 *
 * @author zsp
 * @desc 脉动视图
 */
public class PulseView extends View {
    /**
     * default pulse variables
     */
    private final static int DEFAULT_PULSE_COUNT = 5;
    private final static int DEFAULT_PULSE_SPAWN_PERIOD = 700;
    private final static int DEFAULT_PULSE_ALPHA = 70;
    private final static int DEFAULT_PULSE_COLOR = Color.DKGRAY;
    private final static int DEFAULT_PULSE_MEASURE = PulseMeasure.WIDTH_INDEX;
    /**
     * bounds values
     */
    private final static float MIN_SCALE = 1.0F;
    private final static float MAX_FRACTION = 1.0F;
    /**
     * alpha range
     */
    private final static float MIN_ALPHA = 0.0F;
    private final static float MAX_ALPHA = 255.0F;
    /**
     * pulseView bounds
     */
    private final RectF mBounds = new RectF();
    /**
     * pulse models
     */
    private final List<PulseModel> mPulseModels = new ArrayList<>();
    /**
     * pulse paint
     */
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setDither(true);
            setFilterBitmap(true);
            setStyle(Style.FILL);
        }
    };
    /**
     * icon bitmap and resId
     */
    private Bitmap mIconBitmap;
    private int mIconRes;
    /**
     * pulse measure side
     * <p>
     * This provide ability to measure pulse size relative to width or height.
     */
    private PulseMeasure mPulseMeasure;
    /**
     * pulse variables
     */
    private int mPulseCount;
    private long mPulseSpawnPeriod;
    private float mPulseAlpha;
    private int mPulseColor;
    /**
     * icon size variables
     */
    private int mIconWidth;
    private int mIconHeight;
    /**
     * pulse total duration
     */
    private float mPulseDuration;
    /**
     * pulse destination scale relative to 1.0F fraction time
     */
    private float mPulseDestScale;
    /**
     * current time in millis
     */
    private long mCurrentTime;
    /**
     * last spawn time in millis
     */
    private long mLastTime;
    /**
     * detect whether pulse is started
     */
    private boolean mArePulseStarted;
    /**
     * detect whether pulse is need to finish
     */
    private boolean mAreFinishPulse;
    /**
     * pulse listener
     */
    private PulseListener mPulseListener;
    /**
     * interpolator for pulse
     * <p>
     * Choose your favourite.
     */
    private Interpolator mInterpolator;

    public PulseView(final Context context) {
        this(context, null);
    }

    public PulseView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PulseView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Always draw and improve speed.
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        // Get attrs.
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PulseView);
        try {
            setIconRes(typedArray.getResourceId(R.styleable.PulseView_pulseViewIcon, 0));
            setIconWidth((int) typedArray.getDimension(R.styleable.PulseView_pulseViewIconWidth, 0));
            setIconHeight((int) typedArray.getDimension(R.styleable.PulseView_pulseViewIconHeight, 0));
            setPulseCount(typedArray.getInteger(R.styleable.PulseView_pulseViewCount, DEFAULT_PULSE_COUNT));
            setPulseSpawnPeriod(typedArray.getInteger(R.styleable.PulseView_pulseViewSpawnPeriod, DEFAULT_PULSE_SPAWN_PERIOD));
            setPulseAlpha(typedArray.getInteger(R.styleable.PulseView_pulseViewAlpha, DEFAULT_PULSE_ALPHA));
            setPulseColor(typedArray.getColor(R.styleable.PulseView_pulseViewColor, DEFAULT_PULSE_COLOR));
            setPulseMeasure(typedArray.getInt(R.styleable.PulseView_pulseViewMeasure, DEFAULT_PULSE_MEASURE));
            // Retrieve interpolator.
            Interpolator interpolator = null;
            try {
                final int interpolatorId = typedArray.getResourceId(R.styleable.PulseView_pulseViewInterpolator, 0);
                interpolator = ((interpolatorId == 0) ? null : AnimationUtils.loadInterpolator(context, interpolatorId));
            } catch (Resources.NotFoundException exception) {
                Timber.e(exception);
            } finally {
                setInterpolator(interpolator);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public int getIconRes() {
        return mIconRes;
    }

    /**
     * Set icon resId and get bitmap.
     *
     * @param iconRes iconRes
     */
    public void setIconRes(final int iconRes) {
        mIconRes = iconRes;
        invalidateIcon();
    }

    public Bitmap getIconBitmap() {
        return mIconBitmap;
    }

    public PulseMeasure getPulseMeasure() {
        return mPulseMeasure;
    }

    private void setPulseMeasure(final int index) {
        switch (index) {
            case PulseMeasure.HEIGHT_INDEX:
                setPulseMeasure(PulseMeasure.HEIGHT);
                break;
            case PulseMeasure.WIDTH_INDEX:
            default:
                setPulseMeasure(PulseMeasure.WIDTH);
                break;
        }
    }

    public void setPulseMeasure(final PulseMeasure pulseMeasure) {
        mPulseMeasure = pulseMeasure;
        requestLayout();
    }

    public int getPulseCount() {
        return mPulseCount;
    }

    public void setPulseCount(final int pulseCount) {
        mPulseCount = pulseCount;
        invalidatePulse();
    }

    public long getPulseSpawnPeriod() {
        return mPulseSpawnPeriod;
    }

    public void setPulseSpawnPeriod(final long pulseSpawnPeriod) {
        mPulseSpawnPeriod = pulseSpawnPeriod;
        invalidatePulse();
    }

    public float getPulseAlpha() {
        return mPulseAlpha;
    }

    @SuppressLint("SupportAnnotationUsage")
    @FloatRange
    public void setPulseAlpha(@FloatRange(from = MIN_ALPHA, to = MAX_ALPHA) final float pulseAlpha) {
        mPulseAlpha = Math.max(MIN_ALPHA, Math.min(pulseAlpha, MAX_ALPHA));
        postInvalidate();
    }

    public int getPulseColor() {
        return mPulseColor;
    }

    public void setPulseColor(final int pulseColor) {
        mPulseColor = pulseColor;
        // Set color and color filter to fill our icon.
        mPaint.setColor(pulseColor);
        mPaint.setColorFilter(new PorterDuffColorFilter(pulseColor, PorterDuff.Mode.SRC_IN));
        postInvalidate();
    }

    public int getIconWidth() {
        return mIconWidth;
    }

    public void setIconWidth(final int iconWidth) {
        mIconWidth = iconWidth;
        invalidateIcon();
    }

    public int getIconHeight() {
        return mIconHeight;
    }

    public void setIconHeight(final int iconHeight) {
        mIconHeight = iconHeight;
        invalidateIcon();
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(final Interpolator interpolator) {
        mInterpolator = ((null == interpolator) ? new LinearInterpolator() : interpolator);
    }

    public PulseListener getPulseListener() {
        return mPulseListener;
    }

    public void setPulseListener(final PulseListener pulseListener) {
        mPulseListener = pulseListener;
    }

    /**
     * Start pulse.
     */
    public void startPulse() {
        mArePulseStarted = true;
        mAreFinishPulse = false;
        if (null != mPulseListener) {
            mPulseListener.onStartPulse();
        }
        postInvalidate();
    }

    /**
     * Finish pulse.
     */
    public void finishPulse() {
        mAreFinishPulse = true;
        postInvalidate();
    }

    /**
     * Invalidate icon and get bitmap by icon resId.
     */
    private void invalidateIcon() {
        // You must to set icon resId.
        if (mIconRes == 0) {
            throw new IllegalArgumentException("Icon not found. Please select the icon and set to PulseView");
        }
        final Drawable iconDrawable = ContextCompat.getDrawable(getContext(), mIconRes);
        if (null != iconDrawable) {
            // Get width and height of bitmap.
            // If there are is some 0, get original size.
            final int width = ((mIconWidth == 0) ? iconDrawable.getIntrinsicWidth() : mIconWidth);
            final int height = ((mIconHeight == 0) ? iconDrawable.getIntrinsicHeight() : mIconHeight);
            if (iconDrawable instanceof BitmapDrawable) {
                // Create scaled bitmap relative to size from normal image,
                mIconBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) iconDrawable).getBitmap(), width, height, true);
            } else {
                // Create icon bitmap for other type of resources, especially SVG,
                mIconBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(mIconBitmap);
                iconDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                iconDrawable.draw(canvas);
            }
        } else {
            // There is somethings wrong with your icon,
            mIconBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }
    }

    /**
     * Invalidate pulse.
     * <p>
     * Need to recalculate dest scale relative to pulse measure and detect general pulse duration.
     */
    private void invalidatePulse() {
        mPulseDestScale = ((mPulseMeasure == PulseMeasure.HEIGHT) ?
                ((mBounds.height() - (float) mIconBitmap.getHeight()) / (float) mIconBitmap.getHeight()) :
                ((mBounds.width() - (float) mIconBitmap.getWidth()) / (float) mIconBitmap.getWidth()));
        mPulseDuration = mPulseCount * mPulseSpawnPeriod;
        postInvalidate();
    }

    /**
     * Restore pulse state according to current time when config changes or view goes out and restore.
     */
    private void restorePulseState() {
        mCurrentTime = System.currentTimeMillis();
        mLastTime = (mCurrentTime - mPulseSpawnPeriod);
        for (int i = 0; i < mPulseModels.size(); i++) {
            mPulseModels.get(i).setStartTime(mCurrentTime - mPulseSpawnPeriod * i);
        }
        postInvalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        // Reset bounds and pulse.
        mBounds.set(0.0F, 0.0F, width, height);
        invalidatePulse();
        // Provide pulse preview,
        if (isInEditMode()) {
            for (int i = 0; i < mPulseCount; i++) {
                mPulseModels.add(new PulseModel(0));
            }
            restorePulseState();
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        // Get center variables of icon.
        final float bitmapLeft = (mBounds.centerX() - (mIconBitmap.getWidth() * 0.5F));
        final float bitmapTop = (mBounds.centerY() - (mIconBitmap.getHeight() * 0.5F));
        // Detect if pulse is started for pulse drawing.
        if (mArePulseStarted) {
            // Get current time
            if (hasWindowFocus()) {
                mCurrentTime = System.currentTimeMillis();
            }
            // If view not going to finish, so add new pulse after spawn period.
            if (!mAreFinishPulse && (mCurrentTime > (mLastTime + mPulseSpawnPeriod))) {
                mLastTime = mCurrentTime;
                // Limit pulse models size to pulse count.
                if (mPulseModels.size() < mPulseCount) {
                    // We need to set start time of pulse model.
                    // Its our start value where we can calculate fraction of pulse according to pulse duration.
                    mPulseModels.add(0, new PulseModel(mCurrentTime));
                }
            }
            // Draw pulse models.
            for (int i = 0; i < mPulseModels.size(); i++) {
                // Get pulse model.
                final PulseModel pulseModel = mPulseModels.get(i);
                // Set pulse fraction in range [0.0F, 1.0F].
                pulseModel.setFraction((float) (mCurrentTime - pulseModel.getStartTime()) / mPulseDuration);
                // Save canvas for scaling.
                canvas.save();
                // Get pulse scale where min is 1.0F and max is mPulseDestScale.
                // Interpolator used for some special effects.
                final float pulseScale = (MIN_SCALE + (mInterpolator.getInterpolation(pulseModel.getFraction()) * mPulseDestScale));
                // Scale canvas.
                // Its provide more speed unlike scale icon matrix
                canvas.scale(pulseScale, pulseScale, mBounds.centerX(), mBounds.centerY());
                // Set pulse alpha.
                // We need alpha for better effect, cause we have pulse models like stack so alpha would also stack.
                mPaint.setAlpha((int) (mPulseAlpha - (pulseModel.getFraction() * mPulseAlpha)));
                // Draw icon bitmap pulse and restore canvas to default scale.
                canvas.drawBitmap(mIconBitmap, bitmapLeft, bitmapTop, mPaint);
                canvas.restore();
            }
            // Detect whether we have filled models and last pulse reached max fraction.
            if (!mPulseModels.isEmpty() && (MAX_FRACTION == (float) Math.floor(mPulseModels.get(mPulseModels.size() - 1).getFraction()))) {
                // Remove finished pulse.
                mPulseModels.remove(mPulseModels.size() - 1);
            }
            // If pulses goes to finish and pulse models is empty - finish pulse.
            if (mAreFinishPulse && mPulseModels.isEmpty()) {
                mAreFinishPulse = false;
                mArePulseStarted = false;
                if (null != mPulseListener) {
                    mPulseListener.onFinishPulse();
                }
            }
        }
        // Draw icon bitmap.
        canvas.drawBitmap(mIconBitmap, bitmapLeft, bitmapTop, null);
        // Invalidate draw for auto refresh.
        if (hasWindowFocus()) {
            postInvalidate();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull final View changedView, final int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        restorePulseState();
    }

    @Override
    public void onWindowFocusChanged(final boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        // Restore pulse after resume.
        if (hasWindowFocus) {
            restorePulseState();
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        // Clear old models and add last restored.
        mPulseModels.clear();
        mPulseModels.addAll(savedState.mPulseModels);
        // Restore variables and pulse.
        mArePulseStarted = savedState.mArePulseStarted;
        mAreFinishPulse = savedState.mAreFinishPulse;
        restorePulseState();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState savedState = new SavedState(superState);
        // Save current state of PulseView.
        savedState.mPulseModels = mPulseModels;
        savedState.mArePulseStarted = mArePulseStarted;
        savedState.mAreFinishPulse = mAreFinishPulse;
        return savedState;
    }

    /**
     * pulse measure
     */
    public enum PulseMeasure {
        /**
         * 宽
         */
        WIDTH,
        /**
         * 高
         */
        HEIGHT;
        private final static int WIDTH_INDEX = 0;
        private final static int HEIGHT_INDEX = 1;
    }

    /**
     * 脉动监听
     */
    public interface PulseListener {
        /**
         * OnStart trigger when we start draw pulse.
         */
        void onStartPulse();

        /**
         * OnFinish trigger when all of pulse models reached their max fraction
         */
        void onFinishPulse();
    }

    private static class SavedState extends BaseSavedState {
        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @NonNull
            @Contract("_ -> new")
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @NonNull
            @Contract(value = "_ -> new", pure = true)
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        // SavedState variables.
        private List<PulseModel> mPulseModels;
        private boolean mArePulseStarted;
        private boolean mAreFinishPulse;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @SuppressWarnings("unchecked")
        private SavedState(Parcel in) {
            super(in);
            mPulseModels = (List<PulseModel>) in.readSerializable();
            mArePulseStarted = (in.readByte() != 0);
            mAreFinishPulse = (in.readByte() != 0);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeSerializable((Serializable) mPulseModels);
            dest.writeByte((byte) (mArePulseStarted ? 1 : 0));
            dest.writeByte((byte) (mAreFinishPulse ? 1 : 0));
        }
    }

    private static class PulseModel implements Serializable {
        // Start time of pulse model.
        private long mStartTime;
        // Current fraction of pulse model.
        private float mFraction;

        public PulseModel(final long startTime) {
            mStartTime = startTime;
        }

        public long getStartTime() {
            return mStartTime;
        }

        public void setStartTime(final long startTime) {
            mStartTime = startTime;
        }

        public float getFraction() {
            return mFraction;
        }

        public void setFraction(final float fraction) {
            mFraction = fraction;
        }
    }
}
