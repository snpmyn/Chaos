package com.chaos.pool;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.chaos.janalytics.kit.JanalyticsKit;
import com.chaos.jpush.kit.JpushKit;
import com.chaos.util.java.keyboard.KeyboardUtils;
import com.chaos.widget.dialog.bocdialog.base.BaseInstanceDialog;
import com.chaos.widget.dialog.bocdialog.loading.CanCancelLoadingDialog;
import com.chaos.widget.dialog.bocdialog.loading.CommonLoadingDialog;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnBackPressedListener;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnClickToCloseListener;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnDialogCloseListener;

import org.jetbrains.annotations.NotNull;

import support.SupportActivity;

/**
 * Created on 2021/3/9
 *
 * @author zsp
 * @desc BaseActivity
 * 优点：
 * 方便代码编写，减重复代码，加快开发；
 * 优化代码结构，降耦合度，方便修改；
 * 提代码可读性，显井井有条、优美。
 * <p>
 * 下抽象法子类须实现：
 * {@link #initContentView(Bundle, int)}
 * {@link #stepUi()}
 * {@link #initConfiguration()}
 * {@link #setListener()}
 * {@link #startLogic()}
 */
public abstract class BaseActivity extends SupportActivity {
    private BaseInstanceDialog baseInstanceDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载视图
        initContentView(savedInstanceState, layoutResId());
        // 初始控件
        stepUi();
        // 初始配置
        initConfiguration();
        // 设置监听
        setListener();
        // 开始逻辑
        startLogic();
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    protected abstract int layoutResId();

    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     * @param layoutResId        布局资源 ID
     */
    protected abstract void initContentView(Bundle savedInstanceState, int layoutResId);

    /**
     * 初始控件
     */
    protected abstract void stepUi();

    /**
     * 初始配置
     */
    protected abstract void initConfiguration();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 开始逻辑
     */
    protected abstract void startLogic();

    /**
     * 普通加载
     *
     * @param hint                  提示
     * @param onBackPressedListener 回退按压监听
     */
    public void commonLoading(String hint, OnBackPressedListener onBackPressedListener) {
        baseInstanceDialog = new CommonLoadingDialog.Builder(this, 0)
                .setHint(hint)
                .setOnBackPressedListener(onBackPressedListener).build();
        baseInstanceDialog.setCancelable(false);
        baseInstanceDialog.show();
    }

    /**
     * 可取消加载
     *
     * @param hint                   提示
     * @param onClickToCloseListener 点击关闭监听
     * @param onDialogCloseListener  对话框关闭监听
     * @param onBackPressedListener  回退按压监听
     */
    public void canCancelLoading(String hint, OnClickToCloseListener onClickToCloseListener, OnDialogCloseListener onDialogCloseListener, OnBackPressedListener onBackPressedListener) {
        baseInstanceDialog = new CanCancelLoadingDialog.Builder(this, 0)
                .setHint(hint)
                .setOnClickToCloseListener(onClickToCloseListener)
                .setOnDialogCloseListener(onDialogCloseListener)
                .setOnBackPressedListener(onBackPressedListener).build();
        baseInstanceDialog.setCancelable(false);
        baseInstanceDialog.show();
    }

    /**
     * 取消加载
     */
    public void dismissLoading() {
        if (null != baseInstanceDialog) {
            if (baseInstanceDialog.isShowing()) {
                baseInstanceDialog.dismiss();
            }
            baseInstanceDialog = null;
        }
    }

    /**
     * 清 EditText 焦点
     *
     * @param v   焦点所在 View
     * @param ids 输入框
     */
    protected void clearViewFocus(View v, int... ids) {
        if ((null != v) && (null != ids) && (ids.length > 0)) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐键盘
     *
     * @param v   焦点所在 View
     * @param ids 输入框
     * @return true 表焦点在 EditText
     */
    protected boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText editText = (EditText) v;
            for (int id : ids) {
                if (editText.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 传 EditText 的 ID
     * 没传入 EditText 不处理
     *
     * @return ID 数组
     */
    protected int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传需过滤 View
     * 过滤后点无隐软键盘操作
     *
     * @return ID 数组
     */
    protected View[] filterViewByIds() {
        return null;
    }

    /**
     * 触摸指定 View 否（过滤控件）
     *
     * @param views 视图
     * @param ev    手势事件
     * @return boolean
     */
    protected boolean isTouchView(View[] views, MotionEvent ev) {
        if ((null == views) || (views.length == 0)) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            boolean flag = ((ev.getX() > x) && (ev.getX() < (x + view.getWidth())) && (ev.getY() > y && ev.getY() < (y + view.getHeight())));
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 触摸指定 View 否（过滤控件）
     *
     * @param ids 控件数组
     * @param ev  手势事件
     * @return boolean
     */
    protected boolean isTouchView(int @NotNull [] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (null == view) {
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            boolean flag = ((ev.getX() > x) && (ev.getX() < (x + view.getWidth())) && (ev.getY() > y && ev.getY() < (y + view.getHeight())));
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Note: return supportActivityDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     *
     * @param ev 手势事件
     * @return boolean
     */
    @Override
    public boolean dispatchTouchEvent(@NotNull MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) {
                return super.dispatchTouchEvent(ev);
            }
            if ((null == hideSoftByEditViewIds()) || (hideSoftByEditViewIds().length == 0)) {
                return super.dispatchTouchEvent(ev);
            }
            View view = getCurrentFocus();
            if (isFocusEditText(view, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev)) {
                    return super.dispatchTouchEvent(ev);
                }
                // 隐键盘
                KeyboardUtils.closeKeyboardInActivity(this);
                clearViewFocus(view, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JpushKit.onResume(this);
        JanalyticsKit.onPageStart(this, this.getClass().getCanonicalName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        JpushKit.onPause(this);
        JanalyticsKit.onPageEnd(this, this.getClass().getCanonicalName());
    }
}


