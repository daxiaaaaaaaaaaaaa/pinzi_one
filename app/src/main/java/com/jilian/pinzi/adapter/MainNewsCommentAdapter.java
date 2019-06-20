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
import com.jilian.pinzi.common.dto.CommentListDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.views.CircularImageView;

import java.util.Date;
import java.util.List;


public class MainNewsCommentAdapter extends RecyclerView.Adapter<MainNewsCommentAdapter.ViewHolder> {
    private Activity mContext;
    private List<CommentListDto> datas;
    private CustomItemClickListener listener;

    public MainNewsCommentAdapter(Activity context, List<CommentListDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_news_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getUserName());
        holder.tvContent.setText(datas.get(position).getContent());
        holder.tvDate.setText(DateUtil.dateToString(DateUtil.DATE_FORMAT,new Date(datas.get(position).getCreateDate())));
        Glide.with(mContext).
                load(datas.get(position).getHeadImg()).error(R.drawable.ic_launcher_background) //异常时候显示的图片
                .placeholder(R.drawable.ic_launcher_background) //加载成功前显示的图片
                .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .into(holder.ivHead);//在RequestBuilder 中使用自定义的ImageViewTarge

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView ivHead;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvContent;
        private View vLine;





        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivHead = (CircularImageView)itemView. findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView)itemView. findViewById(R.id.tv_date);
            tvContent = (TextView)itemView. findViewById(R.id.tv_content);
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
