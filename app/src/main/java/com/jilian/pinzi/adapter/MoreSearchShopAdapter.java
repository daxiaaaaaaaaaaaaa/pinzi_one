package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.HotWordSelectBusinessDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.GlideCircleTransform;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MoreSearchShopAdapter extends RecyclerView.Adapter<MoreSearchShopAdapter.ViewHolder> {
    private Activity mContext;
    private List<HotWordSelectBusinessDto> datas;
    private CustomItemClickListener listener;

    public MoreSearchShopAdapter(Activity context, List<HotWordSelectBusinessDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_more_shop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getImgUrl()))
                .into(holder.ivHead);
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPoint.setText(datas.get(position).getGrade()+"分");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPoint;
        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPoint = (TextView) itemView.findViewById(R.id.tv_point);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemView,getAdapterPosition());
                }
            });

        }

    }
}
