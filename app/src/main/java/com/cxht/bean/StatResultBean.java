package com.cxht.bean;

/**
 * ͳ�ƽ����
 * 
 * @author Administrator
 * 
 */
public class StatResultBean {
	private int flag = 1; // 0:���� 1��item_text 2:item text1
	private String name;// ͳ������
	private int count;// ��������
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
