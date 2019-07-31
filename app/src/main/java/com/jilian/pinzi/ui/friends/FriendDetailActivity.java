package com.jilian.pinzi.ui.friends;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonActivity;
import com.jilian.pinzi.common.dto.FriendCircleListDetailDto;
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.common.dto.FriendTblCommentDto;
import com.jilian.pinzi.common.dto.FriendlLikeDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.VideoPlayerActivity;
import com.jilian.pinzi.ui.friends.imagepager.ImagePagerActivity;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.ui.viewmodel.FriendViewModel;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MyPinziDialogUtils;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;

public class FriendDetailActivity extends CommonActivity {
    private ImageView ivHead;
    private TextView tvName;
    private TextView tvContent;
    private RecyclerView rvItemListFriendsImages;
    private RelativeLayout rlCenter;
    private TextView tvItemListFriendsMinute;
    private TextView tvItemListFriendsDel;
    private RelativeLayout rlCommentLike;
    private ImageView ivItemListFriendsMsg;
    private LinearLayout llItemListFriendsReply;
    private RecyclerView rvItemListFriendsLike;
    private View viewItemListFriendsLine;
    private RecyclerView rvItemListFriendsReplay;
    private FriendViewModel viewModel;
    private FriendCircleListDto dto;
    private ImageView ivLike;
    private ImageView ivComment;
    private LinearLayout llFragmentThreeComment;
    private EditText etComment;
    private TextView tvOk;
    private RelativeLayout rlVideo;
    private ImageView btnVideo;
    private ImageView ivStart;


    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_friend_detail;
    }

    private NestedScrollView scrollView;

    @Override
    public void initView() {
        setNormalTitle("详情", v -> finish());


        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        rlVideo = (RelativeLayout) findViewById(R.id.rl_video);
        btnVideo = (ImageView) findViewById(R.id.btnVideo);
        ivStart = (ImageView) findViewById(R.id.iv_start);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvContent = (TextView) findViewById(R.id.tv_content);
        rvItemListFriendsImages = (RecyclerView) findViewById(R.id.rv_item_list_friends_images);
        rlCenter = (RelativeLayout) findViewById(R.id.rl_center);
        tvItemListFriendsMinute = (TextView) findViewById(R.id.tv_item_list_friends_minute);
        tvItemListFriendsDel = (TextView) findViewById(R.id.tv_item_list_friends_del);
        rlCommentLike = (RelativeLayout) findViewById(R.id.rl_comment_like);
        ivItemListFriendsMsg = (ImageView) findViewById(R.id.iv_item_list_friends_msg);
        llItemListFriendsReply = (LinearLayout) findViewById(R.id.ll_item_list_friends_reply);
        rvItemListFriendsLike = (RecyclerView) findViewById(R.id.rv_item_list_friends_like);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 4));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 4));//下间距
        rvItemListFriendsLike.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        ivLike = (ImageView) findViewById(R.id.iv_like);
        ivComment = (ImageView) findViewById(R.id.iv_comment);
        viewItemListFriendsLine = (View) findViewById(R.id.view_item_list_friends_line);
        rvItemListFriendsReplay = (RecyclerView) findViewById(R.id.rv_item_list_friends_replay);
        llFragmentThreeComment = (LinearLayout) findViewById(R.id.ll_fragment_three_comment);
        etComment = (EditText) findViewById(R.id.et_comment);
        tvOk = (TextView) findViewById(R.id.tv_ok);


    }

    @Override
    public void initData() {
        getFriendDetai();
    }

    private void getFriendDetai() {
        viewModel.SingleFriendCircle(getIntent().getStringExtra("id"), getIntent().getStringExtra("uId"));
        viewModel.getSingleFriendCircle().observe(this, new Observer<BaseDto<List<FriendCircleListDetailDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleListDetailDto>> listBaseDto) {
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    //单条朋友圈详情
                    dto = listBaseDto.getData().get(0).getFriendCircle();

                    initDetailView(dto);
                    //通过事件总线 把 前面两个Activity的数据也刷新
                    FriendMsg eventMsg = new FriendMsg();
                    eventMsg.setCode(200);
                    RxBus.getInstance().post(eventMsg);


                } else {
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }
            }
        });
    }

    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        return bitmap;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1000) {

                Bitmap bitmap = (Bitmap) msg.obj;
                friend.setBitmap(bitmap);
                btnVideo.setImageBitmap(bitmap);
            }
        }
    };

    /**
     * 展示UI
     *
     * @param dto
     */
    private void initDetailView(FriendCircleListDto dto) {
        if (PinziApplication.getInstance().getLoginDto().getId().equals(dto.getuId())) {
            tvItemListFriendsDel.setVisibility(View.VISIBLE);
        } else {
            tvItemListFriendsDel.setVisibility(View.GONE);
        }
        this.friend = dto;
        Glide
                .with(this)
                .load(friend.getHeadImg())
                .into(ivHead);
        tvName.setText(friend.getName());
        String releaseContent = friend.getContent();
        tvContent.setText(releaseContent);
        tvContent.setVisibility(TextUtils.isEmpty(releaseContent) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(friend.getVideo())) {
            rlVideo.setVisibility(View.VISIBLE);
            rvItemListFriendsImages.setVisibility(View.GONE);
            //开启子线程
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap bitmap = BitmapUtils.getScanBitmap(BitmapUtils.getNetVideoBitmap(friend.getVideo()));
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    message.what = 1000;
                    handler.sendMessage(message);
                }


            }.start();


        } else {
            rlVideo.setVisibility(View.GONE);
            rvItemListFriendsImages.setVisibility(View.VISIBLE);
            // 朋友圈发布的图片
            // TODO 模拟一些图片数据: 最多添加9张图片
            List<String> images = getImageUrls(friend.getImgUrl());
            // 显示图片
            initImagesRecyclerView(images, friend);
        }

        // 设置发布时间
        tvItemListFriendsMinute.setText(DateUtil.getTimeFormatText(new Date(Long.valueOf(friend.getCreateDate()))));

        //
        // 删除该朋友圈
        tvItemListFriendsDel.setOnClickListener(v -> {
            if (PinziApplication.getInstance().getLoginDto() == null) {
                Intent intent = new Intent(FriendDetailActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                    && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                    && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                return;
            }
            Dialog dialog = new Dialog(this, R.style.dialogFullscreen);
            dialog.setContentView(R.layout.dialog_bottom_layout);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            layoutParams.dimAmount = 0.5f;
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(layoutParams);

            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
                // TODO 删除
                dialog.dismiss();
                driendCircleDelete(friend.getId());
            });
            dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                dialog.dismiss();

            });
        });

        // 弹出点赞和评论 PopupWindow.
        rlCommentLike.setOnClickListener(v -> {
            if (PinziApplication.getInstance().getLoginDto() == null) {
                Intent intent = new Intent(FriendDetailActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                    && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                    && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                return;
            }
            View content = LayoutInflater.from(this).inflate(R.layout.popup_friends_msg, null, false);
            PopupWindow popupWindow = new PopupWindow(content,
                    ScreenUtils.dip2px(this, 180), ScreenUtils.dip2px(this, 40));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            int[] location = new int[2];
            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int mShowMorePopupWindowWidth = -content.getMeasuredWidth();
            int mShowMorePopupWindowHeight = -content.getMeasuredHeight();
            popupWindow.showAsDropDown(rlCommentLike, -500, mShowMorePopupWindowHeight);

            LinearLayout ll_like = content.findViewById(R.id.ll_popup_friends_msg_like);
            LinearLayout ll_comment = content.findViewById(R.id.ll_popup_friends_msg_comment);
            TextView tv_left = content.findViewById(R.id.tv_left);
            //获取点赞列表
            List<FriendlLikeDto> likeList = friend.getTblLikeList();
            //判断有么有已经点赞过
            if (EmptyUtils.isNotEmpty(likeList)) {
                for (int i = 0; i < likeList.size(); i++) {
                    if (PinziApplication.getInstance().getLoginDto().getId().equals(likeList.get(i).getuId())) {
                        tv_left.setText("取消");
                        break;
                    } else {
                        tv_left.setText("点赞");
                    }
                }
            }
            ll_like.setOnClickListener(v1 -> {
                popupWindow.dismiss();
                if (tv_left.getText().toString().equals("取消")) {
                    //取消点赞
                    //取消点赞
                    FriendCircleCancelLike(friend);
                } else {
                    //点赞
                    FriendCircleLike(getIntent().getStringExtra("id"), PinziApplication.getInstance().getLoginDto().getId());
                }


            });
            ll_comment.setOnClickListener(v1 -> {
                popupWindow.dismiss();
                //评论
//                llFragmentThreeComment.setVisibility(View.VISIBLE);
                showPopupWindow(friend.getId(), null);


            });
        });
        // 初始化点赞列表
        List<FriendlLikeDto> likes = new ArrayList<>();
        likes.addAll(friend.getTblLikeList());
        //  if (likes.size() != 0) likes.add(0, null); // 添加红色桃心

        if (likes.size() > 0) {
            rvItemListFriendsLike.setVisibility(View.VISIBLE);
            initLikesRecyclerView(rvItemListFriendsLike, likes);
        } else {
            rvItemListFriendsLike.setVisibility(View.GONE);
        }

        // 初始化评论列表
        List<FriendTblCommentDto> replyList = new ArrayList<>();
        replyList.addAll(getReplyList(friend.getTblCommentList()));

        if (replyList.size() > 0) {
            rvItemListFriendsReplay.setVisibility(View.VISIBLE);
            initReplayRecyclerView(rvItemListFriendsReplay, replyList);
        } else {
            rvItemListFriendsReplay.setVisibility(View.GONE);
        }
        llItemListFriendsReply.setVisibility(likes.size() == 0 && replyList.size() == 0 ? View.GONE : View.VISIBLE);

        if (EmptyUtils.isNotEmpty(dto.getTblLikeList())) {
            ivLike.setVisibility(View.VISIBLE);
            rvItemListFriendsLike.setVisibility(View.VISIBLE);
        } else {
            ivLike.setVisibility(View.GONE);
            rvItemListFriendsLike.setVisibility(View.GONE);
        }
        if (EmptyUtils.isNotEmpty(dto.getTblCommentList())) {
            ivComment.setVisibility(View.VISIBLE);
            rvItemListFriendsReplay.setVisibility(View.VISIBLE);
        } else {
            ivComment.setVisibility(View.GONE);
            rvItemListFriendsReplay.setVisibility(View.GONE);
        }

    }

    /**
     * @param fcId     朋友圈ID
     * @param parentId 回復 評論 ID
     */
    private void showPopupWindow(String fcId, String parentId) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(FriendDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            ToastUitl.showImageToastFail("您是平台用户，只可浏览");
            return;
        }
        // 一个自定义的布局，作为显示的内容
        MyPinziDialogUtils dialog = MyPinziDialogUtils.getDialog(this, R.layout.dialog_input_comment);
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
        if (parentId == null) {
            //评论
            etComment.setHint("评论");
        } else {
            //回复
            etComment.setHint("回复");
        }
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(FriendDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                String comment = etComment.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    return;
                }
                if (parentId == null) {
                    //评论
                    FriendCircleComment(PinziApplication.getInstance().getLoginDto().getId(), comment, fcId);
                } else {
                    //回复
                    CommentReplyAdd(PinziApplication.getInstance().getLoginDto().getId(), comment, fcId, parentId);
                }
                dialog.dismiss();
            }
        });

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int height = getWindow().getDecorView().getHeight();
                        int keyboardHeight = height - displayHeight;
                        if (previousKeyboardHeight != keyboardHeight) {
                            boolean hide = (double) displayHeight / height > 0.8;
                            if (hide) {
                                dialog.dismiss();
                            }
                        }
                    }
                }, 200);


            }
        });
    }

    private int previousKeyboardHeight = -1;

    /**
     * 删除朋友圈
     *
     * @param id
     */
    private void driendCircleDelete(String id) {
        getLoadingDialog().showDialog();
        viewModel.FriendCircleDelete(id);
        viewModel.getDelete().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("删除成功");
                    //通过事件总线 把 前面两个Activity的数据也刷新
                    FriendMsg eventMsg = new FriendMsg();
                    eventMsg.setCode(200);
                    RxBus.getInstance().post(eventMsg);

                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });

    }

    private FriendCircleListDto friend;

    /**
     * 初始化图片 RecyclerView
     */
    public void initImagesRecyclerView(List<String> images, FriendCircleListDto friend) {
        rvItemListFriendsImages.setLayoutManager(new GridLayoutManager(this, 3));
        ImagesAdapter imagesAdapter = new ImagesAdapter(this, R.layout.item_friends_images, images);
        rvItemListFriendsImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                Intent intent = new Intent(view.getContext(), ViewPhotosActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                intent.putExtra("url", friend.getImgUrl());
                intent.putExtra("position", position);
                view.getContext().startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 获取朋友圈发布的图片
     *
     * @param imgUrl
     * @return
     */
    private List<String> getImageUrls(String imgUrl) {
        List<String> imageUrls = new ArrayList<>();
        if (!TextUtils.isEmpty(imgUrl)) {
            if (imgUrl.contains(",")) {
                String[] imgUrls = imgUrl.split(",");
                imageUrls.addAll(Arrays.asList(imgUrls));
            } else {
                imageUrls.add(imgUrl);
            }
        }
        return imageUrls;
    }

    private ArrayList<String> getUrls(String imgUrl) {
        ArrayList<String> urls;
        if (imgUrl.contains(",")) {
            urls = new ArrayList<>(Arrays.asList(imgUrl.split(",")));
        } else {
            urls = new ArrayList<>();
            urls.add(imgUrl);
        }
        return urls;
    }

    @Override
    public void initListener() {
        rlVideo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FriendDetailActivity.this, VideoPlayerActivity.class);
                intent.putExtra("url", friend.getVideo());
                Bitmap bitmap = friend.getBitmap();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap",bitmapByte);

                // 添加跳转动画
                startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                FriendDetailActivity.this,
                                btnVideo,
                                FriendDetailActivity.this.getString(R.string.share_str))
                                .toBundle());






//
//                Intent intent = new Intent(FriendDetailActivity.this, VideoPlayerActivity.class);
//                intent.putExtra("url", friend.getVideo());
//                Bitmap bitmap = BitmapUtils.getScanBitmap(friend.getBitmap());
//                ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
//                byte [] bitmapByte =baos.toByteArray();
//                intent.putExtra("bitmap",bitmapByte);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FriendDetailActivity.this).toBundle());
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(FriendDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                if (TextUtils.isEmpty(etComment.getText().toString())) {
                    return;
                }
                FriendCircleComment(PinziApplication.getInstance().getLoginDto().getId(), friend.getId(), etComment.getText().toString());
            }
        });
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llFragmentThreeComment.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 图片 Adapter
     */
    class ImagesAdapter extends CommonAdapter<String> {

        /**
         * 构造方法
         *
         * @param context  上下文
         * @param layoutId 布局id
         * @param datas    数据源
         */
        public ImagesAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(CommonViewHolder holder, String imageUrl, int position) {
            Glide.with(mContext).load(imageUrl).into((ImageView) holder.itemView);
        }
    }

    /**
     * 获取朋友圈评论列表, 并按时间先后顺序排序.
     *
     * @param commentList
     * @return
     */
    private List<FriendTblCommentDto> getReplyList(List<FriendTblCommentDto> commentList) {
        List<FriendTblCommentDto> replyList = new ArrayList<>();
        if (commentList != null && commentList.size() > 0) {
            for (int i = 0; i < commentList.size(); i++) {
                FriendTblCommentDto comment = commentList.get(i);
                replyList.add(comment);
                List<FriendTblCommentDto> replyComments = comment.getReplyComment();
                if (replyComments != null && replyComments.size() > 0) {
                    replyList.addAll(replyComments);
                }
            }
        }
        Collections.sort(replyList, (o1, o2) ->
                new Date(Long.valueOf(o1.getCreateDate())).compareTo(new Date(Long.valueOf(o2.getCreateDate()))));
        return replyList;
    }


    /**
     * 初始化点赞 RecyclerView
     */
    public void initLikesRecyclerView(RecyclerView recyclerView, List<FriendlLikeDto> likes) {
        if (recyclerView != null && recyclerView.getTag() != null) {
            ((LikesAdapter) recyclerView.getTag()).updateLikeDataList(likes);
            return;
        }
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(FriendDetailActivity.this);
        // 设置主轴为水平方向, 从左往右
        layoutManager.setFlexDirection(FlexDirection.ROW);
        // 换行
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        // 设置副轴对其方式
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerView.setLayoutManager(layoutManager);
        LikesAdapter likesAdapter = new LikesAdapter(FriendDetailActivity.this, likes);
        recyclerView.setAdapter(likesAdapter);
        recyclerView.setTag(likesAdapter);
    }

    /**
     * 初始化评论 RecyclerView
     */
    public void initReplayRecyclerView(RecyclerView rvReply, List<FriendTblCommentDto> replays) {
        rvReply.setLayoutManager(new LinearLayoutManager(FriendDetailActivity.this));
        ReplyAdapter replyAdapter = new ReplyAdapter(FriendDetailActivity.this, R.layout.item_friend_detail_comment, replays);
        rvReply.setAdapter(replyAdapter);
        replyAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // TODO 点击回复
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvReply.setTag(replyAdapter);
    }

    /**
     * 图片 Adapter
     */
    class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private Context context;
        private List<FriendlLikeDto> likeDatas;

        public LikesAdapter(Context context, List<FriendlLikeDto> likeDatas) {
            this.context = context;
            this.likeDatas = likeDatas;
        }

        public void updateLikeDataList(List<FriendlLikeDto> likeDatas) {
            this.likeDatas.clear();
            this.likeDatas.addAll(likeDatas);
            this.notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_friends_detail_likes, parent, false);
            return new OtherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ImageViewHolder) {

            } else {
                OtherViewHolder viewHolder = (OtherViewHolder) holder;
                // 隐藏最后一个逗号.
                ImageView ivLikeHead = viewHolder.getView(R.id.iv_like_head);
                Glide
                        .with(context)
                        .load(likeDatas.get(position).getHeadImg())
                        .into(ivLikeHead);

            }
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return likeDatas.size();
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {

            public ImageViewHolder(View itemView) {
                super(itemView);
            }
        }

        class OtherViewHolder extends BaseViewHolder {

            public OtherViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BottomViewHolder extends BaseViewHolder {

        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews = new SparseArray<>();

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String text) {
            ((TextView) getView(id)).setText(text);
        }
    }

    /**
     * 图片 Adapter
     */
    class ReplyAdapter extends CommonAdapter<FriendTblCommentDto> {

        /**
         * 构造方法
         *
         * @param context  上下文
         * @param layoutId 布局id
         * @param datas    数据源
         */
        public ReplyAdapter(Context context, int layoutId, List<FriendTblCommentDto> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(CommonViewHolder holder, FriendTblCommentDto comment, int position) {


            ImageView ivHead = holder.getView(R.id.iv_head);
            TextView tvName = holder.getView(R.id.tv_name);
            TextView tvDate = holder.getView(R.id.tv_date);
            tvContent = holder.getView(R.id.tv_content);

            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (comment.getuId().equals(PinziApplication.getInstance().getLoginDto().getId())) {
                        Dialog dialog = new Dialog(FriendDetailActivity.this, R.style.dialogFullscreen);
                        dialog.setContentView(R.layout.dialog_bottom_layout);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams layoutParams = window.getAttributes();
                        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        layoutParams.dimAmount = 0.5f;
                        window.setGravity(Gravity.BOTTOM);
                        window.setAttributes(layoutParams);

                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
                            // TODO 删除
                            dialog.dismiss();
                            FriendCircleCommentDelete(comment.getId());
                        });
                        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                            dialog.dismiss();

                        });

                    } else {
                        awnser(comment.getId());
                    }
                }
            });
            tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //刪除評論
                    if (comment.getuId().equals(PinziApplication.getInstance().getLoginDto().getId())) {
                        Dialog dialog = new Dialog(FriendDetailActivity.this, R.style.dialogFullscreen);
                        dialog.setContentView(R.layout.dialog_bottom_layout);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams layoutParams = window.getAttributes();
                        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        layoutParams.dimAmount = 0.5f;
                        window.setGravity(Gravity.BOTTOM);
                        window.setAttributes(layoutParams);

                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
                            // TODO 删除
                            dialog.dismiss();
                            FriendCircleCommentDelete(comment.getId());
                        });
                        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                            dialog.dismiss();

                        });

                    }

                    return true;
                }
            });
//            String type = comment.getType();
            //评论
            if (comment.getParentId() == null || comment.getParentId().equals("0")) {
                //姓名
                tvName.setText(comment.getCommentUser().getName());
                //内容
                tvContent.setText(comment.getContent());
                //日期
                tvDate.setText(DateUtil.getTimeFormatText(new Date(Long.valueOf(comment.getCreateDate()))));
                //图片
                Glide
                        .with(mContext)
                        .load(comment.getCommentUser().getHeadImg())
                        .into(ivHead);


            } else {
                // 回复
                String name1 = comment.getReplyCommentUser().getName(); // 回复评论人的姓名
                String name2 = comment.getCommentUser().getName(); // 评论人姓名

                String content = comment.getContent();
                SpannableString ssName1 = new SpannableString(name1);
                ssName1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_c71233)),
                        0, name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString ssName2 = new SpannableString(name2);
                ssName2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_c71233)),
                        0, ssName2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString ssContent = new SpannableString(content);
                ssContent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_222222)),
                        0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                SpannableString ssText = new SpannableString(strText);
                ssContent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_222222)),
                        0, strText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append(ssName1);
                ssb.append(ssText);
                ssb.append(ssName2);
                //
                String ssbsStr = ssb.toString();
                SpannableString ssbsStrSpan = new SpannableString(ssbsStr);



                ssbsStrSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_c71233)),
                        0, name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssbsStrSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_c71233)),
                        name1.length() + 2,  name1.length() + 2+name2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssbsStrSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(FriendDetailActivity.this, R.color.color_222222)),
                        name1.length(), name1.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                //姓名组合
                tvName.setText(ssbsStrSpan);

                //内容
                tvContent.setText(ssContent);
                //日期
                tvDate.setText(DateUtil.getTimeFormatText(new Date(Long.valueOf(comment.getCreateDate()))));
                //图片
                Glide
                        .with(mContext)
                        .load(comment.getReplyCommentUser().getHeadImg())
                        .into(ivHead);

//
//
//                ssb.append(ssContent);
//                tvContent.setText(ssb);

            }
        }

    }

    private String strText = "回复";

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
                    getFriendDetai();

                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 回复
     * 回复的内容ID
     *
     * @param id
     */
    public void awnser(String id) {
        //評論回復
        showPopupWindow(friend.getId(), id);

    }

    /**
     * 朋友圈取消点赞 点赞ID
     *
     * @param friend
     * @return
     */
    private void FriendCircleCancelLike(FriendCircleListDto friend) {
        List<FriendlLikeDto> likeList = friend.getTblLikeList();
        //判断有么有已经点赞过
        if (EmptyUtils.isNotEmpty(likeList)) {
            for (int i = 0; i < likeList.size(); i++) {
                if (PinziApplication.getInstance().getLoginDto().getId().equals(likeList.get(i).getuId())) {
                    //取消点赞
                    viewModel.FriendCircleCancelLike(likeList.get(i).getId());
                    viewModel.getCancel().observe(this, new Observer<BaseDto<String>>() {
                        @Override
                        public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                            if (stringBaseDto.isSuccess()) {
                                //
                                getFriendDetai();
                                Log.e(TAG, "onChanged: 取消点赞成功");
                            } else {
                                ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                            }
                        }
                    });
                    break;
                }
            }
        }

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
                    getFriendDetai();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 朋友圈评论
     *
     * @param uId     用户ID
     * @param content string评论内容
     * @param fcId    string朋友圈ID
     */
    private void FriendCircleComment(String uId, String content, String fcId) {
        viewModel.FriendCircleComment(uId, content, fcId);
        viewModel.getComment().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    Log.e(TAG, "onChanged: 评论成功");
                    getFriendDetai();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
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
    private void CommentReplyAdd(String uId, String content, String fcId, String parentId) {
        viewModel.CommentReplyAdd(uId, content, fcId, parentId);
        viewModel.getCommentAdd().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    Log.e(TAG, "onChanged: 朋友圈评论回复成功");
                    getFriendDetai();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }
}
