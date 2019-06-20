package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsCommentAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.CommentListDto;
import com.jilian.pinzi.common.dto.GoodsDetailDto;
import com.jilian.pinzi.common.dto.InformationtDetailDto;
import com.jilian.pinzi.common.vo.InformationVo;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.repository.impl.MainRepositoryImpl;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CusRecyclerView;
import com.jilian.pinzi.views.CustomerLinearLayoutManager;
import com.jilian.pinzi.views.MyRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 咨询详情
 */
public class MainNewsDetailActivity extends BaseActivity implements CustomItemClickListener {
    private TextView tvNewTitle;
    private TextView tvDate;
    private WebView webview;
    private CusRecyclerView recyclerView;
    private EditText etContent;
    private List<CommentListDto> datas;
    private CustomerLinearLayoutManager linearLayoutManager;
    private MainNewsCommentAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mainnewsdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("资讯详情", v -> finish());
        webview = (WebView) findViewById(R.id.webview);

        tvNewTitle = (TextView) findViewById(R.id.tv_new_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        webview = (WebView) findViewById(R.id.webview);
        recyclerView = (CusRecyclerView) findViewById(R.id.recyclerView);
        etContent = (EditText) findViewById(R.id.et_content);

        linearLayoutManager = new CustomerLinearLayoutManager(this);
        linearLayoutManager.setCanScrollVertically(false);
        datas = new ArrayList<>();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);
        adapter = new MainNewsCommentAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getInformationDetail(getIntent().getStringExtra("id"), getLoginDto().getId());
    }

    @Override
    public void initListener() {

        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //在这里做请求操作
                    commentInformation(getIntent().getStringExtra("id"), getLoginDto().getId(), etContent.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 评论
     *
     * @param id      咨询ID
     * @param uId     用户ID
     * @param content 内容
     */
    private void commentInformation(String id, String uId, String content) {
        showLoadingDialog();
        viewModel.commentInformation(id, uId, content);
        viewModel.getCommentDetailData().observe(this, new Observer<BaseDto>() {
            @Override
            public void onChanged(@Nullable BaseDto baseDto) {
                hideLoadingDialog();
                if (baseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("评论成功");
                    etContent.setText(null);
                    getInformationDetail(getIntent().getStringExtra("id"), getLoginDto().getId());
                } else {
                    ToastUitl.showImageToastFail(baseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void getInformationDetail(String id, String uId) {
        showLoadingDialog();
        viewModel.getInformationDetail(id, uId, null, null);
        viewModel.getInformationtDetailData().observe(this, new Observer<BaseDto<InformationtDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<InformationtDetailDto> detailDtoBaseDto) {
                hideLoadingDialog();
                if (detailDtoBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(detailDtoBaseDto.getData())) {
                        tvNewTitle.setText(detailDtoBaseDto.getData().getTitle());
                        tvDate.setText(DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date(detailDtoBaseDto.getData().getCreateDate())));
                        datas.addAll(detailDtoBaseDto.getData().getCommentList());
                        adapter.notifyDataSetChanged();
                        //图文详情
                        String content = detailDtoBaseDto.getData().getDetail();

                        //content是后台返回的h5标签
                        webview.loadDataWithBaseURL(null,
                                getHtmlData(content), "text/html", "utf-8", null);
                    }
                } else {
                    ToastUitl.showImageToastFail(detailDtoBaseDto.getMsg());
                }
            }
        });

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
}
