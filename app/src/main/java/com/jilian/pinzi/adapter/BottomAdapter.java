package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.GoodsEvaluateDto;
import com.jilian.pinzi.common.dto.GoodsEvaluateItem;
import com.jilian.pinzi.common.vo.DeleteAdressVo;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.GlideCircleTransform;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.ViewHolder> implements CommentPhotoAdapter.CommentPhotoClickListener {
    private Activity mContext;
    private List<GoodsEvaluateItem> datas;
    private CustomItemClickListener listener;

    public BottomAdapter(Activity context, List<GoodsEvaluateItem> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bottom, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    private CommentPhotoAdapter adapter;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        holder.rvPhoto.setLayoutManager(gridLayoutManager);
//            HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
//            stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(mContext, 15));//右间距
//            stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(mContext, 15));//下间距
        getUrlList(datas.get(position).getImgUrl());
        //   holder.rvPhoto.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        adapter = new CommentPhotoAdapter(mContext, getUrlList(datas.get(position).getImgUrl()), this);
        holder.rvPhoto.setAdapter(adapter);
        Glide.with(mContext).load(datas.get(position).getHeadImg()).centerCrop()
                .transform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.ivHead);
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPositionCity.setText(datas.get(position).getProfession() + "/" + datas.get(position).getCity());
        holder.tvContent.setText(datas.get(position).getContent());
        holder.tvCommentCount.setText(datas.get(position).getCommentNum());
        holder.tvLikeCount.setText(datas.get(position).getLikeNum());
        holder.tvDay.setText(DateUtil.dateToString("yyyy-MM-dd", new Date(datas.get(position).getCreateDate())));

    }

    /**
     * 获取评论图片
     *
     * @param url
     * @return
     */
    private List<String> files;

    private List<String> getUrlList(String url) {
        files = new ArrayList<>();
        if (EmptyUtils.isNotEmpty(url)) {
            if (url.contains(",")) {
                files = new ArrayList<>(Arrays.asList(url.split(",")));
            } else {
                files = new ArrayList<>();
                files.add(url);
            }
        }
        return files;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    public void clickPhoto(int position, List<String> datas) {
        Intent intent = new Intent(mContext, ViewPhotosActivity.class);
        intent.putExtra("url", getUrl(datas));
        intent.putExtra("position", position);
        mContext.startActivity(intent);
    }

    private String getUrl(List<String> datas) {
        String url = "";
        if (EmptyUtils.isNotEmpty(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                if (i != datas.size() - 1) {
                    url += datas.get(i) + ",";
                }
                else{
                    url += datas.get(i) + "";
                }
            }
        }
        return url;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPositionCity;
        private RecyclerView rvPhoto;
        private TextView tvContent;
        private TextView tvDay;
        private TextView tvCommentCount;
        private TextView tvLikeCount;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPositionCity = (TextView) itemView.findViewById(R.id.tv_position_city);
            rvPhoto = (RecyclerView) itemView.findViewById(R.id.rv_photo);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
