package com.cxht.entity;

/**
 * ��ѵ��Ϣʵ����
 * Created by HeJiaMing on 2020/12/1 09:23
 * E-Mail Address��1774690@qq.com
 */
public class Train {
    private int id;//����id
    private String user_name;//����
    private String user_code;//���֤��
    private String org_code;//��������
    private String class_name;//��ѵ������
    private String desc;//��ѵ������
    private String start_time;//��ʼʱ��
    private String end_time;//����ʱ��


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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }



}
