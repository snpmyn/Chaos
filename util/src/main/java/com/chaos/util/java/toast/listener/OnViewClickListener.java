package com.chaos.util.java.toast.listener;

import java.util.Map;

/**
 * Created on 2020-09-03
 *
 * @author zsp
 * @desc 视图点击监听
 */
public interface OnViewClickListener {
    /**
     * 视图点击
     *
     * @param extraMap 额外参数
     */
    void onViewClick(Map<String, String> extraMap);
}
