package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Assess;

/**
 * 考核表DAO层
 * @author hejiaming
 *
 */
public class AssessDao {
	/**
	 * 获取奖惩列表
	 * @param userCode
	 * @return
	 */
	public static List<Assess> getAssessList(String userCode){
		List<Assess> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_assess where user_code='"+userCode+"' order by year desc";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Assess>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
		
	}

	private static Assess pushData(Cursor cur) {
		Assess assess = new Assess();
		int assess_id = cur.getColumnIndex("assess_id");
		int user_id = cur.getColumnIndex("user_id");
		int year = cur.getColumnIndex("year");
		int result = cur.getColumnIndex("result");
		assess.setAssess_id(cur.getInt(assess_id));
		assess.setUser_id(cur.getInt(user_id));
		assess.setYear(cur.getString(year));
		assess.setResult(cur.getString(result));
		return assess;
	}
	/**
	 * 清空记录
	 * @param context
	 */
	public static void deleteAll(Context context){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_assess");
		db.close();
	}
}
