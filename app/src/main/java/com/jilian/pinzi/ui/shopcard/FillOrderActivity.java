package com.jilian.pinzi.ui.shopcard;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.FillOrderAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddOrderDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.DiscountConpouDto;
import com.jilian.pinzi.common.dto.DiscountMoneyDto;
import com.jilian.pinzi.common.dto.GoodsIsSecondCheckDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.OrderGoodsDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.common.dto.SelectCardDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.AddAddressActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

/**
 * 填写订单
 */
public class FillOrderActivity extends BaseActivity implements FillOrderAdapter.CilckGoodListener {
    private FillOrderAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderGoodsDto> datas;
    private TextView tvOk;
    private LinearLayout llSelectAddress;
    private RelativeLayout rlSelectPerson;
    private RelativeLayout rlSelectCard;
    private RelativeLayout rlInvoice;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvDefault;
    private TextView tvAdress;
    private int pageNo = 1;//
    private int pageSize = 1;//
    private MyViewModel viewModel;
    private LinearLayout llContainer;
    private int SELECTADRESS_CODE = 101;
    private int ADDADRESS_CODE = 102;
    private int SELECTCARD_CODE = 103;
    private int SELECTPERSON_CODE = 104;
    private int INVOICE_CODE = 105;
    private MainViewModel mainViewModel;
    private TextView tvCardCount;
    private TextView tvCard;
    private TextView tvFreight;
    private TextView tvOne;
    private ImageView ivOne;
    private TextView tvTwo;
    private ImageView ivTwo;
    private TextView tvPerson;
    private TextView tvDeductionMoney;
    private TextView tvCommisionNum;
    private TextView tvAllCount;
    private TextView tvPayCount;
    private TextView tvInvoice;
    private String couponId = "0";//优惠券ID
    private String isUseScore = "0";//使用积分（未使用传0）
    private String isUseCommisson = "0";//使用佣金（未使用传0）
    private String shipper = "0";//发货人（平台为0）
    private String invoiceId;//发票ID
    private String name;//奖品名称（默认传""
    private String addressId;
    private String conpouGoodsId = "0";////适用该优惠券的商品Id（默认传0）
    private String conpouQuantity = "0";//适用该优惠券的商品数量(默认传0)
    private List<OrderGoodsDto> dtoList;
    private LoginDto dto = PinziApplication.getInstance().getLoginDto();
    private RelativeLayout rlOne;
    private RelativeLayout rlCard;
    private RelativeLayout rlScore;
    private TextView tvPreMoney;//定金





    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 获取地址列表
     */
    private void getAddressList() {
        viewModel.getUserAddressList(pageNo, pageSize, getLoginDto().getId());
        viewModel.getAddressListliveData().observe(this, new Observer<BaseDto<List<AddressDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<AddressDto>> addressDtoBaseDto) {
                hideLoadingDialog();
                //有数据
                if (EmptyUtils.isNotEmpty(addressDtoBaseDto.getData())) {
                    address = addressDtoBaseDto.getData().get(0);
                    initAdrdessView();
                }
                //没数据
                else {
                    llContainer.setVisibility(View.GONE);
                    showAddAdressTips();
                }
            }
        });
    }

    /**
     * 添加地址的对话框
     */
    private void showAddAdressTips() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_addadress_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //添加收货地址
                startActivityForResult(new Intent(FillOrderActivity.this, AddAddressActivity.class), ADDADRESS_CODE);
            }
        });
        dialog.show();

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
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_fillorder;
    }

    @Override
    public void initView() {
        setNormalTitle("填写订单", v -> finish());
        tvPreMoney = (TextView) findViewById(R.id.tv_preMoney);
        rlCard = (RelativeLayout) findViewById(R.id.rl_card);
        rlScore = (RelativeLayout) findViewById(R.id.rl_score);
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        tvInvoice = (TextView) findViewById(R.id.tv_invoice);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvAllCount = (TextView) findViewById(R.id.tv_all_count);
        tvDeductionMoney = (TextView) findViewById(R.id.tv_deductionMoney);
        tvCommisionNum = (TextView) findViewById(R.id.tv_commisionNum);
        tvPerson = (TextView) findViewById(R.id.tv_person);
        tvOne = (TextView) findViewById(R.id.tv_one);
        ivOne = (ImageView) findViewById(R.id.iv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvFreight = (TextView) findViewById(R.id.tv_freight);
        tvCard = (TextView) findViewById(R.id.tv_card);
        tvCardCount = (TextView) findViewById(R.id.tv_card_count);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        llSelectAddress = (LinearLayout) findViewById(R.id.ll_select_address);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvDefault = (TextView) findViewById(R.id.tv_default);
        tvAdress = (TextView) findViewById(R.id.tv_adress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rlSelectPerson = (RelativeLayout) findViewById(R.id.rl_select_person);
        rlSelectCard = (RelativeLayout) findViewById(R.id.rl_select_card);
        rlInvoice = (RelativeLayout) findViewById(R.id.rl_invoice);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);
        datas = new ArrayList<>();
        adapter = new FillOrderAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        showLoadingDialog();
        //获取收货地址
        getAddressList();
        //获取下单商品的信息
        initOrderGood();
        //2 奖品
        if ("2".equals(getIntent().getStringExtra("orderType"))) {
            //下单的商品信息
            name = dtoList.get(0).getName();
            datas.addAll(dtoList);
            adapter.notifyDataSetChanged();
            rlOne.setVisibility(View.GONE);
            rlSelectCard.setVisibility(View.GONE);
            rlCard.setVisibility(View.GONE);
            rlScore.setVisibility(View.GONE);
            //初始化佣金
            getMyScoreOrCommission();
            tvFreight.setText("¥" + NumberUtils.forMatNumber(Double.parseDouble(datas.get(0).getFreight())));
            tvPayCount.setText("¥ "+NumberUtils.forMatNumber(Double.parseDouble(datas.get(0).getFreight())));

        }
        //商品
        else {
            rlOne.setVisibility(View.VISIBLE);
            rlSelectCard.setVisibility(View.VISIBLE);
            rlCard.setVisibility(View.VISIBLE);
            rlScore.setVisibility(View.VISIBLE);
            //初始化运费
            initFreight();
            //获取商品的真实价格
            getTrueGoodInfo();

        }

    }



    /**
     * 初始化下单的商品信息
     */
    private void initOrderGood() {
        dtoList = JSONObject.parseArray(getIntent().getStringExtra("dtoList"), OrderGoodsDto.class);
    }

    /**
     * 获取商品的真实价格
     */
    private void getTrueGoodInfo() {
        if (EmptyUtils.isNotEmpty(dtoList)) {
            //判罚是否有秒杀商品
            mainViewModel.getGoodsIsSecondCheck(getClasses(), getLoginDto().getType(), getGoodId());
            mainViewModel.getGoodsIsSecondCheckliveData().observe(this, new Observer<BaseDto<GoodsIsSecondCheckDto>>() {
                @Override
                public void onChanged(@Nullable BaseDto<GoodsIsSecondCheckDto> dto) {
                    if (dto.isSuccess() && EmptyUtils.isNotEmpty(dto)) {
                        if (dto.getData().getGoodsId().contains(",") && dto.getData().getPrices().contains(",")) {
                            String prices[] = dto.getData().getPrices().split(",");
                            String goodIds[] = dto.getData().getGoodsId().split(",");
                            for (int i = 0; i < dtoList.size(); i++) {
                                for (int j = 0; j < prices.length; j++) {
                                        if (dtoList.get(i).getId().equals(goodIds[j])) {
                                        dtoList.get(i).setPrice(Double.parseDouble(prices[j]));
                                    }
                                }
                            }
                        } else {
                            double price = Double.parseDouble(dto.getData().getPrices());
                            dtoList.get(0).setPrice(price);
                        }
                        //下单的商品信息
                        datas.addAll(dtoList);
                        adapter.notifyDataSetChanged();
                        //初始化订单信息
                        initGoodOrderData();

                    } else {
                        ToastUitl.showImageToastFail(dto.getMsg());
                    }
                }
            });

        }
    }

    /**
     * 初始化订单数据
     */
    private void initGoodOrderData() {
        //初始化商品的总金额
        iniGoodsAllCount();
        //佣金 积分余额
        getMyScoreOrCommission();
        //选择优惠券
        getDiscountConpou();
        //获取抵扣金额
        initDiscountMoney();
    }
    private double commisionNum = 0;//总佣金
    //佣金 积分 余额
    private void getMyScoreOrCommission() {
        tvOne.setText(getScrore());
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> dto) {
                if (dto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(dto.getData().getCommisionNum())) {
                        ivTwo.setEnabled(true);
                        commisionNum = Double.parseDouble(dto.getData().getCommisionNum());
                        tvTwo.setText("使用" + NumberUtils.forMatNumber(Double.parseDouble(dto.getData().getCommisionNum())) + "佣金可抵扣¥" + NumberUtils.forMatNumber(Double.parseDouble(dto.getData().getCommisionNum())) + "金额");
                    } else {
                        ivTwo.setEnabled(false);
                        tvTwo.setText("没有可用的佣金");
                    }
                } else {
                    ivTwo.setEnabled(false);
                    tvTwo.setText("没有可用的佣金");

                }
            }

        });
    }

    /**
     * 获取抵消的积分
     * @return
     */
    private String getScrore() {
        double scoreTop =0;
        if(EmptyUtils.isNotEmpty(dtoList))
        {
            for (int i = 0; i <dtoList.size() ; i++) {
                scoreTop+=dtoList.get(i).getTopScore();
            }
        }
        return "使用"+NumberUtils.forMatZeroNumber(scoreTop)+"积分可抵扣¥"+NumberUtils.forMatNumber(scoreTop*0.1)+"金额";
    }

    /**
     * 获取抵扣金额 结算 最终金额
     */
    private void initDiscountMoney() {
        showLoadingDialog();
        mainViewModel.getDiscountMoney(
                getQuantity(), getUserId(),
                String.valueOf(getLoginDto().getType()),
                couponId,
                getClasses(),
                conpouGoodsId,
                isUseCommisson,
                isUseScore,
                conpouQuantity,
                getGoodId());
        mainViewModel.getDiscountMoney().observe(this, new Observer<BaseDto<DiscountMoneyDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<DiscountMoneyDto> dto) {
                hideLoadingDialog();
                if (dto.isSuccess() && EmptyUtils.isNotEmpty(dto.getData())) {
                    tvCard.setText("¥" + NumberUtils.forMatNumber(dto.getData().getCouponRemission()));
                    tvDeductionMoney.setText("¥" + NumberUtils.forMatNumber(dto.getData().getScoreRemission()));
                    tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(dto.getData().getCommissionRemission()));
                } else {
                    tvCard.setText("¥0.00");
                    tvDeductionMoney.setText("¥0.00");
                    tvCommisionNum.setText("¥0.00");
                }
                tvPayCount.setText("¥" + getPayCount());
            }
        });
    }

    /**
     * 初始化商品总金额
     */
    private void iniGoodsAllCount() {
        double allCount = 0;
        for (int i = 0; i < dtoList.size(); i++) {
            allCount += dtoList.get(i).getPrice() * dtoList.get(i).getCount();
        }
        tvAllCount.setText("¥" + NumberUtils.forMatNumber(allCount));
    }

    /**
     * 初始化运费
     */
    private void initFreight() {
        mainViewModel.getFreight(getGoodId());
        mainViewModel.getFreight().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (EmptyUtils.isNotEmpty(stringBaseDto.getData())) {
                    tvFreight.setText("¥" + NumberUtils.forMatNumber(Double.parseDouble(stringBaseDto.getData())));
                } else {
                    tvFreight.setText("¥0.00");
                }
            }
        });

    }

    /**
     * 获取商品 id
     *
     * @return
     */
    private String getGoodId() {
        String id = "";
        for (int i = 0; i < dtoList.size(); i++) {
            if (i == dtoList.size() - 1) {
                id += dtoList.get(i).getId();
            } else {
                id += dtoList.get(i).getId() + ",";

            }
        }
        return id;
    }

    private int usableCount;//可用优惠券张数

    /**
     * 选择优惠券
     */
    private void getDiscountConpou() {
        mainViewModel.getDiscountConpou(getUserId(), getGoodId(), getQuantity(), getClasses());
        mainViewModel.getDiscountConpouDtoliveData().observe(this, new Observer<BaseDto<DiscountConpouDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<DiscountConpouDto> dto) {
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    usableCount = dto.getData().getUsableCount();
                    tvCardCount.setText(usableCount + "张可用");
                } else {
                    tvCardCount.setText("0张可用");
                }

            }
        });


    }

    /**
     * 获取数量
     *
     * @return
     */
    private String getQuantity() {
        String quantity = "";
        for (int i = 0; i < dtoList.size(); i++) {
            if (i == dtoList.size() - 1) {
                quantity += dtoList.get(i).getCount();
            } else {
                quantity += dtoList.get(i).getCount() + ",";

            }
        }
        return quantity;
    }

    private AddressDto address;
    private boolean one;
    private boolean two;

    @Override
    public void initListener() {
        ivOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one = !one;
                if (one) {
                    ivOne.setImageResource(R.drawable.image_chat_open);
                    isUseScore = "1";
                } else {
                    ivOne.setImageResource(R.drawable.image_chat_turn_on);
                    isUseScore = "0";
                }
                initDiscountMoney();

            }
        });
        ivTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                two = !two;
                if (two) {
                    ivTwo.setImageResource(R.drawable.image_chat_open);
                    isUseCommisson = "1";
                } else {
                    ivTwo.setImageResource(R.drawable.image_chat_turn_on);
                    tvCommisionNum.setText("¥0.00");
                    isUseCommisson = "0";
                }
                //奖品
                if("2".equals(getIntent().getStringExtra("orderType"))){
                    if(two){
                        double payCount = Double.parseDouble(datas.get(0).getFreight())-commisionNum;
                        //佣金抵消完
                        if(payCount<=0){
                            tvPayCount.setText("¥0.00" );
                            tvCommisionNum.setText("¥"+NumberUtils.forMatNumber(Double.parseDouble(datas.get(0).getFreight())));
                        }
                        //佣金抵消不完
                        else{
                            tvPayCount.setText("¥"+NumberUtils.forMatNumber(payCount));
                            tvCommisionNum.setText("¥"+NumberUtils.forMatNumber(commisionNum));
                        }
                    }
                    else{
                        tvCommisionNum.setText("¥0.00");
                        tvPayCount.setText("¥ "+NumberUtils.forMatNumber(Double.parseDouble(datas.get(0).getFreight())));
                    }


                }
                //商品
                else{
                    initDiscountMoney();
                }


            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();

            }
        });
        llSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择收货地址
                Intent intent = new Intent(FillOrderActivity.this, SelectAdressActivity.class);
                intent.putExtra("address", address);
                startActivityForResult(intent, SELECTADRESS_CODE);

            }
        });
        rlSelectPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EmptyUtils.isNotEmpty(dtoList)) {
                    Intent intent = new Intent(FillOrderActivity.this, SelectPersonActivity.class);
                    intent.putExtra("shipper", shipper);
                    startActivityForResult(intent, SELECTPERSON_CODE);
                }

            }
        });
        rlSelectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择优惠券
                if (EmptyUtils.isNotEmpty(dtoList)) {
                    Intent intent = new Intent(FillOrderActivity.this, SelectCardActivity.class);
                    intent.putExtra("couponId",couponId);
                    intent.putExtra("classes", getClasses());
                    intent.putExtra("goodsId", getGoodId());
                    intent.putExtra("quantity", getQuantity());
                    startActivityForResult(intent, SELECTCARD_CODE);
                }

            }
        });
        //开发票
        rlInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FillOrderActivity.this, InvoiceActivity.class);
                startActivityForResult(intent, INVOICE_CODE);
            }
        });


    }

    /**
     * 下单
     */
    private void addOrder() {
        showLoadingDialog();
        mainViewModel.addOrder(getClasses(), String.valueOf(getLoginDto().getType()),
                getUserId(), addressId, getGoodId(), getQuantity(),
                couponId, conpouGoodsId,
                String.valueOf(isUseScore),
                String.valueOf(isUseCommisson), shipper,
                tvFreight.getText().toString().substring(1), invoiceId, conpouQuantity,
                getIntent().getStringExtra("type"),
                getIntent().getStringExtra("orderType"), name);
        mainViewModel.getAddOrderliveData().observe(this, new Observer<BaseDto<AddOrderDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<AddOrderDto> addOrderDtoBaseDto) {
                hideLoadingDialog();
                if (addOrderDtoBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("下单成功！");
                    Intent intent = new Intent(FillOrderActivity.this, PayOrderActivity.class);
                    intent.putExtra("addOrderDto", addOrderDtoBaseDto.getData());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUitl.showImageToastFail(addOrderDtoBaseDto.getMsg());
                }
            }
        });

    }

    /**
     * 获取类型
     *
     * @return
     */
    private String getClasses() {
        String classes = "";
        for (int i = 0; i < dtoList.size(); i++) {
            if (i == dtoList.size() - 1) {
                classes += dtoList.get(i).getClasses();
            } else {
                classes += dtoList.get(i).getClasses() + ",";

            }
        }
        return classes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //增加
            if (requestCode == ADDADRESS_CODE) {
                showLoadingDialog();
                getAddressList();
            }
            //选择
            if (requestCode == SELECTADRESS_CODE) {
                address = (AddressDto) data.getSerializableExtra("address");
                initAdrdessView();
            }
            //选择优惠券
            if (requestCode == SELECTCARD_CODE) {
                SelectCardDto dto = (SelectCardDto) data.getSerializableExtra("data");
                initCardView(dto);
            }
            //选择发货人
            if (requestCode == SELECTPERSON_CODE) {
                ShipperDto dto = (ShipperDto) data.getSerializableExtra("data");
                initPersonView(dto);
            }
            //开发票
            if (requestCode == INVOICE_CODE) {
                String dto = data.getStringExtra("data");
                invoiceId = dto;//发票ID

                String invoiceType = data.getStringExtra("invoiceType");
                //类型（1.增值税专用发票 2.增值税普通发票）
                if ("1".equals(invoiceType)) {
                    tvInvoice.setText("增值税专用发票");
                }
                if ("2".equals(invoiceType)) {
                    tvInvoice.setText("增值税普通发票");

                }
            }
        }

    }

    /**
     * 显示发货人
     *
     * @param dto
     */
    private void initPersonView(ShipperDto dto) {
        if (EmptyUtils.isNotEmpty(dto)) {
            tvPerson.setText(dto.getName());
            shipper = dto.getuId();
        }
    }

    /**
     * 选择优惠券
     *
     * @param dto
     */
    private void initCardView(SelectCardDto dto) {
        if (EmptyUtils.isNotEmpty(dto)) {
            couponId = dto.getId();
            conpouQuantity = dto.getGoodsQuantity();
            conpouGoodsId = getGoodId();
            tvCardCount.setText(dto.getName());
        }
        //选择的是空的
        else {
            //重新刷新
            conpouQuantity = "0";
            conpouGoodsId = "0";
            couponId = "0";
        }
        //刷新抵扣金额
        initDiscountMoney();
    }

    /**
     * 1 折扣券
     * 2 代金券
     *
     * @param
     * @return
     */
    private String getPayCount() {
        double payCount;
        //总额
        double allCount = Double.parseDouble(tvAllCount.getText().toString().substring(1));
        //运费
        double freight = Double.parseDouble(tvFreight.getText().toString().substring(1));
        //积分
        double deductionMoney = Double.parseDouble(tvDeductionMoney.getText().toString().substring(1));
        //佣金
        double commisionNum = Double.parseDouble(tvCommisionNum.getText().toString().substring(1));
        //优惠券
        double counpou = Double.parseDouble(tvCard.getText().toString().substring(1));
        payCount = allCount + freight - deductionMoney - commisionNum - counpou;
        return NumberUtils.forMatNumber(payCount);
    }


    /**
     * 显示选择的 地址
     *
     * @param
     */
    private void initAdrdessView() {
        //有数据
        if (EmptyUtils.isNotEmpty(address)) {
            llContainer.setVisibility(View.VISIBLE);
            tvName.setText(address.getName());
            try {
                String phone = address.getPhone().substring(0, 3) + "****" + address.getPhone().substring(7, 11);
                tvPhone.setText(phone);
            } catch (Exception e) {

            }
            if (address.getIsDefault() == 0) {
                tvDefault.setVisibility(View.INVISIBLE);
            } else {
                tvDefault.setVisibility(View.VISIBLE);
            }
            addressId = address.getId();
            tvAdress.setText(address.getArea() + address.getAddress());
        }
        //没数据
        else {
            llContainer.setVisibility(View.GONE);
            showAddAdressTips();
        }
    }

    @Override
    public void clickGoods(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getId());
        startActivity(intent);
    }
}
