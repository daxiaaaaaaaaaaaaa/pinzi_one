package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.NumberUtils;

import java.util.Date;
import java.util.List;


public class MyMyCouponsAdapter extends RecyclerView.Adapter<MyMyCouponsAdapter.ViewHolder> {
    private Activity mContext;
    private List<MyRecordDto> datas;
    private CustomItemClickListener listener;
    private DeleteRecordListener deleteRecordListener;

    public MyMyCouponsAdapter(Activity context, List<MyRecordDto> datas, CustomItemClickListener listener, DeleteRecordListener deleteRecordListener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
        this.deleteRecordListener = deleteRecordListener;
    }

    public interface DeleteRecordListener {
        void deleteRecord(int pisition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mycoupons, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }
    String [] status = new String[]{"","(待审核)", "(审核成功)","(审核失败)"};
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == datas.size() - 1) {
            holder.vLine.setVisibility(View.GONE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }

        //1.待审核 2.审核成功 3.审核失败

        holder.tvName.setText(datas.get(position).getTitle() +status[datas.get(position).getWdStatus()] );

        holder.tvCount.setText((datas.get(position).getStatus() == 1 ? "+" : "-") + NumberUtils.forMatNumber(datas.get(position).getSource()));
        holder.tvDate.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN, new Date(datas.get(position).getCreateDate())));
        if (!TextUtils.isEmpty(datas.get(position).getBuyUserName())) {
            holder.tvUser.setText("(购买人：" + datas.get(position).getBuyUserName() + ")");
        }
        holder.rlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteRecordListener.deleteRecord(position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vLine;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCount;
        private TextView tvUser;
        private RelativeLayout rlItem;


        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            vLine = (View) itemView.findViewById(R.id.v_line);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvUser = (TextView) itemView.findViewById(R.id.tv_user);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
