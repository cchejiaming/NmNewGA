package com.cxht.entity;

/**
 * ��ǹ֤��Ϣʵ����
 * Created by HeJiaMing on 2020/11/30 17:22
 * E-Mail Address��1774690@qq.com
 */
public class Gun {
    private int id;//����id
    private String user_code; //���֤��
    private String user_name;//����
    private String org_code;//��������
    private String licence;//֤������
    private String lssuing;//��֤����
    private String lssue_date;//��֤����
    private String renewal_date;//��֤����

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
