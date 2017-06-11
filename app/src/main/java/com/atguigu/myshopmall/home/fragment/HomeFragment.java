package com.atguigu.myshopmall.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.home.bean.HomeBean;
import com.atguigu.myshopmall.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/11.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.tv_scan_home)
    TextView tvScanHome;
    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;

    @Override
    public View initView() {
        Log.e("TAG", "HomeFragment--initView");
        View view = View.inflate(context, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
//        Log.e("TAG","HomeFragment--initData");

        //联网请求
        getDataFromNet();
    }

    private void getDataFromNet() {

        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","联网成功==" + response);
                        //解析数据
                        processData(response);

                    }
                });
    }

    private void processData(String json) {
        if(!TextUtils.isEmpty(json)) {
            HomeBean resultBeanData  = JSON.parseObject(json, HomeBean.class);
            HomeBean.ResultBean resultBean = resultBeanData.getResult();
            Log.e("TAG","resultBean..=="+resultBean.getAct_info().get(0).getName());
        }


    }


    @OnClick({R.id.tv_scan_home, R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan_home:
                Toast.makeText(context, "扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_search_home:
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(context, "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                Toast.makeText(context, "回到顶部", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
