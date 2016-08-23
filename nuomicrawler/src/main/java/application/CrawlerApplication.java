package application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhaoyang on 16-8-14.
 */

public class CrawlerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
