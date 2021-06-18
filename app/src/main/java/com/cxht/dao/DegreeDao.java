package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Degree;
import com.cxht.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 学位DAO层
 * Created by HeJiaMing on 2020/8/18 14:36
 * E-Mail Address：1774690@qq.com
 */
public class DegreeDao {
    /**
     * 获取学位对象列表
     * @param userCode  身份证号
     * @return
     */
    public static List <Degree> getDegreeList(String userCode){
        List<Degree> list = null;
        if(userCode!=null){
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    GonganApplication.dataBassPath, null);
            String sql = "select * from t_degree_us where user_code = '"+userCode+"'";
            Cursor cur = db.rawQuery(sql, null);
            if (cur.getCount() > 0) {
                list = new ArrayList <Degree>();
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    list.add(pushDegreeData(cur));

                }
            }
            cur.close();
            db.close();
        }
        return list;

    }
    /**
     * 填充用户对象
     *
     * @param cur
     * @return
     */
    public static Degree pushDegreeData(Cursor cur) {
        Degree degree = new Degree();
        degree.setId(cur.getInt(cur.getColumnIndex("id")));
        degree.setUser_id(cur.getInt(cur.getColumnIndex("user_id")));
        degree.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        degree.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        degree.setDegree(cur.getString(cur.getColumnIndex("degree")));
        degree.setType(cur.getString(cur.getColumnIndex("type")));
        degree.setSchool(cur.getString(cur.getColumnIndex("school")));
        degree.setDepartment(cur.getString(cur.getColumnIndex("department")));
        degree.setMajor(cur.getString(cur.getColumnIndex("major")));
        degree.setDegree_time(cur.getString(cur.getColumnIndex("degree_time")));
        return degree;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_degree_us");
        db.close();
    }
}
