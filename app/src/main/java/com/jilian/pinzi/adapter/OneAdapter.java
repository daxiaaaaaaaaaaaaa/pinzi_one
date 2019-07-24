package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.TimeKillGoodsDto;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class OneAdapter extends RecyclerView.Adapter<OneAdapter.ViewHolder> {
    private Activity mContext;
    private List<SeckillPrefectureDto> datas;
    private OneListener listener;
    private int classes;

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public interface OneListener {
        void onItemOneClick(View view, int position);
    }

    public OneAdapter(Activity context, List<SeckillPrefectureDto> datas, OneListener listener, int classes) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.classes = classes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_one_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        ViewGroup.LayoutParams  lp = holder.llTop.getLayoutParams();
        lp.width = (ScreenUtils.getScreenWidth(mContext)- DisplayUtil.dip2px(mContext,45))/2;
        holder.llTop.setLayoutParams(lp);


        holder.tvName.setText(datas.get(position).getName());
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
        if(classes==2)
        {
            if (dto.getType() == 1) {
                holder.tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
            }
            if (dto.getType() == 2) {
                holder.tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(datas.get(position).getTerminalBuy()));
            }
            if (dto.getType() == 3) {
                holder.tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(datas.get(position).getChannelBuy()));
            }
            if (dto.getType() == 4) {
                holder.tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(datas.get(position).getFranchiseeBuy()));
            }
        }
        else{
            holder.tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
        }

        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getFile()))
                .into(holder.ivHead);
        holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getSeckillPrice()));
        holder.tvPerPrice.getPaint().setAntiAlias(true);//抗锯齿
        holder.tvPerPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llTop;
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvPerPrice;

        private LinearLayout llItemWidth;



        public ViewHolder(final View itemView, final OneListener listener) {
            super(itemView);

            llTop = (LinearLayout) itemView.findViewById(R.id.ll_top);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvPerPrice = (TextView) itemView.findViewById(R.id.tv_per_price);
            llItemWidth = (LinearLayout) itemView.findViewById(R.id.ll_item_width);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemOneClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
