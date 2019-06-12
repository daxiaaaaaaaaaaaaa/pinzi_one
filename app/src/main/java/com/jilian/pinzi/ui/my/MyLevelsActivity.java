package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.LevelAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonActivity;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.InviteListDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyLevelsActivity extends CommonActivity implements CustomItemClickListener {
    private LinearLayout llMylevelDetail;
    private SmartRefreshLayout srHasData;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;
    private TextView tvFour;
    private RecyclerView recyclerView;
    private LinearLayout llLevelBottom;
    private MyViewModel viewModel;
    private LevelAdapter adapter;
    private List<InviteListDto> mDataList;
    private LinearLayoutManager linearLayoutManager;
    private SmartRefreshLayout srNoData;
    private EditText etPhone;
    private TextView tvOk;
    private TextView tvInvitationCode;
    private TextView tvName;
    private TextView tvAdd;


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
        return R.layout.activity_my_levels;
    }

    @Override
    public void initView() {
        setNormalTitle("我的上下级", v -> finish());
        tvName = (TextView) findViewById(R.id.tv_name);

        tvAdd = (TextView) findViewById(R.id.tv_add);
        llMylevelDetail = (LinearLayout) findViewById(R.id.ll_mylevel_detail);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        tvThree = (TextView) findViewById(R.id.tv_three);
        tvFour = (TextView) findViewById(R.id.tv_four);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        llLevelBottom = (LinearLayout) findViewById(R.id.ll_level_bottom);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        mDataList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new LevelAdapter(type, this, mDataList, this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvInvitationCode = (TextView) findViewById(R.id.tv_invitationCode);
        if (EmptyUtils.isNotEmpty(getLoginDto()) && getLoginDto().getType() != 1) {

            tvThree.setVisibility(View.INVISIBLE);
            tvFour.setVisibility(View.INVISIBLE);
            tvFour.setEnabled(false);
            tvThree.setEnabled(false);
            tvOne.setText("我的服务商");
            tvTwo.setText("我的客户");
            setNormalTitle("服务关系", v -> finish());
            tvAdd.setText("+添加服务商");
            etPhone.setHint("输入服务商账号");

        }


    }

    @Override
    public void initData() {
        //获取上下级
        getInviteList();
    }

    private Integer type = 1;
    private int pageNo = 1;//
    private int pageSize = 20;//

    private void getInviteList() {
        viewModel.getInviteList(getUserId(), type, pageNo, pageSize);
        viewModel.getInviteList().observe(this, new Observer<BaseDto<List<InviteListDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<InviteListDto>> dto) {
                hideLoadingDialog();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        mDataList.clear();
                    }
                    mDataList.addAll(dto.getData());
                    adapter.setType(type);
                    adapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        adapter.setType(type);
                        adapter.notifyDataSetChanged();
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }

                initLevelVie();
            }
        });

    }

    private void initLevelVie() {
        //如果是获取是下级
        if (type != 1) {
            //重置 刷新控件的margin
            //重置 无数据 布局的 magin
            RelativeLayout.LayoutParams lp_has = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
            RelativeLayout.LayoutParams lp_no = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
            lp_has.setMargins(0, 0, 0, 0);
            lp_no.setMargins(0, 0, 0, 0);
            srHasData.setLayoutParams(lp_has);
            srNoData.setLayoutParams(lp_no);
            llLevelBottom.setVisibility(View.GONE);
        }
        //如果获取的是上级
        else {
            //普通用户 拥有一个上级 当有一个上级的时候 隐藏 添加按钮
            if (getLoginDto().getType() == 1) {
                if (adapter.getItemCount() >= 1) {
                    llLevelBottom.setVisibility(View.GONE);
                    tvInvitationCode.setText(getLoginDto().getInvitationCode());
                } else {
                    RelativeLayout.LayoutParams lp_has = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
                    RelativeLayout.LayoutParams lp_no = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
                    lp_has.setMargins(0, 0, 0, DisplayUtil.dip2px(MyLevelsActivity.this, 180));
                    lp_no.setMargins(0, 0, 0, DisplayUtil.dip2px(MyLevelsActivity.this, 180));
                    srHasData.setLayoutParams(lp_has);
                    srNoData.setLayoutParams(lp_no);
                    llLevelBottom.setVisibility(View.VISIBLE);
                    tvInvitationCode.setText(getLoginDto().getInvitationCode());
                }
            }
            //非普通用户
            else {
                tvInvitationCode.setText(getLoginDto().getInvitationCode());
                RelativeLayout.LayoutParams lp_has = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
                RelativeLayout.LayoutParams lp_no = new RelativeLayout.LayoutParams(srHasData.getLayoutParams());
                lp_has.setMargins(0, 0, 0, DisplayUtil.dip2px(MyLevelsActivity.this, 180));
                lp_no.setMargins(0, 0, 0, DisplayUtil.dip2px(MyLevelsActivity.this, 180));
                srHasData.setLayoutParams(lp_has);
                srNoData.setLayoutParams(lp_no);
                llLevelBottom.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                tvOne.setTextColor(getResources().getColor(R.color.color_main_select));
                tvTwo.setTextColor(getResources().getColor(R.color.color_text));
                tvThree.setTextColor(getResources().getColor(R.color.color_text));
                tvFour.setTextColor(getResources().getColor(R.color.color_text));
                type = 1;
                getInviteList();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                tvTwo.setTextColor(getResources().getColor(R.color.color_main_select));
                tvOne.setTextColor(getResources().getColor(R.color.color_text));
                tvThree.setTextColor(getResources().getColor(R.color.color_text));
                tvFour.setTextColor(getResources().getColor(R.color.color_text));
                type = 2;
                getInviteList();
            }
        });
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                tvThree.setTextColor(getResources().getColor(R.color.color_main_select));
                tvTwo.setTextColor(getResources().getColor(R.color.color_text));
                tvOne.setTextColor(getResources().getColor(R.color.color_text));
                tvFour.setTextColor(getResources().getColor(R.color.color_text));
                type = 3;
                getInviteList();
            }
        });
        tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                tvFour.setTextColor(getResources().getColor(R.color.color_main_select));
                tvTwo.setTextColor(getResources().getColor(R.color.color_text));
                tvThree.setTextColor(getResources().getColor(R.color.color_text));
                tvOne.setTextColor(getResources().getColor(R.color.color_text));
                type = 4;
                getInviteList();
            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getInviteList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getInviteList();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    return;
                }
                addMySuperior();
            }
        });
    }

    /**
     * 添加上级
     */
    private void addMySuperior() {
        showLoadingDialog();
        viewModel.addMySuperior(getUserId(), etPhone.getText().toString());
        viewModel.getAddMysuper().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("添加成功");
                    getInviteList();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        if (type == 1) {
            return;
        }
        Intent intent = new Intent(MyLevelsActivity.this, MyLevelsDetailActivity.class);
        intent.putExtra("inviteeId", mDataList.get(position).getuId());
        startActivity(intent);
    }
}
