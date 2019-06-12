package com.jilian.pinzi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ningpan
 * @since 2018/11/23 14:05 <br>
 * description: 我的朋友圈 Adapter
 */
public class MineFriendsCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER_VIEW_TYPE = 200;
    private final Context context;
    private final List<Map<String, String>> dataList;

    public MineFriendsCircleAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER_VIEW_TYPE) {
            // TODO 这个 item 布局可以共用
            View inflate = inflater.inflate(R.layout.item_header_friends_circle, parent, false);
            return new HeaderViewHolder(inflate);
        } else {
            // TODO item 布局需要更换
            View inflate = inflater.inflate(R.layout.item_list_friends_circle, parent, false);
            return new ListViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        } else {
            ListViewHolder viewHolder = (ListViewHolder) holder;
            // 朋友圈发布的图片
            // TODO 模拟一些图片数据: 最多添加6张图片
            List<Map<String, String>> images = new ArrayList<>();
            images.add(null);
            images.add(null);
            images.add(null);
            images.add(null);
            images.add(null);
            viewHolder.initImagesRecyclerView(R.id.rv_item_list_friends_images, images);

            // 删除该朋友圈
            viewHolder.getView(R.id.tv_item_list_friends_del).setOnClickListener(v -> {
                Dialog dialog = new Dialog(context, R.style.dialogFullscreen);
                dialog.setContentView(R.layout.dialog_bottom_layout);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                layoutParams.dimAmount = 0.5f;
                window.setGravity(Gravity.BOTTOM);
                window.setAttributes(layoutParams);

                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
                    // TODO 删除
                    dialog.dismiss();
                });
                dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /** 图片 Adapter */
    class ImagesAdapter extends CommonAdapter<Map<String, String>> {

        /**
         * 构造方法
         *
         * @param context  上下文
         * @param layoutId 布局id
         * @param datas    数据源
         */
        public ImagesAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(CommonViewHolder holder, Map<String, String> stringStringMap, int position) {

        }
    }

    class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ListViewHolder extends BaseViewHolder {

        private RecyclerView rvImages;
        private ImagesAdapter imagesAdapter;
        private List<Map<String, String>> images;


        public ListViewHolder(View itemView) {
            super(itemView);
        }

        /** 初始化图片 RecyclerView */
        public void initImagesRecyclerView(int id, List<Map<String, String>> images) {
            rvImages = itemView.findViewById(id);
            rvImages.setLayoutManager(new GridLayoutManager(context, 3));
            this.images = images;
            imagesAdapter = new ImagesAdapter(itemView.getContext(), R.layout.item_friends_images, images);
            rvImages.setAdapter(imagesAdapter);
            imagesAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    // TODO 点击查看图片
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
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
}
