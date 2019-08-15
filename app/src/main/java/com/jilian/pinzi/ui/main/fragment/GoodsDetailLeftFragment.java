package com.jilian.pinzi.ui.main.fragment;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CommentGoodAdapter;
import com.jilian.pinzi.adapter.CommentHeadAdapter;
import com.jilian.pinzi.adapter.CommonPagerAdapter;
import com.jilian.pinzi.adapter.GoodParamAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.adapter.common.BannerAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.BannerDto;
import com.jilian.pinzi.common.dto.GoodsDetailDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.ParameterDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.ShopDetailActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.GoodRxTimerUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.UrlUtils;
import com.jilian.pinzi.views.CustomScrollViewPager;
import com.jilian.pinzi.views.NoScrollViewPager;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.jilian.pinzi.views.RoundImageView;
import com.jilian.pinzi.views.RoundViewPager;
import com.jilian.pinzi.views.SlideSeeMoreLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GoodsDetailLeftFragment
        extends BaseFragment implements CustomItemClickListener, ViewPagerItemClickListener, CommentHeadAdapter.CommentHeadListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CommentHeadAdapter headAdapter;
    private List<String> datas;
    private List<ScoreBuyGoodsDto> goods;
    private RecyclerView rvGood;
    private LinearLayoutManager lm_good;
    private CommentGoodAdapter goodAdapter;
    private MainViewModel viewModel;
    private RelativeLayout rlKillGood;
    private ImageView ivLogo;
    private TextView tvName;
    public TextView tvPrice;
    private RelativeLayout rlSeeParam;
    private TextView tvIntrouce;
    private ImageView ivOne;
    private ImageView ivTwo;
    private ImageView ivThree;
    private TextView tvEvaluateCount;
    private ImageView ivShopHead;
    private LinearLayout llGoShop;
    private TextView tvShopName;
    private TextView tvShopGrade;
    private LinearLayout llCommentPhoto;
    private LinearLayout llShop;
    private TextView tvStoreName;
    private ViewPager viewPager;
    private LinearLayout llIndcator;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSecond;
    public TextView tvPerPrice;
    private TextView tvPoint;
    private TextView tvGoodsStandard;
    private SlideSeeMoreLayout layout;
    private WebView webview;
    private RelativeLayout rlReturn;
    private TextView tvFreight;
    private TextView tvEarnest;


    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goodsdetailleft;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        tvFreight = (TextView) view.findViewById(R.id.tv_freight);
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(getmActivity(), 15));//右间距
        tvPoint = (TextView) view.findViewById(R.id.tv_point);
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        tvMin = (TextView) view.findViewById(R.id.tv_min);
        tvSecond = (TextView) view.findViewById(R.id.tv_second);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tvEarnest = (TextView) view.findViewById(R.id.tv_earnest);
        llIndcator = (LinearLayout) view.findViewById(R.id.ll_indcator);
        tvStoreName = (TextView) view.findViewById(R.id.tv_storeName);
        llShop = (LinearLayout) view.findViewById(R.id.ll_shop);
        llCommentPhoto = (LinearLayout) view.findViewById(R.id.ll_comment_photo);
        ivShopHead = (ImageView) view.findViewById(R.id.iv_shop_head);
        llGoShop = (LinearLayout) view.findViewById(R.id.ll_go_shop);
        tvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
        tvShopGrade = (TextView) view.findViewById(R.id.tv_shop_grade);
        ivOne = (ImageView) view.findViewById(R.id.iv_one);
        ivTwo = (ImageView) view.findViewById(R.id.iv_two);
        ivThree = (ImageView) view.findViewById(R.id.iv_three);
        tvEvaluateCount = (TextView) view.findViewById(R.id.tv_evaluateCount);
        rlSeeParam = (RelativeLayout) view.findViewById(R.id.rl_see_param);
        tvIntrouce = (TextView) view.findViewById(R.id.tv_introuce);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getmActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(getmActivity(), -30));//右间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        rlKillGood = (RelativeLayout) view.findViewById(R.id.rl_kill_good);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        recyclerView.setFocusable(false);
        datas = new ArrayList<>();
        headAdapter = new CommentHeadAdapter(getmActivity(), datas, this);
        recyclerView.setAdapter(headAdapter);
        rvGood = (RecyclerView) view.findViewById(R.id.rv_good);
        rvGood.addItemDecoration(new RecyclerViewSpacesItemDecoration(hashMap));
        lm_good = new LinearLayoutManager(getmActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvGood.setLayoutManager(lm_good);
        goods = new ArrayList<>();
        goodAdapter = new CommentGoodAdapter(getmActivity(), goods, this, getmActivity().getIntent().getIntExtra("classes", 1));
        rvGood.setAdapter(goodAdapter);
        // tvBuy = (TextView) view.findViewById(R.id.tv_buy);
        tvPerPrice = (TextView) view.findViewById(R.id.tv_per_price);
        tvPerPrice.getPaint().setAntiAlias(true);//抗锯齿
        tvPerPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        layout = (SlideSeeMoreLayout) view.findViewById(R.id.layout);
        webview = (WebView) view.findViewById(R.id.webview);
        rlReturn = (RelativeLayout) view.findViewById(R.id.rl_return);
        tvGoodsStandard = (TextView) view.findViewById(R.id.tv_goodsStandard);
        //返佣金
        if (getmActivity().getIntent().getIntExtra("return", 1) == 2) {
            rlReturn.setVisibility(View.VISIBLE);
        } else {
            rlReturn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取类似推荐商品
        getScoreBuyGoods();
    }

    /**
     * 获取商品信息
     */
    private void getScoreBuyGoods() {
        //不需要分页 这里
        viewModel.getScoreBuyGoods(null, null, PinziApplication.getInstance().getLoginDto() == null ? 0 : PinziApplication.getInstance().getLoginDto().getType(), 2);
        viewModel.getScoreBuyGoodsliveData().observe(this, new Observer<BaseDto<List<ScoreBuyGoodsDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ScoreBuyGoodsDto>> dto) {
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    goods.clear();
                    goods.addAll(dto.getData());
                    goodAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    private GoodsDetailDto mData;

    /**
     * //监听外来是否要去成功的界面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        /* Do something */
        if (EmptyUtils.isNotEmpty(event)
                && EmptyUtils.isNotEmpty(event.getGoodsDetailDto())

        ) {
            initGoodetailView(event.getGoodsDetailDto());
        }
    }


    /**
     * 初始化商品详情的布局
     *
     * @param data
     */
    private void initGoodetailView(GoodsDetailDto data) {
        if (data == null) {
            return;
        }
        this.mData = data;
        tvFreight.setText("邮费：¥" + (data.getFreight() == null ? "0.00" : NumberUtils.forMatNumber(Double.parseDouble(data.getFreight()))));
        tvGoodsStandard.setText(data.getGoodsStandard());
        //初始化viewpager
        initLogoPager();
        tvName.setText(data.getName());
        initPrice(data);
        if (EmptyUtils.isNotEmpty(data.getIntroduce())) {
            tvIntrouce.setVisibility(View.VISIBLE);
            tvIntrouce.setText(data.getIntroduce());
        } else {
            tvIntrouce.setVisibility(View.GONE);
        }
        if (data.getIsEarnest() == 1) {
            tvEarnest.setVisibility(View.VISIBLE);
        } else {
            tvEarnest.setVisibility(View.GONE);
        }
        initShopType();
        //倒计时视图
        initTimeTaskView();
        //初始化商品参数
        initParamDara();
        //初始化评价数据
        initCommentData();
        //初始化门店数据
        initShopData();

        //图文详情
        String content = data.getContent();

        //content是后台返回的h5标签
        webview.loadDataWithBaseURL(null,
                getHtmlData(content), "text/html", "utf-8", null);

    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 初始化商城的类型i
     */
    private void initShopType() {
        //积分商城
        if (getmActivity().getIntent().getIntExtra("shopType", 1) == 2) {
            tvPoint.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.VISIBLE);
            tvPrice.getPaint().setAntiAlias(true);//抗锯齿
            tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            tvPrice.setTextColor(Color.parseColor("#999999"));
            tvPoint.setText(mData.getScore() + "积分");
        }

    }

    /**
     * 初始化价格
     *
     * @param data
     */
    private void initPrice(GoodsDetailDto data) {
        LoginDto dto = PinziApplication.getInstance().getLoginDto();
        //不同用户 从采购中心进来
        if (getmActivity().getIntent().getIntExtra("classes", 1) == 2)
        {
            //秒杀商品
            if (getmActivity().getIntent().getIntExtra("type", 1) == 2)
            {
                tvPerPrice.setVisibility(View.VISIBLE);
                //类型（1.普通用户 2.终端 3.渠道 4.总经销商）
                if (dto.getType() == 1) {
                    tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getPersonBuy()));
                }
                if (dto.getType() == 2) {
                    tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getTerminalBuy()));
                }
                if (dto.getType() == 3) {
                    tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getChannelBuy()));
                }
                if (dto.getType() == 4) {
                    tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy()));
                }
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));

            } else {
                tvPerPrice.setVisibility(View.GONE);
                if (dto.getType() == 1) {
                    tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getPersonBuy()));
                    tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getPersonBuy() * data.getEarnestRate() * 0.01));
                }
                if (dto.getType() == 2) {
                    tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getTerminalBuy()));
                    tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getTerminalBuy() * data.getEarnestRate() * 0.01));
                }
                if (dto.getType() == 3) {
                    tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getChannelBuy()));
                    tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getChannelBuy() * data.getEarnestRate() * 0.01));
                }
                if (dto.getType() == 4) {
                    tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy()));
                    tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy() * data.getEarnestRate() * 0.01));
                }
            }
        }//平台  从总经销商进来
        else if (getmActivity().getIntent().getIntExtra("classes", 1) == 3) {
            //秒杀商品
            if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
                tvPerPrice.setVisibility(View.VISIBLE);
                tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy()));
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));
            } else {
                tvPerPrice.setVisibility(View.GONE);
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getFranchiseeBuy() * data.getEarnestRate() * 0.01));
            }

        }
        ////平台  从二批商进来
        else if (getmActivity().getIntent().getIntExtra("classes", 1) == 4) {
            //秒杀商品
            if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
                tvPerPrice.setVisibility(View.VISIBLE);
                tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getChannelBuy()));
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));

            } else {
                tvPerPrice.setVisibility(View.GONE);
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getChannelBuy()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getChannelBuy() * data.getEarnestRate() * 0.01));
            }

        }
        ////平台  从门店进来
        else if (getmActivity().getIntent().getIntExtra("classes", 1) == 5) {

            if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
                tvPerPrice.setVisibility(View.VISIBLE);
                tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getTerminalBuy()));
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));

            } else {
                tvPerPrice.setVisibility(View.GONE);
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getTerminalBuy()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getTerminalBuy() * data.getEarnestRate() * 0.01));
            }

        } else if (getmActivity().getIntent().getIntExtra("classes", 1) == 6) {

            if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
                tvPerPrice.setVisibility(View.VISIBLE);
                tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getPersonBuy()));
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));

            } else {
                tvPerPrice.setVisibility(View.GONE);
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getPersonBuy()));
                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getPersonBuy() * data.getEarnestRate() * 0.01));
            }

        }

        //不是从采购中心进来
        else {
            //秒杀商品
            if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
                tvPerPrice.setVisibility(View.VISIBLE);
                tvPerPrice.setText("原价¥" + NumberUtils.forMatNumber(data.getPersonBuy()));
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getSeckillPrice()));

                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getSeckillPrice() * data.getEarnestRate() * 0.01));

            } else {
                tvPerPrice.setVisibility(View.GONE);
                tvPrice.setText("¥" + NumberUtils.forMatNumber(data.getPersonBuy()));

                tvEarnest.setText("定金：¥" + NumberUtils.forMatNumber(data.getPersonBuy() * data.getEarnestRate() * 0.01));
            }
        }


    }

    private String earnest;//    定金 金额

    public String getEarnest() {
        if (mData.getIsEarnest() == 1) {
            earnest = tvEarnest.getText().toString().replace("定金：¥", "");
            return earnest;
        }
        return null;
    }

    /**
     * 倒计时视图
     */
    private void initTimeTaskView() {
        if (getmActivity().getIntent().getIntExtra("type", 1) == 2) {
            rlKillGood.setVisibility(View.VISIBLE);
            //显示秒杀价格
            long endTime = 0;
            try {
                endTime = DateUtil.stringToDate(DateUtil.DATE_FORMAT_TIME, mData.getEndTime()).getTime();
            } catch (ParseException e) {
                Log.e(TAG, "initTimeTaskView{}" + e);
                e.printStackTrace();
            }
            //开启一个倒计时
            initTimeTask(endTime);

        } else {
            rlKillGood.setVisibility(View.GONE);
        }
    }

    private void initTimeTask(long endTime) {
        GoodRxTimerUtil.interval(1000, new GoodRxTimerUtil.IRxNext() {
            @Override
            public void doNext() {
                long nowTime = 0;
                try {
                    //获取现在的 时分秒 时间戳
                    nowTime = DateUtil.stringToDate(DateUtil.DATE_FORMAT_TIME, DateUtil.dateToString(DateUtil.DATE_FORMAT_TIME, new Date())).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long delTime = endTime - nowTime;
                if (delTime <= 0) {
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

    private List<String> files;
    private BannerAdapter bannerAdapter;
    private List<BannerDto> bannerDtoList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getLoadingDialog().dismiss();
            if (msg.what == 1000) {
                List<BannerDto> list = (List<BannerDto>) msg.obj;
                viewPager.setOffscreenPageLimit(list.size());
                viewPager.addOnPageChangeListener(new ViewPagerIndicator(getmActivity(), viewPager, llIndcator, list.size()));
                bannerAdapter = new BannerAdapter(getmActivity());
                bannerAdapter.update(list);
                viewPager.setAdapter(bannerAdapter);
            }
        }
    };

    /**
     * 初始化logo图片
     */
    private void initLogoPager() {
        getLoadingDialog().showDialog();
        String fileStr = mData.getFile();
        bannerDtoList = new ArrayList<>();
        if (EmptyUtils.isNotEmpty(fileStr)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    if (fileStr.contains(",")) {
                        files = new ArrayList<>(Arrays.asList(fileStr.split(",")));
                    } else {
                        files = new ArrayList<>();
                        files.add(fileStr);
                    }
                    for (int i = 0; i < files.size(); i++) {
                        BannerDto dto = new BannerDto();
                        dto.setPath(files.get(i));
                        if (files.get(i).contains("mp4")) {
                            dto.setBitmap(getNetVideoBitmap(files.get(i)));
                        }
                        bannerDtoList.add(dto);
                    }
                    Message msg = Message.obtain();
                    msg.what = 1000;
                    msg.obj = bannerDtoList;
                    handler.sendMessage(msg);

                }
            }.start();

        }

    }

    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        return bitmap;
    }

    /**
     * 初始化门店数据
     */
    private void initShopData() {
        if (mData.getStoreId() == 0) {
            llShop.setVisibility(View.GONE);
            tvStoreName.setVisibility(View.VISIBLE);
        } else {
            tvStoreName.setVisibility(View.GONE);
            llShop.setVisibility(View.VISIBLE);
            tvShopName.setText(mData.getStoreName());
            Glide
                    .with(getmActivity())
                    .load(UrlUtils.getUrl(mData.getStoreLogo()))
                    .into(ivShopHead);
            tvShopGrade.setText("综合评分" + NumberUtils.forMatOneNumber(mData.getStoreGrade()) + "分");
        }

    }

    /**
     * 初始化评价数据
     */
    private void initCommentData() {
        try {
            //评论图片
            String evaluateImg = mData.getEvaluateImg();
            //头像
            String userImgUrl = mData.getUserImgUrl();
            //评论数
            String evaluateCount = mData.getEvaluateCount();
            //初始化评论图片
            initEvaluateImg(evaluateImg);
            //初始化评论数
            tvEvaluateCount.setText("共有" + evaluateCount + "条评价");
            //初始化头像
            initUserImgUrl(userImgUrl);
        } catch (Exception e) {
            Log.e(TAG, "initCommentData{}" + e);
        }
    }

    /**
     * 初始化头像
     *
     * @param userImgUrl
     */
    private void initUserImgUrl(String userImgUrl) {
        datas.clear();
        if (EmptyUtils.isNotEmpty(userImgUrl)) {
            recyclerView.setVisibility(View.VISIBLE);
            if (userImgUrl.contains(",")) {
                String[] dates = userImgUrl.split(",");
                for (int i = 0; i < dates.length; i++) {
                    if (i <= 5) {
                        datas.add(dates[i]);
                        datas.add("");
                        headAdapter.notifyDataSetChanged();
                    }

                }
            } else {
                datas.add(userImgUrl);
                datas.add("");
                headAdapter.notifyDataSetChanged();
            }
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化评论图片
     *
     * @param evaluateImg
     */
    private void initEvaluateImg(String evaluateImg) {
        if (EmptyUtils.isNotEmpty(evaluateImg)) {
            llCommentPhoto.setVisibility(View.VISIBLE);
            if (evaluateImg.contains(",")) {
                String[] dates = evaluateImg.split(",");
                if (dates.length == 3) {
                    Glide
                            .with(getmActivity())
                            .load(UrlUtils.getUrl(dates[0]))
                            .into(ivOne);
                    Glide
                            .with(getmActivity())
                            .load(UrlUtils.getUrl(dates[1]))
                            .into(ivTwo);
                    Glide
                            .with(getmActivity())
                            .load(UrlUtils.getUrl(dates[2]))
                            .into(ivThree);
                }
                if (dates.length == 2) {
                    Glide
                            .with(getmActivity())
                            .load(UrlUtils.getUrl(dates[0]))
                            .into(ivOne);
                    Glide
                            .with(getmActivity())
                            .load(UrlUtils.getUrl(dates[1]))
                            .into(ivTwo);
                }


            } else {
                Glide
                        .with(getmActivity())
                        .load(UrlUtils.getUrl(evaluateImg))
                        .into(ivOne);
            }
        } else {
            llCommentPhoto.setVisibility(View.GONE);
        }
    }

    private List<ParameterDto> parameterDtoList;

    /**
     * 初始化参数据
     */
    private void initParamDara() {
        if (EmptyUtils.isNotEmpty(mData)) {
            try {
                String parameterContent = mData.getParameterContent();
                String parameterName = mData.getParameterName();
                if(!TextUtils.isEmpty(parameterContent)
                        &&!TextUtils.isEmpty(parameterName)){
                    if (parameterContent.contains(",") && parameterName.contains(",")) {
                        String[] names = parameterName.split(",");
                        String[] content = parameterContent.split(",");
                        parameterDtoList = new ArrayList<>();
                        for (int i = 0; i < names.length; i++) {
                            ParameterDto dto = new ParameterDto(names[i], content[i]);
                            parameterDtoList.add(dto);

                        }
                    }
                    else{
                        parameterDtoList = new ArrayList<>();
                        ParameterDto dto = new ParameterDto(parameterName, parameterContent);
                        parameterDtoList.add(dto);
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "initParamDara{} " + e);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        layout.setOnSlideDetailsListener(new SlideSeeMoreLayout.OnSlideDetailsListener() {
            @Override
            public void onStateChanged(SlideSeeMoreLayout.Status status) {
                if (status == SlideSeeMoreLayout.Status.OPEN) {
                    //当前为查看更多页
                    ((GoodsDetailActivity) getmActivity()).rlCenter.setVisibility(View.VISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvCenter.setTextColor(Color.parseColor("#c71233"));

                    ((GoodsDetailActivity) getmActivity()).rlLeft.setVisibility(View.INVISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvLeft.setTextColor(Color.parseColor("#222222"));

                    ((GoodsDetailActivity) getmActivity()).rlRight.setVisibility(View.INVISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvRight.setTextColor(Color.parseColor("#222222"));

                } else {
                    //当前为商品页
                    ((GoodsDetailActivity) getmActivity()).rlLeft.setVisibility(View.VISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvLeft.setTextColor(Color.parseColor("#c71233"));

                    ((GoodsDetailActivity) getmActivity()).rlCenter.setVisibility(View.INVISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvCenter.setTextColor(Color.parseColor("#222222"));

                    ((GoodsDetailActivity) getmActivity()).rlRight.setVisibility(View.INVISIBLE);
                    ((GoodsDetailActivity) getmActivity()).tvRight.setTextColor(Color.parseColor("#222222"));
                }
            }
        });

        //进入店铺
        llGoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShopDetailActivity.class);
                intent.putExtra("shopId", String.valueOf(mData.getStoreId()));
                intent.putExtra("classes", getClasses());
                startActivity(intent);

            }
        });

        //查看参数
        rlSeeParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看商品参数对话框
                if (EmptyUtils.isNotEmpty(parameterDtoList)) {
                    showSeeParamDialog();
                } else {
                    ToastUitl.showImageToastFail("暂无商品参数");
                }

            }
        });

    }
    public int getClasses() {
        return getmActivity().getIntent().getIntExtra("classes",1);
    }
    private LinearLayoutManager lm_see_param;

    /**
     * 查看商品参数对话框
     */
    private void showSeeParamDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_showseeparam)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        lm_see_param = new LinearLayoutManager(getmActivity());
                        ImageView ivClose = holder.getView(R.id.iv_close);
                        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        recyclerView.setLayoutManager(lm_see_param);
                        GoodParamAdapter adapter = new GoodParamAdapter(getmActivity(), parameterDtoList);
                        recyclerView.setAdapter(adapter);

                    }
                })
                .setShowBottom(true)
                .show(getActivity().getSupportFragmentManager());


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getmActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", goods.get(position).getId());
        intent.putExtra("classes", getmActivity().getIntent().getIntExtra("classes", 1));
        startActivity(intent);
        // getmActivity().finish();

    }

    @Override
    public void onViewPageItemClick(View view, int position) {


    }

    @Override
    public void clickCommentHead(View view, int position) {
        NoScrollViewPager viewPager = (NoScrollViewPager) (((GoodsDetailActivity) getmActivity()).findViewById(R.id.customScrollViewPager));
        viewPager.setCurrentItem(2);
        ((GoodsDetailActivity) getmActivity()).rlRight.setVisibility(View.VISIBLE);
        ((GoodsDetailActivity) getmActivity()).tvRight.setTextColor(Color.parseColor("#c71233"));
        ((GoodsDetailActivity) getmActivity()).rlLeft.setVisibility(View.INVISIBLE);
        ((GoodsDetailActivity) getmActivity()).tvLeft.setTextColor(Color.parseColor("#222222"));
        ((GoodsDetailActivity) getmActivity()).rlCenter.setVisibility(View.INVISIBLE);
        ((GoodsDetailActivity) getmActivity()).tvCenter.setTextColor(Color.parseColor("#222222"));

    }

}
