package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyTntegralDetailDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;

public class MyTntegralDetailActivity extends BaseActivity {
    private MyViewModel viewModel;
    private TextView tvContent;



    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mytntegraldetail;
    }

    @Override
    public void initView() {
        setCenterTitle(getIntent().getStringExtra("title"), "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void initData() {
        getContent();
    }

    /**
     * 获取积分说明数据
     */
    private void getContent() {
        viewModel.getContent();
        viewModel.getMyTntegralDetailDtoLiveData().observe(this, new Observer<BaseDto<MyTntegralDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<MyTntegralDetailDto> data) {
                if(data.getData()!=null){
                    if(getIntent().getIntExtra("type",0)==0){
                        tvContent.setText(Html.fromHtml(data.getData().getScoreContent()));
                    }
                    else{
                        tvContent.setText(Html.fromHtml(data.getData().getContent()));
                    }

                }
            }
        });
    }

    @Override
    public void initListener() {

    }
}
