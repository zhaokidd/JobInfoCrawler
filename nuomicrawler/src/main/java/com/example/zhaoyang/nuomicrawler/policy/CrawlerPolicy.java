package com.example.zhaoyang.nuomicrawler.policy;

import java.io.IOException;

/**
 * Created by zhaoyang on 16-7-24.
 */

public abstract class CrawlerPolicy {
    boolean needLogin = false;

    public <T> T onCrawl() throws IOException {
        return null;
    }


    public void onLogin() {
    }


    boolean isNeedLogin() {
        return needLogin;
    }


    void setNeedLogin(boolean isNeedLogin) {
        needLogin = isNeedLogin;
    }

}
