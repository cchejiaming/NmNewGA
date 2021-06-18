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

    public static final int ZS_HEAD_TYPE = 1; // ֱ�����ͱ�ͷ
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
     * ��ȡ����Դ��Ϣ
     *
     * @param context
     * @param name    ����Դ��������
     * @param col     ģ������������
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
     * ��ȡ�ܱ�,��ͷ����
     *
     * @param name �ܱ����Ʊ���
     * @return �ܱ��ͷ����
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
     * ����ͳ��,ͷ����ʽ
     *
     * @param index ����
     * @param row   �ж���
     */
    private static void setZSJG_HeadRow(int index, RecordRows row) {

        switch (index) {
            case 0:
                row.setHeadText("�ܼ�");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 1:
                row.setHeadText("������");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 2:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 3:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 4:
                row.setHeadText("������");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 5:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 6:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 7:
                row.setHeadText("������");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 8:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 9:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 10:
                row.setHeadText("������");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 11:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 12:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 13:
                row.setHeadText("���Ƽ�");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 14:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 15:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 16:
                row.setHeadText("���Ƽ�");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_DEFAULT);
                break;
            case 17:
                row.setHeadText("ʵְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD);
                break;
            case 18:
                row.setHeadText("��ְ");
                row.setHeadStyle(CustomListScrollManageFragment.HEAD_CHILD_FOOT);
                break;
            case 19:
                row.setHeadText("��Ա������");
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
     * ���ݱ�ͷ���ͻ�ȡ�����ַ���
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
     * ��ȡֱ����λ�����ַ���
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
                ret = " and (depth like '%���ּ���ְ%' or depth like '%һ������רԱ%' or depth like '%������һ���ܼ�%' or depth like '%һ��Ѳ��Ա%'" +
                        " or depth like '%���ּ���ְ%' or depth like '%��������רԱ%' or depth like '%�����������ܼ�%' or depth like '%����Ѳ��Ա%'" +
                        " or depth like '%�ش�����ְ%' or depth like '%һ���߼�����%' or depth like '%�����߼�����%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ������Ա%' or depth like '%��������Ա%'" +
                        " or depth like '%�ش�����ְ%' or depth like '%�����߼�����%' or depth like '%�ļ��߼�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%��������Ա%' or depth like '%�ļ�����Ա%'" +
                        " or depth like '%��Ƽ���ְ%' or depth like '%һ������%' or depth like '%��������%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ�����ο�Ա%' or depth like '%�������ο�Ա%'" +
                        " or depth like '%��Ƽ���ְ%' or depth like '%��������%' or depth like '%�ļ�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%�������ο�Ա%' or depth like '%�ļ����ο�Ա%'" +
                        " or depth = '��Ա' or depth like '%����Ա%' or depth like '%һ����Ա%' or depth like '%������Ա%' or depth like '%һ����Ա%'" +
                        " or depth like '%������Ա%'  or depth like '%������Ա%') and " + filter;
                break;
            case 1:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%һ������רԱ%' or depth like '%������һ���ܼ�%' or depth like '%һ��Ѳ��Ա%') and " + filter;
                break;
            case 2:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%һ������רԱ%' or depth like '%������һ���ܼ�%' or depth like '%һ��Ѳ��Ա%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 3:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%һ������רԱ%' or depth like '%������һ���ܼ�%' or depth like '%һ��Ѳ��Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 4:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%��������רԱ%' or depth like '%�����������ܼ�%' or depth like '%����Ѳ��Ա%') and " + filter;
                break;
            case 5:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%��������רԱ%' or depth like '%�����������ܼ�%' or depth like '%����Ѳ��Ա%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 6:
                ret = " and (depth like '%���ּ���ְ%' or depth like '%��������רԱ%' or depth like '%�����������ܼ�%' or depth like '%����Ѳ��Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 7:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%һ���߼�����%' or depth like '%�����߼�����%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ������Ա%' or depth like '%��������Ա%') and " + filter;
                break;
            case 8:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%һ���߼�����%' or depth like '%�����߼�����%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ������Ա%' or depth like '%��������Ա%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 9:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%һ���߼�����%' or depth like '%�����߼�����%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ������Ա%' or depth like '%��������Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 10:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%�����߼�����%' or depth like '%�ļ��߼�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%��������Ա%' or depth like '%�ļ�����Ա%') and " + filter;
                break;
            case 11:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%�����߼�����%' or depth like '%�ļ��߼�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%��������Ա%' or depth like '%�ļ�����Ա%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 12:
                ret = " and (depth like '%�ش�����ְ%' or depth like '%�����߼�����%' or depth like '%�ļ��߼�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%��������Ա%' or depth like '%�ļ�����Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 13:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%һ������%' or depth like '%��������%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ�����ο�Ա%' or depth like '%�������ο�Ա%') and " + filter;
                break;
            case 14:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%һ������%' or depth like '%��������%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ�����ο�Ա%' or depth like '%�������ο�Ա%') and  user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 15:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%һ������%' or depth like '%��������%' or depth like '%������һ������%'" +
                        " or depth like '%��������������%' or depth like '%һ�����ο�Ա%' or depth like '%�������ο�Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 16:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%��������%' or depth like '%�ļ�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%�������ο�Ա%' or depth like '%�ļ����ο�Ա%') and " + filter;
                break;
            case 17:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%��������%' or depth like '%�ļ�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%�������ο�Ա%' or depth like '%�ļ����ο�Ա%') and user_type = '"+ Setting.USER_TYPE_LD+"' and " + filter;
                break;
            case 18:
                ret = " and (depth like '%��Ƽ���ְ%' or depth like '%��������%' or depth like '%�ļ�����%' or depth like '%��������������%'" +
                        " or depth like '%�������ļ�����%' or depth like '%�������ο�Ա%' or depth like '%�ļ����ο�Ա%') and user_type = '"+ Setting.USER_TYPE_FLD+"' and " + filter;
                break;
            case 19:
                ret = " and (depth = '��Ա' or depth like '%����Ա%' or depth like '%һ����Ա%' or depth like '%������Ա%' or depth like '%һ����Ա%'" +
                        " or depth like '%������Ա%'  or depth like '%������Ա%') and " + filter;
                break;


        }
        return ret;
    }

    /**
     * ������ϸ�ֲ�
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
            // ���� --����
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            mList.add(row);
            // ���� --ƽ������
            String w18 = " where 1=1 " + andWhere;
            String s18 = "select cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double)  / cast((select count(*) from t_user " +
                    "where 1=1 "
                    + andWhere + ") as double) as size from t_user" + w18;
            double age = UserDao.getUserAge(context, s18);
            String v18 = StringUtil.toAgeStr(age);
            row.addRecordRow(new RecordColumn(s1, v18));
            // ���� --35�꼰���£����䣩
            String w2 = " where birth_date > '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "'" + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // ���� --(����)
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
     * ѧ����ϸ�����
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
            // ���� --����
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            // ���� --��ʿ�о�����ȫ�������ѧ��)
            String w2 = " where before_education like '%��ʿ�о���%' " + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // ���� --˶ʿ�о�����ȫ�������ѧ��)
            String w3 = " where before_education like '%˶ʿ�о���%' " + andWhere;
            String s3 = toSql(w3);
            // String v3 = String.valueOf(TableDao.getTableCount(context, s3));
            row.addRecordRow(new RecordColumn(s3, ""));

            // ���� --��ѧ��ȫ�������ѧ����
            String w4 = " where before_education like '%��ѧ%' " + andWhere;
            String s4 = toSql(w4);
            // String v4 = String.valueOf(TableDao.getTableCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, ""));
            // ���� --��ר��ȫ�������ѧ����
            String w5 = " where before_education like '%��ר%' " + andWhere;
            String s5 = toSql(w5);
            // String v5 = String.valueOf(TableDao.getTableCount(context, s5));
            row.addRecordRow(new RecordColumn(s5, ""));
            // ���� --��ר��ȫ�������ѧ����
            String w6 = " where before_education like '%��ר%' " + andWhere;
            String s6 = toSql(w6);
            // String v6 = String.valueOf(TableDao.getTableCount(context, s6));
            row.addRecordRow(new RecordColumn(s6, ""));

            // ���� --���У�ȫ�������ѧ����
            String w7 = " where before_education like '%����%' " + andWhere;
            String s7 = toSql(w7);
            // String v7 = String.valueOf(TableDao.getTableCount(context, s7));
            row.addRecordRow(new RecordColumn(s7, ""));

            // ���� --���У�ȫ�������ѧ����
            String w8 = " where before_education like '%����%' " + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(TableDao.getTableCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));
            /**
             * // ���� --�����ࣨȫ�������ѧ��רҵ��� String w11 = " where 1 <> 1 ";//
             * " where before_specialty like '%������%' "+ // andWhere; String s11
             * = toSql(w11); // String v11 =
             * String.valueOf(TableDao.getTableCount(context, // s11));
             * row.addRecordRow(new RecordColumn(s11, ""));
             *
             * // ���� --��ѧ��ȫ�������ѧ��רҵ��� String w12 = " where 1 <> 1 "; //
             * " where before_specialty like '%��ѧ%' "+ // andWhere; String s12 =
             * toSql(w12); // String v12 =
             * String.valueOf(TableDao.getTableCount(context, // s12));
             * row.addRecordRow(new RecordColumn(s12, ""));
             *
             * // ���� --��ѧ��ȫ�������ѧ��רҵ��� String w13 = " where 1 <> 1 "; //
             * " where before_specialty like '%��ѧ%' "+ // andWhere; String s13 =
             * toSql(w13); // String v13 =
             * String.valueOf(TableDao.getTableCount(context, // s13));
             * row.addRecordRow(new RecordColumn(s13, ""));
             *
             * // ���� --��ѧ��ȫ�������ѧ��רҵ��� String w14 = " where 1 <> 1 "; //
             * " where before_specialty like '%��ѧ%' "+ // andWhere; String s14 =
             * toSql(w14); // String v14 =
             * String.valueOf(TableDao.getTableCount(context, // s14));
             * row.addRecordRow(new RecordColumn(s14, ""));
             *
             * // ���� --��ѧ��ȫ�������ѧ��רҵ��� String w15 = " where 1 <> 1 "; //
             * " where before_specialty like '%��ѧ%' "+ // andWhere; String s15 =
             * toSql(w15); // String v15 =
             * String.valueOf(TableDao.getTableCount(context, // s15));
             * row.addRecordRow(new RecordColumn(s15, ""));
             *
             * // ���� --����ѧ��ȫ�������ѧ��רҵ��� String w16 = " where 1 <> 1 "; //
             * " where before_specialty like '%����ѧ%' "+ // andWhere; String s16
             * = toSql(w16); // String v16 =
             * String.valueOf(TableDao.getTableCount(context, // s16));
             * row.addRecordRow(new RecordColumn(s16, ""));
             *
             * // ���� --������ȫ�������ѧ��רҵ��� String w17 = " where 1 <> 1 "; String s17
             * = toSql(w17); // String v17 =
             * String.valueOf(TableDao.getTableCount(context, // s17));
             * row.addRecordRow(new RecordColumn(s17, ""));
             **/
            // ���� --��ʿ�о�������ְ���ѧ����
            String w18 = " where latest_education like '%��ʿ�о���%' " + andWhere;
            String s18 = toSql(w18);
            // String v18 = String.valueOf(TableDao.getTableCount(context,
            // s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // ���� --˶ʿ�о�������ְ���ѧ����
            String w19 = " where latest_education like '%˶ʿ�о���%' " + andWhere;
            String s19 = toSql(w19);
            // String v19 = String.valueOf(TableDao.getTableCount(context,
            // s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // ���� --���뵳У�о�������ְ���ѧ����
            String w20 = " where latest_education like '%���뵳У�о���%' " + andWhere;
            String s20 = toSql(w20);
            // String v20 = String.valueOf(TableDao.getTableCount(context,
            // s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // ���� --ʡί��У�о�������ְ���ѧ��)
            String w21 = " where latest_education like '%ί��У�о���%' " + andWhere;
            String s21 = toSql(w21);
            // String v21 = String.valueOf(TableDao.getTableCount(context,
            // s21));
            row.addRecordRow(new RecordColumn(s21, ""));
            // ���� --��ѧ����ְ���ѧ����
            String w22 = " where latest_education like '%��ѧ%' " + andWhere;
            String s22 = toSql(w22);
            // String v22 = String.valueOf(TableDao.getTableCount(context,
            // s22));
            row.addRecordRow(new RecordColumn(s22, ""));

            // ���� --����˶ʿѧλ���о�����
            String w23 = " where latest_education like '%���뵳У��ѧ%' " + andWhere;
            String s23 = toSql(w23);
            // String v23 = String.valueOf(TableDao.getTableCount(context,
            // s23));
            row.addRecordRow(new RecordColumn(s23, ""));
            // ���� --��ѧ���ƣ���ְ���ѧ����
            String w24 = " where latest_education like '%ί��У��ѧ%' " + andWhere;
            String s24 = toSql(w24);
            // String v24 = String.valueOf(TableDao.getTableCount(context,
            // s24));
            row.addRecordRow(new RecordColumn(s24, ""));
            // ���� --��ѧר�ƣ���ְ���ѧ����
            String w25 = " where latest_education like '%��ר%' " + andWhere;
            String s25 = toSql(w25);
            // String v25 = String.valueOf(TableDao.getTableCount(context,
            // s25));
            row.addRecordRow(new RecordColumn(s25, ""));
            /**
             * // ���� --�����ࣨ��ְ���ѧ��רҵ��� String w26 = " where 1 <> 1 " + andWhere;
             * String s26 = toSql(w26); // String v26 =
             * String.valueOf(TableDao.getTableCount(context, // s26));
             * row.addRecordRow(new RecordColumn(s26, ""));
             *
             * // ���� --��ѧ����ְ���ѧ��רҵ��� String w27 = " where 1 <> 1 " + andWhere;
             * String s27 = toSql(w27); // String v30 =
             * String.valueOf(TableDao.getTableCount(context, // s30));
             * row.addRecordRow(new RecordColumn(s27, ""));
             *
             * // ���� --��ѧ����ְ���ѧ��רҵ��� String w28 = " where 1 <> 1 " + andWhere;
             * String s28 = toSql(w28); // String v31 =
             * String.valueOf(TableDao.getTableCount(context, // s31));
             * row.addRecordRow(new RecordColumn(s28, ""));
             *
             * // ���� --��ѧ����ְ���ѧ��רҵ��� String w29 = " where 1 <> 1 " + andWhere;
             * String s29 = toSql(w29); // String v32 =
             * String.valueOf(TableDao.getTableCount(context, // s32));
             * row.addRecordRow(new RecordColumn(s29, ""));
             *
             * // ���� --��ѧ����ְ���ѧ��רҵ��� String w30 = " where 1 <> 1 " + andWhere;
             * String s30 = toSql(w30); // String v33 =
             * String.valueOf(TableDao.getTableCount(context, // s33));
             * row.addRecordRow(new RecordColumn(s30, ""));
             *
             * // ���� --����ѧ����ְ���ѧ��רҵ��� String w31 = " where 1 <> 1 " + andWhere;
             * String s31 = toSql(w31); // String v34 =
             * String.valueOf(TableDao.getTableCount(context, // s34));
             * row.addRecordRow(new RecordColumn(s31, ""));
             *
             * // ���� --��������ְ���ѧ��רҵ��� String w32 = " where 1 <> 1 " + andWhere;
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
     * ����ְ����ְ�����ޱ�
     *
     * @param context
     * @return
     */
    private static ArrayList <RecordRows> getPositionYear(Context context,
                                                          int rCount, int headType, String inWhere) {
        TableDao.createDBView(context);
        // ����ͳ������
        ArrayList <RecordRows> mList = new ArrayList <RecordRows>( );
        for (int i = 0; i < rCount; i++) {
            RecordRows row = new RecordRows( );
            setHeadRow(i, row, headType);
            String andWhere = getAndWhere(i, headType, context, inWhere);
            // ���� --����
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            // String v1 = String.valueOf(UserDao.getUserCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, ""));
            // ���� --����1�꣨����ְְ���ޣ�
            String w2 = " where pre_job_time >'" + StringUtil.getNowYM(-12)
                    + "'" + andWhere;
            String s2 = toSql(w2);
            // String v2 = String.valueOf(UserDao.getUserCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, ""));
            // ���� --1�꣨����ְ�����ޣ�
            String w3 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((1 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((1 * 12) * (-1)) + "') " + andWhere;
            String s3 = toSql(w3);
            // String v3 = String.valueOf(UserDao.getUserCount(context, s3));
            row.addRecordRow(new RecordColumn(s3, ""));
            // ���� --2�꣨����ְ�����ޣ�
            String w4 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((2 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((2 * 12) * (-1)) + "') " + andWhere;
            String s4 = toSql(w4);
            // String v4 = String.valueOf(UserDao.getUserCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, ""));
            // ���� --3�꣨����ְ�����ޣ�
            String w5 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((3 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((3 * 12) * (-1)) + "') " + andWhere;
            String s5 = toSql(w5);
            // String v5 = String.valueOf(UserDao.getUserCount(context, s5));
            row.addRecordRow(new RecordColumn(s5, ""));
            // ���� --4�꣨����ְ�����ޣ�
            String w6 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((4 * 12) * (-1)) + "') " + andWhere;
            String s6 = toSql(w6);
            // String v6 = String.valueOf(UserDao.getUserCount(context, s6));
            row.addRecordRow(new RecordColumn(s6, ""));
            // ���� --5�꣨����ְ�����ޣ�
            String w7 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((5 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s7 = toSql(w7);
            // String v7 = String.valueOf(UserDao.getUserCount(context, s7));
            row.addRecordRow(new RecordColumn(s7, ""));

            // ���� --6�꣨����ְ�����ޣ�
            String w8 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((6 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((6 * 12) * (-1)) + "') " + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(UserDao.getUserCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));

            // ���� --7�꣨����ְ�����ޣ�
            String w9 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((7 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((7 * 12) * (-1)) + "') " + andWhere;
            String s9 = toSql(w9);
            // String v9 = String.valueOf(UserDao.getUserCount(context, s9));
            row.addRecordRow(new RecordColumn(s9, ""));

            // ���� --8�꣨����ְ�����ޣ�
            String w10 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((8 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((8 * 12) * (-1)) + "') " + andWhere;
            String s10 = toSql(w10);
            // String v10 = String.valueOf(UserDao.getUserCount(context, s10));
            row.addRecordRow(new RecordColumn(s10, ""));

            // ���� --9�꣨����ְ�����ޣ�
            String w11 = " where ( pre_job_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((9 * 12) * (-1)) + "') " + andWhere;
            String s11 = toSql(w11);
            // String v11 = String.valueOf(UserDao.getUserCount(context, s11));
            row.addRecordRow(new RecordColumn(s11, ""));

            // ���� --10�꼰���ϣ�����ְ�����ޣ�
            String w12 = " where pre_job_time <='"
                    + StringUtil.getNowYM(-12 * 10) + "'" + andWhere;
            String s12 = toSql(w12);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s12, ""));

            // ���� --����1�꣨����ְ�����ޣ�
            String w13 = " where pre_depth_time >'" + StringUtil.getNowYM(-12)
                    + "'" + andWhere;

            String s13 = toSql(w13);
            // String v13 = String.valueOf(TableDao.getTableCount(context,
            // s13));
            row.addRecordRow(new RecordColumn(s13, ""));

            // ���� --1�꣨����ְ�����ޣ�
            String w14 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((1 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((1 * 12) * (-1)) + "') " + andWhere;

            String s14 = toSql(w14);
            // String v14 = String.valueOf(TableDao.getTableCount(context,
            // s14));
            row.addRecordRow(new RecordColumn(s14, ""));

            // ���� --2�꣨����ְ�����ޣ�
            String w15 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((2 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((2 * 12) * (-1)) + "') " + andWhere;

            String s15 = toSql(w15);
            // String v15 = String.valueOf(TableDao.getTableCount(context,
            // s15));
            row.addRecordRow(new RecordColumn(s15, ""));
            // ���� --3�꣨����ְ�����ޣ�
            String w16 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((3 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((3 * 12) * (-1)) + "') " + andWhere;

            String s16 = toSql(w16);
            // String v16 = String.valueOf(TableDao.getTableCount(context,
            // s16));
            row.addRecordRow(new RecordColumn(s16, ""));

            // ���� --4�꣨����ְ�����ޣ�
            String w17 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((4 * 12) * (-1)) + "') " + andWhere;

            String s17 = toSql(w17);
            // String v17 = String.valueOf(TableDao.getTableCount(context,
            // s17));
            row.addRecordRow(new RecordColumn(s17, ""));

            // ���� --5�꣨����ְ�����ޣ�
            String w18 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((5 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;

            String s18 = toSql(w18);
            // String v18 = String.valueOf(TableDao.getTableCount(context,
            // s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // ���� --6�꣨����ְ�����ޣ�
            String w19 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((6 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((6 * 12) * (-1)) + "') " + andWhere;

            String s19 = toSql(w19);
            // String v19 = String.valueOf(TableDao.getTableCount(context,
            // s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // ���� --7�꣨����ְ�����ޣ�
            String w20 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((7 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((7 * 12) * (-1)) + "') " + andWhere;

            String s20 = toSql(w20);
            // String v20 = String.valueOf(TableDao.getTableCount(context,
            // s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // ���� --8�꣨����ְ�����ޣ�
            String w21 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((8 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((8 * 12) * (-1)) + "') " + andWhere;

            String s21 = toSql(w21);
            // String v21 = String.valueOf(TableDao.getTableCount(context,
            // s21));
            row.addRecordRow(new RecordColumn(s21, ""));
            // ���� --9�꣨����ְ�����ޣ�
            String w22 = " where " + "(pre_depth_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((9 * 12) * (-1)) + "') " + andWhere;

            String s22 = toSql(w22);
            // String v22 = String.valueOf(TableDao.getTableCount(context,
            // s22));
            row.addRecordRow(new RecordColumn(s22, ""));

            // ���� --��10�꼰���ϣ�����ְ�����ޣ�
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
     * �����ؼ�ֱ����λ�ܱ�
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
            // ���� --����
            String w1 = " where 1=1 " + andWhere;
            String s1 = toSql(w1);
            String v1 = String.valueOf(TableDao.getTableCount(context, s1));
            row.addRecordRow(new RecordColumn(s1, v1));
            // ���� --С�ƣ�Ů�ԣ�
            String w2 = " where sex ='Ů'" + andWhere;
            String s2 = toSql(w2);
            String v2 = String.valueOf(TableDao.getTableCount(context, s2));
            row.addRecordRow(new RecordColumn(s2, v2));
            // ���� --ռ�ȣ�Ů�ԣ�

            double d3 = (Double.parseDouble(v2) / Double.parseDouble(v1)) * 100;
            String v3 = toZbStr(d3);
            row.addRecordRow(new RecordColumn(s2, v3));
            // ���� --С�ƣ��������壩
            String w4 = " where nation<>'����'" + andWhere;
            String s4 = toSql(w4);
            String v4 = String.valueOf(TableDao.getTableCount(context, s4));
            row.addRecordRow(new RecordColumn(s4, v4));
            // ���� --ռ�ȣ��������壩
            String w5 = " where nation<>'����'" + andWhere;
            double d5 = (Double.parseDouble(v4) / Double.parseDouble(v1)) * 100;
            String v5 = toZbStr(d5);
            row.addRecordRow(new RecordColumn(s4, v5));
            // ƽ������
            String w6 = " where 1=1 " + andWhere;
            String s6 = "select cast(sum(cast((julianday(datetime('now','localtime'))  - julianday((birth_date || '-01'))-1) / 365 as Integer)) as double)  / cast((select count(*) from t_user " +
                    "where 1=1 "
                    + andWhere + ") as double) as size from t_user" + w6;
            double age = UserDao.getUserAge(context, s6);
            String v6 = StringUtil.toAgeStr(age);
            row.addRecordRow(new RecordColumn(s1, v6));

            // ����--35�꼰����(����)
            String w15 = " where  birth_date > '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "'" + andWhere;
            String s15 = toSql(w15);
            // String v15 = String.valueOf(UserDao.getUserCount(context, s15));
            row.addRecordRow(new RecordColumn(s15, ""));
            // ����--36����40(����)
            String w16 = " where  (birth_date > '"
                    + StringUtil.getNowYM((40 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((36 * 12) * (-1)) + "') " + andWhere;
            String s16 = toSql(w16);
            // String v16 = String.valueOf(UserDao.getUserCount(context, s16));
            row.addRecordRow(new RecordColumn(s16, ""));

            // ����--41����45(����)
            String w17 = " where (birth_date > '"
                    + StringUtil.getNowYM((45 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((41 * 12) * (-1)) + "') " + andWhere;
            String s17 = toSql(w17);
            // String v17 = String.valueOf(UserDao.getUserCount(context, s17));
            row.addRecordRow(new RecordColumn(s17, ""));

            // ����--46����50(����)
            String w18 = " where (birth_date > '"
                    + StringUtil.getNowYM((50 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((46 * 12) * (-1)) + "') " + andWhere;
            String s18 = toSql(w18);
            // String v18 = String.valueOf(UserDao.getUserCount(context, s18));
            row.addRecordRow(new RecordColumn(s18, ""));

            // ����--51����55(����)
            String w19 = " where (birth_date > '"
                    + StringUtil.getNowYM((55 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((51 * 12) * (-1)) + "') " + andWhere;
            String s19 = toSql(w19);
            // String v19 = String.valueOf(UserDao.getUserCount(context, s19));
            row.addRecordRow(new RecordColumn(s19, ""));

            // ����--56����60(����)
            String w20 = " where (birth_date >'"
                    + StringUtil.getNowYM((60 * 12 + 12) * (-1))
                    + "' and birth_date <= '"
                    + StringUtil.getNowYM((56 * 12) * (-1)) + "') " + andWhere;
            String s20 = toSql(w20);
            // String v20 = String.valueOf(UserDao.getUserCount(context, s20));
            row.addRecordRow(new RecordColumn(s20, ""));

            // ���� --�о�����ȫ�������ѧ����
            String w8 = " where before_education like'%�о���%'" + andWhere;
            String s8 = toSql(w8);
            // String v8 = String.valueOf(UserDao.getUserCount(context, s8));
            row.addRecordRow(new RecordColumn(s8, ""));

            // ���� --��ѧ��ȫ�������ѧ����
            String w9 = " where before_education like'%��ѧ%'" + andWhere;
            String s9 = toSql(w9);
            // String v9 = String.valueOf(UserDao.getUserCount(context, s9));
            row.addRecordRow(new RecordColumn(s9, ""));

            // ���� --��ר��ȫ�������ѧ����
            String w10 = " where  (before_education like'%����%' or before_education like '%��ר%' or before_education like '%ר��%')"
                    + andWhere;
            String s10 = toSql(w10);
            // String v10 = String.valueOf(UserDao.getUserCount(context, s10));
            row.addRecordRow(new RecordColumn(s10, ""));

            // ���� --��ר�����£�ȫ�������ѧ����
            String w11 = " where   (before_education like'%��ר%' or before_education like '%����%' or before_education like '%��У%' or before_education like '%����%')"
                    + andWhere;
            String s11 = toSql(w11);
            // String v11 = String.valueOf(UserDao.getUserCount(context, s11));
            row.addRecordRow(new RecordColumn(s11, ""));

            // ���� --4�����£�����ְʱ�䣩
            String w12 = " where  pre_job_time > '"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1)) + "'" + andWhere;
            String s12 = toSql(w12);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s12, ""));

            // ���� --5-9�꣨����ְʱ�䣩
            String w13 = " where (pre_job_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_job_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s13 = toSql(w13);
            // String v13 = String.valueOf(UserDao.getUserCount(context, s13));
            row.addRecordRow(new RecordColumn(s13, ""));

            // ���� --10�����ϣ�����ְʱ�䣩
            String w14 = " where pre_job_time <= '"
                    + StringUtil.getNowYM((10 * 12) * (-1)) + "'" + andWhere;
            String s14 = toSql(w14);
            // String v14 = String.valueOf(UserDao.getUserCount(context, s14));
            row.addRecordRow(new RecordColumn(s14, ""));

            // ���� --4�����£�����ְ��ʱ�䣩
            String w22 = " where  pre_depth_time > '"
                    + StringUtil.getNowYM((4 * 12 + 12) * (-1)) + "'" + andWhere;
            String s22 = toSql(w22);
            // String v12 = String.valueOf(UserDao.getUserCount(context, s12));
            row.addRecordRow(new RecordColumn(s22, ""));

            // ���� --5-9�꣨����ְ��ʱ�䣩
            String w23 = " where (pre_depth_time >'"
                    + StringUtil.getNowYM((9 * 12 + 12) * (-1))
                    + "' and pre_depth_time <= '"
                    + StringUtil.getNowYM((5 * 12) * (-1)) + "') " + andWhere;
            String s23 = toSql(w23);
            // String v13 = String.valueOf(UserDao.getUserCount(context, s13));
            row.addRecordRow(new RecordColumn(s23, ""));

            // ���� --10�����ϣ�����ְ��ʱ�䣩
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
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("Ů", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("����", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "ƽ������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35�꼰����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36��40��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "41��45��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "46��50��", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51��55��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56��60��", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ȫ����", "��ѧ", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("���ѧ��", "��ר", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "��ר������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְʱ��", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְ��ʱ��", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));

        return rows;
    }

    private static HeadRows getSummaryHeadRows3() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "������", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ְ", "������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "���Ƽ�", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "���Ƽ�", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("Ů", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("����", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "ƽ������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35�꼰����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36��40��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "41��45��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "46��50��", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51��55��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56��60��", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ȫ����", "��ѧ", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("���ѧ��", "��ר", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "��ר������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְʱ��", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְ��ʱ��", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));

        return rows;
    }

    private static HeadRows getSummaryHeadRows2() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "������", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ְ", "������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "������", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("Ů", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("����", "��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����", "ռ��", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "ƽ������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35�꼰����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36��40��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "41��45��", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��", "46��50��", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "51��55��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56��60��", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ȫ����", "��ѧ", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("���ѧ��", "��ר", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "��ר������", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְʱ��", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "4������", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("����ְ��ʱ��		", "5-9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10������", true, Gravity.LEFT));
        return rows;
    }

    private static HeadRows getYearHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "����1��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "1��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "2��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ְ", "3��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "4��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "5��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "6��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "7��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "8��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10�꼰����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "����1��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "1��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "2��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ְ", "3��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "4��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "5��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��", "6��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "7��", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "8��", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "9��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "10�꼰����", true, Gravity.CENTER));

        return rows;
    }

    private static HeadRows getEduHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "��ʿ�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "˶ʿ�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "��ѧ", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("ȫ����", "��ר", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("���ѧ��", "��ר", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "������", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "��ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("ȫ��", "��ѧ", false, Gravity.RIGHT));
        // rows.addHeadItem(new HeadItem("�����ѧ��", "��ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("רҵ���", "��ѧ", false, Gravity.LEFT));
        // rows.addHeadItem(new HeadItem("", "����ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "����", true, Gravity.CENTER));

        rows.addHeadItem(new HeadItem("", "��ʿ�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "˶ʿ�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "���뵳У�о���", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("��ְ��", "ʡί��У�о���", false, Gravity.RIGHT));
        rows.addHeadItem(new HeadItem("��ѧ��", "��ѧ", false, Gravity.LEFT));
        rows.addHeadItem(new HeadItem("", "���뵳У��ѧ", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "ʡί��У��ѧ", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "��ר", true, Gravity.CENTER));

        // rows.addHeadItem(new HeadItem("", "������", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "��ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("��", "��ѧ", false, Gravity.RIGHT));
        // rows.addHeadItem(new HeadItem("ְ���ѧ��", "��ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("רҵ���", "��ѧ", false, Gravity.LEFT));
        // rows.addHeadItem(new HeadItem("", "����ѧ", false, Gravity.CENTER));
        // rows.addHeadItem(new HeadItem("", "����", false, Gravity.CENTER));

        return rows;
    }

    private static HeadRows getAgeHeadRows() {
        HeadRows rows = new HeadRows( );
        rows.setHeadText("����");
        rows.addHeadItem(new HeadItem("", "����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "ƽ������", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "35�꼰����", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "36��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "37��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "38��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "39��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "40��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "41��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "42��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "43��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "44��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "45��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "46��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "47��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "48��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "49��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "50��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "51��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "52��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "53��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "54��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "55��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "56��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "57��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "58��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "59��", false, Gravity.CENTER));
        rows.addHeadItem(new HeadItem("", "60��", true, Gravity.CENTER));

        return rows;
    }



    private static void addEduPieView(String title, int rIndex,
                                      HoriScViewTable table, List <StatItem> list) {
        // ������
        int mCt = table.getRecordColumn(rIndex, 0).getValueOfInt( );
        // ͳ��ֵ
        int vCt = 0;
        StatItem s11 = new StatItem( );
        s11.setTitle(title + "ȫ�������ѧ��ͳ��");
        vCt = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("�о���", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 6).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("��ר", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 7).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("��ר", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 8).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("����", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 9).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("��������", vCt);
        }
        list.add(s11);

        StatItem s12 = new StatItem( );
        s12.setTitle(title + "ȫ�����о���ѧλͳ��");
        int yjs = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 2).getValueOfInt( );
        yjs = yjs - vCt;
        if (vCt > 0) {
            s12.addItem("��ʿѧλ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 3).getValueOfInt( );
        yjs = yjs - vCt;
        if (vCt > 0) {
            s12.addItem("˶ʿѧλ", vCt);
        }
        if (yjs > 0) {
            s12.addItem("��ѧλ", yjs);
        }
        list.add(s12);

        StatItem s13 = new StatItem( );
        s13.setTitle(title + "ȫ���ƴ�ѧѧλͳ��");
        int dx = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 5).getValueOfInt( );
        dx = dx - vCt;
        if (vCt > 0) {
            s13.addItem("ѧʿѧλ", vCt);
        }
        if (dx > 0) {
            s13.addItem("��ѧλ", dx);
        }
        list.add(s13);

        StatItem s14 = new StatItem( );
        s14.setTitle(title + "ȫ����ѧ��רҵͳ��");
        int cCt = table.getRecordColumn(rIndex, 0).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 10).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 11).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("����ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 12).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 13).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("����ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 14).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 15).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ʷѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 16).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 17).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 18).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("ũѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 19).getValueOfInt( );
        cCt = cCt - vCt;
        if (vCt > 0) {
            s14.addItem("ҽѧ", vCt);
        }
        if (cCt > 0) {
            s14.addItem("����רҵ", cCt);
        }
        list.add(s14);

        int zCt = 0;
        StatItem s15 = new StatItem( );
        s15.setTitle(title + "��ְ���ѧ��ͳ��");
        vCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("�о���", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 23).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("��ѧ����", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 25).getValueOfInt( );
        zCt += vCt;
        if (vCt > 0) {
            s15.addItem("��ѧר��", vCt);
        }
        list.add(s15);

        StatItem s16 = new StatItem( );
        s16.setTitle(title + "��ְ�о���ѧλͳ��");
        int zyCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 21).getValueOfInt( );
        zyCt = zyCt - vCt;
        if (vCt > 0) {
            s16.addItem("��ʿѧλ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 22).getValueOfInt( );
        zyCt = zyCt - vCt;
        if (vCt > 0) {
            s16.addItem("˶ʿѧλ", vCt);
        }
        if (zyCt > 0) {
            s16.addItem("��ѧλ", zyCt);
        }
        list.add(s16);

        StatItem s17 = new StatItem( );
        s17.setTitle(title + "��ְ��ѧѧλͳ��");
        int zdx = table.getRecordColumn(rIndex, 23).getValueOfInt( );
        vCt = table.getRecordColumn(rIndex, 24).getValueOfInt( );
        zdx = zdx - vCt;
        if (vCt > 0) {
            s17.addItem("ѧʿѧλ", vCt);
        }
        if (zdx > 0) {
            s17.addItem("��ѧλ", zdx);
        }
        list.add(s17);

        StatItem s18 = new StatItem( );
        s18.setTitle(title + "��ְѧ��רҵͳ��");
        int zeCt = zCt;
        vCt = table.getRecordColumn(rIndex, 26).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 27).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("����ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 28).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 29).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("����ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 30).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 31).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ʷѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 32).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 33).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("��ѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 34).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("ũѧ", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 35).getValueOfInt( );
        zeCt = zeCt - vCt;
        if (vCt > 0) {
            s18.addItem("ҽѧ", vCt);
        }
        if (zeCt > 0) {
            s18.addItem("����רҵ", zeCt);
        }
        list.add(s18);

    }

    private static void addPieView(String title, int rIndex,
                                   HoriScViewTable table, List <StatItem> list) {

        // ͳ��ֵ
        int vCt = 0;
        StatItem s11 = new StatItem( );
        s11.setTitle(title + "����ְ������");
        vCt = table.getRecordColumn(rIndex, 1).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("����1��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 2).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("1��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 3).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("2��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("3��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 5).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("4��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 6).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("5��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 7).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("6��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 8).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("7��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 9).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("8��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 10).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("9��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 4).getValueOfInt( );
        if (vCt > 0) {
            s11.addItem("10�꼰����", vCt);
        }

        list.add(s11);

        StatItem s12 = new StatItem( );
        s12.setTitle(title + "��Ҫְ������");
        vCt = table.getRecordColumn(rIndex, 12).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("����1��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 13).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("1��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 14).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("2��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 15).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("3��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 16).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("4��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 17).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("5��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 18).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("6��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 19).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("7��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 20).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("8��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 21).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("9��", vCt);
        }
        vCt = table.getRecordColumn(rIndex, 22).getValueOfInt( );
        if (vCt > 0) {
            s12.addItem("10�꼰����", vCt);
        }

        list.add(s12);
    }
}
