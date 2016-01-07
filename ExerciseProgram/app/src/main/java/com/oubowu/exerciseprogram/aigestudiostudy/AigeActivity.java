package com.oubowu.exerciseprogram.aigestudiostudy;

import android.view.Menu;
import android.view.MenuItem;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.FragmentManage;

import butterknife.Bind;

public class AigeActivity extends BaseActivity {

    @Bind(R.id.cv)
    CustomView cv;

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
}
