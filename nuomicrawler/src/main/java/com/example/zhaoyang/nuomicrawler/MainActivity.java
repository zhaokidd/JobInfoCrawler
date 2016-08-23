package com.example.zhaoyang.nuomicrawler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhaoyang.nuomicrawler.module.VTwoData;
import com.example.zhaoyang.nuomicrawler.module.VTwoRecyclerViewAdapter;
import com.example.zhaoyang.nuomicrawler.network.VTwoExCrawlerTask;
import com.example.zhaoyang.nuomicrawler.util.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, VTwoExCrawlerTask.onCrawSuccess {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.editText2)
    EditText et_city;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private VTwoRecyclerViewAdapter mAdapter;
    private ArrayList<VTwoData> mDataSet;
//    private String TAG = "MainActivity";
//    private String URL_TEST = "https://zh.nuomi.com/film/9742/3581-0/subd/cb0-d10000-s6-o-b1-f0-p1#cinema-sort";
//    private String MY_ZHIHU_TOPICS = "https://www.zhihu.com/people/zhao-yang-90-41/topics";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        //init recyclerview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = new ArrayList<VTwoData>();
        mAdapter = new VTwoRecyclerViewAdapter(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
/*        String ZHIHU_LOGIN = "http://www.zhihu.com";
        CrawlerTask mCrawlerTask = new CrawlerTask(this);
        mCrawlerTask.execute(ZHIHU_LOGIN);*/

        String city = et_city.getText().toString().trim();
        if (city.equals("")) {
            return;
        } else {
            mDataSet.clear();
        }
        for (int i = 0; i < Data.COUNT_SEARCH_PAGE; i++) {
            VTwoExCrawlerTask mTask = new VTwoExCrawlerTask(this, i, this, city);
            //Use this THREAD_POOL_EXECUTOR, task can run paralled
            mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }


    @Override
    public void onUpdateResult(List<VTwoData> result) {
        mDataSet.addAll(result);
        mAdapter.notifyDataSetChanged();
    }
}
