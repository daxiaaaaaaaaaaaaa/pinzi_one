package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Date;
import java.util.List;


public class QuestionnaireSurveyAdapter extends RecyclerView.Adapter<QuestionnaireSurveyAdapter.ViewHolder> {
    private Activity mContext;
    private List<QuestionDto> datas;
    private CustomItemClickListener listener;

    public QuestionnaireSurveyAdapter(Activity context, List<QuestionDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_questionnaire_survey, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tvContent.setText(datas.get(position).getTitle());
            holder.tvDate.setText(DateUtil.dateToString(DateUtil.DATE_FORMAT_,new Date(datas.get(position).getCreateDate())));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;
        private TextView tvDate;




        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);


            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
