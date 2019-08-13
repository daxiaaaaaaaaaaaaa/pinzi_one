package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.TimeKillGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class SecondsKillZoneAdapter extends RecyclerView.Adapter<SecondsKillZoneAdapter.ViewHolder> {
    private Activity mContext;
    private List<SeckillPrefectureDto> datas;
    private CustomItemClickListener listener;
    private int classes;
    public SecondsKillZoneAdapter(Activity context, List<SeckillPrefectureDto> datas, CustomItemClickListener listener,int classes) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.classes = classes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_kill_zoner_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getName());
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
        if(dto==null){
            holder.tvPrePrice.setText("原价¥" + datas.get(position).getPersonBuy());
        }
        else{
            if(classes==2){
                if (dto.getType() == 1) {
                    holder.tvPrePrice.setText("原价¥" + datas.get(position).getPersonBuy());
                }
                if (dto.getType() == 2) {
                    holder.tvPrePrice.setText("原价¥" + datas.get(position).getTerminalBuy());
                }
                if (dto.getType() == 3) {
                    holder.tvPrePrice.setText("原价¥" + datas.get(position).getChannelBuy());
                }
                if (dto.getType() == 4) {
                    holder.tvPrePrice.setText("原价¥" + datas.get(position).getFranchiseeBuy());
                }
            }
            else{
                holder.tvPrePrice.setText("原价¥" + datas.get(position).getPersonBuy());
            }

        }

        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getFile()))
                .into(holder.ivHead);
        holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getSeckillPrice()));
        holder.tvPrePrice.getPaint().setAntiAlias(true);//抗锯齿
        holder.tvPrePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.tvCount.setText("仅剩" + datas.get(position).getResidueQuantity() + "件");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPrePrice;
        private TextView tvPrice;
        private TextView tvCount;
        private TextView tvGet;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrePrice = (TextView) itemView.findViewById(R.id.tv_pre_price);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvGet = (TextView) itemView.findViewById(R.id.tv_get);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
