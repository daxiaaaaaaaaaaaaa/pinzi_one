package com.jilian.pinzi.ui.shopcard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.MyOrderWaitePayAfterDetailActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.MyOrderWaiteGetGoodDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.ToastUitl;

public class PaySuccessActivity extends BaseActivity {
    private TextView tvPayType;
    private TextView tvPayCount;
    private TextView tvSeeOrder;
    private TextView tvBack;


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
    private MyViewModel viewModel;
    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_paysuccess;
    }

    @Override
    public void initView() {
        setNormalTitle("支付成功", v -> finish());
        setrightTitle("完成", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvPayType = (TextView) findViewById(R.id.tv_pay_type);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvSeeOrder = (TextView) findViewById(R.id.tv_see_order);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvPayType.setText(getIntent().getStringExtra("payType"));
        tvPayCount.setText(getIntent().getStringExtra("payCount"));
    }

    @Override
    public void initData() {
        PinziApplication.clearSpecifyActivities(new Class[]{MyOrderWaitePayAfterDetailActivity.class});
        PinziApplication.clearSpecifyActivities(new Class[]{MyOrderWaiteGetGoodDetailActivity.class});
        getOrderDetail();
    }
    private int payStatus;// true number   支付状态（1.待支付 2.已支付，待发货 3.已发货 4.已完成，待评价 5.已评价 6.已取消）
    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        showLoadingDialog();
        viewModel.getOrderDetail(getIntent().getStringExtra("orderId"));
        viewModel.getOrderDetail().observe(this, new Observer<BaseDto<OrderDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<OrderDetailDto> dto) {
                hideLoadingDialog();
                if(dto.isSuccess()){
                    payStatus = dto.getData().getPayStatus();
                }
                else{
                    ToastUitl.showImageToastFail(dto.getMsg());
                }
            }
        });
    }
    @Override
    public void initListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PaySuccessActivity.this, MainActivity.class));
                PinziApplication.clearAllActivitys();
            }
        });
        tvSeeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(payStatus==0){
                    return;
                }
                if(payStatus==7){
                    Intent intent = new Intent(PaySuccessActivity.this, MyOrderWaitePayAfterDetailActivity.class);
                    intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(PaySuccessActivity.this, MyOrderWaiteGetGoodDetailActivity.class);
                    intent.putExtra("type",1);// 1 代发货 2 待收货
                    intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
