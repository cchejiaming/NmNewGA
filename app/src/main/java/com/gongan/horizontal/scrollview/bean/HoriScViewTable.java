package com.gongan.horizontal.scrollview.bean;

import java.io.Serializable;
import java.util.List;

public class HoriScViewTable implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HeadRows headRow;
    private List<RecordRows> bodyRows;
    public HoriScViewTable(){
    
    }
    public HoriScViewTable(HeadRows h,List<RecordRows> b){
    	this.headRow = h;
    	this.bodyRows = b;
    }
	public HeadRows getHeadRow() {
		return headRow;
	}
	public void setHeadRow(HeadRows headRow) {
		this.headRow = headRow;
	}
	public List<RecordRows> getBodyRows() {
		return bodyRows;
	}
	public void setBodyRows(List<RecordRows> bodyRows) {
		this.bodyRows = bodyRows;
	}
	public RecordColumn getRecordColumn(int rIndex,int cIndex){
		RecordColumn ret= null;
		if (bodyRows!=null){
			ret = bodyRows.get(rIndex).getRows().get(cIndex); 
		}
		return ret;
	}
}
