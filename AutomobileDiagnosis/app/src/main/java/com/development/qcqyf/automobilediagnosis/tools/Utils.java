package com.development.qcqyf.automobilediagnosis.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

	/**
	 * ����Ļ������ʾһ��Toast
	 * @param text
	 */
	public static void showToast(Context context, CharSequence text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
