package com.jilian.pinzi.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.my.fragment.AllOrderFragment;
import com.jilian.pinzi.ui.my.fragment.HavedCancelFragment;
import com.jilian.pinzi.ui.my.fragment.HavedFinishFragment;
import com.jilian.pinzi.ui.my.fragment.WaiteGetGoodsFragment;
import com.jilian.pinzi.ui.my.fragment.WaitePayOrderFragment;
import com.jilian.pinzi.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity implements ViewPagerItemClickListener {
    private List<Fragment> fragmentList;
    private AllOrderFragment allOrderFragment;
    private WaitePayOrderFragment waitePayOrderFragment;
    private WaiteGetGoodsFragment waiteGetGoodsFragment;
    private HavedFinishFragment havedFinishFragment;
    private HavedCancelFragment havedCancelFragment;
    private TabLayout tlTab;
    private NoScrollViewPager viewPager;
    String[] mTitle = new String[5];
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

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myorder;
    }

    @Override
    public void initView() {
        setNormalTitle("我的订单", v -> finish());
        tlTab = (TabLayout) findViewById(R.id.tl_tab);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        allOrderFragment = new AllOrderFragment();
        waitePayOrderFragment = new WaitePayOrderFragment();
        waiteGetGoodsFragment = new WaiteGetGoodsFragment();
        havedFinishFragment = new HavedFinishFragment();
        havedCancelFragment = new HavedCancelFragment();
        fragmentList.add(allOrderFragment);
        fragmentList.add(waitePayOrderFragment);
        fragmentList.add(waiteGetGoodsFragment);
        fragmentList.add(havedFinishFragment);
        fragmentList.add(havedCancelFragment);
        initTabData();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                return  fragmentList.get(position);

            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //将ViewPager关联到TabLayout上
        tlTab.setupWithViewPager(viewPager);



    }

    /**
     * 初始化tab的数据
     */
    private void initTabData() {
        mTitle[0] = "全部";
        mTitle[1] = "待付款";
        mTitle[2] = "待收货";
        mTitle[3] = "已完成";
        mTitle[4] = "已取消";
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onViewPageItemClick(View view, int position) {

    }
}
