package com.atguigu.myshopmall;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.home.fragment.CommunityFragment;
import com.atguigu.myshopmall.home.fragment.HomeFragment;
import com.atguigu.myshopmall.home.fragment.ShoppingCartFragment;
import com.atguigu.myshopmall.home.fragment.TypeFragment;
import com.atguigu.myshopmall.home.fragment.UserFragemnt;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position;
    private BaseFragment tempFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //准备数据
        initFragment();

        rgMain.setOnCheckedChangeListener(this);

        //默认选择
        rgMain.check(R.id.rb_home);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragemnt());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_home :
                position = 0;
                break;
            case R.id.rb_type :
                position = 1;
                break;
            case R.id.rb_community :
                position = 2;
                break;
            case R.id.rb_cart :
                position = 3;
                break;
            case R.id.rb_user :
                position = 4;
                break;
        }
//        Log.e("TAG","position=" + position);
        addFragment(position);
    }

    private void addFragment(int position) {
        if(fragments != null) {
            BaseFragment currentFragment = fragments.get(position);
            //开启事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(tempFragment != currentFragment) {
                //隐藏之前的
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //若没有则添加
                if(!currentFragment.isAdded()) {
                    ft.add(R.id.frameLayout,currentFragment);
                }else {
                    //显示当前的
                    ft.show(currentFragment);
                }

            }
            ft.commit();
            tempFragment = currentFragment;
        }

    }
}
