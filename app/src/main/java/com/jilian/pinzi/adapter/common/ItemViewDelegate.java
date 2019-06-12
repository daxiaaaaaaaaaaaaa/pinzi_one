package com.jilian.pinzi.adapter.common;


/**
 * @param <T> t
 * @author Created by zhy on 16/6/22
 */
public interface ItemViewDelegate<T> {
    /**
     * 获取item布局id
     *
     * @return int
     */
    int getItemViewLayoutId();

    /**
     * @param item     对象
     * @param position 下标
     * @return true of false
     */
    boolean isForViewType(T item, int position);

    /**
     * 布局转换
     *
     * @param holder   布局holder
     * @param t        对象
     * @param position 下标
     */
    void convert(CommonViewHolder holder, T t, int position);

}
