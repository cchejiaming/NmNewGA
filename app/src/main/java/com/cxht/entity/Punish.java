package com.cxht.entity;

/**
 * 惩戒信息实体类
 * Created by HeJiaMing on 2020/11/13 09:19
 * E-Mail Address：1774690@qq.com
 */
public class Punish {

    private  int id; //索引ID
    private String user_name;//姓名
    private String user_code;//身份证号
    private String org_code;//机构编码
    private String punish_name;//惩戒名称
    private String punish_type;//惩戒类别
    private String punish_time;//批准时间
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getPunish_name() {
        return punish_name;
    }

    public void setPunish_name(String punish_name) {
        this.punish_name = punish_name;
    }

    public String getPunish_type() {
        return punish_type;
    }

    public void setPunish_type(String punish_type) {
        this.punish_type = punish_type;
    }

    public String getPunish_time() {
        return punish_time;
    }

    public void setPunish_time(String punish_time) {
        this.punish_time = punish_time;
    }


}
