package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.AllWorksActivity;
import com.jilian.pinzi.ui.VideoPlayerActivity;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CircularImageView;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class AllWorkAdapter extends RecyclerView.Adapter<AllWorkAdapter.ViewHolder> implements CustomItemClickListener, AllWorkPhotoAdapter.ClickPhotoListener {
    private Activity mContext;
    private List<ActivityProductDto> datas;
    private CustomItemClickListener listener;
    private ClickListener clickVideoListener;
    private int type;

    public AllWorkAdapter(Activity context, List<ActivityProductDto> datas, CustomItemClickListener listener, ClickListener clickVideoListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.clickVideoListener = clickVideoListener;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void clickPhoto(int position, List<String> datas) {
        Intent intent = new Intent(mContext, ViewPhotosActivity.class);
        intent.putExtra("url", getUrl(datas));
        intent.putExtra("position", position);
        mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
    }

    private String getUrl(List<String> datas) {
        String url = "";
        if (EmptyUtils.isNotEmpty(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                if (i != datas.size() - 1) {
                    url += datas.get(i) + ",";
                } else {
                    url += datas.get(i) + "";
                }
            }
        }
        return url;

    }

    public interface ClickListener {
        /**
         * 点击视频
         *
         * @param position
         */
        void clickVideo(int position);

        /**
         * 投票 或者 编辑
         *
         * @param position
         */
        void vote(int position,String text);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_work, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //判断是视频 还是 图片
        //图片
        if (EmptyUtils.isNotEmpty(datas.get(position).getPathUrl())) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.rlVideo.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
            HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
            stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(mContext, 15));//下间距
            stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(mContext, 15));//下间距
            if (holder.recyclerView.getItemDecorationCount() <= 0) {
                holder.recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
            }
            String imageUrl = datas.get(position).getPathUrl();
            List<String> urlList = new ArrayList<>();
            if (imageUrl.contains(",")) {
                urlList = Arrays.asList(imageUrl.split(","));
            } else {
                urlList.add(imageUrl);
            }
            holder.recyclerView.setAdapter(new AllWorkPhotoAdapter(mContext, urlList, this));
        } else if (EmptyUtils.isNotEmpty(datas.get(position).getVideo())) {
            holder.recyclerView.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.VISIBLE);
            holder.btnVideo.setImageBitmap(datas.get(position).getBitmap());


        } else {
            holder.recyclerView.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.GONE);
        }
        holder.rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra("url", datas.get(position).getVideo());
                Bitmap bitmap = datas.get(position).getBitmap();
                if(EmptyUtils.isNotEmpty(bitmap)){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bitmapByte = baos.toByteArray();
                    intent.putExtra("bitmap", bitmapByte);
                }


                // 添加跳转动画
                mContext.startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) mContext,
                                holder.btnVideo,
                                mContext.getString(R.string.share_str))
                                .toBundle());


            }
        });
        holder.tvName.setText(datas.get(position).getUserName());
        holder.tvContent.setText(datas.get(position).getContent());

        holder.tvCount.setText("得票：" + datas.get(position).getVoteNum());
        Glide.with(mContext).
                load(TextUtils.isEmpty(datas.get(position).getImgUrl())?datas.get(position).getHeadImg():datas.get(position).getImgUrl()).error(R.drawable.ic_launcher_background) //异常时候显示的图片
                .placeholder(R.drawable.ic_launcher_background) //加载成功前显示的图片
                .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .into(holder.ivHead);//在RequestBuilder 中使用自定义的ImageViewTarge

        holder.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickVideoListener.vote(position,holder.tvSend.getText().toString());
            }
        });
        if(PinziApplication.getInstance().getLoginDto()!=null){
            if(PinziApplication.getInstance().getLoginDto().getId().equals(datas.get(position).getuId())){
                holder.tvSend.setText("编辑");
                holder.tvSend.setVisibility(View.VISIBLE);
                holder.tvSend.setBackground(null);
            }
            else{
                if(datas.get(position).getIsCanVote()==0){
                    holder.tvSend.setVisibility(View.GONE);
                }
                else{
                    holder.tvSend.setVisibility(View.VISIBLE);

                }
                holder.tvSend.setText(datas.get(position).getIsVote() == 0 ? "投票" : "取消投票");
                holder.tvSend.setBackgroundResource(R.drawable.shape_input_one_bg);
            }
        }
        else{
            if(datas.get(position).getIsCanVote()==0){
                holder.tvSend.setVisibility(View.GONE);
            }
            else{
                holder.tvSend.setVisibility(View.VISIBLE);

            }
            holder.tvSend.setText(datas.get(position).getIsVote() == 0 ? "投票" : "取消投票");
            holder.tvSend.setBackgroundResource(R.drawable.shape_input_one_bg);
        }


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView ivHead;
        private TextView tvName;
        private TextView tvSend;
        private TextView tvCount;
        private TextView tvContent;
        private RecyclerView recyclerView;
        private ImageView btnVideo;
        private RelativeLayout rlVideo;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            btnVideo = (ImageView) itemView.findViewById(R.id.btnVideo);
            ivHead = (CircularImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSend = (TextView) itemView.findViewById(R.id.tv_send);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

            rlVideo = (RelativeLayout) itemView.findViewById(R.id.rl_video);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
