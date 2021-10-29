package com.chaos.widget.choose.pickerview.util;

import android.view.Gravity;

import com.chaos.widget.R;

/**
 * @decs: PickerViewAnimateUtils
 * @author: 郑少鹏
 * @date: 2018/4/3 17:31
 */
public class PickerViewAnimateUtils {
    private static final int INVALID = -1;

    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity        the animGravity of the widget.dialog
     * @param areInAnimation determine if is in or out animation. true when is
     * @return the id of the animation resource
     */
    public static int getAnimationResource(int gravity, boolean areInAnimation) {
        if (gravity == Gravity.BOTTOM) {
            return (areInAnimation ? R.anim.picker_view_slide_in_from_bottom_to_top : R.anim.picker_view_slide_out_from_top_to_bottom);
        }
        return INVALID;
    }
}
