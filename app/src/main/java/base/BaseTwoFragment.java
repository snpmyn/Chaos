package base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaos.pool.base.BasePoolTwoFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 2021/10/21
 *
 * @author zsp
 * @desc BaseTwoFragment
 */
public abstract class BaseTwoFragment extends BasePoolTwoFragment {
    /**
     * Unbinder
     */
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }
}
