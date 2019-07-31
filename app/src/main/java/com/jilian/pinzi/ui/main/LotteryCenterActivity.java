package com.jilian.pinzi.ui.main;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LotteryInfoDto;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.ThreeActivity;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.ui.my.MyTntegralDetailActivity;
import com.jilian.pinzi.ui.my.MyTntegralRecordActivity;
import com.jilian.pinzi.ui.my.TwoActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.shopcard.FillOrderActivity;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.wheelsruflibrary.listener.RotateListener;
import com.jilian.pinzi.views.wheelsruflibrary.view.WheelSurfView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 抽奖中心
 */
public class LotteryCenterActivity extends BaseActivity {
    /**
     * 抽中的奖品位置
     */
    private int lotteryPos = 0;

    private List<LotteryInfoDto> lotteryInfoes = new ArrayList<>();
    private LotteryViewModel lotteryViewModel;
    private TextView tvCount;
    private  WheelSurfView wheelSurfView;
    //文字
    String[] des = new String[10];
    private TextView tvContent;



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
        lotteryViewModel = ViewModelProviders.of(this).get(LotteryViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_lotterycenter;
    }

    @Override
    public void initView() {
        setNormalTitle("幸运抽奖", v -> finish());
        setrightTitle("中奖记录", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LotteryCenterActivity.this, LotteryCenterRecordActivity.class));
            }
        });
        tvCount = (TextView) findViewById(R.id.tv_count);
        //获取第三个视图
        wheelSurfView = findViewById(R.id.wheelSurfView2);
        tvContent = (TextView) findViewById(R.id.tv_content);



    }

    @Override
    public void initData() {
        getMyRecord();
    }

    @Override
    public void initListener() {
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LotteryCenterActivity.this, MyTntegralDetailActivity.class);
//                intent.putExtra("title","抽奖说明");
//                intent.putExtra("type",1);
//                startActivity(intent);
                Intent intent = new Intent(LotteryCenterActivity.this, ThreeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void doBusiness() {
        getLotteryInfo();
    }
    /**
     * 根据概率计算奖品位置
     */
    private int getLotteryPosByRate() {
        int pos = 1;
        int random = new Random().nextInt(100);
        int prizeRate = 0;// 中奖率
        for (int i = 0; i <lotteryInfoes.size(); i++) {
            LotteryInfoDto lottery = lotteryInfoes.get(i);
            prizeRate += lottery.getRate() * 100;
            if (random < prizeRate) {
                pos = i + 1;
                break;
            }
        }
        return pos;
    }

    /**
     * 获取奖品数据
     */
    private void getLotteryInfo() {
        showLoadingDialog();
        lotteryViewModel.getLotteryInfo();
        if (lotteryViewModel.getLotteryLiveData().hasObservers()) return;
        lotteryViewModel.getLotteryLiveData().observe(this, lotteryInfoDtoBaseDto ->
        {
            hideLoadingDialog();
            if (lotteryInfoDtoBaseDto == null) return;
            if (lotteryInfoDtoBaseDto.isSuccess()) {
                lotteryInfoes.clear();
                List<LotteryInfoDto> dataList = lotteryInfoDtoBaseDto.getData();
                if (dataList != null) {
                    lotteryInfoes.clear();
                    lotteryInfoes.addAll(dataList);
                    for (int i = 0; i <lotteryInfoes.size() ; i++) {

                        //String regex = "(.{1})";
                        //.replaceAll (regex, "$1 ")
                        des[i] = lotteryInfoes.get(i).getName();
                    }
                    intLotterView();
                }
            } else {
                ToastUitl.showImageToastFail(lotteryInfoDtoBaseDto.getMsg());
            }
        });

    }

    private void intLotterView() {
        //颜色
        Integer[] colors = new Integer[]{
                Color.parseColor("#FFB54C"), Color.parseColor("#FF8758")
                , Color.parseColor("#FFB54C"), Color.parseColor("#FF8758")
                , Color.parseColor("#FFB54C"), Color.parseColor("#FF8758")
                , Color.parseColor("#FFB54C"),Color.parseColor("#FF8758")
                , Color.parseColor("#FFB54C"), Color.parseColor("#FF8758")};
        WheelSurfView.Builder build = new WheelSurfView.Builder()
                .setmColors(colors)
                .setmDeses(des)
                .setmType(1)
                .setmTypeNum(10)
                .setmMinTimes(10)
                .setmVarTime(20)
                .build();
        wheelSurfView.setConfig(build);
        //添加滚动监听
        wheelSurfView.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position, String des) {
                Log.e(TAG, "rotateBefore: "+ position);
                if ("谢谢惠顾".equals(des)) {
                    showNowinningDialog();
                } else {
                    if(des.endsWith("元")

                            ||des.endsWith("积分")
                            ||des.endsWith("优惠券")||
                            des.endsWith("折扣券")){
                        ToastUitl.showImageToastSuccess("恭喜你抽中了：" +des);
                    }
                    else{
                        //实物的话 去下单
                        Intent intent = new Intent(LotteryCenterActivity.this, FillOrderActivity.class);
                        OrderGoodsDto dto = new OrderGoodsDto();
                        dto.setFreight(String.valueOf(lotteryInfoes.get(lotteryInfoes.size()-position+1).getFreight()));
                        dto.setCount(1);
                        dto.setId(lotteryInfoes.get(lotteryInfoes.size()-position+1).getId());
                        //dto.setFile(mData.getFile());
                        dto.setName(String.valueOf(lotteryInfoes.get(lotteryInfoes.size()-position+1).getName()));
                        dto.setClasses(0);
                        List<OrderGoodsDto> dtoList = new ArrayList<>();
                        dtoList.add(dto);
                        intent.putExtra("dtoList", JSONObject.toJSONString(dtoList));
                        //1 代表直接购买 2 代表购物车购买
                        intent.putExtra("type", "2");
                        //1 代表商品 2  代表奖品
                        intent.putExtra("orderType", "2");
                        startActivity(intent);
                    }
                }
                addLotteryResultToServer(lotteryInfoes.size()-position+1);
            }
            @Override
            public void rotating(ValueAnimator valueAnimator) {

            }
            @Override
            public void rotateBefore(ImageView goImg) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(LotteryCenterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                // 判断积分
                getLoadingDialog().showDialog();
                lotteryViewModel.getLotteryScore(PinziApplication.getInstance().getLoginDto().getId());
                if (lotteryViewModel.getScoreLiveData().hasObservers()) return;
                lotteryViewModel.getScoreLiveData().observe(LotteryCenterActivity.this, emptyDtoBaseDto -> {
                    getLoadingDialog().dismiss();
                    if (emptyDtoBaseDto.isSuccess()) {
                        //积分足够，可以抽奖
                        lotteryPos = getLotteryPosByRate();
                        wheelSurfView.startRotate(lotteryPos);
                    } else {
                        ToastUitl.showImageToastFail(emptyDtoBaseDto.getMsg());

                    }
                });
            }
        });
    }
    /**
     * 添加抽奖结果到服务器
     */
    private void addLotteryResultToServer(int position) {
        if(position==10){
            position=9;
        }
        lotteryViewModel.addLotteryResult(PinziApplication.getInstance().getLoginDto().getId(),
                String.valueOf(lotteryInfoes.get(position).getId()));
        if (lotteryViewModel.getResultLiveData().hasObservers()) return;
        lotteryViewModel.getResultLiveData().observe(this, emptyDtoBaseDto -> {
            if (emptyDtoBaseDto == null) return;
            if (emptyDtoBaseDto.isSuccess()) {
                //刷新积分
                getMyRecord();
            } else {
                ToastUitl.showImageToastFail(emptyDtoBaseDto.getMsg());
            }
        });
    }
    private MyViewModel viewModel;

    /**
     * 获取我的积分
     */
    private void getMyRecord() {
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> scoreOrCommissionDtoBaseDto) {
                //积分
                if(EmptyUtils.isNotEmpty(scoreOrCommissionDtoBaseDto.getData())&&EmptyUtils.isNotEmpty(scoreOrCommissionDtoBaseDto.getData().getScoreNum())){
                    tvCount.setText(NumberUtils.forMatZeroNumber(Double.parseDouble(scoreOrCommissionDtoBaseDto.getData().getScoreNum())));
                }
                else{
                    tvCount.setText("0");
                }

            }
        });

    }
    private void showNowinningDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(LotteryCenterActivity.this, R.layout.dialog_not_winning);
        ImageView ivCancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
