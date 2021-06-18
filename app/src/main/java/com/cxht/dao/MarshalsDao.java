package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Marshals;
import com.cxht.entity.Rank;

/**
 * 执法资格DAO层
 * Created by HeJiaMing on 2020/11/30 16:30
 * E-Mail Address：1774690@qq.com
 */
public class MarshalsDao {
    /**
     * 获取执法资格信息
     * @param userCode 身份证号
     * @return
     */
    public static Marshals getMarshals(String userCode) {

        Marshals mar = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_marshals where user_code='" + userCode+"'";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            mar = new Marshals();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                mar = pushData(cur);

            }
        }
        cur.close();
        db.close();
        return mar;
    }
    /**
     * 数据填充
     * @param cur
     * @return
     */
    private static Marshals pushData(Cursor cur) {
        Marshals mar = new Marshals();
        mar.setId(cur.getInt(cur.getColumnIndex("id")));
        mar.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        mar.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        mar.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        mar.setLevel(cur.getString(cur.getColumnIndex("level")));
        mar.setStart_time(cur.getString(cur.getColumnIndex("start_time")));
        return mar;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_marshals");
        db.close();
    }
}
