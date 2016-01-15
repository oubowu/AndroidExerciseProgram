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
import com.oubowu.exerciseprogram.rxjava.rxbus.RxBus;
import com.oubowu.exerciseprogram.rxjava.utils.DiskCacheUtils;
import com.oubowu.exerciseprogram.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Bind(R.id.rrv)
    RefreshRecyclerView mRefreshRecyclerView;

    private static final String TAG = RxJavaActivity.class.getSimpleName();
    private Observable<String> register;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @OnClick(R.id.bt)
    void runBus() {
        RxBus.get().post(TAG, "我是中国人！！！");
    }

    MyAdapter mAdapter;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {
        mAdapter = new MyAdapter(RxJavaActivity.this, null, mRefreshRecyclerView, true, true);
        mRefreshRecyclerView.setAdapterAndLayoutManager(mAdapter, new LinearLayoutManager(RxJavaActivity.this, LinearLayoutManager.VERTICAL, false));
        mRefreshRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshList();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected void initData() {

//        refreshList();

        register = RxBus.get().register(TAG, String.class);
        register.observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    ToastUtil.showShort(RxJavaActivity.this, s);
                });

        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> infos = getPackageManager().queryIntentActivities(intent, 0);
        Observable
                .from(infos)
                .map(info -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(getPackageManager());
                    DiskCacheUtils.getInstance(RxJavaActivity.this).storeBitmap(RxJavaActivity.this, icon.getBitmap(), info.activityInfo.name);
                    return new AppInfo(1,
                            info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + sdf.format(new Date()),
                            info.activityInfo.name);
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mRefreshRecyclerView.showRefreshCircleView())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(() -> {
                    mRefreshRecyclerView.hideRefreshCircleView();
                    ToastUtil.showShort(RxJavaActivity.this, "全部加载完毕");
                })
                .doOnError(throwable -> {
                    ToastUtil.showShort(RxJavaActivity.this, throwable.getMessage());
                    mRefreshRecyclerView.hideRefreshCircleView();
                })
                .subscribe(appInfo -> {
                    mAdapter.addData(appInfo);
                    mRefreshRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(TAG, register);
    }

    /*private void refreshList() {

        getApps()
                .toSortedList()
                .subscribeOn(Schedulers.io())
                        // 而与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。
                        // 它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。
                        // 默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，
                        // 它将执行在离它最近的 subscribeOn() 所指定的线程。
                .doOnSubscribe(() -> {
                    mRefreshRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshRecyclerView.showRefreshCircleView();
                        }
                    });
                })
                        // 在调onNext前的操作
                .doOnNext(appInfos -> KLog.e("doOnNext"))
                        // 在调onCompleted前的操作
                .doOnCompleted(() -> KLog.e("doOnCompleted"))
                        // 指定doOnSubscribe的操作运行在主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                        // Observer 即观察者，它决定事件触发的时候将有怎样的行为。除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：
                        // Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的，
                        // 在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用。
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showShort(RxJavaActivity.this, "加载完毕!");
                        mRefreshRecyclerView.hideRefreshCircleView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(RxJavaActivity.this, "发生错误：" + e.getMessage());
                        mRefreshRecyclerView.hideRefreshCircleView();
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        KLog.e("setDatas");
                        mAdapter.setDatas(appInfos);
                    }
                });
    }

    private Observable<AppInfo> getApps() {
        // 我们需要一个函数来检索安装的应用程序列表并把它提供给我们的观察者。我们一个接一个的发射这些应用程序数据，将它们分组到一个单独的列表中，以此来展示响应式方法的灵活性
        return Observable.create(subscriber -> {

            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            final List<ResolveInfo> infos = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : infos) {
                BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(getPackageManager());
                DiskCacheUtils.getInstance(this).storeBitmap(this, icon.getBitmap(), info.activityInfo.name);

                if (subscriber.isUnsubscribed()) {
                    // 在发射新的数据或者完成序列之前要检测观察者的订阅情况。这样的话代码会更高效，因为如果没有观察者等待时我们就不生成没有必要的数据项。
                    return;
                }
//                KLog.e("getApps");
                subscriber.onNext(new AppInfo(1, info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + sdf.format(new Date()),
                        info.activityInfo.name));
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
    }*/

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

        public MyAdapter(Context context, List<AppInfo> datas, RefreshRecyclerView refreshRecyclerView, boolean isHideLoadAllFooter, boolean disableLoadMore) {
            super(context, datas, refreshRecyclerView, isHideLoadAllFooter, disableLoadMore);
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
