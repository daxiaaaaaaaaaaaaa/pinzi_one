package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.List;


public class SearchRecordAdapter extends RecyclerView.Adapter<SearchRecordAdapter.ViewHolder> {
    private Activity mContext;
    private List<String> datas;
    private SearchRecordListener listener;

    public interface SearchRecordListener{
        void recordClick(int position);
    }

    public SearchRecordAdapter(Activity context, List<String> datas, SearchRecordListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {

            holder.vLine.setVisibility(View.GONE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(datas.get(position));

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;
        private TextView tvName;



        public ViewHolder(final View itemView, final SearchRecordListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.recordClick( getAdapterPosition());
                }
            });
        }

    }
}
