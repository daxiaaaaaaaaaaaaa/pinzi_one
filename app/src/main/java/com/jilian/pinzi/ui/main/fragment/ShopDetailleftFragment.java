package com.jilian.pinzi.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopGoodsAdapter;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.main.viewmodel.ShopViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailleftFragment extends BaseFragment implements ShopGoodsAdapter.AddOrDelListener {
    private RelativeLayout rlHasData;
    private RecyclerView rvShopDetail;
    private TextView tvJoinShopCart;
    private RelativeLayout rlShopDetailEmpty;
    private String mGoodsName = "";
    private EditText etSearch;
    private MainViewModel viewModel;
    private List<ShopGoodsDto> mGoodsList = new ArrayList<>();
    private ShopGoodsAdapter mGoodsAdapter;
    private ShopViewModel shopViewModel;


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        shopViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopdetailleft;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rlHasData = (RelativeLayout) view.findViewById(R.id.rl_has_data);
        rvShopDetail = (RecyclerView) view.findViewById(R.id.rv_shop_detail);
        tvJoinShopCart = (TextView) view.findViewById(R.id.tv_join_shopCart);
        rlShopDetailEmpty = (RelativeLayout)view. findViewById(R.id.rl_shop_detail_empty);
        etSearch = getActivity().findViewById (R.id.et_shop_detail_search_name);
        initRecyclerView();

    }
    private void initRecyclerView() {
        mGoodsAdapter = new ShopGoodsAdapter(getActivity(), R.layout.item_my_goods_detail, mGoodsList, this);
        rvShopDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvShopDetail.setAdapter(mGoodsAdapter);
    }

    @Override
    protected void initData() {
        getStoreGoodsByName(mGoodsName);
    }

    @Override
    protected void initListener() {
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                mGoodsName = etSearch.getText().toString().trim();
                getStoreGoodsByName(mGoodsName);
                return true;
            }
            return false;
        });
        tvJoinShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                //判断有数量 可以加入购物车
                if (!isAdd()) {
                    ToastUitl.showImageToastFail("请先选择商品数量");
                    return;
                }
                joinShopCart(getActivity().getIntent().getIntExtra("classes", 1), PinziApplication.getInstance().getLoginDto().getId(), getIdStr(), getCount());
            }
        });
        mGoodsAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsId", String.valueOf(mGoodsList.get(position).getId()));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    /**
     * 判断是否有商品数量
     * 才能假如购物车
     * @return
     */
    private boolean isAdd() {
        boolean flag = false;
        if (EmptyUtils.isNotEmpty(mGoodsList)) {
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (mGoodsList.get(i).getQuantity() > 0) {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;

    }

    /**
     * 获取需要假如购物车的数量
     *
     * @return
     */
    private String getCount() {
        String count = "";
        if (EmptyUtils.isNotEmpty(mGoodsList)) {
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (mGoodsList.get(i).getQuantity() > 0) {
                    if (TextUtils.isEmpty(count)) {
                        count = String.valueOf(mGoodsList.get(i).getQuantity());
                    } else {
                        count += "," + String.valueOf(mGoodsList.get(i).getQuantity());
                    }

                }
            }
        }
        return count;
    }

    /**
     * 获取需要假如购物车的ID
     *
     * @return
     */
    private String getIdStr() {
        String id = "";
        if (EmptyUtils.isNotEmpty(mGoodsList)) {
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (mGoodsList.get(i).getQuantity() > 0) {
                    if (TextUtils.isEmpty(id)) {
                        id = String.valueOf(mGoodsList.get(i).getId());
                    } else {
                        id += "," + String.valueOf(mGoodsList.get(i).getId());
                    }

                }
            }
        }
        return id;
    }

    /**
     * 加入购物车
     *
     * @param userId
     * @param id
     * @param quantity
     * @param classes
     */
    private void joinShopCart(Integer classes, String userId, String id, String quantity) {
        viewModel.joinShopCart(classes, userId, id, quantity);
        viewModel.getJoinShopCartliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("加入购物车成功");
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }

    /**
     * 获取商铺商品
     */
    private void getStoreGoodsByName(String goodsName) {

        shopViewModel.getStoreGoods(PinziApplication.getInstance().getLoginDto() == null ? 0 : PinziApplication.getInstance().getLoginDto().getType(), getActivity().getIntent().getStringExtra("shopId"), goodsName, String.valueOf(getActivity().getIntent().getIntExtra("entrance",1)));
        shopViewModel.getShopGoodsLiveData().observe(this, shopGoodsBaseDto -> {
            if (shopGoodsBaseDto == null) return;
            if (shopGoodsBaseDto.isSuccess()) {
                List<ShopGoodsDto> goodsList = shopGoodsBaseDto.getData();
                if (goodsList == null || goodsList.size() == 0) {
                    rlShopDetailEmpty.setVisibility(View.VISIBLE);
                    rlHasData.setVisibility(View.GONE);
                } else {
                    rlShopDetailEmpty.setVisibility(View.GONE);
                    rlHasData.setVisibility(View.VISIBLE);
                    mGoodsList.clear();
                    mGoodsList.addAll(goodsList);
                    mGoodsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void add(int position) {
        mGoodsList.get(position).setQuantity(mGoodsList.get(position).getQuantity() + 1);
        mGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void del(int position) {
        if (mGoodsList.get(position).getQuantity() == 0) {
            return;
        }
        mGoodsList.get(position).setQuantity(mGoodsList.get(position).getQuantity() - 1);
        mGoodsAdapter.notifyDataSetChanged();
    }
}
