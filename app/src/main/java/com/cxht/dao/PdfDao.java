package com.cxht.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.config.GonganApplication;
import com.cxht.entity.Family;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HeJiaMing on 2019/12/26 14:02
 * E-Mail Address£º1774690@qq.com
 */
public class PdfDao {

    public static HashMap<String,String> getPdfInfo(){

        HashMap<String,String> map = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(

                GonganApplication.dataBassPath, null);
        String sql = "select * from t_pdf_info " ;
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            map = new HashMap<String,String>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                int codeColumn = cur.getColumnIndex("pdf_code");
                int nameColumn = cur.getColumnIndex("pdf_name");
                map.put(cur.getString(codeColumn),cur.getString(nameColumn));

            }
        }
        cur.close();
        db.close();
        return map;
    }
}
