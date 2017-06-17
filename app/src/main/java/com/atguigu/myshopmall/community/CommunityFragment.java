package com.atguigu.myshopmall.community;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.community.adapter.CommunityViewPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/11.
 */

public class CommunityFragment extends BaseFragment {
    @InjectView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @InjectView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "CommunityFragment--initView");

        View view = View.inflate(mContext, R.layout.fragment_community, null);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void initData() {

        super.initData();

        adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);

        //如果有多个ViewPager页面
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_community_icon, R.id.ib_community_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:
                Toast.makeText(mContext, "icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_community_message:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
