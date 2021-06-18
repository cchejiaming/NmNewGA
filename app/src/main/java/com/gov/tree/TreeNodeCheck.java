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
    * ���ڵ�pIdΪ0 
    */
	 @TreeNodePid
	private String pId = "0";
	//�ڵ���ʾ����
	@TreeNodeLabel
	private String name;
	//�ڵ���ʾֵ
	private String value;
	
	/**
	 * check����Tree���ڵ��Ƿ�ѡ�в���
	 */
	private boolean isCheck;

	/** 
    * ��ǰ�ļ��� 
    */  
	private int level;
	/**
	 * �Ƿ�Ϊsql����ʹ�ò���
	 */
	private boolean isParam = false; 

	/**
	 * �Ƿ�չ��
	 */
	private boolean isExpand = false;

	private int icon;

	/** 
    * ��һ������Node 
    */  
	private List<TreeNodeCheck> children = new ArrayList<TreeNodeCheck>();

	 /** 
    * ��Node 
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
    * �Ƿ�Ϊ���ڵ� 
    *  
    * @return 
    */  
	public boolean isRoot()
	{
		return parent == null;
	}

	 /** 
    * �жϸ��ڵ��Ƿ�չ�� 
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
    * �Ƿ���Ҷ�ӽ�� 
    *  
    * @return 
    */  
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

   /** 
    * ��ȡlevel 
    */  
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

   /** 
    * ����չ�� 
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
