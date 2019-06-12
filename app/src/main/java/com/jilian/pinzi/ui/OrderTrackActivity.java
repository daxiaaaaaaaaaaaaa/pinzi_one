package com.jilian.pinzi.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.OrderTrackAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.ui.viewmodel.OrderViewModel;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.TimeLineDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单跟踪
 */
public class OrderTrackActivity extends BaseActivity {

    public static void startActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderTrackActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    private TextView tvExpressDelivery;
    private TextView tvDeliveryNum;
    private TextView tvPhone;

    private LinearLayout llShipped;
    private ImageView ivShipped;
    private TextView tvShipped;
    private LinearLayout llTransit;
    private ImageView ivTransit;
    private TextView tvTransit;
    private LinearLayout llDispatch;
    private ImageView ivDispatch;
    private TextView tvDispatch;
    private LinearLayout llReceived;
    private ImageView ivReceived;
    private TextView tvReceived;
    private ImageView ivOrderTrackHead;


    private RecyclerView recyclerView;

    private OrderTrackAdapter mOrderTrackAdapter;
    private List<OrderTrackDto.LogisticsBean> mDataList = new ArrayList<>();

    private OrderTrackDto mOrderTrack;
    private OrderViewModel orderViewModel;

    @Override
    protected void createViewModel() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_order_track;
    }

    @Override
    public void initView() {
        setNormalTitle("订单跟踪", v -> finish());
        tvExpressDelivery = findViewById(R.id.tv_order_track_express_delivery);
        tvDeliveryNum = findViewById(R.id.tv_order_track_num);
        tvPhone = findViewById(R.id.tv_order_track_phone);
        ivOrderTrackHead = (ImageView) findViewById(R.id.iv_order_track_head);
        llShipped = findViewById(R.id.ll_order_track_shipped);
        ivShipped = findViewById(R.id.iv_order_track_shipped);
        tvShipped = findViewById(R.id.tv_order_track_shipped);
        llTransit = findViewById(R.id.ll_order_track_in_transit);
        ivTransit = findViewById(R.id.iv_order_track_in_transit);
        tvTransit = findViewById(R.id.tv_order_track_in_transit);
        llDispatch = findViewById(R.id.ll_order_track_in_dispatch);
        ivDispatch = findViewById(R.id.iv_order_track_in_dispatch);
        tvDispatch = findViewById(R.id.tv_order_track_in_dispatch);
        llReceived = findViewById(R.id.ll_order_track_received);
        ivReceived = findViewById(R.id.iv_order_track_received);
        tvReceived = findViewById(R.id.tv_order_track_received);

        recyclerView = findViewById(R.id.rv_order_track);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mOrderTrackAdapter = new OrderTrackAdapter(this, R.layout.item_order_track, mDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mOrderTrackAdapter);
        recyclerView.addItemDecoration(new TimeLineDecoration(
                ScreenUtils.dip2px(this, 19), ScreenUtils.dip2px(this, 1)
                , ContextCompat.getDrawable(this, R.drawable.image_my_agreed)
                , ScreenUtils.dip2px(this, 19), ScreenUtils.dip2px(this, 3)));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doBusiness() {
        showLoadingDialog();
        orderViewModel.getOrderOfLogistics(getIntent().getStringExtra("orderId"));
        if (orderViewModel.getOrderTrackLiveData().hasObservers()) return;
        orderViewModel.getOrderTrackLiveData().observe(this, orderTrackDtoBaseDto -> {
            hideLoadingDialog();
            if (orderTrackDtoBaseDto == null) return;
            if (orderTrackDtoBaseDto.isSuccess()) {
                mOrderTrack = orderTrackDtoBaseDto.getData();
                initViewData();
                if (mOrderTrack.getLogistics() != null) {
                    mDataList.addAll(mOrderTrack.getLogistics());
                    mOrderTrackAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUitl.showImageToastFail(orderTrackDtoBaseDto.getMsg());
            }
        });
    }

    private void initViewData() {
        Glide.with(OrderTrackActivity.this)
                .load(UrlUtils.getUrl(mOrderTrack.getGoodsImg()))
                .into(ivOrderTrackHead);
        tvExpressDelivery.setText(mOrderTrack.getDeliveryMethodName());
        tvDeliveryNum.setText(mOrderTrack.getDeliveryMethodNumber());
        tvPhone.setText(mOrderTrack.getDeliveryMethodPhone());
        int checkedId = 0;
        switch (mOrderTrack.getState()) {
            case "0":
                // 0 初始状态 未发货 1个都不亮
                break;
            case "1":
                // 第1个亮
                checkedId = 1;
                break;
            case "2":
                // 第2个亮
                checkedId = 2;
                break;
            case "3":
                // 第4个亮
                checkedId = 4;
                break;
            case "4":
                // 问题件 不处理 1个都不亮
                break;
            default:
                break;
        }
        llShipped.setBackgroundResource(checkedId == 1 ? R.drawable.icon_order_track_bg : 0);
        ivShipped.setImageResource(checkedId == 1 ? R.drawable.icon_red_dot : R.drawable.icon_grey_dot);
        tvShipped.setTextColor(checkedId == 1 ? ContextCompat.getColor(this, R.color.color_white)
                : ContextCompat.getColor(this, R.color.color_999999));
        llTransit.setBackgroundResource(checkedId == 2 ? R.drawable.icon_order_track_bg : 0);
        ivTransit.setImageResource(checkedId == 2 ? R.drawable.icon_red_dot : R.drawable.icon_grey_dot);
        tvTransit.setTextColor(checkedId == 2 ? ContextCompat.getColor(this, R.color.color_white)
                : ContextCompat.getColor(this, R.color.color_999999));
        llReceived.setBackgroundResource(checkedId == 4 ? R.drawable.icon_order_track_bg : 0);
        ivReceived.setImageResource(checkedId == 4 ? R.drawable.icon_red_dot : R.drawable.icon_grey_dot);
        tvReceived.setTextColor(checkedId == 4 ? ContextCompat.getColor(this, R.color.color_white)
                : ContextCompat.getColor(this, R.color.color_999999));
    }
}
