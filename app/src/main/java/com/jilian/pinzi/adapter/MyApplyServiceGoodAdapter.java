package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;

/**
 * 订单商品的列表
 */

public class MyApplyServiceGoodAdapter extends RecyclerView.Adapter<MyApplyServiceGoodAdapter.ViewHolder> {
    private Activity mContext;
    private List<MyOrderGoodsInfoDto> datas;
    private GoodClickListener listener;
    private MyOrderDto dto;
    public interface  GoodClickListener{
        void clickGoods(MyOrderDto dto);
        void apply(MyOrderGoodsInfoDto dto);
    }
    public MyApplyServiceGoodAdapter(Activity context, MyOrderDto dto, List<MyOrderGoodsInfoDto> datas, GoodClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.dto =dto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_apply_service_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLineTwo.setVisibility(View.GONE);
        } else {
            holder.vLineTwo.setVisibility(View.VISIBLE);
        }
        //超过退货日期
        if(dto.getIsReturn()==1){
            holder.tvRight.setBackgroundResource(R.drawable.shape_ok_dark);
            holder.tvOut.setText("该商品已超过售后期");
            holder.tvOut.setVisibility(View.VISIBLE);
            holder.ivOut.setVisibility(View.VISIBLE);
        }
        //未超过 退货日期
        else{

            //已经申请了退货
            if(datas.get(position).getIsApply()==1){
                holder.tvRight.setBackgroundResource(R.drawable.shape_ok_dark);
                holder.tvOut.setText("该商品已申请退货");
                holder.tvOut.setVisibility(View.VISIBLE);
                holder.ivOut.setVisibility(View.VISIBLE);
            }
            //未申请退货
            else{
                holder.tvOut.setVisibility(View.GONE);
                holder.ivOut.setVisibility(View.GONE);
                holder.tvRight.setBackgroundResource(R.drawable.shape_ok_red);
            }
        }

        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getFile()))
                .into(holder.ivHead);
        holder.tvCount.setText("X"+datas.get(position).getQuantity());
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getGoodsPrice()));

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
        private TextView tvRight;
        private View vLineTwo;
        private ImageView ivOut;
        private TextView tvOut;








        public ViewHolder(final View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvRight = (TextView) itemView.findViewById(R.id.tv_right);
            vLine = (View) itemView.findViewById(R.id.v_line);
            vLineTwo = (View) itemView.findViewById(R.id.v_line_two);

            ivOut = (ImageView) itemView.findViewById(R.id.iv_out);
            tvOut = (TextView) itemView.findViewById(R.id.tv_out);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickGoods(dto);
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.apply(datas.get(getAdapterPosition()));
                }
            });
        }

    }
}
