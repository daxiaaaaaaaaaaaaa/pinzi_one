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
import com.jilian.pinzi.common.dto.InformationtDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class MainNewsAdapter extends RecyclerView.Adapter<MainNewsAdapter.ViewHolder> {
    private Activity mContext;
    private List<InformationtDto> datas;

    private ClickNewsListener clickNewsListener;

    public MainNewsAdapter(Activity context, List<InformationtDto> datas, ClickNewsListener clickNewsListener) {
        mContext = context;
        this.datas = datas;
        this.clickNewsListener = clickNewsListener;
    }

    public interface ClickNewsListener {
        void clickNews(InformationtDto dto);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, clickNewsListener);
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
        holder.tvDate.setText("发表于 " + DateUtil.dateToString(DateUtil.DATE_FORMAT, new Date(datas.get(position).getCreateDate())));
        Glide.with(mContext).
                load(datas.get(position).getImgUrl()).error(R.drawable.ic_launcher_background) //异常时候显示的图片
                .placeholder(R.drawable.ic_launcher_background) //加载成功前显示的图片
                .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .into(holder.ivPhoto);//在RequestBuilder 中使用自定义的ImageViewTarge
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvDate;
        private View vLine;


        public ViewHolder(final View itemView, final ClickNewsListener listener) {
            super(itemView);


            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            vLine = (View) itemView.findViewById(R.id.v_line);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickNews(datas.get(getAdapterPosition()));
                }
            });
        }

    }
}
