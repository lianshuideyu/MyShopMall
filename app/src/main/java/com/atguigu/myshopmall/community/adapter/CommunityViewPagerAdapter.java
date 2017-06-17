package com.atguigu.myshopmall.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.community.fragment.HotPostFragment;
import com.atguigu.myshopmall.community.fragment.NewPostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm) {
        super(fm);

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewPostFragment());
        fragments.add(new HotPostFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
