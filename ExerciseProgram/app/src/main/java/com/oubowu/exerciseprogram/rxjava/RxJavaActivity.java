package com.oubowu.exerciseprogram.rxjava;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {

    }

    /*************************
     * 深入浅出RxJava（1）：基础篇
     *****************************************/
    private void test1() {
        // 这里定义的Observable对象仅仅发出一个Hello World字符串，然后就结束了。
        // 接着我们创建一个Subscriber来处理Observable对象发出的字符串。
        /*Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello, world!");
                        subscriber.onCompleted();
                    }
                }
        );*/
        // lambda简化
        Observable<String> myObservable = Observable.create(
                subscriber -> {
                    subscriber.onNext("Hello, world!");
                    subscriber.onCompleted();
                }
        );
        // 这里subscriber仅仅就是打印observable发出的字符串。通过subscribe函数就可以将我们定义的
        // myObservable对象和mySubscriber对象关联起来，这样就完成了subscriber对observable的订阅。
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                KLog.e("完成");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("发生错误");
            }

            @Override
            public void onNext(String s) {
                KLog.e("输出" + s);
            }
        };

        // 一旦mySubscriber订阅了myObservable，myObservable就是调用
        // mySubscriber对象的onNext和onComplete方法，mySubscriber就会打印出Hello World！
        myObservable.subscribe(mySubscriber);
    }

    private void test2() {
        /*// RxJava内置了很多简化创建Observable对象的函数，比如Observable.just
        // 就是用来创建只发出一个事件就结束的Observable对象，上面创建Observable对象
        // 的代码可以简化为一行
        Observable<String> myObservable = Observable.just("Hello China!");
        // 其实并不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类。
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                KLog.e("输出" + s);
            }
        };
        // subscribe方法有一个重载版本，接受三个Action1类型的参数，分别对应OnNext，OnComplete， OnError函数。
        // 并不关心onError和onComplete，所以只需要第一个参数就可以
        myObservable.subscribe(onNextAction);*/

        // 最终简化成
        Observable.just("Hello China!").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                KLog.e("输出" + s);
            }
        });

        // 使用Lambda
        Observable.just("Hello China!").subscribe(s -> KLog.e(s));

    }

    private void test3() {
        /*操作符就是为了解决对Observable对象的变换的问题，操作符用于
        在Observable和最终的Subscriber之间修改Observable发出的事件。
        RxJava提供了很多很有用的操作符。
        比如map操作符，就是用来把把一个事件转换为另一个事件的。*/
        /*Observable.just("Hello people!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "-From Ou";
                    }
                })
                .subscribe(s -> KLog.e(s));*/

        // 使用lambda可以简化为
        Observable.just("Hello people!")
                // map把一个事件转换为另一个事件
                .map(s -> s + "-From Ou")
                .subscribe(s -> KLog.e(s));
        // map操作符返回一个Observable对象，这样就可以实现链式调用，
        // 在一个Observable对象上多次使用map操作符，最终将最简洁的
        // 数据传递给Subscriber对象。
    }

    private void test4() {
        /*map操作符更有趣的一点是它不必返回Observable对象返回的类型，
        你可以使用map操作符返回一个发出新的数据类型的observable对象。
        比如上面的例子中，subscriber并不关心返回的字符串，而是想要字符串的hash值*/
        Observable.just("Hello google!")
                // 第一个值是传入的类型，第二个是希望返回的类型
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(integer -> KLog.e(integer));

        // lambda简化
        Observable.just("Hello google!")
                .map(s -> s.hashCode())
                .subscribe(i -> KLog.e(Integer.toString(i)));

        // Subscriber做的事情越少越好，我们再增加一个map操作符
        Observable.just("Hello google!")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> KLog.e(s));
    }

    /*不服？

    是不是觉得我们的例子太简单，不足以说服你？你需要明白下面的两点:
    1.Observable和Subscriber可以做任何事情
      Observable可以是一个数据库查询，Subscriber用来显示查询结果；Observable可以是屏幕上的点击事件，
      Subscriber用来响应点击事件；Observable可以是一个网络请求，Subscriber用来显示请求结果。
    2.Observable和Subscriber是独立于中间的变换过程的。
      在Observable和Subscriber中间可以增减任何数量的map。整个系统是高度可组合的，操作数据是一个很简单的过程。*/

    /*************************
     * 深入浅出RxJava（2）：操作符
     *****************************************/
    private void test5() {

        // 1.下面的代码使我们丧失了变化数据流的能力。一旦我们想要更改每一个URL，
        // 只能在Subscriber中来做。我们竟然没有使用如此酷的map()操作符！！！
        query("").subscribe(urls -> {
            for (String url : urls) {
                KLog.e(url);
            }
        });

        // Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber
        /*ArrayList<String> list = new ArrayList<>(Arrays.asList("url1", "url2", "url3"));
        Observable.from(list)
                .subscribe(url -> KLog.e(url));*/

        // 2.使用Observable.from()
        // Observable.from接收urls作为输入，再每次输出一个元素到Subscriber
        // 多个嵌套的subscription不仅看起来很丑，难以修改
        query("").subscribe(urls -> Observable.from(urls).map(url -> "使用Observable.from方法：" + url).subscribe(url -> KLog.e(url)));

        //  3.Observable.flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable
        //  Func1第一个参数是query("")的输出作为输入，第二个是将处理后的值输出到另外一个Observable
        /*query("").flatMap(new Func1<List<String>, Observable<?>>() {
            @Override
            public Observable<?> call(List<String> urls) {
                // Observable.from接收urls作为输入，再每次输出一个元素到Subscriber
                return Observable.from(urls);
            }
        }).subscribe(url -> KLog.e(url));*/
        // lambda简化
        query("").flatMap(urls -> Observable.from(urls)).map(url -> "使用flatMap方法：" + url).subscribe(url -> KLog.e(url));

        /*flatMap()是不是看起来很奇怪？为什么它要返回另外一个Observable呢？理解flatMap的关键点在于，
        flatMap输出的新的Observable正是我们在Subscriber想要接收的。现在Subscriber不再收到List<String>，
        而是收到一些列单个的字符串，就像Observable.from()的输出一样。*/
    }

    // 根据输入的字符串返回一个网站的url列表
    private Observable<List<String>> query(String text) {
        return Observable.create(subscribe -> {
            // Observable将查找到的url列表传给Subscriber
            subscribe.onNext(new ArrayList<>(Arrays.asList("url1", "url2", "url3")));
            subscribe.onCompleted();
        });
    }

    // 返回网站的标题，如果404了就返回null  
    private Observable<String> getTitle(String url) {
        return Observable.create(subscriber -> {
            if ("url3".equals(url)) {
                subscriber.onNext(null);
            } else {
                subscriber.onNext("此乃标题");
            }
            subscriber.onCompleted();
        });
    }

    private void test6() {

        /*query("")
                .flatMap(urls -> Observable.from(urls))
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String url) {
                        return getTitle(url);
                    }
                })
                .subscribe(title -> System.out.println(title));*/

        // 打印收到的每个网站的标题
        query("")
                // 先将搜索到的url集合一个一个输出
                .flatMap(urls -> Observable.from(urls))
                        // 将输入的url用来查找标题输出
                .flatMap(url -> getTitle(url))
                .map(title -> "搜索到的网页的标题是: " + title)
                .subscribe(title -> KLog.e(title));
        // 将两个API的调用组合到一个链式调用中了
        // 大家应该都应该知道同步所有的API调用，然后将所有API调用的回调结果组合成需要展示的数据是一件多么蛋疼的事情。
        // 这里我们成功的避免了callback hell（多层嵌套的回调，导致代码难以阅读维护）。现在所有的逻辑都包装成了这种简单的响应式调用。
    }

    private void test7() {
        // getTitle()返回null如果url不存在。我们不想输出”null”，
        // 那么我们可以从返回的title列表中过滤掉null值！
        query("")
                // 先将搜索到的url集合一个一个输出
                .flatMap(urls -> Observable.from(urls))
                        // 将输入的url用来查找标题输出
                .flatMap(url -> getTitle(url))
                        // 过滤null值
                .filter(url -> url != null)
                        // 只想要最多1个结果
                .take(1)
                        // 想在打印之前，把每个标题保存到磁盘
                        // doOnNext()允许我们在每次输出一个元素之前做一些额外的事情
                .doOnNext(title -> saveTitle(title))
                .map(title -> "搜索到的网页的标题是: " + title)
                .subscribe(title -> KLog.e(title));
        // filter()输出和输入相同的元素，并且会过滤掉那些不满足检查条件的。
    }

    private void saveTitle(String title) {
        KLog.e("将 " + title + " 保存到磁盘中");
    }

    /*RxJava包含了大量的操作符。操作符的数量是有点吓人，但是很值得你去挨个看一下，这样你可以知道有哪些操作符可以使用。
    弄懂这些操作符可能会花一些时间，但是一旦弄懂了，你就完全掌握了RxJava的威力。

    你甚至可以编写自定义的操作符！这篇blog不打算将自定义操作符，如果你想的话，清自行Google吧。*/

    /*感觉如何？

    好吧，你是一个怀疑主义者，并且还很难被说服，那为什么你要关心这些操作符呢？

    因为操作符可以让你对数据流做任何操作。

    将一系列的操作符链接起来就可以完成复杂的逻辑。代码被分解成一系列可以组合的片段。这就是响应式函数编程的魅力。
    用的越多，就会越多的改变你的编程思维。

    另外，RxJava也使我们处理数据的方式变得更简单。在最后一个例子里，我们调用了两个API，对API返回的数据进行了处理，
    然后保存到磁盘。但是我们的Subscriber并不知道这些，它只是认为自己在接收一个Observable<String>对象。良好的封装性也带来了编码的便利！

    在第三部分中，我将介绍RxJava的另外一些很酷的特性，比如错误处理和并发，这些特性并不会直接用来处理数据。*/

    /*************************
     * 深入浅出RxJava（3）：响应式的好处
     *****************************************/

    /*错误处理

    到目前为止，我们都没怎么介绍onComplete()和onError()函数。这两个函数用来通知订阅者，
    被观察的对象将停止发送数据以及为什么停止（成功的完成或者出错了）。*/
    private void test8() {
        Observable.just("Test error!!!")
                .map(s -> handleException(s))
                .map(s -> handleAnotherException(s))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        KLog.e("完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        KLog.e(s);
                    }
                });
    }

    private String handleAnotherException(String s) {
        if (s.contains("!!!")) {
            throw new RuntimeException("字符串发现!!!,发生异常");
        }
        return s;
    }

    private String handleException(String s) {
        if (s.contains("???")) {
            throw new RuntimeException("字符串发现???,发生异常");
        }
        return s;
    }

    @Override
    protected void initData() {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
        test8();
    }

}
