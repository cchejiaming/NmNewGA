package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.app.FavoriteGroupActivity;
import com.cxht.app.FavoriteUserActivity;
import com.cxht.app.GroupSearchActivity;
import com.cxht.app.HistoryListActivity;
import com.cxht.app.StatisticsClassActivity;
import com.cxht.app.SystemSetActivity;
import com.cxht.app.UserSearchActivity;
import com.cxht.db.DBHelper;
import com.cxht.entity.NaviClass;

public class NaviDao {
	/**
	 * 添加导航菜单到数据库
	 * 
	 * @param context
	 * @param list
	 */
	public static void AddNaviListToData(Context context, List<NaviClass> list) {
		if (list != null && list.size() > 0) {
			// 插入数据到数据库
			DBHelper helper = DBHelper.getInstance(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			for (int i = 0; i < list.size(); i++) {
				ContentValues cv = new ContentValues();
				cv.put("sort", list.get(i).getSort());
				cv.put("title", list.get(i).getTitle());
				cv.put("image", list.get(i).getImage());
				if (list.get(i).getNavi() != null) {
					cv.put("class_name", list.get(i).getNavi().getName());
				}
				db.insert("t_navi", null, cv);
				// Log.i("Dao","insert:"+in);
			}

			db.close();
		}
	}

	/**
	 * 获取导航列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<NaviClass> GetNaviMenuList(Context context) {
		List<NaviClass> list = null;
		DBHelper helper = DBHelper.getInstance(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from t_navi order by sort";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<NaviClass>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				NaviClass navi = new NaviClass();
				navi.setImage(cur.getInt(cur.getColumnIndex("image")));
				navi.setSort(cur.getInt(cur.getColumnIndex("sort")));
				navi.setTitle(cur.getString(cur.getColumnIndex("title")));
				navi.setNavi(GetNaviClass(cur.getString(cur
						.getColumnIndex("class_name"))));
				list.add(navi);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static Class<? extends Activity> GetNaviClass(String className) {
		Class<? extends Activity> activity = null;
		if (className == null) {
			return null;
		}
		if (className.equals("com.gongan.app.UserSearchActivity")) {
			activity = UserSearchActivity.class;
		}
		if (className.equals("com.gongan.app.StatisticsClassActivity")) {
			activity = StatisticsClassActivity.class;
		}
		if (className.equals("com.gongan.app.FavoriteUserActivity")) {
			activity = FavoriteUserActivity.class;
		}
		if (className.equals("com.gongan.app.GroupSearchActivity")) {
			activity = GroupSearchActivity.class;
		}
		if (className.equals("com.gongan.app.SystemSetActivity")) {
			activity = SystemSetActivity.class;
		}
		if (className.equals("com.gongan.app.FavoriteGroupActivity")) {
			activity = FavoriteGroupActivity.class;
		}
		if (className.equals("com.gongan.app.HistoryListActivity")) {
			activity = HistoryListActivity.class;
		}

		return activity;
	}
}
