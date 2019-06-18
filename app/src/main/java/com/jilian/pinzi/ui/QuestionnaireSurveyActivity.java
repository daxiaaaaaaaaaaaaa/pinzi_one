package com.jilian.pinzi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.QuestionnaireSurveyAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 活动列表
 */
public class QuestionnaireSurveyActivity extends BaseActivity implements CustomItemClickListener {
    private TextView tvOne;
    private TextView tvTwo;
    private View vOne;
    private View vTwo;
    private RecyclerView recyclerView;
    private QuestionnaireSurveyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> list;

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_questionnairesurvey;
    }

    @Override
    public void initView() {
        setNormalTitle("问卷调查", v -> finish());
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
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
        adapter = new QuestionnaireSurveyAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#c71233"));
                tvTwo.setTextColor(Color.parseColor("#888888"));
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#c71233"));
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this, QuestionnaireSurveyDetailActivity.class));
    }
}
