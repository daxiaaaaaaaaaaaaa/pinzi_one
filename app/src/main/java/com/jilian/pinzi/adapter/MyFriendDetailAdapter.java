package com.jilian.pinzi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.List;


public class MyFriendDetailAdapter extends RecyclerView.Adapter<MyFriendDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<FriendCircleListDto> datas;
    private FriendListener friendListener;

    public interface FriendListener {

        void driendCircleDelete(String id);

        void clickCircle(String id);

    }

    public MyFriendDetailAdapter(Context context, List<FriendCircleListDto> datas, FriendListener friendListener) {
        mContext = context;
        this.datas = datas;
        this.friendListener = friendListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_my_friends_circle_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(datas.get(position).getContent());
        holder.tvContentNo.setText(datas.get(position).getContent());
        String imgUrl = datas.get(position).getImgUrl();

        if (TextUtils.isEmpty(imgUrl)) {
            holder.tvCount.setText("共0张");
            holder.tvCountNo.setText("共0张");
            holder.llOne.setVisibility(View.GONE);
            holder.llTwo.setVisibility(View.VISIBLE);
        } else {
            holder.llOne.setVisibility(View.VISIBLE);
            holder.llTwo.setVisibility(View.GONE);
            if (imgUrl.contains(",")) {
                holder.tvCount.setText("共" + imgUrl.split(",").length + "张");
                holder.tvCountNo.setText("共" + imgUrl.split(",").length + "张");
                String[] urls = imgUrl.split(",");
                switch (urls.length) {
                    case 2:
                        holder.llImageOne.setVisibility(View.VISIBLE);
                        holder.llImageTwo.setVisibility(View.VISIBLE);
                        holder.ivOne.setVisibility(View.VISIBLE);
                        holder.ivTwo.setVisibility(View.VISIBLE);
                        holder.ivThree.setVisibility(View.GONE);
                        holder.ivFour.setVisibility(View.GONE);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[0]))
                                .into(holder.ivOne);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[1]))
                                .into(holder.ivTwo);
                        break;
                    case 3:
                        holder.llImageOne.setVisibility(View.VISIBLE);
                        holder.llImageTwo.setVisibility(View.VISIBLE);
                        holder.ivOne.setVisibility(View.VISIBLE);
                        holder.ivTwo.setVisibility(View.VISIBLE);
                        holder.ivThree.setVisibility(View.GONE);
                        holder.ivFour.setVisibility(View.VISIBLE);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[0]))
                                .into(holder.ivOne);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[1]))
                                .into(holder.ivTwo);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[2]))
                                .into(holder.ivFour);
                        break;
                    case 4:
                        holder.llImageOne.setVisibility(View.VISIBLE);
                        holder.llImageTwo.setVisibility(View.VISIBLE);
                        holder.ivOne.setVisibility(View.VISIBLE);
                        holder.ivTwo.setVisibility(View.VISIBLE);
                        holder.ivThree.setVisibility(View.VISIBLE);
                        holder.ivFour.setVisibility(View.VISIBLE);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[0]))
                                .into(holder.ivOne);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[1]))
                                .into(holder.ivTwo);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[2]))
                                .into(holder.ivThree);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[3]))
                                .into(holder.ivFour);
                        break;
                    default:
                        holder.llImageOne.setVisibility(View.VISIBLE);
                        holder.llImageTwo.setVisibility(View.VISIBLE);
                        holder.ivOne.setVisibility(View.VISIBLE);
                        holder.ivTwo.setVisibility(View.VISIBLE);
                        holder.ivThree.setVisibility(View.VISIBLE);
                        holder.ivFour.setVisibility(View.VISIBLE);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[0]))
                                .into(holder.ivOne);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[1]))
                                .into(holder.ivTwo);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[2]))
                                .into(holder.ivThree);
                        Glide
                                .with(mContext)
                                .load(UrlUtils.getUrl(urls[3]))
                                .into(holder.ivFour);
                        break;
                }

            } else {
                holder.tvCount.setText("共1张");
                holder.tvCountNo.setText("共" + imgUrl.split(",").length + "张");

                holder.llImageOne.setVisibility(View.VISIBLE);
                holder.llImageTwo.setVisibility(View.GONE);
                holder.ivOne.setVisibility(View.VISIBLE);
                holder.ivTwo.setVisibility(View.GONE);
                holder.ivThree.setVisibility(View.GONE);
                holder.ivFour.setVisibility(View.GONE);

                Glide
                        .with(mContext)
                        .load(UrlUtils.getUrl(imgUrl))
                        .into(holder.ivOne);
            }
        }
        if (datas.get(position).getuId() != null && datas.get(position).getuId().equals(PinziApplication.getInstance().getLoginDto().getId())) {
            holder.tvDeleteNo.setVisibility(View.VISIBLE);
            holder.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvDeleteNo.setVisibility(View.GONE);
            holder.tvDelete.setVisibility(View.GONE);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendListener.driendCircleDelete(datas.get(position).getId());
            }
        });
        holder.tvDeleteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendListener.driendCircleDelete(datas.get(position).getId());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendListener.clickCircle(datas.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHead;
        TextView tvContent;
        TextView tvCount;
        TextView tvDelete;
        private TextView tvContentNo;
        private TextView tvCountNo;
        private TextView tvDeleteNo;
        private LinearLayout llOne;
        private LinearLayout llTwo;
        private View itemView;
        private LinearLayout llImageOne;
        private ImageView ivOne;
        private ImageView ivTwo;
        private LinearLayout llImageTwo;
        private ImageView ivThree;
        private ImageView ivFour;


        public ViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvContentNo = (TextView) itemView.findViewById(R.id.tv_content_no);
            tvCountNo = (TextView) itemView.findViewById(R.id.tv_count_no);
            tvDeleteNo = (TextView) itemView.findViewById(R.id.tv_delete_no);
            llOne = (LinearLayout) itemView.findViewById(R.id.ll_one);
            llTwo = (LinearLayout) itemView.findViewById(R.id.ll_two);
            llImageOne = (LinearLayout) itemView.findViewById(R.id.ll_image_one);
            ivOne = (ImageView) itemView.findViewById(R.id.iv_one);
            ivTwo = (ImageView) itemView.findViewById(R.id.iv_two);
            llImageTwo = (LinearLayout) itemView.findViewById(R.id.ll_image_two);
            ivThree = (ImageView) itemView.findViewById(R.id.iv_three);
            ivFour = (ImageView) itemView.findViewById(R.id.iv_four);

        }

    }
}
