package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.NewsTypeDto;

import java.util.List;


public class NewsTypeAdapter extends RecyclerView.Adapter<NewsTypeAdapter.ViewHolder> {
    private Activity mContext;
    private List<NewsTypeDto> datas;
    private TypeTopListener listener;

    public interface TypeTopListener {
        void oneTypeClick(View view, int position);
    }

    public NewsTypeAdapter(Activity context, List<NewsTypeDto> datas, TypeTopListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_top, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (datas.get(position).isSelected()) {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_main_select));
            holder.vLine.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_text_dark));
            holder.vLine.setVisibility(View.INVISIBLE);
        }
        holder.tvName.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private View vLine;


        public ViewHolder(final View itemView, final TypeTopListener listener) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            vLine = (View) itemView.findViewById(R.id.v_line);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.oneTypeClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
