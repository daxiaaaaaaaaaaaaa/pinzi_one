package com.jilian.pinzi.ui.my.viewmdel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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
import com.jilian.pinzi.common.dto.VersionInfoDto;
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
import com.jilian.pinzi.ui.my.repository.MyRepository;
import com.jilian.pinzi.ui.my.repository.impl.MyRepositoryImpl;

import java.util.List;

public class MyViewModel extends ViewModel {
    private LiveData<BaseDto<PersonalDto>> personalliveData;//个人信息

    private LiveData<BaseDto<UpdatePersonInfoDto>> updatePersonalliveData;//修改个人信息

    private LiveData<BaseDto<String>> updatePasswordliveData;//修改密码

    private LiveData<BaseDto<String>> addressliveData;//修改收获地址 删除地址

    private LiveData<BaseDto<List<AddressDto>>> addressListliveData;//地址列表

    private LiveData<BaseDto<List<CollectionFootDto>>> collectionFootListliveData;//我的收藏 我的足迹

    private LiveData<BaseDto<List<MyCouponDto>>> myCouponListliveData;//我的优惠券

    private LiveData<BaseDto<String>> commissionnListliveData;//赠送佣金


    private LiveData<BaseDto<String>> withdrawDepositListliveData;//提现

    private LiveData<BaseDto<ScoreOrCommissionDto>> acountLiveData;//结算接口


    private LiveData<BaseDto<String>> deliverGoodsliveData;//发货


    private LiveData<BaseDto<List<ShippmentDto>>> ShippmentListliveData;//发货列表


    private LiveData<BaseDto<List<DeliveryMethodDto>>> deliveryMethodliveData;//快递公司


    private LiveData<BaseDto<List<MyOrderDto>>> orderliveData;//订单列表

    private LiveData<BaseDto<String>> orderStatusliveData;//订单状态更新

    private LiveData<BaseDto<String>> pay;//支付

    private LiveData<BaseDto<List<InviteListDto>>> inviteList;//我的上级 、下级、下二级、下三级

    private LiveData<BaseDto<InviteeDetailDto>> inviteeDetail;//我的下级详情

    private LiveData<BaseDto<String>> addMysuper;//添加上级

    private LiveData<BaseDto<OrderDetailDto>> orderDetail;//订单详情

    private LiveData<BaseDto<MemberDto>> member;//会员中心

    private LiveData<BaseDto<String>> addEvaluate;//评价

    private LiveData<BaseDto<List<RefundReasonDto>>> refundReasonliveData;//退货原因列表

    private LiveData<BaseDto<String>> applyRefundLiveData;//申请退货

    private LiveData<BaseDto<List<SaleRecordDto>>> saleRecordLiveData; // 申请记录

    private LiveData<BaseDto<SaleRecordDetailDto>> saleRecordDetailLiveData; // 记录详情

    private LiveData<BaseDto<EmptyDto>> addCourierInfoLiveData; // 添加物流地址


    private LiveData<BaseDto<MyTntegralDetailDto>> myTntegralDetailDtoLiveData; // 积分说明


    private LiveData<BaseDto<VersionInfoDto>> updateLiveData; // 更新

    public LiveData<BaseDto<VersionInfoDto>> getUpdateLiveData() {
        return updateLiveData;
    }

    public LiveData<BaseDto<MyTntegralDetailDto>> getMyTntegralDetailDtoLiveData() {
        return myTntegralDetailDtoLiveData;
    }

    public LiveData<BaseDto<EmptyDto>> getAddCourierInfoLiveData() {
        return addCourierInfoLiveData;
    }

    public LiveData<BaseDto<SaleRecordDetailDto>> getSaleRecordDetailLiveData() {
        return saleRecordDetailLiveData;
    }

    public LiveData<BaseDto<List<SaleRecordDto>>> getSaleRecordLiveData() {
        return saleRecordLiveData;
    }

    private LiveData<BaseDto<EvaluateDetailDto>> evaluateDetailLiveData;//查看评价

    private LiveData<BaseDto<String>> delUserFoot;//删除足迹

    public LiveData<BaseDto<String>> getDelUserFoot() {
        return delUserFoot;
    }

    public LiveData<BaseDto<EvaluateDetailDto>> getEvaluateDetailLiveData() {
        return evaluateDetailLiveData;
    }

    public LiveData<BaseDto<String>> getApplyRefundLiveData() {
        return applyRefundLiveData;
    }

    public LiveData<BaseDto<List<RefundReasonDto>>> getRefundReasonliveData() {
        return refundReasonliveData;
    }

    public LiveData<BaseDto<String>> getAddEvaluate() {
        return addEvaluate;
    }

    public LiveData<BaseDto<MemberDto>> getMember() {
        return member;
    }

    public LiveData<BaseDto<OrderDetailDto>> getOrderDetail() {
        return orderDetail;
    }

    public LiveData<BaseDto<String>> getAddMysuper() {
        return addMysuper;
    }

    public LiveData<BaseDto<InviteeDetailDto>> getInviteeDetail() {
        return inviteeDetail;
    }

    public LiveData<BaseDto<List<InviteListDto>>> getInviteList() {
        return inviteList;
    }

    public LiveData<BaseDto<String>> getPay() {
        return pay;
    }

    public LiveData<BaseDto<String>> getOrderStatusliveData() {
        return orderStatusliveData;
    }

    public LiveData<BaseDto<List<MyOrderDto>>> getOrderliveData() {
        return orderliveData;
    }

    public LiveData<BaseDto<List<DeliveryMethodDto>>> getDeliveryMethodliveData() {
        return deliveryMethodliveData;
    }

    public LiveData<BaseDto<String>> getDeliverGoodsliveData() {
        return deliverGoodsliveData;
    }

    public LiveData<BaseDto<List<ShippmentDto>>> getShippmentListliveData() {
        return ShippmentListliveData;
    }

    public LiveData<BaseDto<ScoreOrCommissionDto>> getAcountLiveData() {
        return acountLiveData;
    }

    public LiveData<BaseDto<String>> getWithdrawDepositListliveData() {
        return withdrawDepositListliveData;
    }

    public LiveData<BaseDto<String>> getCommissionnListliveData() {
        return commissionnListliveData;
    }

    public LiveData<BaseDto<List<MyCouponDto>>> getMyCouponListliveData() {
        return myCouponListliveData;
    }

    public MyRepository getRepository() {
        return repository;
    }

    public LiveData<BaseDto<List<CollectionFootDto>>> getCollectionFootListliveData() {
        return collectionFootListliveData;
    }

    public LiveData<BaseDto<String>> getAddressliveData() {
        return addressliveData;
    }

    public LiveData<BaseDto<List<AddressDto>>> getAddressListliveData() {
        return addressListliveData;
    }

    public LiveData<BaseDto<String>> getAddUserAddressliveData() {
        return addressliveData;
    }

    public LiveData<BaseDto<String>> getUpdatePasswordliveData() {
        return updatePasswordliveData;
    }

    public LiveData<BaseDto<UpdatePersonInfoDto>> getUpdatePersonalliveData() {
        return updatePersonalliveData;
    }


    private MyRepository repository;

    public LiveData<BaseDto<PersonalDto>> getPersonalliveData() {
        return personalliveData;
    }

    /**
     * 个人资料查询
     *
     * @param id
     */
    public void queryMyInfo(String id) {
        repository = new MyRepositoryImpl();
        personalliveData = repository.personalMessage(id);
    }

    /**
     * 个人资料查询
     *
     * @param id
     */
    public void updatePersonalMessage(String headImg, String name, Integer sex, String profession, String province, String city, String id, String birthday, String area) {
        repository = new MyRepositoryImpl();
        UpdatePersonalVo vo = new UpdatePersonalVo();
        vo.setHeadImg(headImg);
        vo.setName(name);
        vo.setSex(sex);
        vo.setProfession(profession);
        vo.setProvince(province);
        vo.setCity(city);
        vo.setId(id);
        vo.setBirthday(birthday);
        vo.setArea(area);
        updatePersonalliveData = repository.updatePersonalMessage(vo);
    }

    /**
     * 个人密码
     *
     * @param pastPassword
     * @param newPassword
     * @param id
     */
    public void updatePassword(String pastPassword, String newPassword, String id) {
        UpdatePwdVo vo = new UpdatePwdVo();
        vo.setId(id);
        vo.setNewPassword(newPassword);
        vo.setPastPassword(pastPassword);
        repository = new MyRepositoryImpl();
        updatePasswordliveData = repository.updatePassword(vo);
    }

    /**
     * 添加收获地址
     * 编辑收货人
     *
     * @param name
     * @param phone
     * @param postalCode
     * @param area
     * @param address
     * @param isDefault
     * @param uId
     */
    public void addUserAddress(String name, String phone, String postalCode, String area, String address, Integer isDefault, String uId) {
        AddAdressVo vo = new AddAdressVo();
        vo.setName(name);
        vo.setPhone(phone);
        vo.setPostalCode(postalCode);
        vo.setArea(area);
        vo.setAddress(address);
        vo.setIsDefault(isDefault);
        vo.setuId(uId);
        repository = new MyRepositoryImpl();
        addressliveData = repository.addUserAddress(vo);
    }

    /**
     * @param name
     * @param phone
     * @param postalCode
     * @param area
     * @param address
     * @param isDefault
     * @param uId
     * @param id
     */
    public void editUserAddress(String name, String phone, String postalCode, String area, String address, Integer isDefault, String uId, String id) {
        EditAdressVo vo = new EditAdressVo();
        vo.setName(name);
        vo.setPhone(phone);
        vo.setPostalCode(postalCode);
        vo.setArea(area);
        vo.setAddress(address);
        vo.setIsDefault(isDefault);
        vo.setuId(uId);
        vo.setId(id);
        repository = new MyRepositoryImpl();
        addressliveData = repository.editUserAddress(vo);
    }

    /**
     * 获取地址列表
     *
     * @param pageNo
     * @param pageSize
     * @param uId
     */
    public void getUserAddressList(int pageNo, int pageSize, String uId) {
        GetUserAddressListVo vo = new GetUserAddressListVo();
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        repository = new MyRepositoryImpl();
        addressListliveData = repository.getUserAddressList(vo);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void deleteUserAddress(String id) {
        DeleteAdressVo vo = new DeleteAdressVo();
        vo.setId(id);
        repository = new MyRepositoryImpl();
        addressliveData = repository.deleteAddress(vo);
    }

    /**
     * @param uId      用户IOD
     * @param type     （1.收藏商品 2.收藏店铺）
     * @param classify 1.收藏 2.足迹
     * @param startNum 当前页数
     * @param pageSize 每页条数
     */
    public void FootPrintAndCollect(String uId, String type, String classify, int startNum, int pageSize) {
        CollectionFootVo vo = new CollectionFootVo();
        vo.setuId(uId);
        vo.setType(type);
        vo.setClassify(classify);
        vo.setStartNum(startNum);
        vo.setPageSize(pageSize);
        repository = new MyRepositoryImpl();
        collectionFootListliveData = repository.FootPrintAndCollect(vo);
    }

    /**
     * 获取我的优惠券
     *
     * @param uId
     * @param status   0.未使用 1.已使用 2.已过期
     * @param pageNo
     * @param pageSize
     */
    public void getMyCouponList(String uId, Integer status, int pageNo, int pageSize) {
        repository = new MyRepositoryImpl();
        myCouponListliveData = repository.getMyCouponList(uId, status, pageNo, pageSize);
    }


    /**
     * 用户id
     *
     * @param uId
     * @param phone      电话
     * @param commission 赠送数量
     */
    public void giveCommission(String uId, String phone, String commission) {
        GiveCommissionVo vo = new GiveCommissionVo();
        vo.setuId(uId);
        vo.setPhone(phone);
        vo.setCommission(commission);
        repository = new MyRepositoryImpl();
        commissionnListliveData = repository.giveCommission(vo);
    }


    /**
     * @param uId
     * @param money
     * @param accountName
     * @param account
     * @param type
     */
    public void getWithdrawDeposit(String uId, String money, String accountName, String account, Integer type) {
        GetWithdrawDepositVo vo = new GetWithdrawDepositVo();
        vo.setuId(uId);
        vo.setMoney(money);
        vo.setAccountName(accountName);
        vo.setAccount(account);
        vo.setType(type);
        repository = new MyRepositoryImpl();
        withdrawDepositListliveData = repository.getWithdrawDeposit(vo);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void getMyScoreOrCommission(String id) {
        repository = new MyRepositoryImpl();
        acountLiveData = repository.getMyScoreOrCommission(id);
    }

    /**
     * 我的发货列表
     *
     * @param uId
     * @param pageNo
     * @param pageSize
     */
    public void getMyShippmentList(String uId, int pageNo, int pageSize) {
        repository = new MyRepositoryImpl();
        MyShippmentVo vo = new MyShippmentVo();
        vo.setuId(uId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        ShippmentListliveData = repository.getMyShippmentList(vo);
    }

    /**
     * 发货
     *
     * @param orderId
     * @param courierNumber  快递单号
     * @param courierCompany 快递公司ID
     */
    public void deliverGoods(String orderId, String courierNumber, String courierCompany) {
        repository = new MyRepositoryImpl();
        DeliverGoodsVo vo = new DeliverGoodsVo();
        vo.setOrderId(orderId);
        vo.setCourierNumber(courierNumber);
        vo.setCourierCompany(courierCompany);
        deliverGoodsliveData = repository.deliverGoods(vo);
    }


    /**
     * 快递公司
     */
    public void getDeliveryMethodList() {
        repository = new MyRepositoryImpl();
        deliveryMethodliveData = repository.getDeliveryMethodList();
    }

    /**
     * 获取我的订单列表
     *
     * @param uId
     * @param payStatus
     * @param pageNo
     * @param paegSize
     */
    public void getMyOrderList(String uId, Integer payStatus, Integer pageNo, Integer paegSize) {
        repository = new MyRepositoryImpl();
        MyOrderListVo vo = new MyOrderListVo();
        vo.setuId(uId);
        vo.setPayStatus(payStatus);
        vo.setPageNo(pageNo);
        vo.setPaegSize(paegSize);
        orderliveData = repository.getMyOrderList(vo);
    }

    /**
     * 更新订单
     *
     * @param status
     * @param orderId
     * @param reason
     */
    public void updateOrderStatus(Integer status, String orderId, int reason) {
        repository = new MyRepositoryImpl();
        OrderStatusVo vo = new OrderStatusVo();
        vo.setStatus(status);
        vo.setOrderId(orderId);
        vo.setReason(reason);
        orderStatusliveData = repository.updateOrderStatus(vo);
    }

    /**
     * 支付
     *
     * @param orderNo
     * @param type
     * @param payfright
     * @param platform 平台 1.APP支付 2.PC支付
     */
    public void payOrder(String orderNo, Integer type, Integer payfright,String platform) {
        repository = new MyRepositoryImpl();
        PayOrderVo vo = new PayOrderVo();
        vo.setOrderNo(orderNo);
        vo.setType(type);
        vo.setPlatform(platform);
        vo.setPayfright(payfright==null?"":String.valueOf(payfright));
        pay = repository.payOrder(vo);
    }

    /**
     * 我的上级 、下级、下二级、下三级
     *
     * @param uId
     * @param type     1.上级 2.下级 3.下二级 4下三级
     * @param pageNo
     * @param pageSize
     */
    public void getInviteList(String uId, Integer type, Integer pageNo, Integer pageSize) {
        repository = new MyRepositoryImpl();
        InviteListVo vo = new InviteListVo();
        vo.setuId(uId);
        vo.setType(type);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        inviteList = repository.getInviteList(vo);
    }

    /**
     * 我的下级详情
     *
     * @param uId
     * @param inviteeId
     * @param pageNo
     * @param pageSize
     */
    public void getInviteeDetail(String uId, String inviteeId, Integer pageNo, Integer pageSize) {
        repository = new MyRepositoryImpl();
        InviteeDetailVo vo = new InviteeDetailVo();
        vo.setuId(uId);
        vo.setInviteeId(inviteeId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        inviteeDetail = repository.getInviteeDetail(vo);
    }

    /**
     * 添加上级
     *
     * @param
     * @return
     */
    public void addMySuperior(String uId, String account) {
        repository = new MyRepositoryImpl();
        AddMySuperiorVo vo = new AddMySuperiorVo();
        vo.setuId(uId);
        vo.setAccount(account);
        addMysuper = repository.addMySuperior(vo);
    }

    /**
     * 添加上级
     *
     * @param
     * @return
     */
    public void getOrderDetail(String orderId) {
        repository = new MyRepositoryImpl();
        ;
        orderDetail = repository.getOrderDetail(orderId);
    }

    /**
     * 会员中心
     *
     * @param type
     * @param id
     * @return
     */
    public void MemberCenter(Integer type, String id) {
        repository = new MyRepositoryImpl();
        ;
        member = repository.MemberCenter(type, id);
    }

    /**
     * 评价
     *
     * @param uId
     * @param orderId
     * @param grade
     * @param content
     * @param imgUrl
     * @param isAnonymity
     */
    public void addEvaluate(String uId, String orderId, Integer grade, String content, String imgUrl, Integer isAnonymity) {
        repository = new MyRepositoryImpl();
        CommentGoodVo vo = new CommentGoodVo();
        vo.setuId(uId);
        vo.setOrderId(orderId);
        vo.setGrade(grade);
        vo.setContent(content);
        vo.setImgUrl(imgUrl);
        vo.setIsAnonymity(isAnonymity);
        addEvaluate = repository.addEvaluate(vo);
    }

    /**
     * 退货原因列表
     */
    public void getRefundReason() {
        repository = new MyRepositoryImpl();
        refundReasonliveData = repository.getRefundReason();
    }


    /**
     * 退货原因列表
     *
     * @param orderId
     * @param goodsId
     * @param quantity
     * @param reasonId
     * @param describtion
     * @param imgUrl
     * @param linkPeople
     * @param linkPhone
     */
    public void applyRefund(String orderId, String goodsId, int quantity,
                            String reasonId, String describtion, String imgUrl, String linkPeople, String linkPhone) {
        repository = new MyRepositoryImpl();
        ApplyRefundVo vo = new ApplyRefundVo();
        vo.setOrderId(orderId);
        vo.setGoodsId(goodsId);
        vo.setQuantity(quantity);
        vo.setReasonId(reasonId);
        vo.setDescribtion(describtion);
        vo.setImgUrl(imgUrl);
        vo.setLinkPeople(linkPeople);
        vo.setLinkPhone(linkPhone);
        applyRefundLiveData = repository.applyRefund(vo);
    }

    /**
     * 申请退货
     *
     * @param uId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public void getApplyRefundList(String uId, int pageNo, int pageSize) {
        repository = new MyRepositoryImpl();
        saleRecordLiveData = repository.getApplyRefundList(uId, pageNo, pageSize);
    }

    /**
     * 记录详情
     *
     * @param aId
     */
    public void getSaleRecordDetail(String aId) {
        repository = new MyRepositoryImpl();
        saleRecordDetailLiveData = repository.getSaleRecordDetail(aId);
    }

    /**
     * 添加物流地址
     *
     * @param id
     * @param courierName
     * @param courierNo
     */
    public void addCourierInfo(String id, String courierName, String courierNo) {
        repository = new MyRepositoryImpl();
        AddCourierInfoVo vo = new AddCourierInfoVo();
        vo.setId(id);
        vo.setCourierName(courierName);
        vo.setCourierNo(courierNo);
        addCourierInfoLiveData = repository.addCourierInfo(vo);
    }

    /**
     * 查看评价
     *
     * @param orderId
     */
    public void getEvaluateDetail(String orderId) {
        repository = new MyRepositoryImpl();
        evaluateDetailLiveData = repository.getEvaluateDetail(orderId);
    }

    /**
     * @param vo
     */
    public void delUserFootprint(DeleteFootVo vo) {
        repository = new MyRepositoryImpl();
        delUserFoot = repository.delUserFootprint(vo);
    }

    /**
     * 积分说明 抽奖说明
     *
     * @param
     * @return
     */
    public void getContent() {
        repository = new MyRepositoryImpl();
        myTntegralDetailDtoLiveData = repository.getContent();
    }

    /**
     * 获取版本
     */
    public void getVersionInfo() {
        repository = new MyRepositoryImpl();
        updateLiveData = repository.getVersionInfo();
    }
}
