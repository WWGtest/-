package com.example.wwg.view.fragment.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwg.R;
import com.example.wwg.bean.BannerDataBean;
import com.example.wwg.myclass.GlideImageLoader;
import com.example.wwg.net.OkhttpUtils;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江南开锁王
 * on 2018/7/18 17:06.
 * <p>
 * Effect :
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
public class Fragment_Recommend_Hot extends Fragment {

    private Banner banner;
    private RecyclerView recommendHotRecyclerview;
    private List<String> images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_hot,null);

        initView(view);

        initBannerData();

        return view;
    }

    //载入轮播图数据
    private void initBannerData() {
        OkhttpUtils.getInstance().doSyncGet("https://www.zhaoapi.cn/quarter/getAd", new OkhttpUtils.OnNetworkListener() {
            @Override
            public void onSuccess(Object result) {
                String s = result.toString();
                Gson gson = new Gson();
                BannerDataBean bannerDataBean = gson.fromJson(s, BannerDataBean.class);
                for (int i = 0; i < bannerDataBean.getData().size(); i++){
                    String icon = bannerDataBean.getData().get(i).getIcon();
                    Log.d("Fragment_Recommend_Hot", icon);
                    images.add(icon);
                }
                banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        recommendHotRecyclerview = (RecyclerView) view.findViewById(R.id.recommend_hot_recyclerview);
    }
}
