package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CircularImageView;
import com.jilian.pinzi.views.NoScrollViewPager;

public class FiveFragment extends BaseFragment {
    private RelativeLayout rlMember;
    private LinearLayout llWallet;
    private LinearLayout llPoint;
    private LinearLayout llMycoupons;
    private LinearLayout llMyCard;
    private RelativeLayout rlMycollection;
    private RelativeLayout rlFoot;
    private String []  types = new String[]{"","会员","会员","会员","会员"};
    private String []  isVips = new String[]{"","普通","金牌","铂金","钻石","皇冠"};
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
        llQuestionnaireSurvey = (LinearLayout)view. findViewById(R.id.ll_questionnaire_survey);
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
        ivSetting = (ImageView)view. findViewById(R.id.iv_setting);
        ivNew = (ImageView) view.findViewById(R.id.iv_new);
        rlFiveQrCode = (RelativeLayout) view.findViewById(R.id.rl_five_qr_code);
        tvFiveMyLevels = (TextView) view.findViewById(R.id.tv_five_my_levels);
        if(EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())){
            if(PinziApplication.getInstance().getLoginDto().getType()==1){
                rlMyShipment.setVisibility(View.GONE);
            }
            else{
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
    protected void initData() {
        getMemberData();
    }

    /**
     * 获取新消息
     */
    private void getNewMsgData() {
        if(PinziApplication.getInstance().getLoginDto()==null){
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
        if(PinziApplication.getInstance().getLoginDto()==null){
            return;
        }
            viewModel.MemberCenter(PinziApplication.getInstance().getLoginDto().getType(),PinziApplication.getInstance().getLoginDto().getId());
            viewModel.getMember().observe(this, new Observer<BaseDto<MemberDto>>() {
                @Override
                public void onChanged(@Nullable BaseDto<MemberDto> dto) {
                    if(dto.isSuccess()){
                        initDataView(dto.getData());
                    }
                    else{
                        ToastUitl.showImageToastFail(dto.getMsg());
                    }
                }
            });

    }

    /**
     *       private int type;// true number类型（1.普通用户 2.终端 3.渠道 4.总经销商）
     *         private int isVip;// true number  会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
     * @param data
     */
    private void initDataView(MemberDto data) {
        tvMember.setText(isVips[data.getIsVip()]+types[data.getType()]);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginDto dto = ((MainActivity)getActivity()).getLoginDto();
        if(EmptyUtils.isNotEmpty(dto)){
            Glide
                    .with(getActivity())
                    .load(((MainActivity)getActivity()).getLoginDto().getHeadImg())
                    .into(ivHead);
            tvName.setText(((MainActivity)getActivity()).getLoginDto().getName());
            tvIniviteCode.setText("我的邀请码："+dto.getInvitationCode());

        }
        //获取新消息 是否红点
        getNewMsgData();

    }

    @Override
    protected void initListener() {
        llActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyActivityActivity.class));
            }
        });

        llQuestionnaireSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QuestionnaireSurveyActivity.class));
            }
        });
        rlFiveQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
            }
        });
        rlMyShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyShipmentActivity.class));
            }
        });
        tvMember.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MemberActivity.class));
            }
        });
        ivShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PinziApplication.getInstance().getLoginDto()==null){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                ((NoScrollViewPager) (getActivity()).findViewById(R.id.viewPager)).setCurrentItem(3);
            }
        });
        ivSystemMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SystemMsgActivity.class));
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCenterActivity.class));
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCenterActivity.class));
            }
        });
        rlShopAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShopAtActivity.class));
            }
        });
        rlMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MemberActivity.class));
            }
        });
        rlAddressManager.setOnClickListener(v -> startActivity(new Intent(mActivity, AddressManagerActivity.class)));
        rlMyLevels.setOnClickListener(v -> startActivity(new Intent(mActivity, MyLevelsActivity.class)));
        llWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });
        llPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyTntegralActivity.class));
            }
        });
        llMycoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCouponsActivity.class));
            }
        });
        llMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCardActivity.class));
            }
        });
        rlMycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
            }
        });
        rlFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyFootActivity.class));
            }
        });
        rlAfterSalesService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ServiceCenterActivity.class));
            }
        });


    }
}
