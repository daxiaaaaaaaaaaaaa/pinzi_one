package com.jilian.pinzi.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author Created by zhy on 16/4/9.
 */
public class BaseMultiItemAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据源
     */
    protected List<T> mDatas = new ArrayList<>();
    /**
     * 点击标识
     */
    private boolean clickFlag = true;
    /**
     *
     */
    protected ItemViewDelegateManager mItemViewDelegateManager;
    /**
     * 点击监听
     */
    protected OnItemClickListener mOnItemClickListener;
    /**
     *
     */
    protected OnItemPostionClickListener onItemPostionClickListener;


    public BaseMultiItemAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加item
     *
     * @param isPull 是否是下拉
     * @param data   返回数据
     */
    public void addItem(boolean isPull, List<T> data) {
        if (!isPull && mDatas != null) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        } else {
            mDatas.clear();
            mDatas.addAll(data);
            notifyDataSetChanged();

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            return super.getItemViewType(position);
        }
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        CommonViewHolder holder = CommonViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    /**
     * onViewHolderCreated
     *
     * @param holder
     * @param itemView
     */
    public void onViewHolderCreated(CommonViewHolder holder, View itemView) {

    }

    /**
     * 转换 holder
     *
     * @param holder 转换holder
     * @param item   泛型对象
     */
    public void convert(CommonViewHolder holder, T item) {
        mItemViewDelegateManager.convert(holder, item, holder.getAdapterPosition());
    }

    /**
     * @param viewType
     * @return true
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * 设置监听
     *
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    protected void setListener(final ViewGroup parent, final CommonViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickFlag && mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
                clickFlag = true;
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                clickFlag = false;
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() == 0) {
            return 0;
        } else {
            int itemCount = mDatas.size();
            return itemCount;
        }
    }


    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * @param itemViewDelegate
     * @return this
     */
    public BaseMultiItemAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * @param viewType
     * @param itemViewDelegate
     * @return this
     */
    public BaseMultiItemAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * @return mItemViewDelegateManager.getItemViewDelegateCount() > 0
     */
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 点击监听interface
     */
    public interface OnItemClickListener {
        /**
         * 点击
         *
         * @param view
         * @param holder
         * @param position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        /**
         * 长按点击
         *
         * @param view
         * @param holder
         * @param position
         * @return postion
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    /**
     * 设置控件点击监听
     *
     * @param itemPostionClickListener
     */
    public void setOnItemPostionClickListener(OnItemPostionClickListener itemPostionClickListener) {
        this.onItemPostionClickListener = itemPostionClickListener;
    }

    /**
     * 控件点击监听interface
     */
    public interface OnItemPostionClickListener {
        /**
         * 点击
         *
         * @param position 位置
         */
        void onItemPostionClick(int position);

    }
}
