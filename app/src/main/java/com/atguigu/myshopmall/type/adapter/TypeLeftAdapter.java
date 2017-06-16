package com.atguigu.myshopmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.myshopmall.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/16.
 */

public class TypeLeftAdapter extends BaseAdapter {


    private final Context mContext;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};

    public TypeLeftAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_left_item, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(prePosition == position) {
            //设置为高亮
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.tv.setTextColor(Color.RED);
        }else {
            //设置为默认#F2F2F2
            convertView.setBackgroundColor(Color.parseColor("#F2F2F2"));
            viewHolder.tv.setTextColor(Color.BLACK);
        }

        viewHolder.tv.setText(titles[position]);

        return convertView;
    }

    //记录高亮的下标
    private int prePosition;//默认为0
    /**
     * 点击某一条为高亮
     * @param position
     */
    public void selectPosition(int position) {
        this.prePosition = position;
    }

    static class ViewHolder {
        @InjectView(R.id.tv)
        TextView tv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
