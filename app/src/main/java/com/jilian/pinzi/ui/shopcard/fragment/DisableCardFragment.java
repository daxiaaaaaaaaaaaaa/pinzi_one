package com.jilian.pinzi.ui.shopcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.DisableAdapter;
import com.jilian.pinzi.adapter.NoUserAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
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
 * 不可用的优惠券
 */
public class DisableCardFragment extends BaseFragment implements CustomItemClickListener,DisableAdapter.CheckListener {
    private RecyclerView recyclerView;
    private DisableAdapter adapter;
    private List<SelectCardDto> datas;

    private LinearLayoutManager linearLayoutManager;
    private SmartRefreshLayout srNoData;
    @Override
    protected void createViewModel() {

    }
    public void getData(List<SelectCardDto> disabledList ) {
        if(EmptyUtils.isNotEmpty(disabledList)){
            datas.clear();
            datas.addAll(disabledList);
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
        return R.layout.fragment_disable;
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
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getmActivity(), 10)));
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DisableAdapter(getmActivity(), datas, this,this);
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
    }

    @Override
    public void checkClick(int position) {
//        for (int i = 0; i <datas.size() ; i++) {
//            if(position == i){
//                datas.get(i) .setCheck(true);
//            }
//            else{
//                datas.get(i) .setCheck(false);
//            }
//        }
//        adapter.notifyDataSetChanged();
    }
}
