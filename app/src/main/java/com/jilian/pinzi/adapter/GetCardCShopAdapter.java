package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class GetCardCShopAdapter extends RecyclerView.Adapter<GetCardCShopAdapter.ViewHolder> {
    private Activity mContext;
    private List<CouponCentreDto> datas;
    private CustomItemClickListener listener;
    private ToReceiveListener toReceiveListener;

    public interface ToReceiveListener {
        void toReceive(int position);
    }

    public GetCardCShopAdapter(Activity context, List<CouponCentreDto> datas, CustomItemClickListener listener, ToReceiveListener toReceiveListener) {
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
        if(position==0){
            holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round);
            holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tvOk.setText("领取");
            holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
        }
        if(position==1){
            holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round_dark);
            holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
            holder.tvOk.setText("已领取");
            holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
            holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
            holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
            holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));


        }
        if(position==2){
            holder.tvOk.setBackgroundResource(R.drawable.shape_input_0round);
            holder.tvOk.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tvOk.setText("购买");
            holder.tvUserConditions.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tvDaller.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.tcCount.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
        }
//        CouponCentreDto dto = datas.get(position);
//        if (dto != null) {
//            //先 判断是代金券 还是 优惠券
//            //优惠券类型（1.折扣劵 2.代金券）
//            Integer type = dto.getType();
//            if (type != null) {
//                if (type == 1) {
//                    //折扣劵
//                    holder.tvUserConditions.setVisibility(View.INVISIBLE);
//                    //折扣
//                    String moneyOrDiscount = dto.getMoneyOrDiscount();
//                    holder.tcCount.setText(moneyOrDiscount + "折");
//                    holder.tvDaller.setVisibility(View.GONE);
//                } else {
//                    //代金券
//                    holder.tvUserConditions.setVisibility(View.VISIBLE);
//                    //使用条件
//                    holder.tvUserConditions.setText("满" + dto.getFullReduct() + "元可用");
//                    //面额
//                    String moneyOrDiscount = dto.getMoneyOrDiscount();
//                    holder.tcCount.setText(moneyOrDiscount);
//                    holder.tvDaller.setVisibility(View.VISIBLE);
//                }
//
//                holder.tvName.setText(dto.getName());
//                if(dto.getApplyPlatform()==0){
//                    holder.tvUserPlatform.setText("适用平台：" + "全平台");
//                }
//                if(dto.getApplyPlatform()==1){
//                    holder.tvUserPlatform.setText("适用平台：" + "指定店铺");
//                }
//
//                holder.tvDay.setText("有效期限：" + DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date(Long.parseLong(dto.getValidityDate()))));
//            }
//
            holder.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toReceiveListener.toReceive(position);
                }
            });
//        }
//

    }

    @Override
    public int getItemCount() {
        return 5;
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
