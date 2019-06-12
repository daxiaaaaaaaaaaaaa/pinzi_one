package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class CommentPhotoAdapter extends RecyclerView.Adapter<CommentPhotoAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private CommentPhotoClickListener listener;

    public interface CommentPhotoClickListener {
        void clickPhoto(int position,List<String> datas);

    }

    public CommentPhotoAdapter(Activity context, List<String> datas, CommentPhotoClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position)))
                .into(holder.ivHead);
        holder.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickPhoto(position,datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHead;


        public ViewHolder(final View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

        }

    }
}
