package com.oubowu.exerciseprogram.customview.meituan;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.UiUtil;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;

/**
 * 类名： MeituanListView
 * 作者: oubowu
 * 时间： 2016/1/25 15:10
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class MeituanListView extends ListView implements AbsListView.OnScrollListener {

    // 隐藏的状态
    private static final int DONE = 0;
    // 下拉刷新的状态
    private static final int PULL_TO_REFRESH = 1;
    // 松开刷新的状态
    private static final int RELEASE_TO_REFRESH = 2;
    // 正在刷新的状态
    private static final int REFRESHING = 3;

    private static final int RATIO = 3;

    private LinearLayout mHeaderView;
    private int mHeaderViewHeight;
    private float mStartY;
    private float mOffsetY;
    private TextView mPullToRefreshTextView;
    private OnMeiTuanRefreshListener mOnRefreshListener;
    private int mState;
    private int mFirstVisibleItem;
    private boolean mIsRecord;
    private boolean mIsEnd;
    private boolean mIsRefreable;
    private FrameLayout mAnimContainer;
    private Animation animation;
    private SimpleDateFormat format;
    private MeiTuanRefreshFirstStepView mFirstView;
    private MeiTuanRefreshSecondStepView mSecondView;
    private AnimationDrawable mSecondAnim;
    private MeiTuanRefreshThirdStepView mThirdView;
    private AnimationDrawable mThirdAnim;
    private boolean mOnRefreshComplete;

    public MeituanListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public interface OnMeiTuanRefreshListener {
        void onRefresh();
    }

    /**
     * 回调接口，想实现下拉刷新的listview实现此接口
     *
     * @param onRefreshListener
     */
    public void setOnMeiTuanRefreshListener(OnMeiTuanRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        mIsRefreable = true;
    }

    /**
     * 刷新完毕，从主线程发送过来，并且改变headerView的状态和文字动画信息
     */
    public void setOnRefreshComplete() {
        //一定要将isEnd设置为true，以便于下次的下拉刷新
        mIsEnd = true;
        mState = DONE;
        mOnRefreshComplete = true;
        changeHeaderByState(mState);
    }

    private void init(Context context) {
        // 在listview中滑动到顶部或者是底部的时候，在默认的情况下，是会有黄色或者黑色的阴影出现。
        // 在2.3之前可以在listview的属性中通过设置android:fadingEdge="none"来解决问题，但是在2.3及以上这个是不行的，这里，可以通过代码来设置模式，禁止其阴影的出现
        setOverScrollMode(OVER_SCROLL_NEVER);
        setOnScrollListener(this);

        // 头部布局
        mHeaderView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.meituan_item, null, false);
        // 步骤一view
        mFirstView = UiUtil.getView(mHeaderView, R.id.first_view);

        // 下拉刷新提示
        mPullToRefreshTextView = UiUtil.getView(mHeaderView, R.id.tv_pull_to_refresh);

        // 步骤二view
        mSecondView = UiUtil.getView(mHeaderView, R.id.second_view);
        // 设置帧动画
        mSecondView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        mSecondAnim = (AnimationDrawable) mSecondView.getBackground();

        mThirdView = UiUtil.getView(mHeaderView, R.id.third_view);
        mThirdView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
        mThirdAnim = (AnimationDrawable) mThirdView.getBackground();

        // 在自定义ListView中，需要将下拉刷新的View在初始化的时候设置padding隐藏起来，这时就要在初始化的时候获得要加载的布局View的高度
        // getMeasuredHeight要在measure后才有值，getHeight要在layout后才有值
        // 在inflate完布局后，调用measureView(headView),然后再调用getMeasureHeight函数就可以获得实际高度了
        measureView(mHeaderView);
        addHeaderView(mHeaderView);

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        // 隐藏刷新头部
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

        mState = DONE;
        mIsEnd = true;
        mIsRefreable = false;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!mIsEnd)
            return super.onTouchEvent(ev);

        //如果现在时结束的状态，即刷新完毕了，可以再次刷新了，在onRefreshComplete中设置
        if (!mIsRefreable)
            return super.onTouchEvent(ev);

        //如果现在是可刷新状态   在setOnMeiTuanListener中设置为true
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 用户按下
                // 如果当前是在listview顶部并且没有记录y坐标
                if (mFirstVisibleItem == 0 && !mIsRecord) {
                    //将isRecord置为true，说明现在已记录y坐标
                    mIsRecord = true;
                    //将当前y坐标赋值给startY起始y坐标
                    mStartY = ev.getY();
                    KLog.e("ACTION_DOWN 如果当前是在listview顶部并且没有记录y坐标");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 用户滑动
                //再次得到y坐标，用来和startY相减来计算offsetY位移值
                float tmpY = ev.getY();
                //如果当前状态不是正在刷新的状态，并且已经记录了y坐标
                if (mState != REFRESHING && mIsRecord) {
                    // 计算y的偏移量
                    mOffsetY = tmpY - mStartY;
                    // 计算当前滑动的高度
                    float currentHeight = (-mHeaderViewHeight + mOffsetY / RATIO);
                    //用当前滑动的高度和头部headerView的总高度进行比 计算出当前滑动的百分比 0到1
                    float currentProgress = 1 + currentHeight / mHeaderViewHeight;
                    //如果当前百分比大于1了，将其设置为1，目的是让第一个状态的椭圆不再继续变大
                    if (currentProgress >= 1) {
                        currentProgress = 1;
                    }
                    KLog.e("如果当前状态不是正在刷新的状态，并且已经记录了y坐标");

                    //如果当前状态为下拉刷新并且已经记录y坐标
                    if (mState == PULL_TO_REFRESH && mIsRecord) {

                        // ③下拉过程中，

                        KLog.e("如果当前状态为下拉刷新并且已经记录y坐标");
                        setSelection(0);
                        //如果下拉距离大于等于headerView的总高度
                        if (-mHeaderViewHeight + mOffsetY / RATIO >= 0) {
                            //将状态变为放开刷新
                            mState = RELEASE_TO_REFRESH;
                            //根据状态改变headerView，主要是更新动画和文字等信息
                            changeHeaderByState(mState);
                            //如果当前y的位移值小于0，即为headerView隐藏了
                        } else if (mOffsetY <= 0) {
                            //将状态变为done
                            mState = DONE;
                            //根据状态改变headerView，主要是更新动画和文字等信息
                            changeHeaderByState(mState);
                        }
                    }

                    //如果当前的状态是放开刷新，并且已经记录y坐标
                    if (mState == RELEASE_TO_REFRESH && mIsRecord) {
                        KLog.e("如果当前的状态是放开刷新，并且已经记录y坐标");
                        // 位置回到0处
                        setSelection(0);
                        //如果当前滑动的距离小于headerView的总高度
                        if (-mHeaderViewHeight + mOffsetY / RATIO < 0) {
                            //将状态置为下拉刷新状态
                            mState = PULL_TO_REFRESH;
                            //根据状态改变headerView，主要是更新动画和文字等信息
                            changeHeaderByState(mState);
                        }
                    } else if (mOffsetY <= 0) {
                        KLog.e("如果当前y的位移值小于0，即为headerView隐藏了");
                        //如果当前y的位移值小于0，即为headerView隐藏了
                        //将状态变为done
                        mState = DONE;
                        //根据状态改变headerView，主要是更新动画和文字等信息
                        changeHeaderByState(mState);
                    }

                    //如果当前状态为done并且已经记录y坐标
                    if (mState == DONE && mIsRecord) {
                        // ①将状态改为正在下拉的状态
                        //如果位移值大于0
                        if (mOffsetY >= 0) {
                            //将状态改为下拉刷新状态
                            mState = PULL_TO_REFRESH;
                        }
                    }

                    //如果为下拉刷新状态
                    if (mState == PULL_TO_REFRESH) {

                        // ②设置paddingTop实现下拉效果(一开始为-mHeaderViewHeight，然后不断的+mOffsetY / RATIO逐渐变大回到原来的位置)

                        KLog.e("如果为下拉刷新状态");
                        //则改变headerView的padding来实现下拉的效果
                        mHeaderView.setPadding(0, (int) (-mHeaderViewHeight + mOffsetY / RATIO), 0, 0);
                        //给第一个状态的View设置当前进度值
                        mFirstView.setCurrentProgress(currentProgress);
                        //重画
                        mFirstView.postInvalidate();
                    }

                    //如果为放开刷新状态
                    if (mState == RELEASE_TO_REFRESH) {
                        KLog.e("如果为放开刷新状态");
                        //改变headerView的padding值
                        mHeaderView.setPadding(0, (int) (-mHeaderViewHeight + mOffsetY / RATIO), 0, 0);
                        //给第一个状态的View设置当前进度值
                        mFirstView.setCurrentProgress(currentProgress);
                        //重画
                        mFirstView.postInvalidate();
                    }

                }


                break;
            case MotionEvent.ACTION_UP:
                //当用户手指抬起时
                // 如果当前状态为下拉刷新状态
                if (mState == PULL_TO_REFRESH) {
                    KLog.e("当用户手指抬起时,如果当前状态为下拉刷新状态：" + (int) (-mHeaderViewHeight + mOffsetY / RATIO) + " ; " + mHeaderViewHeight);
                    //平滑的隐藏headerView
                    this.smoothScrollBy((int) (-mHeaderViewHeight + mOffsetY / RATIO) + mHeaderViewHeight, 500);
                    //根据状态改变headerView
                    changeHeaderByState(mState);
                }
                //如果当前状态为放开刷新
                if (mState == RELEASE_TO_REFRESH) {
                    KLog.e("当用户手指抬起时,如果当前状态为放开刷新");
                    //平滑的滑到正好显示headerView
                    this.smoothScrollBy((int) (-mHeaderViewHeight + mOffsetY / RATIO), 500);
                    //将当前状态设置为正在刷新
                    mState = REFRESHING;
                    //回调接口的onRefresh方法
                    mOnRefreshListener.onRefresh();
                    //根据状态改变headerView
                    changeHeaderByState(mState);
                }
                //这一套手势执行完，一定别忘了将记录y坐标的isRecord改为false，以便于下一次手势的执行
                mIsRecord = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据状态改变headerView的动画和文字显示
     *
     * @param state
     */
    private void changeHeaderByState(int state) {
        switch (state) {
            case DONE://如果的隐藏的状态
                KLog.e("mHeaderView的top：" + mHeaderView.getPaddingTop());
                if (mOnRefreshComplete) {
                    mOnRefreshComplete = false;
                    ValueAnimator animator = new ValueAnimator();
                    animator.setIntValues(mHeaderView.getPaddingTop(), -mHeaderViewHeight);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mHeaderView.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.setDuration(500);
                    animator.start();
                } else {
                    //设置headerView的padding为隐藏
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                }
                //第一状态的view显示出来
                mFirstView.setVisibility(View.VISIBLE);
                //第二状态的view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //停止第二状态的动画
                mSecondAnim.stop();
                //第三状态的view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //停止第三状态的动画
                mThirdAnim.stop();
                break;
            case RELEASE_TO_REFRESH://当前状态为放开刷新
                //文字显示为放开刷新
                mPullToRefreshTextView.setText("放开刷新");
                //第一状态view隐藏起来
                mFirstView.setVisibility(View.GONE);
                //第二状态view显示出来
                mSecondView.setVisibility(View.VISIBLE);
                //播放第二状态的动画
                mSecondAnim.start();
                //第三状态view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //停止第三状态的动画
                mThirdAnim.stop();
                break;
            case PULL_TO_REFRESH://当前状态为下拉刷新
                //设置文字为下拉刷新
                mPullToRefreshTextView.setText("下拉刷新");
                //第一状态view显示出来
                mFirstView.setVisibility(View.VISIBLE);
                //第二状态view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //第二状态动画停止
                mSecondAnim.stop();
                //第三状态view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //第三状态动画停止
                mThirdAnim.stop();
                break;
            case REFRESHING://当前状态为正在刷新
                //文字设置为正在刷新
                mPullToRefreshTextView.setText("正在刷新");
                //第一状态view隐藏起来
                mFirstView.setVisibility(View.GONE);
                //第三状态view显示出来
                mThirdView.setVisibility(View.VISIBLE);
                //第二状态view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //停止第二状态动画
                mSecondAnim.stop();
                //启动第三状态view
                mThirdAnim.start();
                break;
            default:
                break;
        }
    }

    /*ViewGroup.getChildMeasureSpec(int spec, int padding, int childDimension)
    计算MeasureSpec然后传递到特定的子视图，此方法用来计算一个合适子视图的尺寸大小（宽度或者高度)，
    此方法的目的在于结合我们从子视图的LayoutParams所给出的MeasureSpec信息来获取最合适的结果。
    比如，如果这个View知道自己的大小尺寸（因为它本身的MeasureSpec的model为Exactly,）并且子视图的大小恰好跟父窗口一样大，父窗口必须用给定的大小去layout子视图
    参数：
    spec 父窗口传递给子视图的大小和模式
    padding 父窗口的边距，也就是xml中的android:padding
    childDimension 子视图想要绘制的准确大小，但最终不一定绘制此值*/

    /*区别:
    getChildMeasureSpec可以设置子View的内外边距。并且记录预定大小
    若spec，padding均为0，则子布局为实际大小。
    makeMeasureSpec(size,MeasureSpec.EXACTLY)得到的是size。
    makeMeasureSpec(size,MeasureSpec.UNSPECIFIED)得到的是子布局的实际大小。*/

    // 因为listView不限制高度。child有多高，listView就给它多高的空间。但是listView是限制宽度的，所以需要getChildMeasureSpec

    private void measureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        // 通过inflater.inflate(R.layout.header, null);将XML转化为View的时候，是没有设置View的LayoutParams的，就会进入到lp ==null条件中new一个LayoutParams
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // 调用 ViewGroup 的 getChildMeasureSpec 传递的第一个参数（父类的spec）竟然是0，
        // 传进0表示父类的spec是未指定的MeasureSpec.UNSPECIFIED模式，size也为0。那么该函数返回的值就是一个UNSPECIFIED类型size为0的值
        // 父View的详细测量值（即MeasureSpec），view的内外边距
        // MATCH_PARENT=-1,WRAP_CONTENT=-2
        KLog.e(lp.width + " --- " + lp.height);
        final int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, lp.width);
        // childWidthSpec出来后为：MeasureSpec.EXACTLY+lp.width
        final int lpHeight = lp.height;
        int childHeightSpec;
        // 和上面类似，如果lp.height > 0就表示设置的是一个精确值，接下来会封装一个EXACTLY和lp.height的值给childMeasureHeight，
        // 然后这里lp.height是wrap_content，他是个负值（-2），所以会封装一个未指定UNSPECIFIED和size为0的值给childMeasureHeight。
        // 即childMeasureWidth和childMeasureHeight两个参数均为0
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        KLog.e(childWidthSpec + ";" + childHeightSpec);
        // 类似child.measure(0,0)，0表示MeasureSpec.UNSPECIFIED让父View来决定它的大小
        child.measure(childWidthSpec, childHeightSpec);
    }

}
