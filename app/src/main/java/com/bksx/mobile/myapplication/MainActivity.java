package com.bksx.mobile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String strContent = "{\"returnCode\":\"200\",\"returnMsg\":\"查询数据成功\",\"returnData\":{\"yz\":\"300\",\"tgqs\":[{\"ry\":\"500\",\"sj\":\"2020年-03月-10日\",\"lz\":\"1\"},{\"ry\":\"1800\",\"sj\":\"2020年-03月-11日\",\"lz\":\"4\"},{\"ry\":\"1500\",\"sj\":\"2020年-03月-12日\",\"lz\":\"4\"},{\"ry\":\"7\",\"sj\":\"2020年-03月-13日\",\"lz\":\"4\"},{\"ry\":\"7\",\"sj\":\"2020年-03月-14日\",\"lz\":\"4\"},{\"ry\":\"7\",\"sj\":\"2020年-03月-15日\",\"lz\":\"4\"},{\"ry\":\"7\",\"sj\":\"2020年-03月-16日\",\"lz\":\"4\"},{\"ry\":\"8\",\"sj\":\"2020年-03月-17日\",\"lz\":\"4\"},{\"ry\":\"200\",\"sj\":\"2020年-03月-18日\",\"lz\":\"5\"},{\"ry\":\"1200\",\"sj\":\"2020年-03月-19日\",\"lz\":\"5\"},{\"ry\":\"800\",\"sj\":\"2020年-03月-20日\",\"lz\":\"5\"},{\"ry\":\"1200\",\"sj\":\"2020年-03月-21日\",\"lz\":\"5\"}],\"rkzs\":\"9\",\"xzlz\":\"0\",\"gzry\":\"2\",\"dqsj\":\"2020年03月21日 21:02\",\"clsl\":\"2\",\"gs\":[],\"zk\":\"3\",\"drzj\":\"0\",\"lzzs\":\"5\",\"xzrs\":\"0\",\"sq\":[{\"xxzs\":\"9\",\"xzry\":\"1\",\"mc\":\"三羊里一小区\",\"xzlz\":\"0\",\"lzdr\":\"0\"}]}}";
    private LineChartView mLineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Histogram histogram = findViewById(R.id.histogram);
        mLineChart = findViewById(R.id.histograms);
        Gson gson = new Gson();
        Bean bean = gson.fromJson(strContent, Bean.class);

        Log.i("TAG", "===onCreate: " + bean.getReturnMsg());
        List<Integer> xzrylist = new ArrayList<>();
        List<Integer> lzrylist = new ArrayList<>();
        List<String> bottomTitleList = new ArrayList<>();
        for (int i = 0; i < bean.getReturnData().getTgqs().size() ; i++) {
            xzrylist.add(Integer.parseInt(bean.getReturnData().getTgqs().get(i).getRy()));
            lzrylist.add(Integer.parseInt(bean.getReturnData().getTgqs().get(i).getLz()));
            bottomTitleList.add(bean.getReturnData().getTgqs().get(i).getSj());
        }
        mLineChart.setData(xzrylist,lzrylist,bottomTitleList,2000);
//        List<Integer> list = new ArrayList<>();
//        list.add(500);
//        list.add(1500);
//        list.add(2000);
//        List<String> lists = new ArrayList<>();
//        lists.add("业主");
//        lists.add("租户");
//        lists.add("工作人员");
        //histogram.setData(600,100);
       // histogram.setLayerType(View.LAYER_TYPE_SOFTWARE,null
       // );
    }
}
