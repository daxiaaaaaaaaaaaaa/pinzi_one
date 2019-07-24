package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.common.dto.FriendTblCommentDto;
import com.jilian.pinzi.common.dto.FriendlLikeDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.ui.AllWorksActivity;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.VideoPlayerActivity;
import com.jilian.pinzi.ui.friends.imagepager.ImagePagerActivity;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.views.RoundImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ningpan
 * @since 2018/11/21 20:20 <br>
 * description: 朋友圈 Adapter
 */
public class FriendsCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public int height = 0;
    private static final int HEADER_VIEW_TYPE = 200;
    private static final int BOTTOM_TYPE = 100;
    private static final int NORMAL_TYPE = 300;
    private final Context context;
    private Activity activity;

    private final List<FriendCircleListDto> datas;
    private FriendListener listener;

    public FriendsCircleAdapter(Context context, List<FriendCircleListDto> datas, FriendListener listener,Activity activity) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
        this.activity = activity;
    }

    public interface FriendListener {
        void comment(int position, View view);

        void driendCircleDelete(int position);

        void like(int position);

        void cancel(int position);

        /**
         * 回复评论ID
         *
         * @param id
         */
        void awnser(String id, View view);

        /**
         * 回复评论ID
         *
         * @param id
         */
        void delete(String id, View view);

        /**
         * 去我的朋友圈
         *
         * @param id
         */
        void toMineFriend(String name, String url, String id);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER_VIEW_TYPE) {
            View inflate = inflater.inflate(R.layout.item_header_friends_circle, parent, false);
            return new HeaderViewHolder(inflate);
        } else if (viewType == NORMAL_TYPE) {
            View inflate = inflater.inflate(R.layout.item_list_friends_circle, parent, false);
            return new ListViewHolder(inflate);
        } else {
            //設置高度
            View inflate = inflater.inflate(R.layout.item_list_friends_circle_bottom, parent, false);
            inflate.getLayoutParams().height = height;
            return new BottomViewHolder(inflate);
        }
    }

    private List<FriendTblCommentDto> replyList;
    private ReplyAdapter replyAdapter;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            RoundImageView ivHead = viewHolder.getView(R.id.iv_item_header_friends_circle_head);
            TextView tvName = viewHolder.getView(R.id.tv_name);
            ivHead.setOnClickListener(v -> {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                if (onItemClickListener != null) onItemClickListener.onHeadClick(v, position);
            });
            if (PinziApplication.getInstance().getLoginDto() != null) {
                Glide
                        .with(context)
                        .load(PinziApplication.getInstance().getLoginDto().getHeadImg())
                        .into(ivHead);
                tvName.setText(PinziApplication.getInstance().getLoginDto().getName());
            }

        }
        //列表
        else if (holder instanceof ListViewHolder) {
            ListViewHolder viewHolder = (ListViewHolder) holder;
            ImageView ivHead = viewHolder.getView(R.id.iv_head);
            TextView tvName = viewHolder.getView(R.id.tv_name);
            TextView tvContent = viewHolder.getView(R.id.tv_content);
            TextView tv_del = viewHolder.getView(R.id.tv_item_list_friends_del);
            RecyclerView rvLike = viewHolder.getView(R.id.rv_item_list_friends_like);
            RecyclerView rvReply = viewHolder.getView(R.id.rv_item_list_friends_replay);
            RecyclerView recyclerView = viewHolder.getView(R.id.rv_item_list_friends_images);

            RelativeLayout rlVideo = viewHolder.getView(R.id.rl_video);
            ImageView btnVideo = viewHolder.getView(R.id.btnVideo);
            ImageView ivStart = viewHolder.getView(R.id.iv_start);

            LoginDto loginDto = PinziApplication.getInstance().getLoginDto();

            if(!EmptyUtils.isNotEmpty(loginDto)||
                !EmptyUtils.isNotEmpty(loginDto.getId())
                    ||(!loginDto.getId().equals(datas.get(position).getuId())))
            tv_del.setVisibility(View.GONE);

            {
                tv_del.setVisibility(View.VISIBLE);
            }


            RelativeLayout rlCommentLike = viewHolder.getView(R.id.rl_comment_like);
            FriendCircleListDto friend = datas.get(position);
//            Glide
//                    .with(context)
//                    .load(friend.getHeadImg())
//                    .into(ivHead);

            Glide.with(context).
                    load(friend.getHeadImg()).error(R.drawable.ic_launcher_background) //异常时候显示的图片
                    .placeholder(R.drawable.ic_launcher_background) //加载成功前显示的图片
                    .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                    .into(ivHead);//在RequestBuilder 中使用自定义的ImageViewTarge

            tvName.setText(friend.getName());
            String releaseContent = friend.getContent();
            tvContent.setText(releaseContent);
            tvContent.setVisibility(TextUtils.isEmpty(releaseContent) ? View.GONE : View.VISIBLE);

            // 朋友圈发布的图片
            // TODO 模拟一些图片数据: 最多添加9张图片
            String url = friend.getVideo();
            if (!TextUtils.isEmpty(url)) {
                recyclerView.setVisibility(View.GONE);
                rlVideo.setVisibility(View.VISIBLE);
                btnVideo.setImageBitmap(friend.getBitmap());

            } else {
                List<String> images = getImageUrls(friend.getImgUrl());
                viewHolder.initImagesRecyclerView(R.id.rv_item_list_friends_images, images, position);
                recyclerView.setVisibility(View.VISIBLE);
                rlVideo.setVisibility(View.GONE);
            }
            rlVideo.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("url", datas.get(position).getVideo());
                    Bitmap bitmap = datas.get(position).getBitmap();
                    if(EmptyUtils.isNotEmpty(bitmap)){
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
                        byte [] bitmapByte =baos.toByteArray();
                        intent.putExtra("bitmap",bitmapByte);
                    }

                        // 添加跳转动画
                    context.startActivity(intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    (Activity) context,
                                    btnVideo,
                                    context.getString(R.string.share_str))
                                    .toBundle());

                }
            });


            // 设置发布时间
            TextView releaseTime = viewHolder.getView(R.id.tv_item_list_friends_minute);
            releaseTime.setText(DateUtil.getTimeFormatText(new Date(Long.valueOf(friend.getCreateDate()))));

            // 删除该朋友圈
            viewHolder.getView(R.id.tv_item_list_friends_del).setOnClickListener(v -> {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                Dialog dialog = new Dialog(context, R.style.dialogFullscreen);
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
                    listener.driendCircleDelete(position);
                });
                dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                    dialog.dismiss();

                });
            });
            ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PinziApplication.getInstance().getLoginDto() == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    listener.toMineFriend(datas.get(position).getName(), datas.get(position).getHeadImg(), datas.get(position).getuId());

                }
            });
            // 弹出点赞和评论 PopupWindow.
            rlCommentLike.setOnClickListener(v -> {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                View content = LayoutInflater.from(context).inflate(R.layout.popup_friends_msg, null, false);
                PopupWindow popupWindow = new PopupWindow(content,
                        ScreenUtils.dip2px(context, 180), ScreenUtils.dip2px(context, 40));
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
                List<FriendlLikeDto> likeList = datas.get(position).getTblLikeList();
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
                        listener.cancel(position);
                    } else {
                        listener.like(position);
                    }

                    // TODO 点赞
                });
                ll_comment.setOnClickListener(v1 -> {
                    // TODO 评论
                    popupWindow.dismiss();
                    //
                    if (datas.get(position).getTblCommentList() != null && datas.get(position).getTblCommentList().size() > 0) {
                        listener.comment(position, rvReply);
                    } else if (datas.get(position).getTblLikeList() != null && datas.get(position).getTblLikeList().size() > 0) {
                        listener.comment(position, rvLike);
                    } else {
                        listener.comment(position, rlCommentLike);
                    }

                });
            });
            // 初始化点赞列表
            List<FriendlLikeDto> likes = new ArrayList<>();
            likes.addAll(friend.getTblLikeList());
            if (likes.size() != 0) likes.add(0, null); // 添加红色桃心

            if (likes.size() > 1) {
                rvLike.setVisibility(View.VISIBLE);
                viewHolder.initLikesRecyclerView(rvLike, likes);
            } else {
                rvLike.setVisibility(View.GONE);
            }

            // 初始化评论列表
            replyList = new ArrayList<>();
            replyList.addAll(getReplyList(friend.getTblCommentList()));

            if (replyList.size() > 0) {
                rvReply.setVisibility(View.VISIBLE);
                viewHolder.initReplayRecyclerView(rvReply, replyList);
            } else {
                rvReply.setVisibility(View.GONE);
            }
            LinearLayout llReply = viewHolder.getView(R.id.ll_item_list_friends_reply);
            llReply.setVisibility(likes.size() == 0 && replyList.size() == 0 ? View.GONE : View.VISIBLE);
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW_TYPE;
        } else if (datas.get(position).getId() != null) {
            return NORMAL_TYPE;
        } else {
            return BOTTOM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
     * 图片 Adapter
     */
    class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int IMAGE_VIEW_TYPE = 200;

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
            if (viewType == IMAGE_VIEW_TYPE) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_friends_likes_image, parent, false);
                return new ImageViewHolder(view);
            }
            View view = LayoutInflater.from(context).inflate(R.layout.item_friends_likes, parent, false);
            return new OtherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ImageViewHolder) {

            } else {
                OtherViewHolder viewHolder = (OtherViewHolder) holder;
                // 隐藏最后一个逗号.
                View view = viewHolder.getView(R.id.tv_item_friends_likes_dot);
                TextView tvName = viewHolder.getView(R.id.tv_item_friends_likes_name);
                tvName.setText(likeDatas.get(position).getName());
                if (position == likeDatas.size() - 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return IMAGE_VIEW_TYPE;
            }
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
            TextView tvContent = holder.getView(R.id.tv_item_friends_replays);
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PinziApplication.getInstance().getLoginDto() == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    } else if (comment.getuId().equals(PinziApplication.getInstance().getLoginDto().getId())) {
                        Dialog dialog = new Dialog(context, R.style.dialogFullscreen);
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
                            listener.delete(comment.getId(), tvContent);
                        });
                        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                            dialog.dismiss();

                        });

                    } else {
                        listener.awnser(comment.getId(), tvContent);
                    }
                }
            });
            tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (PinziApplication.getInstance().getLoginDto() == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                    if (comment.getuId().equals(PinziApplication.getInstance().getLoginDto().getId())) {
                        Dialog dialog = new Dialog(context, R.style.dialogFullscreen);
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
                            listener.delete(comment.getId(), tvContent);
                        });
                        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                            dialog.dismiss();

                        });

                    }
                    return true;
                }
            });
//            String type = comment.getType();
            if (comment.getParentId() == null || comment.getParentId().equals("0")) {


                // 评论好友的朋友圈
                String name = comment.getCommentUser().getName() + ": ";
                String content = comment.getContent();
                SpannableString ssName = new SpannableString(name);
                ssName.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_c71233)),
                        0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString ssContent = new SpannableString(content);
                ssContent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_222222)),
                        0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append(ssName);
                ssb.append(ssContent);
                tvContent.setText(ssb);
            } else {
                // 评论好友的评论
                String name1 = comment.getReplyCommentUser().getName(); // 回复评论人的姓名
                String name2 = comment.getCommentUser().getName() + ": "; // 评论人姓名
                String content = comment.getContent();
                SpannableString ssName1 = new SpannableString(name1);
                ssName1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_c71233)),
                        0, name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString ssName2 = new SpannableString(name2);
                ssName2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_c71233)),
                        0, ssName2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString ssContent = new SpannableString(content);
                ssContent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_222222)),
                        0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append(ssName1);
                ssb.append("回复");
                ssb.append(ssName2);
                ssb.append(ssContent);
                tvContent.setText(ssb);

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

    class ListViewHolder extends BaseViewHolder {

        private RecyclerView rvImages;
        private ImagesAdapter imagesAdapter;
        private List<String> images;

        private RecyclerView rvLikes;
        private LikesAdapter likesAdapter;
        private List<FriendlLikeDto> likes;

        private RecyclerView rvReplays;
        private List<Map<String, String>> replays;

        public ListViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 初始化图片 RecyclerView
         */
        public void initImagesRecyclerView(int id, List<String> images, int friendCirclePosition) {
            rvImages = itemView.findViewById(id);
            rvImages.setLayoutManager(new GridLayoutManager(context, 3));
            this.images = images;
            imagesAdapter = new ImagesAdapter(itemView.getContext(), R.layout.item_friends_images, images);
            rvImages.setAdapter(imagesAdapter);
            imagesAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    // TODO 点击查看图片

                    Intent intent = new Intent(view.getContext(), ViewPhotosActivity.class);
//                    // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
//                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, getUrls(datas.get(friendCirclePosition).getImgUrl()));
//                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
//                    view.getContext().startActivity(intent);



                    intent.putExtra("url", datas.get(friendCirclePosition).getImgUrl());
                    intent.putExtra("position", position);

                    view.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());



                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

        /**
         * 初始化点赞 RecyclerView
         */
        public void initLikesRecyclerView(RecyclerView recyclerView, List<FriendlLikeDto> likes) {
            if (recyclerView != null && recyclerView.getTag() != null) {
                ((LikesAdapter) recyclerView.getTag()).updateLikeDataList(likes);
                return;
            }
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
            // 设置主轴为水平方向, 从左往右
            layoutManager.setFlexDirection(FlexDirection.ROW);
            // 换行
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            // 设置副轴对其方式
            layoutManager.setAlignItems(AlignItems.STRETCH);
            recyclerView.setLayoutManager(layoutManager);
            LikesAdapter likesAdapter = new LikesAdapter(itemView.getContext(), likes);
            recyclerView.setAdapter(likesAdapter);
            recyclerView.setTag(likesAdapter);
        }

        /**
         * 初始化评论 RecyclerView
         */
        public void initReplayRecyclerView(RecyclerView rvReply, List<FriendTblCommentDto> replays) {
            rvReply.setLayoutManager(new LinearLayoutManager(context));
            replyAdapter = new ReplyAdapter(itemView.getContext(), R.layout.item_friends_replays, replays);
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

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onHeadClick(View view, int position);
    }
}
