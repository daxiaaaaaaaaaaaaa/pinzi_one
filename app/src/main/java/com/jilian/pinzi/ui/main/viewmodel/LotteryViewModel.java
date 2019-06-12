package com.jilian.pinzi.ui.main.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.EmptyDto;
import com.jilian.pinzi.common.dto.LotteryInfoDto;
import com.jilian.pinzi.common.dto.LotteryRecordDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.vo.LotteryRecordVo;
import com.jilian.pinzi.common.vo.MyRecordVo;
import com.jilian.pinzi.ui.main.repository.LotteryRepository;

import java.util.List;

/**
 * @author ningpan
 * @since 2018/12/6 21:05 <br>
 * description: 抽奖 ViewModel
 */
public class LotteryViewModel extends ViewModel {

    private LotteryRepository lotteryRepository;
    // 奖品数据
    private LiveData<BaseDto<List<LotteryInfoDto>>> lotteryLiveData;
    // 判断抽奖积分
    private LiveData<BaseDto<EmptyDto>> scoreLiveData;
    // 添加抽奖结果
    private LiveData<BaseDto<EmptyDto>> resultLiveData;
    // 抽奖结果列表
    private LiveData<BaseDto<List<LotteryRecordDto>>> listLiveData;

    public LiveData<BaseDto<EmptyDto>> getResultLiveData() {
        return resultLiveData;
    }

    public LiveData<BaseDto<List<LotteryRecordDto>>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<BaseDto<EmptyDto>> getScoreLiveData() {
        return scoreLiveData;
    }

    public LiveData<BaseDto<List<LotteryInfoDto>>> getLotteryLiveData() {
        return lotteryLiveData;
    }

    //接口名称: 我的积分、钱包、佣金
    private LiveData<BaseDto<List<MyRecordDto>>> myRecordLiveData;

    public LiveData<BaseDto<List<MyRecordDto>>> getMyRecordLiveData() {
        return myRecordLiveData;
    }

    /**
     * 获取奖品数据
     */
    public void getLotteryInfo() {
        lotteryRepository = new LotteryRepository();
        lotteryLiveData = lotteryRepository.getLotteryInfo();
    }

    /**
     * 判断抽奖积分
     */
    public void getLotteryScore(String uId) {
        lotteryRepository = new LotteryRepository();
        scoreLiveData = lotteryRepository.getLotteryScore(uId);
    }

    /**
     * 添加抽奖结果
     *
     * @param uId 用户id
     * @param id  奖品id
     */
    public void addLotteryResult(String uId, String id) {
        lotteryRepository = new LotteryRepository();
        resultLiveData = lotteryRepository.addLotteryResult(uId, id);
    }

    /**
     * 抽奖结果列表
     */
    public void getLotteryList(int startNum,int pageSize,String uId) {
        LotteryRecordVo vo = new LotteryRecordVo();
        vo.setuId(uId);
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        lotteryRepository = new LotteryRepository();
        listLiveData = lotteryRepository.getLotteryList(vo);
    }

    /**
     * 我的积分、钱包、佣金
     * 分页
     */
    public void getMyRecord(String uId, Integer type, int pageNo, int pageSize) {
        lotteryRepository = new LotteryRepository();
        MyRecordVo vo = new MyRecordVo();
        vo.setuId(uId);
        vo.setType(type);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        myRecordLiveData = lotteryRepository.getMyRecord(vo);
    }

    /**
     * 我的积分、钱包、佣金
     * 全部
     */
    public void getAllMyRecord(String uId, Integer type) {
        lotteryRepository = new LotteryRepository();
        MyRecordVo vo = new MyRecordVo();
        vo.setuId(uId);
        vo.setType(type);
        myRecordLiveData = lotteryRepository.getMyRecord(vo);
    }
}
