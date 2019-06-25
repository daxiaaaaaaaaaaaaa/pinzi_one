package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.QuestionnaireSurveyDetaiItemlAdapter;
import com.jilian.pinzi.adapter.QuestionnaireSurveyDetailAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.common.dto.QuestionItemDto;
import com.jilian.pinzi.common.msg.QuestionMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.common.vo.CommitQuestionItemVo;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionnaireSurveyDetailActivity extends BaseActivity implements CustomItemClickListener {

    private RecyclerView recyclerView;
    private TextView tvOk;
    private QuestionnaireSurveyDetailAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<QuestionDetailDto> list;
    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
        adapter = new QuestionnaireSurveyDetailAdapter(this, list, this);
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
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitQuestion();
            }
        });

    }

    /**
     * 提交答案
     */
    private void commitQuestion() {
        showLoadingDialog();
        viewModel.commitQuestion(getUserId(), questionDto.getId(), getResults());
        viewModel.getCommiteData().observe(this, new Observer<BaseDto>() {
            @Override
            public void onChanged(@Nullable BaseDto baseDto) {
                hideLoadingDialog();
                if (baseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("提交成功");
                    finish();
                } else {
                    ToastUitl.showImageToastFail(baseDto.getMsg());
                }
            }
        });

    }

    /**
     * 获取选中的题目答案
     *
     * @return
     */
    private List<CommitQuestionItemVo> getResults() {
        //遍历适配器，
        List<QuestionnaireSurveyDetaiItemlAdapter> adapterList = adapter.getAdapterList();

        List<CommitQuestionItemVo> results = new ArrayList<>();

        if (EmptyUtils.isNotEmpty(adapterList)) {
            for (int i = 0; i < adapterList.size(); i++) {
                CommitQuestionItemVo vo = new CommitQuestionItemVo();
                vo.setTopicId(list.get(i).getId());
                vo.setOptionId(getOptionId(i));
                results.add(vo);
            }

        }
        return results;

    }

    /**
     * 拼接选中的ID
     *
     * @param index
     * @return
     */
    private String getOptionId(int index) {
        QuestionnaireSurveyDetaiItemlAdapter detaiItemlAdapter = adapter.getAdapterList().get(index);
        List<QuestionItemDto> itemList = detaiItemlAdapter.getDatas();
        String idStr = "";
        for (int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).isSelected()){
                if (TextUtils.isEmpty(idStr)) {
                    idStr = itemList.get(i).getId();
                } else {
                    idStr = idStr + "," + itemList.get(i).getId();
                }
            }

        }
        return TextUtils.isEmpty(idStr) ? null : idStr;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
