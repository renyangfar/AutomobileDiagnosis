package com.development.qcqyf.automobilediagnosis.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.tools.LocationService;


/**
 * 地理位置获取
 * 
 * @author LarryLong
 * 
 */
public class LocationForGPSActivity extends Activity {

	private MsgReceiver msgReceiver;
	private TextView tv_location_get;
	private IntentFilter intentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		tv_location_get = (TextView) findViewById(R.id.tv_location_get);
		// 动态注册广播接收器
		msgReceiver = new MsgReceiver();
		intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.communication.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);
	}

	/**
	 * 广播接收器
	 * 
	 * @author len
	 * 
	 */
	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int latitude = intent.getIntExtra("latitude", 0);
			int longitude = intent.getIntExtra("longitude", 0);
			tv_location_get.setText("latitude:" + latitude + "\n"
					+ "longitude:" + longitude);
		}
	}

	/**
	 * 获取经纬度
	 * 
	 * @param v
	 */
	public void get_location(View v) {
		Intent intent = new Intent(getApplicationContext(),
				LocationService.class);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 注销广播
		unregisterReceiver(msgReceiver);
	}

}
