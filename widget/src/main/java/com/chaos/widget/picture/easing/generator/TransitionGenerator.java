package com.chaos.widget.picture.easing.generator;

import android.graphics.RectF;

import com.chaos.widget.picture.easing.transition.Transition;

/**
 * @decs: 过渡发电机
 * @author: 郑少鹏
 * @date: 2019/10/26 16:44
 */
public interface TransitionGenerator {
    /**
     * Generates the next transition to be played by the {@link com.chaos.widget.picture.easing.view.EasingView}.
     *
     * @param drawableBounds The bounds of the drawable to be shown in the {@link com.chaos.widget.picture.easing.view.EasingView}.
     * @param viewport       The rect that represents the viewport where the transition will be played in.
     *                       This is usually the bounds of the {@link com.chaos.widget.picture.easing.view.EasingView}.
     * @return A {@link Transition} object to be played by the {@link com.chaos.widget.picture.easing.view.EasingView}.
     */
    Transition generateNextTransition(RectF drawableBounds, RectF viewport);
}
