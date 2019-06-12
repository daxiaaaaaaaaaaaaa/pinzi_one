package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class MainNewsAdapter extends RecyclerView.Adapter<MainNewsAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private CustomItemClickListener listener;

    public MainNewsAdapter(Activity context, List<String> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvDate;



        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);


            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
