package com.development.qcqyf.automobilediagnosis.map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

public class Querry4sStore extends PoiSearchBaseActivity {

	@Override
	public void poiSearchInit() {
		poiSearch.searchNearby(getSearchParams(position));
	}

	private PoiNearbySearchOption getSearchParams(LatLng postion) {
		PoiNearbySearchOption params = new PoiNearbySearchOption();
		params.location(postion); //
		params.radius(50000); //
		params.keyword("4s店"); //
		return params;
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {

	}

	@Override
	protected void poiSearchInitInlocation() {
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(this);
		poiOverlay = new PoiOverlay(baiduMap) {
			@Override
			public boolean onPoiClick(int index) {
				return Querry4sStore.this.onPoiClick(index);
			}
		};
		baiduMap.setOnMarkerClickListener(poiOverlay);

	}

}
