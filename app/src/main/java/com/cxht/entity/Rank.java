package com.cxht.entity;

/**
 * 警衔信息实体类
 * Created by HeJiaMing on 2020/11/13 15:32
 * E-Mail Address：1774690@qq.com
 */
public class Rank {
    private int id;//索引id
    private String user_code;//身份证号码
    private String user_name;//姓名
    private String org_code;//机构编码
    private String rank_name;//警衔名称

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

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }
}
