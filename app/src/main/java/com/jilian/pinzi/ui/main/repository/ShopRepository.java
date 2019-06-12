package com.jilian.pinzi.ui.main.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.http.Api;

import java.util.List;

/**
 * 商铺 Repository
 */
public class ShopRepository extends CommonRepository {

    public LiveData<BaseDto<ShopDetailDto>> getStoreDetail(String storeId, String uId) {
        return request(Api.getStoreDetail(storeId, uId)).send().get();
    }

    public LiveData<BaseDto<List<ShopGoodsDto>>> getStoreGoods(int identity, String storeId, String goodsName,String entrance) {
        return request(Api.getStoreGoods(identity, storeId, goodsName,entrance)).send().get();
    }

}
