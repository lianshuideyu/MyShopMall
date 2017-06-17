package com.atguigu.myshopmall.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.community.adapter.NewPostListViewAdapter;
import com.atguigu.myshopmall.community.bean.NewPostBean;
import com.atguigu.myshopmall.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/17.
 */

public class NewPostFragment extends BaseFragment {
    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    private List<NewPostBean.ResultBean> result;
    private NewPostListViewAdapter adapter;

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getDataFromNet();

    }
    private void getDataFromNet() {

        String url = Constants.NEW_POST_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    private class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG","NewPostFragment联网失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG","NewPostFragment联网成功==" + response);

            //解析
            processData(response);
        }
    }

    private void processData(String json) {
        NewPostBean newPostBean = JSON.parseObject(json, NewPostBean.class);
        Log.e("TAG","解析成功==" + newPostBean.getResult().get(0).getUsername());

        result = newPostBean.getResult();
        if(result != null && result.size() > 0) {

            adapter = new NewPostListViewAdapter(mContext,result);

            lvNewPost.setAdapter(adapter);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}


