package com.bksx.mobile.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Histogram extends View {
   int MAX = 0;//矩形显示的最大值
   int corner = 5; //矩形的角度。 设置为0 则没有角度。
   double data = 500.0;//显示的数
   double tempData,tempData2,tempData3; //初始数据
   int textPadding = 18; //字体与矩形图的距离

   int mColor;
   private Context mContext;
   private List<Integer> dataList = new ArrayList<>();
   //定义画笔绘制线
   private Paint mLinePaint;
   private Paint mTextPaint;
   private Paint mPaint;
   private String[] tagging = {"2000","1500","1000","500","0"};
   private List<String> title = new ArrayList<>();
   private int min_height;
   private int min_weight;

    //构造函数
   public Histogram(Context context) {
     super(context);
     mContext = context;
   }

   public Histogram(Context context, @Nullable AttributeSet attrs) {
     super(context, attrs);
     mContext = context;
     initPaint();
   }

   public Histogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
     super(context, attrs, defStyleAttr);
     mContext = context;
     initPaint();
   }

   //画笔方法
   private void initPaint() {
     mPaint = new Paint();
     mLinePaint = new Paint();
     mTextPaint = new Paint();
     mLinePaint.setAntiAlias(true);
     mTextPaint.setAntiAlias(true);
     mPaint.setAntiAlias(true);
     mColor = mContext.getResources().getColor(R.color.colorAccent);
     mPaint.setColor(mColor);
   }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        min_height = (int) (0.7 * h);
        min_weight = (int) (0.7 * w);
        Log.i("TAG", "===onSizeChanged: " + min_height);
    }
    @Override
   public void draw(Canvas canvas) {
    super.draw(canvas);
      drawLine(canvas);
      drawBottomText(canvas);
      drawPic(canvas);
//      if (data == 0.0) {
//        mPaint.setTextSize(getWidth() / 2);
//        RectF oval3 = new RectF(0, getHeight() - DensityUtil.px2dip(mContext, 20), getWidth(), getHeight());// 设置个新的长方形
//        canvas.drawRoundRect(oval3, DensityUtil.px2dip(mContext, corner),  DensityUtil.px2dip(mContext, corner), mPaint);
//
//        canvas.drawText("0",
//          getWidth() * 0.5f - mPaint.measureText("0") * 0.5f,
//          getHeight() -  DensityUtil.px2dip(mContext, 20) - 2 *  DensityUtil.px2dip(mContext, textPadding),
//          mPaint);
//        return;
//      }
//
   }

    private void drawPic(Canvas canvas) {
        //文本上坡度和下坡度 总和是文本的高度
        float textH = mPaint.ascent() + mPaint.descent() + textPadding;
        float MaxH = getHeight() - textH - 2 * min_height;
        for (int i = 0; i < dataList.size(); i++) {
            //防止数值很大的的时候，动画时间过长
            int step = dataList.get(i) / 100;
            int leftR = 20  + i * min_weight + min_weight / 2;
            int rightR = leftR + min_weight / 2;
            int buttomR = min_height * 5;
            //int topR = buttomR - (int) (hei / 100 * mData.get(i));

            if (i == 0){

                //圆角矩形的实际高度
                float realH = (float) (MaxH / MAX * tempData);
                if (tempData < dataList.get(i) - step) {
                    tempData = tempData + step;
                } else {
                    tempData = dataList.get(i);
                }
                //画圆角矩形
                String S = tempData + ""; //如果数字后面需要加% 则在""中添加%
                // 一个字和两,三个字的字号相同
                if (S.length() < 4) {
                    mPaint.setTextSize(getWidth()/2);
                } else {
                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
                }
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#00EBEB"));
                Log.i("TAG", "===drawPicTop: " + (getHeight() - realH -min_height));
                Log.i("TAG", "===drawPicBottom: " + buttomR);
                RectF oval = new RectF(leftR + 70, getHeight() - realH -min_height , rightR-70, buttomR);// 设置个新的长方形
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                canvas.drawRect(oval, mPaint);
                //写数字
                canvas.drawText(S,
                        leftR + 70f + mPaint.measureText(S) * 0.5f,
                  getHeight() - realH - min_height - 20,
                  mTextPaint);
                continue;
            }else if (i == 1){
                //圆角矩形的实际高度
                float realH2 = (int) (MaxH / MAX * tempData2);
                if (tempData2 < dataList.get(i) - step) {
                    tempData2 = tempData2 + step;
                } else {
                    tempData2 = dataList.get(i);
                }
                //画圆角矩形
                String S = tempData2 + ""; //如果数字后面需要加% 则在""中添加%
                // 一个字和两,三个字的字号相同
                if (S.length() < 4) {
                    mPaint.setTextSize(getWidth()/2);
                } else {
                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
                }
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#23E0FF"));
                RectF ova1 = new RectF(leftR + 70, getHeight() - realH2 - min_height , rightR-70, buttomR);// 设置个新的长方形
                canvas.drawRect(ova1,mPaint);
                //写数字
                canvas.drawText(S,
                        leftR + 70f + mPaint.measureText(S) * 0.5f,
                        getHeight() - realH2 - min_height - 20,
                        mTextPaint);
                if (tempData2 != dataList.get(i)) {
                    postInvalidate();
                }
                continue;
            }else if (i == 2){
                //圆角矩形的实际高度
                float realH3 = (float) (MaxH / MAX * tempData3);
                if (tempData3 < dataList.get(i) - step) {
                    tempData3 = tempData3 + step;
                } else {
                    tempData3 = dataList.get(i);
                }
                //画圆角矩形
                String S = tempData3 + ""; //如果数字后面需要加% 则在""中添加%
                // 一个字和两,三个字的字号相同
                if (S.length() < 4) {
                    mPaint.setTextSize(getWidth()/2);
                } else {
                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
                }
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#FDB33F"));
                //canvas.drawRect(new RectF(leftR, getHeight(), rightR, buttomR), mPaint);
                RectF oval = new RectF(leftR + 70, getHeight()-realH3-min_height , rightR-70, buttomR);// 设置个新的长方形
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                canvas.drawRect(oval, mPaint);
                //写数字
                canvas.drawText(S,
                        leftR + 70f + mPaint.measureText(S) * 0.5f,
                        getHeight() - realH3 - min_height - 20,
                        mTextPaint);
                if (tempData3 != dataList.get(i)) {
                    invalidate();
                }
                //continue;
            }
        }
    }

    private void drawBottomText(Canvas canvas) {
        min_weight = (getWidth() - 60 ) / (dataList.size());
        mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < dataList.size(); i++) {
            int leftR = (int) (20  + i * min_weight + min_weight / 2);
            int rightR = leftR + (int) (min_weight / 2);
            int buttomR = (int) (min_height * 5 + min_height*0.7);
            int topR = buttomR - (int) (getHeight() / 100 * dataList.get(i));
            mTextPaint.setARGB(255, 51, 51, 51);
            canvas.drawText(title.get(i), leftR + min_weight / 4, buttomR, mTextPaint);
        }
    }

    private void drawLine(Canvas canvas) {
     min_height = (getHeight()) / 6;
     for (int i = 5; i >= 1; i--) {
         if (i == 4) {
             mLinePaint.setColor(Color.parseColor("#000000"));
             canvas.drawLine(DensityUtil.dip2px(mContext,70),  min_height * i, getWidth(), min_height * i, mLinePaint);
         } else {
             mLinePaint.setColor(Color.parseColor("#000000"));
             //mLinePaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
             canvas.drawLine(DensityUtil.dip2px(mContext,70) ,  min_height * i,  getWidth(),  min_height * i, mLinePaint);
         }
         mTextPaint.setTextAlign(Paint.Align.RIGHT);
         mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
         canvas.drawText(tagging[i-1], DensityUtil.dip2px(mContext,65),  min_height * i + 15, mTextPaint);
     }
 }

    public void setData(double data, int MAX) {
         this.data = data;
         this.MAX = MAX;
         postInvalidate();
    }

   public void setData(List<Integer> data, List<String> listTitle ,int MAX) {
       this.data = data.get(0);
       dataList = data;
       title = listTitle;
       this.MAX = MAX;
       postInvalidate();
   }

   public int getmColor() {
        return mColor;
   }

   public void setmColor(int mColor) {
    this.mColor = mColor;
   }
}