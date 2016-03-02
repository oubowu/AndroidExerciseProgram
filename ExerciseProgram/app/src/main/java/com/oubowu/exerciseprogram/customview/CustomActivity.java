package com.oubowu.exerciseprogram.customview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.customview.meituan.MeituanListView;
import com.oubowu.exerciseprogram.customview.uc.UcActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class CustomActivity extends BaseActivity
        implements MeituanListView.OnMeiTuanRefreshListener {

    @Bind(R.id.mt_lv)
    MeituanListView mListView;

    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private Handler mHandler;

    @SuppressWarnings("unchecked")
    private static class MyHandler
            extends Handler {

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

        String[] data = new String[]{"hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world", "hello world", "hello world",
                "hello world", "hello world"};
        mDatas = new ArrayList<String>(Arrays.asList(data));
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnMeiTuanRefreshListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)
                    startActivity(new Intent(CustomActivity.this, UcActivity.class));
            }
        });
    }


    @Override
    protected void initData() {

        mHandler = new MyHandler(this);
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
