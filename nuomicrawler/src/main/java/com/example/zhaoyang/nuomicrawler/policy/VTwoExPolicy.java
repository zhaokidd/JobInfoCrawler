package com.example.zhaoyang.nuomicrawler.policy;

import android.content.Context;
import android.util.Log;

import com.example.zhaoyang.nuomicrawler.module.VTwoData;
import com.example.zhaoyang.nuomicrawler.okhttp.VTwoOkHttpClient;
import com.example.zhaoyang.nuomicrawler.util.Data;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoyang on 16-8-18.
 * author : zy
 */

public class VTwoExPolicy extends CrawlerPolicy {
    private static final String TAG = "VTwoPolicy";
    private final static int COUNT_RESULT_NUM = 30;
    private Context mCtx;
    private int mPage;
    private String mCity;
    String[] mResult = null;


    public VTwoExPolicy(Context mContext, int page, String city) {
        mCtx = mContext;
        mPage = page;
        mCity = city;
    }

    @Override
    public List<VTwoData> onCrawl() throws IOException {
        OkHttpClient client = VTwoOkHttpClient.getInstance(mCtx);
        //set the timeout
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("p", mPage + "");
        //async
        Request request = new Request.Builder().url(createRequestUrl(Data.URL_VTWO_JOB, map)).addHeader("User-Agent", Data.USER_AGENT).build();
        Log.e(TAG, "【request】 " + request.headers().toString(), null);

/*        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "【onFailure】: " + "The request is failed", null);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    parseResponse(response);
                }
            }
        });*/

        Response response = client.newCall(request).execute();
        return parseResponse(response);
    }

    /**
     * add parameter onto the end of the url
     */
    private String createRequestUrl(String baseUrl, HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        for (String key : map.keySet()) {
            sb.append("&");
            try {
                String url = String.format("%s=%s", key, URLEncoder.encode(map.get(key), "utf-8"));
                sb.append(url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "createRequestUrl: " + sb.toString(), null);
        return sb.toString();
    }

    @Override
    public void onLogin() {
        super.onLogin();
    }

    /**
     * parse the response body
     */
    private List<VTwoData> parseResponse(Response response) throws IOException {
        ArrayList<VTwoData> list = new ArrayList<VTwoData>(COUNT_RESULT_NUM);
        String result = response.body().string();
        Document doc = Jsoup.parse(result);
        Elements topicNode = doc.select("#TopicsNode");
        if (topicNode != null) {
            Log.e(TAG, "onCrawl: " + topicNode.text(), null);
            int i = 0;
            for (Element element : topicNode) {
                Elements cells = topicNode.select("> div");
                if (cells != null) {
                    for (Element cell : cells) {
                        String content = cell.text();
                        if (content.contains(mCity)) {
                            String url = cell.select("a[href]").first().attr("href");
                            url = "www.v2ex.com" + url;
                            VTwoData data = new VTwoData();
                            data.setContent(content);
                            data.setUrl(url);
                            list.add(data);
                        }
                        Log.e(TAG, "" + cell.text() + "\n", null);
                    }
                }
            }
        }
        Log.e(TAG, "onCrawl: " + result, null);
        return list;
    }
}
