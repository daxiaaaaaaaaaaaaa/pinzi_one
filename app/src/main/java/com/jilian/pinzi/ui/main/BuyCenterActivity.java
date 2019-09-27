package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.GoodTypeAdapter;
import com.jilian.pinzi.adapter.OneAdapter;
import com.jilian.pinzi.adapter.TwoAdapter;
import com.jilian.pinzi.adapter.TypeTopAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.BuyerCenterGoodsDto;
import com.jilian.pinzi.common.dto.GoodsTypeDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.SeckillDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.TimeKillGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.ThreeFragmentActity;
import com.jilian.pinzi.ui.friends.MyFriendsCircleActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 采购中心
 * 分类，公用界面
 */
public class BuyCenterActivity extends BaseActivity implements OneAdapter.OneListener, TwoAdapter.TwoListener, CustomItemClickListener, TypeTopAdapter.TypeTopListener {

    private List<GoodsTypeDto> typeDtos;//头部的分类列表
    private MainViewModel viewModel;

    private LinearLayout llKillGoods;

    private LinearLayout rlReturn;
    private RecyclerView rvOne;
    private List<SeckillPrefectureDto> timeKillGoods;//秒杀专区
    private OneAdapter oneAdapter;
    private LinearLayoutManager lm_one;
    private RecyclerView rvTwo;
    private LinearLayoutManager lm_two;
    private List<MainRecommendDto> returnData;//佣金反区
    private TwoAdapter twoAdapter;
    //
    private RecyclerView rvTop;
    private RecyclerView rvBottom;
    private List<BuyerCenterGoodsDto> datas;
    private GridLayoutManager gridLayoutManager;
    private GoodTypeAdapter goodTypeAdapter;
    private LinearLayoutManager lm_top;
    private TypeTopAdapter typeTopAdapter;
    private RelativeLayout rlData;
    private TextView tvOneMore;
    private TextView tvTwoMore;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSecond;

    public int getClasses() {
        return getIntent().getIntExtra("classes", 1);
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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_buycenter;
    }
    private int type;
    @Override
    public void initView() {

        tvOneMore = (TextView) findViewById(R.id.tv_one_more);
        tvTwoMore = (TextView) findViewById(R.id.tv_two_more);
        tvHour = (TextView) findViewById(R.id.tv_hour);
        tvMin = (TextView) findViewById(R.id.tv_min);
        tvSecond = (TextView) findViewById(R.id.tv_second);
        //上面
        rvTop = (RecyclerView) findViewById(R.id.rv_top);
        lm_top = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTop.setLayoutManager(lm_top);
        typeDtos = new ArrayList<>();
        GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
//        goodsTypeDto.setSelected(true);
        goodsTypeDto.setName("全部");
        typeDtos.add(goodsTypeDto);
        typeTopAdapter = new TypeTopAdapter(this, typeDtos, this);
        rvTop.setAdapter(typeTopAdapter);


        //   //下面
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rvBottom.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 15));//右间距
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvBottom.setHasFixedSize(true);
        rvBottom.setNestedScrollingEnabled(false);
        rvBottom.addItemDecoration(new RecyclerViewSpacesItemDecoration(map));
        datas = new ArrayList<>();
        goodTypeAdapter = new GoodTypeAdapter(this, datas, this, getClasses());
        rvBottom.setAdapter(goodTypeAdapter);


        //

        rlData = (RelativeLayout) findViewById(R.id.rl_no_data);
        rlReturn = (LinearLayout) findViewById(R.id.rl_return);
        llKillGoods = (LinearLayout) findViewById(R.id.ll_kill_goods);

        rvOne = (RecyclerView) findViewById(R.id.rv_one);
        lm_one = new LinearLayoutManager(this);
        lm_one.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvOne.setLayoutManager(lm_one);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 15));//右间距
        rvOne.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        rvTwo = (RecyclerView) findViewById(R.id.rv_two);
        lm_two = new LinearLayoutManager(this);
        lm_two.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvTwo.setLayoutManager(lm_two);
        rvTwo.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        //分类进来
        if (getIntent().getBooleanExtra("flag", false)) {
            setNormalTitle("分类详情", v -> finish());
            llKillGoods.setVisibility(View.GONE);
            rlReturn.setVisibility(View.GONE);
        } else {
            //采购中心进来
            LoginDto dto = PinziApplication.getInstance().getLoginDto();

            if (dto.getType() == 1 || getClasses() == 1) {
                setNormalTitle("个人采购中心", v -> finish());
                type = 1;

            } else if (dto.getType() == 2) {
                setNormalTitle("门店采购中心", v -> finish());
                type = 2;
            } else if (dto.getType() == 3) {
                setNormalTitle("二批商采购中心", v -> finish());
                type = 3;
            } else if (dto.getType() == 4) {
                setNormalTitle("总经销商采购中心", v -> finish());
                type = 4;
            }
            else if (dto.getType() == 5) {

                if(getIntent().getIntExtra("classes",0)==3){
                    setNormalTitle("总经销商采购中心", v -> finish());
                    type = 4;
                }
                if(getIntent().getIntExtra("classes",0)==4){
                    setNormalTitle("二批商采购中心", v -> finish());
                    type = 3;
                }
                if(getIntent().getIntExtra("classes",0)==5){
                    setNormalTitle("门店采购中心", v -> finish());
                    type = 2;
                }
                if(getIntent().getIntExtra("classes",0)==6){
                    setNormalTitle("个人采购中心", v -> finish());
                    type = 1;
                }

            }
            setrightTitle("朋友圈", "#FFFFFF", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(new Intent(BuyCenterActivity.this, ThreeFragmentActity.class));
                    intent.putExtra("type", type);
                    intent.putExtra("uId", PinziApplication.getInstance().getLoginDto().getId());
                    intent.putExtra("index", 2);
                    intent.putExtra("back", 2);
                    startActivity(intent);
                }
            });
            //
            timeKillGoods = new ArrayList<>();//秒杀专区
            oneAdapter = new OneAdapter(this, timeKillGoods, this, getClasses());
            oneAdapter.setClasses(getClasses());
            rvOne.setAdapter(oneAdapter);
            rvOne.setFocusable(false);
            //
            //
            returnData = new ArrayList<>();//佣金反渠
            twoAdapter = new TwoAdapter(this, returnData, this);
            twoAdapter.setClasses(getClasses());
            rvTwo.setAdapter(twoAdapter);
            rvTwo.setFocusable(false);
            //
            //获取秒杀专区的数据
            getSeckillPrefectureData();
            //获取返佣金专区数据
            getReturnCommissionData();

        }
    }

    /**
     * 获取返佣金专区数据
     */
    private void getReturnCommissionData() {
        viewModel.ReturnCommission(1, 3);
        viewModel.getReturnCommissionliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> listBaseDto) {
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    rlReturn.setVisibility(View.VISIBLE);
                    returnData.clear();
                    returnData.addAll(listBaseDto.getData());
                    twoAdapter.notifyDataSetChanged();
                } else {
                    rlReturn.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 获取秒杀专区的数据
     */
    private void getSeckillPrefectureData() {
        viewModel.SeckillPrefecture(1, 3);
        viewModel.getSeckillPrefectureliveData().observe(this, new Observer<BaseDto<SeckillDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<SeckillDto> seckillPrefectureDtoBaseDto) {
                try {
                    if (EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData())
                            && EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData().getTimeKillGoods())) {

                        llKillGoods.setVisibility(View.VISIBLE);
                        timeKillGoods.clear();
                        timeKillGoods.addAll(seckillPrefectureDtoBaseDto.getData().getTimeKillGoods());
                        oneAdapter.notifyDataSetChanged();

                        if (EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData().getEndTime())) {
                            long endTime = seckillPrefectureDtoBaseDto.getData().getEndTime();
                            initTimeTask(endTime);
                        }
                    } else {
                        llKillGoods.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "getSeckillPrefectureData: {}" + e);

                }


            }
        });
    }

    private void initTimeTask(long endTime) {
        MainRxTimerUtil.cancel();
        MainRxTimerUtil.interval(1000, new MainRxTimerUtil.IRxNext() {
            @Override
            public void doNext() {//获取现在的 时分秒 时间戳
                long nowTime = 0;
                try {
                    nowTime = DateUtil.stringToDate(DateUtil.DATE_FORMAT_TIME, DateUtil.dateToString(DateUtil.DATE_FORMAT_TIME, new Date())).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //单位 毫秒
                long delTime = endTime - nowTime;
                if (delTime <= 0) {
                    MainRxTimerUtil.cancel();
                    getSeckillPrefectureData();
                    tvHour.setText("00");
                    tvMin.setText("00");
                    tvSecond.setText("00");
                } else {
                    String str = DateUtil.timeToHms(delTime);
                    tvHour.setText(str.split(":")[0]);
                    tvMin.setText(str.split(":")[1]);
                    tvSecond.setText(str.split(":")[2]);
                }

            }
        });
    }
    private int screenWidth;

    @Override
    public void initData() {
        //获取屏幕宽度
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        //获取类型数据
        getGoodTypeData();
        //
    }

    private void getGoodTypeData() {
        showLoadingDialog();
        viewModel.getGoodsType();
        viewModel.getGoodsTypeliveData().observe(this, new Observer<BaseDto<List<GoodsTypeDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<GoodsTypeDto>> listBaseDto) {
                hideLoadingDialog();
                if (listBaseDto.isSuccess() && EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    typeDtos.addAll(listBaseDto.getData());
                    //算出个数
                    int itemCount = screenWidth / DisplayUtil.dip2px(BuyCenterActivity.this, 80);
                    int position = getIntent().getIntExtra("position", 0);
                    typeDtos.get(position).setSelected(true);
                    typeTopAdapter.notifyDataSetChanged();
//                    if(typeDtos.size()>itemCount){
//                        //向左移动一个单位
//                        if(position>itemCount+1){
                    rvTop.scrollToPosition(position);
//                        }
//                    }


                    //获取商品数据
                    initGoodData();

                } else {
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }

            }
        });
    }

    private int pageNo = 1;//
    private int pageSize = 40;//
    private String entrance;

    private void initGoodData() {
        if (getClasses() == 1) {
            entrance = "2";
        } else {
            entrance = "1";
        }
        viewModel.getBuyerCenterGoods(pageNo, pageSize, PinziApplication.getInstance().getLoginDto() == null ? 0 : PinziApplication.getInstance().getLoginDto().getType(), getGoodsType(), entrance);
        viewModel.getBuyerCenterGoodsliveData().observe(this, new Observer<BaseDto<List<BuyerCenterGoodsDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<BuyerCenterGoodsDto>> listBaseDto) {
                //srNoData.finishRefresh();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    //srNoData.setVisibility(View.GONE);
                    rvBottom.setVisibility(View.VISIBLE);
                    rlData.setVisibility(View.GONE);
                    datas.clear();
                    datas.addAll(listBaseDto.getData());
                    goodTypeAdapter = new GoodTypeAdapter(BuyCenterActivity.this, datas, BuyCenterActivity.this, getClasses());
                    rvBottom.setAdapter(goodTypeAdapter);

                } else {
                    rvBottom.setVisibility(View.GONE);
                    rlData.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    /**
     * 获取采购中心的数据
     */
    @Override
    public void initListener() {
        tvOneMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyCenterActivity.this, SecondsKillZoneActivity.class);
                intent.putExtra("classes", getClasses());
                startActivity(intent);
            }
        });
        tvTwoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyCenterActivity.this, MoreGoodsActivity.class);
                intent.putExtra("return", 2);
                intent.putExtra("classes", getClasses());
                startActivity(intent);
            }
        });

    }

    /**
     * 获取类型
     *
     * @return
     */
    private String getGoodsType() {
        for (int i = 0; i < typeDtos.size(); i++) {
            if (typeDtos.get(i).isSelected()) {
                return typeDtos.get(i).getId();
            }
        }
        return null;
    }

    @Override
    public void onItemOneClick(View view, int position) {
        Intent intent;
        if (position == timeKillGoods.size() - 1) {
            intent = new Intent(this, SecondsKillZoneActivity.class);
            intent.putExtra("classes", getClasses());
            startActivity(intent);
        } else {
            intent = new Intent(this, GoodsDetailActivity.class);
            intent.putExtra("goodsId", timeKillGoods.get(position).getId());
            intent.putExtra("type", 2);
            intent.putExtra("classes", getClasses());
            startActivity(intent);
        }
    }

    @Override
    public void onItemTwoClick(View view, int position) {
        Intent intent;
//        if (position == returnData.size() - 1) {
//            intent = new Intent(this, MoreGoodsActivity.class);
//            intent.putExtra("classes", getClasses());
//            startActivity(intent);
//        } else {
        intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", returnData.get(position).getId());
        intent.putExtra("classes", getClasses());
        startActivity(intent);

        // }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getId());
        intent.putExtra("classes", getClasses());
        startActivity(intent);
    }

    @Override
    public void oneTypeClick(View view, int position) {
        for (int i = 0; i < typeDtos.size(); i++) {
            if (i == position) {
                typeDtos.get(i).setSelected(true);
            } else {
                typeDtos.get(i).setSelected(false);
            }
        }
        typeTopAdapter.notifyDataSetChanged();
        initGoodData();
    }
}
