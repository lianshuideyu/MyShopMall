package com.atguigu.myshopmall.type;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.type.adapter.TagGridViewAdapter;
import com.atguigu.myshopmall.type.bean.TagBean;
import com.atguigu.myshopmall.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/11.
 */

public class TagFragment extends BaseFragment {
    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "TagFragment--initView");

        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
//        Log.e("TAG","TypeFragment--initView");
        super.initData();

        getDataFromNet();
    }

    private void getDataFromNet() {

        OkHttpUtils
                .get()
                .url(Constants.TAG_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG","联网失败=="+ e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG","联网成功=="+ response);
            processData(response);
        }
    }

    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json,TagBean.class);
        Log.e("TAG",tagBean.getResult().get(0).getName());

        adapter = new TagGridViewAdapter(mContext,tagBean.getResult());
        gvTag.setAdapter(adapter);

        //设置item的点击事件
        gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TagBean.ResultBean item = adapter.getItem(position);
                Toast.makeText(mContext, ""+item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
