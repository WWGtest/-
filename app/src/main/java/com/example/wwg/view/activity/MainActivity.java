package com.example.wwg.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.wwg.R;
import com.example.wwg.view.myview.DragViewGroup;

public class MainActivity extends Activity {

    private String[] menuStr = {"开通会员", "QQ钱包", "个性装扮", "我的收藏", "我的相册", "我的文件"};
    private ArrayAdapter adapter;
    private DragViewGroup dragViewGroup;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        initViewByJava();
    }

    private void initView() {
        dragViewGroup = (DragViewGroup) findViewById(R.id.main_dragview);
        View slideView = dragViewGroup.getSlideView();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuStr);

    }

    private void initViewByJava() {
        dragViewGroup = (DragViewGroup) findViewById(R.id.main_dragview);
        dragViewGroup.setSlideView(R.layout.activity_slide);
        dragViewGroup.setRangePercent(0.8f);
        dragViewGroup.setOpenRangePercent(0.4f);
        dragViewGroup.setCloseRangePercent(0.5f);
        dragViewGroup.setSlideOpenSpeed(0.8f);
        View slideView = dragViewGroup.getSlideView();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuStr);

        //
        dragViewGroup.setOnDraggingListener(new DragViewGroup.OnDraggingListener() {
            @Override
            public void onOpen() {
                Toast.makeText(context, "open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClose() {
                Toast.makeText(context, "close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragging(float percent) {
                Log.v("onDragging", "" + percent);
            }
        });
    }
}
