package com.cxht.entity;

import android.app.Activity;

/**
 * 
 * @author hejiaming
 * 	首页导航对象实体类
 */
public class NaviClass {
	
    private String title; //名称
    private int image; //图片id
    private int sort;//排序
    private  Class<? extends Activity> navi;
    public NaviClass(){}
	public NaviClass(String title,int image,int sort,Class<? extends Activity> navi){
		this.setTitle(title);
		this.image = image;
		this.sort = sort;
		this.setNavi(navi);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Class<? extends Activity> getNavi() {
		return navi;
	}
	public void setNavi(Class<? extends Activity> navi) {
		this.navi = navi;
	}
	

	
}
