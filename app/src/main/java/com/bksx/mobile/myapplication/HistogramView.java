package com.bksx.mobile.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class HistogramView extends View {
    /**
     * 自定义view实现柱状图
     * 首先定义一个类实现View
     */
    //定义画笔
    private Paint mLinePaint;
    private Paint mGreenPaint;
    private Paint mTextPaint;
    //定义上下文
    private Context mContext;
    //定义宽高
    private float weight;
    private float height;
    private float mScale;
    //这个数组是高度的值
    private String[] y_title = {"2000", "1500", "1000", "500", "0"};
    //分别为定义数据与数据源名称的集合
    private List<Long> mData;
    private List<String> mNames;
 
    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //给定义的画笔进行加工
        mContext = context;
        mLinePaint = new Paint();
        mGreenPaint = new Paint();
        mTextPaint = new Paint();
 
        mLinePaint.setARGB(255, 223, 233, 231);
        mTextPaint.setARGB(255, 153, 153, 153);
 
        mGreenPaint.setStyle(Paint.Style.FILL);
 
        mTextPaint.setAntiAlias(true);
        mGreenPaint.setAntiAlias(true);
        mLinePaint.setAntiAlias(true);
        mScale = context.getResources().getDisplayMetrics().density;
        //初始化数据
        mData = new ArrayList<>();
        mNames = new ArrayList<>();
 
    }
 
    //尺寸发生改变的时候调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        weight = 0.7F * w;
        height = 0.70F * h;
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float min_height = height / 5;
        for (int i = 4; i >= 0; i--) {
            if (i == 4) {
                mLinePaint.setARGB(255, 131, 148, 144);
                canvas.drawLine(70 * mScale, 30 * mScale + min_height * i, 70 * mScale + weight, 30 * mScale + min_height * i, mLinePaint);
            } else {
                mLinePaint.setARGB(255, 223, 233, 231);
                mLinePaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
                canvas.drawLine(70 * mScale, 30 * mScale + min_height * i, 70 * mScale + weight, 30 * mScale + min_height * i, mLinePaint);
            }
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
            canvas.drawText(y_title[i], 60 * mScale, 32 * mScale + min_height * i, mTextPaint);
        }
        float min_weight = (weight - 70 * mScale) / (mData.size());
        mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < mData.size(); i++) {
            int leftR = (int) (70 * mScale + i * min_weight + min_weight / 2);
            int rightR = leftR + (int) (min_weight / 2);
            int buttomR = (int) (30 * mScale + min_height * 4);
            int topR = buttomR - (int) (height / 100 * mData.get(i));
            if (i == 0) {
                mGreenPaint.setColor(Color.parseColor("#00EBEB"));
                canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mGreenPaint);
            }
            if (i == 1) {
                mGreenPaint.setColor(Color.parseColor("#62E0FF"));
                canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mGreenPaint);
            }
            if (i == 2) {
                mGreenPaint.setColor(Color.parseColor("#FDB33F"));
                canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mGreenPaint);
            }
            mTextPaint.setARGB(255, 51, 51, 51);
            canvas.drawText(mNames.get(i), leftR + min_weight / 4, buttomR + 20 * mScale, mTextPaint);
            mTextPaint.setARGB(255, 51, 51, 51);
            canvas.drawText(mData.get(i) + "", leftR + min_weight / 4, topR - 10 * mScale, mTextPaint);
        }
    }

    //传入数据并进行绘制
    public void updateThisData(List<Long> data, List<String> name) {
        mData = data;
        mNames = name;
        invalidate();
    }
}