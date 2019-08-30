package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.FriendsCircleAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.common.dto.FriendTblCommentDto;
import com.jilian.pinzi.common.dto.FriendlLikeDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.common.tesk.Task;
import com.jilian.pinzi.ui.friends.FriendDetailActivity;
import com.jilian.pinzi.ui.friends.MyFriendsCircleActivity;
import com.jilian.pinzi.ui.friends.PublishFriendsActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.viewmodel.FriendViewModel;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MyPinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ThreeFragment extends BaseFragment implements FriendsCircleAdapter.FriendListener {

    private RecyclerView recyclerView;
    private FriendsCircleAdapter friendsCircleAdapter;
    private List<FriendCircleListDto> list;
    private FriendViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    public int height = 0;
    private int previousKeyboardHeight = -1;

    public LinearLayout llBottom;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        llBottom = (LinearLayout) getmActivity().findViewById(R.id.ll_bottom);
        recyclerView = view.findViewById(R.id.rv_three);
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        setNormalTitle("朋友圈", R.drawable.icon_friends_camera, v -> {
            if (PinziApplication.getInstance().getLoginDto() == null) {
                Intent intent = new Intent(getmActivity(), LoginActivity.class);
                startActivity(intent);
                return;
            }
            if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                    && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                    && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                return;
            }
            startActivity(new Intent(mActivity, PublishFriendsActivity.class));
        });

        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        list.add(null);
        friendsCircleAdapter = new FriendsCircleAdapter(mActivity, list, this, getmActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(friendsCircleAdapter);
        friendsCircleAdapter.setOnItemClickListener(new FriendsCircleAdapter.OnItemClickListener() {
            @Override
            public void onHeadClick(View view, int position) {
                // TODO 进入我的朋友圈界面
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    getmActivity().startActivity(intent);
                    return;
                }
                toMineFriend(PinziApplication.getInstance().getLoginDto().getName(), PinziApplication.getInstance().getLoginDto().getHeadImg(), PinziApplication.getInstance().getLoginDto().getId());
            }
        });
    }

    /**
     * 去我的朋友圈
     *
     * @param id
     */
    @Override
    public void toMineFriend(String name, String url, String id) {
        Intent intent = new Intent(new Intent(mActivity, MyFriendsCircleActivity.class));
        intent.putExtra("uId", id);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        /* Do something */
        if (EmptyUtils.isNotEmpty(event)
                && EmptyUtils.isNotEmpty(event.getMainCreatMessage())
                && event.getMainCreatMessage().getCode() == 200
        ) {
            getData();
        }
    }

    @Override
    protected void initData() {
        getData();
        //根據類型  顯示界面
        if (getmActivity().getIntent().getStringExtra("uId") != null && getmActivity().getIntent().getIntExtra("back", 1) == 2) {
            setleftImage(R.drawable.image_back, true, null);
            llBottom.setVisibility(View.GONE);
        } else {
            llBottom.setVisibility(View.VISIBLE);

        }
        //监听外来是否要刷新
        RxBus.getInstance().toObservable().map(new Function<Object, FriendMsg>() {
            @Override
            public FriendMsg apply(Object o) throws Exception {
                return (FriendMsg) o;
            }
        }).subscribe(new Consumer<FriendMsg>() {
            @Override
            public void accept(FriendMsg eventMsg) throws Exception {
                if (eventMsg != null) {
                    if (eventMsg.getCode() == 200 || eventMsg.getCode() == 300) {
                        getData();
                    }
                }
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        pageNo = 1;
        //采購中心進來的
        //根據類型 判斷 獲取數據 接口
        if (getmActivity().getIntent().getIntExtra("type", 0) != 1
                &&
                getmActivity().getIntent().getStringExtra("uId") != null && getmActivity().getIntent().getIntExtra("back", 1) == 2) {
            //獲取類型朋友圈接口
            getUserTypeFriendCircleList();
        } else {
            //獲取普通朋友圈
            getFriendList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private int i = 0;
    /**
     * 用戶類型朋友圈
     */
    private void getUserTypeFriendCircleList() {
        viewModel.UserTypeFriendCircleList(pageNo, pageSize, getmActivity().getIntent().getStringExtra("uId"), getmActivity().getIntent().getIntExtra("type", 0));
        viewModel.getUserTypeFriendCircleList().observe(this, new Observer<BaseDto<List<FriendCircleDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleDto>> listBaseDto) {
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                getLoadingDialog().dismiss();
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getList())) {
                    //有数据
                    if (EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))) {
                        if (pageNo == 1) {
                            list.clear();
                            list.add(null); // 添加头布局
                        }
                        list.addAll(listBaseDto.getData().get(0).getList());
                    } else {
                        //说明是上拉加载
                        if (pageNo > 1) {
                            pageNo--;
                        } else {
                            //第一次就没数据
                            list.clear();
                            list.add(null); // 添加头布局
                        }
                    }
                    friendsCircleAdapter.notifyDataSetChanged();
                    /**
                     * 线程池
                     */
                    ExecutorService executor = Executors.newCachedThreadPool();
                    Task task = new Task(list);
                    Future<List<FriendCircleListDto>> result = executor.submit(task);
                    executor.shutdown();
                    try {
                        list.clear();
                        list.addAll(result.get());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    friendsCircleAdapter.notifyDataSetChanged();



                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        list.clear();
                        list.add(null); // 添加头布局
                    }
                }

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            friendsCircleAdapter.notifyDataSetChanged();
        }
    };


    /**
     * 首頁朋友圈
     */
    private void getFriendList() {
        viewModel.FriendCircleList(pageNo, pageSize);
        if (viewModel.getFriendCirc().hasObservers()) return;
        viewModel.getFriendCirc().observe(this, new Observer<BaseDto<List<FriendCircleDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                recyclerView.setEnabled(true);
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getList())) {
                    //有数据
                    if (EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))) {
                        if (pageNo == 1) {
                            list.clear();
                            list.add(null); // 添加头布局
                        }
                        list.addAll(listBaseDto.getData().get(0).getList());
                    } else {
                        //说明是上拉加载
                        if (pageNo > 1) {
                            pageNo--;
                        } else {
                            //第一次就没数据
                            list.clear();
                            list.add(null); // 添加头布局
                        }
                    }
                    friendsCircleAdapter.notifyDataSetChanged();
                    //开启子线程
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //对视频封面处理 耗时操作
                            for (int i = 0; i < list.size(); i++) {
                                //视频地址不为空
                                if (EmptyUtils.isNotEmpty(list.get(i)) && EmptyUtils.isNotEmpty(list.get(i).getVideo())) {
                                    list.get(i).setBitmap(BitmapUtils.getScanBitmap(BitmapUtils.getNetVideoBitmap(list.get(i).getVideo())));
                                    handler.sendEmptyMessage(1000);
                                }
                            }


                        }
                    }.start();


                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        list.clear();
                        list.add(null); // 添加头布局
                    }
                }

            }
        });
    }

    @Override
    protected void initListener() {

        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (getmActivity().getIntent().getStringExtra("uId") != null) {
                    pageNo = 1;
                    getUserTypeFriendCircleList();
                } else {
                    pageNo = 1;
                    getFriendList();
                }

            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (getmActivity().getIntent().getStringExtra("uId") != null) {
                    pageNo++;
                    getUserTypeFriendCircleList();
                } else {
                    pageNo++;
                    getFriendList();
                }


            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 评论弹出框
     *
     * @param position
     * @param view
     */
    @Override
    public void comment(int position, View view) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            ToastUitl.showImageToastFail("您是平台用户，只可浏览");
            return;
        }
        //评论
        showPopupWindow(view, position, null);
    }

    /**
     * 对话框弹出
     *
     * @param view
     * @param position
     * @param id
     */
    private void showPopupWindow(View view, int position, String id) {
        // 一个自定义的布局，作为显示的内容
        MyPinziDialogUtils dialog = MyPinziDialogUtils.getDialog(getmActivity(), R.layout.dialog_input_comment);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        EditText etComment = (EditText) dialog.findViewById(R.id.et_comment);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        LinearLayout linearLayout = dialog.findViewById(R.id.ll_fragment_three_comment);
        ScrollView layout = dialog.findViewById(R.id.scrollView);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (id == null) {
            //评论
            etComment.setHint("评论");
        } else {
            //回复
            etComment.setHint("回复");
        }
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    return;
                }
                if (id == null) {
                    //评论
                    FriendCircleComment(position, PinziApplication.getInstance().getLoginDto().getId(), comment, list.get(position).getId());
                } else {
                    //回复
                    CommentReplyAdd(position, PinziApplication.getInstance().getLoginDto().getId(), comment, list.get(position).getId(), id);
                }
                dialog.dismiss();
            }
        });
        // RV中评论区起始Y的位置
        int rvInputY = getY(view);
        int rvInputHeight = view.getHeight();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 对话框中的输入框Y的位置
                int dialogY = getY(linearLayout);
                if (position == list.size() - 1) {
                    list.add(list.get(list.size() - 1));
                    friendsCircleAdapter.notifyDataSetChanged();
                }

                recyclerView.smoothScrollBy(0, rvInputY - (dialogY - rvInputHeight));
            }
        }, 400);

        getmActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        getmActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int height = getmActivity().getWindow().getDecorView().getHeight();
                        int keyboardHeight = height - displayHeight;
                        if (previousKeyboardHeight != keyboardHeight) {
                            boolean hide = (double) displayHeight / height > 0.8;
                            if (hide) {
                                try {
                                    if (list.get(list.size() - 1).getId().equals(list.get(list.size() - 2).getId())) {
                                        list.remove(list.size() - 1);
                                        friendsCircleAdapter.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                }

                                dialog.dismiss();
                            }
                        }
                    }
                }, 200);


            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return false;
            }
        });

    }


    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }


    /**
     * 删除朋友圈
     *
     * @param position 朋友圈ID
     */
    @Override
    public void driendCircleDelete(int position) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            ToastUitl.showImageToastFail("您是平台用户，只可浏览");
            return;
        }
        getLoadingDialog().showDialog();
        viewModel.FriendCircleDelete(list.get(position).getId());
        viewModel.getDelete().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("删除成功");
                    list.remove(position);
                    friendsCircleAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    private int mPosition;

    @Override
    public void like(int position) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            ToastUitl.showImageToastFail("您是平台用户，只可浏览");
            return;
        }
        this.mPosition = position;
        //点赞
        FriendCircleLike(list.get(position).getId(), PinziApplication.getInstance().getLoginDto().getId());
    }

    /**
     * 取消点赞
     *
     * @param position
     */
    @Override
    public void cancel(int position) {
        this.mPosition = position;
        List<FriendlLikeDto> likeList = list.get(position).getTblLikeList();
        //判断有么有已经点赞过
        if (EmptyUtils.isNotEmpty(likeList)) {
            for (int i = 0; i < likeList.size(); i++) {
                if (PinziApplication.getInstance().getLoginDto().getId().equals(likeList.get(i).getuId())) {
                    //取消点赞
                    FriendCircleCancelLike(likeList.get(i).getId());
                    break;
                }
            }
        }

    }

    /**
     * 回复
     * 回复的内容ID
     *
     * @param id
     */
    @Override
    public void awnser(String id, View view) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            ToastUitl.showImageToastFail("您是平台用户，只可浏览");
            return;
        }
        //根据回复 获取 评论ID
        for (int i = 1; i < list.size(); i++) {
            List<FriendTblCommentDto> commentList = list.get(i).getTblCommentList();
            for (int j = 0; j < commentList.size(); j++) {
                //遍历评论
                if (commentList.get(j).getId().equals(id)) {
                    showPopupWindow(view, i, id);
                    return;
                } else {
                    //遍历回复
                    List<FriendTblCommentDto> commentDtos = commentList.get(j).getReplyComment();
                    for (int k = 0; k < commentDtos.size(); k++) {
                        if (commentDtos.get(k).getId().equals(id)) {
                            showPopupWindow(view, i, id);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void delete(String id, View view) {
        FriendCircleCommentDelete(id);
    }

    /**
     * 朋友圈评论
     *
     * @param uId     用户ID
     * @param content string评论内容
     * @param fcId    string朋友圈ID
     */
    private void FriendCircleComment(int position, String uId, String content, String fcId) {
        viewModel.FriendCircleComment(uId, content, fcId);
        viewModel.getComment().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    //ToastUitl.showImageToastSuccess("删除成功");
                    Log.e(TAG, "onChanged: 评论成功");
                    getPageAndPageSizeComment(position);
                }
            }
        });
    }

    /**
     * 朋友圈评论删除
     *
     * @param id 评论ID
     */
    private void FriendCircleCommentDelete(String id) {
        //  getLoadingDialog().showDialog();
        viewModel.FriendCircleCommentDelete(id);
        viewModel.getCommentDelete().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    //根据回复 获取 评论ID
                    for (int i = 1; i < list.size(); i++) {
                        List<FriendTblCommentDto> commentList = list.get(i).getTblCommentList();
                        for (int j = 0; j < commentList.size(); j++) {
                            //遍历评论
                            if (commentList.get(j).getId().equals(id)) {
                                commentList.remove(j);
                                friendsCircleAdapter.notifyDataSetChanged();
                                return;
                            } else {
                                //遍历回复
                                List<FriendTblCommentDto> commentDtos = commentList.get(j).getReplyComment();
                                for (int k = 0; k < commentDtos.size(); k++) {
                                    if (commentDtos.get(k).getId().equals(id)) {
                                        commentDtos.remove(k);
                                        friendsCircleAdapter.notifyDataSetChanged();
                                        return;
                                    }
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    /**
     * 朋友圈点赞
     *
     * @param fcId 朋友圈ID
     * @param uId  用户ID
     */
    private void FriendCircleLike(String fcId, String uId) {
        viewModel.FriendCircleLike(fcId, uId);
        viewModel.getLike().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    //
                    getPageAndPageSizeLike(mPosition);
                }
            }
        });
    }

    /**
     * 朋友圈取消点赞 点赞ID
     *
     * @param id
     * @return
     */
    private void FriendCircleCancelLike(String id) {
        viewModel.FriendCircleCancelLike(id);
        viewModel.getCancel().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    //
                    getPageAndPageSizeCancel(mPosition);
                    Log.e(TAG, "onChanged: 取消点赞成功");
                }
            }
        });
    }

    /**
     * 朋友圈评论回复
     *
     * @param uId      用户
     * @param content  内容
     * @param fcId     朋友圈 ID
     * @param parentId 回复评论ID
     */
    private void CommentReplyAdd(int position, String uId, String content, String fcId, String parentId) {
        viewModel.CommentReplyAdd(uId, content, fcId, parentId);
        viewModel.getCommentAdd().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    Log.e(TAG, "onChanged: 朋友圈评论回复成功");
                    getPageAndPageSizeAwnser(position);
                }
            }
        });
    }

    /**
     * 局部刷新 点赞信息
     * 计算当前位置 属于第几页 第几条
     *
     * @param position
     */
    private void getPageAndPageSizeLike(int position) {
        viewModel.SingleFriendCircle(list.get(position).getId(), list.get(position).getuId());
        viewModel.getSingleFriendCircle().observe(this, new Observer<BaseDto<List<FriendCircleListDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleListDetailDto>> listBaseDto) {
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getFriendCircle())) {
                    //需要刷新的該條朋友圈
                    FriendCircleListDto friendCircleListDto = listBaseDto.getData().get(0).getFriendCircle();
                    String video = list.get(position).getVideo();
                    Bitmap bitmap = list.get(position).getBitmap();
                    list.set(position, friendCircleListDto);
                    list.get(position).setBitmap(bitmap);
                    list.get(position).setVideo(video);
                    friendsCircleAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 局部刷新 取消点赞信息
     * 计算当前位置 属于第几页 第几条
     *
     * @param position
     */
    private void getPageAndPageSizeCancel(int position) {
        viewModel.SingleFriendCircle(list.get(position).getId(), list.get(position).getuId());
        viewModel.getSingleFriendCircle().observe(this, new Observer<BaseDto<List<FriendCircleListDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleListDetailDto>> listBaseDto) {
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getFriendCircle())) {
                    //需要刷新的該條朋友圈
                    FriendCircleListDto friendCircleListDto = listBaseDto.getData().get(0).getFriendCircle();


                    String video = list.get(position).getVideo();
                    Bitmap bitmap = list.get(position).getBitmap();

                    list.set(position, friendCircleListDto);
                    list.get(position).setBitmap(bitmap);
                    list.get(position).setVideo(video);


                    friendsCircleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //}

    /**
     * 局部刷新 评论信息
     * 计算当前位置 属于第几页 第几条
     *
     * @param position
     */
    private void getPageAndPageSizeComment(int position) {

        viewModel.SingleFriendCircle(list.get(position).getId(), list.get(position).getuId());
        viewModel.getSingleFriendCircle().observe(this, new Observer<BaseDto<List<FriendCircleListDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleListDetailDto>> listBaseDto) {
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getFriendCircle())) {
                    //需要刷新的該條朋友圈
                    FriendCircleListDto friendCircleListDto = listBaseDto.getData().get(0).getFriendCircle();

                    String video = list.get(position).getVideo();
                    Bitmap bitmap = list.get(position).getBitmap();

                    list.set(position, friendCircleListDto);
                    list.get(position).setBitmap(bitmap);
                    list.get(position).setVideo(video);


                    friendsCircleAdapter.notifyDataSetChanged();
                }
            }
        });

        //   }

    }

    /**
     * 局部刷新 回复信息
     * 计算当前位置 属于第几页 第几条
     *
     * @param position 朋友圈的位置 位置
     */
    private void getPageAndPageSizeAwnser(int position) {
        viewModel.SingleFriendCircle(list.get(position).getId(), list.get(position).getuId());
        viewModel.getSingleFriendCircle().observe(this, new Observer<BaseDto<List<FriendCircleListDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleListDetailDto>> listBaseDto) {
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getFriendCircle())) {
                    //需要刷新的該條朋友圈
                    FriendCircleListDto friendCircleListDto = listBaseDto.getData().get(0).getFriendCircle();


                    String video = list.get(position).getVideo();
                    Bitmap bitmap = list.get(position).getBitmap();

                    list.set(position, friendCircleListDto);
                    list.get(position).setBitmap(bitmap);
                    list.get(position).setVideo(video);

                    friendsCircleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    // }
}
