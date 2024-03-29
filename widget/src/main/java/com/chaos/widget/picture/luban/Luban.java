package com.chaos.widget.picture.luban;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.chaos.widget.picture.luban.adapter.BaseInputStreamAdapter;
import com.chaos.widget.picture.luban.listener.OnCompressListener;
import com.chaos.widget.picture.luban.listener.OnRenameListener;
import com.chaos.widget.picture.luban.predicate.CompressionPredicate;
import com.chaos.widget.picture.luban.provider.InputStreamProvider;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

/**
 * @decs: Luban
 * @author: 郑少鹏
 * @date: 2019/8/28 19:08
 */
public class Luban implements Handler.Callback {
    private static final String DEFAULT_DISK_CACHE_DIR = "LubanDiskCache";
    private static final int MSG_COMPRESS_SUCCESS = 0;
    private static final int MSG_COMPRESS_START = 1;
    private static final int MSG_COMPRESS_ERROR = 2;
    private final int mLeastCompressSize;
    private final OnRenameListener mRenameListener;
    private final OnCompressListener mCompressListener;
    private final CompressionPredicate mCompressionPredicate;
    private final List<InputStreamProvider> mStreamProviders;
    private final Handler mHandler;
    private final Random random = new Random();
    private String mTargetDir;
    private boolean focusAlpha;

    private Luban(@NonNull Builder builder) {
        this.mTargetDir = builder.mTargetDir;
        this.mRenameListener = builder.mRenameListener;
        this.mStreamProviders = builder.mStreamProviders;
        this.mCompressListener = builder.mCompressListener;
        this.mLeastCompressSize = builder.mLeastCompressSize;
        this.mCompressionPredicate = builder.mCompressionPredicate;
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    @NonNull
    @Contract("_ -> new")
    public static Builder with(Context context) {
        return new Builder(context);
    }

    /**
     * Returns a directory with the given name in the private cache directory of the application to use to store retrieved media and thumbnails.
     *
     * @param context   A context.
     * @param cacheName The name of the subdirectory in which to store the cache.
     * @see #getImageCacheDir(Context)
     */
    private static @Nullable File getImageCacheDir(@NonNull Context context, String cacheName) {
        File cacheDir = context.getExternalCacheDir();
        if (null != cacheDir) {
            File result = new File(cacheDir, cacheName);
            boolean flag = !result.mkdirs() && (!result.exists() || !result.isDirectory());
            if (flag) {
                // File wasn't able to create a directory, or the result exists but not a directory.
                return null;
            }
            return result;
        }
        Timber.d("default disk cache dir is null");
        return null;
    }

    /**
     * Returns a file with a cache image name in the private cache directory.
     *
     * @param context A context.
     */
    @NonNull
    @Contract("_, _ -> new")
    private File getImageCacheFile(Context context, String suffix) {
        if (TextUtils.isEmpty(mTargetDir)) {
            mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        String cacheBuilder = (mTargetDir + "/" + System.currentTimeMillis() + (random.nextInt() * 1000) + (TextUtils.isEmpty(suffix) ? ".jpg" : suffix));
        return new File(cacheBuilder);
    }

    @NonNull
    @Contract("_, _ -> new")
    private File getImageCustomFile(Context context, String filename) {
        if (TextUtils.isEmpty(mTargetDir)) {
            mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        String cacheBuilder = (mTargetDir + "/" + filename);
        return new File(cacheBuilder);
    }

    /**
     * Returns a directory with a default name in the private cache directory of the application to use to store retrieved audio.
     *
     * @param context A context.
     * @see #getImageCacheDir(Context, String)
     */
    private File getImageCacheDir(Context context) {
        return getImageCacheDir(context, DEFAULT_DISK_CACHE_DIR);
    }

    /**
     * Start asynchronous compress thread.
     *
     * @param context A context.
     */
    private void launch(final Context context) {
        boolean flag = ((null == mStreamProviders) || (mStreamProviders.size() == 0) && (null != mCompressListener));
        if (flag) {
            mCompressListener.onError(new NullPointerException("image file cannot be null"));
        }
        Iterator<InputStreamProvider> iterator;
        if (null != mStreamProviders) {
            iterator = mStreamProviders.iterator();
            while (iterator.hasNext()) {
                final InputStreamProvider path = iterator.next();
                AsyncTask.SERIAL_EXECUTOR.execute(() -> {
                    try {
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_START));
                        File result = compress(context, path);
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_SUCCESS, result));
                    } catch (IOException e) {
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_ERROR, e));
                    }
                });
                iterator.remove();
            }
        }
    }

    /**
     * Start compress and return the file.
     *
     * @param inputStreamProvider InputStreamProvider
     * @param context             A Context.
     * @return File
     * @throws IOException IOException
     */
    private File get(InputStreamProvider inputStreamProvider, Context context) throws IOException {
        try {
            return new Engine(inputStreamProvider, getImageCacheFile(context, Checker.SINGLE.extSuffix(inputStreamProvider)), focusAlpha).compress();
        } finally {
            inputStreamProvider.close();
        }
    }

    @NonNull
    private List<File> get(Context context) throws IOException {
        List<File> results = new ArrayList<>();
        Iterator<InputStreamProvider> iterator = mStreamProviders.iterator();
        while (iterator.hasNext()) {
            results.add(compress(context, iterator.next()));
            iterator.remove();
        }
        return results;
    }

    private File compress(Context context, InputStreamProvider path) throws IOException {
        try {
            return compressReal(context, path);
        } finally {
            path.close();
        }
    }

    private File compressReal(Context context, InputStreamProvider path) throws IOException {
        File result;
        File outFile = getImageCacheFile(context, Checker.SINGLE.extSuffix(path));
        if (null != mRenameListener) {
            String filename = mRenameListener.rename(path.getPath());
            outFile = getImageCustomFile(context, filename);
        }
        if (null != mCompressionPredicate) {
            if (mCompressionPredicate.apply(path.getPath()) && Checker.SINGLE.needCompress(mLeastCompressSize, path.getPath())) {
                result = new Engine(path, outFile, focusAlpha).compress();
            } else {
                result = new File(path.getPath());
            }
        } else {
            result = (Checker.SINGLE.needCompress(mLeastCompressSize, path.getPath()) ?
                    new Engine(path, outFile, focusAlpha).compress() :
                    new File(path.getPath()));
        }
        return result;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (null == mCompressListener) {
            return false;
        }
        switch (msg.what) {
            case MSG_COMPRESS_START:
                mCompressListener.onStart();
                break;
            case MSG_COMPRESS_SUCCESS:
                mCompressListener.onSuccess((File) msg.obj);
                break;
            case MSG_COMPRESS_ERROR:
                mCompressListener.onError((Throwable) msg.obj);
                break;
            default:
                break;
        }
        return false;
    }

    public static class Builder {
        private final Context context;
        private final List<InputStreamProvider> mStreamProviders;
        private String mTargetDir;
        private boolean focusAlpha;
        private int mLeastCompressSize = 100;
        private OnRenameListener mRenameListener;
        private OnCompressListener mCompressListener;
        private CompressionPredicate mCompressionPredicate;

        Builder(Context context) {
            this.context = context;
            this.mStreamProviders = new ArrayList<>();
        }

        @NonNull
        @Contract(" -> new")
        private Luban build() {
            return new Luban(this);
        }

        public Builder load(InputStreamProvider inputStreamProvider) {
            mStreamProviders.add(inputStreamProvider);
            return this;
        }

        void load(final File file) {
            mStreamProviders.add(new BaseInputStreamAdapter() {
                @Override
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(file);
                }

                @Override
                public String getPath() {
                    return file.getAbsolutePath();
                }
            });
        }

        void load(final String string) {
            mStreamProviders.add(new BaseInputStreamAdapter() {
                @Override
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(string);
                }

                @Override
                public String getPath() {
                    return string;
                }
            });
        }

        public <T> Builder load(@NonNull List<T> list) {
            for (T src : list) {
                if (src instanceof String) {
                    load((String) src);
                } else if (src instanceof File) {
                    load((File) src);
                } else if (src instanceof Uri) {
                    load((Uri) src);
                } else {
                    throw new IllegalArgumentException("Incoming data type exception, it must be String, File, Uri or Bitmap.");
                }
            }
            return this;
        }

        void load(final Uri uri) {
            mStreamProviders.add(new BaseInputStreamAdapter() {
                @Override
                public InputStream openInternal() throws IOException {
                    return context.getContentResolver().openInputStream(uri);
                }

                @Override
                public String getPath() {
                    return uri.getPath();
                }
            });
        }

        public Builder putGear(int gear) {
            return this;
        }

        public Builder setRenameListener(OnRenameListener listener) {
            this.mRenameListener = listener;
            return this;
        }

        public Builder setCompressListener(OnCompressListener listener) {
            this.mCompressListener = listener;
            return this;
        }

        public Builder setTargetDir(String targetDir) {
            this.mTargetDir = targetDir;
            return this;
        }

        /**
         * Do I need to keep the image's alpha channel.
         *
         * @param focusAlpha <p> true - to keep alpha channel, the compress speed will be slow. </p>
         *                   <p> false - don't keep alpha channel, it might have a black background.</p>
         */
        public Builder setFocusAlpha(boolean focusAlpha) {
            this.focusAlpha = focusAlpha;
            return this;
        }

        /**
         * Do not compress when the origin image file size less than one value.
         *
         * @param size the value of file size, unit KB, default 100K
         */
        public Builder ignoreBy(int size) {
            this.mLeastCompressSize = size;
            return this;
        }

        /**
         * Do compress image when return value was true, otherwise, do not compress the image file.
         *
         * @param compressionPredicate A predicate callback that returns true or false for the given input path should be compressed.
         */
        public Builder filter(CompressionPredicate compressionPredicate) {
            this.mCompressionPredicate = compressionPredicate;
            return this;
        }

        /**
         * Begin compress image with asynchronous.
         */
        public void launch() {
            build().launch(context);
        }

        public File get(final String path) throws IOException {
            return build().get(new BaseInputStreamAdapter() {
                @Override
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(path);
                }

                @Override
                public String getPath() {
                    return path;
                }
            }, context);
        }

        /**
         * Begin compress image with synchronize.
         *
         * @return the thumb image file list
         */
        public List<File> get() throws IOException {
            return build().get(context);
        }
    }
}