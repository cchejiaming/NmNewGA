package com.cxht.bean;

/**
 * �б���ʾ��
 * Created by HeJiaMing on 2020/10/14 09:52
 * E-Mail Address��1774690@qq.com
 */
public class Item {
    private String display; //��ʾ��ǩ
    private String value; //��Ŀֵ
    public  Item(String display,String value){
        this.display = display;
        this.value = value;
    }
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
