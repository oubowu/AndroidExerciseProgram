package com.oubowu.exerciseprogram.rxjava.nbabymvp.viewholder;

import android.support.annotation.UiThread;
import android.view.View;

import com.oubowu.exerciseprogram.databinding.NbaRecyclerViewBinding;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.Tr;

/**
 * 类名： NbaViewHolder
 * 作者: oubowu
 * 时间： 2016/1/18 15:27
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NbaViewHolder extends BaseRecyclerViewHolder {

    final NbaRecyclerViewBinding binding;

    public NbaViewHolder(final View view, final NbaRecyclerViewBinding binding) {
        super(view);
        this.binding = binding;
    }

    @UiThread
    public void bindMatchScore(final Tr match) {
        this.binding.setMatch(match);
    }

    @UiThread
    public void bindPlayer1logo(final String player1logobig) {
        this.binding.setPlayer1logobig(player1logobig);
    }

    @UiThread
    public void bindPlayer2logo(final String player2logobig) {
        this.binding.setPlayer2logobig(player2logobig);
    }

    @UiThread
    public void bindImageSize(final int size) {
        this.binding.setSize(size);
    }

}
