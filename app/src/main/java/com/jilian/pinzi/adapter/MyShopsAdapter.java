package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.BusinesslistDto;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.ShopDetailActivity;
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


public class MyShopsAdapter extends RecyclerView.Adapter<MyShopsAdapter.ViewHolder> implements MyShopsDetailAdapter.ClickShopListener {
    private Activity mContext;
    private List<CollectionFootDto> datas;
    private CustomItemClickListener listener;
    private SwipeMenuItemClickListener swipeMenuItemClickListener;
    private SwipeMenuCreator sm;
    private MyGoodsAdapter.DeleteListener deleteListener;
    private String classify;//1 收藏 2 足迹

    public MyShopsAdapter(Activity context, List<CollectionFootDto> datas, CustomItemClickListener listener, MyGoodsAdapter.DeleteListener deleteListener, String classify) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.classify = classify;
        this.deleteListener = deleteListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_shops, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvDay.setText(datas.get(position).getCreateDate());
        if ("2".equals(classify)) {
            // 创建菜单：
            sm = new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                            .setImage(R.drawable.image_delete_item)// 各种文字和图标属性设置。
                            .setText("删除")
                            .setTextSize(DisplayUtil.sp2px(mContext, 5))
                            .setTextColor(Color.parseColor("#FFFFFF"))
                            .setWidth(DisplayUtil.dip2px(mContext, 100))
                            .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                            .setBackgroundColor(Color.parseColor("#FF0000"));
                    rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
                }
            };
            swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
                @Override
                public void onItemClick(SwipeMenuBridge menuBridge) {
                    menuBridge.closeMenu();

                    int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                    final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                    //   int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

                    if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                        //删除
                        String id = datas.get(position).getBusinesslist().get(adapterPosition).getCollectId();
                        deleteListener.delete(id);
                    }
                }

            };

            if(  holder.recyclerView.getOriginAdapter()==null){
                holder.recyclerView.setSwipeMenuCreator(sm);
                holder.recyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
            }

        }

        CustomerLinearLayoutManager linearLayoutManager = new CustomerLinearLayoutManager(mContext);
        linearLayoutManager.setCanScrollVertically(false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        MyShopsDetailAdapter shopsDetailAdapter = new MyShopsDetailAdapter(mContext, datas.get(position).getBusinesslist(), this);
        holder.recyclerView.setAdapter(shopsDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    public void clickShop(BusinesslistDto dto) {
        //在这里跳转到店铺展示的界面
        ShopDetailActivity.startActivity(mContext, dto.getId(),2,null,null);
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
