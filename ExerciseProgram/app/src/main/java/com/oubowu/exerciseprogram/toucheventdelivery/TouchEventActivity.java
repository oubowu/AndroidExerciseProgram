package com.oubowu.exerciseprogram.toucheventdelivery;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.socks.library.KLog;

import butterknife.Bind;

public class TouchEventActivity extends BaseActivity {
    @Bind(R.id.farmer)
    TouchEventTextView farmer;
    @Bind(R.id.mayor)
    TouchEventRelativeLayout mayor;
    @Bind(R.id.governor)
    TouchEventFrameLayout governor;


    @Override
    protected int provideLayoutId() {
        return R.layout.activity_touch_event;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    // 传递流程都是dispatchTouchEvent()方法来控制的，如果不人为地干预，事件将由上自下依次传递（因为默认是返回false不会拦截的）

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.e("【总统】任务<" + Util.actionToString(ev.getAction()) + "> : 需要分派");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bo = false;
        KLog.e("【总统】任务<" + Util.actionToString(ev.getAction()) + "> : 下面都解决不了，下次再也不能靠你们了，哼…只能自己尝试一下啦。能解决？" + bo);
        return bo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_touch_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_farmer_fail:
                farmer.setIsSuccess(false);
                item.setChecked(true);
                break;
            case R.id.action_farmer_success:
                farmer.setIsSuccess(true);
                item.setChecked(true);
                break;
            case R.id.action_mayor_success:
                mayor.setIsIntercept(true);
                mayor.setIsSuccess(true);
                item.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
