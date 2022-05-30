package com.example.chaos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chaos.widget.other.adapter.BaseListViewAdapter;
import com.example.chaos.R;
import com.example.chaos.bean.MainActivityModule;

/**
 * Created on 2020-09-18
 *
 * @author zsp
 * @desc 主页 ListView 适配器
 */
public class MainActivityListViewAdapter extends BaseListViewAdapter<MainActivityModule> {
    public MainActivityListViewAdapter(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == view) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.main_activity_item, null, false);
            viewHolder.mainActivityItemTv = view.findViewById(R.id.mainActivityItemTv);
            viewHolder.mainActivityItemIb = view.findViewById(R.id.mainActivityItemIb);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MainActivityModule mainActivityModule = getItem(i);
        // 设置条目子视图点击监听
        setOnItemChildViewClickListener(i, mainActivityModule, viewHolder.mainActivityItemIb);
        // 模块名称
        viewHolder.mainActivityItemTv.setText(mainActivityModule.getModuleName());
        return view;
    }

    private static class ViewHolder {
        private TextView mainActivityItemTv;
        private ImageButton mainActivityItemIb;
    }
}
