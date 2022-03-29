package kit;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.intent.IntentJump;
import com.chaos.util.java.storage.mmkv.MmkvKit;
import com.example.chaos.MainActivity;

/**
 * Created on 2022/3/24
 *
 * @author zsp
 * @desc 应用配套元件
 */
public class AppKit {
    /**
     * 本地保存
     *
     * @param appCompatActivity 活动
     * @param phoneNumber       手机号
     */
    public void localSave(AppCompatActivity appCompatActivity, String phoneNumber) {
        MmkvKit.defaultMmkv().encode(PoolConstant.LOGIN_$_PHONE_NUMBER, phoneNumber);
        IntentJump.getInstance().jump(null, appCompatActivity, true, MainActivity.class);
    }
}
