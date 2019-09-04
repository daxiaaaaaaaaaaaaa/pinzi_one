package com.jilian.pinzi.ui.main;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.GoodsDetailDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.ui.CommonViewPagerAdapter;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.fragment.GoodsDetailCenterFragment;
import com.jilian.pinzi.ui.main.fragment.GoodsDetailLeftFragment;
import com.jilian.pinzi.ui.main.fragment.GoodsDetailRightFragment;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.AfterSalesServiceActivity;
import com.jilian.pinzi.ui.shopcard.FillOrderActivity;
import com.jilian.pinzi.ui.shopcard.IntegralFillOrderActivity;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MobileInfoUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.AddWxDialogUtils;
import com.jilian.pinzi.views.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 商品信息
 */
public class GoodsDetailActivity extends BaseActivity {
    public TextView tvLeft;
    public TextView tvCenter;
    public TextView tvRight;
    private NoScrollViewPager customScrollViewPager;
    private List<Fragment> mFragmentList;
    private GoodsDetailLeftFragment leftFragment;
    private GoodsDetailCenterFragment centerFragment;
    private GoodsDetailRightFragment rightFragment;
    public RelativeLayout rlLeft;
    public RelativeLayout rlCenter;
    public RelativeLayout rlRight;
    private CommonViewPagerAdapter adapter;
    private Integer type;//1.普通商品 2.秒杀商品
    private MainViewModel viewModel;
    private ImageView ivCollect;
    private LinearLayout llShop;
    private TextView tvJoinShopCart;
    private TextView tvBuy;
    private TextView tvIntergal;
    private LinearLayout llNornmal;
    private LinearLayout llCutomer;


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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_integralgooddetail;
    }

    @Override
    public void initView() {
        setNormalTitle("商品信息", v -> finish());
        tvIntergal = (TextView) findViewById(R.id.tv_intergal);
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        tvRight = (TextView) findViewById(R.id.tv_right);
        customScrollViewPager = (NoScrollViewPager) findViewById(R.id.customScrollViewPager);
        customScrollViewPager.setOffscreenPageLimit(3);
        ivCollect = (ImageView) findViewById(R.id.iv_collect);
        llShop = (LinearLayout) findViewById(R.id.ll_shop);
        tvJoinShopCart = (TextView) findViewById(R.id.tv_joinShopCart);
        llCutomer = (LinearLayout) findViewById(R.id.ll_cutomer);
        rlLeft = (RelativeLayout) findViewById(R.id.rl_left);
        rlCenter = (RelativeLayout) findViewById(R.id.rl_center);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llNornmal = (LinearLayout) findViewById(R.id.ll_nornmal);
        //积分商城
        if (getIntent().getIntExtra("shopType", 1) == 2) {
            tvIntergal.setVisibility(View.VISIBLE);
            llNornmal.setVisibility(View.GONE);
        }
        //普通商城
        else {
            tvIntergal.setVisibility(View.GONE);
            llNornmal.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * 获取商品详情
     */
    private void getGoodDetail() {
        showLoadingDialog();
        viewModel.getGoodsDetail(getIntent().getIntExtra("type", 1), getIntent().getStringExtra("goodsId"), PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getGoodsDetailliveData().observe(this, new Observer<BaseDto<GoodsDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<GoodsDetailDto> dto) {
                RxTimerUtil.timer(1200, new RxTimerUtil.IRxNext() {
                    @Override
                    public void doNext() {
                        hideLoadingDialog();
                    }
                });
                //商品详情
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    initGoodetailView(dto.getData());
                } else {
                    ToastUitl.showImageToastFail(dto.getMsg());
                }
            }
        });
    }

    private GoodsDetailDto mData;

    /**
     * 初始化商品详情视图
     *
     * @param data
     */
    private void initGoodetailView(GoodsDetailDto data) {
        this.mData = data;
        if (data.getCollectId() == 0) {
            ivCollect.setImageResource(R.drawable.image_colletion_normal);
        } else {
            ivCollect.setImageResource(R.drawable.image_colletion_selected);
        }
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setGoodsDetailDto(data);
        EventBus.getDefault().post(messageEvent);
//        leftFragment.initGoodetailView(data);
//        centerFragment.initGoodetailView(data);

    }

    /**
     * 收藏
     *
     * @param uId
     * @param goodOrStoreId 店铺  或者  商品ID
     * @param type          1.收藏商品 2.收藏店铺
     */
    private void collectGoodsOrStore(String uId, String goodOrStoreId, Integer type) {
        showLoadingDialog();
        viewModel.collectGoodsOrStore(uId, goodOrStoreId, type);
        viewModel.getCollectGoodsOrStoreliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("收藏成功");
                    getGoodDetail();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param cId
     */
    public void cancelCollect(String cId) {
        showLoadingDialog();
        viewModel.cancelCollect(cId);
        viewModel.getCancelCollectliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("取消收藏成功");
                    getGoodDetail();

                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    protected void onPause() {
        super.onPause();   //     Jzvd.clearSavedProgress(this, null);
        //home back
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", 1);
        mFragmentList = new ArrayList<>();
        leftFragment = new GoodsDetailLeftFragment();
        centerFragment = new GoodsDetailCenterFragment();
        rightFragment = new GoodsDetailRightFragment();
        mFragmentList.add(leftFragment);
        mFragmentList.add(centerFragment);
        mFragmentList.add(rightFragment);
        adapter = new CommonViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        customScrollViewPager.setAdapter(adapter);
        getGoodDetail();
        updatePvOrUv();
    }

    /**
     * 浏览记录统计(查看商品详情时调用)
     */
    private void updatePvOrUv() {
        String mac = MobileInfoUtil.getIMEI(this);
        String goodsId = getIntent().getStringExtra("goodsId");
        //浏览记录统计(查看商品详情时调用)
        viewModel.updatePvOrUv(mac, goodsId);
    }

    @Override
    public void initListener() {
        //
        llCutomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                AddWxDialogUtils.showAddWxDialog(GoodsDetailActivity.this);

//                showLoadingDialog();
//                // 连接融云服务器
//                RongIM.connect(PinziApplication.getInstance().getLoginDto().getToken(), new RongIMClient.ConnectCallback() {
//                    /**
//                     * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
//                     * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
//                     */
//                    @Override
//                    public void onTokenIncorrect() {
//                        hideLoadingDialog();
//                        Log.d("WelcomeActivity", "--onTokenIncorrect");
//                    }
//
//                    /**
//                     * 连接融云成功
//                     *
//                     * @param userid 当前 token 对应的用户 id
//                     */
//                    @Override
//                    public void onSuccess(String userid) {
//                        hideLoadingDialog();
//                        RongIM.getInstance().refreshUserInfoCache(
//                                new UserInfo(PinziApplication.getInstance().getLoginDto().getId(), PinziApplication.getInstance().getLoginDto().getName(), Uri.parse(PinziApplication.getInstance().getLoginDto().getHeadImg())));
//                        RongIM.getInstance()
//                                .startConversation(GoodsDetailActivity.this,
//                                        Conversation.ConversationType.CUSTOMER_SERVICE, "KEFU154046100693716", "在线客服");
//                    }
//
//                    /**
//                     * 连接融云失败
//                     *
//                     * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                     */
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d("WelcomeActivity", "--onError：" + errorCode.getValue());
//                    }
//                });
            }
        });
        //马上兑换
        tvIntergal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                showTipsDialog();
            }
        });
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                if (!EmptyUtils.isNotEmpty(mData)) {
                    return;
                }
                if (leftFragment.tvPrice.getText().toString().substring(1, leftFragment.tvPrice.getText().toString().length()).equals("0.00")) {
                    ToastUitl.showImageToastFail("该商品已售罄");
                    return;
                }
                showBuyDialog();

            }
        });
        llShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                Intent intent = new Intent(GoodsDetailActivity.this, MainActivity.class);
                intent.putExtra("index", 3);
                intent.putExtra("back", 2);
                startActivity(intent);
            }
        });
        tvJoinShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                if (!EmptyUtils.isNotEmpty(mData)) {
                    return;
                }
                showBuyDialog();
            }
        });
//        tvJoinShopCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(PinziApplication.getInstance().getLoginDto()==null){
//                    Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    return;
//                }
//                if (EmptyUtils.isNotEmpty(mData)) {
//                    joinShopCart(getIntent().getIntExtra("classes",1),getUserId(), mData.getId(), 1);
//                }
//
//            }
//        });
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                if (EmptyUtils.isNotEmpty(mData)) {
                    if (mData.getCollectId() == 0) {
                        collectGoodsOrStore(getUserId(), mData.getId(), 1);
                    } else {
                        cancelCollect(String.valueOf(mData.getCollectId()));
                    }
                }
            }
        });
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customScrollViewPager.setCurrentItem(0);
                rlLeft.setVisibility(View.VISIBLE);
                tvLeft.setTextColor(Color.parseColor("#c71233"));

                rlCenter.setVisibility(View.INVISIBLE);
                tvCenter.setTextColor(Color.parseColor("#222222"));

                rlRight.setVisibility(View.INVISIBLE);
                tvRight.setTextColor(Color.parseColor("#222222"));
            }
        });

        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customScrollViewPager.setCurrentItem(1);
                rlCenter.setVisibility(View.VISIBLE);
                tvCenter.setTextColor(Color.parseColor("#c71233"));

                rlLeft.setVisibility(View.INVISIBLE);
                tvLeft.setTextColor(Color.parseColor("#222222"));

                rlRight.setVisibility(View.INVISIBLE);
                tvRight.setTextColor(Color.parseColor("#222222"));
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customScrollViewPager.setCurrentItem(2);
                rlRight.setVisibility(View.VISIBLE);
                tvRight.setTextColor(Color.parseColor("#c71233"));

                rlLeft.setVisibility(View.INVISIBLE);
                tvLeft.setTextColor(Color.parseColor("#222222"));

                rlCenter.setVisibility(View.INVISIBLE);
                tvCenter.setTextColor(Color.parseColor("#222222"));

            }
        });

    }

    /**
     * 是否兑换
     */
    private void showTipsDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvContent.setText("是否确认使用" + mData.getScore() + "积分兑换该商品？");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(GoodsDetailActivity.this, IntegralFillOrderActivity.class);
                OrderGoodsDto dto = new OrderGoodsDto();
                dto.setFreight(mData.getFreight());
                dto.setCount(1);
                dto.setId(mData.getId());
                dto.setFile(mData.getFile());
                dto.setName(mData.getName());
                dto.setScore(mData.getScore());
                Integer type = PinziApplication.getInstance().getLoginDto().getType();
                //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
                if (getIntent().getIntExtra("classes", 1) == 2) {
                    if (type == 1) {
                        dto.setPrice(mData.getPersonBuy());
                    }
                    if (type == 2) {
                        dto.setPrice(mData.getTerminalBuy());
                    }
                    if (type == 3) {
                        dto.setPrice(mData.getChannelBuy());
                    }
                    if (type == 4) {
                        dto.setPrice(mData.getFranchiseeBuy());
                    }
                } else {
                    dto.setPrice(mData.getPersonBuy());
                }

                List<OrderGoodsDto> dtoList = new ArrayList<>();
                dtoList.add(dto);
                intent.putExtra("dtoList", JSONObject.toJSONString(dtoList));
                intent.putExtra("type", "2");
                intent.putExtra("orderType", "1");
                startActivity(intent);
                dialog.dismiss();

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

    /**
     * 选择 数量
     */
    private void showBuyDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_buy_shop)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        ImageView ivClose = (ImageView) holder.getView(R.id.iv_close);
                        ImageView ivHead = (ImageView) holder.getView(R.id.iv_head);
                        TextView tvName = (TextView) holder.getView(R.id.tv_name);
                        TextView tvPrice = (TextView) holder.getView(R.id.tv_price);
                        ImageView tvAdd = (ImageView) holder.getView(R.id.tv_add);
                        EditText tvCount = (EditText) holder.getView(R.id.tv_count);
                        ImageView tvDel = (ImageView) holder.getView(R.id.tv_del);
                        TextView tvAddShop = (TextView) holder.getView(R.id.tv_add_shop);
                        TextView tvBuy = (TextView) holder.getView(R.id.tv_buy);
                        tvCount.setSelection(tvCount.length());
                        Glide
                                .with(GoodsDetailActivity.this)
                                .load(UrlUtils.getUrl(mData.getFile()))
                                .into(ivHead);
                        tvName.setText(mData.getName());
                        LoginDto dto = PinziApplication.getInstance().getLoginDto();

                        //秒杀商品
                        if (getIntent().getIntExtra("type", 1) == 2) {
                            tvPrice.setText(NumberUtils.forMatNumber(mData.getSeckillPrice()));
                        } else {
                            //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
                            if (getIntent().getIntExtra("classes", 1) == 2) {
                                if (dto.getType() == 1) {
                                    tvPrice.setText(NumberUtils.forMatNumber(mData.getPersonBuy()));
                                }
                                if (dto.getType() == 2) {
                                    tvPrice.setText(NumberUtils.forMatNumber(mData.getTerminalBuy()));
                                }
                                if (dto.getType() == 3) {
                                    tvPrice.setText(NumberUtils.forMatNumber(mData.getChannelBuy()));
                                }
                                if (dto.getType() == 4) {
                                    tvPrice.setText(NumberUtils.forMatNumber(mData.getFranchiseeBuy()));
                                }
                            } else if (getIntent().getIntExtra("classes", 1) == 3) {
                                tvPrice.setText(NumberUtils.forMatNumber(mData.getFranchiseeBuy()));
                            } else if (getIntent().getIntExtra("classes", 1) == 4) {
                                tvPrice.setText(NumberUtils.forMatNumber(mData.getChannelBuy()));
                            } else if (getIntent().getIntExtra("classes", 1) == 5) {
                                tvPrice.setText(NumberUtils.forMatNumber(mData.getTerminalBuy()));
                            } else if (getIntent().getIntExtra("classes", 1) == 6) {
                                tvPrice.setText(NumberUtils.forMatNumber(mData.getPersonBuy()));
                            } else {
                                tvPrice.setText(NumberUtils.forMatNumber(mData.getPersonBuy()));
                            }
                        }

                        //减法
                        tvDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(TextUtils.isEmpty(tvCount.getText().toString())){
                                    tvCount.setText(String.valueOf(1));
                                    tvCount.setSelection(tvCount.length());
                                    return;
                                }
                                int count = Integer.parseInt(tvCount.getText().toString());
                                if (count == 1) {
                                    return;
                                }
                                if(count==0){
                                    tvCount.setText(String.valueOf(1));
                                    tvCount.setSelection(tvCount.length());
                                    return;
                                }
                                tvCount.setText(String.valueOf(count - 1));
                                tvCount.setSelection(tvCount.length());
                            }
                        });
                        //关闭
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        //加法
                        tvAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(TextUtils.isEmpty(tvCount.getText().toString())){
                                    tvCount.setText(String.valueOf(1));
                                    tvCount.setSelection(tvCount.length());
                                    return;
                                }
                                int count = Integer.parseInt(tvCount.getText().toString());
                                tvCount.setText(String.valueOf(count + 1));
                                tvCount.setSelection(tvCount.length());
                            }
                        });
                        tvBuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(tvCount.getText().toString())
                                        ||"0".equals(tvCount.getText().toString())
                                        ||tvCount.getText().toString().startsWith("-")
                                        ||tvCount.getText().toString().startsWith("0"))
                                {
                                    return;
                                }
                                dialog.dismiss();
                                Intent intent = new Intent(GoodsDetailActivity.this, FillOrderActivity.class);
                                OrderGoodsDto dto = new OrderGoodsDto();
                                dto.setTopScore(mData.getTopScore());
                                dto.setFreight(mData.getFreight());
                                dto.setCount(Integer.parseInt(tvCount.getText().toString()));
                                dto.setId(mData.getId());
                                dto.setFile(mData.getFile());
                                dto.setName(mData.getName());
                                // dto.setEarnest(leftFragment.getEarnest());
                                dto.setClasses(getIntent().getIntExtra("classes", 1));
                                List<OrderGoodsDto> dtoList = new ArrayList<>();
                                dtoList.add(dto);
                                intent.putExtra("dtoList", JSONObject.toJSONString(dtoList));
                                intent.putExtra("type", "2");
                                intent.putExtra("orderType", "1");
                                startActivity(intent);

                            }
                        });
                        tvAddShop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(tvCount.getText().toString())
                                        ||"0".equals(tvCount.getText().toString())
                                        ||tvCount.getText().toString().startsWith("-")
                                        ||tvCount.getText().toString().startsWith("0"))
                                {
                                    return;
                                }
                                dialog.dismiss();
                                joinShopCart(getIntent().getIntExtra("classes", 1), getUserId(), mData.getId(), Integer.parseInt(tvCount.getText().toString()));
                            }
                        });


                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    /**
     * 加入购物车
     *
     * @param userId
     * @param id
     * @param quantity
     * @param classes
     */
    private void joinShopCart(Integer classes, String userId, String id, Integer quantity) {
        showLoadingDialog();
        viewModel.joinShopCart(classes, userId, id, String.valueOf(quantity));
        viewModel.getJoinShopCartliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("加入购物车成功");
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }


}
