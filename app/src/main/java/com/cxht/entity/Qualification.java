package com.cxht.entity;

/**
 * ְ�ƶ���
 * Created by HeJiaMing on 2020/8/19 13:37
 * E-Mail Address��1774690@qq.com
 */
public class Qualification {

    private  int id; //id;
    private String user_id;//�û�����
    private String user_name;//����
    private String user_code;//���֤��
    private String group_code; //��������
    private String qfc_name;//ְ��
    private String get_time;//��ȡʱ��

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getQfc_name() {
        return qfc_name;
    }

    public void setQfc_name(String qfc_name) {
        this.qfc_name = qfc_name;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }
}
