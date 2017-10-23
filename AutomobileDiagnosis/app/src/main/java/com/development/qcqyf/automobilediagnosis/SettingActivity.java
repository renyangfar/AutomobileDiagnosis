package com.development.qcqyf.automobilediagnosis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.development.qcqyf.automobilediagnosis.view.SpeedSettingViewActivity;
import com.development.qcqyf.automobilediagnosis.view.TempSettingViewActivity;
import com.development.qcqyf.automobilediagnosis.view.TiredSettingViewActivity;


/**
 * 设置中心
 * 
 * @author LarryLong
 * 
 */
public class SettingActivity extends AppCompatActivity{

	public static String SPEED_LIMIT = "speed_limit";
	public static String TIRED_LIMIT = "tired_limit";
	public static String TEMP_LIMIT = "temp_limit";
	public static String CB_SPEED = "cb_speed";
	public static String CB_TIRED = "cb_tired";
	public static String CB_TEMP = "cb_temp";
	public static String CONFIG = "config";

	private CheckBox cb_speed;
	private CheckBox cb_tired;
	private CheckBox cb_temp;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		cb_speed = (CheckBox) findViewById(R.id.cb_speed);
		cb_tired = (CheckBox) findViewById(R.id.cb_tired);
		cb_temp = (CheckBox) findViewById(R.id.cb_temp_water);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		update_checkBox();
		check_listener();

	}

	public void bt_over_speed(View v) {
		Intent intent = new Intent(getApplicationContext(),
				SpeedSettingViewActivity.class);
		startActivity(intent);
	}

	public void bt_tired_driver(View v) {
		Intent intent = new Intent(getApplicationContext(),
				TiredSettingViewActivity.class);
		startActivity(intent);
	}

	public void bt_temp_water(View v) {
		Intent intent = new Intent(getApplicationContext(),
				TempSettingViewActivity.class);
		startActivity(intent);
	}

	private void check_listener() {
		cb_speed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			Editor edit = sp.edit();

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					edit.putBoolean(CB_SPEED, true);
				} else {
					edit.putBoolean(CB_SPEED, false);
				}

				edit.commit();
			}
		});
		cb_temp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			Editor edit = sp.edit();

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					edit.putBoolean(CB_TEMP, true);
				} else {
					edit.putBoolean(CB_TEMP, false);
				}
				edit.commit();
			}
		});
		cb_tired.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			Editor edit = sp.edit();

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					edit.putBoolean(CB_TIRED, true);
				} else {
					edit.putBoolean(CB_TIRED, false);
				}
				edit.commit();
			}
		});

	}

	private void update_checkBox() {
		if (sp.getBoolean(CB_SPEED, false)) {
			cb_speed.setChecked(true);
		} else {
			cb_speed.setChecked(false);
		}
		if (sp.getBoolean(CB_TIRED, false)) {
			cb_tired.setChecked(true);
		} else {
			cb_tired.setChecked(false);
		}
		if (sp.getBoolean(CB_TEMP, false)) {
			cb_temp.setChecked(true);
		} else {
			cb_temp.setChecked(false);
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		update_checkBox();
	}

}
