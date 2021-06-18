package com.gov.tree;

import java.io.Serializable;

import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;

public class TreeNodeText implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private String orgId;
	private long length;
	private String desc;
	private int childSize = 0;
    public TreeNodeText(){
    	
    }
	public TreeNodeText(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}
	public int getParentId(){
		
		return parentId;
	}
	public void setParentId(int pid){
		this.parentId = pid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String n){
		this.name = n;
	}
	public int getId(){
		return _id;
	}
	public void setId(int id){
		this._id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public int getChildSize() {
		return childSize;
	}
	public void setChildSize(int childSize) {
		this.childSize = childSize;
	}
}
