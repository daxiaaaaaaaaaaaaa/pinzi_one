package com.jilian.pinzi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 主页面tap适配器
 *
 * @author weishixiong
 * @Time 2018-03-19
 */
public class CarTapPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public CarTapPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mFragmentList.size();//有几个页面

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}