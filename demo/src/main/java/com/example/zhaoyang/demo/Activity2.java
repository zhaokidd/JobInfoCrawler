package com.example.zhaoyang.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Activity2 extends AppCompatActivity  implements View.OnLongClickListener,View.OnClickListener{
    private Button btn1;
    private Button btn2;

    public Activity2() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.activity2_btn1);
        btn2 = (Button) findViewById(R.id.activity2_btn2);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity2_btn1:{
                break;
            }
            case R.id.activity2_btn2:{
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.activity2_btn1:{
                break;
            }
            case R.id.activity2_btn2:{
                break;
            }
        }
        return true;
    }
}
