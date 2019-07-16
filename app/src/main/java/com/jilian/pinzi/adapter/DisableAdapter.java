package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.SelectCardDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class DisableAdapter extends RecyclerView.Adapter<DisableAdapter.ViewHolder> {
    private Activity mContext;
    private List<SelectCardDto> datas;
    private CustomItemClickListener listener;
    private CheckListener checkListener;
    public interface  CheckListener{
        void checkClick(int position);
    }
    public DisableAdapter(Activity context, List<SelectCardDto> datas, CustomItemClickListener listener,CheckListener checkListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.checkListener = checkListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_disable_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SelectCardDto dto = datas.get(position);
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
                holder.tvDay.setText("有效期限：" + (dto.getValidityDate()==null?"":dto.getValidityDate()));
            }
        }
        holder.rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.checkClick(position);
            }
        });
//        if(datas.get(position).isCheck()){
//            holder.ivCheck.setImageResource(R.drawable.image_checked);
//        }
//        else{
//            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
//        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;

        private ImageView ivCheck;
        private TextView tvName;
        private TextView tvUserPlatform;
        private TextView tvDay;
        private TextView tvDaller;
        private TextView tcCount;
        private TextView tvUserConditions;
        private TextView tvOk;
        private RelativeLayout rlCheck;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvUserPlatform = (TextView) itemView.findViewById(R.id.tv_user_platform);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            tvDaller = (TextView) itemView.findViewById(R.id.tv_daller);
            tcCount = (TextView) itemView.findViewById(R.id.tc_count);
            tvUserConditions = (TextView) itemView.findViewById(R.id.tv_user_conditions);
            tvOk = (TextView) itemView.findViewById(R.id.tv_ok);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
            rlCheck = (RelativeLayout) itemView.findViewById(R.id.rl_check);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
