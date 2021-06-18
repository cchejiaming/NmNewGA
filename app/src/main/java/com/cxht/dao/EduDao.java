package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;

public class EduDao {

	/**
	 * 获取学历文字列表
	 * @return
	 */
	public static List<String> getEduList(){
		List<String> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_edu";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<String>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                 int edu = cur.getColumnIndex("edu_name");
				 list.add(cur.getString(edu));

			}
		}
		cur.close();
		db.close();
		return list;
		
	}
}
