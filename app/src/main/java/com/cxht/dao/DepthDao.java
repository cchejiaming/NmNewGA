package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.gov.tree.TreeNodeCheck;

public class DepthDao {

	/**
	 * 获取级别文字列表
	 * 
	 * @return
	 */
	public static List<String> 	getDepthList() {
		List<String> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_depth";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<String>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int depth = cur.getColumnIndex("depth_text");
				list.add(cur.getString(depth));

			}
		}
		cur.close();
		db.close();
		return list;

	}

	/**
	 * 职务层次列表
	 * @param context
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getDepthList(Context context){
		ArrayList<TreeNodeCheck> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_depth order by sort ";
		//String sql = "select * from t_org where org_id";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<TreeNodeCheck>();
			TreeNodeCheck root = new TreeNodeCheck("0", "-1", "全选", false);
			list.add(root);
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int codeColumn = cur.getColumnIndex("code_value");
				int depthColumn = cur.getColumnIndex("depth_text");
				String code = cur.getString(codeColumn);
				String depthName = cur.getString(depthColumn);
				int id = Integer.valueOf(code);
				int parentId= 0;
				if (code.length()>2){
					String pCode = code.substring(0,2);
					parentId = Integer.valueOf(pCode);
				}
				TreeNodeCheck er = new TreeNodeCheck(String.valueOf(id),String.valueOf(parentId),depthName,false);
				er.setParam(true);
				list.add(er);

			}
		}
		cur.close();
		db.close();
		return list;
	}

	/**
	 * 公务员职务层次列表
	 * @param context
	 * @return
	 */
	public static ArrayList<TreeNodeCheck> getDepthListOfGwy(Context context){
		ArrayList<TreeNodeCheck> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_depth where code_value like '01%' order by sort ";
		//String sql = "select * from t_org where org_id";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<TreeNodeCheck>();
			TreeNodeCheck root = new TreeNodeCheck("0", "-1", "全选", false);
			list.add(root);
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

				int codeColumn = cur.getColumnIndex("code_value");
				int depthColumn = cur.getColumnIndex("depth_text");
				String code = cur.getString(codeColumn);
				String depthName = cur.getString(depthColumn);
				int id = Integer.valueOf(code);
				int parentId= 0;
				if (code.length()>2){
					String pCode = code.substring(0,2);
					parentId = Integer.valueOf(pCode);
				}
				TreeNodeCheck er = new TreeNodeCheck(String.valueOf(id),String.valueOf(parentId),depthName,false);
				er.setParam(true);
				list.add(er);

			}
		}
		cur.close();
		db.close();
		return list;
	}
}
