package com.atguigu.myshopmall.app;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.atguigu.myshopmall.R;

public class CallCenterActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);

        webView = (WebView)findViewById(R.id.webView);
        progressbar = (ProgressBar)findViewById(R.id.progressbar);

        WebSettings settings = webView.getSettings();

        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);


        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });

        webView.loadUrl("http://www6.53kf.com/webCompany.php?arg=10007377&style=1&kflist=off&kf=info@atguigu.com,video@atguigu.com,public@atguigu.com,3069368606@qq.com,215648937@qq.com,sudan@atguigu.com,wangya@atguigu.com,zhuchangqing@atguigu.com,wanggang@atguigu.com&zdkf_type=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fwww.atguigu.com%2F&keyword=&tfrom=1&tpl=crystal_blue&uid=0b5f389ff532a4bf22e97a029d39d8a7&timeStamp=1497588921650&ucust_id=");
    }
}
