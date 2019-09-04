package com.jilian.pinzi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.CommonAdapter;
import com.jilian.pinzi.adapter.common.CommonViewHolder;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.NewsDto;
import com.jilian.pinzi.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ningpan
 * @since 2018/10/30 17:40 <br>
 * description: 新闻公告 Adapter
 */
public class NewsNoticeAdapter extends CommonAdapter<MsgDto> {

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public NewsNoticeAdapter(Context context, int layoutId, List<MsgDto> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, MsgDto newsDto, int position) {
        TextView tvEdit = holder.getView(R.id.tv_detail);
        TextView tvDay = holder.getView(R.id.tv_day);
        TextView tvTitle = holder.getView(R.id.tv_title);
        WebView tvContent = holder.getView(R.id.tv_content);
        ImageView ivPoints= holder.getView(R.id.iv_points);
        tvTitle.setText(newsDto.getTitle());


        //content是后台返回的h5标签
        tvContent.loadDataWithBaseURL(null,
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
            tvDay.setText("今天"+hour);
        }
        else if(yesterday.equals(day)){
            String hour = DateUtil.dateToString("HH:mm",new Date(newsDto.getCreateDate()));
            tvDay.setText("昨天"+hour);
        }
        else{
            String dayHour = DateUtil.dateToString("yyyy年MM月dd日 HH:mm",new Date(newsDto.getCreateDate()));
            tvDay.setText(dayHour);
        }
        if(TextUtils.isEmpty(newsDto.getrId())){
            ivPoints.setVisibility(View.VISIBLE);
        }
        else{
            ivPoints.setVisibility(View.INVISIBLE);
        }
        tvEdit.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onEditClick(tvEdit, position);
        });
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
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onEditClick(View view, int position);
    }
}
