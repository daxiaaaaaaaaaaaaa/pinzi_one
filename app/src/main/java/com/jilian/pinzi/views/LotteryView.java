package com.jilian.pinzi.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LotteryInfoDto;
import com.jilian.pinzi.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ningpan
 * @since 2018/12/5 17:02 <br>
 * description: 抽奖 View
 */
public class LotteryView extends View {

    private static final String TAG = LotteryView.class.getSimpleName();
    private Paint mBitmapPaint;
    private Paint mTextPaint;

    private Bitmap mInnerBitmap;
    private Rect mInnerRect;

    private int screenWidth;

    /** 奖品名称 */
    private List<LotteryInfoDto> mLotteryList = new ArrayList<>();
    private int initDegrees = 0;

    /** 旋转一圈所需要的时间 */
    private static final long ONE_WHEEL_TIME = 300;
    /** 指针大小 */
    private int pointerSize;

    public LotteryView(Context context) {
        this(context, null);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        initData();
        screenWidth = ScreenUtils.getScreenWidth(context);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.color_white));
        mTextPaint.setTextSize(ScreenUtils.sp2px(context, 14));

        mInnerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_home_luck_draw);
        pointerSize = (screenWidth - ScreenUtils.dip2px(getContext(), 42)) / 2;
        mInnerRect = new Rect(-pointerSize / 2, -pointerSize / 2, pointerSize / 2, pointerSize / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        // 将圆点移至视图中心
        canvas.translate(width / 2, height / 2);
        // 画奖品文字
        float degrees = -90;
        for (int i = 0; i < mLotteryList.size(); i++) {
            String lottery = mLotteryList.get(i).getName();
            canvas.save();
            canvas.rotate(degrees);
            int radius = ScreenUtils.dip2px(getContext(), 100);
            Path path = new Path();
            RectF range = new RectF(-radius, -radius, radius, radius);
            float startAngle = -17;
            float sweepAngle = 36;
            path.addArc(range, startAngle, sweepAngle);
            String text1 = "";
            String text2 = "";
            int lotteryLength = lottery.length();
            if (lotteryLength > 4) {
                text1 = lottery.substring(0, 4);
                text2 = lottery.substring(4, lotteryLength > 7 ? 7 : lotteryLength);
            } else {
                text1 = lottery;
            }
            canvas.drawTextOnPath(text1, path, 0, 0, mTextPaint);
            if (text2.length() > 0) {
                Rect text1Rect = new Rect();
                mTextPaint.getTextBounds(text1, 0, text1.length(), text1Rect);
                float text1Height = text1Rect.height() + ScreenUtils.dip2px(getContext(), 3);
                // 需要同比例缩小, 才能划线.
                RectF text2Rect = new RectF(-radius + text1Height, -radius + text1Height,
                        radius - text1Height, radius - text1Height);
                Path path2 = new Path();
                path2.addArc(text2Rect, startAngle, sweepAngle);
                canvas.drawTextOnPath(text2, path2, 0, 0, mTextPaint);
            }
            degrees += 36;
            canvas.restore();
        }
        // 画指针
        canvas.save();
        canvas.rotate(initDegrees);
        canvas.drawBitmap(mInnerBitmap, null, mInnerRect, mBitmapPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    private int measureHeight(int measureSpec) {
        int height = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        if (MeasureSpec.AT_MOST == mode) {
            height = screenWidth - ScreenUtils.dip2px(getContext(), 62);
        }
        return height;
    }

    /** 奖品名称集合 */
    public void setLotteryList(List<LotteryInfoDto> list) {
        this.mLotteryList = list;
        invalidate();
    }

    public void start(int pos) {
        initDegrees = 0;
        // 旋转的圈数, 默认 10 圈
//        int lap = (int) (Math.random() * 12) + 4;
        int lap = 10;
        int angle = (pos - 1) * 36;
        // 总共转的时间
        long duration = (lap + angle / 360) * ONE_WHEEL_TIME;
        int start = initDegrees;
        int end = lap * 360 + angle + initDegrees;
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            int updateValue = (int) animation.getAnimatedValue();
            initDegrees = (updateValue % 360 + 360) % 360;
            ViewCompat.postInvalidateOnAnimation(LotteryView.this);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LotteryView.this.setEnabled(true);
                if (onLotteryListener != null) onLotteryListener.onLotteryEnd();
            }
        });
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO 懒得自定义两个View了, 这里直接控制点击范围.
        float touchX = event.getRawX();
        float touchY = event.getRawY();
        int pointerLength = ScreenUtils.dip2px(getContext(), 10); // 指针尖尖的长度
        float startTouchX = screenWidth - ScreenUtils.dip2px(getContext(), 30)
                - getMeasuredHeight() / 2 - pointerSize / 2 + pointerLength;
        float endTouchX = startTouchX + pointerSize - pointerLength;
        float startTouchY = ScreenUtils.dip2px(getContext(), 192)
                + getMeasuredHeight() / 2 - pointerSize / 2 + pointerLength;
        float endTouchY = startTouchY + pointerSize - pointerLength;
//        Log.i(TAG, "onTouchEvent X: " + touchX + ", Y: " + touchY);
//        Log.e(TAG, "onTouchEvent X1: " + startTouchX + ", X2: " + endTouchX);
//        Log.w(TAG, "onTouchEvent Y1: " + startTouchY + ", Y2: " + endTouchY);
        if (touchX >= startTouchX && touchX <= endTouchX && touchY >= startTouchY && touchY <= endTouchY) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean performClick() {
//        Log.i(TAG, "performClick.....");
        return super.performClick();
    }

    private OnLotteryListener onLotteryListener;

    public void setOnLotteryListener(OnLotteryListener onLotteryListener) {
        this.onLotteryListener = onLotteryListener;
    }

    public interface OnLotteryListener {
        /** 幸运转盘转动结束 */
        void onLotteryEnd();
    }
}
