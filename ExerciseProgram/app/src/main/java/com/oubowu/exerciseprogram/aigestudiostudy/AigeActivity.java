package com.oubowu.exerciseprogram.aigestudiostudy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.BitmapMeshFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.BitmapMeshFragment1;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.BlurMaskFilterFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.BrickViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.CameraFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.ClipRegionFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.ColorMatrixFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.CustomView;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.DreamEffectFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.ECGViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.EraserView;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.LightingColorFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.MatrixImageFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.MultiCircleFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.PathClipViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.PolyLineViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.PorterDuffColorFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.PorterDuffXfermodeFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.ReflectViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.ShadowViewFragment;
import com.oubowu.exerciseprogram.aigestudiostudy.customview.WaveViewFragment;
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
        cv.ondestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aige, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.action_circle:
                FragmentManage.changeFragment(new MultiCircleFragment(), getSupportFragmentManager(), R.id.fl, MultiCircleFragment.class.getSimpleName());
                break;
            case R.id.action_bitmap_mesh:
                FragmentManage.changeFragment(new BitmapMeshFragment(), getSupportFragmentManager(), R.id.fl, BitmapMeshFragment.class.getSimpleName());
                break;
            case R.id.action_bitmap_mesh1:
                FragmentManage.changeFragment(new BitmapMeshFragment1(), getSupportFragmentManager(), R.id.fl, BitmapMeshFragment1.class.getSimpleName());
                break;
            case R.id.action_path_clip:
                FragmentManage.changeFragment(new PathClipViewFragment(), getSupportFragmentManager(), R.id.fl, PathClipViewFragment.class.getSimpleName());
                break;
            case R.id.action_wave:
                FragmentManage.changeFragment(new WaveViewFragment(), getSupportFragmentManager(), R.id.fl, WaveViewFragment.class.getSimpleName());
                break;
            case R.id.action_poly:
                FragmentManage.changeFragment(new PolyLineViewFragment(), getSupportFragmentManager(), R.id.fl, PolyLineViewFragment.class.getSimpleName());
                break;
            case R.id.action_clip_region:
                FragmentManage.changeFragment(new ClipRegionFragment(), getSupportFragmentManager(), R.id.fl, ClipRegionFragment.class.getSimpleName());
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
