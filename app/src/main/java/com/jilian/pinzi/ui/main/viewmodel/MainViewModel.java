package com.jilian.pinzi.ui.main.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddOrderDto;
import com.jilian.pinzi.common.dto.BuyerCenterGoodsDto;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
import com.jilian.pinzi.common.dto.DiscountMoneyDto;
import com.jilian.pinzi.common.dto.GoodByScoreDto;
import com.jilian.pinzi.common.dto.GoodsDetailDto;
import com.jilian.pinzi.common.dto.GoodsEvaluateDto;
import com.jilian.pinzi.common.dto.GoodsIsSecondCheckDto;
import com.jilian.pinzi.common.dto.GoodsTypeDto;
import com.jilian.pinzi.common.dto.HotWordListDto;
import com.jilian.pinzi.common.dto.HotWordSelectBusinessDto;
import com.jilian.pinzi.common.dto.HotWordSelectDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.vo.AddOrderVo;
import com.jilian.pinzi.common.vo.BuyerCenterGoodsVo;
import com.jilian.pinzi.common.vo.CancelCollectVo;
import com.jilian.pinzi.common.vo.CollectGoodsOrStoreVo;
import com.jilian.pinzi.common.vo.DiscountConpouVo;
import com.jilian.pinzi.common.vo.DiscountMoneyVo;
import com.jilian.pinzi.common.vo.GoodsByScoreVo;
import com.jilian.pinzi.common.vo.GoodsDetailVo;
import com.jilian.pinzi.common.vo.GoodsEvaluateVo;
import com.jilian.pinzi.common.vo.GoodsIsSecondCheckVo;
import com.jilian.pinzi.common.vo.HotWordListVo;
import com.jilian.pinzi.common.vo.InvoiceVo;
import com.jilian.pinzi.common.vo.JoinShopCartVo;
import com.jilian.pinzi.common.vo.MsgVo;
import com.jilian.pinzi.common.vo.RecommendVo;
import com.jilian.pinzi.common.vo.ReturnCommissionVo;
import com.jilian.pinzi.common.vo.ScoreBuyGoodsVo;
import com.jilian.pinzi.common.vo.SeckillPrefectureVo;
import com.jilian.pinzi.common.vo.ShipperVo;
import com.jilian.pinzi.common.vo.StoreShowVo;
import com.jilian.pinzi.ui.main.repository.MainRepository;
import com.jilian.pinzi.ui.main.repository.impl.MainRepositoryImpl;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MainRepository mainRepository;

    private LiveData<BaseDto<List<MsgDto>>> msgliveData;//消息

    private LiveData<BaseDto<MsgDto>> msgDetailliveData;//消息详情

    private LiveData<BaseDto<Integer>> msgNewliveData;//新消息

    private LiveData<BaseDto<List<StartPageDto>>> startPageliveData;//启动页配置

    private LiveData<BaseDto<List<CouponCentreDto>>> couponliveData;//领券中心  优惠券 代金券

    private LiveData<BaseDto<CouponCentreDto>> couponDetailliveData;//优惠券详情

    private LiveData<BaseDto<String>> stringliveData;//领券优惠券

    private LiveData<BaseDto<String>> clockInliveData;//用户签到

    private LiveData<BaseDto<List<HotWordListDto>>> hotWordLisInliveData;//热词列表

    private LiveData<BaseDto<List<HotWordSelectDto>>> searchResultliveData;//搜索结果

    private LiveData<BaseDto<List<HotWordSelectBusinessDto>>> searcBussliveData;//店铺搜索结果

    private LiveData<BaseDto<List<GoodsTypeDto>>> goodsTypeliveData;//全部分类

    private LiveData<BaseDto<List<BuyerCenterGoodsDto>>> buyerCenterGoodsliveData;//采购中心

    private LiveData<BaseDto<List<MainRecommendDto>>> recommendPersonliveData;//人气推荐 新
    private LiveData<BaseDto<List<MainRecommendDto>>> recommendNewliveData;//品推荐

    private LiveData<BaseDto<List<MainRecommendDto>>> ReturnCommissionliveData;//返佣金专区


    private LiveData<BaseDto<List<SeckillPrefectureDto>>> seckillPrefectureliveData;//秒杀专区

    private LiveData<BaseDto<List<StoreShowDto>>> storeShowliveData;//店铺展示

    private LiveData<BaseDto<GoodsDetailDto>> goodsDetailliveData;//商品详情

    private LiveData<BaseDto<GoodsEvaluateDto>> goodsEvaluateliveData;//商品评价

    private LiveData<BaseDto<String>> joinShopCartliveData;//加入购物车

    private LiveData<BaseDto<String>> collectGoodsOrStoreliveData;//加入购物车

    private LiveData<BaseDto<String>> cancelCollectliveData;//加入购物车

    private LiveData<BaseDto<DiscountConpouDto>> discountConpouDtoliveData;//选择优惠券

    private LiveData<BaseDto<List<ShipperDto>>> shipperliveData;//选择发货人

    private LiveData<BaseDto<String>> invoiceliveData;//发票g

    private LiveData<BaseDto<GoodsIsSecondCheckDto>> goodsIsSecondCheckliveData;//判断是否有秒杀商品

    private LiveData<BaseDto<AddOrderDto>> addOrderliveData;//下单
    private LiveData<BaseDto<GoodByScoreDto>> buyGoodsByScore;//积分购买

    private LiveData<BaseDto<String>> freight;//运费

    private LiveData<BaseDto<DiscountMoneyDto>> discountMoney;//抵扣金额

    private LiveData<BaseDto<String>> clickData;//点击次数新增

    public LiveData<BaseDto<String>> getClickData() {
        return clickData;
    }

    public LiveData<BaseDto<DiscountMoneyDto>> getDiscountMoney() {
        return discountMoney;
    }

    public LiveData<BaseDto<String>> getFreight() {
        return freight;
    }

    public LiveData<BaseDto<GoodByScoreDto>> getBuyGoodsByScore() {
        return buyGoodsByScore;
    }

    public LiveData<BaseDto<AddOrderDto>> getAddOrderliveData() {
        return addOrderliveData;
    }

    public LiveData<BaseDto<GoodsIsSecondCheckDto>> getGoodsIsSecondCheckliveData() {
        return goodsIsSecondCheckliveData;
    }

    public LiveData<BaseDto<String>> getInvoiceliveData() {
        return invoiceliveData;
    }

    public LiveData<BaseDto<List<ShipperDto>>> getShipperliveData() {
        return shipperliveData;
    }

    public LiveData<BaseDto<DiscountConpouDto>> getDiscountConpouDtoliveData() {
        return discountConpouDtoliveData;
    }

    public LiveData<BaseDto<String>> getJoinShopCartliveData() {
        return joinShopCartliveData;
    }

    public LiveData<BaseDto<String>> getCollectGoodsOrStoreliveData() {
        return collectGoodsOrStoreliveData;
    }

    public LiveData<BaseDto<String>> getCancelCollectliveData() {
        return cancelCollectliveData;
    }

    public LiveData<BaseDto<GoodsEvaluateDto>> getGoodsEvaluateliveData() {
        return goodsEvaluateliveData;
    }

    public LiveData<BaseDto<GoodsDetailDto>> getGoodsDetailliveData() {
        return goodsDetailliveData;
    }

    public LiveData<BaseDto<List<SeckillPrefectureDto>>> getSeckillPrefectureliveData() {
        return seckillPrefectureliveData;
    }

    public LiveData<BaseDto<List<StoreShowDto>>> getStoreShowliveData() {
        return storeShowliveData;
    }

    public LiveData<BaseDto<List<MainRecommendDto>>> getRecommendPersonliveData() {
        return recommendPersonliveData;
    }

    public LiveData<BaseDto<List<MainRecommendDto>>> getRecommendNewliveData() {
        return recommendNewliveData;
    }

    public LiveData<BaseDto<List<MainRecommendDto>>> getReturnCommissionliveData() {
        return ReturnCommissionliveData;
    }

    public LiveData<BaseDto<List<BuyerCenterGoodsDto>>> getBuyerCenterGoodsliveData() {
        return buyerCenterGoodsliveData;
    }

    public LiveData<BaseDto<List<GoodsTypeDto>>> getGoodsTypeliveData() {
        return goodsTypeliveData;
    }

    public LiveData<BaseDto<List<HotWordSelectDto>>> getSearchResultliveData() {
        return searchResultliveData;
    }

    public LiveData<BaseDto<List<HotWordListDto>>> getHotWordLisInliveData() {
        return hotWordLisInliveData;
    }

    private LiveData<BaseDto<List<ScoreBuyGoodsDto>>> scoreBuyGoodsliveData;//积分商品或相似推荐商品

    public LiveData<BaseDto<List<ScoreBuyGoodsDto>>> getScoreBuyGoodsliveData() {
        return scoreBuyGoodsliveData;
    }

    public LiveData<BaseDto<String>> getClockInliveData() {
        return clockInliveData;
    }

    public LiveData<BaseDto<CouponCentreDto>> getCouponDetailliveData() {
        return couponDetailliveData;
    }

    public LiveData<BaseDto<String>> getStringliveData() {
        return stringliveData;
    }

    public LiveData<BaseDto<List<CouponCentreDto>>> getCouponliveData() {
        return couponliveData;
    }

    public LiveData<BaseDto<MsgDto>> getMsgDetailliveData() {
        return msgDetailliveData;
    }

    public LiveData<BaseDto<List<StartPageDto>>> getStartPageliveData() {
        return startPageliveData;
    }

    public LiveData<BaseDto<List<MsgDto>>> getMsgliveData() {
        return msgliveData;
    }

    public LiveData<BaseDto<Integer>> getMsgNewliveData() {
        return msgNewliveData;
    }

    public LiveData<BaseDto<List<HotWordSelectBusinessDto>>> getSearcBussliveData() {
        return searcBussliveData;
    }

    /**
     * @param uId
     * @param startNum
     * @param pageSize
     * @param type     1.新闻公告 2.消息
     */
    public void SystemInformation(String uId, int startNum, int pageSize, int type) {
        mainRepository = new MainRepositoryImpl();
        MsgVo vo = new MsgVo();
        vo.setuId(uId);
        vo.setPageSize(pageSize);
        vo.setStartNum(startNum);
        vo.setType(type);
        msgliveData = mainRepository.SystemInformation(vo);

    }

    /**
     * 获取新消息 首页消息已读
     *
     * @param type
     * @param uId
     */
    public void MessageRead(Integer type, String uId) {
        mainRepository = new MainRepositoryImpl();
        msgNewliveData = mainRepository.MessageRead(type, uId);

    }

    /**
     * 启动页配置
     *
     * @param type 类型（1.启动页 2.引导页 3.首页广告轮播 4,店铺广告轮播，5精品广告轮播 6，人气广告轮播 7，积分商场轮播图）
     * @return
     */
    public void StartPage(Integer type) {
        mainRepository = new MainRepositoryImpl();
        startPageliveData = mainRepository.StartPage(type);

    }

    /**
     * 消息详情/公告详情
     *
     * @param id
     * @param uId
     * @return
     */
    public void InformationDetails(String id, String uId) {
        mainRepository = new MainRepositoryImpl();
        msgDetailliveData = mainRepository.InformationDetails(id, uId);

    }

    /**
     * 领券中心 优惠券 代金券
     *
     * @param uId
     */
    public void CouponCentre(String uId) {
        mainRepository = new MainRepositoryImpl();
        couponliveData = mainRepository.CouponCentre(uId);

    }

    /**
     * 领取优惠券
     *
     * @param id
     * @param uId
     * @return
     */
    public void GetCoupon(String id, String uId) {
        mainRepository = new MainRepositoryImpl();
        stringliveData = mainRepository.GetCoupon(id, uId);

    }

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    public void CouponDetails(String id) {
        mainRepository = new MainRepositoryImpl();
        couponDetailliveData = mainRepository.CouponDetails(id);
    }

    /**
     * 用户签到
     *
     * @param
     * @return
     */
    public void ClockIn(String uId) {
        mainRepository = new MainRepositoryImpl();
        clockInliveData = mainRepository.ClockIn(uId);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param identity 用户身份登录用户身份（1.普通 2.终端 3.渠道 4.总经销商）
     * @param type     商城类型 1.积分商品 2.相似推荐商品
     */
    public void getScoreBuyGoods(Integer pageNo, Integer pageSize, int identity, int type) {
        mainRepository = new MainRepositoryImpl();
        ScoreBuyGoodsVo vo = new ScoreBuyGoodsVo();
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        vo.setIdentity(identity);
        vo.setType(type);
        scoreBuyGoodsliveData = mainRepository.getScoreBuyGoods(vo);
    }

    /**
     * 热词列表
     */
    public void HotWordList(String word) {
        mainRepository = new MainRepositoryImpl();
        HotWordListVo vo = new HotWordListVo();
        vo.setWord(word);
        hotWordLisInliveData = mainRepository.HotWordList(vo);
    }

    /**
     * 去搜索
     */
    public void HotWordSelect(String word) {
        mainRepository = new MainRepositoryImpl();
        HotWordListVo vo = new HotWordListVo();
        vo.setWord(word);
        searchResultliveData = mainRepository.HotWordSelect(vo);
    }
    /**
     * 去搜索店铺
     */
    public void HotWordSelectBusiness(String word) {
        mainRepository = new MainRepositoryImpl();
        HotWordListVo vo = new HotWordListVo();
        vo.setWord(word);
        searcBussliveData = mainRepository.HotWordSelectBusiness(vo);
    }
    /**
     * 全部分类
     *
     * @return
     */
    public void getGoodsType() {
        mainRepository = new MainRepositoryImpl();
        goodsTypeliveData = mainRepository.getGoodsType();
    }

    /**
     * 全部分类
     *
     * @return
     */
    public void getBuyerCenterGoods(int pageNo, int pageSize, Integer identity, String goodsType,String entrance) {
        mainRepository = new MainRepositoryImpl();
        BuyerCenterGoodsVo vo = new BuyerCenterGoodsVo();
        vo.setEntrance(entrance);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        vo.setIdentity(identity);
        vo.setGoodsType(goodsType);
        buyerCenterGoodsliveData = mainRepository.getBuyerCenterGoods(vo);
    }

    /**
     * 人气推荐
     *
     * @return
     */
    public void RecommendPerson(String recommend, int startNum, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        RecommendVo vo = new RecommendVo();
        vo.setRecommend(recommend);
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        recommendPersonliveData = mainRepository.Recommend(vo);

    }

    /**
     * 新品推荐
     *
     * @return
     */
    public void RecommendNews(int startNum, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        RecommendVo vo = new RecommendVo();
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        recommendNewliveData = mainRepository.Recommend(vo);

    }

    /**
     * 返佣金专区
     *
     * @return
     */
    public void ReturnCommission(int startNum, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        ReturnCommissionVo vo = new ReturnCommissionVo();
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        ReturnCommissionliveData = mainRepository.ReturnCommission(vo);

    }


    /**
     * 秒杀专区
     *
     * @return
     */
    public void SeckillPrefecture(int startNum, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        SeckillPrefectureVo vo = new SeckillPrefectureVo();
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        seckillPrefectureliveData = mainRepository.SeckillPrefecture(vo);

    }

    /**
     * 店铺展示
     *
     * @return
     */
    public void StoreShow(int startNum, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        StoreShowVo vo = new StoreShowVo();
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        storeShowliveData = mainRepository.StoreShow(vo);

    }


    /**
     * 获取商品详情
     *
     * @param type    1.普通商品 2.秒杀商品
     * @param goodsId
     * @param uId
     */
    public void getGoodsDetail(Integer type, String goodsId, String uId) {
        mainRepository = new MainRepositoryImpl();
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoodsId(goodsId);
        vo.setType(type);
        vo.setuId(uId);
        goodsDetailliveData = mainRepository.getGoodsDetail(vo);

    }

    /**
     * 获取商品评价
     *
     * @param type    0.全部 1.差 2.中 3.好 4.有图
     * @param goodsId
     * @param uId
     */
    public void getGoodsEvaluate(Integer pageNo,Integer  pageSize,Integer type, String goodsId, String uId) {
        mainRepository = new MainRepositoryImpl();
        GoodsEvaluateVo vo = new GoodsEvaluateVo();
        vo.setGoodsId(goodsId);
        vo.setType(type);
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        goodsEvaluateliveData = mainRepository.getGoodsEvaluate(vo);

    }

    /**
     * 加入购物车
     *
     * @param classes
     * @param uId      用户ID
     * @param goodsId  商品ID
     * @param quantity 数量
     */
    public void joinShopCart(Integer classes, String uId, String goodsId, String quantity) {
        JoinShopCartVo vo = new JoinShopCartVo();
        vo.setuId(uId);
        vo.setGoodsId(goodsId);
        vo.setQuantity(quantity);
        vo.setClasses(classes);
        mainRepository = new MainRepositoryImpl();
        joinShopCartliveData = mainRepository.joinShopCart(vo);
    }

    /**
     * 收藏商品 或者 店铺
     *
     * @param uId           用户ID
     * @param goodOrStoreId 商品ID 店铺ID
     * @param type          1.收藏商品 2.收藏店铺
     */
    public void collectGoodsOrStore(String uId, String goodOrStoreId, Integer type) {
        CollectGoodsOrStoreVo vo = new CollectGoodsOrStoreVo();
        vo.setuId(uId);
        vo.setGoodOrStoreId(goodOrStoreId);
        vo.setType(type);
        mainRepository = new MainRepositoryImpl();
        collectGoodsOrStoreliveData = mainRepository.collectGoodsOrStore(vo);
    }

    /**
     * 取消收藏
     *
     * @param cId 收藏ID
     */
    public void cancelCollect(String cId) {
        CancelCollectVo vo = new CancelCollectVo();
        vo.setcId(cId);
        mainRepository = new MainRepositoryImpl();
        cancelCollectliveData = mainRepository.cancelCollect(vo);
    }

    /**
     * 选择优惠券
     *
     * @param uId
     * @param goodsId
     * @param quantity
     * @param classes
     */
    public void getDiscountConpou(String uId, String goodsId, String quantity, String classes) {
        DiscountConpouVo vo = new DiscountConpouVo();
        vo.setuId(uId);
        vo.setGoodsId(goodsId);
        vo.setQuantity(quantity);
        vo.setClasses(classes);
        mainRepository = new MainRepositoryImpl();
        discountConpouDtoliveData = mainRepository.getDiscountConpou(vo);
    }

    /**
     * 选择发货人
     *
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getShipperList(String uId, Integer pageNo, Integer pageSize) {
        ShipperVo vo = new ShipperVo();
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        mainRepository = new MainRepositoryImpl();
        shipperliveData = mainRepository.getShipperList(vo);
    }


    /**
     * 开发票
     *
     * @param type         类型（1.增值税专用发票 2.增值税普通发票）
     * @param invoiceTitle 发票抬头
     * @param unitAddress  单位地址
     * @param phone        联系电话
     * @param bankAccount  银行账户
     * @param openBank     开户行
     * @param dutyMark     税号
     * @param takerName    收票人姓名
     * @param takerPhone   收票人电话
     * @param takerAddress 收票地址
     */
    public void addInvoice(Integer type, String invoiceTitle, String unitAddress, String phone, String bankAccount, String openBank, String dutyMark, String takerName, String takerPhone, String takerAddress) {

        InvoiceVo vo = new InvoiceVo();

        vo.setType(type);
        vo.setInvoiceTitle(invoiceTitle);
        vo.setTakerName(takerName);
        vo.setTakerAddress(takerAddress);
        vo.setTakerPhone(takerPhone);
        if (type == 1) {
            vo.setUnitAddress(unitAddress);
            vo.setPhone(phone);
            vo.setBankAccount(bankAccount);
            vo.setOpenBank(openBank);
            vo.setDutyMark(dutyMark);
        }
        mainRepository = new MainRepositoryImpl();
        invoiceliveData = mainRepository.addInvoice(vo);
    }

    /**
     * 判断是否有秒杀商品
     *
     * @param identity
     * @param goods
     */
    public void getGoodsIsSecondCheck(String classes,Integer identity, String goods) {
        GoodsIsSecondCheckVo vo = new GoodsIsSecondCheckVo();
        vo.setIdentity(identity);
        vo.setGoods(goods);
        vo.setClasses(classes);
        mainRepository = new MainRepositoryImpl();
        goodsIsSecondCheckliveData = mainRepository.getGoodsIsSecondCheck(vo);
    }

    /**
     * 下单接口
     *
     * @param identity
     * @param uId
     * @param addressId
     * @param goodsId
     * @param quantity
     * @param couponId
     * @param conpouGoodsId
     * @param isUseScore
     * @param isUseCommisson
     * @param shipper
     * @param freightPrice
     * @param invoiceId
     * @param conpouQuantity
     * @param type
     * @param orderType
     * @param name           下单接口
     */
    public void addOrder(String classes, String identity, String uId, String addressId,
                         String goodsId, String quantity, String couponId,
                         String conpouGoodsId, String isUseScore, String isUseCommisson,
                         String shipper, String freightPrice, String invoiceId,
                         String conpouQuantity, String type, String orderType, String name) {
        mainRepository = new MainRepositoryImpl();
        AddOrderVo vo = new AddOrderVo();
        vo.setClasses(classes);
        vo.setIdentity(identity);
        vo.setuId(uId);
        vo.setAddressId(TextUtils.isEmpty(addressId) ? "0" : addressId);
        vo.setGoodsId(goodsId);
        vo.setQuantity(quantity);
        vo.setCouponId(TextUtils.isEmpty(couponId) ? "0" : couponId);
        vo.setConpouGoodsId(TextUtils.isEmpty(conpouGoodsId) ? "0" : conpouGoodsId);
        vo.setIsUseScore(isUseScore);
        vo.setIsUseCommisson(isUseCommisson);
        //发货人ID
        vo.setShipper(TextUtils.isEmpty(shipper) ? "0" : shipper);
        vo.setFreightPrice(freightPrice);
        vo.setInvoiceId(TextUtils.isEmpty(invoiceId) ? "0" : invoiceId);
        vo.setConpouQuantity(conpouQuantity);
        vo.setType(type);
        vo.setOrderType(orderType);
        vo.setName(name);
        addOrderliveData = mainRepository.addOrder(vo);
    }

    /**
     * @param goodsIds
     * @param quantity
     * @param score
     * @param addressId
     * @param shipper
     * @param uId
     * @param identity
     * @param freightPrice
     * @param isUseCommisson 积分兑换提交商品
     */
    public void buyGoodsByScore(String goodsIds, String quantity, String score, String addressId, String shipper, String uId, String identity, String freightPrice, String isUseCommisson) {
        mainRepository = new MainRepositoryImpl();
        GoodsByScoreVo vo = new GoodsByScoreVo();
        vo.setIdentity(identity);
        vo.setuId(uId);
        vo.setAddressId(TextUtils.isEmpty(addressId) ? "0" : addressId);
        vo.setGoodsIds(goodsIds);
        vo.setQuantity(quantity);
        vo.setScore(score);

        vo.setIsUseCommisson(isUseCommisson);
        //发货人ID
        vo.setShipper(TextUtils.isEmpty(shipper) ? "0" : shipper);
        vo.setFreightPrice(freightPrice);
        buyGoodsByScore = mainRepository.buyGoodsByScore(vo);

    }

    /**
     * 获取运费
     *
     * @param goodsIds
     * @return
     */
    public void getFreight(String goodsIds) {
        mainRepository = new MainRepositoryImpl();
        freight = mainRepository.getFreight(goodsIds);
    }


    /**
     * 计算优惠券、积分、佣金抵扣金额
     *
     * @param quantity       商品的数量
     * @param uId            用户ID
     * @param identity       用户类型
     * @param couponId       优惠券ID
     * @param classes        商品类型
     * @param conpouGoodsId  适用的商品ID
     * @param isUseCommisson 是否使用佣金
     * @param isUseScore     是否使用 积分
     * @param conpouQuantity 适用的商品 数量
     * @param goodsId        商品ID
     */
    public void getDiscountMoney(String quantity,
                                 String uId, String identity,
                                 String couponId, String classes,
                                 String conpouGoodsId,
                                 String isUseCommisson,
                                 String isUseScore, String conpouQuantity,
                                 String goodsId) {
        mainRepository = new MainRepositoryImpl();
        DiscountMoneyVo vo = new DiscountMoneyVo();
        vo.setQuantity(quantity);
        vo.setuId(uId);
        vo.setIdentity(identity);
        vo.setCouponId(couponId);
        vo.setClasses(classes);
        vo.setConpouGoodsId(conpouGoodsId);
        vo.setIsUseCommisson(isUseCommisson);
        vo.setIsUseScore(isUseScore);
        vo.setConpouQuantity(conpouQuantity);
        vo.setGoodsId(goodsId);
        discountMoney = mainRepository.getDiscountMoney(vo);
    }

    /**
     * 点击次数新增
     * @param id
     */
    public void ClickByPageId(String id){
        mainRepository = new MainRepositoryImpl();
        clickData = mainRepository.ClickByPageId(id);
    }


}
