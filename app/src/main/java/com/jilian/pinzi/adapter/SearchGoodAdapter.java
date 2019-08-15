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
import com.jilian.pinzi.common.dto.HotWordSelectDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class SearchGoodAdapter extends RecyclerView.Adapter<SearchGoodAdapter.ViewHolder> {
    private Activity mContext;
    private List<HotWordSelectDto> datas;
    private GoodClickListener listener;
    public interface  GoodClickListener{
        void clickGoods(int position);
    }
    public SearchGoodAdapter(Activity context, List<HotWordSelectDto> datas, GoodClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_goods, parent, false);
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
        String url = datas.get(position).getFile();
        if(!url.contains(",")){
            Glide
                    .with(mContext)
                    .load(datas.get(position).getFile())
                    .into(holder.ivHead);
        }
        else{
                Glide
                        .with(mContext)
                        .load(UrlUtils.getUrl(datas.get(position).getFile())).into(holder.ivHead);
        }
        holder.tvName.setText(datas.get(position).getName());
        holder.tvContent.setText(datas.get(position).getParameterContent());

//        LoginDto dto = PinziApplication.getInstance().getLoginDto();
//        //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
//        if (dto.getType()==1) {
        //秒杀商品
        if(datas.get(position).getIsSeckill()==1
                &&datas.get(position).getSeckillPrice()>0){
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getSeckillPrice()));
        }
        else{
            //普通商品
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPersonBuy()));
        }


//        }
//        if (dto.getType()==2) {
//            holder.tvPrice.setText(datas.get(position).getTerminalBuy());
//        }
//        if (dto.getType()==3) {
//            holder.tvPrice.setText(datas.get(position).getChannelBuy());
//        }
//        if (dto.getType()==4) {
//            holder.tvPrice.setText(datas.get(position).getFranchiseeBuy());
//        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View vLine;

        private ImageView ivHead;
        private TextView tvName;
        private TextView tvContent;
        private TextView tvPrice;


        public ViewHolder(final View itemView, final GoodClickListener listener) {
            super(itemView);


            vLine = (View) itemView.findViewById(R.id.v_line);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickGoods(getAdapterPosition());
                }
            });
        }

    }
}
