package com.example.chaos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.util.java.listener.AppListener;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.dialog.message.RoundCornerMessageDialog;
import com.chaos.widget.other.listview.MeasuredListView;
import com.example.chaos.kit.MainActivityKit;
import com.google.android.material.button.MaterialButton;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import value.ChaosMagic;

/**
 * @desc: 主页
 * @author: zsp
 * @date: 2021/3/5 10:21 AM
 */
public class MainActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainActivityMlv)
    MeasuredListView mainActivityMlv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainActivityRv)
    RecyclerView mainActivityRv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainActivityMb)
    MaterialButton mainActivityMb;
    /**
     * 主页配套元件
     */
    private MainActivityKit mainActivityKit;

    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        mainActivityKit = new MainActivityKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        AppListener.getInstance().registerCallback(areForeground -> ToastKit.showShort(areForeground ? getString(R.string.comeToTheForeground) : getString(R.string.goBackground)));
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        // 是否前台
        if (AppListener.getInstance().areForeground()) {
            ToastKit.showShort(getString(R.string.foregroundOperation));
        }
        // 请求权限
        mainActivityKit.requestPermissions(this);
        // 展示
        mainActivityKit.display(this, mainActivityMlv, mainActivityRv, mainActivityMb);
    }

    @OnClick({R.id.mainActivityMb})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.mainActivityMb) {
            mainActivityKit.display(this, mainActivityMlv, mainActivityRv, mainActivityMb);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 小米机型无论授予权限否，shouldShowRequestPermissionRationale 都为 false。故需另加判断 grantResults[0] == -1。
        if ((requestCode == ChaosMagic.INT_ZERO_X_ZERO_ONE) && (grantResults[0] == -1) && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            RoundCornerMessageDialog roundCornerMessageDialog = new RoundCornerMessageDialog.Builder(this, 0)
                    .setTitle(getString(R.string.hint))
                    .setTitleColor(ContextCompat.getColor(this, R.color.fontInput))
                    .setContent(getString(R.string.needToAllowReadWritePermissions))
                    .setContentColor(ContextCompat.getColor(this, R.color.fontHint))
                    .setContentHorizontalCenter()
                    .setLeftButtonText(getString(R.string.cancel))
                    .setRightButtonText(getString(R.string.ensure))
                    .setOnRoundCornerMessageDialogLeftButtonClickListener((view, roundCornerMessageDialog1) -> roundCornerMessageDialog1.handle(roundCornerMessageDialog1.getClass()))
                    .setOnRoundCornerMessageDialogRightButtonClickListener((view, roundCornerMessageDialog12) -> roundCornerMessageDialog12.handle(roundCornerMessageDialog12.getClass())).build();
            roundCornerMessageDialog.setCancelable(false);
            roundCornerMessageDialog.show();
        }
    }
}