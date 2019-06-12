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
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.IntegralMallActivity;

import java.util.List;


public class IntegralMallAdapter extends RecyclerView.Adapter<IntegralMallAdapter.ViewHolder> {
    private Activity mContext;
    private List<ScoreBuyGoodsDto> datas;
    private CustomItemClickListener listener;

    public IntegralMallAdapter(Activity context, List<ScoreBuyGoodsDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_integra_mall, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getName());
        holder.tvScore.setText(datas.get(position).getScore() + "积分");
        holder.tvStoreName.setText(datas.get(position).getStoreName());
        String url = datas.get(position).getFile();
        if (!TextUtils.isEmpty(url)) {
            if (url.contains(",")) {
                url = url.split(",")[0];
            }
            Glide.with(mContext).load(url).into(holder.ivPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvStoreName;
        private TextView tvScore;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStoreName = (TextView) itemView.findViewById(R.id.tv_storeName);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
