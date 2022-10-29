package widget.bean;

import com.example.chaos.R;

/**
 * Created on 2022/5/26
 *
 * @author zsp
 * @desc 组件菜单枚举
 */
public enum WidgetMenuEnum {
    /**
     * 对话框
     */
    BOC_DIALOG(1, R.drawable.ic_widgets_purple_500_24dp, "对话框", true),
    /**
     * 金额
     */
    MONEY(2, R.drawable.ic_widgets_purple_500_24dp, "金额", true),
    /**
     * 选择
     */
    CHOOSE(3, R.drawable.ic_widgets_purple_500_24dp, "选择", true),
    /**
     * 搜索
     */
    SEARCH(4, R.drawable.ic_widgets_purple_500_24dp, "搜索", true),
    /**
     * 图片
     */
    PICTURE(5, R.drawable.ic_widgets_purple_500_24dp, "图片", true),
    /**
     * 扫描
     */
    SCAN(6, R.drawable.ic_widgets_purple_500_24dp, "扫描", true),
    /**
     * LOTTIE
     */
    LOTTIE(7, R.drawable.ic_widgets_purple_500_24dp, "LOTTIE", true),
    /**
     * PUDDING
     */
    PUDDING(8, R.drawable.ic_widgets_purple_500_24dp, "PUDDING", true),
    /**
     * 网格
     */
    GRID(9, R.drawable.ic_widgets_purple_500_24dp, "网格", true),
    /**
     * 属性
     */
    PROPERTY(10, R.drawable.ic_widgets_purple_500_24dp, "属性", true),
    /**
     * 轮播
     */
    BANNER(11, R.drawable.ic_widgets_purple_500_24dp, "轮播", true),
    /**
     * 模块一
     */
    MODULE_ONE(12, R.drawable.ic_widgets_purple_500_24dp, "模块一", true),
    /**
     * 模块二
     */
    MODULE_TWO(13, R.drawable.ic_widgets_purple_500_24dp, "模块二", true),
    /**
     * TextView
     */
    TEXT_VIEW(14, R.drawable.ic_widgets_purple_500_24dp, "TextView", true),
    /**
     * 状态
     */
    STATUS(15, R.drawable.ic_widgets_purple_500_24dp, "状态", true),
    /**
     * 筛选
     */
    SCREEN(16, R.drawable.ic_widgets_purple_500_24dp, "筛选", true);
    /**
     * 菜单 ID
     */
    private final int menuId;
    /**
     * 菜单图标资源 ID
     */
    private final int menuIconResId;
    /**
     * 菜单名称
     */
    private final String menuName;
    /**
     * 菜单显示
     */
    private final Boolean menuShow;

    /**
     * constructor
     *
     * @param menuId        菜单 ID
     * @param menuIconResId 菜单图标资源 ID
     * @param menuName      菜单名称
     * @param menuShow      菜单显示
     */
    WidgetMenuEnum(int menuId, int menuIconResId, String menuName, Boolean menuShow) {
        this.menuId = menuId;
        this.menuIconResId = menuIconResId;
        this.menuName = menuName;
        this.menuShow = menuShow;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getMenuIconResId() {
        return menuIconResId;
    }

    public String getMenuName() {
        return menuName;
    }

    public Boolean getMenuShow() {
        return menuShow;
    }
}
