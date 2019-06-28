package com.jilian.pinzi.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopBusAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.shopcard.FillOrderActivity;
import com.jilian.pinzi.ui.shopcard.viewmodel.ShopCardViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FourFragment extends BaseFragment implements CustomItemClickListener, ShopBusAdapter.AddOrDelListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShopBusAdapter shopBusAdapter;
    private List<ShopCartLisDto> datas;
    private TextView tvOk;
    private ShopCardViewModel viewModel;
    private RelativeLayout rlCheckAll;
    private ImageView ivCheck;
    private TextView tvCount;
    private LinearLayout llHasData;
    private SmartRefreshLayout srNoData;


    @Override
    public void onResume() {
        super.onResume();
        //获取购物车列表
        getShopCartLis();

    }

    /**
     * 获取购物车列表
     */
    private void getShopCartLis() {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            return;
        }
        viewModel.getShopCartLis(PinziApplication.getInstance().getLoginDto().getId(), null, null);
        viewModel.getShopCartLisLiveData().observe(this, new Observer<BaseDto<List<ShopCartLisDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ShopCartLisDto>> listBaseDto) {
                srNoData.finishRefresh();

                if (listBaseDto.isSuccess() && EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    llHasData.setVisibility(View.VISIBLE);
                    srNoData.setVisibility(View.GONE);
                    List<ShopCartLisDto> netDatas = listBaseDto.getData();
                    if (EmptyUtils.isNotEmpty(netDatas) && EmptyUtils.isNotEmpty(datas)) {
                        //把选中的 用一个list 存起来
                        List<String> checkIdList = new ArrayList<>();
                        for (int i = 0; i < datas.size(); i++) {
                            if (datas.get(i).isChecked()) {
                                checkIdList.add(datas.get(i).getId());
                            }
                        }
                        for (int i = 0; i < netDatas.size(); i++) {
                            if (checkIdList.contains(netDatas.get(i).getId())) {
                                netDatas.get(i).setChecked(true);
                            }
                        }
                        datas.clear();
                        datas.addAll(netDatas);
                    } else {
                        datas.clear();
                        datas.addAll(netDatas);
                    }
                    initMoney();
                    shopBusAdapter.notifyDataSetChanged();
                    initAllCheck();
                } else {
                    datas.clear();
                    llHasData.setVisibility(View.GONE);
                    srNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(ShopCardViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_four;
    }

    private List<String> idList;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        setCenterTitle("购物车", "#FFFFFF");
        if (getmActivity().getIntent().getIntExtra("back", 1) == 2) {
            setleftImage(R.drawable.image_back, true, null);
        }

        setrightTitle("删除", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EmptyUtils.isNotEmpty(datas)) {
                    return;
                }
                idList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isChecked()) {
                        idList.add(datas.get(i).getId());
                    }
                }
                if (!EmptyUtils.isNotEmpty(idList)) {
                    ToastUitl.showImageToastFail("没有选择的商品");
                    return;
                }
                showDeleteOrderDialog();

            }
        });

        llHasData = (LinearLayout) view.findViewById(R.id.ll_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        rlCheckAll = (RelativeLayout) view.findViewById(R.id.rl_check_all);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvOk = (TextView) view.findViewById(R.id.tv_ok);
        ivCheck = (ImageView) view.findViewById(R.id.iv_check);
        recyclerView.setFocusable(false);
        recyclerView.addItemDecoration(new CustomerItemDecoration(1));
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        shopBusAdapter = new ShopBusAdapter(getmActivity(), datas, this, this);
        recyclerView.setAdapter(shopBusAdapter);

    }

    /**
     * 判断 有没有 跨店
     *
     * @param datas
     * @return
     */
    private boolean isDifferShop(List<ShopCartLisDto> datas) {
        List<String> shopIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isChecked()) {
                if (!shopIds.contains(datas.get(i).getStoreId()) && shopIds.size() > 0) {
                    return true;
                } else {
                    shopIds.add(datas.get(i).getStoreId());
                }
            }

        }
        return false;
    }

    /**
     * 是否确认删除
     */
    private void showDeleteOrderDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_delete_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                deleteShop();

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
     * 删除购物车商品
     */
    private void deleteShop() {
        getLoadingDialog().showDialog();
        String shopId = "";
        for (int i = 0; i < idList.size(); i++) {
            if (i == idList.size() - 1) {
                shopId += idList.get(i);
            } else {
                shopId += idList.get(i) + ",";
            }
        }
        viewModel.deleteGoods(1, PinziApplication.getInstance().getLoginDto().getId(), shopId);
        viewModel.getDeleteGoodLiveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                datas.clear();
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("删除成功");
                    getShopCartLis();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private boolean checkedAll;

    @Override
    protected void initListener() {
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getShopCartLis();
            }
        });
        rlCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedAll = !checkedAll;
                checkAll(checkedAll);
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderGoodsDto> dtoList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isChecked()) {
                        OrderGoodsDto dto = new OrderGoodsDto();
                        dto.setFreight(datas.get(i).getFreight());
                        dto.setCount(Integer.parseInt(datas.get(i).getQuantity()));
                        dto.setId(datas.get(i).getGoodsId());
                        dto.setFile(datas.get(i).getFile());
                        dto.setName(datas.get(i).getName());
                        dto.setClasses(datas.get(i).getClasses());
                        dto.setTopScore(datas.get(i).getTopScore());
                        dtoList.add(dto);

                    }
                }
                if (!EmptyUtils.isNotEmpty(dtoList)) {
                    ToastUitl.showImageToastFail("请选择商品在下单");
                    return;
                }
                if (isDifferShop(datas)) {
                    ToastUitl.showImageToastFail("不能跨店铺下单");
                    return;

                }
                Intent intent = new Intent(getmActivity(), FillOrderActivity.class);
                intent.putExtra("dtoList", JSONObject.toJSONString(dtoList));
                //购物车
                intent.putExtra("type", "1");
                intent.putExtra("orderType", "1");
                startActivity(intent);
            }
        });
    }

    /**
     * 选择全部 反选
     *
     * @param checkedAll
     */
    private void checkAll(boolean checkedAll) {
        if (checkedAll) {
            ivCheck.setImageResource(R.drawable.image_checked);
        } else {
            ivCheck.setImageResource(R.drawable.image_uncheck);
        }
        if (EmptyUtils.isNotEmpty(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setChecked(checkedAll);
            }
            shopBusAdapter.notifyDataSetChanged();
            initMoney();
            initAllCheck();
        }
    }

    //选中的数量
    private List<String> checkSize;

    private void initAllCheck() {
        checkSize = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isChecked()) {
                checkSize.add(datas.get(i).getId());
            }
        }
        if (checkSize.size() == datas.size()) {
            ivCheck.setImageResource(R.drawable.image_checked);
        } else {
            ivCheck.setImageResource(R.drawable.image_uncheck);
        }

    }

    private double money;

    /**
     * 计算金额
     */
    private void initMoney() {
        if (EmptyUtils.isNotEmpty(datas)) {
            money = 0;
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).isChecked()) {
                    if (datas.get(i).getSeckillPrice() == 0) {
                        money += datas.get(i).getFastPrice() * Integer.parseInt(datas.get(i).getQuantity());
                    } else {
                        money += datas.get(i).getSeckillPrice() * Integer.parseInt(datas.get(i).getQuantity());
                    }

                }
            }
            tvCount.setText(NumberUtils.forMatNumber(money));
        }
    }

    @Override

    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getGoodsId());
        intent.putExtra("classes", datas.get(position).getClasses());
        if(datas.get(position).getSeckillPrice()>0){
            //判斷是否是秒殺商品
            intent.putExtra("type",2);
        }
        startActivity(intent);
    }

    /**
     * 增加数量
     *
     * @param position
     */
    @Override
    public void add(int position) {

        viewModel.addOrReduce(datas.get(position).getId(), 1);
        viewModel.getAddOrReduceLiveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (!stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                } else {
                    getShopCartLis();
                }
            }
        });

    }

    /**
     * 减少数量
     *
     * @param position
     */
    @Override
    public void del(int position) {
        if ("1".equals(datas.get(position).getQuantity())) {
            return;
        }
        viewModel.addOrReduce(datas.get(position).getId(), 2);
        viewModel.getAddOrReduceLiveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (!stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                } else {
                    getShopCartLis();
                }
            }
        });


    }

    /**
     * 选中某一项
     *
     * @param position
     */
    @Override
    public void check(int position) {
        datas.get(position).setChecked(!datas.get(position).isChecked());
        shopBusAdapter.notifyDataSetChanged();
        initMoney();
        initAllCheck();
    }
}
