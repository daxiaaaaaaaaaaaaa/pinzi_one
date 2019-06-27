package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class ShopCardCShopAdapter extends RecyclerView.Adapter<ShopCardCShopAdapter.ViewHolder> {
    private Activity mContext;
    private List<StoreCouponDto> datas;
    private CustomItemClickListener listener;
    private ToReceiveListener toReceiveListener;

    public interface ToReceiveListener {
        void toReceive(int position);

        void toBuy(int position);
    }

    public ShopCardCShopAdapter(Activity context, List<StoreCouponDto> datas, CustomItemClickListener listener, ToReceiveListener toReceiveListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.toReceiveListener = toReceiveListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_get_card_shop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        StoreCouponDto dto = datas.get(position);
        //先判断价格
        //免费的
        if (datas.get(position).getPrice() <= 0) {
            //判断是否已经领取
            //未领取
            if (datas.get(position).getIsGet() <= 0) {
                holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round);
                holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tvOk.setText("领取");
                holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            }
            //已领取
            else {
                holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round_dark);
                holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvOk.setText("已领取");
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));

            }
        }
        //需要购买的
        else {
            //未购买
            if (datas.get(position).getIsGet() <= 0) {
                holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round);
                holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tvOk.setText("购买");
                holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
                holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            }
            //已购买
            else {
                holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round_dark);
                holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvOk.setText("已购买");
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
                holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));

            }
        }
        holder.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.tvOk.getText().toString()){
                    case "购买":
                        toReceiveListener.toBuy(position);
                        break;
                    case "领取":
                        toReceiveListener.toReceive(position);
                        break;
                }
            }
        });

        //先 判断是代金券 还是 优惠券
        //优惠券类型（1.折扣劵 2.代金券）
        Integer type = dto.getType();
        if (type != null) {
            if (type == 1) {
                //折扣劵
                holder.tvUserConditions.setVisibility(View.INVISIBLE);
                //折扣
                String moneyOrDiscount = dto.getMoneyOrDiscount();
                holder.tcCount.setText(moneyOrDiscount + "折");
                holder.tvDaller.setVisibility(View.GONE);
            } else {
                //代金券
                holder.tvUserConditions.setVisibility(View.VISIBLE);
                //使用条件
                holder.tvUserConditions.setText("满" + dto.getFullReduct() + "元可用");
                //面额
                String moneyOrDiscount = dto.getMoneyOrDiscount();
                holder.tcCount.setText(moneyOrDiscount);
                holder.tvDaller.setVisibility(View.VISIBLE);
            }

            holder.tvName.setText(dto.getName());
//            if(dto.getApplyPlatform()==0){
//                holder.tvUserPlatform.setText("适用平台：" + "全平台");
//            }
//            if(dto.getApplyPlatform()==1){
            holder.tvUserPlatform.setText("适用平台：" + "指定店铺");
            //}

            if (TextUtils.isEmpty(dto.getValidityDate())) {
                holder.tvDay.setText("有效期限：");
            } else {
                holder.tvDay.setText("有效期限：" + DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date(Long.parseLong(dto.getValidityDate()))));
            }

        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvUserPlatform;
        private TextView tvDay;
        private TextView tvDaller;
        private TextView tcCount;
        private TextView tvUserConditions;
        private TextView tvOk;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvUserPlatform = (TextView) itemView.findViewById(R.id.tv_user_platform);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            tvDaller = (TextView) itemView.findViewById(R.id.tv_daller);
            tcCount = (TextView) itemView.findViewById(R.id.tc_count);
            tvUserConditions = (TextView) itemView.findViewById(R.id.tv_user_conditions);
            tvOk = (TextView) itemView.findViewById(R.id.tv_ok);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });

        }

    }
}
