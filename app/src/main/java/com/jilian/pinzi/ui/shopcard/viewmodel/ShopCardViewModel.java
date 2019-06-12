package com.jilian.pinzi.ui.shopcard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.common.vo.AddOrReduceGoodsVo;
import com.jilian.pinzi.common.vo.DeleteGoodsVo;
import com.jilian.pinzi.common.vo.ShopCartVo;
import com.jilian.pinzi.ui.shopcard.repository.ShopCardRepository;
import com.jilian.pinzi.ui.shopcard.repository.impl.ShopCardRepositoryImpl;

import java.util.List;

public class ShopCardViewModel extends ViewModel {
    private LiveData<BaseDto<List<ShopCartLisDto>>> shopCartLisLiveData;//购物车列表
    private LiveData<BaseDto<String>> addOrReduceLiveData;//增加或减少购物车数量
    private LiveData<BaseDto<String>> deleteGoodLiveData;//单个删除或全部删除
    private ShopCardRepository shopCardRepository;

    public LiveData<BaseDto<List<ShopCartLisDto>>> getShopCartLisLiveData() {
        return shopCartLisLiveData;
    }

    public LiveData<BaseDto<String>> getAddOrReduceLiveData() {
        return addOrReduceLiveData;
    }

    public LiveData<BaseDto<String>> getDeleteGoodLiveData() {
        return deleteGoodLiveData;
    }

    /**
     * 获取购物车列表
     *
     * @param uId      用户ID
     * @param pageNo
     * @param pageSize
     */
    public void getShopCartLis(String uId, Integer pageNo, Integer pageSize) {
        ShopCartVo vo = new ShopCartVo();
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        shopCardRepository = new ShopCardRepositoryImpl();
        shopCartLisLiveData = shopCardRepository.getShopCartList(vo);
    }

    /**
     * 增加或减少购物车数量
     *
     * @param shopId
     * @param type
     */
    public void addOrReduce(String shopId, int type) {
        AddOrReduceGoodsVo vo = new AddOrReduceGoodsVo();
        vo.setShopId(shopId);
        vo.setType(type);
        shopCardRepository = new ShopCardRepositoryImpl();
        addOrReduceLiveData = shopCardRepository.addOrReduceGoods(vo);
    }

    /**
     * 删除单个 多个 购物车
     *
     * @param type
     * @param uId
     * @param shopIds
     */
    public void deleteGoods(int type, String uId, String shopIds) {
        DeleteGoodsVo vo = new DeleteGoodsVo();
        vo.setType(type);
        vo.setuId(uId);
        vo.setShopIds(shopIds);
        shopCardRepository = new ShopCardRepositoryImpl();
        deleteGoodLiveData = shopCardRepository.deleteGoods(vo);

    }
}
