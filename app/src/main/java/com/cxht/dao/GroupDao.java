package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.db.DBHelper;
import com.cxht.entity.Group;
import com.cxht.unit.StatisticsUtil;
import com.cxht.unit.StringUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 机构信息DAO层
 */
public class GroupDao {

	public static List<Group> getFavoriteList() {
		List<Group> list = null;

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_group order by sort ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Group>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int idColumn = cur.getColumnIndex("group_id");
				int nameColumn = cur.getColumnIndex("group_name");
				int codeColumn = cur.getColumnIndex("group_code");
				int parentColumn = cur.getColumnIndex("parent_id");
				Group group = new Group();
				group.setGroup_id(cur.getInt(idColumn));
				group.setGroup_name(cur.getString(nameColumn));
				group.setGroup_code(cur.getString(codeColumn));
				group.setParent_id(cur.getInt(parentColumn));
				list.add(group);

			}
		}
		cur.close();
		db.close();
		return list;

	}
	public static String getGroupCode(int groupId){
		String result = "0";
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_code from t_group where group_id=" + groupId;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int groupCode = cur.getColumnIndex("group_code");
				result = cur.getString(groupCode);
			}
		}
		cur.close();
		db.close();
		return result;
	}
	/**
	 * 获取组织名称
	 * 
	 * @param groupId
	 * @return
	 */
	public static String getGroupName(int groupId) {
		String result = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_name from t_group where group_id=" + groupId;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int idGroupName = cur.getColumnIndex("group_name");
				result = cur.getString(idGroupName);
			}
		}
		cur.close();
		db.close();
		return result;
	}

	/**
	 * 模糊查询获得组织ID
	 * 
	 * @param groupName
	 * @return
	 */
	public static int getGroupId2(String groupName) {
		int res = 0;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_id from t_group where group_name like '%"
				+ groupName + "%'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int id = cur.getColumnIndex("group_id");
				res = cur.getInt(id);
				break;
			}
		}
		cur.close();
		db.close();
		return res;
	}

	/**
	 * 获取组织ID
	 * 
	 * @param groupName
	 * @return
	 */
	public static int getGroupId(String groupName) {
		int res = 0;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_id from t_group where group_name='"
				+ groupName + "'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int id = cur.getColumnIndex("group_id");
				res = cur.getInt(id);
			}
		}
		cur.close();
		db.close();
		return res;
	}

	/**
	 * 获取组织列表
	 *
	 * @return
	 */

	public static List<GroupBean> getGroupList() {
		List<GroupBean> list = null;

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_group ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int groupColumn = cur.getColumnIndex("group_name");
			    int groupCodeColumn = cur.getColumnIndex("group_code");
				String groupName = cur.getString(groupColumn);
				String groupCode = cur.getString(groupCodeColumn);
				String ParentGroupCode = StatisticsUtil.getParentGroupCode(groupCode);
						//+ StringUtil.toPositionAttrString(csz, cxz, sz, xz);

				list.add(new GroupBean(groupCode,ParentGroupCode, groupName,groupCode));
			
			}
		}
		cur.close();
		db.close();
		return list;
	}
	/**
	 * 获取组织id
	 *
	 *            组织编码
	 * @return 返回组织id int类型
	 */
	public static int getId(String code) {
		int id = 0;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_id from t_group where group_code='" + code + "'";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {

			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int idColumn = cur.getColumnIndex("group_id");
				id = cur.getInt(idColumn);

			}
		}
		cur.close();
		db.close();
		return id;
	}

	/**
	 * 保存组织Id到数据库
	 *
	 * @param context
	 */
	public static void saveFavoriteGroup(int groupId, Context context) {

		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("group_id", groupId);
		cv.put("add_time", StringUtil.getData());
		db.delete("t_favorite_group", "group_id=" + groupId, null);
		db.insert("t_favorite_group", null, cv);
		db.close();

	}

	/**
	 * 获取组织收藏列表
	 * 
	 * @return
	 */
	public static List<Group> getFavoriteGroupList(Context context) {
		List<Group> list = null;
		List<Integer> idList = getFavoriteIdList(context);
		if (idList != null) {
			String ids = "";
			for (int i = 0; i < idList.size(); i++) {
				ids += "," + idList.get(i);
			}
			if (ids != null) {
				// 截取字符串
				ids = ids.substring(1);
				String sql = "select * from t_group where group_id in (" + ids
						+ ")"; // 查询SQL语句
				/**
				 * 数据读取Group数据
				 */
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						GonganApplication.dataBassPath, null);
				Cursor cur = db.rawQuery(sql, null);
				if (cur.getCount() > 0) {
					list = new ArrayList<Group>();
					for (cur.moveToFirst(); !cur.isAfterLast(); cur
							.moveToNext()) {
						int idColumn = cur.getColumnIndex("group_id");
						int nameColumn = cur.getColumnIndex("group_name");
						int codeColumn = cur.getColumnIndex("group_code");
						int parentColumn = cur.getColumnIndex("parent_id");
						Group group = new Group();
						group.setGroup_id(cur.getInt(idColumn));
						group.setGroup_name(cur.getString(nameColumn));
						group.setGroup_code(cur.getString(codeColumn));
						group.setParent_id(cur.getInt(parentColumn));
						list.add(group);
					}
				}
				cur.close();
				db.close();
			}

		}
		return list;
	}

	/**
	 * 获得收藏人员ID列表
	 * 
	 * @param context
	 * @return
	 */
	private static List<Integer> getFavoriteIdList(Context context) {
		List<Integer> list = null;
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		String sql = "select * from t_favorite_group order by favorite_id desc";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Integer>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int column = cur.getColumnIndex("group_id");
				list.add(cur.getInt(column));

			}
		}
		cur.close();
		db.close();
		return list;
	}
 public static List<String> getUpdateParentIdEx(Context context){
	 List<String> list = new ArrayList<String>();
	 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_group ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int groupId =cur.getInt( cur.getColumnIndex("group_id"));
				String groupCode = cur.getString(cur.getColumnIndex("group_code"));
				String al = "";
				
				int len = 0;
				int pid = 0;
				if (groupCode!=null ) len = groupCode.length();
				if (len>3) {
				   String fcode = groupCode.substring(0,groupCode.length()-3);	
					al = "update t_group set parent_id = (select group_id from t_group where group_code ='"+fcode+"' ) where group_id = "+groupId;
				}else {
					 al = "update t_group set parent_id = 0 where group_id = "+groupId;
				}
				list.add(al);

			}
		}
		cur.close();
		db.close();
	 return list;
 }
	/**
	 * 获取配置组织ID列表集
	 * 
	 * @param context
	 * @return
	 */
	public static List<Integer> getConfigIDList(Context context) {
		List<Integer> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select group_id from t_group where config_sz_num>0 or config_xz_num>0";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<Integer>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int column = cur.getColumnIndex("group_id");
				list.add(cur.getInt(column));

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * 删除收藏组织
	 *
	 * @param context
	 */
	public static void deleteFavoriteGroup(int groupId, Context context) {
		DBHelper dbhp = DBHelper.getInstance(context);
		SQLiteDatabase db = dbhp.getReadableDatabase();
		ContentValues cv = new ContentValues();
		db.delete("t_favorite_group", "group_id=" + groupId, null);
		db.close();
	}


}
