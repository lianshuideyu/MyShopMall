package com.atguigu.addsubview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

import static com.atguigu.addsubview.R.id.iv_add;
import static com.atguigu.addsubview.R.id.iv_sub;

/**
 * Created by Administrator on 2017/6/14.
 */

public class AddSubView extends LinearLayout {


    private final Context mContext;

    @butterknife.InjectView(iv_sub)
    ImageView ivSub;
    @butterknife.InjectView(R.id.tv_value)
    TextView tvValue;
    @butterknife.InjectView(iv_add)
    ImageView ivAdd;

    private int value = 1;
    private int maxValue = 10;
    private int minValue = 1;

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = View.inflate(context, R.layout.add_sub_view, this);
        ButterKnife.inject(this,view);

        if(attrs != null) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(mContext, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if(value > 0) {
                setValue(value);
            }

            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                ivAdd.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                ivSub.setImageDrawable(subDrawable);
            }

        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvValue.setText(value + "");
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;

    }

    @butterknife.OnClick({iv_sub, iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case iv_sub:
                //减数
                //Toast.makeText(mContext, "减减", Toast.LENGTH_SHORT).show();
                subNumber();
                break;
            case iv_add:
                //增加
                //Toast.makeText(mContext, "增加", Toast.LENGTH_SHORT).show();
                addNumber();

                break;
        }
        if(changeListener != null) {
            changeListener.numberChange(value);
        }
        //Toast.makeText(mContext, "value==" +value, Toast.LENGTH_SHORT).show();
    }

    private void addNumber() {
        if(value   < maxValue){
            value++;
        }
        tvValue.setText(value+"");

    }

    /**
     * 减
     */
    private void subNumber() {
        if(value > minValue){
            value--;
        }
        tvValue.setText(value+"");
    }

    /**
     * 接口回调
     */
    public interface OnNumberChangeListener {
        /**
         *  当按钮被点击的时候回调
         */
        public void numberChange(int value);
    }

    private OnNumberChangeListener changeListener;

    /**
     * 设置监听数据变化
     * @param l
     */
    public void setOnNumberChangeListener(OnNumberChangeListener l) {
        this.changeListener = l;
    }

}
