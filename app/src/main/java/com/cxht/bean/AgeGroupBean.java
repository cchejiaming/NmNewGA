package com.cxht.bean;

/**
 * 时间段对象
 */
public class AgeGroupBean {
   private String startYear; //开始年份
   private String overYear; // 结束年份
   
   private String title; // 年龄段标签
public String getStartYear() {
	return startYear;
}
public void setStartYear(String startYear) {
	this.startYear = startYear;
}
public String getOverYear() {
	return overYear;
}
public void setOverYear(String overYear) {
	this.overYear = overYear;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
   
}
