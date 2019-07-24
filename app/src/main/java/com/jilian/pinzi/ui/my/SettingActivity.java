package com.jilian.pinzi.ui.my;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.VersionInfoDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.service.DownloadIntentService;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.PackageUtils;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.CleanDataUtils;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.List;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity implements DownloadIntentService.UpdateUi {
    private RelativeLayout rlClear;
    private RelativeLayout rlAbout;
    private TextView tvLogout;
    private TextView tvCacheSize;
    private TextView tvVersion;
    private RelativeLayout rlUpdate;


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
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
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
        tvVersion = (TextView) findViewById(R.id.tv_version);
        rlUpdate = (RelativeLayout) findViewById(R.id.rl_update);

    }

    @Override
    public void initData() {
        try {
            tvCacheSize.setText(CleanDataUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvVersion.setText(PackageUtils.getVersionName(this));

    }

    @Override
    public void initListener() {
        rlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVersionInfo();
            }
        });
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
        rlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });
    }

    private void showLogoutDialog() {

        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_confirm_order_tips);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
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

    private MyViewModel myViewModel;

    /**
     * 获取版本
     */
    private void getVersionInfo() {
        showLoadingDialog();
        myViewModel.getVersionInfo();
        myViewModel.getUpdateLiveData().observe(this, new Observer<BaseDto<VersionInfoDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<VersionInfoDto> versionInfoDtoBaseDto) {
                hideLoadingDialog();
                String versionName = PackageUtils.getVersionName(SettingActivity.this);
                if (versionInfoDtoBaseDto.isSuccess()&&EmptyUtils.isNotEmpty(versionInfoDtoBaseDto.getData())) {
                    if (!versionName.equals(versionInfoDtoBaseDto.getData().getVersionNo())) {
                        showUpdateDialog(versionInfoDtoBaseDto.getData());
                    } else {
                        ToastUitl.showImageToastSuccess("当前已经是最新版本");
                    }
                } else {
                    ToastUitl.showImageToastSuccess("当前已经是最新版本");
                }

            }
        });

    }

    /**
     * 弹出更新对话框
     */
    private ProgressBar progressBar;
    private TextView tvPercent;
    private TextView tvOk;

    private void showUpdateDialog(VersionInfoDto data) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_update_show)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        TextView tvContent = (TextView) holder.getView(R.id.tv_content);
                        ImageView ivCancel = (ImageView) holder.getView(R.id.iv_cancel);
                        tvOk = (TextView) holder.getView(R.id.tv_ok);
                        tvContent.setText(data == null ? "" : data.getDescs());
                        progressBar = (ProgressBar) holder.getView(R.id.progressBar);
                        tvPercent = (TextView) holder.getView(R.id.tv_percent);
                        progressBar.setMax(100);
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateApk(data, dialog);
                            }
                        });
                    }
                })
                .show(getSupportFragmentManager());
    }


    private static final int DOWNLOADAPK_ID = 10;

    /**
     * 更新版本
     */
    private void updateApk(VersionInfoDto dto, final BaseNiceDialog dialog) {
        tvOk.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvPercent.setVisibility(View.VISIBLE);
        DownloadIntentService.updateUi(this);
        ToastUitl.showImageToastSuccess("开始下载...");
        //dialog.dismiss();
        if (isServiceRunning(DownloadIntentService.class.getName())) {
            ToastUitl.showImageToastSuccess("正在下载...");
            return;
        }
        Intent intent = new Intent(SettingActivity.this, DownloadIntentService.class);
        Bundle bundle = new Bundle();
        http:
//39.108.14.94:9007/donghui_oa/IMG/20190716105609.apk
        bundle.putString("download_url", dto.getLinkUrl());
        bundle.putInt("download_id", DOWNLOADAPK_ID);
        //apk 的名字
        bundle.putString("download_file", dto.getLinkUrl().substring(dto.getLinkUrl().lastIndexOf('/') + 1));
        intent.putExtras(bundle);
        startService(intent);
    }

    /**
     * 实时更新下载安装包进度条
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.what;
            //更新对话框的进度条
            progressBar.setProgress(progress);
            tvPercent.setText((progress == 101 ? 100 : progress) + "%");

        }
    };

    @Override
    public void update(int progress) {
        Message msg = Message.obtain();
        msg.what = progress;
        handler.sendMessage(msg);
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    private boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    //不要删这行代码
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}

