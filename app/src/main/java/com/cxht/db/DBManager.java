package com.cxht.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {

	private SQLiteDatabase database;
	private Context context;
	private String sdPath;// SD卡数据库路径

	public DBManager(Context context, String sdPath) {

		this.context = context;
		this.sdPath = sdPath;
	}

	public boolean openDatabase() {
		boolean result = false;
		this.database = this.openDatabase(GonganApplication.dataBassPath);
		if (this.database != null) {
			result = true;
		}
		return result;
	}

	public SQLiteDatabase openDatabase(String dbfile) {
		Log.i("Database", dbfile);
		try {
			if (!(new File(dbfile).exists())) {
				Log.i("Database", "is exists");
				File file = new File(sdPath);
				Log.i("Database", sdPath);
				InputStream is = new FileInputStream(file);// 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[Setting.BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
					null);
			return db;
		} catch (FileNotFoundException e) {
			Log.i("Database", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("Database", "IO exception");
			e.printStackTrace();
		}
		return null;
	}
}