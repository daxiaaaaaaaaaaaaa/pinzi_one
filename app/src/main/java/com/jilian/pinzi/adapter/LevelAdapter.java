package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.InviteListDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CircularImageView;

import java.util.List;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    private Activity mContext;
    private List<InviteListDto> datas;
    private CustomItemClickListener listener;
    private Integer type;
    private String []  types = new String[]{"","会员","会员","会员","会员"};
    private String []  isVips = new String[]{"","普通","金牌","铂金","钻石","皇冠"};
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LevelAdapter(Integer type, Activity context, List<InviteListDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_level, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide
                .with(mContext)
                .load(UrlUtils.getUrl(datas.get(position).getHeadImg()))
                .into(holder.ivHead);
        holder.tvName.setText(datas.get(position).getName());
        holder.tvAccount.setText("账号："+datas.get(position).getPhone());
        holder.tvMember.setText(isVips[datas.get(position).getIsVip()]);

        holder.tvMember.setText(isVips[datas.get(position).getIsVip()]+types[datas.get(position).getType()]);
        if(position==datas.size()-1){
            holder.vLine.setVisibility(View.GONE);
        }
        else{
            holder.vLine.setVisibility(View.VISIBLE);
        }
        if(type==1){
            holder.tvDetail.setVisibility(View.GONE);
        }
        else{
            holder.tvDetail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircularImageView ivHead;
        private TextView tvName;
        private TextView tvMember;
        private TextView tvAccount;
        private View vLine;

        private TextView tvDetail;







        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivHead = (CircularImageView)  itemView.findViewById(R.id.iv_head);
            tvName = (TextView)  itemView.findViewById(R.id.tv_name);
            tvMember = (TextView)  itemView.findViewById(R.id.tv_member);
            tvAccount = (TextView)  itemView.findViewById(R.id.tv_account);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
