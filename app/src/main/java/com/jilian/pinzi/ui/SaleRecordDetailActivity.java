package com.jilian.pinzi.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.SaleRecordDetailDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.Date;

/**
 * 记录详情
 */
public class SaleRecordDetailActivity extends BaseActivity {

    public static void startActivity(Context context, String recordId) {
        Intent intent = new Intent(context, SaleRecordDetailActivity.class);
        intent.putExtra("recordId", recordId);
        context.startActivity(intent);
    }

    private TextView tvServiceNum;
    private TextView tvApplyTime;

    private TextView tvVerifyProgress;

    private LinearLayout llInfoContainer;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout llInfo1;
    private EditText etExpressName;
    private EditText etExpressNum;
    private Button btnAdd;
    private LinearLayout llInfo2;
    private TextView tvExpressName;
    private TextView tvExpressNum;

    private TextView tvReason;

    private SaleRecordDetailDto mSaleRecordDetail;
    private MyViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_sale_record_detail;
    }

    @Override
    public void initView() {
        setNormalTitle("记录详情", v -> finish(), R.drawable.image_customer_service, v -> {
            // TODO 人工服务
        });
        tvServiceNum = findViewById(R.id.tv_sale_record_detail_service_num);
        tvApplyTime = findViewById(R.id.tv_sale_record_detail_apply_time);

        tvVerifyProgress = findViewById(R.id.tv_sale_record_detail_progress);

        llInfoContainer = findViewById(R.id.ll_sale_record_detail_info);
        tvName = findViewById(R.id.tv_sale_record_detail_name);
        tvPhone = findViewById(R.id.tv_sale_record_detail_phone);
        tvAddress = findViewById(R.id.tv_sale_record_detail_address);
        llInfo1 = findViewById(R.id.ll_sale_record_detail_info2);
        etExpressName = findViewById(R.id.et_sale_record_detail_express_name);
        etExpressNum = findViewById(R.id.et_sale_record_detail_express_num);
        btnAdd = findViewById(R.id.btn_sale_record_detail_add);
        llInfo2 = findViewById(R.id.ll_sale_record_detail_info3);
        tvExpressName = findViewById(R.id.tv_sale_record_detail_express_name);
        tvExpressNum = findViewById(R.id.tv_sale_record_detail_express_num);

        tvReason = findViewById(R.id.tv_sale_record_detail_reason);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnAdd.setOnClickListener(v -> {
            String courierName = etExpressName.getText().toString().trim();
            String courierNo = etExpressNum.getText().toString().trim();
            if (TextUtils.isEmpty(courierName)) {
                ToastUitl.showImageToastFail("请输入快递名称");
                return;
            }
            if (TextUtils.isEmpty(courierNo)) {
                ToastUitl.showImageToastFail("请输入快递单号");
                return;
            }
            showLoadingDialog();
            viewModel.addCourierInfo(mSaleRecordDetail.getId() + "", courierName, courierNo);
            if (viewModel.getAddCourierInfoLiveData().hasObservers()) return;
            viewModel.getAddCourierInfoLiveData().observe(this, emptyDtoBaseDto -> {
                if (emptyDtoBaseDto == null) return;
                if (emptyDtoBaseDto.isSuccess()) {
                    getSaleRecordDetailInfo();
                } else {
                    hideLoadingDialog();
                    ToastUitl.showImageToastFail(emptyDtoBaseDto.getMsg());
                }
            });
        });
    }

    @Override
    public void doBusiness() {
        showLoadingDialog();
        getSaleRecordDetailInfo();
    }

    /** 获取申请记录详情数据 */
    private void getSaleRecordDetailInfo() {
        viewModel.getSaleRecordDetail(getIntent().getStringExtra("recordId"));
        if (viewModel.getSaleRecordDetailLiveData().hasObservers()) return;
        viewModel.getSaleRecordDetailLiveData().observe(this, saleRecordDetailDtoBaseDto -> {
            hideLoadingDialog();
            if (saleRecordDetailDtoBaseDto == null) return;
            if (saleRecordDetailDtoBaseDto.isSuccess()) {
                mSaleRecordDetail = saleRecordDetailDtoBaseDto.getData();
                initViewData();
            } else {
                ToastUitl.showImageToastFail(saleRecordDetailDtoBaseDto.getMsg());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initViewData() {
        tvServiceNum.setText("服务单号：" + mSaleRecordDetail.getServiceNo());
        tvApplyTime.setText("申请时间：" + DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,
                new Date(mSaleRecordDetail.getCreateDate())));

        // 状态（1.待处理 2.退货中 3.已完成 4.已拒绝）
        int status = mSaleRecordDetail.getStatus();
        if (1 == status) {
            llInfoContainer.setVisibility(View.GONE);
            tvVerifyProgress.setText("审核进度：您的退货申请正在审核中");
            tvVerifyProgress.setTextColor(ContextCompat.getColor(this, R.color.color_c71233));
        } else if (2 == status) {
            // 退货中, 未填写快递单号.
            tvVerifyProgress.setText("审核进度：您的退货申请已经通过审核，请按照下方地址寄快递并填写快递单号。");
            tvVerifyProgress.setTextColor(ContextCompat.getColor(this, R.color.color_222222));
            llInfoContainer.setVisibility(View.VISIBLE);
            llInfo1.setVisibility(View.VISIBLE);
            llInfo2.setVisibility(View.GONE);

            // 退货中, 已填写快递单号.
            if (mSaleRecordDetail.getCourierName().length() > 0 && mSaleRecordDetail.getCourierNo().length() > 0) {
                tvVerifyProgress.setText("审核进度：您的退货申请已经通过审核，等待商家收货。");
                llInfo1.setVisibility(View.GONE);
                llInfo2.setVisibility(View.VISIBLE);
            }
        } else if (3 == status) {
            tvVerifyProgress.setText("审核进度：售后已确认收货，等待退款");
            tvVerifyProgress.setTextColor(ContextCompat.getColor(this, R.color.color_c71233));
            llInfoContainer.setVisibility(View.VISIBLE);
            llInfo1.setVisibility(View.GONE);
            llInfo2.setVisibility(View.VISIBLE);
        } else {
            llInfoContainer.setVisibility(View.GONE);
            // 已完成, 允许退款
            tvVerifyProgress.setText("审核进度：服务单已退款，请注意查收");
            tvVerifyProgress.setTextColor(ContextCompat.getColor(this, R.color.color_c71233));

            // 已完成, 拒绝退款
            if (4 == status) {
                tvVerifyProgress.setText("审核进度：您的退货申请未通过审核");
                tvVerifyProgress.setTextColor(ContextCompat.getColor(this, R.color.color_c71233));
            }
        }

        tvName.setText("收货人姓名：" + mSaleRecordDetail.getReceiveName());
        tvPhone.setText("电话：" + mSaleRecordDetail.getReceivePhone());
        tvAddress.setText("收货人地址：" + mSaleRecordDetail.getReceiveAddress());
        tvExpressName.setText("快递名称：" + mSaleRecordDetail.getCourierName());
        tvExpressNum.setText("快递单号：" + mSaleRecordDetail.getCourierNo());

        tvReason.setText(mSaleRecordDetail.getReason());
    }
}
