package com.example.zhaoyang.nuomicrawler.network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.zhaoyang.nuomicrawler.module.VTwoData;
import com.example.zhaoyang.nuomicrawler.policy.VTwoExPolicy;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhaoyang on 16-8-18.
 */
public class VTwoExCrawlerTask extends AsyncTask<Void, Void, List<VTwoData>> {
    private VTwoExPolicy mPolicy;
    private onCrawSuccess mOnCrawSuccessListener;

    public VTwoExCrawlerTask(Context mCtx, int page, onCrawSuccess listener, String city) {
        mOnCrawSuccessListener = listener;
        mPolicy = new VTwoExPolicy(mCtx, page, city);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<VTwoData> result) {
        super.onPostExecute(result);
        mOnCrawSuccessListener.onUpdateResult(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<VTwoData> doInBackground(Void... params) {
        List<VTwoData> result = null;
        mPolicy.onLogin();
        try {
            result = mPolicy.onCrawl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public interface onCrawSuccess {
        void onUpdateResult(List<VTwoData> result);
    }
}
