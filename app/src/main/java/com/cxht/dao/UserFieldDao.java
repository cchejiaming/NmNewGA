package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.UserField;

/**
 * 用户自定义自段Dao层
 * 
 * @author hejiaming
 * 
 */
public class UserFieldDao {

	public static List<UserField> getUserField(int userId) {
		List<UserField> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user_field where user_id =" + userId;

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<UserField>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				UserField field = new UserField();
				int id = cur.getColumnIndex("id");
				int field_id = cur.getColumnIndex("field_id");
				int user_id = cur.getColumnIndex("user_id");
				int field_name = cur.getColumnIndex("field_name");
				int field_value = cur.getColumnIndex("field_value");
				field.setId(cur.getInt(id));
				field.setField_id(cur.getInt(field_id));
				field.setUser_id(cur.getInt(user_id));
				field.setField_name(cur.getString(field_name));
				field.setField_value(cur.getString(field_value));
				list.add(field);

			}
		}
		cur.close();
		db.close();
		return list;

	}

	/**
	 * 清空记录
	 * 
	 * @param context
	 */
	public static void deleteAll(Context context) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_user_field");
		db.close();

	}
}
