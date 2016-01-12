package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

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

    private Paint mPaint;
    private Path mPath;

    public PathClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GREEN);

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        canvas.clipRect(0, 0, 500, 500);
        canvas.drawColor(Color.RED);

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

        // 我们在以[500,600]为圆心绘制一个半径为100px的绿色圆，按道理来说，
        // 这个圆应该刚好与红色区域下方相切对吧，但是事实上呢我们见不到任何效果，为什么？
        // 因为如上所说，当前画布被“裁剪”了，只有500x500也就是上图中红色区域的大小了，
        // 如果我们所绘制的东西在该区域外部，即便绘制了你也看不到
        canvas.drawCircle(500, 550, 100, mPaint);
    }

}
