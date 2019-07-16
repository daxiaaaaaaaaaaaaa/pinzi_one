package com.jilian.pinzi.ui.friends;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyFriendDetailAdapter;
import com.jilian.pinzi.adapter.MyFriendsCircleAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.FriendCircleDto;
import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.ui.viewmodel.FriendViewModel;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author ningpan
 * @since 2018/11/23 14:00 <br>
 * description: 我的朋友圈 Activity
 */
public class MyFriendsCircleActivity extends BaseActivity implements MyFriendDetailAdapter.FriendListener {

    private RecyclerView rvMineFriendsCircle;
    private MyFriendsCircleAdapter adapter;
    private List<FriendCircleListDto> datas;//
    private LinearLayoutManager linearLayoutManager;
    private FriendViewModel viewModel;
    private SmartRefreshLayout srHasData;


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
        viewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mine_friends_circle;
    }

    @Override
    public void initView() {
        if (PinziApplication.getInstance().getLoginDto().getId().equals(getIntent().getStringExtra("uId"))) {
            setNormalTitle("朋友圈", v -> finish(), R.drawable.icon_friends_camera, v -> {
                // 进入发布朋友圈界面
                startActivity(new Intent(this, PublishFriendsActivity.class));
            });
        } else {
            setNormalTitle("朋友圈", v -> finish());
        }

        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        rvMineFriendsCircle = (RecyclerView) findViewById(R.id.rv_mine_friends_circle);
        rvMineFriendsCircle.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(this, 26)));
        linearLayoutManager = new LinearLayoutManager(this);
        rvMineFriendsCircle.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        datas.add(new FriendCircleListDto());
        adapter = new MyFriendsCircleAdapter(getIntent().getStringExtra("url"), getIntent().getStringExtra("name"), this, datas, this, this);
        rvMineFriendsCircle.setAdapter(adapter);
        srHasData.setEnableLoadMore(false);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void initData() {
        getData();
        //监听外来是否要刷新
        RxBus.getInstance().toObservable().map(new Function<Object, FriendMsg>() {
            @Override
            public FriendMsg apply(Object o) throws Exception {
                return (FriendMsg) o;
            }
        }).subscribe(new Consumer<FriendMsg>() {
            @Override
            public void accept(FriendMsg eventMsg) throws Exception {
                if (eventMsg != null) {
                    if (eventMsg.getCode() == 200) {
                        getData();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 获取我的 或者 某個人的 朋友圈列表数据
     */
    private void getData() {
        viewModel.MyFriendCircleList(getIntent().getStringExtra("uId"));
        viewModel.getMyFriendCirc().observe(this, new Observer<BaseDto<List<FriendCircleDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<FriendCircleDto>> listBaseDto) {
                srHasData.finishRefresh();
                if (listBaseDto.isSuccess()
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0))
                        && EmptyUtils.isNotEmpty(listBaseDto.getData().get(0).getList())) {
                    //朋友圈 数据
                    List<FriendCircleListDto> list = listBaseDto.getData().get(0).getList();
                    //按时间分组
                    datas.clear();
                    datas.add(new FriendCircleListDto());
                    datas.addAll(groudByDay(list));
                    adapter.notifyDataSetChanged();
                    //开启子线程
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //对视频封面处理 耗时操作
                            for (int i = 1; i < datas.size(); i++) {

                                for (int j = 0; j < datas.get(i).getDatas().size(); j++) {
                                    //视频地址不为空
                                    if (EmptyUtils.isNotEmpty(datas.get(i).getDatas().get(j)) && EmptyUtils.isNotEmpty(datas.get(i).getDatas().get(j).getVideo())) {
                                        datas.get(i).getDatas().get(j).setBitmap(BitmapUtils.getScanBitmap(BitmapUtils.getNetVideoBitmap(datas.get(i).getDatas().get(j).getVideo())));
                                        handler.sendEmptyMessage(1000);
                                    }
                                }
                            }


                        }
                    }.start();


                }
            }
        });
    }

    private boolean isHas;//是否有同一天的

    /**
     * 数据按时间分组
     *
     * @param list
     * @return
     */
    private List<FriendCircleListDto> groudByDay(List<FriendCircleListDto> list) {
        List<FriendCircleListDto> mList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //当前这条说说的 年 月 日
            String day = DateUtil.dateToString(DateUtil.DATE_FORMAT, new Date(Long.parseLong(list.get(i).getCreateDate())));
            FriendCircleListDto dto = list.get(i);
            dto.setDay(day);

            if (mList.size() <= 0) {
                List<FriendCircleListDto> data = new ArrayList<>();
                data.add(list.get(i));
                dto.setDatas(data);
                mList.add(dto);

            } else {
                for (int j = 0; j < mList.size(); j++) {
                    isHas = false;
                    if (mList.get(j).getDay().equals(day)) {
                        isHas = true;
                        mList.get(j).getDatas().add(dto);
                    }
                    if (!isHas && j == (mList.size() - 1)) {
                        List<FriendCircleListDto> data = new ArrayList<>();
                        data.add(list.get(i));
                        dto.setDatas(data);
                        mList.add(dto);
                    }

                }
            }
        }
        return mList;
    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    /**
     * 删除朋友圈
     *
     * @param id
     */
    @Override
    public void driendCircleDelete(String id) {
        Dialog dialog = new Dialog(this, R.style.dialogFullscreen);
        dialog.setContentView(R.layout.dialog_bottom_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
            dialog.dismiss();
            viewModel.FriendCircleDelete(id);
            viewModel.getDelete().observe(this, new Observer<BaseDto<String>>() {
                @Override
                public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                    getLoadingDialog().dismiss();
                    if (stringBaseDto.isSuccess()) {
                        ToastUitl.showImageToastSuccess("删除成功");
                        //通过事件总线 把 前面两个Activity的数据也刷新
                        FriendMsg eventMsg = new FriendMsg();
                        eventMsg.setCode(300);
                        RxBus.getInstance().post(eventMsg);

                        for (int i = 1; i < datas.size(); i++) {
                            for (int j = 0; j < datas.get(i).getDatas().size(); j++) {
                                if (id.equals(datas.get(i).getDatas().get(j).getId())) {
                                    datas.get(i).getDatas().remove(j);
                                    adapter.notifyDataSetChanged();
                                    return;
                                }
                            }

                        }

                    } else {
                        ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                    }
                }
            });
        });
        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
            dialog.dismiss();

        });
    }

    /**
     * 点击详情
     *
     * @param id
     */
    @Override
    public void clickCircle(String id) {
        Intent intent = new Intent(this, FriendDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("uId", getIntent().getStringExtra("uId"));
        startActivity(intent);
    }
}
