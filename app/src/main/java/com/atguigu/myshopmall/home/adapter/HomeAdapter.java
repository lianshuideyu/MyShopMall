package com.atguigu.myshopmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myshopmall.R;
import com.atguigu.myshopmall.app.GoodsInfoActivity;
import com.atguigu.myshopmall.home.activity.GoodsListActivity;
import com.atguigu.myshopmall.home.activity.WebViewActivity;
import com.atguigu.myshopmall.home.bean.GoodsBean;
import com.atguigu.myshopmall.home.bean.HomeBean;
import com.atguigu.myshopmall.home.bean.WebViewBean;
import com.atguigu.myshopmall.home.util.GlideImageLoader;
import com.atguigu.myshopmall.home.view.MyGridView;
import com.atguigu.myshopmall.util.Constants;
import com.atguigu.myshopmall.util.DensityUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by Administrator on 2017/6/12.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    private final HomeBean.ResultBean resultBean;
    private final Context mContext;

    /**
     * 六种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;

    private LayoutInflater inflater;

    public static final String GOODS_BEAN = "goods_bean";
    public static final String WEBVIEW_BEAN = "webview_bean";

    public HomeAdapter(Context mContext, HomeBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据
            bannerViewHolder.setData(resultBean.getBanner_info());

        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());

        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());

        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());

        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());

        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }


        return currentType;
    }

    @Override
    public int getItemCount() {
        //全部写完的时候修改成6，只实现一个类型的话就返回1
        return 6;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());

            }

            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {

                            //Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();

                            if(position < banner_info.size()){
                                String product_id = "";
                                String name = "";
                                String cover_price = "";
                                if (position == 0) {
                                    product_id = "627";
                                    cover_price = "32.00";
                                    name = "剑三T恤批发";
                                } else if (position == 1) {
                                    product_id = "21";
                                    cover_price = "8.00";
                                    name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                                } else {
                                    product_id = "1341";
                                    cover_price = "50.00";
                                    name = "【蓝诺】《天下吾双》 剑网3同人本";
                                }
                                String image = banner_info.get(position).getImage();
                                GoodsBean goodsBean = new GoodsBean();
                                goodsBean.setName(name);
                                goodsBean.setCover_price(cover_price);
                                goodsBean.setFigure(image);
                                goodsBean.setProduct_id(product_id);

                                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                                intent.putExtra(GOODS_BEAN, goodsBean);
                                mContext.startActivity(intent);
                            }
                        }
                    })
                    .start();
        }
    }

    private class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private final GridView gv;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv = (GridView) itemView.findViewById(R.id.gv);
        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            ChannelAdapter adapter = new ChannelAdapter(mContext, channel_info);

            gv.setAdapter(adapter);

            //点击事件

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    HomeBean.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
//                    Toast.makeText(mContext, "" + channelInfoBean.getChannel_name(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("position", position);//位置传过去
                    mContext.startActivity(intent);

                }
            });
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {

        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {

            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext, act_info);
            act_viewpager.setAdapter(adapter);

            //设置pager的间距
            act_viewpager.setPageMargin(DensityUtil.dip2px(mContext, 20));

            //利用接口实现点击事件
            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    Toast.makeText(mContext, "" + actInfoBean.getName(), Toast.LENGTH_SHORT).show();

                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setName(actInfoBean.getName());
                    webViewBean.setIcon_url(actInfoBean.getIcon_url());
                    webViewBean.setUrl(Constants.BASE_URL_IMAGE+actInfoBean.getUrl());

                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEBVIEW_BEAN,webViewBean);
                    mContext.startActivity(intent);
                }
            });
            //翻动页面的特效优化
            act_viewpager.setPageTransformer(true, new RotateYTransformer());
        }
    }

    /**
     * 是否为第一次倒计时
     */
    private boolean isFirst = false;
    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        private RecyclerView recyclerView;
        public Context mContext;
        private CountdownView countdownView;

        private long dt;
        private HomeBean.ResultBean.SeckillInfoBean seckillinfo;

        private Handler handler = new Handler();
        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            countdownView = (CountdownView) itemView.findViewById(R.id.countdownview);

            this.mContext = mContext;

        }

        public void setData(final HomeBean.ResultBean.SeckillInfoBean seckill_info) {

            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);

            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);

            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
                }
            });


            adapter.setOnItemClickListener(new SeckillRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void setOnItemClick(int positon) {
                    HomeBean.ResultBean.SeckillInfoBean.ListBean infoBean = seckill_info.getList().get(positon);
                    //Toast.makeText(mContext, "" + infoBean.getName(), Toast.LENGTH_SHORT).show();
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(infoBean.getName());
                    goodsBean.setCover_price(infoBean.getCover_price());
                    goodsBean.setFigure(infoBean.getFigure());
                    goodsBean.setProduct_id(infoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);
                }
            });


            //设置时间
            //秒杀倒计时 -毫秒
            /*dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            countdownView.start(dt);*/
            this.seckillinfo = seckill_info;
            if(!isFirst) {//第一次进来
                isFirst = true;

                long totalTime = Long.parseLong(seckill_info.getEnd_time()) - Long.parseLong(seckill_info.getStart_time());
                long currentTime = System.currentTimeMillis();
                //同步时间
                seckillinfo.setEnd_time(currentTime + totalTime + "");
                //开始刷新时间
                freshTime();
            }

        }

        private void freshTime(){
            handler.postDelayed(myRunnable,10);
        }

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if(currentTime > Long.parseLong(seckillinfo.getEnd_time())) {
                    handler.removeCallbacksAndMessages(null);
                }else {
                    countdownView.updateShow(Long.parseLong(seckillinfo.getEnd_time()) - currentTime);
                    handler.postDelayed(myRunnable,1000);

                }

            }
        };
    }

    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private TextView tvMoreRecommend;
        private GridView gvRecommend;


        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;

            tvMoreRecommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gvRecommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
            tvMoreRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
                }
            });

            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext,recommend_info);
            gvRecommend.setAdapter(adapter);

            //点击事件
            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //Toast.makeText(mContext, "" + recommend_info.get(position).getName(), Toast.LENGTH_SHORT).show();

                    HomeBean.ResultBean.RecommendInfoBean infoBean = recommend_info.get(position);

                    //传递数据
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(infoBean.getName());
                    goodsBean.setCover_price(infoBean.getCover_price());
                    goodsBean.setFigure(infoBean.getFigure());
                    goodsBean.setProduct_id(infoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private TextView tv_more_hot;
        private MyGridView gv_hot;
        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;

            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (MyGridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            tv_more_hot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
                }
            });

            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //Toast.makeText(mContext, "" + hot_info.get(position).getName(), Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.HotInfoBean data = hot_info.get(position);
                    Toast.makeText(mContext, "" + data.getName(), Toast.LENGTH_SHORT).show();

                    String cover_price = data.getCover_price();
                    String name = data.getName();
                    String figure = data.getFigure();
                    String product_id = data.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(cover_price, figure,name, product_id);

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
