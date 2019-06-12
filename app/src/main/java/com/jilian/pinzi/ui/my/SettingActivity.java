package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.utils.CleanDataUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {
    private RelativeLayout rlClear;
    private RelativeLayout rlAbout;
    private TextView tvLogout;
    private TextView tvCacheSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setNormalTitle("系统设置", v -> finish());
        rlClear = (RelativeLayout) findViewById(R.id.rl_clear);
        rlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        tvLogout = (TextView) findViewById(R.id.tv_logout);
        tvCacheSize = (TextView) findViewById(R.id.tv_cache_size);

    }

    @Override
    public void initData() {
        try {
            tvCacheSize.setText(CleanDataUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initListener() {
        rlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CleanDataUtils.clearAllCache(SettingActivity.this);
                    ToastUitl.showImageToastSuccess("清理成功");
                    tvCacheSize.setText(CleanDataUtils.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();


            }
        });
    }

    private void showLogoutDialog() {

        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_confirm_order_tips);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)dialog. findViewById(R.id.tv_content);
        tvContent.setText("是否确认退出登录？");
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getLoadingDialog().showDialog();
                RxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
                    @Override
                    public void doNext() {
                        getLoadingDialog().dismiss();
                        ToastUitl.showImageToastSuccess("退出登录成功");
                       // PinziApplication.clearAllActivitys();
                        SPUtil.clearData(Constant.SP_VALUE.SP);
                        startActivity(new Intent(SettingActivity.this, MainActivity.class));
                        finish();
                        PinziApplication.clearAllActivitys();
                    }
                });
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();





    }
}
