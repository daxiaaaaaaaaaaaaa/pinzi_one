package com.jilian.pinzi.ui.shopcard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CarTapPagerAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.shopcard.fragment.DisableCardFragment;
import com.jilian.pinzi.ui.shopcard.fragment.EnableCardFragment;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择优惠券
 */
public class SelectCardActivity extends BaseActivity {
    private List<Fragment> mFragmentList;
    private EnableCardFragment enableCardFragment;
    private DisableCardFragment disableCardFragment;
    private CarTapPagerAdapter adapter;
    private CustomScrollViewPager viewPager;
    private View vOne;
    private View vTwo;
    private TextView tvOne;
    private TextView tvTwo;
    private MainViewModel mainViewModel;
    private TextView tvOk;


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
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_select_card;
    }

    @Override
    public void initView() {
        setCenterTitle("选择优惠券", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        viewPager = (CustomScrollViewPager) findViewById(R.id.viewPager);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
    }


    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        enableCardFragment = new EnableCardFragment();
        disableCardFragment = new DisableCardFragment();
        mFragmentList.add(enableCardFragment);
        mFragmentList.add(disableCardFragment);
        adapter = new CarTapPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        getDiscountConpou();

    }



    /**
     * 选择优惠券
     */
    private void getDiscountConpou() {
        showLoadingDialog();
        mainViewModel.getDiscountConpou(getUserId(), getIntent().getStringExtra("goodsId"), getIntent().getStringExtra("quantity"), getIntent().getStringExtra("classes"));
        mainViewModel.getDiscountConpouDtoliveData().observe(this, new Observer<BaseDto<DiscountConpouDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<DiscountConpouDto> dto) {
                hideLoadingDialog();
                enableCardFragment.getData(dto.getData().getUsableList());
                disableCardFragment.getData(dto.getData().getDisabledList());

            }
        });


    }

    /**
     * 分发给孩子
     *
     * @param discountConpouDto
     */
    private void postToFragment(DiscountConpouDto discountConpouDto) {
        EventBus.getDefault().postSticky(discountConpouDto);
    }

    @Override
    public void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                if (EmptyUtils.isNotEmpty(enableCardFragment.getDatas())) {
                    for (int i = 0; i < enableCardFragment.getDatas().size(); i++) {
                        if (enableCardFragment.getDatas().get(i).isCheck()) {
                            intent.putExtra("data", enableCardFragment.getDatas().get(i));
                            break;
                        }
                    }
                }
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        vOne.setVisibility(View.VISIBLE);
                        vTwo.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#c71233"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));

                        break;
                    case 1:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.VISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#c71233"));

                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
