package com.cxht.bean;

import java.io.Serializable;
import java.util.List;



public class GroupSerializable implements Serializable  {

	/**
	 * ���ڴ洢��֯���ϵĶ���
	 */
	private static final long serialVersionUID = 1L;
	private List<GroupBean> group;
	public List<GroupBean> getGroup() {
		return group;
	}
	public void setGroup(List<GroupBean> group) {
		this.group = group;
	}

}
