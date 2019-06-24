package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.QuestionnaireSurveyDetaiFinishlAdapter;
import com.jilian.pinzi.adapter.QuestionnaireSurveyDetailAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionnaireSurveyDetailFinishActivity extends BaseActivity implements CustomItemClickListener {

    private RecyclerView recyclerView;

    private QuestionnaireSurveyDetaiFinishlAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<QuestionDetailDto> list;
    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_questionnairesurveydetail_finish;
    }

    @Override
    public void initView() {
        setNormalTitle("问卷调查详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //咨询列表
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距

        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(map));
        list = new ArrayList<>();
        adapter = new QuestionnaireSurveyDetaiFinishlAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    private QuestionDto questionDto;


    @Override
    public void initData() {
        questionDto = (QuestionDto) getIntent().getSerializableExtra("data");
        getQuestionDetail();
    }

    private void getQuestionDetail() {
        showLoadingDialog();
        viewModel.getQuestionDetail(getUserId(), questionDto.getId());
        viewModel.getQuestionDetailData().observe(this, new Observer<BaseDto<List<QuestionDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<QuestionDetailDto>> listBaseDto) {
                hideLoadingDialog();
                if (listBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                        list.clear();
                        list.addAll(listBaseDto.getData());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
