package com.atguigu.myshopmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.home.bean.TypeListBean;
import com.atguigu.myshopmall.util.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/19.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {

    private final List<TypeListBean.ResultBean.PageDataBean> page_data;
    private final Context context;


    public GoodsListAdapter(Context context, List<TypeListBean.ResultBean.PageDataBean> page_data) {
        this.context = context;
        this.page_data = page_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_goods_list, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TypeListBean.ResultBean.PageDataBean pageDataBean = page_data.get(position);

        holder.tvName.setText(pageDataBean.getName());
        holder.tvPrice.setText("￥" + pageDataBean.getCover_price());

        Glide.with(context)
                .load(Constants.BASE_URL_IMAGE + pageDataBean.getFigure())
                .placeholder(R.drawable.new_img_loading_2)
                .error(R.drawable.new_img_loading_2)
                .into(holder.ivHot);
    }

    @Override
    public int getItemCount() {
        return page_data == null ? 0 : page_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_hot)
        ImageView ivHot;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(page_data.get(getLayoutPosition()));
                    }

                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置item的点击事件的监听
     */
    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data);
    }

    /**
     * 设置监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
