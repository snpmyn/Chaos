package com.chaos.util.java.url;

/**
 * Created on 2019/8/4.
 *
 * @author 郑少鹏
 * @desc UrlUtils
 */
public class UrlUtils {
    /**
     * Whether the url is a local one.
     *
     * @param url String
     * @return boolean
     */
    public static boolean isLocal(String url) {
        return ((null != url) && !url.startsWith("http://") && !url.startsWith("https://"));
    }
}
