package com.cxht.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Gun;
import com.cxht.entity.Train;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ѵ��ϢDao��
 * Created by HeJiaMing on 2020/12/1 09:42
 * E-Mail Address��1774690@qq.com
 */
public class TrainDao {
    /**
     * ��ȡ��ѵ��Ϣ�б�
     * @param userCode ���֤��
     * @return
     */
    public static List <Train> getTrain(String userCode) {

        List <Train> data = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String sql = "select * from t_train where user_code='" + userCode+"'";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            data = new ArrayList <>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                Train   train = new Train();
                train = pushData(cur);
                data.add(train);
            }
        }
        cur.close();
        db.close();
        return data;
    }
    /**
     * �������
     * @param cur
     * @return
     */
    private static Train pushData(Cursor cur) {
        Train train = new Train();
        train.setId(cur.getInt(cur.getColumnIndex("id")));
        train.setOrg_code(cur.getString(cur.getColumnIndex("org_code")));
        train.setUser_code(cur.getString(cur.getColumnIndex("user_code")));
        train.setUser_name(cur.getString(cur.getColumnIndex("user_name")));
        train.setClass_name(cur.getString(cur.getColumnIndex("class_name")));
        train.setDesc(cur.getString(cur.getColumnIndex("desc")));
        train.setStart_time(cur.getString(cur.getColumnIndex("start_time")));
        train.setEnd_time(cur.getString(cur.getColumnIndex("end_time")));
        return train;
    }
    /**
     * ��ռ�¼
     * @param context
     */
    public static void deleteAll(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        db.execSQL("delete from t_train");
        db.close();
    }
}
