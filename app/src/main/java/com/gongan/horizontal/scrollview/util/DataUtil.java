package com.gongan.horizontal.scrollview.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;

import com.cxht.bean.StatItem;
import com.cxht.config.Setting;
import com.gongan.horizontal.scrollview.CustomListScrollManageFragment;
import com.gongan.horizontal.scrollview.bean.HeadItem;
import com.gongan.horizontal.scrollview.bean.HeadRows;
import com.gongan.horizontal.scrollview.bean.HoriScViewTable;
import com.gongan.horizontal.scrollview.bean.RecordColumn;
import com.gongan.horizontal.scrollview.bean.RecordRows;
import com.cxht.dao.GroupDao;
import com.cxht.dao.TableDao;
import com.cxht.dao.UserDao;
import com.cxht.unit.StringUtil;

public class DataUtil {

    public static final int ZS_HEAD_TYPE = 1; // 直属类型表头
    public static final int ROW_COUNT = 20;
    public static String tag = "data";

    public static HoriScViewTable getHorScViewTable(Context context, int id,
                                                    int headType, String inWhere) {
        tag = StringUtil.getNowDataYear( ) + StringUtil.getNowDataMonth( );
        String name = tag + String.valueOf(id) + String.valueOf(headType);
        if (inWhere != null && !inWhere.equals("")) {
            name += inWhere;
        }
        HoriScViewTable table = (HoriScViewTable) ObjectUtil.getObject(context,
                name);
        if (table == null) {
            table = new HoriScViewTable( );
            HeadRows head = getHeadRow(name);
            if (head != null) {
                table.setHeadRow(head);
                table.setBodyRows(getBodyData(context, name, head.getRows( )
                        .size( ), inWhere));
                ObjectUtil.saveObject(context, table, name);
            }
        }
        return table;
    }

    /**
     * 获取数据源信息
     *
     * @param context
     * @param name    数据源编码名称
     * @param col     模拟数据列总数
     * @return
     */
    private static ArrayList <RecordRows> getBodyData(Context context,
                                                      String name, int col, String inWhere) {

        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        if (name.indexOf(tag + "01") != -1) {
            mList = getZsSumTable(context, ROW_COUNT, ZS_HEAD_TYPE, inWhere);
        } else if (name.indexOf(tag + "02") != -1) {
            mList = getAge(context, ROW_COUNT, ZS_HEAD_TYPE, inWhere);
        } else if (name.indexOf(tag + "03") != -1) {
            mList = getEdu(context, ROW_COUNT, ZS_HEAD_TYPE, inWhere);
        } else if (name.indexOf(tag + "04") != -1) {
            mList = getPositionYear(context, ROW_COUNT, ZS_HEAD_TYPE, inWhere);
        }

        return mList;
    }

    /**
     * 获取总表,表头数据
     *
     * @param name 总表名称编码
     * @return 总表表头对象
     */
    private static HeadRows getHeadRow(String name) {
        tag = StringUtil.getNowDataYear( ) + StringUtil.getNowDataMonth( );
        HeadRows row = null;
        if (name.indexOf(tag + "01") > -1) {
            row = getSummaryHeadRows( );
        }
        if (name.indexOf(tag + "02") > -1) {
            row = getAgeHeadRows( );
        }
        if (name.indexOf(tag + "03") > -1) {
            row = getEduHeadRows( );
        }
        if (name.indexOf(tag + "04") > -1) {
            row = getYearHeadRows( );
        }


        return row;
    }

    private static void setHeadRow(int index, RecordRows row, int headType) {
        switch (headType) {
            case ZS_HEAD_TYPE:
                setZSJG_HeadRow(index, row);
                break;

        }
    }

    /**
     * 内蒙统计,头部样式
     *
     * @param index 行数
     * @param row   行对象
     */
    private static void setZSJG_HeadRow(int index, RecordRows row) {

        switch (index) {
            case 0:
                row.setHeadText("总计");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 1:
                row.setHeadText("正厅级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 2:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 3:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 4:
                row.setHeadText("副厅级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 5:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 6:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 7:
                row.setHeadText("正处级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 8:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 9:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 10:
                row.setHeadText("副处级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 11:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 12:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 13:
                row.setHeadText("正科级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 14:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 15:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 16:
                row.setHeadText("副科级");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 17:
                row.setHeadText("实职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 18:
                row.setHeadText("虚职");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 19:
                row.setHeadText("科员及以下");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;

        }
    }

    public static String toSql(String where) {
        String ret = "select *  from t_user ";
        if (where != null)
            ret += where;
        return ret;
    }

    private static String toZbStr(double d) {

        DecimalFormat df = new DecimalFormat("#.00");
        String ret = df.format(d);
        if (ret.equals("NaN")) ret = "";
        if (ret.equals(".00")) ret = "";
        if (ret.equals(".0")) ret = "";
        return ret;
    }


    /**
     * 根据表头类型获取过滤字符串
     *
     * @param index
     * @param headType
     * @param context
     * @return
     */
    private static String getAndWhere(int index, int headType, Context context,
                                      String inWhere) {
        String ret = "";
        switch (headType) {
            case ZS_HEAD_TYPE:
                ret = getZsAndWhere(index, context, inWhere);
                break;

        }
        return ret;
    }

    private static String getFilterWhere(String inWhere, Context context) {
        String ret = "";

        if (!inWhere.equals("") && inWhere != null) {
            ret = " (user_code in (select user_code from t_job where group_code in ("
                    + inWhere + ")) or user_code in (select user_code from t_pre_depth where group_code in ("
                    + inWhere + "))) ";
        }
        return ret;
    }



    /**
     * 获取直属单位过滤字符串
     *
     * @param index
     * @param context
     * @return
     */
    private static String getZsAndWhere(int index, Context context,
                                        String inWhere) {
        String ret = "";
        String filter = getFilterWhere(inWhere, context);
        switch (index) {
            case 0:
                ret = " and (depth like '%厅局级正职%' or depth like '%一级警务专员%' or depth like '%警务技术一级总监%' or depth like '%一级巡视员%'" +
                        " or depth like '%厅局级副职%' or depth like '%二级警务专员%' or depth like '%警务技术二级总监%' or depth like '%二级巡视员%'" +
                        " or depth like '%县处级正职%' or depth like '%一级高级警长%' or depth like '%二级高级警长%' or depth like '%警务技术一级主任%'" +
                        " or depth like '%警务技术二级主任%' or depth like '%一级调研员%' or depth like '%二级调研员%'" +
                        " or depth like '%县处级副职%' or depth like '%三级高级警长%' or depth like '%四级高级警长%' or depth like '%警务技术三级主任%'" +
                        " or depth like '%警务技术四级主任%' or depth like '%三级调研员%' or depth like '%四级调研员%'" +
                        " or depth like '%乡科级正职%' or depth like '%一级警长%' or depth like '%二级警长%' or depth like '%警务技术一级主管%'" +
                        " or depth like '%警务技术二级主管%' or depth like '%一级主任科员%' or depth like '%二级主任科员%'" +
                        " or depth like '%乡科级副职%' or depth like '%三级警长%' or depth like '%四级警长%' or depth like '%警务技术三级主管%'" +
                        " or depth like '%警务技术四级主管%' or depth like '%三级主任科员%' or depth like '%四级主任科员%'" +
                        " or depth = '科员' or depth like '%办事员%' or depth like '%一级警员%' or depth like '%警务技术员%' or depth like '%一级科员%'" +
                        " or depth like '%二级警员%'  or depth like '%二级科员%') and " + filter;
                break;
            case 1:
                ret = " and (depth like '%厅局级正职%' or depth like '%一级警务专员%' or depth like '%警务技术一级总监%' or depth like '%一级巡视员%') and " + filter;
                break;
            case 2:
                ret = " and (depth like '%厅局级正职%' or depth like '%一级警务专员%' or depth like '%警务技术一级总监%' or depth like '%一级巡视员%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 3:
                ret = " and (depth like '%厅局级正职%' or depth like '%一级警务专员%' or depth like '%警务技术一级总监%' or depth like '%一级巡视员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 4:
                ret = " and (depth like '%厅局级副职%' or depth like '%二级警务专员%' or depth like '%警务技术二级总监%' or depth like '%二级巡视员%') and " + filter;
                break;
            case 5:
                ret = " and (depth like '%厅局级副职%' or depth like '%二级警务专员%' or depth like '%警务技术二级总监%' or depth like '%二级巡视员%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 6:
                ret = " and (depth like '%厅局级副职%' or depth like '%二级警务专员%' or depth like '%警务技术二级总监%' or depth like '%二级巡视员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 7:
                ret = " and (depth like '%县处级正职%' or depth like '%一级高级警长%' or depth like '%二级高级警长%' or depth like '%警务技术一级主任%'" +
                        " or depth like '%警务技术二级主任%' or depth like '%一级调研员%' or depth like '%二级调研员%') and " + filter;
                break;
            case 8:
                ret = " and (depth like '%县处级正职%' or depth like '%一级高级警长%' or depth like '%二级高级警长%' or depth like '%警务技术一级主任%'" +
                        " or depth like '%警务技术二级主任%' or depth like '%一级调研员%' or depth like '%二级调研员%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 9:
                ret = " and (depth like '%县处级正职%' or depth like '%一级高级警长%' or depth like '%二级高级警长%' or depth like '%警务技术一级主任%'" +
                        " or depth like '%警务技术二级主任%' or depth like '%一级调研员%' or depth like '%二级调研员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 10:
                ret = " and (depth like '%县处级副职%' or depth like '%三级高级警长%' or depth like '%四级高级警长%' or depth like '%警务技术三级主任%'" +
                        " or depth like '%警务技术四级主任%' or depth like '%三级调研员%' or depth like '%四级调研员%') and " + filter;
                break;
            case 11:
                ret = " and (depth like '%县处级副职%' or depth like '%三级高级警长%' or depth like '%四级高级警长%' or depth like '%警务技术三级主任%'" +
                        " or depth like '%警务技术四级主任%' or depth like '%三级调研员%' or depth like '%四级调研员%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 12:
                ret = " and (depth like '%县处级副职%' or depth like '%三级高级警长%' or depth like '%四级高级警长%' or depth like '%警务技术三级主任%'" +
                        " or depth like '%警务技术四级主任%' or depth like '%三级调研员%' or depth like '%四级调研员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 13:
                ret = " and (depth like '%乡科级正职%' or depth like '%一级警长%' or depth like '%二级警长%' or depth like '%警务技术一级主管%'" +
                        " or depth like '%警务技术二级主管%' or depth like '%一级主任科员%' or depth like '%二级主任科员%') and " + filter;
                break;
            case 14:
                ret = " and (depth like '%乡科级正职%' or depth like '%一级警长%' or depth like '%二级警长%' or depth like '%警务技术一级主管%'" +
                        " or depth like '%警务技术二级主管%' or depth like '%一级主任科员%' or depth like '%二级主任科员%') and  user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 15:
                ret = " and (depth like '%乡科级正职%' or depth like '%一级警长%' or depth like '%二级警长%' or depth like '%警务技术一级主管%'" +
                        " or depth like '%警务技术二级主管%' or depth like '%一级主任科员%' or depth like '%二级主任科员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 16:
                ret = " and (depth like '%乡科级副职%' or depth like '%三级警长%' or depth like '%四级警长%' or depth like '%警务技术三级主管%'" +
                        " or depth like '%警务技术四级主管%' or depth like '%三级主任科员%' or depth like '%四级主任科员%') and " + filter;
                break;
            case 17:
                ret = " and (depth like '%乡科级副职%' or depth like '%三级警长%' or depth like '%四级警长%' or depth like '%警务技术三级主管%'" +
                        " or depth like '%警务技术四级主管%' or depth like '%三级主任科员%' or depth like '%四级主任科员%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 18:
                ret = " and (depth like '%乡科级副职%' or depth like '%三级警长%' or depth like '%四级警长%' or depth like '%警务技术三级主管%'" +
                        " or depth like '%警务技术四级主管%' or depth like '%三级主任科员%' or depth like '%四级主任科员%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 19:
                ret = " and (depth = '科员' or depth like '%办事员%' or depth like '%一级警员%' or depth like '%警务技术员%' or depth like '%一级科员%'" +
                        " or depth like '%二级警员%'  or depth like '%二级科员%') and " + filter;
                break;


        }
        return ret;
    }

    /**
     * 年龄详细分布
     *
     * @param context
     * @return
     */
    private static ArrayList <RecordRows> getAge(Context context, int rCount,
                                                 int headType, String inWhere) {
        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        for (int i = 0; i < rCount; i++) {
            RecordRows row = new RecordRows( );
            setHeadRow(i, row, headType);
            String andWhere = getAndWhere(i, headType, context, inWhere);
            // 行列 --总数
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            mList.add(row);
            // 行列 --平均年龄
            String w18 = " where 1=1 " + andWhere;
            String s18 = "select cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double)  / cast((select count(*) from t_user " +
                    "where 1=1 "
                    + andWhere + ") as double) as size from t_user" + w18;
            double age = UserDao.getUserAge(context, s18);
            String v18 = StringUtil.toAgeStr(age);
            row.addRecordRow(new RecordColumn(s1, v18));
            // 行列 --35岁及以下（年龄）
            String w2 = " where birth_date > '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "'" + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // 行列 --(年龄)
            for (int j = 36; j <= 60; j++) {
                String w = " where (birth_date <= '"
                        + StringUtil.getNowYM((j * 12) * (-1))
                        + "' and birth_date > '"
                        + StringUtil.getNowYM((j * 12 + 12) * (-1)) + "') "
                        + andWhere;
                String s = toSql(w);
                // String v = String.valueOf(TableDao.getTableCount(context,
                // s));
                row.addRecordRow(new RecordColumn(s, ""));
            }

        }
        mList = TableDao.execTransaction(context, mList);
        return mList;
    }

    /**
     * 学历详细情况表
     *
     * @param context
     * @return
     */
    private static ArrayList <RecordRows> getEdu(Context context, int rCount,
                                                 int headType, String inWhere) {
        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        for (int i = 0; i < rCount; i++) {
            RecordRows row = new RecordRows( );
            setHeadRow(i, row, headType);
            String andWhere = getAndWhere(i, headType, context, inWhere);
            // 行列 --总数
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            // 行列 --博士研究生（全日制最高学历)
            String w2 = " where before_education like '%博士研究生%' " + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // 行列 --硕士研究生（全日制最高学历)
            String w3 = " where before_education like '%硕士研究生%' " + andWhere;
            String s3 = toSql(w3);
            // String v3 = String.valueOf(TableDao.getTableCount(context, s3));
            row.addRecordRow(new RecordColumn(s3, ""));

            // 行列 --大学（全日制最高学历）
            String w4 = " where before_education like '%大学%' " + andWhere;
            String s4 = toSql(w4);
            // String v4 = String.valueOf(TableDao.getTableCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, ""));
            // 行列 --大专（全日制最高学历）
            String w5 = " where before_education like '%大专%' " + andWhere;
            String s5 = toSql(w5);
            // String v5 = String.valueOf(TableDao.getTableCount(context, s5));
            row.addRecordRow(new RecordColumn(s5, ""));
            // 行列 --中专（全日制最高学历）
            String w6 = " where before_education like '%中专%' " + andWhere;
            String s6 = toSql(w6);
            // String v6 = String.valueOf(TableDao.getTableCount(context, s6));
            row.addRecordRow(new RecordColumn(s6, ""));

            // 行列 --高中（全日制最高学历）
            String w7 = " where before_education like '%高中%' " + andWhere;
            String s7 = toSql(w7);
            // String v7 = String.valueOf(TableDao.getTableCount(context, s7));
            row.addRecordRow(new RecordColumn(s7, ""));

            // 行列 --初中（全日制最高学历）
            String w8 = " where before_education like '%初中%' " + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(TableDao.getTableCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));
            /**
             * // 行列 --公安类（全日制最高学历专业类别） String w11 = " where 1 <> 1 ";//
             * " where before_specialty like '%公安类%' "+ // andWhere; String s11
             * = toSql(w11); // String v11 =
             * String.valueOf(TableDao.getTableCount(context, // s11));
             * row.addRecordRow(new RecordColumn(s11, ""));
             *
             * // 行列 --文学（全日制最高学历专业类别） String w12 = " where 1 <> 1 "; //
             * " where before_specialty like '%文学%' "+ // andWhere; String s12 =
             * toSql(w12); // String v12 =
             * String.valueOf(TableDao.getTableCount(context, // s12));
             * row.addRecordRow(new RecordColumn(s12, ""));
             *
             * // 行列 --法学（全日制最高学历专业类别） String w13 = " where 1 <> 1 "; //
             * " where before_specialty like '%法学%' "+ // andWhere; String s13 =
             * toSql(w13); // String v13 =
             * String.valueOf(TableDao.getTableCount(context, // s13));
             * row.addRecordRow(new RecordColumn(s13, ""));
             *
             * // 行列 --工学（全日制最高学历专业类别） String w14 = " where 1 <> 1 "; //
             * " where before_specialty like '%工学%' "+ // andWhere; String s14 =
             * toSql(w14); // String v14 =
             * String.valueOf(TableDao.getTableCount(context, // s14));
             * row.addRecordRow(new RecordColumn(s14, ""));
             *
             * // 行列 --理学（全日制最高学历专业类别） String w15 = " where 1 <> 1 "; //
             * " where before_specialty like '%理学%' "+ // andWhere; String s15 =
             * toSql(w15); // String v15 =
             * String.valueOf(TableDao.getTableCount(context, // s15));
             * row.addRecordRow(new RecordColumn(s15, ""));
             *
             * // 行列 --经济学（全日制最高学历专业类别） String w16 = " where 1 <> 1 "; //
             * " where before_specialty like '%经济学%' "+ // andWhere; String s16
             * = toSql(w16); // String v16 =
             * String.valueOf(TableDao.getTableCount(context, // s16));
             * row.addRecordRow(new RecordColumn(s16, ""));
             *
             * // 行列 --其他（全日制最高学历专业类别） String w17 = " where 1 <> 1 "; String s17
             * = toSql(w17); // String v17 =
             * String.valueOf(TableDao.getTableCount(context, // s17));
             * row.addRecordRow(new RecordColumn(s17, ""));
             **/
            // 行列 --博士研究生（在职最高学历）
            String w18 = " where latest_education like '%博士研究生%' " + andWhere;
            String s18 = toSql(w18);
            // String v18 = String.valueOf(TableDao.getTableCount(context,
            // s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // 行列 --硕士研究生（在职最高学历）
            String w19 = " where latest_education like '%硕士研究生%' " + andWhere;
            String s19 = toSql(w19);
            // String v19 = String.valueOf(TableDao.getTableCount(context,
            // s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // 行列 --中央党校研究生（在职最高学历）
            String w20 = " where latest_education like '%中央党校研究生%' " + andWhere;
            String s20 = toSql(w20);
            // String v20 = String.valueOf(TableDao.getTableCount(context,
            // s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // 行列 --省委党校研究生（在职最高学历)
            String w21 = " where latest_education like '%委党校研究生%' " + andWhere;
            String s21 = toSql(w21);
            // String v21 = String.valueOf(TableDao.getTableCount(context,
            // s21));
            row.addRecordRow(new RecordColumn(s21, ""));
            // 行列 --大学（在职最高学历）
            String w22 = " where latest_education like '%大学%' " + andWhere;
            String s22 = toSql(w22);
            // String v22 = String.valueOf(TableDao.getTableCount(context,
            // s22));
            row.addRecordRow(new RecordColumn(s22, ""));

            // 行列 --具有硕士学位（研究生）
            String w23 = " where latest_education like '%中央党校大学%' " + andWhere;
            String s23 = toSql(w23);
            // String v23 = String.valueOf(TableDao.getTableCount(context,
            // s23));
            row.addRecordRow(new RecordColumn(s23, ""));
            // 行列 --大学本科（在职最高学历）
            String w24 = " where latest_education like '%委党校大学%' " + andWhere;
            String s24 = toSql(w24);
            // String v24 = String.valueOf(TableDao.getTableCount(context,
            // s24));
            row.addRecordRow(new RecordColumn(s24, ""));
            // 行列 --大学专科（在职最高学历）
            String w25 = " where latest_education like '%大专%' " + andWhere;
            String s25 = toSql(w25);
            // String v25 = String.valueOf(TableDao.getTableCount(context,
            // s25));
            row.addRecordRow(new RecordColumn(s25, ""));
            /**
             * // 行列 --公安类（在职最高学历专业类别） String w26 = " where 1 <> 1 " + andWhere;
             * String s26 = toSql(w26); // String v26 =
             * String.valueOf(TableDao.getTableCount(context, // s26));
             * row.addRecordRow(new RecordColumn(s26, ""));
             *
             * // 行列 --文学（在职最高学历专业类别） String w27 = " where 1 <> 1 " + andWhere;
             * String s27 = toSql(w27); // String v30 =
             * String.valueOf(TableDao.getTableCount(context, // s30));
             * row.addRecordRow(new RecordColumn(s27, ""));
             *
             * // 行列 --法学（在职最高学历专业类别） String w28 = " where 1 <> 1 " + andWhere;
             * String s28 = toSql(w28); // String v31 =
             * String.valueOf(TableDao.getTableCount(context, // s31));
             * row.addRecordRow(new RecordColumn(s28, ""));
             *
             * // 行列 --工学（在职最高学历专业类别） String w29 = " where 1 <> 1 " + andWhere;
             * String s29 = toSql(w29); // String v32 =
             * String.valueOf(TableDao.getTableCount(context, // s32));
             * row.addRecordRow(new RecordColumn(s29, ""));
             *
             * // 行列 --理学（在职最高学历专业类别） String w30 = " where 1 <> 1 " + andWhere;
             * String s30 = toSql(w30); // String v33 =
             * String.valueOf(TableDao.getTableCount(context, // s33));
             * row.addRecordRow(new RecordColumn(s30, ""));
             *
             * // 行列 --经济学（在职最高学历专业类别） String w31 = " where 1 <> 1 " + andWhere;
             * String s31 = toSql(w31); // String v34 =
             * String.valueOf(TableDao.getTableCount(context, // s34));
             * row.addRecordRow(new RecordColumn(s31, ""));
             *
             * // 行列 --其他（在职最高学历专业类别） String w32 = " where 1 <> 1 " + andWhere;
             * String s32 = toSql(w32); // String v35 =
             * String.valueOf(TableDao.getTableCount(context, // s35));
             * row.addRecordRow(new RecordColumn(s32, ""));
             **/
            mList.add(row);

        }
        mList = TableDao.execTransaction(context, mList);
        return mList;
    }

    /**
     * 任现职、现职级年限表
     *
     * @param context
     * @return
     */
    private static ArrayList <RecordRows> getPositionYear(Context context,
                                                          int rCount, int headType, String inWhere) {
        TableDao.createDBView(context);
        // 生成统计数据
        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        for (int i = 0; i < rCount; i++) {
            RecordRows row = new RecordRows( );
            setHeadRow(i, row, headType);
            String andWhere = getAndWhere(i, headType, context, inWhere);
            // 行列 --总数
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(UserDao.getUserCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            // 行列 --不满1年（现任职职年限）
            String w2 = " where pre_job_time >'" + StringUtil.getNowYM(-12)
                    + "'" + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(UserDao.getUserCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // 行列 --1年（现任职级年限）
            String w3 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((1 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((1 * 12) * (-1)) + "') " + andWhere;
            String s3 = toSql(w3);
            // String v3 = String.valueOf(UserDao.getUserCount(context, s3));
            row.addRecordRow(new RecordColumn(s3, ""));
            // 行列 --2年（现任职级年限）
            String w4 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((2 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((2 * 12) * (-1)) + "') " + andWhere;
            String s4 = toSql(w4);
            // String v4 = String.valueOf(UserDao.getUserCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, ""));
            // 行列 --3年（现任职级年限）
            String w5 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((3 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((3 * 12) * (-1)) + "') " + andWhere;
            String s5 = toSql(w5);
            // String v5 = String.valueOf(UserDao.getUserCount(context, s5));
            row.addRecordRow(new RecordColumn(s5, ""));
            // 行列 --4年（现任职级年限）
            String w6 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((4 * 12) * (-1)) + "') " + andWhere;
            String s6 = toSql(w6);
            // String v6 = String.valueOf(UserDao.getUserCount(context, s6));
            row.addRecordRow(new RecordColumn(s6, ""));
            // 行列 --5年（现任职级年限）
            String w7 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((5 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s7 = toSql(w7);
            // String v7 = String.valueOf(UserDao.getUserCount(context, s7));
            row.addRecordRow(new RecordColumn(s7, ""));

            // 行列 --6年（现任职级年限）
            String w8 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((6 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((6 * 12) * (-1)) + "') " + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(UserDao.getUserCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));

            // 行列 --7年（现任职级年限）
            String w9 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((7 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((7 * 12) * (-1)) + "') " + andWhere;
            String s9 = toSql(w9);
            // String v9 = String.valueOf(UserDao.getUserCount(context, s9));
            row.addRecordRow(new RecordColumn(s9, ""));

            // 行列 --8年（现任职级年限）
            String w10 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((8 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((8 * 12) * (-1)) + "') " + andWhere;
            String s10 = toSql(w10);
            // String v10 = String.valueOf(UserDao.getUserCount(context, s10));
            row.addRecordRow(new RecordColumn(s10, ""));

            // 行列 --9年（现任职级年限）
            String w11 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((9 * 12) * (-1)) + "') " + andWhere;
            String s11 = toSql(w11);
            // String v11 = String.valueOf(UserDao.getUserCount(context, s11));
            row.addRecordRow(new RecordColumn(s11, ""));

            // 行列 --10年及以上（现任职级年限）
            String w12 = " where pre_job_time <='"
                    + StringUtil.getNowYM(-12 * 10) + "'" + andWhere;
            String s12 = toSql(w12);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s12, ""));

            // 行列 --不满1年（现任职级年限）
            String w13 = " where pre_depth_time >'" + StringUtil.getNowYM(-12)
                    + "'" + andWhere;

            String s13 = toSql(w13);
            // String v13 = String.valueOf(TableDao.getTableCount(context,
            // s13));
            row.addRecordRow(new RecordColumn(s13, ""));

            // 行列 --1年（现任职级年限）
            String w14 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((1 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((1 * 12) * (-1)) + "') " + andWhere;

            String s14 = toSql(w14);
            // String v14 = String.valueOf(TableDao.getTableCount(context,
            // s14));
            row.addRecordRow(new RecordColumn(s14, ""));

            // 行列 --2年（现任职级年限）
            String w15 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((2 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((2 * 12) * (-1)) + "') " + andWhere;

            String s15 = toSql(w15);
            // String v15 = String.valueOf(TableDao.getTableCount(context,
            // s15));
            row.addRecordRow(new RecordColumn(s15, ""));
            // 行列 --3年（现任职级年限）
            String w16 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((3 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((3 * 12) * (-1)) + "') " + andWhere;

            String s16 = toSql(w16);
            // String v16 = String.valueOf(TableDao.getTableCount(context,
            // s16));
            row.addRecordRow(new RecordColumn(s16, ""));

            // 行列 --4年（现任职级年限）
            String w17 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((4 * 12) * (-1)) + "') " + andWhere;

            String s17 = toSql(w17);
            // String v17 = String.valueOf(TableDao.getTableCount(context,
            // s17));
            row.addRecordRow(new RecordColumn(s17, ""));

            // 行列 --5年（现任职级年限）
            String w18 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((5 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;

            String s18 = toSql(w18);
            // String v18 = String.valueOf(TableDao.getTableCount(context,
            // s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // 行列 --6年（现任职级年限）
            String w19 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((6 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((6 * 12) * (-1)) + "') " + andWhere;

            String s19 = toSql(w19);
            // String v19 = String.valueOf(TableDao.getTableCount(context,
            // s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // 行列 --7年（现任职级年限）
            String w20 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((7 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((7 * 12) * (-1)) + "') " + andWhere;

            String s20 = toSql(w20);
            // String v20 = String.valueOf(TableDao.getTableCount(context,
            // s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // 行列 --8年（现任职级年限）
            String w21 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((8 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((8 * 12) * (-1)) + "') " + andWhere;

            String s21 = toSql(w21);
            // String v21 = String.valueOf(TableDao.getTableCount(context,
            // s21));
            row.addRecordRow(new RecordColumn(s21, ""));
            // 行列 --9年（现任职级年限）
            String w22 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((9 * 12) * (-1)) + "') " + andWhere;

            String s22 = toSql(w22);
            // String v22 = String.valueOf(TableDao.getTableCount(context,
            // s22));
            row.addRecordRow(new RecordColumn(s22, ""));

            // 行列 --满10年及以上（现任职级年限）
            String w23 = " where " + "pre_depth_time <='"
                    + StringUtil.getNowYM(-12 * 10) + "'" + andWhere;

            String s23 = toSql(w23);
            // String v23 = String.valueOf(TableDao.getTableCount(context,
            // s23));
            row.addRecordRow(new RecordColumn(s23, ""));
            mList.add(row);

        }
        mList = TableDao.execTransaction(context, mList);
        return mList;
    }

    /**
     * 厅机关及直属单位总表
     *
     * @param context
     * @return
     */
    private static ArrayList <RecordRows> getZsSumTable(Context context,
                                                        int rCount, int headType, String inWhere) {
        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        for (int i = 0; i < rCount; i++) {
            RecordRows row = new RecordRows( );
            setHeadRow(i, row, headType);
            String andWhere = getAndWhere(i, headType, context, inWhere);
            // 行列 --总数
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, v1));
            // 行列 --小计（女性）
            String w2 = " where sex ='女'" + andWhere;
            String s2 = toSql(w2);
            String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, v2));
            // 行列 --占比（女性）

            double d3 = (Double.parseDouble(v2) / Double.parseDouble(v1)) * 100;
            String v3 = toZbStr(d3);
            row.addRecordRow(new RecordColumn(s2, v3));
            // 行列 --小计（少数民族）
            String w4 = " where nation<>'汉族'" + andWhere;
            String s4 = toSql(w4);
            String v4 = String.valueOf(TableDao.getTableCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, v4));
            // 行列 --占比（少数民族）
            String w5 = " where nation<>'汉族'" + andWhere;
            double d5 = (Double.parseDouble(v4) / Double.parseDouble(v1)) * 100;
            String v5 = toZbStr(d5);
            row.addRecordRow(new RecordColumn(s4, v5));
            // 平均年龄
            String w6 = " where 1=1 " + andWhere;
            String s6 = "select cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double)  / cast((select count(*) from t_user " +
                    "where 1=1 "
                    + andWhere + ") as double) as size from t_user" + w6;
            double age = UserDao.getUserAge(context, s6);
            String v6 = StringUtil.toAgeStr(age);
            row.addRecordRow(new RecordColumn(s1, v6));

            // 行列--35岁及以下(年龄)
            String w15 = " where  birth_date > '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "'" + andWhere;
            String s15 = toSql(w15);
            // String v15 = String.valueOf(UserDao.getUserCount(context, s15));
            row.addRecordRow(new RecordColumn(s15, ""));
            // 行列--36岁至40(年龄)
            String w16 = " where  (birth_date > '"
                    + StringUtil.getNowYM((40 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "') " + andWhere;
            String s16 = toSql(w16);
            // String v16 = String.valueOf(UserDao.getUserCount(context, s16));
            row.addRecordRow(new RecordColumn(s16, ""));

            // 行列--41岁至45(年龄)
            String w17 = " where (birth_date > '"
                    + StringUtil.getNowYM((45 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((41 * 12) * (-1)) + "') " + andWhere;
            String s17 = toSql(w17);
            // String v17 = String.valueOf(UserDao.getUserCount(context, s17));
            row.addRecordRow(new RecordColumn(s17, ""));

            // 行列--46岁至50(年龄)
            String w18 = " where (birth_date > '"
                    + StringUtil.getNowYM((50 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((46 * 12) * (-1)) + "') " + andWhere;
            String s18 = toSql(w18);
            // String v18 = String.valueOf(UserDao.getUserCount(context, s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // 行列--51岁至55(年龄)
            String w19 = " where (birth_date > '"
                    + StringUtil.getNowYM((55 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((51 * 12) * (-1)) + "') " + andWhere;
            String s19 = toSql(w19);
            // String v19 = String.valueOf(UserDao.getUserCount(context, s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // 行列--56岁至60(年龄)
            String w20 = " where (birth_date >'"
                    + StringUtil.getNowYM((60 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((56 * 12) * (-1)) + "') " + andWhere;
            String s20 = toSql(w20);
            // String v20 = String.valueOf(UserDao.getUserCount(context, s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // 行列 --研究生（全日制最高学历）
            String w8 = " where before_education like'%研究生%'" + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(UserDao.getUserCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));

            // 行列 --大学（全日制最高学历）
            String w9 = " where before_education like'%大学%'" + andWhere;
            String s9 = toSql(w9);
            // String v9 = String.valueOf(UserDao.getUserCount(context, s9));
            row.addRecordRow(new RecordColumn(s9, ""));

            // 行列 --大专（全日制最高学历）
            String w10 = " where  (before_education like'%大普%' or before_education like '%大专%' or before_education like '%专科%')"
                    + andWhere;
            String s10 = toSql(w10);
            // String v10 = String.valueOf(UserDao.getUserCount(context, s10));
            row.addRecordRow(new RecordColumn(s10, ""));

            // 行列 --中专及以下（全日制最高学历）
            String w11 = " where   (before_education like'%中专%' or before_education like '%高中%' or before_education like '%技校%' or before_education like '%初中%')"
                    + andWhere;
            String s11 = toSql(w11);
            // String v11 = String.valueOf(UserDao.getUserCount(context, s11));
            row.addRecordRow(new RecordColumn(s11, ""));

            // 行列 --4年以下（任现职时间）
            String w12 = " where  pre_job_time > '"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1)) + "'" + andWhere;
            String s12 = toSql(w12);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s12, ""));

            // 行列 --5-9年（任现职时间）
            String w13 = " where (pre_job_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s13 = toSql(w13);
            // String v13 = String.valueOf(UserDao.getUserCount(context, s13));
            row.addRecordRow(new RecordColumn(s13, ""));

            // 行列 --10年以上（任现职时间）
            String w14 = " where pre_job_time <= '"
                    + StringUtil.getNowYM((10 * 12) * (-1)) + "'" + andWhere;
            String s14 = toSql(w14);
            // String v14 = String.valueOf(UserDao.getUserCount(context, s14));
            row.addRecordRow(new RecordColumn(s14, ""));

            // 行列 --4年以下（任现职级时间）
            String w22 = " where  pre_depth_time > '"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1)) + "'" + andWhere;
            String s22 = toSql(w22);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s22, ""));

            // 行列 --5-9年（任现职级时间）
            String w23 = " where (pre_depth_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s23 = toSql(w23);
            // String v13 = String.valueOf(UserDao.getUserCount(context, s13));
            row.addRecordRow(new RecordColumn(s23, ""));

            // 行列 --10年以上（任现职级时间）
            String w24 = " where pre_depth_time <= '"
                    + StringUtil.getNowYM((10 * 12) * (-1)) + "'" + andWhere;
            String s24 = toSql(w24);
            // String v14 = String.valueOf(UserDao.getUserCount(context, s14));
            row.addRecordRow(new RecordColumn(s24, ""));

            mList.add(row);
        }
        mList = TableDao.execTransaction(context, mList);
        return mList;
    }

    private static HeadRows getSummaryHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("女", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("性", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("少数", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("民族", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "平均年龄", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35岁及以下", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36至40岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("年", "41至45岁", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("龄", "46至50岁", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51至55岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56至60岁", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("全日制", "大学", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("最高学历", "大专", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "中专及以下", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职时间", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职级时间", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));

        return rows;
    }

    private static HeadRows getSummaryHeadRows3() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "正处级", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("职", "副处级", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("级", "正科级", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "副科级", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("女", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("性", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("少数", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("民族", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "平均年龄", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35岁及以下", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36至40岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("年", "41至45岁", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("龄", "46至50岁", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51至55岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56至60岁", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("全日制", "大学", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("最高学历", "大专", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "中专及以下", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职时间", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职级时间", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));

        return rows;
    }

    private static HeadRows getSummaryHeadRows2() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "正厅级", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("职", "副厅级", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("级", "正处级", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "副处级", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("女", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("性", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("少数", "计", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("民族", "占比", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "平均年龄", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35岁及以下", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36至40岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("年", "41至45岁", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("龄", "46至50岁", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51至55岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56至60岁", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("全日制", "大学", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("最高学历", "大专", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "中专及以下", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职时间", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4年以下", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("任现职级时间		", "5-9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年以上", true, Gravity.LEFT));
        return rows;
    }

    private static HeadRows getYearHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "不满1年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("任", "1年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("现", "2年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("职", "3年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("年", "4年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("限", "5年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "6年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "7年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "8年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年及以上", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "不满1年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("任", "1年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("现", "2年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("职", "3年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("级", "4年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("年", "5年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("限", "6年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "7年", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "8年", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "9年", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10年及以上", true, Gravity.CENTER));

        return rows;
    }

    private static HeadRows getEduHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "博士研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "硕士研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "大学", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("全日制", "大专", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("最高学历", "中专", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "高中", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "初中", true, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "公安类", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "文学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("全日", "法学", false, Gravity.RIGHT));
        // rows.addHeadItem(new HeadItem("制最高学历", "工学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("专业类别", "理学", false, Gravity.LEFT));
        // rows.addHeadItem(new HeadItem("", "经济学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "其他", true, Gravity.CENTER));

        rows.addHeadItem(new HeadItem("", "博士研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "硕士研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "中央党校研究生", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("在职最", "省委党校研究生", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("高学历", "大学", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "中央党校大学", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "省委党校大学", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "大专", true, Gravity.CENTER));

        // rows.addHeadItem(new HeadItem("", "公安类", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "文学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("在", "法学", false, Gravity.RIGHT));
        // rows.addHeadItem(new HeadItem("职最高学历", "工学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("专业类别", "理学", false, Gravity.LEFT));
        // rows.addHeadItem(new HeadItem("", "经济学", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "其他", false, Gravity.CENTER));

        return rows;
    }

    private static HeadRows getAgeHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("级别");
        rows.addHeadItem(new HeadItem("", "总数", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "平均年龄", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35岁及以下", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "37岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "38岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "39岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "40岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "41岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "42岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "43岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "44岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "45岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "46岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "47岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "48岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "49岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "50岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "51岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "52岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "53岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "54岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "55岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "57岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "58岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "59岁", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "60岁", true, Gravity.CENTER));

        return rows;
    }



    private static void addEduPieView(String title, int rIndex,
                                      HoriScViewTable table, List <StatItem> list) {
        // 总人数
        int mCt = table.getRecordColumn(rIndex, 0).getValueOfInt( );
        // 统计值
        int vCt = 0;
        StatItem s11 = new StatItem( );
        s11.setTitle(title + "全日制最高学历统计");
        vCt = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("研究生", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("大学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 6).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("大专", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 7).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("中专", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 8).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("高中", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 9).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("初中以下", vCt);
        }
        list.add(s11);

        StatItem s12 = new StatItem( );
        s12.setTitle(title + "全日制研究生学位统计");
        int yjs = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 2).getValueOfInt( );
        yjs = yjs - vCt;
        if (vCt > 0) {
            s12.addItem("博士学位", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 3).getValueOfInt( );
        yjs = yjs - vCt;
        if (vCt > 0) {
            s12.addItem("硕士学位", vCt);
        }
        if (yjs > 0) {
            s12.addItem("无学位", yjs);
        }
        list.add(s12);

        StatItem s13 = new StatItem( );
        s13.setTitle(title + "全日制大学学位统计");
        int dx = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 5).getValueOfInt( );
        dx = dx - vCt;
        if (vCt > 0) {
            s13.addItem("学士学位", vCt);
        }
        if (dx > 0) {
            s13.addItem("无学位", dx);
        }
        list.add(s13);

        StatItem s14 = new StatItem( );
        s14.setTitle(title + "全日制学历专业统计");
        int cCt = table.getRecordColumn(rIndex, 0).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 10).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("哲学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 11).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("经济学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 12).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("法学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 13).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("教育学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 14).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("文学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 15).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("历史学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 16).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("理学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 17).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("工学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 18).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("农学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 19).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("医学", vCt);
        }
        if (cCt > 0) {
            s14.addItem("其他专业", cCt);
        }
        list.add(s14);

        int zCt = 0;
        StatItem s15 = new StatItem( );
        s15.setTitle(title + "在职最高学历统计");
        vCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("研究生", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 23).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("大学本科", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 25).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("大学专科", vCt);
        }
        list.add(s15);

        StatItem s16 = new StatItem( );
        s16.setTitle(title + "在职研究生学位统计");
        int zyCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 21).getValueOfInt( );
        zyCt = zyCt - vCt;
        if (vCt > 0) {
            s16.addItem("博士学位", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 22).getValueOfInt( );
        zyCt = zyCt - vCt;
        if (vCt > 0) {
            s16.addItem("硕士学位", vCt);
        }
        if (zyCt > 0) {
            s16.addItem("无学位", zyCt);
        }
        list.add(s16);

        StatItem s17 = new StatItem( );
        s17.setTitle(title + "在职大学学位统计");
        int zdx = table.getRecordColumn(rIndex, 23).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 24).getValueOfInt( );
        zdx = zdx - vCt;
        if (vCt > 0) {
            s17.addItem("学士学位", vCt);
        }
        if (zdx > 0) {
            s17.addItem("无学位", zdx);
        }
        list.add(s17);

        StatItem s18 = new StatItem( );
        s18.setTitle(title + "在职学历专业统计");
        int zeCt = zCt;
        vCt = table.getRecordColumn(rIndex, 26).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("哲学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 27).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("经济学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 28).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("法学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 29).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("教育学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 30).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("文学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 31).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("历史学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 32).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("理学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 33).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("工学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 34).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("农学", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 35).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("医学", vCt);
        }
        if (zeCt > 0) {
            s18.addItem("其他专业", zeCt);
        }
        list.add(s18);

    }

    private static void addPieView(String title, int rIndex,
                                   HoriScViewTable table, List <StatItem> list) {

        // 统计值
        int vCt = 0;
        StatItem s11 = new StatItem( );
        s11.setTitle(title + "现任职级年限");
        vCt = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("不满1年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 2).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("1年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 3).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("2年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("3年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 5).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("4年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 6).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("5年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 7).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("6年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 8).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("7年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 9).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("8年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 10).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("9年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("10年及以上", vCt);
        }

        list.add(s11);

        StatItem s12 = new StatItem( );
        s12.setTitle(title + "主要职务年限");
        vCt = table.getRecordColumn(rIndex, 12).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("不满1年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 13).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("1年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 14).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("2年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 15).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("3年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 16).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("4年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 17).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("5年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 18).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("6年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 19).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("7年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("8年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 21).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("9年", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 22).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("10年及以上", vCt);
        }

        list.add(s12);
    }
}
