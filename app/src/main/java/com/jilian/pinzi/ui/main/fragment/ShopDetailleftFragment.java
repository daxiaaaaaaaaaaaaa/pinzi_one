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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopGoodsAdapter;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.main.viewmodel.ShopViewModel;
import com.jilian.pinzi.ui.shopcard.FillOrderActivity;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private LinearLayout llBottom;
    private LinearLayout llShop;
    private TextView tvBuy;


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

        llShop = (LinearLayout) view.findViewById(R.id.ll_shop);
        tvBuy = (TextView) view.findViewById(R.id.tv_buy);
        llBottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        rlHasData = (RelativeLayout) view.findViewById(R.id.rl_has_data);
        rvShopDetail = (RecyclerView) view.findViewById(R.id.rv_shop_detail);
        tvJoinShopCart = (TextView) view.findViewById(R.id.tv_join_shopCart);
        rlShopDetailEmpty = (RelativeLayout) view.findViewById(R.id.rl_shop_detail_empty);
        etSearch = getmActivity().findViewById(R.id.et_shop_detail_search_name);
        initRecyclerView();

    }

    private void initRecyclerView() {
        mGoodsAdapter = new ShopGoodsAdapter(getmActivity(), R.layout.item_my_goods_detail, mGoodsList, this,getmActivity().getIntent().getIntExtra("classes", 1));
        rvShopDetail.setLayoutManager(new LinearLayoutManager(getmActivity()));
        rvShopDetail.setAdapter(mGoodsAdapter);
    }

    @Override
    protected void initData() {
        getStoreGoodsByName(mGoodsName);
    }

    @Override
    protected void initListener() {
        llShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getmActivity(), MainActivity.class);
                intent.putExtra("index", 3);
                intent.putExtra("back", 2);
                startActivity(intent);
            }
        });
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
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                //判断有数量 可以加入购物车
                if (!isAdd()) {
                    ToastUitl.showImageToastFail("请先选择商品数量");
                    return;
                }
                joinShopCart(getmActivity().getIntent().getIntExtra("classes", 1), PinziApplication.getInstance().getLoginDto().getId(), getIdStr(), getCount());
            }
        });

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                //判断有数量 可以加入购物车
                if (!isAdd()) {
                    ToastUitl.showImageToastFail("请先选择商品数量");
                    return;
                }
                if (differTypeGood()) {
                    ToastUitl.showImageToastFail("定金商品和非定金商品不能同时购买");
                    return;
                }

                List<OrderGoodsDto> dtoList = new ArrayList<>();
                for (int i = 0; i < mGoodsList.size(); i++) {
                    if (mGoodsList.get(i).getQuantity() > 0) {
                        OrderGoodsDto dto = new OrderGoodsDto();
                        dto.setFreight(mGoodsList.get(i).getFreight());
                        dto.setCount(mGoodsList.get(i).getQuantity());
                        dto.setId(String.valueOf(mGoodsList.get(i).getId()));
                        dto.setFile(mGoodsList.get(i).getFile());
                        dto.setName(mGoodsList.get(i).getName());
                        dto.setClasses(getClasses());
                        dto.setTopScore(mGoodsList.get(i).getScore());
                        dtoList.add(dto);

                    }
                }
                if (!EmptyUtils.isNotEmpty(dtoList)) {
                    ToastUitl.showImageToastFail("请选择商品在下单");
                    return;
                }

                Intent intent = new Intent(getmActivity(), FillOrderActivity.class);
                intent.putExtra("dtoList", JSONObject.toJSONString(dtoList));
                intent.putExtra("type", "2");
                intent.putExtra("orderType", "1");
                startActivity(intent);
            }
        });
        mGoodsAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsId", String.valueOf(mGoodsList.get(position).getId()));

                float score = mGoodsList.get(position).getScore();
                //积分商品
                if(score>0){
                    intent.putExtra("shopType", 2);//积分商城
                }
                intent.putExtra("classes", getClasses());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public int getClasses() {
        return getmActivity().getIntent().getIntExtra("classes", 1);
    }

    /**
     * 判断是否同时包含了定金 和 非定金商品
     *
     * @return
     */
    private boolean differTypeGood() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < mGoodsList.size(); i++) {
            if (mGoodsList.get(i).getQuantity() > 0) {
                set.add(String.valueOf(mGoodsList.get(i).getIsEarnest()));
            }
        }
        if (set.size() > 1) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 判断是否有商品数量
     * 才能假如购物车
     *
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

        shopViewModel.getStoreGoods(PinziApplication.getInstance().getLoginDto() == null ? 0 : PinziApplication.getInstance().getLoginDto().getType(), getmActivity().getIntent().getStringExtra("shopId"), goodsName, String.valueOf(getmActivity().getIntent().getIntExtra("entrance", 1)));
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
