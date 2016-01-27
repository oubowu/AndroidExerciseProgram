#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "com_oubowu_exerciseprogram_jni_CheckUtil.h"
#include "./utils/android_log_util.h"
#include "./utils/md5.h"

JNIEXPORT jstring JNICALL get_encode_psw(JNIEnv *env, jobject obj, jstring psw) {

    /*// 声明局部量
    char key[KEY_SIZE] = {0};
    //清空数组内存
    memset(key, 0, sizeof(key));

    char temp[KEY_NAME_SIZE] = {0};

    //将java传入的name转换为本地utf的char*
    const char *pName = (*env)->GetStringUTFChars(env, psw, NULL);

    if (NULL != pName) {
        //将传进来的name拷贝到临时空间
        strcpy(temp, pName);
        strcpy(key, generatePws(temp));
        // java的psw对象不需要再使用，通知虚拟机回收psw
        (*env)->ReleaseStringUTFChars(env, psw, pName);
    }

    return (*env)->NewStringUTF(env, key);*/

    LOGE("get_encode_psw");

    char *szText = (char *) (*env)->GetStringUTFChars(env, psw, 0);

    MD5_CTX context = {0};
    MD5Init(&context);
    MD5Update(&context, szText, strlen(szText));
    unsigned char dest[16] = {0};
    MD5Final(dest, &context);
    (*env)->ReleaseStringUTFChars(env, psw, szText);

    int i = 0;
    char szMd5[32] = {0};
    for (i = 0; i < 16; i++) {
        /*原型
        int sprintf( char *buffer, const char *format, [ argument] … );
        参数列表
        buffer：char型指针，指向将要写入的字符串的缓冲区。
        format：格式化字符串。
        [argument]...：可选参数，可以是任何类型的数据。
        返回值：字符串长度（strlen）*/
        // %02x 格式控制: 以十六进制输出,2为指定的输出字段的宽度.如果位数小于2,则左端补0
        sprintf(szMd5, "%s%02x", szMd5, dest[i]);
    }
    return (*env)->NewStringUTF(env, szMd5);

}

//自定义函数，为某一个类注册本地方法，调运JNI注册方法
static int registerNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *gMethods,
                                 int numMethods) {
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    //JNI函数，参见系列教程2
    if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

static JNINativeMethod methods[] = {
        {"getEncodePsw", "(Ljava/lang/String;)Ljava/lang/String;", (void *) get_encode_psw},
        //这里可以有很多其他映射函数
};

//自定义函数
static int registerNatives(JNIEnv *env) {
    const char *kClassName = "com/oubowu/exerciseprogram/jni/CheckUtil";//指定要注册的类
    return registerNativeMethods(env, kClassName, methods, sizeof(methods) / sizeof(methods[0]));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGE("执行JNI_OnLoad");
    JNIEnv *env = NULL;
    jint result = -1;

    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }

    assert(env != NULL);

    //动态注册，自定义函数
    if (!registerNatives(env)) {
        return -1;
    }

    return JNI_VERSION_1_4;

}