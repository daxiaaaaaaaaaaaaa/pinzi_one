package com.jilian.pinzi.http;


import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.common.dto.AddOrderDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.BuyerCenterGoodsDto;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
import com.jilian.pinzi.common.dto.DiscountMoneyDto;
import com.jilian.pinzi.common.dto.EmptyDto;
import com.jilian.pinzi.common.dto.EvaluateDetailDto;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
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
import com.jilian.pinzi.common.dto.InviteListDto;
import com.jilian.pinzi.common.dto.InviteeDetailDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.LotteryInfoDto;
import com.jilian.pinzi.common.dto.LotteryRecordDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.MemberDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.MyCouponDto;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.dto.MyTntegralDetailDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.common.dto.PersonalDto;
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.common.dto.RefundReasonDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.common.dto.SaleRecordDetailDto;
import com.jilian.pinzi.common.dto.SaleRecordDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.common.dto.SeckillDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.common.dto.ShippmentDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.dto.UpdatePersonInfoDto;
import com.jilian.pinzi.common.dto.VersionInfoDto;
import com.jilian.pinzi.common.vo.AddAdressVo;
import com.jilian.pinzi.common.vo.AddCourierInfoVo;
import com.jilian.pinzi.common.vo.AddOrderVo;
import com.jilian.pinzi.common.vo.ApplyRefundVo;
import com.jilian.pinzi.common.vo.BuyCouponVo;
import com.jilian.pinzi.common.vo.CommentGoodVo;
import com.jilian.pinzi.common.vo.CommitQuestionVo;
import com.jilian.pinzi.common.vo.DiscountMoneyVo;
import com.jilian.pinzi.common.vo.EditAdressVo;
import com.jilian.pinzi.common.vo.GetWithdrawDepositVo;
import com.jilian.pinzi.common.vo.GoodsByScoreVo;
import com.jilian.pinzi.common.vo.InvoiceVo;
import com.jilian.pinzi.common.vo.LoginVo;
import com.jilian.pinzi.common.vo.PayOrderVo;
import com.jilian.pinzi.common.vo.ProductVo;
import com.jilian.pinzi.common.vo.ThirdUserLoginVo;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * api接口
 *
 * @author weishixiong
 * @Time 2018-04-2
 */

public interface ApiService {
    /**
     * 登录
     * post
     * 表单提交
     *
     * @param vo
     * @return
     */
    @POST("user/PhoneUserLogin")
    Flowable<BaseDto<LoginDto>> login(@Body LoginVo vo);

    /**
     * 注册
     * post
     * 表单提交
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/RegistrationUser")
    Flowable<BaseDto<RegisterDto>> register(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     * post
     * 表单提交
     *
     * @param map
     * @return
     */
    @GET("user/SendMsg")
    Flowable<BaseDto<String>> sendMsg(@QueryMap Map<String, String> map);


    /**
     * 重置密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/ResetPassword")
    Flowable<BaseDto<String>> resetPwd(@FieldMap Map<String, String> map);


    /**
     * 完善信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/PerfectInformation")
    Flowable<BaseDto<String>> perfectInformation(@FieldMap Map<String, String> map);

    /**
     * 一键上传文件
     *
     * @param multipartBody
     * @return
     */
    @POST("photoImg")
    Flowable<BaseDto<String>> photoImg(@Body MultipartBody multipartBody);


    /**
     * 查询个人资料
     *
     * @param id
     * @return
     */
    @GET("user/PersonalMessage")
    Flowable<BaseDto<PersonalDto>> personalMessage(@Query("id") String id);

    /**
     * 修改个人资料
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/UpdatePersonalMessage")
    Flowable<BaseDto<UpdatePersonInfoDto>> updatePersonalMessage(@FieldMap Map<String, String> map);

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/UpdatePassword")
    Flowable<BaseDto<String>> updatePassword(@FieldMap Map<String, String> map);

    /**
     * 添加收货人
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    @POST("user/addUserAddress")
    Flowable<BaseDto<String>> addUserAddress(@Body AddAdressVo vo);

    /**
     * 编辑收货人
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    @POST("user/editUserAddress")
    Flowable<BaseDto<String>> editUserAddress(@Body EditAdressVo vo);

    /**
     * 用户地址列表
     * post
     * json 提交
     *
     * @param map
     * @return
     */
    @GET("user/getUserAddressList")
    Flowable<BaseDto<List<AddressDto>>> getUserAddressList(@QueryMap Map<String, String> map);


    /**
     * 删除用户地址
     * post
     * json 提交
     *
     * @param map
     * @return
     */
    @GET("user/delUserAddress")
    Flowable<BaseDto<List<String>>> delUserAddress(@QueryMap Map<String, String> map);

    /**
     * 我的足迹 我的收藏
     *
     * @param map
     * @return
     */
    @GET("user/FootPrintAndCollect")
    Flowable<BaseDto<List<CollectionFootDto>>> FootPrintAndCollect(@QueryMap Map<String, String> map);


    /**
     * 系统消息/新闻公告
     *
     * @param map
     * @return
     */
    @GET("conf/SystemInformation")
    Flowable<BaseDto<List<MsgDto>>> SystemInformation(@QueryMap Map<String, String> map);


    /**
     * 消息详情/公告详情
     *
     * @param id
     * @param uId
     * @return
     */
    @GET("conf/InformationDetails")
    Flowable<BaseDto<MsgDto>> InformationDetails(@Query("id") String id, @Query("uId") String uId);


    /**
     * 获取新消息 首页消息已读
     *
     * @param
     * @return
     */
    @GET("conf/MessageRead")
    Flowable<BaseDto<Integer>> MessageRead(@Query("type") Integer type, @Query("uId") String uId);


    /**
     * 启动页配置
     *
     * @param type 类型（1.启动页 2.引导页 3.首页广告轮播 4,店铺广告轮播，5精品广告轮播 6，人气广告轮播 7，积分商场轮播图）
     * @return
     */
    @GET("conf/StartPage")
    Flowable<BaseDto<List<StartPageDto>>> StartPage(@Query("type") Integer type);


    /**
     * 领劵中心
     *
     * @param
     * @return
     */
    @GET("index/CouponCentre")
    Flowable<BaseDto<List<CouponCentreDto>>> CouponCentre(@Query("uId") String uId,@Query("startNum") Integer startNum,@Query("pageSize") Integer pageSize);


    /**
     * 领取优惠券
     *
     * @param
     * @return
     */
    @GET("index/GetCoupon")
    Flowable<BaseDto<String>> GetCoupon(@Query("id") String id, @Query("uId") String uId);

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    @GET("personCenter/getCouponDetail")
    Flowable<BaseDto<CouponCentreDto>> getCouponDetail(@Query("cId") String cId);

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    @GET("index/CouponDetails")
    Flowable<BaseDto<CouponCentreDto>> CouponDetails(@Query("id") String id);

    /**
     * 用户签到
     *
     * @param
     * @return
     */
    @GET("index/ClockIn")
    Flowable<BaseDto<String>> ClockIn(@Query("uId") String uId);


    /**
     * 我的优惠券列表
     *
     * @param
     * @return
     */
    @GET("personCenter/getMyCouponList")
    Flowable<BaseDto<List<MyCouponDto>>> getMyCouponList(@Query("uId") String uId, @Query("status") Integer status, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 奖品数据
     *
     * @return
     */
    @GET("index/PrizeData")
    Flowable<BaseDto<List<LotteryInfoDto>>> getLotteryInfo();

    /**
     * 判断抽奖积分
     *
     * @param uId
     * @return
     */
    @GET("index/BeginLottry")
    Flowable<BaseDto> getLotteryScore(@Query("uId") String uId);

    /**
     * 添加抽奖结果
     *
     * @param uId
     * @param id
     * @return
     */
    @GET("index/AddDrawRecord")
    Flowable<BaseDto> addLotteryResult(@Query("uId") String uId, @Query("id") String id);

    /**
     * 抽奖结果列表
     *
     * @return
     */
    @GET("index/DrawRecordList")
    Flowable<BaseDto<List<LotteryRecordDto>>> getLotteryList(@QueryMap Map<String, String> map);


    /**
     * 积分商品或相似推荐商品
     *
     * @param map
     * @return
     */
    @GET("goods/getScoreBuyGoods")
    Flowable<BaseDto<List<ScoreBuyGoodsDto>>> getScoreBuyGoods(@QueryMap Map<String, String> map);

    /**
     * 积分商品或相似推荐商品
     *
     * @param map
     * @return
     */
    @GET("user/getMyRecord")
    Flowable<BaseDto<List<MyRecordDto>>> getMyRecord(@QueryMap Map<String, String> map);

    /**
     * 赠送佣金
     *
     * @param map
     * @return
     */
    @GET("personCenter/giveCommission")
    Flowable<BaseDto<String>> giveCommission(@QueryMap Map<String, String> map);

    /**
     * 提现
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    @POST("personCenter/getWithdrawDeposit")
    Flowable<BaseDto<String>> getWithdrawDeposit(@Body GetWithdrawDepositVo vo);


    /**
     * 积分 余额 佣金结算接口
     *
     * @return
     */
    @GET("order/getMyScoreOrCommission")
    Flowable<BaseDto<ScoreOrCommissionDto>> getMyScoreOrCommission(@Query("uId") String uId);

    /**
     * 热词列表
     *
     * @return
     */
    @GET("index/HotWordList")
    Flowable<BaseDto<List<HotWordListDto>>> HotWordList(@QueryMap Map<String, String> map);


    /**
     * 首页搜索
     *
     * @return
     */
    @GET("index/HotWordSelect")
    Flowable<BaseDto<List<HotWordSelectDto>>> HotWordSelect(@QueryMap Map<String, String> map);

    /**
     * 店铺搜索
     *
     * @return
     */
    @GET("index/HotWordSelectBusiness")
    Flowable<BaseDto<List<HotWordSelectBusinessDto>>> HotWordSelectBusiness(@QueryMap Map<String, String> map);

    /**
     * 购物车
     *
     * @return
     */
    @GET("shopCart/getShopCartList")
    Flowable<BaseDto<List<ShopCartLisDto>>> getShopCartList(@QueryMap Map<String, String> map);

    /**
     * 增加或减少购物车数量
     *
     * @return
     */
    @GET("shopCart/addOrReduceGoods")
    Flowable<BaseDto<String>> addOrReduceGoods(@QueryMap Map<String, String> map);


    /**
     * 单个删除或全部删除
     *
     * @return
     */
    @GET("shopCart/deleteGoods")
    Flowable<BaseDto<String>> deleteGoods(@QueryMap Map<String, String> map);


    /**
     * 全部分类
     *
     * @return
     */
    @GET("goods/getGoodsType")
    Flowable<BaseDto<List<GoodsTypeDto>>> getGoodsType();


    /**
     * 采购中心商品
     *
     * @return
     */
    @GET("goods/getBuyerCenterGoods")
    Flowable<BaseDto<List<BuyerCenterGoodsDto>>> getBuyerCenterGoods(@QueryMap Map<String, String> map);


    /**
     * 人气推荐 新品推荐 同一个接口
     *
     * @return
     */
    @GET("index/Recommend")
    Flowable<BaseDto<List<MainRecommendDto>>> Recommend(@QueryMap Map<String, String> map);

    /**
     * 返佣金专区
     *
     * @return
     */
    @GET("index/ReturnCommission")
    Flowable<BaseDto<List<MainRecommendDto>>> ReturnCommission(@QueryMap Map<String, String> map);

    /**
     * 秒杀专区
     *
     * @return
     */
    @GET("index/SeckillPrefecture")
    Flowable<BaseDto<SeckillDto>> SeckillPrefecture(@QueryMap Map<String, String> map);

    /**
     * 店铺展示
     *
     * @return
     */
    @GET("index/StoreShow")
    Flowable<BaseDto<List<StoreShowDto>>> StoreShow(@QueryMap Map<String, String> map);

    /**
     * 我的发货列表
     *
     * @return
     */
    @GET("personCenter/getMyShippmentList")
    Flowable<BaseDto<List<ShippmentDto>>> getMyShippmentList(@QueryMap Map<String, String> map);

    /**
     * 发货
     *
     * @return
     */
    @GET("personCenter/deliverGoods")
    Flowable<BaseDto<String>> deliverGoods(@QueryMap Map<String, String> map);

    /**
     * 快递公司
     *
     * @return
     */
    @GET("personCenter/getDeliveryMethodList")
    Flowable<BaseDto<List<DeliveryMethodDto>>> getDeliveryMethodList();

    /**
     * 商品详情
     *
     * @return
     */
    @GET("goods/getGoodsDetail")
    Flowable<BaseDto<GoodsDetailDto>> getGoodsDetail(@QueryMap Map<String, String> map);


    /**
     * 商品评价
     *
     * @return
     */
    @GET("goods/getGoodsEvaluate")
    Flowable<BaseDto<GoodsEvaluateDto>> getGoodsEvaluate(@QueryMap Map<String, String> map);

    /**
     * 加入购物车
     *
     * @return
     */
    @GET("goods/joinShopCart")
    Flowable<BaseDto<String>> joinShopCart(@QueryMap Map<String, String> map);

    /**
     * 收藏商品或店铺
     *
     * @return
     */
    @GET("goods/collectGoodsOrStore")
    Flowable<BaseDto<String>> collectGoodsOrStore(@QueryMap Map<String, String> map);

    /**
     * 取消收藏
     *
     * @return
     */
    @GET("goods/cancelCollect")
    Flowable<BaseDto<String>> cancelCollect(@QueryMap Map<String, String> map);


    /**
     * 选择优惠券
     *
     * @return
     */
    @GET("order/getDiscountConpou")
    Flowable<BaseDto<DiscountConpouDto>> getDiscountConpou(@QueryMap Map<String, String> map);


    /**
     * 选择发货人
     *
     * @return
     */
    @GET("order/getShipperList")
    Flowable<BaseDto<List<ShipperDto>>> getShipperList(@QueryMap Map<String, String> map);


    /**
     * 判断是否有秒杀商品
     *
     * @param map
     * @return
     */
    @POST("goods/getGoodsIsSecondCheck")
    Flowable<BaseDto<GoodsIsSecondCheckDto>> getGoodsIsSecondCheck(@QueryMap Map<String, String> map);

    /**
     * 开发票
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    @POST("order/addInvoice")
    Flowable<BaseDto<String>> addInvoice(@Body InvoiceVo vo);

    /**
     *  提交订单
     * post
     * json  提交订单
     *
     * @param vo
     * @return
     */
    @POST("order/addOrder")
    Flowable<BaseDto<AddOrderDto>> addOrder(@Body AddOrderVo vo);

    /**
     *  积分兑换商品提交订单
     * post
     * json  积分兑换商品提交订单
     *
     * @param vo
     * @return
     */
    @POST("buyGoods/buyGoodsByScore")
    Flowable<BaseDto<GoodByScoreDto>> buyGoodsByScore(@Body GoodsByScoreVo vo);

    /**
     * 我的订单列表
     * @param map
     * @return
     */
    @GET("personCenter/getMyOrderList")
    Flowable<BaseDto<List<MyOrderDto>>> getMyOrderList(@QueryMap Map<String, String> map);



    /**
     * 取消订单、确认收货、删除订单
     * @param map
     * @return
     */
    @GET("personCenter/updateOrderStatus")
    Flowable<BaseDto<String>> updateOrderStatus(@QueryMap Map<String, String> map);



    /**
     * 获取运费
     * @param goodsIds
     * @return
     */
    @GET("order/getFreight")
    Flowable<BaseDto<String>> getFreight(@Query("goodsIds") String goodsIds);

    /**
     * 计算优惠券、积分、佣金抵扣金额
     * @param
     * @return
     */
    @POST("order/getDiscountMoney")
    Flowable<BaseDto<DiscountMoneyDto>> getDiscountMoney(@Body  DiscountMoneyVo vo);


    /**
     * 支付
     * @param
     * @return
     */
    @POST("buyGoods/payOrder")
    Flowable<BaseDto<String>> payOrder(@Body PayOrderVo vo);

    /**
     * 我的上级 、下级、下二级、下三级
     * @param map
     * @return
     */
    @GET("personCenter/getInviteList")
    Flowable<BaseDto<List<InviteListDto>>> getInviteList(@QueryMap Map<String, String> map);

    /**
     * 我的下级详情
     * @param map
     * @return
     */
    @GET("personCenter/getInviteeDetail")
    Flowable<BaseDto<InviteeDetailDto>> getInviteeDetail(@QueryMap Map<String, String> map);

    /**
     * 添加上级
     * @param map
     * @return
     */
    @GET("personCenter/addMySuperior")
    Flowable<BaseDto<String>> addMySuperior(@QueryMap Map<String, String> map);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GET("personCenter/getOrderDetail")
    Flowable<BaseDto<OrderDetailDto>> getOrderDetail(@Query("orderId") String orderId);

    /**
     *个人中心
     * @param type
     * @param id
     * @return
     */
    @GET("user/MemberCenter")
    Flowable<BaseDto<MemberDto>> MemberCenter(@Query("type") Integer type, @Query("id") String id);

    /**
     * 获取商铺详情
     * @param storeId
     * @param uId
     * @return
     */
    @GET("goods/getStoreDetail")
    Flowable<BaseDto<ShopDetailDto>> getStoreDetail(@Query("storeId") String storeId, @Query("uId") String uId);

    /**
     * 获取商铺详情
     * @return
     */
    @GET("goods/getStoreGoods")
    Flowable<BaseDto<List<ShopGoodsDto>>> getStoreGoods(@Query("identity") int identity,
                                                  @Query("storeId") String storeId, @Query("goodsName") String goodsName,@Query("entrance") String entrance);


    /**
     * 订单跟踪
     * @param orderId
     * @return
     */
    @GET("personCenter/getOrderOfLogistics")
    Flowable<BaseDto<OrderTrackDto>> getOrderOfLogistics(@Query("orderId") String orderId);
    /**
     * 评价
     * @param
     * @return
     */
    @POST("personCenter/addEvaluate")
    Flowable<BaseDto<String>> addEvaluate(@Body CommentGoodVo vo);


    /**
     * 退货原因列表
     * @return
     */
    @GET("afterSale/getRefundReason")
    Flowable<BaseDto<List<RefundReasonDto>>> getRefundReason();

    /**
     * 申请退货
     * @param vo
     * @return
     */
    @POST("afterSale/applyRefund")
    Flowable<BaseDto<String>> applyRefund(@Body ApplyRefundVo vo);

    /**
     * 申请记录
     * @return
     */
    @POST("afterSale/getApplyRefundList")
    Flowable<BaseDto<List<SaleRecordDto>>> getApplyRefundList(@Query("uId") String uId,
                                                        @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 记录详情
     * @param aId
     * @return
     */
    @GET("afterSale/getDetail")
    Flowable<BaseDto<SaleRecordDetailDto>> getSaleRecordDetail(@Query("aId") String aId);

    /**
     * 添加物流信息
     * @param vo
     * @return
     */
    @POST("afterSale/addCourierInfo")
    Flowable<BaseDto<EmptyDto>> addCourierInfo(@Body AddCourierInfoVo vo);
    /**
     * 查看评价
     * @param orderId
     * @return
     */
    @GET("personCenter/getEvaluateDetail")
    Flowable<BaseDto<EvaluateDetailDto>> getEvaluateDetail(@Query("orderId") String orderId);

    /**
     * 删除浏览记录
     * @param map
     * @return
     */
    @GET("user/delUserFootprint")
    Flowable<BaseDto<String>> delUserFootprint(@QueryMap Map<String, String> map);

    /**
     * 点击次数新增
     * @param id)
     * @return
     */
    @GET("conf/ClickByPageId")
    Flowable<BaseDto<String>> ClickByPageId(@Query("id") String id);

    /**
     * 获取朋友列表
     * @param
     * @return
     */
    @GET("circle/FriendCircleList")
    Flowable<BaseDto<List<FriendCircleDto>>> FriendCircleList(@QueryMap Map<String, String> map);

    /**
     * 朋友圈删除
     * @param id
     * @return
     */
    @GET("circle/FriendCircleDelete")
    Flowable<BaseDto<String>> FriendCircleDelete(@Query("id") String id);

    /**
     * 朋友圈评论
     * @param
     * @return
     */
    @POST("circle/FriendCircleComment")
    Flowable<BaseDto<String>> FriendCircleComment(@QueryMap Map<String, String> map);

    /**
     * 朋友圈评论删除
     * @param id 评论ID
     * @return
     */
    @GET("circle/FriendCircleCommentDelete")
    Flowable<BaseDto<String>> FriendCircleCommentDelete(@Query("id") String id);

    /**
     * 朋友圈点赞
     * @param
     * @return
     */
    @POST("circle/FriendCircleLike")
    Flowable<BaseDto<String>> FriendCircleLike(@QueryMap Map<String, String> map);

    /**
     * 朋友圈取消点赞
     * @param id 点赞ID
     * @return
     */
    @GET("circle/FriendCircleCancelLike")
    Flowable<BaseDto<String>> FriendCircleCancelLike(@Query("id") String id);


    /**
     *  朋友圈评论回复
     * @param
     * @return
     */
    @POST("circle/CommentReplyAdd")
     Flowable<BaseDto<String>> CommentReplyAdd(@QueryMap Map<String, String> map);

    /**
     *  发布朋友圈
     * @param
     * @return
     */
    @POST("circle/FriendCircleIssue")
    Flowable<BaseDto<String>> FriendCircleIssue(@QueryMap Map<String, String> map);


    /**
     * 刷新单条朋友圈
     * @param map
     * @return
     */
    @GET("circle/SingleFriendCircle")
    Flowable<BaseDto<List<FriendCircleListDetailDto>>> SingleFriendCircle(@QueryMap Map<String, String> map);


    /**
     * 我的朋友圈
     * @param
     * @return
     */
    @GET("circle/MyFriendCircleList")
    Flowable<BaseDto<List<FriendCircleDto>>> MyFriendCircleList(@QueryMap Map<String, String> map);


    /**
     * 用户类型朋友圈列表
     * @param
     * @return
     */
    @GET("circle/UserTypeFriendCircleList")
    Flowable<BaseDto<List<FriendCircleDto>>> UserTypeFriendCircleList(@QueryMap Map<String, String> map);

    /**
     * 积分 抽奖说明
     * @return
     */
    @GET("user/getContent")
    Flowable<BaseDto<MyTntegralDetailDto>> getContent();

    /**
     * 资讯分类
     * @param map
     * @return
     */
    @GET("information/getInformationTypeList")
    Flowable<BaseDto<List<InformationtTypeDto>>> getInformationTypeList(@QueryMap Map<String, String> map);

    /**
     *  资讯列表
     * @param map
     * @return
     */
    @GET("information/getInformationList")
    Flowable<BaseDto<List<InformationtDto>>> getInformationList(@QueryMap Map<String, String> map);

    /**
     * 咨询详情
     * @param map
     * @return
     */
    @GET("information/getInformationDetail")
    Flowable<BaseDto<InformationtDetailDto>> getInformationDetail(@QueryMap Map<String, String> map);

    /**
     * 评论资讯
     * @param map
     * @return
     */
    @GET("information/commentInformation")
    Flowable<BaseDto> commentInformation(@QueryMap Map<String, String> map);

    /**
     *
     * 活动列表
     * @param map
     * @return
     */
    @GET("activity/getActivityList")
    Flowable<BaseDto<List<ActivityDto>>> getActivityList(@QueryMap Map<String, String> map);

    /**
     * 活动详情
     * @param map
     * @return
     */
    @GET("activity/getActivityDetail")
    Flowable<BaseDto<ActivityDto>> getActivityDetail(@QueryMap Map<String, String> map);


    /**
     * 报名
     * @param map
     * @return
     */
    @GET("activity/applyActivity")
    Flowable<BaseDto> applyActivity(@QueryMap Map<String, String> map);

    /**
     * 取消报名
     * @param map
     * @return
     */
    @GET("activity/cancelApply")
    Flowable<BaseDto> cancelApply(@QueryMap Map<String, String> map);

    /**
     * 查看作品
     * @param map
     * @return
     */
    @GET("activity/getActivityProductList")
    Flowable<BaseDto<List<ActivityProductDto>>> getActivityProductList(@QueryMap Map<String, String> map);

    /**
     * 投票或取消投票
     * @param map
     * @return
     */
    @GET("activity/voteActivityProduct")
    Flowable<BaseDto> voteActivityProduct(@QueryMap Map<String, String> map);


    /**
     * 问卷列表
     * @param map
     * @return
     */
    @GET("question/getQuestionList")
    Flowable<BaseDto<List<QuestionDto>>> getQuestionList(@QueryMap Map<String, String> map);


    /**
     * 问卷详情
     * @param map
     * @return
     */
    @GET("question/getQuestionDetail")
    Flowable<BaseDto<List<QuestionDetailDto>>> getQuestionDetail(@QueryMap Map<String, String> map);

    /**
     * 提交问卷
     * @param vo
     * @return
     */
    @POST("question/commitQuestion")
    Flowable<BaseDto> commitQuestion(@Body CommitQuestionVo vo);

    /**
     * 我参与的活动列表
     * @param map
     * @return
     */
    @GET("activity/getMyActivityList")
    Flowable<BaseDto<List<ActivityDto>>> getMyActivityList(@QueryMap Map<String, String> map);


    /**
     * 我的作品
     * @param map
     * @return
     */
    @GET("activity/getMyProduct")
    Flowable<BaseDto<List<ActivityProductDto>>> getMyProduct(@QueryMap Map<String, String> map);


    /**
     * 上传作品
     * @param
     * @return
     */

    @POST("activity/addProduct")
    Flowable<BaseDto> addProduct(@Body ProductVo vo);

    /**
     * 获取七牛云token
     * @param
     * @return
     */
    @GET("qiniu/uptoken")
    Flowable<BaseDto<String>> uptoken();

    /**
     * 店铺优惠
     * @param map
     * @return
     */
    @GET("goods/getStoreCoupon")
    Flowable<BaseDto<List<StoreCouponDto>>> getStoreCoupon(@QueryMap Map<String, String> map);

    /**
     * 购买优惠券
     * @param vo
     * @return
     */
    @POST("buyGoods/buyCoupon")
    Flowable<BaseDto<String>> buyCoupon(@Body BuyCouponVo vo);


    /**
     * 览记录统计(查看商品详情时调用)
     * @param map
     * @return
     */
    @GET("goods/updatePvOrUv")
    Flowable<BaseDto> updatePvOrUv(@QueryMap Map<String, String> map);

    /**
     * 充值
     * @param vo
     * @return
     */
    @POST("buyGoods/rechargeCommsion")
    Flowable<BaseDto<String>> rechargeCommsion(@Body BuyCouponVo vo);

    /**
     * 获取第一步的code后，请求以下链接获取access_token：
     * @param map
     * @return
     */
    @GET("sns/oauth2/access_token")
    Flowable<String> access_token(@QueryMap Map<String, String> map);

    /**
     * 删除充值记录
     * @param ids
     * @return
     */
    @POST("user/deleteRecharge")
    Flowable<BaseDto> deleteRecharge(@Query("ids") String ids);

    /**
     * 提现批量删除
     * @param ids
     * @return
     */
    @POST("withdrawDeposit/deleteByIds")
    Flowable<BaseDto> deleteByIds(@Query("ids") String ids);

    /**
     * 第三方登录
     * @param vo
     * @return
     */
    @POST("user/ThirdUserLogin")
    Flowable<BaseDto<LoginDto>> ThirdUserLogin(@Body ThirdUserLoginVo vo);


    /**
     * 版本更新
     * @return
     */
    @GET("user/getVersionInfo")
    Flowable<BaseDto<VersionInfoDto>> getVersionInfo();


    /**
     * 版本更新 下载apk 文件
     * @param range
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> executeDownload(@Header("Range") String range, @Url() String url);

}


