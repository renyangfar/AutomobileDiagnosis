package com.development.qcqyf.automobilediagnosis.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class FaultCodeQuery {
	/**
	 * 打开数据库，查询故障码对应的中文含义
	 */
	public static String queryFaultCode(String code, Context context) {
		String allInformation;
		String toChinese = "";
		String des = "";
		// 1、获取数据库路径
		File file = new File(context.getFilesDir(), "faultcode.db");
		// 2、打开数据库
		// getAbsolutePath:获取文件的绝对路径
		SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(
				file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
		// 3、查询号码归属地
	/*	Cursor cursor = openDatabase
				.rawQuery(
						"select  chinese_definition from fault_code where fault_code = ?",
						new String[] { code });*/
		Cursor cursor =openDatabase.query("fault_code", new String[] { "chinese_definition","others" },
				"fault_code = ?", new String[] { code }, null, null, null);

		// 4、解析cursor
		if (cursor.moveToNext()) {
			toChinese = cursor.getString(0);
			des= cursor.getString(1);
		}
		cursor.close();
		openDatabase.close();
		allInformation = toChinese + "#" + des;
		return allInformation;

	}
}
