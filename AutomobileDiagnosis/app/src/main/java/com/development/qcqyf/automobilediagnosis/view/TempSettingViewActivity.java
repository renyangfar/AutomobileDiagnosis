package com.development.qcqyf.automobilediagnosis.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.SettingActivity;
public class TempSettingViewActivity extends AppCompatActivity {

	private EditText et_temp;
	private SharedPreferences sp;
	private int temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_over_temp);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		et_temp = (EditText) findViewById(R.id.et_temp);

		sp = getSharedPreferences(SettingActivity.CONFIG, MODE_PRIVATE);

		int temp_old = sp.getInt(SettingActivity.TEMP_LIMIT, 80);
		et_temp.setText(String.valueOf(temp_old));

	}

	public void temp_ok(View v) {
		String get_temp = et_temp.getText().toString();
		try {
			temp = Integer.parseInt(get_temp);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "请输入合法的数哦", 0).show();
			return;
		}
		Toast.makeText(getApplicationContext(),
				"设置的冷却液温度为： " + get_temp + "C", 0).show();
		Editor edit = sp.edit();
		edit.putInt(SettingActivity.TEMP_LIMIT, temp);
		edit.putBoolean(SettingActivity.CB_TEMP, true);

		edit.commit();

		finish();
	}

}
