package com.chaos.widget.upgrade.manager;

import android.annotation.SuppressLint;
import android.app.Application;

import timber.log.Timber;

/**
 * @decs: AppManager
 * @author: 郑少鹏
 * @date: 2018/8/22 8:45
 */
@SuppressLint("PrivateApi")
class AppManager {
    static final Application APPLICATION;

    private AppManager() {

    }

    static {
        Application application = null;
        try {
            application = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (null == application) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (final Exception e) {
            Timber.e("Failed to get current application from AppGlobals.");
            try {
                application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Timber.e(AppManager.class.getSimpleName(), "Failed to get current application from ActivityThread.%s", e.getMessage());
            }
        } finally {
            APPLICATION = application;
        }
    }
}
