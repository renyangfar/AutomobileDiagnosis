package com.development.qcqyf.automobilediagnosis.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.development.qcqyf.automobilediagnosis.R;


public abstract class PoiSearchBaseActivity extends BaiduLocationActivity implements OnGetPoiSearchResultListener {

	protected PoiSearch poiSearch;
	protected PoiOverlay poiOverlay;
	private View pop;
	private TextView tv_title;
	private ImageView ig_gohere;
	protected RoutePlanSearch routePlanSearch;
	private PoiInfo poiInfo;
	/**
	 */
	public boolean onPoiClick(int index) {
		poiInfo = poiOverlay.getPoiResult().getAllPoi().get(index);
		//showToast(poiInfo.name + ", " + poiInfo.address);
		//寮瑰嚭璇︽儏淇℃伅
		//public boolean onMarkerClick(Marker marker) {
			// 显示一个泡泡
			if (pop == null) {
				pop = View.inflate(this, R.layout.pop, null);
				tv_title = (TextView) pop.findViewById(R.id.tv_title);
				ig_gohere = (ImageView) pop.findViewById(R.id.ig_gohere);
				ig_gohere.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//进入导航
						//showToast("跳转到导航页面");
						Intent intent = new Intent(getApplicationContext(),DrivingSearchActivity.class);
			            Bundle bundle = new Bundle(); 
			            //获取当前位置的经纬度
			            double latitude = position.latitude;
			            double longitude = position.longitude;
			            //获取目的地的经纬度
			            double latitude2 = poiInfo.location.latitude;
			            double longitude2 = poiInfo.location.longitude;
			            
			            double[] location = new double[]{latitude,longitude,latitude2,longitude2};
			            bundle.putDoubleArray("location", location);
			            intent.putExtras(bundle);
			            startActivity(intent);						
					}
				});
				mapView.addView(pop, createLayoutParams(poiInfo.location));
				
			} else {
				mapView.updateViewLayout(pop, createLayoutParams(poiInfo.location));
			}
			tv_title.setText(poiInfo.name);
			return true;
	}
	
	
	/**
	 * 条目点击事件
	 */
	private void listviewItemClick() {

			
		}
	

	// 锟斤拷为锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷拇锟斤拷锟斤拷锟斤拷锟酵拷模锟斤拷锟斤拷苑锟斤拷诟锟斤拷锟�
	/** 锟斤拷取锟斤拷趣锟斤拷锟斤拷息 */
	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			showToast("没锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�");
			return;
		}
		
		poiOverlay.setData(result);	
		poiOverlay.addToMap();		
		poiOverlay.zoomToSpan();	
	}
/**
 * 创建一个布局参数
 * @param position
 * @return
 */
private MapViewLayoutParams createLayoutParams(LatLng position) {
	MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder(); 
	buidler.layoutMode(ELayoutMode.mapMode);	// 指定坐标类型为经纬度
	buidler.position(position);		// 设置标志的位置
	buidler.yOffset(-25);			// 设置View往上偏移
	MapViewLayoutParams params = buidler.build();
	return params;
}

}


