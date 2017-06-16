package com.atguigu.myshopmall.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myshopmall.MainActivity;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.home.adapter.HomeAdapter;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.shoppingcart.utils.AddSubView;
import com.atguigu.myshopmall.shoppingcart.utils.CartStorage;
import com.atguigu.myshopmall.util.Constants;
import com.atguigu.myshopmall.util.VirtualkeyboardHeight;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GoodsInfoActivity extends AppCompatActivity {

    @InjectView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @InjectView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @InjectView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @InjectView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @InjectView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @InjectView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @InjectView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @InjectView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @InjectView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @InjectView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @InjectView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @InjectView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @InjectView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @InjectView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @InjectView(R.id.tv_more_home)
    TextView tvMoreHome;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;

    private GoodsBean goodsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.inject(this);

        getData();

        //设置数据
        setData();
    }

    //取出数据
    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);

    }

    private void setData() {
        //设置图片和名字和价格的数据
        String coverPrice = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String productId = goodsBean.getProduct_id();
        String name = goodsBean.getName();

        //设置图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        tvGoodInfoName.setText(name);
        tvGoodInfoPrice.setText("￥" + coverPrice);

        //设置webView的数据
        setWebViewData(productId);

    }

    private void setWebViewData(String productId) {
        WebSettings webSettings = wbGoodInfoMore.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);

        //设置检索缓存的
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置不跳转到系统的浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient());
        wbGoodInfoMore.loadUrl("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_callcenter:
//                Toast.makeText(this, "呼叫中心", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, CallCenterActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                //Toast.makeText(this, "进入购物车", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyApplication.getContext(),MainActivity.class);
                intent.putExtra("checkId",R.id.rb_cart);
                startActivity(intent);

                break;
            case R.id.btn_good_info_addcart:
                Toast.makeText(this, "添加到购物车", Toast.LENGTH_SHORT).show();
//                //验证代码
//                CartStorage.getInstance(MyApplication.getContext()).addData(goodsBean);
                showPopwindow();


                break;
            case R.id.tv_more_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
                break;
        }
    }


//    private GoodsBean tempGoodBean;
    /**
     * 显示popupWiddow
     */
    private void showPopwindow() {

//        tempGoodBean = CartStorage.getInstance(MyApplication.getContext()).findData(Integer.parseInt(goodsBean.getProduct_id()));


        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        //2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window  = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        //3设置参数
        //设置popuwindow弹出窗口
        window.setFocusable(true);

        //设置显示和消失的动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);

        //4控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(goodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(goodsBean.getCover_price());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);
        //内存数据
        goodsBean.setNumber(1);
        //显示的
        nas_goodinfo_num.setValue(goodsBean.getNumber());

        //监听数字的价格变化
        nas_goodinfo_num.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void numberChange(int value) {
                goodsBean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();//点击取消popuwindow消失
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加购物车
                CartStorage.getInstance(MyApplication.getContext()).addData(goodsBean);
                Log.e("TAG", "66:" + goodsBean.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        //5在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root), Gravity.BOTTOM,
                0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));
    }
}
