package androidx.fragment.app;

import com.chaos.util.java.log.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @decs: FragmentationMagician
 * @author: 郑少鹏
 * @date: 2019/7/5 11:43
 */
public class FragmentationMagician {
    public static boolean isStateSaved(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return false;
        }
        try {
            FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
            return fragmentManagerImpl.isStateSaved();
        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }

    /**
     * popBackStackAllowingStateLoss
     * <p>
     * Like {@link FragmentManager#popBackStack()}} but allows the commit to be executed after an activity's state is saved.
     * This is dangerous because the action can be lost if the activity needs to later be restored from its state,
     * so this should only be used for cases where it is okay for the UI state to change unexpectedly on the user.
     *
     * @param fragmentManager 碎片管理器
     */
    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, fragmentManager::popBackStack);
    }

    /**
     * popBackStackImmediateAllowingStateLoss
     * <p>
     * Like {@link FragmentManager#popBackStackImmediate()}} but allows the commit to be executed after an activity's state is saved.
     *
     * @param fragmentManager 碎片管理器
     */
    public static void popBackStackImmediateAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, fragmentManager::popBackStackImmediate);
    }

    /**
     * popBackStackAllowingStateLoss
     * <p>
     * Like {@link FragmentManager#popBackStack(String, int)} but allows the commit to be executed after an activity's state is saved.
     *
     * @param fragmentManager 碎片管理器
     * @param name            名字
     * @param flags           标志
     */
    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager, final String name, final int flags) {
        FragmentationMagician.hookStateSaved(fragmentManager, () -> fragmentManager.popBackStack(name, flags));
    }

    /**
     * executePendingTransactionsAllowingStateLoss
     * <p>
     * Like {@link FragmentManager#executePendingTransactions()} but allows the commit to be executed after an activity's state is saved.
     *
     * @param fragmentManager 碎片管理器
     */
    public static void executePendingTransactionsAllowingStateLoss(final FragmentManager fragmentManager) {
        FragmentationMagician.hookStateSaved(fragmentManager, fragmentManager::executePendingTransactions);
    }

    public static @NotNull List<Fragment> getActiveFragments(@NotNull FragmentManager fragmentManager) {
        return fragmentManager.getFragments();
    }

    private static void hookStateSaved(FragmentManager fragmentManager, Runnable runnable) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return;
        }
        runnable.run();
    }
}