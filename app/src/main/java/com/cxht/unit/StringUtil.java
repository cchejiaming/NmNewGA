package com.cxht.unit;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cxht.bean.AgeGroupBean;
import com.cxht.bean.GroupBean;
import com.cxht.bean.InfoItem;
import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.dao.GroupDao;

public class StringUtil {

	public static boolean isEmpty(String string) {
		if (string == null || "".equals(string)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * ��Ա������ʾ�ַ���
	 * @param d ����
	 * @return 
	 */
	public static String toAgeStr(double d) {
		DecimalFormat formater = new DecimalFormat();

		formater.setMaximumFractionDigits(1);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		String ret = formater.format(d);
		return ret;
	}
	public static String getResumTime(String time) {
		String ret = time;
		try {
			String[] temp = time.split("\\/");
			if (temp.length == 3) {

				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						int year = Integer.parseInt(temp[0]);
						if (year > 0) {
							ret += frontCompWithZore(year, 4) + "��";
						}
					}
					if (i == 1) {
						int month = Integer.parseInt(temp[1]);
						if (month > 0) {
							ret += frontCompWithZore(month, 2) + "��";
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		return time;
	}

	public static String getSortTime(String sort) {
		String ret = "";
		try {
			String[] temp = sort.split("\\/");
			if (temp.length == 3) {

				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						int year = Integer.parseInt(temp[0]);
						if (year > 0) {
							ret += frontCompWithZore(year, 4);
						}
					}
					if (i == 1) {
						int month = Integer.parseInt(temp[1]);
						if (month > 0) {
							ret += frontCompWithZore(month, 2);
						}
					}
					if (i == 2) {
						int day = Integer.parseInt(temp[2]);
						if (day > 0) {
							ret += frontCompWithZore(day, 2);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret;
	}

	/**
	 * ��Ԫ����ǰ���㣬������ܳ���Ϊָ���ĳ��ȣ����ַ�������ʽ����
	 * 
	 * @param sourceDate
	 * @param formatLength
	 * @return ����������
	 */
	public static String frontCompWithZore(int sourceDate, int formatLength) {
		/*
		 * 0 ָǰ�油���� formatLength �ַ��ܳ���Ϊ formatLength d ����Ϊ������
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

	public static boolean isNotEmpty(String string) {
		if (string != null && !"".equals(string)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getSubString(String desc, int length) {
		if (desc.length() < length) {
			return desc;
		} else {
			return desc.substring(0, length) + "...";
		}

	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String JsonFilter(String jsonstr) {

		return jsonstr.substring(jsonstr.indexOf("{")).replace("\r\n", "\n");

	}

	/**
	 * ȥ�������ַ����������ı���滻ΪӢ�ı��
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {

		if (str != null) {
			str = str.replaceAll("��", "[").replaceAll("��", "]")
					.replaceAll("��", "!").replaceAll("��", ":");// �滻���ı��
			String regEx = "[����]"; // ����������ַ�
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			str = m.replaceAll("").trim();
		}

		return str;
	}

	/**
	 * ��������
	 */
	public static String getAge(String birthData) {
		String result = "";
		int birthYear = 0;
		try {
			birthYear = Integer.valueOf(birthData.substring(0, 4));
		} catch (Exception e) {
		}
		if (birthYear != 0) {
			int age = GonganApplication.getDateNowYear() - birthYear;
			result = "[" + age + "��]";
		}

		return result;
	}

	/**
	 * ���������ѯ������ַ���
	 * 
	 * @param parentId
	 * @return
	 */
	public static String getSqlInItems(String parentId, Context context) {

		String ret = GonganApplication.readShareText(context,parentId);
		if (ret == null || ret.equals("")) {
			sql = "'"+parentId+"'";
			getSqlNonGropChildID(parentId, context);
			ret = sql;
			GonganApplication.saveShareText(context, parentId,ret);
		}

		return ret;
	}

	/**
	 * ��ȡsql��֯����ID���
	 * 
	 * @param parentId
	 */
	private static String sql = "";

	public static void getSqlNonGropChildID(String parentId, Context context) {

		List<GroupBean> all = getChildGroupBeans(parentId, context);

		if (all.size() == 0) {
			return;
		} else {
			for (int i = 0; i < all.size(); i++) {
				{
					sql += ",'" + all.get(i).getId()+"'";
					getSqlNonGropChildID(all.get(i).getId(), context);
				}

			}
		}

	}

	/**
	 * �������GroupBean�б�
	 * 
	 * @param parentId
	 * @return
	 */
	private static List<GroupBean> getChildGroupBeans(String parentId,
			Context context) {
		List<GroupBean> mGroup = GonganApplication.getGroupBeanList(context);
		List<GroupBean> result = new ArrayList<GroupBean>();
		if (mGroup != null) {
			for (int i = 0; i < mGroup.size(); i++) {
				if (parentId == mGroup.get(i).getParentId()) {
					result.add(mGroup.get(i));
				}
			}
		}

		return result;
	}
    public static String getWhere(String sql){
		if (sql!=null){
			Log.i("TextCharWhere",sql);
			int startIdx = sql.indexOf("and");
			if (startIdx>-1){
				String andWhere = sql.substring(startIdx);
				Log.i("TextCharWhere",andWhere);
				return andWhere;
			}
		}
		return null;
	}
	public static String getData() {
		/**
		 * ��õ�ǰ������
		 */
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		return year + "-" + (month + 1) + "-" + day;

	}

	public static String getTime() {
		/**
		 * ��õ�ǰʵ��
		 */
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		return year + "-" + (month + 1) + "-" + date + " " + hour + ":"
				+ minute + ":" + second;

	}



	private static String getEduAndWhere(String title,String colName) {
		String result = "";
		//result = " and before_education='" + title + "'";
		switch(title)
		{
		case "�о���":
			result = " and "+colName+" like '%�о���%' ";
			break;
		case "��ѧ":
			result = " and "+colName+" like '%��ѧ%' ";
			break;
		case "��ר":
			result = " and "+colName+" like '%��ר%' ";
			break;
		case "��ר������":
			result = " and ("+colName+" like '%��ר%' or "+colName+" like '%����%' or "
		         +colName+" like '%����%' or "+colName+" like '%Сѧ%' )";
			break;
		
		}
		return result;
	}

	/**
	 * ��ȡ�ֶ�ʱ��AndWhere����
	 * 
	 * @param title
	 * @param colName
	 * @return
	 */
	public static String getYearAndWhere(String title, String colName) {
		String result = null;
		List<AgeGroupBean> list = Setting.getYearGroupList();
		for (AgeGroupBean agb : list) {
			if (agb.getTitle().equals(title)) {
				result = " and " + colName + " >'" + agb.getStartYear() + "' and "
						+ colName + " < '" + agb.getOverYear()+"'";
			}
		}
		return result;
	}

	/**
	 * ��ȡ����AndWhere����
	 * 
	 * @param title
	 * @return
	 */
	public static String getAgeAndWhere(String title) {
		String result = null;
		List<AgeGroupBean> list = Setting.getAgeGroupList();
		for (AgeGroupBean agb : list) {
			if (agb.getTitle().equals(title)) {
				result = " and birth_date>'" + agb.getStartYear()
						+ "' and birth_date< '" + agb.getOverYear()+"'";
			}
		}
		return result;
	}

	/**
	 * ͼƬ��ַ�б�
	 * 
	 * @param json
	 * @return
	 */
	public static List<String> ImagePathFromJson(String json) {
		List<String> list = null;
		if (json != null && !"".equals(json)) {
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(json);
				list = new ArrayList<String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					String imageUrl = obj.getString("ImagePath");
					list.add(imageUrl);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;

	}

	public static String getShortDate(String date, String key) {
		String res = "";
		if (!date.equals(null) && !date.equals("")) {
			int index = date.indexOf(key);
			if (index > -1) {
				res = date.substring(0, index);
			} else {
				res = date;
			}
		}
		return res;
	}

	public static String toPositionAttrString(int c_sz, int c_xz, int sz, int xz) {
		String ret = "";
		if (c_sz > 0 || c_xz > 0) {
			ret += "(";
			if (c_sz > 0) {
				ret += "ʵְ" + c_sz + "ʵ��" + sz + "��";

			}
			if (c_xz > 0) {
				if (ret.length() != 1)
					ret += ",";
				ret += "��ְ" + c_xz + "ʵ��" + xz + "��";
			}

			ret += ")";
		}

		return ret;
	}

	public static String getNowDataYear() {
		Calendar c1 = Calendar.getInstance();
		return String.valueOf(c1.get(Calendar.YEAR));
	}

	public static String getNowDataMonth() {
		Calendar c1 = Calendar.getInstance();
		return String.valueOf(c1.get(Calendar.MONTH) + 1);
	}

	public static String getNowYM(int m) {
		/**
		 * ��õ�ǰ����
		 */

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.add(2, m);// ��ʾ�·�
		int year = gc.get(Calendar.YEAR);
		int month = gc.get(Calendar.MONTH) + 1;
		return String.valueOf(year) +"-"+ frontCompWithZore(month, 2);
	}

	/**
	 * �ַ���ת��������
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat(Setting.DB_TIME_FORMAT);
		// SimpleDateFormat mformat = new
		// SimpleDateFormat(Setting.SH_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * yyyy.MM ��ʽʵ��ת����
	 * @param str  ʱ���ʽ�� yyyy-MM-DD ��ʽ����
	 * @return yyyy.MM �ַ���
	 */
	public static String toShowData(String str) {

		String ret = "";
	
		if (str!= null && !str.equals("1900-01")) {
			
			try {
				ret = str;
				SimpleDateFormat format = new SimpleDateFormat(
						Setting.DB_TIME_FORMAT);
				SimpleDateFormat mformat = new SimpleDateFormat(
						Setting.SH_TIME_FORMAT);
				Date date = null;
				date = format.parse(str);
				ret = mformat.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static String getYM(Date d, int m) {
		String ret = "";
		if (d != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			gc.add(2, m);// ��ʾ�·�
			int year = gc.get(Calendar.YEAR);
			int month = gc.get(Calendar.MONTH) + 1;
			ret = String.valueOf(year) + "-" + frontCompWithZore(month, 2);
		}
		return ret;
	}
	/**
	 * ��ȡ������ʾ�ַ���(���ɹŹ����޸�)
	 * @param date ���ڸ�ʽ�ַ���
	 * @param format ʱ���ʽ
	 * @return �ַ���
	 */
    public static String getYearMonthString(String date,String format){
    	String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Date d1 = sdf.parse(date);
			Date d2 = new Date();
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			c1.setTime(d1);
			c2.setTime(d2);
			int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			int m = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
			int sum =Math.abs(y) * 12 + m;
			int yNub = sum/12;
			int mNum = sum -(yNub*12);
			// �ж���ݼ�����·�ʣ����ʾ
			if(yNub>0){
				if (mNum>0){
					result = "["+yNub+"��"+mNum+"����]";
				}else{
					result = "["+yNub+"��]";
				}
				
			}else{
				result = "["+mNum+"����]";
			}
			// result = "["+n+"��]";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }
	public static int getYearNum(String date, String format) {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Date d1 = sdf.parse(date);
			Date d2 = new Date();
			result = getMonthSpace(d1, d2);
			// result = "["+n+"��]";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��ȡ����ʱ���·ݼ��
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(Date date1, Date date2)
			throws ParseException {

		int result = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);
		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		result = (Math.abs(y) * 12 + m) / 12;
		return result;

	}
	/**
	 * ��ȡAndWhere���
	 * @param list 
	 * @return
	 */
    public static String toAndWhere(List<?> list){
    	String ret = "";
        if(list!=null){
          for(int i = 0;i<list.size();i++){
        	  ret+= ","+ list.get(i).toString();
          }
          if (ret.length()>1) ret = ret.substring(1);
        }
    	return ret;
    }

	/**
	 * �����ڸ�ʽת�� yyyy.MM.dd ��ʽ
	 * @param date �����ڸ�ʽ�ַ��� yyyy-MM-dd ��ʽ
	 * @return
	 */
	public static String longDateFormat(String date){
    	return formatDate(date,Setting.DB_LONG_DATE_FORMAT,Setting.SH_LONG_DATE_FORMAT);
	}
	/**
	 * ʱ���ʽת��
	 * @param str ��Ҫת�������ڸ�ʽ�ַ���
	 * @param sourceFormat  ��Դ�����ַ�����ʽ
	 * @param targetFormat  Ŀ���ַ�����ʽ
	 * @return ת��ʱ���ʽ
	 */
    public static String formatDate(String str,String sourceFormat,String targetFormat){


		String ret = "";

		if (str!= null && !str.equals("1900-01")) {

			try {
				ret = str;
				SimpleDateFormat format = new SimpleDateFormat(
						sourceFormat);
				SimpleDateFormat mformat = new SimpleDateFormat(
						targetFormat);
				Date date = null;
				date = format.parse(str);
				ret = mformat.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ret;

	}

	/**
	 * ���ݳ��������Զ���������
	 * @param birthDay ����
	 * @return ����
	 * @throws ParseException �쳣�׳�
	 */
	public static int getAgeByBirth(Date birthDay) throws ParseException {
		int age = 0;
		Calendar cal = Calendar.getInstance( );
		if (cal.before(birthDay)) { //�����������ڵ�ǰʱ�䣬�޷�����
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);  //��ǰ���
		int monthNow = cal.get(Calendar.MONTH);  //��ǰ�·�
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //��ǰ����
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		age = yearNow - yearBirth;   //����������
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;//��ǰ����������֮ǰ�������һ
			} else {
				age--;//��ǰ�·�������֮ǰ�������һ
			}
		}
		return age;
	}

	/**
	 * ��ȡ��Ա����
	 * @param date ʱ���ַ���
	 * @param format �ַ�����ʽ
	 * @return ���� int
	 */
	public static int getAge(String date,String format){
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date d1 = sdf.parse(date);
			result = getAgeByBirth(d1);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �ַ���תint���� ��ǿ��ת����
	 * @param str �ַ���
	 * @return
	 */
	public static int toInt(String str){
		int ret = -1;
		if (str!= null){
			try{
				ret = Integer.parseInt(str);
			}catch (Exception ex){
				ex.printStackTrace( );
			}
		}
		return  ret;
	}
}
