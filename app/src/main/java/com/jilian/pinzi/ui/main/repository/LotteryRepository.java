package com.jilian.pinzi.ui.main.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.EmptyDto;
import com.jilian.pinzi.common.dto.LotteryInfoDto;
import com.jilian.pinzi.common.dto.LotteryRecordDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.vo.LotteryRecordVo;
import com.jilian.pinzi.common.vo.MyRecordVo;
import com.jilian.pinzi.http.Api;

import java.util.List;

/**
 * @author ningpan
 * @since 2018/12/6 18:22 <br>
 * description: 抽奖 Repository
 */
public class LotteryRepository extends CommonRepository {

    /**
     * 奖品数据
     * @return
     */
    public LiveData<BaseDto<List<LotteryInfoDto>>> getLotteryInfo() {
        return request(Api.getLotteryInfo()).send().get();
    }

    /**
     * 判断抽奖积分
     * @param uId
     * @return
     */
    public LiveData<BaseDto<EmptyDto>> getLotteryScore(String uId) {
        return request(Api.getLotteryScore(uId)).send().get();
    }

    /**
     * 添加抽奖结果
     * @param uId 用户id
     * @param id 奖品id
     * @return
     */
    public LiveData<BaseDto<EmptyDto>> addLotteryResult(String uId, String id) {
        return request(Api.addLotteryResult(uId, id)).send().get();
    }

    /**
     * 抽奖结果列表
     * @return
     */
    public LiveData<BaseDto<List<LotteryRecordDto>>> getLotteryList(LotteryRecordVo vo) {
        return request(Api.getLotteryList(vo)).send().get();
    }
    /**
     * 接口名称: 我的积分、钱包、佣金
     * @return
     */
    public LiveData<BaseDto<List<MyRecordDto>>> getMyRecord(MyRecordVo vo) {
        return request(Api.getMyRecord(vo)).send().get();
    }
}
