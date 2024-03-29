package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.BuyerCenterGoodsDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;

import io.reactivex.internal.fuseable.HasUpstreamObservableSource;


public class GoodTypeAdapter extends RecyclerView.Adapter<GoodTypeAdapter.ViewHolder> {
    private Activity mContext;
    private List<BuyerCenterGoodsDto> datas;
    private CustomItemClickListener listener;
    private int classes;

    public GoodTypeAdapter(Activity context, List<BuyerCenterGoodsDto> datas, CustomItemClickListener listener, int classes) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.classes = classes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goos_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getName());
        holder.tvStoreName.setText(datas.get(position).getStoreName());
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
        if (classes == 2) {
            if (dto.getType() == 1) {
                holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
            }
            if (dto.getType() == 2) {
                holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getTerminalBuy()));
            }
            if (dto.getType() == 3) {
                holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getChannelBuy()));
            }
            if (dto.getType() == 4) {
                holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getFranchiseeBuy()));
            }
        } else if (classes == 3) {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getFranchiseeBuy()));
        } else if (classes == 4) {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getChannelBuy()));
        } else if (classes == 5) {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getTerminalBuy()));
        } else if (classes == 6) {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
        } else {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
        }

        try {
            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .into(holder.ivHead);
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvStoreName;
        private TextView tvPrice;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStoreName = (TextView) itemView.findViewById(R.id.tv_storeName);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
