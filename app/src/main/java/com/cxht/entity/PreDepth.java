package com.cxht.entity;

/**
 * 职务级实体类
 * 
 * @author hejiaming
 * 
 */
public class PreDepth {
	private int id; // id
	private String name; // 级别名称
	private String startTime; // 开始时间
	private String attr; // 职级属性
    private int userId;//用户编码
	private String lxTime;//任相当层次职务职级起算时间
	private String ratifyTime;//职级批准时间
	private String groupCode;//组织机构编码

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
