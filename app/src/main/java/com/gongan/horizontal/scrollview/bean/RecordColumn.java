	package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;

public class RecordColumn implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rowsTitle; //�б���
    private String columnTitle; //�б���
    private String value; // ����ֵ
    private String executeWhereSql; //ִ��ͳ��Where����SQL���
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
	 * ��ȡͳ�ƶ���sql���
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
	 * ��ȡͳ����ֵsql���
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
