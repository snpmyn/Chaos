package com.chaos.util.java.statusbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chaos.util.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import timber.log.Timber;
import value.UtilMagic;

/**
 * Created on 2017/7/20.
 *
 * @author 郑少鹏
 * @desc StatusBarUtils
 */
public class StatusBarUtils {
    private static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_STATUS_BAR_VIEW_ID = 1000;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = 1001;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;
    private static final Set<String> NO_NAVIGATION_BAR_MODEL_SET = new HashSet<>();

    static {
        NO_NAVIGATION_BAR_MODEL_SET.add("Nexus 4");
        NO_NAVIGATION_BAR_MODEL_SET.add("H60-L01");
        NO_NAVIGATION_BAR_MODEL_SET.add("P7-L07");
        NO_NAVIGATION_BAR_MODEL_SET.add("MT7-UL00");
    }

    /**
     * 状态栏色
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 状态栏色
     *
     * @param activity       需设 Activity
     * @param color          状态栏色值
     * @param statusBarAlpha 状态栏透明度
     */
    private static void setColor(@NotNull Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
    }

    /**
     * 滑返界面状态栏色
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     */
    public static void setColorForSwipeBack(Activity activity, int color) {
        setColorForSwipeBackCustom(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 滑返界面状态栏色
     *
     * @param activity       需设 Activity
     * @param color          状态栏色值
     * @param statusBarAlpha 状态栏透明度
     */
    private static void setColorForSwipeBackCustom(@NotNull Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        View rootView = contentView.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);
        if (rootView instanceof CoordinatorLayout) {
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
            coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        } else {
            contentView.setPadding(0, statusBarHeight, 0, 0);
            contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        }
        setTransparentForWindow(activity);
    }

    /**
     * 状态栏纯色 无半透明效果
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     */
    public static void setColorNoTranslucent(Activity activity, @ColorInt int color) {
        setColor(activity, color, 0);
    }

    /**
     * 状态栏色（5.0 下无半透明效果，不建议用）
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     */
    @Deprecated
    public static void setColorDiff(Activity activity, @ColorInt int color) {
        transparentStatusBar(activity);
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        // 移半透明矩形（避叠加）
        View fakeStatusBarView = contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null != fakeStatusBarView) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            contentView.addView(createStatusBarView(activity, color));
        }
        setRootView(activity);
    }

    /**
     * 状态栏半透明
     * 适用图作背景界面，此时需图填充至状态栏
     *
     * @param activity 需设 Activity
     */
    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 状态栏半透明
     * 适用图作背景界面，此时需图填充至状态栏
     *
     * @param activity       需设 Activity
     * @param statusBarAlpha 状态栏透明度
     */
    private static void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        setTransparent(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 针对根布局为 CoordinatorLayout, 状态栏半透明
     * 适用图作背景界面，此时需图填充至状态栏
     *
     * @param activity       需设 Activity
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        transparentStatusBar(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 状态栏全透明
     *
     * @param activity 需设 Activity
     */
    private static void setTransparent(Activity activity) {
        transparentStatusBar(activity);
        setRootView(activity);
    }

    /**
     * 状态栏透明（5.0+ 半透明效果，不建议用）
     * 适用图作背景界面，此时需图填充至状态栏
     *
     * @param activity 需设 Activity
     */
    @Deprecated
    public static void setTranslucentDiff(@NotNull Activity activity) {
        // 状态栏透明
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setRootView(activity);
    }

    /**
     * DrawerLayout 状态栏色
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏色值
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * DrawerLayout 状态栏色 纯色
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏色值
     */
    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    /**
     * DrawerLayout 布局状态栏色
     *
     * @param activity       需设 Activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏色值
     * @param statusBarAlpha 状态栏透明度
     */
    private static void setColorForDrawerLayout(@NotNull Activity activity, @NotNull DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        // 生一状态栏大小矩形
        // 添 StatusBarView 到布局
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null != fakeStatusBarView) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            contentLayout.addView(createStatusBarView(activity, color), 0);
        }
        // 内容布局非 LinearLayout 时设 PaddingTop
        if (!(contentLayout instanceof LinearLayout) && (null != contentLayout.getChildAt(1))) {
            contentLayout.getChildAt(1).setPadding(contentLayout.getPaddingLeft(),
                    getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                    contentLayout.getPaddingRight(),
                    contentLayout.getPaddingBottom());
        }
        // 设属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 内容布局
     */
    private static void setDrawerLayoutProperty(@NotNull DrawerLayout drawerLayout, @NotNull ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }

    /**
     * DrawerLayout 状态栏颜色（5.0 下无半透明效果，不建议用）
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏色值
     */
    @Deprecated
    public static void setColorForDrawerLayoutDiff(@NotNull Activity activity, @NotNull DrawerLayout drawerLayout, @ColorInt int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 生一状态栏大小矩形
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null != fakeStatusBarView) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA));
        } else {
            // 添 StatusBarView 到布局
            contentLayout.addView(createStatusBarView(activity, color), 0);
        }
        // 内容布局非 LinearLayout 时设 PaddingTop
        if (!(contentLayout instanceof LinearLayout) && (null != contentLayout.getChildAt(1))) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        // 属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }

    /**
     * DrawerLayout 状态栏透明
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        setTranslucentForDrawerLayoutCustom(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * DrawerLayout 状态栏透明
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     */
    private static void setTranslucentForDrawerLayoutCustom(Activity activity, DrawerLayout drawerLayout, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        setTransparentForDrawerLayout(activity, drawerLayout);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * DrawerLayout 状态栏透明
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     */
    private static void setTransparentForDrawerLayout(@NotNull Activity activity, @NotNull DrawerLayout drawerLayout) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        // 内容布局非 LinearLayout 时设 PaddingTop
        if (!(contentLayout instanceof LinearLayout) && (null != contentLayout.getChildAt(1))) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        // 属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }

    /**
     * DrawerLayout 状态栏透明（5.0+ 半透明效果，不建议用）
     *
     * @param activity     需设 Activity
     * @param drawerLayout DrawerLayout
     */
    @Deprecated
    public static void setTranslucentForDrawerLayoutDiff(@NotNull Activity activity, @NotNull DrawerLayout drawerLayout) {
        // 状态栏透明
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 内容布局属性
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        contentLayout.setFitsSystemWindows(true);
        contentLayout.setClipToPadding(true);
        // 抽屉布局属性
        ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
        vg.setFitsSystemWindows(false);
        // DrawerLayout 属性
        drawerLayout.setFitsSystemWindows(false);
    }

    /**
     * 头部 ImageView 界面状态栏全透明
     *
     * @param activity       需设 Activity
     * @param needOffsetView 需向下偏移 View
     */
    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, 0, needOffsetView);
    }

    /**
     * 头部 ImageView 界面状态栏透明（用默透明度）
     *
     * @param activity       需设 Activity
     * @param needOffsetView 需向下偏移 View
     */
    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * 头部 ImageView 界面状态栏透明
     *
     * @param activity       需设 Activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需向下偏移 View
     */
    private static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView) {
        setTransparentForWindow(activity);
        addTranslucentView(activity, statusBarAlpha);
        if (null != needOffsetView) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if ((null != haveSetOffset) && (Boolean) haveSetOffset) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity), layoutParams.rightMargin, layoutParams.bottomMargin);
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
        }
    }

    /**
     * Fragment 头部 ImageView 界面状态栏透明
     *
     * @param activity       Fragment 对应 Activity
     * @param needOffsetView 需向下偏移 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * Fragment 头部 ImageView 界面状态栏透明
     *
     * @param activity       Fragment 对应 Activity
     * @param needOffsetView 需向下偏移 View
     */
    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
    }

    /**
     * Fragment 头部 ImageView 界面状态栏透明
     *
     * @param activity       Fragment 对应 Activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需向下偏移 View
     */
    private static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用 Activity
     */
    public static void hideFakeStatusBarView(@NotNull Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null != fakeStatusBarView) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
        View fakeTranslucentView = decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (null != fakeTranslucentView) {
            fakeTranslucentView.setVisibility(View.GONE);
        }
    }

    private static void clearPreviousSetting(@NotNull Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (null != fakeStatusBarView) {
            decorView.removeView(fakeStatusBarView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 添半透明矩形条
     *
     * @param activity       需设 Activity
     * @param statusBarAlpha 透明值
     */
    private static void addTranslucentView(@NotNull Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (null != fakeTranslucentView) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }

    /**
     * 生一同状态栏大小彩色矩形条
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     * @return 状态栏矩形条
     */
    private static @NotNull View createStatusBarView(Activity activity, @ColorInt int color) {
        return createStatusBarView(activity, color, 0);
    }

    /**
     * 生一同状态栏大小半透明矩形条
     *
     * @param activity 需设 Activity
     * @param color    状态栏色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static @NotNull View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        // 绘一状态栏等高矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }

    /**
     * 根布局参数
     */
    private static void setRootView(@NotNull Activity activity) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 透明
     */
    private static void setTransparentForWindow(@NotNull Activity activity) {
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 状态栏透明
     */
    private static void transparentStatusBar(@NotNull Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private static @NotNull View createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘一状态栏等高矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;
    }

    /**
     * 获状态栏高
     *
     * @param context 上下文
     * @return 态栏高
     */
    private static int getStatusBarHeight(@NotNull Context context) {
        int result = 0;
        // 获状态栏高
        int resourceId = context.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 算状态栏色
     *
     * @param color color 值
     * @param alpha alpha 值
     * @return 最终状态栏色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = (1 - alpha / 255f);
        int red = (color >> 16 & 0xff);
        int green = (color >> 8 & 0xff);
        int blue = (color & 0xff);
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return (0xff << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * 改 MIUI 内置状态栏字体色模式（Dark、Light 两模式）
     *
     * @param activity 活动
     * @param mode     模式
     */
    private static void setMiUiStatusBarDarkMode(@NotNull Activity activity, boolean mode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), mode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * 改魅族状态栏字体色模式
     *
     * @param activity 活动
     * @param dark     深色否
     */
    private static void setMeiZuStatusBarDarkIcon(Activity activity, boolean dark) {
        if (null != activity) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meiZuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meiZuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meiZuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meiZuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
            } catch (Exception e) {
                Timber.e(e);
            }
        }
    }

    /**
     * 状态栏字体色暗
     *
     * @param activity 活动
     * @param isDark   暗否
     */
    private static void statusBarTextColorDark(Activity activity, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (isDark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        } else {
            setMiUiStatusBarDarkMode(activity, isDark);
            setMeiZuStatusBarDarkIcon(activity, isDark);
        }
    }

    /**
     * 状态栏亮
     *
     * @param activity 活动
     */
    public static void statusBarLight(Activity activity) {
        StatusBarUtils.setColorNoTranslucent(activity, ContextCompat.getColor(activity, R.color.pageBackground));
        StatusBarUtils.statusBarTextColorDark(activity, true);
    }

    /**
     * 状态栏暗
     *
     * @param activity 活动
     */
    public static void statusBarDark(Activity activity) {
        StatusBarUtils.setColorNoTranslucent(activity, ContextCompat.getColor(activity, R.color.purple_500));
        StatusBarUtils.statusBarTextColorDark(activity, false);
    }

    /**
     * 底部导航栏高
     *
     * @param activity 活动
     * @return 底部导航栏高
     */
    public static int getNavigationBarHeight(@NotNull Activity activity) {
        int navigationBarHeight = 0;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if ((resourceId > 0) && checkDeviceHasNavigationBar(activity)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 检测有无底部导航栏
     *
     * @param activity 活动
     * @return 有底部导航栏否
     */
    private static boolean checkDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar;
        if (NO_NAVIGATION_BAR_MODEL_SET.contains(Build.MODEL)) {
            hasNavigationBar = false;
        } else {
            hasNavigationBar = newCheckDeviceHasNavigationBar(activity);
        }
        return hasNavigationBar;
    }

    private static boolean oldCheckDeviceHasNavigationBar(@NotNull Activity activity) {
        boolean hasNavigationBar = false;
        Resources resources = activity.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            @SuppressLint("PrivateApi") Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method method = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) method.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if (UtilMagic.STRING_L.equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if (UtilMagic.STRING_Y.equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return hasNavigationBar;
    }

    private static boolean newCheckDeviceHasNavigationBar(@NotNull Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return ((realWidth - displayWidth) > 0) || ((realHeight - displayHeight) > 0);
    }
}