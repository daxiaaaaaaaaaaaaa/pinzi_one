package com.jilian.pinzi.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.utils.ScreenUtils;

public class TimeLineDecoration extends RecyclerView.ItemDecoration {
    /** 时间轴宽度 */
    private int width;
    /** 线条宽度 */
    private int dividerWidth;
    /** 时间抽首图标 */
    private Drawable firstDrawable;
    /** 时间抽首图标大小 */
    private int firstDrawableHeight;
    /** 小圆点半径 */
    private int ovalRadius;
    /** 竖线颜色 */
    private int lineColor = 0xffd8d8d8;
    private Paint mPaint;
    private Paint mRedPaint;

    public TimeLineDecoration(int width, int dividerWidth, Drawable firstDrawable, int firstDrawableHeight, int ovalRadius) {
        this.width = width;
        this.dividerWidth = dividerWidth;
        this.firstDrawable = firstDrawable;
        this.firstDrawableHeight = firstDrawableHeight;
        this.ovalRadius = ovalRadius;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(lineColor);
        mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedPaint.setColor(0xffc71233);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int topLineHeight = ScreenUtils.dip2px(parent.getContext(), 10);
        int position = parent.getChildAdapterPosition(view);
        // 设置 item 内容左上右下的 padding 值.
        outRect.set(width, position == 0 ? topLineHeight : 0, width, dividerWidth);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int childTop = child.getTop();
            int childBottom = child.getBottom();

            // 画竖线
            int left = child.getPaddingLeft() + width / 2;
            int offsets = ScreenUtils.dip2px(parent.getContext(), 3);
            canvas.drawRect(left,
                    i == 0 ? childTop + firstDrawableHeight + offsets : childTop + offsets + ovalRadius,
                    left + dividerWidth,
                    i == 0 ? childBottom - offsets : childBottom - offsets, mPaint);
            // 画图标/圆
            if (i == 0) {
                // 画第一个 item 的竖直红线
                int topLineHeight = ScreenUtils.dip2px(parent.getContext(), 10);
                canvas.drawRect(left,
                        childTop - topLineHeight,
                        left + dividerWidth,
                        childTop - offsets, mRedPaint);
                // 画第一个 item 的图标
                firstDrawable.setBounds(left - firstDrawableHeight / 2, childTop, left + firstDrawableHeight / 2, childTop + firstDrawableHeight);
                firstDrawable.draw(canvas);
            } else {
                canvas.drawCircle(left, childTop + ovalRadius / 2, ovalRadius, mPaint);
            }
        }
    }
}
