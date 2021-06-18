package com.cxht.entity;

/**
 * 学位实体对象
 * Created by HeJiaMing on 2020/8/18 14:26
 * E-Mail Address：1774690@qq.com
 */
public class Degree {

    private int id;
    private int user_id;//用户编号
    private String user_code;//身份证号码
    private String user_name;//姓名
    private String degree;//学位
    private String type;//学位类型
    private String school;//毕业院校
    private String department;//所属部门
    private String major;//专业
    private String degree_time;//授予时间
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree_time() {
        return degree_time;
    }

    public void setDegree_time(String degree_time) {
        this.degree_time = degree_time;
    }

}
