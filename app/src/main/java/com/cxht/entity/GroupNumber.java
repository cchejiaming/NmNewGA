package com.cxht.entity;

import java.io.Serializable;

/**
 * 职数信息实体类
 * Created by HeJiaMing on 2020/11/6 14:04
 * E-Mail Address：1774690@qq.com
 */
public class GroupNumber implements Serializable {

   private static final long serialVersionUID = 1L;
   private int group_id; //机构ID
   private String group_name;//机构名称
   private String group_code;//机构编码
   private int zt_ld;//正厅领导职数
   private int ft_ld;//副厅领导职数
   private int zt_fld;//正厅非领导职数
   private int ft_fld;//副厅非领导职数
   private int zc_ld;//正处领导职数
   private int fc_ld;//副处领导职数
   private int zc_fld;//正处非领导职数
   private int fc_fld;//副处非领导职数

   public int getGroup_id() {
      return group_id;
   }

   public void setGroup_id(int group_id) {
      this.group_id = group_id;
   }

   public String getGroup_name() {
      return group_name;
   }

   public void setGroup_name(String group_name) {
      this.group_name = group_name;
   }

   public String getGroup_code() {
      return group_code;
   }

   public void setGroup_code(String group_code) {
      this.group_code = group_code;
   }

   public int getZt_ld() {
      return zt_ld;
   }

   public void setZt_ld(int zt_ld) {
      this.zt_ld = zt_ld;
   }

   public int getFt_ld() {
      return ft_ld;
   }

   public void setFt_ld(int ft_ld) {
      this.ft_ld = ft_ld;
   }

   public int getZt_fld() {
      return zt_fld;
   }

   public void setZt_fld(int zt_fld) {
      this.zt_fld = zt_fld;
   }

   public int getFt_fld() {
      return ft_fld;
   }

   public void setFt_fld(int ft_fld) {
      this.ft_fld = ft_fld;
   }

   public int getZc_ld() {
      return zc_ld;
   }

   public void setZc_ld(int zc_ld) {
      this.zc_ld = zc_ld;
   }

   public int getFc_ld() {
      return fc_ld;
   }

   public void setFc_ld(int fc_ld) {
      this.fc_ld = fc_ld;
   }

   public int getZc_fld() {
      return zc_fld;
   }

   public void setZc_fld(int zc_fld) {
      this.zc_fld = zc_fld;
   }

   public int getFc_fld() {
      return fc_fld;
   }

   public void setFc_fld(int fc_fld) {
      this.fc_fld = fc_fld;
   }
}
