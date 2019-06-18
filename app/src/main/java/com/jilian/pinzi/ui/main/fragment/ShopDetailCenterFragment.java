package com.jilian.pinzi.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopPhotpAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopDetailCenterFragment extends BaseFragment implements CustomItemClickListener {
    //商品评分
    private TextView tvComment;
    //营业时间
    private TextView tvTime;
    private TextView tvShopDetailAddress;
    private TextView tvShopDetailPhone;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShopPhotpAdapter adapter;
    private List<String> datas;

    public void initDataView(ShopDetailDto mShopDetail) {
        //地址
        tvShopDetailAddress.setText(mShopDetail.getCity() + mShopDetail.getArea() + mShopDetail.getAddress());
        //电话
        tvShopDetailPhone.setText(mShopDetail.getPhone());
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopdetailcenter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvShopDetailAddress = (TextView) view.findViewById(R.id.tv_shop_detail_address);
        tvShopDetailPhone = (TextView) view.findViewById(R.id.tv_shop_detail_phone);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //调整RecyclerView的排列方向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION, DisplayUtil.dip2px(getActivity(), 15));//左间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        adapter = new ShopPhotpAdapter(getActivity(), datas, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
