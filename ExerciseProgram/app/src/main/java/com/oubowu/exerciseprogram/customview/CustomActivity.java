package com.oubowu.exerciseprogram.customview;

import android.view.View;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;

import butterknife.Bind;

public class CustomActivity extends BaseActivity {

    @Bind(R.id.tlv)
    ThreePointLoadingView loadingView;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.startLoading();
            }
        });
    }
}
