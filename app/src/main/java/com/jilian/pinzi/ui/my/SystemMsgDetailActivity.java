package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 消息详情
 */
public class SystemMsgDetailActivity extends BaseActivity {
    private TextView tvMsgTitle;
    private MainViewModel viewModel;
    private TextView tvDay;
    private TextView tvContent;
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
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_systemmsgdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("消息详情", v -> finish());
        tvMsgTitle = (TextView) findViewById(R.id.tv_msg_title);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void initData() {
        MsgDto mMsgDto = (MsgDto) getIntent().getSerializableExtra("msgDto");
        if (EmptyUtils.isNotEmpty(mMsgDto)) {
            getMsgDtoDetail(mMsgDto.getId());

        }


    }

    /**
     * 初始化消息详情
     *
     * @param id
     */
    private void getMsgDtoDetail(String id) {
        viewModel.InformationDetails(id, PinziApplication.getInstance().getLoginDto()==null?null:PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getMsgDetailliveData().observe(this, new Observer<BaseDto<MsgDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<MsgDto> msgDtoBaseDto) {
                if (EmptyUtils.isNotEmpty(msgDtoBaseDto)) {
                    initMsgDetailView(msgDtoBaseDto.getData());
                }
            }
        });

    }

    /**
     * 初始化消息详情
     *
     * @param msgDto
     */
    private void initMsgDetailView(MsgDto msgDto) {
        tvMsgTitle.setText(msgDto.getTitle());
        tvContent.setText(msgDto.getContent());
        //消息的时间
        String day = DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date(msgDto.getCreateDate()));
        // 昨天
        String nowDay = DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        //昨天
        String yesterday = DateUtil.dateToString(DateUtil.DATE_FORMAT_, cal.getTime());

        if (nowDay.equals(day)) {
            String hour = DateUtil.dateToString("HH:mm", new Date(msgDto.getCreateDate()));
            tvDay.setText("今天" + hour);
        } else if (yesterday.equals(day)) {
            String hour = DateUtil.dateToString("HH:mm", new Date(msgDto.getCreateDate()));
            tvDay.setText("昨天" + hour);
        } else {
            String dayHour = DateUtil.dateToString("yyyy年MM月dd日 HH:mm", new Date(msgDto.getCreateDate()));
            tvDay.setText(dayHour);
        }
    }

    @Override
    public void initListener() {

    }
}
