package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cxht.config.GonganApplication;
import com.cxht.entity.Punish;

import java.util.ArrayList;
import java.util.List;

/**
 * 惩戒表DAO层
 * Created by HeJiaMing on 2020/11/13 09:17
 * E-Mail Address：1774690@qq.com
 */
public class PunishDao {
    /**
     * 惩罚列表
     * @param userCode 身份证号
     * @return
     */
    public static List <Punish> getPunishList(String userCode) {

        List<Punish> list = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_punish where user_code='" + userCode+"' order by punish_time desc";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            list = new ArrayList <Punish>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                list.add(pushData(cur));

            }
        }
        cur.close();
        db.close();
        return list;
    }
    /**
     * 数据填充
     * @param cur
     * @return
     */
    private static Punish pushData(Cursor cur) {
        Punish punish = new Punish();
        punish.setId(cur.getInt(cur.getColumnIndex("id")));
        punish.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        punish.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        punish.setPunish_name(cur.getString(cur.getColumnIndex("punish_name")));
        punish.setPunish_time(cur.getString(cur.getColumnIndex("punish_time")));
        punish.setPunish_type(cur.getString(cur.getColumnIndex("punish_type")));
        return punish;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_punish");
        db.close();
    }
}
