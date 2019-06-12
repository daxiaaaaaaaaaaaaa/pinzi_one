package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MemberDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CircularImageView;

/**
 * 会员中心
 */
public class MemberActivity extends BaseActivity {
    private CircularImageView ivHead;
    private TextView tvName;
    private TextView tvTotalpepole;
    private TextView tvTotalMoney;
    private ImageView ivOne;
    private ImageView ivTwo;
    private ImageView ivThree;
    private ImageView ivFour;
    private ImageView ivFive;
    private MyViewModel viewModel;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;
    private TextView tvFour;
    private TextView tvFive;


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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_member;
    }

    @Override
    public void initView() {
        setCenterTitle("会员中心", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        tvThree = (TextView) findViewById(R.id.tv_three);
        tvFour = (TextView) findViewById(R.id.tv_four);
        tvFive = (TextView) findViewById(R.id.tv_five);

        ivHead = (CircularImageView) findViewById(R.id.iv_head);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvTotalpepole = (TextView) findViewById(R.id.tv_totalpepole);
        tvTotalMoney = (TextView) findViewById(R.id.tv_totalMoney);
        ivOne = (ImageView) findViewById(R.id.iv_one);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        ivThree = (ImageView) findViewById(R.id.iv_three);
        ivFour = (ImageView) findViewById(R.id.iv_four);
        ivFive = (ImageView) findViewById(R.id.iv_five);
    }

    @Override
    public void initData() {
        showLoadingDialog();
        getMemberData();
    }

    private void getMemberData() {
        viewModel.MemberCenter(getLoginDto().getType(),getUserId());
        viewModel.getMember().observe(this, new Observer<BaseDto<MemberDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<MemberDto> dto) {
                hideLoadingDialog();
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
     *     private int type;// true number类型（1.普通用户 2.终端 3.渠道 4.总经销商）
     *     private int isVip;// true number  会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
     * @param data
     */
    private void initDataView(MemberDto data) {
        if(!EmptyUtils.isNotEmpty(data)){
            return;
        }
        Glide
                .with(MemberActivity.this)
                .load(UrlUtils.getUrl(data.getHeadImg()))
                .into(ivHead);
        tvTotalpepole.setText("直推："+data.getTotalpepole()+"人");
        tvTotalMoney.setText("销售额："+"¥"+NumberUtils.forMatNumber(data.getTotalMoney()));
        tvName.setText(data.getName());
        switch (data.getIsVip()){
            case 1:
                ivOne.setImageResource(R.drawable.image_member_center_select);
                break;
            case 2:
                ivTwo.setImageResource(R.drawable.image_god_member_select);
                break;
            case 3:
                ivThree.setImageResource(R.drawable.image_baijin_member_select);
                break;
            case 4:
                ivFour.setImageResource(R.drawable.image_zuanshi_member_select);
                break;
            case 5:
                ivFive.setImageResource(R.drawable.image_huangguan_member_select);
                break;
        }
        tvOne.setText(isVips[1]+types[data.getType()]);
        tvTwo.setText(isVips[2]+types[data.getType()]);
        tvThree.setText(isVips[3]+types[data.getType()]);
        tvFour.setText(isVips[4]+types[data.getType()]);
        tvFive.setText(isVips[5]+types[data.getType()]);
    }
    private String []  types = new String[]{"","会员","会员","会员","会员"};
    private String []  isVips = new String[]{"","普通","金牌","铂金","钻石","皇冠"};
    @Override
    public void initListener() {

    }
}
