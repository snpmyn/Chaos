package widget.banner.kit;

import com.chaos.banner.kit.BannerKit;
import com.example.chaos.R;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/9/30
 *
 * @author zsp
 * @desc 轮播一页配套元件
 */
public class BannerOneActivityKit {
    /**
     * 轮播
     *
     * @param homePageChildFragmentBanner 轮播图
     */
    public void banner(Banner homePageChildFragmentBanner) {
        BannerKit bannerKit = new BannerKit();
        List<Integer> integerList = new ArrayList<>(3);
        integerList.add(R.drawable.icon_background);
        integerList.add(R.drawable.icon_background);
        integerList.add(R.drawable.icon_background);
        bannerKit.banner(homePageChildFragmentBanner, integerList, Transformer.DepthPage, 3000);
        homePageChildFragmentBanner.startAutoPlay();
    }
}
