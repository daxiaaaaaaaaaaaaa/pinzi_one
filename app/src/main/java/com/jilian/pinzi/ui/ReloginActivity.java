package com.jilian.pinzi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.utils.SPUtil;

;


/**
 * 重新登录 Activity
 *
 * @author weishixiong
 * @Time 2018-06-25
 */
public class ReloginActivity extends FragmentActivity {
    private TextView tvOk;
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
        setContentView(R.layout.activity_relogin);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setText(getIntent().getStringExtra("msg"));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relogin();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tvContent.setText(intent.getStringExtra("msg"));
    }



    /**
     * 重新登录
     */
    private void relogin() {
        //清除session
        SPUtil.clearData(Constant.SP_VALUE.SP);
        Intent intent = new Intent(ReloginActivity.this, LoginActivity.class);
        startActivity(intent);


    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return false;
        }
        return true;
    }

}
