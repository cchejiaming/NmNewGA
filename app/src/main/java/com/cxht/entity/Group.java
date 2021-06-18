package com.cxht.entity;

public class Group {
private int group_id; //机构ID
private String group_name;//机构名称
private String group_code;//机构代码
private int parent_id;//父ID
public int getGroup_id() {
	return group_id;
}
public void setGroup_id(int group_id) {
	this.group_id = group_id;
}
public String getGroup_name() {
	return group_name;
}
public void setGroup_name(String group_name) {
	this.group_name = group_name;
}
public String getGroup_code() {
	return group_code;
}
public void setGroup_code(String group_code) {
	this.group_code = group_code;
}
public int getParent_id() {
	return parent_id;
}
public void setParent_id(int parent_id) {
	this.parent_id = parent_id;
}
}
