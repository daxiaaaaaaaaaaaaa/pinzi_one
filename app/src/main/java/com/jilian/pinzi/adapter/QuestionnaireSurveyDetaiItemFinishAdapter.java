package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.QuestionItemDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuestionnaireSurveyDetaiItemFinishAdapter extends RecyclerView.Adapter<QuestionnaireSurveyDetaiItemFinishAdapter.ViewHolder> {
    private Activity mContext;
    private List<QuestionItemDto> datas;
    private String answerOption;//答案对应的选项Id(多选由逗号隔开)


    public QuestionnaireSurveyDetaiItemFinishAdapter(Activity context, List<QuestionItemDto> datas, String answerOption) {
        mContext = context;
        this.datas = datas;

        this.answerOption = answerOption;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_questionnaire_survey_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (TextUtils.isEmpty(answerOption)) {
            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
        } else {
            List<String> list = new ArrayList<>();
            if (answerOption.contains(",")) {
                String array[] = answerOption.split(",");
                list.addAll(Arrays.asList(array));
            } else {
                list.add(answerOption);
            }
            if(list!=null&&list.contains(datas.get(position).getId())){
                holder.ivCheck.setImageResource(R.drawable.image_checked);
            }
            else{
                holder.ivCheck.setImageResource(R.drawable.image_uncheck);
            }

        }
        holder.tvName.setText(datas.get(position).getOptionContent());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCheck;
        private TextView tvName;


        public ViewHolder(final View itemView) {
            super(itemView);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);

        }

    }
}
