package com.cxht.bean;

/**
 * ͳ�Ʒ���
 * 
 * @author hejiaming
 * 
 */
public class StatColumnBean {

	private int id;
	private String title;

	public StatColumnBean(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
