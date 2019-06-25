package com.jilian.pinzi.ui.main.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
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
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.vo.ActivityVo;
import com.jilian.pinzi.common.vo.AddOrderVo;
import com.jilian.pinzi.common.vo.BuyerCenterGoodsVo;
import com.jilian.pinzi.common.vo.CancelCollectVo;
import com.jilian.pinzi.common.vo.CollectGoodsOrStoreVo;
import com.jilian.pinzi.common.vo.CommentInformationVo;
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
import com.jilian.pinzi.common.vo.QuestionVo;
import com.jilian.pinzi.common.vo.RecommendVo;
import com.jilian.pinzi.common.vo.ReturnCommissionVo;
import com.jilian.pinzi.common.vo.ScoreBuyGoodsVo;
import com.jilian.pinzi.common.vo.SeckillPrefectureVo;
import com.jilian.pinzi.common.vo.ShipperVo;
import com.jilian.pinzi.common.vo.StoreShowVo;
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.main.repository.MainRepository;

import java.util.List;

public class MainRepositoryImpl extends CommonRepository implements MainRepository {
    /**
     * 系统消息/新闻公告
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<MsgDto>>> SystemInformation(MsgVo vo) {
        return request(Api.SystemInformation(vo)).send().get();
    }

    /**
     * 消息详情/公告详情
     *
     * @param id
     * @param uId
     * @return
     */
    @Override
    public LiveData<BaseDto<MsgDto>> InformationDetails(String id, String uId) {
        return request(Api.InformationDetails(id, uId)).send().get();
    }

    /**
     * 获取新消息 首页消息已读
     *
     * @param type
     * @param uId
     * @return
     */
    @Override
    public LiveData<BaseDto<Integer>> MessageRead(Integer type, String uId) {
        return request(Api.MessageRead(type, uId)).send().get();
    }

    /**
     * 启动页配置
     *
     * @param type 类型（1.启动页 2.引导页 3.首页广告轮播 4,店铺广告轮播，5精品广告轮播 6，人气广告轮播 7，积分商场轮播图）
     * @return
     */
    @Override
    public LiveData<BaseDto<List<StartPageDto>>> StartPage(Integer type) {
        return request(Api.StartPage(type)).send().get();
    }

    /**
     * 领劵中心
     *
     * @param uId
     * @return
     */
    @Override
    public LiveData<BaseDto<List<CouponCentreDto>>> CouponCentre(String uId) {
        return request(Api.CouponCentre(uId)).send().get();
    }

    /**
     * 领取优惠券
     *
     * @param id
     * @param uId
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> GetCoupon(String id, String uId) {
        return request(Api.GetCoupon(id, uId)).send().get();
    }

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    @Override
    public LiveData<BaseDto<CouponCentreDto>> CouponDetails(String id) {
        return request(Api.CouponDetails(id)).send().get();
    }

    /**
     * 用户签到
     *
     * @param
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> ClockIn(String uId) {
        return request(Api.ClockIn(uId)).send().get();
    }

    /**
     * 积分商品或相似推荐商品
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ScoreBuyGoodsDto>>> getScoreBuyGoods(ScoreBuyGoodsVo vo) {
        return request(Api.getScoreBuyGoods(vo)).send().get();
    }

    /**
     * 热词列表
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<HotWordListDto>>> HotWordList(HotWordListVo vo) {
        return request(Api.HotWordList(vo)).send().get();
    }

    /**
     * 首页搜索
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<HotWordSelectDto>>> HotWordSelect(HotWordListVo vo) {
        return request(Api.HotWordSelect(vo)).send().get();
    }

    /**
     * 店铺搜索
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<HotWordSelectBusinessDto>>> HotWordSelectBusiness(HotWordListVo vo) {
        return request(Api.HotWordSelectBusiness(vo)).send().get();
    }

    /**
     * 全部分类
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<GoodsTypeDto>>> getGoodsType() {
        return request(Api.getGoodsType()).send().get();
    }

    /**
     * 采购中心
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<BuyerCenterGoodsDto>>> getBuyerCenterGoods(BuyerCenterGoodsVo vo) {
        return request(Api.getBuyerCenterGoods(vo)).send().get();
    }

    /**
     * 人气推荐 新品推荐 同一个接口
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<MainRecommendDto>>> Recommend(RecommendVo vo) {
        return request(Api.Recommend(vo)).send().get();
    }

    /**
     * 返佣金专区
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<MainRecommendDto>>> ReturnCommission(ReturnCommissionVo vo) {
        return request(Api.ReturnCommission(vo)).send().get();
    }

    /**
     * 秒杀专区
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<SeckillPrefectureDto>>> SeckillPrefecture(SeckillPrefectureVo vo) {
        return request(Api.SeckillPrefecture(vo)).send().get();
    }

    /**
     * 店铺展示
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<StoreShowDto>>> StoreShow(StoreShowVo vo) {
        return request(Api.StoreShow(vo)).send().get();
    }

    /**
     * 商品详情
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<GoodsDetailDto>> getGoodsDetail(GoodsDetailVo vo) {
        return request(Api.getGoodsDetail(vo)).send().get();
    }

    /**
     * 商品评价
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<GoodsEvaluateDto>> getGoodsEvaluate(GoodsEvaluateVo vo) {
        return request(Api.getGoodsEvaluate(vo)).send().get();
    }

    /**
     * 加入购物车
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> joinShopCart(JoinShopCartVo vo) {
        return request(Api.joinShopCart(vo)).send().get();
    }

    /**
     * 收藏商品或店铺
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> collectGoodsOrStore(CollectGoodsOrStoreVo vo) {
        return request(Api.collectGoodsOrStore(vo)).send().get();
    }

    /**
     * 取消收藏
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> cancelCollect(CancelCollectVo vo) {
        return request(Api.cancelCollect(vo)).send().get();
    }

    /**
     * 选择优惠券
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<DiscountConpouDto>> getDiscountConpou(DiscountConpouVo vo) {
        return request(Api.getDiscountConpou(vo)).send().get();
    }

    /**
     * 判断是否有秒杀商品
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<GoodsIsSecondCheckDto>> getGoodsIsSecondCheck(GoodsIsSecondCheckVo vo) {
        return request(Api.getGoodsIsSecondCheck(vo)).send().get();
    }

    /**
     * 选择发货人
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ShipperDto>>> getShipperList(ShipperVo vo) {
        return request(Api.getShipperList(vo)).send().get();
    }

    /**
     * 开发票
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> addInvoice(InvoiceVo vo) {
        return request(Api.addInvoice(vo)).send().get();
    }

    /**
     * 提交订单
     * post
     * json  提交订单
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<AddOrderDto>> addOrder(AddOrderVo vo) {
        return request(Api.addOrder(vo)).send().get();
    }

    /**
     * 积分兑换商品提交订单
     * post
     * json  积分兑换商品提交订单
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<GoodByScoreDto>> buyGoodsByScore(GoodsByScoreVo vo) {
        return request(Api.buyGoodsByScore(vo)).send().get();
    }

    /**
     * 获取运费
     *
     * @param goodsIds
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> getFreight(String goodsIds) {
        return request(Api.getFreight(goodsIds)).send().get();
    }

    /**
     * 计算优惠券、积分、佣金抵扣金额
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<DiscountMoneyDto>> getDiscountMoney(DiscountMoneyVo vo) {
        return request(Api.getDiscountMoney(vo)).send().get();
    }

    /**
     * 点击次数新增
     *
     * @param id
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> ClickByPageId(String id) {
        return request(Api.ClickByPageId(id)).send().get();
    }

    /**
     * 资讯分类
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<InformationtTypeDto>>> getInformationTypeList(InformationVo vo) {
        return request(Api.getInformationTypeList(vo)).send().get();
    }

    /**
     * 资讯列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<InformationtDto>>> getInformationList(InformationVo vo) {
        return request(Api.getInformationList(vo)).send().get();
    }

    /**
     * 咨询详情
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<InformationtDetailDto>> getInformationDetail(InformationVo vo) {
        return request(Api.getInformationDetail(vo)).send().get();
    }

    /**
     * 评论资讯
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> commentInformation(CommentInformationVo vo) {
        return request(Api.commentInformation(vo)).send().get();
    }


    /**
     * 活动列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ActivityDto>>> getActivityList(ActivityVo vo) {
        return request(Api.getActivityList(vo)).send().get();
    }

    /**
     * 活动详情
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<ActivityDto>> getActivityDetail(ActivityVo vo) {
        return request(Api.getActivityDetail(vo)).send().get();
    }

    /**
     * 报名
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> applyActivity(ActivityVo vo) {
        return request(Api.applyActivity(vo)).send().get();
    }

    /**
     * 取消报名
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> cancelApply(ActivityVo vo) {
        return request(Api.cancelApply(vo)).send().get();
    }

    /**
     * 查看作品
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ActivityProductDto>>> getActivityProductList(ProductVo vo) {
        return request(Api.getActivityProductList(vo)).send().get();
    }

    /**
     * 投票或取消投票
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> voteActivityProduct(ProductVo vo) {
        return request(Api.voteActivityProduct(vo)).send().get();
    }

    /**
     * 问卷列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<QuestionDto>>> getQuestionList(QuestionVo vo) {
        return request(Api.getQuestionList(vo)).send().get();
    }

    /**
     * 提交问题答案
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<QuestionDetailDto>>> getQuestionDetail(QuestionVo vo) {
        return request(Api.getQuestionDetail(vo)).send().get();
    }

    /**
     * 提交问题答案
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> commitQuestion(CommitQuestionVo vo) {
        return request(Api.commitQuestion(vo)).send().get();
    }


    /**
     * 我的活动列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ActivityDto>>> getMyActivityList(ActivityVo vo) {
        return request(Api.getMyActivityList(vo)).send().get();
    }

    /**
     * 我的作品列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ActivityProductDto>>> getMyProduct(ProductVo vo) {
        return request(Api.getMyProduct(vo)).send().get();
    }

    /**
     * 上传作品
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto> addProduct(ProductVo vo) {
        return request(Api.addProduct(vo)).send().get();
    }



}
