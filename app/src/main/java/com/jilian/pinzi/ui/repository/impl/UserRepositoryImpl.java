package com.jilian.pinzi.ui.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.common.vo.LoginVo;
import com.jilian.pinzi.common.vo.PerfectInformationVo;
import com.jilian.pinzi.common.vo.PhotoImgVo;
import com.jilian.pinzi.common.vo.RegisterVo;
import com.jilian.pinzi.common.vo.ResetPwdVo;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.repository.UserRepository;

import java.io.File;
import java.util.List;

public class UserRepositoryImpl extends CommonRepository implements UserRepository {
    /**
     * 登录
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<LoginDto>> login(LoginVo vo) {
        return request(Api.login(vo)).send().get();
    }
    /**
     * 获取验证码
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> sendMsg(SmsVo vo) {
        return request(Api.sendMsg(vo)).send().get();
    }

    /**
     * 重置密码
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> resetPwd(ResetPwdVo vo) {
        return request(Api.resetPwd(vo)).send().get();
    }

    /**
     * 完善信息
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> perfectInformation(PerfectInformationVo vo) {
        return request(Api.perfectInformation(vo)).send().get();
    }

    @Override
    public LiveData<BaseDto<String>> photoImg(PhotoImgVo vo, List<File> files) {
        return request(Api.photoImg(vo, files, "*/*")).send().get();
    }
    /**
     * 注册
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<RegisterDto>> register(RegisterVo vo) {
        return request(Api.register(vo)).send().get();
    }

}
