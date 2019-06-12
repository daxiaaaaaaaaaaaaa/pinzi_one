package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.jilian.pinzi.common.vo.DeleteFootVo;
import com.jilian.pinzi.ui.my.fragment.GoodsFragment;
import com.jilian.pinzi.ui.my.fragment.ShopFragment;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomScrollViewPager;
import com.jilian.pinzi.views.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyFootActivity extends BaseActivity {
    private List<Fragment> mFragmentList;
    private GoodsFragment goodsFragment;
    private ShopFragment shopFragment;

    private CarTapPagerAdapter adapter;
    private NoScrollViewPager viewPager;
    private View vOne;
    private View vTwo;
    private TextView tvOne;
    private TextView tvTwo;
    private String classify;//1.收藏 2.足迹
    private MyViewModel viewModel;

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

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
        return R.layout.activity_mycollection;
    }

    @Override
    public void initView() {
        setCenterTitle("我的足迹", "#FFFFFF");
        setrightTitle("全部删除", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteDialog();


            }
        });
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
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
                    ToastUitl.showImageToastSuccess("删除成功");
                    goodsFragment.getFootPrintAndCollect();
                    shopFragment.getFootPrintAndCollect();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void initData() {
        setClassify("2");
        mFragmentList = new ArrayList<>();
        goodsFragment = new GoodsFragment();
        shopFragment = new ShopFragment();
        mFragmentList.add(goodsFragment);
        mFragmentList.add(shopFragment);
        adapter = new CarTapPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void initListener() {
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
                    case 2:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 提示删除对话框
     *
     */
    private void showDeleteDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_order_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvContent.setText("是否确认删除？");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                delete(getUserId(), null, 2);
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
