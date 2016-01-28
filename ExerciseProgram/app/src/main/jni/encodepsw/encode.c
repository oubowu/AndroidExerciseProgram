#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "com_oubowu_exerciseprogram_jni_CheckUtil.h"
#include "./utils/android_log_util.h"
#include "./utils/md5.h"
#include "./utils/sha1.h"

JNIEXPORT jstring JNICALL get_encode_psw(JNIEnv *env, jobject obj, jstring psw) {

    // 先用SHA1算法加密
    SHA1Context sha;
    char *pswChar = (char *) (*env)->GetStringUTFChars(env, psw, 0);

    SHA1Reset(&sha);
    SHA1Input(&sha, (const unsigned char *) pswChar, strlen(pswChar));
    char szSha1[32] = {0};
    if (!SHA1Result(&sha)) {
        fprintf(stderr, "ERROR-- could not compute message digest\n");
    } else {
        int n;
        for (n = 0; n < 5; n++) {
            sprintf(szSha1, "%s%02x", szSha1, sha.Message_Digest[n]);
        }
    }

    // SHA1加密后拼接字符串
    const char *sailvanStr = "oubowu";
    strcat(szSha1, sailvanStr);

    // 再用MD5加密
    MD5_CTX context = {0};
    MD5Init(&context);
    MD5Update(&context, szSha1, strlen(szSha1));
    unsigned char dest[16] = {0};
    MD5Final(dest, &context);

    int i = 0;
    char szMd5[32] = {0};
    for (i = 0; i < 16; i++) {
        sprintf(szMd5, "%s%02x", szMd5, dest[i]);
    }

    // ReleaseStringUTFChars 通知虚拟机平台相关代码无需再访问utf。utf参数是一个指针，可利用GetStringUTFChars()从string获得
    (*env)->ReleaseStringUTFChars(env, psw, pswChar);

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