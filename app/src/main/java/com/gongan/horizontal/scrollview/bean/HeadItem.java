package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;
import java.util.List;

/***
 * 横滚列表头部实体类
 * 
 * @author hejiaming
 * 
 */
public class HeadItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String desc; // 标题描述
	private String title; // 标题
	private Boolean descIsSpe; // 是否隐藏描述字段分割符
	private int descGra; // 描述字段对齐方式
    


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
