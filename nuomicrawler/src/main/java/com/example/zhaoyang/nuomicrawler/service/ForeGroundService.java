package com.example.zhaoyang.nuomicrawler.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ForeGroundService extends Service {
    public ForeGroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
