package com.jilian.pinzi.ui.main.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.ui.main.repository.ShopRepository;

import java.util.List;

/**
 * 商铺 ViewModel
 */
public class ShopViewModel extends ViewModel {

    private LiveData<BaseDto<ShopDetailDto>> shopDetailLiveData;
    private LiveData<BaseDto<List<ShopGoodsDto>>> shopGoodsLiveData;
    private ShopRepository shopRepository;

    public LiveData<BaseDto<ShopDetailDto>> getShopDetailLiveData() {
        return shopDetailLiveData;
    }

    public LiveData<BaseDto<List<ShopGoodsDto>>> getShopGoodsLiveData() {
        return shopGoodsLiveData;
    }

    /**
     * 获取商铺详情
     * @param storeId
     * @param uId
     */
    public void getStoreDetail(String storeId, String uId) {
        shopRepository = new ShopRepository();
        shopDetailLiveData = shopRepository.getStoreDetail(storeId, uId);
    }

    /**
     * 获取商铺商品
     * @param identity 登录用户身份（1.普通 2.终端 3.渠道 4.总经销商）
     * @param storeId
     * @param goodsName
     * entrance 1，采购中心接口 2.首页店铺商品分类
     */
    public void getStoreGoods(int identity, String storeId, String goodsName,String entrance) {
        shopRepository = new ShopRepository();
        shopGoodsLiveData = shopRepository.getStoreGoods(identity, storeId, goodsName,entrance);
    }

}
