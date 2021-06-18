package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Family;

/**
 * 家庭成员DAO层
 * 
 * @author hejiaming
 * 
 */
public class FamilyDao {
	/**
	 * 获取成员列表
	 *
	 * @param userCode
	 * @return
	 */
	public static List<Family> getFamilyList(String userCode) {
		List<Family> list = null;

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_family where user_code ='"+userCode+"'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Family>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				list.add(pushData(cur));
			}
		}
		cur.close();
		db.close();
		return list;

	}

	private static Family pushData(Cursor cur) {

		Family family = new Family();
		int family_id = cur.getColumnIndex("family_id");
		int user_id = cur.getColumnIndex("user_id");
		int title = cur.getColumnIndex("title");
		int family_name = cur.getColumnIndex("family_name");
		int birth_date = cur.getColumnIndex("birth_date");
		int politics_status = cur.getColumnIndex("politics_status");
		int job_desc = cur.getColumnIndex("job_desc");
		family.setFamily_id(cur.getInt(family_id));
		family.setUser_id(cur.getInt(user_id));
		family.setTitle(cur.getString(title));
		family.setFamily_name(cur.getString(family_name));
		family.setBirth_date(cur.getString(birth_date));
		family.setPolitics_status(cur.getString(politics_status));
		family.setJob_desc(cur.getString(job_desc));
		return family;
	}
	/**
	 * 清空记录
	 * @param context
	 */
	public static void deleteAll(Context context){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_family");
		db.close();
	}
}
