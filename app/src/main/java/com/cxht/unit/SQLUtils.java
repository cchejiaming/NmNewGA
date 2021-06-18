package com.cxht.unit;

import android.util.Log;

/**
 * sql �������Ԫ
 * Created by HeJiaMing on 2020/11/20 11:05
 * E-Mail Address��1774690@qq.com
 */
public class SQLUtils {
    /**
     * ƽ������
     * @param andWhere sql���׷��where����
     * @return ��ִ��sql���
     */
    public static String averageAge(String andWhere){
        String sql = "select  cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double) / cast((select count(*) from t_user " +
                "where 1=1 "
                + andWhere + ") as Integer) as size from t_user where 1=1 " + andWhere;
        Log.i("SqlEx",sql);
        return  sql;

    }

    /**
     * ƽ������
     * @param sqlItems ����in�ַ���
     * @return ��ִ��sql���
     */
    public static String averageAgeOfGroup(String sqlItems){
        String 	sql = "select  cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double)  / cast((select count(*) from t_user "
                + "where user_code in (select user_code from t_job where group_code in ("
                + sqlItems + "))) as Integer) as size from t_user "
                + "where user_code in (select user_code from t_job where group_code in ("+ sqlItems + "))";
        Log.i("SqlEx",sql);
        return  sql;
    }

    /**
     *�û���׷��sql���
     * @param andWhere
     * @return
     */
    public static String userAddWhere(String andWhere){
        String sql = "select * from t_user where 1=1 "+andWhere;
        Log.i("SqlEx",sql);
        return sql;
    }
    public static String sqlItemsToAndWhere (String sqlItems){
       return  " and user_code in (select user_code from t_job where group_id in ("  + sqlItems + ") order by job_bz,sort) ";
    }

}
