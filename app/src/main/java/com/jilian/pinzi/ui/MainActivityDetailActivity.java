package com.jilian.pinzi.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.Date;

import cn.jzvd.JzvdStd;

public class MainActivityDetailActivity extends BaseActivity {
    private TextView tvGet;
    private TextView tvSee;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvRegistrationQuota;
    private TextView tvRegistrationNumber;
    private WebView webview;
    private MainViewModel viewModel;
    private TextView tvUpload;
    private JzvdStd ivVideo;





    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mainactivitydetail;
    }

    @Override
    public void initView() {
        setNormalTitle("活动详情", v -> finish());

        tvUpload = (TextView) findViewById(R.id.tv_upload);
        tvGet = (TextView) findViewById(R.id.tv_get);
        tvSee = (TextView) findViewById(R.id.tv_see);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvRegistrationQuota = (TextView) findViewById(R.id.tv_registration_quota);
        tvRegistrationNumber = (TextView) findViewById(R.id.tv_registration_number);
        ivVideo = (JzvdStd) findViewById(R.id.iv_video);
        webview = (WebView) findViewById(R.id.webview);
    }

    @Override
    public void initData() {
        getActivityDetail(getIntent().getStringExtra("id"), getLoginDto() == null ? null : getLoginDto().getId());
    }

    private ActivityDto mData;

    private void getActivityDetail(String id, String uId) {
        showLoadingDialog();
        viewModel.getActivityDetail(id, uId);
        viewModel.getActivityDetailtData().observe(this, new Observer<BaseDto<ActivityDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ActivityDto> activityDtoBaseDto) {
                hideLoadingDialog();
                if (activityDtoBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(activityDtoBaseDto.getData())) {
                        mData = activityDtoBaseDto.getData();
                        if (activityDtoBaseDto.getData().getApplyActivityId() == 0) {
                            tvGet.setText("报名");
                            setrightTitle(null, null, null);
                            tvUpload.setVisibility(View.GONE);
                        } else {
                            tvGet.setText("取消报名");
                            tvUpload.setVisibility(View.VISIBLE);
                        }
                        tvName.setText(activityDtoBaseDto.getData().getTitle());

                        tvDate.setText("活动时间：" + DateUtil.dateToString(DateUtil.DATE_FORMAT, new Date(activityDtoBaseDto.getData().getStartDate())) +
                                "~"
                                + DateUtil.dateToString(DateUtil.DATE_FORMAT, new Date(activityDtoBaseDto.getData().getEndDate())));
                        tvRegistrationQuota.setText("报名名额：" + activityDtoBaseDto.getData().getPeopleNum());
                        tvRegistrationNumber.setText("已报名人数：" + activityDtoBaseDto.getData().getAlreadyPeopleNum());

                        //图文详情
                        String content = activityDtoBaseDto.getData().getDescs();

                        //content是后台返回的h5标签
                        webview.loadDataWithBaseURL(null,
                                getHtmlData(content), "text/html", "utf-8", null);
                        //視頻不為空
                        if(!TextUtils.isEmpty(activityDtoBaseDto.getData().getVideo())){
                            initVideo(activityDtoBaseDto.getData());
                        }
                        else{
                            ivVideo.setVisibility(View.GONE);
                        }
                    }
                } else {
                    ToastUitl.showImageToastFail(activityDtoBaseDto.getMsg());
                }

            }
        });
    }

    /**

    /**
     * 初始化视频
     *
     * @param dto
     */
    private void initVideo(ActivityDto dto) {
        if (TextUtils.isEmpty(dto.getVideo())) {
            return;
        }
        showLoadingDialog();
        //开启子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = BitmapUtils.getNetVideoBitmap(dto.getVideo());
                dto.setBitmap(bitmap);
                Message msg = Message.obtain();
                msg.what = 1000;
                msg.obj = dto;
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 显示视频图片
     * 初始化视频
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoadingDialog();
            if (msg.what == 1000) {
                ActivityDto dto = (ActivityDto) msg.obj;
                ivVideo.setVisibility(View.VISIBLE);
                ivVideo.thumbImageView.setImageBitmap(dto.getBitmap());
                ivVideo.setUp(dto.getVideo(), "", JzvdStd.SCREEN_WINDOW_NORMAL);
                JzvdStd.setJzUserAction(null);

            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
        JzvdStd.releaseAllVideos();
    }
    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void initListener() {
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDetailActivity.this, PublishWorksActivity.class);
                intent.putExtra("activityId", mData.getId());
                startActivity(intent);
            }
        });
        tvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDetailActivity.this, AllWorksActivity.class);
                intent.putExtra("id", mData.getId());
                startActivity(intent);
            }
        });
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(MainActivityDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }

                if (mData == null) {
                    return;
                }

                //未报名
                if (mData.getApplyActivityId() == 0) {
                    applyActivity(mData.getId(), getLoginDto().getId());
                } else {
                    cancelApply(mData.getApplyActivityId());
                }

            }
        });
    }

    /**
     * 取消报名
     *
     * @param applyActivityId
     */
    private void cancelApply(int applyActivityId) {
        showLoadingDialog();
        viewModel.cancelApply(String.valueOf(applyActivityId));
        viewModel.getCancelData().observe(this, new Observer<BaseDto>() {
            @Override
            public void onChanged(@Nullable BaseDto baseDto) {
                hideLoadingDialog();
                if (baseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("取消报名成功");
                    getActivityDetail(getIntent().getStringExtra("id"), getLoginDto().getId());
                } else {
                    ToastUitl.showImageToastFail(baseDto.getMsg());
                }
            }
        });
    }

    /**
     * 报名
     *
     * @param id
     * @param uId
     */
    private void applyActivity(String id, String uId) {
        showLoadingDialog();
        viewModel.applyActivity(id, uId);
        viewModel.getApplyData().observe(this, new Observer<BaseDto>() {
            @Override
            public void onChanged(@Nullable BaseDto baseDto) {
                hideLoadingDialog();
                if (baseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("报名成功");
                    getActivityDetail(getIntent().getStringExtra("id"), getLoginDto().getId());
                } else {
                    ToastUitl.showImageToastFail(baseDto.getMsg());
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }
}
