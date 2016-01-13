package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： CanvasView
 * 作者: oubowu
 * 时间： 2016/1/12 17:12
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class PathClipView extends View {

    private final TextPaint mTextPaint;
    private Paint mPaint;
    private Path mPath;

    public PathClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.parseColor("#ff2d6f"));

        mPath = new Path();

        // mRect = new Rect(0, 0, 500, 500);
        // mRect.intersect(250, 250, 750, 750);
        // intersect的作用跟我们之前学到的图形混合模式有点类似，它会取两个区域的相交区域作为最终区域，
        // 上面我们的第一个区域是在实例化Rect时确定的(0, 0, 500, 500)，
        // 第二个区域是调用intersect方法时指定的(250, 250, 750, 750)，这两个区域对应上图的两个黄色线框，
        // 两者相交的地方则为最终的红色区域，而intersect方法的计算方式是相当有趣的，它不是单纯地计算相交
        // 而是去计算相交区域最近的左上端点和最近的右下端点，不知道大家是否明白这个意思，

        // mRect.union(250, 250, 750, 750)
        // 我们来看Rect中的另一个union方法你就会懂，union方法与intersect相反，取的是相交区域最远的左上端点
        // 作为新区域的左上端点，而取最远的右下端点作为新区域的右下端点

        // Path中除了上面介绍的几个XXXTo方法外还有一套rXXXTo方法：
        // rCubicTo(float x1, float y1, float x2, float y2, float x3, float y3)
        // rLineTo(float dx, float dy)
        // rMoveTo(float dx, float dy)
        // rQuadTo(float dx1, float dy1, float dx2, float dy2)
        // 这一系列rXXXTo方法其实跟上面的那些XXXTo差不多的，唯一的不同是rXXXTo方法的参考坐标是相对的而XXXTo方法的参考坐标始终是参照画布原点坐标

        // 移动点至[100,100]
        mPath.moveTo(100, 100);
        // 将会以[100,100]作为原点坐标，连接以其为原点坐标的坐标点[200,200]，如果换算成一画布原点的话，实际上现在的[200,200]就是[300,300]了
        mPath.rLineTo(200, 200);

        // addCircle(float x, float y, float radius, Path.Direction dir)
        // addOval(float left, float top, float right, float bottom, Path.Direction dir)
        // addRect(float left, float top, float right, float bottom, Path.Direction dir)
        // addRoundRect(float left, float top, float right, float bottom, float rx, float ry, Path.Direction dir)
        // 这些方法和addArc有很明显的区别，就是多了一个Path.Direction参数，其他呢都大同小异，除此之外不知道大家还发现没有，
        // addArc是往Path中添加一段弧，说白了就是一条开放的曲线，而上述几种方法都是一个具体的图形，或者说是一条闭合的曲线，
        // Path.Direction的意思就是标识这些闭合曲线的闭合方向。那什么叫闭合方向呢？
        // 光说大家一定会蒙，有学习激情的童鞋看到后肯定会马上敲代码试验一下两者的区别，可是不管你如何改，
        // 单独地在一条闭合曲线上你是看不出所谓闭合方向的区别的，这时我们可以借助Canvas的另一个方法来简单地说明一下
        // drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint)
        // 这个方法呢很简单沿着Path绘制一段文字，参数也是一看就该懂得了不多说。Path.Direction只有两个常量值
        // CCW和CW分别表示逆时针方向闭合和顺时针方向闭合

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.parseColor("#304ffe"));
        mTextPaint.setTextSize(20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#2baf2b"));
        canvas.clipRect(0, 0, 500, MeasureUtil.getScreenHeight((Activity) getContext()) - MeasureUtil.getStatusBarHeight(getContext()) - MeasureUtil.getToolbarHeight(getContext()));
        canvas.drawColor(Color.parseColor("#fb8c00"));

        mPath.reset();
        mPath.moveTo(20, 20);
        // 二阶贝赛尔曲线,quadTo的前两个参数为控制点的坐标，后两个参数为终点坐标
        mPath.quadTo(100, 100, 200, 50);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(100, 100);
        // 三阶贝赛尔曲线,前四个参数表示两个控制点，最后两个参数表示终点
        mPath.cubicTo(250, 350, 150, 50, 400, 200);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(110, 250);
        mPath.lineTo(310, 250);
        mPath.lineTo(210, 350);
        mPath.lineTo(10, 350);
        // 自动闭合路径
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(10, 360);
        RectF oval = new RectF(10, 360, 110, 460);
        // 用来生成弧线，其实说白了就是从圆或椭圆上截取一部分而已
        // 值为true时将会把弧的起点作为Path的起点
        mPath.arcTo(oval, 0, 100, true);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(10, 460);
        oval = new RectF(10, 460, 110, 520);
        mPath.arcTo(oval, 0, 90);
        canvas.drawPath(mPath, mPaint);


        mPath.reset();
        mPath.moveTo(10, 530);
        // 相对坐标(以(10,530)为原点)，实际上的终点是100,600
        mPath.rLineTo(90, 70);
        // XXXTo方法可以连接Path中的曲线而Path提供的另一系列addXXX方法则可以让我们直接往Path中添加一些曲线，比如
        // addArc(RectF oval, float startAngle, float sweepAngle)
        // 方法允许我们将一段弧形添加至Path，注意这里我用到了“添加”这个词汇，也就是说，
        // 通过addXXX方法添加到Path中的曲线是不会和上一次的曲线进行连接的
        // 添加一条弧线到Path中
        oval = new RectF(10, 540, 100, 640);
        mPath.addArc(oval, 30, 150);
        oval = new RectF(10, 700, 100, 800);
        // CCW和CW分别表示逆时针方向闭合和顺时针方向闭合
        mPath.addRoundRect(oval, 10, 20, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        oval = new RectF(150, 700, 250, 900);
        // CCW和CW分别表示逆时针方向闭合和顺时针方向闭合
        mPath.addOval(oval, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
        // mPath.addOval(oval, Path.Direction.CCW);
        // 沿着Path的文字全都在闭合曲线的“内部”了，Path.Direction闭合方向大概就是这么个意思。
        canvas.drawTextOnPath("我欲乘风归去，又恐琼楼玉宇，高处不胜寒", mPath, -5, -5, mTextPaint);

        mPath.reset();
        oval = new RectF(300, 700, 400, 900);
        mPath.addOval(oval, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
        canvas.drawTextOnPath("我欲乘风归去，又恐琼楼玉宇，高处不胜寒", mPath, -5, -5, mTextPaint);

        // 我们在以[500,600]为圆心绘制一个半径为100px的绿色圆，按道理来说，
        // 这个圆应该刚好与红色区域下方相切对吧，但是事实上呢我们见不到任何效果，为什么？
        // 因为如上所说，当前画布被“裁剪”了，只有500x500也就是上图中红色区域的大小了，
        // 如果我们所绘制的东西在该区域外部，即便绘制了你也看不到
        canvas.drawCircle(500, 550, 100, mPaint);
    }

}
