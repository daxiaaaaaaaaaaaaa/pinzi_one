package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyMyCouponsAdapter;
import com.jilian.pinzi.adapter.MyTntegralAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyCouponsActivity extends BaseActivity implements CustomItemClickListener, MyMyCouponsAdapter.DeleteRecordListener {
    private RecyclerView recyclerView;
    private MyMyCouponsAdapter pointAdapter;
    private List<MyRecordDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvWithdrawal;
    private TextView tvMore;
    private TextView tvCount;
    private LotteryViewModel lotteryViewModel;
    private int pageNo = 1;//
    private int pageSize = 20;//
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;
    private TextView tvShopDat;

    private MainViewModel mainViewModel;

    @Override
    protected void onResume() {
        super.onResume();
        //获取用户佣金
        getMyRecord();
        //结算
        getMyScoreOrCommission();
    }

    //结算
    private void getMyScoreOrCommission() {
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> scoreOrCommissionDtoBaseDto) {
                tvCount.setText(NumberUtils.forMatNumber(Double.parseDouble(scoreOrCommissionDtoBaseDto.getData().getCommisionNum())));
            }
        });
    }

    /**
     * 获取我的积分
     */
    private void getMyRecord() {
        //1.积分 2.钱包 3.佣金
        lotteryViewModel.getMyRecord(PinziApplication.getInstance().getLoginDto().getId(), 3, pageNo, pageSize);
        lotteryViewModel.getMyRecordLiveData().observe(this, new Observer<BaseDto<List<MyRecordDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MyRecordDto>> listBaseDto) {

                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(listBaseDto.getData());
                    pointAdapter.notifyDataSetChanged();
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
        lotteryViewModel = ViewModelProviders.of(this).get(LotteryViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mycoupons;
    }

    @Override
    public void initView() {
        tvShopDat = (TextView) findViewById(R.id.tv_shop_dat);
        tvMore = (TextView) findViewById(R.id.tv_more);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvWithdrawal = (TextView) findViewById(R.id.tv_withdrawal);
        tvCount = (TextView) findViewById(R.id.tv_count);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        setCenterTitle("我的佣金", "#FFFFFF");
        setrightTitle("赠送佣金", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(MyCouponsActivity.this, R.layout.dialog_giving_commission);
                ImageView ivCancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
                EditText etPhone = (EditText) dialog.findViewById(R.id.et_phone);
                EditText etCount = (EditText) dialog.findViewById(R.id.et_count);
                TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
                TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(etCount.getText().toString())
                                || TextUtils.isEmpty(etPhone.getText().toString())) {
                            return;
                        }
                        if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
                            ToastUitl.showImageToastFail("请输入正确的手机号码");
                        }
                        if (Double.parseDouble(etCount.getText().toString()) == 0) {
                            ToastUitl.showImageToastFail("数量要大于0");
                            return;
                        }
                        getLoadingDialog().showDialog();
                        viewModel.giveCommission(getLoginDto().getId(), etPhone.getText().toString(), etCount.getText().toString());
                        viewModel.getCommissionnListliveData().observe(MyCouponsActivity.this, new Observer<BaseDto<String>>() {
                            @Override
                            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                                getLoadingDialog().dismiss();
                                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                                    ToastUitl.showImageToastSuccess("赠送成功");
                                    dialog.dismiss();
                                    //记录
                                    getMyRecord();
                                    //结算
                                    getMyScoreOrCommission();
                                } else {
                                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        pointAdapter = new MyMyCouponsAdapter(this, datas, this, this);
        recyclerView.setAdapter(pointAdapter);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        //充值
        tvShopDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUitl.showImageToastSuccess("该功能将即刻开通");
//                return;
                startActivity(new Intent(MyCouponsActivity.this, TopUpActivity.class));
            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                //获取用户佣金
                getMyRecord();
                //结算
                getMyScoreOrCommission();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMyRecord();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyRecord();
            }
        });
        tvWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCouponsActivity.this, WithdrawalActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCouponsActivity.this, MyCouponsRecordActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void deleteRecord(int pisition) {
        Dialog dialog = new Dialog(this, R.style.dialogFullscreen);
        dialog.setContentView(R.layout.dialog_bottom_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
            // TODO 删除
            dialog.dismiss();
            delete(datas.get(pisition).getId(),datas.get(pisition).getStatus());
        });
        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
            dialog.dismiss();

        });
    }

    /**
     *
     * @param id ID
     * @param status 1.增 2.减
     */
    private void delete(String id,int status) {
        if(status==1){
            showLoadingDialog();
            mainViewModel.deleteRecharge(id);
            mainViewModel.getDeleteRechargeData().observe(this, new Observer<BaseDto>() {
                @Override
                public void onChanged(@Nullable BaseDto baseDto) {
                    hideLoadingDialog();
                    if(baseDto.isSuccess()){
                        ToastUitl.showImageToastSuccess("删除成功");
                        pageNo = 1;
                        getMyRecord();
                    }
                    else{
                        ToastUitl.showImageToastFail(baseDto.getMsg());
                    }
                }
            });
        }
        if(status==2){
            showLoadingDialog();
            mainViewModel.deleteByIds(id);
            mainViewModel.getDeleteByIdsData().observe(this, new Observer<BaseDto>() {
                @Override
                public void onChanged(@Nullable BaseDto baseDto) {
                    hideLoadingDialog();
                    if(baseDto.isSuccess()){
                        ToastUitl.showImageToastSuccess("删除成功");
                        pageNo = 1;
                        getMyRecord();
                    }
                    else{
                        ToastUitl.showImageToastFail(baseDto.getMsg());
                    }
                }
            });
        }
    }
}
