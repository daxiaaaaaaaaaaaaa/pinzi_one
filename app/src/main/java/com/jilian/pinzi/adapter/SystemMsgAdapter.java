package com.jilian.pinzi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SystemMsgAdapter extends RecyclerView.Adapter<SystemMsgAdapter.ViewHolder> {
    private Activity mContext;
    private List<MsgDto> datas;
    private CustomItemClickListener listener;

    public SystemMsgAdapter(Activity context, List<MsgDto> datas, CustomItemClickListener listener) {
        mContext = context;
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_system_msg, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MsgDto newsDto =datas.get(position);
        if(EmptyUtils.isNotEmpty(newsDto)){
            holder.tvTitle.setText(newsDto.getTitle());



            //content是后台返回的h5标签
            holder.tvContent.loadDataWithBaseURL(null,
                    getHtmlData(newsDto.getContent()), "text/html", "utf-8", null);

            //消息的时间
            String day = DateUtil.dateToString(DateUtil.DATE_FORMAT_,new Date(newsDto.getCreateDate()));
            // 昨天
            String nowDay =  DateUtil.dateToString(DateUtil.DATE_FORMAT_,new Date());
            Calendar cal  =   Calendar.getInstance();
            cal.add(Calendar.DATE,   -1);
            //昨天
            String yesterday =  DateUtil.dateToString(DateUtil.DATE_FORMAT_,cal.getTime());;

            if(nowDay.equals(day)){
                String hour = DateUtil.dateToString("HH:mm",new Date(newsDto.getCreateDate()));
                holder.tvDay.setText("今天"+hour);
            }
            else if(yesterday.equals(day)){
                String hour = DateUtil.dateToString("HH:mm",new Date(newsDto.getCreateDate()));
                holder.tvDay.setText("昨天"+hour);
            }
            else{
                String dayHour = DateUtil.dateToString("yyyy年MM月dd日 HH:mm",new Date(newsDto.getCreateDate()));
                holder.tvDay.setText(dayHour);
            }

            holder.tvDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick( holder.tvDetail, position);
                }
            });
            if(TextUtils.isEmpty(newsDto.getrId())){
                holder.ivPoints.setVisibility(View.VISIBLE);
            }
            else{
                holder.ivPoints.setVisibility(View.INVISIBLE);
            }
        }

    }
    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDay;
        private TextView tvTitle;
        private WebView tvContent;
        private TextView tvDetail;
        private ImageView ivPoints;





        public ViewHolder(final View itemView, final CustomItemClickListener listener) {
            super(itemView);
            tvDay = (TextView)itemView. findViewById(R.id.tv_day);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (WebView) itemView.findViewById(R.id.tv_content);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            ivPoints = (ImageView) itemView.findViewById(R.id.iv_points);
        }

    }
}
