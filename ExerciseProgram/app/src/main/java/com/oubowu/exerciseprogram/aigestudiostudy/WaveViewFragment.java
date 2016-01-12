package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oubowu.exerciseprogram.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaveViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.ccv)
    WaveView ccv;

    @Bind(R.id.bt)
    Button bt;

    @OnClick(R.id.bt)
    void play() {
        if (ccv.getTag() == null) {
            new Thread(ccv).start();
            ccv.startWave();
            ccv.setTag(1);
            bt.setText("停止播放");
        } else {
            ccv.stopWave();
            ccv.setTag(null);
            bt.setText("开始播放");
        }
    }


    private String mParam1;
    private String mParam2;

    public WaveViewFragment() {
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
    public static WaveViewFragment newInstance(String param1, String param2) {
        WaveViewFragment fragment = new WaveViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_wave, container, false);
        ButterKnife.bind(this, view);

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
        ccv.stopWave();
        ButterKnife.unbind(this);
    }

}
