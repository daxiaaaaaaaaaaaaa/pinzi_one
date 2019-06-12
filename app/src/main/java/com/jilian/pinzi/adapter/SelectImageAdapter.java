package com.jilian.pinzi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.Image;

import java.util.List;

/**
 * @author ningpan
 * @since 2018/11/23 11:01 <br>
 * description: 选择相册照片 Adapter
 */
public class SelectImageAdapter extends CommonAdapter<Image> {

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public SelectImageAdapter(Context context, int layoutId, List<Image> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, Image image, int position) {
        ImageView ivImage = holder.getView(R.id.iv_item_select_image_img);
        Glide.with(mContext).load(image.getPath()).into(ivImage);
        ImageView ivCheck = holder.getView(R.id.iv_item_select_image_check);
        ivCheck.setImageResource(image.isCheck() ? R.drawable.image_checked : R.drawable.image_uncheck);
        ivCheck.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onCheckClick(v, position);
        });
        ivImage.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onImageClick(v, position);
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onImageClick(View view, int position);
        void onCheckClick(View view, int position);
    }
}
