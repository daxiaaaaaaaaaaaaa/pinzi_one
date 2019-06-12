package com.jilian.pinzi.ui.friends.imagepager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jilian.pinzi.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
    }

    public void doClick(View view)
    {
        ArrayList<String> urls = new ArrayList<String>();
        urls.add(String.valueOf(R.mipmap.p1));
        urls.add(String.valueOf(R.mipmap.p2));
        urls.add(String.valueOf(R.mipmap.p3));
        urls.add(String.valueOf(R.mipmap.p4));
        Intent intent = new Intent(this, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
        this.startActivity(intent);
    }


}
