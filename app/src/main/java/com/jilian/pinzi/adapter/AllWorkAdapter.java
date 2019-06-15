package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.CircularImageView;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.HashMap;
import java.util.List;


public class AllWorkAdapter extends RecyclerView.Adapter<AllWorkAdapter.ViewHolder> implements CustomItemClickListener {
    private Activity mContext;
    private List<String> datas;
    private CustomItemClickListener listener;

    public AllWorkAdapter(Activity context, List<String> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_work, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        holder.recyclerView.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,  DisplayUtil.dip2px(mContext,15));//下间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,  DisplayUtil.dip2px(mContext,15));//下间距
        if(holder.recyclerView.getItemDecorationCount()<=0){
            holder.recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        }

        holder.recyclerView.setAdapter(new AllWorkPhotoAdapter(mContext,datas,this));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView ivHead;
        private TextView tvName;
        private TextView tvSend;
        private TextView tvCount;
        private TextView tvContent;
        private RecyclerView recyclerView;





        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);

            ivHead = (CircularImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSend = (TextView) itemView.findViewById(R.id.tv_send);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
