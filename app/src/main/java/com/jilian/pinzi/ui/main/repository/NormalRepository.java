package com.jilian.pinzi.ui.main.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.common.vo.AccesstokenVo;

public interface NormalRepository {

    /**
     * 获取第一步的code后，请求以下链接获取access_token：
     * @param vo
     * @return
     */
    LiveData<String> access_token(AccesstokenVo vo);

}
