package com.bksx.mobile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Histogram histogram = findViewById(R.id.histogram);
        List<Integer> list = new ArrayList<>();
        list.add(500);
        list.add(1500);
        list.add(2000);
        List<String> lists = new ArrayList<>();
        lists.add("业主");
        lists.add("租户");
        lists.add("工作人员");
        //histogram.setData(600,100);
        histogram.setData(list,lists,2000);
    }
}
