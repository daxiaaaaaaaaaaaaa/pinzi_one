package com.jilian.pinzi.ui;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.VersionInfoDto;
import com.jilian.pinzi.common.msg.MainCreatMessage;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.common.msg.WxPayMessage;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.service.DownloadIntentService;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.BadgeUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements DownloadIntentService.UpdateUi {
    private List<Fragment> mFragmentList;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private FiveFragment fiveFragment;
    private CommonViewPagerAdapter mainTapPagerAdapter;
    private NoScrollViewPager viewPager;


    private RelativeLayout rlOne;
    private ImageView ivOne;
    private TextView tvOne;
    private RelativeLayout rlTwo;
    private ImageView ivTwo;
    private TextView tvTwo;
    private RelativeLayout rlThree;
    private ImageView ivThree;
    private TextView tvThree;
    private RelativeLayout rlFour;
    private ImageView ivFour;
    private TextView tvFour;
    private RelativeLayout rlFive;
    private ImageView ivFive;
    private TextView tvFive;
    private int index;
    public LinearLayout llBottom;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
        PinziApplication.clearAllActivitysAdditionActivty(this);



    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxTimerUtil.cancel();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        viewPager.setCurrentItem(intent.getIntExtra("index",0));
        //首页创建，像各个fragment发送消息
        MessageEvent messageEvent = new MessageEvent();
        MainCreatMessage message = new MainCreatMessage();
        message.setCode(200);
        messageEvent.setMainCreatMessage(message);
        EventBus.getDefault().post(messageEvent);

    }

    @Override
    protected void createViewModel() {
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(5);
//        viewPager.setScanScroll(false);
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        ivOne = (ImageView) findViewById(R.id.iv_one);
        tvOne = (TextView) findViewById(R.id.tv_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        rlThree = (RelativeLayout) findViewById(R.id.rl_three);
        ivThree = (ImageView) findViewById(R.id.iv_three);
        tvThree = (TextView) findViewById(R.id.tv_three);
        rlFour = (RelativeLayout) findViewById(R.id.rl_four);
        ivFour = (ImageView) findViewById(R.id.iv_four);
        tvFour = (TextView) findViewById(R.id.tv_four);
        rlFive = (RelativeLayout) findViewById(R.id.rl_five);
        ivFive = (ImageView) findViewById(R.id.iv_five);
        tvFive = (TextView) findViewById(R.id.tv_five);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    @Override
    public void initData() {
        PinziApplication.clearSpecifyActivities(new Class[]{WelcomeActivity.class, WelcomeActivity.class});
        mFragmentList = new ArrayList<>();
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();
        fiveFragment = new FiveFragment();
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fourFragment);
        mFragmentList.add(fiveFragment);

        //
        mainTapPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mainTapPagerAdapter);
        getVersionInfo();

    }


    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ivOne.setImageResource(R.drawable.image_home_selected);
                        tvOne.setTextColor(Color.parseColor("#c71233"));

                        ivTwo.setImageResource(R.drawable.image_type_unselect);
                        tvTwo.setTextColor(Color.parseColor("#999999"));

                        ivThree.setImageResource(R.drawable.image_circle_unselect);
                        tvThree.setTextColor(Color.parseColor("#999999"));

                        ivFour.setImageResource(R.drawable.image_shopping_unselect);
                        tvFour.setTextColor(Color.parseColor("#999999"));

                        ivFive.setImageResource(R.drawable.image_my_unselect);
                        tvFive.setTextColor(Color.parseColor("#999999"));
                        break;
                    case 1:

                        ivOne.setImageResource(R.drawable.image_home_unselecte);
                        tvOne.setTextColor(Color.parseColor("#999999"));

                        ivTwo.setImageResource(R.drawable.image_type_select);
                        tvTwo.setTextColor(Color.parseColor("#c71233"));

                        ivThree.setImageResource(R.drawable.image_circle_unselect);
                        tvThree.setTextColor(Color.parseColor("#999999"));

                        ivFour.setImageResource(R.drawable.image_shopping_unselect);
                        tvFour.setTextColor(Color.parseColor("#999999"));

                        ivFive.setImageResource(R.drawable.image_my_unselect);
                        tvFive.setTextColor(Color.parseColor("#999999"));

                        break;
                    case 2:

                        ivOne.setImageResource(R.drawable.image_home_unselecte);
                        tvOne.setTextColor(Color.parseColor("#999999"));

                        ivTwo.setImageResource(R.drawable.image_type_unselect);
                        tvTwo.setTextColor(Color.parseColor("#999999"));

                        ivThree.setImageResource(R.drawable.image_circle_select);
                        tvThree.setTextColor(Color.parseColor("#c71233"));

                        ivFour.setImageResource(R.drawable.image_shopping_unselect);
                        tvFour.setTextColor(Color.parseColor("#999999"));

                        ivFive.setImageResource(R.drawable.image_my_unselect);
                        tvFive.setTextColor(Color.parseColor("#999999"));

                        break;
                    case 3:
                        ivOne.setImageResource(R.drawable.image_home_unselecte);
                        tvOne.setTextColor(Color.parseColor("#999999"));

                        ivTwo.setImageResource(R.drawable.image_type_unselect);
                        tvTwo.setTextColor(Color.parseColor("#999999"));

                        ivThree.setImageResource(R.drawable.image_circle_unselect);
                        tvThree.setTextColor(Color.parseColor("#999999"));

                        ivFour.setImageResource(R.drawable.image_shopping_select);
                        tvFour.setTextColor(Color.parseColor("#c71233"));

                        ivFive.setImageResource(R.drawable.image_my_unselect);
                        tvFive.setTextColor(Color.parseColor("#999999"));
                        break;
                    case 4:
                        ivOne.setImageResource(R.drawable.image_home_unselecte);
                        tvOne.setTextColor(Color.parseColor("#999999"));

                        ivTwo.setImageResource(R.drawable.image_type_unselect);
                        tvTwo.setTextColor(Color.parseColor("#999999"));

                        ivThree.setImageResource(R.drawable.image_circle_unselect);
                        tvThree.setTextColor(Color.parseColor("#999999"));

                        ivFour.setImageResource(R.drawable.image_shopping_unselect);
                        tvFour.setTextColor(Color.parseColor("#999999"));

                        ivFive.setImageResource(R.drawable.image_my_select);
                        tvFive.setTextColor(Color.parseColor("#c71233"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        rlFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getLoginDto() == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                viewPager.setCurrentItem(3);
            }
        });
        rlFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getLoginDto() == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                viewPager.setCurrentItem(4);
            }
        });
        //
        index = getIntent().getIntExtra("index", 0);
        viewPager.setCurrentItem(index);
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getIntExtra("back", 1) != 2) {
                exitBy2Click(); //调用双击退出函数
                return false;
            } else {
                finish();
                return true;
            }
        }
        return true;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    /**
     * 退出应用程序
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            ToastUitl.showImageToastSuccess("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            //System.exit(0);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

        }
    }

    private MyViewModel myViewModel;

    /**
     * 获取版本
     */
    private void getVersionInfo() {
        myViewModel.getVersionInfo();
        myViewModel.getUpdateLiveData().observe(this, new Observer<BaseDto<VersionInfoDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<VersionInfoDto> versionInfoDtoBaseDto) {
                String versionName = PackageUtils.getVersionName(MainActivity.this);
                if (versionInfoDtoBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(versionInfoDtoBaseDto.getData())) {
                    if (!versionName.equals(versionInfoDtoBaseDto.getData().getVersionNo())) {
                        showUpdateDialog(versionInfoDtoBaseDto.getData());
                    }

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
                                finish();
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
        Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
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

