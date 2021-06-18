package com.gov.tree;

import java.io.Serializable;
import java.util.ArrayList;

public class TreeOrgCkTable  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<TreeNodeCheck> data;
	
	public TreeOrgCkTable(){
		data = new ArrayList<TreeNodeCheck>();
	}
	public TreeOrgCkTable (ArrayList<TreeNodeCheck> d){
		this.setData(d);
	}
	public ArrayList<TreeNodeCheck> getData() {
		return data;
	}
	public void setData(ArrayList<TreeNodeCheck> data) {
		this.data = data;
	}
}
