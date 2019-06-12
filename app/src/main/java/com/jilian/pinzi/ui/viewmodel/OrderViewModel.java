package com.jilian.pinzi.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.ui.repository.OrderRepository;

/**
 * 订单 ViewModel
 */
public class OrderViewModel extends ViewModel {

    private LiveData<BaseDto<OrderTrackDto>> orderTrackLiveData;
    private OrderRepository orderRepository;

    public LiveData<BaseDto<OrderTrackDto>> getOrderTrackLiveData() {
        return orderTrackLiveData;
    }

    /**
     * 订单跟踪
     * @param orderId
     */
    public void getOrderOfLogistics(String orderId) {
        orderRepository = new OrderRepository();
        orderTrackLiveData = orderRepository.getOrderOfLogistics(orderId);
    }

}
