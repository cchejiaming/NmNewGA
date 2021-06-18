package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cxht.bean.RosterRow;
import com.cxht.config.GonganApplication;
import com.cxht.entity.Job;
import com.cxht.entity.User;

/**
 * 职务集操作DAO层
 */
public class JobDao {
	/**
	 * 获取主要职务信息
	 * @param userCode 身份证号码
	 * @return 职务实体类
	 */
  public static Job getMainJob(String userCode){
  	Job job = null;
  	if (userCode!=null){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_job where user_code = '"+userCode+"'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			job = new Job();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int idColumn = cur.getColumnIndex("id");
				int nameColumn = cur.getColumnIndex("user_name");
				int gIdColumn = cur.getColumnIndex("group_id");
				int gNameColumn = cur.getColumnIndex("group_name");
				int fNameColumn = cur.getColumnIndex("group_full_name");
				int jobColumn = cur.getColumnIndex("job_name");
				int stColumn = cur.getColumnIndex("start_time");
				int depthColumn = cur.getColumnIndex("depth");
				int pattColumn = cur.getColumnIndex("position_attr");
				int ltColumn = cur.getColumnIndex("lx_time");

				job.setId(cur.getInt(idColumn));
				job.setUser_name(cur.getString(nameColumn));
				job.setGroup_id(cur.getInt(gIdColumn));
				job.setGroup_name(cur.getString(gNameColumn));
				job.setGroup_full_name(cur.getString(fNameColumn));
				job.setJob_name(cur.getString(jobColumn));
				job.setStart_time(cur.getString(stColumn));
				job.setDepth(cur.getString(depthColumn));
				job.setPosition_attr(cur.getString(pattColumn));
				job.setLx_time(cur.getString(ltColumn));
			}
		}
		cur.close();
		db.close();
	}
  	return  job;
  }
	/**
	 * 获取职务集信息
	 * @param userCode
	 * @return
	 */
	public static List<Job> getJobs(String userCode) {
		List<Job> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_job where user_code = '" + userCode+ "' order by start_time ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Job>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int idColumn = cur.getColumnIndex("id");
				int nameColumn = cur.getColumnIndex("user_name");
				int gIdColumn = cur.getColumnIndex("group_id");
				int gNameColumn = cur.getColumnIndex("group_name");
				int fNameColumn = cur.getColumnIndex("group_full_name");
				int jobColumn = cur.getColumnIndex("job_name");
				int stColumn = cur.getColumnIndex("start_time");
				int depthColumn = cur.getColumnIndex("depth");
				int pattColumn = cur.getColumnIndex("position_attr");
				int ltColumn = cur.getColumnIndex("lx_time");
				Job job = new Job();
				job.setId(cur.getInt(idColumn));
				job.setUser_name(cur.getString(nameColumn));
				job.setGroup_id(cur.getInt(gIdColumn));
				job.setGroup_name(cur.getString(gNameColumn));
				job.setGroup_full_name(cur.getString(fNameColumn));
				job.setJob_name(cur.getString(jobColumn));
				job.setStart_time(cur.getString(stColumn));
				job.setDepth(cur.getString(depthColumn));
				job.setPosition_attr(cur.getString(pattColumn));
				job.setLx_time(cur.getString(ltColumn));
				list.add(job);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * 通过条件获取人员列表，（人员列表体内排序用到此方法）
	 * 此方法是通过职务集合体内排序获取user_id后，再次获取信息后填充到List列表中。
	 * 此方法多一步查询填充的过程，不适合大批量数据操作。
	 * @param sqlfilter 过滤条件
	 * @param pageIndex 分页索引
	 * @return
	 */
	public static List<User> getJobUserList(String sqlfilter, int pageIndex){
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select distinct user_code from t_job ";
		if (!sqlfilter.equals(null) && !sqlfilter.equals(null)) {
			sql += " where group_id in (" + sqlfilter + ")";
		}
		sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Cursor cur = db.rawQuery(sql, null);
		Log.i("sqlEx", sql);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				RosterRow row = new RosterRow();
				String userCode =cur.getString(cur.getColumnIndex("user_code"));
				row.setDegrees(DegreeDao.getDegreeList(userCode));
				row.setQualifications(QualificationDao.getQualificationList(userCode));
				row.setJob(JobDao.getMainJob(userCode));
				row.setDepth(PreDepthDao.getHighestDepth(userCode));
				row.setMarshals(MarshalsDao.getMarshals(userCode));
				list.add(UserDao.queryUser(userCode));

			}
		}
		return list;
	}

	/**
	 * 通过条件获取名册行实体，（名册展示做体内排序时用此方法）
	 * 此方法是通过职务集合体内排序获取user_id后，再次获取信息后填充到List列表中。
	 * 此方法多一步查询填充的过程，不适合大批量数据操作。
	 * @param sqlfilter 过滤条件
	 * @param pageIndex 分页索引
	 * @return
	 */
	public static List<RosterRow> getJobRosterList( String sqlfilter,int pageIndex){
		List<RosterRow> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select distinct user_code from t_job ";
		if (!sqlfilter.equals(null) && !sqlfilter.equals(null)) {
			sql += " where group_id in (" + sqlfilter + ")";
		}
		sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Cursor cur = db.rawQuery(sql, null);
		Log.i("sqlEx", sql);
		if (cur.getCount() > 0) {
			list = new ArrayList<RosterRow>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				RosterRow row = new RosterRow();
				String userCode = cur.getString(cur.getColumnIndex("user_code"));
				row.setUser(UserDao.queryUser(userCode));
				row.setDegrees(DegreeDao.getDegreeList(userCode));
				row.setQualifications(QualificationDao.getQualificationList(userCode));
				row.setJob(JobDao.getMainJob(userCode));
				row.setDepth(PreDepthDao.getHighestDepth(userCode));
				row.setMarshals(MarshalsDao.getMarshals(userCode));
				list.add(row);

			}
		}
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
		db.execSQL("delete from t_job");
		db.close();
	}
}
