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
	
	private int userId;// 查询用户Id
	private User user; // 当前用户对象
	private String actionTitle;

	/**
	 * 定义5个Fragment片段
	 */
	private UserAbout about; // 人员简介
	private UserDetail detail; // 人员详细
	private UserFamily family;// 家庭信息
	private UserResume resume; // 简历信息
	private UserMaterial material;//材料
	//private UserField  field;//自定义信息
	/**
	 * 底部五个按钮
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
		//ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
	
		userId = getIntent().getIntExtra("UserId", 0);
		user = UserDao.queryUser(userId);
		initView();
		String title = getIntent().getStringExtra("Title");
		if (title != null)actionBar.setTitle(title);
		
		
		setTabSelection(0);
		initFragment();
	}
	/** 
	 *  初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();//清空
		
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
	 *  ViewPager切换监听方法
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
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		

		switch (i) {
		case 0:

			setRodioButtonState(rbAbout, R.drawable.info_jj_h);
			mViewPager.setCurrentItem(i);
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbDetail, R.drawable.info_xx_h);
			mViewPager.setCurrentItem(i);
			break;
		
		case 2:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbResume, R.drawable.info_jl_h);
			mViewPager.setCurrentItem(i);
			break;
		case 3:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbFamily, R.drawable.info_home_h);
			mViewPager.setCurrentItem(i);
            break;
		case 4:
				// 当点击了动态tab时，改变控件的图片和文字颜色
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
	 * 按钮重置
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
	 * 设置RoidoButton图标
	 */
	private void setRodioButtonState(RadioButton btn, int id) {
		Drawable img_off;
		Resources res = getResources();
		img_off = res.getDrawable(id);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_off.setBounds(0, 0, img_off.getMinimumWidth(),
				img_off.getMinimumHeight());
		btn.setCompoundDrawables(null, img_off, null, null); // 设置左图标

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
		// 当点击不同的menu item 是执行不同的操作
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
	 * 打开任免审批表
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
			Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
		}

	}
	//android获取一个用于打开Excel文件的intent 
	  public static Intent getExcelFileIntent( String param )  { 
	    Intent intent = new Intent("android.intent.action.VIEW"); 
	    intent.addCategory("android.intent.category.DEFAULT"); 
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    Uri uri = Uri.fromFile(new File(param )); 
	    intent.setDataAndType(uri, "application/vnd.ms-excel"); 
	    return intent; 
	  } 
}
