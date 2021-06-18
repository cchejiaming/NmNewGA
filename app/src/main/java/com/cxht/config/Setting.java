package com.cxht.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;

import com.cxht.app.GroupNumberTreeActivity;
import com.cxht.app.GroupSearchActivity;
import com.cxht.app.StatisticsClassActivity;
import com.cxht.app.SystemSetActivity;
import com.cxht.app.UserCompareActivity;
import com.cxht.app.UserSearchActivity;
import com.cxht.bean.AgeGroupBean;
import com.cxht.bean.CheckBean;
import com.cxht.bean.Item;
import com.cxht.bean.StatColumnBean;
import com.cxht.dao.DepthDao;
import com.cxht.dao.TableDao;
import com.cxht.entity.NaviClass;
import com.cxht.unit.FileUtil;
import com.cxht.unit.SDCardUtil;
import com.cxht.unit.StringUtil;

import com.gongan.manage.R;
import com.gov.tree.TreeNodeCheck;
import com.gov.tree.TreeOrgCkTable;

/**
 * 
 * @author hejiaming 单设备安装多个APP需要设置 更改数据库名称DB_NAME,DB_NAME是系统搜索路径唯一条件
 *         更改PACKAGE_NAME包名设置和更改应用程序包名一致切记 修改app包名
 *         在gen文件夹右键需和AndroidManifest.xml一同更改才能生效
 */
public class Setting {
	public static final String DB_ZIP = "cxht_data.zip";
	public static final String DEFAULT_BASE_PATH= SDCardUtil.getSDPath()+"/"+DB_ZIP;
	public static final String END_DATE = "2020-05-15"; //试用截止日期
	public static final String REG_KEY = "043211"; //激活KEY;
	public static final boolean isDemoVer = true; // 是否为演示版，演示版本需要对应asset引入数据库资源文件
	public static final int BUFFER_SIZE = 1000000;
	public static final String DB_NAME = "cxdata.db"; // 保存的数据库文件名
	public static final String PACKAGE_NAME = "com.gongan.manage";// 包名
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // 在手机里存放数据库的位置
	public static final int F_MIN_AGE = 0;// 家庭成员最新年龄
	public static final int F_MAX_AGE = 100;// 家庭成员最大年龄
	public static final int MIN_AGE = 20; // 最小年龄
	public static final int MAX_AGE = 70;// 最大年龄
	public static final int MIN_JOB = 1940;// 最早参加工作年份
	public static final int MAX_JOB = 2050;// 最晚参加工作年份
	public static final int MIN_RD = 1940; // 最早参加工作年份
	public static final int MAX_RD = 2050; // 最晚参加工作年份
	public static final int EDU_ZZYX = 0; // 中专以及以下
	public static final String EDU_ZZYX_TITLE = "中专以及以下";
	public static final int EDU_DZ = 1; // 大专
	public static final String EDU_DZ_TITLE = "大专";
	public static final int EDU_BK = 2; // 本科
	public static final String EDU_BK_TITLE = "本科";
	public static final int EDU_SS = 3; // 硕士
	public static final String EDU_SS_TITLE = "硕士";
	public static final int EDU_BS = 4; // 博士
	public static final String EDU_BS_TITLE = "博士";
	public static final int SEX_ALL = 0;// 全部性别
	public static final int SEX_MAN = 1;// 男性
	public static final int SEX_WOMAN = 2;// 女性
	public static final int NATION_ALL = 0;// 全部名族
	public static final int NATION_HZ = 1;// 汉族
	public static final int NATION_SSMZ = 2;// 少数名族
	public static final int POLITICS_ALL = 0;// 全部
	public static final int POLITICS_DY = 1;// 中共党员
	public static final int POLITICS_FDY = 2;// 非中共党员
	public static final int DEPTH_ALL = 0;// 全部
	public static final int DEPTH_ZZ = 1;// 县处正职
	public static final int DEPTH_FZ = 2;// 县处副职
	public static final String STAT_SEX = "sex";// select count(*) as num,sex
												// from t_user group by sex
	public static final String STAT_AGE = "age"; // 年龄标示
	public static final String STAT_JOB_YEAR = "job_year"; // 任现职时间
	public static final String STAT_DEPTH_YEAR = "depth_year";// 任现职级时间
	public static final String STAT_NATION = "nation";// 民族
	public static final String STAT_EDU = "highest_edu";// 最高学历
	public static final String STAT_DEFORE_EDU = "before_education";// 全日制最高学历
    public static final String STAT_JOB_NAME = "job_name";//職務名稱
	public static final String STAT_DEPTH = "depth";// 职务级别
	public static final String STAT_POSITION = "position";// 职务
	public static final String STAT_GROUP = "group_id";// 组织ID
	public static final String STAT_LATEST_EDU = "latest_education";
	public static final String STAT_HIGHEST_DEGREE = "highest_degree"; // 最高学位
	public static final String STAT_PRE_JOB_TIME = "pre_job_time";// 现任职时间
	public static final String STAT_PRE_DEPTH_TIME = "pre_depth_time";// 现任职级时间
	public static final int HISTORY_MAX = 500;// 历史记录最大临界值
	public static final String INIT_LOGIN_PASS = "6550597";// 初始密码
	public static final String STAT_SEX_TITLE = "人员性别统计";
	public static final String STAT_AGE_TITLE = "人员年龄统计";
	public static final String STAT_NATION_TITLE = "人员民族统计";
	public static final String STAT_DEDU_TITLE = "人员全日制学历统计";
	public static final String STAT_DEPTH_TITLE = "人员最高职级统计";
	public static final String STAT_LEDU_TITLE = "人员在职学历统计";
	public static final String STAT_POLITICS_TITLE = "人员党派统计";
	public static final String STAT_POSITION_TITLE = "人员职务统计";
	public static final String STAT_JOBTIME_TITLE = "人员现任职时间统计";
	public static final String STAT_DEPTHTIME_TITLE = "人员现任职级时间统计";
	public static final String STAT_JOBNAME_TITLE = "人员现任职务统计";
	public static final String STAT_GROUP_TITLE = "人员组织统计";
	public static final String AGE_GROUP_1 = "35岁及以下";
	public static final String AGE_GROUP_2 = "36岁-40岁";
	public static final String AGE_GROUP_3 = "41岁-45岁";
	public static final String AGE_GROUP_4 = "46岁-50岁";
	public static final String AGE_GROUP_5 = "51岁-55岁";
	public static final String AGE_GROUP_6 = "56岁-60岁";
	public static final String YEAR_GROUP_1 = "4年及以下";
	public static final String YEAR_GROUP_2 = "5-9年";
	public static final String YEAR_GROUP_3 = "10年及以上";
	public static final String DB_TIME_FORMAT = "yyyy-MM"; // 数据库时间格式
	public static final String SH_TIME_FORMAT = "yyyy.MM";
	public static final String DB_LONG_DATE_FORMAT= "yyyy-MM-dd";//长日期格式
	public static final String SH_LONG_DATE_FORMAT="yyyy.MM.dd";//长日期格式

	// ----------------新版增加------------------------------------------------
	public static final String TABLE_ZS_TITLE = "全区公安机关";
	public static final String TABLE_DS_TITLE = "市州公安局";
	public static final String TABLE_SDS_TITLE = "试点市公安局";
	public static final String TABLE_XS_TITLE = "县级公安局";
	public static final int MAX_IMPORT_DATE = 90; //数据文件生命周期
    public static final String USER_CODE = "360102196401064812";// 隐藏领导信息身份证号码
    //----------------2020年改版添加 t_value_code字典表编码分类--------------------
	public  static final String VALUE_CODE_DICT_ID_TA = "TA"; //奖励编码分类
	public  static final String VALUE_CODE_DICT_ID_AR = "AR"; //惩戒编码分类
	public  static final String VALUE_CODE_DICT_ID_XI = "XI"; //警衔编码分类
	public  static final String VALUE_CODE_DICT_ID_EI = "EI"; //年度考核编码分类
	public  static final String VALUE_CODE_DICT_ID_FD = "FD"; //执法资格编码分类
	public  static final String VALUE_CODE_DICT_ID_YJ = "YJ"; //职称码分类
	public  static final String APP_DATE="2021年4月";
	public  static final String USER_TYPE_LD="在职领导";
	public  static final String USER_TYPE_FLD="在职非领导";
    //----------------------------------------------------------------------------

	/**
	 * 获取总体分析列表
	 * 
	 * @return
	 */
	public static List<StatColumnBean> getStatisticsList() {

		List<StatColumnBean> list = new ArrayList<StatColumnBean>();

		StatColumnBean item1 = new StatColumnBean(0, STAT_SEX_TITLE);
		list.add(item1);
		StatColumnBean item2 = new StatColumnBean(1, STAT_AGE_TITLE);
		list.add(item2);
		StatColumnBean item3 = new StatColumnBean(2, STAT_NATION_TITLE);
		list.add(item3);
		StatColumnBean item4 = new StatColumnBean(3, STAT_DEDU_TITLE);
		list.add(item4);
		StatColumnBean item5 = new StatColumnBean(4, STAT_LEDU_TITLE);
		list.add(item5);
		StatColumnBean item6 = new StatColumnBean(5, STAT_DEPTH_TITLE);
		list.add(item6);
		StatColumnBean item7 = new StatColumnBean(6, STAT_JOBTIME_TITLE);
		list.add(item7);
		StatColumnBean item8 = new StatColumnBean(7, STAT_DEPTHTIME_TITLE);
		list.add(item8);
		StatColumnBean item9 = new StatColumnBean(8, STAT_JOBNAME_TITLE);
		list.add(item9);
		return list;
	}

	/**
	 * 获取总体分析列表
	 * 
	 * @return
	 */
	public static List<StatColumnBean> getTableStatisticsList() {

		List<StatColumnBean> list = new ArrayList<StatColumnBean>();

		StatColumnBean item1 = new StatColumnBean(0, TABLE_ZS_TITLE);
		list.add(item1);
		StatColumnBean item2 = new StatColumnBean(1, TABLE_DS_TITLE);
		list.add(item2);
		StatColumnBean item3 = new StatColumnBean(2, TABLE_SDS_TITLE);
		list.add(item3);
		StatColumnBean item4 = new StatColumnBean(3, TABLE_XS_TITLE);
		list.add(item4);

		return list;
	}

	/**
	 * 获取历史记录操作名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getHistoryTypeName(int type) {
		String result = null;
		switch (type) {
		case 1:
			result = "人员查询";
			break;
		case 2:
			result = "组织查询";
			break;
		}
		return result;

	}

	/**
	 * 获取职级/任职 时间段对象
	 * 
	 * @return
	 */
	public static List<AgeGroupBean> getYearGroupList() {

		List<AgeGroupBean> list = new ArrayList<AgeGroupBean>();
		AgeGroupBean agb1 = new AgeGroupBean();

		agb1.setTitle(YEAR_GROUP_1);
		agb1.setStartYear(StringUtil.getNowYM((4 * 12 + 12) * (-1)));
		agb1.setOverYear(StringUtil.getNowYM(0));
		list.add(agb1);
		AgeGroupBean agb2 = new AgeGroupBean();
		agb2.setTitle(YEAR_GROUP_2);
		agb2.setStartYear(StringUtil.getNowYM((9 * 12 + 12) * (-1)));
		agb2.setOverYear(StringUtil.getNowYM((5 * 12 ) * (-1)));
		list.add(agb2);
		AgeGroupBean agb3 = new AgeGroupBean();
		agb3.setTitle(YEAR_GROUP_3);
		agb3.setStartYear(StringUtil.getNowYM((55 * 12 + 12) * (-1)));
		agb3.setOverYear(StringUtil.getNowYM((10 * 12 ) * (-1)));
		list.add(agb3);

		return list;
	}

	/**
	 * 获取年龄段对象列表
	 * 
	 * @return
	 */
	public static List<AgeGroupBean> getAgeGroupList() {
		List<AgeGroupBean> list = new ArrayList<AgeGroupBean>();
		AgeGroupBean agb1 = new AgeGroupBean();
		agb1.setTitle(AGE_GROUP_1);
		agb1.setStartYear(StringUtil.getNowYM((35 * 12 + 12) * (-1)));
		agb1.setOverYear(StringUtil.getNowYM((0 * 12 ) * (-1)));
		list.add(agb1);
		AgeGroupBean agb2 = new AgeGroupBean();
		agb2.setTitle(AGE_GROUP_2);
		agb2.setStartYear(StringUtil.getNowYM((40 * 12 + 12) * (-1)));
		agb2.setOverYear(StringUtil.getNowYM((36 * 12 ) * (-1)));
		list.add(agb2);
		AgeGroupBean agb3 = new AgeGroupBean();
		agb3.setTitle(AGE_GROUP_3);
		
		agb3.setStartYear(StringUtil.getNowYM((45 * 12 + 12) * (-1)));
		agb3.setOverYear(StringUtil.getNowYM((41 * 12) * (-1)));
		list.add(agb3);
		AgeGroupBean agb4 = new AgeGroupBean();
		agb4.setTitle(AGE_GROUP_4);

		agb4.setStartYear(StringUtil.getNowYM((50 * 12 + 12) * (-1)));
		agb4.setOverYear(StringUtil.getNowYM((46 * 12 ) * (-1)));
		list.add(agb4);
		AgeGroupBean agb5 = new AgeGroupBean();
		agb5.setTitle(AGE_GROUP_5);
		agb5.setStartYear(StringUtil.getNowYM((55 * 12 + 12) * (-1)));
		agb5.setOverYear(StringUtil.getNowYM((51 * 12 ) * (-1)));
		list.add(agb5);
		AgeGroupBean agb6 = new AgeGroupBean();
		agb6.setTitle(AGE_GROUP_6);
		agb6.setStartYear(StringUtil.getNowYM((60 * 12 + 12) * (-1)));
		agb6.setOverYear(StringUtil.getNowYM((56 * 12) * (-1)));
		list.add(agb6);
		return list;
	}


	public static List<NaviClass> createNaviClass() {
		List<NaviClass> list = new ArrayList<NaviClass>();

		NaviClass groupQuery = new NaviClass();
		groupQuery.setTitle("部门查询");
		groupQuery.setImage(R.drawable.zzcx);
		groupQuery.setNavi(GroupSearchActivity.class);
		groupQuery.setSort(1);
		list.add(groupQuery);

		NaviClass userQuery = new NaviClass();
		userQuery.setTitle("筛选查询");
		userQuery.setImage(R.drawable.rycx);
		userQuery.setNavi(UserSearchActivity.class);
		userQuery.setSort(2);
		list.add(userQuery);

		NaviClass all = new NaviClass();
		all.setTitle("统计分析");
		all.setImage(R.drawable.ztfx);
		all.setNavi(StatisticsClassActivity.class);
		all.setSort(3);
		list.add(all);

		NaviClass userColl = new NaviClass();
		userColl.setTitle("人员对比");
		userColl.setImage(R.drawable.rysc);
		userColl.setNavi(UserCompareActivity.class);
		userColl.setSort(4);
		list.add(userColl);

		NaviClass groupNumber = new NaviClass();
		groupNumber.setTitle("部门职数");
		groupNumber.setImage(R.drawable.rycz);
		groupNumber.setNavi(GroupNumberTreeActivity.class);
		groupNumber.setSort(5);
		list.add(groupNumber);

		NaviClass sysSet = new NaviClass();
		sysSet.setTitle("系统设置");
		sysSet.setImage(R.drawable.xtsz);
		sysSet.setNavi(SystemSetActivity.class);
		sysSet.setSort(6);
		list.add(sysSet);

		return list;
	}

	public static List<CheckBean> getFullEdu(Context context) {
		List<String> pol = TableDao.getFullEdu(context);
		return getCheckList(pol);
	}
	//职称列表对象
    public static  List<CheckBean> getQualification(Context context){
		List<String> pol = TableDao.getQualification(context);
		return getCheckList(pol);
	}
	public static List<CheckBean> getPolitical(Context context) {

		List<String> pol = TableDao.getPolitical(context);
		return getCheckList(pol);
	}

	/**
	 * 获取状态列表
	 * @return
	 */
    public static List<CheckBean> getState(Context context){
		List<String> data = TableDao.getState(context);
		return getCheckList(data);
	}
	/**
	 * 考核年份
	 * @return
	 */
	public static List<CheckBean> getAssessYear(Context context){
		List<String> data = TableDao.getAssessYear(context);
		return getCheckList(data);
	}
	/**
	 * 考核结果
	 * @return
	 */
	public static List<CheckBean> getAssessResult(Context context){
		List<String> data = TableDao.getAssessResult(context);
		return getCheckList(data);
	}
	/**
	 * 惩戒类型
	 * @return
	 */
	public static List<CheckBean> getPunishType(Context context){
		List<String> data = TableDao.getPunishType(context);
		return getCheckList(data);
	}
	/**
	 * 执法资格
	 * @return
	 */
	public static List<CheckBean> getMarshals(Context context){
		List<String> data = TableDao.getMarshals(context);
		return getCheckList(data);
	}
	/**
	 * 警衔对象列表
	 * @return
	 */
	public static List<CheckBean> getUserRank(Context context){
		List<String> data = TableDao.getUserRank(context);
		return getCheckList(data);
	}
	public static List<CheckBean> getDepth(Context context) {
		List<String> pre = DepthDao.getDepthList();
		return getCheckList(pre);
	}

	private static List<CheckBean> getCheckList(List<String> pol) {
		List<CheckBean> list = new ArrayList<CheckBean>();
		if (pol != null) {
			for (String p : pol) {
				list.add(new CheckBean(p.toString(), false));
			}
		}
		return list;
	}

	public static List<CheckBean> getFullDegree() {
		List<CheckBean> list = new ArrayList<CheckBean>();
		list.add(new CheckBean("学士", false));
		list.add(new CheckBean("硕士", false));
		list.add(new CheckBean("博士", false));
		return list;
	}

	public static List<CheckBean> getAgeLimit() {
		List<CheckBean> list = new ArrayList<CheckBean>();
		list.add(new CheckBean("1年", false));
		list.add(new CheckBean("2年", false));
		list.add(new CheckBean("3年", false));
		list.add(new CheckBean("4年", false));
		list.add(new CheckBean("5年", false));
		list.add(new CheckBean("6年", false));
		list.add(new CheckBean("7年", false));
		list.add(new CheckBean("8年", false));
		list.add(new CheckBean("9年", false));
		list.add(new CheckBean("10年", false));
		list.add(new CheckBean("11年", false));
		list.add(new CheckBean("12年", false));
		list.add(new CheckBean("13年", false));
		list.add(new CheckBean("14年", false));
		list.add(new CheckBean("15年及以上", false));

		return list;
	}

	public static List<CheckBean> getNation() {
		List<CheckBean> list = new ArrayList<CheckBean>();
		list.add(new CheckBean("汉族", false));
		list.add(new CheckBean("蒙古族", false));
		list.add(new CheckBean("回族", false));
		list.add(new CheckBean("藏族", false));
		list.add(new CheckBean("维吾尔族", false));
		list.add(new CheckBean("苗族", false));
		list.add(new CheckBean("彝族", false));
		list.add(new CheckBean("壮族", false));
		list.add(new CheckBean("布依族", false));
		list.add(new CheckBean("朝鲜族", false));
		list.add(new CheckBean("满族", false));
		list.add(new CheckBean("侗族", false));
		list.add(new CheckBean("瑶族", false));
		list.add(new CheckBean("白族", false));
		list.add(new CheckBean("土家族", false));
		list.add(new CheckBean("哈尼族", false));
		list.add(new CheckBean("哈萨克族", false));
		list.add(new CheckBean("傣族", false));
		list.add(new CheckBean("黎族", false));
		list.add(new CheckBean("僳僳族", false));
		list.add(new CheckBean("畲族", false));
		list.add(new CheckBean("高山族", false));
		list.add(new CheckBean("拉祜族", false));
		list.add(new CheckBean("水族", false));
		list.add(new CheckBean("东乡族", false));
		list.add(new CheckBean("纳西族", false));
		list.add(new CheckBean("景颇族", false));
		list.add(new CheckBean("柯尔克孜族", false));
		list.add(new CheckBean("土族", false));
		list.add(new CheckBean("达斡尔族", false));
		list.add(new CheckBean("仫佬族", false));
		list.add(new CheckBean("羌族", false));
		list.add(new CheckBean("布朗族", false));
		list.add(new CheckBean("撒拉族", false));
		list.add(new CheckBean("毛南族", false));
		list.add(new CheckBean("仡佬族", false));
		list.add(new CheckBean("锡伯族", false));
		list.add(new CheckBean("阿昌族", false));
		list.add(new CheckBean("普米族", false));
		list.add(new CheckBean("塔吉克族", false));
		list.add(new CheckBean("怒族", false));
		list.add(new CheckBean("乌孜别克族", false));
		list.add(new CheckBean("俄罗斯族", false));
		list.add(new CheckBean("鄂温克族", false));
		list.add(new CheckBean("德昂族", false));
		list.add(new CheckBean("保安族", false));
		list.add(new CheckBean("裕固族", false));
		list.add(new CheckBean("京族", false));
		list.add(new CheckBean("塔塔尔族", false));
		list.add(new CheckBean("独龙族", false));
		list.add(new CheckBean("鄂伦春族", false));
		list.add(new CheckBean("赫哲族", false));
		list.add(new CheckBean("门巴族", false));
		list.add(new CheckBean("珞巴族", false));
		list.add(new CheckBean("基诺族", false));
		return list;

	}

	public static List<CheckBean> getPositionAttr(Context context) {

		String sql = "select DISTINCT attr from t_pre_depth where attr <>''";
		return TableDao.getTableCheckBean(context, sql, "attr");
	}

	/**
	 * 全日制学历树形列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<TreeNodeCheck> getFullEduTree(Context context) {
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		String sql = "";
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "全选", false);
		list.add(root);
		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "研究生学历",
				false);
		list.add(yjs);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%研究生%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),yjsEduTitle());
		list.addAll(yjs_list);
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大学学历", false);
		list.add(dx);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%大学%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大专学历", false);
		list.add(dz);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%大专%' or before_education like '%大普%' or before_education like '%专科%'";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),dzEduTitle());
		list.addAll(dz_list);
		
		TreeNodeCheck zz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "中专及以下学历",
				false);
		list.add(zz);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%中专%' "
				+ "or before_education like '%高中%' or before_education like '%初中%' or before_education like '%小学%' or before_education like '%技工学校%'";
		List<TreeNodeCheck> zz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),zzyxEduTitle());
		list.addAll(zz_list);
		return list;
	}

	/**
	 * 在职学历树形列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<TreeNodeCheck> getJobEduTree(Context context) {
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		String sql = "";
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "全选", false);
		list.add(root);
	

		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "研究生学历",
				false);
		list.add(yjs);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%研究生%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),yjsEduTitle());
		list.addAll(yjs_list);
		
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大学学历", false);
		list.add(dx);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%大学%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大专学历", false);
		list.add(dz);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%大专%' ";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),dzEduTitle());
		list.addAll(dz_list);

		return list;
	}
	/**
	 * 排序
	 * 
	 * @param srb
	 *            需要排序的集合
	 * @return 排序后的集合
	 */
	public static List<TreeNodeCheck> eduListSort(List<TreeNodeCheck> srb,List<String> l) {
		List<TreeNodeCheck> list = null;
		if (srb != null) {
			list = new ArrayList<TreeNodeCheck>();
			
			for (String s : l) {
				TreeNodeCheck bean = queryObject(s, srb);
				if (bean != null) {
					list.add(bean);
					srb.remove(bean);
				}
			}
			list.addAll(srb);
		}
		return list;
	}

	/**
	 * 名称检索
	 * @param name
	 * @param list
	 * @return
	 */
	private static TreeNodeCheck queryObject(String name,
			List<TreeNodeCheck> list) {
		TreeNodeCheck srb = null;
		for (TreeNodeCheck s : list) {
			if (s.getName().equals(name)) {
				srb = s;
				break;
			}
		}
		return srb;
	}

	public static List<TreeNodeCheck> getHighestEdu(Context context) {
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		String sql = "";
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "全选", false);
		list.add(root);
		// sql =
		// "select DISTINCT highest_edu from t_user where highest_edu <>''";
		// list.addAll(TableDao.getFullEduTree(context, sql,1,"highest_edu"));

		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "研究生学历",
				false);
		list.add(yjs);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%研究生%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),yjsEduTitle());
		list.addAll(yjs_list);
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大学学历", false);
		list.add(dx);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%大学%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "大专学历", false);
		list.add(dz);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%大专%' or highest_edu like '%大普%' or highest_edu like '%专科%'";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),dzEduTitle());
		list.addAll(dz_list);
		
		TreeNodeCheck zz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "中专及以下学历",
				false);
		list.add(zz);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%中专%' "
				+ "or highest_edu like '%高中%' or highest_edu like '%初中%' or highest_edu like '%小学%' or highest_edu like '%技工学校%'";
		List<TreeNodeCheck> zz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),zzyxEduTitle());
		list.addAll(zz_list);

		return list;
	}

	public static List<TreeNodeCheck> getGroupTree(Context context) {
		ArrayList<TreeNodeCheck> mDatas = null;
		TreeOrgCkTable table = FileUtil.readOrgCkTable(context);
		if (table != null) {
			mDatas = table.getData();
		} else {

			mDatas = TableDao.getCheckGroupList(context);
			// 装入对象写入文件
			table = new TreeOrgCkTable(mDatas);
			FileUtil.writeOrgCkTable(context, table);
		}
		return mDatas;
	}

	public static List<TreeNodeCheck> getGroupTreeZs(Context context) {
		ArrayList<TreeNodeCheck> mDatas = null;
		TreeOrgCkTable table = FileUtil.readOrgZsTable(context);
		if (table != null) {
			mDatas = table.getData();
		} else {

			mDatas = TableDao.getZsCheckGroupList(context);
			// 装入对象写入文件
			table = new TreeOrgCkTable(mDatas);
			FileUtil.writeOrgZsTable(context, table);
		}
		return mDatas;
	}

	/**
	 * 获取学历标签列表
	 * 
	 * @return
	 */
	public static List<String> getEduTitle() {
		List<String> list = new ArrayList<String>();
		list.add("研究生");
		list.add("本科");
		list.add("大专");
		list.add("中专及以下");
		return list;
	}
	public static List<String> yjsEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("博士研究生");
		list.add("硕士研究生");
		list.add("中央党校研究生");
		list.add("省（区、市）委党校研究生");
		return list;
	}
	public static List<String> dxEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("大学");
		list.add("中央党校大学");
		list.add("省（区、市）委党校大学");
		return list;
	}
	public static List<String> dzEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("大专");
		list.add("中央党校大专");
		list.add("省（区、市）委党校大专");
		return list;
	}
	public static List<String> zzyxEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("中专");
		list.add("高中");
		list.add("初中");
		list.add("小学");
		return list;
	}
    public static List<Item> sortCriteria(){
		List<Item> list = new ArrayList <>();
		list.add(new Item("出生年月","birth_date"));
	    list.add(new Item("工作时间","job_time"));
	    list.add(new Item("职务时间","pre_job_time"));
	    list.add(new Item("职级时间","pre_depth_time"));
	   list.add(new Item("入党时间","rd_time"));
		return list;
   }
}
