package com.jilian.pinzi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.ShopGoodsDto;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;

/**
 * 商铺商品 Adapter
 */
public class ShopGoodsAdapter extends CommonAdapter<ShopGoodsDto> {
    private int classes;
    private AddOrDelListener addOrDelListener;

    public interface AddOrDelListener {
        void add(int position);

        void del(int position);
    }

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public ShopGoodsAdapter(Context context, int layoutId, List<ShopGoodsDto> datas, AddOrDelListener addOrDelListener, int classes) {
        super(context, layoutId, datas);
        this.addOrDelListener = addOrDelListener;
        this.classes = classes;
    }

    @Override
    protected void convert(CommonViewHolder holder, ShopGoodsDto shopGoodsDto, int position) {
        ImageView ivHead = holder.getView(R.id.iv_photo);

        ImageView tvDel = holder.getView(R.id.tv_del);
        ImageView tvAdd = holder.getView(R.id.tv_add);

        TextView tvCount = holder.getView(R.id.tv_count);
        tvCount.setText(String.valueOf(shopGoodsDto.getQuantity()));
        Glide.with(mContext).load(UrlUtils.getUrl(shopGoodsDto.getFile())).into(ivHead);
        holder.setText(R.id.tv_name, shopGoodsDto.getName());

        String price;

        // 1.普通用户 2.终端 3.渠道 4.总经销商
        price = NumberUtils.forMatNumber(shopGoodsDto.getPersonBuy());

        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //不同用户 从采购中心进来
        if (classes == 2) {

            if (dto.getType() == 1) {
                price = NumberUtils.forMatNumber(shopGoodsDto.getPersonBuy());

            }
            if (dto.getType() == 2) {
                price = NumberUtils.forMatNumber(shopGoodsDto.getTerminalBuy());

            }
            if (dto.getType() == 3) {
                price = NumberUtils.forMatNumber(shopGoodsDto.getChannelBuy());

            }
            if (dto.getType() == 4) {
                price = NumberUtils.forMatNumber(shopGoodsDto.getFranchiseeBuy());

            }

        }//平台  从总经销商进来
        else if (classes == 3) {
            price = NumberUtils.forMatNumber(shopGoodsDto.getFranchiseeBuy());
        }
        ////平台  从二批商进来
        else if (classes == 4) {
            price = NumberUtils.forMatNumber(shopGoodsDto.getChannelBuy());
        }
        ////平台  从门店进来
        else if (classes == 5) {

            price = NumberUtils.forMatNumber(shopGoodsDto.getTerminalBuy());
        } else if (classes == 6) {
            price = NumberUtils.forMatNumber(shopGoodsDto.getPersonBuy());
        }
        //不是从采购中心进来
        else {
            price = NumberUtils.forMatNumber(shopGoodsDto.getPersonBuy());
        }

        holder.setText(R.id.tv_price, price);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrDelListener.add(position);
            }
        });
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrDelListener.del(position);
            }
        });
    }
}
