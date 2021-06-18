package com.cxht.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.adapter.NationAdapter;
import com.cxht.bean.CheckBean;
import com.cxht.bean.Item;
import com.cxht.bean.SearchItem;
import com.cxht.config.Setting;
import com.cxht.dao.DepthDao;
import com.cxht.dao.TableDao;
import com.cxht.view.KingoitFlowLayout;
import com.gongan.manage.R;
import com.gov.tree.EduTreeAdapter;
import com.gov.tree.TreeNodeCheck;
public class DialogUtil {
	private final String TAG = DialogUtil.class.getSimpleName();
	private static final int SORTCRITERIA_DESC = 1; //��������
	private static final int SORTCRITERIA_VALUES =2; //����ֵ

	/**
	 * diaglog ������
	 * 
	 * @author hejiaming
	 * 
	 */
	public interface OnDialogResultListener {
		/***
		 * ���ؽ������
		 *
		 */
		void OnDialogResult(String n, List<SearchItem> d);
	}

	/**
	 *  diaglog ���ز������
	 */
   public interface  OnDialogParamResultListener<T>{
		void OnDialogResult(T t);
   }
	private static OnDialogResultListener onDialogResultListener;


	public static Dialog createProgressDialog(Context context, String message) {

		Dialog dialog = null;
		dialog = new Dialog(context, R.style.dialog_progress);
		dialog.setContentView(R.layout.progress_dialog_index);
		// dialog.setCanceledOnTouchOutside(false); // 设置点击边缘是否消失
		// dialog.setCancelable(false);
		ImageView img = (ImageView) dialog.findViewById(R.id.progress_img);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.progress_anim);
		img.startAnimation(animation);
		TextView text = (TextView) dialog.findViewById(R.id.progress_msg);
		text.setText(message);
		return dialog;

	}
    public static AlertDialog createPositionAttr(final Context context){
    	final List<CheckBean> mDatas = Setting.getPositionAttr(context);
		AlertDialog dialog = createDialog(context, mDatas, "positionAttr",
				"ְ������", "ȫѡ");
		return dialog;
    }
	public static AlertDialog createFullEduDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getFullEdu(context);
		AlertDialog dialog = createDialog(context, mDatas, "fullEdu",
				"ȫ����ѧ��ѡ��", "ȫѡ");
		return dialog;
	}
	public static AlertDialog createStateDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getState(context);
		AlertDialog dialog = createDialog(context, mDatas, "state","����״��","ȫѡ");
		return dialog;
	}
	public static AlertDialog createAssessYearDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getAssessYear(context);
		AlertDialog dialog = createDialog(context, mDatas, "assessYear","�������","ȫѡ");
		return dialog;
	}
	public static AlertDialog createAssessResultDialog(final Context context) {
		List<TreeNodeCheck> mDatas = TableDao.getCheckAssessList();
		AlertDialog dialog = baseTreeDialog(context, mDatas, "assessResult","���˽��",0);
		return dialog;
	}
	public static AlertDialog createPunishTypeDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getPunishType(context);
		AlertDialog dialog = createDialog(context, mDatas, "punishType","�ͽ�����","ȫѡ");
		return dialog;
	}
	public static AlertDialog createMarshalsDialog(final Context context) {
		List<TreeNodeCheck> mDatas = TableDao.getCheckMarshalsList();
		AlertDialog dialog = createTreeDialog(context, mDatas, "marshals","ִ���ʸ�");
		return dialog;
	}
	public static AlertDialog createUserRankDialog(final Context context) {

		List<TreeNodeCheck> mDatas = TableDao.getCheckRankList();
		AlertDialog dialog = createTreeDialog(context, mDatas, "userRank","����");
		return dialog;
	}

	public static AlertDialog createPoliticalDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getPolitical(context);
		AlertDialog dialog = createDialog(context, mDatas, "political","����ѡ��","ȫѡ");
		return dialog;
	}
	public static AlertDialog createDegreeDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createDegreeDialog(context, mDatas,"ѧλѡ��");
		return dialog;
	}

	public static AlertDialog createJobDegreeDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createDialog(context, mDatas, "jobDegree",
				"��ְѧλѡ��", "ȫѡ");
		return dialog;
	}
    public static AlertDialog createPrePositionDialog(final Context context){
		List<TreeNodeCheck> mDatas = DepthDao.getDepthList(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "prePosition",
				"����ְ��");
		return dialog;
    }
	public static AlertDialog createJobGwyDialog(final Context context){
		List<TreeNodeCheck> mDatas = DepthDao.getDepthListOfGwy(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "jobGwy",
				"ְ����");
		return dialog;
	}
	public static AlertDialog createQualificationDialog(final Context context){
		List<TreeNodeCheck> mDatas = TableDao.getCheckQualificationList();
		AlertDialog dialog = baseTreeDialog(context, mDatas, "qualification",
				"ְ��ѡ��",0);
		return dialog;
	}
	public static AlertDialog createTreeFullEduDialog(final Context context) {
		List<TreeNodeCheck> mDatas = Setting.getFullEduTree(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "fullEdu",
				"ȫ����ѧ��ѡ��");
		return dialog;
	}

	public static AlertDialog createTreeJobEduDialog(final Context context) {
		List<TreeNodeCheck> mDatas = Setting.getJobEduTree(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "jobEdu",
				"��ְѧ��ѡ��");
		return dialog;
	}

	public static AlertDialog createTreeHighestEduDialog(final Context context) {
		List<TreeNodeCheck> mDatas = Setting.getHighestEdu(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "highestEdu",
				"���ѧ��ѡ��");
		return dialog;
	}
    public static AlertDialog createTreeGroupDialog (final Context context){
    	List<TreeNodeCheck> mDatas = Setting.getGroupTree(context);
		AlertDialog dialog = createTreeDialog(context, mDatas, "jobOrg",
				"������λѡ��");
		return dialog;
    }
	public static AlertDialog createSchoolDialog(
			final Context context) {
		AlertDialog dialog = createSchoolDialog(context,"��ҵԺУ");
		return dialog;
	}
	public static AlertDialog createHighestDegreeDialog(
			final Context context) {
		final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createDialog(context, mDatas, "highestDegree",
				"���ѧλѡ��", "ȫѡ");
		return dialog;
	}
    public static AlertDialog createResumeDialog(final Context context){
    	//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "resumeSearch",
				"����ģ����ѯ");
		return dialog;	
    }
    public static AlertDialog createMajorDialog(final Context context){
    	AlertDialog dialog = createTextDialog(context, "major",
				"ȫ����רҵģ����ѯ");
		return dialog;
    }
    public static AlertDialog createfamilyDialog(final Context context){
    	//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "familySearch",
				"��ͥ��Ա��λ");
		return dialog;	
    }
	public static AlertDialog createNativeDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "nativeSearch",
				"����");
		return dialog;
	}
	public static AlertDialog createBirthDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "birthSearch",
				"������");
		return dialog;
	}
	public static AlertDialog createExamineDialog(final Context context){

		List<TreeNodeCheck> mDatas = TableDao.getCheckExamineList();
		AlertDialog dialog = createTreeDialog(context, mDatas, "examineSearch",
				"��������");

		return dialog;
	}
	public static AlertDialog createPunishNameDialog(final Context context){

		List<TreeNodeCheck> mDatas = TableDao.getCheckPunishList();
		AlertDialog dialog = baseTreeDialog(context, mDatas, "punishNameSearch","�ͽ�����",0);

		return dialog;
	}
	public static AlertDialog createUserTypeDialog(final Context context){
		String sql = "select DISTINCT user_type from t_user where user_type <>''";
		List<CheckBean> mDatas =TableDao.getTableCheckBean(context, sql, "user_type");
		AlertDialog dialog = createDialog(context, mDatas, "userType",
				"��Ա���", "ȫѡ");
		return dialog;
	}
	public static AlertDialog createFamilyNameDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "familyNameSearch",
				"��ͥ��Ա����");
		return dialog;
	}
	public static AlertDialog createUserNumberDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "userNumber",
				"����");
		return dialog;
	}
	public static AlertDialog createUserCodeDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "userCode",
				"���֤��");
		return dialog;
	}
	public static AlertDialog createGunLicenceDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "gunLicence",
				"��ǹ֤����");
		return dialog;
	}
	public static AlertDialog createJobDescDialog(final Context context){
		//final List<CheckBean> mDatas = Setting.getFullDegree();
		AlertDialog dialog = createTextDialog(context, "jobDescSearch",
				"����ְ��");
		return dialog;
	}
    public static AlertDialog createFamilyAgeDialog (final Context context){
    	
		AlertDialog dialog = createAgeDialog(context,  "familyAge",
				"��ͥ��Ա����");
		return dialog;
    }
    private static AlertDialog CreateAgeDialog(Context context,final String flag,String title){
    	
    	AlertDialog dialog = null;
    	AlertDialog.Builder builder = new AlertDialog.Builder(context,
    			AlertDialog.THEME_HOLO_LIGHT);
    	LayoutInflater factory = LayoutInflater.from(context);
		final View view = factory
				.inflate(R.layout.dialog_age_jd, null);
		SeekBar age_min_seek = (SeekBar) view.findViewById(R.id.min_age_seekBar);
		SeekBar age_max_seek = (SeekBar) view.findViewById(R.id.max_age_seekBar);
		final EditText age_min = (EditText) view.findViewById(R.id.min_age_text);
		final EditText age_max = (EditText) view.findViewById(R.id.max_age_text);
		age_min_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				setEditValue(age_min, Setting.F_MIN_AGE, progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		age_max_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				setEditValue(age_max, Setting.F_MIN_AGE, progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		builder.setTitle(title);
		builder.setView(view);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<SearchItem> data = new ArrayList<SearchItem>();
				String min = age_min.getText().toString();
				data.add(new SearchItem(min,min));
				String max = age_max.getText().toString();
				data.add(new SearchItem(max,max));
				onDialogResultListener.OnDialogResult(flag,data);
			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
	
		dialog = builder.create();
		dialog.show();
    	return dialog;
    }
    public static AlertDialog createJobYear( Context context){

		AlertDialog dialog = CreateAgeDialog(context,"jobYear","��ְ������ѡ��");
		return dialog;
    }

	/**
	 * �����θ�ְ������
	 * @param context
	 * @return
	 */
	public static AlertDialog createJobLxYear( Context context){

		AlertDialog dialog = CreateAgeDialog(context,"jobLx","�����θ�ְ������ѡ��");
		return dialog;
	}
	/**
	 * ���൱���ְ��ְ����������
	 * @param context
	 * @return
	 */
	public static AlertDialog createDepthLxYear( Context context){

		AlertDialog dialog = CreateAgeDialog(context,"depthLx","���൱���ְ��ְ����������ѡ��");
		return dialog;
	}

	/**
	 * ��������
	 * @param context
	 * @return
	 */
	public static AlertDialog createWorkYear( Context context){

		AlertDialog dialog = CreateAgeDialog(context,"workYear","��������ѡ��");
		return dialog;
	}

	/**
	 * �뵳����
	 * @param context
	 * @return
	 */
	public static AlertDialog createPartyYear( Context context){
		AlertDialog dialog = CreateAgeDialog(context,"partyYear","�뵳����ѡ��");
		return dialog;
	}
	/**
	 * ְ������׼����
	 * @param context
	 * @return
	 */
	public static AlertDialog createDepthRatifyYear( Context context){
		AlertDialog dialog = CreateAgeDialog(context,"depthRatifyYear","ְ������׼����ѡ��");
		return dialog;
	}
    public static AlertDialog createPosYear(final Context context){
    	final List<CheckBean> mDatas = Setting.getAgeLimit();
		//AlertDialog dialog = createDialog(context, mDatas, "posYear",
			//	"��ְ������ѡ��", "ȫѡ");
    	AlertDialog dialog = CreateAgeDialog(context,"posYear","��ְ������ѡ��");
		return dialog;
    }
    private static AlertDialog createAgeDialog(final Context context,final String flag,String title){
    	AlertDialog dialog = null;
    	AlertDialog.Builder builder = new AlertDialog.Builder(context,
    			AlertDialog.THEME_HOLO_LIGHT);
    	LayoutInflater factory = LayoutInflater.from(context);
		final View view = factory
				.inflate(R.layout.dialog_age, null);
		SeekBar age_min_seek = (SeekBar) view.findViewById(R.id.min_age_seekBar);
		SeekBar age_max_seek = (SeekBar) view.findViewById(R.id.max_age_seekBar);
		final EditText age_min = (EditText) view.findViewById(R.id.min_age_text);
		final EditText age_max = (EditText) view.findViewById(R.id.max_age_text);
		age_min_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				setEditValue(age_min, Setting.F_MIN_AGE, progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		age_max_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				setEditValue(age_max, Setting.F_MIN_AGE, progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		builder.setTitle(title);
		builder.setView(view);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<SearchItem> data = new ArrayList<SearchItem>();
				String min = age_min.getText().toString();
				data.add(new SearchItem(min,min));
				String max = age_max.getText().toString();
				data.add(new SearchItem(max,max));
				onDialogResultListener.OnDialogResult(flag,data);
			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
	
		dialog = builder.create();
		dialog.show();
    	return dialog;
    }
    private static void setEditValue(EditText et, int startValue, int value) {
		et.setText(String.valueOf(startValue + value));
	}

	/**
	 * �ı��Ի���
	 * @param context
	 * @param flag
	 * @param title
	 * @return
	 */
    private static AlertDialog createTextDialog(final Context context, final String flag, String title){
    	AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory
				.inflate(R.layout.dialog_edit, null);
		final EditText editTv = (EditText) textEntryView
				.findViewById(R.id.content_text);
		 
		builder.setTitle(title);
		builder.setView(textEntryView);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<SearchItem> data = new ArrayList<SearchItem>();
				String vl = editTv.getText().toString();
				if(!vl.equals("")){
					data.add(new SearchItem(vl,vl));
				}
				onDialogResultListener.OnDialogResult(flag,data);
			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
	
		dialog = builder.create();
		dialog.show();
	
		return dialog;
    }

	/**
	 * ���ζԻ���
	 * @param context
	 * @param data ����Դ
	 * @param flag ��ʶ
	 * @param title �Ի����ǩ
	 * @return
	 */
	private static AlertDialog createTreeDialog(final Context context,final List<TreeNodeCheck> data, final String flag, String title) {

		return baseTreeDialog(context,data,flag,title,1);
	}

	/**
	 * ���ζԻ���
	 * @param context
	 * @param data ����Դ
	 * @param flag ��ʶ
	 * @param title �Ի����ǩ
	 * @param defaultExpandLevel ����չ�����
	 * @return
	 */
	private  static AlertDialog baseTreeDialog(final Context context,final List<TreeNodeCheck> data, final String flag, String title, int defaultExpandLevel){
		AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.dialog_tree, null);

		// builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(title);
		builder.setView(textEntryView);

		ListView listView = (ListView) textEntryView
				.findViewById(R.id.data_lv);
		final EduTreeAdapter mAdapter;
		try {
			mAdapter = new EduTreeAdapter(listView, context, data, defaultExpandLevel);
			listView.setAdapter(mAdapter);
			// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
			builder.setPositiveButton("ȷ��", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onDialogResultListener.OnDialogResult(flag,
							getTreeParam(mAdapter.mAllNodes));
				}
			});

		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/**
	 * ѧλ�Ի���
	 * @param context
	 * @param title
	 * @return
	 */
	private static AlertDialog createDegreeDialog(final Context context,final List<CheckBean> mDatas, String title) {

		AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.dialog_option_3, null);
		final RadioButton fullRb =(RadioButton) textEntryView.findViewById(R.id.full_radioButton);
		final RadioButton higRb =(RadioButton) textEntryView.findViewById(R.id.hig_radioButton);
		final RadioButton jobRb =(RadioButton) textEntryView.findViewById(R.id.job_radioButton);
		CheckBox mCheck = (CheckBox) textEntryView.findViewById(R.id.all_ckb);
		builder.setTitle(title);
		builder.setView(textEntryView);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (higRb.isChecked())	onDialogResultListener.OnDialogResult("highestDegree",getCheckParam(mDatas));
				if (fullRb.isChecked())	onDialogResultListener.OnDialogResult("fullDegree",getCheckParam(mDatas));
				if (jobRb.isChecked())	onDialogResultListener.OnDialogResult("jobDegree",getCheckParam(mDatas));
			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		ListView listView = (ListView) textEntryView
				.findViewById(R.id.data_lv);
		final NationAdapter mAdapter = new NationAdapter(context, mDatas);
		listView.setAdapter(mAdapter);
		mCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				setMinority(isChecked, mDatas, "degree");
				mAdapter.notifyDataSetChanged();
			}
		});
		dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/**
	 * ԺУ��ѯ�Ի���
	 * @param context
	 * @param title
	 * @return
	 */
	private static AlertDialog createSchoolDialog(final Context context, String title) {

		AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.dialog_option_2, null);
		final RadioButton jobRb =(RadioButton) textEntryView.findViewById(R.id.job_radioButton);
		final EditText editTv = (EditText) textEntryView
				.findViewById(R.id.content_text);
		builder.setTitle(title);
		builder.setView(textEntryView);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ArrayList<SearchItem> data = new ArrayList<SearchItem>();
				String vl = editTv.getText().toString();
				if(!vl.equals("")){
					data.add(new SearchItem(vl,vl));
				}
				if (jobRb.isChecked()){
					onDialogResultListener.OnDialogResult("jobSchool",data);
				}else {
					onDialogResultListener.OnDialogResult("fullSchool",data);
				}

			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialog = builder.create();
		dialog.show();
		return dialog;
	}
	private static AlertDialog createDialog(final Context context,
			final List<CheckBean> mDatas, final String flag, String title,
			String top) {

		AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory
				.inflate(R.layout.dialog_nation, null);
		CheckBox mCheck = (CheckBox) textEntryView
				.findViewById(R.id.all_ckb);
		TextView topTv = (TextView) textEntryView.findViewById(R.id.title_tv);
		topTv.setText(top);
		// builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(title);
		builder.setView(textEntryView);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				onDialogResultListener.OnDialogResult(flag,
						getCheckParam(mDatas));
			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		ListView listView = (ListView) textEntryView
				.findViewById(R.id.data_lv);
		final NationAdapter mAdapter = new NationAdapter(context, mDatas);
		listView.setAdapter(mAdapter);
		mCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setMinority(isChecked, mDatas, flag);
				mAdapter.notifyDataSetChanged();
			}
		});
		dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static AlertDialog createNationDialog(final Context context) {
		final List<CheckBean> mDatas = Setting.getNation();
		AlertDialog dialog = createDialog(context, mDatas, "nation", "����ѡ��",
				"��������");
		return dialog;
	}

	/**
	 * ��̬�����������б��ظ�������ͬ��������ɾ������������ӣ���û����ͬ����������ӡ���Ӱ��Ⱥ�˳��
	 * @param data �����б�
	 * @param displayName ��������
	 * @return ��������б�
	 */
	private static void execSortCriteriaListItem(List<Item> data,String displayName){
		Item currentItem = findSortCriteriaItem(displayName);
		if(currentItem!=null){
			boolean isRemove = false;
			Iterator<Item> it = data.iterator();
			while(it.hasNext()){
				if(it.next().getDisplay().equals(currentItem.getDisplay())){
					it.remove();
					isRemove = true;
				}
			}
			if(!isRemove) data.add(currentItem);
		}
	}

	/**
	 * ����ʾ���Ʋ���������Ŀ
	 * @param displayName ��������
	 * @return ������Ŀ����
	 */
	private static Item findSortCriteriaItem(String displayName) {
		if (displayName!= null && Setting.sortCriteria()!=null){
			for(Item item:Setting.sortCriteria()){
				if (item.getDisplay().equals(displayName))
					return  item;

			}
		}
		return null;
	}


	/**
	 * ��ȡ������Ϣ
	 * @param type ����
	 * @param temp �������
	 * @return ������ϢString �ַ���
	 */
   public static String sortCriteriaInfo(int type,List<Item> temp){
	   String ret = null;
	   if (temp!=null && temp.size()>0){
	   	   ret = "";
		   for(Item it:temp){
			   if (type == SORTCRITERIA_DESC)
				   ret+= it.getDisplay()+","; else ret+= it.getValue() +",";
		   }
		   if(ret.length()>1) ret = ret.substring(0,ret.length()-1);
	   }
	   return ret;
   }
	/**
	 * ���������Ի���
	 * @param context
	 * @param title
	 * @return
	 */
	public static AlertDialog createSortCriteriaDialog(final Context context, String title, final OnDialogParamResultListener <String> listener){
		AlertDialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View view = factory.inflate(R.layout.dialog_sort_criteria, null);
		final KingoitFlowLayout flowLayout = (KingoitFlowLayout) view.findViewById(R.id.kingoit_flow_layout);
	    final TextView descTv = (TextView) view.findViewById(R.id.descTv);
	    final RadioButton descRb = (RadioButton) view.findViewById(R.id.desc_radioButton);
		final RadioButton defRb = (RadioButton) view.findViewById(R.id.def_radioButton);
	    final List<Item> temp = new ArrayList<Item>();
		flowLayout.showTag(Setting.sortCriteria(), new KingoitFlowLayout.ItemClickListener(){
			@Override
			public void onClick(String currentSelectedkeywords, List <String> allSelectedKeywords) {
				execSortCriteriaListItem(temp,currentSelectedkeywords); // ��̬����ѡ������
                descTv.setText(sortCriteriaInfo(SORTCRITERIA_DESC,temp));
			}
		});
		builder.setTitle(title);
		builder.setView(view);
		// �û�ѡ��Ҫѡ��ѡ��󣬵��ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String mSort = sortCriteriaInfo(SORTCRITERIA_VALUES,temp);
				if (descRb.isChecked() && mSort!=null ) mSort+=" desc";
				if (defRb.isChecked()) mSort = null;
				listener.OnDialogResult(mSort);


			}
		});
		// ȡ��ѡ��
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		dialog = builder.create();
		dialog.show();
		return dialog;
	}
	public static ArrayList<SearchItem> getTreeParam(List<TreeNodeCheck> l) {
		ArrayList<SearchItem> list = null;
		if (l != null) {
			list = new ArrayList<SearchItem>();
			for (TreeNodeCheck et : l) {
				if (et.isCheck() && et.isParam()) {
					list.add(new SearchItem(String.valueOf(et.getId()), et.getName()));
				}
			}
		}
		return list;
	}

	private static ArrayList<SearchItem> getCheckParam(List<CheckBean> l) {
		ArrayList<SearchItem> list = null;
		if (l != null) {
			list = new ArrayList<SearchItem>();
			for (int i=0;i<l.size();i++ ) {
				if (l.get(i).isChecked()) {
					list.add(new SearchItem(String.valueOf(i),l.get(i).getName()));
				}
			}
		}
		return list;
	}

	public static void setMinority(boolean bl, List<CheckBean> list, String flag) {
		if (list != null) {
			String f = getFilter(flag);
			for (CheckBean n : list) {
				if (!n.getName().equals(f)) {
					n.setChecked(bl);
				}
			}
		}
	}

	public static String getFilter(String flag) {
		String ret = "";
		if (flag != null) {
			if (flag.equals("nation")) {
				ret = "����";
			}
			//if (flag.equals("political")) {
				//ret = "�й���Ա";
			//}
		}
		return ret;
	}

	public static OnDialogResultListener getOnDialogResultListener() {
		return onDialogResultListener;
	}

	public static void setOnDialogResultListener(OnDialogResultListener onDialogResultListener) {
		DialogUtil.onDialogResultListener = onDialogResultListener;
	}
}
