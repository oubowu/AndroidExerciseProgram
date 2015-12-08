package com.oubowu.exerciseprogram.tailview;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;

import butterknife.Bind;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_rate_app)
    TextView tvRateApp;
    @Bind(R.id.ttv_language)
    TailTextView ttvLanguage;
    @Bind(R.id.ttv_currency)
    TailTextView ttvCurrency;
    @Bind(R.id.tv_shipping)
    TextView tvShipping;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_change_profile)
    TextView tvChangeProfile;
    @Bind(R.id.tv_about_us)
    TextView tvAboutUs;
    @Bind(R.id.tv_contact_us)
    TextView tvContactUs;
    @Bind(R.id.tv_services)
    TextView tvServices;
    @Bind(R.id.tv_privacy)
    TextView tvPrivacy;
    @Bind(R.id.tv_return)
    TextView tvReturn;
    @Bind(R.id.tv_logout)
    TextView tvLogout;
    @Bind(R.id.ll_list)
    LinearLayout llList;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < llList.getChildCount(); i++) {
            if (llList.getChildAt(i) instanceof TextView) {
                llList.getChildAt(i).setOnClickListener(SettingsActivity.this);
            }
        }
    }

    @Override
    protected void initData() {
        ttvLanguage.setTailText("EN");
        ttvCurrency.setTailText("USD");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ttv_currency:
                Toast.makeText(this, "当前货币: " + ttvCurrency.getTailText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ttv_language:
                Toast.makeText(this, "当前语言: " + ttvLanguage.getTailText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
