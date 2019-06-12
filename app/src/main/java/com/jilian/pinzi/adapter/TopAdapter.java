package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.CommentTitleDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private Activity mContext;
    private List<CommentTitleDto> datas;
    private TopClickListener listener;
    public interface TopClickListener{
        void topClick(View view,int position);
    }
    public TopAdapter(Activity context, List<CommentTitleDto> datas, TopClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(datas.get(position).getTitle());
        holder.tvCount.setText(datas.get(position).getCount());
        holder.tvTitle.setTextColor(datas.get(position).isSelected() ? Color.parseColor("#c71233") : Color.parseColor("#999999"));
        holder.tvCount.setTextColor(datas.get(position).isSelected() ? Color.parseColor("#c71233") : Color.parseColor("#999999"));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvCount;


        public ViewHolder(final View itemView, final TopClickListener listener) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.topClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
