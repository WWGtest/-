package com.example.wwg.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wwg.R;
import com.example.wwg.base.BaseActivity;
import com.example.wwg.view.fragment.article.Fragment_Article;
import com.example.wwg.view.fragment.recommend.Fragment_Recommend;
import com.example.wwg.view.fragment.video.Fragment_Video;
import com.example.wwg.view.myview.DragViewGroup;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kyleduo.switchbutton.SwitchButton;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private DragViewGroup dragViewGroup;

    private Context context;
    private SwitchButton sb_ios;
    private RadioButton rb_recommend;
    private RadioButton rb_article;
    private RadioButton rb_video;
    private FragmentManager fragmentManager;
    private Fragment_Recommend fragment_recommend;
    private Fragment currentFragment = new Fragment();
    private Fragment_Article fragment_article;
    private Fragment_Video fragment_video;
    private RadioGroup bottom_radioGroup;
    private SimpleDraweeView user_title_img;
    private boolean isSlideViewOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        fragmentManager = getSupportFragmentManager();
        initView();
        initViewByJava();
        initRadioButtonImageSize();
        //设置默认显示的fragment
        showFragment(fragment_recommend);
        rb_recommend.setChecked(true);
    }

    private void initView() {
        dragViewGroup = (DragViewGroup) findViewById(R.id.main_dragview);
        View slideView = dragViewGroup.getSlideView();

        //首页用户头像
        user_title_img = findViewById(R.id.user_title_img);

        //初始化fragment
        fragment_recommend = new Fragment_Recommend();
        fragment_article = new Fragment_Article();
        fragment_video = new Fragment_Video();

        //初始化radiobutton按钮
        bottom_radioGroup = findViewById(R.id.bottom_radioGroup);
        rb_recommend = findViewById(R.id.rb_recommend);
        rb_article = findViewById(R.id.rb_article);
        rb_video = findViewById(R.id.rb_video);
        //设置选中监听
        bottom_radioGroup.setOnCheckedChangeListener(this);
    }

    private void initViewByJava() {
        dragViewGroup = (DragViewGroup) findViewById(R.id.main_dragview);
        dragViewGroup.setSlideView(R.layout.activity_slide);
        dragViewGroup.setRangePercent(0.8f);
        dragViewGroup.setOpenRangePercent(0.4f);
        dragViewGroup.setCloseRangePercent(0.5f);
        dragViewGroup.setSlideOpenSpeed(0.8f);
        View slideView = dragViewGroup.getSlideView();
        sb_ios = (SwitchButton)slideView.findViewById(R.id.sb_ios);
        sb_ios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(MainActivity.this, "打开", Toast.LENGTH_SHORT).show();
            }
        });

        //
        dragViewGroup.setOnDraggingListener(new DragViewGroup.OnDraggingListener() {
            @Override
            public void onOpen() {
//                Toast.makeText(context, "open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClose() {
//                Toast.makeText(context, "close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragging(float percent) {
//                Log.v("onDragging", "" + percent);
            }
        });
        //用户头像点击监听
        user_title_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSlideViewOpen){
                    //设置侧拉栏当前状态为开启状态
                    isSlideViewOpen = true;
                    //打开侧边栏
                    dragViewGroup.open();
                }else {
                    //设置侧拉栏当前状态为关闭状态
                    isSlideViewOpen = false;
                    //关闭侧边栏
                    dragViewGroup.close();
                }
            }
        });
    }

    /**
     * 设置底部按钮中图片的大小
     */
    private void initRadioButtonImageSize() {
        //定义底部标签图片大小
        Drawable drawableFirst = getResources().getDrawable(R.drawable.buttom_but_recommend_back);
        drawableFirst.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb_recommend.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSearch = getResources().getDrawable(R.drawable.buttom_but_article_back);
        drawableSearch.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb_article.setCompoundDrawables(null, drawableSearch, null, null);//只放上面

        Drawable drawableMe = getResources().getDrawable(R.drawable.buttom_but_video_back);
        drawableMe.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb_video.setCompoundDrawables(null, drawableMe, null, null);//只放上面
    }

    /**
     * radiobutton选中状态发生改变时回调
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_recommend:
                showFragment(fragment_recommend);
//                Log.d("MainActivity", "推荐选中");
                break;
            case R.id.rb_article:
                showFragment(fragment_article);
//                Log.d("MainActivity", "段子选中");
                break;
            case R.id.rb_video:
                showFragment(fragment_video);
//                Log.d("MainActivity", "视频选中");
                break;
        }
    }


    //   展示Fragment
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {//  判断传入的fragment是不是当前的currentFragmentgit
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(currentFragment);//  不是则隐藏
            currentFragment = fragment;  //  然后将传入的fragment赋值给currentFragment
            if (!fragment.isAdded()) { //  判断传入的fragment是否已经被add()过
                transaction.add(R.id.main_fragment, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    //设置返回键点击时的时间触发
    @Override
    public void onBackPressed() {
        if (isSlideViewOpen){
            //设置侧拉栏当前状态为关闭状态
            isSlideViewOpen = false;
            //关闭侧边栏
            dragViewGroup.close();
        }else {
            super.onBackPressed();
        }
    }

}
