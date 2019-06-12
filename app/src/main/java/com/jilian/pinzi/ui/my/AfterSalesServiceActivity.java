package com.jilian.pinzi.ui.my;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CarTapPagerAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.ui.my.fragment.AfterSaleRecordFragment;
import com.jilian.pinzi.ui.my.fragment.AfterSaleServiceFragment;
import com.jilian.pinzi.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class AfterSalesServiceActivity extends BaseActivity {
    String[] mTitle = new String[2];
    private NoScrollViewPager viewPager;
    private List<Fragment> fragmentList;
    private AfterSaleRecordFragment afterSaleRecordFragment;
    private AfterSaleServiceFragment afterSaleServiceFragment;
    private CarTapPagerAdapter adapter;
    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private TextView tvOne;
    private TextView tvTwo;




    @Override
    protected void createViewModel() {

    }
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
    public int intiLayout() {
        return R.layout.activity_aftersalesservice;
    }

    @Override
    public void initView() {
        setNormalTitle("退换售后", v -> finish());
        setrightImage(R.drawable.image_customer_service, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                .startConversation(AfterSalesServiceActivity.this,
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

            }
        });
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);

        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        afterSaleRecordFragment = new AfterSaleRecordFragment();
        afterSaleServiceFragment = new AfterSaleServiceFragment();
        fragmentList.add(afterSaleServiceFragment);
        fragmentList.add(afterSaleRecordFragment);
        adapter = new CarTapPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rlOne.setVisibility(View.VISIBLE);
                        rlTwo.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#c71233"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        break;
                    case 1:
                        rlOne.setVisibility(View.INVISIBLE);
                        rlTwo.setVisibility(View.VISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#c71233"));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
