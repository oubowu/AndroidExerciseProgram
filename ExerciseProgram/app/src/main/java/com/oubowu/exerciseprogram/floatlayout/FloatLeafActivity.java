package com.oubowu.exerciseprogram.floatlayout;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/11/29 16:24
 * UpdateUser:
 * UpdateDate:
 */
public class FloatLeafActivity extends BaseActivity {

    @Bind(R.id.fl)
    FloatLayout fl;

    @OnClick(R.id.bt_add_leaf)
    void addLeaf() {
        fl.addLeaf();
    }

    @OnClick(R.id.bt_play_leaf)
    void playLeaf() {
        fl.playLeaf();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_float_leaf;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fl.onDestroy();
    }
}
