package com.development.qcqyf.automobilediagnosis.map;

import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.List;

public class DrivingSearchActivity extends RoutePlanSearchBaseActivity {

	@Override
	public void routePlanSearchInit() {
		//获取导航路线起始地和目的地；
		Intent intent=getIntent();
		Bundle bundle = intent.getExtras();
		double[] location = bundle.getDoubleArray("location");
		LatLng start = new LatLng(location[0], location[1]);
		LatLng destination = new LatLng(location[2], location[3]);
		routePlanSearch.drivingSearch(getSearchParams(start,destination));
	}
	
	private DrivingRoutePlanOption getSearchParams(LatLng start,LatLng des) {
		DrivingRoutePlanOption params = new DrivingRoutePlanOption();
		List<PlanNode> nodes = new ArrayList<PlanNode>();
		//nodes.add(PlanNode.withCityNameAndPlaceName("桂林市", "航天工业学院"));
		params.from(PlanNode.withLocation(start));	// 璁剧疆璧风偣
		params.passBy(nodes);						// 璁剧疆閫旂粡鐐�
		params.to(PlanNode.withLocation(des));	// 璁剧疆缁堢偣
		return params;
	}

	/** 鑾峰彇椹捐溅鎼滅储缁撴灉鐨勫洖璋冩柟娉� */
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
		baiduMap.setOnMarkerClickListener(overlay);
		List<DrivingRouteLine> routeLines = result.getRouteLines();	// 鑾峰彇鍒版墍鏈夌殑鎼滅储璺嚎锛屾渶浼樺寲鐨勮矾绾夸細鍦ㄩ泦鍚堢殑鍓嶉潰
		overlay.setData(routeLines.get(0));	// 鎶婃悳绱㈢粨鏋滆缃埌瑕嗙洊鐗�
		overlay.addToMap();					// 鎶婃悳绱㈢粨鏋滄坊鍔犲埌鍦板浘
		overlay.zoomToSpan();				// 鎶婃悳绱㈢粨鏋滃湪涓�涓睆骞曞唴鏄剧ず瀹�
	}

	/** 鑾峰彇鎹箻锛堝叕浜ゃ�佸湴閾侊級鎼滅储缁撴灉鐨勫洖璋冩柟娉� */
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
	}

	/** 鑾峰彇姝ヨ鎼滅储缁撴灉鐨勫洖璋冩柟娉� */
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
	}

}
