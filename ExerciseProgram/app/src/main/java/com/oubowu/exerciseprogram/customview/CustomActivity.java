package com.oubowu.exerciseprogram.customview;

import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.customview.meituan.MeituanListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class CustomActivity extends BaseActivity implements MeituanListView.OnMeiTuanRefreshListener {

//    @Bind(R.id.tlv)
//    ThreePointLoadingView loadingView;

//    @Bind(R.id.seekbar)
//    SeekBar mSeekBar;
//
//    @Bind(R.id.meituan_first)
//    MeiTuanRefreshFirstStepView mMeiTuanRefreshFirstStepView;
//
//    @Bind(R.id.meituan_second)
//    MeiTuanRefreshSecondStepView mMeiTuanRefreshSecondStepView;
//
//    @Bind(R.id.meituan_third)
//    MeiTuanRefreshThirdStepView mMeiTuanRefreshthThirdStepView;

    @Bind(R.id.mt_lv)
    MeituanListView mListView;

    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private Handler mHandler;


    @SuppressWarnings("unchecked")
    private static class MyHandler extends Handler {

        private final WeakReference<CustomActivity> mActivity;

        public MyHandler(CustomActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        activity.mListView.setOnRefreshComplete();
                        activity.mAdapter.notifyDataSetChanged();
                        activity.mListView.setSelection(0);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView() {

        /*mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //计算出当前seekBar滑动的比例结果为0到1
                float currentProgress = (float) progress / (float) seekBar.getMax();
                //给我们的view设置当前进度值
                mMeiTuanRefreshFirstStepView.setCurrentProgress(currentProgress);
                //重画
                mMeiTuanRefreshFirstStepView.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMeiTuanRefreshSecondStepView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) mMeiTuanRefreshSecondStepView.getBackground();
        animationDrawable.start();

        mMeiTuanRefreshthThirdStepView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) mMeiTuanRefreshthThirdStepView.getBackground();
        animationDrawable1.start();*/

        String[] data = new String[]{"hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world", "hello world"};
        mDatas = new ArrayList<String>(Arrays.asList(data));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnMeiTuanRefreshListener(this);

    }

    @Override
    protected void initData() {

        mHandler = new MyHandler(this);

//        NdkJniUtils ndkJniUtils = new NdkJniUtils();
//        KLog.e("jni调用：" + ndkJniUtils.getCLanguageString());
//        KLog.e("jni调用：" + ndkJniUtils.nativeGenerateKey("oubowu"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mDatas.add(0, "new data");
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
