package com.jilian.pinzi.ui.my.fragment;

import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class WaitePayOrderFragment extends BaseOrderFragment  {


    @Override
    protected Integer getPayStatus() {
        return 1;
    }
}
