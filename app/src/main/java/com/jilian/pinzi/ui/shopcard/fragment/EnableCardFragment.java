package com.jilian.pinzi.ui.shopcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.EnableAdapter;
import com.jilian.pinzi.adapter.NoUserAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
import com.jilian.pinzi.common.dto.GoodsDetailDto;
import com.jilian.pinzi.common.dto.SelectCardDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.MyCarddetailActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 可用的优惠券
 */
public class EnableCardFragment extends BaseFragment implements CustomItemClickListener,EnableAdapter.CheckListener {
    private RecyclerView recyclerView;
    private EnableAdapter adapter;
    private List<SelectCardDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private SmartRefreshLayout srNoData;

    public List<SelectCardDto> getDatas() {
        return datas;
    }

    public void setDatas(List<SelectCardDto> datas) {
        this.datas = datas;
    }



    @Override
    protected void createViewModel() {

    }

    public void getData( List<SelectCardDto> usableList ) {
        if(EmptyUtils.isNotEmpty(usableList)){
            datas.clear();
            String couponId = getActivity().getIntent().getStringExtra("couponId");
            datas.addAll(usableList);
            for (int i = 0; i <datas.size() ; i++) {
                if(datas.get(i).getId().equals(couponId)){
                    datas.get(i).setCheck(true);
                }
            }
            recyclerView.setVisibility(View.VISIBLE);
            srNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
        else{
            recyclerView.setVisibility(View.GONE);
            srNoData.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_enable;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout)view. findViewById(R.id.sr_no_data);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getActivity(), 10)));
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new EnableAdapter(getActivity(), datas, this,this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableRefresh(false);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {
        //startActivity(new Intent(getActivity(), MyCarddetailActivity.class));
    }

    @Override
    public void checkClick(int position) {
        for (int i = 0; i <datas.size() ; i++) {
            if(position == i){
                datas.get(i) .setCheck(true);
            }
            else{
                datas.get(i) .setCheck(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
