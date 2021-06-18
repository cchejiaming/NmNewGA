package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class HeadRows implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String headText;
	private ArrayList<HeadItem> rows;

	public HeadRows() {
		this.rows = new ArrayList<HeadItem>();
	}

	public String getHeadText(int index) {
		String ret = "";
		if (rows != null) {
			ret = rows.get(index).getTitle();
		}
		return ret;
	}

	public String getHeadText() {
		return headText;
	}

	public void setHeadText(String headText) {
		this.headText = headText;
	}

	public void addHeadItem(HeadItem item) {
		rows.add(item);
	}

	public ArrayList<HeadItem> getRows() {
		return rows;
	}

	public void setRows(ArrayList<HeadItem> rows) {
		this.rows = rows;
	}
}
