package com.chaos.widget.picture.easing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.chaos.widget.picture.easing.generator.RandomTransitionGenerator;
import com.chaos.widget.picture.easing.generator.TransitionGenerator;
import com.chaos.widget.picture.easing.transition.Transition;

/**
 * @decs: 缓动视图
 * @author: 郑少鹏
 * @date: 2019/10/26 16:39
 */
public class EasingView extends androidx.appcompat.widget.AppCompatImageView {
    /**
     * Delay between a pair of frames at a 60 FPS frame rate.
     */
    private static final long FRAME_DELAY = 1000 / 60;
    /**
     * Matrix used to perform all the necessary transition transformations.
     */
    private final Matrix mMatrix = new Matrix();
    /**
     * The rect that holds the bounds of this view.
     */
    private final RectF mViewportRect = new RectF();
    /**
     * Indicates whether the parent constructor was already called.
     * This is needed to distinguish if the image is being set before or after the super class constructor returns.
     */
    private final boolean mInitialized;
    /**
     * The {@link TransitionGenerator} implementation used to perform the transitions between rects.
     * The default {@link TransitionGenerator} is {@link RandomTransitionGenerator}.
     */
    private TransitionGenerator mTransGen = new RandomTransitionGenerator();
    /**
     * A {@link TransitionListener} to be notified when a transition starts or ends.
     */
    private TransitionListener mTransitionListener;
    /**
     * The ongoing transition.
     */
    private Transition mCurrentTrans;
    /**
     * The rect that holds the bounds of the current {@link Drawable}.
     */
    private RectF mDrawableRect;
    /**
     * The progress of the animation, in milliseconds.
     */
    private long mElapsedTime;
    /**
     * The time, in milliseconds, of the last animation frame.
     * This is useful to increment {@link #mElapsedTime} regardless of the amount of time the animation has been paused.
     */
    private long mLastFrameTime;
    /**
     * Controls whether the the animation is running.
     */
    private boolean mPaused;

    public EasingView(Context context) {
        this(context, null);
    }

    public EasingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInitialized = true;
        // Attention to the super call here!
        super.setScaleType(ScaleType.MATRIX);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        // It'll always be center-cropped by default.
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        // When not visible, onDraw() doesn't get called, but the time elapses anyway.
        if (visibility == VISIBLE) {
            resume();
        } else {
            pause();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        handleImageChange();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        handleImageChange();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        handleImageChange();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        handleImageChange();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        restart();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (!mPaused && (null != drawable)) {
            if (mDrawableRect.isEmpty()) {
                updateDrawableBounds();
            } else if (hasBounds()) {
                if (null == mCurrentTrans) {
                    // Starting the first transition.
                    startNewTransition();
                }
                if (null != mCurrentTrans.getDestinyRect()) {
                    // If null, it's supposed to stop.
                    mElapsedTime += (System.currentTimeMillis() - mLastFrameTime);
                    RectF currentRect = mCurrentTrans.getInterpolatedRect(mElapsedTime);
                    float widthScale = mDrawableRect.width() / currentRect.width();
                    float heightScale = mDrawableRect.height() / currentRect.height();
                    // Scale to make the current rect match the smallest drawable dimension.
                    float currRectToDrwScale = Math.min(widthScale, heightScale);
                    // Scale to make the current rect match the viewport bounds.
                    float vpWidthScale = mViewportRect.width() / currentRect.width();
                    float vpHeightScale = mViewportRect.height() / currentRect.height();
                    float currRectToVpScale = Math.min(vpWidthScale, vpHeightScale);
                    // Combines the two scales to fill the viewport with the current rect.
                    float totalScale = currRectToDrwScale * currRectToVpScale;
                    float xTransl = totalScale * (mDrawableRect.centerX() - currentRect.left);
                    float yTransl = totalScale * (mDrawableRect.centerY() - currentRect.top);
                    // Performs matrix transformations to fit the content of the current rect into the entire view.
                    mMatrix.reset();
                    mMatrix.postTranslate(-mDrawableRect.width() / 2, -mDrawableRect.height() / 2);
                    mMatrix.postScale(totalScale, totalScale);
                    mMatrix.postTranslate(xTransl, yTransl);
                    setImageMatrix(mMatrix);
                    // Current transition is over. It's time to start a new one.
                    if (mElapsedTime >= mCurrentTrans.getDuration()) {
                        fireTransitionEnd(mCurrentTrans);
                        startNewTransition();
                    }
                } else {
                    // Stopping? A stop event has to be fired.
                    fireTransitionEnd(mCurrentTrans);
                }
            }
            mLastFrameTime = System.currentTimeMillis();
            postInvalidateDelayed(FRAME_DELAY);
        }
        super.onDraw(canvas);
    }

    /**
     * Generates and starts a transition.
     */
    private void startNewTransition() {
        if (!hasBounds()) {
            // Can't start transition if the drawable has no bounds.
            return;
        }
        mCurrentTrans = mTransGen.generateNextTransition(mDrawableRect, mViewportRect);
        mElapsedTime = 0;
        mLastFrameTime = System.currentTimeMillis();
        fireTransitionStart(mCurrentTrans);
    }

    /**
     * Creates a new transition and starts over.
     */
    public void restart() {
        int width = getWidth();
        int height = getHeight();
        if ((width == 0) || (height == 0)) {
            // Can't call restart() when view area is zero.
            return;
        }
        updateViewport(width, height);
        updateDrawableBounds();
        startNewTransition();
    }

    /**
     * Checks whether this view has bounds.
     *
     * @return boolean
     */
    private boolean hasBounds() {
        return !mViewportRect.isEmpty();
    }

    /**
     * Fires a start event on {@link #mTransitionListener};
     *
     * @param transition The transition that just started.
     */
    private void fireTransitionStart(Transition transition) {
        if ((null != mTransitionListener) && (null != transition)) {
            mTransitionListener.onTransitionStart(transition);
        }
    }

    /**
     * Fires an end event on {@link #mTransitionListener};
     *
     * @param transition The transition that just ended.
     */
    private void fireTransitionEnd(Transition transition) {
        if ((null != mTransitionListener) && (null != transition)) {
            mTransitionListener.onTransitionEnd(transition);
        }
    }

    /**
     * Sets the {@link TransitionGenerator} to be used in animations.
     *
     * @param transitionGenerator The {@link TransitionGenerator} to be used in animations.
     */
    public void setTransitionGenerator(TransitionGenerator transitionGenerator) {
        mTransGen = transitionGenerator;
        startNewTransition();
    }

    /**
     * Updates the viewport rect. This must be called every time the size of this view changes.
     *
     * @param width  The new viewport with.
     * @param height The new viewport height.
     */
    private void updateViewport(float width, float height) {
        mViewportRect.set(0, 0, width, height);
    }

    /**
     * Updates the drawable bounds rect.
     * This must be called every time the drawable associated to this view changes.
     */
    private void updateDrawableBounds() {
        if (null == mDrawableRect) {
            mDrawableRect = new RectF();
        }
        Drawable drawable = getDrawable();
        if ((null != drawable) && (drawable.getIntrinsicHeight() > 0) && (drawable.getIntrinsicWidth() > 0)) {
            mDrawableRect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    /**
     * This method is called every time the underlying image is changed.
     */
    private void handleImageChange() {
        updateDrawableBounds();
        // Don't start a new transition if this event was fired during the super constructor execution.
        // The view won't be ready at this time.
        // Also, don't start it if this view size is still unknown.
        if (mInitialized) {
            startNewTransition();
        }
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        mTransitionListener = transitionListener;
    }

    /**
     * Pauses the Ken Burns Effect animation.
     */
    public void pause() {
        mPaused = true;
    }

    /**
     * Resumes the Ken Burns Effect animation.
     */
    public void resume() {
        mPaused = false;
        // This will make the animation to continue from where it stopped.
        mLastFrameTime = System.currentTimeMillis();
        invalidate();
    }

    /**
     * A transition listener receives notifications when a transition starts or ends.
     */
    public interface TransitionListener {
        /**
         * Notifies the start of a transition.
         *
         * @param transition The transition that just started.
         */
        void onTransitionStart(Transition transition);

        /**
         * Notifies the end of a transition.
         *
         * @param transition The transition that just ended.
         */
        void onTransitionEnd(Transition transition);
    }
}
