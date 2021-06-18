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
 * ��Ա��ϸ��ϢFragmentҳ
 */
public class UserDetail extends Fragment {

	private int user_id;
	private Context context;
	private View view;
	private User user;// �û�����
	private List<Assess> assessDataList; // �����б�����
	private List<Examine> examineDataList;// �����б�����
	private List<Punish> punishDataList;//�ͽ��б�����
	private ListView basicListView; // ��ͼ�б�
	private List<InfoItem> basicData; //������Դ
	private InfoAdapter adapter; //�б�������

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	/**
	 * ������ʵ��
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
		InfoItem itemTitle = new InfoItem("������Ϣ", null);
		itemTitle.setFlag(0);
		basicData.add(itemTitle);



		InfoItem user_name = new InfoItem("����", user.getUser_name());
		user_name.setMore(false);
		basicData.add(user_name);
		InfoItem sex = new InfoItem("�Ա�", user.getSex());
		sex.setMore(false);
		basicData.add(sex);
		InfoItem birth_date = new InfoItem("��������", StringUtil.toShowData(user
				.getBirth_date())
				+ "["
				+ StringUtil.getYearNum(user.getBirth_date(),
						Setting.DB_TIME_FORMAT) + "��]");
		birth_date.setMore(false);
		basicData.add(birth_date);
		InfoItem nation = new InfoItem("����", user.getNation());
		nation.setMore(false);
		basicData.add(nation);
		InfoItem nativeplace = new InfoItem("����", user.getNativeplace());
		nativeplace.setMore(false);
		basicData.add(nativeplace);
		InfoItem birthplace = new InfoItem("������", user.getBirthplace());
		birthplace.setMore(false);
		basicData.add(birthplace);
		String rdTime = StringUtil.toShowData(user.getRd_time());
		String rdYear =  "["+StringUtil.getYearNum(rdTime, Setting.SH_TIME_FORMAT)+"��]";
		InfoItem rd_time = new InfoItem("�뵳ʱ��", rdTime+ rdYear);
		rd_time.setMore(false);
		basicData.add(rd_time);
		
		 String jobTime = StringUtil.toShowData(user.getJob_time());
		 String jobYear = "["+StringUtil.getYearNum(jobTime, Setting.SH_TIME_FORMAT)+"��]";
		InfoItem job_time = new InfoItem("�μӹ���ʱ��",jobTime+jobYear);
		job_time.setMore(false);
		basicData.add(job_time);
		InfoItem status = new InfoItem("����״��", user.getStatus());
		status.setMore(false);
		basicData.add(status);
		//ְ����Ϣ��
		List<Job> jobs = JobDao.getJobs(user.getUser_code());
		InfoItem position1 = new InfoItem("����ְ��ʱ��",jobToString(jobs));
		basicData.add(position1);
		InfoItem position2 = new InfoItem("�����θ�ְ������ʱ��",job2ToString(jobs));
		basicData.add(position2);
		//ְ����Ϣ��
		List<PreDepth> preDepths = PreDepthDao.getPreDepth(user.getUser_code());
		InfoItem depth1 = new InfoItem("����ְ����ʱ��",	preDepthToString(preDepths));
		depth1.setMore(false);
		basicData.add(depth1);
		InfoItem depth2 = new InfoItem("���൱���ְ��ְ������ʱ��",preDepth2ToString(preDepths));
		depth2.setMore(false);
		basicData.add(depth2);
		InfoItem depth3 = new InfoItem("ְ������׼ʱ��",preDepth3ToString(preDepths));
		depth3.setMore(false);
		basicData.add(depth3);
		//ְ����Ϣ
		List<Qualification> quas = QualificationDao.getQualificationList(user.getUser_code());
		CreateQualificationItem(quas,basicData);
		Rank userRank =RankDao.getRank(user.getUser_code());
	    if (userRank!=null){
			InfoItem rank = new InfoItem("����", RankDao.getRank(user.getUser_code()).getRank_name());
			basicData.add(rank);
		}
		InfoItem user_num = new InfoItem("����", user.getNumber());
		user_num.setMore(false);
		basicData.add(user_num);

		String marInfo = marshalsToString(MarshalsDao.getMarshals(user.getUser_code()));
		if (marInfo!=null && marInfo.length()>0){
			InfoItem marshals = new InfoItem("ִ���ʸ�",marInfo);
			marshals.setMore(false);
			basicData.add(marshals);
		}

		String gunInfo = gunToString(GunDao.getGun(user.getUser_code()));
		if (gunInfo!=null && gunInfo.length()>0){
			InfoItem gun = new InfoItem("��ǹ֤",gunInfo);
			gun.setMore(false);
			basicData.add(gun);
		}

		InfoItem eduTitle = new InfoItem("ѧ����Ϣ", null);
		eduTitle.setFlag(0);
		basicData.add(eduTitle);
		String bEdu;
		if (user.getBefore_degree() != null) {
			bEdu = user.getBefore_education() + " " + user.getBefore_degree();
		} else {
			bEdu = user.getBefore_education();
		}
		InfoItem before_edu = new InfoItem("ȫ���ƽ���", delNull(bEdu));
		basicData.add(before_edu);
		InfoItem before_school = new InfoItem("��ҵԺУ��רҵ",delNull(
				user.getBefore_school() + " " + user.getBefore_specialty()));
		basicData.add(before_school);

		String lEdu;
		if (user.getBefore_degree() != null) {
			lEdu = user.getLatest_education() + " " + user.getLatest_degree();
		} else {
			lEdu = user.getLatest_education();
		}
		InfoItem latest_edu = new InfoItem("��ְ����", delNull(lEdu));
		basicData.add(latest_edu);
		InfoItem latest_school = new InfoItem("��ҵԺУ��רҵ",delNull(
				user.getLatest_school() + " " + user.getLatest_specialty()));
		basicData.add(latest_school);


		List<Degree> degree = DegreeDao.getDegreeList(user.getUser_code());
		createDegreeItme(degree,basicData);



		// �����ȿ����б�����
		if (assessDataList != null) {
			InfoItem assessTitle = new InfoItem("��ȿ���", null);
			assessTitle.setFlag(0);
			basicData.add(assessTitle);
			for (int i = 0; i < assessDataList.size(); i++) {
				InfoItem assessItem = new InfoItem(assessDataList.get(i)
						.getYear() , assessDataList.get(i).getResult());
				assessItem.setMore(false);
				basicData.add(assessItem);
			}
		}


		//��佱����Ϣ����
		if (examineDataList != null) {
			InfoItem examineTitle = new InfoItem("������Ϣ", null);
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

		//���ͽ���Ϣ����
		if (punishDataList != null) {
			InfoItem punishTitle = new InfoItem("�ͽ���Ϣ", null);
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
			InfoItem trainsInfo = new InfoItem("��ѵ��Ϣ", null);
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
	 * ְ����Ϣ��ʾ
	 * @param quas ְ����Ϣ��
	 * @param basicData ����Դ
	 */
	private void CreateQualificationItem(List<Qualification> quas, List<InfoItem> basicData) {
		if(quas!=null && quas.size()>0){
			String qualication = "";
			for(Qualification qua:quas){
				qualication += qua.getQfc_name()  +"\n";
			}
			if(qualication.length()>0){
				qualication = qualication.substring(0,qualication.length()-1);
				basicData.add(new InfoItem("ְ��",delNull(qualication)));
			}
		}
	}

	/**
	 * ѧλ��Ϣ��ʾ
	 * @param degree ѧλ��Ϣ��
	 * @param basicData ����Դ
	 */
	private void createDegreeItme(List<Degree> degree, List<InfoItem> basicData) {
		if (degree!= null && degree.size()>0){
			InfoItem degreeTitle = new InfoItem("ѧλ��Ϣ", null);
			degreeTitle.setFlag(0);
			basicData.add(degreeTitle);
			String fullDeg = "";
			String jobDeg = "";
			for(Degree deg:degree){
				if (deg.getType().indexOf("ȫ����")>-1){
                      fullDeg += deg.getSchool() +" "+deg.getDegree() +"\n";
				}
				if (deg.getType().indexOf("��ְ")>-1){
					 jobDeg += deg.getSchool() +" "+deg.getDegree() +"\n";
				}
			}
			if(fullDeg.length()>0){
				fullDeg = fullDeg.substring(0,fullDeg.length()-1);
				basicData.add(new InfoItem("ȫ����ѧλ",delNull(fullDeg)));
			}
			if(jobDeg.length()>0){
				jobDeg = jobDeg.substring(0,jobDeg.length()-1);
				basicData.add(new InfoItem("��ְѧλ",delNull(jobDeg)));
			}
		}
	}


}
