package com.cxht.entity;

/**
 * ְ��ʵ����
 * 
 * @author hejiaming
 * 
 */
public class PreDepth {
	private int id; // id
	private String name; // ��������
	private String startTime; // ��ʼʱ��
	private String attr; // ְ������
    private int userId;//�û�����
	private String lxTime;//���൱���ְ��ְ������ʱ��
	private String ratifyTime;//ְ����׼ʱ��
	private String groupCode;//��֯��������

	public String getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(String ratifyTime) {
		this.ratifyTime = ratifyTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getLxTime() {
		return lxTime;
	}

	public void setLxTime(String lxTime) {
		this.lxTime = lxTime;
	}


}
