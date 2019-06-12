package com.jilian.pinzi.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.dto.OrderTrackDto;

import java.util.List;

/***
 * 订单追踪
 */
public class OrderTrackAdapter extends CommonAdapter<OrderTrackDto.LogisticsBean> {
    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public OrderTrackAdapter(Context context, int layoutId, List<OrderTrackDto.LogisticsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, OrderTrackDto.LogisticsBean logisticsBean, int position) {
//        View view = holder.getView(R.id.view_item_order_track);
//        view.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//        ImageView imageView = holder.getView(R.id.iv_item_order_track);
//        imageView.setImageResource(position == 0 ? R.drawable.image_my_agreed : R.drawable.icon_grey_dot);
        TextView tvContent = holder.getView(R.id.tv_item_order_track_content);
        TextView tvTime = holder.getView(R.id.tv_item_order_track_time);
        tvContent.setText(logisticsBean.getAcceptStation());
        tvContent.setTextColor(position == 0 ? ContextCompat.getColor(mContext, R.color.color_c71233)
                : ContextCompat.getColor(mContext, R.color.color_a5a5a5));
        tvTime.setText(logisticsBean.getAcceptTime());
        tvTime.setTextColor(position == 0 ? ContextCompat.getColor(mContext, R.color.color_c71233)
                : ContextCompat.getColor(mContext, R.color.color_a5a5a5));

//        View lineBottom = holder.getView(R.id.view_item_order_track_line_bottom);
//        RelativeLayout rlContent = holder.getView(R.id.rl_item_order_track);
//        RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) lineBottom.getLayoutParams();
////        lineParams.height = rlContent.getMeasuredHeight() + rlContent.getPaddingBottom();
//        lineParams.height = holder.itemView.getMeasuredHeight();
//        lineBottom.setLayoutParams(lineParams);
    }
}
