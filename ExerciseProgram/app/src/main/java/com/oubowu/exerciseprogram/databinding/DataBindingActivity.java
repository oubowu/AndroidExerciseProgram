package com.oubowu.exerciseprogram.databinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.oubowu.exerciseprogram.rxjava.nbabymvp.NbaActivity;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private BaiduBinding binding;

    private BaiduBaikeResult result;

    /*@OnClick(R.id.bt_search)
    void search() {
        if (!TextUtils.isEmpty(etKeyword.getText().toString())) {
            searchKeyWord(etKeyword.getText().toString());
        } else {
            Toast.makeText(this, "请输入搜索词", Toast.LENGTH_SHORT).show();
        }
    }*/

    // databing提供点击事件
    public void search(View view) {
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
        ButterKnife.bind(this);
        initView();
        initData();

    }

    protected void initView() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("ObservableBinding");
        }

    }

    protected void initData() {
        // http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%E9%99%88%E6%9F%8F%E9%9C%96&bk_length=600

        // ObservableBinding的方式，绑定的变量result改变随之改变页面，与普通的bean不同的是需要继承BaseObservable,
        // getXXX需添加@Bindable注解，setXXX最后调用notifyPropertyChanged(BR.xxx);
        result = new BaiduBaikeResult("", "", "");
        binding.setBaiduBaikeResult(result);
        binding.setError(false);
        binding.setStr("搜索");
        // 点击事件注册
        binding.setSearchClick(this);
    }

    private void searchKeyWord(String keyword) {
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

                            // 最基本的绑定方式，每次都去设置一遍数据，不如ObservableBinding灵活
                            /*result = new BaiduBaikeResult(
                                    baike.card.get(0).name + ": " + Html.fromHtml(baike.card.get(0).value.get(0)),
                                    baike.card.get(1).name + ": " + Html.fromHtml(baike.card.get(1).value.get(0)),
                                    "简介:\n" + baike.abstractX);*/
                            // binding.setBaiduBaikeResult(result);

                            result.setChineseName(baike.card.get(0).name + ": " + Html.fromHtml(baike.card.get(0).value.get(0)));
                            result.setEnglishName(baike.card.get(1).name + ": " + Html.fromHtml(baike.card.get(1).value.get(0)));
                            result.setAbstractX("简介:\n" + baike.abstractX);

                            binding.setTime(new Date());

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DataBindingActivity.this, "搜索出错!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_binding, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recyclerview_binding:
                startActivity(new Intent(this, NbaActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
