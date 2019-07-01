package com.jilian.pinzi.ui.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.common.vo.LoginVo;
import com.jilian.pinzi.common.vo.PerfectInformationVo;
import com.jilian.pinzi.common.vo.PhotoImgVo;
import com.jilian.pinzi.common.vo.RegisterVo;
import com.jilian.pinzi.common.vo.ResetPwdVo;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.common.vo.ThirdUserLoginVo;

import java.io.File;
import java.util.List;

public interface UserRepository {
    /**
     * 登录
     * @param vo
     * @return
     */
    LiveData<BaseDto<LoginDto>> login(LoginVo vo);

    /**
     * 第三方登录
     * @param vo
     * @return
     */
    LiveData<BaseDto<LoginDto>> ThirdUserLogin(ThirdUserLoginVo vo);
    /**
     * 获取验证码
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> sendMsg(SmsVo vo);

    /**
     * 重置密码
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> resetPwd(ResetPwdVo vo);

    /**
     * 完善信息
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> perfectInformation(PerfectInformationVo vo);

    /**
     * 一键上传
     * @param vo
     * @param files
     * @param
     * @return
     */
    LiveData<BaseDto<String>> photoImg(PhotoImgVo vo, List<File> files);

    /**
     * 注册
     *
     * @param vo
     * @return
     */
    LiveData<BaseDto<RegisterDto>> register(RegisterVo vo);



}
