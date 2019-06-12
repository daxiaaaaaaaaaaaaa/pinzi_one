package com.jilian.pinzi.adapter.common;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends BaseMultiItemAdapter<T> {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 布局id
     */
    protected int mLayoutId;
    /**
     * 数据源
     */
    protected List<T> mDatas = new ArrayList<>();
    /**
     * 布局inflater
     */
    protected LayoutInflater mInflater;

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局id
     * @param datas    数据源
     */
    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(CommonViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * 添加数据
     *
     * @param isPull 是否是下拉
     * @param data   返回数据
     */
    public void addItem(boolean isPull, List<T> data) {
        if (!isPull && mDatas != null) {
            if (null != data) {
                mDatas.addAll(data);
            }
        } else {
            mDatas.clear();
            if (null != data) {
                mDatas.addAll(data);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void convert(CommonViewHolder holder, T t, int position);


}
