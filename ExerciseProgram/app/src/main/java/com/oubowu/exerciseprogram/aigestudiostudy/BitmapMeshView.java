package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： BitmapMeshView
 * 作者: oubowu
 * 时间： 2016/1/12 13:48
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BitmapMeshView extends View {

    // drawBitmapMesh是个很屌毛的方法，为什么这样说呢？因为它可以对Bitmap做几乎任何改变，
    // 是的，你没听错，是任何，几乎无所不能，这个屌毛方法我曾一度怀疑谷歌那些逗比为何将它屈尊在Canvas下，
    // 因为它对Bitmap的处理实在在强大了。

    private static final int WIDTH = 19;// 横向分割成的网格数量
    private static final int HEIGHT = 19;// 纵向分割成的网格数量
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);// 横纵向网格交织产生的点数量
    private final int mWidth;
    private final int mHeight;

    private Bitmap mBitmap;// 位图资源
    private float[] verts;// 交点的坐标数组

    public BitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mHeight = MeasureUtil.getScreenWidth((Activity) getContext());

        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_pal,
                (int) (mWidth / 1.5f),
                (int) (mHeight / 1.5f));

        verts = new float[COUNT * 2];
        // Y均分的长度
        float multipleY = mBitmap.getHeight() * 1.0f / HEIGHT;
        // X均分的长度
        float multipleX = mBitmap.getWidth() * 1.0f / WIDTH;
        // 生成各个交点坐标
        int index = 0;
        // 这段代码生成了200个点的坐标数据全部存入verts数组，verts数组中，偶数位表示x轴坐标，奇数位表示y轴坐标，
        // 最终verts数组中的元素构成为：[x,y,x,y,x,y,x,y,x,y,x,y,x,y………………]共200 * 2=400个元素
        for (int y = 0; y <= HEIGHT; y++) {
            // 得到分割每一行的y坐标(HEIGHT等于19，从0开始循环，共得到20个y坐标)
            float fy = multipleY * y;
            for (int x = 0; x <= WIDTH; x++) {
                // 横切效果，得到分割每一行的x坐标(HEIGHT等于19，从0开始循环，共得到20个x坐标)，x随y的值往右偏移一定的长度
//                float fx = multipleX * x + (HEIGHT - y) * 1.0f / HEIGHT * mBitmap.getWidth();

                float fx = multipleX * x;
                setXY(fx, fy, index);

                // 放大镜效果(相当于把原来四个交点增大了一倍)
                if (6 == y) {
                    if (2 == x) {
                        // 6行2列交点坐标往左上方向变化
                        setXY(fx - multipleX, fy - multipleY, index);
                    }
                    if (3 == x) {
                        // 6行3列交点坐标往右上方向变化
                        setXY(fx + multipleX, fy - multipleY, index);
                    }
                }
                if (7 == y) {
                    if (2 == x) {
                        // 7行2列交点坐标往左下方向变化
                        setXY(fx - multipleX, fy + multipleY, index);
                    }
                    if (3 == x) {
                        // 7行3列交点坐标往右下方向变化
                        setXY(fx + multipleX, fy + multipleY, index);
                    }
                }

                index++;
            }
        }

    }

    /**
     * 计算分割后的交点坐标
     *
     * @param fx    x坐标
     * @param fy    y坐标
     * @param index 标示值
     */
    private void setXY(float fx, float fy, int index) {
        // 这样子相邻两个数为存储的xy值，偶数下标为x，奇数下标为y
        verts[2 * index] = fx;
        verts[2 * index + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制网格位图
        // drawBitmapMesh的原理灰常简单，它按照meshWidth和meshHeight这两个参数的值将我们的图片划分成一定数量的网格，
        // 比如上面我们传入的meshWidth和meshHeight均为19，意思就是把整个图片横纵向分成19份
        // 横纵向19个网格那么意味着横纵向分别有20条分割线对吧，这20条分割线交织又构成了20 * 20个交织点
        // 每个点又有x、y两个坐标……而drawBitmapMesh的verts参数就是存储这些坐标值的，不过是图像变化后的坐标值
        // drawBitmapMesh的原理就与之类似，只不过我们不常只改变一点，而是改变大量的点来达到效果，
        // 而参数verts则存储了改变后的坐标，drawBitmapMesh依据这些坐标来改变图像
        // drawBitmapMesh不能存储计算后点的值，每次调用drawBitmapMesh方法改变图像都是以基准点坐标为参考的，也就是说，
        // 不管你执行drawBitmapMesh方法几次，只要参数没改变，效果不累加。

        //-bitmap: 就是将要扭曲的图像
        //-meshWidth: 需要的横向网格数目
        //-meshHeight: 需要的纵向网格数目
        //-verts: 网格顶点坐标数组。
        //-vertOffset: verts数组中开始跳过的(x,y)对的数目。(vertOffset若不为0则verts的数组元素要减去2*vertOffset)
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }
}
