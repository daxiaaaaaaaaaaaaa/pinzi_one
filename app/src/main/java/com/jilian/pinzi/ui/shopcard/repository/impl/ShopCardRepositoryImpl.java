package com.jilian.pinzi.ui.shopcard.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.common.vo.AddOrReduceGoodsVo;
import com.jilian.pinzi.common.vo.DeleteGoodsVo;
import com.jilian.pinzi.common.vo.ShopCartVo;
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.shopcard.repository.ShopCardRepository;

import java.util.List;

public class ShopCardRepositoryImpl extends CommonRepository implements ShopCardRepository {
    /**
     * 购物车
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ShopCartLisDto>>> getShopCartList(ShopCartVo vo) {
        return request(Api.getShopCartList(vo)).send().get();
    }
    /**
     * 增加或减少购物车数量
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> addOrReduceGoods(AddOrReduceGoodsVo vo) {
        return request(Api.addOrReduceGoods(vo)).send().get();
    }
    /**
     * 单个删除或全部删除
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> deleteGoods(DeleteGoodsVo vo) {
        return request(Api.deleteGoods(vo)).send().get();
    }
}
