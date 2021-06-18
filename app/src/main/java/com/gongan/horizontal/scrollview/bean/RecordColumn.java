	package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;

public class RecordColumn implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rowsTitle; //行标题
    private String columnTitle; //列标题
    private String value; // 属性值
    private String executeWhereSql; //执行统计Where条件SQL语句
    public RecordColumn(){
    	
    }
    public RecordColumn(String sql,String val){
    	this.executeWhereSql =sql;
    	this.value = val;
    }
    public RecordColumn(String row,String col,String val){
    	this.rowsTitle = row;
    	this.columnTitle = col;
    	this.value = val;
    }
	public String getRowsTitle() {
		return rowsTitle;
	}
	public void setRowsTitle(String rowsTitle) {
		this.rowsTitle = rowsTitle;
	}
	public String getColumnTitle() {
		return columnTitle;
	}
	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}
	public String getValue() {
		if (value.equals("0")) value="";
		return value;
	}
	public String getIntValue(){
		return value;
	}
	public int  getValueOfInt(){
		return Integer.parseInt(value);
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getExecuteWhereSql() {
		return executeWhereSql;
	}
	public void setExecuteWhereSql(String executeWhereSql) {
		this.executeWhereSql = executeWhereSql;
	}
	/**
	 * 获取统计对象sql语句
	 * @return
	 */
	public String toSql(){
		String ret ="select * from t_user ";
		if (executeWhereSql!=null){
			ret +=executeWhereSql;
		}
		return ret;
	}
	/**
	 * 获取统计数值sql语句
	 * @return
	 */
	public String toSqlOfCount(){
		String ret ="select count(*) as size from t_user ";
		if (executeWhereSql!=null){
			ret +=executeWhereSql;
		}
		return ret;
	}
}
