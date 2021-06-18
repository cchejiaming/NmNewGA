package com.cxht.unit;

import com.cxht.bean.GroupBean;
import com.cxht.bean.GroupInfo;
import com.cxht.bean.GroupNumberItem;
import com.cxht.dao.TableDao;
import com.cxht.entity.GroupNumber;
import java.util.ArrayList;
import java.util.List;

/**
 * 职数信息整理类
 * Created by HeJiaMing on 2020/11/16 11:17
 * E-Mail Address：1774690@qq.com
 */
public class GroupNumberUtils {
    /**
     * 整合机构信息和职数信息方法
     * @param gbs 机构信息集
     * @param gns 职数配置信息集
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
                List<GroupNumberItem> its =createGroupNumberItems(gn);//机构职数信息项集合创建
                info.setItems(its);
                String gnTxt = getHintGroupNumber(its); //获取机构配置超配缺配显示字符串
                String title = gb.getName();
                if(gnTxt.length()>0) title+= gnTxt;
                info.setTitle(title);
                data.add(info);
            }


        }
        return data;
    }

    /**
     * 职数配置提示信息
     * 获取机构配置超配缺配显示字符串
     * @param its 职数配置项
     * @return
     */
    private static String getHintGroupNumber(List<GroupNumberItem> its){
        String result = "";
        if (its!=null && its.size()>0){
            int qp = 0;
            int cp = 0;
           for(GroupNumberItem it:its){
               if (it.getNumberOfPeople()>it.getActualNumberOfPeople()){
                   //缺配-配置人数大于实际人数
                   qp+=(it.getNumberOfPeople()-it.getActualNumberOfPeople());
               }
               if(it.getNumberOfPeople()<it.getActualNumberOfPeople()){
                   //超配 -配置人数小于实际人数
                   cp+=(it.getActualNumberOfPeople()-it.getNumberOfPeople());
               }

           }
           if(qp>0) result+=" <font color=\"#ff0000\">缺</font> "+qp;
           if(cp>0) result+=" <font color=\"#0000FF\">超</font> "+cp;
            if (!result.equals("")) result = "<pre" +
                    "style=\"background-color: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: '宋体'; font-size: 9pt;\"><small><span" +
                    "style=\"color: rgb(0, 128, 0);\">("+result+")</span></small></pre>";
        }
        return  result;
    }

    /**
     * 机构职数信息与实际人数信息项创建
     * 需要动态统计是有人数，以及整理是有人数查询sql语句，为日后反查信息做准备
     * @param gn 职数信息（单机构职数信息，不包含实有人数）
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
     * 职数项创建
     * @param colName 列名
     * @param gn  机构职数信息对象
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
                name = "正厅领导";
                shortName ="正厅实";
                number = gn.getZt_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职领导' and depth in ('厅局级正职','一级巡视员','一级警务专员','警务技术一级总监')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "ft_ld":
                name = "副厅领导";
                shortName ="副厅实";
                number = gn.getFt_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职领导' and depth in ('厅局级副职','二级巡视员','二级警务专员','警务技术二级总监')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zt_fld":
                name = "正厅非领导";
                shortName ="正厅虚";
                number = gn.getZt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职非领导' and depth in ('厅局级正职','一级巡视员','一级警务专员','警务技术一级总监')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "ft_fld":
                name = "副厅非领导";
                shortName ="副厅虚";
                number = gn.getFt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职非领导' and depth in ('厅局级副职','二级巡视员','二级警务专员','警务技术二级总监')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zc_ld":
                name = "正处领导";
                shortName ="正处实";
                number = gn.getZc_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职领导' and depth in ('县处级正职','一级调研员','二级调研员','一级高级警长','二级高级警长','警务技术一级主任','警务技术二级主任')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "fc_ld":
                name = "副处领导";
                shortName ="副处实";
                number = gn.getFc_ld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职领导' and depth in ('县处级副职','三级调研员','四级调研员','三级高级警长','四级高级警长','警务技术三级主任','警务技术四级主任')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "zc_fld":
                name = "正处非领导";
                shortName ="正处实";
                number = gn.getZt_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职非领导' and depth in ('县处级正职','一级调研员','二级调研员','一级高级警长','二级高级警长','警务技术一级主任','警务技术二级主任')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
            case "fc_fld":
                name = "副处非领导";
                shortName ="副处虚";
                number = gn.getFc_fld();
                if(!isCountZero(gn)){
                    andWhere = " and user_code in (select user_code from t_job where group_code ='"+gn.getGroup_code()+"') " +
                            "and user_type = '在职非领导' and depth in ('县处级副职','三级调研员','四级调研员','三级高级警长','四级高级警长','警务技术三级主任','警务技术四级主任')";
                    String sql = "select * from t_user where 1=1 " +andWhere;
                    aNumber = TableDao.getTableCount(sql);
                }
                break;
        }

        return  getItem(colName,name,shortName,number,aNumber,andWhere);
    }

    /**
     * 判断职数总人数是否大于零
     * 如果等于0则表明此机构未配置职数信息
     * 反之机构配置职数信息
     * @param gn 机构职数信息对象
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
     * 生成职数配置项目
     * @param colName 列名
     * @param name 职数名称
     * @param shortName 职数简称
     * @param number 配置人数
     * @param aNumber 实际人员
     * @param andWhere sql过滤条件
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
     * 通过机构信息查找职数配置信息
     * @param gns 职数配置信息集
     * @param gb 机构信息
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
