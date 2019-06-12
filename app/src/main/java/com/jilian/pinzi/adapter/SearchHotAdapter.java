package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.HotWordListDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class SearchHotAdapter extends RecyclerView.Adapter<SearchHotAdapter.ViewHolder> {
    private Activity mContext;
    private List<HotWordListDto> datas;
    private SearchHotListener listener;
    public interface  SearchHotListener{
        void hotClick(int position);
    }

    public SearchHotAdapter(Activity context, List<HotWordListDto> datas, SearchHotListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_hot, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvName.setText(datas.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;



        public ViewHolder(final View itemView, final SearchHotListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.hotClick( getAdapterPosition());
                }
            });
        }

    }
}
