package com.example.zhaoyang.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Activity1 extends AppCompatActivity implements View.OnClickListener ,View.OnLongClickListener {
    private Button btn1;
    private Button btn2;
    private View popwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.activity1_btn1);
        btn2 = (Button) findViewById(R.id.activity1_btn2);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);

        btn1.setOnLongClickListener(this);
        btn2.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity1_btn1: {
                //click button1
                Intent intent = new Intent(Activity1.this, Activity2.class);
                startActivity(intent);
                break;
            }
            case R.id.activity1_btn2: {
                //click button2
                popWindow();
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.activity1_btn1: {
                //click button1
                startActivity(new Intent(Activity1.this,Activity2.class));
                break;
            }
            case R.id.activity1_btn2: {
                //click button2
                popWindow();
                break;
            }
        }
        return true;
    }

    //show the popwindow
    void popWindow() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //lanjie back
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //params.flags = flags;
        params.format = PixelFormat.RGBA_8888   ;

        params.width = 600;
        params.height = 800;

        params.gravity = Gravity.CENTER;

        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        manager.addView((popwindow = setUpView()), params);

    }

    //init the view
    View setUpView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
        final Button btn1 = (Button) view.findViewById(R.id.popwindow_btn1);
        Button btn2 = (Button) view.findViewById(R.id.popwindow_btn2);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.popwindow_btn1: {
                        break;
                    }
                    case R.id.popwindow_btn2: {
                        break;
                    }
                }
            }
        };

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (v.getId()) {
                    case R.id.popwindow_btn1: {
                        break;
                    }
                    case R.id.popwindow_btn2: {
                        break;
                    }
                }
                return true;
            }
        };

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn1.setOnLongClickListener(longClickListener);
        btn2.setOnLongClickListener(longClickListener);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
                    windowManager.removeView(view);
                }
                return true;
            }
        });

        view.setFocusableInTouchMode(true);
        return  view;
    }
}
