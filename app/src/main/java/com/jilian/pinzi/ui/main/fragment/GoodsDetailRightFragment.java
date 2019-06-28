package com.jilian.pinzi.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.BottomAdapter;
import com.jilian.pinzi.adapter.TopAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.CommentTitleDto;
import com.jilian.pinzi.common.dto.GoodsEvaluateDto;
import com.jilian.pinzi.common.dto.GoodsEvaluateItem;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailRightFragment extends BaseFragment implements CustomItemClickListener, TopAdapter.TopClickListener {
    private RecyclerView rvTop;
    private RecyclerView rvBottom;
    private GridLayoutManager gm_top;
    private LinearLayoutManager lm_bottom;
    private TopAdapter topAdapter;
    private List<CommentTitleDto> mCommentTitleDtoList;
    private List<GoodsEvaluateItem> datas;
    private BottomAdapter bottomAdapter;
    private MainViewModel viewModel;
    private SmartRefreshLayout srNoData;
    private SmartRefreshLayout srHasData;




    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goodsdetailright;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvTop = (RecyclerView) view.findViewById(R.id.rv_top);
        rvBottom = (RecyclerView) view.findViewById(R.id.rv_bottom);
        rvBottom.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getmActivity(), 10)));
        gm_top = new GridLayoutManager(getmActivity(), 5);
        lm_bottom = new LinearLayoutManager(getmActivity());
        rvTop.setLayoutManager(gm_top);
        rvBottom.setLayoutManager(lm_bottom);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
        srNoData.setEnableRefresh(true);
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);

        //top
        mCommentTitleDtoList = new ArrayList<>();
        topAdapter = new TopAdapter(getmActivity(), mCommentTitleDtoList, this);
        rvTop.setAdapter(topAdapter);

        //bottom
        datas = new ArrayList<>();
        bottomAdapter = new BottomAdapter(getmActivity(),datas,this);
        rvBottom.setAdapter(bottomAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    private int  type = 0;
    /**
     * 获取商品评价
     *
     * @param //type    0.全部 1.差 2.中 3.好 4.有图
     * @param goodsId
     * @param id
     */
    private void getGoodsEvaluate(boolean isFirst, String goodsId, String id) {
        viewModel.getGoodsEvaluate(pageNo,pageSize,type, goodsId, id);
        viewModel.getGoodsEvaluateliveData().observe(this, new Observer<BaseDto<GoodsEvaluateDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<GoodsEvaluateDto> goodsEvaluateDtoBaseDto) {
                getLoadingDialog().dismiss();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                srNoData.finishRefresh();
                if (EmptyUtils.isNotEmpty(goodsEvaluateDtoBaseDto.getData())) {
                    if (isFirst) {
                        mCommentTitleDtoList.clear();
                        mCommentTitleDtoList.add(new CommentTitleDto("全部评价",
                                goodsEvaluateDtoBaseDto.getData().getBadCount() + goodsEvaluateDtoBaseDto.getData().getGoodCount() +
                                        goodsEvaluateDtoBaseDto.getData().getMediumCount() + "", true));
                        mCommentTitleDtoList.add(new CommentTitleDto("好评", goodsEvaluateDtoBaseDto.getData().getGoodCount() + "", false));
                        mCommentTitleDtoList.add(new CommentTitleDto("中评", goodsEvaluateDtoBaseDto.getData().getMediumCount() + "", false));
                        mCommentTitleDtoList.add(new CommentTitleDto("差评", goodsEvaluateDtoBaseDto.getData().getBadCount() + "", false));
                        mCommentTitleDtoList.add(new CommentTitleDto("有图", goodsEvaluateDtoBaseDto.getData().getHavePictures() + "", false));
                        topAdapter.notifyDataSetChanged();
                    }
                    //有数据
                    if (EmptyUtils.isNotEmpty(goodsEvaluateDtoBaseDto.getData().getList())) {
                        srNoData.setVisibility(View.GONE);
                        srHasData.setVisibility(View.VISIBLE);
                        if (pageNo == 1) {
                            datas.clear();
                        }
                        datas.addAll(goodsEvaluateDtoBaseDto.getData().getList());
                        bottomAdapter.notifyDataSetChanged();
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

            }
        });
    }

    @Override
    protected void initData() {
        pageNo = 1;
        getGoodsEvaluate(true, getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null:PinziApplication.getInstance().getLoginDto().getId());
    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    //0.全部 1.差 2.中 3.好 4.有图
    @Override
    public void topClick(View view, int position) {
        for (int i = 0; i < mCommentTitleDtoList.size(); i++) {
            if (position == i) {
                mCommentTitleDtoList.get(i).setSelected(true);
            } else {
                mCommentTitleDtoList.get(i).setSelected(false);
            }
        }
        topAdapter.notifyDataSetChanged();
        //0.全部 1.差 2.中 3.好 4.有图
        switch (position) {

            case 0:
                //全部
                getLoadingDialog().showDialog();
                pageNo = 1;
                type = 0;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
                break;
            case 1:
                //好评
                getLoadingDialog().showDialog();
                pageNo = 1;
                type = 3;
                getGoodsEvaluate(false, getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
                break;
            case 2:
                //中评
                getLoadingDialog().showDialog();
                pageNo = 1;
                type = 2;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
                break;
            case 3:
                //差评
                getLoadingDialog().showDialog();
                pageNo = 1;
                type = 1;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
                break;
            case 4:
                //有图
                getLoadingDialog().showDialog();
                pageNo = 1;
                type = 4;
                getGoodsEvaluate(false,  getmActivity().getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto()==null?null: PinziApplication.getInstance().getLoginDto().getId());
                break;
        }

    }
}
