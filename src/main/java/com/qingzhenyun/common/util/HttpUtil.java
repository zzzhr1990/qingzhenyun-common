package com.qingzhenyun.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by guna on 2017/6/4.
 */
@Slf4j
public class HttpUtil {
    //
    private static final OkHttpClient client = new OkHttpClient();

    public static InputStream getRemoteByte(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        ResponseBody body = response.body();
        if (body == null) {
            throw new IOException("Response empty code " + response);
        }
        return body.byteStream();
    }

    public static InputStream getRemoteByte(String url, Integer tryTime) throws IOException {
        if (tryTime < 0) {
            throw new IOException("Request url " + url + "failed.");
        }
        tryTime--;
        try {
            return getRemoteByte(url);
        } catch (IOException ex) {
            log.warn("Request {} failed, {} left.", url, tryTime, ex);
            return getRemoteByte(url, tryTime);
        }
    }

}
