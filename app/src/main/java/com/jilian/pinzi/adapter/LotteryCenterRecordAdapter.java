package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LotteryRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class LotteryCenterRecordAdapter extends RecyclerView.Adapter<LotteryCenterRecordAdapter.ViewHolder> {
    private Activity mContext;
    private List<LotteryRecordDto> datas;
    private CustomItemClickListener listener;

    public LotteryCenterRecordAdapter(Activity context, List<LotteryRecordDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lotterycenterrecord, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLine.setVisibility(View.INVISIBLE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }
        holder.tvTime.setText(DateUtil.dateToString(
                DateUtil.DEFAULT_DATE_FORMATTER_MIN, new Date(datas.get(position).getCreateDate())));
        holder.tvResult.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View vLine;
        private TextView tvTime;
        private TextView tvResult;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvTime = itemView.findViewById(R.id.tv_lottery_record_time);
            tvResult = itemView.findViewById(R.id.tv_lottery_record_result);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
