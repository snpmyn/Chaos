package com.chaos.widget.menu;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.ListPopupWindow;

import com.chaos.widget.R;

import java.lang.ref.WeakReference;

/**
 * Created on 2021/4/20
 *
 * @author zsp
 * @desc ListPopupWindow 配套元件
 */
public class ListPopupWindowKit {
    public static ListPopupWindowKit getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 显示
     *
     * @param context                     上下文
     * @param anchor                      锚
     * @param objects                     数据
     * @param listPopupWindowKitInterface ListPopupWindow 配套元件接口
     */
    public void show(Context context, View anchor, String[] objects, ListPopupWindowKitInterface listPopupWindowKitInterface) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        ListPopupWindow listPopupWindow = new ListPopupWindow(weakReference.get(), null, R.attr.listPopupWindowStyle);
        listPopupWindow.setAnchorView(anchor);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(weakReference.get(), R.layout.list_popup_window_item, objects);
        listPopupWindow.setAdapter(arrayAdapter);
        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            listPopupWindowKitInterface.onItemClick(view, position, id);
        });
        listPopupWindow.show();
    }

    /**
     * ListPopupWindow 配套元件接口
     */
    public interface ListPopupWindowKitInterface {
        /**
         * 条目点击
         *
         * @param view     视图
         * @param position 位置
         * @param id       ID
         */
        void onItemClick(View view, int position, long id);
    }

    private static final class InstanceHolder {
        static final ListPopupWindowKit INSTANCE = new ListPopupWindowKit();
    }
}
