package com.atguigu.myshopmall.shoppingcart.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.util.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<GoodsBean> datas;


    public ShoppingCartAdapter(Context mContext, ArrayList<GoodsBean> datas) {
        this.mContext = mContext;
        this.datas = datas;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //1.更加位置得到数据
        GoodsBean goodsBean = datas.get(position);
        //2.绑定数据
        holder.cbGov.setChecked(goodsBean.isChecked());
        //图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥"+goodsBean.getCover_price());
        holder.AddSubView.setValue(goodsBean.getNumber());

        holder.AddSubView.setMinValue(1);
        //设置库存
        holder.AddSubView.setMaxValue(20);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.AddSubView)
        com.atguigu.myshopmall.shoppingcart.utils.AddSubView AddSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //勾选的状态取反
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());

                    //刷新适配器
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }
    }
}
