package com.atguigu.myshopmall.type;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.base.BaseFragment;
import com.atguigu.myshopmall.type.adapter.TypeLeftAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ListFragment extends BaseFragment {
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.rv)
    RecyclerView rv;
    private TypeLeftAdapter leftAdapter;

    @Override
    public View initView() {
        Log.e("TAG", "TypeFragment--initView");

        View view = View.inflate(mContext, R.layout.list_fragment, null);
        ButterKnife.inject(this, view);
        //leftlistview的点击事件
        listview.setOnItemClickListener(new MyOnItemClick());

        return view;
    }

    @Override
    public void initData() {
//        Log.e("TAG","TypeFragment--initView");
        super.initData();

        leftAdapter = new TypeLeftAdapter(mContext);
        listview.setAdapter(leftAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class MyOnItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //点击某一条变为高亮
            leftAdapter.selectPosition(position);

            //刷新适配器
            leftAdapter.notifyDataSetChanged();
        }
    }
}
