package com.cxht.entity;
/**
 * 
 * @author hejiaming
 * 	��������ʵ����
 */
public class Resume {
   private int resume_id;
   private int user_id;//�û����
   private String start_time;//��ʼʱ��
   private String over_time;//����ʱ��
   private String job_desc;//������ְ������
   private String sort_time;//����ʱ��
   
public int getResume_id() {
	return resume_id;
}
public void setResume_id(int resume_id) {
	this.resume_id = resume_id;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public String getStart_time() {
	return start_time;
}
public void setStart_time(String start_time) {
	this.start_time = start_time;
}
public String getOver_time() {
	return over_time;
}
public void setOver_time(String over_time) {
	this.over_time = over_time;
}
public String getJob_desc() {
	return job_desc;
}
public void setJob_desc(String job_desc) {
	this.job_desc = job_desc;
}
public String getSort_time() {
	return sort_time;
}
public void setSort_time(String sort_time) {
	this.sort_time = sort_time;
}
}
