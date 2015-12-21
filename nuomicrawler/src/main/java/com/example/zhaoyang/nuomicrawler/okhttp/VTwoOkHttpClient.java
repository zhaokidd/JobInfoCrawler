package com.example.zhaoyang.nuomicrawler.okhttp;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by zhaoyang on 16-8-18.
 */

public class VTwoOkHttpClient {
    private static OkHttpClient mClient = null;

    public static OkHttpClient getInstance(Context mContext) {
        if (mClient == null) {
            mClient = new OkHttpClient();
        }
        return mClient;
    }
}
