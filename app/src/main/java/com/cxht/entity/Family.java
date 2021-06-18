package com.cxht.entity;

/**
 * 
 * @author hejiaming 家庭成员对象实体类
 */
public class Family {

	private int family_id;
	private int user_id;//用户编号
	private String title;//称谓
	private String family_name;//成员姓名
	private String birth_date;//生日
	private String politics_status;//状态
	private String job_desc;//职务及工作描述

	public int getFamily_id() {
		return family_id;
	}

	public void setFamily_id(int family_id) {
		this.family_id = family_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getPolitics_status() {
		return politics_status;
	}

	public void setPolitics_status(String politics_status) {
		this.politics_status = politics_status;
	}

	public String getJob_desc() {
		return job_desc;
	}

	public void setJob_desc(String job_desc) {
		this.job_desc = job_desc;
	}

}
