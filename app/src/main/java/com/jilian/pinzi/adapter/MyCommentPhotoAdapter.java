package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class MyCommentPhotoAdapter extends RecyclerView.Adapter<MyCommentPhotoAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private CustomItemClickListener listener;
    private ClosePhotonListener closePhotonListener;
    public interface ClosePhotonListener{
        void close(int position);
    }
    public MyCommentPhotoAdapter(Activity context, List<String> datas, CustomItemClickListener listener, ClosePhotonListener closePhotonListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.closePhotonListener = closePhotonListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.postevaluationphoto, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            if(position!=(datas.size()-1)){
                Glide.with(mContext).load(datas.get(position)).into(holder.ivOne);
//                holder.ivOne.setImageBitmap(BitmapFactory.decodeFile(datas.get(position)));
                holder.ivClose.setVisibility(View.VISIBLE);
                holder.rlClose.setVisibility(View.VISIBLE);
            }
            else{
                holder.ivClose.setVisibility(View.GONE);
                holder.rlClose.setVisibility(View.GONE);
            }
            if(getItemCount()==10&&position==9){
                holder.rlItem.setVisibility(View.GONE);
            }
            else{
                holder.rlItem.setVisibility(View.VISIBLE);
            }
            holder.rlClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePhotonListener.close(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivOne;
        private RelativeLayout rlItem;
        private ImageView ivClose;
        private RelativeLayout rlClose;








        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ivClose = (ImageView) itemView.findViewById(R.id.iv_close);
            ivOne = (ImageView) itemView.findViewById(R.id.iv_one);
            rlClose = (RelativeLayout)itemView. findViewById(R.id.rl_close);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
