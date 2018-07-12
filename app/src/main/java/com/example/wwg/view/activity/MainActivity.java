package com.example.wwg.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.wwg.R;
import com.example.wwg.view.myview.DragViewGroup;
import com.kyleduo.switchbutton.SwitchButton;

public class MainActivity extends Activity {
    private DragViewGroup dragViewGroup;

    private Context context;
    private SwitchButton sb_ios;

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
    }
}
