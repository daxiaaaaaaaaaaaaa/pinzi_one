package com.jilian.pinzi.ui.my.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyOrderAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MyOrderWaitePayAfterDetailActivity;
import com.jilian.pinzi.ui.OrderTrackActivity;
import com.jilian.pinzi.ui.my.MyOrderCancelDetailActivity;
import com.jilian.pinzi.ui.my.MyOrderFinishNoCommentDetailActivity;
import com.jilian.pinzi.ui.my.MyOrderWaiteGetGoodDetailActivity;
import com.jilian.pinzi.ui.my.MyOrderWaitePayDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseOrderFragment extends BaseFragment implements CustomItemClickListener, MyOrderAdapter.OrderListener {
    private static final String TAG ="BaseOrderFragment" ;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyOrderAdapter orderAdapter;
    public List<MyOrderDto> datas;
    private MyViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_base;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getmActivity(), 10)));
        srNoData.setEnableLoadMore(false);
        datas = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getmActivity(), datas, this, this);
        recyclerView.setAdapter(orderAdapter);

    }
    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                //stopLoad();
            }
        }
    }

    private void lazyLoad() {
        pageNo =1;
        getOrderDatas();
        Log.e(TAG, "lazyLoad:----"  );
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void loadData() {

    }
    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }


    private Integer pageNo = 1;//false number
    private Integer paegSize = 20;////false number
    private  List<MyOrderGoodsInfoDto> goodsInfo;
    /**
     * 获取订单数据
     *
     * @return
     */
    private void getOrderDatas() {
        viewModel.getMyOrderList(PinziApplication.getInstance().getLoginDto().getId(), getPayStatus(), pageNo, paegSize);
        viewModel.getOrderliveData().observe(this, new Observer<BaseDto<List<MyOrderDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MyOrderDto>> listBaseDto) {
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

                    for (int i = 0; i <listBaseDto.getData().size() ; i++) {
                        if(listBaseDto.getData().get(i).getOrderType()==2){
                            goodsInfo = new ArrayList<>();
                            MyOrderGoodsInfoDto dto = new MyOrderGoodsInfoDto();
                            dto.setName(listBaseDto.getData().get(i).getAwardName());
                            dto.setQuantity(1);
                            dto.setGoodsPrice(0);
                            goodsInfo.add(dto);
                            listBaseDto.getData().get(i).setGoodsInfo(goodsInfo);
                        }
                    }
                    datas.addAll(listBaseDto.getData());
                    orderAdapter.notifyDataSetChanged();
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


    protected abstract Integer getPayStatus();

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getOrderDatas();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getOrderDatas();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getOrderDatas();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Integer type = datas.get(position).getPayStatus();
        Intent intent;
        switch (type) {
            case 1:
                //待付款
                 intent =  new Intent(getmActivity(), MyOrderWaitePayDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                startActivity(intent);
                break;
            case 2:
                //待收货 未发货
                intent =  new Intent(getmActivity(), MyOrderWaiteGetGoodDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case 3:
                // 待收货  已发货
                intent =  new Intent(getmActivity(), MyOrderWaiteGetGoodDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case 5:
                //已完成 已评价
                intent =  new Intent(getmActivity(), MyOrderFinishNoCommentDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case 4:
                //已完成 未评价
                intent =  new Intent(getmActivity(), MyOrderFinishNoCommentDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case 6:
                //已取消
                intent =  new Intent(getmActivity(), MyOrderCancelDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                startActivity(intent);
                break;

            case 7:
                //待付尾款
                intent =  new Intent(getmActivity(), MyOrderWaitePayAfterDetailActivity.class);
                intent.putExtra("orderId",datas.get(position).getId());
                startActivity(intent);
                break;
        }
    }



    /**
     * 取消订单
     */
    @Override
    public void showCancelOrderDialog(MyOrderDto dto) {
        final int[] reason = {1};
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_cancel_order)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        ImageView ivCancel = (ImageView) holder.getView(R.id.iv_cancel);
                        TextView tvOk = (TextView) holder.getView(R.id.tv_ok);
                        LinearLayout llOne = (LinearLayout) holder.getView(R.id.ll_one);
                        LinearLayout llTwo = (LinearLayout) holder.getView(R.id.ll_two);
                        LinearLayout llThree = (LinearLayout) holder.getView(R.id.ll_three);
                        LinearLayout llFour = (LinearLayout) holder.getView(R.id.ll_four);
                        LinearLayout llFive = (LinearLayout) holder.getView(R.id.ll_five);
                        ImageView ivOne = (ImageView) holder.getView(R.id.iv_one);
                        ImageView ivTwo = (ImageView) holder.getView(R.id.iv_two);
                        ImageView ivThree = (ImageView) holder.getView(R.id.iv_three);
                        ImageView ivFour = (ImageView) holder.getView(R.id.iv_four);
                        ImageView ivFive = (ImageView) holder.getView(R.id.iv_five);

                        llOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_checked);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 1;

                            }
                        });
                        llTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_checked);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 2;
                            }
                        });
                        llThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_checked);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 3;
                            }
                        });

                        llFour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_checked);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 4;
                            }
                        });
                        llFive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_checked);
                                reason[0] = 5;
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                updateOrderStatus(1, dto.getId(), reason[0]);
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getActivity().getSupportFragmentManager());
    }

    /**
     * 更新订单状态
     *
     * @param status
     * @param orderId
     * @param reason
     * @return
     */
    private void updateOrderStatus(Integer status, String orderId, int reason) {
        getLoadingDialog().showDialog();
        viewModel.updateOrderStatus(status, orderId, reason);
        viewModel.getOrderStatusliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("操作成功");
                    pageNo = 1;
                    getOrderDatas();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                    getLoadingDialog().dismiss();
                }
            }
        });
    }


    /**
     * 确认收货
     */
    @Override
    public void showConfirmGoodsTipsDialog(MyOrderDto dto) {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_confirm_order_tips);
      /*  TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)dialog. findViewById(R.id.tv_content);*/
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                updateOrderStatus(2, dto.getId(), 0);
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 删除订单
     */
    @Override
    public void showDeleteOrderDialog(MyOrderDto dto)

    {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_delete_order_tips);
      /*  TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)dialog. findViewById(R.id.tv_content);*/
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                updateOrderStatus(3, dto.getId(), 0);
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void checkOgistics(MyOrderDto dto) {
        // 查看物流
        OrderTrackActivity.startActivity(getContext(), dto.getId());
    }
}
