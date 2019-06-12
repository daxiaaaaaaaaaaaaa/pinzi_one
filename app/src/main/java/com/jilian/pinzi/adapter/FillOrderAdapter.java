package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class FillOrderAdapter extends RecyclerView.Adapter<FillOrderAdapter.ViewHolder> {
    private Activity mContext;
    private List<OrderGoodsDto> datas;
    private CilckGoodListener listener;
    private int goodTypes;//商品类型 0 价格商品 1  积分商品
    private int classes;//1 普通价格 2 采购中心价格
    public int getGoodTypes() {
        return goodTypes;
    }

    public void setGoodTypes(int goodTypes) {
        this.goodTypes = goodTypes;
    }

    public interface  CilckGoodListener{
        void clickGoods(View view, int position);
    }

    public FillOrderAdapter(Activity context, List<OrderGoodsDto> datas, CilckGoodListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_order_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }
    LoginDto dto = PinziApplication.getInstance().getLoginDto();
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLine.setVisibility(View.GONE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(datas.get(position).getName());
        holder.tvCount.setText("x" + datas.get(position).getCount());
        holder.tvPrePrice.getPaint().setAntiAlias(true);//抗锯齿
        holder.tvPrePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        if(!TextUtils.isEmpty(datas.get(position).getFile())){
            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .into(holder.ivHead);
        }

        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
        if(goodTypes==1){
            //价格
            holder.tvPrice.setText(datas.get(position).getScore()+"积分");
            holder.tvPrePrice.setText("￥"+ NumberUtils.forMatNumber(datas.get(position).getPrice()));
        }
        else{
            if(EmptyUtils.isNotEmpty(datas.get(position).getPrice())){
                holder.tvPrice.setText("￥"+ NumberUtils.forMatNumber(datas.get(position).getPrice()));
            }

       }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View vLine;
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvCount;
        private TextView tvPrePrice;



        public ViewHolder(final View itemView, final CilckGoodListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvPrePrice = (TextView) itemView.findViewById(R.id.tv_pre_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickGoods(itemView, getAdapterPosition());
                }
            });
        }

    }
}
