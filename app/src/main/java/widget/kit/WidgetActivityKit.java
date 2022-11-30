package widget.kit;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.adapttemplate.bean.MenuBean;
import com.chaos.widget.adapttemplate.kit.MenuAdapterKit;
import com.chaos.widget.transition.kit.TransitionKit;
import com.example.chaos.R;

import java.util.ArrayList;
import java.util.List;

import widget.banner.BannerHomeActivity;
import widget.bean.WidgetMenuEnum;
import widget.choose.ChooseActivity;
import widget.dialog.DialogActivity;
import widget.grid.GridActivity;
import widget.lottie.LottieHomeActivity;
import widget.module.one.ModuleOneActivity;
import widget.module.textview.TextViewHomeActivity;
import widget.money.MoneyActivity;
import widget.picture.PictureHomeActivity;
import widget.property.PropertyActivity;
import widget.pudding.PuddingActivity;
import widget.scan.ScanActivity;
import widget.screen.ScreenActivity;
import widget.search.SearchActivity;
import widget.status.StatusActivity;

/**
 * Created on 2022/5/26
 *
 * @author zsp
 * @desc 组件页配套元件
 */
public class WidgetActivityKit {
    /**
     * 展示
     *
     * @param appCompatActivity 活动
     * @param recyclerView      控件
     */
    public void display(AppCompatActivity appCompatActivity, RecyclerView recyclerView) {
        // 数据
        WidgetMenuEnum[] widgetMenuEnums = WidgetMenuEnum.values();
        List<MenuBean> moduleBeanList = new ArrayList<>(widgetMenuEnums.length);
        for (WidgetMenuEnum widgetMenuEnum : widgetMenuEnums) {
            if (widgetMenuEnum.getMenuShow()) {
                moduleBeanList.add(new MenuBean(widgetMenuEnum.getMenuId(), widgetMenuEnum.getMenuIconResId(), widgetMenuEnum.getMenuName()));
            }
        }
        // 菜单适配器配套元件
        MenuAdapterKit menuAdapterKit = new MenuAdapterKit();
        menuAdapterKit.display(appCompatActivity, recyclerView, moduleBeanList, 3, 48, 192, (view, menuBean) -> distribute(appCompatActivity, view, menuBean.getMenuId()));
    }

    /**
     * 分发
     *
     * @param appCompatActivity 活动
     * @param view              视图
     * @param menuId            菜单 ID
     */
    private void distribute(AppCompatActivity appCompatActivity, View view, int menuId) {
        switch (menuId) {
            // 对话框页
            case 1:
                jumpWithTransition(appCompatActivity, view, DialogActivity.class);
                break;
            // 金额页
            case 2:
                jumpWithTransition(appCompatActivity, view, MoneyActivity.class);
                break;
            // 选择页
            case 3:
                jumpWithTransition(appCompatActivity, view, ChooseActivity.class);
                break;
            // 搜索页
            case 4:
                jumpWithTransition(appCompatActivity, view, SearchActivity.class);
                break;
            // 图片主页
            case 5:
                jumpWithTransition(appCompatActivity, view, PictureHomeActivity.class);
                break;
            // 扫描页
            case 6:
                jumpWithTransition(appCompatActivity, view, ScanActivity.class);
                break;
            // Lottie 主页
            case 7:
                jumpWithTransition(appCompatActivity, view, LottieHomeActivity.class);
                break;
            // Pudding 页
            case 8:
                jumpWithTransition(appCompatActivity, view, PuddingActivity.class);
                break;
            // 网格页
            case 9:
                jumpWithTransition(appCompatActivity, view, GridActivity.class);
                break;
            // 属性页
            case 10:
                jumpWithTransition(appCompatActivity, view, PropertyActivity.class);
                break;
            // 轮播主页
            case 11:
                jumpWithTransition(appCompatActivity, view, BannerHomeActivity.class);
                break;
            //  模块一页
            case 12:
                jumpWithTransition(appCompatActivity, view, ModuleOneActivity.class);
                break;
            //  模块二页
            case 13:
                ToastKit.showShort(appCompatActivity.getString(R.string.notRealizeYet));
                break;
            // TextView 主页
            case 14:
                jumpWithTransition(appCompatActivity, view, TextViewHomeActivity.class);
                break;
            // 状态页
            case 15:
                jumpWithTransition(appCompatActivity, view, StatusActivity.class);
                break;
            // 筛选
            case 16:
                jumpWithTransition(appCompatActivity, view, ScreenActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 过渡跳转
     *
     * @param appCompatActivity   活动
     * @param view                视图
     * @param targetActivityClass 目标活动
     */
    private void jumpWithTransition(AppCompatActivity appCompatActivity, View view, Class<?> targetActivityClass) {
        TransitionKit.getInstance().jumpWithTransition(appCompatActivity, view, new Intent(appCompatActivity, targetActivityClass));
    }
}
