package com.jilian.pinzi.ui.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopGoodsAdapter;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.main.viewmodel.ShopViewModel;
import com.jilian.pinzi.ui.shopcard.viewmodel.ShopCardViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

/**
 * 商铺详情 Activity
 */
public class ShopDetailActivity extends BaseActivity implements ShopGoodsAdapter.AddOrDelListener {

    public static void startActivity(Context context, String shopId,int entrance) {
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("entrance",entrance);
        context.startActivity(intent);
    }

    private ImageView ivBack;
    private EditText etSearch;
    private ImageView ivForward;

    private ImageView ivShopHead;
    private TextView tvShopName;
    private TextView tvShopAttention;
    private TextView tvShopEvaluation;
    private LinearLayout llShopAttention;
    private TextView tvShopAddAttention;

    private TextView tvShopAddress;
    private TextView tvShopPhone;

    //    private SmartRefreshLayout refreshLayoutHas;
//    private SmartRefreshLayout refreshLayoutNo;
    private RecyclerView recyclerView;
    private RelativeLayout rlEmpty;

    private List<ShopGoodsDto> mGoodsList = new ArrayList<>();
    private ShopGoodsAdapter mGoodsAdapter;

    private ShopDetailDto mShopDetail;
    private String mStoreId;
    private String mGoodsName = "";
    /**
     * 是否关注 true: 已关注 false：未关注
     */
    private boolean isAttention = false;
    //    private int pageNo = 1;//
//    private int pageSize = 20;//
    private RelativeLayout rlHasData;
    private TextView tvJoinShopCart;
    private ShopViewModel shopViewModel;
    private MainViewModel mainViewModel;

    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        shopViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {


        rlHasData = (RelativeLayout) findViewById(R.id.rl_has_data);
        tvJoinShopCart = (TextView) findViewById(R.id.tv_join_shopCart);

        ivBack = findViewById(R.id.iv_shop_detail_back);
        etSearch = findViewById(R.id.et_shop_detail_search_name);
        ivForward = findViewById(R.id.iv_shop_detail_forward);

        ivShopHead = findViewById(R.id.iv_shop_detail_shop_head);
        tvShopName = findViewById(R.id.tv_shop_detail_shop_name);
        tvShopAttention = findViewById(R.id.tv_shop_detail_shop_attention);
        tvShopEvaluation = findViewById(R.id.tv_shop_detail_shop_evaluation);
        llShopAttention = findViewById(R.id.ll_shop_detail_attention);
        tvShopAddAttention = findViewById(R.id.tv_shop_detail_add_attention);
        tvShopAddress = findViewById(R.id.tv_shop_detail_address);
        tvShopPhone = findViewById(R.id.tv_shop_detail_phone);

//        refreshLayoutHas = findViewById(R.id.srl_shop_detail);
//        refreshLayoutNo = findViewById(R.id.sr_no_data);
        recyclerView = findViewById(R.id.rv_shop_detail);
        rlEmpty = findViewById(R.id.rl_shop_detail_empty);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mGoodsAdapter = new ShopGoodsAdapter(this, R.layout.item_my_goods_detail, mGoodsList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mGoodsAdapter);
    }

    @Override
    public void initData() {
        mStoreId = getIntent().getStringExtra("shopId");
    }

    @Override
    public void initListener() {
        tvJoinShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(ShopDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                //判断有数量 可以加入购物车
                if (!isAdd()) {
                    ToastUitl.showImageToastFail("请先选择商品数量");
                    return;
                }
                joinShopCart(getIntent().getIntExtra("classes", 1), getUserId(), getId(), getCount());
            }
        });
        ivBack.setOnClickListener(v -> finish());
        ivForward.setOnClickListener(v -> {
            // TODO: 转发
        });
        llShopAttention.setOnClickListener(v -> {
            // 关注商铺
            if (isAttention) {
                // 取消关注
                showLoadingDialog();
                mainViewModel.cancelCollect(String.valueOf(mShopDetail.getCollectId()));
                if (mainViewModel.getCancelCollectliveData().hasObservers()) return;
                mainViewModel.getCancelCollectliveData().observe(this, stringBaseDto -> {
                    hideLoadingDialog();
                    if (stringBaseDto == null) return;
                    if (stringBaseDto.isSuccess()) {
                        ToastUitl.showImageToastSuccess("取消关注成功");
                        getShopData();
                    } else {
                        ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                    }
                });
            } else {
                // 添加关注
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                showLoadingDialog();
                mainViewModel.collectGoodsOrStore(PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId(), mStoreId, 2);
                if (mainViewModel.getCollectGoodsOrStoreliveData().hasObservers()) return;
                mainViewModel.getCollectGoodsOrStoreliveData().observe(this, stringBaseDto -> {
                    hideLoadingDialog();
                    if (stringBaseDto == null) return;
                    if (stringBaseDto.isSuccess()) {
                        ToastUitl.showImageToastSuccess("关注成功");
                        getShopData();
                    } else {
                        ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                    }
                });
            }
        });
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                mGoodsName = etSearch.getText().toString().trim();
                showLoadingDialog();
                getStoreGoodsByName(mGoodsName);
                return true;
            }
            return false;
        });
        mGoodsAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(ShopDetailActivity.this, GoodsDetailActivity.class);
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
    private String getId() {
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
        showLoadingDialog();
        viewModel.joinShopCart(classes, userId, id, quantity);
        viewModel.getJoinShopCartliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("加入购物车成功");
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }

    @Override
    public void doBusiness() {
        getShopData();
    }

    /**
     * 获取商铺数据
     */
    private void getShopData() {
        showLoadingDialog();
        shopViewModel.getStoreDetail(mStoreId, PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId());
        shopViewModel.getShopDetailLiveData().observe(this, shopDetailDtoBaseDto -> {
            if (shopDetailDtoBaseDto == null) {
                hideLoadingDialog();
                return;
            }
            if (shopDetailDtoBaseDto.isSuccess()) {
                mShopDetail = shopDetailDtoBaseDto.getData();
                initShopInfo();
                getStoreGoodsByName(mGoodsName);
            } else {
                hideLoadingDialog();
                ToastUitl.showImageToastFail(shopDetailDtoBaseDto.getMsg());
            }
        });
    }

    /**
     * 获取商铺商品
     */
    private void getStoreGoodsByName(String goodsName) {

        shopViewModel.getStoreGoods(PinziApplication.getInstance().getLoginDto() == null ? 0 : PinziApplication.getInstance().getLoginDto().getType(), mStoreId, goodsName, String.valueOf(getIntent().getIntExtra("entrance",1)));
        if (shopViewModel.getShopGoodsLiveData().hasObservers()) return;
        shopViewModel.getShopGoodsLiveData().observe(this, shopGoodsBaseDto -> {
            hideLoadingDialog();
            if (shopGoodsBaseDto == null) return;
            if (shopGoodsBaseDto.isSuccess()) {
                List<ShopGoodsDto> goodsList = shopGoodsBaseDto.getData();
                if (goodsList == null || goodsList.size() == 0) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    rlHasData.setVisibility(View.GONE);
                } else {
                    rlEmpty.setVisibility(View.GONE);
                    rlHasData.setVisibility(View.VISIBLE);
                    mGoodsList.clear();
                    mGoodsList.addAll(goodsList);
                    mGoodsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initShopInfo() {
        Glide.with(this).load(mShopDetail.getStoreLogo())
                .placeholder(R.drawable.iv_default_head)
                .error(R.drawable.iv_default_head).into(ivShopHead);
        tvShopName.setText(mShopDetail.getStoreName());
        tvShopAttention.setText(mShopDetail.getCollectCount() + "人关注");
        tvShopEvaluation.setText("评价：" + mShopDetail.getStoreGrade() + "分");
        isAttention = !(mShopDetail.getCollectId() == 0);
        llShopAttention.setBackgroundResource(mShopDetail.getCollectId() == 0 ?
                R.drawable.shape_round_red_bg : R.drawable.shape_round_grey_bg);
        tvShopAddAttention.setText(mShopDetail.getCollectId() == 0 ? "+关注" : "√已关注");
        tvShopAddress.setText(mShopDetail.getCity() + mShopDetail.getArea() + mShopDetail.getAddress());
        tvShopPhone.setText(mShopDetail.getPhone());
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
