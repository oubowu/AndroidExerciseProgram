package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorMatrixFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.ccv)
    ColorMatrixCustomView ccv;
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

    private String mParam1;
    private String mParam2;

    public ColorMatrixFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorMatrixFragment.
     */
    public static ColorMatrixFragment newInstance(String param1, String param2) {
        ColorMatrixFragment fragment = new ColorMatrixFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_matrix, container, false);
        ButterKnife.bind(this, view);

        sbA.setOnSeekBarChangeListener(this);
        sbR.setOnSeekBarChangeListener(this);
        sbG.setOnSeekBarChangeListener(this);
        sbB.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
