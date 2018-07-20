package com.example.wwg.net;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 江南开锁王
 * on 2018/7/20 19:08.
 * <p>
 * Effect :二次封装的Okhttp工具类
 * <p>
 * ━━━━ Code is far away from ━━━━━━
 * 　　  () 　　　  ()
 * 　　  ( ) 　　　( )
 * 　　  ( ) 　　　( )
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━ bug with the more protecting ━━━
 */

public class OkhttpUtils {
    private static OkhttpUtils instance;
    private Handler handler;
    private OkHttpClient client;

    /**
     * 构造器
     */
    private OkhttpUtils() {
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式
     * @return
     */
    public static OkhttpUtils getInstance() {
        if (instance == null) {
            instance = new OkhttpUtils();
        }
        return instance;
    }

    /**
     * 同步Get请求
     */
    public void doSyncGet(final String url, final OnNetworkListener onNetworkListener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //2.创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //3.创建Request 对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();
                    Response response = null;//得到Response 对象
                    response = client.newCall(request).execute();
                    //请求成功判断
                    if (response.isSuccessful()) {
                        final String s = response.body().string();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onNetworkListener.onSuccess(s);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 异步Get请求
     */
    public void doAsynGet(String url, final OnNetworkListener onNetworkListener){
        //获取Request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //发送请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onNetworkListener.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String s = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetworkListener.onSuccess(s);
                    }
                });


            }
        });
    }

    /**
     * 异步post请求
     */
    public void doAsynPost(String url, Map<String,String> map, final OnNetworkListener onNetworkListener){
        //初始Request对象
        Request request = null;
        //判断参数是否为空
        if (null!=map){
            //初始FormBody对象
            FormBody.Builder builder = new FormBody.Builder();
            //循环添加传进来的参数
            for(Map.Entry<String,String> entry : map.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
            //完成FormBody对象的创建
            FormBody body =  builder.build();
            //创建Request并添加参数
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

        }else {
            request = new Request.Builder()
                    .url(url)
                    .build();

        }

        //进行请求
        client.newCall(request).enqueue(new Callback() {
            //失败回调
            @Override
            public void onFailure(Call call, IOException e) {

            }
            //成功回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //获取返回的数据,并转化为string类型
                final String s = response.body().string();

                //利用handler将数据发送到主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetworkListener.onSuccess(s);
                    }
                });
            }
        });

    }

    /**
     * 向外部提供接口
     */
    public interface OnNetworkListener{
        /**
         * 成功回调
         */
        void onSuccess(Object result);
        /**
         * 失败回调
         */
        void onFailed(Exception e);
    }

}