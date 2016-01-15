package com.oubowu.exerciseprogram.rxjava.rxbus;

import android.support.annotation.NonNull;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * 类名： RxBus
 * 作者: oubowu
 * 时间： 2016/1/15 14:35
 * 功能：这是作为中间人调用的对象
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RxBus {

    private volatile static RxBus instance;

    private RxBus() {
    }

    public static RxBus get() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null)
                    instance = new RxBus();
            }
        }
        return instance;
    }

    /**
     * 存储某个标签的Subject集合
     */
    private ConcurrentMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    /**
     * 注册事件
     *
     * @param tag   标签
     * @param clazz 类
     * @param <T>   类型
     * @return 被观察者
     */
    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }

        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        KLog.e("{register}subjectMapper: " + subjectMapper);
        return subject;
    }

    /**
     * 取消注册事件
     *
     * @param tag        标签
     * @param observable 被观察者
     */
    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        final List<Subject> subjectList = subjectMapper.get(tag);
        if (null != subjectList) {
            subjectList.remove((Subject) observable);
            if (subjectList.isEmpty()) {
                // 集合数据为0的时候移map除掉tag
                subjectMapper.remove(tag);
            }
        }
        KLog.e("{unregister}subjectMapper: " + subjectMapper);
    }

    /**
     * 发送事件
     *
     * @param tag     标签
     * @param content 发送的内容
     */
    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content) {
        final List<Subject> subjectList = subjectMapper.get(tag);
        if (null != subjectList && !subjectList.isEmpty()) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    /*private static volatile Subject<Object, Object> bus;

    private static Subject<Object, Object> getInstance() {
        if (bus == null) {
            synchronized (RxBus.class) {
                if (bus == null) {
                    bus = new SerializedSubject<>(PublishSubject.create());
                }
            }
        }
        return bus;
    }

    public static void send(Object obj) {
        getInstance().onNext(obj);
//        bus.onNext(obj);
    }

    public static Observable<Object> toObservable() {
        return getInstance();
//        return bus;
    }*/

}
