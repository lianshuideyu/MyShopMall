package com.atguigu.myshopmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.type.bean.ListBean;
import com.atguigu.myshopmall.util.Constants;
import com.atguigu.myshopmall.util.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/17.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {


    private final Context mContext;
    private final List<ListBean.ResultBean.ChildBean> child;
    private final List<ListBean.ResultBean.HotProductListBean> hot_product_list;


    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;


    /**
     * 当前类型
     */
    private int currentType = HOT;

    public TypeRightAdapter(Context mContext, List<ListBean.ResultBean> result) {
        this.mContext = mContext;
        child = result.get(0).getChild();
        hot_product_list = result.get(0).getHot_product_list();

    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = COMMON;
        }

        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            View view = View.inflate(mContext, R.layout.item_hot_right, null);
            return new HotViewHolder(view);
        } /*else if (viewType == COMMON) {
            View view = View.inflate(mContext, R.layout.item_hot_right, null);
            return new HotViewHolder(view);
        }*/

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(hot_product_list);

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

        }

        public void setData(List<ListBean.ResultBean.HotProductListBean> hot_product_list) {

            for(int i = 0; i < hot_product_list.size(); i++) {
                //得到数据
                final ListBean.ResultBean.HotProductListBean listBean = hot_product_list.get(i);

                //线性布局
                LinearLayout myLinear = new LinearLayout(mContext);
                //-2代表包裹，-1代表填充
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2);
                //上下分布
                myLinear.setOrientation(LinearLayout.VERTICAL);
                myLinear.setGravity(Gravity.CENTER);
                params.setMargins(DensityUtil.dip2px(mContext,10),0,0,0);

                //后边点击事件会用到
                myLinear.setTag(i);

                //图片----------------
                ImageView imageView = new ImageView(mContext);

                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,80), DensityUtil.dip2px(mContext,80));
                ivParams.setMargins(0,0,0,DensityUtil.dip2px(mContext,10));

                //加载图片
                Glide.with(mContext)
                        .load(Constants.BASE_URL_IMAGE+ listBean.getFigure())
                        .into(imageView);
                //将图片添加到线性布局，包含布局参数
                myLinear.addView(imageView,ivParams);


                //文字------------
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
//                textView.setTextColor(Color.RED);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + listBean.getCover_price());

                myLinear.addView(textView,tvParams);

                //最后将里边的线性布局添加到外边的水平线性布局
                llHotRight.addView(myLinear,params);

                //设置点击事件
                myLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();
                        Toast.makeText(mContext, "" + listBean.getCover_price(), Toast.LENGTH_SHORT).show();


                    }
                });

            }

        }
    }
}
