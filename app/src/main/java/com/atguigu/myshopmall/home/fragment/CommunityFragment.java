package com.atguigu.myshopmall.home.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myshopmall.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/11.
 */

public class CommunityFragment extends BaseFragment {
    private TextView tv;
    @Override
    public View initView() {
        tv = new TextView(context);
        tv.setTextColor(Color.RED);
        tv.setTextSize(30);
        Log.e("TAG","CommunityFragment--initView");
        return tv;
    }

    @Override
    public void initData() {
        tv.setText("发现");

        super.initData();
    }
}
