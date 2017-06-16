package com.atguigu.myshopmall.shoppingcart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myshopmall.MainActivity;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.app.MyApplication;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.shoppingcart.adpter.ShoppingCartAdapter;
import com.atguigu.myshopmall.shoppingcart.utils.CartStorage;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ShoppingCartFragment extends BaseFragment {


    @InjectView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @InjectView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @InjectView(R.id.btn_check_out)
    Button btnCheckOut;
    @InjectView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @InjectView(R.id.checkbox_delete_all)
    CheckBox checkbox_delete_all;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_collection)
    Button btnCollection;
    @InjectView(R.id.ll_delete)
    LinearLayout llDelete;
    @InjectView(R.id.iv_empty)
    ImageView ivEmpty;
    @InjectView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @InjectView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    private ArrayList<GoodsBean> datas;
    private ShoppingCartAdapter adapter;


    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);

        Log.e("TAG", "ShoppingCartFragment--initView");
        ButterKnife.inject(this, view);

        //初始先设置编辑状态的tag
        tvShopcartEdit.setTag(ACTION_EDIT);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                //Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                int tag = (int) tvShopcartEdit.getTag();
                if(tag == ACTION_EDIT) {
                    //切换到完成字样--编辑页面
                    showDelete();
                }else {
                    hideDelete();
                }

                break;
            case R.id.checkbox_all:
                //Toast.makeText(mContext, "结算的全选/反选", Toast.LENGTH_SHORT).show();
                boolean checked = checkboxAll.isChecked();
                //设置是否选择
                adapter.checkAll_none(checked);

                //重新核对价格
                adapter.showTotalPrice();
                break;
            case R.id.btn_check_out:
                Toast.makeText(mContext, "结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_delete_all:
                //Toast.makeText(mContext, "删除的全选/反选", Toast.LENGTH_SHORT).show();
                boolean ischecked = checkbox_delete_all.isChecked();
                //设置是否选择
                adapter.checkAll_none(ischecked);

                //重新核对价格
               // adapter.showTotalPrice();

                break;
            case R.id.btn_delete:
                //Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();

                adapter.deleteData();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
//                Toast.makeText(mContext, "去逛逛", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyApplication.getContext(),MainActivity.class);
                intent.putExtra("checkId",R.id.rb_home);
                startActivity(intent);
                break;
        }
    }

    private void showEempty() {
        if(datas == null || datas.size() == 0) {

            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");

        tvShopcartEdit.setTag(ACTION_EDIT);

        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);

        adapter.checkAll_none(true);

        adapter.checkAll();

        adapter.showTotalPrice();
    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");

        tvShopcartEdit.setTag(ACTION_COMPLETE);

        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);

        adapter.checkAll_none(false);

        adapter.checkAll();

        adapter.showTotalPrice();

    }

    @Override
    public void initData() {
        super.initData();

        showData();
    }

    private void showData() {
        datas = CartStorage.getInstance(MyApplication.getContext()).getAllData();

        if (datas != null && datas.size() > 0) {
            //有数据-隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new ShoppingCartAdapter(mContext,datas,checkboxAll,tvShopcartTotal,checkbox_delete_all);

            recyclerview.setAdapter(adapter);
            //设置布局管理
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        }else {
            //没有数据-显示没有数据的页面
            llEmptyShopcart.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //当屏幕重新聚焦的时候，刷新页面数据
        showData();
    }
}
