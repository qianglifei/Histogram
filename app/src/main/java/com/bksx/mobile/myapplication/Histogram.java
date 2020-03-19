package com.bksx.mobile.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Histogram extends View {
   int MAX = 100;//矩形显示的最大值
   int corner = 5; //矩形的角度。 设置为0 则没有角度。
   double data = 0.0;//显示的数
   double tempData = 0; //初始数据
   int textPadding = 50; //字体与矩形图的距离

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
//    //防止数值很大的的时候，动画时间过长
//    int step = (int) (data / 100 + 1.0);
//    Log.i("TAG", "===draw: "+ step);
//    if (tempData < data - step) {
//     tempData = tempData + step;
//    } else {
//     tempData = data;
//    }
//    //画圆角矩形
//    String S = tempData + "%"; //如果数字后面需要加% 则在""中添加%
//
//    //一个字和两,三个字的字号相同
//    if (S.length() < 4) {
//     mPaint.setTextSize(getWidth()/2);
//    } else {
//     mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
//    }
//    //文本上坡度和下坡度 总和是文本的高度
//    float textH = mPaint.ascent() + mPaint.descent();
//    float MaxH = getHeight() - textH - 2 *  DensityUtil.px2dip(mContext, textPadding);
//    //圆角矩形的实际高度
//    float realH = (float) (MaxH / MAX * tempData);
//    RectF oval3 = new RectF(0, getHeight() - realH, getWidth(), getHeight());// 设置个新的长方形
//    canvas.drawRoundRect(oval3,  DensityUtil.px2dip(mContext, corner),  DensityUtil.px2dip(mContext, corner), mPaint);
//    //写数字
//    canvas.drawText(S,
//      getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
//      getHeight() - realH - 2 *  DensityUtil.px2dip(mContext, textPadding),
//      mPaint);
//     if (tempData != data) {
//         postInvalidate();
//     }
   }

    private void drawPic(Canvas canvas) {
        //防止数值很大的的时候，动画时间过长

        int step = (int) (data / 100 + 1.0);
        Log.i("TAG", "===draw: "+ step);
        if (tempData < data - step) {
            tempData = tempData + step;
        } else {
            tempData = data;
        }
        //画圆角矩形
        String S = tempData + "%"; //如果数字后面需要加% 则在""中添加%
        // 一个字和两,三个字的字号相同
        if (S.length() < 4) {
         mPaint.setTextSize(getWidth()/2);
        } else {
         mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
        }
        //文本上坡度和下坡度 总和是文本的高度
        float textH = mPaint.ascent() + mPaint.descent();
        float MaxH = getHeight() - textH - 2 *  DensityUtil.px2dip(mContext, textPadding);
        //圆角矩形的实际高度
        float realH = (float) (MaxH / MAX * tempData);

        for (int i = 0; i < dataList.size(); i++) {
            int leftR = 20  + i * min_weight + min_weight / 2;
            int rightR = leftR + min_weight / 2;
            int buttomR = 20  + min_height * 4;
            int topR = buttomR - (int) (getHeight() / 100 * dataList.get(i));
            if (i == 0){
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#00EBEB"));
                RectF oval = new RectF(leftR + 70, getHeight() - realH -40 , rightR-70, buttomR);// 设置个新的长方形
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                canvas.drawRoundRect(oval, DensityUtil.px2dip(mContext, corner),  DensityUtil.px2dip(mContext, corner), mPaint);
                //写数字
                canvas.drawText(S,
                  getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
                  getHeight() - realH - 2 *  DensityUtil.px2dip(mContext, textPadding),
                  mPaint);
            }else if (i == 1){
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#62E0FF"));
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                RectF oval = new RectF(leftR + 70, getHeight() - realH -40 , rightR-70, buttomR);// 设置个新的长方形
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                canvas.drawRoundRect(oval, DensityUtil.px2dip(mContext, corner),  DensityUtil.px2dip(mContext, corner), mPaint);
            }else if (i == 2){
                Log.i("TAG", "===drawPic: " + i);
                mPaint.setColor(Color.parseColor("#FDB33F"));
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                RectF oval = new RectF(leftR + 70, getHeight() - realH -40 , rightR-70, buttomR);// 设置个新的长方形
                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
                canvas.drawRoundRect(oval, DensityUtil.px2dip(mContext, corner),  DensityUtil.px2dip(mContext, corner), mPaint);
            }
        }
        if (tempData != data) {
            postInvalidate();
        }
    }

    private void drawBottomText(Canvas canvas) {
        float min_weight = (getWidth() - 70 ) / (dataList.size());
        mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < dataList.size(); i++) {
            int leftR = (int) (20  + i * min_weight + min_weight / 2);
            int rightR = leftR + (int) (min_weight / 2);
            int buttomR = (int) (20  + min_height * 4);
            int topR = buttomR - (int) (getHeight() / 100 * dataList.get(i));
            mTextPaint.setARGB(255, 51, 51, 51);
            canvas.drawText(title.get(i), leftR + min_weight / 4, buttomR + 30, mTextPaint);
//            mTextPaint.setARGB(255, 51, 51, 51);
//            canvas.drawText(title.get(i) + "", leftR + min_weight / 4, topR - 10, mTextPaint);
        }
    }

    private void drawLine(Canvas canvas) {
     min_height = getHeight() / 5;
     for (int i = 4; i >= 0; i--) {
         if (i == 4) {
             mLinePaint.setARGB(255, 131, 148, 144);
             canvas.drawLine(70 , 20  + min_height * i, 70  + getWidth(), 20  + min_height * i, mLinePaint);
         } else {
             mLinePaint.setARGB(255, 223, 233, 231);
             mLinePaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
             canvas.drawLine(70 , 20  + min_height * i, 70  + getWidth(), 20  + min_height * i, mLinePaint);
         }
         mTextPaint.setTextAlign(Paint.Align.RIGHT);
         mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
         canvas.drawText(tagging[i], 60, 32 + min_height * i, mTextPaint);
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