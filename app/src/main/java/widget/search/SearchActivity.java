package widget.search;

import android.annotation.SuppressLint;

import androidx.appcompat.widget.SearchView;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.searchview.SearchViewKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;

/**
 * @desc: 搜索页
 * @author: zsp
 * @date: 2021/8/24 4:30 下午
 */
public class SearchActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.searchActivitySv)
    SearchView searchActivitySv;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_search;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        SearchViewKit.setSearchView(this, searchActivitySv, R.dimen.sp_12, R.color.fontInput, R.color.fontHint);
    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {

    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        searchActivitySv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastKit.showShort(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ToastKit.showShort(newText);
                return false;
            }
        });
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {

    }
}