package com.atguigu.myshopmall.type;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.myshopmall.MainActivity;
import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/11.
 */

public class TypeFragment extends BaseFragment {
    @InjectView(R.id.tl_4)
    SegmentTabLayout tl4;
    @InjectView(R.id.iv_type_search)
    ImageView ivTypeSearch;


    private ArrayList<BaseFragment> fragments;

    private BaseFragment tempFragment;
    private String[] titles = {"分类", "标签"};

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.type_fragment, null);
        Log.e("TAG", "TypeFragment--initView");
        ButterKnife.inject(this, view);

        initFragment();

        return view;
    }

    private void initFragment() {
        fragments = new ArrayList<>();

        fragments.add(new ListFragment());
        fragments.add(new TagFragment());

        //默认选择
        switchFragment(fragments.get(0));
    }

    @Override
    public void initData() {
//        Log.e("TAG","TypeFragment--initView");
        super.initData();

        tl4.setTabData(titles);

        tl4.setOnTabSelectListener(new MyOnTabSelectListener());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick(R.id.iv_type_search)
    public void onViewClicked() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }


    private class MyOnTabSelectListener implements OnTabSelectListener {
        @Override
        public void onTabSelect(int position) {
            switchFragment(fragments.get(position));
        }

        @Override
        public void onTabReselect(int position) {

        }
    }

    private void switchFragment(BaseFragment currentFragment) {
        MainActivity mainActivity = (MainActivity) mContext;
        //开启事务
        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
        if (tempFragment != currentFragment) {
            if (tempFragment != null) {
                ft.hide(tempFragment);
            }

            if (!currentFragment.isAdded()) {
                ft.add(R.id.fl_change, currentFragment);
            } else {
                ft.show(currentFragment);
            }

            ft.commit();
            tempFragment = currentFragment;
        }

    }
}
