package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.BusinesslistDto;
import com.jilian.pinzi.common.dto.GoodlistDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class MyShopsDetailAdapter extends RecyclerView.Adapter<MyShopsDetailAdapter.ViewHolder> {
    private Activity mContext;
    private List<BusinesslistDto> datas;
    private ClickShopListener listener;
    public  interface  ClickShopListener{
        void clickShop(BusinesslistDto dto);
    }
    public MyShopsDetailAdapter(Activity context, List<BusinesslistDto> datas, ClickShopListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_shops_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
        holder.tvName.setText(datas.get(position).getName());
        Glide
                .with(mContext)
                .load(datas.get(position).getImgUrl())
                .into(holder.ivPhoto);
        holder.tvPoints.setText(datas.get(position).getGrade() + "分");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvPoints;

        public ViewHolder(final View itemView, final ClickShopListener listener) {
            super(itemView);

            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);


            tvPoints = (TextView) itemView.findViewById(R.id.tv_points);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickShop(datas.get(getAdapterPosition()));
                }
            });
        }

    }
}
