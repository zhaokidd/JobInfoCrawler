package com.example.zhaoyang.nuomicrawler.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zhaoyang.nuomicrawler.okhttp.PersistentCookieStore;
import com.example.zhaoyang.nuomicrawler.policy.TopicPolicy;
import com.example.zhaoyang.nuomicrawler.util.Data;
import com.squareup.okhttp.OkHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by zhaoyang on 16-7-23.
 *
 */

public class CrawlerTask extends AsyncTask<String, Void, Document> {

    private final String TAG = "CrawlerTask";
    private TopicPolicy mPolicy;


    public CrawlerTask(Context mContext) {
        super();
        mPolicy = new TopicPolicy(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... params) {
        mPolicy.onLogin();
        mPolicy.onCrawl();
        return null;
    }

    @Override
    protected void onPostExecute(Document doc) {
/*        Elements citys = doc.select("ul.left-city > li.with-padding");
        for (Element element : citys) {
            Log.e(TAG, "onPostExecute: " + element.html(), null);
        }*/
    }
}
