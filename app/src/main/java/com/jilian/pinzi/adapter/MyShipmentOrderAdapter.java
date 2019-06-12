package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.common.dto.ShippmentDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;


public class MyShipmentOrderAdapter extends RecyclerView.Adapter<MyShipmentOrderAdapter.ViewHolder> implements MyShipmentGoodAdapter.GoodClickListener {
    private Activity mContext;
    private List<ShippmentDto> datas;
    private CustomItemClickListener listener;
    private SendListener sendListener;
    public MyShipmentOrderAdapter(Activity context, List<ShippmentDto> datas, CustomItemClickListener listener,SendListener sendListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.sendListener =sendListener;


    }

    @Override
    public void clickGoods(String goodId) {
        Intent intent  = new Intent(mContext,GoodsDetailActivity.class);
        intent.putExtra("goodsId",goodId);
        mContext.startActivity(intent);
    }

    public  interface SendListener{
        /**
         * 发货
         * @param position
         */
        void send(int position);

        /**
         * 自送发货
         * @param position
         */
        void mySend(int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myshipmentorder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.rvGoods.setLayoutManager(linearLayoutManager);
        holder.rvGoods.setAdapter(new MyShipmentGoodAdapter(mContext,datas.get(position).getGoodsInfo(),this));
        holder.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListener.send(position);
            }
        });
        holder.tvMySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListener.mySend(position);
            }
        });
        holder.tvOrdeNo.setText("订单编号："+datas.get(position).getOrderNo());
        holder.tvCount.setText(NumberUtils.forMatNumber(datas.get(position).getPayMoney()));

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvGoods;
        private TextView tvOrdeNo;
        private TextView tvCount;
        private TextView tvMySend;




        private TextView tvSend;





        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            rvGoods = (RecyclerView) itemView.findViewById(R.id.rv_goods);
            tvMySend = (TextView) itemView.findViewById(R.id.tv_my_send);
            tvOrdeNo = (TextView) itemView.findViewById(R.id.tv_ordeNo);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvSend = (TextView) itemView.findViewById(R.id.tv_send);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
