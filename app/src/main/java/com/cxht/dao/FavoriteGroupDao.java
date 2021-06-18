package com.cxht.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.db.DBHelper;

public class FavoriteGroupDao {
	/**
	 * Çå¿Õ¼ÇÂ¼
	 * 
	 * @param context
	 */
	public static void deleteAll(Context context) {
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		db.execSQL("delete from t_favorite_group");
		db.close();
	}

}
