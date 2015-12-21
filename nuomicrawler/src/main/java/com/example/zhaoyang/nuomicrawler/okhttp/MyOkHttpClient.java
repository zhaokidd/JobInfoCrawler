package com.example.zhaoyang.nuomicrawler.okhttp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by zhaoyang on 16-7-24.
 */

public class MyOkHttpClient {
    private static final String TAG = "MyOkHttpClient";
    private static OkHttpClient mOkHttpClient = null;
    private static String xsrf = null;

    public static OkHttpClient getInstance(Context mContext) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setCookieHandler(new CookieManager(new PersistentCookieStore(mContext), CookiePolicy.ACCEPT_ALL));
        }
        return mOkHttpClient;
    }

    public static void initInstance(Context mContext) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setCookieHandler(new CookieManager(new PersistentCookieStore(mContext), CookiePolicy.ACCEPT_ALL));
        }
    }

    //  Get the verify code to log in
    public static void getCode() throws IOException {
        Request request = new Request.Builder()
                .url("http://www.zhihu.com/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
                .build();

        Response response = mOkHttpClient.newCall(request).execute();
        String result = response.body().string();

        Document parse = Jsoup.parse(result);
        System.out.println(parse + "");
        result = null;
        Elements element = parse.select("input[type=hidden]");
        if (element.size() > 0) {
            result = parse.select("input[type=hidden]").get(0).attr("value")
                    .trim();
        }
        xsrf = result;
        System.out.println("_xsrf:" + xsrf == null ? "_xsrf = null" : xsrf);
        String codeUrl = "http://www.zhihu.com/captcha.gif?r=";
        codeUrl += System.currentTimeMillis();
        System.out.println("codeUrl:" + codeUrl);
        Request getcode = new Request.Builder()
                .url(codeUrl)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
                .build();

        Response code = mOkHttpClient.newCall(getcode).execute();

        byte[] bytes = code.body().bytes();
        saveCode(bytes, "code.png");
    }

    //  save the code
    public static void saveCode(byte[] bfile, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + fileName);
            Log.e(TAG, file.getPath(), null);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //  use the information to login the website
    public static void login(String email, String password) throws IOException {
        getCode();
        StringBuilder randCode = new StringBuilder();
        String str = null;
        BufferedReader in = new BufferedReader(new FileReader(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "code.png")));
        while ((str = in.readLine()) != null) {
            randCode.append(str);
        }
        RequestBody formBody = new FormEncodingBuilder()
                .add("_xsrf", xsrf)
                .add("captcha", randCode.toString())
                .add("email", email)
                .add("password", password)
                .add("remember_me", "true")
                .build();
        Request login = new Request.Builder()
                .url("http://www.zhihu.com/login/email")
                .post(formBody)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
                .build();


        Response execute = mOkHttpClient.newCall(login).execute();
//        Log.e(TAG, decode(execute.body().string())
//                , null);
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
