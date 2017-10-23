package com.development.qcqyf.automobilediagnosis.tools;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.R;

public class CarNumQueryActivity extends JsonQuery {
	private EditText et_query_location;
	private TextView tv_home_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carnum_query);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		et_query_location = (EditText) findViewById(R.id.et_query_location);
		tv_home_location = (TextView) findViewById(R.id.tv_home_location);
	}

	public void queryLocation(View v) {
		// 1、得到查询号码
		String input = et_query_location.getText().toString().trim();
		// 2、判断号码是否为空
		if (TextUtils.isEmpty(input) || input.length() < 2) {
			// 为空
			Toast.makeText(getApplicationContext(), "亲 ，请输入正确的车牌号", Toast.LENGTH_SHORT).show();
			return;
		} else {
			String car_num = input.substring(0, 2);
			String result = parseJSONWithGSON(0,car_num);
			tv_home_location.setText(result);
		}

	}

}