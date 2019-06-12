package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class SelectAdressAdapter extends RecyclerView.Adapter<SelectAdressAdapter.ViewHolder> {
    private Activity mContext;
    private List<AddressDto> datas;
    private CustomItemClickListener listener;
    private EditClickListener editClickListener;

    public interface EditClickListener {
        void editAdress(int position);
    }

    public SelectAdressAdapter(Activity context, List<AddressDto> datas, CustomItemClickListener listener, EditClickListener editClickListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.editClickListener = editClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AddressDto addressDto = datas.get(position);
        holder.tvName.setText(addressDto.getName());
        holder.tvAdress.setText(addressDto.getArea() + addressDto.getAddress());
        //tools:text="180****1849"
        try {
            String phone = addressDto.getPhone().substring(0, 3) + "****" + addressDto.getPhone().substring(7, 11);
            holder.tvPhone.setText(phone);
        } catch (Exception e) {

        }
        if (addressDto.getIsDefault() == 0) {
            holder.tvIsDefault.setVisibility(View.INVISIBLE);
        } else {
            holder.tvIsDefault.setVisibility(View.VISIBLE);
        }
        if (!addressDto.isChecked()) {
            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
        } else {
            holder.ivCheck.setImageResource(R.drawable.image_checked);
        }
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClickListener.editAdress(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCheck;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvIsDefault;
        private ImageView ivEdit;
        private TextView tvAdress;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvIsDefault = (TextView) itemView.findViewById(R.id.tv_is_default);
            ivEdit = (ImageView) itemView.findViewById(R.id.iv_edit);
            tvAdress = (TextView) itemView.findViewById(R.id.tv_adress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
