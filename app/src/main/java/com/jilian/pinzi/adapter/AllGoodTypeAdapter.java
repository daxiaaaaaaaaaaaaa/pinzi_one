package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.GoodsTypeDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CircularImageView;

import java.util.List;


public class AllGoodTypeAdapter extends RecyclerView.Adapter<AllGoodTypeAdapter.ViewHolder> {
    private Activity mContext;
    private List<GoodsTypeDto> datas;
    private CustomItemClickListener listener;

    public AllGoodTypeAdapter(Activity context, List<GoodsTypeDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_goos_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getName());
        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getImgUrl()))
                .error(R.drawable.image_type_default)
                .into(holder.ivHead);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView ivHead;
        private TextView tvName;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivHead = (CircularImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
