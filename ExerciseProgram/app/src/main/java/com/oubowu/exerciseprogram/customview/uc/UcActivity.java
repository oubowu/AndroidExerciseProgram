package com.oubowu.exerciseprogram.customview.uc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.itemtouchhelper.RecyclerViewAdapter;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/2/9 20:11
 * UpdateUser:
 * UpdateDate:
 */
public class UcActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_uc;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, null);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
