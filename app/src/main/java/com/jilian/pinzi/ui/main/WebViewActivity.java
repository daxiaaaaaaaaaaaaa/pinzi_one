package com.jilian.pinzi.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.tamic.jswebview.browse.JsWeb.CustomWebViewClient;
import com.tamic.jswebview.view.ProgressBarWebView;

import java.util.Map;

public class WebViewActivity extends BaseActivity {
    private  ProgressBarWebView mProgressBarWebView;
    @Override
    protected void createViewModel() {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }
    @Override
    public int intiLayout() {
        return R.layout.layout_webview;
    }

    @Override
    public void initView() {
        mProgressBarWebView = (ProgressBarWebView) findViewById(R.id.login_progress_webview);
        mProgressBarWebView.setWebViewClient(new CustomWebViewClient(mProgressBarWebView.getWebView()) {
            @Override
            public String onPageError(String url) {
                //指定网络加载失败时的错误页面
                return null;
            }

            @Override
            public Map<String, String> onPageHeaders(String url) {

                // 可以加入header

                return null;
            }


        });

        // 打开页面，也可以支持网络url
        String linkUrl = getIntent().getStringExtra("linkUrl");
        if(linkUrl!=null&&!linkUrl.startsWith("http")){
            linkUrl = "http://"+linkUrl;
        }
        mProgressBarWebView.loadUrl(linkUrl);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
