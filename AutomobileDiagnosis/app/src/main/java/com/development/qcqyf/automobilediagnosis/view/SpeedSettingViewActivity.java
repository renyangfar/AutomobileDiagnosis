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


public class SpeedSettingViewActivity extends AppCompatActivity {

	private EditText et_speed;
	private SharedPreferences sp;
	private int speed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_over_speed);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		et_speed = (EditText) findViewById(R.id.et_speed);

		sp = getSharedPreferences(SettingActivity.CONFIG, MODE_PRIVATE);

		int speed_old = sp.getInt(SettingActivity.SPEED_LIMIT, 80);
		et_speed.setText(String.valueOf(speed_old));

	}

	public void speed_ok(View v) {
		String get_speend = et_speed.getText().toString();
		try {
			speed = Integer.parseInt(get_speend);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "请输入合法的数哦", 0).show();
			return;
		}
		Toast.makeText(getApplicationContext(),
				"设置的速度为： " + get_speend + "KM/H", 0).show();
		Editor edit = sp.edit();
		edit.putInt(SettingActivity.SPEED_LIMIT, speed);
		edit.putBoolean(SettingActivity.CB_SPEED, true);

		edit.commit();

		finish();
	}

}
