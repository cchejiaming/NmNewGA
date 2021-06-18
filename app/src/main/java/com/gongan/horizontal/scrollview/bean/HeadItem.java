package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;
import java.util.List;

/***
 * ����б�ͷ��ʵ����
 * 
 * @author hejiaming
 * 
 */
public class HeadItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String desc; // ��������
	private String title; // ����
	private Boolean descIsSpe; // �Ƿ����������ֶηָ��
	private int descGra; // �����ֶζ��뷽ʽ
    


	public HeadItem(String desc, String title, Boolean spe, int gra) {

		this.desc = desc;
		this.title = title;
		this.descIsSpe = spe;
		this.descGra = gra;
	}

	public int getDescGra() {
		return descGra;
	}

	public void setDescGra(int descGra) {
		this.descGra = descGra;
	}

	public Boolean getDescIsSpe() {
		return descIsSpe;
	}

	public void setDescIsSpe(Boolean descIsSpe) {
		this.descIsSpe = descIsSpe;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}
