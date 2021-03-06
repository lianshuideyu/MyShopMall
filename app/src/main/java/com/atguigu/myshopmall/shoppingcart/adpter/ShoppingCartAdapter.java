package com.atguigu.myshopmall.shoppingcart.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.app.MyApplication;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.shoppingcart.utils.AddSubView;
import com.atguigu.myshopmall.shoppingcart.utils.CartStorage;
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

    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    private final CheckBox cbAll;

    public ShoppingCartAdapter(Context mContext, ArrayList<GoodsBean> datas, CheckBox checkboxAll, TextView tvShopcartTotal, CheckBox checkbox_delete_all) {
        this.mContext = mContext;
        this.datas = datas;

        this.checkboxAll = checkboxAll;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cbAll = checkbox_delete_all;
        //显示总价格
        showTotalPrice();

        //检查是否全部勾选
        checkAll();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" + getTotalPrice());
    }

    public double getTotalPrice() {
        double result = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //判断是否勾选
                if (goodsBean.isChecked()) {
                    result += goodsBean.getNumber() * Double.parseDouble(goodsBean.getCover_price());
                }

            }
        }

        return result;
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
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());

        holder.addSubView.setMinValue(1);
        //设置库存
        holder.addSubView.setMaxValue(20);


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    /**
     * 删除购物车数据
     */
    public void deleteData() {
        if(datas != null && datas.size() > 0) {
            Log.e("TAG","deleteData--datas.size()==" + datas.size());
            for(int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()) {
                    datas.remove(goodsBean);
                    Log.e("TAG","datas.size()==" + datas.size());
                    notifyItemRemoved(i);

                    CartStorage.getInstance(MyApplication.getContext()).deleteData(goodsBean);
                    i--;
                }

            }

        }

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
        com.atguigu.myshopmall.shoppingcart.utils.AddSubView addSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //勾选的状态取反
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());

                    //刷新适配器
                    notifyItemChanged(getLayoutPosition());

                    //点击勾选按键时重新刷新价格
                    showTotalPrice();

                    //判断是否全部勾选
                    checkAll();
                }
            });

            /**
             * 购物车单条数据的加减改变总价并保存数据
             */
            addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
                @Override
                public void numberChange(int value) {
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setNumber(value);

                    //点击勾选按键时重新刷新价格
                    showTotalPrice();

                    //保存
                    CartStorage.getInstance(MyApplication.getContext()).updateData(goodsBean);
                }
            });
        }
    }



    /**
     * 设置是否选择
     *
     * @param checked
     */
    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {

            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setChecked(checked);
                notifyItemChanged(i);
            }
        }else {
            checkboxAll.setChecked(false);
        }
    }

    /**
     * 判断是否全部选中
     */
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            //有数据
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    //如果存在没有勾选的数据
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                    break;//跳出循环
                } else {
                    number++;
                }

            }

            if (number == datas.size()) {
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }

        } else {//没有数据
            //设置勾选取消
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);

        }


    }
}
