package com.jilian.pinzi.ui.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.ui.CommonViewPagerAdapter;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.main.fragment.ShopDetailCenterFragment;
import com.jilian.pinzi.ui.main.fragment.ShopDetailRightFragment;
import com.jilian.pinzi.ui.main.fragment.ShopDetailleftFragment;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.main.viewmodel.ShopViewModel;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 商铺详情 Activity
 */
public class ShopDetailActivity extends BaseActivity {

    public static void startActivity(Context context, String shopId, int entrance) {
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("entrance", entrance);
        context.startActivity(intent);
    }

    private ImageView ivBack;
    private ImageView ivForward;

    private ImageView ivShopHead;
    private TextView tvShopName;
    private TextView tvShopAttention;
    private TextView tvShopEvaluation;
    private TextView tvShopAddAttention;


    private ShopDetailDto mShopDetail;
    private String mStoreId;

    /**
     * 是否关注 true: 已关注 false：未关注
     */
    private boolean isAttention = false;
    private ShopViewModel shopViewModel;
    private MainViewModel mainViewModel;
    private LinearLayout llShopAttention;
    private ShopDetailleftFragment shopDetailleftFragment;
    private ShopDetailRightFragment shopDetailRightFragment;
    private ShopDetailCenterFragment shopDetailCenterFragment;
    private List<Fragment> mFragmentList;
    private CommonViewPagerAdapter mainTapPagerAdapter;
    private NoScrollViewPager viewPager;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;
    private View vOne;
    private View vTwo;
    private View vThree;


    @Override
    protected void createViewModel() {
        shopViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        tvThree = (TextView) findViewById(R.id.tv_three);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        vThree = (View) findViewById(R.id.v_three);
        ivBack = findViewById(R.id.iv_shop_detail_back);
        ivForward = findViewById(R.id.iv_shop_detail_forward);
        ivShopHead = findViewById(R.id.iv_shop_detail_shop_head);
        tvShopName = findViewById(R.id.tv_shop_detail_shop_name);
        tvShopAttention = findViewById(R.id.tv_shop_detail_shop_attention);
        tvShopEvaluation = findViewById(R.id.tv_shop_detail_shop_evaluation);
        llShopAttention = findViewById(R.id.ll_shop_detail_attention);
        tvShopAddAttention = findViewById(R.id.tv_shop_detail_add_attention);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        mFragmentList = new ArrayList<>();
        shopDetailleftFragment = new ShopDetailleftFragment();
        shopDetailCenterFragment = new ShopDetailCenterFragment();
        shopDetailRightFragment = new ShopDetailRightFragment();
        mFragmentList.add(shopDetailleftFragment);
        mFragmentList.add(shopDetailCenterFragment);
        mFragmentList.add(shopDetailRightFragment);
        //
        mainTapPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mainTapPagerAdapter);


    }


    @Override
    public void initData() {
        mStoreId = getIntent().getStringExtra("shopId");
    }

    @Override
    public void initListener() {

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


        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                vThree.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#c71233"));
                tvTwo.setTextColor(Color.parseColor("#888888"));
                tvThree.setTextColor(Color.parseColor("#888888"));
                viewPager.setCurrentItem(0);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#c71233"));
                vThree.setVisibility(View.INVISIBLE);
                tvThree.setTextColor(Color.parseColor("#888888"));
                viewPager.setCurrentItem(1);
            }
        });

        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#888888"));

                vThree.setVisibility(View.VISIBLE);
                tvThree.setTextColor(Color.parseColor("#c71233"));
                viewPager.setCurrentItem(2);
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
            hideLoadingDialog();
            if (shopDetailDtoBaseDto.isSuccess()) {
                mShopDetail = shopDetailDtoBaseDto.getData();
                initShopInfo();

            } else {
                hideLoadingDialog();
                ToastUitl.showImageToastFail(shopDetailDtoBaseDto.getMsg());
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
        shopDetailCenterFragment.initDataView(mShopDetail);

    }


}
