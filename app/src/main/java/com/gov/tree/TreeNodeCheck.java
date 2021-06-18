package com.gov.tree;

import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TreeNodeCheck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TreeNodeId
	private String id;
	 /** 
    * 根节点pId为0 
    */
	 @TreeNodePid
	private String pId = "0";
	//节点显示名称
	@TreeNodeLabel
	private String name;
	//节点显示值
	private String value;
	
	/**
	 * check类型Tree树节点是否选中参数
	 */
	private boolean isCheck;

	/** 
    * 当前的级别 
    */  
	private int level;
	/**
	 * 是否为sql过滤使用参数
	 */
	private boolean isParam = false; 

	/**
	 * 是否展开
	 */
	private boolean isExpand = false;

	private int icon;

	/** 
    * 下一级的子Node 
    */  
	private List<TreeNodeCheck> children = new ArrayList<TreeNodeCheck>();

	 /** 
    * 父Node 
    */  
	private TreeNodeCheck parent;

	public TreeNodeCheck()
	{
		
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TreeNodeCheck(String id, String pId, String name,boolean is)
	{
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isCheck = is;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getpId()
	{
		return pId;
	}

	public void setpId(String pId)
	{
		this.pId = pId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public boolean isExpand()
	{
		return isExpand;
	}

	public List<TreeNodeCheck> getChildren()
	{
		return children;
	}

	public void setChildren(List<TreeNodeCheck> children)
	{
		this.children = children;
	}

	public TreeNodeCheck getParent()
	{
		return parent;
	}

	public void setParent(TreeNodeCheck parent)
	{
		this.parent = parent;
	}

	  /** 
    * 是否为跟节点 
    *  
    * @return 
    */  
	public boolean isRoot()
	{
		return parent == null;
	}

	 /** 
    * 判断父节点是否展开 
    *  
    * @return 
    */  
	public boolean isParentExpand()
	{
		if (parent == null)
			return false;
		return parent.isExpand();
	}

   /** 
    * 是否是叶子界点 
    *  
    * @return 
    */  
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

   /** 
    * 获取level 
    */  
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

   /** 
    * 设置展开 
    *  
    * @param isExpand 
    */ 
	public void setExpand(boolean isExpand)
	{
		this.isExpand = isExpand;
		if (!isExpand)
		{

			for (TreeNodeCheck node : children)
			{
				node.setExpand(isExpand);
			}
		}
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public boolean isParam() {
		return isParam;
	}

	public void setParam(boolean isParam) {
		this.isParam = isParam;
	}
}
