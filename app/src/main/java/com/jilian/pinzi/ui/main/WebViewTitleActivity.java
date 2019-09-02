package com.jilian.pinzi.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.UrlUtils;
import com.tamic.jswebview.browse.JsWeb.CustomWebViewClient;
import com.tamic.jswebview.view.ProgressBarWebView;

import java.util.Map;

public class WebViewTitleActivity extends BaseActivity {
    private ProgressBarWebView mProgressBarWebView;

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
        return R.layout.layout_webview_title;
    }

    @Override
    public void initView() {
        setNormalTitle(getIntent().getStringExtra("title"), v -> finish());
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
        if (linkUrl != null && !linkUrl.startsWith("http")) {
            linkUrl = "http://" + linkUrl;
        }
        linkUrl = UrlUtils.addUrlParamsNoEncode(linkUrl,"id",PinziApplication.getInstance().getLoginDto().getId());
        Log.e(TAG, "linkUrl: "+linkUrl );
        mProgressBarWebView.loadUrl(linkUrl);
    }

    /**
     * js可以调用该类的方法
     */
    class AndroidAndJSInterface {
        @JavascriptInterface
        public void logOut(int code) {
            if (code == 403) {
                PinziApplication.getInstance().logout("您的账号登录已失效，请重新登录");
            }
            if (code == 401) {
                PinziApplication.getInstance().logout("您的账号被删除或被禁用");
            }

        }

        @JavascriptInterface
        public String getSessionId() {
            return PinziApplication.getInstance().getLoginDto().getSessionId();
        }

        @JavascriptInterface
        public String getPhone() {
            return PinziApplication.getInstance().getLoginDto().getPhone();
        }
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
