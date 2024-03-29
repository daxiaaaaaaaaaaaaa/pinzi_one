package com.jilian.pinzi.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.common.vo.LoginVo;
import com.jilian.pinzi.common.vo.PerfectInformationVo;
import com.jilian.pinzi.common.vo.PhotoImgVo;
import com.jilian.pinzi.common.vo.ResetPwdVo;
import com.jilian.pinzi.common.vo.ThirdUserLoginVo;
import com.jilian.pinzi.ui.repository.UserRepository;
import com.jilian.pinzi.common.vo.RegisterVo;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.ui.repository.impl.UserRepositoryImpl;
import com.jilian.pinzi.utils.ToastUitl;

import java.io.File;
import java.util.List;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private UserRepository userRepository;


    private LiveData<BaseDto<LoginDto>> loginliveData;//登陆

    private LiveData<BaseDto<LoginDto>> threeliveData;//登陆

    private LiveData<BaseDto<RegisterDto>> registerliveData;//注册
    private LiveData<BaseDto<String>> smsliveData;//发送短信
    private LiveData<BaseDto<String>> resetPwdliveData;//重置密码
    private LiveData<BaseDto<String>> perfectInformationliveData;//完善信息
    private LiveData<BaseDto<String>> photoImageliveData;//完善信息

    public LiveData<BaseDto<LoginDto>> getThreeliveData() {
        return threeliveData;
    }

    public LiveData<BaseDto<String>> getPerfectInformationliveData() {
        return perfectInformationliveData;
    }

    public LiveData<BaseDto<String>> getPhotoImageliveData() {
        return photoImageliveData;
    }

    public LiveData<BaseDto<LoginDto>> getLoginliveData() {
        return loginliveData;
    }


    public LiveData<BaseDto<String>> getSmsliveData() {
        return smsliveData;
    }

    public LiveData<BaseDto<RegisterDto>> getRegisterliveData() {
        return registerliveData;

    }

    public LiveData<BaseDto<String>> getResetPwdliveData() {
        return resetPwdliveData;
    }


    /**
     * 登录
     *
     * @param phone
     * @param pwd
     */
    public void login(String phone, String pwd) {
        userRepository = new UserRepositoryImpl();
        LoginVo vo = new LoginVo();
        vo.setPassword(pwd);
        vo.setPhone(phone);
        loginliveData = userRepository.login(vo);

    }

    /**
     * private String loginId;//true string    登录ID
     * private String loginType;// false string 0微信 1QQ 2微博
     * private String headImg;//false string 头像
     * private String uName;//false string    名称
     * private String type;// false number 0,用户首次调用三方登录接口，二次调用需要携带以下参数且type类型的值必须为类型（1.普通用户 2.门店 3.二批商）
     * private String phone;// false number 电话号码
     * private String msgCode;//false number     短信验证码
     * private String InvitationCode;//false number    邀请码
     * private String password;// false number  密码
     */
    public void ThirdUserLogin(String loginId, String loginType, String headImg, String uName, String type, String phone, String msgCode, String InvitationCode, String password) {
        userRepository = new UserRepositoryImpl();
        ThirdUserLoginVo vo = new ThirdUserLoginVo();
        vo.setLoginId(loginId);
        vo.setLoginType(loginType);
        vo.setHeadImg(headImg);
        vo.setuName(uName);
        vo.setType(type);
        vo.setPhone(phone);
        vo.setMsgCode(msgCode);
        vo.setInvitationCode(InvitationCode);
        vo.setPassword(password);

        threeliveData = userRepository.ThirdUserLogin(vo);

    }


    /**
     * 注册
     *
     * @param vo
     */
    public void register(RegisterVo vo) {
        userRepository = new UserRepositoryImpl();
        registerliveData = userRepository.register(vo);

    }

    /**
     * 获取验证码
     *
     * @param vo
     */
    public void sendMsg(SmsVo vo) {
        userRepository = new UserRepositoryImpl();
        smsliveData = userRepository.sendMsg(vo);

    }

    /**
     * 重置密码
     *
     * @param phone
     * @param msgCode
     * @param password
     */
    public void resetPwd(String phone, String msgCode, String password) {
        ResetPwdVo vo = new ResetPwdVo();
        userRepository = new UserRepositoryImpl();
        vo.setPhone(phone);
        vo.setPassword(password);
        vo.setMsgCode(msgCode);
        resetPwdliveData = userRepository.resetPwd(vo);
    }

    /**
     * 完善信息
     *
     * @param province  省份
     * @param city      城市
     * @param area      地区
     * @param name      终端名称（渠道名称）
     * @param contact   联系人
     * @param linkPhone 联系电话
     * @param id        ID
     * @param imgUrl    营业执照图片地址
     * @param storeImg  门店图片地址
     */
    public void perfectInformation(String province, String city, String area, String name, String contact, String linkPhone, String id, String imgUrl, String storeImg,String address) {
        userRepository = new UserRepositoryImpl();
        PerfectInformationVo vo = new PerfectInformationVo();
        vo.setProvince(province);
        vo.setCity(city);
        vo.setArea(area);
        vo.setName(name);
        vo.setContact(contact);
        vo.setLinkPhone(linkPhone);
        vo.setId(id);
        vo.setImgUrl(imgUrl);
        vo.setStoreImg(storeImg);
        vo.setAddress(address);
        perfectInformationliveData = userRepository.perfectInformation(vo);
    }

    /**
     * 上传图片
     *
     * @param type
     * @param files
     */
    public void photoImg(Integer type, List<File> files) {
        userRepository = new UserRepositoryImpl();
        PhotoImgVo vo = new PhotoImgVo();
        vo.setType(type);
        photoImageliveData = userRepository.photoImg(vo, files);
    }

}
