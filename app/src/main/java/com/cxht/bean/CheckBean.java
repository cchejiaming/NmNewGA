package com.cxht.bean;

public class CheckBean {
	private String name; //显示名称
	private String value;//字段数值
	private boolean checked = false;

	public CheckBean(String n, boolean isCheck) {
		this.name = n;
		this.checked = isCheck;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
