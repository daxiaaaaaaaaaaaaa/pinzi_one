package com.jilian.pinzi.http;


import com.jilian.pinzi.base.BaseDto;
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
import com.jilian.pinzi.common.dto.RefundReasonDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.common.dto.SaleRecordDetailDto;
import com.jilian.pinzi.common.dto.SaleRecordDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.common.dto.ShippmentDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.dto.UpdatePersonInfoDto;
import com.jilian.pinzi.common.vo.AddAdressVo;
import com.jilian.pinzi.common.vo.AddCourierInfoVo;
import com.jilian.pinzi.common.vo.AddMySuperiorVo;
import com.jilian.pinzi.common.vo.AddOrReduceGoodsVo;
import com.jilian.pinzi.common.vo.AddOrderVo;
import com.jilian.pinzi.common.vo.ApplyRefundVo;
import com.jilian.pinzi.common.vo.BuyerCenterGoodsVo;
import com.jilian.pinzi.common.vo.CancelCollectVo;
import com.jilian.pinzi.common.vo.CollectGoodsOrStoreVo;
import com.jilian.pinzi.common.vo.CollectionFootVo;
import com.jilian.pinzi.common.vo.CommentGoodVo;
import com.jilian.pinzi.common.vo.CommentInformationVo;
import com.jilian.pinzi.common.vo.CommentReplyAddVo;
import com.jilian.pinzi.common.vo.DeleteAdressVo;
import com.jilian.pinzi.common.vo.DeleteFootVo;
import com.jilian.pinzi.common.vo.DeleteGoodsVo;
import com.jilian.pinzi.common.vo.DeliverGoodsVo;
import com.jilian.pinzi.common.vo.DiscountConpouVo;
import com.jilian.pinzi.common.vo.DiscountMoneyVo;
import com.jilian.pinzi.common.vo.EditAdressVo;
import com.jilian.pinzi.common.vo.FriendCircleCommentVo;
import com.jilian.pinzi.common.vo.FriendCircleIssueVo;
import com.jilian.pinzi.common.vo.FriendCircleLikeVo;
import com.jilian.pinzi.common.vo.FriendCircleListVo;
import com.jilian.pinzi.common.vo.GetUserAddressListVo;
import com.jilian.pinzi.common.vo.GetWithdrawDepositVo;
import com.jilian.pinzi.common.vo.GiveCommissionVo;
import com.jilian.pinzi.common.vo.GoodsByScoreVo;
import com.jilian.pinzi.common.vo.GoodsDetailVo;
import com.jilian.pinzi.common.vo.GoodsEvaluateVo;
import com.jilian.pinzi.common.vo.GoodsIsSecondCheckVo;
import com.jilian.pinzi.common.vo.HotWordListVo;
import com.jilian.pinzi.common.vo.InformationVo;
import com.jilian.pinzi.common.vo.InviteListVo;
import com.jilian.pinzi.common.vo.InviteeDetailVo;
import com.jilian.pinzi.common.vo.InvoiceVo;
import com.jilian.pinzi.common.vo.JoinShopCartVo;
import com.jilian.pinzi.common.vo.LoginVo;
import com.jilian.pinzi.common.vo.LotteryRecordVo;
import com.jilian.pinzi.common.vo.MsgVo;
import com.jilian.pinzi.common.vo.MyOrderListVo;
import com.jilian.pinzi.common.vo.MyRecordVo;
import com.jilian.pinzi.common.vo.MyShippmentVo;
import com.jilian.pinzi.common.vo.OrderStatusVo;
import com.jilian.pinzi.common.vo.PayOrderVo;
import com.jilian.pinzi.common.vo.PerfectInformationVo;
import com.jilian.pinzi.common.vo.PhotoImgVo;
import com.jilian.pinzi.common.vo.RecommendVo;
import com.jilian.pinzi.common.vo.RegisterVo;
import com.jilian.pinzi.common.vo.ResetPwdVo;
import com.jilian.pinzi.common.vo.ReturnCommissionVo;
import com.jilian.pinzi.common.vo.ScoreBuyGoodsVo;
import com.jilian.pinzi.common.vo.SeckillPrefectureVo;
import com.jilian.pinzi.common.vo.ShipperVo;
import com.jilian.pinzi.common.vo.ShopCartVo;
import com.jilian.pinzi.common.vo.SingleFriendCircleVo;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.common.vo.StoreShowVo;
import com.jilian.pinzi.common.vo.UpdatePersonalVo;
import com.jilian.pinzi.common.vo.UpdatePwdVo;
import com.jilian.pinzi.common.vo.UserTypeFriendCircleListVo;
import com.jilian.pinzi.utils.HttpUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public class Api {


    /**
     * 登录接口
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<LoginDto>> login(LoginVo vo) {
        return RequetRetrofit.getInstance().login(vo);
    }

    /**
     * 注册接口
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<RegisterDto>> register(RegisterVo vo) {
        return RequetRetrofit.getInstance().register(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 获取验证码接口
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<String>> sendMsg(SmsVo vo) {
        return RequetRetrofit.getInstance().sendMsg(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 重置密码
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<String>> resetPwd(ResetPwdVo vo) {
        return RequetRetrofit.getInstance().resetPwd(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 完善信息接口
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> perfectInformation(PerfectInformationVo vo) {
        return RequetRetrofit.getInstance().perfectInformation(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 上传单个案件文件
     *
     * @param vo
     * @param files
     * @return
     */
    public static Flowable<BaseDto<String>> photoImg(PhotoImgVo vo, List<File> files, String mediaType) {
        return RequetRetrofit.getInstance().photoImg(HttpUtil.filesToMultipartBody(vo, files, mediaType));
    }

    /**
     * 个人资料
     *
     * @param id
     * @return
     */
    public static Flowable<BaseDto<PersonalDto>> personalMessage(String id) {
        return RequetRetrofit.getInstance().personalMessage(id);
    }

    /**
     * 修改个人资料
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<UpdatePersonInfoDto>> updatePersonalMessage(UpdatePersonalVo vo) {
        return RequetRetrofit.getInstance().updatePersonalMessage(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 修改密码
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> updatePassword(UpdatePwdVo vo) {
        return RequetRetrofit.getInstance().updatePassword(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 添加收获地址
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<String>> addUserAddress(AddAdressVo vo) {
        return RequetRetrofit.getInstance().addUserAddress(vo);
    }

    /**
     * 编辑收获地址
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<String>> editUserAddress(EditAdressVo vo) {
        return RequetRetrofit.getInstance().editUserAddress(vo);
    }

    /**
     * 查询地址列表
     *
     * @param vo
     * @return
     */

    public static Flowable<BaseDto<List<AddressDto>>> getUserAddressList(GetUserAddressListVo vo) {
        return RequetRetrofit.getInstance().getUserAddressList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 删除收货地址
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<String>>> delUserAddress(DeleteAdressVo vo) {
        return RequetRetrofit.getInstance().delUserAddress(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 我的足迹 我的收藏
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<CollectionFootDto>>> FootPrintAndCollect(CollectionFootVo vo) {
        return RequetRetrofit.getInstance().FootPrintAndCollect(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 系统消息/新闻公告
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<MsgDto>>> SystemInformation(MsgVo vo) {
        return RequetRetrofit.getInstance().SystemInformation(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 消息详情/公告详情
     *
     * @param id
     * @param uId
     * @return
     */
    public static Flowable<BaseDto<MsgDto>> InformationDetails(String id, String uId) {
        return RequetRetrofit.getInstance().InformationDetails(id, uId);
    }

    /**
     * 获取新消息 首页消息已读
     *
     * @param
     * @return
     */
    public static Flowable<BaseDto<Integer>> MessageRead(Integer type, String uId) {
        return RequetRetrofit.getInstance().MessageRead(type, uId);
    }

    /**
     * 启动页配置
     *
     * @param type 类型（1.启动页 2.引导页 3.首页广告轮播 4,店铺广告轮播，5精品广告轮播 6，人气广告轮播 7，积分商场轮播图）
     * @return
     */
    public static Flowable<BaseDto<List<StartPageDto>>> StartPage(Integer type) {
        return RequetRetrofit.getInstance().StartPage(type);
    }

    /**
     * 领劵中心
     *
     * @param uId
     * @return
     */
    public static Flowable<BaseDto<List<CouponCentreDto>>> CouponCentre(String uId) {
        return RequetRetrofit.getInstance().CouponCentre(uId);
    }

    /**
     * 领取优惠券
     *
     * @param id
     * @param uId
     * @return
     */
    public static Flowable<BaseDto<String>> GetCoupon(String id, String uId) {
        return RequetRetrofit.getInstance().GetCoupon(id, uId);
    }

    /**
     * 优惠券详情
     *
     * @param
     * @return
     */
    public static Flowable<BaseDto<CouponCentreDto>> CouponDetails(String id) {
        return RequetRetrofit.getInstance().CouponDetails(id);
    }

    /**
     * 用户签到
     *
     * @param
     * @return
     */
    public static Flowable<BaseDto<String>> ClockIn(String uId) {
        return RequetRetrofit.getInstance().ClockIn(uId);
    }

    /**
     * 我的优惠券列表
     *
     * @param
     * @return
     */
    public static Flowable<BaseDto<List<MyCouponDto>>> getMyCouponList(String uId, Integer status, int pageNo, int pageSize) {
        return RequetRetrofit.getInstance().getMyCouponList(uId, status, pageNo, pageSize);
    }

    /**
     * 获取奖品数据
     *
     * @return
     */
    public static Flowable<BaseDto<List<LotteryInfoDto>>> getLotteryInfo() {
        return RequetRetrofit.getInstance().getLotteryInfo();
    }

    /**
     * 判断抽奖积分
     *
     * @param uId
     * @return
     */
    public static Flowable<BaseDto> getLotteryScore(String uId) {
        return RequetRetrofit.getInstance().getLotteryScore(uId);
    }

    /**
     * 添加抽奖结果
     *
     * @param uId
     * @param id
     * @return
     */
    public static Flowable<BaseDto> addLotteryResult(String uId, String id) {
        return RequetRetrofit.getInstance().addLotteryResult(uId, id);
    }

    /**
     * 抽奖结果列表
     *
     * @return
     */
    public static Flowable<BaseDto<List<LotteryRecordDto>>> getLotteryList(LotteryRecordVo vo) {
        return RequetRetrofit.getInstance().getLotteryList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 积分商品或相似推荐商品
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<ScoreBuyGoodsDto>>> getScoreBuyGoods(ScoreBuyGoodsVo vo) {
        return RequetRetrofit.getInstance().getScoreBuyGoods(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 接口名称: 我的积分、钱包、佣金
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<MyRecordDto>>> getMyRecord(MyRecordVo vo) {
        return RequetRetrofit.getInstance().getMyRecord(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 赠送佣金
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> giveCommission(GiveCommissionVo vo) {
        return RequetRetrofit.getInstance().giveCommission(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 提现
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> getWithdrawDeposit(GetWithdrawDepositVo vo) {
        return RequetRetrofit.getInstance().getWithdrawDeposit(vo);
    }

    /**
     * 积分 余额 佣金结算接口
     *
     * @return
     */
    public static Flowable<BaseDto<ScoreOrCommissionDto>> getMyScoreOrCommission(String uId) {
        return RequetRetrofit.getInstance().getMyScoreOrCommission(uId);
    }

    /**
     * 热词列表
     *
     * @return
     */
    public static Flowable<BaseDto<List<HotWordListDto>>> HotWordList(HotWordListVo vo) {
        return RequetRetrofit.getInstance().HotWordList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 首页搜索
     *
     * @return
     */
    public static Flowable<BaseDto<List<HotWordSelectDto>>> HotWordSelect(HotWordListVo vo) {
        return RequetRetrofit.getInstance().HotWordSelect(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 店铺搜索
     *
     * @return
     */
    public static Flowable<BaseDto<List<HotWordSelectBusinessDto>>> HotWordSelectBusiness(HotWordListVo vo) {
        return RequetRetrofit.getInstance().HotWordSelectBusiness(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 购物车
     *
     * @return
     */
    public static Flowable<BaseDto<List<ShopCartLisDto>>> getShopCartList(ShopCartVo vo) {
        return RequetRetrofit.getInstance().getShopCartList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 增加或减少购物车数量
     *
     * @return
     */
    public static Flowable<BaseDto<String>> addOrReduceGoods(AddOrReduceGoodsVo vo) {
        return RequetRetrofit.getInstance().addOrReduceGoods(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 单个删除或全部删除 商品
     *
     * @return
     */
    public static Flowable<BaseDto<String>> deleteGoods(DeleteGoodsVo vo) {
        return RequetRetrofit.getInstance().deleteGoods(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 全部分类
     *
     * @return
     */
    public static Flowable<BaseDto<List<GoodsTypeDto>>> getGoodsType() {
        return RequetRetrofit.getInstance().getGoodsType();
    }


    /**
     * 采购中心商品
     *
     * @return
     */
    public static Flowable<BaseDto<List<BuyerCenterGoodsDto>>> getBuyerCenterGoods(BuyerCenterGoodsVo vo) {
        return RequetRetrofit.getInstance().getBuyerCenterGoods(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 人气推荐 新品推荐 同一个接口
     *
     * @return
     */
    public static Flowable<BaseDto<List<MainRecommendDto>>> Recommend(RecommendVo vo) {
        return RequetRetrofit.getInstance().Recommend(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 返佣金专区
     *
     * @return
     */
    public static Flowable<BaseDto<List<MainRecommendDto>>> ReturnCommission(ReturnCommissionVo vo) {
        return RequetRetrofit.getInstance().ReturnCommission(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 秒杀专区
     *
     * @return
     */
    public static Flowable<BaseDto<List<SeckillPrefectureDto>>> SeckillPrefecture(SeckillPrefectureVo vo) {
        return RequetRetrofit.getInstance().SeckillPrefecture(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 店铺展示
     *
     * @return
     */
    public static Flowable<BaseDto<List<StoreShowDto>>> StoreShow(StoreShowVo vo) {
        return RequetRetrofit.getInstance().StoreShow(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 我的发货列表
     *
     * @return
     */
    public static Flowable<BaseDto<List<ShippmentDto>>> getMyShippmentList(MyShippmentVo vo) {
        return RequetRetrofit.getInstance().getMyShippmentList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 发货
     *
     * @return
     */
    public static Flowable<BaseDto<String>> deliverGoods(DeliverGoodsVo vo) {
        return RequetRetrofit.getInstance().deliverGoods(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 快递公司
     *
     * @return
     */
    public static Flowable<BaseDto<List<DeliveryMethodDto>>> getDeliveryMethodList() {
        return RequetRetrofit.getInstance().getDeliveryMethodList();
    }


    /**
     * 商品详情
     *
     * @return
     */
    public static Flowable<BaseDto<GoodsDetailDto>> getGoodsDetail(GoodsDetailVo vo) {
        return RequetRetrofit.getInstance().getGoodsDetail(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 商品评价
     *
     * @return
     */
    public static Flowable<BaseDto<GoodsEvaluateDto>> getGoodsEvaluate(GoodsEvaluateVo vo) {
        return RequetRetrofit.getInstance().getGoodsEvaluate(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 加入购物车
     *
     * @return
     */
    public static Flowable<BaseDto<String>> joinShopCart(JoinShopCartVo vo) {
        return RequetRetrofit.getInstance().joinShopCart(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 收藏商品或店铺
     *
     * @return
     */
    public static Flowable<BaseDto<String>> collectGoodsOrStore(CollectGoodsOrStoreVo vo) {
        return RequetRetrofit.getInstance().collectGoodsOrStore(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 取消收藏
     *
     * @return
     */
    public static Flowable<BaseDto<String>> cancelCollect(CancelCollectVo vo) {
        return RequetRetrofit.getInstance().cancelCollect(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 选择优惠券
     *
     * @return
     */
    public static Flowable<BaseDto<DiscountConpouDto>> getDiscountConpou(DiscountConpouVo vo) {
        return RequetRetrofit.getInstance().getDiscountConpou(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 选择发货人
     *
     * @return
     */
    public static Flowable<BaseDto<List<ShipperDto>>> getShipperList(ShipperVo vo) {
        return RequetRetrofit.getInstance().getShipperList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 判断是否有秒杀商品
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<GoodsIsSecondCheckDto>> getGoodsIsSecondCheck(GoodsIsSecondCheckVo vo) {
        return RequetRetrofit.getInstance().getGoodsIsSecondCheck(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 开发票
     * post
     * json 提交
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> addInvoice(InvoiceVo vo) {
        return RequetRetrofit.getInstance().addInvoice(vo);
    }

    /**
     * 提交订单
     * post
     * json  提交订单
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<AddOrderDto>> addOrder(@Body AddOrderVo vo) {
        return RequetRetrofit.getInstance().addOrder(vo);
    }

    /**
     * 积分兑换商品提交订单
     * post
     * json  积分兑换商品提交订单
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<GoodByScoreDto>> buyGoodsByScore(GoodsByScoreVo vo) {
        return RequetRetrofit.getInstance().buyGoodsByScore(vo);
    }

    /**
     * 我的订单列表
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<MyOrderDto>>> getMyOrderList(MyOrderListVo vo) {
        return RequetRetrofit.getInstance().getMyOrderList(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 取消订单、确认收货、删除订单
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> updateOrderStatus(OrderStatusVo vo) {
        return RequetRetrofit.getInstance().updateOrderStatus(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 获取运费
     *
     * @param goodsIds
     * @return
     */
    public static Flowable<BaseDto<String>> getFreight(String goodsIds) {
        return RequetRetrofit.getInstance().getFreight(goodsIds);
    }

    /**
     * 计算优惠券、积分、佣金抵扣金额
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<DiscountMoneyDto>> getDiscountMoney(DiscountMoneyVo vo) {
        return RequetRetrofit.getInstance().getDiscountMoney(vo);
    }


    /**
     * 支付
     *
     * @param
     * @return
     */
    public static Flowable<BaseDto<String>> payOrder(PayOrderVo vo) {
        return RequetRetrofit.getInstance().payOrder(vo);
    }

    /**
     * 我的上级 、下级、下二级、下三级
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<InviteListDto>>> getInviteList(InviteListVo vo) {
        return RequetRetrofit.getInstance().getInviteList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 我的下级详情
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<InviteeDetailDto>> getInviteeDetail(InviteeDetailVo vo) {
        return RequetRetrofit.getInstance().getInviteeDetail(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 添加上级
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> addMySuperior(AddMySuperiorVo vo) {
        return RequetRetrofit.getInstance().addMySuperior(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    public static Flowable<BaseDto<OrderDetailDto>> getOrderDetail(String orderId) {
        return RequetRetrofit.getInstance().getOrderDetail(orderId);
    }

    /**
     * 会员中心
     *
     * @param type
     * @param id
     * @return
     */
    public static Flowable<BaseDto<MemberDto>> MemberCenter(Integer type, String id) {
        return RequetRetrofit.getInstance().MemberCenter(type, id);
    }


    /**
     * 获取商铺详情
     *
     * @param storeId
     * @param uId
     * @return
     */
    public static Flowable<BaseDto<ShopDetailDto>> getStoreDetail(String storeId, String uId) {
        return RequetRetrofit.getInstance().getStoreDetail(storeId, uId);
    }

    /**
     * 商铺商品
     *
     * @param identity
     * @param storeId
     * @param goodsName
     * @return
     */
    public static Flowable<BaseDto<List<ShopGoodsDto>>> getStoreGoods(int identity, String storeId, String goodsName, String entrance) {
        return RequetRetrofit.getInstance().getStoreGoods(identity, storeId, goodsName, entrance);
    }

    /**
     * 评价
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> addEvaluate(CommentGoodVo vo) {
        return RequetRetrofit.getInstance().addEvaluate(vo);
    }

    /**
     * 订单跟踪
     *
     * @param orderId
     * @return
     */
    public static Flowable<BaseDto<OrderTrackDto>> getOrderOfLogistics(String orderId) {
        return RequetRetrofit.getInstance().getOrderOfLogistics(orderId);
    }

    /**
     * 退货原因列表
     *
     * @return
     */
    public static Flowable<BaseDto<List<RefundReasonDto>>> getRefundReason() {
        return RequetRetrofit.getInstance().getRefundReason();
    }

    /**
     * 申请退货
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> applyRefund(ApplyRefundVo vo) {
        return RequetRetrofit.getInstance().applyRefund(vo);
    }

    /**
     * 申请记录
     *
     * @return
     */
    public static Flowable<BaseDto<List<SaleRecordDto>>> getApplyRefundList(String uId, int pageNo, int pageSize) {
        return RequetRetrofit.getInstance().getApplyRefundList(uId, pageNo, pageSize);
    }

    /**
     * 查看评价
     *
     * @param orderId
     * @return
     */
    public static Flowable<BaseDto<EvaluateDetailDto>> getEvaluateDetail(String orderId) {
        return RequetRetrofit.getInstance().getEvaluateDetail(orderId);
    }

    /**
     * 记录详情
     *
     * @param aId
     * @return
     */
    public static Flowable<BaseDto<SaleRecordDetailDto>> getSaleRecordDetail(String aId) {
        return RequetRetrofit.getInstance().getSaleRecordDetail(aId);
    }

    /**
     * 删除足迹
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> delUserFootprint(DeleteFootVo vo) {
        return RequetRetrofit.getInstance().delUserFootprint(HttpUtil.convertVo2Params(vo));
    }

    /**
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<EmptyDto>> addCourierInfo(AddCourierInfoVo vo) {
        return RequetRetrofit.getInstance().addCourierInfo(vo);
    }

    /**
     * 新增点击
     *
     * @param id
     * @return
     */
    public static Flowable<BaseDto<String>> ClickByPageId(String id) {
        return FriendRequetRetrofit.getInstance().ClickByPageId(id);
    }

    /**
     * 获取朋友列表
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<FriendCircleDto>>> FriendCircleList(FriendCircleListVo vo) {
        return FriendRequetRetrofit.getInstance().FriendCircleList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 朋友圈删除
     *
     * @param id
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleDelete(String id) {
        return FriendRequetRetrofit.getInstance().FriendCircleDelete(id);
    }


    /**
     * 朋友圈评论
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleComment(FriendCircleCommentVo vo) {
        return FriendRequetRetrofit.getInstance().FriendCircleComment(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 朋友圈评论删除
     *
     * @param id 评论ID
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleCommentDelete(String id) {
        return FriendRequetRetrofit.getInstance().FriendCircleCommentDelete(id);
    }

    /**
     * 朋友圈点赞
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleLike(FriendCircleLikeVo vo) {
        return FriendRequetRetrofit.getInstance().FriendCircleLike(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 朋友圈取消点赞
     *
     * @param id 点赞ID
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleCancelLike(String id) {
        return FriendRequetRetrofit.getInstance().FriendCircleCancelLike(id);
    }

    /**
     * 朋友圈评论回复
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> CommentReplyAdd(CommentReplyAddVo vo) {
        return FriendRequetRetrofit.getInstance().CommentReplyAdd(HttpUtil.convertVo2Params(vo));
    }


    /**
     * 发布朋友圈
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<String>> FriendCircleIssue(FriendCircleIssueVo vo) {
        return FriendRequetRetrofit.getInstance().FriendCircleIssue(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 刷新单条朋友圈
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<FriendCircleListDetailDto>>> SingleFriendCircle(SingleFriendCircleVo vo) {
        return FriendRequetRetrofit.getInstance().SingleFriendCircle(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 我的朋友圈列表
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<FriendCircleDto>>> MyFriendCircleList(FriendCircleListVo vo) {
        return FriendRequetRetrofit.getInstance().MyFriendCircleList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 用户类型朋友圈列表
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<FriendCircleDto>>> UserTypeFriendCircleList(UserTypeFriendCircleListVo vo) {
        return FriendRequetRetrofit.getInstance().UserTypeFriendCircleList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 积分 抽奖说明
     *
     * @return
     */
    public static Flowable<BaseDto<MyTntegralDetailDto>> getContent() {
        return RequetRetrofit.getInstance().getContent();
    }

    /**
     * 资讯分类
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<InformationtTypeDto>>> getInformationTypeList(InformationVo vo) {
        return FriendRequetRetrofit.getInstance().getInformationTypeList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 资讯列表
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<List<InformationtDto>>> getInformationList(InformationVo vo) {
        return FriendRequetRetrofit.getInstance().getInformationList(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 咨询详情
     *
     * @param vo
     * @return
     */
    public static Flowable<BaseDto<InformationtDetailDto>> getInformationDetail(InformationVo vo) {
        return FriendRequetRetrofit.getInstance().getInformationDetail(HttpUtil.convertVo2Params(vo));
    }

    /**
     * 评论资讯
     * @param vo
     * @return
     */
    public static Flowable<BaseDto> commentInformation(CommentInformationVo vo) {
        return FriendRequetRetrofit.getInstance().commentInformation(HttpUtil.convertVo2Params(vo));
    }
}
