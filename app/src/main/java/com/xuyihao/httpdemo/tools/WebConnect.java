package com.xuyihao.httpdemo.tools;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by XuYihao on 2016/4/6.
 */
public class WebConnect {

    private static String BASE_URL = null;
    private static AsyncHttpClient client = new AsyncHttpClient();

    /*
    * constructor
    * read the property file and get the server URL
    * */
    public WebConnect(){
        try{
            InputStream in = getClass().getResourceAsStream("WebConnect.properties");
            Properties properties = new Properties();
            properties.load(in);
            BASE_URL = properties.getProperty("SERVER_URL");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    * method get
    * */
    public static void get(String url_pattern, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url_pattern), params, responseHandler);
    }

    /*
    * method post
    * */
    public static void post(String url_pattern, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url_pattern), params, responseHandler);
    }

    /*
    * connect the base url and the relative url to get the absolute url
    * */
    private static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }
}
