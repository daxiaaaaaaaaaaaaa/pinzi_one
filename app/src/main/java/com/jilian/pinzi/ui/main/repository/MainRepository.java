package com.jilian.pinzi.ui.main.repository;

import android.arch.lifecycle.LiveData;

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

import java.util.List;

import io.reactivex.Flowable;

public interface MainRepository {
    /**
     * 系统消息/新闻公告
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<MsgDto>>> SystemInformation(MsgVo vo);

    /**
     * 消息详情/公告详情
     *
     * @param id
     * @param uId
     * @return
     */
    LiveData<BaseDto<MsgDto>> InformationDetails(String id, String uId);

    /**
     * 获取新消息 首页消息已读
     *
     * @param type
     * @param uId
     * @return
     */
    LiveData<BaseDto<Integer>> MessageRead(Integer type, String uId);

    /**
     * 启动页配置
     *
     * @param type 类型（1.启动页 2.引导页 3.首页广告轮播 4,店铺广告轮播，5精品广告轮播 6，人气广告轮播 7，积分商场轮播图）
     * @return
     */
    LiveData<BaseDto<List<StartPageDto>>> StartPage(Integer type);

    /**
     * 领劵中心
     *
     * @param uId
     * @return
     */
    LiveData<BaseDto<List<CouponCentreDto>>> CouponCentre(String uId);

    /**
     * 领取优惠券
     *
     * @param id
     * @param uId
     * @return
     */
    LiveData<BaseDto<String>> GetCoupon(String id, String uId);


    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    LiveData<BaseDto<CouponCentreDto>> CouponDetails(String id);

    /**
     * 用户签到
     *
     * @param
     * @return
     */
    LiveData<BaseDto<String>> ClockIn(String uId);

    /**
     * 积分商品或相似推荐商品
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<ScoreBuyGoodsDto>>> getScoreBuyGoods(ScoreBuyGoodsVo vo);

    /**
     * 热词列表
     *
     * @return
     */
    LiveData<BaseDto<List<HotWordListDto>>> HotWordList(HotWordListVo vo);

    /**
     * 首页搜索
     *
     * @return
     */
    LiveData<BaseDto<List<HotWordSelectDto>>> HotWordSelect(HotWordListVo vo);

    /**
     * 店铺搜索
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<HotWordSelectBusinessDto>>> HotWordSelectBusiness(HotWordListVo vo);


    /**
     * 全部分类
     *
     * @return
     */
    LiveData<BaseDto<List<GoodsTypeDto>>> getGoodsType();

    /**
     * 采购中心
     *
     * @return
     */
    LiveData<BaseDto<List<BuyerCenterGoodsDto>>> getBuyerCenterGoods(BuyerCenterGoodsVo vo);

    /**
     * 人气推荐 新品推荐 同一个接口
     *
     * @return
     */
    LiveData<BaseDto<List<MainRecommendDto>>> Recommend(RecommendVo vo);

    /**
     * 返佣金专区
     *
     * @return
     */
    LiveData<BaseDto<List<MainRecommendDto>>> ReturnCommission(ReturnCommissionVo vo);


    /**
     * 秒杀专区
     *
     * @return
     */
    LiveData<BaseDto<List<SeckillPrefectureDto>>> SeckillPrefecture(SeckillPrefectureVo vo);


    /**
     * 店铺展示
     *
     * @return
     */
    LiveData<BaseDto<List<StoreShowDto>>> StoreShow(StoreShowVo vo);

    /**
     * 商品详情
     *
     * @return
     */
    LiveData<BaseDto<GoodsDetailDto>> getGoodsDetail(GoodsDetailVo vo);

    /**
     * 商品评价
     *
     * @return
     */
    LiveData<BaseDto<GoodsEvaluateDto>> getGoodsEvaluate(GoodsEvaluateVo vo);

    /**
     * 加入购物车
     *
     * @return
     */
    LiveData<BaseDto<String>> joinShopCart(JoinShopCartVo vo);

    /**
     * 收藏商品或店铺
     *
     * @return
     */
    LiveData<BaseDto<String>> collectGoodsOrStore(CollectGoodsOrStoreVo vo);

    /**
     * 取消收藏
     *
     * @return
     */
    LiveData<BaseDto<String>> cancelCollect(CancelCollectVo vo);


    /**
     * 选择优惠券
     *
     * @return
     */
    LiveData<BaseDto<DiscountConpouDto>> getDiscountConpou(DiscountConpouVo vo);

    /**
     * 判断是否有秒杀商品
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<GoodsIsSecondCheckDto>> getGoodsIsSecondCheck(GoodsIsSecondCheckVo vo);

    /**
     * 选择发货人
     *
     * @return
     */
    LiveData<BaseDto<List<ShipperDto>>> getShipperList(ShipperVo vo);

    /**
     * 开发票
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> addInvoice(InvoiceVo vo);

    /**
     * 提交订单
     * post
     * json  提交订单
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<AddOrderDto>> addOrder(AddOrderVo vo);

    /**
     * 积分兑换商品提交订单
     * post
     * json  积分兑换商品提交订单
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<GoodByScoreDto>> buyGoodsByScore(GoodsByScoreVo vo);

    /**
     * 获取运费
     *
     * @param goodsIds
     * @return
     */
    LiveData<BaseDto<String>> getFreight(String goodsIds);

    /**
     * 计算优惠券、积分、佣金抵扣金额
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<DiscountMoneyDto>> getDiscountMoney(DiscountMoneyVo vo);

    /**
     * 点击次数新增
     *
     * @param id
     * @return
     */
    LiveData<BaseDto<String>> ClickByPageId(String id);

    /**
     * 资讯分类
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<InformationtTypeDto>>> getInformationTypeList(InformationVo vo);

    /**
     * 资讯列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<InformationtDto>>> getInformationList(InformationVo vo);

    /**
     * 咨询详情
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<InformationtDetailDto>> getInformationDetail(InformationVo vo);

    /**
     * 评论资讯
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto> commentInformation(CommentInformationVo vo);

    /**
     * 活动列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<ActivityDto>>> getActivityList(ActivityVo vo);


    /**
     * 活动详情
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<ActivityDto>> getActivityDetail(ActivityVo vo);

    /**
     * 报名
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto> applyActivity(ActivityVo vo);

    /**
     * 取消报名
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto> cancelApply(ActivityVo vo);

    /**
     * 查看作品
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<ActivityProductDto>>> getActivityProductList(ProductVo vo);

    /**
     * 投票或取消投票
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto> voteActivityProduct(ProductVo vo);

    /**
     * 问卷列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<QuestionDto>>> getQuestionList(QuestionVo vo);

    /**
     * 问卷详情
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<QuestionDetailDto>>> getQuestionDetail(QuestionVo vo);

    /**
     * 提交问题
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto> commitQuestion(CommitQuestionVo vo);


    /**
     * 我的活动列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<ActivityDto>>> getMyActivityList(ActivityVo vo);

    /**
     * 我的作品列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<ActivityProductDto>>> getMyProduct(ProductVo vo);

    /**
     * 上传作品
     * @param vo
     * @return
     */
    LiveData<BaseDto> addProduct(ProductVo vo);

    /**
     * 获取七牛云token
     * @return
     */
    LiveData<BaseDto<String>> uptoken();

}
