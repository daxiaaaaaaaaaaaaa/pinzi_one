package com.jilian.pinzi.adapter;

import android.app.Activity;
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
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.ScreenUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class FourAdapter extends RecyclerView.Adapter<FourAdapter.ViewHolder> {
    private Activity mContext;
    private List<MainRecommendDto> datas;
    private FourListener listener;
    public interface FourListener{
        void onItemFourClick(View view, int position);
    }
    public FourAdapter(Activity context, List<MainRecommendDto> datas, FourListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_four_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams  lp = holder.llTop.getLayoutParams();
        lp.width = (ScreenUtils.getScreenWidth(mContext)- DisplayUtil.dip2px(mContext,45))/2;
        holder.llTop.setLayoutParams(lp);
        holder.tvName.setText(datas.get(position).getName());
        holder.tvStoreName.setText(datas.get(position).getStoreName());
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
//        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
//        if (dto.getType()==1) {
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
//        }
//        if (dto.getType()==2) {
//            holder.tvPrice.setText(datas.get(position).getTerminalBuy());
//        }
//        if (dto.getType()==3) {
//            holder.tvPrice.setText(datas.get(position).getChannelBuy());
//        }
//        if (dto.getType()==4) {
//            holder.tvPrice.setText(datas.get(position).getFranchiseeBuy());
//        }

            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .into(holder.ivHead);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llTop;

        private ImageView ivHead;
        private TextView tvName;
        private TextView tvStoreName;
        private TextView tvPrice;

        public ViewHolder(final View itemView, final FourListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStoreName = (TextView) itemView.findViewById(R.id.tv_storeName);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            llTop = (LinearLayout) itemView.findViewById(R.id.ll_top);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemFourClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
