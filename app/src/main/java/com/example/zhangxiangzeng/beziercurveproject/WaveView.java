package com.example.zhangxiangzeng.beziercurveproject;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * <pre>
 *     e-mail : majingze@shuangke.net.cn
 *     time   : 2018/05/24
 *     desc   :
 *     version: 1.0
 *     Copyright: Copyright（c）2017
 *     Company:爽客智能设备有限公司
 * </pre>
 *
 * @author majingze
 */
public class WaveView extends View {
    //波浪画笔
    private Paint mPaint;
    //测试红点画笔
    private Paint mCyclePaint;

    //波浪Path类
    private Path mPath;
    //一个波浪长度
    private int mWaveLength = 1000;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int mOffset;
    //波纹的中间轴
    private int mCenterY;

    //屏幕高度
    private int mScreenHeight;
    //屏幕宽度
    private int mScreenWidth;

    private WaveState waveState = WaveState.HORIZONTAL;

    private ValueAnimator animator;

    private boolean isPlay = false;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //用来绘制测试红点
        mCyclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCyclePaint.setColor(Color.TRANSPARENT);
        mCyclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        initAnim();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        //加1.5：至少保证波纹有2个，至少2个才能实现平移效果
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
        mCenterY = mScreenHeight / 2 + 500;
    }
    private void initAnim(){
        animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                if (waveState == WaveState.UP) {
                    mCenterY -= 1;
                } else if(waveState == WaveState.DOWN){
                    mCenterY += 1;

                }

                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //移到屏幕外最左边
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            //正弦曲线
            mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mCenterY + 60, (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY);
            mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset, mCenterY - 60, i * mWaveLength + mOffset, mCenterY);
            //贝塞尔坐标，测试红点
            canvas.drawCircle((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mCenterY + 60, 5, mCyclePaint);
            canvas.drawCircle((-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY, 5, mCyclePaint);
            canvas.drawCircle((-mWaveLength / 4) + (i * mWaveLength) + mOffset, mCenterY - 60, 5, mCyclePaint);
            canvas.drawCircle(i * mWaveLength + mOffset, mCenterY, 5, mCyclePaint);
        }
        //填充矩形
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        if(isPlay)return;
        animator.start();
        isPlay = true;
    }

    public void stop() {
        if (!isPlay) {
            return;
        }
        animator.pause();
        isPlay = false;
    }

    public void up() {
        waveState = WaveState.UP;
    }

    public void down() {
        waveState = WaveState.DOWN;
    }
}
