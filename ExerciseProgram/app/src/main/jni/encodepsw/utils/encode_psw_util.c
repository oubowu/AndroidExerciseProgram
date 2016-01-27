#include "encode_psw_util.h"
#include <string.h>
#include "./android_log_util.h"

char *generatePws(char *psw) {

    // 判断参数是否有效
    if (NULL == psw || strlen(psw) > KEY_NAME_SIZE) {
        LOGE("密码不能大于16位啊，亲！！！");
        return NULL;
    }

    //声明局部变量
    int index = 0;
    char temp[KEY_SIZE] = {"\0"};
    //清空数组内存
    memset(temp, 0, sizeof(temp));
    //将传进来的name拷贝到临时空间
    strcpy(temp, psw);
    //进行通过name转化生成key的逻辑，这里是模拟测试，实际算法比这复杂
    for (index = 0; index < KEY_SIZE - 1; index++) {
        temp[index] += 10;
        LOGE("---------------temp[%d]=%c", index, temp[index]);
    }

    return temp;

}
