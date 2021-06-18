package com.gov.tree;

import java.io.Serializable;
import java.util.ArrayList;


public class TreeOrgTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<TreeNodeText> data;
	
	public TreeOrgTable(){
		data = new ArrayList<TreeNodeText>();
	}
	public TreeOrgTable (ArrayList<TreeNodeText> d){
		this.setData(d);
	}
	public ArrayList<TreeNodeText> getData() {
		return data;
	}
	public void setData(ArrayList<TreeNodeText> data) {
		this.data = data;
	}
	
	

}
