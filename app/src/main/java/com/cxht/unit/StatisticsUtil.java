package com.cxht.unit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxht.bean.AgeGroupBean;
import com.cxht.bean.StatResultBean;
import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据统计项
 * Created by HeJiaMing on 2021/4/13 14:59
 * E-Mail Address：1774690@qq.com
 */
public class StatisticsUtil {
    /**
     * 职务统计项1
     * @param sqlItemParams
     * @return
     */
    public static List <StatResultBean> jobName(String sqlItemParams){
        List<StatResultBean> list = null;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        String where = "";
        String sql = "select count(*) as num,job_name"
                + " from t_job group by  job_name";
        if (!sqlItemParams.equals(null) && !sqlItemParams.equals("")) {
            sql = "select count(*) as num,job_name"
                    + " from  t_job  where  group_code in (" + sqlItemParams + ") group by  job_name";
            where =  " and user_code in (select user_code from t_job where group_code in (" + sqlItemParams + ")";
        }
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            list = new ArrayList<StatResultBean>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                int num = cur.getColumnIndex("num");
                int name = cur.getColumnIndex("job_name");
                StatResultBean result = new StatResultBean();
                result.setCount(cur.getInt(num));
                result.setName(cur.getString(name));
                String andWhere = where + " and job_name = '"+result.getName()+"')";
                result.setAndWhere(andWhere);
                if (result.getName() != null && !result.getName().equals(""))
                    list.add(result);

            }
        }
        cur.close();
        db.close();
        return list;
    }

    /**
     * 职务统计项2
     * @param where
     * @return
     */
    public static List<StatResultBean> jobName2(String where) {
        List<StatResultBean> list = null;

        String sql = "select count(*) as num,job_name as name from t_job";
        sql += " where user_code in (select user_code from t_user "+where+")";
        sql += " group by job_name";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                GonganApplication.dataBassPath, null);
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            list = new ArrayList<StatResultBean>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {

                int num = cur.getColumnIndex("num");
                int name = cur.getColumnIndex("name");
                StatResultBean result = new StatResultBean();
                result.setCount(cur.getInt(num));
                result.setName(cur.getString(name));
                String andWhere = where+" and user_code in (select user_code from t_job where job_name = '"+result.getName()+"') ";
                result.setAndWhere(andWhere);
                if (result.getName() != null && !result.getName().equals(""))
                    list.add(result);
            }
        }
        cur.close();
        db.close();
        return list;
    }

    /**
     * 计算上级组织机构编码
     * 警员库机构编码为3位一个层级，父级机构编码为当前机构ID编码长度减去3位
     * @param groupCode
     * @return
     */
    public static String getParentGroupCode(String groupCode){
        String parentGroup = "0";
        if(groupCode!=null && groupCode.length()>3){
            parentGroup =groupCode.substring(0,groupCode.length()-3);
        }
        return parentGroup;
    }
}
