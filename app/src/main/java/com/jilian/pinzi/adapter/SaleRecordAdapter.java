package com.jilian.pinzi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.dto.SaleRecordDto;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.Date;
import java.util.List;

/**
 * 申请记录 Adapter.
 */
public class SaleRecordAdapter extends CommonAdapter<SaleRecordDto> {

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public SaleRecordAdapter(Context context, int layoutId, List<SaleRecordDto> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, SaleRecordDto record, int position) {
        holder.setText(R.id.tv_item_sale_record_order_no, "订单编号：" + record.getOrderNo());
        holder.setText(R.id.tv_item_sale_record_time,
                DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN, new Date(record.getCreateDate())));
        ImageView ivHead = holder.getView(R.id.iv_item_sale_record_head);
        Glide.with(mContext).load(UrlUtils.getUrl(record.getFile())).placeholder(R.drawable.image_good_test)
                .error(R.drawable.image_good_test).into(ivHead);
        holder.setText(R.id.tv_item_sale_record_name, record.getName());
        holder.setText(R.id.tv_item_sale_record_price, record.getGoodsPrice() + "");
        holder.setText(R.id.tv_item_sale_record_count, "X" + record.getQuantity() + "");
        int status = record.getStatus();
        holder.setText(R.id.tv_item_sale_record_status,
                status == 1 ? "待处理" : status == 2 ? "退货中" : status == 3 ? "已完成" :  "已拒绝");

//        holder.getView(R.id.rl_item_sale_record_bottom).setOnClickListener(v -> {
//            if (onItemClickListener != null) {
//                onItemClickListener.onItemClick(holder.itemView, position);
//            }
//        });
//        holder.getView(R.id.ll_item_sale_record_content).setOnClickListener(v -> {
//            if (onItemClickListener != null) {
//                onItemClickListener.onGoodsItemClick(holder.getView(R.id.ll_item_sale_record_content), position);
//            }
//        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onGoodsItemClick(View view, int position);
        void onItemClick(View view, int position);
    }
}
