package com.atguigu.myshopmall.type;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.type.adapter.TypeLeftAdapter;
import com.atguigu.myshopmall.type.adapter.TypeRightAdapter;
import com.atguigu.myshopmall.type.bean.ListBean;
import com.atguigu.myshopmall.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ListFragment extends BaseFragment {
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.rv)
    RecyclerView rv;
    private TypeLeftAdapter leftAdapter;

    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private TypeRightAdapter rightAdapter;

    @Override
    public View initView() {
        Log.e("TAG", "TypeFragment--initView");

        View view = View.inflate(mContext, R.layout.list_fragment, null);
        ButterKnife.inject(this, view);
        //leftlistview的点击事件
        listview.setOnItemClickListener(new MyOnItemClick());

        return view;
    }

    @Override
    public void initData() {
//        Log.e("TAG","TypeFragment--initView");
        super.initData();

        leftAdapter = new TypeLeftAdapter(mContext);
        listview.setAdapter(leftAdapter);

        //默认
        getDataFromNet(urls[0]);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class MyOnItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //点击某一条变为高亮
            leftAdapter.selectPosition(position);

            //刷新适配器
            leftAdapter.notifyDataSetChanged();

            //点击某一条联网获取相应的数据
            getDataFromNet(urls[position]);
        }
    }

    private void getDataFromNet(String url) {

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    private class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG","联网失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG","联网成功==" + response);

            //解析
            processData(response);
        }
    }

    private void processData(String json) {
        ListBean listBean = JSON.parseObject(json, ListBean.class);
        Log.e("TAG","解析成功==" + listBean.getResult().get(0).getName());
        //Log.e("TAG","解析成功==" + listBean.getResult().size());

        List<ListBean.ResultBean> result = listBean.getResult();
        if(result != null && result.size() > 0) {

            rightAdapter = new TypeRightAdapter(mContext,result);
            rv.setAdapter(rightAdapter);

            GridLayoutManager manager = new GridLayoutManager(mContext,3);

            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position == 0) {
                        return 3;
                    }else {
                        return 1;
                    }
                }
            });

            rv.setLayoutManager(manager);
        }

    }
}
