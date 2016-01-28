package com.oubowu.exerciseprogram.jni;

import android.widget.EditText;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.ToastUtil;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.OnClick;

public class JniActivity extends BaseActivity {

    @Bind(R.id.et_psw)
    EditText mPswEditText;

    @OnClick(R.id.bt_check)
    void checkPsw() {
        CheckUtil checkUtil = new CheckUtil();
        final boolean bb = EncodeUtil.getEncoderPass(mPswEditText.getText().toString()).equals(checkUtil.getEncodePsw(mPswEditText.getText().toString()));
        ToastUtil.showShort(this, "JNI加密的密码与JAVA加密的密码匹配吗？ " + bb);
        KLog.e(EncodeUtil.getEncoderPass(mPswEditText.getText().toString()) + "\n" + checkUtil.getEncodePsw(mPswEditText.getText().toString()));
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_jni;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
