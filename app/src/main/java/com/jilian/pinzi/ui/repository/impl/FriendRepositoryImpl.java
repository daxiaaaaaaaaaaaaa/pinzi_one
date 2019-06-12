package com.jilian.pinzi.ui.repository.impl;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonRepository;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
import com.jilian.pinzi.common.vo.CommentReplyAddVo;
import com.jilian.pinzi.common.vo.FriendCircleCommentVo;
import com.jilian.pinzi.common.vo.FriendCircleIssueVo;
import com.jilian.pinzi.common.vo.FriendCircleLikeVo;
import com.jilian.pinzi.common.vo.FriendCircleListVo;
import com.jilian.pinzi.common.vo.SingleFriendCircleVo;
import com.jilian.pinzi.common.vo.UserTypeFriendCircleListVo;
import com.jilian.pinzi.http.Api;
import com.jilian.pinzi.ui.repository.FriendRepository;

import java.util.List;

public class FriendRepositoryImpl extends CommonRepository implements FriendRepository {
    /**
     * 获取朋友列表
     *
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<FriendCircleDto>>> FriendCircleList(FriendCircleListVo vo) {
        return request(Api.FriendCircleList(vo)).send().get();
    }

    /**
     * 朋友圈删除
     *
     * @param id
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> FriendCircleDelete(String id) {
        return request(Api.FriendCircleDelete(id)).send().get();
    }

    /**
     * 朋友圈评论接口
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> FriendCircleComment(FriendCircleCommentVo vo) {
        return request(Api.FriendCircleComment(vo)).send().get();
    }

    /**
     * 朋友圈评论删除
     *
     * @param id 评论ID
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> FriendCircleCommentDelete(String id) {
        return request(Api.FriendCircleCommentDelete(id)).send().get();
    }
    /**
     *朋友圈点赞
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> FriendCircleLike(FriendCircleLikeVo vo) {
        return request(Api.FriendCircleLike(vo)).send().get();
    }
    /**
     * 朋友圈取消点赞 点赞ID
     * @param id
     * @return
     */
    @Override
     public LiveData<BaseDto<String>> FriendCircleCancelLike(String id) {
        return request(Api.FriendCircleCancelLike(id)).send().get();
    }
    /**
     * 朋友圈评论回复
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> CommentReplyAdd(CommentReplyAddVo vo) {
        return request(Api.CommentReplyAdd(vo)).send().get();
    }
    /**
     *发布朋友圈
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<String>> FriendCircleIssue(FriendCircleIssueVo vo) {
        return request(Api.FriendCircleIssue(vo)).send().get();
    }
    /**
     * 刷新单条朋友圈
     * @param  vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<FriendCircleListDetailDto>>> SingleFriendCircle(SingleFriendCircleVo vo) {
        return request(Api.SingleFriendCircle(vo)).send().get();
    }
    /**
     * 我的朋友圈列表
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<FriendCircleDto>>> MyFriendCircleList(FriendCircleListVo vo) {
        return request(Api.MyFriendCircleList(vo)).send().get();
    }
    /**
     * 用户类型朋友圈列表
     * @param vo
     * @return
     */
    @Override
    public LiveData<BaseDto<List<FriendCircleDto>>> UserTypeFriendCircleList(UserTypeFriendCircleListVo vo) {
        return request(Api.UserTypeFriendCircleList(vo)).send().get();
    }
}
