package com.development.qcqyf.automobilediagnosis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.development.qcqyf.automobilediagnosis.http.ATCommand;
import com.development.qcqyf.automobilediagnosis.http.MyHttpUtil;

import java.util.HashMap;

public class MycarActivity extends MyHttpUtil {

    private TextView mycar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        mycar = (TextView) findViewById(R.id.tv_mycar);

        // 获取汽车故障码
        HashMap<String, String> params = new HashMap<>();
        params.put(ATCommand.STYLE, ATCommand.CARID);
        sendCommandToWeb(params);
        progressBarDisplay(this);
    }

    @Override
    public void getResponse(String response) {
        mycar.setText(response);
    }

    @Override
    public void getBadResponse(String response) {
        finish();

    }
}
