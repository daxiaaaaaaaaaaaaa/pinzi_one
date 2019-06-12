package com.jilian.pinzi.ui.repository;

import android.arch.lifecycle.LiveData;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
import com.jilian.pinzi.common.vo.CommentReplyAddVo;
import com.jilian.pinzi.common.vo.FriendCircleCommentVo;
import com.jilian.pinzi.common.vo.FriendCircleIssueVo;
import com.jilian.pinzi.common.vo.FriendCircleLikeVo;
import com.jilian.pinzi.common.vo.FriendCircleListVo;
import com.jilian.pinzi.common.vo.SingleFriendCircleVo;
import com.jilian.pinzi.common.vo.UserTypeFriendCircleListVo;

import java.util.List;

public interface FriendRepository  {
    /**
     * 获取朋友列表
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<FriendCircleDto>>> FriendCircleList(FriendCircleListVo vo);


    /**
     * 朋友圈删除
     * @param id
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleDelete(String id);

    /**
     *朋友圈评论
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleComment(FriendCircleCommentVo vo);

    /**
     * 朋友圈评论删除 评论ID
     * @param id
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleCommentDelete(String id);

    /**
     *朋友圈点赞
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleLike(FriendCircleLikeVo vo);

    /**
     * 朋友圈取消点赞 点赞ID
     * @param id
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleCancelLike(String id);

    /**
     * 朋友圈评论回复
     * @param vo
     * @return
     */
     LiveData<BaseDto<String>> CommentReplyAdd(CommentReplyAddVo vo);

    /**
     *发布朋友圈
     * @param vo
     * @return
     */
    LiveData<BaseDto<String>> FriendCircleIssue(FriendCircleIssueVo vo);


    /**
     * 刷新单条朋友圈
     * @param  vo
     * @return
     */
    LiveData<BaseDto<List<FriendCircleListDetailDto>>> SingleFriendCircle(SingleFriendCircleVo vo);

    /**
     * 我的朋友圈列表
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<FriendCircleDto>>> MyFriendCircleList(FriendCircleListVo vo);

    /**
     * 用户类型朋友圈列表
     * @param vo
     * @return
     */
    LiveData<BaseDto<List<FriendCircleDto>>> UserTypeFriendCircleList(UserTypeFriendCircleListVo vo);

}
