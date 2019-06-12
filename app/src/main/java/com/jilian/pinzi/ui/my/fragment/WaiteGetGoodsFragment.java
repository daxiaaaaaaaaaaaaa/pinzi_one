package com.jilian.pinzi.ui.my.fragment;

import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 待收货
 */
public class WaiteGetGoodsFragment extends BaseOrderFragment {


    @Override
    protected Integer getPayStatus() {
        return 2;
    }



}
