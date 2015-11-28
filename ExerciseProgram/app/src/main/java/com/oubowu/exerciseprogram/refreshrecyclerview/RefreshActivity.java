package com.oubowu.exerciseprogram.refreshrecyclerview;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RefreshActivity extends BaseActivity {

    @Bind(R.id.rrv)
    RefreshRecyclerView rrv;

    private int count;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add("位置" + i);
        }
        count = 20;
        final MyAdapter adapter = new MyAdapter(this, arrayList, rrv);
        rrv.setAdapterAndLayoutManager(adapter, new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rrv.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        final List<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            arrayList.add("位置" + i);
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setDatas(arrayList);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onLoadMore() {
                final List<String> arrayList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    arrayList.add("位置" + (i + count));
                }
                count += 20;
                if (adapter.getItemCount() > 50) {
                    rrv.loadAllComplete();
                    count = 20;
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addMoreDatas(arrayList);
                            }
                        });
                    }
                }.start();
            }
        });
    }

    class MyViewHolder extends BaseRecyclerViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class MyAdapter extends BaseRecyclerViewAdapter<MyViewHolder, String> {

        public MyAdapter(Context context, List<String> datas, RefreshRecyclerView refreshRecyclerView) {
            super(context, datas, refreshRecyclerView);
        }

        @Override
        protected MyViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(RefreshActivity.this).inflate(R.layout.item, parent, false));
        }

        @Override
        protected void onBindCustomViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(mDatas.get(position));
            holder.textView.setHeight((int) (80 + Math.random() * 100));
        }

        @Override
        protected int getCustomItemViewType(int position) {
            return 0;
        }

    }

}
