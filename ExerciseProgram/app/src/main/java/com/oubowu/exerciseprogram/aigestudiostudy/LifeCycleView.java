package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.socks.library.KLog;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/22 21:32
 * UpdateUser:
 * UpdateDate:
 */
public class LifeCycleView extends View {

    private static final String TAG = "AigeStudio:LifeCycleView";

    public LifeCycleView(Context context) {
        super(context);
        KLog.e("Construction with single parameter");
    }

    public LifeCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 首先是调用了构造方法
        KLog.e("Construction with two parameters");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 然后调用了onFinishInflate方法，这个方法当xml布局中我们的View被解析完成后则会调用
        KLog.e("onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 紧接着调用的是onAttachedToWindow方法，此时表示我们的View已被创建并添加到了窗口Window中
        KLog.e("onAttachedToWindow");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        // 紧接着一般会调用onWindowVisibilityChanged方法，只要我们当前的Window窗口中View的可见状态发生改变都会被触发，这时View是被显示了
        KLog.e("onWindowVisibilityChanged");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 随后就会开始调用onMeasure方法对View进行测量
        KLog.e("onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 如果测量结果被确定则会先调用onSizeChanged方法通知View尺寸大小发生了改变
        KLog.e("onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 紧跟着便会调用onLayout方法对子元素进行定位布局，然后再次调用onMeasure方法对View进行二次测量，如果测量值与上一次相同则不再调用onSizeChanged方法，接着再次调用onLayout方法
        KLog.e("onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 如果测量过程结束，则会调用onDraw方法绘制View
        KLog.e("onDraw");
    }

    // onMeasure和onLayout方法被调用了两次，很多童鞋会很纠结为何onMeasure方法回被多次调用，其实没必要过于纠结这个问题，onMeasure的调用取决于控件的父容器以及View Tree的结构，
    // 不同的父容器有不同的测量逻辑，比如上一节自定义控件其实很简单2/3中，我们在SquareLayout测量子元素时就采取了二次测量，在API 19的时候Android对测量逻辑做了进一步的优化，
    // 比如在19之前只会对最后一次的测量结果进行Cache而在19开始则会对每一次测量结果都进行Cache，如果相同的代码相同布局相同的逻辑在19和19之前你有可能会看到不一样的测量次数结果，
    // 所以没必要去纠结这个问题，一般情况下只要你逻辑正确onMeasure都会得到正确的调用。

    // 退出时先是onWindowVisibilityChanged，然后onDetachedFromWindow
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KLog.e("onDetachedFromWindow");
    }

    // framework对xml文件的解析是相当耗时的，如果可以，我们应当尽量避免对xml文档的读取，特别是元素结构复杂的xml文件，这里我们用到的xml布局文件还不算复杂，如果我不想从xml文档读取而是直接实例化类
    // 直接让复合控件类继承LinearLayout，在构造方法中实例化子元素并设置其属性值然后添加至LinearLayout中搞定，复合控件在实际应用中也使用得相当广泛，因为很多时候使用符合控件不需要处理复杂的测绘逻辑，简单方便高效。

}
