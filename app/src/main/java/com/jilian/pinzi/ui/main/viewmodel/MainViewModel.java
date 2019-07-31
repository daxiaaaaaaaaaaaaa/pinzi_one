package com.jilian.pinzi.ui.main.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ActivityProductDto;
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
import com.jilian.pinzi.common.dto.InformationtDetailDto;
import com.jilian.pinzi.common.dto.InformationtDto;
import com.jilian.pinzi.common.dto.InformationtTypeDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.SeckillDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.vo.AccesstokenVo;
import com.jilian.pinzi.common.vo.ActivityVo;
import com.jilian.pinzi.common.vo.AddOrderVo;
import com.jilian.pinzi.common.vo.BuyCouponVo;
import com.jilian.pinzi.common.vo.BuyerCenterGoodsVo;
import com.jilian.pinzi.common.vo.CancelCollectVo;
import com.jilian.pinzi.common.vo.CollectGoodsOrStoreVo;
import com.jilian.pinzi.common.vo.CommentInformationVo;
import com.jilian.pinzi.common.vo.CommitQuestionItemVo;
import com.jilian.pinzi.common.vo.CommitQuestionVo;
import com.jilian.pinzi.common.vo.DiscountConpouVo;
import com.jilian.pinzi.common.vo.DiscountMoneyVo;
import com.jilian.pinzi.common.vo.GoodsByScoreVo;
import com.jilian.pinzi.common.vo.GoodsDetailVo;
import com.jilian.pinzi.common.vo.GoodsEvaluateVo;
import com.jilian.pinzi.common.vo.GoodsIsSecondCheckVo;
import com.jilian.pinzi.common.vo.HotWordListVo;
import com.jilian.pinzi.common.vo.InformationVo;
import com.jilian.pinzi.common.vo.InvoiceVo;
import com.jilian.pinzi.common.vo.JoinShopCartVo;
import com.jilian.pinzi.common.vo.MsgVo;
import com.jilian.pinzi.common.vo.ProductVo;
import com.jilian.pinzi.common.vo.PvOrUvVo;
import com.jilian.pinzi.common.vo.QuestionVo;
import com.jilian.pinzi.common.vo.RecommendVo;
import com.jilian.pinzi.common.vo.ReturnCommissionVo;
import com.jilian.pinzi.common.vo.ScoreBuyGoodsVo;
import com.jilian.pinzi.common.vo.SeckillPrefectureVo;
import com.jilian.pinzi.common.vo.ShipperVo;
import com.jilian.pinzi.common.vo.StoreCouponVo;
import com.jilian.pinzi.common.vo.StoreShowVo;
import com.jilian.pinzi.ui.main.repository.MainRepository;
import com.jilian.pinzi.ui.main.repository.NormalRepository;
import com.jilian.pinzi.ui.main.repository.impl.MainRepositoryImpl;
import com.jilian.pinzi.ui.main.repository.impl.NormalRepositoryImpl;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private MainRepository mainRepository;
    private NormalRepository normalRepository;
    private LiveData<BaseDto<List<MsgDto>>> msgliveData;//消息

    private LiveData<BaseDto<MsgDto>> msgDetailliveData;//消息详情

    private LiveData<BaseDto<Integer>> msgNewliveData;//新消息

    private LiveData<BaseDto<List<StartPageDto>>> startPageliveData;//启动页配置

    private LiveData<BaseDto<List<CouponCentreDto>>> couponliveData;//领券中心  优惠券 代金券

    private LiveData<BaseDto<CouponCentreDto>> couponDetailliveData;//优惠券详情

    private LiveData<BaseDto<CouponCentreDto>> myCardDetailliveData;//优惠券详情

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


    private LiveData<BaseDto<SeckillDto>> seckillPrefectureliveData;//秒杀专区

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

    private LiveData<BaseDto<List<InformationtTypeDto>>> informationtTypeData;//咨询分类

    private LiveData<BaseDto<List<InformationtDto>>> informationtData;//咨询列表

    private LiveData<BaseDto<InformationtDetailDto>> informationtDetailData;//咨询详情

    private LiveData<BaseDto> commentDetailData;//评论详情


    private LiveData<BaseDto<List<ActivityDto>>> activityListData;//活动列表


    private LiveData<BaseDto<ActivityDto>> activityDetailtData;//活动详情

    private LiveData<BaseDto> applyData;//报名

    private LiveData<BaseDto> cancelData;//取消报名


    private LiveData<BaseDto<List<ActivityProductDto>>> productData;//查看作品


    private LiveData<BaseDto> voteData;// 投票或取消投票

    private LiveData<BaseDto<List<QuestionDto>>> questionListData;// //问卷列表

    private LiveData<BaseDto<List<QuestionDetailDto>>> questionDetailData;// //问卷详情

    private LiveData<BaseDto> commiteData;// 提交问题

    private LiveData<BaseDto<List<ActivityDto>>> myActivityListData;//活动列表

    private LiveData<BaseDto<List<ActivityProductDto>>> myProductData;//查看作品

    private LiveData<BaseDto<List<StoreCouponDto>>> storeCouponData;//店铺优惠券


    private LiveData<BaseDto> addProductData;//查看作品

    private LiveData<BaseDto<String>> sevenTokenData;//七牛token

    private MutableLiveData<String> uploadVideoData;//上传视频


    private LiveData<BaseDto<String>> payCardliveData;//支付优惠券

    private LiveData<BaseDto<String>> rechargeCommsionData;//充值佣金


    private LiveData<BaseDto> updatePvData;//浏览记录统计(查看商品详情时调用)


    private LiveData<String> access_tokenData;//充值佣金


    private LiveData<BaseDto> deleteRechargeData;//删除充值记录

    private LiveData<BaseDto> deleteByIdsData;//删除提现记录

    public LiveData<BaseDto<CouponCentreDto>> getMyCardDetailliveData() {
        return myCardDetailliveData;
    }

    public LiveData<BaseDto> getDeleteRechargeData() {
        return deleteRechargeData;
    }

    public LiveData<BaseDto> getDeleteByIdsData() {
        return deleteByIdsData;
    }

    public LiveData<String> getAccess_tokenData() {
        return access_tokenData;
    }

    public LiveData<BaseDto<String>> getRechargeCommsionData() {
        return rechargeCommsionData;
    }

    public LiveData<BaseDto> getUpdatePvData() {
        return updatePvData;
    }

    public LiveData<BaseDto<String>> getPayCardliveData() {
        return payCardliveData;
    }

    public LiveData<BaseDto<List<StoreCouponDto>>> getStoreCouponData() {
        return storeCouponData;
    }

    public LiveData<String> getUploadVideoData() {
        return uploadVideoData;
    }

    public LiveData<BaseDto<String>> getSevenTokenData() {
        return sevenTokenData;
    }

    public LiveData<BaseDto> getAddProductData() {
        return addProductData;
    }

    public LiveData<BaseDto<List<ActivityDto>>> getMyActivityListData() {
        return myActivityListData;
    }

    public LiveData<BaseDto<List<ActivityProductDto>>> getMyProductData() {
        return myProductData;
    }

    public LiveData<BaseDto> getCommiteData() {
        return commiteData;
    }

    public LiveData<BaseDto<List<QuestionDetailDto>>> getQuestionDetailData() {
        return questionDetailData;
    }

    public LiveData<BaseDto<List<QuestionDto>>> getQuestionListData() {
        return questionListData;
    }

    public LiveData<BaseDto<List<ActivityDto>>> getActivityListData() {
        return activityListData;
    }

    public LiveData<BaseDto<ActivityDto>> getActivityDetailtData() {
        return activityDetailtData;
    }

    public LiveData<BaseDto> getApplyData() {
        return applyData;
    }

    public LiveData<BaseDto> getCancelData() {
        return cancelData;
    }

    public LiveData<BaseDto<List<ActivityProductDto>>> getProductData() {
        return productData;
    }

    public LiveData<BaseDto> getVoteData() {
        return voteData;
    }

    public LiveData<BaseDto> getCommentDetailData() {
        return commentDetailData;
    }

    public LiveData<BaseDto<List<InformationtTypeDto>>> getInformationtTypeData() {
        return informationtTypeData;
    }

    public LiveData<BaseDto<List<InformationtDto>>> getInformationtData() {
        return informationtData;
    }

    public LiveData<BaseDto<InformationtDetailDto>> getInformationtDetailData() {
        return informationtDetailData;
    }

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

    public LiveData<BaseDto<SeckillDto>> getSeckillPrefectureliveData() {
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
    public void getCouponDetail(String id) {
        mainRepository = new MainRepositoryImpl();
        couponDetailliveData = mainRepository.getCouponDetail(id);
    }

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    public void CouponDetails(String id) {
        mainRepository = new MainRepositoryImpl();
        myCardDetailliveData = mainRepository.CouponDetails(id);
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
    public void getBuyerCenterGoods(int pageNo, int pageSize, Integer identity, String goodsType, String entrance) {
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
     * @param startNum 当前页数
     * @param pageSize 每页条数
     * @param content  搜索内容
     * @param province 省
     * @param city     市
     * @param area     区
     * @param lat      纬度（用户定位地址）
     * @param lot      经度（用户定位地址）
     * @param orderby  1.距离从近到远 2.距离从远到近
     * @param scoreBy  1.好评优先
     */
    public void StoreShow(int startNum, int pageSize, String content, String province, String city, String area, Double lat, Double lot, String orderby, String scoreBy) {
        mainRepository = new MainRepositoryImpl();
        StoreShowVo vo = new StoreShowVo();
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        vo.setContent(content);
        vo.setProvince(province);
        vo.setCity(city);
        vo.setArea(area);
        vo.setLat(lat);
        vo.setLot(lot);
        vo.setOrderby(orderby);
        vo.setScoreBy(scoreBy);
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
    public void getGoodsEvaluate(Integer pageNo, Integer pageSize, Integer type, String goodsId, String uId) {
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
    public void getGoodsIsSecondCheck(String classes, Integer identity, String goods) {
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
     *
     * @param id
     */
    public void ClickByPageId(String id) {
        mainRepository = new MainRepositoryImpl();
        clickData = mainRepository.ClickByPageId(id);
    }


    /**
     * 获取咨询分类
     */
    public void getInformationTypeList() {
        mainRepository = new MainRepositoryImpl();
        informationtTypeData = mainRepository.getInformationTypeList(new InformationVo());
    }

    /**
     * 获取咨询列表
     *
     * @param typeId
     * @param pageNo
     * @param pageSize
     */
    public void getInformationList(String typeId, int pageNo, int pageSize) {
        mainRepository = new MainRepositoryImpl();
        InformationVo vo = new InformationVo();
        vo.setTypeId(typeId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        informationtData = mainRepository.getInformationList(vo);
    }

    /**
     * 获取咨询详情
     *
     * @param id
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getInformationDetail(String id, String uId, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        InformationVo vo = new InformationVo();
        vo.setId(id);
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        informationtDetailData = mainRepository.getInformationDetail(vo);
    }

    /**
     * 评论咨询
     *
     * @param id      资讯Id
     * @param uId
     * @param content
     */
    public void commentInformation(String id, String uId, String content) {
        mainRepository = new MainRepositoryImpl();
        CommentInformationVo vo = new CommentInformationVo();
        vo.setId(id);
        vo.setuId(uId);
        vo.setContent(content);
        commentDetailData = mainRepository.commentInformation(vo);
    }


    /**
     * 活动列表
     *
     * @param identity 1.个人 2.门店 3.二批商 4.总经销商
     * @param type     0.进行中 1.已结束
     * @param pageNo
     * @param pageSize
     */
    public void getActivityList(String identity, int type, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        ActivityVo vo = new ActivityVo();
        vo.setIdentity(identity);
        vo.setType(type);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        activityListData = mainRepository.getActivityList(vo);
    }

    /**
     * 活动详情
     *
     * @param id  活动Id
     * @param uId 用户Id
     */
    public void getActivityDetail(String id, String uId) {
        mainRepository = new MainRepositoryImpl();
        ActivityVo vo = new ActivityVo();
        vo.setId(id);
        vo.setuId(uId);
        activityDetailtData = mainRepository.getActivityDetail(vo);
    }

    /**
     * 报名
     *
     * @param aId 活动id
     * @param uId
     */
    public void applyActivity(String aId, String uId) {
        mainRepository = new MainRepositoryImpl();
        ActivityVo vo = new ActivityVo();
        vo.setaId(aId);
        vo.setuId(uId);
        applyData = mainRepository.applyActivity(vo);
    }

    /**
     * 取消报名
     *
     * @param applyId 报名Id
     */
    public void cancelApply(String applyId) {
        mainRepository = new MainRepositoryImpl();
        ActivityVo vo = new ActivityVo();
        vo.setApplyId(applyId);
        cancelData = mainRepository.cancelApply(vo);
    }

    /**
     * 查看作品
     *
     * @param uId
     * @param aId 活动Id
     */
    public void getActivityProductList(String uId, String aId) {
        mainRepository = new MainRepositoryImpl();
        ProductVo vo = new ProductVo();
        vo.setuId(uId);
        vo.setaId(aId);
        productData = mainRepository.getActivityProductList(vo);
    }

    /**
     * 投票或取消投票
     *
     * @param uId
     * @param apId
     * @param type
     */
    public void voteActivityProduct(String uId, String apId, int type) {
        mainRepository = new MainRepositoryImpl();
        ProductVo vo = new ProductVo();
        vo.setuId(uId);
        vo.setApId(apId);
        vo.setType(type);
        voteData = mainRepository.voteActivityProduct(vo);
    }

    /**
     * 问卷列表
     *
     * @param uId
     * @param type
     * @param pageNo
     * @param pageSize
     */
    public void getQuestionList(String uId, int type, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        QuestionVo vo = new QuestionVo();
        vo.setuId(uId);
        vo.setType(type);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        questionListData = mainRepository.getQuestionList(vo);
    }

    /**
     * @param uId
     * @param qId 问卷Id
     */
    public void getQuestionDetail(String uId, String qId) {
        mainRepository = new MainRepositoryImpl();
        QuestionVo vo = new QuestionVo();
        vo.setuId(uId);
        vo.setqId(qId);
        questionDetailData = mainRepository.getQuestionDetail(vo);
    }

    /**
     * 提交问题
     *
     * @param uId
     * @param questionId
     * @param results
     */
    public void commitQuestion(String uId, String questionId, List<CommitQuestionItemVo> results) {
        mainRepository = new MainRepositoryImpl();
        CommitQuestionVo vo = new CommitQuestionVo();
        vo.setuId(uId);
        vo.setQuestionId(questionId);
        vo.setResults(results);
        commiteData = mainRepository.commitQuestion(vo);
    }

    /**
     * 我的活动列表
     *
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getMyActivityList(String uId, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        ActivityVo vo = new ActivityVo();
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        myActivityListData = mainRepository.getMyActivityList(vo);
    }

    /**
     * 我的作品
     *
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getMyProduct(String uId, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        ProductVo vo = new ProductVo();
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        vo.setuId(uId);
        myProductData = mainRepository.getMyProduct(vo);
    }

    /**
     * 上传作品
     *
     * @param uId
     * @param activityId
     * @param content
     * @param pathUrl
     * @param video
     */
    public void addProduct(String uId, String activityId, String content, String pathUrl, String video) {
        mainRepository = new MainRepositoryImpl();
        ProductVo vo = new ProductVo();
        vo.setuId(uId);
        vo.setActivityId(activityId);
        vo.setContent(content);
        vo.setPathUrl(pathUrl);
        vo.setVideo(video);
        addProductData = mainRepository.addProduct(vo);
    }

    /**
     * 获取七牛云token
     */
    public void uptoken() {
        mainRepository = new MainRepositoryImpl();
        sevenTokenData = mainRepository.uptoken();
    }

    ///指定zone的具体区域
    //FixedZone.zone0   华东机房
    //FixedZone.zone1   华北机房
    //FixedZone.zone2   华南机房
    //FixedZone.zoneNa0 北美机房
    //自动识别上传区域
    //AutoZone.autoZone
    //Configuration config = new Configuration.Builder()
    //.zone(Zone.autoZone)
    //.zone(FixedZone.zone0)
    //.build();
    //UploadManager uploadManager = new UploadManager(config);
    //  data = <File对象、或 文件路径、或 字节数组>
    //  String key = <指定七牛服务上的文件名，或 null >;
    // String token = <从服务端SDK获取 >;
    // 重用uploadManager。一般地，只需要创建一个uploadManager对象
    public void uploadVideoToSeven(File data, String key, String token) {
        uploadVideoData = new MutableLiveData<String>();

        UploadManager uploadManager = new UploadManager();
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            uploadVideoData.setValue(key);
                            Log.e(TAG, "complete: qiniu Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }


    /**
     * 店铺优惠
     *
     * @param storeId
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getStoreCoupon(String storeId, String uId, Integer pageNo, Integer pageSize) {
        mainRepository = new MainRepositoryImpl();
        StoreCouponVo vo = new StoreCouponVo();
        vo.setStoreId(storeId);
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        storeCouponData = mainRepository.getStoreCoupon(vo);
    }

    /**
     * 购买优惠券支付
     *
     * @param
     */
    public void buyCoupon(BuyCouponVo vo) {
        mainRepository = new MainRepositoryImpl();
        payCardliveData = mainRepository.buyCoupon(vo);
    }

    /**
     * 浏览记录统计(查看商品详情时调用)
     *
     * @param mac
     * @param goodsId
     */
    public void updatePvOrUv(String mac, String goodsId) {
        PvOrUvVo vo = new PvOrUvVo();
        vo.setMac(mac);
        vo.setGoodsId(goodsId);
        mainRepository = new MainRepositoryImpl();
        updatePvData = mainRepository.updatePvOrUv(vo);
    }

    /**
     * 充值金额
     *
     * @param uId
     * @param money
     * @param type
     * @param platform
     */
    public void rechargeCommsion(String uId, String money, String type, String platform) {
        mainRepository = new MainRepositoryImpl();
        BuyCouponVo vo = new BuyCouponVo();
        vo.setuId(uId);
        vo.setMoney(money);
        vo.setType(type);
        vo.setPlatform(platform);
        rechargeCommsionData = mainRepository.rechargeCommsion(vo);
    }

    /**
     * @param appid      是	应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param secret     是	应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * @param code       是	填写第一步获取的code参数
     * @param grant_type 是	填authorization_code
     */
    public void access_token(String appid, String secret, String code, String grant_type) {
        normalRepository = new NormalRepositoryImpl();
        AccesstokenVo vo = new AccesstokenVo();
        vo.setAppid(appid);
        vo.setSecret(secret);
        vo.setCode(code);
        vo.setGrant_type(grant_type);
        access_tokenData = normalRepository.access_token(vo);
    }


    /**
     * 删除充值记录
     *
     * @param ids
     */
    public void deleteRecharge(String ids) {
        mainRepository = new MainRepositoryImpl();

        deleteRechargeData = mainRepository.deleteRecharge(ids);
    }

    /**
     * 删除提现记录
     *
     * @param ids
     */
    public void deleteByIds(String ids) {
        mainRepository = new MainRepositoryImpl();
        deleteByIdsData = mainRepository.deleteByIds(ids);
    }


}
