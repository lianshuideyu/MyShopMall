package com.atguigu.myshopmall.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.app.GoodsInfoActivity;
import com.atguigu.myshopmall.home.adapter.ExpandableListViewAdapter;
import com.atguigu.myshopmall.home.adapter.GoodsListAdapter;
import com.atguigu.myshopmall.home.adapter.HomeAdapter;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.home.bean.TypeListBean;
import com.atguigu.myshopmall.util.Constants;
import com.atguigu.myshopmall.util.DensityUtil;
import com.atguigu.myshopmall.util.SpaceItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.atguigu.myshopmall.R.id.ib_drawer_layout_back;

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
    @InjectView(ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @InjectView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @InjectView(R.id.tv_drawer_layout_confirm)
    TextView tvDrawerLayoutConfirm;
    @InjectView(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @InjectView(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @InjectView(R.id.rg_select)
    RadioGroup rgSelect;
    @InjectView(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @InjectView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @InjectView(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @InjectView(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @InjectView(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @InjectView(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @InjectView(R.id.btn_select_all)
    Button btnSelectAll;
    @InjectView(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @InjectView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @InjectView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @InjectView(R.id.iv_price_no_limit)
    ImageView ivPriceNoLimit;
    @InjectView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @InjectView(R.id.iv_price_0_15)
    ImageView ivPrice015;
    @InjectView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @InjectView(R.id.iv_price_15_30)
    ImageView ivPrice1530;
    @InjectView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @InjectView(R.id.iv_price_30_50)
    ImageView ivPrice3050;
    @InjectView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @InjectView(R.id.iv_price_50_70)
    ImageView ivPrice5070;
    @InjectView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @InjectView(R.id.iv_price_70_100)
    ImageView ivPrice70100;
    @InjectView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @InjectView(R.id.iv_price_100)
    ImageView ivPrice100;
    @InjectView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @InjectView(R.id.et_price_start)
    EditText etPriceStart;
    @InjectView(R.id.v_price_line)
    View vPriceLine;
    @InjectView(R.id.et_price_end)
    EditText etPriceEnd;
    @InjectView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @InjectView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @InjectView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @InjectView(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @InjectView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @InjectView(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @InjectView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @InjectView(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @InjectView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @InjectView(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @InjectView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @InjectView(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @InjectView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @InjectView(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @InjectView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @InjectView(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @InjectView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @InjectView(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @InjectView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @InjectView(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @InjectView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @InjectView(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @InjectView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @InjectView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @InjectView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @InjectView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @InjectView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @InjectView(R.id.ll_type_root)
    LinearLayout llTypeRoot;


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
    private ExpandableListViewAdapter expandableListViewAdapter;

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

    @OnClick({R.id.btn_drawer_layout_cancel, R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm,
            R.id.btn_drawer_type_cancel, R.id.rl_select_type, R.id.rl_select_recommend_theme,
            R.id.rl_select_price, R.id.ib_drawer_layout_back, R.id.tv_goods_list_sort, R.id.ib_goods_list_back,
            R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_price,
            R.id.tv_goods_list_select})
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

                //筛选------------------------
                dlLeft.openDrawer(Gravity.RIGHT);
                showSelectorLayout();
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

            case ib_drawer_layout_back://点击返回
                //关闭筛选菜单
                dlLeft.closeDrawers();
                break;
            case R.id.rl_select_price://价格
                //价格筛选的页面
                llPriceRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);

                showPriceLayout();
                break;
            case R.id.rl_select_recommend_theme://推荐主题
                llThemeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);

                showThemeLayout();
                break;
            case R.id.rl_select_type://类别
                llTypeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);

                showTypeLayout();
                break;
            case R.id.btn_drawer_layout_cancel:
                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_cancel:
                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_confirm:
                Toast.makeText(GoodsListActivity.this, "确认", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_drawer_theme_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;

        }
    }

    //筛选页面
    private void showSelectorLayout() {
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }


    //价格页面
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();

    }

    private ArrayList<String> group;//父级标签
    private ArrayList<List<String>> child;

    private void initExpandableListView() {
        group = new ArrayList<>();
        child = new ArrayList<>();

        //添加数据
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});


        //设置适配器
        expandableListViewAdapter = new ExpandableListViewAdapter(this, group, child);
        expandableListView.setAdapter(expandableListViewAdapter);


        //点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {
                    return true;//如果父级标签下没有数据的话，则不打开
                } else {
                    return false;
                }

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                //传入点击相应的id
                expandableListViewAdapter.isChildSelectable(groupPosition, childPosition);

                //刷新适配器
                expandableListViewAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    /**
     * 添加数据
     *
     * @param s
     * @param c
     */
    private void addInfo(String s, String[] c) {
        group.add(s);//添加父级标签

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }

        child.add(list);
    }


}
