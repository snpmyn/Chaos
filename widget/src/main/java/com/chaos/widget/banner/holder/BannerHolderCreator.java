package com.chaos.widget.banner.holder;

/**
 * @desc: 轮播持有者创建器
 * @author: zsp
 * @date: 2021/10/15 5:11 下午
 */
public interface BannerHolderCreator<Holder> {
    /**
     * 创持有者
     *
     * @return 持有者
     */
    Holder createHolder();
}
