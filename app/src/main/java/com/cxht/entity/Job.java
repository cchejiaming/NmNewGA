package com.cxht.entity;

public class Job {
	private int id; // id
	private int user_id; // ��Ա���
	private String user_name; // ��Ա����
	private int group_id; // ��֯���
	private String group_name; // ��֯����
	private String group_full_name;// ������֯ȫ��
	private String start_time; // ��ְʱ��
	private String job_name;// ְ���ǩ
	private String job_attr; // ְ������
	private String depth; // ְ�����μ���
	private String position_attr;// ��������
	private String lx_time; // �����θ�ְʱ��

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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

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

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_attr() {
		return job_attr;
	}

	public void setJob_attr(String job_attr) {
		this.job_attr = job_attr;
	}

	public String getPosition_attr() {
		return position_attr;
	}

	public void setPosition_attr(String position_attr) {
		this.position_attr = position_attr;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getGroup_full_name() {
		return group_full_name;
	}

	public void setGroup_full_name(String group_full_name) {
		this.group_full_name = group_full_name;
	}

	public String getLx_time() {
		return lx_time;
	}

	public void setLx_time(String lx_time) {
		this.lx_time = lx_time;
	}
}
