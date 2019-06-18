package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.common.dto.GoodlistDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MainNewsDetailActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.jilian.pinzi.views.CustomerLinearLayoutManager;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;


public class MyNewsCollectAdapter extends RecyclerView.Adapter<MyNewsCollectAdapter.ViewHolder> implements CustomItemClickListener {
    private Activity mContext;
    private List<String> datas;
    private CustomItemClickListener listener;

    @Override
    public void onItemClick(View view, int position) {
        mContext.startActivity(new Intent(mContext, MainNewsDetailActivity.class));
    }

    public MyNewsCollectAdapter(Activity context, List<String> datas, CustomItemClickListener listener) {

        mContext = context;
        this.datas = datas;
        this.listener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.tvDay.setText(datas.get(position).getCreateDate());
        holder.recyclerView.addItemDecoration(new CustomerItemDecoration(1));


        CustomerLinearLayoutManager linearLayoutManager = new CustomerLinearLayoutManager(mContext);
        linearLayoutManager.setCanScrollVertically(false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        MainNewsAdapter goodsDetailAdapter = new MainNewsAdapter(mContext, datas, this);
        holder.recyclerView.setAdapter(goodsDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private SwipeMenuRecyclerView recyclerView;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            recyclerView = (SwipeMenuRecyclerView) itemView.findViewById(R.id.recyclerView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
