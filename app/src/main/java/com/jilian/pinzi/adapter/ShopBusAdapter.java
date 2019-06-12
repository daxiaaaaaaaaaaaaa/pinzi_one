package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.ShopCartLisDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class ShopBusAdapter extends RecyclerView.Adapter<ShopBusAdapter.ViewHolder> {
    private Activity mContext;
    private List<ShopCartLisDto> datas;
    private CustomItemClickListener listener;
    private  AddOrDelListener addOrDelListener;
    public interface  AddOrDelListener{
        void add(int position);
        void del(int position);
        void check(int position);
    }
    public ShopBusAdapter(Activity context, List<ShopCartLisDto> datas, CustomItemClickListener listener,AddOrDelListener addOrDelListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.addOrDelListener = addOrDelListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_bus, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLine.setVisibility(View.VISIBLE);
        } else {
            holder.vLine.setVisibility(View.GONE);
        }

        holder.tvName.setText(datas.get(position).getName());

        holder.tvCount.setText(String.valueOf(datas.get(position).getQuantity()));

        if(datas.get(position).getSeckillPrice()==0){
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getFastPrice()));
        }
        else{
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getSeckillPrice()));
        }

        holder.tvStoreName.setText(datas.get(position).getStoreName());

        try {
            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .into(holder.ivHead);
        } catch (Exception e) {

        }
        if(datas.get(position).isChecked()){
            holder.ivCheck.setImageResource(R.drawable.image_checked);
        }
        else{
            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
        }
        holder.rlChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrDelListener.check(position);
            }
        });
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrDelListener.add(position);
            }
        });
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrDelListener.del(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCheck;
        private View vLine;
        private RelativeLayout rlChecked;
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvOne;
        private TextView tvPrice;
        private ImageView tvAdd;
        private TextView tvCount;
        private ImageView tvDel;
        private TextView tvStoreName;




        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvStoreName = (TextView)itemView. findViewById(R.id.tv_storeName);
            vLine = (View) itemView.findViewById(R.id.v_line);
            rlChecked = (RelativeLayout) itemView.findViewById(R.id.rl_checked);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvOne = (TextView) itemView.findViewById(R.id.tv_one);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvAdd = (ImageView) itemView.findViewById(R.id.tv_add);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvDel = (ImageView) itemView.findViewById(R.id.tv_del);
            ivCheck = (ImageView)itemView.findViewById(R.id.iv_check);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
