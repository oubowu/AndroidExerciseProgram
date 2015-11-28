package com.oubowu.exerciseprogram.mvpdemo;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.mvpdemo.bean.AnimateType;
import com.oubowu.exerciseprogram.mvpdemo.presenter.AnimatorPresenter;
import com.oubowu.exerciseprogram.mvpdemo.view.IPerformAnimatorView;

import butterknife.Bind;

/**
 * 类名： MvpActivity
 * 作者: oubowu
 * 时间： 2015/11/27 13:29
 * 功能：View的实现类，MVP中的View其实就是Activity
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class MvpActivity extends BaseActivity implements IPerformAnimatorView, View.OnClickListener {

    @Bind(R.id.et_start)
    EditText etStart;
    @Bind(R.id.et_end)
    EditText etEnd;

    private AnimatorPresenter presenter;
    private int id;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {
        getViewById(R.id.bt_scale_big).setOnClickListener(this);
        getViewById(R.id.bt_scale_small).setOnClickListener(this);
        getViewById(R.id.bt_rotate).setOnClickListener(this);
        getViewById(R.id.bt_circle).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new AnimatorPresenter(this);
    }

    @Override
    public AnimateType getAnimateType() {
        return new AnimateType(id, getAnimatorStartValue(), getAnimatorEndValue());
    }

    @Override
    public View getAnimateView() {
        return findViewById(R.id.iv_head);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    @Override
    public float getAnimatorStartValue() {
        if (TextUtils.isEmpty(etStart.getText().toString())){
            return 0;
        }
        return Float.parseFloat(etStart.getText().toString());
    }

    @Override
    public float getAnimatorEndValue() {
        if (TextUtils.isEmpty(etEnd.getText().toString())){
            return 1;
        }
        return Float.parseFloat(etEnd.getText().toString());
    }

    @Override
    public void onClick(View v) {
        id = v.getId();
        presenter.actAnimator();
    }

}
