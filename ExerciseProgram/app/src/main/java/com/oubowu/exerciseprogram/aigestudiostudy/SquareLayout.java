package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.exerciseprogram.R;
import com.socks.library.KLog;

/**
 * 类名： SquareLayout
 * 作者: oubowu
 * 时间： 2016/1/22 11:39
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class SquareLayout extends ViewGroup {

    private static final int ORIENTATION_HORIZONTAL = 0, ORIENTATION_VERTICAL = 1;// 排列方向的常量标识值
    private static final int DEFAULT_MAX_ROW = Integer.MAX_VALUE, DEFAULT_MAX_COLUMN = Integer.MAX_VALUE;// 最大行列默认值

    private int mMaxRow = DEFAULT_MAX_ROW;// 最大行数
    private int mMaxColumn = DEFAULT_MAX_COLUMN;// 最大列数

    private int mOrientation = /*ORIENTATION_HORIZONTAL*/ORIENTATION_VERTICAL;// 排列方向默认横向

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化最大行列数
        mMaxRow = 3;
        mMaxColumn = 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 声明临时变量存储父容器的期望值，该值应该等于父容器的内边距加上所有子元素的测量宽高和外边距
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        int childMeasureState = 0;

        if (getChildCount() > 0) {

            // 声明两个一维数组存储子元素宽高数据
            int[] childWidths = new int[getChildCount()];
            int[] childHeights = new int[getChildCount()];

            // 遍历子元素并对其进行测量
            for (int i = 0; i < getChildCount(); i++) {
                // 获取子元素
                final View child = getChildAt(i);
                // 如果该子元素没有以“不占用空间”的方式隐藏则表示其需要被测量计算
                if (child.getVisibility() != View.GONE) {
                    // 测量子元素并考量其外边距
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    // 比较子元素测量宽高并比较取其较大值（因为这里我们做的是把子View强制截为正方形）
                    int childMeasureSize = Math.max(child.getMeasuredWidth(), child.getMeasuredHeight());
                    // 重新封装子元素测量规格
                    int childMeasureSpec = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.EXACTLY);
                    // 重新测量子元素
                    child.measure(childMeasureSpec, childMeasureSpec);
                    // 获取子元素布局参数
                    final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                    /*
                     * 考量外边距计算子元素实际宽高并将数据存入数组
                     */
                    childWidths[i] = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                    childHeights[i] = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                    // 合并子元素的测量状态
                    childMeasureState = combineMeasuredStates(childMeasureState, child.getMeasuredState());
                }
            }

            // 声明临时变量存储行/列宽高
            int indexMultiWidth = 0, indexMultiHeight = 0;

            if (mOrientation == ORIENTATION_HORIZONTAL) {
                // 水平方向

                // 子元素数量大于最大列数
                if (getChildCount() > mMaxColumn) {
                    // 计算出行数
                    final int row = getChildCount() / mMaxColumn;
                    // 计算余数
                    final int remainder = getChildCount() % mMaxColumn;

                    int index = 0;

                    for (int x = 0; x < row; x++) {
                        for (int y = 0; y < mMaxColumn; y++) {
                            // 单行宽度累加
                            indexMultiWidth += childWidths[index];
                            // 单行取高度最大值
                            indexMultiHeight = Math.max(indexMultiHeight, childHeights[index++]);
                        }
                        // 每一行遍历完成后，将这一行宽度与上一行宽度比较取最大值
                        parentDesireWidth = Math.max(parentDesireWidth, indexMultiWidth);
                        // 每一行遍历完成后累加各行高度
                        parentDesireHeight += indexMultiHeight;
                        // 重置参数
                        indexMultiWidth = indexMultiHeight = 0;
                    }

                    // 如果有余数说明，有子元素未能填满一行
                    if (remainder != 0) {
                        // 遍历剩下的子元素将其宽高计算到父容器期望值
                        for (int i = getChildCount() - remainder; i < getChildCount(); i++) {
                            indexMultiWidth += childWidths[i];
                            indexMultiHeight = Math.max(indexMultiHeight, childHeights[i]);
                        }
                        parentDesireWidth = Math.max(parentDesireWidth, indexMultiWidth);
                        parentDesireHeight += indexMultiHeight;
                        indexMultiWidth = indexMultiHeight = 0;
                    }
                } else {
                    // 如果子元素数量还没有限制值大那么直接计算即可不须折行
                    for (int i = 0; i < getChildCount(); i++) {
                        parentDesireWidth += childWidths[i];
                        parentDesireHeight = Math.max(parentDesireHeight, childHeights[i]);
                    }
                }

            } else {
                // 垂直方向
                if (getChildCount() > mMaxRow) {
                    final int column = getChildCount() / mMaxRow;
                    final int remainder = getChildCount() % mMaxRow;

                    int index = 0;

                    for (int x = 0; x < column; x++) {
                        for (int y = 0; y < mMaxRow; y++) {
                            indexMultiHeight += childHeights[index];
                            indexMultiWidth = Math.max(indexMultiWidth, childWidths[index++]);
                        }
                        parentDesireHeight = Math.max(parentDesireHeight, indexMultiHeight);
                        parentDesireWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }

                    if (remainder != 0) {
                        for (int i = getChildCount() - remainder; i < getChildCount(); i++) {
                            indexMultiHeight += childHeights[i];
                            indexMultiWidth = Math.max(indexMultiWidth, childWidths[i]);
                        }
                        parentDesireHeight = Math.max(parentDesireHeight, indexMultiHeight);
                        parentDesireWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }

                } else {

                    for (int i = 0; i < getChildCount(); i++) {
                        parentDesireWidth = Math.max(parentDesireWidth, childWidths[i]);
                        parentDesireHeight += childHeights[i];
                    }

                }

            }

            // 考量父容器内边距将其累加到期望值
            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();

            // 尝试比较父容器期望值与Android建议的最小值大小并取较大值
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());

        }

        // resolveSize方法其实是View提供给我们解算尺寸大小的一个工具方法，其具体实现在API 11后交由另一个方法resolveSizeAndState也就是我们这一节例子所用到的去处理
        // 确定父容器的测量宽高
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));
        /**
         * childMeasuredState这个值呢由View.getMeasuredState()这个方法返回，一个布局（或者按我的说法父容器）通过View.combineMeasuredStates()
         * 这个方法来统计其子元素的测量状态。
         * 在大多数情况下你可以简单地只传递0作为参数值，而子元素状态值目前的作用只是用来告诉父容器在对其进行测量得出的测量值比它自身想要的尺寸要小，
         * 如果有必要的话一个对话框将会根据这个原因来重新校正它的尺寸
         *
         * 测量状态对谷歌官方而言也还算个测试性的功能，具体鄙人也没有找到很好的例证，
         * 如果大家谁找到了其具体的使用方法可以分享一下，这里我们还是就按照谷歌官方的建议依葫芦画瓢
         *
         */
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (getChildCount() > 0) {

            // 储存宽高倍增值
            int multi = 0;

            // 指数倍增值
            int indexMulti = 1;

            // 声明临时变量存储行/列宽高
            int indexMultiWidth = 0, indexMultiHeight = 0;

            // 声明临时变量存储行/列临时宽高
            int tempHeight = 0, tempWidth = 0;

            for (int i = 0; i < getChildCount(); i++) {

                final View child = getChildAt(i);

                if (child.getVisibility() != GONE) {

                    final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                    // 这里做的宽高一致
                    int childActualSize = child.getMeasuredWidth();

                    if (mOrientation == ORIENTATION_HORIZONTAL) {
                        // 水平方向

                        if (getChildCount() > mMaxColumn) {

                            if (i < mMaxColumn * indexMulti) {

                                child.layout(getPaddingLeft() + lp.leftMargin + indexMultiWidth,
                                        getPaddingTop() + lp.topMargin + indexMultiHeight,
                                        getPaddingLeft() + lp.leftMargin + indexMultiWidth + childActualSize,
                                        getPaddingTop() + lp.topMargin + indexMultiHeight + childActualSize);

                                indexMultiWidth += childActualSize + lp.leftMargin + lp.rightMargin;

                                tempHeight = Math.max(tempHeight, childActualSize) + lp.topMargin + lp.bottomMargin;

                                // 如果下一次遍历到的子元素下标值大于限定值
                                if (i + 1 >= mMaxColumn * indexMulti) {
                                    // 那么累加高度到高度倍增值
                                    indexMultiHeight += tempHeight;
                                    // 重置宽度倍增值
                                    indexMultiWidth = 0;
                                    // 增加指数倍增值
                                    indexMulti++;
                                }
                            }

                        } else {

                            child.layout(getPaddingLeft() + lp.leftMargin + multi,
                                    getPaddingTop() + lp.topMargin,
                                    getPaddingLeft() + lp.leftMargin + multi + childActualSize,
                                    getPaddingTop() + lp.topMargin + childActualSize);

                            multi += childActualSize + lp.leftMargin + lp.rightMargin;

                        }


                    } else {
                        // 垂直方向

                        if (getChildCount() > mMaxRow) {

                            if (i < mMaxRow * indexMulti) {

                                child.layout(getPaddingLeft() + lp.leftMargin + indexMultiWidth,
                                        getPaddingTop() + lp.topMargin + indexMultiHeight,
                                        getPaddingLeft() + lp.leftMargin + indexMultiWidth + childActualSize,
                                        getPaddingTop() + lp.topMargin + indexMultiHeight + childActualSize);

                                indexMultiHeight += childActualSize + lp.topMargin + lp.bottomMargin;

                                tempWidth = Math.max(tempWidth, childActualSize) + lp.leftMargin + lp.rightMargin;

                                if (i + 1 >= mMaxRow * indexMulti) {
                                    indexMultiWidth += tempWidth;
                                    indexMultiHeight = 0;
                                    indexMulti++;
                                }

                            }

                        } else {
                            child.layout(getPaddingLeft() + lp.leftMargin,
                                    getPaddingTop() + lp.topMargin + multi,
                                    getPaddingLeft() + lp.leftMargin + childActualSize,
                                    getPaddingTop() + lp.topMargin + multi + childActualSize);

                            multi += childActualSize + lp.topMargin + lp.bottomMargin;
                        }

                    }
                }
            }

        }

    }

    /**
     * 在Android很多的布局控件中都会重写这么一个方法,并且都会一致地返回false，其作用是告诉framework我们当前的布局不是一个滚动的布局
     *
     * @return
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    // 直接返回一个MarginLayoutParams的实例对象，因为我不需要在LayoutParams处理自己的逻辑，
    // 单纯地计算margins就没必要去实现一个自定义的MarginLayoutParams子类

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int mGravity = Gravity.LEFT | Gravity.RIGHT;// 对齐方式

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

			/*
             * 获取xml对应属性
			 */
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.SquareLayout);
            mGravity = a.getInt(R.styleable.SquareLayout_my_gravity, 0);
            KLog.e("SquareLayout_android_layout_gravity：" + a.getInt(R.styleable.SquareLayout_android_layout_gravity, -1));
            a.recycle();

            a = c.obtainStyledAttributes(attrs, R.styleable.AttrView);
            /**
             *  第一个参数很好理解表示我们定义的属性资源ID，最后一个参数呢也和前面的getInt类似，主要是这第二、三个参数，其作用是分开来的，
             *  当我们在xml中使用百分比属性时有两种写法，一种是标准的10%而另一种是带p的10%p：
             aigestudio:alpha="10%"
             aigestudio:alpha="10%p"
             当属性值为10%的时候base参数起作用，我们此时获取的参数值就等于(10% * base)，而pbase参数则无效，
             同理当属性值为10%p时参数值就等于(10% * pbase)而base无效，Just it
             */
            final float alphaFraction = a.getFraction(R.styleable.AttrView_alpha, 100, 200, 50);
            KLog.e("自定义属性的百分比为(%基数为100，%p基数为200)：" + alphaFraction);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

}
