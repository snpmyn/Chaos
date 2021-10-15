package widget.banner.kit;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.kotlin.widget.banner.IBannerView;
import com.chaos.kotlin.widget.banner.view.BannerView;
import com.chaos.util.java.density.DensityUtils;
import com.chaos.util.java.glide.transformation.CenterCropRoundCornerTransform;
import com.chaos.util.java.glide.util.GlideUtils;
import com.example.chaos.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created on 2021/9/30
 *
 * @author zsp
 * @desc 轮播二页配套元件
 */
public class BannerTwoActivityKit {
    public List<Integer> integerList;

    /**
     * constructor
     */
    public BannerTwoActivityKit() {
        this.integerList = new ArrayList<>(3);
    }

    /**
     * 初始轮播数据
     */
    public void stepBannerData() {
        integerList.add(R.mipmap.icon);
        integerList.add(R.mipmap.icon);
        integerList.add(R.mipmap.icon);
    }

    /**
     * 间隔轮播
     *
     * @param bannerView BannerView
     */
    public void intervalBanner(@NonNull BannerView bannerView) {
        bannerView.setBannerViewImpl(new IBannerView() {
            @Override
            public void onPageSelected(int position) {
                Timber.d("选 %s", position);
            }

            @Override
            public boolean isDefaultAutoScroll() {
                return true;
            }

            @Override
            public View getDefaultView(@NotNull Context context) {
                View view = new View(context);
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                return view;
            }

            @Override
            public int getCount() {
                return integerList.size();
            }

            @NotNull
            @Override
            public View getItemView(@NotNull Context context) {
                return new ImageView(context);
            }

            @Override
            public void onBindView(@NotNull View itemView, int position) {
                if (itemView instanceof ImageView) {
                    ImageView imageView = (ImageView) itemView;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    GlideUtils.loadByObject(itemView.getContext(), integerList.get(position), imageView);
                    imageView.setOnClickListener(view -> Timber.d("onBindView onClick %s", position));
                }
            }
        });
    }

    /**
     * 平滑轮播
     *
     * @param bannerView BannerView
     */
    public void smoothBanner(@NonNull BannerView bannerView) {
        bannerView.setBannerViewImpl(new IBannerView() {
            @Override
            public void onPageSelected(int position) {
                Timber.d("选 %s", position);
            }

            @Override
            public boolean isDefaultAutoScroll() {
                return true;
            }

            @Override
            public View getDefaultView(@NotNull Context context) {
                View view = new View(context);
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                return view;
            }

            @Override
            public int getCount() {
                return integerList.size();
            }

            @NotNull
            @Override
            public View getItemView(@NotNull Context context) {
                return new ImageView(context);
            }

            @Override
            public void onBindView(@NotNull View itemView, int position) {
                if (itemView instanceof ImageView) {
                    ImageView imageView = (ImageView) itemView;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    GlideUtils.loadByObjectTransformation(
                            itemView.getContext(), integerList.get(position),
                            new CenterCropRoundCornerTransform(DensityUtils.dipToPxByFloat(imageView.getContext(), 8.0F)), imageView);
                    imageView.setOnClickListener(view -> Timber.d("onBindView onClick %s", position));
                }
            }
        });
    }
}
