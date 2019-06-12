package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.ParameterDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;

/**
 * 商品参数
 */
public class GoodParamAdapter extends RecyclerView.Adapter<GoodParamAdapter.ViewHolder> {
    private Activity mContext;
    private List<ParameterDto> datas;

    public GoodParamAdapter(Activity context, List<ParameterDto> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_see_param, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(datas.get(position).getContent());
        holder.tvName.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvContent;


        public ViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }
}
