package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： CustomView
 * 作者: oubowu
 * 时间： 2016/1/6 13:43
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class PorterDuffColorCustomView extends View {

    private Bitmap mBitmap;
    private Rect appAreaRect;
    private AvoidXfermode avoidXfermode;

    public PorterDuffColorCustomView(Context context) {
        super(context);
        initPaint();
    }

    public PorterDuffColorCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PorterDuffColorCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private Paint mPaint;
    private Paint mPaint1;

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

       /*
        * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
        *
        * 画笔样式分三种：
        * 1.Paint.Style.STROKE：描边
        * 2.Paint.Style.FILL_AND_STROKE：描边并填充
        * 3.Paint.Style.FILL：填充
        */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 设置画笔颜色为自定义颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

       /*
        * 设置描边的粗细，单位：像素px
       * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
        */
        mPaint.setStrokeWidth(10);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);

        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        // PorterDuffColorFilter跟LightingColorFilter一样，只有一个构造方法：
        // PorterDuffColorFilter(int color, PorterDuff.Mode mode)
        // 这个构造方法也接受两个值，一个是16进制表示的颜色值这个很好理解，而另一个是PorterDuff内部类Mode中的一个常量值，这个值表示混合模式。
        // 那么什么是混合模式呢？混合混合必定是有两种东西混才行，第一种就是我们设置的color值而第二种当然就是我们画布上的元素了！
        // 比如这里我们把Color的值设为红色，而模式设为PorterDuff.Mode.LIGHTEN变亮：

        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN));

        // setXfermode(Xfermode xfermode)
        // Xfermode国外有大神称之为过渡模式，这种翻译比较贴切但恐怕不易理解，大家也可以直接称之为图像混合模式，
        // 因为所谓的“过渡”其实就是图像混合的一种，这个方法跟我们上面讲到的setColorFilter蛮相似的，首先它与set一样没有公开的实现的方法：
        // 有三个子类：AvoidXfermode, PixelXorXfermode和PorterDuffXfermode，这三个子类实现的功能要比setColorFilter的三个子类复杂得多，
        // 主要是是涉及到图像处理的一些知识可能对大家来说会比较难以理解


        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void avoidXfermode() {
        // AvoidXfermode
        // 首先我要告诉大家的是这个API因为不支持硬件加速在API 16已经过时了（大家可以在HardwareAccel查看那些方法不支持硬件加速）……
        // 如果想在高于API 16的机子上测试这玩意，必须现在应用或手机设置中关闭硬件加速，在应用中我们可以通过在AndroidManifest.xml文件中
        // 设置application节点下的android:hardwareAccelerated属性为false来关闭硬件加速：
        // 第一个opColor表示一个16进制的可以带透明通道的颜色值例如0x12345678，
        // 第二个参数tolerance表示容差值，那么什么是容差呢？你可以理解为一个可以标识“精确”或“模糊”的东西
        // 最后一个参数表示AvoidXfermode的具体模式，其可选值只有两个：AvoidXfermode.Mode.AVOID或者AvoidXfermode.Mode.TARGET


        // AvoidXfermode.Mode.TARGET
        // 在该模式下Android会判断画布上的颜色是否会有跟opColor不一样的颜色，比如我opColor是红色，
        // 那么在TARGET模式下就会去判断我们的画布上是否有存在红色的地方，如果有，则把该区域“染”上一层我们画笔定义的颜色，
        // 否则不“染”色，而tolerance容差值则表示画布上的像素和我们定义的红色之间的差别该是多少的时候才去“染”的，
        // 比如当前画布有一个像素的色值是(200, 20, 13)，而我们的红色值为(255, 0, 0)，当tolerance容差值为255时，
        // 即便(200, 20, 13)并不等于红色值也会被“染”色，容差值越大“染”色范围越广反之则反

        /*
         * 当画布中有跟opColor色一样的地方时候才“染”色
         */
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 100, AvoidXfermode.Mode.AVOID);
        mPaint1.setXfermode(avoidXfermode);
        // “染”什么色是由我们自己决定的
        mPaint1.setColor(Color.GREEN);

        // AvoidXfermode.Mode.AVOID
        // 则与TARGET恰恰相反，TARGET是我们指定的颜色是否与画布的颜色一样，而AVOID是我们指定的颜色是否与画布不一样，其他的都与TARGET类似
        // AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.AVOID)：
        // 当模式为AVOID容差值为0时，只有当图片中像素颜色值与0XFFFFFFFF完全不一样的地方才会被染色
        // AvoidXfermode(0XFFFFFFFF, 255, AvoidXfermode.Mode.AVOID)：
        // 当容差值为255时，只要与0XFFFFFFFF稍微有点不一样的地方就会被染色
        // 那么这玩意究竟有什么用呢？比如说当我们只想在白色的区域画点东西或者想把白色区域的地方替换为另一张图片的时候就可以采取这种方式！

        //  PixelXorXfermode
        // 与AvoidXfermode一样也在API 16过时了，该类也提供了一个含参的构造方法PixelXorXfermode(int opColor)，该类的计算实现很简单，
        // 从官方给出的计算公式来看就是：op ^ src ^ dst，像素色值的按位异或运算，如果大家感兴趣，可以自己用一个纯色去尝试，
        // 并自己计算异或运算的值是否与得出的颜色值一样，这里我就不讲了，Because it was deprecated and useless。

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (appAreaRect == null) return;
        canvas.drawBitmap(mBitmap, (appAreaRect.width() - mBitmap.getWidth()) / 2,
                (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mBitmap.getHeight()) / 2,
                mPaint);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint1);
        // 设置AV模式
        avoidXfermode();
        canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mPaint1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            appAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            invalidate();
        }
    }

}
