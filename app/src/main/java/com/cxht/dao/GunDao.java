package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cxht.config.GonganApplication;
import com.cxht.entity.Gun;

/**
 * 持枪证信息Dao层
 * Created by HeJiaMing on 2020/11/30 17:26
 * E-Mail Address：1774690@qq.com
 */
public class GunDao {
    /**
     * 获取持枪证格信息
     * @param userCode 身份证号
     * @return
     */
    public static Gun getGun(String userCode) {

        Gun gun = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_gun where user_code='" + userCode+"'";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            gun = new Gun();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                gun = pushData(cur);

            }
        }
        cur.close();
        db.close();
        return gun;
    }
    /**
     * 数据填充
     * @param cur
     * @return
     */
    private static Gun pushData(Cursor cur) {
        Gun gun = new Gun();
        gun.setId(cur.getInt(cur.getColumnIndex("id")));
        gun.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        gun.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        gun.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        gun.setLicence(cur.getString(cur.getColumnIndex("licence")));
        gun.setLssuing(cur.getString(cur.getColumnIndex("lssuing")));
        gun.setLssue_date(cur.getString(cur.getColumnIndex("lssue_date")));
        gun.setRenewal_date(cur.getString(cur.getColumnIndex("renewal_date")));
        return gun;
    }
    /**
     * 清空记录
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_gun");
        db.close();
    }
}
