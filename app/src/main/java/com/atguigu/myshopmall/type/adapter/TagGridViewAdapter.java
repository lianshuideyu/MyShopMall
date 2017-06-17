package com.atguigu.myshopmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.type.bean.TagBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/17.
 */

public class TagGridViewAdapter extends BaseAdapter {


    private final Context mContext;
    private final List<TagBean.ResultBean> datas;

    private int[] colors = {
            Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<TagBean.ResultBean> result) {
        this.mContext = mContext;
        this.datas = result;

    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public TagBean.ResultBean getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tag_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TagBean.ResultBean resultBean = datas.get(position);
        viewHolder.tvTag.setText(resultBean.getName());
        viewHolder.tvTag.setTextColor(colors[position  % colors.length]);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);

        }
    }
}
