package com.jilian.pinzi.ui;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CommonPagerAdapter;
import com.jilian.pinzi.adapter.FiveAdapter;
import com.jilian.pinzi.adapter.FourAdapter;
import com.jilian.pinzi.adapter.MainPagerAdapter;
import com.jilian.pinzi.adapter.OneAdapter;
import com.jilian.pinzi.adapter.ThreeAdapter;
import com.jilian.pinzi.adapter.TwoAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.SeckillDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.common.dto.TimeKillGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.main.BuyCenterActivity;
import com.jilian.pinzi.ui.main.GetCardCenterActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.IntegralMallActivity;
import com.jilian.pinzi.ui.main.LotteryCenterActivity;
import com.jilian.pinzi.ui.main.MoreGoodsActivity;
import com.jilian.pinzi.ui.main.MoreNewGoodsActivity;
import com.jilian.pinzi.ui.main.MoreShopsActivity;
import com.jilian.pinzi.ui.main.SecondsKillZoneActivity;
import com.jilian.pinzi.ui.main.SentimentRecommendedActivity;
import com.jilian.pinzi.ui.main.ShopDetailActivity;
import com.jilian.pinzi.ui.main.WebViewActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.SystemMsgActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomScrollViewPager;
import com.jilian.pinzi.views.NoScrollViewPager;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.jilian.pinzi.views.RoundImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OneFragment extends BaseFragment implements OneAdapter.OneListener, TwoAdapter.TwoListener, ThreeAdapter.ThreeListener, FourAdapter.FourListener, FiveAdapter.FiveListener, CustomItemClickListener, ViewPagerItemClickListener {
    private ImageView ivMsg;


    private ViewPager viewPager;
    private List<View> advertiseViews;
    private MainPagerAdapter commonPagerAdapter;
    //
    private LinearLayout llIndcator;
    //
    private OneAdapter oneAdapter;
    private TwoAdapter twoAdapter;
    private ThreeAdapter threeAdapter;
    private FourAdapter fourAdapter;
    //人气推荐
    private FiveAdapter fiveAdapter;
    //
    private RecyclerView rvOne;
    private RecyclerView rvTwo;
    private RecyclerView rvThree;
    private RecyclerView rvFour;
    private RecyclerView rvFive;


    private LinearLayoutManager lm_one;
    private LinearLayoutManager lm_two;
    private LinearLayoutManager lm_three;
    private LinearLayoutManager lm_four;
    private LinearLayoutManager lm_five;
    private RelativeLayout rlNews;
    private ImageView ivSearch;
    private ImageView ivShop;
    private LinearLayout llGetcardCenter;
    private LinearLayout llLotteryCenter;
    private LinearLayout llIntegralMall;
    private LinearLayout llBuyOne;
    private LinearLayout llBuyTwo;
    private LinearLayout llBuyThree;
    private NestedScrollView nestedScrollView;
    private ImageView ivTop;
    private MainViewModel viewModel;
    private TextView tvMsgTitle;
    private ImageView ivNew;
    private LinearLayout llSign;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSecond;

    private SmartRefreshLayout srHasData;
    //
    private List<MainRecommendDto> personData;//人气推荐
    private List<MainRecommendDto> newsData;//新品推荐
    private List<MainRecommendDto> returnData;//佣金反区
    private List<SeckillPrefectureDto> timeKillGoods;//秒杀专区
    private List<StoreShowDto> storeShows;//店铺展示
    private LinearLayout llKillGoods;
    private LinearLayout llBuyFour;
    private TextView tvOneMore;
    private TextView tvTwoMore;
    private TextView tvThreeMore;
    private TextView tvFourMore;
    private TextView tvFiveMore;
    private LinearLayout llFiveContainer;
    private LinearLayout llFourContainer;
    private LinearLayout llFhreeContainer;
    private LinearLayout rlReturn;
    private LinearLayout llNews;
    private LinearLayout llAcivity;


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取消息公告列表
        getMsgData();
        //获取新消息 是否红点
        getNewMsgData();
        //获取秒杀专区的数据
        getSeckillPrefectureData();

    }

    @Override
    protected void initData() {
        setCenterTitle("779百香街", "#FFFFFF");
        //获取首页轮博图
        getStartPage();
        //获取人气推荐数据
        getRecommenPersondData();
        //获取新品推荐数据
        getRecommendNewData();
        //获取返佣金专区数据
        getReturnCommissionData();
        //获取店铺展示的数据
        getStoreShowData();


    }

    /**
     * 获取店铺展示的数据
     */
    private void getStoreShowData() {
        viewModel.StoreShow(1, 6, null, null, null, null, null, null, null, null);
        viewModel.getStoreShowliveData().observe(this, new Observer<BaseDto<List<StoreShowDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StoreShowDto>> listBaseDto) {
                srHasData.finishRefresh();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    storeShows.clear();
                    storeShows.addAll(listBaseDto.getData());
                    threeAdapter.notifyDataSetChanged();
                    llFhreeContainer.setVisibility(View.VISIBLE);
                } else {
                    llFhreeContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 获取秒杀专区的数据
     */
    private void getSeckillPrefectureData() {
        viewModel.SeckillPrefecture(1, 6);
        viewModel.getSeckillPrefectureliveData().observe(this, new Observer<BaseDto<SeckillDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<SeckillDto> seckillPrefectureDtoBaseDto) {
                srHasData.finishRefresh();
                try {
                    if (EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData())
                            &&EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData().getTimeKillGoods())) {
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

    /**
     * 人气推荐数据
     */
    private void getRecommenPersondData() {
        viewModel.RecommendPerson("1", 1, 6);
        viewModel.getRecommendPersonliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> listBaseDto) {
                srHasData.finishRefresh();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    llFiveContainer.setVisibility(View.VISIBLE);
                    personData.clear();
                    personData.addAll(listBaseDto.getData());
                    fiveAdapter.notifyDataSetChanged();
                } else {
                    llFiveContainer.setVisibility(View.GONE);

                }
            }
        });

    }

    /**
     * 获取返佣金专区数据
     */
    private void getReturnCommissionData() {
        viewModel.ReturnCommission(1, 6);
        viewModel.getReturnCommissionliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> listBaseDto) {
                srHasData.finishRefresh();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    returnData.clear();
                    returnData.addAll(listBaseDto.getData());
                    twoAdapter.notifyDataSetChanged();
                    rlReturn.setVisibility(View.VISIBLE);
                } else {
                    rlReturn.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 获取新品推荐数据
     */
    private void getRecommendNewData() {
        viewModel.RecommendNews(1, 6);
        viewModel.getRecommendNewliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> listBaseDto) {
                srHasData.finishRefresh();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    newsData.clear();
                    newsData.addAll(listBaseDto.getData());
                    fourAdapter.notifyDataSetChanged();
                    llFourContainer.setVisibility(View.VISIBLE);
                } else {
                    llFourContainer.setVisibility(View.GONE);
                }
            }
        });

    }


    private List<StartPageDto> list;

    /**
     * 获取轮播图
     */
    private void getStartPage() {
        viewModel.StartPage(3);
        viewModel.getStartPageliveData().observe(this, new Observer<BaseDto<List<StartPageDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StartPageDto>> listBaseDto) {
                srHasData.finishRefresh();
                advertiseViews.clear();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    list = listBaseDto.getData();
                    viewPager.addOnPageChangeListener(new ViewPagerIndicator(getmActivity(), viewPager, llIndcator, list.size()));
                    commonPagerAdapter = new MainPagerAdapter(list, OneFragment.this, getmActivity());
                    viewPager.setAdapter(commonPagerAdapter);
                    startTimeTaskForBanner();
                }

            }
        });
    }

    private int mPosition = 0;

    /**
     * 开启轮播
     */
    private void startTimeTaskForBanner() {
        if (commonPagerAdapter != null && commonPagerAdapter.getCount() > 1) {
            mPosition = 0;
            RxTimerUtil.cancel();
            RxTimerUtil.interval(4000, new RxTimerUtil.IRxNext() {
                @Override
                public void doNext() {
                    mPosition++;
                    if (mPosition == commonPagerAdapter.getCount()) {
                        mPosition = 0;
                    }
                    viewPager.setCurrentItem(mPosition);
                }
            });
        }
    }

    /**
     * 获取新消息
     */
    private void getNewMsgData() {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            return;
        }
        viewModel.MessageRead(2, PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getMsgNewliveData().observe(this, new Observer<BaseDto<Integer>>() {
            @Override
            public void onChanged(@Nullable BaseDto<Integer> integerBaseDto) {
                srHasData.finishRefresh();
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

    /**
     * 获取消息数据
     */
    private void getMsgData() {
        viewModel.SystemInformation(PinziApplication.getInstance().getLoginDto() == null ? null : PinziApplication.getInstance().getLoginDto().getId(), 1, 20, 1);
        viewModel.getMsgliveData().observe(this, new Observer<BaseDto<List<MsgDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MsgDto>> listBaseDto) {
                srHasData.finishRefresh();
                try {
                    srHasData.finishRefresh();
                    String title = listBaseDto.getData().get(0).getTitle();
                    tvMsgTitle.setText(TextUtils.isEmpty(title) ? "暂无新闻消息" : title);
                } catch (Exception e) {
                    tvMsgTitle.setText("暂无新闻消息");
                }

            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        llFiveContainer = (LinearLayout) view.findViewById(R.id.ll_five_container);
        llFourContainer = (LinearLayout) view.findViewById(R.id.ll_four_container);
        llFhreeContainer = (LinearLayout) view.findViewById(R.id.ll_fhree_container);
        rlReturn = (LinearLayout) view.findViewById(R.id.rl_return);

        llNews = (LinearLayout) view.findViewById(R.id.ll_news);
        llAcivity = (LinearLayout) view.findViewById(R.id.ll_acivity);

        //
        tvOneMore = (TextView) view.findViewById(R.id.tv_one_more);
        tvTwoMore = (TextView) view.findViewById(R.id.tv_two_more);
        tvThreeMore = (TextView) view.findViewById(R.id.tv_three_more);
        tvFourMore = (TextView) view.findViewById(R.id.tv_four_more);
        tvFiveMore = (TextView) view.findViewById(R.id.tv_five_more);
        llKillGoods = (LinearLayout) view.findViewById(R.id.ll_kill_goods);
        llBuyFour = (LinearLayout) view.findViewById(R.id.ll_buy_four);
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        srHasData.setEnableLoadMore(false);
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        tvMin = (TextView) view.findViewById(R.id.tv_min);
        tvSecond = (TextView) view.findViewById(R.id.tv_second);

        rvOne = (RecyclerView) view.findViewById(R.id.rv_one);
        rvTwo = (RecyclerView) view.findViewById(R.id.rv_two);
        rvThree = (RecyclerView) view.findViewById(R.id.rv_three);
        rvFour = (RecyclerView) view.findViewById(R.id.rv_four);
        rvFive = (RecyclerView) view.findViewById(R.id.rv_five);
        llIndcator = (LinearLayout) view.findViewById(R.id.ll_indcator);
        ivMsg = (ImageView) view.findViewById(R.id.iv_msg);
        rlNews = (RelativeLayout) view.findViewById(R.id.rl_news);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        ivShop = (ImageView) view.findViewById(R.id.iv_shop);
        llGetcardCenter = (LinearLayout) view.findViewById(R.id.ll_getcard_center);
        llLotteryCenter = (LinearLayout) view.findViewById(R.id.ll_lottery_center);
        llIntegralMall = (LinearLayout) view.findViewById(R.id.ll_integral_mall);
        ivNew = (ImageView) view.findViewById(R.id.iv_new);
        llBuyOne = (LinearLayout) view.findViewById(R.id.ll_buy_one);
        llBuyTwo = (LinearLayout) view.findViewById(R.id.ll_buy_two);
        llBuyThree = (LinearLayout) view.findViewById(R.id.ll_buy_three);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        tvMsgTitle = (TextView) view.findViewById(R.id.tv_msg_title);
        ivTop = (ImageView) view.findViewById(R.id.iv_top);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        llSign = (LinearLayout) view.findViewById(R.id.ll_sign);
        advertiseViews = new ArrayList<>();
        //
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(getmActivity(), 15));//右间距

        //

        lm_one = new LinearLayoutManager(getmActivity());
        lm_one.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvOne.setLayoutManager(lm_one);
        rvOne.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        //
        lm_two = new LinearLayoutManager(getmActivity());
        lm_two.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvTwo.setLayoutManager(lm_two);
        rvTwo.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        //
        lm_three = new LinearLayoutManager(getmActivity());
        lm_three.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvThree.setLayoutManager(lm_three);
        rvThree.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        //
        lm_four = new LinearLayoutManager(getmActivity());
        lm_four.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvFour.setLayoutManager(lm_four);
        rvFour.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));


        lm_five = new LinearLayoutManager(getmActivity());
        lm_five.setOrientation(LinearLayoutManager.HORIZONTAL);// 横向滑动
        rvFive.setLayoutManager(lm_five);
        rvFive.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        //
        storeShows = new ArrayList<>();//店铺展示
        timeKillGoods = new ArrayList<>();//秒杀专区
        personData = new ArrayList<>();//人气推荐
        newsData = new ArrayList<>();//新品推荐
        returnData = new ArrayList<>();//佣金反渠
        oneAdapter = new OneAdapter(getmActivity(), timeKillGoods, this, 1);
        twoAdapter = new TwoAdapter(getmActivity(), returnData, this);
        threeAdapter = new ThreeAdapter(getmActivity(), storeShows, this);
        fourAdapter = new FourAdapter(getmActivity(), newsData, this);
        fiveAdapter = new FiveAdapter(getmActivity(), personData, this);

        rvOne.setAdapter(oneAdapter);
        rvTwo.setAdapter(twoAdapter);
        rvThree.setAdapter(threeAdapter);
        rvFour.setAdapter(fourAdapter);
        rvFive.setAdapter(fiveAdapter);

        rvOne.setFocusable(false);
        rvTwo.setFocusable(false);
        rvThree.setFocusable(false);
        rvFour.setFocusable(false);
        rvFive.setFocusable(false);

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        /**
         * 活动
         */
        llAcivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getmActivity(), MainActivityActivity.class));


            }
        });
        /**
         * 咨询列表
         */
        llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getmActivity(), MainNewsActivity.class));
            }
        });
        /**
         * 秒杀专区
         */
        tvOneMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getmActivity(), SecondsKillZoneActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 更多商品
         */
        tvTwoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getmActivity(), MoreGoodsActivity.class);
                intent.putExtra("return", 2);
                startActivity(intent);
            }
        });
        /**
         * 更多新品
         */
        tvThreeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getmActivity(), MoreShopsActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 更多新品
         */
        tvFourMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getmActivity(), MoreNewGoodsActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 人气推荐 更多推荐
         */
        tvFiveMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getmActivity(), SentimentRecommendedActivity.class);
                startActivity(intent);
            }
        });


        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                //获取消息公告列表
                getMsgData();
                //获取新消息 是否红点
                getNewMsgData();
                //获取首页轮博图
                getStartPage();
                //获取人气推荐数据
                getRecommenPersondData();
                //获取新品推荐数据
                getRecommendNewData();
                //获取返佣金专区数据
                getReturnCommissionData();
                //获取秒杀专区的数据
                getSeckillPrefectureData();
                //获取店铺展示的数据
                getStoreShowData();
            }
        });
        llSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }


                if (EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto())
                        && EmptyUtils.isNotEmpty(PinziApplication.getInstance().getLoginDto().getType())
                        && PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    ToastUitl.showImageToastFail("您是平台用户，只可浏览");
                    return;
                }
                sign();
            }
        });
        ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nestedScrollView.scrollTo(0, 0);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e(TAG, "onScrollChange: " + scrollX + ":" + oldScrollX);
                if (scrollY >= 2000) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }
        });
        llBuyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    intent.putExtra("classes", 2);
                    startActivity(intent);
                    return;
                }

                if (PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                    intent.putExtra("classes", 3);
                    startActivity(intent);
                } else {
                    if (PinziApplication.getInstance().getLoginDto().getType() == 4) {
                        Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                        intent.putExtra("classes", 2);
                        startActivity(intent);
                    } else {
                        ToastUitl.showImageToastFail("该功能开放给总经销商");
                    }
                }


            }
        });
        llBuyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                    intent.putExtra("classes", 4);
                    startActivity(intent);
                } else {
                    if (PinziApplication.getInstance().getLoginDto().getType() == 3) {
                        Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                        intent.putExtra("classes", 2);
                        startActivity(intent);
                    } else {
                        ToastUitl.showImageToastFail("该功能开放给二批商");
                    }

                }


            }
        });
        llBuyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                if (PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                    intent.putExtra("classes", 5);
                    startActivity(intent);
                } else {
                    if (PinziApplication.getInstance().getLoginDto().getType() == 2) {
                        Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                        intent.putExtra("classes", 2);
                        startActivity(intent);
                    } else {
                        ToastUitl.showImageToastFail("该功能开放给门店");
                    }

                }


            }
        });
        llBuyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                if (PinziApplication.getInstance().getLoginDto().getType() == 5) {
                    Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                    intent.putExtra("classes", 6);
                    startActivity(intent);
                } else {

                    if (PinziApplication.getInstance().getLoginDto().getType() == 1) {
                        Intent intent = new Intent(getmActivity(), BuyCenterActivity.class);
                        intent.putExtra("classes", 2);
                        startActivity(intent);
                    } else {
                        ToastUitl.showImageToastFail("该功能开放给个人");

                    }
                }

            }
        });
        llIntegralMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), IntegralMallActivity.class));
            }
        });
        llLotteryCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(getmActivity(), LotteryCenterActivity.class));
            }
        });
        llGetcardCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(getmActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(getmActivity(), GetCardCenterActivity.class));
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), SearchActivity.class));
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
        ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), SystemMsgActivity.class));
            }
        });
        rlNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getmActivity(), NewsNoticeActivity.class));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 用户签到
     */
    private void sign() {
        viewModel.ClockIn(PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getClockInliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    showSignSuccessDialog();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 签到成功提示框
     */
    private void showSignSuccessDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_showsignsuccess);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        dialog.show();
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 人气推荐
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemFiveClick(View view, int position) {
        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", personData.get(position).getId());
        startActivity(intent);


    }

    /**
     * 新品推荐
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemFourClick(View view, int position) {
        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", newsData.get(position).getId());
        startActivity(intent);


    }

    /**
     * 秒杀专区
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemOneClick(View view, int position) {

        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", timeKillGoods.get(position).getId());
        intent.putExtra("type", 2);
        startActivity(intent);

    }

    /**
     * 店铺展示
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemThreeClick(View view, int position) {
        //在这里跳转到店铺展示的界面
        ShopDetailActivity.startActivity(getContext(), storeShows.get(position).getId(), 2);


    }

    /**
     * 返佣金专区
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemTwoClick(View view, int position) {

        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", returnData.get(position).getId());
        intent.putExtra("return", 2);
        startActivity(intent);


    }

    @Override
    public void onItemClick(View view, int position) {


    }

    @Override
    public void onViewPageItemClick(View view, int position) {
        viewModel.ClickByPageId(list.get(position).getId());
        viewModel.getClickData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    Log.e(TAG, "onChanged: 点击成功");
                } else {
                    Log.e(TAG, "onChanged: 点击失败");
                }
            }
        });
        if (list.get(position).getJumpType() == 1) {
            String linkUrl = list.get(position).getLinkUrl();
            Intent intent = new Intent(getmActivity(), WebViewActivity.class);
            intent.putExtra("linkUrl", linkUrl);
            startActivity(intent);
        }
        if (list.get(position).getJumpType() == 2) {
            Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
            intent.putExtra("goodsId", list.get(position).getGoodId());
            startActivity(intent);

        }
    }
}
