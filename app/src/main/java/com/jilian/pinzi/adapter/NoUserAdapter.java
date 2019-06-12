package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyCouponDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class NoUserAdapter extends RecyclerView.Adapter<NoUserAdapter.ViewHolder> {
    private Activity mContext;
    private List<MyCouponDto> datas;
    private CustomItemClickListener listener;
    /** 0：未使用 1：已使用 2:已过期 */
    private int status;

    public NoUserAdapter(Activity context, List<MyCouponDto> datas, CustomItemClickListener listener, int status) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_no_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyCouponDto dto = datas.get(position);
        if (dto != null) {
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
                holder.tvUserPlatform.setText("适用平台：" + dto.getStoreName());
                holder.tvDay.setText("有效期限：" + dto.getValidityDate());

                if (status == 0) {
                    holder.llContainer.setBackgroundResource(R.drawable.image_nouse_bg);
                } else if (status == 1) {
                    holder.llContainer.setBackgroundResource(R.drawable.icon_coupon_used);
                } else {
                    holder.llContainer.setBackgroundResource(R.drawable.icon_coupon_expired);
                }
                holder.viewBg.setVisibility(status == 0 ? View.GONE : View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llContainer;
        private TextView tvName;
        private TextView tvUserPlatform;
        private TextView tvDay;
        private TextView tvDaller;
        private TextView tcCount;
        private TextView tvUserConditions;
        private TextView tvOk;
        private View viewBg;



        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            llContainer = itemView.findViewById(R.id.ll_item_no_user_container);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvUserPlatform = (TextView) itemView.findViewById(R.id.tv_user_platform);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            tvDaller = (TextView) itemView.findViewById(R.id.tv_daller);
            tcCount = (TextView) itemView.findViewById(R.id.tc_count);
            tvUserConditions = (TextView) itemView.findViewById(R.id.tv_user_conditions);
            tvOk = (TextView) itemView.findViewById(R.id.tv_ok);
            viewBg = itemView.findViewById(R.id.view_item_no_user_bg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
