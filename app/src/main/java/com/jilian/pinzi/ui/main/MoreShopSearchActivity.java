package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MoreSearchShopAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.HotWordSelectBusinessDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoreShopSearchActivity extends BaseActivity implements CustomItemClickListener {
    private EditText etName;
    private TextView tvCancel;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;
    private MoreSearchShopAdapter adapter;
    private List<HotWordSelectBusinessDto> datas;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_moreshopsearch;
    }

    @Override
    public void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        adapter = new MoreSearchShopAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        timeSearch();
    }

    private void timeSearch() {
        RxTextView.textChanges(etName)
                .debounce(200, TimeUnit.MILLISECONDS).skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(CharSequence charSequence) {
                        if (TextUtils.isEmpty(etName.getText().toString())) {
                            datas.clear();
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        search(etName.getText().toString());
                    }
                });
    }


    /**
     * 搜索
     *
     * @param name
     */
    private void search(String name) {
        viewModel.HotWordSelectBusiness(name);
        viewModel.getSearcBussliveData().observe(this, new Observer<BaseDto<List<HotWordSelectBusinessDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<HotWordSelectBusinessDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                datas.clear();
                if (listBaseDto.getData() != null) {
                    datas.addAll(listBaseDto.getData());
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //在这里跳转到店铺展示的界面
        ShopDetailActivity.startActivity(this, datas.get(position).getId(),2);
    }
}
