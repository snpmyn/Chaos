package com.chaos.widget.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.chaos.util.java.log.LogUtils;
import com.chaos.widget.R;

/**
 * @desc: 显隐密码软键盘
 * @author: zsp
 * @date: 2021/9/10 4:09 下午
 */
public class ShowHidePasswordEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final String TAG = ShowHidePasswordEditText.class.getSimpleName();
    private final static String ARE_SHOWING_PASSWORD_STATE_KEY = "ARE_SHOWING_PASSWORD_STATE_KEY";
    private final static String SUPER_STATE_KEY = "SUPER_STATE_KEY";
    private final int DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE = 40;
    private boolean areShowingPassword = false;
    private Drawable drawableEnd;
    private boolean leftToRight = true;
    private int tintColor = 0;
    @DrawableRes
    private int visibilityIndicatorShow;
    @DrawableRes
    private int visibilityIndicatorHide;
    private int additionalTouchTargetSize = DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE;

    public ShowHidePasswordEditText(Context context) {
        super(context);
        step(null);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        step(attrs);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        step(attrs);
    }

    private void step(AttributeSet attributeSet) {
        if (null != attributeSet) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.ShowHidePasswordEditText);
            visibilityIndicatorShow = typedArray.getResourceId(R.styleable.ShowHidePasswordEditText_showHidePasswordEditTextDrawableShow, R.drawable.ic_visibility_font_input_20dp);
            visibilityIndicatorHide = typedArray.getResourceId(R.styleable.ShowHidePasswordEditText_showHidePasswordEditTextDrawableHide, R.drawable.ic_visibility_off_font_input_20dp);
            tintColor = typedArray.getColor(R.styleable.ShowHidePasswordEditText_showHidePasswordEditTextTintColor, 0);
            additionalTouchTargetSize = typedArray.getDimensionPixelSize(R.styleable.ShowHidePasswordEditText_showHidePasswordEditTextAdditionalTouchTargetSize, DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE);
            typedArray.recycle();
        } else {
            visibilityIndicatorShow = R.drawable.ic_visibility_font_input_20dp;
            visibilityIndicatorHide = R.drawable.ic_visibility_off_font_input_20dp;
        }
        leftToRight = areLeftToRight();
        // ensures by default this view is only line only
        setMaxLines(1);
        // note this must be set before maskPassword() otherwise it was undo the passwordTransformation
        setSingleLine(true);
        // initial state is hiding
        areShowingPassword = false;
        maskPassword();
        // save the state of whether the password is being shown
        setSaveEnabled(true);
        if (!TextUtils.isEmpty(getText())) {
            showPasswordVisibilityIndicator(true);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showPasswordVisibilityIndicator(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean areLeftToRight() {
        // If we are pre JB assume always LTR.
        // Other methods, seemingly broken when testing though.
        /*return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;*/
        /*return !ViewUtils.isLayoutRtl(this);*/
        Configuration config = getResources().getConfiguration();
        return (config.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        // keep a reference to the right drawable so later on touch we can check if touch is on the drawable.
        if (leftToRight && (null != right)) {
            drawableEnd = right;
        } else if (!leftToRight && (null != left)) {
            drawableEnd = left;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setTintColor(@ColorInt int tintColor) {
        this.tintColor = tintColor;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if ((event.getAction() == MotionEvent.ACTION_UP) && (null != drawableEnd)) {
            Rect bounds = drawableEnd.getBounds();
            int x = (int) event.getX();
            // take into account the padding and additionalTouchTargetSize
            int drawableWidthWithPadding = (bounds.width() + (leftToRight ? getPaddingRight() : getPaddingLeft()) + additionalTouchTargetSize);
            // check if the touch is within bounds of drawableEnd icon
            boolean flag = (leftToRight && (x >= (this.getRight() - drawableWidthWithPadding))) || (!leftToRight && (x <= (this.getLeft() + drawableWidthWithPadding)));
            if (flag) {
                togglePasswordVisibility();
                // use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    private void showPasswordVisibilityIndicator(boolean show) {
        LogUtils.d(TAG, "showPasswordVisibilityIndicator() called with: " + "show = [" + show + "]");
        // preserve and existing CompoundDrawables
        Drawable[] existingDrawables = getCompoundDrawables();
        Drawable left = existingDrawables[0];
        Drawable top = existingDrawables[1];
        Drawable right = existingDrawables[2];
        Drawable bottom = existingDrawables[3];
        if (show) {
            Drawable original = (areShowingPassword ? ContextCompat.getDrawable(getContext(), visibilityIndicatorHide) : ContextCompat.getDrawable(getContext(), visibilityIndicatorShow));
            assert original != null;
            original.mutate();
            if (tintColor == 0) {
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : original, top, leftToRight ? original : right, bottom);
            } else {
                Drawable wrapper = DrawableCompat.wrap(original);
                DrawableCompat.setTint(wrapper, tintColor);
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : wrapper, top, leftToRight ? wrapper : right, bottom);
            }
        } else {
            setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : null, top, leftToRight ? null : right, bottom);
        }
    }

    /**
     * make it visible
     */
    private void unmaskPassword() {
        setTransformationMethod(null);
    }

    /**
     * hide it
     */
    private void maskPassword() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void togglePasswordVisibility() {
        // store the selection
        int selectionStart = this.getSelectionStart();
        int selectionEnd = this.getSelectionEnd();
        // set transformation method to show / hide password
        if (areShowingPassword) {
            maskPassword();
        } else {
            unmaskPassword();
        }
        // restore selection
        this.setSelection(selectionStart, selectionEnd);
        // toggle flag and show indicator
        areShowingPassword = !areShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableEnd = null;
        super.finalize();
    }

    public
    @DrawableRes
    int getVisibilityIndicatorShow() {
        return visibilityIndicatorShow;
    }

    public void setVisibilityIndicatorShow(@DrawableRes int visibilityIndicatorShow) {
        this.visibilityIndicatorShow = visibilityIndicatorShow;
    }

    @DrawableRes
    public int getVisibilityIndicatorHide() {
        return visibilityIndicatorHide;
    }

    public void setVisibilityIndicatorHide(@DrawableRes int visibilityIndicatorHide) {
        this.visibilityIndicatorHide = visibilityIndicatorHide;
    }

    /**
     * 正显密码否
     *
     * @return true if the password is visible | false if hidden
     */
    public boolean areAreShowingPassword() {
        return areShowingPassword;
    }

    public int getAdditionalTouchTargetSizePixels() {
        return additionalTouchTargetSize;
    }

    /**
     * setAdditionalTouchTargetSizePixels
     *
     * @param additionalTouchTargetSize inPixels
     */
    public void setAdditionalTouchTargetSizePixels(int additionalTouchTargetSize) {
        this.additionalTouchTargetSize = additionalTouchTargetSize;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState());
        bundle.putBoolean(ARE_SHOWING_PASSWORD_STATE_KEY, this.areShowingPassword);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.areShowingPassword = bundle.getBoolean(ARE_SHOWING_PASSWORD_STATE_KEY, false);
            if (areShowingPassword) {
                unmaskPassword();
            }
            state = bundle.getParcelable(SUPER_STATE_KEY);
        }
        super.onRestoreInstanceState(state);
    }
}