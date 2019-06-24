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
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionDto;
import com.jilian.pinzi.common.dto.QuestionItemDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuestionnaireSurveyDetaiItemlAdapter extends RecyclerView.Adapter<QuestionnaireSurveyDetaiItemlAdapter.ViewHolder> {
    private Activity mContext;
    private List<QuestionItemDto> datas;
    private String answerOption;//答案对应的选项Id(多选由逗号隔开)
    private int isRadio;
    private  int parentPosition;

    public List<QuestionItemDto> getDatas() {
        return datas;
    }

    public void setDatas(List<QuestionItemDto> datas) {
        this.datas = datas;
    }

    public QuestionnaireSurveyDetaiItemlAdapter(Activity context, int parentPosition, List<QuestionItemDto> datas, String answerOption, int isRadio) {
        mContext = context;
        this.datas = datas;
        this.answerOption = answerOption;
        this.isRadio = isRadio;
        this. parentPosition = parentPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_questionnaire_survey_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (datas.get(position).isSelected()) {
            holder.ivCheck.setImageResource(R.drawable.image_checked);
        } else {
            holder.ivCheck.setImageResource(R.drawable.image_uncheck);
        }
        holder.tvName.setText(datas.get(position).getOptionContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单选
                if (isRadio == 1) {
                    datas.get(position).setSelected(!datas.get(position).isSelected());
                    if (datas.get(position).isSelected()) {

                        for (int i = 0; i < datas.size(); i++) {
                            if (i != position) {
                                datas.get(i).setSelected(false);
                            }
                        }
                    }
                }
                //多选
                if (isRadio == 2) {
                    datas.get(position).setSelected(!datas.get(position).isSelected());
                }
               notifyDataSetChanged();
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
        private View itemView;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

    }
}
