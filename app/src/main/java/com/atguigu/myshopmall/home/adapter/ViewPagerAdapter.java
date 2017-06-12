package com.atguigu.myshopmall.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.myshopmall.home.bean.HomeBean;
import com.atguigu.myshopmall.util.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<HomeBean.ResultBean.ActInfoBean> datas;
    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        HomeBean.ResultBean.ActInfoBean actInfoBean = datas.get(position);
        //联网请求加载图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+actInfoBean.getIcon_url()).into(imageView);

        container.addView(imageView);

        //利用接口这是item的点击事件，因为viewpager没有自带点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null) {
                    clickListener.OnItemClick(position);
                }
            }
        });

        return imageView;
    }

    /**
     * 利用接口设置点击事件
     */
    public interface OnItemClickListener{
        public void OnItemClick(int position);
    }
    public OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener l){
        this.clickListener = l;
    }

}
