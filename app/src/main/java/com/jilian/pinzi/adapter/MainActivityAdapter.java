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
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private Activity mContext;
    private List<ActivityDto> datas;
    private CustomItemClickListener listener;

    public MainActivityAdapter(Activity context, List<ActivityDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_activity, parent, false);
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

        Glide.with(mContext).
                load(datas.get(position).getImgUrl()).error(R.drawable.ic_launcher_background) //异常时候显示的图片
                .placeholder(R.drawable.ic_launcher_background) //加载成功前显示的图片
                .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .into(holder.ivPhoto);//在RequestBuilder 中使用自定义的ImageViewTarge

        holder.tvName.setText(datas.get(position).getTitle());
        holder.tvDate.setText("活动时间："+DateUtil.dateToString(DateUtil.DATE_FORMAT,new Date(datas.get(position).getStartDate()))+
                "~"
                +DateUtil.dateToString(DateUtil.DATE_FORMAT,new Date(datas.get(position).getEndDate())));
        holder.tvRegistrationQuota.setText("报名名额："+datas.get(position).getPeopleNum());
        holder.tvRegistrationNumber.setText("已报名人数："+datas.get(position).getAlreadyPeopleNum());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvRegistrationQuota;
        private TextView tvRegistrationNumber;
        private View vLine;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvRegistrationQuota = (TextView) itemView.findViewById(R.id.tv_registration_quota);
            tvRegistrationNumber = (TextView) itemView.findViewById(R.id.tv_registration_number);
            vLine = (View) itemView.findViewById(R.id.v_line);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
