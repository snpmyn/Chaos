package widget.money;

import android.annotation.SuppressLint;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.keyboard.MoneyInputEditText;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;

/**
 * @desc: 金额页
 * @author: zsp
 * @date: 2021/7/17 3:26 下午
 */
public class MoneyActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.preAuthorizationHomeFragmentMiet)
    MoneyInputEditText preAuthorizationHomeFragmentMiet;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_money;
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

    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        preAuthorizationHomeFragmentMiet.setCanCanceled(false);
        preAuthorizationHomeFragmentMiet.setOnKeyboardCompleteListener(formatNumber -> {
            if (Double.parseDouble(formatNumber) == 0) {
                ToastKit.showShort(getString(R.string.moneyIsIllegal));
                return;
            }
            preAuthorizationHomeFragmentMiet.cancel();
            ToastKit.showShort(formatNumber);
        });
        preAuthorizationHomeFragmentMiet.setKeyBackListener(this::finish);
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        preAuthorizationHomeFragmentMiet.showInputDialog();
    }
}