package com.atguigu.myshopmall.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.app.GoodsInfoActivity;
import com.atguigu.myshopmall.home.adapter.GoodsListAdapter;
import com.atguigu.myshopmall.home.adapter.HomeAdapter;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.home.bean.TypeListBean;
import com.atguigu.myshopmall.util.Constants;
import com.atguigu.myshopmall.util.DensityUtil;
import com.atguigu.myshopmall.util.SpaceItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsListActivity extends AppCompatActivity {

    @InjectView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @InjectView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @InjectView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @InjectView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @InjectView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @InjectView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @InjectView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @InjectView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @InjectView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.dl_left)
    DrawerLayout dlLeft;


    /**
     * 请求网络
     */
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };

    private GoodsListAdapter adapter;
    private List<TypeListBean.ResultBean.PageDataBean> page_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.inject(this);

        int position = getIntent().getIntExtra("position", -1);
        //联网回去数据
        getDataFromNet(urls[position]);

        //默认设置红色
        tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
    }

    private void getDataFromNet(String url) {

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallBack());

    }


    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "GoodsListActivity联网失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG", "GoodsListActivity联网成功==" + response);
            //解析
            processData(response);
        }
    }

    private void processData(String json) {

        TypeListBean typeListBean = JSON.parseObject(json, TypeListBean.class);
        Log.e("TAg", "GoodsListActivity解析成功=" + typeListBean.getResult().getPage_data().get(0).getName());

        page_data = typeListBean.getResult().getPage_data();

        adapter = new GoodsListAdapter(this, page_data);

        GridLayoutManager parame = new GridLayoutManager(this, 2);

        recyclerview.setLayoutManager(parame);

        //添加item的边界效果--分割线
        recyclerview.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(this, 10)));

        recyclerview.setAdapter(adapter);

        //设置点击事件
        adapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data) {
                String name = data.getName();
                String cover_price = data.getCover_price();
                String figure = data.getFigure();
                String product_id = data.getProduct_id();

                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setName(name);
                goodsBean.setFigure(figure);
                goodsBean.setCover_price(cover_price);
                goodsBean.setProduct_id(product_id);

                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                startActivity(intent);

            }
        });

    }


    private boolean isListPrice = false;

    @OnClick({R.id.tv_goods_list_sort, R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_price, R.id.tv_goods_list_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(GoodsListActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:
                Toast.makeText(GoodsListActivity.this, "回到主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_goods_list_price:
//                Toast.makeText(GoodsListActivity.this, "按价格排序", Toast.LENGTH_SHORT).show();

                //设置红色
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

                isListPrice = !isListPrice;
                if (isListPrice) {
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);

                } else {
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }

                break;
            case R.id.tv_goods_list_select:
//                Toast.makeText(GoodsListActivity.this, "筛选", Toast.LENGTH_SHORT).show();
                isListPrice = false;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置红色
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                break;
            case R.id.tv_goods_list_sort:
//                Toast.makeText(GoodsListActivity.this, "综合排序", Toast.LENGTH_SHORT).show();

                isListPrice = false;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置红色
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

                break;
        }
    }


}
