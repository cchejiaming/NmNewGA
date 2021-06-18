package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Examine;

/**
 * ������DAO��
 * 
 * @author hejiaming
 * 
 */
public class ExamineDao {
	/**
	 * �����б�
	 * @param userCode ���֤��
	 * @return
	 */
	public static List<Examine> getExamineList(String userCode) {

		List<Examine> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_examine where user_code='" + userCode+"' order by examine_time desc";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Examine>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}
  /**
   * �������
   * @param cur
   * @return
   */
	private static Examine pushData(Cursor cur) {
		Examine examine = new Examine();
		int examine_id = cur.getColumnIndex("examine_id");
		int user_id = cur.getColumnIndex("user_id");
		int examine_name = cur.getColumnIndex("examine_name");
		int examine_time = cur.getColumnIndex("examine_time");
		examine.setExamine_id(cur.getInt(examine_id));
		examine.setUser_id(cur.getInt(user_id));
		examine.setExamine_name(cur.getString(examine_name));
		examine.setExamine_time(cur.getString(examine_time));
		return examine;
	}
	/**
	 * ��ռ�¼
	 * @param context
	 */
	public static void deleteAll(Context context){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_examine");
		db.close();
	}
}
