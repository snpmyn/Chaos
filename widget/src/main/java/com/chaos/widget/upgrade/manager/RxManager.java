package com.chaos.widget.upgrade.manager;

import com.chaos.widget.upgrade.event.DownloadProgressEvent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @decs: RxManager
 * @author: 郑少鹏
 * @date: 2018/8/22 8:55
 */
public class RxManager {
    private static RxManager sInstance;
    private final Subject<Object, Object> mBus;

    private RxManager() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    private static RxManager getInstance() {
        if (null == sInstance) {
            // [1]
            synchronized (RxManager.class) {
                if (null == sInstance) {
                    // 单例模式之双重检测
                    // 线程一在此前线程二到位 [1]，此不二判则线程二执行到此仍重 new。
                    sInstance = new RxManager();
                }
            }
        }
        return sInstance;
    }

    public static void send(Object obj) {
        if (getInstance().getBus().hasObservers()) {
            getInstance().getBus().onNext(obj);
        }
    }

    private static Observable<Object> toObservable() {
        return getInstance().getBus();
    }

    static Observable<DownloadProgressEvent> getDownloadEventObservable() {
        getInstance();
        return toObservable().ofType(DownloadProgressEvent.class).observeOn(AndroidSchedulers.mainThread());
    }

    private Subject<Object, Object> getBus() {
        return mBus;
    }
}
