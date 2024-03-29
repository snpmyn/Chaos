package com.chaos.widget.picture.luban.adapter;

import com.chaos.widget.picture.luban.provider.InputStreamProvider;

import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

/**
 * @decs: BaseInputStreamAdapter
 * Automatically close the previous InputStream when opening a new InputStream, and finally need to manually call {@link #close()} to release the resource.
 * @author: 郑少鹏
 * @date: 2019/8/28 19:06
 */
public abstract class BaseInputStreamAdapter implements InputStreamProvider {
    private InputStream inputStream;

    @Override
    public InputStream open() throws IOException {
        close();
        inputStream = openInternal();
        return inputStream;
    }

    /**
     * Open internal.
     *
     * @return InputStream
     * @throws IOException IOException
     */
    public abstract InputStream openInternal() throws IOException;

    @Override
    public void close() {
        if (null != inputStream) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Timber.e(e);
            } finally {
                inputStream = null;
            }
        }
    }
}