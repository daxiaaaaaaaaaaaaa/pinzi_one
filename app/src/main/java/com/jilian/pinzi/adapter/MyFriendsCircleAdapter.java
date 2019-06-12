package com.jilian.pinzi.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.views.RoundImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ningpan
 * @since 2018/11/21 20:20 <br>
 * description: 某个人的朋友圈 Adapter
 */
public class MyFriendsCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int HEADER_VIEW_TYPE = 200;
    private final Context context;
    private final List<FriendCircleListDto> datas;
    private MyFriendDetailAdapter.FriendListener friendListener;
    private String mUrl;
    private String name;
    public MyFriendsCircleAdapter(String url,String name,Context context, List<FriendCircleListDto> datas,MyFriendDetailAdapter.FriendListener friendListener) {
        this.context = context;
        this.datas = datas;
        this.friendListener = friendListener;
        this.name = name;
        this.mUrl = url;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER_VIEW_TYPE) {
            View inflate = inflater.inflate(R.layout.item_header_my_friends_circle, parent, false);
            return new HeaderViewHolder(inflate);
        } else {
            View inflate = inflater.inflate(R.layout.item_list_my_friends_circle, parent, false);
            return new ListViewHolder(inflate);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            RoundImageView ivHead = viewHolder.getView(R.id.iv_item_header_friends_circle_head);
            TextView tvName = viewHolder.getView(R.id.tv_name);
                Glide
                        .with(context)
                        .load(mUrl)
                        .into(ivHead);
                tvName.setText(name);
        }
        //列表
        else {
            ListViewHolder viewHolder = (ListViewHolder) holder;
            TextView  tvOneDay = viewHolder.getView(R.id.tv_one_day);
            TextView tvTwoDay = viewHolder.getView(R.id.tv_two_day);
            String mDay = DateUtil.getTitleDay(new Date(Long.parseLong(datas.get(position).getCreateDate())));
            if(mDay.equals("今天")||mDay.equals("昨天")){
                tvOneDay.setText(mDay);
                tvOneDay.setVisibility(View.VISIBLE);
                tvTwoDay.setVisibility(View.GONE);
            }
            else{
                tvOneDay.setVisibility(View.VISIBLE);
                tvTwoDay.setVisibility(View.VISIBLE);
                String month = mDay.split("/")[1]+"月";
                String day = mDay.split("/")[2];
                tvOneDay.setText(day);
                tvTwoDay.setText(month);
            }
            RecyclerView recyclerView = viewHolder.getView(R.id.recyclerView);
            MyFriendDetailAdapter detailAdapter  = new MyFriendDetailAdapter(context,datas.get(position).getDatas(),friendListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(detailAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }


    class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews = new SparseArray<>();

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String text) {
            ((TextView) getView(id)).setText(text);
        }
    }



    class ListViewHolder extends BaseViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
