package com.jilian.pinzi.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.MemberDto;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.ui.main.WebViewActivity;
import com.jilian.pinzi.ui.main.WebViewTitleActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.AddressManagerActivity;
import com.jilian.pinzi.ui.my.MemberActivity;
import com.jilian.pinzi.ui.my.MyCardActivity;
import com.jilian.pinzi.ui.my.MyCenterActivity;
import com.jilian.pinzi.ui.my.MyCollectionActivity;
import com.jilian.pinzi.ui.my.MyCouponsActivity;
import com.jilian.pinzi.ui.my.MyFootActivity;
import com.jilian.pinzi.ui.my.MyOrderActivity;
import com.jilian.pinzi.ui.my.MyShipmentActivity;
import com.jilian.pinzi.ui.my.MyTntegralActivity;
import com.jilian.pinzi.ui.my.QrCodeActivity;
import com.jilian.pinzi.ui.my.ServiceCenterActivity;
import com.jilian.pinzi.ui.my.SettingActivity;
import com.jilian.pinzi.ui.my.ShopAtActivity;
import com.jilian.pinzi.ui.my.SystemMsgActivity;
import com.jilian.pinzi.ui.my.WalletActivity;
import com.jilian.pinzi.ui.my.MyLevelsActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CircularImageView;
import com.jilian.pinzi.views.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FiveFragment extends BaseFragment {
    private RelativeLayout rlMember;
    private LinearLayout llWallet;
    private LinearLayout llPoint;
    private LinearLayout llMycoupons;
    private LinearLayout llMyCard;
    private RelativeLayout rlMycollection;
    private RelativeLayout rlFoot;
    private String[] types = new String[]{"", "会员", "会员", "会员", "会员", "会员"};
    private String[] isVips = new String[]{"", "普通", "金牌", "铂金", "钻石", "皇冠"};
    /**
     * 地址管理
     */
    private RelativeLayout rlAddressManager;
    /**
     * 我的上下级
     */
    private RelativeLayout rlMyLevels;
    /**
     * 售后服务
     */
    private RelativeLayout rlAfterSalesService;
    /**
     * 店铺入住
     */
    private RelativeLayout rlShopAt;
    private CircularImageView ivHead;
    private TextView tvName;
    private ImageView ivSystemMsg;
    private ImageView ivShop;
    private TextView tvMember;
    private RelativeLayout rlMyShipment;
    private RelativeLayout rlOrder;
    private TextView tvIniviteCode;
    private ImageView ivSetting;
    private MyViewModel viewModel;
    private MainViewModel mainViewModel;
    private ImageView ivNew;
    private RelativeLayout rlFiveQrCode;
    private TextView tvFiveMyLevels;
    private LinearLayout llQuestionnaireSurvey;
    private LinearLayout llActivity;
    private LinearLayout llOne;
    private LinearLayout llTwo;
    private LinearLayout llThree;
    private LinearLayout llFour;


    private LinearLayout llFive;
    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private RelativeLayout rlThree;
    private RelativeLayout rlFour;
    private RelativeLayout rlFive;
    private RelativeLayout rlSix;
    private RelativeLayout rlSeven;
    private RelativeLayout rlEight;
    private LinearLayout llSix;
    private RelativeLayout rlTen;
    private RelativeLayout rlTelf;
    private RelativeLayout rlNine;
    private ImageView ivQCode;

    /**
     *
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        /* Do something */
        if (EmptyUtils.isNotEmpty(event)
                && EmptyUtils.isNotEmpty(event.getMainCreatMessage())
                && event.getMainCreatMessage().getCode() == 200
                ) {
            //初始化平台賬號
            initPlatformView();
        }
    }





    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_five;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rlNine = (RelativeLayout) view.findViewById(R.id.rl_nine);
        llSix = (LinearLayout) view.findViewById(R.id.ll_six);
        rlTen = (RelativeLayout) view.findViewById(R.id.rl_ten);
        rlTelf = (RelativeLayout) view.findViewById(R.id.rl_telf);
        llFive = (LinearLayout) view.findViewById(R.id.ll_five);
        rlOne = (RelativeLayout) view.findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) view.findViewById(R.id.rl_two);
        rlThree = (RelativeLayout) view.findViewById(R.id.rl_three);
        rlFour = (RelativeLayout) view.findViewById(R.id.rl_four);
        rlFive = (RelativeLayout) view.findViewById(R.id.rl_five);
        rlSix = (RelativeLayout) view.findViewById(R.id.rl_six);
        rlSeven = (RelativeLayout) view.findViewById(R.id.rl_seven);
        rlEight = (RelativeLayout) view.findViewById(R.id.rl_eight);
        llThree = (LinearLayout) view.findViewById(R.id.ll_three);
        llFour = (LinearLayout) view.findViewById(R.id.ll_four);
        llOne = (LinearLayout) view.findViewById(R.id.ll_one);
        llTwo = (LinearLayout) view.findViewById(R.id.ll_two);
        llQuestionnaireSurvey = (LinearLayout) view.findViewById(R.id.ll_questionnaire_survey);
        llActivity = (LinearLayout) view.findViewById(R.id.ll_activity);
        rlMember = (RelativeLayout) view.findViewById(R.id.rl_member);
        rlAddressManager = (RelativeLayout) view.findViewById(R.id.rl_five_address_manager);
        rlMyLevels = (RelativeLayout) view.findViewById(R.id.rl_five_my_levels);
        rlAfterSalesService = (RelativeLayout) view.findViewById(R.id.rl_five_after_sales_service);
        rlShopAt = (RelativeLayout) view.findViewById(R.id.rl_five_shop_at);
        llWallet = (LinearLayout) view.findViewById(R.id.ll_wallet);
        llPoint = (LinearLayout) view.findViewById(R.id.ll_point);
        llMycoupons = (LinearLayout) view.findViewById(R.id.ll_mycoupons);
        llMyCard = (LinearLayout) view.findViewById(R.id.ll_my_card);
        rlMycollection = (RelativeLayout) view.findViewById(R.id.rl_mycollection);
        rlFoot = (RelativeLayout) view.findViewById(R.id.rl_foot);
        ivHead = (CircularImageView) view.findViewById(R.id.iv_head);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        ivSystemMsg = (ImageView) view.findViewById(R.id.iv_system_msg);
        ivShop = (ImageView) view.findViewById(R.id.iv_shop);
        tvMember = (TextView) view.findViewById(R.id.tv_member);
        rlMyShipment = (RelativeLayout) view.findViewById(R.id.rl_my_shipment);
        rlOrder = (RelativeLayout) view.findViewById(R.id.rl_order);
        tvIniviteCode = (TextView) view.findViewById(R.id.tv_inivite_code);
        ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        ivNew = (ImageView) view.findViewById(R.id.iv_new);
        rlFiveQrCode = (RelativeLayout) view.findViewById(R.id.rl_five_qr_code);
        tvFiveMyLevels = (TextView) view.findViewById(R.id.tv_five_my_levels);
        ivQCode = (ImageView) view.findViewById(R.id.iv_q_code);

        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())) {
            if (PinziApplication.getInstance().getLoginDto().getType() == 1) {
                rlMyShipment.setVisibility(View.GONE);
            } else {
                rlMyShipment.setVisibility(View.VISIBLE);
            }
        }


        if (EmptyUtils.isNotEmpty(getLoginDto()) && getLoginDto().getType() != 1) {
            if (getLoginDto().getType() != 1) {
                tvFiveMyLevels.setText("服务关系");
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        //初始化平台賬號
        initPlatformView();

    }


    /**
     * 初始化平台账号的界面
     */
    private void initPlatformView() {

        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            tvIniviteCode.setVisibility(View.GONE);
            llOne.setVisibility(View.GONE);
            llTwo.setVisibility(View.GONE);
            llThree.setVisibility(View.GONE);
            llFour.setVisibility(View.GONE);
            llFive.setVisibility(View.VISIBLE);
            llSix.setVisibility(View.VISIBLE);
            ivQCode.setVisibility(View.GONE);
        } else {
            llOne.setVisibility(View.VISIBLE);
            llTwo.setVisibility(View.VISIBLE);
            llThree.setVisibility(View.VISIBLE);
            llFour.setVisibility(View.VISIBLE);
            llFive.setVisibility(View.GONE);
            llSix.setVisibility(View.GONE);
            ivQCode.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取新消息
     */
    private void getNewMsgData() {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            return;
        }
        mainViewModel.MessageRead(2, PinziApplication.getInstance().getLoginDto().getId());
        mainViewModel.getMsgNewliveData().observe(this, new Observer<BaseDto<Integer>>() {
            @Override
            public void onChanged(@Nullable BaseDto<Integer> integerBaseDto) {
                if (integerBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    if (integerBaseDto.getData() != null && integerBaseDto.getData() >= 1) {
                        ivNew.setVisibility(View.VISIBLE);
                    } else {
                        ivNew.setVisibility(View.GONE);
                    }
                } else {
                    ivNew.setVisibility(View.GONE);
                }

            }
        });
    }

    private void getMemberData() {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            return;
        }
        viewModel.MemberCenter(PinziApplication.getInstance().getLoginDto().getType(), PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getMember().observe(this, new Observer<BaseDto<MemberDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<MemberDto> dto) {
                if (dto.isSuccess()) {
                    initDataView(dto.getData());
                } else {
                    ToastUitl.showImageToastFail(dto.getMsg());
                }
            }
        });

    }

    /**
     * private int type;// true number类型（1.普通用户 2.终端 3.渠道 4.总经销商）
     * private int isVip;// true number  会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
     *
     * @param data
     */
    private void initDataView(MemberDto data) {
        if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                && PinziApplication.getInstance().getLoginDto().getType() == 5) {
            tvMember.setText("平台账号");
        } else {
            tvMember.setText(isVips[data.getIsVip()] + types[data.getType()]);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LoginDto dto = ((MainActivity) getmActivity()).getLoginDto();
        if (EmptyUtils.isNotEmpty(dto)) {
            Glide
                    .with(getmActivity())
                    .load(((MainActivity) getmActivity()).getLoginDto().getHeadImg())
                    .into(ivHead);
            tvName.setText(((MainActivity) getmActivity()).getLoginDto().getName());
            tvIniviteCode.setText("我的邀请码：" + (dto.getInvitationCode() == null ? "" : dto.getInvitationCode()));

        }
        //获取新消息 是否红点
        getNewMsgData();
        //获取会员等级
        getMemberData();


    }

    @Override
    protected void initListener() {
        llActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getmActivity(), MyActivityActivity.class));
            }
        });

        llQuestionnaireSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getmActivity(), QuestionnaireSurveyActivity.class));
            }
        });
        ivQCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getmActivity(), QrCodeActivity.class));
            }
        });
//        rlFiveQrCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), SettingActivity.class));
            }
        });
        rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyOrderActivity.class));
            }
        });
        rlMyShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyShipmentActivity.class));
            }
        });
        tvMember.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    return;
                }
                startActivity(new Intent(getmActivity(), MemberActivity.class));
            }
        });
        ivShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                ((NoScrollViewPager) (getmActivity()).findViewById(R.id.viewPager)).setCurrentItem(3);
            }
        });
        ivSystemMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), SystemMsgActivity.class));
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyCenterActivity.class));
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyCenterActivity.class));
            }
        });
        rlShopAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), ShopAtActivity.class));
            }
        });
        rlMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MemberActivity.class));
            }
        });
        rlAddressManager.setOnClickListener(v -> startActivity(new Intent(mActivity, AddressManagerActivity.class)));
        rlMyLevels.setOnClickListener(v -> startActivity(new Intent(mActivity, MyLevelsActivity.class)));
        llWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
               // startActivity(new Intent(getmActivity(), WalletActivity.class));
            }
        });
        llPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyTntegralActivity.class));
            }
        });
        llMycoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyCouponsActivity.class));
            }
        });
        llMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyCardActivity.class));
            }
        });
        rlMycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyCollectionActivity.class));
            }
        });
        rlFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), MyFootActivity.class));
            }
        });
        rlAfterSalesService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), ServiceCenterActivity.class));
            }
        });
        //消费排行
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getmActivity(), CustomRanActivity.class);
            startActivity(intent);
            }
        });

        //订单统计
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), OrderRanActivity.class);
                startActivity(intent);
            }
        });

        //营收统计
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl",Constant.Server.url_08);
                intent.putExtra("title","营收统计");
                startActivity(intent);

            }
        });

        //充值统计
        rlFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl",Constant.Server.url_09);
                intent.putExtra("title","充值统计");
                startActivity(intent);
            }
        });

        //提现统计
        rlFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl",Constant.Server.url_09);
                intent.putExtra("title","提现统计");
                startActivity(intent);
            }
        });

        //分享
        rlSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl",Constant.Server.url_10);
                intent.putExtra("title","分享统计");
                startActivity(intent);
            }
        });


        //销售统计
        rlSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmActivity(), BuyRanActivity.class);
                startActivity(intent);
            }
        });

        //单品统计
        rlEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_16);
                intent.putExtra("title","单品统计");
                startActivity(intent);

            }
        });
        //用户购买统计
        rlNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getmActivity(), WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_17);
                intent.putExtra("title","用户购买统计");
                startActivity(intent);
            }
        });

        //售后服务
        rlTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getmActivity(), ServiceCenterActivity.class));
            }
        });

        //客服电话
        rlTelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwoTipsDialog();
            }
        });


    }
    private void showTwoTipsDialog() {

        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_confirm);
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
}
