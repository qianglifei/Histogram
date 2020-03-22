package com.bksx.mobile.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class LineChartView extends View {
   int MAX = 0;//矩形显示的最大值
   double data = 0.0;//显示的数
   int mColor;
   private Context mContext;
   //定义画笔绘制线
   private Paint mLinePaint;
   private Paint mLinePaint2;
   private Paint mTextPaint;
   private Paint mPaint;
    private Paint circlePaint;//圆点画笔
    private String[] tagging = {"","2000","1500","1000","500","0"};
   private List<Integer> xzryList = new ArrayList<>();
   private List<Integer> lzryList = new ArrayList<>();
   private List<String> bottomTitleList = new ArrayList<>();
   //存储定好的坐标点的集合
   private ArrayList<PointF> xyPointFList;
   private ArrayList<PointF> xyPointFLzList;
   //最小单位高度
   private float min_height;
   //最小单位宽度
   private float min_width;
   //测量出的控件总高度
   private float widgetSumHeight;
   //测量出的控件总宽度
   private float widgetSumWidth;
    //开始绘图x轴的位置
   private float beginDistance;
   private Path baseLinePath;//折线路径
   private Path baseLineLzPath;//折线路径
   private float smoothness = 0.05999f; //折线的弯曲率
    //构造函数
   public LineChartView(Context context) {
     super(context);
     mContext = context;
     initData();
   }

    private void initData() {
       beginDistance = DensityUtil.dip2px(mContext,100);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
     super(context, attrs);
     mContext = context;
     initPaint();
     initData();
    }

   public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
     super(context, attrs, defStyleAttr);
     mContext = context;
     initPaint();
     initData();
   }

   //画笔方法
   private void initPaint() {
     mLinePaint = new Paint();
     mLinePaint2 = new Paint();
     mTextPaint = new Paint();
     mLinePaint.setAntiAlias(true);
     mTextPaint.setAntiAlias(true);
     mLinePaint2.setAntiAlias(true);
     mLinePaint2.setStyle(Paint.Style.STROKE);
     mColor = mContext.getResources().getColor(R.color.colorAccent);
     mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     mPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2));
     mPaint.setStyle(Paint.Style.STROKE);
     mPaint.setStrokeCap(Paint.Cap.ROUND);
     mPaint.setColor(Color.parseColor("#17CAAA"));

     circlePaint = new Paint();
     circlePaint.setColor(Color.parseColor("#8BE4D4"));
   }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("TAG", "===onSizeChanged: " + min_height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widgetSumHeight = getMeasuredHeight();
        widgetSumWidth = getMeasuredWidth();
    }

    @Override
   public void draw(Canvas canvas) {
    super.draw(canvas);
      //绘制x轴网格线以及x轴对应的文字
      drawLine(canvas);
      //获取坐标点
      getXyPoint();
      //绘制曲线图
      drawCurve(canvas);
      //绘制底部文字
      drawBottomText(canvas);
   }

    private void drawBottomText(Canvas canvas) {
       min_height = widgetSumHeight / 6;
       mTextPaint.setColor(Color.parseColor("#333333"));
       canvas.drawText(bottomTitleList.get(0), beginDistance,min_height * 5 + DensityUtil.dip2px(mContext,20),mTextPaint);
       canvas.drawText(bottomTitleList.get(bottomTitleList.size() - 1), xyPointFList.get(xyPointFList.size() - 1).x,min_height * 5 + DensityUtil.dip2px(mContext,20),mTextPaint);
        Log.i("TAG", "===drawBottomText: " + bottomTitleList.get(0));
    }

    private void drawCurve(Canvas canvas) {
       if (xyPointFList == null || xyPointFList.size() == 0){
           return;
       }

       if (xyPointFLzList == null || xyPointFLzList.size() == 0){
           return;
       }

       List<PointF> newPoints = new ArrayList<>();
       newPoints.addAll(xyPointFList);

        float lX = 0;
        float lY = 0;
       baseLinePath = new Path();
//        Log.i("TAG", "===drawCurve: " + newPoints.size());
//        for (int i = 0; i < newPoints.size() ; i++) {
//           if (i == 0){
//               baseLinePath.moveTo(newPoints.get(i).x,newPoints.get(i).y);
//           }else {
//               baseLinePath.lineTo(newPoints.get(i).x,newPoints.get(i).y);
//           }
//       }
        baseLinePath.moveTo(newPoints.get(0).x, newPoints.get(0).y);
        for (int i = 1; i < newPoints.size(); i++) {
            PointF p = newPoints.get(i);
            PointF firstPointF = newPoints.get(i - 1);
            float x1 = firstPointF.x + lX;
            float y1 = firstPointF.y + lY;

            PointF secondPointF = newPoints.get(i + 1 < newPoints.size() ? i + 1 : i);
            lX = (secondPointF.x - firstPointF.x) / 2 * smoothness;
            lY = (secondPointF.y - firstPointF.y) / 2 * smoothness;
            float x2 = p.x - lX;
            float y2 = p.y - lY;
            if (y1 == p.y) {
                y2 = y1;
            }
            baseLinePath.cubicTo(x1, y1, x2, y2, p.x, p.y);
        }
        canvas.drawPath(baseLinePath, mPaint);
        //绘制最后坐标上的圆圈
        canvas.drawCircle(newPoints.get(newPoints.size() -1).x,
                newPoints.get(newPoints.size() -1).y,
                DensityUtil.dip2px(mContext,4),
                circlePaint);
        //绘制最后一个圆圈左边的文字
        mTextPaint.setColor(Color.parseColor("#00CC99"));
        canvas.drawText(
                String.valueOf(xzryList.get(xzryList.size() - 1)),
                newPoints.get(newPoints.size() -1).x + DensityUtil.dip2px(mContext,20),
                newPoints.get(newPoints.size() -1).y - DensityUtil.dip2px(mContext,10),
                mTextPaint);

        //绘制领证人员曲线图
        List<PointF> newLzPoints = new ArrayList<>();
        newLzPoints.addAll(xyPointFLzList);
        float lzlX = 0;
        float lzlY = 0;
        baseLineLzPath = new Path();
        baseLineLzPath.moveTo(newLzPoints.get(0).x, newLzPoints.get(0).y);
        for (int i = 1; i < newLzPoints.size(); i++) {
            PointF p = newLzPoints.get(i);
            PointF firstPointF = newLzPoints.get(i - 1);
            float x1 = firstPointF.x + lzlX;
            float y1 = firstPointF.y + lzlY;

            PointF secondPointF = newLzPoints.get(i + 1 < newLzPoints.size() ? i + 1 : i);
            lX = (secondPointF.x - firstPointF.x) / 2 * smoothness;
            lY = (secondPointF.y - firstPointF.y) / 2 * smoothness;
            float x2 = p.x - lX;
            float y2 = p.y - lY;
            if (y1 == p.y) {
                y2 = y1;
            }
            baseLineLzPath.cubicTo(x1, y1, x2, y2, p.x, p.y);
        }
        mPaint.setColor(Color.parseColor("#FFCC00"));
        canvas.drawPath(baseLineLzPath, mPaint);
        circlePaint.setColor(Color.parseColor("#FFCC00"));
        //绘制最后坐标上的圆圈
        canvas.drawCircle(newLzPoints.get(newLzPoints.size() -1).x,
                newLzPoints.get(newLzPoints.size() -1).y,
                DensityUtil.dip2px(mContext,4),
                circlePaint);
        //绘制最后一个圆圈左边的文字
        mTextPaint.setColor(Color.parseColor("#FFCC00"));
        canvas.drawText(
                String.valueOf(lzryList.get(lzryList.size() - 1)),
                newLzPoints.get(newPoints.size() -1).x + DensityUtil.dip2px(mContext,20),
                newLzPoints.get(newPoints.size() -1).y - DensityUtil.dip2px(mContext,10),
                mTextPaint);
    }

    private void getXyPoint() {
       //获取新增人员的坐标集合
       xyPointFList = new ArrayList<>();
       min_width = (widgetSumWidth - beginDistance) / bottomTitleList.size();
       min_height = widgetSumHeight / 6;
        for (int i = 0; i < bottomTitleList.size(); i++) {
            float x = beginDistance + min_width * i;
            float y = min_height * 5 - (widgetSumHeight - 2 * min_height)/MAX* xzryList.get(i);
            xyPointFList.add(new PointF(x,y));
            Log.i("TAG", "===getXyPoint:X " + x);
            Log.i("TAG", "===getXyPoint:Y " + y);
        }
        Log.i("TAG", "===getXyPoint: " + bottomTitleList.size());
        //获取领证人员的坐标集合
        xyPointFLzList = new ArrayList<>();
        for (int i = 0; i < bottomTitleList.size(); i++) {
            float x = beginDistance + min_width * i;
            float y = min_height * 5 - (widgetSumHeight - 2 * min_height)/MAX* lzryList.get(i);
            xyPointFLzList.add(new PointF(x,y));
            Log.i("TAG", "===getXyPoint:X " + x);
            Log.i("TAG", "===getXyPoint:Y " + y);
        }

    }

//    private void drawPic(Canvas canvas) {
//        //文本上坡度和下坡度 总和是文本的高度
//        float textH = mPaint.ascent() + mPaint.descent() + textPadding;
//        float MaxH = getHeight() - textH - 2 * min_height;
//        for (int i = 0; i < dataList.size(); i++) {
//            //防止数值很大的的时候，动画时间过长
//            int step = dataList.get(i) / 100;
//            int leftR = 20  + i * min_weight + min_weight / 2;
//            int rightR = leftR + min_weight / 2;
//            int buttomR = min_height * 5;
//            if (i == 0){
//                //圆角矩形的实际高度
//                float realH = (float) (MaxH / MAX * tempData);
//                if (tempData < dataList.get(i) - step) {
//                    tempData = tempData + step;
//                } else {
//                    tempData = dataList.get(i);
//                }
//                //画圆角矩形
//                String S = tempData + ""; //如果数字后面需要加% 则在""中添加%
//                // 一个字和两,三个字的字号相同
//                if (S.length() < 4) {
//                    mPaint.setTextSize(getWidth()/2);
//                } else {
//                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
//                }
//                Log.i("TAG", "===drawPic: " + i);
//                mPaint.setColor(Color.parseColor("#00EBEB"));
//                Log.i("TAG", "===drawPicTop: " + (getHeight() - realH -min_height));
//                Log.i("TAG", "===drawPicBottom: " + buttomR);
//                RectF oval = new RectF(leftR + 70, getHeight() - realH -min_height , rightR-70, buttomR);// 设置个新的长方形
//                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
//                canvas.drawRect(oval, mPaint);
//                //写数字
//                canvas.drawText(S,
//                        leftR + DensityUtil.dip2px(mContext,8) + mPaint.measureText(S) * 0.5f,
//                  getHeight() - realH - min_height - 20,
//                  mTextPaint);
//                if (tempData != dataList.get(i)) {
//                    postInvalidate();
//                }
//                continue;
//            }else if (i == 1){
//                //圆角矩形的实际高度
//                float realH2 = (int) (MaxH / MAX * tempData2);
//                if (tempData2 < dataList.get(i) - step) {
//                    tempData2 = tempData2 + step;
//                } else {
//                    tempData2 = dataList.get(i);
//                }
//                //画圆角矩形
//                String S = tempData2 + ""; //如果数字后面需要加% 则在""中添加%
//                // 一个字和两,三个字的字号相同
//                if (S.length() < 4) {
//                    mPaint.setTextSize(getWidth()/2);
//                } else {
//                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
//                }
//                Log.i("TAG", "===drawPic: " + i);
//                mPaint.setColor(Color.parseColor("#23E0FF"));
//                RectF ova1 = new RectF(leftR + 70, getHeight() - realH2 - min_height , rightR-70, buttomR);// 设置个新的长方形
//                canvas.drawRect(ova1, mPaint);
//                //写数字
//                canvas.drawText(S,
//                        leftR + DensityUtil.dip2px(mContext,8) + mPaint.measureText(S) * 0.5f,
//                        getHeight() - realH2 - min_height - 20,
//                        mTextPaint);
//                if (tempData2 != dataList.get(i)) {
//                  postInvalidate();
//                }
//                continue;
//            }else if (i == 2){
//                //圆角矩形的实际高度
//                float realH3 = (float) (MaxH / MAX * tempData3);
//                if (tempData3 < dataList.get(i) - step) {
//                    tempData3 = tempData3 + step;
//                } else {
//                    tempData3 = dataList.get(i);
//                }
//                //画圆角矩形
//                String S = tempData3 + ""; //如果数字后面需要加% 则在""中添加%
//                // 一个字和两,三个字的字号相同
//                if (S.length() < 4) {
//                    mPaint.setTextSize(getWidth()/2);
//                } else {
//                    mPaint.setTextSize(30); //可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
//                }
//                Log.i("TAG", "===drawPic: " + i);
//                mPaint.setColor(Color.parseColor("#FDB33F"));
//                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
//                RectF oval = new RectF(leftR + 70, getHeight() - realH3 -min_height , rightR-70, buttomR);// 设置个新的长方形
//                //canvas.drawRect(new RectF(leftR, topR, rightR, buttomR), mPaint);
//                canvas.drawRect(oval,mPaint);
//                //写数字
//                canvas.drawText(S,
//                        leftR + DensityUtil.dip2px(mContext,8) + mPaint.measureText(S) * 0.5f,
//                        getHeight() - realH3 - min_height - 20,
//                        mTextPaint);
//                if (tempData3 != dataList.get(i)) {
//                    postInvalidate();
//                }
//                continue;
//            }
//        }
//    }
//
//    private void drawBottomText(Canvas canvas) {
//        min_weight = (getWidth() - 60 ) / (dataList.size());
//        mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
//        mTextPaint.setTextAlign(Paint.Align.CENTER);
//        for (int i = 0; i < dataList.size(); i++) {
//            int leftR = (int) (20  + i * min_weight + min_weight / 2);
//            int rightR = leftR + (int) (min_weight / 2);
//            int buttomR = (int) (min_height * 5 + min_height*0.7);
//            int topR = buttomR - (int) (getHeight() / 100 * dataList.get(i));
//            mTextPaint.setARGB(255, 51, 51, 51);
//            canvas.drawText(title.get(i), leftR + min_weight / 4, buttomR, mTextPaint);
//        }
//    }

    private void drawLine(Canvas canvas) {
     min_height = (getHeight()) / 6;
     for (int i = 5; i >= 1; i--) {
         if (i == 5) {
             mLinePaint.setColor(Color.parseColor("#000000"));
             canvas.drawLine(DensityUtil.dip2px(mContext,70),  min_height * i, getWidth(), min_height * i, mLinePaint);
         } else {
             mLinePaint2.setColor(Color.parseColor("#999999"));
             mLinePaint2.setPathEffect(new DashPathEffect(new float[] {5, 5}, 0));
             canvas.drawLine(DensityUtil.dip2px(mContext,70) ,  min_height * i,  getWidth(),  min_height * i, mLinePaint2);
         }
     }
    mTextPaint.setTextAlign(Paint.Align.RIGHT);
    mTextPaint.setColor(Color.parseColor("#999999"));
    mTextPaint.setTextSize(DensityUtil.dip2px(mContext,16));
    for (int i = 5; i >= 0; i--) {
        if (i == 5){
            canvas.drawText(tagging[i], DensityUtil.dip2px(mContext,65),  min_height * i, mTextPaint);
        }else {
            canvas.drawText(tagging[i], DensityUtil.dip2px(mContext,65),  min_height * i + DensityUtil.dip2px(mContext,5), mTextPaint);
        }
    }
 }

   public void setData(List<Integer> xzrylist, List<Integer> lzryList , List<String> bottomTitleList,int MAX) {
       this.xzryList = xzrylist;
       this.lzryList = lzryList;
       this.bottomTitleList = getTime(bottomTitleList);
       this.MAX = MAX;
       invalidate();
   }

    private List<String> getTime(List<String> bottomTitleList) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < bottomTitleList.size() ; i++) {
            String strDate = bottomTitleList.get(i);
            StringBuffer buffer = new StringBuffer();
//            buffer.append(strDate.substring(2,4));
//            buffer.append(".");
            buffer.append(strDate.substring(6,8));
            buffer.append(".");
            buffer.append(strDate.substring(10,12));
            stringList.add(buffer.toString());
        }
        return stringList;
    }

}