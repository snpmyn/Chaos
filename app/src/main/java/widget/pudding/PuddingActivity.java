package widget.pudding;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.pudding.Pudding;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: Pudding 页
 * @author: zsp
 * @date: 2021/9/26 1:49 下午
 */
public class PuddingActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_pudding;
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

    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.puddingActivityJavaExampleOne,
            R.id.puddingActivityJavaExampleTwo,
            R.id.puddingActivityJavaExampleThree,
            R.id.puddingActivityJavaExampleFour,
            R.id.puddingActivityJavaExampleFive,
            R.id.puddingActivityJavaExampleSix,
            R.id.puddingActivityJavaExampleSeven,
            R.id.puddingActivityJavaExampleEight})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 示例一
            case R.id.puddingActivityJavaExampleOne:
                exampleOne();
                break;
            // 示例二
            case R.id.puddingActivityJavaExampleTwo:
                exampleTwo();
                break;
            // 示例三
            case R.id.puddingActivityJavaExampleThree:
                exampleThree();
                break;
            // 示例四
            case R.id.puddingActivityJavaExampleFour:
                exampleFour();
                break;
            // 示例五
            case R.id.puddingActivityJavaExampleFive:
                exampleFive();
                break;
            // 示例六
            case R.id.puddingActivityJavaExampleSix:
                exampleSix();
                break;
            // 示例七
            case R.id.puddingActivityJavaExampleSeven:
                exampleSeven();
                break;
            // 示例八
            case R.id.puddingActivityJavaExampleEight:
                exampleEight();
                break;
            default:
                break;
        }
    }

    /**
     * 示例一
     */
    private void exampleOne() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            return null;
        }).show();
    }

    /**
     * 示例二
     */
    private void exampleTwo() {
        Pudding.create(this, choco -> {
            choco.setChocoBackgroundColor(ContextCompat.getColor(PuddingActivity.this, R.color.colorAccent));
            choco.setTitle(getString(R.string.title));
            choco.setTitleTypeface(Typeface.DEFAULT_BOLD);
            choco.setText(getString(R.string.content));
            choco.setTextAppearance(R.style.TextAppearance_AppCompat_Caption);
            return null;
        }).show();
    }

    /**
     * 示例三
     */
    private void exampleThree() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.setIcon(R.drawable.ic_info_white_24dp);
            return null;
        }).show();
    }

    /**
     * 示例四
     */
    private void exampleFour() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.setEnableInfiniteDuration(true);
            return null;
        }).show();
    }

    /**
     * 示例五
     */
    private void exampleFive() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.setEnableProgress(true);
            choco.setProgressColorRes(R.color.colorAccent);
            return null;
        }).show();
    }

    /**
     * 示例六
     */
    private void exampleSix() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.setEnableInfiniteDuration(true);
            choco.enableSwipeToDismiss();
            return null;
        }).show();
    }

    /**
     * 示例七
     */
    private void exampleSeven() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.onShow(() -> {
                ToastKit.showShort(getString(R.string.show));
                return null;
            });
            choco.onDismiss(() -> {
                ToastKit.showShort(getString(R.string.dismiss));
                return null;
            });
            return null;
        }).show();
    }

    /**
     * 示例八
     */
    private void exampleEight() {
        Pudding.create(this, choco -> {
            choco.setTitle(getString(R.string.title));
            choco.setText(getString(R.string.content));
            choco.addMaterialButton(getString(R.string.ok), R.style.Widget_MaterialComponents_Button_UnelevatedButton, view -> ToastKit.showShort(getString(R.string.ok)));
            choco.addMaterialButton(getString(R.string.cancel), R.style.Widget_MaterialComponents_Button_UnelevatedButton, view -> ToastKit.showShort(getString(R.string.cancel)));
            return null;
        }).show();
    }
}