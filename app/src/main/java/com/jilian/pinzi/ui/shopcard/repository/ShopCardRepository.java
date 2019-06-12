package com.jilian.pinzi.ui.shopcard.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.common.vo.AddOrReduceGoodsVo;
import com.jilian.pinzi.common.vo.DeleteGoodsVo;
import com.jilian.pinzi.common.vo.ShopCartVo;

import java.util.List;

public interface ShopCardRepository {
    /**
     * 购物车
     * @return
     */
    LiveData <BaseDto<List<ShopCartLisDto>>> getShopCartList(ShopCartVo vo);
    /**
     * 增加或减少购物车数量
     * @return
     */
    LiveData<BaseDto<String>> addOrReduceGoods(AddOrReduceGoodsVo vo);

    /**
     * 单个删除或全部删除
     * @return
     */
    LiveData<BaseDto<String>> deleteGoods(DeleteGoodsVo vo);

}
