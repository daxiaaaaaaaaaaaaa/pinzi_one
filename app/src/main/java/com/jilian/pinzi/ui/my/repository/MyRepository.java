package com.jilian.pinzi.ui.my.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;
import com.jilian.pinzi.common.dto.EmptyDto;
import com.jilian.pinzi.common.dto.EvaluateDetailDto;
import com.jilian.pinzi.common.dto.InviteListDto;
import com.jilian.pinzi.common.dto.InviteeDetailDto;
import com.jilian.pinzi.common.dto.MemberDto;
import com.jilian.pinzi.common.dto.MyCouponDto;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyTntegralDetailDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.common.dto.PersonalDto;
import com.jilian.pinzi.common.dto.RefundReasonDto;
import com.jilian.pinzi.common.dto.SaleRecordDetailDto;
import com.jilian.pinzi.common.dto.SaleRecordDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.common.dto.ShippmentDto;
import com.jilian.pinzi.common.dto.UpdatePersonInfoDto;
import com.jilian.pinzi.common.vo.AddAdressVo;
import com.jilian.pinzi.common.vo.AddCourierInfoVo;
import com.jilian.pinzi.common.vo.AddMySuperiorVo;
import com.jilian.pinzi.common.vo.ApplyRefundVo;
import com.jilian.pinzi.common.vo.CollectionFootVo;
import com.jilian.pinzi.common.vo.CommentGoodVo;
import com.jilian.pinzi.common.vo.DeleteAdressVo;
import com.jilian.pinzi.common.vo.DeleteFootVo;
import com.jilian.pinzi.common.vo.DeliverGoodsVo;
import com.jilian.pinzi.common.vo.EditAdressVo;
import com.jilian.pinzi.common.vo.GetUserAddressListVo;
import com.jilian.pinzi.common.vo.GetWithdrawDepositVo;
import com.jilian.pinzi.common.vo.GiveCommissionVo;
import com.jilian.pinzi.common.vo.InviteListVo;
import com.jilian.pinzi.common.vo.InviteeDetailVo;
import com.jilian.pinzi.common.vo.MyOrderListVo;
import com.jilian.pinzi.common.vo.MyShippmentVo;
import com.jilian.pinzi.common.vo.OrderStatusVo;
import com.jilian.pinzi.common.vo.PayOrderVo;
import com.jilian.pinzi.common.vo.UpdatePersonalVo;
import com.jilian.pinzi.common.vo.UpdatePwdVo;

import java.util.List;


public interface MyRepository {
    /**
     * 查询个人信息
     *
     * @param id
     * @return
     */
    LiveData<BaseDto<PersonalDto>> personalMessage(String id);

    /**
     * 修改个人资料
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<UpdatePersonInfoDto>> updatePersonalMessage(UpdatePersonalVo vo);

    /**
     * 修改密码
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> updatePassword(UpdatePwdVo vo);

    /**
     * 添加收获地址
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> addUserAddress(AddAdressVo vo);

    /**
     * 编辑收获地址
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> editUserAddress(EditAdressVo vo);

    /**
     * 获取地址列表
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<AddressDto>>> getUserAddressList(GetUserAddressListVo vo);

    /**
     * 删除收货地址
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> deleteAddress(DeleteAdressVo vo);

    /**
     * 查询我的足迹 我的收藏
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<CollectionFootDto>>> FootPrintAndCollect(CollectionFootVo vo);

    /**
     * 我的优惠券列表
     *
     * @param
     * @return
     */
    LiveData<BaseDto<List<MyCouponDto>>> getMyCouponList(String uId, Integer status, int pageNo, int pageSize);

    /**
     * 赠送佣金
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> giveCommission(GiveCommissionVo vo);


    /**
     * 提现
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> getWithdrawDeposit(GetWithdrawDepositVo vo);

    /**
     * 结算接口
     *
     * @param uId
     * @return
     */
    LiveData<BaseDto<ScoreOrCommissionDto>> getMyScoreOrCommission(String uId);


    /**
     * 我的发货列表
     *
     * @return
     */
    LiveData<BaseDto<List<ShippmentDto>>> getMyShippmentList(MyShippmentVo vo);

    /**
     * 发货
     *
     * @return
     */
    LiveData<BaseDto<String>> deliverGoods(DeliverGoodsVo vo);


    /**
     * 快递公司
     *
     * @return
     */
    LiveData<BaseDto<List<DeliveryMethodDto>>> getDeliveryMethodList();


    /**
     * 我的订单列表
     * @param vo
     * @return
     */
    LiveData <BaseDto<List<MyOrderDto>>>getMyOrderList(MyOrderListVo vo);

    /**
     *取消订单、确认收货、删除订单
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>> updateOrderStatus(OrderStatusVo vo);


    /**
     * 支付
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>> payOrder(PayOrderVo vo);

    /**
     * 我的上级 、下级、下二级、下三级
     * @param vo
     * @return
     */
    LiveData <BaseDto<List<InviteListDto>>> getInviteList(InviteListVo vo);

    /**
     * 我的下级详情
     * @param vo
     * @return
     */
    LiveData <BaseDto<InviteeDetailDto>> getInviteeDetail(InviteeDetailVo vo);

    /**
     *  添加上级
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>> addMySuperior(AddMySuperiorVo vo);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    LiveData <BaseDto<OrderDetailDto>> getOrderDetail(String orderId);

    /**
     *会员中心
     * @param type
     * @param id
     * @return
     */
    LiveData <BaseDto<MemberDto>> MemberCenter(Integer type,  String id);


    /**
     * 评价
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>> addEvaluate(CommentGoodVo vo);

    /**
     * 退货原因列表
     * @return
     */
    LiveData <BaseDto<List<RefundReasonDto>>>  getRefundReason();

    /**
     * 申请退货
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>>  applyRefund(ApplyRefundVo vo);
    /**
     * 申请退货
     * @return
     */
    LiveData<BaseDto<List<SaleRecordDto>>> getApplyRefundList(String uId, int pageNo, int pageSize);

    /**
     * 记录详情
     * @param aId
     * @return
     */
    LiveData<BaseDto<SaleRecordDetailDto>> getSaleRecordDetail(String aId);

    /**
     * 添加物流地址
     * @param id
     * @param courierName
     * @param courierNo
     * @return
     */
    LiveData<BaseDto<EmptyDto>> addCourierInfo(AddCourierInfoVo vo);


    /**
     * 查看评价
     * @param orderId
     * @return
     */
    LiveData <BaseDto<EvaluateDetailDto>>  getEvaluateDetail(String orderId);


    /**
     * 删除足迹
     * @param vo
     * @return
     */
    LiveData <BaseDto<String>>  delUserFootprint(DeleteFootVo vo);

    /**
     * 积分说明 抽奖说明
     * @param
     * @return
     */
    LiveData <BaseDto<MyTntegralDetailDto>>  getContent();

}
