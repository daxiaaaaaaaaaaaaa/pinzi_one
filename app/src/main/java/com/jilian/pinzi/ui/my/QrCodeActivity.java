package com.jilian.pinzi.ui.my;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.ZXingUtils;

public class QrCodeActivity extends BaseActivity {
    private ImageView ivQcode;



    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initView() {
        setNormalTitle("我的二维码", v -> finish());
        ivQcode = (ImageView) findViewById(R.id.iv_qcode);
    }

    @Override
    public void initData() {
        String url = Constant.Server.FIRE_URL+"?code="+getLoginDto().getCode()+"&userId = "+getLoginDto().getId();
        Log.e(TAG, "url: "+url );
        Bitmap bitmap = ZXingUtils.createQRImage(url, DisplayUtil.dip2px(this,230),  DisplayUtil.dip2px(this,230));
        ivQcode.setImageBitmap(bitmap);

    }

    @Override
    public void initListener() {

    }
}
