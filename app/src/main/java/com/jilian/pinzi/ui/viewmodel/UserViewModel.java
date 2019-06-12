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
    private LiveData<BaseDto<RegisterDto>> registerliveData;//注册
    private LiveData<BaseDto<String>> smsliveData;//发送短信
    private LiveData<BaseDto<String>> resetPwdliveData;//重置密码
    private LiveData<BaseDto<String>> perfectInformationliveData;//完善信息
    private LiveData<BaseDto<String>> photoImageliveData;//完善信息

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
     * @param imgUrl    图片地址
     */
    public void perfectInformation(String province, String city, String area, String name, String contact, String linkPhone, String id, String imgUrl) {
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
