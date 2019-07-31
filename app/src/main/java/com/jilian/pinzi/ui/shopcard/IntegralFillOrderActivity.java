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

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.FillOrderAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.GoodByScoreDto;
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
import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

public class IntegralFillOrderActivity extends BaseActivity implements FillOrderAdapter.CilckGoodListener {
    private LinearLayout llSelectAddress;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvDefault;
    private TextView tvAdress;
    private LinearLayout llGoodsDetail;
    private RecyclerView recyclerView;
    private TextView tvPayCount;
    private TextView tvOk;
    private MyViewModel viewModel;
    private MainViewModel mainViewModel;
    private int pageNo = 1;//
    private int pageSize = 1;//
    private AddressDto address;
    private LinearLayout llContainer;
    private int SELECTADRESS_CODE = 101;
    private int ADDADRESS_CODE = 102;
    private int SELECTPERSON_CODE = 104;
    private String addressId;
    private int isUseCommisson = 0;//使用佣金（未使用传0）
    private LinearLayoutManager linearLayoutManager;
    private List<OrderGoodsDto> datas;
    private FillOrderAdapter adapter;
    private TextView tvTwo;
    private ImageView ivTwo;
    private RelativeLayout rlSelectPerson;
    private TextView tvPerson;
    private TextView tvAllCount;
    private TextView tvFreight;
    private TextView tvCommisionNum;
    private String shipper = "0";//发货人（平台为0）
    private String freightPrice;//运费
    private TextView tvDeductionMoney;

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
        return R.layout.activity_integral_fill_order;
    }

    @Override
    public void initView() {
        setNormalTitle("填写订单", v -> finish());
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        llSelectAddress = (LinearLayout) findViewById(R.id.ll_select_address);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvDefault = (TextView) findViewById(R.id.tv_default);
        tvAdress = (TextView) findViewById(R.id.tv_adress);
        llGoodsDetail = (LinearLayout) findViewById(R.id.ll_goods_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);
        datas = new ArrayList<>();
        adapter = new FillOrderAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
        adapter.setGoodTypes(1);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvDeductionMoney = (TextView) findViewById(R.id.tv_deductionMoney);
        rlSelectPerson = (RelativeLayout) findViewById(R.id.rl_select_person);
        tvPerson = (TextView) findViewById(R.id.tv_person);
        tvAllCount = (TextView) findViewById(R.id.tv_all_count);
        tvFreight = (TextView) findViewById(R.id.tv_freight);
        tvCommisionNum = (TextView) findViewById(R.id.tv_commisionNum);
    }

    private List<OrderGoodsDto> dtoList;

    @Override
    public void initData() {
        initGoodList();
        // 初始化运费
        getFreight();
        //初始化商品总金额
        getGoodsAllCount();
        //获取收货地址
        getAddressList();
        //获取佣金积分抵扣
        getMyScoreOrCommission();


    }

    private void initGoodList() {
        dtoList = JSONObject.parseArray(getIntent().getStringExtra("dtoList"), OrderGoodsDto.class);
        //下单的商品信息
        datas.addAll(dtoList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化总金额
     */
    private double getGoodsAllCount() {
        double allCount = 0;
        for (int i = 0; i < dtoList.size(); i++) {
            allCount += dtoList.get(i).getPrice() * dtoList.get(i).getCount();
        }
        tvAllCount.setText("¥" + NumberUtils.forMatNumber(allCount));
        return allCount;

    }

    /**
     * 初始化运费
     */
    private void getFreight() {
        mainViewModel.getFreight(getGoodId());
        mainViewModel.getFreight().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()&&EmptyUtils.isNotEmpty(stringBaseDto.getData())) {
                    tvFreight.setText("¥" + NumberUtils.forMatNumber(Double.parseDouble(stringBaseDto.getData())));
                } else {
                    tvFreight.setText("¥0.00");
                }
                tvPayCount.setText("￥" + getPayCount());
            }
        });

    }

    /**
     * 需要支付的费用
     *
     * @return
     */
    private String getPayCount() {
        if (two) {
            double payCount = Double.parseDouble(tvFreight.getText().toString().substring(1)) - Double.parseDouble(tvCommisionNum.getText().toString().substring(1));
            if (payCount < 0) {
                return "0.00";
            } else {
                return NumberUtils.forMatNumber(payCount);
            }
        } else {
            return NumberUtils.forMatNumber(Double.parseDouble(dtoList.get(0).getFreight()));
        }

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
                startActivityForResult(new Intent(IntegralFillOrderActivity.this, AddAddressActivity.class), ADDADRESS_CODE);
            }
        });
        dialog.show();

    }

    //佣金 抵扣
    private void getMyScoreOrCommission() {
        tvDeductionMoney.setText("¥" + NumberUtils.forMatNumber(getGoodsAllCount()));
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> dto) {
                if (dto.isSuccess()&&EmptyUtils.isNotEmpty(dto.getData().getCommisionNum())) {
                    ivTwo.setEnabled(true);
                    commisionNum = Double.parseDouble(dto.getData().getCommisionNum());
                    tvTwo.setText("使用" + NumberUtils.forMatNumber(commisionNum) + "佣金可抵扣¥" + NumberUtils.forMatNumber(commisionNum) + "金额");
                } else {
                    ivTwo.setEnabled(false);
                    tvTwo.setText("没有可用的佣金");
                }
                if (two) {
                    if (commisionNum <= Double.parseDouble(tvFreight.getText().toString().substring(1))) {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(commisionNum));
                    } else {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(Double.parseDouble(tvFreight.getText().toString().substring(1))));
                    }
                } else {
                    tvCommisionNum.setText("¥0.00");
                }

            }

        });
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

    private boolean two;
    private double commisionNum;//佣金抵扣的金额

    @Override
    public void initListener() {
        rlSelectPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EmptyUtils.isNotEmpty(dtoList)) {
                    Intent intent = new Intent(IntegralFillOrderActivity.this, SelectPersonActivity.class);
                    intent.putExtra("shipper",shipper);
                    startActivityForResult(intent, SELECTPERSON_CODE);
                }


            }
        });
        ivTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = !two;
                if (two) {
                    ivTwo.setImageResource(R.drawable.image_chat_open);
                    if (commisionNum <= Double.parseDouble(tvFreight.getText().toString().substring(1))) {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(commisionNum));
                    } else {
                        tvCommisionNum.setText("¥" + NumberUtils.forMatNumber(Double.parseDouble(tvFreight.getText().toString().substring(1))));
                    }
                    isUseCommisson = 1;

                } else {
                    ivTwo.setImageResource(R.drawable.image_chat_turn_on);
                    tvCommisionNum.setText("¥0.00");
                    isUseCommisson = 0;

                }
                tvPayCount.setText("￥" + getPayCount());

            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyGoodsByScore();
            }
        });
        llSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择收货地址
                Intent intent = new Intent(IntegralFillOrderActivity.this, SelectAdressActivity.class);
                intent.putExtra("address", address);
                startActivityForResult(intent, SELECTADRESS_CODE);

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

    /**
     * 下单
     */
    private void buyGoodsByScore() {
        showLoadingDialog();
        mainViewModel.buyGoodsByScore(getGoodId(), getQuantity(), getScore(), addressId, shipper, getUserId(), String.valueOf(getLoginDto().getType()), freightPrice, String.valueOf(isUseCommisson));
        mainViewModel.getBuyGoodsByScore().observe(this, new Observer<BaseDto<GoodByScoreDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<GoodByScoreDto> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("下单成功！");
                    Intent intent =new Intent( IntegralFillOrderActivity.this, PayOrderActivity.class);
                    intent.putExtra("shopType",2);
                    intent.putExtra("goodByScoreDto",stringBaseDto.getData());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });

    }

    /**
     * 获取积分
     *
     * @return
     */
    private String getScore() {
        int score = 0;
        for (int i = 0; i < dtoList.size(); i++) {
            score += Integer.parseInt(dtoList.get(i).getScore());
        }
        return String.valueOf(score);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //选择发货人
            if (requestCode == SELECTPERSON_CODE) {
                ShipperDto dto = (ShipperDto) data.getSerializableExtra("data");
                initPersonView(dto);
            }
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

        }

    }

    @Override
    public void clickGoods(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getId());
        startActivity(intent);
    }
}
