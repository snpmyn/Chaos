package widget.screen.kit;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.screen.kit.ScreenHandleKit;
import com.chaos.widget.screen.listener.ScreenHandleListener;

/**
 * Created on 2022/5/26
 *
 * @author zsp
 * @desc 筛选页配套元件
 */
public class ScreenActivityKit {
    /**
     * 筛选
     *
     * @param appCompatActivity 活动
     */
    public void screen(AppCompatActivity appCompatActivity) {
        ScreenHandleKit screenHandleKit = new ScreenHandleKit(appCompatActivity);
        // 性别
        screenHandleKit.packStringConditions("性别", 2, true, "男", "女");
        // 年龄段
        screenHandleKit.packStringConditions("年龄段", 4, false,
                "18岁以下", "18～40岁", "40～60岁", "60岁以上");
        // 祝福类型
        screenHandleKit.packStringConditions("祝福类型", 2, true,
                "生日祝福", "节日祝福");
        // 单选后可反选
        screenHandleKit.canReverseSelectAfterSingleSelect("性别", "年龄段");
        // 默选
        screenHandleKit.defaultSelect("祝福类型", "生日祝福");
        // 关联
        screenHandleKit.associate();
        // 设筛选操作监听
        screenHandleKit.setScreenHandleListener(new ScreenHandleListener() {
            @Override
            public void click(View view, String classification, String condition, boolean selected) {
                ToastKit.showShort(classification + " || " + condition + " || " + selected);
            }

            @Override
            public void reset() {
                screenHandleKit.reset();
            }

            @Override
            public void ensure() {
                screenHandleKit.dismiss();
            }
        });
        // 显示
        screenHandleKit.show();
    }
}
