package com.cxht.entity;

/**
 * 持枪证信息实体类
 * Created by HeJiaMing on 2020/11/30 17:22
 * E-Mail Address：1774690@qq.com
 */
public class Gun {
    private int id;//索引id
    private String user_code; //身份证号
    private String user_name;//姓名
    private String org_code;//机构编码
    private String licence;//证件号码
    private String lssuing;//发证机关
    private String lssue_date;//发证日期
    private String renewal_date;//换证日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLssuing() {
        return lssuing;
    }

    public void setLssuing(String lssuing) {
        this.lssuing = lssuing;
    }

    public String getLssue_date() {
        return lssue_date;
    }

    public void setLssue_date(String lssue_date) {
        this.lssue_date = lssue_date;
    }

    public String getRenewal_date() {
        return renewal_date;
    }

    public void setRenewal_date(String renewal_date) {
        this.renewal_date = renewal_date;
    }



}
