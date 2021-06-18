package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cxht.bean.AgeGroupBean;
import com.cxht.bean.CheckBean;
import com.cxht.bean.RosterRow;
import com.cxht.bean.SearchParams;
import com.cxht.bean.StatResultBean;
import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.db.DBHelper;
import com.cxht.entity.User;
import com.cxht.unit.StatisticsUtil;
import com.cxht.unit.StringUtil;

/**
 * User����DAO��
 * 
 * @author hejiaming
 * 
 */
public class UserDao {
	/**
	 * ��ѯ�û����֤��
	 * @param userId �û�����
	 * @return
	 */
	public static String getUserCode(int userId){
		String userCode= "";
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select user_code from t_user where user_id ="+userId;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				userCode = cur.getString(cur.getColumnIndex("user_code"));
			}
		}
		cur.close();
		db.close();
		return userCode;
	}
	/**
	 * ��ȡ��Ա�б�
	 * @param context
	 * @param sql
	 * @param pageIndex
	 * @return
	 */
	public static List<User> getUser(Context context, String sql, int pageIndex) {

		List<User> list = null;

		if (sql != null) {
			Log.i("dataSql", sql);
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
					GonganApplication.dataBassPath, null);
			sql += " order by user_id limit 20 offset 20*" + pageIndex;

			Cursor cur = db.rawQuery(sql, null);
			if (cur.getCount() > 0) {
				list = new ArrayList<User>();
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

					list.add(pushUserData(cur));
				}
			}
			cur.close();
			db.close();
		}
		return list;
	}
	/**
	 * ����userCode��ѯUser����
	 * @param userId
	 * @return
	 */
	public static User queryUser(int userId ){
		User user = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user where user_id= "+userId;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			user = new User();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				user = pushUserData(cur);

			}
		}
		cur.close();
		db.close();
		return user;
	}
	/**
	 * ����userCode��ѯUser����
	 * @param userCode
	 * @return
	 */
	public static User queryUser(String userCode) {
		User user = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user where user_code= '"+userCode+"'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			user = new User();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				user = pushUserData(cur);

			}
		}
		cur.close();
		db.close();
		return user;
	}

	public static List<RosterRow> queryRosterList(SearchParams param,
			int pageIndex,String sort) {
		List<RosterRow> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = param.toSql();
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<RosterRow>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				RosterRow row = new RosterRow();
				row.setUser(pushUserData(cur));
				String userCode = row.getUser().getUser_code();
				if(userCode!= null){
					row.setDegrees(DegreeDao.getDegreeList(userCode));
					row.setQualifications(QualificationDao.getQualificationList(userCode));
					row.setJob(JobDao.getMainJob(userCode));
					row.setDepth(PreDepthDao.getHighestDepth(userCode));
					row.setMarshals(MarshalsDao.getMarshals(userCode));
				}

				list.add(row);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static int getUserCount(SearchParams param) {
		int ret = 0;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = param.toSql();

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			ret = cur.getCount();
		}
		cur.close();
		db.close();
		return ret;
	}

	/**
	 * ��ȡ��ѯ�б�
	 * 
	 * @param param
	 * @return
	 */
	public static List<User> queryUserList(SearchParams param, int pageIndex,String sort) {
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = param.toSql();
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				list.add(pushUserData(cur));
			}
		}
		cur.close();
		db.close();
		return list;

	}

	/**
	 * ��ȡ��Ա�б� ���ϡ�����
	 * 
	 * @return
	 */
	public static List<User> getUserList() {
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user order by user_id ";

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushUserData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}
    public static int getUserCountFormSql(String sql){
    	int ret = 0;
    	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		
		Cursor cur = db.rawQuery(sql, null);

		if (cur.getCount() > 0) {
			ret = cur.getCount();
		}
		cur.close();
		db.close();
    	return ret;
    }
	public static int getUserCount(String sqlItemParams) {
		int ret = 0;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select * from t_user where (user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) or user_code in (select user_code from t_pre_depth where group_code in ("
					+ sqlItemParams + "))) ";
		}

		Cursor cur = db.rawQuery(sql, null);

		if (cur.getCount() > 0) {
			ret = cur.getCount();
		}
		cur.close();
		db.close();
		return ret;
	}

	/**
	 * ��ȡ�����б���Ϣ
	 * @param sqlItemParams ������������
	 * @param pageIndex ��ҳ����
	 * @param sort ����
	 * @return
	 */
	public static List<RosterRow> getRosterList(String sqlItemParams,
			int pageIndex,String sort) {
		List<RosterRow> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals(null)) {
			sql = "select * from t_user where user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+")) ";
		}
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Cursor cur = db.rawQuery(sql, null);
		Log.i("sqlEx", sql);
		if (cur.getCount() > 0) {
			list = new ArrayList<RosterRow>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				RosterRow row = new RosterRow();
				row.setUser(pushUserData(cur));
				String userCode = row.getUser().getUser_code();
				if(userCode!= null){
					row.setDegrees(DegreeDao.getDegreeList(userCode));
					row.setQualifications(QualificationDao.getQualificationList(userCode));
					row.setJob(JobDao.getMainJob(userCode));
					row.setDepth(PreDepthDao.getHighestDepth(userCode));
					row.setMarshals(MarshalsDao.getMarshals(userCode));
				}
				list.add(row);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ���ָ����Ա�б�
	 * 
	 * @param sqlItemParams
	 *            //���˲���
	 * @param pageIndex
	 *            //����ҳ��
	 * @return
	 */
	public static List<User> getUserList(String sqlItemParams, int pageIndex,String sort) {
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user";

		if (!sqlItemParams.equals(null) && !sqlItemParams.equals(null)) {
			sql = "select * from t_user where user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+")) ";
		}
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushUserData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ȡ�����б���Ϣ
	 * @param param �Զ�������
	 * @param andWhere sql����������
	 * @param pageIndex ��ҳ����
	 * @param sort ����
	 * @return
	 */
	public static List<RosterRow> getRosterList(SearchParams param,
			String andWhere, int pageIndex,String sort) {
		List<RosterRow> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = param.toSql() + andWhere;
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<RosterRow>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				RosterRow row = new RosterRow();
				row.setUser(pushUserData(cur));
				String userCode = row.getUser().getUser_code();
				if(userCode!= null){
					row.setDegrees(DegreeDao.getDegreeList(userCode));
					row.setQualifications(QualificationDao.getQualificationList(userCode));
					row.setJob(JobDao.getMainJob(userCode));
					row.setDepth(PreDepthDao.getHighestDepth(userCode));
					row.setMarshals(MarshalsDao.getMarshals(userCode));
				}
				list.add(row);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ���ָ����Ա�б�
	 * 
	 * @param param
	 *            //���˲���
	 * @param andWhere
	 *            //׷�ӹ���where������and
	 * @param pageIndex
	 *            //����ҳ��
	 * @return
	 */
	public static List<User> getUserList(SearchParams param, String andWhere,
			int pageIndex,String sort) {
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = param.toSql() + andWhere;
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;

		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushUserData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ȡ�����б���Ϣ
	 * @param sqlItemParams ������Ϣ��������
	 * @param andWhere sql��� where ���������
	 * @param pageIndex ��ҳ����
	 * @param sort ����
	 * @return
	 */
	public static List<RosterRow> getRosterList(String sqlItemParams,
			String andWhere, int pageIndex,String sort) {
		List<RosterRow> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user";
		if (!sqlItemParams.equals(null) && !andWhere.equals(null)) {
			if (sqlItemParams.equals("")) {
				sql = "select * from t_user where 1=1 " + andWhere;
			} else {
				sql = "select * from t_user where user_code in (select user_code from t_job where group_code in ("
						+ sqlItemParams + ") order by job_bz,sort ) " + andWhere;
			}

		}
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<RosterRow>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				RosterRow row = new RosterRow();
				row.setUser(pushUserData(cur));
				String userCode = row.getUser().getUser_code();
				if(userCode!= null){
					row.setDegrees(DegreeDao.getDegreeList(userCode));
					row.setQualifications(QualificationDao.getQualificationList(userCode));
					row.setJob(JobDao.getMainJob(userCode));
					row.setDepth(PreDepthDao.getHighestDepth(userCode));
					row.setMarshals(MarshalsDao.getMarshals(userCode));
				}
				list.add(row);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ���ָ����Ա�б�
	 * 
	 * @param sqlItemParams
	 *            //���˲���
	 * @param andWhere
	 *            //׷�ӹ���where������and
	 * @param pageIndex
	 *            //����ҳ��
	 * @return
	 */
	public static List<User> getUserList(String sqlItemParams, String andWhere,
			int pageIndex,String sort) {
		List<User> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_user";
		if (!sqlItemParams.equals(null) && !andWhere.equals(null)) {
			if (sqlItemParams.equals("")) {
				sql = "select * from t_user where 1=1 " + andWhere;
			} else {
				sql = "select * from t_user where user_code in (select user_code from t_job where group_code in ("
						+ sqlItemParams + ") order by job_bz,sort ) " + andWhere;
			}

		}
		if (sort!= null) sql += " order by "+sort+" limit 20 offset 20*" + pageIndex;
		else sql += " order by user_id limit 20 offset 20*" + pageIndex;
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<User>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				list.add(pushUserData(cur));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ����û�����
	 * 
	 * @param cur
	 * @return
	 */
	public static User pushUserData(Cursor cur) {
		User user = new User();
		int user_id = cur.getColumnIndex("user_id");
		int group_id = cur.getColumnIndex("group_id");
		int full_name = cur.getColumnIndex("full_name");
		int user_name = cur.getColumnIndex("user_name");
		int sex = cur.getColumnIndex("sex");
		int birth_date = cur.getColumnIndex("birth_date");
		int user_code = cur.getColumnIndex("user_code");
		int nativeplace = cur.getColumnIndex("nativeplace");
		int birthplace = cur.getColumnIndex("birthplace");
		int nation = cur.getColumnIndex("nation");
		int rd_time = cur.getColumnIndex("rd_time");
		int politics_status = cur.getColumnIndex("politics_status");
		int job_time = cur.getColumnIndex("job_time");
		int state = cur.getColumnIndex("state");
		int image = cur.getColumnIndex("image");
		int photo = cur.getColumnIndex("image1");
		int position = cur.getColumnIndex("position");
		int depth = cur.getColumnIndex("depth");
		int before_education = cur.getColumnIndex("before_education");
		int before_specialty = cur.getColumnIndex("before_specialty");
		int before_school = cur.getColumnIndex("before_school");
		int before_degree = cur.getColumnIndex("before_degree");
		int before_time = cur.getColumnIndex("before_time");
		int latest_education = cur.getColumnIndex("latest_education");
		int latest_specialty = cur.getColumnIndex("latest_specialty");
		int latest_school = cur.getColumnIndex("latest_school");
		int latest_degree = cur.getColumnIndex("latest_degree");
		int latest_time = cur.getColumnIndex("latest_time");
		int highest_edu = cur.getColumnIndex("highest_edu");
		int highest_degree = cur.getColumnIndex("highest_degree");
		int specialty = cur.getColumnIndex("specialty");
		int specialty_desc = cur.getColumnIndex("specialty_desc");
		int job_title = cur.getColumnIndex("job_title");
		int spell = cur.getColumnIndex("spell");
		int sort = cur.getColumnIndex("sort");
		int pre_job_time = cur.getColumnIndex("pre_job_time");
		int pre_depth_time = cur.getColumnIndex("pre_depth_time");
		int rmb_path = cur.getColumnIndex("rmb_path");
		int number = cur.getColumnIndex("number");
		user.setUser_id(cur.getInt(user_id));
		user.setGroup_id(cur.getInt(group_id));
		user.setFull_name(cur.getString(full_name));
		user.setUser_name(cur.getString(user_name));
		user.setSex(cur.getString(sex));
		user.setBirth_date(cur.getString(birth_date));
		user.setUser_code(cur.getString(user_code));
		user.setNativeplace(cur.getString(nativeplace));
		user.setBirthplace(cur.getString(birthplace));
		user.setNation(cur.getString(nation));
		user.setRd_time(cur.getString(rd_time));
		user.setPolitics_status(cur.getString(politics_status));
		user.setJob_time(cur.getString(job_time));
		user.setStatus(cur.getString(state));
		user.setImage(cur.getString(image));
		user.setPhoto(cur.getString(photo));
		user.setPosition(cur.getString(position));
		user.setDepth(cur.getString(depth));
		user.setBefore_education(cur.getString(before_education));
		user.setBefore_specialty(cur.getString(before_specialty));
		user.setBefore_school(cur.getString(before_school));
		user.setBefore_degree(cur.getString(before_degree));
		user.setBefore_time(cur.getString(before_time));
		user.setLatest_education(cur.getString(latest_education));
		user.setLatest_specialty(cur.getString(latest_specialty));
		user.setLatest_school(cur.getString(latest_school));
		user.setLatest_degree(cur.getString(latest_degree));
		user.setLatest_time(cur.getString(latest_time));
		user.setHighest_edu(cur.getString(highest_edu));
		user.setHighest_degree(cur.getString(highest_degree));
		user.setSpecialty(cur.getString(specialty));
		user.setSpecialty_desc(cur.getString(specialty_desc));
		user.setJob_title(cur.getString(job_title));
		user.setSpell(cur.getString(spell));
		user.setSort(cur.getInt(sort));
		user.setPre_job_time(cur.getString(pre_job_time));
		user.setPre_depth_time(cur.getString(pre_depth_time));
		user.setRmb_path(cur.getString(rmb_path));
		user.setNumber(cur.getString(number));
		return user;
	}

	/**
	 * ͳ�ƽ��
	 * 
	 * @param columnName
	 * @return
	 */
	public static List<StatResultBean> getStatisticsResult(String columnName,String sqlItemParams) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
        String where = "";
		String sql = "select count(*) as num," + columnName
				+ " from t_user group by  " + columnName + "";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num,"
					+ columnName
					+ " from  t_user  where user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+"))  group by  "
					+ columnName + "";
			where =  " and user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) ";
		}
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				int name = cur.getColumnIndex(columnName);
				StatResultBean result = new StatResultBean();
				result.setCount(cur.getInt(num));
				result.setName(cur.getString(name));
				String andWhere = where + " and "+columnName+" = '"+result.getName()+"'";
				result.setAndWhere(andWhere);
				if (result.getName() != null && !result.getName().equals(""))
					list.add(result);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static List<StatResultBean> getStatisticsResult(String columnName) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);

		String sql = "select count(*) as num," + columnName
				+ " from t_user group by  " + columnName + "";

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				int name = cur.getColumnIndex(columnName);
				StatResultBean result = new StatResultBean();
				result.setCount(cur.getInt(num));
				result.setName(cur.getString(name));
				list.add(result);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ȡʱ���ͳ�ƽ��
	 * 
	 * @param startYear
	 *            ��ʼ���
	 * @param overYear
	 *            �������
	 * @param title
	 *            ��ǩ
	 * @param sqlItemParams
	 *            ��֯����sql
	 * @param colName
	 *            ��������
	 * @return
	 */
	public static StatResultBean getYearStatisticsResult(String startYear,
			String overYear, String title, String sqlItemParams, String colName) {
		StatResultBean srb = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user where " + colName
				+ ">'" + startYear + "' and " + colName + "<= '" + overYear+"'";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num from t_user where (user_code in (select user_code from t_job where group_code in("+ sqlItemParams
					+ ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+"))) and "
					+ colName
					+ " > '"
					+ startYear
					+ "' and " + colName + " <= '" + overYear+"'";
		}
		Cursor cur = db.rawQuery(sql, null);

		if (cur.getCount() > 0) {
			srb = new StatResultBean();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				srb.setAndWhere(sql);
				int num = cur.getColumnIndex("num");
				srb.setCount(cur.getInt(num));
				srb.setName(title);
			}
		}
		cur.close();
		db.close();
		return srb;
	}

	/**
	 * ��ȡ�����ͳ�ƽ��
	 * 
	 * @param startYear
	 *            ��ʼ���
	 * @param overYear
	 *            �������
	 * @param title
	 *            ��ǩ
	 * @return
	 */
	public static StatResultBean getAgeStatisticsResult(String startYear,
			String overYear, String title, String sqlItemParams) {
		StatResultBean srb = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user where birth_date>'"
				+ startYear + "' and birth_date<= '" + overYear+"'";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num from t_user where (user_code in (select user_code from t_job where group_code in("
					+ sqlItemParams
					+ ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+"))) and birth_date>'"
					+ startYear
					+ "' and birth_date<='" + overYear+"'";
		}
		Log.i("sqlhjm",sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			srb = new StatResultBean();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				srb.setCount(cur.getInt(num));
				srb.setName(title);
				srb.setAndWhere(sql);

			}
		}
		cur.close();
		db.close();
		return srb;
	}

	/**
	 * ��ȡ��ְ/ְ�� ʱ��ֶ�ͳ�� ���
	 * 
	 * @param sqlItemParams
	 *            sql���˲���
	 * @param colName
	 *            ������
	 * @return
	 */
	public static List<StatResultBean> getYearStatisticsGroupList(
			String sqlItemParams, String colName) {
		List<StatResultBean> list = null;

		List<AgeGroupBean> group = Setting.getYearGroupList();
		if (group != null) {
			list = new ArrayList<StatResultBean>();
			for (int i = 0; i < group.size(); i++) {
				StatResultBean srb = new StatResultBean();
				srb = getYearStatisticsResult(group.get(i).getStartYear(),
						group.get(i).getOverYear(), group.get(i).getTitle(),
						sqlItemParams, colName);
				list.add(srb);
			}
		}
		return list;
	}

	/**
	 * ��ȡ�����ͳ�ƶ����б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getAgeStatisticsGroupList(
			String sqlItemParams) {
		List<StatResultBean> list = null;

		List<AgeGroupBean> group = Setting.getAgeGroupList();
		if (group != null) {
			list = new ArrayList<StatResultBean>();
			for (int i = 0; i < group.size(); i++) {
				StatResultBean srb = new StatResultBean();
				srb = getAgeStatisticsResult(group.get(i).getStartYear(), group
						.get(i).getOverYear(), group.get(i).getTitle(),
						sqlItemParams);

				list.add(srb);
			}
		}
		return list;
	}

	public static List<StatResultBean> getEduStatisticsResultL(
			String sqlItemParams) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num,latest_education from t_user group by latest_education";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num,latest_education from t_user where user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) group by latest_education";
		}
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				StatResultBean srb = new StatResultBean();
				int num = cur.getColumnIndex("num");
				int highest = cur.getColumnIndex("latest_education");
				srb.setCount(cur.getInt(num));
				srb.setName(cur.getString(highest));
				list.add(srb);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static StatResultBean getEduStatResult(String sqlItemParams,
			String filter, String name) {
		StatResultBean result = new StatResultBean();
		result.setName(name);
		result.setCount(0);

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user where 1=1 and " + filter;

		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num from t_user where 1=1 and"
					+ filter
					+ " and (user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) or user_code in (select user_code from t_pre_depth where group_code in ("+sqlItemParams+")))";
		}
		result.setAndWhere(sql);
		Log.i("eduhjm",sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				result.setCount(cur.getInt(num));

			}
		}
		cur.close();
		db.close();

		return result;
	}

	/**
	 * ��ȡѧ��ͳ�ƽ��
	 * 
	 * @param sqlItemParams
	 *            ��֯���˲���
	 * @param colName
	 *            ѧ��ͳ������
	 * @return ͳ�ƽ��
	 */
	public static List<StatResultBean> getEduStatisticsResult(
			String sqlItemParams, String colName) {
		List<StatResultBean> list = new ArrayList<StatResultBean>();

		String _yjs = " " + colName + " like '%�о���%' ";
		StatResultBean yjs = getEduStatResult(sqlItemParams, _yjs, "�о���");
		list.add(yjs);

		String _dx = " " + colName + " like '%��ѧ%' ";
		StatResultBean dx = getEduStatResult(sqlItemParams, _dx, "��ѧ");
		list.add(dx);

		String _dz = " " + colName + " like '%��ר%' ";
		StatResultBean dz = getEduStatResult(sqlItemParams, _dz, "��ר");
		list.add(dz);

		String _zzyx = " (" + colName + " like '%��ר%' or " + colName
				+ " like '%����%' or " + colName + " like '%����%' or " + colName
				+ " like '%Сѧ%' )";
		StatResultBean zzyx = getEduStatResult(sqlItemParams, _zzyx, "��ר������");
		list.add(zzyx);

		return list;
	}

	/**
	 * ѧ��ͳ���б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getEduStatisticsResult(
			String sqlItemParams) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num,highest_edu from t_user group by highest_edu";
		if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
			sql = "select count(*) as num,highest_edu from t_user where user_code in (select user_code from t_job where group_code in ("
					+ sqlItemParams + ")) group by highest_edu";
		}
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				StatResultBean srb = new StatResultBean();
				int num = cur.getColumnIndex("num");
				int highest = cur.getColumnIndex("highest_edu");
				srb.setCount(cur.getInt(num));
				srb.setName(cur.getString(highest));
				list.add(srb);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ����ͳ���б�����
	 * 
	 * @param id
	 *            ����ͳ�Ʒ���
	 * @return
	 */
	public static List<StatResultBean> getDataList(int id) {

		List<StatResultBean> list = new ArrayList<StatResultBean>();

		switch (id) {

		case 0:
			list = getStatisticsResult(Setting.STAT_SEX, "");
			break;
		case 1:
			list = getAgeStatisticsGroupList("");
			break;
		case 2:
			list = getStatisticsResult(Setting.STAT_NATION, "");
			break;
		case 3:
			list = getEduStatisticsResult("");
			list = eduListSort(list);
			break;
		case 4:
			list = getStatisticsResult(Setting.STAT_HIGHEST_DEGREE);
			break;
		case 5:
			list = getStatisticsResult(Setting.STAT_POSITION, "");
			break;
		case 6:
			list = getYearStatisticsGroupList("", Setting.STAT_PRE_JOB_TIME);
			break;
		case 7:
			list = getYearStatisticsGroupList("", Setting.STAT_PRE_DEPTH_TIME);
			break;
		}
		return list;
	}

	/**
	 * ��֯ͳ���б�
	 * 
	 * @param id
	 * @param sqlItemParams
	 * @return
	 */
	public static List<StatResultBean> getDataList(int id, String sqlItemParams) {
		List<StatResultBean> list = null;

		switch (id) {

		case 0:
			list = getStatisticsResult(Setting.STAT_SEX, sqlItemParams);
			break;
		case 1:
			list = getAgeStatisticsGroupList(sqlItemParams);
			break;
		case 2:
			list = getStatisticsResult(Setting.STAT_NATION, sqlItemParams);
			list = nationListSort(list);
			break;
		case 3:
			list = getEduStatisticsResult(sqlItemParams,
					Setting.STAT_DEFORE_EDU);
			break;
		case 4:
			list = getEduStatisticsResult(sqlItemParams,
					Setting.STAT_LATEST_EDU);
			break;
		case 5:
			list = getStatisticsResult(Setting.STAT_DEPTH, sqlItemParams);
			list = depthListSort(list);
			break;
		case 6:
			list = getYearStatisticsGroupList(sqlItemParams,
					Setting.STAT_PRE_JOB_TIME);
			break;
		case 7:
			list = getYearStatisticsGroupList(sqlItemParams,
					Setting.STAT_PRE_DEPTH_TIME);
			break;
		case 8:
				list = StatisticsUtil.jobName(sqlItemParams);
				break;
		}
		return list;

	}

	/**
	 * ͳ����Ϣ���Ƽ���
	 *
	 *    Ҫ����������
	 * @param list
	 *            ͳ���б�
	 * @return ͳ�ƽ��
	 */
	private static StatResultBean getDepthResult(String name,List<StatResultBean> list) {
		StatResultBean srb = null;
		try{
			for (StatResultBean s : list) {
				if (s.getName().equals(name)) {
					srb = s;
					break;
				}
			}
		}catch (Exception e){
			e.getStackTrace();
		}
		return srb;
	}

	/**
	 * ѧ����Ϣ����
	 * 
	 * @param srb
	 *            ��Ҫ�������Ϣ��
	 * @return �����ļ���
	 */
	public static List<StatResultBean> eduListSort(List<StatResultBean> srb) {
		List<StatResultBean> list = null;
		if (srb != null) {
			list = new ArrayList<StatResultBean>();
			List<String> l = EduDao.getEduList();
			for (String s : l) {
				StatResultBean bean = getDepthResult(s, srb);
				if (bean != null) {
					list.add(bean);
					srb.remove(bean);
				}
			}
			list.addAll(srb);
		}
		return list;
	}

	/**
	 * ��������
	 * 
	 * @param srb
	 *            ��Ҫ����ļ���
	 * @return �����ļ���
	 */
	public static List<StatResultBean> nationListSort(List<StatResultBean> srb) {
		List<StatResultBean> list = null;
		if (srb != null) {
			list = new ArrayList<StatResultBean>();
			List<CheckBean> l = Setting.getNation();
			for (CheckBean s : l) {
				StatResultBean bean = getDepthResult(s.getName(), srb);
				if (bean != null) {
					list.add(bean);
					srb.remove(bean);
				}
			}
			list.addAll(srb);
		}
		return list;
	}

	/**
	 * ְ������
	 * 
	 * @param srb
	 *            ��Ҫ����ļ���
	 * @return �����ļ���
	 */
	public static List<StatResultBean> depthListSort(List<StatResultBean> srb) {
		List<StatResultBean> list = null;
		if (srb != null) {
			list = new ArrayList<StatResultBean>();
			List<String> l = DepthDao.getDepthList();
			for (String s : l) {
				StatResultBean bean = getDepthResult(s, srb);
				if (bean != null) {
					list.add(bean);
					srb.remove(bean);
				}
			}
			list.addAll(srb);
		}
		return list;
	}

	/**
	 * ������ԱId�����ݿ�
	 * 
	 * @param userId
	 * @param context
	 */
	public static void saveFavoriteUser(int userId, Context context) {

		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("user_id", userId);
		cv.put("add_time", StringUtil.getData());
		db.delete("t_favorite_user", "user_id=" + userId, null);
		db.insert("t_favorite_user", null, cv);
		db.close();

	}

	public static List<RosterRow> getFavoriteRosterList(Context context,
			int pageIndex) {
		List<RosterRow> list = null;
		List<Integer> idList = getFavoriteIdList(context, pageIndex);
		if (idList != null) {
			String ids = "";
			for (int i = 0; i < idList.size(); i++) {
				ids += "," + idList.get(i);
			}
			if (ids != null) {
				// ��ȡ�ַ���
				ids = ids.substring(1);
				String sql = "select * from t_user where user_id in (" + ids
						+ ")"; // ��ѯSQL���
				/**
				 * �ڵ������ݶ�ȡUser����
				 */
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						GonganApplication.dataBassPath, null);
				Cursor cur = db.rawQuery(sql, null);
				if (cur.getCount() > 0) {
					list = new ArrayList<RosterRow>();
					for (cur.moveToFirst(); !cur.isAfterLast(); cur
							.moveToNext()) {
						RosterRow row = new RosterRow();
						row.setUser(pushUserData(cur));
						String userCode = row.getUser().getUser_code();
						if(userCode!= null){
							row.setDegrees(DegreeDao.getDegreeList(userCode));
							row.setQualifications(QualificationDao.getQualificationList(userCode));
							row.setMarshals(MarshalsDao.getMarshals(userCode));
						}
						list.add(row);
					}
				}
				cur.close();
				db.close();
			}

		}
		return list;
	}

	/**
	 * ��ȡ��Ա�ղ��б�
	 * 
	 * @return
	 */
	public static List<User> getFavoriteUserList(Context context, int pageIndex) {
		List<User> list = null;
		List<Integer> idList = getFavoriteIdList(context, pageIndex);
		if (idList != null) {
			String ids = "";
			for (int i = 0; i < idList.size(); i++) {
				ids += "," + idList.get(i);
			}
			if (ids != null) {
				// ��ȡ�ַ���
				ids = ids.substring(1);
				String sql = "select * from t_user where user_id in (" + ids
						+ ")"; // ��ѯSQL���
				/**
				 * �ڵ������ݶ�ȡUser����
				 */
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						GonganApplication.dataBassPath, null);
				Cursor cur = db.rawQuery(sql, null);
				if (cur.getCount() > 0) {
					list = new ArrayList<User>();
					for (cur.moveToFirst(); !cur.isAfterLast(); cur
							.moveToNext()) {
						list.add(pushUserData(cur));
					}
				}
				cur.close();
				db.close();
			}

		}
		return list;
	}
   public static List<Integer> getFavoriteIdList(Context context){
	   List<Integer> list = null;
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		String sql = "select * from t_favorite_user";
		sql += " order by favorite_id desc ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Integer>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int column = cur.getColumnIndex("user_id");
				list.add(cur.getInt(column));

			}
		}
		cur.close();
		db.close();
	   return list;
   }
	/**
	 * ����ղ���ԱID�б�
	 * 
	 * @param context
	 * @param pageIndex
	 * @return
	 */
	private static List<Integer> getFavoriteIdList(Context context,
			int pageIndex) {
		List<Integer> list = null;
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		String sql = "select * from t_favorite_user";
		sql += " order by favorite_id desc limit 20 offset 20*" + pageIndex;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Integer>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int column = cur.getColumnIndex("user_id");
				list.add(cur.getInt(column));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ɾ���ղ���Ա
	 * 
	 * @param userId
	 * @param context
	 */
	public static void deleteFavoriteUser(int userId, Context context) {
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		ContentValues cv = new ContentValues();
		db.delete("t_favorite_user", "user_id=" + userId, null);
		db.close();
	}

	/**
	 * ��Ա��ѯͳ�����
	 * 
	 * @param id
	 * @return
	 */
	public static List<StatResultBean> getDataList1(int id, String where) {
		List<StatResultBean> list = null;
		switch (id) {
		case 0:
			list = getStatisticsFromSql(where,Setting.STAT_SEX);
			break;
		case 1:
			list = getAgeStatisticsUserList(where);
			break;
		case 2:

			list = getStatisticsFromSql(where,Setting.STAT_NATION);
			list = nationListSort(list);
			break;
		case 3:
			list = getEduStatisticsResult1(where, Setting.STAT_DEFORE_EDU);
			break;
		case 4:

			list = getEduStatisticsResult1(where, Setting.STAT_LATEST_EDU);
			break;
		case 5:
			list = getStatisticsFromSql(where,Setting.STAT_DEPTH);
			list = depthListSort(list);
			break;
		case 6:
			list = getYearStatisticsUserList1(where, Setting.STAT_PRE_JOB_TIME);
			break;
		case 7:
			list = getYearStatisticsUserList1(where,
					Setting.STAT_PRE_DEPTH_TIME);
			break;
			case 8:
				list = StatisticsUtil.jobName2(where);
				break;
		}
		return list;
	}

	/**
	 * ѧ��ͳ���б�
	 * 
	 * @param where
	 *            ��ѯ����
	 * @param colName
	 *            ͳ������
	 * @return ͳ�ƽ��
	 */
	public static List<StatResultBean> getEduStatisticsResult1(String where,
			String colName) {
		List<StatResultBean> list = new ArrayList<StatResultBean>();

		String _yjs = " " + colName + " like '%�о���%' ";
		StatResultBean yjs = getEduStatResult1(where, _yjs, "�о���");
		list.add(yjs);

		String _dx = " " + colName + " like '%��ѧ%' ";
		StatResultBean dx = getEduStatResult1(where, _dx, "��ѧ");
		list.add(dx);

		String _dz = " " + colName + " like '%��ר%' ";
		StatResultBean dz = getEduStatResult1(where, _dz, "��ר");
		list.add(dz);

		String _zzyx = " (" + colName + " like '%��ר%' or " + colName
				+ " like '%����%' or " + colName + " like '%����%' or " + colName
				+ " like '%Сѧ%' )";
		StatResultBean zzyx = getEduStatResult1(where, _zzyx, "��ר������");
		list.add(zzyx);

		return list;

	}

	/**
	 * ��ȡѧ����ز�ѯ����������ͳ�ƽ��
	 *
	 *            ������������
	 * @param filter
	 *            ѧ��Ҫ���˵�SQL�ַ��� ע�ⲻ�ü�Where
	 * @param name
	 *            ͳ��ѧ������
	 * @return
	 */
	public static StatResultBean getEduStatResult1(String where, String filter,
			String name) {
		StatResultBean result = new StatResultBean();
		result.setName(name);
		result.setCount(0);
		where = where.replace("where 1=1", "");
		// where = where.replace("where", "");
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user where " + filter
				+ where;

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				result.setCount(cur.getInt(num));
				String andWhere = " and "+filter+where;
				result.setAndWhere(andWhere);


			}
		}
		cur.close();
		db.close();
		return result;
	}

	/**
	 * ��ѯ��Աѧ��ͳ���б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getStatisticsResult(SearchParams param,
			String colName) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num," + colName + " from t_user "
				+ param.toWhereSql() + " group by " + colName;

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				StatResultBean srb = new StatResultBean();
				int num = cur.getColumnIndex("num");
				int highest = cur.getColumnIndex(colName);
				srb.setCount(cur.getInt(num));
				srb.setName(cur.getString(highest));
				list.add(srb);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ѯ��Աѧ��ͳ���б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getEduStatisticsResult(SearchParams param) {
		List<StatResultBean> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num,highest_edu from t_user "
				+ param.toWhereSql() + " group by highest_edu";

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				StatResultBean srb = new StatResultBean();
				int num = cur.getColumnIndex("num");
				int highest = cur.getColumnIndex("highest_edu");
				srb.setCount(cur.getInt(num));
				srb.setName(cur.getString(highest));
				list.add(srb);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ȡ��ѯ��Աʱ���ͳ�ƶ����б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getYearStatisticsUserList1(String where,
			String colName) {
		List<StatResultBean> list = null;

		List<AgeGroupBean> group = Setting.getYearGroupList();
		if (group != null) {
			list = new ArrayList<StatResultBean>();
			for (int i = 0; i < group.size(); i++) {
				StatResultBean srb = new StatResultBean();
				srb = getYearStatisticsResult1(group.get(i).getStartYear(),
						group.get(i).getOverYear(), group.get(i).getTitle(),
						where, colName);
				list.add(srb);
			}
		}
		return list;
	}

	/**
	 * ��ȡ��ѯ��Ա�����ͳ�ƶ����б�
	 * 
	 * @return
	 */
	public static List<StatResultBean> getAgeStatisticsUserList(String where) {
		List<StatResultBean> list = null;

		List<AgeGroupBean> group = Setting.getAgeGroupList();
		if (group != null) {
			list = new ArrayList<StatResultBean>();
			for (int i = 0; i < group.size(); i++) {
				StatResultBean srb = new StatResultBean();
				srb = getAgeStatisticsResult1(group.get(i).getStartYear(),
						group.get(i).getOverYear(), group.get(i).getTitle(),
						where);
				list.add(srb);
			}
		}
		return list;
	}

	/**
	 * ��ȡ��ѯ��Ա�����ͳ�ƽ��
	 * 
	 * @param startYear
	 *            ��ʼ���
	 * @param overYear
	 *            �������
	 * @param title
	 *            ��ǩ
	 * @return
	 */
	public static StatResultBean getYearStatisticsResult1(String startYear,
			String overYear, String title, String where, String colName) {
		StatResultBean srb = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user " + where + " and "
				+ colName + ">'" + startYear + "' and " + colName + "<= '"
				+ overYear+"'";

		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			srb = new StatResultBean();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				srb.setCount(cur.getInt(num));
				srb.setName(title);
				srb.setAndWhere(sql);
			}
		}
		cur.close();
		db.close();
		return srb;
	}

	/**
	 * ��ȡ��ѯ��Ա�����ͳ�ƽ��
	 * 
	 * @param startYear
	 *            ��ʼ���
	 * @param overYear
	 *            �������
	 * @param title
	 *            ��ǩ
	 * @return
	 */
	public static StatResultBean getAgeStatisticsResult1(String startYear,
			String overYear, String title, String where) {
		StatResultBean srb = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select count(*) as num from t_user " + where
				+ " and birth_date>'" + startYear + "' and birth_date<='"
				+ overYear+"'";
        Log.i("SqlAgeCount",sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			srb = new StatResultBean();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				srb.setCount(cur.getInt(num));
				srb.setName(title);
				srb.setAndWhere(sql);
			}
		}
		cur.close();
		db.close();
		return srb;
	}

	/**
	 * ͳ�Ʋ�ѯ
	 * 
	 * @param where ��������
	 * @column column ��̬��������
	 * @return
	 */
	public static List<StatResultBean> getStatisticsFromSql(String where,String column) {
		List<StatResultBean> list = null;

		String sql = "select count(*) as num," + column
				+ " as name from t_user";
		sql += where;
		sql += " group by " + column;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<StatResultBean>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int num = cur.getColumnIndex("num");
				int name = cur.getColumnIndex("name");
				StatResultBean result = new StatResultBean();
				result.setCount(cur.getInt(num));
				result.setName(cur.getString(name));
				String andWhere = where+" and "+column+" = '"+result.getName()+"'";
				result.setAndWhere(andWhere);
				if (result.getName() != null && !result.getName().equals(""))
					list.add(result);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ��ռ�¼
	 * 
	 * @param context
	 */
	public static void deleteAll(Context context) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("delete from t_user");
		db.close();

	}

	public static double getUserAge(Context context, String sql) {
		double age = 0.0;
		Log.i("sqlCountAge", sql);
		if (sql != null) {
			try {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						GonganApplication.dataBassPath, null);
				Cursor cur = db.rawQuery(sql, null);
				if (cur.getCount() > 0) {

					for (cur.moveToFirst(); !cur.isAfterLast(); cur
							.moveToNext()) {
						int countColumn = cur.getColumnIndex("size");
						age = cur.getDouble(countColumn);
					}
				}
				cur.close();
				db.close();
			} catch (Exception ex) {
				ex.getStackTrace();
			}

		}
		return age;
	}
}
