package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.bean.HistoryBean;
import com.cxht.config.Setting;
import com.cxht.db.DBHelper;
import com.cxht.unit.StringUtil;

/**
 * ��ʷ��¼DAO��ʵ����
 * 
 * @author Administrator
 * 
 */
public class HistoryDao {
	/**
	 * ��ʷ��¼д�����ݿ�
	 * 
	 * @param context
	 * @param title
	 * @param id
	 */
	public static void wirteHistoryToBase(Context context, String title,
			int id, int type) {

		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		// ��¼�Ƿ���������ٽ�ֵ
		String sql = "select count(*) as num,min(history_id) as minid from t_history";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int num = cur.getColumnIndex("num");
				if (cur.getInt(num) > Setting.HISTORY_MAX) {
					// ������ɾ������ļ�¼
					int minId = cur.getColumnIndex("minid");
					db.delete("t_history", "history_id=" + cur.getInt(minId),
							null);

				}
			}
		}
		// д���ѯ��¼
		ContentValues cv = new ContentValues();
		cv.put("item_id", id);
		cv.put("item_title", title);
		cv.put("item_type", type);
		cv.put("add_time", StringUtil.getTime());
		db.insert("t_history", null, cv);
		cur.close();
		db.close();

	}

	/**
	 * ��ȡ�ղ��б�
	 * 
	 * @param context
	 * @param pageIndex
	 * @return
	 */
	public static List<HistoryBean> getHistoryList(Context context,
			int pageIndex) {
		List<HistoryBean> list = null;
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		String sql = "select * from t_history";
		sql += " order by history_id desc limit 20 offset 20*" + pageIndex;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<HistoryBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				HistoryBean hb = new HistoryBean();
				int history_id = cur.getColumnIndex("history_id");
				int item_title = cur.getColumnIndex("item_title");
				int item_id = cur.getColumnIndex("item_id");
				int item_type = cur.getColumnIndex("item_type");
				int add_time = cur.getColumnIndex("add_time");
				hb.setId(cur.getInt(history_id));
				hb.setItemId(cur.getInt(item_id));
				hb.setItemType(cur.getInt(item_type));
				hb.setItemTitle(cur.getString(item_title));
				hb.setAddTime(cur.getString(add_time));
				list.add(hb);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ɾ��������ʷ��¼
	 * 
	 * @param context
	 * @param id
	 */
	public static void deleteHistory(Context context, int id) {
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		db.delete("t_history", "history_id=" + id, null);
		db.close();
	}

	/**
	 * �����ʷ��¼
	 * 
	 * @param context
	 */
	public static void deleteAllHistory(Context context) {
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		db.execSQL("delete from t_history");
		db.close();
	}
}
