package com.jilian.pinzi.ui.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.http.Api;

public class OrderRepository extends CommonRepository {

    /**
     * 订单跟踪
     * @param orderId
     * @return
     */
    public LiveData<BaseDto<OrderTrackDto>> getOrderOfLogistics(String orderId) {
        return request(Api.getOrderOfLogistics(orderId)).send().get();
    }

}
