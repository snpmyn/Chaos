package widget.grid.kit;

import androidx.annotation.NonNull;

import com.chaos.widget.other.grid.HorizontalGridView;
import com.chaos.widget.other.grid.OnGridItemChildViewClickListener;
import com.chaos.widget.other.grid.OnGridItemClickListener;
import com.chaos.widget.other.grid.bean.GridBean;
import com.example.chaos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/9/26
 *
 * @author zsp
 * @desc 网格页配套元件
 */
public class GridActivityKit {
    /**
     * 展示
     *
     * @param horizontalGridView               水平 GridView
     * @param onGridItemClickListener          条目点击监听
     * @param onGridItemChildViewClickListener 条目子视图点击监听
     */
    public void display(@NonNull HorizontalGridView<GridBean> horizontalGridView, OnGridItemClickListener<GridBean> onGridItemClickListener, OnGridItemChildViewClickListener<GridBean> onGridItemChildViewClickListener) {
        horizontalGridView.setMaxCountEveryPage(8);
        horizontalGridView.loop(true);
        List<GridBean> gridBeanList = new ArrayList<>();
        gridBeanList.add(new GridBean("1", "甲", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("2", "乙", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("3", "丙", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("4", "丁", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("5", "金", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("6", "木", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("7", "水", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("8", "火", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("9", "土", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("0", "天", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("11", "地", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("12", "玄", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("13", "黄", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("14", "宇", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("15", "宙", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("16", "洪", R.mipmap.icon, "", "说明"));
        gridBeanList.add(new GridBean("17", "荒", R.mipmap.icon, "", "说明"));
        horizontalGridView.setData(gridBeanList);
        horizontalGridView.setOnGridItemClickListener(onGridItemClickListener);
        horizontalGridView.setOnGridItemChildViewClickListener(onGridItemChildViewClickListener);
    }
}
