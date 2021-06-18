package com.cxht.bean;

import com.cxht.entity.Degree;
import com.cxht.entity.Job;
import com.cxht.entity.Marshals;
import com.cxht.entity.PreDepth;
import com.cxht.entity.Qualification;
import com.cxht.entity.User;

import java.util.List;

/**
 * 名册行对象实体
 */
public class RosterRow {
	private User user; //基本信息
	private List <Degree> degrees; //学位信息
	private List<Qualification> qualifications;// 职称信息
	private int rowHight = 85;// 行的高度
	private Job job; //主要工作职务信息
	private PreDepth depth;//最高职级信息
	private Marshals marshals;//执法资格

	public List <Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List <Degree> degrees) {
		this.degrees = degrees;
	}

	public List <Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List <Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRowHight() {
		return rowHight;
	}

	public void setRowHight(int rowHight) {
		this.rowHight = rowHight;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public PreDepth getDepth() {
		return depth;
	}

	public void setDepth(PreDepth depth) {
		this.depth = depth;
	}

	public Marshals getMarshals() {
		return marshals;
	}

	public void setMarshals(Marshals marshals) {
		this.marshals = marshals;
	}
}
