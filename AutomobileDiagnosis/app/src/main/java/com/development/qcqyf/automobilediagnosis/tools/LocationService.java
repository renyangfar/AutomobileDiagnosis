package com.development.qcqyf.automobilediagnosis.tools;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import java.util.List;

/**
 * 获取经纬度
 * 
 * @author LarryLong
 * 
 */
public class LocationService extends Service {
	private LocationManager locationManager;
	private MyLocationListener myLocationListener;
	// 广播发送intent
	private Intent intent = new Intent("com.example.communication.RECEIVER");

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 1.获取位置的管理者
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// 2.获取定位方式
		// 2.1获取所有的定位方式
		// 2.2获取最佳的定位方式
		Criteria criteria = new Criteria();
		// 2.1获取所有的定位方式
		// enabledOnly : true : 返回所有可用的定位方式
		List<String> providers = locationManager.getProviders(true);
		for (String string : providers) {
			System.out.println(string);
		}
		criteria.setAltitudeRequired(true);// 设置是否可以定位海拔,true:可以定位海拔,一定返回gps定位
		// criteria : 设置定位的属性,决定使用什么定位方式的
		// enabledOnly : true : 定位可用的就返回
		String bestProvider = locationManager.getBestProvider(criteria, true);
		System.out.println("最佳的定位方式:" +bestProvider);
		// 3.定位
		myLocationListener = new MyLocationListener();
		// provider : 定位方式
		// minTime : 定位的最小时间间隔
		// minDistance : 定位的最小距离间隔
		// listener : LocationListener
		locationManager.requestLocationUpdates(bestProvider, 0, 0,
				myLocationListener);
	}

	private class MyLocationListener implements LocationListener {
		// 当定位位置改变的时候调用
		// location : 当前的位置
		@Override
		public void onLocationChanged(Location location) {
			float latitude = (float) location.getLatitude();// 获取纬度,平行
			float longitude = (float) location.getLongitude();// 获取经度
			System.out.println("latitude:  " + latitude);
			System.out.println("longitude:  " + longitude);

			// 发送Action为com.example.communication.RECEIVER的广播
			intent.putExtra("latitude", (int)latitude);
			intent.putExtra("longitude", (int)longitude);
			sendBroadcast(intent);

			// 停止服务
			stopSelf();
		}

		// 当定位状态改变的时候调用
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		// 当定位可用的时候调用
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		// 当定位不可用的时候调用
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 关闭gps定位,高版本中已经不能这么做了,高版本中规定关闭和开启gps必须交由用户自己去实现
		locationManager.removeUpdates(myLocationListener);
	}
}
