package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyLevelDetailAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.InviteeDetailDto;
import com.jilian.pinzi.common.dto.InviteeDetailItem;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CircularImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyLevelsDetailActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private MyLevelDetailAdapter adapter;
    private List<InviteeDetailItem> data;
    private LinearLayoutManager linearLayoutManager;
    private MyViewModel viewModel;
    private CircularImageView ivHead;
    private TextView tvNickName;
    private TextView tvMember;
    private TextView tvPhone;
    private TextView tvLinkePhone;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private String []  types = new String[]{"","会员","会员","会员","会员"};
    private String []  isVips = new String[]{"","普通","金牌","铂金","钻石","皇冠"};
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
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mylevelsdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("我的下级详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new MyLevelDetailAdapter(this, data, this);
        ivHead = (CircularImageView) findViewById(R.id.iv_head);
        tvNickName = (TextView) findViewById(R.id.tv_nick_name);
        tvMember = (TextView) findViewById(R.id.tv_member);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvLinkePhone = (TextView) findViewById(R.id.tv_linke_phone);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        getIntent().getStringExtra("inviteeId");

    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    /**
     * 获取下级详情
     */
    private void getInviteeDetail() {
        viewModel.getInviteeDetail(getUserId(), getIntent().getStringExtra("inviteeId"), pageNo,pageSize);
        viewModel.getInviteeDetail().observe(this, new Observer<BaseDto<InviteeDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<InviteeDetailDto> dto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                tvNickName.setText("昵称："+dto.getData().getName());
                tvLinkePhone.setText("联系电话："+dto.getData().getLinkPhone()==null?"":dto.getData().getLinkPhone());
                tvPhone.setText("账号："+dto.getData().getPhone());
                tvMember.setText(isVips[dto.getData().getIsVip()]+types[dto.getData().getType()]);
                Glide
                        .with(MyLevelsDetailActivity.this)
                        .load(UrlUtils.getUrl(dto.getData().getHeadImg()))
                        .into(ivHead);
                //有数据
                if (EmptyUtils.isNotEmpty(dto.getData().getList())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        data.clear();
                    }
                    data.addAll(dto.getData().getList());
                    adapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        adapter.notifyDataSetChanged();
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    @Override
    public void initData() {
        showLoadingDialog();
        getInviteeDetail();
    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getInviteeDetail();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getInviteeDetail();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getInviteeDetail();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
