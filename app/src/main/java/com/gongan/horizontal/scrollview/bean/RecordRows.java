package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordRows implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String headText; // 头部文字
	private int headStyle; // 头部样式ID
	private ArrayList<RecordColumn> rows;

	public RecordRows() {
		this.rows = new ArrayList<RecordColumn>();
		this.headStyle = 0;
	}

	public void addRecordRow(RecordColumn col) {
		rows.add(col);
	}

	public ArrayList<RecordColumn> getRows() {
		return rows;
	}

	public void setRows(ArrayList<RecordColumn> rows) {
		this.rows = rows;
	}

	public String getHeadText() {
		return headText;
	}

	public void setHeadText(String headText) {
		this.headText = headText;
	}

	public int getHeadStyle() {
		return headStyle;
	}

	public void setHeadStyle(int headStyle) {
		this.headStyle = headStyle;
	}

}
