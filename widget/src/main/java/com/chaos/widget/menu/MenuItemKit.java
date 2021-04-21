package com.chaos.widget.menu;

import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2021/4/21
 *
 * @author zsp
 * @desc MenuItem 配套元件
 */
public class MenuItemKit {
    private static MenuItemKit instance;

    public static MenuItemKit getInstance() {
        if (null == instance) {
            synchronized (MenuItemKit.class) {
                if (null == instance) {
                    instance = new MenuItemKit();
                }
            }
        }
        return instance;
    }

    /**
     * 显示
     *
     * @param menuItem 菜单条目
     */
    public void show(@NotNull MenuItem menuItem) {
        menuItem.setVisible(true);
        menuItem.setEnabled(true);
    }

    /**
     * 隐藏
     *
     * @param menuItem 菜单条目
     */
    public void hide(@NotNull MenuItem menuItem) {
        menuItem.setVisible(false);
        menuItem.setEnabled(false);
    }
}
