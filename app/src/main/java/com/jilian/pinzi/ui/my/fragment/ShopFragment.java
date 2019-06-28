package com.jilian.pinzi.ui.my.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyGoodsAdapter;
import com.jilian.pinzi.adapter.MyShopsAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.common.vo.DeleteFootVo;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.MyCollectionActivity;
import com.jilian.pinzi.ui.my.MyFootActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class ShopFragment extends BaseFragment implements CustomItemClickListener, MyGoodsAdapter.DeleteListener {
    private RecyclerView recyclerView;
    private MyShopsAdapter shopsAdapter;
    private List<CollectionFootDto> datas;
    private CustomerLinearLayoutManager linearLayoutManager;
    private MyViewModel viewModel;
    private String classify;//1.收藏 2.足迹
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        datas = new ArrayList<>();
        linearLayoutManager = new CustomerLinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {
        //获取类型
        if (getmActivity() instanceof MyCollectionActivity) {
            classify = ((MyCollectionActivity) getmActivity()).getClassify();
        }
        if (getmActivity() instanceof MyFootActivity) {
            classify = ((MyFootActivity) getmActivity()).getClassify();
        }

    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    @Override
    public void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getFootPrintAndCollect();
    }

    /**
     * 获取地址列表
     */
    public void getFootPrintAndCollect() {
        viewModel.FootPrintAndCollect(PinziApplication.getInstance().getLoginDto().getId(), "2", classify, pageNo, pageSize);
        viewModel.getCollectionFootListliveData().observe(this, new Observer<BaseDto<List<CollectionFootDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<CollectionFootDto>> listBaseDto) {
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
                    datas.addAll(listBaseDto.getData());
                    shopsAdapter = new MyShopsAdapter(getmActivity(), datas, ShopFragment.this,ShopFragment.this,classify);
                    recyclerView.setAdapter(shopsAdapter);

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

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getFootPrintAndCollect();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getFootPrintAndCollect();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getFootPrintAndCollect();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {

    }
    /**
     * @param id
     * @param status 1 单个删除，或多个删除，2删除全部
     */
    private void delete(String uId, String id, Integer status) {
        DeleteFootVo vo = new DeleteFootVo();
        vo.setuId(uId);
        vo.setId(id);
        vo.setStatus(status);
        getLoadingDialog().showDialog();
        viewModel.delUserFootprint(vo);
        viewModel.getDelUserFoot().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    getFootPrintAndCollect();
                    ToastUitl.showImageToastSuccess("删除成功");
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }
    @Override
    public void delete(String collectId) {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_delete_order_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvContent.setText("是否确认删除？");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                delete(PinziApplication.getInstance().getLoginDto().getId(), collectId, 1);
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
}
