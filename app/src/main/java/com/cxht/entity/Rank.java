package com.cxht.entity;

/**
 * ������Ϣʵ����
 * Created by HeJiaMing on 2020/11/13 15:32
 * E-Mail Address��1774690@qq.com
 */
public class Rank {
    private int id;//����id
    private String user_code;//���֤����
    private String user_name;//����
    private String org_code;//��������
    private String rank_name;//��������

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
