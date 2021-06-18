package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cxht.adapter.InfoAdapter;
import com.cxht.bean.InfoItem;
import com.cxht.config.Setting;
import com.cxht.dao.AssessDao;
import com.cxht.dao.DegreeDao;
import com.cxht.dao.ExamineDao;
import com.cxht.dao.GunDao;
import com.cxht.dao.JobDao;
import com.cxht.dao.MarshalsDao;
import com.cxht.dao.PreDepthDao;
import com.cxht.dao.PunishDao;
import com.cxht.dao.QualificationDao;
import com.cxht.dao.RankDao;
import com.cxht.dao.TrainDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.Assess;
import com.cxht.entity.Degree;
import com.cxht.entity.Examine;
import com.cxht.entity.Gun;
import com.cxht.entity.Job;
import com.cxht.entity.Marshals;
import com.cxht.entity.PreDepth;
import com.cxht.entity.Punish;
import com.cxht.entity.Qualification;
import com.cxht.entity.Rank;
import com.cxht.entity.Train;
import com.cxht.entity.User;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 人员详细信息Fragment页
 */
public class UserDetail extends Fragment {

	private int user_id;
	private Context context;
	private View view;
	private User user;// 用户对象
	private List<Assess> assessDataList; // 考核列表数据
	private List<Examine> examineDataList;// 奖惩列表数据
	private List<Punish> punishDataList;//惩戒列表数据
	private ListView basicListView; // 视图列表
	private List<InfoItem> basicData; //绑定数据源
	private InfoAdapter adapter; //列表适配器

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	/**
	 * 创建新实例
	 *
	 * @return
	 */

	public static UserDetail newInstance(int userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("UserId", userId);
		UserDetail fragment = new UserDetail();
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.user_id = args != null ? args.getInt("UserId", 0) : 0;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_detail,
				null);
		basicListView = (ListView) view.findViewById(R.id.basic_ListView);
		initData();
		return view;
	}

	private void initData() {
		user = UserDao.queryUser(user_id);
		String user_code = UserDao.getUserCode(user_id);
		assessDataList = AssessDao.getAssessList(user_code);
		examineDataList = ExamineDao.getExamineList(user_code);
		punishDataList = PunishDao.getPunishList(user_code);
		if (user != null) {
			initListData(user);
			adapter = new InfoAdapter(context, basicData);
			basicListView.setAdapter(adapter);

		}
	}
	private static String marshalsToString(Marshals mar) {
		String ret = "";

			if (mar!=null) {
				ret += mar.getLevel() ;

				if (mar.getStart_time()!=null){
					String sTime =StringUtil.toShowData(mar.getStart_time());
					String sYear = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret +="\n"+sTime+sYear;
				}
			}


		return ret;
	}
	private static String gunToString(Gun gun){
		String ret = "";

		if (gun!=null) {
			ret += gun.getLicence() ;

			if (gun.getLssue_date()!=null){
				String sTime =StringUtil.toShowData(gun.getLssue_date());
				String sYear = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
				ret +="\n"+sTime+sYear;
			}
		}

		return ret;
	}
	private static String preDepth3ToString(List<PreDepth> list) {
		String ret = "";
		if (list != null) {
			for (PreDepth pd : list) {

				if (pd.getRatifyTime()!=null){
					String sTime =StringUtil.toShowData(pd.getRatifyTime());
					String sYear = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret +=sTime+sYear;
				}
				ret += "\n";
			}
			ret = ret.substring(0,ret.length() -1);
		}
		return ret;
	}
	private static String preDepth2ToString(List<PreDepth> list) {
		String ret = "";
		if (list != null) {
			for (PreDepth pd : list) {

				if (pd.getLxTime()!=null){
					String sTime =StringUtil.toShowData(pd.getLxTime());
					String sYear = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret +=sTime+sYear;
				}
				ret += "\n";
			}
			ret = ret.substring(0,ret.length() -1);
		}
		return ret;
	}
	private static String preDepthToString(List<PreDepth> list) {
		String ret = "";
		if (list != null) {
			for (PreDepth pd : list) {
				ret += pd.getName() ;
				if (pd.getAttr()!=null){
					ret+="[" + pd.getAttr() + "] ";
				}
				if (pd.getStartTime()!=null){
					String sTime =StringUtil.toShowData(pd.getStartTime());
					String sYear = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret +="\n"+sTime+sYear;
				}
				ret += "\n";
			}
			ret = ret.substring(0,ret.length() -1);
		}
		return ret;
	}
    private static String job2ToString(List<Job> list){
		String ret = "";
		if (list != null) {

			for (Job j : list) {
				String full = "";
				//if (j.getGroup_name() != null) {
					//full = j.getGroup_name();
				//}
				if(j.getJob_name()!= null){
					ret += full+j.getJob_name();
				}
				if (j.getLx_time()!= null)
				{
					String sTime = StringUtil.toShowData(j.getLx_time());
					String tm = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret += "\n" +sTime+ tm ;
				}
				ret +="\n";
			}
			if (ret.length() > 1)
				ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}
	private static String jobToString(List<Job> list) {
		String ret = "";
		if (list != null) {

			for (Job j : list) {
				String full = "";
				if (j.getGroup_name() != null) {
					full = j.getGroup_name();
				}
				if(j.getJob_name()!= null){
					ret += full+j.getJob_name();
				}
             /**   ret += user.getPosition(); //full + j.getJob_name();// + " " + j.getDepth();
				if (j.getPosition_attr() != null) {
					ret += " [" + j.getPosition_attr() + "] ";
				}
				**/
				if (j.getStart_time()!= null)
				{
					String sTime = StringUtil.toShowData(j.getStart_time());
					String tm = StringUtil.getYearMonthString(sTime, Setting.SH_TIME_FORMAT);
					ret += "\n" +sTime+ tm ;
				}
				ret +="\n";
			}
			if (ret.length() > 1)
				ret = ret.substring(0, ret.length() - 1);
		}

		return ret;
	}
    private String delNull(String st){
    	String ret= "";
    	if (st!= null){
    		ret = st;
    		ret = ret.replace("null", "");
    	}
    	return ret;
    }
	private void initListData(User user) {

		basicData = new ArrayList<InfoItem>();
		InfoItem itemTitle = new InfoItem("基本信息", null);
		itemTitle.setFlag(0);
		basicData.add(itemTitle);



		InfoItem user_name = new InfoItem("姓名", user.getUser_name());
		user_name.setMore(false);
		basicData.add(user_name);
		InfoItem sex = new InfoItem("性别", user.getSex());
		sex.setMore(false);
		basicData.add(sex);
		InfoItem birth_date = new InfoItem("出生年月", StringUtil.toShowData(user
				.getBirth_date())
				+ "["
				+ StringUtil.getYearNum(user.getBirth_date(),
						Setting.DB_TIME_FORMAT) + "岁]");
		birth_date.setMore(false);
		basicData.add(birth_date);
		InfoItem nation = new InfoItem("民族", user.getNation());
		nation.setMore(false);
		basicData.add(nation);
		InfoItem nativeplace = new InfoItem("籍贯", user.getNativeplace());
		nativeplace.setMore(false);
		basicData.add(nativeplace);
		InfoItem birthplace = new InfoItem("出生地", user.getBirthplace());
		birthplace.setMore(false);
		basicData.add(birthplace);
		String rdTime = StringUtil.toShowData(user.getRd_time());
		String rdYear =  "["+StringUtil.getYearNum(rdTime, Setting.SH_TIME_FORMAT)+"年]";
		InfoItem rd_time = new InfoItem("入党时间", rdTime+ rdYear);
		rd_time.setMore(false);
		basicData.add(rd_time);
		
		 String jobTime = StringUtil.toShowData(user.getJob_time());
		 String jobYear = "["+StringUtil.getYearNum(jobTime, Setting.SH_TIME_FORMAT)+"年]";
		InfoItem job_time = new InfoItem("参加工作时间",jobTime+jobYear);
		job_time.setMore(false);
		basicData.add(job_time);
		InfoItem status = new InfoItem("健康状况", user.getStatus());
		status.setMore(false);
		basicData.add(status);
		//职务信息集
		List<Job> jobs = JobDao.getJobs(user.getUser_code());
		InfoItem position1 = new InfoItem("现任职务及时间",jobToString(jobs));
		basicData.add(position1);
		InfoItem position2 = new InfoItem("连续任该职务起算时间",job2ToString(jobs));
		basicData.add(position2);
		//职级信息集
		List<PreDepth> preDepths = PreDepthDao.getPreDepth(user.getUser_code());
		InfoItem depth1 = new InfoItem("现任职级及时间",	preDepthToString(preDepths));
		depth1.setMore(false);
		basicData.add(depth1);
		InfoItem depth2 = new InfoItem("任相当层次职务职级起算时间",preDepth2ToString(preDepths));
		depth2.setMore(false);
		basicData.add(depth2);
		InfoItem depth3 = new InfoItem("职务层次批准时间",preDepth3ToString(preDepths));
		depth3.setMore(false);
		basicData.add(depth3);
		//职称信息
		List<Qualification> quas = QualificationDao.getQualificationList(user.getUser_code());
		CreateQualificationItem(quas,basicData);
		Rank userRank =RankDao.getRank(user.getUser_code());
	    if (userRank!=null){
			InfoItem rank = new InfoItem("警衔", RankDao.getRank(user.getUser_code()).getRank_name());
			basicData.add(rank);
		}
		InfoItem user_num = new InfoItem("警号", user.getNumber());
		user_num.setMore(false);
		basicData.add(user_num);

		String marInfo = marshalsToString(MarshalsDao.getMarshals(user.getUser_code()));
		if (marInfo!=null && marInfo.length()>0){
			InfoItem marshals = new InfoItem("执法资格",marInfo);
			marshals.setMore(false);
			basicData.add(marshals);
		}

		String gunInfo = gunToString(GunDao.getGun(user.getUser_code()));
		if (gunInfo!=null && gunInfo.length()>0){
			InfoItem gun = new InfoItem("持枪证",gunInfo);
			gun.setMore(false);
			basicData.add(gun);
		}

		InfoItem eduTitle = new InfoItem("学历信息", null);
		eduTitle.setFlag(0);
		basicData.add(eduTitle);
		String bEdu;
		if (user.getBefore_degree() != null) {
			bEdu = user.getBefore_education() + " " + user.getBefore_degree();
		} else {
			bEdu = user.getBefore_education();
		}
		InfoItem before_edu = new InfoItem("全日制教育", delNull(bEdu));
		basicData.add(before_edu);
		InfoItem before_school = new InfoItem("毕业院校及专业",delNull(
				user.getBefore_school() + " " + user.getBefore_specialty()));
		basicData.add(before_school);

		String lEdu;
		if (user.getBefore_degree() != null) {
			lEdu = user.getLatest_education() + " " + user.getLatest_degree();
		} else {
			lEdu = user.getLatest_education();
		}
		InfoItem latest_edu = new InfoItem("在职教育", delNull(lEdu));
		basicData.add(latest_edu);
		InfoItem latest_school = new InfoItem("毕业院校及专业",delNull(
				user.getLatest_school() + " " + user.getLatest_specialty()));
		basicData.add(latest_school);


		List<Degree> degree = DegreeDao.getDegreeList(user.getUser_code());
		createDegreeItme(degree,basicData);



		// 填充年度考核列表数据
		if (assessDataList != null) {
			InfoItem assessTitle = new InfoItem("年度考核", null);
			assessTitle.setFlag(0);
			basicData.add(assessTitle);
			for (int i = 0; i < assessDataList.size(); i++) {
				InfoItem assessItem = new InfoItem(assessDataList.get(i)
						.getYear() , assessDataList.get(i).getResult());
				assessItem.setMore(false);
				basicData.add(assessItem);
			}
		}


		//填充奖励信息数据
		if (examineDataList != null) {
			InfoItem examineTitle = new InfoItem("奖励信息", null);
			examineTitle.setFlag(0);
			basicData.add(examineTitle);
			for (int i = 0; i < examineDataList.size(); i++) {
                String time = StringUtil.toShowData(examineDataList.get(i).getExamine_time());
				String desc = examineDataList.get(i).getExamine_name();
				InfoItem examineItem = new InfoItem(time , desc);
				examineItem.setMore(false);
				if(desc!=null && desc.length()>0) basicData.add(examineItem);
			}
		}

		//填充惩戒信息数据
		if (punishDataList != null) {
			InfoItem punishTitle = new InfoItem("惩戒信息", null);
			punishTitle.setFlag(0);
			basicData.add(punishTitle);
			for (int i = 0; i < punishDataList.size(); i++) {
				String time = StringUtil.toShowData(punishDataList.get(i)
						.getPunish_time());
				String desc = punishDataList.get(i)
						.getPunish_name()+" "+punishDataList.get(i).getPunish_type();
				InfoItem puishItem = new InfoItem(time ,desc);
				puishItem.setMore(false);
				if(desc!=null &&  !desc.equals(" "))  basicData.add(puishItem);
			}
		}
		List<Train> trains = TrainDao.getTrain(user.getUser_code());
		if (trains!=null && trains.size()>0){
			InfoItem trainsInfo = new InfoItem("培训信息", null);
			trainsInfo.setFlag(0);
			basicData.add(trainsInfo);
			for (int i = 0; i < trains.size(); i++) {
				String time = StringUtil.toShowData(trains.get(i).getStart_time())+"-"+StringUtil.toShowData(trains.get(i).getEnd_time());
				String desc = trains.get(i).getClass_name() +" "+trains.get(i).getDesc();
				InfoItem trainsItem = new InfoItem(time ,desc);
				trainsItem.setMore(false);
				if(desc!=null && !desc.equals(" ")) basicData.add(trainsItem);
			}

		}

	}

	/**
	 * 职称信息显示
	 * @param quas 职称信息集
	 * @param basicData 数据源
	 */
	private void CreateQualificationItem(List<Qualification> quas, List<InfoItem> basicData) {
		if(quas!=null && quas.size()>0){
			String qualication = "";
			for(Qualification qua:quas){
				qualication += qua.getQfc_name()  +"\n";
			}
			if(qualication.length()>0){
				qualication = qualication.substring(0,qualication.length()-1);
				basicData.add(new InfoItem("职称",delNull(qualication)));
			}
		}
	}

	/**
	 * 学位信息显示
	 * @param degree 学位信息集
	 * @param basicData 数据源
	 */
	private void createDegreeItme(List<Degree> degree, List<InfoItem> basicData) {
		if (degree!= null && degree.size()>0){
			InfoItem degreeTitle = new InfoItem("学位信息", null);
			degreeTitle.setFlag(0);
			basicData.add(degreeTitle);
			String fullDeg = "";
			String jobDeg = "";
			for(Degree deg:degree){
				if (deg.getType().indexOf("全日制")>-1){
                      fullDeg += deg.getSchool() +" "+deg.getDegree() +"\n";
				}
				if (deg.getType().indexOf("在职")>-1){
					 jobDeg += deg.getSchool() +" "+deg.getDegree() +"\n";
				}
			}
			if(fullDeg.length()>0){
				fullDeg = fullDeg.substring(0,fullDeg.length()-1);
				basicData.add(new InfoItem("全日制学位",delNull(fullDeg)));
			}
			if(jobDeg.length()>0){
				jobDeg = jobDeg.substring(0,jobDeg.length()-1);
				basicData.add(new InfoItem("在职学位",delNull(jobDeg)));
			}
		}
	}


}
