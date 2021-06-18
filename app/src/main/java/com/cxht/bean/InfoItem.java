package com.cxht.bean;

/**
 * 列表item类
 * 
 * @author hejiaming
 * 
 */
public class InfoItem {

	private int flag = 1; // 0:标题 1：item_text 2:item text1
	private String title; // 标题
	private String desc; // 内容
	private String columnTitle;// 列标题名称


	private String where; //sqlwhere 语句

	private boolean more = true;

	public InfoItem(String title, String desc) {
		this.setTitle(title);
		this.setDesc(desc);
	}

	public InfoItem(String title, String desc, boolean newLine) {

		this.setTitle(title);
		if (newLine) {
			this.setDesc("\n" + desc);
		} else {
			this.setDesc(desc);
		}

	}
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}
}
