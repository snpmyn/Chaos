package widget.module.two.kit;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.chaos.R;

import widget.module.two.fragment.TestOneFragment;
import widget.module.two.fragment.TestOtherFragmentArgs;

/**
 * Created on 2021/10/22
 *
 * @author zsp
 * @desc 测试一碎片配套元件
 */
public class TestOneFragmentKit {
    /**
     * 导航
     *
     * @param testOneFragment 测试其它碎片
     * @param view            视图
     */
    public void navigate(@NonNull TestOneFragment testOneFragment, View view) {
        TestOtherFragmentArgs testOtherFragmentArgs = new TestOtherFragmentArgs
                .Builder(true,
                1,
                1.1F,
                testOneFragment.getClass().getSimpleName())
                .build();
        Navigation.findNavController(view).navigate(R.id.actionFromTestOneFragmentToTestOtherFragment, testOtherFragmentArgs.toBundle());
    }
}
