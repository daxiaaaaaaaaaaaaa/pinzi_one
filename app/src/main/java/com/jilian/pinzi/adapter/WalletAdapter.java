package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.NumberUtils;

import java.util.Date;
import java.util.List;


public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Activity mContext;
    private List<MyRecordDto> datas;
    private CustomItemClickListener listener;

    public WalletAdapter(Activity context, List<MyRecordDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wallet, parent, false);
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
        holder.tvName.setText(datas.get(position).getTitle());
        holder.tvCount.setText((datas.get(position).getStatus()==1?"+":"-")+NumberUtils.forMatNumber(datas.get(position).getSource()));
        holder.tvDate.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(datas.get(position).getCreateDate())));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCount;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
