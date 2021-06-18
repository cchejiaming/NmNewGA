package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Qualification;
import com.gov.tree.TreeNodeCheck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 职称DAO层
 * Created by HeJiaMing on 2020/8/19 14:20
 * E-Mail Address：1774690@qq.com
 */
public class QualificationDao {
    /**
     * 获取职称对象列表
     * @param userCode  身份证号
     * @return
     */
    public static List <Qualification> getQualificationList(String userCode){
        List<Qualification> list = null;
        if (userCode!=null){
            SQLiteDatabase db = null;
            Cursor cur = null;
            try{
                db = SQLiteDatabase.openOrCreateDatabase(
                        GonganApplication.dataBassPath, null);
                String sql = "select * from t_qualification where user_code = '"+userCode+"'";
                cur = db.rawQuery(sql, null);
                if (cur.getCount() > 0) {
                    list = new ArrayList <Qualification>();
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                        list.add(pushQualificationData(cur));

                    }
                }
            }catch (Exception e) {
                e.printStackTrace( );
            }finally{
                if(db!= null && db.isOpen()) db.close();
                if (cur!= null ) cur.close();
            }
        }
        return list;

    }

    /**
     * 填充用户对象
     *
     * @param cur
     * @return
     */
    public static Qualification pushQualificationData(Cursor cur) {
        Qualification qua = new Qualification();
        qua.setId(cur.getInt(cur.getColumnIndex("id")));
        qua.setUser_id(cur.getString(cur.getColumnIndex("user_id")));
        qua.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        qua.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        qua.setQfc_name(cur.getString(cur.getColumnIndex("qfc_name")));
        qua.setGroup_code(cur.getString(cur.getColumnIndex("group_code")));
        qua.setGet_time(cur.getString(cur.getColumnIndex("get_time")));
        return qua;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_qualification");
        db.close();
    }
}
