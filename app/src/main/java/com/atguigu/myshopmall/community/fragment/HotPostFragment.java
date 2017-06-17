package com.atguigu.myshopmall.community.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myshopmall.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/17.
 */

public class HotPostFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(23);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("热帖内容");
    }

}


