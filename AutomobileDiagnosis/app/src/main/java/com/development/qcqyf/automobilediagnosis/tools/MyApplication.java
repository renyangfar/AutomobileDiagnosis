package com.development.qcqyf.automobilediagnosis.tools;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		//��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
        //ע��÷���Ҫ��setContentView����֮ǰʵ��  
        SDKInitializer.initialize(getApplicationContext());
	}
	
}
