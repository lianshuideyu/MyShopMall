package com.atguigu.myshopmall.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.community.bean.NewPostBean;
import com.atguigu.myshopmall.util.Constants;
import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/17.
 */

public class NewPostListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewPostBean.ResultBean> result;

    public NewPostListViewAdapter(Context mContext, List<NewPostBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;

    }

    @Override
    public int getCount() {
        return result == null ? 0 : result.size();
    }

    @Override
    public NewPostBean.ResultBean getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        NewPostBean.ResultBean resultBean = result.get(position);

        //加载用户头像
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE + resultBean.getAvatar())
                .into(viewHolder.ibNewPostAvatar);

        viewHolder.tvCommunityUsername.setText(resultBean.getUsername());

        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE + resultBean.getFigure())
                .into(viewHolder.ivCommunityFigure);

        viewHolder.tvCommunitySaying.setText(resultBean.getSaying());
        viewHolder.tvCommunityLikes.setText(resultBean.getLikes());

        //设置弹幕
        List<String> comment_list = resultBean.getComment_list();
        if (comment_list != null && comment_list.size() > 0) {

            List<IDanmakuItem> list = new ArrayList<>();
            for (int i = 0; i < comment_list.size(); i++) {

                list.add(new DanmakuItem(mContext, comment_list.get(i), viewHolder.danmakuView.getWidth()));
            }

            //随机
            Collections.shuffle(list);

            viewHolder.danmakuView.addItem(list, true);

            viewHolder.danmakuView.show();
        }else {

            //hide and pause playing:

            viewHolder.danmakuView.hide();
            viewHolder.danmakuView.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @InjectView(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @InjectView(R.id.rl)
        RelativeLayout rl;
        @InjectView(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;
        @InjectView(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @InjectView(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @InjectView(R.id.tv_community_likes)
        TextView tvCommunityLikes;
        @InjectView(R.id.tv_community_comments)
        TextView tvCommunityComments;

        private DanmakuView danmakuView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);

            danmakuView = (DanmakuView) view.findViewById(R.id.danmakuView);

        }
    }
}
