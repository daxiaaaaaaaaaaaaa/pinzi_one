package com.jilian.pinzi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.utils.ImageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author ningpan
 * @since 2018/11/22 17:33 <br>
 * description: 发布朋友圈 添加图片 Adapter
 */
public class PublishFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int IMAGE_SIZE = 9;
    private static final int ADD_VIEW_TYPE = 200;

    private Context context;
    private List<Map<String, String>> dataList;

    public PublishFriendsAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_publish_friends_add, parent, false);
            return new AddViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_friends_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddViewHolder) {
            AddViewHolder viewHolder = (AddViewHolder) holder;
            viewHolder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.addItemClick(v, position);
            });
        } else {
            if (dataList.get(position) == null) {
                holder.itemView.setVisibility(View.GONE);
                return;
            }
            holder.itemView.setVisibility(View.VISIBLE);
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            viewHolder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.imageItemClick(v, position);
            });
            ((ImageView) viewHolder.itemView).setImageBitmap(ImageUtils.getBitmapByUrl(dataList.get(position).get("path")));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.size() <= IMAGE_SIZE && position == dataList.size() - 1) {
            return ADD_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void addItemClick(View view, int position);
        void imageItemClick(View view, int position);
    }

    class AddViewHolder extends BaseViewHolder {

        public AddViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ImageViewHolder extends BaseViewHolder {

        public ImageViewHolder(View itemView) {
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


}
