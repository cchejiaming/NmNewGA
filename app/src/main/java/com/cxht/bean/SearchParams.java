package com.cxht.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cxht.unit.StringUtil;

/**
 * 自定义查询参数实体类
 */
public class SearchParams implements Serializable {

	private static final long serialVersionUID = 1L;
	private String content; // 查询内容
	private int sex; // 性别
	private int age_min; // 最小年龄
	private int age_max; // 最大年龄
	private String st_time; // 统计日期
	private List<SearchItem> nation; // 民族
	private List<SearchItem> political; // 党派
	private List<SearchItem> fullEdu; // 全日制学历
	private List<SearchItem> fullDegree; // 全日制学位
	private List<SearchItem> jobEdu; // 在职学位
	private List<SearchItem> jobDegree; // 在职学历
	private List<SearchItem> highestEdu; // 最高学历
	private List<SearchItem> highestDegree; // 最高学位
	private List<SearchItem> position; // 职务级别
	private List<SearchItem> orgs;// 工作单位
	private List<SearchItem> nature; // 单位性质
	private List<SearchItem> resumeKey; // 履历模糊查询关键字
	private List<SearchItem> jobYear; // 职务年限
	private List<SearchItem> posYear; // 现职级年限
	private List<SearchItem> workYear; // 工作年限
	private List<SearchItem> partyYear; // 入党年限
	private List<SearchItem> posiAttr;// 职务属性
	private List<SearchItem> familyAge; // 家庭成员年龄段
    private List<SearchItem> familyJob;//家庭成员职务模糊查询
	private List<SearchItem> familyName;//家庭成员姓名
    private List<SearchItem> major; // 全日制专业查询
	private List<SearchItem> fullSchool;//全日制院校名称
	private List<SearchItem> jobSchool;//在职院校名称
	private List<SearchItem> qualification;//职称
	private List<SearchItem> nativeplace;//籍贯
	private List<SearchItem> birthplace;//出生地
	private List<SearchItem> jobDesc;//现任职务
	private List<SearchItem> state;//健康状况
	private List<SearchItem> assessResult;//年度结果
	private List<SearchItem> assessYear;//年度考核年份
	private List<SearchItem> punishName;//惩戒名称
	private List<SearchItem> punishType;//惩戒类型
	private List<SearchItem>  examine;//奖励信息
	private List<SearchItem> userNumber;//警号
	private List<SearchItem> userRank;//警衔
	private List<SearchItem> marshals;//执法资格
	private List<SearchItem> gunLicence;//持枪证信息
	private List<SearchItem> userType;//用户类型
	private List<SearchItem> jobLxYear; // 连续任改职务年限
	private List<SearchItem> depthLxYear; // 任相当层次职务职级起算年限
	private List<SearchItem> userCode;//身份证信息
	private List<SearchItem> jobGwy;//职务层次A02李公务员职务层次
	private List<SearchItem>  ratifyYear;//职务层次批准年限

	public SearchParams() {
		this.nation = new ArrayList<>();
		this.political = new ArrayList<>();
		this.fullEdu = new ArrayList<>();
		this.fullDegree = new ArrayList<>();
		this.jobEdu = new ArrayList<>();
		this.jobDegree = new ArrayList<>();
		this.highestEdu = new ArrayList<>();
		this.highestDegree = new ArrayList<>();
		this.position = new ArrayList<>();
		this.orgs = new ArrayList<>();
		this.nature = new ArrayList<>();
		this.resumeKey = new ArrayList<>();
		this.jobYear = new ArrayList<>();
		this.posYear = new ArrayList<>();
		this.posiAttr = new ArrayList<>();
		this.familyAge = new ArrayList<>();
		this.familyJob = new ArrayList<>();
		this.familyName = new ArrayList <>();
		this.major = new ArrayList<>();
		this.fullSchool = new ArrayList <>();
		this.jobSchool = new ArrayList <>();
		this.qualification = new ArrayList <>();
		this.workYear = new ArrayList <>();
		this.partyYear = new ArrayList <>();
		this.nativeplace = new ArrayList <>();
		this.birthplace = new ArrayList <>();
		this.jobDesc = new ArrayList <>();
		this.state = new ArrayList <>();
		this.examine = new ArrayList <>();
		this.assessResult = new ArrayList <>();
		this.assessYear = new ArrayList <>();
		this.punishName = new ArrayList <>();
		this.punishType = new ArrayList <>();
		this.userNumber = new ArrayList <>();
		this.userRank = new ArrayList <>();
		this.marshals = new ArrayList <>();
		this.gunLicence = new ArrayList <>();
		this.userType = new ArrayList<>();
		this.jobLxYear = new ArrayList <>();
		this.depthLxYear = new ArrayList <>();
		this.userCode = new ArrayList <>();
		this.jobGwy = new ArrayList <>();
		this.ratifyYear = new ArrayList <>();
	}


	public List <SearchItem> getRatifyYear() {
		return ratifyYear;
	}

	public void setRatifyYear(List <SearchItem> ratifyYear) {
		this.ratifyYear = ratifyYear;
	}

	public List <SearchItem> getJobGwy() {
		return jobGwy;
	}

	public void setJobGwy(List <SearchItem> jobGwy) {
		this.jobGwy = jobGwy;
	}

	public List <SearchItem> getUserCode() {
		return userCode;
	}

	public void setUserCode(List <SearchItem> userCode) {
		this.userCode = userCode;
	}

	public List <SearchItem> getJobLxYear() {
		return jobLxYear;
	}

	public void setJobLxYear(List <SearchItem> jobLxYear) {
		this.jobLxYear = jobLxYear;
	}

	public List <SearchItem> getDepthLxYear() {
		return depthLxYear;
	}

	public void setDepthLxYear(List <SearchItem> depthLxYear) {
		this.depthLxYear = depthLxYear;
	}

	public List <SearchItem> getUserType() {
		return userType;
	}

	public void setUserType(List <SearchItem> userType) {
		this.userType = userType;
	}

	public List <SearchItem> getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(List <SearchItem> userNumber) {
		this.userNumber = userNumber;
	}

	public List <SearchItem> getUserRank() {
		return userRank;
	}

	public void setUserRank(List <SearchItem> userRank) {
		this.userRank = userRank;
	}

	public List <SearchItem> getMarshals() {
		return marshals;
	}

	public void setMarshals(List <SearchItem> marshals) {
		this.marshals = marshals;
	}

	public List <SearchItem> getGunLicence() {
		return gunLicence;
	}

	public void setGunLicence(List <SearchItem> gunLicence) {
		this.gunLicence = gunLicence;
	}

	public List <SearchItem> getFullSchool() {
		return fullSchool;
	}

	public void setFullSchool(List <SearchItem> fullSchool) {
		this.fullSchool = fullSchool;
	}

	public List <SearchItem> getJobSchool() {
		return jobSchool;
	}

	public void setJobSchool(List <SearchItem> jobSchool) {
		this.jobSchool = jobSchool;
	}

	public List<SearchItem> getNation() {
		return nation;
	}

	public void setNation(List<SearchItem> nation) {
		this.nation = nation;
	}

	public List<SearchItem> getPolitical() {
		return political;
	}

	public void setPolitical(List<SearchItem> political) {
		this.political = political;
	}

	public List<SearchItem> getFullEdu() {
		return fullEdu;
	}

	public void setFullEdu(List<SearchItem> fullEdu) {
		this.fullEdu = fullEdu;
	}

	public List<SearchItem> getFullDegree() {
		return fullDegree;
	}

	public void setFullDegree(List<SearchItem> fullDegree) {
		this.fullDegree = fullDegree;
	}

	public List<SearchItem> getJobEdu() {
		return jobEdu;
	}

	public void setJobEdu(List<SearchItem> jobEdu) {
		this.jobEdu = jobEdu;
	}

	public List<SearchItem> getJobDegree() {
		return jobDegree;
	}

	public void setJobDegree(List<SearchItem> jobDegree) {
		this.jobDegree = jobDegree;
	}

	public List<SearchItem> getPosition() {
		return position;
	}

	public void setPosition(List<SearchItem> position) {
		this.position = position;
	}

	public List<SearchItem> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<SearchItem> orgs) {
		this.orgs = orgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge_min() {
		return age_min;
	}

	public void setAge_min(int age_min) {
		this.age_min = age_min;
	}

	public int getAge_max() {
		return age_max;
	}

	public void setAge_max(int age_max) {
		this.age_max = age_max;
	}

	public List<SearchItem> getHighestEdu() {
		return highestEdu;
	}

	public void setHighestEdu(List<SearchItem> highestEdu) {
		this.highestEdu = highestEdu;
	}

	public List<SearchItem> getHighestDegree() {
		return highestDegree;
	}

	public List <SearchItem> getState() {
		return state;
	}

	public void setState(List <SearchItem> state) {
		this.state = state;
	}

	public void setHighestDegree(List<SearchItem> highestDegree) {
		this.highestDegree = highestDegree;
	}

	public List<SearchItem> getNature() {
		return nature;
	}

	public void setNature(List<SearchItem> nature) {
		this.nature = nature;
	}

	public List<SearchItem> getResumeKey() {
		return resumeKey;
	}

	public void setResumeKey(List<SearchItem> resumeKey) {
		this.resumeKey = resumeKey;
	}

	public List<SearchItem> getJobYear() {
		return jobYear;
	}

	public void setJobYear(List<SearchItem> jobYear) {
		this.jobYear = jobYear;
	}

	public List<SearchItem> getPosYear() {
		return posYear;
	}

	public void setPosYear(List<SearchItem> posYear) {
		this.posYear = posYear;
	}

	public List <SearchItem> getQualification() {
		return qualification;
	}

	public void setQualification(List <SearchItem> qualification) {
		this.qualification = qualification;
	}

	public List <SearchItem> getExamine() {
		return examine;
	}

	public void setExamine(List <SearchItem> examine) {
		this.examine = examine;
	}

	public List <SearchItem> getWorkYear() {
		return workYear;
	}

	public void setWorkYear(List <SearchItem> workYear) {
		this.workYear = workYear;
	}

	public List <SearchItem> getPartyYear() {
		return partyYear;
	}

	public void setPartyYear(List <SearchItem> partyYear) {
		this.partyYear = partyYear;
	}

	public List <SearchItem> getFamilyName() {
		return familyName;
	}

	public void setFamilyName(List <SearchItem> familyName) {
		this.familyName = familyName;
	}

	public List <SearchItem> getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(List <SearchItem> nativeplace) {
		this.nativeplace = nativeplace;
	}

	public List <SearchItem> getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(List <SearchItem> birthplace) {
		this.birthplace = birthplace;
	}

	public List <SearchItem> getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(List <SearchItem> jobDesc) {
		this.jobDesc = jobDesc;
	}

	public List <SearchItem> getAssessResult() {
		return assessResult;
	}

	public void setAssessResult(List <SearchItem> assessResult) {
		this.assessResult = assessResult;
	}

	public List <SearchItem> getAssessYear() {
		return assessYear;
	}

	public void setAssessYear(List <SearchItem> assessYear) {
		this.assessYear = assessYear;
	}

	public List <SearchItem> getPunishName() {
		return punishName;
	}

	public void setPunishName(List <SearchItem> punishName) {
		this.punishName = punishName;
	}

	public List <SearchItem> getPunishType() {
		return punishType;
	}

	public void setPunishType(List <SearchItem> punishType) {
		this.punishType = punishType;
	}


	public static String mergeSql2(List<String> list) {
		String ret = "";
		for (String si : list) {
			ret += "'" + si + "',";
		}
		if (ret.length() > 1)
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

	public static String mergeSql1(List<SearchItem> list) {
		String ret = "";
		for (SearchItem si : list) {
			ret += si.getValue() + ",";
		}
		if (ret.length() > 1)
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}
    public static String mergeValueSql(List<SearchItem> list){
		String ret = "";
		for (SearchItem si : list) {
			ret += "'" + si.getValue() + "',";
		}
		if (ret.length() > 1)
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}
	public static String mergeSql(List<SearchItem> list) {
		String ret = "";
		for (SearchItem si : list) {
			ret += "'" + si.getDisplay() + "',";
		}
		if (ret.length() > 1)
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

	public static String mergeLikeSql(List<SearchItem> list, String column) {
		String ret = "";
		for (SearchItem si : list) {
			ret += " " + column + " like '%" + si.getDisplay() + "%' or";
		}
		if (ret.length() > 1)
			ret = ret.substring(0, ret.length() - 2);
		return ret;
	}

	public static String mergeTimeSql(List<SearchItem> list, String column) {
		String ret = "";
		for (SearchItem si : list) {
			int index = Integer.parseInt(si.getValue()) + 1;
			int s_m = index * 12;
			int e_m = s_m + 12;
			if (index == 15) {
				ret += " (" + column + " < '" + StringUtil.getNowYM(s_m * (-1))
						+ "') or";
			} else {
				ret += " (" + column + " >= '"
						+ StringUtil.getNowYM(e_m * (-1)) + "' and " + column
						+ " < '" + StringUtil.getNowYM(s_m * (-1)) + "') or";
			}
		}
		if (ret.length() > 2)
			ret = ret.substring(0, ret.length() - 2);

		return ret;
	}

	public String toWhereSql() {
		String ret = " where 1=1 ";
		ret += getWhereSql();
		return ret;
	}

	/**
	 * 获取过滤sql条件
	 * @return
	 */
	public String getWhereSql() {
		String ret = "";
		if (content != null && content.length()>0) {
			if (content.matches("^[a-zA-Z]*")) {
				ret += " and spell like '%" + content + "%'";
			} else {
				ret += " and user_name like '%" + content + "%'";
			}

		}
		if (sex != 0) {

			if (sex == 1) {
				ret += " and sex ='男'";
			} else if (sex == 2) {
				ret += " and sex ='女'";
			}

		}
		if (st_time != null) {
			Date date = StringUtil.StrToDate(st_time);
			String minDate = StringUtil.getYM(date, (age_min * 12) * (-1));
			String maxDate = StringUtil
					.getYM(date, ((age_max + 1) * 12) * (-1));
			// Log.i("toSqlhjm",minDate+" "+ maxDate);
			ret += " and birth_date<= '" + minDate + "' and birth_date>'"
					+ maxDate + "'";
		}
		if (nation != null && nation.size() > 0) {
			ret += " and nation in (";
			ret += mergeSql(nation);
			ret += ")";
		}
		if (political != null && political.size() > 0) {
			ret += " and politics_status in (";
			ret += mergeSql(political);
			ret += ")";
		}
		if (fullEdu != null && fullEdu.size() > 0) {
			ret += " and before_education in (";
			ret += mergeSql(fullEdu);
			ret += ")";
		}
		if (fullDegree != null && fullDegree.size() > 0) {
			ret += " and user_code in (select DISTINCT user_code from t_degree_us where";
			ret +="("+ mergeLikeSql(fullDegree, "degree")+")";
			ret += " and type like '%全日制%')";

		}
		if (jobEdu != null && jobEdu.size() > 0) {
			ret += " and latest_education in (";
			ret += mergeSql(jobEdu);
			ret += ")";
		}
		if (jobDegree != null && jobDegree.size() > 0) {
			ret += " and user_code in (select DISTINCT user_code from t_degree_us where";
			ret +="("+ mergeLikeSql(jobDegree, "degree")+")";
			ret += " and type like '%在职%')";
		}
		if (highestEdu != null && highestEdu.size() > 0) {
			ret += " and highest_edu in (";
			ret += mergeSql(highestEdu);
			ret += ")";
		}
		if (highestDegree != null && highestDegree.size() > 0) {
			ret += " and user_code in (select DISTINCT user_code from t_degree_us where";
			ret += mergeLikeSql(highestDegree, "degree");
			ret += ")";
		}
		if(qualification!=null && qualification.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_qualification where";
			ret +=" qfc_name in ("+ mergeSql(qualification)+")";
			ret += ")";
		}
		if (position != null && position.size() > 0) {
			ret += " and user_code in (select user_code from t_pre_depth where name in(";
			ret += mergeSql(position);
			ret += "))";
		}

		if (jobYear != null && jobYear.size() == 2) {

			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(jobYear.get(0).getValue());
				int max = Integer.parseInt(jobYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil
						.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select DISTINCT user_code from t_job where start_time<= '"
						+ minDate + "' and start_time>'" + maxDate + "' )";
			}

			// ret += "and user_code in(select DISTINCT user_code from t_job where";
			// ret += mergeTimeSql(jobYear, "start_time");
			// ret += " and start_time <>'')";
		}
		if (jobLxYear != null && jobLxYear.size() == 2) {

			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(jobLxYear.get(0).getValue());
				int max = Integer.parseInt(jobLxYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil
						.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select DISTINCT user_code from t_job where lx_time<= '"
						+ minDate + "' and lx_time>'" + maxDate + "' )";
			}

		}
		if (ratifyYear != null && ratifyYear.size() == 2) {

			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(ratifyYear.get(0).getValue());
				int max = Integer.parseInt(ratifyYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select DISTINCT user_code from t_pre_depth where ratify_time<= '"
						+ minDate + "' and ratify_time>'" + maxDate + "' )";
			}

		}
		if (posYear != null && posYear.size() == 2) {

			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(posYear.get(0).getValue());
				int max = Integer.parseInt(posYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil
						.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select DISTINCT user_code from t_pre_depth where start_time<= '"
						+ minDate + "' and start_time>'" + maxDate + "' )";
			}

			// ret +=
			// "and user_code in (select DISTINCT user_code from t_pre_depth where";
			// ret += mergeTimeSql(posYear, "start_time");
			// ret += " and start_time <>'')";
		}
		if (depthLxYear != null && depthLxYear.size() == 2) {

			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(depthLxYear.get(0).getValue());
				int max = Integer.parseInt(depthLxYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil
						.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select DISTINCT user_code from t_pre_depth where lx_time<= '"+ minDate + "' and lx_time>'" + maxDate + "' )";
			}

		}
		if (resumeKey != null && resumeKey.size() > 0) {
			ret += " and user_code in (select DISTINCT user_code from t_resume where";
			ret += mergeLikeSql(resumeKey, "job_desc");
			ret += ")";
		}
		if (familyJob!= null && familyJob.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_family where";
			ret += mergeLikeSql(familyJob, "job_desc");
			ret += ")";
		}
		if (familyName!= null && familyName.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_family where";
			ret += mergeLikeSql(familyName, "family_name");
			ret += ")";
		}
		if (major!=null && major.size()>0){
			ret += " and ";
			ret += mergeLikeSql(major, "before_specialty");
			
		}
		if (fullSchool!=null && fullSchool.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_edu_us where";
			ret += mergeLikeSql(fullSchool, "school");
			ret += " and type = '全日制')";
		}
		if (jobSchool!=null && jobSchool.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_edu_us where";
			ret += mergeLikeSql(jobSchool, "school");
			ret += " and type = '在职')";

		}
		if (posiAttr != null && posiAttr.size() > 0) {
			ret += " and user_code in (select DISTINCT user_code from t_pre_depth where attr in (";
			ret += mergeSql(posiAttr);
			ret += "))";
		}
		if (orgs != null && orgs.size() > 0) {
			ret += " and (user_code in (select user_code from t_job where group_code in (";
			ret += mergeValueSql(orgs);
			ret += "))";
			ret += " or user_code in (select user_code from t_pre_depth where group_code in (";
			ret += mergeValueSql(orgs);
			ret += ")))";

		}
		if (familyAge != null && familyAge.size() == 2) {
			if (st_time != null) {
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(familyAge.get(0).getValue());
				int max = Integer.parseInt(familyAge.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil
						.getYM(date, ((max + 1) * 12) * (-1));
				// Log.i("toSqlhjm",minDate+" "+ maxDate);
				ret += " and user_code in (select user_code from t_family where birth_date<= '"
						+ minDate + "' and birth_date>'" + maxDate + "' )";
			}
		}
		if(workYear!=null && workYear.size()==2){
			if(st_time!=null){
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(workYear.get(0).getValue());
				int max = Integer.parseInt(workYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil.getYM(date, ((max + 1) * 12) * (-1));
				ret += " and job_time<= '" + minDate + "' and job_time>'"+ maxDate + "'";
			}
		}
		if(partyYear!=null && partyYear.size()==2){
			if(st_time!=null){
				Date date = StringUtil.StrToDate(st_time);
				int min = Integer.parseInt(partyYear.get(0).getValue());
				int max = Integer.parseInt(partyYear.get(1).getValue());
				String minDate = StringUtil.getYM(date, (min * 12) * (-1));
				String maxDate = StringUtil.getYM(date, ((max + 1) * 12) * (-1));
				ret += " and rd_time<= '" + minDate + "' and rd_time>'"+ maxDate + "'";
			}
		}
		if (nativeplace!= null && nativeplace.size()>0){
			ret += " and  (";
			ret += mergeLikeSql(nativeplace, "nativeplace");
			ret += ")";
		}
		if (birthplace!= null && birthplace.size()>0){
			ret += " and  (";
			ret += mergeLikeSql(birthplace, "birthplace");
			ret += ")";
		}
		if (jobDesc!= null && jobDesc.size()>0){
			ret += " and  (";
			ret += mergeLikeSql(jobDesc, "position");
			ret += ")";
		}
		if (state!= null && state.size()>0){
			ret += " and state in (";
			ret += mergeSql(state);
			ret += ")";
		}
		if(assessYear!=null && assessYear.size()>0){
			ret += " and user_code in (select user_code from t_assess where year in (";
			ret += mergeSql(assessYear);
			ret += "))";
		}
		if(assessResult!=null && assessResult.size()>0){
			ret += " and user_code in (select user_code from t_assess where result in (";
			ret += mergeSql(assessResult);
			ret += "))";
		}
		if(punishType!=null && punishType.size()>0){
			ret += " and user_code in (select user_code from t_punish where punish_type in (";
			ret += mergeSql(punishType);
			ret += "))";
		}
		if(punishName!=null && punishName.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_punish where punish_code in (";
			ret += mergeValueSql(punishName);
			ret += "))";
		}
		if(examine!=null && examine.size()>0){
			ret += " and user_code in (select DISTINCT user_code from t_examine where examine_code in (";
			ret += mergeValueSql(examine);
			ret += "))";
		}
		if(userNumber!=null && userNumber.size()>0){
			ret += " and number in (";
			ret += mergeSql(userNumber);
			ret += ")";
		}
		if(userRank!=null && userRank.size()>0){
			ret += " and user_code in (select user_code from t_rank where rank_code in (";
			ret += mergeValueSql(userRank);
			ret += "))";
		}
		if(marshals!=null && marshals.size()>0){
			ret += " and user_code in (select user_code from t_marshals where level_code in (";
			ret += mergeValueSql(marshals);
			ret += "))";
		}
		if(gunLicence!=null && gunLicence.size()>0){
			ret += " and user_code in (select user_code from t_gun where licence in (";
			ret += mergeSql(gunLicence);
			ret += "))";
		}
		if(userType!=null && userType.size()>0){
			ret +=" and user_type in (";
			ret += mergeSql(userType);
			ret += ")";
		}
		if(userCode!=null && userCode.size()>0){
			ret += " and user_code in (";
			ret += mergeSql(userCode);
			ret += ")";
		}
		if (jobGwy != null && jobGwy.size() > 0) {
			ret += " and user_code in (select user_code from t_job where depth in (";
			ret += mergeSql(jobGwy);
			ret += "))";

		}
		return ret;
	}

	/**
	 * 生成sql可执行语句
	 * @param r
	 * @return
	 */
	private String strToSql(String r) {
		String ret = r;
		ret += getWhereSql();
		return ret;
	}

	public String toSql() {
		String ret = "select * from t_user where 1=1";
		return strToSql(ret);
	}

	public String toSqlOfCount() {
		String ret = "select count(*) as size from t_user where 1=1";

		return strToSql(ret);
	}

	public String getSt_time() {
		return st_time;
	}

	public void setSt_time(String st_time) {
		this.st_time = st_time;
	}

	public List<SearchItem> getPosiAttr() {
		return posiAttr;
	}

	public void setPosiAttr(List<SearchItem> posiAttr) {
		this.posiAttr = posiAttr;
	}

	public List<SearchItem> getFamilyAge() {
		return familyAge;
	}

	public void setFamilyAge(List<SearchItem> familyAge) {
		this.familyAge = familyAge;
	}

	public List<SearchItem> getFamilyJob() {
		return familyJob;
	}

	public void setFamilyJob(List<SearchItem> familyJob) {
		this.familyJob = familyJob;
	}

	public List<SearchItem> getMajor() {
		return major;
	}

	public void setMajor(List<SearchItem> major) {
		this.major = major;
	}
}
