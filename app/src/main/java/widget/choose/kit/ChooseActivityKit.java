package widget.choose.kit;

import androidx.annotation.NonNull;

import com.chaos.widget.choose.provincialandurbanlinkage.Location;
import com.chaos.widget.choose.provincialandurbanlinkage.ProvincialAndUrbanLinkage;

/**
 * Created on 2021/8/24
 *
 * @author zsp
 * @desc 选择页配套元件
 */
public class ChooseActivityKit {
    /**
     * 省市区联动
     *
     * @param provincialAndUrbanLinkage 省市区联动
     * @param location                  定位
     */
    public void provincialAndUrbanLinkage(@NonNull ProvincialAndUrbanLinkage provincialAndUrbanLinkage, Location location) {
        provincialAndUrbanLinkage.stepOptionsPickerView();
        if (!provincialAndUrbanLinkage.areShowing()) {
            provincialAndUrbanLinkage.setLocation(location);
            provincialAndUrbanLinkage.loadData();
        }
    }
}
