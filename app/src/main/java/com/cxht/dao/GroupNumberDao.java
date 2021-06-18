package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.GroupNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构职数信息DAO层
 * Created by HeJiaMing on 2020/11/6 14:21
 * E-Mail Address：1774690@qq.com
 */
public class GroupNumberDao {
    public static List <GroupNumber> getList(){
       List<GroupNumber> list = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_group_number " ;
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            list = new ArrayList <>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                GroupNumber  gn = new GroupNumber();
                gn = pushGroupNumberData(cur);
                list.add(gn);

            }
        }
        cur.close();
        db.close();
       return list;
    }

    /**
     * 获取职数信息
     * @param groupId 机构ID
     * @return
     */
    public static GroupNumber getGN(int groupId){
        GroupNumber gn = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_group_number where group_id=" + groupId;
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            gn = new GroupNumber();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                gn = pushGroupNumberData(cur);

            }
        }
        cur.close();
        db.close();
        return gn;
    }
    public static GroupNumber pushGroupNumberData(Cursor cur){
        GroupNumber gn = new GroupNumber();
        gn.setGroup_id(cur.getInt(cur.getColumnIndex("group_id")));
        gn.setGroup_name(cur.getString(cur.getColumnIndex("group_name")));
        gn.setGroup_code(cur.getString(cur.getColumnIndex("group_code")));
        gn.setZt_ld(cur.getInt(cur.getColumnIndex("zt_ld")));
        gn.setFt_ld(cur.getInt(cur.getColumnIndex("ft_ld")));
        gn.setZt_fld(cur.getInt(cur.getColumnIndex("zt_fld")));
        gn.setFt_fld(cur.getInt(cur.getColumnIndex("ft_fld")));
        gn.setZc_ld(cur.getInt(cur.getColumnIndex("zc_ld")));
        gn.setFc_ld(cur.getInt(cur.getColumnIndex("fc_ld")));
        gn.setZc_fld(cur.getInt(cur.getColumnIndex("zt_fld")));
        gn.setFc_fld(cur.getInt(cur.getColumnIndex("ft_fld")));
        return  gn;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_group_number");
        db.close();
    }
}
