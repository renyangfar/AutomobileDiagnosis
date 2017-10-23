package com.development.qcqyf.automobilediagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.development.qcqyf.automobilediagnosis.map.LocationForBaiduGPSActivity;
import com.development.qcqyf.automobilediagnosis.tools.CarNumQueryActivity;
import com.development.qcqyf.automobilediagnosis.tools.FaultNumQueryActivity;
import com.development.qcqyf.automobilediagnosis.tools.ProvinceCodeActivity;


public class AssistActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
     * 获取当前经纬度
     */
    public void getlocation(View v) {
        Intent intent = new Intent(getApplicationContext(),
                LocationForBaiduGPSActivity.class);
        startActivity(intent);
    }

    /*
     * 故障码查询
     */
    public void faultnum_query(View v) {
        Intent intent = new Intent(getApplicationContext(),
                FaultNumQueryActivity.class);
        startActivity(intent);
    }

    /*
     * 车牌号查询
     */
    public void car_num_query(View v) {
        Intent intent = new Intent(getApplicationContext(),
                CarNumQueryActivity.class);
        startActivity(intent);
    }	/*
     * 各省简称查询
	 */

    public void provinceCode_query(View v) {
        Intent intent = new Intent(getApplicationContext(),
                ProvinceCodeActivity.class);
        startActivity(intent);
    }

}
