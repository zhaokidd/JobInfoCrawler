package com.example.zhaoyang.nuomicrawler.policy;

import android.content.Context;
import android.util.Log;

import com.example.zhaoyang.nuomicrawler.okhttp.MyOkHttpClient;
import com.example.zhaoyang.nuomicrawler.util.Data;
import com.example.zhaoyang.nuomicrawler.util.HttpUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by zhaoyang on 16-7-24.
 * <p>
 * The policy to crawl the zhihu topic
 */

public class TopicPolicy extends CrawlerPolicy {
    private final String TAG = "Crawler";
    private Context mCtx;

    public TopicPolicy(Context mContext) {
        this.mCtx = mContext;
    }

    @Override
    public Void onCrawl() {
        try {
            getTopic();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getTopic() throws IOException {
        Request request = new Request.Builder().url(Data.URL_MY_TOPIC).addHeader("User-Agent", Data.USER_AGENT).build();
        Response response = MyOkHttpClient.getInstance(mCtx).newCall(request).execute();
        String result = response.body().string();
        Document doc = Jsoup.parse(result);
        Elements elements = doc.select("div#zh-profile-topic-list > div");
        for (Element element : elements) {
            Log.e(TAG, element.text(), null);
        }
    }

    @Override
    public void onLogin() {
        loginZhiHu();
    }

    @Override
    public boolean isNeedLogin() {
        return needLogin;
    }

    @Override
    public void setNeedLogin(boolean isNeedLogin) {
        needLogin = isNeedLogin;
    }

    private void loginZhiHu() {
        MyOkHttpClient.initInstance(mCtx);
        try {
            MyOkHttpClient.login(Data.USERNAME, Data.PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
