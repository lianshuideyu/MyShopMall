package com.atguigu.myshopmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.util.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/14.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private static Context mContext;

    /**
     * 数据存储在内存中
     */
    private SparseArray<GoodsBean> sparseArray;

    public CartStorage() {
        //初始化集合

        sparseArray = new SparseArray<>();
        //得到数据
        listTosparseArray();
    }

    private void listTosparseArray() {
        //得到所有数据
        ArrayList<GoodsBean> datas = getAllData();
        for(int i = 0; i < datas.size(); i++) {

            GoodsBean goodsBean = datas.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 得到所有数据
     * @return
     */
    public ArrayList<GoodsBean> getAllData() {

        return getLocalData();
    }

    /**
     * 得到本地缓存数据
     * @return
     */
    public ArrayList<GoodsBean> getLocalData() {
        ArrayList<GoodsBean> datas = new ArrayList<>();
        //json数据
        String saveJson = CacheUtils.getString(mContext, JSON_CART);
        if(!TextUtils.isEmpty(saveJson)) {
            //把保存的json数据解析成arraylist数据
            datas = new Gson().fromJson(saveJson, new TypeToken<ArrayList<GoodsBean>>() {
            }.getType());

        }

        return datas;
    }

    /**
     * 得到CartStorage
     *
     * @return
     */
    public static CartStorage getInstance(Context context){
        if(instance == null) {
            mContext = context;
            synchronized (CartStorage.class){
                if(instance == null) {
                    instance = new CartStorage();
                }
            }
        }

        return instance;
    }

    /**
     * 添加数据
     * @param bean
     */
    public void addData(GoodsBean bean){
        //查看内容是否存在
        GoodsBean temp = sparseArray.get(Integer.parseInt(bean.getProduct_id()));
        if(temp != null) {
            //存在。就修改
            temp.setNumber(bean.getNumber() + temp.getNumber());

        }else{
            //如果不存在，就保存到内存中
            temp = bean;
        }

        //更新内存中的内容
        sparseArray.put(Integer.parseInt(temp.getProduct_id()), temp);

        //同步到本地
        commit();
    }

    /**
     * 同步到本地,在本地保存一份
     */
    private void commit() {
        //把sparseArray转换成List集合
        ArrayList<GoodsBean> goodsBeens = sparseArrayToList();

        //使用Gson把List集合转换成json的String数据
        String json = new Gson().toJson(goodsBeens);
        //把文本保存到sp中
        CacheUtils.setString(mContext,JSON_CART,json);
    }

    /**
     * 把sparseArray转换成List集合
     * @return
     */
    private ArrayList<GoodsBean> sparseArrayToList() {
        ArrayList<GoodsBean> goodsBeans = new ArrayList<>();
        for(int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeans.add(goodsBean);

        }

        return goodsBeans;
    }

    /**
     * 删除数据
     * @param bean
     */
    public void deleteData(GoodsBean bean){
        //内存中更新
        sparseArray.delete(Integer.parseInt(bean.getProduct_id()));
        //同步到本地
        commit();
    }

    /**
     * 更新数据
     * @param bean
     */
    public void updateData(GoodsBean bean){
        //内存中更新
        sparseArray.put(Integer.parseInt(bean.getProduct_id()),bean);
        //同步到本地
        commit();
    }

    /*public GoodsBean findData(int id) {
        GoodsBean goodsBean = sparseArray.get(id);


        GoodsBean newgoodsBean = new GoodsBean();
        newgoodsBean.setChecked(goodsBean.isChecked());
        newgoodsBean.setNumber(goodsBean.getNumber());
        newgoodsBean.setFigure(goodsBean.getFigure());
        newgoodsBean.setProduct_id(goodsBean.getProduct_id());
        newgoodsBean.setCover_price(goodsBean.getCover_price());
        newgoodsBean.setName(goodsBean.getName());

        return newgoodsBean;
    }*/
}
