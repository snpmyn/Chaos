package com.chaos.widget.searchview;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * Created on 2021/8/24
 *
 * @author zsp
 * @desc SearchView 配套元件
 */
public class SearchViewKit {
    /**
     * 设 SearchView
     *
     * @param appCompatActivity 活动
     * @param searchView        SearchView
     * @param textSize          文本色
     * @param textColor         文本大小
     * @param hintTextColor     提示文本色
     */
    public static void setSearchView(AppCompatActivity appCompatActivity, @NonNull SearchView searchView, int textSize, int textColor, int hintTextColor) {
        WeakReference<AppCompatActivity> weakReference = new WeakReference<>(appCompatActivity);
        try {
            Class<?> clazz = searchView.getClass();
            // SearchView 父布局名字 mSearchPlate
            Field field = clazz.getDeclaredField("mSearchPlate");
            field.setAccessible(true);
            View view = (View) field.get(searchView);
            if (null != view) {
                // 下划线透明
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Timber.e(e);
        }
        EditText editText = searchView.findViewById(R.id.search_src_text);
        // 文本大小、色
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, weakReference.get().getResources().getDimension(textSize));
        editText.setTextColor(ContextCompat.getColor(weakReference.get(), textColor));
        // 提示文本色
        editText.setHintTextColor(ContextCompat.getColor(weakReference.get(), hintTextColor));
    }
}
