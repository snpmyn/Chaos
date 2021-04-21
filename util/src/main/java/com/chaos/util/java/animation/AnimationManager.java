package com.chaos.util.java.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2017/11/15.
 *
 * @author 郑少鹏
 * @desc 动画管理器
 */
public class AnimationManager {
    /**
     * 补间晃动
     *
     * @param cycleTimes        循环次数
     * @param duration          时长
     * @param animationListener 动画监听
     * @return Animation
     */
    public static @NotNull Animation shake(float cycleTimes, long duration, Animation.AnimationListener animationListener) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 10.0f);
        translateAnimation.setInterpolator(new CycleInterpolator(cycleTimes));
        translateAnimation.setDuration(duration);
        if (null != animationListener) {
            translateAnimation.setAnimationListener(animationListener);
        }
        return translateAnimation;
    }

    /**
     * 属性 XY 缩放（1-0）
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void xyScaleGo(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        if (null != animatorListener) {
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    /**
     * 属性 XY 缩放（0-1）
     *
     * @param view              视图
     * @param duration          时长
     * @param animationListener 动画监听
     */
    public static void xyScaleShow(View view, long duration, Animator.AnimatorListener animationListener) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        if (null != animationListener) {
            animatorSet.addListener(animationListener);
        }
        animatorSet.start();
    }

    /**
     * 属性 XY 缩放（1-0-1）
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void xyScale(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        if (null != animatorListener) {
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    /**
     * 属性 X 渐变
     *
     * @param view             视图
     * @param start            开始
     * @param end              终止
     * @param duration         时长
     * @param interpolator     插值器
     * @param animatorListener 动画监听
     */
    public static void xGradual(final @NotNull View view, int start, int end, long duration, int interpolator, Animator.AnimatorListener animatorListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.width = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        if (null != animatorListener) {
            valueAnimator.addListener(animatorListener);
        }
        valueAnimator.start();
    }

    /**
     * 属性 Y 渐变
     *
     * @param view             视图
     * @param start            开始
     * @param end              终止
     * @param duration         时长
     * @param interpolator     插值器
     * @param animatorListener 动画监听
     */
    public static void yGradual(final @NotNull View view, int start, int end, long duration, int interpolator, Animator.AnimatorListener animatorListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        if (null != animatorListener) {
            valueAnimator.addListener(animatorListener);
        }
        valueAnimator.start();
    }

    /**
     * 属性 Y 渐变
     *
     * @param view             视图
     * @param start            开始
     * @param end              终止
     * @param duration         时长
     * @param interpolator     插值器
     * @param animatorListener 动画监听
     * @return ValueAnimator
     */
    public static @NotNull ValueAnimator yGradualReturn(final @NotNull View view, int start, int end, long duration, int interpolator, Animator.AnimatorListener animatorListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        if (null != animatorListener) {
            valueAnimator.addListener(animatorListener);
        }
        return valueAnimator;
    }

    /**
     * 属性 Y 位移
     *
     * @param view             视图
     * @param yTranslationEnd  trEnd
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void yTranslation(@NotNull View view, float yTranslationEnd, long duration, Animator.AnimatorListener animatorListener) {
        float yCurrentTranslation = view.getTranslationY();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", yCurrentTranslation, yTranslationEnd);
        objectAnimator.setDuration(duration);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }

    /**
     * 属性透变（1-0）
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void alphaGone(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        objectAnimator.setDuration(duration);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }

    /**
     * 属性透变（0-1）
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void alphaShow(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        objectAnimator.setDuration(duration);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }

    /**
     * 属性循环透变
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     * @return ObjectAnimator
     */
    public static @NotNull ObjectAnimator alphaChangeCircle(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.1f);
        // 播放次数（infinite 无限重复）
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 播放模式
        // ValueAnimator.RESTART (默) 正序重放
        // ValueAnimator.REVERSE 倒序回放
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setDuration(duration);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        return objectAnimator;
    }

    /**
     * 属性 XY 缩透（1-0）
     *
     * @param view             视图
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void xyScaleAlphaGone(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        if (null != animatorListener) {
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    /**
     * 属性 XY 缩透（0-1）
     *
     * @param view             视图
     * @param duration         duration
     * @param animatorListener 动画监听
     */
    public static void xyScaleAlphaShow(View view, long duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setDuration(duration);
        if (null != animatorListener) {
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    /**
     * 属性色变
     *
     * @param view             视图
     * @param startColor       开始色
     * @param endColor         终止色
     * @param duration         时长
     * @param animatorListener 动画监听
     */
    public static void colorGradual(final View view, int startColor, int endColor, int duration, Animator.AnimatorListener animatorListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        valueAnimator.addUpdateListener(animation -> {
            int color = (int) animation.getAnimatedValue();
            // 取两图层全域（交集色加深）
            view.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
        });
        valueAnimator.setDuration(duration);
        if (null != animatorListener) {
            valueAnimator.addListener(animatorListener);
        }
        valueAnimator.start();
    }

    /**
     * 属性旋转
     *
     * @param view             视图
     * @param duration         时长
     * @param start            开始
     * @param end              终止
     * @param animatorListener 动画监听
     */
    public static void rotation(View view, int duration, Float start, Float end, Animator.AnimatorListener animatorListener) {
        // 负逆正顺（0.0f 到 360.0f）
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        objectAnimator.setDuration(duration);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }
}
