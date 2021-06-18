package com.cxht.entity;

import com.cxht.unit.StringUtil;

/**
 * 
 * @author hejiaming ���Ͷ���ʵ����
 */
public class Examine {

	private int examine_id;
	private int user_id;//�û����
	private String examine_name;//��������
	private String examine_time;//����ʱ��

	public int getExamine_id() {
		return examine_id;
	}

	public void setExamine_id(int examine_id) {
		this.examine_id = examine_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getExamine_name() {
		return examine_name;
	}

	public void setExamine_name(String examine_name) {
		this.examine_name = examine_name;
	}

	public String getExamine_time() {
		return StringUtil.getShortDate(examine_time, "��");
	}

	public void setExamine_time(String examine_time) {
		this.examine_time = examine_time;
	}
}
