package com.cxht.bean;

import java.io.Serializable;

/**
 * 职数详细信息项实体类
 * Created by HeJiaMing on 2020/11/16 10:23
 * E-Mail Address：1774690@qq.com
 */
public class GroupNumberItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;//表名
    private String columnName;//列名
    private String name;//职数名称
    private String simpleName;//简称
    private int numberOfPeople; //配置人数
    private int actualNumberOfPeople;//统计后实际人数
    private String andWhere;//执行sql语句where 过滤条件

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getActualNumberOfPeople() {
        return actualNumberOfPeople;
    }

    public void setActualNumberOfPeople(int actualNumberOfPeople) {
        this.actualNumberOfPeople = actualNumberOfPeople;
    }

    public String getAndWhere() {
        return andWhere;
    }

    public void setAndWhere(String andWhere) {
        this.andWhere = andWhere;
    }



}
