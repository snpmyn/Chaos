package com.chaos.widget.upgrade.engine;

import android.annotation.SuppressLint;

import com.chaos.widget.upgrade.body.DownloadResponseBody;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import timber.log.Timber;

/**
 * @decs: 更新引擎
 * @author: 郑少鹏
 * @date: 2018/8/22 8:55
 */
public class UpgradeEngine {
    private static UpgradeEngine instance;
    private final DownloadApi downloadApi;

    private UpgradeEngine() {
        // url 随便填（反正 DownloadApi 的 downloadFile 传绝对路径）
        downloadApi = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .client(getDownloadOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DownloadApi.class);
    }

    public static UpgradeEngine getInstance() {
        if (null == instance) {
            synchronized (UpgradeEngine.class) {
                if (null == instance) {
                    instance = new UpgradeEngine();
                }
            }
        }
        return instance;
    }

    private static @NotNull OkHttpClient getDownloadOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new DownloadResponseBody(originalResponse.body())).build();
                });
        try {
            // Create a trust manager that does not validate certificate chains.
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};
            // Install the all-trusting trust manager.
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager.
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            Timber.e(UpgradeEngine.class.getSimpleName(), "Failed to new TrustManager. %s", e.getMessage());
        }
        return builder.build();
    }

    public DownloadApi getDownloadApi() {
        return downloadApi;
    }

    public interface DownloadApi {
        /**
         * 下文件
         *
         * @param url url
         * @return Call<ResponseBody>
         */
        @Streaming
        @GET
        Call<ResponseBody> downloadFile(@Url String url);
    }
}
