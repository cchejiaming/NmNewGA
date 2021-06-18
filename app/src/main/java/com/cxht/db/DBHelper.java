package com.cxht.db;

import com.cxht.config.GonganApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper mInstance = null;

	public synchronized static DBHelper getInstance(Context context) {

		if (mInstance == null) {
			mInstance = new DBHelper(context, "gnsys", null,
					GonganApplication.getAppVerisonCode());
		}
		return mInstance;
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS t_favorite_user (favorite_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "user_id INTEGER,add_time text)");
		db.execSQL("CREATE TABLE IF NOT EXISTS t_favorite_group (favorite_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "group_id INTEGER,add_time text)");
		db.execSQL("CREATE TABLE IF NOT EXISTS t_history (history_id INTEGER PRIMARY KEY AUTOINCREMENT,item_title text,"
				+ "item_id INTEGER,item_type INTEGER,add_time text)");
		db.execSQL("CREATE TABLE IF NOT EXISTS t_navi (navi_id INTEGER PRIMARY KEY AUTOINCREMENT,sort INTEGER,title text,"
				+ "image INTEGER,class_name text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
