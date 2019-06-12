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
import com.jilian.pinzi.common.dto.GoodlistDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class MyGoodsDetailAdapter extends RecyclerView.Adapter<MyGoodsDetailAdapter.ViewHolder> {
    private Activity mContext;
    private List<GoodlistDto> datas;
    private ClickGoodsListener listener;
    public  interface  ClickGoodsListener{
        void clickGoods(GoodlistDto dto);
    }
    public MyGoodsDetailAdapter(Activity context, List<GoodlistDto> datas, ClickGoodsListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_goods_detail_normal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
       // if (dto.getType()==1) {

            if(datas.get(position).getScoreBuy()>0){
                holder.tvPrice.setText("积分"+ NumberUtils.forMatOneNumber(datas.get(position).getScoreBuy()));
            }
            else{
                holder.tvPrice.setText("￥"+datas.get(position).getPersonBuy());
            }
//        }
//        if (dto.getType()==2) {
//            holder.tvPrice.setText(datas.get(position).getTerminalBuy());
//        }
//        if (dto.getType()==3) {
//            holder.tvPrice.setText(datas.get(position).getChannelBuy());
//        }
////        if (dto.getType()==4) {
//            holder.tvPrice.setText(datas.get(position).getFranchiseeBuy());
//        }
        holder.tvName.setText(datas.get(position).getName());
        try {
            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .into(holder.ivPhoto);

//            Glide
//                    .with(mContext)
//                    .load("image.liangdaola.com/liangdaola/logo/goods_logo/1545027406036")
//                    .into(holder.ivPhoto);


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvPrice;


        public ViewHolder(final View itemView, final ClickGoodsListener listener) {
            super(itemView);

            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickGoods(datas.get(getAdapterPosition()));
                }
            });
        }

    }
}
