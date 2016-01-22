package com.oubowu.exerciseprogram.aigestudiostudy;

import android.view.Window;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.socks.library.KLog;

public class AigeActivity1 extends BaseActivity {


//    @Bind(R.id.imageView)
//    ImgView imageView;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_aige1;
    }

    @Override
    protected void initView() {
        // 界面的真正根视图
        KLog.e(getWindow().getDecorView() + ";" + getWindow().findViewById(Window.ID_ANDROID_CONTENT));

        // activity的onresume执行之后，view的ondraw方法才执行，这里的onDraw是个陷阱，绘制是个异步过程，这里的setBitmap紧跟在findById后面，
        // onDraw是没有setBitmap快，所以不会有空指针。但是如果是网络图片或者给setBitmap加个延迟，就会异常，而且没有因为刷新View setBitmap也不会显示图片。
        // 这个是跟java基础有关，但是解释太不清晰了吧。

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pal);
//        imageView.setBitmap(bitmap);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aige, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked())
            return true;
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.action_matrix:
                FragmentManage.changeFragment(new ColorMatrixFragment(), getSupportFragmentManager(), R.id.fl, ColorMatrixFragment.class.getSimpleName());
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
    }*/

}
