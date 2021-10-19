package widget.banner.kit;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.banner.kit.BannerKit;
import com.chaos.widget.banner.view.ConvenientBanner;
import com.example.chaos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/10/18
 *
 * @author zsp
 * @desc 轮播三页配套元件
 */
public class BannerThreeActivityKit {
    /**
     * 轮播
     *
     * @param convenientBanner ConvenientBanner<Object>
     * @param autoTurningTime  自翻时
     * @param scrollDuration   滑时
     */
    public void banner(ConvenientBanner<Object> convenientBanner, long autoTurningTime, int scrollDuration) {
        List<Object> objectList = new ArrayList<>(3);
        objectList.add(R.mipmap.icon);
        objectList.add(R.mipmap.icon);
        objectList.add(R.mipmap.icon);
        BannerKit bannerKit = new BannerKit();
        bannerKit.banner(convenientBanner, objectList, autoTurningTime, scrollDuration, true, position -> ToastKit.showShort(String.valueOf(position)));
    }
}
