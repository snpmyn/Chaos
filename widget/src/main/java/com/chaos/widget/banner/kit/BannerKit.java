package com.chaos.widget.banner.kit;

import androidx.annotation.NonNull;

import com.chaos.util.java.list.ListUtils;
import com.chaos.widget.R;
import com.chaos.widget.banner.engine.BannerEngine;
import com.chaos.widget.banner.holder.BannerHolderCreator;
import com.chaos.widget.banner.listener.OnItemClickListener;
import com.chaos.widget.banner.view.ConvenientBanner;

import java.util.List;

/**
 * Created on 2021/10/18
 *
 * @author zsp
 * @desc 轮播配套元件
 */
public class BannerKit {
    /**
     * 轮播
     *
     * @param convenientBanner    ConvenientBanner<Object>
     * @param objectList          数据
     * @param autoTurningTime     自翻时
     * @param scrollDuration      滑时
     * @param indicatorVisible    指示器可见
     * @param onItemClickListener 条目点击监听
     */
    public void banner(@NonNull ConvenientBanner<Object> convenientBanner, List<Object> objectList, long autoTurningTime, int scrollDuration, boolean indicatorVisible, OnItemClickListener onItemClickListener) {
        convenientBanner.setPages((BannerHolderCreator<BannerEngine>) BannerEngine::new, objectList)
                .startTurning(autoTurningTime)
                .setScrollDuration(scrollDuration)
                .setPointViewVisible(indicatorVisible)
                .setCanLoop(ListUtils.listIsNotEmpty(objectList))
                .setPageIndicator(new int[]{R.drawable.gray_dot, R.drawable.purple_500_dot})
                .setOnItemClickListener(onItemClickListener);
    }
}
