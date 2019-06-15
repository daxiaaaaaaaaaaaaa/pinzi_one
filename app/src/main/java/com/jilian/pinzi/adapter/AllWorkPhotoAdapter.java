package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.views.CircularImageView;

import java.util.List;


public class AllWorkPhotoAdapter extends RecyclerView.Adapter<AllWorkPhotoAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private CustomItemClickListener listener;

    public AllWorkPhotoAdapter(Activity context, List<String> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams  lp = holder.ivHead.getLayoutParams();
        lp.width = (ScreenUtils.getScreenWidth(mContext)- DisplayUtil.dip2px(mContext,60))/3;
        lp.height = (ScreenUtils.getScreenWidth(mContext)- DisplayUtil.dip2px(mContext,60))/3;
        holder.ivHead.setLayoutParams(lp);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
