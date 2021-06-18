package com.cxht.unit;

import com.cxht.bean.GroupBean;
import com.cxht.bean.GroupInfo;
import com.cxht.bean.GroupNumberItem;
import com.cxht.dao.TableDao;
import com.cxht.entity.GroupNumber;
import java.util.ArrayList;
import java.util.List;

/**
 * ְ����Ϣ������
 * Created by HeJiaMing on 2020/11/16 11:17
 * E-Mail Address��1774690@qq.com
 */
public class GroupNumberUtils {
    /**
     * ���ϻ�����Ϣ��ְ����Ϣ����
     * @param gbs ������Ϣ��
     * @param gns ְ��������Ϣ��
     * @return
     */
    public static List<GroupInfo> execute(List <GroupBean> gbs, List<GroupNumber> gns){
        List<GroupInfo> data=null;
        if(gbs != null && gbs.size()>0){
            data = new ArrayList <>();
            for(GroupBean gb:gbs){
                GroupInfo info = new GroupInfo();
                info.setId(gb.getId());
                info.setParentId(gb.getParentId());
                info.setGroup_code(gb.getGroup_code());
                GroupNumber gn = findGroupNumber(gns,gb);
                info.setGroupNumber(gn);
                List<GroupNumberItem> its =createGroupNumberItems(gn);//����ְ����Ϣ��ϴ���
                info.setItems(its);
                String gnTxt = getHintGroupNumber(its); //��ȡ�������ó���ȱ����ʾ�ַ���
                String title = gb.getName();
                if(gnTxt.length()>0) title+= gnTxt;
                info.setTitle(title);
                data.add(info);
            }


        }
        return data;
    }

    /**
     * ְ��������ʾ��Ϣ
     * ��ȡ�������ó���ȱ����ʾ�ַ���
     * @param its ְ��������
     * @return
     */
    private static String getHintGroupNumber(List<GroupNumberItem> its){
        String result = "";
        if (its!=null && its.size()>0){
            int qp = 0;
            int cp = 0;
           for(GroupNumberItem it:its){
               if (it.getNumberOfPeople()>it.getActualNumberOfPeople()){
                   //ȱ��-������������ʵ������
                   qp+=(it.getNumberOfPeople()-it.getActualNumberOfPeople());
               }
               if(it.getNumberOfPeople()<it.getActualNumberOfPeople()){
                   //���� -��������С��ʵ������
                   cp+=(it.getActualNumberOfPeople()-it.getNumberOfPeople());
               }

           }
           if(qp>0) result+=" <font color=\"#ff0000\">ȱ</font> "+qp;
           if(cp>0) result+=" <font color=\"#0000FF\">��</font> "+cp;
            if (!result.equals("")) result = "<pre" +
                    "style=\"background-color: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: '����'; font-size: 9pt;\"><small><span" +
                    "style=\"color: rgb(0, 128, 0);\">("+result+")</span></small></pre>";
        }
        return  result;
    }

    /**
     * ����ְ����Ϣ��ʵ��������Ϣ���
     * ��Ҫ��̬ͳ�������������Լ���������������ѯsql��䣬Ϊ�պ󷴲���Ϣ��׼��
     * @param gn ְ����Ϣ��������ְ����Ϣ��������ʵ��������
     * @return
     */
    private static List <GroupNumberItem> createGroupNumberItems(GroupNumber gn){
        List <GroupNumberItem> list = null;
        if(gn!=null){
            list = new ArrayList <>();
            GroupNumberItem ztld = createItem("zt_ld",gn);
            list.add(ztld);
            GroupNumberItem ftld = createItem("ft_ld",gn);
            list.add(ftld);
            GroupNumberItem ztfld = createItem("zt_fld",gn);
            list.add(ztfld);
            GroupNumberItem ftfld = createItem("ft_fld",gn);
            list.add(ftfld);
            GroupNumberItem zcld = createItem("zc_ld",gn);
            list.add(zcld);
            GroupNumberItem fcld = createItem("fc_ld",gn);
            list.add(fcld);
            GroupNumberItem zcfld = createItem("zc_fld",gn);
            list.add(zcfld);
            GroupNumberItem fcfld = createItem("fc_fld",gn);
            list.add(fcfld);

        }
        return list;
    }

    /**
     * ְ�����
     * @param colName ����
     * @param gn  ����ְ����Ϣ����
     * @return
     */
    private static GroupNumberItem createItem(String colName,GroupNumber gn){
        String name = "";
        String shortName = "";
        int number =0;
        int aNumber =0;
        String andWhere = null;
        switch (colName){
            case "zt_ld":
                name = "�����쵼";
                shortName ="����ʵ";
                number = gn.getZt_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ�쵼' and depth in ('���ּ���ְ','һ��Ѳ��Ա','һ������רԱ','������һ���ܼ�')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "ft_ld":
                name = "�����쵼";
                shortName ="����ʵ";
                number = gn.getFt_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ�쵼' and depth in ('���ּ���ְ','����Ѳ��Ա','��������רԱ','�����������ܼ�')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zt_fld":
                name = "�������쵼";
                shortName ="������";
                number = gn.getZt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ���쵼' and depth in ('���ּ���ְ','һ��Ѳ��Ա','һ������רԱ','������һ���ܼ�')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "ft_fld":
                name = "�������쵼";
                shortName ="������";
                number = gn.getFt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ���쵼' and depth in ('���ּ���ְ','����Ѳ��Ա','��������רԱ','�����������ܼ�')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zc_ld":
                name = "�����쵼";
                shortName ="����ʵ";
                number = gn.getZc_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ�쵼' and depth in ('�ش�����ְ','һ������Ա','��������Ա','һ���߼�����','�����߼�����','������һ������','��������������')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "fc_ld":
                name = "�����쵼";
                shortName ="����ʵ";
                number = gn.getFc_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ�쵼' and depth in ('�ش�����ְ','��������Ա','�ļ�����Ա','�����߼�����','�ļ��߼�����','��������������','�������ļ�����')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zc_fld":
                name = "�������쵼";
                shortName ="����ʵ";
                number = gn.getZt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ���쵼' and depth in ('�ش�����ְ','һ������Ա','��������Ա','һ���߼�����','�����߼�����','������һ������','��������������')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "fc_fld":
                name = "�������쵼";
                shortName ="������";
                number = gn.getFc_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '��ְ���쵼' and depth in ('�ش�����ְ','��������Ա','�ļ�����Ա','�����߼�����','�ļ��߼�����','��������������','�������ļ�����')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
        }

        return  getItem(colName,name,shortName,number,aNumber,andWhere);
    }

    /**
     * �ж�ְ���������Ƿ������
     * �������0������˻���δ����ְ����Ϣ
     * ��֮��������ְ����Ϣ
     * @param gn ����ְ����Ϣ����
     * @return
     */
    private static boolean isCountZero(GroupNumber gn){
        if (gn!=null){
            int count = gn.getZt_ld()+gn.getFt_ld()+gn.getZt_fld()+gn.getFt_fld()
                    +gn.getZc_ld()+gn.getFc_ld()+gn.getZc_fld()+gn.getFc_fld();
            if(count>0) return false;
        }
        return true;
    }

    /**
     * ����ְ��������Ŀ
     * @param colName ����
     * @param name ְ������
     * @param shortName ְ�����
     * @param number ��������
     * @param aNumber ʵ����Ա
     * @param andWhere sql��������
     * @return
     */
    private static GroupNumberItem getItem(String colName,String name,String shortName,int number,int aNumber,String andWhere){
        GroupNumberItem item = new GroupNumberItem();
        item.setTableName("t_group_number");
        item.setColumnName(colName);
        item.setName(name);
        item.setSimpleName(shortName);
        item.setSimpleName(name);
        item.setNumberOfPeople(number);
        item.setActualNumberOfPeople(aNumber);
        item.setAndWhere(andWhere);
        return item;
    }

    /**
     * ͨ��������Ϣ����ְ��������Ϣ
     * @param gns ְ��������Ϣ��
     * @param gb ������Ϣ
     * @return
     */
   private static GroupNumber findGroupNumber(List<GroupNumber> gns, GroupBean gb){
       if(gb!=null && gns!=null){
         for(GroupNumber gn:gns){
             if(gn.getGroup_code()!=null && gb.getGroup_code()!=null){
                 if(gn.getGroup_code().equals(gb.getGroup_code())){
                     return  gn;
                 }
             }

         }
       }
       return null;
   }
}
