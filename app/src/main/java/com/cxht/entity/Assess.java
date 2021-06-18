package com.cxht.entity;
/**
 * 
 * @author hejiaming
 * 	考核信息对象实体类
 */
public class Assess {
	
   private int assess_id;
   private int user_id;//用户编号
   private String year;//考核年份
   private String result;//考核结果
   

public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getResult() {
	return result;
}
public void setResult(String result) {
	this.result = result;
}
public int getAssess_id() {
	return assess_id;
}
public void setAssess_id(int assess_id) {
	this.assess_id = assess_id;
}
}