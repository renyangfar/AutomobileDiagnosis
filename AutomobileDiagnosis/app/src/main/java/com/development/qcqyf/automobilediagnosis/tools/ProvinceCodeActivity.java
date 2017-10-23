package com.development.qcqyf.automobilediagnosis.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.R;

import static com.development.qcqyf.automobilediagnosis.R.id.et_query_location;

public class ProvinceCodeActivity extends JsonQuery {

    private EditText et_query_provinceCode;
    private TextView tv_province_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);

        et_query_provinceCode = (EditText) findViewById(R.id.et_query_provinceCode);
        tv_province_code = (TextView) findViewById(R.id.tv_province_code);
    }

    public void queryProvinceCode(View v) {
        // 1、得到查询号码
        String input = et_query_provinceCode.getText().toString().trim();
        // 2、判断号码是否为空
        if (TextUtils.isEmpty(input) || input.length() < 2) {
            // 为空
            Toast.makeText(getApplicationContext(), "亲 ，请输入正确的省市", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String province = input.substring(0, 2);
            String result = parseJSONWithGSON(1,province);
            tv_province_code.setText(result);
        }

    }
}
