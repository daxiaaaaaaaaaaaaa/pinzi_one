package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CircularImageView;
import com.jilian.pinzi.views.GlideCircleTransform;

import java.util.List;


public class CommentHeadAdapter extends RecyclerView.Adapter<CommentHeadAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private CommentHeadListener listener;

    public interface CommentHeadListener {
        void clickCommentHead(View view, int position);
    }

    public CommentHeadAdapter(Activity context, List<String> datas, CommentHeadListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_commenthead, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            Glide
                    .with(mContext)
                    .load(R.drawable.image_news_dynamic)
                    .into(holder.ivHead);

        } else {


            //不带白色边框的圆形图片加载
            Glide.with(mContext).load(UrlUtils.getUrl(datas.get(position))).centerCrop()
                    .transform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.ivHead);
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;

        public ViewHolder(final View itemView, final CommentHeadListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickCommentHead(itemView, getAdapterPosition());
                }
            });
        }

    }
}
