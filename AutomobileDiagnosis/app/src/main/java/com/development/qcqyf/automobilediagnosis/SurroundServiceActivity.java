package com.development.qcqyf.automobilediagnosis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.map.Querry4sStore;
import com.development.qcqyf.automobilediagnosis.map.QuerryGasStation;

/**
 * 周边服务
 * @author LarryLong
 *
 */

public class SurroundServiceActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surround_service);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}
	
	/**
	 * ������븽������վ
	 * @param v
	 */
	public void gas_station(View v){
		Intent intent = new Intent(this,QuerryGasStation.class);
		startActivity(intent);
	}
	/**
	 * ������븽��4sά�޵�
	 * @param v
	 */
	public void four_s_store(View v){
		Intent intent = new Intent(this,Querry4sStore.class);
		startActivity(intent);
	}

}
