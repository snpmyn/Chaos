package widget.property.kit;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.util.java.property.PropertyUtils;

import java.util.List;
import java.util.Properties;

/**
 * Created on 2021/9/26
 *
 * @author zsp
 * @desc 属性页配套元件
 */
public class PropertyActivityKit {
    /**
     * 获取属性
     *
     * @param appCompatActivity  活动
     * @param propertyActivityTv TextView
     */
    public void getProperty(AppCompatActivity appCompatActivity, TextView propertyActivityTv) {
        PropertyUtils propertyUtils = new PropertyUtils();
        Properties properties = propertyUtils.getProperties(appCompatActivity, "property");
        StringBuilder stringBuilder = new StringBuilder(properties.getProperty("0000", "default value" + "\n\n"));
        List<String> list = propertyUtils.getPropertyKeyOrValueList(appCompatActivity, "property", 0);
        for (String s : list) {
            stringBuilder.append(s).append((list.indexOf(s) == (list.size() - 1)) ? "" : " || ");
        }
        propertyActivityTv.setText(stringBuilder.toString());
    }
}
