package com.development.qcqyf.automobilediagnosis.tools;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.tools.FaultCodeQuery;


/**
 * 故障码查询
 * 
 * @author LarryLong
 * 
 */
public class FaultNumQueryActivity extends AppCompatActivity {
	private EditText et_querycode;
	private TextView faultCodeQuery;
	private TextView definition;
	private TextView des;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faultnum_query);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		et_querycode = (EditText) findViewById(R.id.et_querycode);
		faultCodeQuery = (TextView) findViewById(R.id.tv_fault_code);
		des = (TextView) findViewById(R.id.carheath_fault_des);
		definition = (TextView) findViewById(R.id.tv_toChinese);
	}

	/**
	 * 查询点击事件
	 * 
	 * @param v
	 */
	public void queryCode(View v) {
		// 1、得到查询号码
		String faultCode = et_querycode.getText().toString().trim();
		// 2、判断号码是否为空
		if (TextUtils.isEmpty(faultCode) || faultCode.length() != 5) {
			// 为空
			Toast.makeText(getApplicationContext(), "亲 ，请输入正确的查询故障码", 0).show();
			return;
		} else {
			String allFaultCodeInformation = FaultCodeQuery.queryFaultCode(faultCode,
					getApplicationContext());
			if (!TextUtils.isEmpty(allFaultCodeInformation)) {
				faultCodeQuery.setText(faultCode);
				definition.setText(allFaultCodeInformation.subSequence(0,
						allFaultCodeInformation.indexOf("#") ));
				des.setText(allFaultCodeInformation.substring(allFaultCodeInformation.indexOf("#")+1 ));
			}

		}

	}

}
