package com.jilian.pinzi.ui.my.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
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
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.my.repository.MyRepository;

import java.util.List;

public class MyRepositoryImpl extends CommonRepository implements MyRepository {
    @Override
    public LiveData<BaseDto<PersonalDto>> personalMessage(String id) {
        return request(Api.personalMessage(id)).send().get();
    }

    /**
     * 修改个人资料
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<UpdatePersonInfoDto>> updatePersonalMessage(UpdatePersonalVo vo) {
        return request(Api.updatePersonalMessage(vo)).send().get();
    }

    /**
     * 修改密码
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> updatePassword(UpdatePwdVo vo) {
        return request(Api.updatePassword(vo)).send().get();
    }

    /**
     * 添加收获地址
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> addUserAddress(AddAdressVo vo) {
        return request(Api.addUserAddress(vo)).send().get();
    }

    /**
     * 编辑收获地址
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> editUserAddress(EditAdressVo vo) {
        return request(Api.editUserAddress(vo)).send().get();
    }

    /**
     * 获取地址列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<AddressDto>>> getUserAddressList(GetUserAddressListVo vo) {
        return request(Api.getUserAddressList(vo)).send().get();
    }

    /**
     * 删除地址
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> deleteAddress(DeleteAdressVo vo) {
        return request(Api.delUserAddress(vo)).send().get();
    }

    /**
     * 查询我的足迹 我的收藏
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<CollectionFootDto>>> FootPrintAndCollect(CollectionFootVo vo) {
        return request(Api.FootPrintAndCollect(vo)).send().get();
    }

    /**
     * 我的优惠券列表
     *
     * @param
     * @return
     */
    @Override
    public LiveData<BaseDto<List<MyCouponDto>>> getMyCouponList(String uId, Integer status, int pageNo, int pageSize) {
        return request(Api.getMyCouponList(uId, status, pageNo, pageSize)).send().get();
    }

    /**
     * 赠送佣金
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> giveCommission(GiveCommissionVo vo) {
        return request(Api.giveCommission(vo)).send().get();
    }

    /**
     * 提现
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> getWithdrawDeposit(GetWithdrawDepositVo vo) {
        return request(Api.getWithdrawDeposit(vo)).send().get();
    }

    /**
     * 结算接口
     *
     * @param uId
     * @return
     */
    @Override
    public LiveData<BaseDto<ScoreOrCommissionDto>> getMyScoreOrCommission(String uId) {
        return request(Api.getMyScoreOrCommission(uId)).send().get();
    }

    /**
     * 我的发货列表
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<ShippmentDto>>> getMyShippmentList(MyShippmentVo vo) {
        return request(Api.getMyShippmentList(vo)).send().get();
    }

    /**
     * 发货
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> deliverGoods(DeliverGoodsVo vo) {
        return request(Api.deliverGoods(vo)).send().get();
    }

    /**
     * 快递公司
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<DeliveryMethodDto>>> getDeliveryMethodList() {
        return request(Api.getDeliveryMethodList()).send().get();
    }

    /**
     * 我的订单列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<MyOrderDto>>> getMyOrderList(MyOrderListVo vo) {
        return request(Api.getMyOrderList(vo)).send().get();
    }

    /**
     * 取消订单、确认收货、删除订单
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> updateOrderStatus(OrderStatusVo vo) {
        return request(Api.updateOrderStatus(vo)).send().get();
    }

    /**
     * 支付
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> payOrder(PayOrderVo vo) {
        return request(Api.payOrder(vo)).send().get();
    }

    /**
     * 我的上级 、下级、下二级、下三级
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<InviteListDto>>> getInviteList(InviteListVo vo) {
        return request(Api.getInviteList(vo)).send().get();
    }

    /**
     * 我的下级详情
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<InviteeDetailDto>> getInviteeDetail(InviteeDetailVo vo) {
        return request(Api.getInviteeDetail(vo)).send().get();
    }

    /**
     * 添加上级
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> addMySuperior(AddMySuperiorVo vo) {
        return request(Api.addMySuperior(vo)).send().get();
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public LiveData<BaseDto<OrderDetailDto>> getOrderDetail(String orderId) {
        return request(Api.getOrderDetail(orderId)).send().get();
    }

    /**
     * 会员中心
     *
     * @param type
     * @param id
     * @return
     */
    @Override
    public LiveData<BaseDto<MemberDto>> MemberCenter(Integer type, String id) {
        return request(Api.MemberCenter(type, id)).send().get();
    }

    /**
     * 评价
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> addEvaluate(CommentGoodVo vo) {
        return request(Api.addEvaluate(vo)).send().get();
    }

    /**
     * 退货原因列表
     *
     * @return
     */
    @Override
    public LiveData<BaseDto<List<RefundReasonDto>>> getRefundReason() {
        return request(Api.getRefundReason()).send().get();
    }

    /**
     * 申请退货
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> applyRefund(ApplyRefundVo vo) {
        return request(Api.applyRefund(vo)).send().get();
    }

    /**
     * 查看评价
     *
     * @param orderId
     * @return
     */
    @Override
    public LiveData<BaseDto<EvaluateDetailDto>> getEvaluateDetail(String orderId) {
        return request(Api.getEvaluateDetail(orderId)).send().get();
    }
    /**
     * 删除足迹
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> delUserFootprint(DeleteFootVo vo) {
        return request(Api.delUserFootprint(vo)).send().get();
    }
    /**
     * 积分 抽奖说明
     * @return
     */
    @Override
    public LiveData<BaseDto<MyTntegralDetailDto>> getContent() {
        return request(Api.getContent()).send().get();
    }

    /**
     * 申请退货
     * @return
     */
    @Override
    public LiveData<BaseDto<List<SaleRecordDto>>> getApplyRefundList(String uId, int pageNo, int pageSize) {
        return request(Api.getApplyRefundList(uId, pageNo, pageSize)).send().get();
    }

    /**
     * 记录详情
     * @return
     */
    @Override
    public LiveData<BaseDto<SaleRecordDetailDto>> getSaleRecordDetail(String aId) {
        return request(Api.getSaleRecordDetail(aId)).send().get();
    }

    @Override
    public LiveData<BaseDto<EmptyDto>> addCourierInfo(AddCourierInfoVo vo) {
        return request(Api.addCourierInfo(vo)).send().get();
    }
}
