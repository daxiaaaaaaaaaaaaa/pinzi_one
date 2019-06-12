package com.jilian.pinzi.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsCommentAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainNewsDetailActivity extends BaseActivity implements CustomItemClickListener {
    private WebView webview;
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;



    private EditText etContent;



    private List<String> datas;
    private LinearLayoutManager linearLayoutManager;
    private MainNewsCommentAdapter adapter;

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mainnewsdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("资讯详情", v -> finish());
        webview = (WebView) findViewById(R.id.webview);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        etContent = (EditText) findViewById(R.id.et_content);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);
        adapter = new MainNewsCommentAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
