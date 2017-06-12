package com.atguigu.myshopmall.user;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myshopmall.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/11.
 */

public class UserFragemnt extends BaseFragment {
    private TextView tv;
    @Override
    public View initView() {
        tv = new TextView(mContext);
        tv.setTextColor(Color.RED);
        tv.setTextSize(30);
        Log.e("TAG","UserFragemnt--initView");
        return tv;
    }

    @Override
    public void initData() {
        tv.setText("用户中心");

        super.initData();
    }
}
