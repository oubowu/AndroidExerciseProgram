package com.oubowu.exerciseprogram.aigestudiostudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AigeActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @Bind(R.id.cv)
    CustomView cv;
    @Bind(R.id.ccv)
    ColorFilterCustomView ccv;
    @Bind(R.id.sb_r)
    SeekBar sbR;
    @Bind(R.id.tv_r)
    TextView tvR;
    @Bind(R.id.sb_g)
    SeekBar sbG;
    @Bind(R.id.tv_g)
    TextView tvG;
    @Bind(R.id.sb_b)
    SeekBar sbB;
    @Bind(R.id.tv_b)
    TextView tvB;
    @Bind(R.id.sb_a)
    SeekBar sbA;
    @Bind(R.id.tv_a)
    TextView tvA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aige);
        ButterKnife.bind(this);

        sbA.setOnSeekBarChangeListener(this);
        sbR.setOnSeekBarChangeListener(this);
        sbG.setOnSeekBarChangeListener(this);
        sbB.setOnSeekBarChangeListener(this);
        onProgressChanged(null, 0, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        final float a = sbA.getProgress() / 10.0f;
        final float r = sbR.getProgress() / 10.0f;
        final float g = sbG.getProgress() / 10.0f;
        final float b = sbB.getProgress() / 10.0f;
        tvA.setText("Alpha: " + a);
        tvR.setText("Red: " + r);
        tvG.setText("Green: " + g);
        tvB.setText("Blue: " + b);
        ccv.setArgb(a, r, g, b);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
