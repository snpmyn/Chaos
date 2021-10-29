package com.chaos.widget.upgrade.body;

import androidx.annotation.NonNull;

import com.chaos.util.java.datetime.CurrentTimeMillisClock;
import com.chaos.widget.upgrade.event.DownloadProgressEvent;
import com.chaos.widget.upgrade.manager.RxManager;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import rx.Observable;
import rx.schedulers.Schedulers;
import value.WidgetMagic;

/**
 * @decs: 下载响应体
 * @author: 郑少鹏
 * @date: 2018/8/22 8:51
 */
public class DownloadResponseBody extends ResponseBody {
    private final ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;

    public DownloadResponseBody(ResponseBody responseBody) {
        mResponseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public @NotNull BufferedSource source() {
        if (null == mBufferedSource) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    @Contract("_ -> new")
    private @NotNull Source source(Source source) {
        return new ForwardingSource(source) {
            private long mProgress = 0;
            private long mLastSendTime = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                mProgress += ((bytesRead == -1) ? 0 : bytesRead);
                if (CurrentTimeMillisClock.getInstance().now() - mLastSendTime > WidgetMagic.INT_FIVE_HUNDRED) {
                    RxManager.send(new DownloadProgressEvent(contentLength(), mProgress));
                    mLastSendTime = CurrentTimeMillisClock.getInstance().now();
                } else if (mProgress == contentLength()) {
                    Observable.just(mProgress).delaySubscription(500, TimeUnit.MILLISECONDS, Schedulers.io()).subscribe(aLong -> RxManager.send(new DownloadProgressEvent(contentLength(), mProgress)));
                }
                return bytesRead;
            }
        };
    }
}