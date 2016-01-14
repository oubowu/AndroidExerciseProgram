package com.oubowu.exerciseprogram.rxjava;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;
import com.oubowu.exerciseprogram.rxjava.bean.AppInfo;
import com.oubowu.exerciseprogram.rxjava.utils.DiskCacheUtils;
import com.oubowu.exerciseprogram.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Bind(R.id.rrv)
    RefreshRecyclerView mRefreshRecyclerView;

    MyAdapter mAdapter;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {
        mAdapter = new MyAdapter(RxJavaActivity.this, null, mRefreshRecyclerView, true);
        mRefreshRecyclerView.setAdapterAndLayoutManager(mAdapter, new LinearLayoutManager(RxJavaActivity.this, LinearLayoutManager.VERTICAL, false));
        mRefreshRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected void initData() {

        refreshList();

    }

    private void refreshList() {
        getApps()
                .toSortedList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showShort(RxJavaActivity.this, "加载完毕!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(RxJavaActivity.this, "发生错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mRefreshRecyclerView.hideRefreshCircleView();
                        mAdapter.setDatas(appInfos);
                    }
                });
    }

    private Observable<AppInfo> getApps() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        mRefreshRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRefreshRecyclerView.showRefreshCircleView();
            }
        });
        // 我们需要一个函数来检索安装的应用程序列表并把它提供给我们的观察者。我们一个接一个的发射这些应用程序数据，将它们分组到一个单独的列表中，以此来展示响应式方法的灵活性
        final Observable<AppInfo> observable = Observable.create(subscriber -> {

            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            final List<ResolveInfo> infos = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : infos) {
                BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(getPackageManager());
                DiskCacheUtils.getInstance(RxJavaActivity.this).storeBitmap(RxJavaActivity.this, icon.getBitmap(), info.activityInfo.name);

                if (subscriber.isUnsubscribed()) {
                    // 在发射新的数据或者完成序列之前要检测观察者的订阅情况。这样的话代码会更高效，因为如果没有观察者等待时我们就不生成没有必要的数据项。
                    return;
                }
//                KLog.e("getApps");
                subscriber.onNext(new AppInfo(1, info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + sdf.format(new Date()), info.activityInfo.name));
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }

        });
        return observable;
    }

    class MyViewHolder extends BaseRecyclerViewHolder {

        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    class MyAdapter extends BaseRecyclerViewAdapter<MyViewHolder, AppInfo> {

        public MyAdapter(Context context, List<AppInfo> datas, RefreshRecyclerView refreshRecyclerView, boolean disableFooter) {
            super(context, datas, refreshRecyclerView, disableFooter);
        }

        @Override
        protected MyViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(RxJavaActivity.this).inflate(R.layout.item_rxjava, parent, false));
        }

        @Override
        protected void onBindCustomViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(mDatas.get(position).getName());
            holder.imageView.setImageBitmap(DiskCacheUtils.getInstance(RxJavaActivity.this).drawableToBitmap(mDatas.get(position).getIcon()));
        }

        @Override
        protected int getCustomItemViewType(int position) {
            return 0;
        }

    }

}
