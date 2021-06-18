package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Resume;

/**
 * 简历表DAO层
 * 
 * @author hejiaming
 * 
 */
public class ResumeDao {
	/**
	 * 获取人员简历信息
	 * @param userCode 身份证号
	 * @return
	 */
	public static List<Resume> getResumeList(String userCode) {

		List<Resume> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_resume where user_code='"+userCode+"' order by resume_id";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Resume>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	private static Resume pushData(Cursor cur) {
		Resume resume = new Resume();
		int resume_id = cur.getColumnIndex("resume_id");
		int user_id = cur.getColumnIndex("user_id");
		int start_time = cur.getColumnIndex("start_time");
		int over_time = cur.getColumnIndex("over_time");
		int job_desc = cur.getColumnIndex("job_desc");
		resume.setResume_id(cur.getInt(resume_id));
		resume.setUser_id(cur.getInt(user_id));
		resume.setStart_time(cur.getString(start_time));
		resume.setOver_time(cur.getString(over_time));
		resume.setJob_desc(cur.getString(job_desc));
		return resume;
	}

	/**
	 * 清空记录
	 * 
	 * @param context
	 */
	public static void deleteAll(Context context) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_resume");
		db.close();
	}
}
