package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyCommentPhotoSeeAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.EvaluateDetailDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 查看评价
 */
public class PostEvaluationSeeActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<String> datas;
    private MyCommentPhotoSeeAdapter adapter;
    private ImageView ivOne;
    private ImageView tvTwo;
    private ImageView tvThree;
    private ImageView tvFour;
    private ImageView tvFive;
    private TextView tvContent;
    private ImageView ivIsHidden;
    private int isAnonymity;//是否匿名（0.否 1.是）
    private int grade;//评分
    private TextView tvCount;
    private MyViewModel viewModel;


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
        return R.layout.activity_postevaluation_see;
    }

    @Override
    public void initView() {
        setNormalTitle("查看评价", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 3);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 10));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setLayoutManager(gridLayoutManager);
        datas = new ArrayList<>();
        adapter = new MyCommentPhotoSeeAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
        //
        ivOne = (ImageView) findViewById(R.id.iv_one);
        tvTwo = (ImageView) findViewById(R.id.tv_two);
        tvThree = (ImageView) findViewById(R.id.tv_three);
        tvFour = (ImageView) findViewById(R.id.tv_four);
        tvFive = (ImageView) findViewById(R.id.tv_five);
        tvContent = (TextView) findViewById(R.id.tv_content);
        ivIsHidden = (ImageView) findViewById(R.id.iv_is_hidden);
        tvCount = (TextView) findViewById(R.id.tv_count);
    }

    @Override
    public void initData() {
        showLoadingDialog();
        viewModel.getEvaluateDetail(getIntent().getStringExtra("orderId"));
        viewModel.getEvaluateDetailLiveData().observe(this, new Observer<BaseDto<EvaluateDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<EvaluateDetailDto> evaluateDetailDtoBaseDto) {
                hideLoadingDialog();
                if (evaluateDetailDtoBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(evaluateDetailDtoBaseDto.getData())) {
                        initCommentView(evaluateDetailDtoBaseDto.getData());
                    } else {
                        ToastUitl.showImageToastFail("未获取到评价信息");
                    }
                } else {
                    ToastUitl.showImageToastFail(evaluateDetailDtoBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 展示评价内容
     *
     * @param data
     */
    private void initCommentView(EvaluateDetailDto data) {
        String imgUrl = data.getImgUrl();
        if (imgUrl != null) {
            if (!imgUrl.contains(",")) {
                datas.clear();
                datas.add(imgUrl);
            } else {
                String paths[] = imgUrl.split(",");
                datas.clear();
                datas.addAll(new ArrayList<>(Arrays.asList(paths)));
            }
            adapter.notifyDataSetChanged();
        }
        if (data.getIsAnonymity() == 0) {
            ivIsHidden.setImageResource(R.drawable.image_chat_turn_on);
        }
        if (data.getIsAnonymity() == 1) {
            ivIsHidden.setImageResource(R.drawable.image_chat_open);
        }
        tvContent.setText(data.getContent());
        tvCount.setText(data.getContent().length() + "/100");
        switch (data.getGrade()) {
            case 0:
                grade = 1;
                ivOne.setImageResource(R.drawable.image_my_star_active);
                tvTwo.setImageResource(R.drawable.image_my_star_active);
                tvThree.setImageResource(R.drawable.image_my_star_active);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
                break;
            case 1:
                grade = 1;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_active);
                tvThree.setImageResource(R.drawable.image_my_star_active);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
                break;
            case 2:
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_active);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
                break;
            case 3:
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
                break;
            case 4:
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_select);
                tvFive.setImageResource(R.drawable.image_my_star_active);
                break;
            case 5:
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_select);
                tvFive.setImageResource(R.drawable.image_my_star_select);
                break;
        }
    }

    @Override
    public void initListener() {

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewPhotosActivity.class);
        intent.putExtra("url", getUrl(datas));
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private String getUrl(List<String> datas) {
            String url = "";
            if (EmptyUtils.isNotEmpty(datas)) {
                for (int i = 0; i < datas.size(); i++) {
                    if (i != datas.size() - 1) {
                        url += datas.get(i) + ",";
                    }
                    else{
                        url += datas.get(i) + "";
                    }
                }
            }
            return url;

        }

}
