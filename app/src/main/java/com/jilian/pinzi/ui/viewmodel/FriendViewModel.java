package com.jilian.pinzi.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.common.vo.CommentReplyAddVo;
import com.jilian.pinzi.common.vo.FriendCircleCommentVo;
import com.jilian.pinzi.common.vo.FriendCircleIssueVo;
import com.jilian.pinzi.common.vo.FriendCircleLikeVo;
import com.jilian.pinzi.common.vo.FriendCircleListVo;
import com.jilian.pinzi.common.vo.SingleFriendCircleVo;
import com.jilian.pinzi.common.vo.UserTypeFriendCircleListVo;
import com.jilian.pinzi.ui.repository.FriendRepository;
import com.jilian.pinzi.ui.repository.OrderRepository;
import com.jilian.pinzi.ui.repository.impl.FriendRepositoryImpl;

import java.util.List;

/**
 * 朋友圈 ViewModel
 */
public class FriendViewModel extends ViewModel {
    private FriendRepository friendRepository;
    private LiveData<BaseDto<List<FriendCircleDto>>> friendCirc;

    private LiveData<BaseDto<String>> delete;

    private LiveData<BaseDto<String>> comment;

    private LiveData<BaseDto<String>> commentDelete;

    private LiveData<BaseDto<String>> like;

    private LiveData<BaseDto<String>> cancel;


    private LiveData<BaseDto<String>> commentAdd;

    private LiveData<BaseDto<String>> publish;

    private LiveData<BaseDto<List<FriendCircleListDetailDto>>> singleFriendCircle;

    private LiveData<BaseDto<List<FriendCircleDto>>> UserTypeFriendCircleList;

    public LiveData<BaseDto<List<FriendCircleDto>>> getUserTypeFriendCircleList() {
        return UserTypeFriendCircleList;
    }

    public FriendRepository getFriendRepository() {
        return friendRepository;
    }

    public LiveData<BaseDto<List<FriendCircleListDetailDto>>> getSingleFriendCircle() {
        return singleFriendCircle;
    }

    public LiveData<BaseDto<String>> getPublish() {
        return publish;
    }

    public LiveData<BaseDto<String>> getCommentAdd() {
        return commentAdd;
    }

    public LiveData<BaseDto<String>> getCancel() {
        return cancel;
    }

    public LiveData<BaseDto<String>> getLike() {
        return like;
    }

    public LiveData<BaseDto<String>> getCommentDelete() {
        return commentDelete;
    }

    public LiveData<BaseDto<String>> getComment() {
        return comment;
    }

    public LiveData<BaseDto<String>> getDelete() {
        return delete;
    }

    public LiveData<BaseDto<List<FriendCircleDto>>> getFriendCirc() {
        return friendCirc;
    }

    private LiveData<BaseDto<List<FriendCircleDto>>> myFriendCirc;

    public LiveData<BaseDto<List<FriendCircleDto>>> getMyFriendCirc() {
        return myFriendCirc;
    }

    /**
     * 朋友列表
     */
    public void FriendCircleList(int pageNo,int pageSize) {
        friendRepository = new FriendRepositoryImpl();
        FriendCircleListVo vo  = new FriendCircleListVo();
        vo.setStartNum(pageNo);
        vo.setPageSize(pageSize);
        vo.setuId(PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId());
        friendCirc = friendRepository.FriendCircleList(vo);
    }

    /**
     * 删除朋友圈
     */
    public void FriendCircleDelete(String id) {
        friendRepository = new FriendRepositoryImpl();
        delete = friendRepository.FriendCircleDelete(id);
    }

    /**
     * 朋友圈评论
     *
     * @param uId     用户ID
     * @param content string评论内容
     * @param fcId    string朋友圈ID
     */
    public void FriendCircleComment(String uId, String content, String fcId) {
        friendRepository = new FriendRepositoryImpl();
        FriendCircleCommentVo vo = new FriendCircleCommentVo();
        vo.setuId(uId);
        vo.setContent(content);
        vo.setFcId(fcId);
        comment = friendRepository.FriendCircleComment(vo);
    }

    /**
     * 朋友圈评论删除
     * id 评论ID
     */
    public void FriendCircleCommentDelete(String id) {
        friendRepository = new FriendRepositoryImpl();
        commentDelete = friendRepository.FriendCircleCommentDelete(id);
    }

    /**
     * 朋友圈点赞
     * @param fcId 朋友圈ID
     * @param uId  用户ID
     */
    public void FriendCircleLike(String fcId,String uId) {
        friendRepository = new FriendRepositoryImpl();
        FriendCircleLikeVo vo = new FriendCircleLikeVo();
        vo.setFcId(fcId);
        vo.setuId(uId);
        like = friendRepository.FriendCircleLike(vo);
    }
    /**
     * 朋友圈取消点赞 点赞ID
     * @param id
     * @return
     */
    public void FriendCircleCancelLike(String id) {
        friendRepository = new FriendRepositoryImpl();
        cancel = friendRepository.FriendCircleCancelLike(id);
    }
    /**
     *
     * @param uId 用户
     * @param content 内容
     * @param fcId 朋友圈 ID
     * @param parentId 回复评论ID
     */
    public void CommentReplyAdd(String uId,String content,String fcId,String parentId) {
        friendRepository = new FriendRepositoryImpl();
        CommentReplyAddVo vo = new CommentReplyAddVo();
        vo.setFcId(fcId);
        vo.setuId(uId);
        vo.setContent(content);
        vo.setParentId(parentId);
        commentAdd = friendRepository.CommentReplyAdd(vo);
    }

    /**
     * 发布朋友
     * @param uId 用户ID
     * @param content 内容
     * @param imgUrl 图片
     * @param photoSize 图片尺寸
     */
     public void FriendCircleIssue(String uId,String content,String imgUrl,String photoSize,String video) {
        friendRepository = new FriendRepositoryImpl();
        FriendCircleIssueVo vo = new FriendCircleIssueVo();
        vo.setVideo(video);
        vo.setuId(uId);
        vo.setContent(content);
        vo.setImgUrl(imgUrl);
        vo.setPhotoSize(photoSize);
        publish = friendRepository.FriendCircleIssue(vo);
    }

    /**
     *
     * @param uId 用户ID
     * @param id 朋友圈ID
     */
    public void SingleFriendCircle(String id,String uId) {
        friendRepository = new FriendRepositoryImpl();
        SingleFriendCircleVo vo  = new SingleFriendCircleVo();
        vo.setId(id);
        vo.setuId(uId);
        singleFriendCircle = friendRepository.SingleFriendCircle(vo);
    }
    /**
     * 我的朋友圈列表
     */
    public void MyFriendCircleList(String uId) {
        friendRepository = new FriendRepositoryImpl();
        FriendCircleListVo vo  = new FriendCircleListVo();
        vo.setuId(uId);
        myFriendCirc = friendRepository.MyFriendCircleList(vo);
    }

    /**
     * 用户类型朋友圈列表
     * @param
     * @return
     */
    public void UserTypeFriendCircleList(int pageNo,int pageSize, String uId,Integer type) {
        friendRepository = new FriendRepositoryImpl();
        UserTypeFriendCircleListVo vo  = new UserTypeFriendCircleListVo();
        vo.setStartNum(pageNo);
        vo.setPageSize(pageSize);
        vo.setType(type);
        vo.setuId(uId);
        vo.setuId(PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId());
        UserTypeFriendCircleList = friendRepository.UserTypeFriendCircleList(vo);
    }

}
