package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyTntegralRecordAdapter extends RecyclerView.Adapter<MyTntegralRecordAdapter.ViewHolder> {
    private Activity mContext;
    private List<MyRecordDto> datas;
    private CustomItemClickListener listener;

    public MyTntegralRecordAdapter(Activity context, List<MyRecordDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mytntegral_record, parent, false);
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
        if (datas.get(position).isShowDay()) {
            holder.rlDay.setVisibility(View.VISIBLE);
            holder.tvDay.setText(datas.get(position).getDay());
            holder.tvGetCount.setText(NumberUtils.forMatNumber(datas.get(position).getGetCount()));
            holder.tvUseCount.setText(NumberUtils.forMatNumber(datas.get(position).getUseCount()));
        } else {
            holder.rlDay.setVisibility(View.GONE);
        }

        holder.tvName.setText(datas.get(position).getTitle());
        holder.tvCount.setText((datas.get(position).getStatus() == 1 ? "+" : "-") + NumberUtils.forMatNumber(datas.get(position).getSource()));
        holder.tvDate.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN, new Date(datas.get(position).getCreateDate())));


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;
        private TextView tvDay;
        private RelativeLayout rlDay;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCount;
        private TextView tvGetCount;
        private TextView tvUseCount;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            rlDay = (RelativeLayout) itemView.findViewById(R.id.rl_day);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvGetCount = (TextView) itemView.findViewById(R.id.tv_getCount);
            tvUseCount = (TextView) itemView.findViewById(R.id.tv_useCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
