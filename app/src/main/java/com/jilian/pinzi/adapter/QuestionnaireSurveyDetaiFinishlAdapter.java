package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.QuestionDetailDto;
import com.jilian.pinzi.common.dto.QuestionItemDto;
import com.jilian.pinzi.listener.CustomItemClickListener;

import java.util.Arrays;
import java.util.List;


public class QuestionnaireSurveyDetaiFinishlAdapter extends RecyclerView.Adapter<QuestionnaireSurveyDetaiFinishlAdapter.ViewHolder> implements CustomItemClickListener {
    private Activity mContext;
    private List<QuestionDetailDto> datas;
    private CustomItemClickListener listener;

    public QuestionnaireSurveyDetaiFinishlAdapter(Activity context, List<QuestionDetailDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_questionnaire_survey_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getTitle());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        //设置选中 和 不选中
        initSelect(datas.get(position).getOptions(), datas.get(position).getAnswerOption());
        //设置适配器
        holder.recyclerView.setAdapter(new QuestionnaireSurveyDetaiItemFinishAdapter(mContext, datas.get(position).getOptions(),  datas.get(position).getAnswerOption()));
    }

    /**
     * 初始化选中状态
     *
     * @param options
     * @param answerOption
     */
    private void initSelect(List<QuestionItemDto> options, String answerOption) {
        if (TextUtils.isEmpty(answerOption)) {
            return;
        }
        if (answerOption.contains(",")) {
            String array[] = answerOption.split(",");
            List<String> list = Arrays.asList(array);
            for (int i = 0; i < options.size(); i++) {
                if (list.contains(options.get(i))) {
                    options.get(i).setSelected(true);
                } else {
                    options.get(i).setSelected(false);
                }
            }
        } else {
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).getId().equals(answerOption)) {
                    options.get(i).setSelected(true);
                } else {
                    options.get(i).setSelected(false);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private RecyclerView recyclerView;

        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);


            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
