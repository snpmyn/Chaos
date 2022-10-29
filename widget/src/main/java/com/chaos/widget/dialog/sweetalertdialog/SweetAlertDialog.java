package com.chaos.widget.dialog.sweetalertdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

/**
 * Created on 2017/11/3.
 *
 * @author xxx
 * @desc SweetAlertDialog
 */
public class SweetAlertDialog extends Dialog implements View.OnClickListener {
    private static final int NORMAL_TYPE = 0;
    private static final int ERROR_TYPE = 1;
    private static final int SUCCESS_TYPE = 2;
    private static final int WARNING_TYPE = 3;
    private static final int CUSTOM_IMAGE_TYPE = 4;
    private static final int PROGRESS_TYPE = 5;
    private final AnimationSet mModalInAnim;
    private final AnimationSet mModalOutAnim;
    private final Animation mOverlayOutAnim;
    private final Animation mErrorInAnim;
    private final AnimationSet mErrorXInAnim;
    private final AnimationSet mSuccessLayoutAnimSet;
    private final Animation mSuccessBowAnim;
    private final ProgressHelper mProgressHelper;
    private View mDialogView;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowContent;
    private String mCancelText;
    private String mConfirmText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;
    private SuccessTickView mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private MaterialButton mConfirmMaterialButton;
    private MaterialButton mCancelMaterialButton;
    private FrameLayout mWarningFrame;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private boolean mCloseFromCancel;
    /**
     * 接口
     */
    private DialogValue value;

    public SweetAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    public SweetAlertDialog(Context context, int alertType) {
        super(context, R.style.SweetAlertDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_sign_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(() -> {
                    if (mCloseFromCancel) {
                        SweetAlertDialog.super.cancel();
                    } else {
                        SweetAlertDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // widget.dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
                layoutParams.alpha = (1 - interpolatedTime);
                getWindow().setAttributes(layoutParams);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        mDialogView = Objects.requireNonNull(getWindow()).getDecorView().findViewById(android.R.id.content);
        mTitleTextView = findViewById(R.id.titleText);
        mContentTextView = findViewById(R.id.contentText);
        mErrorFrame = findViewById(R.id.errorFrame);
        mErrorX = mErrorFrame.findViewById(R.id.errorX);
        mSuccessFrame = findViewById(R.id.successFrame);
        mProgressFrame = findViewById(R.id.progressDialog);
        mSuccessTick = mSuccessFrame.findViewById(R.id.successTick);
        mSuccessLeftMask = mSuccessFrame.findViewById(R.id.maskLeft);
        mSuccessRightMask = mSuccessFrame.findViewById(R.id.maskRight);
        mCustomImage = findViewById(R.id.customImage);
        mWarningFrame = findViewById(R.id.warningFrame);
        mConfirmMaterialButton = findViewById(R.id.mbConfirm);
        mCancelMaterialButton = findViewById(R.id.mbCancel);
        mProgressHelper.setProgressWheel(findViewById(R.id.progressWheel));
        mConfirmMaterialButton.setOnClickListener(this);
        mCancelMaterialButton.setOnClickListener(this);
        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        changeAlertType(mAlertType, true);
    }

    private void restore() {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);
        mConfirmMaterialButton.setVisibility(View.VISIBLE);
        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
    }

    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startTickAnim();
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (null != mDialogView) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case WARNING_TYPE:
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmMaterialButton.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public int getAlertType() {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }

    public String getTitleText() {
        return mTitleText;
    }

    public SweetAlertDialog setTitleText(String text) {
        mTitleText = text;
        if ((null != mTitleTextView) && (null != mTitleText)) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if ((null != mCustomImage) && (null != mCustomImgDrawable)) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(int resourceId) {
        return setCustomImage(ContextCompat.getDrawable(getContext(), resourceId));
    }

    public String getContentText() {
        return mContentText;
    }

    public SweetAlertDialog setContentText(String text) {
        mContentText = text;
        if ((null != mContentTextView) && (null != mContentText)) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }

    public SweetAlertDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (null != mCancelMaterialButton) {
            mCancelMaterialButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText() {
        return mShowContent;
    }

    public SweetAlertDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (null != mContentTextView) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public SweetAlertDialog setCancelText(String text) {
        mCancelText = text;
        if ((null != mCancelMaterialButton) && (null != mCancelText)) {
            showCancelButton(true);
            mCancelMaterialButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public SweetAlertDialog setConfirmText(String text) {
        mConfirmText = text;
        if ((null != mConfirmMaterialButton) && (null != mConfirmText)) {
            mConfirmMaterialButton.setText(mConfirmText);
        }
        return this;
    }

    public SweetAlertDialog setCancelClickListener(OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public SweetAlertDialog setConfirmClickListener(OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    @Override
    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    private void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmMaterialButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
        if (null != value) {
            value.dialogDismiss(1);
        }
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.mbCancel) {
            if (null != mCancelClickListener) {
                mCancelClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.mbConfirm) {
            if (null != mConfirmClickListener) {
                mConfirmClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper() {
        return mProgressHelper;
    }

    /**
     * Init interface.
     *
     * @param v 视图
     */
    public void setListener(DialogValue v) {
        value = v;
    }

    public interface OnSweetClickListener {
        /**
         * 点击
         *
         * @param sweetAlertDialog sweetAlertDialog
         */
        void onClick(SweetAlertDialog sweetAlertDialog);
    }
}