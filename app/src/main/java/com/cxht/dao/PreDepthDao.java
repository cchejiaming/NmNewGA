package com.cxht.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cxht.config.GonganApplication;
import com.cxht.entity.PreDepth;

/**
 * ְ����ϢDAO��
 */
public class PreDepthDao {
	/**
	 * ��ȡ���ְ����Ϣ
	 *����ж���ߣ��û�δ����ǩ���û�����ְ����Ϣ��ȡ�Ķ��������Ϣ��Ҳ����˵�û�ֻ��һ��ְ����Ϣ
	 * ��ô��ôȡ������ν�ˣ�����ȡ���һ����
	 * չʾ�����õ��˷�����
	 * @param userCode ���֤��
	 * @return
	 */
	public static PreDepth getHighestDepth(String userCode){
        PreDepth depth  = null;
        if(userCode!=null){
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
					GonganApplication.dataBassPath, null);
			String sql = "select * from t_pre_depth where user_code = '"+userCode+"'";
			Cursor cur = db.rawQuery(sql, null);
			if (cur.getCount() > 0) {
				depth = new PreDepth();
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					int idColumn = cur.getColumnIndex("id");
					int nameColumn = cur.getColumnIndex("name");
					int stColumn = cur.getColumnIndex("start_time");
					int attColumn = cur.getColumnIndex("attr");
					int lxColumn = cur.getColumnIndex("lx_time");
                    int groupColumn = cur.getColumnIndex("group_code");
                    int ratifyColumn = cur.getColumnIndex("ratify_time");
					depth.setId(cur.getInt(idColumn));
					depth.setName(cur.getString(nameColumn));
					depth.setStartTime(cur.getString(stColumn));
					depth.setAttr(cur.getString(attColumn));
					depth.setLxTime(cur.getString(lxColumn));
					depth.setGroupCode(cur.getString(groupColumn));
					depth.setRatifyTime(cur.getString(ratifyColumn));

				}
			}
			cur.close();
			db.close();
		}
        return depth;
	}
	/**
	 * ��ȡ��Աְ����Ϣ����
	 * @param userCode ����C̖
	 * @return
	 */
	public static List<PreDepth> getPreDepth(String userCode) {
		List<PreDepth> list = null;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				GonganApplication.dataBassPath, null);
		String sql = "select * from t_pre_depth where user_code = '" + userCode+"'"
				+ " order by id ";
		Cursor cur = db.rawQuery(sql, null);
		if (cur.getCount() > 0) {
			list = new ArrayList<PreDepth>();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				int idColumn = cur.getColumnIndex("id");
				int nameColumn = cur.getColumnIndex("name");
				int stColumn = cur.getColumnIndex("start_time");
				int attColumn = cur.getColumnIndex("attr");
				int lxColumn = cur.getColumnIndex("lx_time");
				int groupColumn = cur.getColumnIndex("group_code");
				int ratifyColumn = cur.getColumnIndex("ratify_time");
				PreDepth pd = new PreDepth();
				pd.setId(cur.getInt(idColumn));
				pd.setName(cur.getString(nameColumn));
				pd.setStartTime(cur.getString(stColumn));
				pd.setAttr(cur.getString(attColumn));
                pd.setLxTime(cur.getString(lxColumn));
                pd.setGroupCode(cur.getString(groupColumn));
				pd.setRatifyTime(cur.getString(ratifyColumn));
				list.add(pd);

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
		db.execSQL("delete from t_pre_depth");
		db.close();
	}
}
