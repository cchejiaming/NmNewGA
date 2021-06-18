package com.cxht.bean;

import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;

public class GroupBean
{
	@TreeNodeId
	private String _id;
	@TreeNodePid
	private String parentId;
	@TreeNodeLabel
	private String name;
	private String group_code;
	private long length;
	private String desc;
	
	private int config_sz_num;
	private int config_xz_num;
	private int sz_num;
	private int xz_num;

	public GroupBean(String _id, String parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}
	public GroupBean(String _id, String parentId, String name,String groupCode)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.group_code = groupCode;
	}
	public String getParentId(){
		
		return parentId;
	}
	public String getName()
	{
		return name;
	}
	public String getId(){
		return _id;
	}
	public int getConfig_sz_num() {
		return config_sz_num;
	}
	public void setConfig_sz_num(int config_sz_num) {
		this.config_sz_num = config_sz_num;
	}
	public int getConfig_xz_num() {
		return config_xz_num;
	}
	public void setConfig_xz_num(int config_xz_num) {
		this.config_xz_num = config_xz_num;
	}
	public int getSz_num() {
		return sz_num;
	}
	public void setSz_num(int sz_num) {
		this.sz_num = sz_num;
	}
	public int getXz_num() {
		return xz_num;
	}
	public void setXz_num(int xz_num) {
		this.xz_num = xz_num;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
}
