package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;

import android.view.Gravity;

public class HeadCell implements Serializable {

	/**
	 * ��ʱδ�� �б�ͷ����ʽ
	 */
	private static final long serialVersionUID = 1L;
	private String desc;// ��������
	private boolean top, bottom, left, right; // �������ұ߿��Ƿ�ɼ�
	private int gra;// �������ݶ��뷽ʽ

	public HeadCell() {
		desc = "";
		gra = Gravity.CENTER;
		top = false;
		bottom = false;
		left = false;
		right = false;
	}

	public HeadCell(String d, boolean t, boolean b, boolean l, boolean r, int g) {
		this.desc = d;
		this.top = t;
		this.bottom = b;
		this.left = l;
		this.right = r;
		this.gra = g;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public int getGra() {
		return gra;
	}

	public void setGra(int gra) {
		this.gra = gra;
	}

}
