package com.oubowu.exerciseprogram.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oubowu.exerciseprogram.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    /*@BindingAdapter({"bind:image", "bind:size"})
    public static void imageLoader(ImageView imageView, String url, int size) {
        imageView.getLayoutParams().width = imageView.getLayoutParams().height = size;
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.ic_loading_small_bg)
                .error(R.mipmap.ic_fail)
                .into(imageView);
    }*/

    @BindingAdapter({"bind:image"})
    public static void imageLoader(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.ic_loading_small_bg)
                .error(R.mipmap.ic_fail)
                .into(imageView);
    }

    // 不需要关心这个convertDate在哪个类中，重要的是他的@BindingConversion注解，这个方法接受一个Date类型的变量，
    // 正好我们的android:text设置的就是一个Date类型的值，在方法内部我们将这个Date类型的变量转换成String类型的日期
    // 并且返回
    @BindingConversion
    public static String convertDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        return "查询时间: " + sdf.format(date);
    }

}
