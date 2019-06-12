package com.jilian.pinzi.ui.my;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.LoginDto;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 店铺入住
 */
public class ShopAtActivity extends BaseActivity {
    private RelativeLayout rlCustomer;


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
        return R.layout.activity_shopat;
    }

    @Override
    public void initView() {
        setNormalTitle("店铺入住", v -> finish());

        rlCustomer = (RelativeLayout) findViewById(R.id.rl_customer);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
                LoginDto loginDto = PinziApplication.getInstance().getLoginDto();
                // 连接融云服务器
                RongIM.connect(loginDto.getToken(), new RongIMClient.ConnectCallback() {
                    /**
                     * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                     * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                     */
                    @Override
                    public void onTokenIncorrect() {
                        Log.d("WelcomeActivity", "--onTokenIncorrect");
                    }

                    /**
                     * 连接融云成功
                     *
                     * @param userid 当前 token 对应的用户 id
                     */
                    @Override
                    public void onSuccess(String userid) {
                        RongIM.getInstance().refreshUserInfoCache(
                                new UserInfo(loginDto.getId(), loginDto.getName(), Uri.parse(loginDto.getHeadImg())));
                        RongIM.getInstance()
                                .startConversation(ShopAtActivity.this,
                                        Conversation.ConversationType.CUSTOMER_SERVICE, "KEFU154046100693716", "在线客服");
                        hideLoadingDialog();
                    }

                    /**
                     * 连接融云失败
                     *
                     * @param errorCode 错误码，可到官网 查看错误码对应的注释
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.d("WelcomeActivity", "--onError：" + errorCode.getValue());
                    }
                });

            }
        });
    }
}
