package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.PostEvaluationActivity;
import com.jilian.pinzi.ui.my.PostEvaluationSeeActivity;
import com.jilian.pinzi.ui.shopcard.PayOrderActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.NumberUtils;

import java.util.Date;
import java.util.List;

/**
 * 订单列表
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> implements MyOrderGoodAdapter.GoodClickListener {
    private Activity mContext;
    private List<MyOrderDto> datas;
    private CustomItemClickListener listener;
    private String leftDatas[] = new String[]{"", "取消订单", "取消订单", "查看物流", "评价商品", "查看评价", "重新购买","取消订单"};
    private String rightDatas[] = new String[]{"", "立即付款", "确认收货", "确认收货", "再次购买", "再次购买","删除订单","立即付款"};
    private String status[] = new String[]{"", "等待付款", "等待发货","等待收货", "交易完成", "交易完成", "交易关闭","等待付款"};
    private OrderListener orderListener;

    public MyOrderAdapter(Activity context, List<MyOrderDto> datas, CustomItemClickListener listener, OrderListener orderListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.orderListener = orderListener;


    }

    @Override
    public void clickGoods(String goodId) {
        Intent intent = new Intent(mContext,GoodsDetailActivity.class);
        intent.putExtra("goodsId",goodId);
        mContext.startActivity(intent);
    }

    /**
     * 订单操作接口
     */
    public interface OrderListener {
        /**
         * 取消
         * @param dto
         */
        void showCancelOrderDialog(MyOrderDto dto);

        /**
         * 确认
         * @param dto
         */
        void showConfirmGoodsTipsDialog(MyOrderDto dto);

        /**
         * 删除
         * @param dto
         */
        void showDeleteOrderDialog(MyOrderDto dto);

        /**
         * 查看物流
         * @param dto
         */
        void checkOgistics(MyOrderDto dto);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.rvGoods.setLayoutManager(linearLayoutManager);
        holder.rvGoods.setAdapter(new MyOrderGoodAdapter(mContext, datas.get(position).getGoodsInfo()));
        holder.tvLeft.setText(leftDatas[datas.get(position).getPayStatus()]);
        holder.tvRight.setText(rightDatas[datas.get(position).getPayStatus()]);
        holder.tvStatus.setText(status[datas.get(position).getPayStatus()]);
        holder.tvOrderNo.setText("订单编号："+datas.get(position).getOrderNo());

        if(datas.get(position).getPayFirstMoney()<=0){
            holder.tvPrice.setText(NumberUtils.forMatNumber(Double.parseDouble(datas.get(position).getPayMoney())));

        }
        else{
            holder.tvPrice.setText(NumberUtils.forMatNumber(datas.get(position).getPayFirstMoney()));

        }


        holder.tvDate.setText("提交时间："+DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_,new Date(datas.get(position).getCreateDate())));
        if(datas.get(position).getPayStatus()!=null&&datas.get(position).getPayStatus()==2){
            holder.tvLeft.setVisibility(View.GONE);
        }
        else{
            holder.tvLeft.setVisibility(View.VISIBLE);
        }
        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer type = datas.get(position).getPayStatus();
                Intent intent = null;
                if(type!=null) switch (type) {
                    case 1:
                        //取消订单
                        orderListener.showCancelOrderDialog(datas.get(position));
                        break;
                    case 2:

                        break;
                    case 3:
                        //查看物流
                        orderListener.checkOgistics(datas.get(position));
                        break;
                    case 4:
                        //评价商品
                        intent = new Intent(mContext, PostEvaluationActivity.class);
                        intent.putExtra("orderId", datas.get(position).getId());
                        mContext.startActivity(intent);

                        break;
                    case 5:
                        //查看评价
                        intent = new Intent(mContext, PostEvaluationSeeActivity.class);
                        intent.putExtra("orderId", datas.get(position).getId());
                        mContext.startActivity(intent);

                        break;
                    case 6:

                        //重新购买
                        //重新购买的时候  判断商品数量
                        //1个商品 跳到商品详情
                        //大于1个商品 跳到 首页

                        if (datas.get(position).getGoodsInfo().size() == 1) {
                            intent = new Intent(mContext, GoodsDetailActivity.class);
                            intent.putExtra("goodsId", String.valueOf(datas.get(position).getGoodsInfo().get(0).getGoodsId()));
                        } else {
                            intent = new Intent(mContext, MainActivity.class);
                        }
                        mContext.startActivity(intent);
                        break;
                    case 7:
                        //取消订单
                        orderListener.showCancelOrderDialog(datas.get(position));
                        break;
                }

            }
        });
        holder.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer type = datas.get(position).getPayStatus();
                Intent intent;
                if(type!=null){
                    switch (type) {
                        case 1:
                            //立即付款
                            intent = new Intent(mContext,PayOrderActivity.class);
                            intent.putExtra("orderDto",datas.get(position));
                            mContext.startActivity(intent);
                            break;
                        case 2:
                            //确认收货
                            orderListener.showConfirmGoodsTipsDialog(datas.get(position));
                            break;
                        case 3:
                            //确认收货
                            orderListener.showConfirmGoodsTipsDialog(datas.get(position));

                            break;
                        case 4:
                            //再次购买
                            //重新购买的时候  判断商品数量
                            //1个商品 跳到商品详情
                            //大于1个商品 跳到 首页

                            if(datas.get(position).getGoodsInfo().size()==1){
                                intent = new Intent(mContext,GoodsDetailActivity.class);
                                intent.putExtra("goodsId",String.valueOf(datas.get(position).getGoodsInfo().get(0).getGoodsId()));
                            }
                            else{
                                intent = new Intent(mContext,MainActivity.class);
                            }
                            mContext.startActivity(intent);
                            break;
                        case 5:
                            //再次购买
                            //重新购买的时候  判断商品数量
                            //1个商品 跳到商品详情
                            //大于1个商品 跳到 首页
                            if(datas.get(position).getGoodsInfo().size()==1){
                                intent = new Intent(mContext,GoodsDetailActivity.class);
                                intent.putExtra("goodsId",String.valueOf(datas.get(position).getGoodsInfo().get(0).getGoodsId()));
                            }
                            else{
                                intent = new Intent(mContext,MainActivity.class);
                            }
                            mContext.startActivity(intent);
                            break;
                        case 6:
                            //删除订单
                            orderListener.showDeleteOrderDialog(datas.get(position));
                            break;
                        case 7:
                            //立即付款
                            intent = new Intent(mContext,PayOrderActivity.class);
                            intent.putExtra("orderDto",datas.get(position));
                            mContext.startActivity(intent);
                            break;
                    }
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvGoods;

        private TextView tvLeft;
        private TextView tvRight;
        private TextView tvStatus;
        private View itemView;
        private TextView tvDate;
        private TextView tvOrderNo;
        private TextView tvPrice;




        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            rvGoods = (RecyclerView) itemView.findViewById(R.id.rv_goods);
            tvOrderNo = (TextView)itemView .findViewById(R.id.tv_order_no);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvLeft = (TextView) itemView.findViewById(R.id.tv_left);
            tvRight = (TextView) itemView.findViewById(R.id.tv_right);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }

    }
}
