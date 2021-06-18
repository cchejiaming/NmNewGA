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
	 * 人员年龄显示字符串
	 * @param d 年龄
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
							ret += frontCompWithZore(year, 4) + "年";
						}
					}
					if (i == 1) {
						int month = Integer.parseInt(temp[1]);
						if (month > 0) {
							ret += frontCompWithZore(month, 2) + "月";
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
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * 
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	public static String frontCompWithZore(int sourceDate, int formatLength) {
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
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
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {

		if (str != null) {
			str = str.replaceAll("【", "[").replaceAll("】", "]")
					.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
			String regEx = "[『』]"; // 清除掉特殊字符
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			str = m.replaceAll("").trim();
		}

		return str;
	}

	/**
	 * 计算年龄
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
			result = "[" + age + "岁]";
		}

		return result;
	}

	/**
	 * 获得条件查询子项集合字符串
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
	 * 获取sql组织子项ID组合
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
	 * 获得子项GroupBean列表
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
		 * 获得当前年月日
		 */
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		return year + "-" + (month + 1) + "-" + day;

	}

	public static String getTime() {
		/**
		 * 获得当前实际
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
		case "研究生":
			result = " and "+colName+" like '%研究生%' ";
			break;
		case "大学":
			result = " and "+colName+" like '%大学%' ";
			break;
		case "大专":
			result = " and "+colName+" like '%大专%' ";
			break;
		case "中专及以下":
			result = " and ("+colName+" like '%中专%' or "+colName+" like '%高中%' or "
		         +colName+" like '%初中%' or "+colName+" like '%小学%' )";
			break;
		
		}
		return result;
	}

	/**
	 * 获取分段时间AndWhere参数
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
	 * 获取年龄AndWhere参数
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
	 * 图片地址列表
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
				ret += "实职" + c_sz + "实有" + sz + "人";

			}
			if (c_xz > 0) {
				if (ret.length() != 1)
					ret += ",";
				ret += "虚职" + c_xz + "实有" + xz + "人";
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
		 * 获得当前日期
		 */

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.add(2, m);// 表示月份
		int year = gc.get(Calendar.YEAR);
		int month = gc.get(Calendar.MONTH) + 1;
		return String.valueOf(year) +"-"+ frontCompWithZore(month, 2);
	}

	/**
	 * 字符串转换成日期
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
	 * yyyy.MM 格式实际转换、
	 * @param str  时间格式需 yyyy-MM-DD 格式类型
	 * @return yyyy.MM 字符串
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
			gc.add(2, m);// 表示月份
			int year = gc.get(Calendar.YEAR);
			int month = gc.get(Calendar.MONTH) + 1;
			ret = String.valueOf(year) + "-" + frontCompWithZore(month, 2);
		}
		return ret;
	}
	/**
	 * 获取年月显示字符串(内蒙古公安修改)
	 * @param date 日期格式字符串
	 * @param format 时间格式
	 * @return 字符串
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
			// 判断年份间隔和月份剩余显示
			if(yNub>0){
				if (mNum>0){
					result = "["+yNub+"年"+mNum+"个月]";
				}else{
					result = "["+yNub+"年]";
				}
				
			}else{
				result = "["+mNum+"个月]";
			}
			// result = "["+n+"年]";

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
			// result = "["+n+"年]";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取二个时间月份间隔
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
	 * 获取AndWhere语句
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
	 * 长日期格式转换 yyyy.MM.dd 格式
	 * @param date 长日期格式字符串 yyyy-MM-dd 格式
	 * @return
	 */
	public static String longDateFormat(String date){
    	return formatDate(date,Setting.DB_LONG_DATE_FORMAT,Setting.SH_LONG_DATE_FORMAT);
	}
	/**
	 * 时间格式转换
	 * @param str 需要转换的日期格式字符串
	 * @param sourceFormat  来源日期字符串格式
	 * @param targetFormat  目标字符串格式
	 * @return 转换时间格式
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
	 * 根据出生日期自动计算年龄
	 * @param birthDay 生日
	 * @return 年龄
	 * @throws ParseException 异常抛出
	 */
	public static int getAgeByBirth(Date birthDay) throws ParseException {
		int age = 0;
		Calendar cal = Calendar.getInstance( );
		if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);  //当前年份
		int monthNow = cal.get(Calendar.MONTH);  //当前月份
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		age = yearNow - yearBirth;   //计算整岁数
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
			} else {
				age--;//当前月份在生日之前，年龄减一
			}
		}
		return age;
	}

	/**
	 * 获取人员年龄
	 * @param date 时间字符串
	 * @param format 字符串格式
	 * @return 年龄 int
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
	 * 字符串转int类型 （强制转换）
	 * @param str 字符串
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
