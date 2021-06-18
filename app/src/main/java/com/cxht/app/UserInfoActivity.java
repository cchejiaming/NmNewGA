package com.cxht.app;

import java.io.File;
import java.util.ArrayList;

import com.cxht.adapter.BasePageAdapter;
import com.cxht.config.Setting;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.cxht.fragment.UserAbout;
import com.cxht.fragment.UserDetail;
import com.cxht.fragment.UserFamily;
import com.cxht.fragment.UserMaterial;
import com.cxht.fragment.UserResume;
import com.gongan.manage.R;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class UserInfoActivity extends BaseFragmentActivity implements
		OnClickListener {
	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	private int userId;// ��ѯ�û�Id
	private User user; // ��ǰ�û�����
	private String actionTitle;

	/**
	 * ����5��FragmentƬ��
	 */
	private UserAbout about; // ��Ա���
	private UserDetail detail; // ��Ա��ϸ
	private UserFamily family;// ��ͥ��Ϣ
	private UserResume resume; // ������Ϣ
	private UserMaterial material;//����
	//private UserField  field;//�Զ�����Ϣ
	/**
	 * �ײ������ť
	 */
	private RadioButton rbAbout;
	private RadioButton rbDetail;
	private RadioButton rbFamily;
	private RadioButton rbResume;
	private RadioButton rbPdf;
	//private RadioButton rbField;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.user_info);
		//ScreenManager.getScreenManager().pushActivity(this); //��ӵ�Activityջ
	
		userId = getIntent().getIntExtra("UserId", 0);
		user = UserDao.queryUser(userId);
		initView();
		String title = getIntent().getStringExtra("Title");
		if (title != null)actionBar.setTitle(title);
		
		
		setTabSelection(0);
		initFragment();
	}
	/** 
	 *  ��ʼ��Fragment
	 * */
	private void initFragment() {
		fragments.clear();//���
		
		about = UserAbout.newInstance(userId);
		detail = UserDetail.newInstance(userId);
	    resume = UserResume.newInstance(userId);
		family = UserFamily.newInstance(userId);
		material = UserMaterial.newInstance(userId);
		//field =  UserField.newInstance(userId);
		fragments.add(about);
		if(user!= null){
			if (!user.getUser_code().equals(Setting.USER_CODE)){
				fragments.add(detail);
				fragments.add(resume);
				fragments.add(family);
				fragments.add(material);
			}
		}
		
		//fragments.add(field);
		BasePageAdapter mAdapetr = new BasePageAdapter(getSupportFragmentManager(), fragments);
	    mViewPager.setAdapter(mAdapetr);
	    //mViewPager.setOffscreenPageLimit(1);
	    mViewPager.setOnPageChangeListener(pageListener);
     
		
	}
	/** 
	 *  ViewPager�л���������
	 * */
	public OnPageChangeListener pageListener= new OnPageChangeListener(){

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			
			setTabSelection(position);
			mViewPager.setCurrentItem(position);
		}
	};
	private void setTabSelection(int i) {
		// ���ð�ť
		resetBtn();
		// ����һ��Fragment����
		

		switch (i) {
		case 0:

			setRodioButtonState(rbAbout, R.drawable.info_jj_h);
			mViewPager.setCurrentItem(i);
			break;
		case 1:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbDetail, R.drawable.info_xx_h);
			mViewPager.setCurrentItem(i);
			break;
		
		case 2:
			// �����������tabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbResume, R.drawable.info_jl_h);
			mViewPager.setCurrentItem(i);
			break;
		case 3:
			// ������˶�̬tabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbFamily, R.drawable.info_home_h);
			mViewPager.setCurrentItem(i);
            break;
		case 4:
				// ������˶�̬tabʱ���ı�ؼ���ͼƬ��������ɫ
				setRodioButtonState(rbPdf, R.drawable.stat_wzlb_h);
				mViewPager.setCurrentItem(i);
				break;

		}
		
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		rbAbout = (RadioButton) findViewById(R.id.rbAbout);
		rbDetail = (RadioButton) findViewById(R.id.rbDetail);
        rbResume = (RadioButton) findViewById(R.id.rbResume);
		rbFamily = (RadioButton) findViewById(R.id.rbFamily);
		rbPdf = (RadioButton) findViewById(R.id.rbPdf);
		//rbField = (RadioButton) findViewById(R.id.rbField);
		rbAbout.setOnClickListener(this);
		if(user!= null){
			if (!user.getUser_code().equals(Setting.USER_CODE)){
				rbDetail.setOnClickListener(this);
				rbFamily.setOnClickListener(this);
				rbResume.setOnClickListener(this);
				rbPdf.setOnClickListener(this);
			}
		}
		
	//	rbField.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rbAbout:
			setTabSelection(0);
		    break;
		case R.id.rbDetail:
			setTabSelection(1);
			break;
		case R.id.rbResume:
			setTabSelection(2);
			break;
		case R.id.rbFamily:
			setTabSelection(3);
			break;
		case R.id.rbPdf:
			setTabSelection(4);
				break;

		default:
			break;
		}

	}

	/**
	 * ��ť����
	 */
	private void resetBtn() {
		setRodioButtonState(rbAbout, R.drawable.info_jj);
		setRodioButtonState(rbDetail, R.drawable.info_xx);
		setRodioButtonState(rbFamily, R.drawable.info_home);
		setRodioButtonState(rbResume, R.drawable.info_jl);
		setRodioButtonState(rbPdf,R.drawable.stat_wzlb);
        //setRodioButtonState(rbField,R.drawable.info_jj);
	}

	/**
	 * ����RoidoButtonͼ��
	 */
	private void setRodioButtonState(RadioButton btn, int id) {
		Drawable img_off;
		Resources res = getResources();
		img_off = res.getDrawable(id);
		// ����setCompoundDrawablesʱ���������Drawable.setBounds()����,����ͼƬ����ʾ
		img_off.setBounds(0, 0, img_off.getMinimumWidth(),
				img_off.getMinimumHeight());
		btn.setCompoundDrawables(null, img_off, null, null); // ������ͼ��

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// �������ͬ��menu item ��ִ�в�ͬ�Ĳ���
		switch (id) {

		case R.id.user_spb:
			openSpb();
			break;
		case R.id.user_db:
			userDb();
			break;
		
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * ������������
	 */
    private void openSpb(){
    	
   
    	if (user!= null){
    		if (user.getRmb_path()!=null && !user.getRmb_path().equals("")){
    			
    			Intent  intent = getExcelFileIntent(user.getRmb_path());
    			startActivity(intent);
    		}else{
    			if(user!= null){
    				if (!user.getUser_code().equals(Setting.USER_CODE)){
    					Intent intent = new Intent(this,ReadExcel.class);
    	    			intent.putExtra("user_id", String.valueOf(userId));
    	    			intent.putExtra("user_name",user.getUser_name());
    	    			intent.putExtra("birth_date",user.getBirth_date());
    	            	startActivity(intent);
    				}
    			}
    			
    		}
    		
    	}
    	
    }
	

	private void userDb() {
		if (userId > 0) {
			UserDao.saveFavoriteUser(userId, this);
			Toast.makeText(this, "�ղسɹ���", Toast.LENGTH_SHORT).show();
		}

	}
	//android��ȡһ�����ڴ�Excel�ļ���intent 
	  public static Intent getExcelFileIntent( String param )  { 
	    Intent intent = new Intent("android.intent.action.VIEW"); 
	    intent.addCategory("android.intent.category.DEFAULT"); 
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    Uri uri = Uri.fromFile(new File(param )); 
	    intent.setDataAndType(uri, "application/vnd.ms-excel"); 
	    return intent; 
	  } 
}
