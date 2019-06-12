package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 订单列表
 */
public class MyApplyServiceAdapter extends RecyclerView.Adapter<MyApplyServiceAdapter.ViewHolder>  {
    private Activity mContext;
    private List<MyOrderDto> datas;
    private CustomItemClickListener listener;
    private MyApplyServiceGoodAdapter.GoodClickListener goodClickListener;

    public MyApplyServiceAdapter(Activity context, List<MyOrderDto> datas, CustomItemClickListener listener,MyApplyServiceGoodAdapter.GoodClickListener goodClickListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.goodClickListener = goodClickListener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_apply_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.rvGoods.setLayoutManager(linearLayoutManager);
        holder.rvGoods.setAdapter(new MyApplyServiceGoodAdapter(mContext,datas.get(position),getList(position) , goodClickListener));
        holder.tvOrderNo.setText("订单编号："+datas.get(position).getOrderNo());
        holder.tvTime.setText("时间:"+DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(datas.get(position).getCreateDate())));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null, position);
            }
        });

    }

    /**
     * 设置订单ID
     * @param position
     * @return
     */
    private List<MyOrderGoodsInfoDto> getList(int position) {
        List<MyOrderGoodsInfoDto> list = datas.get(position).getGoodsInfo();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOrderId(datas.get(position).getId());
        }
        return list;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderNo;
        private TextView tvTime;
        private RecyclerView rvGoods;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvOrderNo = (TextView) itemView.findViewById(R.id.tv_order_no);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            rvGoods = (RecyclerView)itemView. findViewById(R.id.rv_goods);


        }

    }
}
