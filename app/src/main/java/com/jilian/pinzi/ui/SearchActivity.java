package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.SearchAdapter;
import com.jilian.pinzi.adapter.SearchGoodAdapter;
import com.jilian.pinzi.adapter.SearchHotAdapter;
import com.jilian.pinzi.adapter.SearchRecordAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.HotWordListDto;
import com.jilian.pinzi.common.dto.HotWordSelectDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends BaseActivity implements CustomItemClickListener,SearchHotAdapter.SearchHotListener,SearchRecordAdapter.SearchRecordListener,SearchAdapter.SearchListener,SearchGoodAdapter.GoodClickListener {
    private RecyclerView rvHot;
    private RecyclerView rvRecord;
    private SearchRecordAdapter searchRecordAdapter;
    private List<String> historyData;
    private List<HotWordListDto> hotData;
    private LinearLayoutManager linearLayoutManager;
    private FlexboxLayoutManager flexboxLayoutManager;
    private SearchHotAdapter searchHotAdapter;
    private TextView tvCancel;
    private ImageView ivDeleteCache;
    private EditText etName;
    private TextView tvNoHistory;
    private  MainViewModel viewModel;
    private RecyclerView rvSearch;
    private List<HotWordListDto> searchData;
    private LinearLayoutManager lm_seach;
    private LinearLayoutManager lm_result;
    private SearchAdapter searchAdapter;
    private LinearLayout llOnePage;
    private LinearLayout llTwoPage;
    private LinearLayout llThreePage;
    private List<HotWordSelectDto> selectDtoList;
    private RecyclerView rvResult;
    private SearchGoodAdapter searchGoodAdapter;
    private SmartRefreshLayout srNoData;



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
        return R.layout.activity_search;
    }

    @Override
    public void initView() {

        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableRefresh(false);
        srNoData.setEnableLoadMore(false);
        llOnePage = (LinearLayout) findViewById(R.id.ll_one_page);
        llTwoPage = (LinearLayout) findViewById(R.id.ll_two_page);
        llThreePage = (LinearLayout) findViewById(R.id.ll_three_page);
        rvResult = (RecyclerView) findViewById(R.id.rv_result);
        rvHot = (RecyclerView) findViewById(R.id.rv_hot);
        rvRecord = (RecyclerView) findViewById(R.id.rv_record);
        linearLayoutManager = new LinearLayoutManager(this);
        historyData = new ArrayList<>();
        hotData = new ArrayList<>();
        searchRecordAdapter = new SearchRecordAdapter(this, historyData, this);
        rvRecord.setLayoutManager(linearLayoutManager);
        rvRecord.setAdapter(searchRecordAdapter);
        ////
        flexboxLayoutManager = new FlexboxLayoutManager(this);
        //设置主轴排列方式
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        rvHot.setLayoutManager(flexboxLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this,10));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this,10));//下间距
        rvHot.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        searchHotAdapter = new SearchHotAdapter(this, hotData, this);
        rvHot.setAdapter(searchHotAdapter);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        ivDeleteCache = (ImageView) findViewById(R.id.iv_delete_cache);
        etName = (EditText) findViewById(R.id.et_name);
        tvNoHistory = (TextView) findViewById(R.id.tv_no_history);
        //
        searchData = new ArrayList<>();
        rvSearch = (RecyclerView) findViewById(R.id.rv_search);
        lm_seach = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(lm_seach);
        searchAdapter =new SearchAdapter(this,searchData,this);
        rvSearch.setAdapter(searchAdapter);
        //
        selectDtoList = new ArrayList<>();
        lm_result = new LinearLayoutManager(this);
        searchGoodAdapter = new SearchGoodAdapter(this,selectDtoList,this);
        rvResult.setLayoutManager(lm_result);
        rvResult.setAdapter(searchGoodAdapter);
    }

    @Override
    public void initData() {
        getHistoryData();
        HotWordList(null,1);
    }

    /**
     *
     * @param word
     * @param type 1 获取热词  2 获取查询条件列表
     */
    private void HotWordList(String word,int type) {
        viewModel.HotWordList(word);
        viewModel.getHotWordLisInliveData().observe(this, new Observer<BaseDto<List<HotWordListDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<HotWordListDto>> listBaseDto) {
                if(listBaseDto.isSuccess()&&EmptyUtils.isNotEmpty(listBaseDto.getData())){
                    if(type==1){
                        hotData.clear();
                        hotData.addAll(listBaseDto.getData());
                        searchHotAdapter.notifyDataSetChanged();
                    }
                    else{
                        searchData.clear();
                        searchData.addAll(listBaseDto.getData());
                        searchAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }

    /**
     * 获取历史数据
     */
    private void getHistoryData() {
        historyData.clear();
        String listStr = SPUtil.getData(Constant.SP_VALUE.HISTORY_SP,Constant.SP_VALUE.HISTORY,String.class,null);
        if(TextUtils.isEmpty(listStr)){
            ivDeleteCache.setVisibility(View.GONE);
            tvNoHistory.setVisibility(View.VISIBLE);
            rvRecord.setVisibility(View.GONE);
        }
        else{
            ivDeleteCache.setVisibility(View.VISIBLE);
            tvNoHistory.setVisibility(View.GONE);
            List<String> list = JSONObject.parseArray(listStr,String.class);
            historyData.addAll(list);
            searchRecordAdapter.notifyDataSetChanged();
            rvRecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isClick){
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString())){
                    llOnePage.setVisibility(View.VISIBLE);
                    llTwoPage.setVisibility(View.GONE);
                    llThreePage.setVisibility(View.GONE);
                }
                else{
                    llOnePage.setVisibility(View.GONE);
                    llTwoPage.setVisibility(View.VISIBLE);
                    llThreePage.setVisibility(View.GONE);
                }
                HotWordList(null,2);
            }

            @Override
            public void afterTextChanged(Editable s) {
                isClick = false;
            }
        });
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //在这里做请求操作
                    search();
                    return true;
                }
                return false;
            }
        });


        ivDeleteCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.clearData(Constant.SP_VALUE.HISTORY_SP);
                getHistoryData();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 搜索
     */
    private void search() {
        if(TextUtils.isEmpty(etName.getText().toString())){
            return;
        }
        //写入缓存
        String listStr = SPUtil.getData(Constant.SP_VALUE.HISTORY_SP,Constant.SP_VALUE.HISTORY,String.class,null);
        List<String> myLIst = JSONObject.parseArray(listStr,String.class)==null?new ArrayList<>():JSONObject.parseArray(listStr,String.class);
        if(!myLIst.contains(etName.getText().toString())){
            myLIst.add(etName.getText().toString());
            SPUtil.putData(Constant.SP_VALUE.HISTORY_SP,Constant.SP_VALUE.HISTORY,JSONObject.toJSONString(myLIst));
        }
        toSearCh();
    }

    /**
     * 去具体的查询商品数据
     */
    private void toSearCh() {
        getLoadingDialog().showDialog();
        viewModel.HotWordSelect(etName.getText().toString());
        viewModel.getSearchResultliveData().observe(this, new Observer<BaseDto<List<HotWordSelectDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<HotWordSelectDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                llOnePage.setVisibility(View.GONE);
                llTwoPage.setVisibility(View.GONE);
                llThreePage.setVisibility(View.VISIBLE);
                if(listBaseDto.isSuccess()&&EmptyUtils.isNotEmpty(listBaseDto.getData())){
                    selectDtoList.clear();
                    selectDtoList.addAll(listBaseDto.getData());
                    searchGoodAdapter.notifyDataSetChanged();
                    rvResult.setVisibility(View.VISIBLE);
                    srNoData.setVisibility(View.GONE);
                }
                else{
                    rvResult.setVisibility(View.GONE);
                    srNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
    private boolean isClick;//是否是点击 选中的
    @Override
    public void hotClick(int position) {
        isClick = true;
        etName.setText(hotData.get(position).getWord());
        search();
    }

    @Override
    public void recordClick(int position) {
        isClick = true;
        etName.setText(historyData.get(position));
        search();
    }

    @Override
    public void searchClick(int position) {
        isClick = true;
        etName.setText(searchData.get(position).getWord());
        search();
    }

    @Override
    public void clickGoods(int position) {
        //对秒杀商品，积分商品，普通商品区分
       Intent intent = new Intent(this ,GoodsDetailActivity.class);
       intent.putExtra("goodsId", selectDtoList.get(position).getId());
       //秒杀商品
       if(selectDtoList.get(position).getIsSeckill()==1
               &&selectDtoList.get(position).getSeckillPrice()>0){
           intent.putExtra("type", 2);
       }
       //积分商品
       else if(selectDtoList.get(position).getScoreBuy()>0){
           intent.putExtra("shopType", 2);//积分商城
       }
//       //普通商品
//       else{
//
//       }
       startActivity(intent);
    }
}
