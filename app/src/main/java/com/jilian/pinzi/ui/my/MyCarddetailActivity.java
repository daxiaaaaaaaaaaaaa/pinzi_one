package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.IntegralMallActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.shopcard.PaySuccessActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.Date;

/**
 * 我的优惠券详情
 */
public class MyCarddetailActivity extends BaseActivity {
    private MainViewModel viewModel;
    private TextView tvName;
    private TextView tvDaller;
    private TextView tcCount;
    private TextView tvUserPlatform;
    private TextView tvDay;
    private TextView tvDetail;
    private TextView tvOk;
    private TextView tvUserConditions;
    private String id;
    private String param;

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
        return R.layout.activity_mycarddetail;
    }

    @Override
    public void initView() {
        setleftImage(R.drawable.image_back, true, null);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDaller = (TextView) findViewById(R.id.tv_daller);
        tcCount = (TextView) findViewById(R.id.tc_count);
        tvUserPlatform = (TextView) findViewById(R.id.tv_user_platform);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvUserConditions = (TextView) findViewById(R.id.tv_user_conditions);

    }

    @Override
    public void initData() {
        param = getIntent().getStringExtra(Constant.PARAM);
        //首页的领取中心过来
        if ("GetCardCenterActivity".equals(param)) {
            tvOk.setText("立即领取");
            CouponDetails();
        }
        //我的优惠券过来
        else if ("NoUserFragment".equals(param)) {
            tvOk.setText("立即使用");
            getCouponDetail();
        }
        //店铺中心过来
        else if ("ShopDetailRightFragment".equals(param)) {
            CouponDetails();
            StoreCouponDto dto = (StoreCouponDto) getIntent().getSerializableExtra("data");
            //先判断价格
            //免费的
            if (dto.getPrice() <= 0) {
                //判断是否已经领取
                //未领取
                if (dto.getIsGet() <= 0) {
                    tvOk.setText("领取");
                }
                //已领取
                else {
                    tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);
                    tvOk.setEnabled(false);
                    tvOk.setText("已领取");
                }
            }
            //需要购买的
            else {
                //未购买
                if (dto.getIsGet() <= 0) {
                    tvOk.setText("购买");
                }
                //已购买
                else {
                    tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);
                    tvOk.setEnabled(false);
                    tvOk.setText("已购买");
                }
            }
        }

    }

    /**
     * 优惠券详情 首页过来
     *
     * @param
     */
    private void CouponDetails() {
        id = getIntent().getStringExtra("id");
        viewModel.CouponDetails(id);
        viewModel.getMyCardDetailliveData().observe(this, new Observer<BaseDto<CouponCentreDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<CouponCentreDto> couponCentreDtoBaseDto) {
                if (EmptyUtils.isNotEmpty(couponCentreDtoBaseDto)) {
                    initDetailView(couponCentreDtoBaseDto.getData());
                }

            }
        });
    }

    /**
     * 优惠券详情 我的界面过来
     *
     * @param
     */
    private void getCouponDetail() {
        id = getIntent().getStringExtra("id");
        viewModel.getCouponDetail(id);
        viewModel.getCouponDetailliveData().observe(this, new Observer<BaseDto<CouponCentreDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<CouponCentreDto> couponCentreDtoBaseDto) {
                if (EmptyUtils.isNotEmpty(couponCentreDtoBaseDto)) {
                    initDetailView(couponCentreDtoBaseDto.getData());
                }

            }
        });
    }

    /**
     * 显示优惠券详情
     *
     * @param data
     */
    private void initDetailView(CouponCentreDto data) {
        if (data != null) {
            //先 判断是代金券 还是 优惠券
            //优惠券类型（1.折扣劵 2.代金券）
            Integer type = data.getType();
            if (type != null) {
                if (type == 1) {
                    //折扣劵
                    tvUserConditions.setVisibility(View.INVISIBLE);
                    //折扣
                    String moneyOrDiscount = data.getMoneyOrDiscount();
                    tcCount.setText(moneyOrDiscount + "折");
                    tvDaller.setVisibility(View.GONE);
                } else {
                    //代金券
                    tvUserConditions.setVisibility(View.VISIBLE);
                    //使用条件
                    tvUserConditions.setText("满" + data.getFullReduct() + "元可用");
                    //面额
                    String moneyOrDiscount = data.getMoneyOrDiscount();
                    tcCount.setText(moneyOrDiscount);
                    tvDaller.setVisibility(View.VISIBLE);
                }



                tvName.setText(data.getName());




                //首页的领取中心过来
                if ("GetCardCenterActivity".equals(param)) {
                    if (data.getValidity() == 1) {
                        tvDay.setText("有效期限：" + (data.getValidityDate() == null ? "" : data.getValidityDate()));
                    }
                    if (data.getValidity() == 2) {
                        tvDay.setText("有效期限：" + (data.getFixDay() == null ? "" : (data.getFixDay() + "天")));
                    }
                }
                //我的优惠券进来
                else {
                    tvDay.setText("有效期限：" + (data.getValidityDate() == null ? "" : data.getValidityDate()));

                }

                tvUserPlatform.setText("适用平台：" + data.getStoreName());

                //优惠券详情 ：1—全场通用，不限制体条件，  其他——部分商品可用  这个 优惠券 详情 这样显示吗？

                if (data.getUseType() == 1) {
                    tvDetail.setText("详细说明：全场通用，不限条件");
                } else {
                    tvDetail.setText("详细说明：部分商品可用");
                }

            }
        }
    }


    @Override
    public void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(MyCarddetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }

                if ("GetCardCenterActivity".equals(param)) {
                    toReceive(id);
                } else if ("NoUserFragment".equals(param)) {
                    toUse();
                } else if ("ShopDetailRightFragment".equals(param)) {
                    if (tvOk.getText().toString().equals("领取")) {
                        toReceive(id);
                    }
                    if (tvOk.getText().toString().equals("购买")) {
                        toBuy(id);
                    }

                }

            }
        });
    }

    /**
     * 购买优惠券
     *
     * @param id
     */
    private void toBuy(String id) {
    }

    /**
     * 去使用优惠券
     */
    private void toUse() {

        finish();
        startActivity(new Intent(MyCarddetailActivity.this, MainActivity.class));
        PinziApplication.clearAllActivitys();

    }

    /**
     * 领取优惠券
     *
     * @param id
     */
    public void toReceive(String id) {
        getLoadingDialog().showDialog();
        viewModel.GetCoupon(id, PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getStringliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("领取成功");
                    RxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext() {
                            finish();
                        }
                    });

                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }
}
