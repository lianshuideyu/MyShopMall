package com.atguigu.myshopmall.shoppingcart;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myshopmall.app.MyApplication;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.shoppingcart.utils.CartStorage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ShoppingCartFragment extends BaseFragment {
    private TextView tv;
    @Override
    public View initView() {
        tv = new TextView(mContext);
        tv.setTextColor(Color.RED);
        tv.setTextSize(30);
        Log.e("TAG","ShoppingCartFragment--initView");
        return tv;
    }

    @Override
    public void initData() {
        super.initData();
        tv.setText("购物车");

        ArrayList<GoodsBean> allData = CartStorage.getInstance(MyApplication.getContext()).getAllData();
        for(int i = 0; i < allData.size(); i++) {

            Log.e("TAG",""+allData.get(i).toString());
        }
    }
}
