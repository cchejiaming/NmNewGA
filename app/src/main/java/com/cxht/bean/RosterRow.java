package com.cxht.bean;

import com.cxht.entity.Degree;
import com.cxht.entity.Job;
import com.cxht.entity.Marshals;
import com.cxht.entity.PreDepth;
import com.cxht.entity.Qualification;
import com.cxht.entity.User;

import java.util.List;

/**
 * �����ж���ʵ��
 */
public class RosterRow {
	private User user; //������Ϣ
	private List <Degree> degrees; //ѧλ��Ϣ
	private List<Qualification> qualifications;// ְ����Ϣ
	private int rowHight = 85;// �еĸ߶�
	private Job job; //��Ҫ����ְ����Ϣ
	private PreDepth depth;//���ְ����Ϣ
	private Marshals marshals;//ִ���ʸ�

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
