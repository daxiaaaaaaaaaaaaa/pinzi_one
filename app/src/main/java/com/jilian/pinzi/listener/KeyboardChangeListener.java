package com.jilian.pinzi.listener;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author ningpan
 * @since 2018/11/6 21:05 <br>
 * description: 键盘的弹出和隐藏的监听
 */
public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "KeyboardChangeListener";
    private View mContentView;
    private int mOriginHeight;
    private int mPreHeight;
    private KeyBoardListener mKeyBoardListen;

    public interface KeyBoardListener {
        /**
         * @param isShow         软键盘状态，true 显示，false 隐藏
         * @param keyboardHeight 软键盘高度
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    public KeyboardChangeListener(Activity context) {
        if (context == null) {
            return;
        }
        mContentView = findContentView(context);
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity context) {
        return context.findViewById(android.R.id.content);
    }

    private void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        int currHeight = mContentView.getHeight();
        if (currHeight == 0) {
            return;
        } //布局高度是否发生变化
        boolean hasChange = false;
        if (mPreHeight == 0) {
            mPreHeight = currHeight;
            mOriginHeight = currHeight;
        } else {
            if (mPreHeight != currHeight) {
                hasChange = true;
                mPreHeight = currHeight;
            } else {
                hasChange = false;
            }
        }
        if (hasChange) {//判断软键盘高度变化是否显示还是隐藏
            boolean isShow;
            int keyboardHeight = 0;
            if (mOriginHeight == currHeight) {
                isShow = false;
            } else {
                keyboardHeight = mOriginHeight - currHeight;
                isShow = true;
            }
            if (mKeyBoardListen != null) {
                mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
            }
        }
    }

    public void destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    }
}
