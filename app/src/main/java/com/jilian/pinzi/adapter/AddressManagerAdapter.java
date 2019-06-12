package com.jilian.pinzi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.dto.AddressDto;

import java.util.List;

public class AddressManagerAdapter extends CommonAdapter<AddressDto> {

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public AddressManagerAdapter(Context context, int layoutId, List<AddressDto> datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(CommonViewHolder holder, AddressDto addressDto, int position) {

        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvPhone = holder.getView(R.id.tv_phone);
        TextView tvIsDefault = holder.getView(R.id.tv_is_default);
        TextView  tvAdress = holder.getView(R.id.tv_adress);
        ImageView ivChecked = holder.getView(R.id.iv_check);
        TextView  tvEdit =holder.getView(R.id.tv_edit);
        TextView tvDelete = holder.getView(R.id.tv_delete);
        LinearLayout  llCheck =  holder.getView(R.id.ll_check);

        tvName.setText(addressDto.getName());
        tvAdress.setText(addressDto.getArea()+addressDto.getAddress());
        //tools:text="180****1849"
        try{
            String phone = addressDto.getPhone().substring(0,3)+"****"+addressDto.getPhone().substring(7,11);
            tvPhone.setText(phone);
        }
        catch (Exception e){

        }

        if(addressDto.getIsDefault()==0){
            tvIsDefault.setVisibility(View.INVISIBLE);
        }
        else{
            tvIsDefault.setVisibility(View.VISIBLE);
        }
        if(addressDto.getIsDefault()==0){
            ivChecked.setImageResource(R.drawable.image_uncheck);
        }
        else{
            ivChecked.setImageResource(R.drawable.image_checked);
        }


        tvEdit.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onEditClick(tvEdit, position);
        });
        tvDelete.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onDeleteClick(tvEdit, position);
        });
        llCheck.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onCheckClick(llCheck, position);
        });

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onEditClick(View view, int position);

        void onDeleteClick(View view, int position);

        void onCheckClick(View view, int position);
    }
}
