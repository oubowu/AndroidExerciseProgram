package com.oubowu.exerciseprogram.viewdraghelper;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import tyrantgit.explosionfield.ExplosionField;

public class SwipeActivity extends BaseActivity {

    @Bind(R.id.vd)
    VDLayout vd;

    private ExplosionField field;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_swipe;
    }

    @Override
    protected void initView() {
        field = ExplosionField.attach2Window(this);
        vd.setExplosionField(field);
    }

    @Override
    protected void initData() {

    }

}
