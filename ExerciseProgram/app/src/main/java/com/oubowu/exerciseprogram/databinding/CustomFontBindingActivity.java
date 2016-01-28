package com.oubowu.exerciseprogram.databinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomFontBindingActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    protected void initView() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("CustomFontBinding");
        }
    }

    protected void initData() {
        // http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%E9%99%88%E6%9F%8F%E9%9C%96&bk_length=600

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

                break;
            case R.id.action_customfont_binding:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
