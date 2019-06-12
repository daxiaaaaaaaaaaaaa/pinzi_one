package com.jilian.pinzi.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author Created by zhy on 16/4/9.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    /**
     * 缓存views
     */
    private SparseArray<View> mViews;
    /**
     * view
     */
    private View mConvertView;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * @param context  上下文
     * @param itemView view
     */
    public CommonViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    /**
     * @param context 上下文
     * @param itemView view
     * @return holder
     */
    public static CommonViewHolder createViewHolder(Context context, View itemView) {
        CommonViewHolder holder = new CommonViewHolder(context, itemView);
        return holder;
    }

    /**
     * @param context 上下文
     * @param parent 组
     * @param layoutId 布局id
     * @return holder holder
     */
    public static CommonViewHolder createViewHolder(Context context,
                                                    ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        CommonViewHolder holder = new CommonViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId 视图id
     * @param <T>    t
     * @return this
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId 视图id
     * @param text 赋值内容
     * @return this 返回
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId 控件id
     * @param resId  资源id
     * @return this
     */
    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置图片bitmap
     *
     * @param viewId 视图id
     * @param bitmap 位图
     * @return this
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置Drawable
     *
     * @param viewId   视图id
     * @param drawable 资源
     * @return this
     */
    public CommonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param viewId 视图id
     * @param color  颜色
     * @return this
     */
    public CommonViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置背景资源
     * @param viewId 视图id
     * @param backgroundRes 资源id
     * @return this
     */
    public CommonViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * 设置颜色
     * @param viewId 视图id
     * @param textColor 颜色
     * @return this
     */
    public CommonViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     *
     * @param viewId 视图id
     * @param textColorRes 资源id
     * @return this
     */
    public CommonViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    /**'
     * 设置透明度
     * @param viewId 视图id
     * @param value 浮点值
     * @return this
     */
    @SuppressLint("NewApi")
    public CommonViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * 设置控件是否可见
     * @param viewId 视图id
     * @param visible 是否可见
     * @return this
     */
    public CommonViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置连接为所有类型
     * @param viewId 视图id
     * @return this
     */
    public CommonViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     *
     * @param typeface Typeface
     * @param viewIds 多个视图id
     * @return this
     */
    public CommonViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * 设置进度条进度
     * @param viewId 视图id
     * @param progress 进度值
     * @return this
     */
    public CommonViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * 设置进度条进度
     * @param viewId 视图id
     * @param progress 进度值
     * @param max 最大值
     * @return this
     */
    public CommonViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * 设置进度条最大值
     * @param viewId 视图id
     * @param max 最大值
     * @return this
     */
    public CommonViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * 设置星级
     * @param viewId 视图id
     * @param rating 值
     * @return this
     */
    public CommonViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置星级
     * @param viewId 视图id
     * @param rating 值
     * @param max 最大值
     * @return this
     */
    public CommonViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置tag
     * @param viewId 视图id
     * @param tag tag内容
     * @return this
     */
    public CommonViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * 给控件设置tag
     * @param viewId 视图id
     * @param key 键
     * @param tag 内容
     * @return this
     */
    public CommonViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * 设置check
     * @param viewId 视图id
     * @param checked 状态
     * @return this
     */
    public CommonViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     *
     * @param viewId 视图id
     * @param listener 监听
     * @return this
     */
    public CommonViewHolder setOnClickListener(int viewId,
                                               View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置touch监听
     * @param viewId 视图id
     * @param listener touch监听
     * @return this
     */
    public CommonViewHolder setOnTouchListener(int viewId,
                                               View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     *
     * @param viewId 视图id
     * @param listener long监听
     * @return this
     */
    public CommonViewHolder setOnLongClickListener(int viewId,
                                                   View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


}
