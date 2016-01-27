//
// Created by swsc on 2016/1/27.
//

// 防止头文件重复引用
#ifndef _ANDROID_LOG_UTIL_H // 如果不存在xxx.h
#define _ANDROID_LOG_UTIL_H // 就引入xxx.h

#include <android/log.h>

#define IS_DEBUG // 定义当前为debug模式

#ifdef IS_DEBUG // 当前为debug模式的话，定义log语句

#define LOG_TAG "Jni加密实例"

// __VA_ARGS__ 是一个可变参数的宏(gcc支持)。实现思想就是宏定义中参数列表的最后一个参数为省略号（也就是三个点）。
// 这样预定义宏_ _VA_ARGS_ _就可以被用在替换部分中，替换省略号所代表的字符串。加##用来支持0个可变参数的情况。

#define LOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__))

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__))

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__))

#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__))

#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__))

#else // 当前不为debug模式的话，方法为空？？？

#define LOGV(LOG_TAG, ...) NULL

#define LOGD(LOG_TAG, ...) NULL

#define LOGI(LOG_TAG, ...) NULL

#define LOGW(LOG_TAG, ...) NULL

#define LOGE(LOG_TAG, ...) NULL

#endif

#endif //EXERCISEPROGRAM_ANDROID_LOG_UTIL_H // 否则不需要引入xxx.h
