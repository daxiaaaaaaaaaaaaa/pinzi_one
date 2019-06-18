package com.jilian.pinzi.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.QuestionnaireSurveyDetailAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionnaireSurveyDetailActivity extends BaseActivity implements CustomItemClickListener {

    private RecyclerView recyclerView;
    private TextView tvOk;
    private QuestionnaireSurveyDetailAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> list;
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_questionnairesurveydetail;
    }

    @Override
    public void initView() {
        setNormalTitle("问卷调查详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //咨询列表
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距

        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(map));
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new QuestionnaireSurveyDetailAdapter(this, list, this);
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
