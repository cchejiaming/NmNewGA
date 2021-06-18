package com.cxht.entity;

/**
 * 执法资格实体类
 * Created by HeJiaMing on 2020/11/30 16:32
 * E-Mail Address：1774690@qq.com
 */
public class Marshals {
    private int id;//索引id
    private String user_code;//身份证号
    private String user_name;//姓名
    private String org_code;//机构编码
    private String level;//执法资格等级
    private String start_time;//执法资格考试通过时间

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

}
