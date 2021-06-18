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
 * @author hejiaming ���豸��װ���APP��Ҫ���� �������ݿ�����DB_NAME,DB_NAME��ϵͳ����·��Ψһ����
 *         ����PACKAGE_NAME�������ú͸���Ӧ�ó������һ���м� �޸�app����
 *         ��gen�ļ����Ҽ����AndroidManifest.xmlһͬ���Ĳ�����Ч
 */
public class Setting {
	public static final String DB_ZIP = "cxht_data.zip";
	public static final String DEFAULT_BASE_PATH= SDCardUtil.getSDPath()+"/"+DB_ZIP;
	public static final String END_DATE = "2020-05-15"; //���ý�ֹ����
	public static final String REG_KEY = "043211"; //����KEY;
	public static final boolean isDemoVer = true; // �Ƿ�Ϊ��ʾ�棬��ʾ�汾��Ҫ��Ӧasset�������ݿ���Դ�ļ�
	public static final int BUFFER_SIZE = 1000000;
	public static final String DB_NAME = "cxdata.db"; // ��������ݿ��ļ���
	public static final String PACKAGE_NAME = "com.gongan.manage";// ����
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // ���ֻ��������ݿ��λ��
	public static final int F_MIN_AGE = 0;// ��ͥ��Ա��������
	public static final int F_MAX_AGE = 100;// ��ͥ��Ա�������
	public static final int MIN_AGE = 20; // ��С����
	public static final int MAX_AGE = 70;// �������
	public static final int MIN_JOB = 1940;// ����μӹ������
	public static final int MAX_JOB = 2050;// ����μӹ������
	public static final int MIN_RD = 1940; // ����μӹ������
	public static final int MAX_RD = 2050; // ����μӹ������
	public static final int EDU_ZZYX = 0; // ��ר�Լ�����
	public static final String EDU_ZZYX_TITLE = "��ר�Լ�����";
	public static final int EDU_DZ = 1; // ��ר
	public static final String EDU_DZ_TITLE = "��ר";
	public static final int EDU_BK = 2; // ����
	public static final String EDU_BK_TITLE = "����";
	public static final int EDU_SS = 3; // ˶ʿ
	public static final String EDU_SS_TITLE = "˶ʿ";
	public static final int EDU_BS = 4; // ��ʿ
	public static final String EDU_BS_TITLE = "��ʿ";
	public static final int SEX_ALL = 0;// ȫ���Ա�
	public static final int SEX_MAN = 1;// ����
	public static final int SEX_WOMAN = 2;// Ů��
	public static final int NATION_ALL = 0;// ȫ������
	public static final int NATION_HZ = 1;// ����
	public static final int NATION_SSMZ = 2;// ��������
	public static final int POLITICS_ALL = 0;// ȫ��
	public static final int POLITICS_DY = 1;// �й���Ա
	public static final int POLITICS_FDY = 2;// ���й���Ա
	public static final int DEPTH_ALL = 0;// ȫ��
	public static final int DEPTH_ZZ = 1;// �ش���ְ
	public static final int DEPTH_FZ = 2;// �ش���ְ
	public static final String STAT_SEX = "sex";// select count(*) as num,sex
												// from t_user group by sex
	public static final String STAT_AGE = "age"; // �����ʾ
	public static final String STAT_JOB_YEAR = "job_year"; // ����ְʱ��
	public static final String STAT_DEPTH_YEAR = "depth_year";// ����ְ��ʱ��
	public static final String STAT_NATION = "nation";// ����
	public static final String STAT_EDU = "highest_edu";// ���ѧ��
	public static final String STAT_DEFORE_EDU = "before_education";// ȫ�������ѧ��
    public static final String STAT_JOB_NAME = "job_name";//�����Q
	public static final String STAT_DEPTH = "depth";// ְ�񼶱�
	public static final String STAT_POSITION = "position";// ְ��
	public static final String STAT_GROUP = "group_id";// ��֯ID
	public static final String STAT_LATEST_EDU = "latest_education";
	public static final String STAT_HIGHEST_DEGREE = "highest_degree"; // ���ѧλ
	public static final String STAT_PRE_JOB_TIME = "pre_job_time";// ����ְʱ��
	public static final String STAT_PRE_DEPTH_TIME = "pre_depth_time";// ����ְ��ʱ��
	public static final int HISTORY_MAX = 500;// ��ʷ��¼����ٽ�ֵ
	public static final String INIT_LOGIN_PASS = "6550597";// ��ʼ����
	public static final String STAT_SEX_TITLE = "��Ա�Ա�ͳ��";
	public static final String STAT_AGE_TITLE = "��Ա����ͳ��";
	public static final String STAT_NATION_TITLE = "��Ա����ͳ��";
	public static final String STAT_DEDU_TITLE = "��Աȫ����ѧ��ͳ��";
	public static final String STAT_DEPTH_TITLE = "��Ա���ְ��ͳ��";
	public static final String STAT_LEDU_TITLE = "��Ա��ְѧ��ͳ��";
	public static final String STAT_POLITICS_TITLE = "��Ա����ͳ��";
	public static final String STAT_POSITION_TITLE = "��Աְ��ͳ��";
	public static final String STAT_JOBTIME_TITLE = "��Ա����ְʱ��ͳ��";
	public static final String STAT_DEPTHTIME_TITLE = "��Ա����ְ��ʱ��ͳ��";
	public static final String STAT_JOBNAME_TITLE = "��Ա����ְ��ͳ��";
	public static final String STAT_GROUP_TITLE = "��Ա��֯ͳ��";
	public static final String AGE_GROUP_1 = "35�꼰����";
	public static final String AGE_GROUP_2 = "36��-40��";
	public static final String AGE_GROUP_3 = "41��-45��";
	public static final String AGE_GROUP_4 = "46��-50��";
	public static final String AGE_GROUP_5 = "51��-55��";
	public static final String AGE_GROUP_6 = "56��-60��";
	public static final String YEAR_GROUP_1 = "4�꼰����";
	public static final String YEAR_GROUP_2 = "5-9��";
	public static final String YEAR_GROUP_3 = "10�꼰����";
	public static final String DB_TIME_FORMAT = "yyyy-MM"; // ���ݿ�ʱ���ʽ
	public static final String SH_TIME_FORMAT = "yyyy.MM";
	public static final String DB_LONG_DATE_FORMAT= "yyyy-MM-dd";//�����ڸ�ʽ
	public static final String SH_LONG_DATE_FORMAT="yyyy.MM.dd";//�����ڸ�ʽ

	// ----------------�°�����------------------------------------------------
	public static final String TABLE_ZS_TITLE = "ȫ����������";
	public static final String TABLE_DS_TITLE = "���ݹ�����";
	public static final String TABLE_SDS_TITLE = "�Ե��й�����";
	public static final String TABLE_XS_TITLE = "�ؼ�������";
	public static final int MAX_IMPORT_DATE = 90; //�����ļ���������
    public static final String USER_CODE = "360102196401064812";// �����쵼��Ϣ���֤����
    //----------------2020��İ���� t_value_code�ֵ��������--------------------
	public  static final String VALUE_CODE_DICT_ID_TA = "TA"; //�����������
	public  static final String VALUE_CODE_DICT_ID_AR = "AR"; //�ͽ�������
	public  static final String VALUE_CODE_DICT_ID_XI = "XI"; //���α������
	public  static final String VALUE_CODE_DICT_ID_EI = "EI"; //��ȿ��˱������
	public  static final String VALUE_CODE_DICT_ID_FD = "FD"; //ִ���ʸ�������
	public  static final String VALUE_CODE_DICT_ID_YJ = "YJ"; //ְ�������
	public  static final String APP_DATE="2021��4��";
	public  static final String USER_TYPE_LD="��ְ�쵼";
	public  static final String USER_TYPE_FLD="��ְ���쵼";
    //----------------------------------------------------------------------------

	/**
	 * ��ȡ��������б�
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
	 * ��ȡ��������б�
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
	 * ��ȡ��ʷ��¼��������
	 * 
	 * @param type
	 * @return
	 */
	public static String getHistoryTypeName(int type) {
		String result = null;
		switch (type) {
		case 1:
			result = "��Ա��ѯ";
			break;
		case 2:
			result = "��֯��ѯ";
			break;
		}
		return result;

	}

	/**
	 * ��ȡְ��/��ְ ʱ��ζ���
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
	 * ��ȡ����ζ����б�
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
		groupQuery.setTitle("���Ų�ѯ");
		groupQuery.setImage(R.drawable.zzcx);
		groupQuery.setNavi(GroupSearchActivity.class);
		groupQuery.setSort(1);
		list.add(groupQuery);

		NaviClass userQuery = new NaviClass();
		userQuery.setTitle("ɸѡ��ѯ");
		userQuery.setImage(R.drawable.rycx);
		userQuery.setNavi(UserSearchActivity.class);
		userQuery.setSort(2);
		list.add(userQuery);

		NaviClass all = new NaviClass();
		all.setTitle("ͳ�Ʒ���");
		all.setImage(R.drawable.ztfx);
		all.setNavi(StatisticsClassActivity.class);
		all.setSort(3);
		list.add(all);

		NaviClass userColl = new NaviClass();
		userColl.setTitle("��Ա�Ա�");
		userColl.setImage(R.drawable.rysc);
		userColl.setNavi(UserCompareActivity.class);
		userColl.setSort(4);
		list.add(userColl);

		NaviClass groupNumber = new NaviClass();
		groupNumber.setTitle("����ְ��");
		groupNumber.setImage(R.drawable.rycz);
		groupNumber.setNavi(GroupNumberTreeActivity.class);
		groupNumber.setSort(5);
		list.add(groupNumber);

		NaviClass sysSet = new NaviClass();
		sysSet.setTitle("ϵͳ����");
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
	//ְ���б����
    public static  List<CheckBean> getQualification(Context context){
		List<String> pol = TableDao.getQualification(context);
		return getCheckList(pol);
	}
	public static List<CheckBean> getPolitical(Context context) {

		List<String> pol = TableDao.getPolitical(context);
		return getCheckList(pol);
	}

	/**
	 * ��ȡ״̬�б�
	 * @return
	 */
    public static List<CheckBean> getState(Context context){
		List<String> data = TableDao.getState(context);
		return getCheckList(data);
	}
	/**
	 * �������
	 * @return
	 */
	public static List<CheckBean> getAssessYear(Context context){
		List<String> data = TableDao.getAssessYear(context);
		return getCheckList(data);
	}
	/**
	 * ���˽��
	 * @return
	 */
	public static List<CheckBean> getAssessResult(Context context){
		List<String> data = TableDao.getAssessResult(context);
		return getCheckList(data);
	}
	/**
	 * �ͽ�����
	 * @return
	 */
	public static List<CheckBean> getPunishType(Context context){
		List<String> data = TableDao.getPunishType(context);
		return getCheckList(data);
	}
	/**
	 * ִ���ʸ�
	 * @return
	 */
	public static List<CheckBean> getMarshals(Context context){
		List<String> data = TableDao.getMarshals(context);
		return getCheckList(data);
	}
	/**
	 * ���ζ����б�
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
		list.add(new CheckBean("ѧʿ", false));
		list.add(new CheckBean("˶ʿ", false));
		list.add(new CheckBean("��ʿ", false));
		return list;
	}

	public static List<CheckBean> getAgeLimit() {
		List<CheckBean> list = new ArrayList<CheckBean>();
		list.add(new CheckBean("1��", false));
		list.add(new CheckBean("2��", false));
		list.add(new CheckBean("3��", false));
		list.add(new CheckBean("4��", false));
		list.add(new CheckBean("5��", false));
		list.add(new CheckBean("6��", false));
		list.add(new CheckBean("7��", false));
		list.add(new CheckBean("8��", false));
		list.add(new CheckBean("9��", false));
		list.add(new CheckBean("10��", false));
		list.add(new CheckBean("11��", false));
		list.add(new CheckBean("12��", false));
		list.add(new CheckBean("13��", false));
		list.add(new CheckBean("14��", false));
		list.add(new CheckBean("15�꼰����", false));

		return list;
	}

	public static List<CheckBean> getNation() {
		List<CheckBean> list = new ArrayList<CheckBean>();
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("�ɹ���", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("ά�����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("׳��", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("��������", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("���", false));
		list.add(new CheckBean("��ɽ��", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("ˮ��", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("�¶�������", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("���Ӷ���", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("Ǽ��", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("ë����", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("��������", false));
		list.add(new CheckBean("ŭ��", false));
		list.add(new CheckBean("���α����", false));
		list.add(new CheckBean("����˹��", false));
		list.add(new CheckBean("���¿���", false));
		list.add(new CheckBean("�°���", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("ԣ����", false));
		list.add(new CheckBean("����", false));
		list.add(new CheckBean("��������", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("���״���", false));
		list.add(new CheckBean("������", false));
		list.add(new CheckBean("�Ű���", false));
		list.add(new CheckBean("�����", false));
		list.add(new CheckBean("��ŵ��", false));
		return list;

	}

	public static List<CheckBean> getPositionAttr(Context context) {

		String sql = "select DISTINCT attr from t_pre_depth where attr <>''";
		return TableDao.getTableCheckBean(context, sql, "attr");
	}

	/**
	 * ȫ����ѧ�������б�
	 * 
	 * @param context
	 * @return
	 */
	public static List<TreeNodeCheck> getFullEduTree(Context context) {
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		String sql = "";
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "ȫѡ", false);
		list.add(root);
		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "�о���ѧ��",
				false);
		list.add(yjs);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%�о���%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),yjsEduTitle());
		list.addAll(yjs_list);
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��ѧѧ��", false);
		list.add(dx);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%��ѧ%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��רѧ��", false);
		list.add(dz);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%��ר%' or before_education like '%����%' or before_education like '%ר��%'";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),dzEduTitle());
		list.addAll(dz_list);
		
		TreeNodeCheck zz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��ר������ѧ��",
				false);
		list.add(zz);
		sql = "select DISTINCT before_education from t_user where before_education<>'' and before_education like '%��ר%' "
				+ "or before_education like '%����%' or before_education like '%����%' or before_education like '%Сѧ%' or before_education like '%����ѧУ%'";
		List<TreeNodeCheck> zz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"before_education"),zzyxEduTitle());
		list.addAll(zz_list);
		return list;
	}

	/**
	 * ��ְѧ�������б�
	 * 
	 * @param context
	 * @return
	 */
	public static List<TreeNodeCheck> getJobEduTree(Context context) {
		List<TreeNodeCheck> list = new ArrayList<TreeNodeCheck>();
		String sql = "";
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "ȫѡ", false);
		list.add(root);
	

		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "�о���ѧ��",
				false);
		list.add(yjs);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%�о���%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),yjsEduTitle());
		list.addAll(yjs_list);
		
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��ѧѧ��", false);
		list.add(dx);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%��ѧ%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��רѧ��", false);
		list.add(dz);
		sql = "select DISTINCT latest_education from t_user where latest_education<>'' and latest_education like '%��ר%' ";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"latest_education"),dzEduTitle());
		list.addAll(dz_list);

		return list;
	}
	/**
	 * ����
	 * 
	 * @param srb
	 *            ��Ҫ����ļ���
	 * @return �����ļ���
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
	 * ���Ƽ���
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
		TreeNodeCheck root = new TreeNodeCheck("1", "0", "ȫѡ", false);
		list.add(root);
		// sql =
		// "select DISTINCT highest_edu from t_user where highest_edu <>''";
		// list.addAll(TableDao.getFullEduTree(context, sql,1,"highest_edu"));

		TreeNodeCheck yjs = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "�о���ѧ��",
				false);
		list.add(yjs);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%�о���%' ";
		List<TreeNodeCheck> yjs_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),yjsEduTitle());
		list.addAll(yjs_list);
		TreeNodeCheck dx = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��ѧѧ��", false);
		list.add(dx);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%��ѧ%' ";
		List<TreeNodeCheck> dx_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),dxEduTitle());
		list.addAll(dx_list);
		
		TreeNodeCheck dz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��רѧ��", false);
		list.add(dz);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%��ר%' or highest_edu like '%����%' or highest_edu like '%ר��%'";
		List<TreeNodeCheck> dz_list = eduListSort(TableDao.getFullEduTree(context, sql, list.size(),
				"highest_edu"),dzEduTitle());
		list.addAll(dz_list);
		
		TreeNodeCheck zz = new TreeNodeCheck(String.valueOf(list.size() + 1), "1", "��ר������ѧ��",
				false);
		list.add(zz);
		sql = "select DISTINCT highest_edu from t_user where highest_edu<>'' and highest_edu like '%��ר%' "
				+ "or highest_edu like '%����%' or highest_edu like '%����%' or highest_edu like '%Сѧ%' or highest_edu like '%����ѧУ%'";
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
			// װ�����д���ļ�
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
			// װ�����д���ļ�
			table = new TreeOrgCkTable(mDatas);
			FileUtil.writeOrgZsTable(context, table);
		}
		return mDatas;
	}

	/**
	 * ��ȡѧ����ǩ�б�
	 * 
	 * @return
	 */
	public static List<String> getEduTitle() {
		List<String> list = new ArrayList<String>();
		list.add("�о���");
		list.add("����");
		list.add("��ר");
		list.add("��ר������");
		return list;
	}
	public static List<String> yjsEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("��ʿ�о���");
		list.add("˶ʿ�о���");
		list.add("���뵳У�о���");
		list.add("ʡ�������У�ί��У�о���");
		return list;
	}
	public static List<String> dxEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("��ѧ");
		list.add("���뵳У��ѧ");
		list.add("ʡ�������У�ί��У��ѧ");
		return list;
	}
	public static List<String> dzEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("��ר");
		list.add("���뵳У��ר");
		list.add("ʡ�������У�ί��У��ר");
		return list;
	}
	public static List<String> zzyxEduTitle(){
		List<String> list = new ArrayList<String>();
		list.add("��ר");
		list.add("����");
		list.add("����");
		list.add("Сѧ");
		return list;
	}
    public static List<Item> sortCriteria(){
		List<Item> list = new ArrayList <>();
		list.add(new Item("��������","birth_date"));
	    list.add(new Item("����ʱ��","job_time"));
	    list.add(new Item("ְ��ʱ��","pre_job_time"));
	    list.add(new Item("ְ��ʱ��","pre_depth_time"));
	   list.add(new Item("�뵳ʱ��","rd_time"));
		return list;
   }
}
