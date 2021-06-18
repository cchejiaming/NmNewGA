package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cxht.bean.CheckBean;
import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.unit.StatisticsUtil;
import com.cxht.unit.StringUtil;
import com.gongan.horizontal.scrollview.bean.RecordColumn;
import com.gongan.horizontal.scrollview.bean.RecordRows;
import com.gov.tree.TreeNodeCheck;

/**
 * ���ݿ��ۺϴ���Dao��
 */
public class TableDao {
	private static List<GroupBean> all = null;

	/**
	 * ���ϵͳѧ���б�
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getFullEdu(Context context) {
		ArrayList<String> list = null;
		String sql = "select DISTINCT before_education from t_user where before_education <>''";
		list = getListValue(context, sql, "before_education");
		return list;
	}

	/**
	 * ְ���б�
	 * @param context
	 * @return
	 */
   public static ArrayList<String> getQualification(Context context){
	   ArrayList<String> list = null;
	   String sql = "select DISTINCT  qfc_name from t_qualification";
	   list = getListValue(context, sql, "qfc_name");
	   return list;
   }
	/**
	 * ���ϵͳ�����б�
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getPolitical(Context context) {
		ArrayList<String> list = null;
		String sql = "select DISTINCT  politics_status from t_user";
		list = getListValue(context, sql, "politics_status");
		return list;
	}

	/**
	 * ����״��
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getState(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT  state from t_user where state<>''";
		list = getListValue(context, sql, "state");
		return list;
	}
	/**
	 * �������
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getAssessYear(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT  year from t_assess where year<>'' order by year desc ";
		list = getListValue(context, sql, "year");
		return list;
	}
	/**
	 * ���˽��
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getAssessResult(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT  result from t_assess where result<>'' order by result ";
		list = getListValue(context, sql, "result");
		return list;
	}
	/**
	 * �ͽ����
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getPunishType(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT  punish_type from t_punish where punish_type<>'' order by punish_type ";
		list = getListValue(context, sql, "punish_type");
		return list;
	}
	/**
	 * ִ���ʸ�
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getMarshals(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT level from t_marshals where level<>'' order by level ";
		list = getListValue(context, sql, "level");
		return list;
	}
	/**
	 * ����
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getUserRank(Context context){
		ArrayList<String> list = null;
		String sql = "select DISTINCT rank_name from t_rank where rank_name<>'' order by rank_name ";
		list = getListValue(context, sql, "rank_name");
		return list;
	}
	public static ArrayList<String> getDepth(Context context) {
		ArrayList<String> list = null;
		String sql = "select DISTINCT name from t_pre_depth where name<>''";
		list = getListValue(context, sql, "name");
		return list;
	}

	public static ArrayList<String> getListValue(Context context, String sql,
			String columnName) {
		ArrayList<String> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<String>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int index = cur.getColumnIndex(columnName);
				String pol = cur.getString(index);
				list.add(pol);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * �������ݿ��
	 * 
	 * @param context
	 */
	public static void updateDBTable(Context context) {
		//List<String> list = GroupDao. getUpdateParentIdEx(context);
		//execTransaction(context, list);
		String sql = "update t_job set sort = ('0'|| sort) where length(sort) = 1";
		execSql(context,sql);
		//sql = "update t_job set start_time = substr(start_time,0,8) ";
		//execSql(context,sql);
		//sql = "update t_pre_depth set start_time = substr(start_time,0,8) ";
		//execSql(context,sql);
		
	}

	/**
	 * ���ɸ�����֯��Ϣ��TranSQL��伯�� ͳ����Աʵְ����ְ������,��֯��Ϣ��sz_num��xz_num�ֶ��У����и��¡�
	 * 
	 * @param lt
	 *            ��Ҫ���µ���֯���뼯��
	 * @return ִ��sql��伯��
	 */
	private static List<String> toGroupTbTranSql(List<Integer> lt) {
		List<String> list = null;
		if (lt != null && lt.size() > 0) {
			list = new ArrayList<String>();
			for (Integer id : lt) {
				String ids = toGroupInSql(id);
				String sql = "update t_group set sz_num=("
						+ "select count(*) from t_user where user_code in "
						+ "(select user_code from t_job where group_id in ("
						+ ids
						+ ") and position_attr like '%ʵְ%'))"
						+ ", xz_num=(select count(*) from t_user where user_code in "
						+ "(select user_code from t_job where group_id in ("
						+ ids + ") and position_attr like '%��ְ%'))"
						+ " where group_id in (" + ids + ")";
				Log.i("sqlTran", sql);
				list.add(sql);
			}
		}
		return list;
	}

	private static String toGroupInSql(Integer id) {
		String ret = getGroupIds(id);
		if (ret != "") {
			ret += "," + id;
		} else {
			ret += String.valueOf(id);
		}
		return ret;
	}

	private static String getGroupIds(Integer id) {
		String ret = "";
		if (all == null) {
			all = GroupDao.getGroupList();
		}
		for (GroupBean gb : all) {
			if (Integer.valueOf(gb.getParentId()) == id) {
				ret += String.valueOf(gb.getId()) + ",";
				ret += getGroupIds(Integer.valueOf(gb.getId()));
			}
		}
		if (ret.length() > 0)
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

	/**
	 * ִ������
	 * 
	 * @param context
	 * @param lt
	 *            ִ��sql����б�
	 */
	public static void execTransaction(Context context, List<String> lt) {
		if (lt != null && lt.size() > 0) {
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
					GonganApplication.dataBassPath, null);
			db.beginTransaction(); // �ֶ����ÿ�ʼ����
			try {
				for (String sql : lt) {
					// �����������
					// sLog.i("sqlUTran",sql);
					db.execSQL(sql);

				}

				db.setTransactionSuccessful(); // ����������ɹ��������û��Զ��ع����ύ
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				db.endTransaction(); // �������
			}
		}
	}

	public static List<CheckBean> getTableCheckBean(Context context,
			String sql, String column) {
		List<CheckBean> list = new ArrayList<CheckBean>();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int mColumn = cur.getColumnIndex(column);
				String mName = cur.getString(mColumn);
				CheckBean cb = new CheckBean(mName, false);
				list.add(cb);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static List<TreeNodeCheck> getFullEduTree(Context context,
			String sql, int pId, String column) {
		int index = pId;
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				index++;
				int eduColumn = cur.getColumnIndex(column);
				String eduName = cur.getString(eduColumn);
				TreeNodeCheck er = new TreeNodeCheck(String.valueOf(index), String.valueOf(pId), eduName, false);
				er.setParam(true);
				list.add(er);
			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static ArrayList<TreeNodeCheck> getZsCheckGroupList(Context context) {

		ArrayList<TreeNodeCheck> list = null;
	
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_group ";
		Log.i("sqlEx", sql);
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<TreeNodeCheck>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int groupColumn = cur.getColumnIndex("group_name");
				int groupCodeColumn = cur.getColumnIndex("group_code");
				String groupName = cur.getString(groupColumn);
				String groupCode = cur.getString(groupCodeColumn);
				String ParentGroupCode = StatisticsUtil.getParentGroupCode(groupCode);
				TreeNodeCheck er = new TreeNodeCheck(groupCode,ParentGroupCode, groupName, false);
				er.setParam(true);
				list.add(er);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * ������Ϣ�����б�
	 * @return
	 */
   public static ArrayList<TreeNodeCheck> getCheckExamineList(){

	   return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_TA);
   }
	/**
	 * �ͽ���Ϣ�����б�
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckPunishList(){

		return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_AR);
	}
	/**
	 * ������Ϣ�����б�
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckRankList(){

		return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_XI);
	}
	/**
	 * ��ȿ��������б�
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckAssessList(){

		return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_EI);
	}
	/**
	 * ְ�������б�
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckQualificationList(){

		return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_YJ);
	}
	/**
	 * ִ���ʸ������б�
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckMarshalsList(){

		return getCheckValueCodeList(Setting.VALUE_CODE_DICT_ID_FD);
	}
	/**
	 * ��ȡ�ֵ�������б�����
	 * @param dictId ����ID
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckValueCodeList(String dictId){
		ArrayList<TreeNodeCheck> list =  new ArrayList<TreeNodeCheck>();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_value_code where dict_id ='"+dictId+"' order by dict_value";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int idColumn = cur.getColumnIndex("dict_value");
				int pIdColumn = cur.getColumnIndex("parent_value");
				int nameColumn = cur.getColumnIndex("description");
				String id = cur.getString(idColumn);
				String parentId = cur.getString(pIdColumn);
				String name = cur.getString(nameColumn);
				TreeNodeCheck er = new TreeNodeCheck(id,parentId, name, false);
				er.setParam(true);
				list.add(er);

			}
		}
		cur.close();
		db.close();
		return list;
	}
	/**
	 * ���������б�
	 * @param context
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getCheckGroupList(Context context) {
		ArrayList<TreeNodeCheck> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_group order by sort";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<TreeNodeCheck>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int groupColumn = cur.getColumnIndex("group_name");
				int groupCodeColumn = cur.getColumnIndex("group_code");
				String groupName = cur.getString(groupColumn);
				String groupCode = cur.getString(groupCodeColumn);
				String ParentGroupCode = StatisticsUtil.getParentGroupCode(groupCode);
				TreeNodeCheck er = new TreeNodeCheck(groupCode,ParentGroupCode, groupName, false);
				er.setParam(true);
				list.add(er);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	public static ArrayList<RecordRows> execTransaction(Context context,
			ArrayList<RecordRows> lt) {
		ArrayList<RecordRows> list = lt;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.beginTransaction(); // �ֶ����ÿ�ʼ����
		try {
			for (RecordRows rows : list) {
				// �����������
				for (RecordColumn rc : rows.getRows()) {
					if ("".equals(rc.getValue())) {

						String sql = rc.getExecuteWhereSql();
						Log.i("sqlCountTran", sql);
						Cursor cur = db.rawQuery(sql, null);
						rc.setValue(String.valueOf(cur.getCount()));
					}

				}
			}

			db.setTransactionSuccessful(); // ����������ɹ��������û��Զ��ع����ύ
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			db.endTransaction(); // �������
		}
		return list;
	}

	/**
	 * ��ȡ����
	 * @param sql
	 * @return
	 */
    public static int getTableCount(String sql){
		return getTableCount(null,sql);
	}

	/**
	 * ��ȡ��Ա����
	 * @param context
	 * @param sql
	 * @return
	 */
	public static int getTableCount(Context context, String sql) {
		int count = 0;
		Log.i("sqlCount", sql);
		if (sql != null) {
			try {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						GonganApplication.dataBassPath, null);
				Cursor cur = db.rawQuery(sql, null);
				count = cur.getCount();
				cur.close();
				db.close();
			} catch (Exception ex) {
				ex.getStackTrace();
			}

		}

		return count;
	}

	// ������ͼ
	public static void createDBView(Context context) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL("create view if not exists v_st_user as "
				+ "select *,(select start_time  from t_job where "
				+ "id =(select min(id) from t_job where user_code = a.[user_code])) as start_time from t_user as a");
		db.close();
	}

	/**
	 * ��ռ�¼
	 * 
	 * @param context
	 */
	public static void execSql(Context context, String sql) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		db.execSQL(sql);
		db.close();

	}
}
