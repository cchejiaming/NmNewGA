package com.cxht.unit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.cxht.config.GonganApplication;

/**
 * ���ڼ���ʱ�䵥Ԫ
 * @author Administrator
 *
 */
public class DateUtil {
	
   public void saveImportDate(Context context){
	  String impDate = StringUtil.getData();
	  GonganApplication.saveShareText(context,"importDate",impDate);
   }
   public Date readImportDate(Context context){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	String impDate = GonganApplication.readShareText(context, "importDate");
    
    	Date d2 = null;
    	if (impDate!= null){
    		try {
    			d2 = df.parse(impDate);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
		
    	return d2;
   }

   
	 /**
	  * ������ĩ�ڼ����ж�
	  * 
	  * @param date
	  * @return
	  */
	 public static boolean isWeekend(Date date) {
	  boolean weekend = false;
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(date);
	  if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
	    || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
	    || cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
	   weekend = true;
	  }
	  //�ж������Ƿ��ǽڼ���
		 for (Calendar ca :getHolidayList ()) {
			if(ca.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
					ca.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)){
				return true;
			}
		}
	  return weekend;
	 }

	
/**
 * ��ȡ�����б�
 * @return
 */
    private static List<Calendar> getHolidayList (){
    	List<Calendar> list =  new ArrayList<Calendar>();
    	list.add(initHolidayList("2019-01-01"));
    	list.add(initHolidayList("2019-01-02"));
    	list.add(initHolidayList("2019-01-03"));
    	list.add(initHolidayList("2019-02-04"));
    	list.add(initHolidayList("2019-02-05"));
    	list.add(initHolidayList("2019-02-06"));
    	list.add(initHolidayList("2019-02-07"));
    	list.add(initHolidayList("2019-02-08"));
    	list.add(initHolidayList("2019-02-09"));
    	list.add(initHolidayList("2019-02-10"));
    	list.add(initHolidayList("2019-02-11"));
    	list.add(initHolidayList("2019-02-12"));
    	list.add(initHolidayList("2019-02-13"));
    	list.add(initHolidayList("2019-02-14"));
    	list.add(initHolidayList("2019-02-15"));
    	list.add(initHolidayList("2019-04-05"));
    	list.add(initHolidayList("2019-05-01"));
    	list.add(initHolidayList("2019-05-02"));
    	list.add(initHolidayList("2019-09-13"));
    	list.add(initHolidayList("2019-10-01"));
    	list.add(initHolidayList("2019-10-02"));
    	list.add(initHolidayList("2019-10-03"));
    	list.add(initHolidayList("2019-10-04"));
    	list.add(initHolidayList("2019-10-05"));
    	list.add(initHolidayList("2019-10-06"));
    	list.add(initHolidayList("2019-10-07"));
    	
    	
    	return list;
    }
	
	 
	 /**
	  * 
	  * �����нڼ��շ���list
	  * @param date  �����ݿ�� ������ĸ�ʽ2016-05-09
	  * return void    �������� 
	  * throws 
	  */
	private static Calendar initHolidayList(String date){
		
			String [] da = date.split("-");
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
			calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);//�·ݱ�����С1,0����һ��
			calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
			return calendar;
	}
	


}
