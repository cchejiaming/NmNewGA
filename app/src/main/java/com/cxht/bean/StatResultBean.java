package com.cxht.bean;

/**
 * 统计结果类
 * 
 * @author Administrator
 * 
 */
public class StatResultBean {
	private int flag = 1; // 0:标题 1：item_text 2:item text1
	private String name;// 统计名称
	private int count;// 汇总数量
	private String andWhere;

    public StatResultBean(){}
	public StatResultBean(String name, int count) {
		this.setName(name);
		this.setCount(count);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getAndWhere() {
		return andWhere;
	}
	public void setAndWhere(String andWhere) {
		this.andWhere = andWhere;
	}

}
