package com.jilian.pinzi.ui.main.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseRepository;
import com.jilian.pinzi.common.vo.AccesstokenVo;
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.main.repository.NormalRepository;

public class NormalRepositoryImpl extends BaseRepository implements NormalRepository {


    /**
     * access_token 获取第一步的code后，请求以下链接获取access_token：
     * @param vo
     * @return
     */
    @Override
    public LiveData<String> access_token(AccesstokenVo vo) {
        return request(Api.access_token(vo)).send().get();
    }




}
