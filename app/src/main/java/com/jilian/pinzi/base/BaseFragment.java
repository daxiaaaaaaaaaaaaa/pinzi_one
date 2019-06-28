package com.jilian.pinzi.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.views.LoadingDialog;


/**
 * Fragment 基类
 *
 * @author weishixiong
 * @Time 2018-03-19
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected final String TAG = this.getClass().getSimpleName();

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public LoginDto getLoginDto(){
        return SPUtil.getData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,LoginDto.class,null);
    }



    protected abstract void loadData();


    /**
     * 获得全局的，防止使用getmActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);
        createViewModel();
        initView(view, savedInstanceState);
        initListener();
        return view;
    }



    /**
     * 创建presenter
     */
    protected abstract void createViewModel();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 登录拦截
     */
    public boolean initLoginInterceptor() {
        return false;
    }

    public void setNormalTitle(String title) {
        setCenterTitle(title, "#FFFFFF");
        setTitleBg(R.color.color_black);
    }

    public void setNormalTitle(String title, View.OnClickListener backListener) {
        setCenterTitle(title, "#FFFFFF");
        setleftImage(R.drawable.image_back, true, backListener);
        setTitleBg(R.color.color_black);
    }

    public void setNormalTitle(String title, View.OnClickListener backListener, String rightText, View.OnClickListener rightListener) {
        setCenterTitle(title, "#FFFFFF");
        setleftImage(R.drawable.image_back, true, backListener);
        setTitleBg(R.color.color_black);
        setrightTitle(rightText, "#FFFFFF", rightListener);
    }

    public void setNormalTitle(String title, int rightResource, View.OnClickListener rightListener) {
        setCenterTitle(title, "#FFFFFF");
        setTitleBg(R.color.color_black);
        setrightImage(rightResource, rightListener);
    }

    public void setNormalTitle(String title, View.OnClickListener backListener, int rightResource, View.OnClickListener rightListener) {
        setCenterTitle(title, "#FFFFFF");
        setleftImage(R.drawable.image_back, true, backListener);
        setTitleBg(R.color.color_black);
        setrightImage(rightResource, rightListener);
    }

    /**
     * 设置背景图片
     **/
    public void setTitleBg(int color) {
        LinearLayout lLtitle = (LinearLayout) view.findViewById(R.id.ll_title);
        lLtitle.setBackgroundResource(color);

    }

    /**
     * 设置中间标题
     **/
    public void setCenterTitle(String text, String color) {
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(color)) {
            textView.setTextColor(Color.parseColor(color));
        }
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);


    }

    /**
     * 设置左边标题
     **/
    public void setleftTitle(String text, String color, boolean isfinish, View.OnClickListener listener) {
        TextView textView = (TextView) view.findViewById(R.id.tv_left_text);
        if (!TextUtils.isEmpty(color)) {
            textView.setTextColor(Color.parseColor(color));
        }
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
        if (isfinish) {
            view.findViewById(R.id.v_back).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.v_back).setVisibility(View.GONE);
            textView.setOnClickListener(listener);
        }

    }

    /**
     * 设置右边标题
     **/
    public void setrightTitle(String text, String color, View.OnClickListener listener) {
        TextView textView = (TextView) view.findViewById(R.id.tv_right_text);
        if (!TextUtils.isEmpty(color)) {
            textView.setTextColor(Color.parseColor(color));
        }
        textView.setText(text);
        textView.setOnClickListener(listener);
        textView.setVisibility(View.VISIBLE);

    }

    /**
     * 设置左边图片
     **/
    public void setleftImage(int resource, boolean isfinish, View.OnClickListener listener) {
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_left_text);
        imageView.setImageResource(resource);
        imageView.setVisibility(View.VISIBLE);
        if (isfinish) {
            view.findViewById(R.id.v_back).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.v_back).setVisibility(View.GONE);
            imageView.setOnClickListener(listener);
        }

    }

    /**
     * 设置右边图片
     **/
    public void setrightImage(int resource, View.OnClickListener listener) {
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_right_text);
        imageView.setImageResource(resource);
        imageView.setOnClickListener(listener);
        imageView.setVisibility(View.VISIBLE);

    }
    private LoadingDialog loadingDialog;//加载提示框


    public LoadingDialog getLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getmActivity());
            //点击空白处Dialog不消失
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        return loadingDialog;
    }


}
