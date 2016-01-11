package com.oubowu.exerciseprogram.aigestudiostudy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.FragmentManage;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AigeActivity extends BaseActivity {

    @Bind(R.id.cv)
    CustomView cv;
    @Bind(R.id.ev)
    EraserView ev;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_aige;
    }

    @Override
    protected void initView() {
        new Thread(cv).start();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cv.ondestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aige, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        cv.ondestroy();
        ev.setVisibility(View.GONE);
        if (item.isChecked())
            return true;
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.action_matrix:
                FragmentManage.changeFragment(new ColorMatrixFragment(), getSupportFragmentManager(), R.id.fl, ColorMatrixFragment.class.getSimpleName());
                break;
            case R.id.action_ligting:
                FragmentManage.changeFragment(new LightingColorFragment(), getSupportFragmentManager(), R.id.fl, ColorMatrixFragment.class.getSimpleName());
                break;
            case R.id.action_duff:
                FragmentManage.changeFragment(new PorterDuffColorFragment(), getSupportFragmentManager(), R.id.fl, ColorMatrixFragment.class.getSimpleName());
                break;
            case R.id.action_xfermode:
                FragmentManage.changeFragment(new PorterDuffXfermodeFragment(), getSupportFragmentManager(), R.id.fl, ColorMatrixFragment.class.getSimpleName());
                break;
            case R.id.action_blur:
                FragmentManage.changeFragment(new BlurMaskFilterFragment(), getSupportFragmentManager(), R.id.fl, BlurMaskFilterFragment.class.getSimpleName());
                break;
            case R.id.action_shadow:
                FragmentManage.changeFragment(new ShadowViewFragment(), getSupportFragmentManager(), R.id.fl, ShadowViewFragment.class.getSimpleName());
                break;
            case R.id.action_ecg:
                FragmentManage.changeFragment(new ECGViewFragment(), getSupportFragmentManager(), R.id.fl, ECGViewFragment.class.getSimpleName());
                break;
            case R.id.action_bitmapshaper:
                FragmentManage.changeFragment(new BrickViewFragment(), getSupportFragmentManager(), R.id.fl, BrickViewFragment.class.getSimpleName());
                break;
            case R.id.action_reflect:
                FragmentManage.changeFragment(new ReflectViewFragment(), getSupportFragmentManager(), R.id.fl, ReflectViewFragment.class.getSimpleName());
                break;
            case R.id.action_dream:
                FragmentManage.changeFragment(new DreamEffectFragment(), getSupportFragmentManager(), R.id.fl, DreamEffectFragment.class.getSimpleName());
                break;
            case R.id.action_matrix_image:
                FragmentManage.changeFragment(new MatrixImageFragment(), getSupportFragmentManager(), R.id.fl, MatrixImageFragment.class.getSimpleName());
                break;
            case R.id.action_camera:
                FragmentManage.changeFragment(new CameraFragment(), getSupportFragmentManager(), R.id.fl, CameraFragment.class.getSimpleName());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
