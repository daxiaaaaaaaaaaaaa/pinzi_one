package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class SelectPersonAdapter extends RecyclerView.Adapter<SelectPersonAdapter.ViewHolder> {
    private Activity mContext;
    private List<ShipperDto> datas;
    private CustomItemClickListener listener;

    public SelectPersonAdapter(Activity context, List<ShipperDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_person, parent, false);
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
        if(datas.get(position).isChecked()){
            holder.ivCheck.setImageResource(R.drawable.image_checked);
        }
        else{
            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
        }
        holder.tvName.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llItem;
        private ImageView ivCheck;
        private TextView tvName;

   ;

        private View vLine;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            llItem = (LinearLayout)itemView. findViewById(R.id.ll_item);
            ivCheck = (ImageView)itemView. findViewById(R.id.iv_check);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
