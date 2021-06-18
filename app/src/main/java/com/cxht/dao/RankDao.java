package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Punish;
import com.cxht.entity.Rank;

import java.util.ArrayList;
import java.util.List;

/**
 * 警衔信息DAO层
 * Created by HeJiaMing on 2020/11/13 15:29
 * E-Mail Address：1774690@qq.com
 */
public class RankDao {
    /**
     * 获取警衔信息
     * @param userCode 身份证号
     * @return
     */
    public static Rank getRank(String userCode) {

       Rank rank = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_rank where user_code='" + userCode+"'";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            rank = new Rank();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                rank = pushData(cur);

            }
        }
        cur.close();
        db.close();
        return rank;
    }
    /**
     * 数据填充
     * @param cur
     * @return
     */
    private static Rank pushData(Cursor cur) {
        Rank rank = new Rank();
        rank.setId(cur.getInt(cur.getColumnIndex("id")));
        rank.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        rank.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        rank.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        rank.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        rank.setRank_name(cur.getString(cur.getColumnIndex("rank_name")));
        return rank;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_rank");
        db.close();
    }
}
