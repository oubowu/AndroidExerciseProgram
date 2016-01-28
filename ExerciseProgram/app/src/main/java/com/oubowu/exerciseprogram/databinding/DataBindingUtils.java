package com.oubowu.exerciseprogram.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oubowu.exerciseprogram.R;

/**
 * 类名： DataBindingUtils
 * 作者: oubowu
 * 时间： 2016/1/28 17:51
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class DataBindingUtils {

    // 我们定义了一个Utils类，这个类你可以随便起名，该类中只有一个静态的方法imageLoader，该方法有两个参数，一个是需要设置数据的view，
    // 一个是我们需要的url。值得注意的是那个BindingAdapter注解，看看他的参数，是一个数组，内容只有一个bind:image，仅仅几行代码，我们不需要
    // 手工调用Utils.imageLoader，也不需要知道imageLoader方法定义到哪了，一个网络图片加载就搞定了，是不是很神奇，这里面起关键作用的就是BindingAdapter
    @BindingAdapter({"bind:image", "bind:size"})
    public static void imageLoader(ImageView imageView, String url, int size) {
        imageView.getLayoutParams().width = imageView.getLayoutParams().height = size;
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.ic_loading_small_bg)
                .error(R.mipmap.ic_fail)
                .into(imageView);
    }

}
