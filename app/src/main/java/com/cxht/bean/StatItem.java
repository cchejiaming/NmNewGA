package com.cxht.bean;

import java.util.ArrayList;
import java.util.List;

public class StatItem {

	private List<StatResultBean> data;
	private String title;

	public StatItem() {
		this.title = null;
		this.data = new ArrayList<StatResultBean>();
	}

	public void addItem(String name, int count) {
		StatResultBean sb = new StatResultBean();
		sb.setCount(count);
		sb.setName(name);
		data.add(sb);
	}

	public StatItem(List<StatResultBean> d, String t) {
		this.data = d;
		this.title = t;
	}

	public List<StatResultBean> getData() {
		return data;
	}

	public void setData(List<StatResultBean> data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
