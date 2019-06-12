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
import com.jilian.pinzi.common.dto.InviteeDetailDto;
import com.jilian.pinzi.common.dto.InviteeDetailItem;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.Date;
import java.util.List;


public class MyLevelDetailAdapter extends RecyclerView.Adapter<MyLevelDetailAdapter.ViewHolder> {
    private Activity mContext;
    private List<InviteeDetailItem> datas;
    private CustomItemClickListener listener;

    public MyLevelDetailAdapter(Activity context, List<InviteeDetailItem> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_level_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLine.setVisibility(View.GONE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(datas.get(position).getName());
        holder.tvMonney.setText("奖励佣金："+NumberUtils.forMatNumber(datas.get(position).getCommission()));
        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getFile()))
                .into(holder.ivHead);
        holder.tvDay.setText("时间："+DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(datas.get(position).getPayDate())));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvMonney;
        private TextView tvDay;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMonney = (TextView) itemView.findViewById(R.id.tv_monney);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            vLine = (View)itemView. findViewById(R.id.v_line);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
