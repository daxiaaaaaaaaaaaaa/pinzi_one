package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class MyShipmentGoodAdapter extends RecyclerView.Adapter<MyShipmentGoodAdapter.ViewHolder> {
    private Activity mContext;
    private List<GoodsInfoDto> datas;
    private GoodClickListener listener;
    public interface  GoodClickListener{
        void clickGoods(String goodId,GoodsInfoDto dto);
    }
    public MyShipmentGoodAdapter(Activity context, List<GoodsInfoDto> datas, GoodClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myshipmentgood, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position==datas.size()-1){
            holder.vLine.setVisibility(View.GONE);
        }
        else{
            holder.vLine.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(datas.get(position).getFile())){
            Glide
                    .with(mContext)
                    .load(R.drawable.image_good_test)
                    .into(holder.ivHead);
        }
        else{
            Glide
                    .with(mContext)
                    .load(UrlUtils.getUrl(datas.get(position).getFile()))
                    .error(R.drawable.image_good_test)
                    .into(holder.ivHead);
        }

        holder.tvCount.setText("X"+datas.get(position).getQuantity());
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getGoodsPrice()));

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View vLine;
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvCount;


        public ViewHolder(final View itemView, final GoodClickListener listener) {
            super(itemView);

            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

            vLine = (View) itemView.findViewById(R.id.v_line);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickGoods(String.valueOf(datas.get(getAdapterPosition()).getGoodsId()),datas.get(getAdapterPosition()));
                }
            });
        }

    }
}
