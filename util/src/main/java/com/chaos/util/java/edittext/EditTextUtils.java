package com.chaos.util.java.edittext;

import android.view.View;
import android.widget.EditText;

/**
 * Created on 2021/8/27
 *
 * @author zsp
 * @desc EditText 工具类
 */
public class EditTextUtils {
    /**
     * 清 EditText 焦点
     *
     * @param view 焦点所在 View
     * @param ids  输入框
     */
    public static void clearViewFocus(View view, int... ids) {
        if ((null != view) && (null != ids) && (ids.length > 0)) {
            for (int id : ids) {
                if (view.getId() == id) {
                    view.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐键盘
     *
     * @param view 焦点所在 View
     * @param ids  输入框
     * @return true 表焦点在 EditText
     */
    public static boolean isFocusEditText(View view, int... ids) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            for (int id : ids) {
                if (editText.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }
}
