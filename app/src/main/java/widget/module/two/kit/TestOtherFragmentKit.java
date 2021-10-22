package widget.module.two.kit;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.chaos.util.java.toast.ToastKit;
import com.example.chaos.R;

import widget.module.two.fragment.TestOtherFragment;
import widget.module.two.fragment.TestOtherFragmentArgs;

/**
 * Created on 2021/10/22
 *
 * @author zsp
 * @desc 测试其它碎片配套元件
 */
public class TestOtherFragmentKit {
    /**
     * 处理
     *
     * @param testOtherFragment 测试其它碎片
     */
    public void handle(@NonNull TestOtherFragment testOtherFragment) {
        Bundle bundle = testOtherFragment.getArguments();
        if (null != bundle) {
            boolean flag = bundle.containsKey("booleanData");
            if (flag) {
                TestOtherFragmentArgs testOtherFragmentArgs = TestOtherFragmentArgs.fromBundle(bundle);
                ToastKit.showShort(testOtherFragmentArgs.getBooleanData() + " | " +
                        testOtherFragmentArgs.getIntData() + " | " +
                        testOtherFragmentArgs.getFloatData() + " | " +
                        testOtherFragmentArgs.getStringData());
            } else {
                ToastKit.showShort(testOtherFragment.getString(R.string.noValuePassed));
            }
        }
    }
}
