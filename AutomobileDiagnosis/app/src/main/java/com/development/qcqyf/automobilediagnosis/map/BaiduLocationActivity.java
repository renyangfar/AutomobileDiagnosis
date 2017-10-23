package com.development.qcqyf.automobilediagnosis.map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.development.qcqyf.automobilediagnosis.R;

import java.util.List;

public abstract class BaiduLocationActivity extends BaseActivity {

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	LatLng position; //
	protected boolean isSuccessPosition = false;

	// ����λ�ɹ�����ô�handler
	protected Handler querryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			poiSearchInit();
		}

	};

	@Override
	public void init() {
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		initLocation();
		baiduMap.setMyLocationEnabled(true); // ������λͼ��
		setMyLocationConfigeration(MyLocationConfiguration.LocationMode.COMPASS);
		mLocationClient.start(); // ��ʼ��λ

		poiSearchInitInlocation();
	}

	// Poi������ʼ��
	protected abstract void poiSearchInitInlocation();

	/** poi�����ĳ�ʼ������д��������� */
	public abstract void poiSearchInit();

	public class MyLocationListener implements BDLocationListener {

		// ���������������ն�λ���
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null) {

				MyLocationData.Builder builder = new MyLocationData.Builder();
				builder.accuracy(location.getRadius()); // ���þ���
				builder.direction(location.getDirection()); // ���÷���
				builder.latitude(location.getLatitude()); // ����γ��
				builder.longitude(location.getLongitude()); // ���þ���
				//showToast(String.valueOf(location.getLongitude())+","+String.valueOf(location.getLatitude()));
				
				showToast(location.getAddrStr()+'('+String.valueOf(location.getLongitude())+","+String.valueOf(location.getLatitude())+')');
				MyLocationData locationData = builder.build();
				baiduMap.setMyLocationData(locationData); // �Ѷ�λ������ʾ����ͼ��
				if (!isSuccessPosition) {
					// ��־λ��λ
					isSuccessPosition = true;
					// ����������Ȥ��
					position = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
							.newLatLng(position);
					baiduMap.setMapStatus(mapStatusUpdate);
					querryHandler.sendEmptyMessage(0);
				}

			}

			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// ��λ������ÿСʱ
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// ��λ����
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// ��λ��
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps��λ�ɹ�");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("���綨λ�ɹ�");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
				sb.append("\ndescribe : ");
				sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
			}
			sb.append("\nlocationdescribe : ");
			sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
			List<Poi> list = location.getPoiList();// POI����
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_1: // ����̬����ʾ��λ����Ȧ�����ֶ�λͼ���ڵ�ͼ����
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.COMPASS);
			break;
		case KeyEvent.KEYCODE_2: // ����̬�����ֶ�λͼ���ڵ�ͼ����
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.FOLLOWING);
			break;
		case KeyEvent.KEYCODE_3: // ��̬ͨ�� ���¶�λ����ʱ���Ե�ͼ���κβ���
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.NORMAL);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/** ���ö�λͼ������� */
	private void setMyLocationConfigeration(
			MyLocationConfiguration.LocationMode mode) {
		boolean enableDirection = true; // ����������ʾ����
		BitmapDescriptor customMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_geo); // �Զ��嶨λ��ͼ��
		MyLocationConfiguration config = new MyLocationConfiguration(mode,
				enableDirection, customMarker);
		baiduMap.setMyLocationConfigeration(config);
	}

	@Override
	protected void onDestroy() {
		mLocationClient.stop(); // ֹͣ��λ
		super.onDestroy();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 10000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

}
