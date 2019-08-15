package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.DiscountMoneyDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.common.vo.BuyCouponVo;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.shopcard.PayCardActivity;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;

/**
 * 购买优惠券
 */
public class BuyCardActivity extends BaseActivity {
    private LinearLayout llContainer;
    private LinearLayout llGoodsDetail;
    private RecyclerView recyclerView;
    private TextView tvTwo;
    private ImageView ivTwo;
    private TextView tvAllCount;
    private TextView tvCommisionNum;
    private TextView tvPayCount;
    private TextView tvOk;
    private StoreCouponDto dto;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvCount;
    private MyViewModel viewModel;
    private MainViewModel mainViewModel;
    private double price;
    private int count = 1;
    private double allMoney;

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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_buycard;
    }

    @Override
    public void initView() {
        setNormalTitle("填写订单", v -> finish());
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        llGoodsDetail = (LinearLayout) findViewById(R.id.ll_goods_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvAllCount = (TextView) findViewById(R.id.tv_all_count);
        tvCommisionNum = (TextView) findViewById(R.id.tv_commisionNum);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        dto = (StoreCouponDto) getIntent().getSerializableExtra("data");
        price = dto.getPrice();
        tvPrice.setText("￥" + NumberUtils.forMatNumber(dto.getPrice()));
        tvName.setText(dto.getName());
        //总的金额
        allMoney = count * price;
        tvAllCount.setText("￥" + NumberUtils.forMatNumber(allMoney));
        tvPayCount.setText("￥" + NumberUtils.forMatNumber(allMoney));

    }

    @Override
    public void initData() {
        //佣金 积分余额
        getMyScoreOrCommission();
    }

    private double commisionNum = 0.00;//总佣金

    /**
     * 佣金 积分余额
     */
    private void getMyScoreOrCommission() {
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> dto) {
                if (dto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(dto.getData().getCommisionNum())) {

                        commisionNum = Double.parseDouble(dto.getData().getCommisionNum());
                        if (commisionNum == 0) {
                            ivTwo.setEnabled(false);
                            tvTwo.setText("没有可用的佣金");
                        } else {
                            ivTwo.setEnabled(true);
                            if (allMoney >= commisionNum) {
                                tvTwo.setText("使用" + NumberUtils.forMatNumber(Double.parseDouble(dto.getData().getCommisionNum())) + "佣金可抵扣¥" + NumberUtils.forMatNumber(Double.parseDouble(dto.getData().getCommisionNum())) + "金额");
                            } else {
                                tvTwo.setText("使用" + NumberUtils.forMatNumber(allMoney) + "佣金可抵扣¥" + NumberUtils.forMatNumber(allMoney) + "金额");
                            }

                        }

                    } else {
                        ivTwo.setEnabled(false);
                        tvTwo.setText("没有可用的佣金");
                    }
                } else {
                    ivTwo.setEnabled(false);
                    tvTwo.setText("没有可用的佣金");

                }
            }

        });
    }

    private int isUseCommisson = 0;//使用佣金（未使用传0）
    private boolean two;

    @Override
    public void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
        ivTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = !two;
                if (two) {
                    if (allMoney >= commisionNum) {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(commisionNum));
                        tvPayCount.setText("￥" + NumberUtils.forMatNumber(allMoney - commisionNum));

                    } else {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(allMoney));
                        tvPayCount.setText("¥0.00");
                    }

                    ivTwo.setImageResource(R.drawable.image_chat_open);
                    isUseCommisson = 1;
                } else {
                    ivTwo.setImageResource(R.drawable.image_chat_turn_on);
                    tvCommisionNum.setText("¥0.00");
                    isUseCommisson = 0;
                    tvPayCount.setText("￥" + NumberUtils.forMatNumber(allMoney));
                }

            }
        });
    }

    /**
     * 支付
     */
    private void pay() {
        String payCount = tvPayCount.getText().toString().substring(1, tvPayCount.getText().toString().length());
        BuyCouponVo vo = new BuyCouponVo();
        vo.setuId(getUserId());
        vo.setMoney(payCount);
        vo.setCouponId(dto.getId());
        vo.setScore("0");
        vo.setCommission(String.valueOf(isUseCommisson));
        vo.setPlatform("1");
        Intent intent = new Intent(this, PayCardActivity.class);
        intent.putExtra("data",vo);
        startActivity(intent);


    }
}
