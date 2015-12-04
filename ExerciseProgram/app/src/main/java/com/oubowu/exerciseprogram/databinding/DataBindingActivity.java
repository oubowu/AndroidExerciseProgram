package com.oubowu.exerciseprogram.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bumptech.glide.Glide;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.bean.BaiduBaike;
import com.oubowu.exerciseprogram.databinding.model.BaiduBaikeResult;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataBindingActivity extends AppCompatActivity {

    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_chinese_name)
    TextView tvChineseName;
    @Bind(R.id.tv_english_name)
    TextView tvEnglishName;
    @Bind(R.id.tv_abstract)
    TextView tvAbstract;
    @Bind(R.id.bt_search)
    Button btSearch;
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    private BaiduBinding binding;
    private BaiduBaikeResult result;

    @OnClick(R.id.bt_search)
    void search() {
        if (!TextUtils.isEmpty(etKeyword.getText().toString())) {
            searchKeyWord(etKeyword.getText().toString());
        } else {
            Toast.makeText(this, "请输入搜索词", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
//        result = new BaiduBaikeResult("陈柏霖", "Chen Boling", "简介：\n大仁哥");
//        binding.setBaiduBaikeResult(result);

        ButterKnife.bind(this);

    }

    protected void initView() {

    }

    protected void initData() {
        // http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%E9%99%88%E6%9F%8F%E9%9C%96&bk_length=600

    }

    private void searchKeyWord(String keyword) {
        /*new OkHttpRequest.Builder()
                .url("http://baike.baidu.com/api/openapi/BaikeLemmaCardApi")
                .addParams("scope", "103")
                .addParams("format", "json")
                .addParams("appid", "379020")
                .addParams("bk_key", keyword)
                .addParams("bk_length", "600")
                .get(new ResultCallback<BaiduBaike>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(DataBindingActivity.this, "搜索出错!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(BaiduBaike response) {
                        try {
                            final ViewGroup.LayoutParams lp = ivHead.getLayoutParams();
                            lp.height = response.imageHeight * lp.width / response.imageWidth;
                            ivHead.setLayoutParams(lp);
                            Glide.with(DataBindingActivity.this)
                                    .load(response.image)
                                    .crossFade()
                                    .error(R.drawable.__leak_canary_icon)
                                    .into(ivHead);

                            result = new BaiduBaikeResult(
                                    response.card.get(0).name + response.card.get(0).value,
                                    response.card.get(1).name + response.card.get(1).value,
                                    "简介:\n" + response.abstractX);
                            binding.setBaiduBaikeResult(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/
        new OkHttpRequest.Builder()
                .url("http://baike.baidu.com/api/openapi/BaikeLemmaCardApi")
                .addParams("scope", "103")
                .addParams("format", "json")
                .addParams("appid", "379020")
                .addParams("bk_key", keyword)
                .addParams("bk_length", "600")
                .get(new ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(DataBindingActivity.this, "搜索出错!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        KLog.e(response);
                        try {
                            BaiduBaike baike = LoganSquare.parse(response, BaiduBaike.class);

                            if (baike.imageHeight != 0 && baike.imageWidth != 0) {
                                final ViewGroup.LayoutParams lp = ivHead.getLayoutParams();
                                lp.height = baike.imageHeight * lp.width / baike.imageWidth;
                                ivHead.setLayoutParams(lp);
                                Glide.with(DataBindingActivity.this)
                                        .load(baike.image)
                                        .crossFade()
                                        .error(R.mipmap.baidubaike)
                                        .into(ivHead);
                            }

                            Pattern p = Pattern.compile("(<*?>)");
                            Matcher m = p.matcher(baike.card.get(0).value.get(0));
                            if (m.find()) {
                                KLog.e(m.group());
                            }
                            result = new BaiduBaikeResult(
                                    baike.card.get(0).name + ": " + Html.fromHtml(baike.card.get(0).value.get(0)),
                                    baike.card.get(1).name + ": " + Html.fromHtml(baike.card.get(1).value.get(0)),
                                    "简介:\n" + baike.abstractX);
                            binding.setBaiduBaikeResult(result);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DataBindingActivity.this, "搜索出错!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
