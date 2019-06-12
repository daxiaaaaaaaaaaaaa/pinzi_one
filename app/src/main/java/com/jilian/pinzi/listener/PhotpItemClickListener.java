package com.jilian.pinzi.listener;

import android.view.View;

/**
 * item点击事件
 */
public interface PhotpItemClickListener {
    void onItemClick(View view, int position,int type);
    void close(int position,int type);
}
