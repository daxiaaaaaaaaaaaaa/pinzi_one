package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.ui.ThreeActivity;
import com.jilian.pinzi.utils.PinziDialogUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 服务中心
 */
public class ServiceCenterActivity extends BaseActivity {
    // private RelativeLayout rlQuestion;
    private RelativeLayout rlCustomerService;
    private LinearLayout llAfterService;
    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private RelativeLayout rlThree;

    private RelativeLayout rlCall;


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

    private void showTwoTipsDialog() {

        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_confirm);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvTitle.setText("客服热线");
        tvContent.setText("0731-85061459");
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        tvOk.setText("拨打");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "0731-85061459");
                intent.setData(data);
                startActivity(intent);


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

    @Override
    public int intiLayout() {
        return R.layout.activity_servicecenter;
    }

    @Override
    public void initView() {
        setNormalTitle("服务中心", v -> finish());
        //rlQuestion = (RelativeLayout) findViewById(R.id.rl_question);
        rlCustomerService = findViewById(R.id.rl_servicecenter_online);
        llAfterService = (LinearLayout) findViewById(R.id.ll_after_service);

        rlCall = (RelativeLayout) findViewById(R.id.rl_call);
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        rlThree = (RelativeLayout) findViewById(R.id.rl_three);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwoTipsDialog();
            }
        });
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceCenterActivity.this, OneActivity.class));
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceCenterActivity.this, TwoActivity.class));
            }
        });
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceCenterActivity.this, ThreeActivity.class));
            }
        });
//        rlQuestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ServiceCenterActivity.this, AboutWeActivity.class));
//            }
//        });
        // 客服聊天
        rlCustomerService.setOnClickListener(v -> {
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
                    hideLoadingDialog();
                    RongIM.getInstance().refreshUserInfoCache(
                            new UserInfo(loginDto.getId(), loginDto.getName(), Uri.parse(loginDto.getHeadImg())));
                    RongIM.getInstance()
                            .startConversation(ServiceCenterActivity.this,
                                    Conversation.ConversationType.CUSTOMER_SERVICE, "KEFU154046100693716", "在线客服");
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


        });
        llAfterService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceCenterActivity.this, AfterSalesServiceActivity.class));
            }
        });
    }
}
