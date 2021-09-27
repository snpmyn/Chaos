package widget.grid;

import android.annotation.SuppressLint;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.other.grid.HorizontalGridView;
import com.chaos.widget.other.grid.bean.GridBean;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import widget.grid.kit.GridActivityKit;

/**
 * @desc: 网格页
 * @author: zsp
 * @date: 2021/9/26 2:44 下午
 */
public class GridActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.gridActivityHgv)
    HorizontalGridView<GridBean> gridActivityHgv;
    /**
     * 网格页配套元件
     */
    private GridActivityKit gridActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_grid;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {

    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        gridActivityKit = new GridActivityKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {

    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        gridActivityKit.display(gridActivityHgv, (grid, position) -> ToastKit.showShort(grid.getId() + " " + grid.getTitle() + " " + grid.getDescription()), (grid, position) -> ToastKit.showShort(grid.getTitle() + " " + getString(R.string.clickChildView)));
    }
}