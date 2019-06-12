package com.jilian.pinzi.ui.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.R;

/**
 * 聊天界面
 */
public class ConversationActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ivBack = findViewById(R.id.iv_left_text);
        ivBack.setImageResource(R.drawable.image_back);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getIntent().getData().getQueryParameter("title"));
        ivBack.setOnClickListener(v -> finish());
    }
}
