package com.oubowu.exerciseprogram.jsonparse;

import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.bean.Express;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import java.io.IOException;

import butterknife.Bind;

public class JsonParseActivity extends BaseActivity {

    @Bind(R.id.tv_info)
    TextView infoText;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_json_parse;
    }

    @Override
    protected void initView() {

    }

    /*解析&序列化
            将JSON对象解析为实体类
    // 支持直接从InputStream解析
    InputStream is = ...;
    Image imageFromInputStream = LoganSquare.parse(is, Image.class);

    // 从Json字符串解析
    String jsonString = ...;
    Image imageFromString = LoganSquare.parse(jsonString, Image.class);
    实体类序列化为JSON对象
    // Serialize it to an OutputStream
    OutputStream os = ...;
    LoganSquare.serialize(image, os);

    // Serialize it to a String
    String jsonString = LoganSquare.serialize(image);*/

    @Override
    protected void initData() {
        // http://www.kuaidi100.com/query?type=zhongtong&postid=372067641846
        new OkHttpRequest.Builder()
                .url("http://www.kuaidi100.com/query")
                .addParams("type", "zhongtong")
                .addParams("postid", "372067641846")
                .tag(this)
                .post(new ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        KLog.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        KLog.e(response);
                        try {
                            Express info = LoganSquare.parse(response, Express.class);
                            infoText.setText("解析后的信息：\n" + info.toString());

                            String serialize = LoganSquare.serialize(info);
                            KLog.e("序列化后信息: " + serialize);

                        } catch (Exception e) {
                            e.printStackTrace();
                            infoText.setText(response);
                        }
                    }
                });
    }

}
