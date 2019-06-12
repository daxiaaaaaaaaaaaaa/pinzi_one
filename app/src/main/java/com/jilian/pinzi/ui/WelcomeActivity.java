package com.jilian.pinzi.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.ToastUitl;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

public class WelcomeActivity extends FragmentActivity {

    private MainViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        RxPermissions.getInstance(this).request(
                Manifest.permission.RECORD_AUDIO,//录音权限
                Manifest.permission.ACCESS_COARSE_LOCATION, //定位权限
                Manifest.permission.ACCESS_FINE_LOCATION,//定位权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写权限
                Manifest.permission.READ_EXTERNAL_STORAGE, //读权限
                Manifest.permission.CAMERA,//相机权限
                Manifest.permission.CALL_PHONE//拨打电话



        ).subscribe(new PermissionsObserver() {
            @Override
            protected void onGetPermissionsSuccess() {

                        WelcomeActivity.this.goToMain();


            }

            @Override
            protected void onGetPermissionsSuccessFailuer() {
                finish();
                ToastUitl.showImageToastFail("获取权限失败");
            }
        });

    }
    private void goToMain() {
        getStartPage();

    }
    /**
     * 获取引导页
     */
    private void getStartPage() {
        viewModel.StartPage(2);
        viewModel.getStartPageliveData().observe(this, new Observer<BaseDto<List<StartPageDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StartPageDto>> listBaseDto) {
//                if(EmptyUtils.isNotEmpty(listBaseDto)){
//                    List<StartPageDto> list = listBaseDto.getData();
//                    Intent intent = new Intent(WelcomeActivity.this,SplashActivity.class);
//                    intent.putExtra("list",JSONObject.toJSONString(list));
//                    startActivity(intent);
//                }
//                else{
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                //}



            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
