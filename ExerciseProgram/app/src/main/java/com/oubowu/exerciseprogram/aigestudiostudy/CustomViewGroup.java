package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类名： CustomViewGroup
 * 作者: oubowu
 * 时间： 2016/1/22 10:21
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            // 如果有子元素,对子元素进行测量
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }*/

        // 声明临时变量存储父容器的期望值
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                // 获取子元素的布局参数
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                // 测量子元素并考虑外边距
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                // 计算父容器的期望值
                parentDesireWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                parentDesireHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }
            // 考虑父容器的内边距
            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();
            // 尝试比较建议最小值和期望值的大小并取大值
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        }
        // 设置最终测量值
        setMeasuredDimension(resolveSize(parentDesireWidth, widthMeasureSpec), resolveSize(parentDesireHeight, heightMeasureSpec));
    }

    // 一个View的大小由其父容器的测量规格MeasureSpec和View本身的布局参数LayoutParams共同决定，
    // 但是即便如此，最终封装的测量规格也是一个期望值，究竟有多大还是我们调用setMeasuredDimension方法设置的

    // ViewGroup中的onLayout方法是一个抽象方法，这意味着你在继承时必须实现，onLayout的目的是为了确定子元素在父容器中的位置，
    // 那么这个步骤理应该由父容器来决定而不是子元素，因此，我们可以猜到View中的onLayout方法应该是一个空实现
    // ViewGroup的onLayout方法的签名列表中有五个参数，其中boolean changed表示是否与上一次位置不同，
    // 其具体值在View的layout方法中通过setFrame等方法确定
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // 声明一个临时变量存储高度倍增值
        int mutilHeight = 0;
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();

        for (int i = 0; i < getChildCount(); i++) {

            // 遍历子元素并对其进行定位布局
            final View child = getChildAt(i);

            final CustomLayoutParams lp = (CustomLayoutParams) child.getLayoutParams();

            child.layout(0 + paddingLeft + lp.leftMargin,
                    mutilHeight + paddingTop + lp.topMargin,
                    child.getMeasuredWidth() + paddingLeft + lp.leftMargin,
                    child.getMeasuredHeight() + mutilHeight + paddingTop + lp.topMargin);

            // 改变高度倍增值
            mutilHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        }
    }

    // 在我们的CustomLayout中生成了一个静态内部类CustomLayoutParams，保持其默认的构造方法即可，这里我们什么也没做，
    // 当然你可以定义自己的一些属性或逻辑处理，因控件而异这里不多说了，后面慢慢会用到。
    public static class CustomLayoutParams extends MarginLayoutParams {

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    // 生成默认的布局参数
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    // 生成布局参数,将布局参数包装成我们的
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    // 生成布局参数,
    // 从属性配置中生成我们的布局参数
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    // 查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }
}
