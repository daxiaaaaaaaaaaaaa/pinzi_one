package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.QuestionnaireSurveyAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 问卷调查列表
 */
public class QuestionnaireSurveyActivity extends BaseActivity implements CustomItemClickListener {
    private TextView tvOne;
    private TextView tvTwo;
    private View vOne;
    private View vTwo;
    private RecyclerView recyclerview;
    private QuestionnaireSurveyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<QuestionDto> list;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        //咨询列表
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距
        recyclerview.addItemDecoration(new RecyclerViewSpacesItemDecoration(map));
        list = new ArrayList<>();
        adapter = new QuestionnaireSurveyAdapter(this, list, this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog();
        pageNo = 1;
        getQuestionList();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//
    private int type = 1;// 1.未填写 2.已填写

    private void getQuestionList() {
        viewModel.getQuestionList(getUserId(), type, pageNo, pageSize);
        viewModel.getQuestionListData().observe(this, new Observer<BaseDto<List<QuestionDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<QuestionDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        list.clear();
                    }
                    list.addAll(listBaseDto.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#c71233"));
                tvTwo.setTextColor(Color.parseColor("#888888"));
                type = 1;
                pageNo = 1;
                getQuestionList();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#c71233"));
                type = 2;
                pageNo = 1;
                getQuestionList();
            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getQuestionList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getQuestionList();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getQuestionList();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = null;
        if (type == 1) {
            intent = new Intent(this, QuestionnaireSurveyDetailActivity.class);
        }
        if (type == 2) {
            intent = new Intent(this, QuestionnaireSurveyDetailFinishActivity.class);
        }
        intent.putExtra("data", list.get(position));

        startActivity(intent);
    }
}
