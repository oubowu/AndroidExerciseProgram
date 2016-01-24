package com.oubowu.exerciseprogram.rxjava;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.BuildConfig;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;
import com.oubowu.exerciseprogram.rxjava.model.AppInfo;
import com.oubowu.exerciseprogram.rxjava.nbademo.NbaActivity;
import com.oubowu.exerciseprogram.rxjava.rxbus.RxBus;
import com.oubowu.exerciseprogram.rxjava.utils.DiskCacheUtils;
import com.oubowu.exerciseprogram.utils.ToastUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class RxJavaActivity extends BaseActivity {

    @Bind(R.id.rrv)
    RefreshRecyclerView mRefreshRecyclerView;

    private static final String TAG = RxJavaActivity.class.getSimpleName();
    private Observable<String> register;

    SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private Subscription mSubscription;
    private Subscription mDownLoadSubscription;
    private Subscription mPlaySubscription;

    private Observable<AppInfo> register1;

    @OnClick(R.id.bt)
    void runBus() {
//        RxBus.get().post(TAG, "开始下载视频！！！");
//        download();
        AppInfo appInfo = new AppInfo(100, "Oubowu", "Chinese");
        RxBus.get().post(TAG, appInfo);
    }

    MyAdapter mAdapter;

    @Override
    protected int provideLayoutId() {

        // StrictMode帮助我们侦测敏感的活动，如我们无意的在主线程执行磁盘访问或者网络调用。正如你所知道的，在主线程执行繁重的或者长时的任务是不可取的。
        // 因为Android应用的主线程时UI线程，它被用来处理和UI相关的操作：这也是获得更平滑的动画体验和响应式App的唯一方法。
        // 为了在我们的App中激活StrictMode，我们只需要在MainActivity中添加几行代码，即onCreate()方法中这样：
        // 只在debug构建时使用。这种配置将报告每一种关于主线程用法的违规做法，并且这些做法都可能与内存泄露有关：Activities、BroadcastReceivers、Sqlite等对象。
        // 选择了penaltyLog()，当违规做法发生时，StrictMode将会在logcat打印一条信息
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {
        mAdapter = new MyAdapter(RxJavaActivity.this, null, mRefreshRecyclerView, true, true);
        mRefreshRecyclerView.setAdapterAndLayoutManager(mAdapter, new LinearLayoutManager(RxJavaActivity.this, LinearLayoutManager.VERTICAL, false));
        mRefreshRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    @Override
    protected void initData() {

        initList(true);

        /*register = RxBus.get().register(TAG, String.class);
        register.observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    ToastUtil.showShort(RxJavaActivity.this, s);
                });*/

        register1 = RxBus.get().register(TAG, AppInfo.class);
        register1.subscribe(appInfo -> {
            ToastUtil.showShort(RxJavaActivity.this, appInfo.toString());
        });

        // someFilterMethod();

        // someTransformMethod();

        // someCombineMethod();

        someSchedulersMethod();

    }

    private void someSchedulersMethod() {
        /**
         * Schedulers.io()
         这个调度器时用于I/O操作。它基于根据需要，增长或缩减来自适应的线程池。我们将使用它来修复我们之前看到的StrictMode违规做法。由于它专用于I/O操作，所以并不是RxJava的默认方法；正确的使用它是由开发者决定的。
         重点需要注意的是线程池是无限制的，大量的I/O调度操作将创建许多个线程并占用内存。一如既往的是，我们需要在性能和简捷两者之间找到一个有效的平衡点。

         Schedulers.computation()
         这个是计算工作默认的调度器，它与I/O操作无关。它也是许多RxJava方法的默认调度器：buffer(),debounce(),delay(),interval(),sample(),skip()。

         Schedulers.immediate()
         这个调度器允许你立即在当前线程执行你指定的工作。它是timeout(),timeInterval(),以及timestamp()方法默认的调度器。

         Schedulers.newThread()
         这个调度器正如它所看起来的那样：它为指定任务启动一个新的线程。

         Schedulers.trampoline()
         当我们想在当前线程执行一个任务时，并不是立即，我们可以用.trampoline()将它入队。这个调度器将会处理它的队列并且按序运行队列中每一个任务。它是repeat()和retry()方法默认的调度器。
         */


    }

    private PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

    private void download() {

        mDownLoadSubscription = mDownloadProgress.distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showShort(RxJavaActivity.this, "下载完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(RxJavaActivity.this, "下载失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        KLog.e("下载进度：" + integer + "%");
                    }
                });

        String destination = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && DiskCacheUtils.getUsableSpace(new File(Environment.getExternalStorageDirectory().getPath())) > 10 * 1024) {
            destination = Environment.getExternalStorageDirectory().getPath() + File.separator + "softboy.mp4";
        } else {
            ToastUtil.showShort(RxJavaActivity.this, "内置存储卡无法挂载或空间不足!");
            return;
        }

        final String finalDestination = destination;
        mPlaySubscription = observableDownload("http://bsycdn.miaopai.com/stream/J2ZmMdaLvvAQtYjEUVFeOw__.mp4", destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showShort(RxJavaActivity.this, "下载完成");
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(finalDestination);
                        intent.setDataAndType(Uri.fromFile(file), "video/avi");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(RxJavaActivity.this, "下载失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    private Observable<Boolean> observableDownload(String source, String destination) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                final boolean result = downloadFile(source, destination);
                if (result) {
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("Download failed."));
                }
            }
        });
    }

    private boolean downloadFile(String source, String destination) {
        File file = new File(destination);
        if (file.exists()) {
            return true;
        }

        boolean result = false;
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            final int contentLength = connection.getContentLength();
            is = connection.getInputStream();
            os = new FileOutputStream(destination);
            byte[] data = new byte[1024 * 4];
            long total = 0;
            int count;
            while ((count = is.read(data)) != -1) {
                total += count;
                if (contentLength > 0) {
                    int percentage = (int) (total * 100 / contentLength);
                    mDownloadProgress.onNext(percentage);
                }
                os.write(data, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            mDownloadProgress.onError(e);
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }

    /**
     * 合并相关方法
     */
    @SuppressWarnings("unchecked")
    private void someCombineMethod() {

        // 有多个来源但是又只想有一个结果：多输入，单输出。RxJava的merge()方法将帮助你把两个甚至更多的Observables合并到他们发射的数据项里
        // 发射的数据被交叉合并到一个Observable里面。注意如果你同步的合并Observable，它们将连接在一起并且不会交叉
        ArrayList<String> ori = new ArrayList<>(Arrays.asList("I", "Love", "You"));
        ArrayList<Integer> copy = new ArrayList<>(Arrays.asList(1, 2, 3));
        // 注意错误时的toast消息，你可以认为每个Observable抛出的错误都将会打断合并。如果你需要避免这种情况，RxJava提供了mergeDelayError()，
        // 它能从一个Observable中继续发射数据即便是其中有一个抛出了错误。当所有的Observables都完成时，mergeDelayError()将会发射onError()
        final Observable<String> oriObservable = Observable.from(ori);
        final Observable<Integer> copyObservable = Observable.from(copy);
        Observable
                .merge(oriObservable, copyObservable) // .mergeDelayError(oriObservable, copyObservable)
                .subscribe(new Action1<Serializable>() {
                    @Override
                    public void call(Serializable serializable) {
                        KLog.e("merge(Observable.from(ori), Observable.from(copy))输出：" + serializable);
                    }
                });

        // 多从个Observables接收数据，处理它们，然后将它们合并成一个新的可观测序列来使用。RxJava有一个特殊的方法可以完成：
        // zip()合并两个或者多个Observables发射出的数据项，根据指定的函数Func*变换它们，并发射一个新值
        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS).take(10);
        Observable
                .zip(oriObservable, tictoc, (s, aLong) -> s + ":" + aLong)
                .subscribe(s -> {
                    KLog.e("zip()输出合成的数据：" + s);
                });

        // zip()和merge()方法作用在发射数据的范畴内，在决定如何操作值之前有些场景我们需要考虑时间的。RxJava的join()函数基于时间窗口将两个Observables发射的数据结合在一起
        // 为了简单起见，也有一个join()操作符作用于字符串然后简单的和发射的字符串连接成最终的字符串
        // join()需要的参数：
        // 第二个Observable和源Observable结合。
        // Func1参数：在指定的由时间窗口定义时间间隔内，源Observable发射的数据和从第二个Observable发射的数据相互配合返回的Observable。
        // Func1参数：在指定的由时间窗口定义时间间隔内，第二个Observable发射的数据和从源Observable发射的数据相互配合返回的Observable。
        // Func2参数：定义已发射的数据如何与新发射的数据项相结合。

        //产生0,5,10,15,20数列
        Observable<Long> observable1 = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .map(aLong -> aLong * 5)
                .take(5);

        //产生9,18,27,36,45数列
        Observable<Long> observable2 = Observable.interval(500, 1000, TimeUnit.MILLISECONDS)
                .map(aLong -> (aLong + 1) * 9)
                .take(5);

        observable1.join(
                observable2,
                aLong -> {
                    //使Observable延迟600毫秒执行
                    return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
                },
                aLong -> {
                    //使Observable延迟600毫秒执行
                    return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
                }, (aLong, aLong2) -> aLong + ";" + aLong2)
                .subscribe(aLong -> {
                    KLog.e("join输出：" + aLong);
                });

        // zip()作用于最近未打包的两个Observables。相反，combineLatest()作用于最近发射的数据项：如果Observable1发射了A并且Observable2发射了B和C，combineLatest()将会分组处理AB和AC
        // combineLatest()函数接受二到九个Observable作为参数，如果有需要的话或者单个Observables列表作为参数
        Observable
                .combineLatest(oriObservable, tictoc, (s, aLong) -> s + ":" + aLong)
                .subscribe(s -> {
                    KLog.e("combineLatest()输出合成的数据：" + s);
                });

        // 在将来还有一些zip()满足不了的场景。如复杂的架构，或者是仅仅为了个人爱好，你可以使用And/Then/When解决方案。它们在RxJava的joins包下，使用Pattern和Plan作为中介，将发射的数据集合并到一起
        // 被废弃了，JoinObservable.from(observableApp).and(tictoc);

        // RxJava的switch()，正如定义的，将一个发射多个Observables的Observable转换成另一个单独的Observable，后者发射那些Observables最近发射的数据项

        // RxJava的startWith()是concat()的对应部分。正如concat()向发射数据的Observable追加数据那样，在Observable开始发射他们的数据之前， startWith()通过传递一个参数来先发射一个数据序列
        oriObservable
                .startWith("Chinese")
                .subscribe(s -> {
                    KLog.e("startWith(\"Chinese\")输出：" + s);
                });
    }

    /**
     * 变换相关方法
     */
    private void someTansformMethod() {

        // RxJava的map函数接收一个指定的Func对象然后将它应用到每一个由Observable发射的值上
        Observable
                .just(1)
                .map(integer -> integer * 10)
                .subscribe(i -> {
                    KLog.e("map(integer -> integer * 10): " + i);
                });

        // 发射一个数据序列，这些数据本身也可以发射Observable。RxJava的flatMap()函数提供一种铺平序列的方式，然后合并这些Observables发射的数据，最后将合并后的结果作为最终的Observable
        // 当我们在处理可能有大量的Observables时，重要是记住任何一个Observables发生错误的情况，flatMap()将会触发它自己的onError()函数并放弃整个链。
        // 重要的一点提示是关于合并部分：它允许交叉。正如上图所示，这意味着flatMap()不能够保证在最终生成的Observable中源Observables确切的发射顺序。
        final Observable<List<Integer>> objectObservable = Observable.create(subscriber -> {
            subscriber.onNext(Arrays.asList(1, 2, 3, 4, 5));
        });
        objectObservable
                .flatMap(integers -> {
                    for (int i = 0; i < integers.size(); i++) {
                        integers.set(i, integers.get(i) * 10);
                    }
                    return Observable.from(integers);
                })
                .subscribe(i -> KLog.e("flatMap后输出：" + i));

        // RxJava的concatMap()函数解决了flatMap()的交叉问题，提供了一种能够把发射的值连续在一起的铺平函数，而不是合并它们，可以保证发射顺序

        // 作为*map家族的一员，flatMapInterable()和flatMap()很像。仅有的本质不同是它将源数据两两结成对并生成Iterable，而不是原始数据项和生成的Observables

        // switchMap()和flatMap()很像，除了一点：每当源Observable发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。

        // RxJava的scan()函数可以看做是一个累加器函数。scan()函数对原始Observable发射的每一项数据都应用一个函数，计算出函数的结果值，并将该值填充回可观测序列，等待和下一次发射的数据一起使用
        Observable.just(1, 2, 3, 4, 5)
                .scan((integer, integer2) -> {
                    KLog.e("scan()合并：" + integer + "+" + integer2 + "=" + (integer + integer2));
                    return integer + integer2;
                })
                .subscribe(integer -> {
                    KLog.e("scan()输出值：" + integer);
                });

        // RxJava中的buffer()函数将源Observable变换一个新的Observable，这个新的Observable每次发射一组列表值而不是一个一个发射
        final Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        just
                //每skip(3)项数据，然后又用count(2)项数据填充缓冲区
                .buffer(2, 3)
                .subscribe(integers -> {
                    String s = "";
                    for (int i : integers) {
                        s += i + ",";
                    }
                    KLog.e("buffer(2,3)输出：" + s);
                });
        // buffer()带一个timespan的参数，会创建一个每隔timespan时间段就会发射一个列表的Observable
        Observable
                .interval(2, TimeUnit.SECONDS)
                .buffer(4, TimeUnit.SECONDS, 2)
                .subscribe(longs -> {
                    String s = "";
                    for (long i : longs) {
                        s += i + ",";
                    }
                    KLog.e("buffer(4, TimeUnit.SECONDS, 2)输出：" + s);
                });

        // RxJava的window()函数和buffer()很像，但是它发射的是Observable而不是列表。
        // Observables中的每一个都发射原始Observable数据的一个子集，数量由count指定,最后发射一个onCompleted()结束。正如buffer()一样,window()也有一个skip变体

        // RxJava的cast()函数是本章中最后一个操作符。它是map()操作符的特殊版本。它将源Observable中的每一项数据都转换为新的类型，把它变成了不同的Class。
        // cast操作符类似于map操作符，不同的地方在于map操作符可以通过自定义规则，把一个值A1变成另一个值A2，A1和A2的类型可以一样也可以不一样；
        // 而cast操作符主要是做类型转换的，传入参数为类型class，如果源Observable产生的结果不能转成指定的class，则会抛出ClassCastException运行时异常
        just.cast(Integer.class)
                .subscribe(s -> {
                    KLog.e("cast(String.class)输出：" + s);
                });

    }

    /**
     * 过滤相关方法
     */
    private void someFilterMethod() {
        // 创建一个新的Observable并且应用defer()
        Observable<Integer> deferred = Observable.defer(this::getInt);
        // 一旦我们订阅了，create()方法就会被调用
        deferred.subscribe(i -> {
            KLog.e("deferred输出：" + i);
        });

        // range()函数用两个数字作为参数：第一个是起始点，第二个是我们想发射数字的个数
        Observable
                .range(10, 3)
                .subscribe(i -> {
                    KLog.e("range(10, 3)输出：" + i);
                });

        // 一个指定两次发射的时间间隔，另一个是用到的时间单位
        // 每隔3秒发送一个“I say N(N从0开始算起) ”，一直跑下去直到调用unsubscribe()
        final Subscription stopMePlease = Observable
                .interval(3, TimeUnit.SECONDS)
                .subscribe(i -> {
                    KLog.e("I say " + i);
                });

        // 一段时间之后才发送的Observable
        // 十秒后发送0，然后就完成了
        Observable.timer(10, TimeUnit.SECONDS)
                .subscribe(i -> {
                    KLog.e("取消stopMePlease的订阅;同时发送值" + i);
                    stopMePlease.unsubscribe();
                });

        // take()函数用整数N来作为一个参数，从原始的序列中发射前N个元素
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
                .take(5)
                .subscribe(integer -> {
                    KLog.e("take(5)输出前五个：" + integer);
                });

        // 想要最后N个元素，我们只需使用takeLast()
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
                .takeLast(5)
                .subscribe(integer -> {
                    KLog.e("takeLast(5)输出后五个：" + integer);
                });

        // 对我们的序列使用distinct()函数去掉重复
        Observable
                .from(Arrays.asList(1, 2, 3))
                .repeat(3)
                        // 去除重复值
                .distinct()
                .subscribe(integer -> {
                    KLog.e("去除重复值后输出：" + integer);
                });

        // 在一个可观测序列发射一个不同于之前的一个新值
        Observable
                .from(Arrays.asList(1, 1, 1, 1, 2, 3))
                        // 忽略重复的值并且在值改变时才得到通知
                .distinctUntilChanged()
                .subscribe(integer -> {
                    KLog.e("忽略重复的值并且在值改变时才输出：" + integer);
                });

        // first()方法和last()方法很容易弄明白。它们从Observable中只发射第一个元素或者最后一个元素。
        // 这两个都可以传Func1作为参数，：一个可以确定我们感兴趣的第一个或者最后一个的谓词
        // 与first()和last()相似的变量有：firstOrDefault()和lastOrDefault().这两个函数当可观测序列完成时不再发射任何值时用得上。
        // 在这种场景下，如果Observable不再发射任何值时我们可以指定发射一个默认的值
        // 发射第一个值
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
                .first()
                .subscribe(integer -> {
                    KLog.e("first()输出第一个值：" + integer);
                });

        // skip()和skipLast()函数与take()和takeLast()相对应。它们用整数N作参数，从本质上来说，
        // 它们不让Observable发射前N个或者后N个值。如果我们知道一个序列以没有太多用的“可控”元素开头或结尾时我们可以使用它。
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
                        // 跳过前面五个值
                .skip(5)
                .subscribe(integer -> {
                    KLog.e("skip(5)跳过前面五个值再输出：" + integer);
                });

        // elementAt()函数仅从一个序列中发射第n个元素然后就完成了
        // 想查找第五个元素但是可观测序列只有三个元素可供发射时该怎么办？我们可以使用elementAtOrDefault()
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
                        // 只输出第六个值
                .elementAt(5)
                .subscribe(integer -> {
                    KLog.e("elementAt(5)只输出第六个值：" + integer);
                });

        // 在Observable后面加一个sample()，我们将创建一个新的可观测序列，它将在一个指定的时间间隔里由Observable发射最近一次的数值
        // 每隔3秒发送一个值，一直跑下去直到调用unsubscribe()
        final Observable<Long> sample = Observable.interval(3, TimeUnit.SECONDS);
        // 每隔5秒就会发射最后一个值
        sample.sample(5, TimeUnit.SECONDS)
                .subscribe(integer -> {
                    KLog.e("sample(5,TimeUnit.SECONDS)隔五秒输出最后的一个值：" + integer);
                });
        // 每隔5秒发射第一个元素而不是最近的一个元素
        sample.throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(integer -> {
                    KLog.e("throttleFirst(5, TimeUnit.SECONDS)隔五秒输出第一个值：" + integer);
                });

        // timeout()为一个Observable的限时的副本。如果在指定的时间间隔内Observable不发射值的话，它监听的原始的Observable时就会触发onError()函数
        sample.timeout(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // sample隔三秒发送一次值，超过设定的时间间隔了，触发onError()
                        KLog.e("timeout(2, TimeUnit.SECONDS)超时：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        KLog.e("timeout(2, TimeUnit.SECONDS)输出值：" + aLong);
                    }
                });

        // debounce()函数过滤掉由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个
        sample.debounce((long) 1.5, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    KLog.e("debounce((long) 1.5, TimeUnit.SECONDS)过滤发射的速率过快的数据，发射最后的那个：" + aLong);
                });
    }

    // 声明一个Observable但是你又想推迟这个Observable的创建直到观察者订阅
    private Observable<Integer> getInt() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                KLog.e("GetInt");
                subscriber.onNext(110);
                subscriber.onCompleted();
            }
        });
    }

    private void initList(boolean orderByDate) {

        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> infos = getPackageManager().queryIntentActivities(intent, 0);

        if (orderByDate) {
            // 最近更新日期来排序我们的App时该怎么办？RxJava提供了一个有用的函数从列表中按照指定的规则：groupBy()来分组元素
            // 创建了一个新的Observable，groupedItems，它将会发射一个带有GroupedObservable的序列。GroupedObservable是一个特殊的Observable，
            // 它源自一个分组的key。在这个例子中，key就是String，代表的意思是Month/Year格式化的最近更新日期
            Observable<GroupedObservable<String, ResolveInfo>> groupedItems = Observable.from(infos)
                    .groupBy(appInfo -> {
                        String date = null;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            date = sdf.format(new Date(getPackageManager().getPackageInfo(appInfo.activityInfo.packageName, 0).lastUpdateTime));
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        return date;
                    });

            // 创建了几个发射AppInfo数据的Observable，用来填充我们的列表。我们想保留字母排序和分组排序。我们将创建一个新的Observable将所有的联系起来，像往常一样然后订阅它
            mSubscription = Observable
                    .concat(groupedItems)
                    .filter(resolveInfo -> resolveInfo != null
                            && !resolveInfo.loadLabel(getPackageManager()).toString().contains("Leaks"))
                    .map(info -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(getPackageManager());
                        DiskCacheUtils.getInstance(RxJavaActivity.this)
                                .storeBitmap(RxJavaActivity.this, icon.getBitmap(), info.activityInfo.name);

                        long lastUpdateTime = 0;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            lastUpdateTime = getPackageManager().getPackageInfo(info.activityInfo.packageName, 0).lastUpdateTime;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        return new AppInfo(lastUpdateTime,
                                info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + /*mSdf.format(new Date())*/sdf.format(lastUpdateTime),
                                info.activityInfo.name);
                    })
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(mRefreshRecyclerView::showRefreshCircleView)
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
        } else {
            Observable
                    .from(infos)
                            // 过滤空的对象和名称含有Leaks的应用
                            // filter()函数最常用的用法之一时过滤null对象
                    .filter(resolveInfo -> resolveInfo != null
                            && !resolveInfo.loadLabel(getPackageManager()).toString().contains("Leaks"))
                            // 重复两次
                            //.repeat(2)
                    .map(info -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(getPackageManager());
                        DiskCacheUtils.getInstance(RxJavaActivity.this)
                                .storeBitmap(RxJavaActivity.this, icon.getBitmap(), info.activityInfo.name);

                        long lastUpdateTime = 0;
                        try {
                            lastUpdateTime = getPackageManager().getPackageInfo(info.activityInfo.packageName, 0).lastUpdateTime;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        return new AppInfo(lastUpdateTime,
                                info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + mSdf.format(new Date()),
                                info.activityInfo.name);
                    })
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(mRefreshRecyclerView::showRefreshCircleView)
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

    }

    private void refreshList() {

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
                try {
                    subscriber.onNext(
                            new AppInfo(
                                    getPackageManager().getPackageInfo(info.activityInfo.packageName, 0).lastUpdateTime,
                                    info.activityInfo.loadLabel(getPackageManager()).toString() + " : " + mSdf.format(new Date()),
                                    info.activityInfo.name));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
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
            Observable
                    .create(new Observable.OnSubscribe<Bitmap>() {
                        @Override
                        public void call(Subscriber<? super Bitmap> subscriber) {
                            // 异步操作，因为drawableToBitmap是io操作
                            subscriber.onNext(DiskCacheUtils.getInstance(RxJavaActivity.this).drawableToBitmap(mDatas.get(position).getIcon()));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(holder.imageView::setImageBitmap);
        }

        @Override
        protected int getCustomItemViewType(int position) {
            return 0;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(TAG, register1);
        RxBus.get().unregister(TAG, register);
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        if (mDownLoadSubscription != null && !mDownLoadSubscription.isUnsubscribed())
            mDownLoadSubscription.unsubscribe();
        if (mPlaySubscription != null && !mPlaySubscription.isUnsubscribed())
            mPlaySubscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rxjava, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ifeng:
                startActivity(new Intent(this, NbaActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
