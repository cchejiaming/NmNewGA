package com.cxht.bean;
import java.io.Serializable;

import android.os.Parcel;

/**
 *人员查询条件项实体类
 */
public class SearchItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String value; // sql过滤值
	private String display;// 显示名称

	public SearchItem(String v, String d) {
		this.value = v;
		this.display = d;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}


	public SearchItem(Parcel in) {
		value = in.readString();
		display = in.readString();
	}

	public SearchItem() {
		// TODO Auto-generated constructor stub
	}
}
