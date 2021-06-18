package com.cxht.app;

import java.util.ArrayList;

import com.cxht.adapter.BasePageAdapter;
import com.cxht.bean.SearchParams;
import com.cxht.config.ScreenManager;
import com.cxht.dao.HistoryDao;
import com.cxht.dao.UserDao;
import com.cxht.fragment.ListViewCompareChart;
import com.cxht.fragment.ListViewPieChart;
import com.cxht.fragment.ListViewTextChart;
import com.cxht.fragment.ListViewUserChart;
import com.cxht.unit.DialogUtil;
import com.cxht.unit.SQLUtils;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class OrganizationMainActivity extends BaseFragmentActivity implements
		OnClickListener {
	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	/**
	 * ����FragmentƬ��
	 */
	private ListViewUserChart user; // ��Ա�б�
    private ListViewCompareChart bar; // ��Ա�Ա�
	private ListViewPieChart pie; // ��״��ͼ
	private ListViewTextChart list; // ���ֱ�ͼ

	/**
	 * �ײ���ť
	 */
	private RadioButton rbUser;
	private RadioButton rbPie;
	private RadioButton rbBar;
	private RadioButton rbList;
	private String sqlItems = null; //����
	private SearchParams param = null; //��ѯ��������
	private String andWhere = null; //��������
	public static String sort = null; //��������
	private int groupId;
    private int userCount;
    private String age,sql;
    private boolean isFav = false;
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.organization_main);
		ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
		initView();
		String title = getIntent().getStringExtra("Title");
		sqlItems = getIntent().getStringExtra("SqlItems");
		param = (SearchParams) getIntent().getSerializableExtra("SearchParam");
	    andWhere = getIntent().getStringExtra("AndWhere");
		groupId = getIntent().getIntExtra("GroupId", -1);
		isFav = getIntent().getBooleanExtra("IsFav",false);
	    title = getTitile(); //������ƽ������
		if (title != null)
			actionBar.setTitle(title);
		setTabSelection(0);
		initFragment();
		// �ж��Ƿ���ʾ��״��ͼ
		String showPie = getIntent().getStringExtra("ShowPie");
		if (showPie != null) {

			rbPie.performClick();
		}

		// mViewPager.setCurrentItem(2);
		// ��ʷ��¼
		HistoryDao.wirteHistoryToBase(this, title, groupId, 2);

	}

	/**
	 * ������ƽ���������
	 * @return
	 */
    private String getTitile(){
		String title = "";
		if (param != null){
			userCount = UserDao.getUserCount(param);
			String _andWhere = param.getWhereSql();
			sql = SQLUtils.averageAge(_andWhere);
			double ageDouble = UserDao.getUserAge(this, sql);
			age = StringUtil.toAgeStr(ageDouble);
			title = "����"+userCount +"��  ƽ������["+age+"��]";
		}
		if (sqlItems!=null){
			userCount = UserDao.getUserCount(sqlItems);
			sql = SQLUtils.averageAgeOfGroup(sqlItems);
			double ageDouble = UserDao.getUserAge(this, sql);
			age = StringUtil.toAgeStr(ageDouble);
			title = "����"+userCount +"��  ƽ������["+age+"��]";
		}
		if (andWhere!=null){
			sql = SQLUtils.userAddWhere(andWhere);
			userCount = UserDao.getUserCountFormSql(sql);
			sql = SQLUtils.averageAge(andWhere);
			double ageDouble = UserDao.getUserAge(this, sql);
			age = StringUtil.toAgeStr(ageDouble);
			title = "����"+userCount +"��  ƽ������["+age+"��]";
		}
		return  title;
	}
	private void setTabSelection(int i) {
		// ���ð�ť
		resetBtn();

		switch (i) {
		case 0:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbUser, R.drawable.stat_ry_h);
			mViewPager.setCurrentItem(i);
			break;
		case 1:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbBar, R.drawable.stat_zzt_h);
			mViewPager.setCurrentItem(i);

			break;
		case 2:
			setRodioButtonState(rbPie, R.drawable.stat_bzt_h);
			mViewPager.setCurrentItem(i);
			break;
		case 3:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbList, R.drawable.stat_wzlb_h);
			mViewPager.setCurrentItem(i);
			break;
		}

	}

	/**
	 * ��ʼ��Fragment
	 * */
	private void initFragment() {
		fragments.clear();// ���
		user = ListViewUserChart.newInstance(sqlItems, param, andWhere,sort);
		bar = ListViewCompareChart.newInstance(sqlItems, param, andWhere,sort);
		pie = ListViewPieChart.newInstance(sqlItems, param, andWhere);
		list = ListViewTextChart.newInstance(sqlItems, param, andWhere);
		fragments.add(user);
		fragments.add(bar);
		fragments.add(pie);
		fragments.add(list);

		BasePageAdapter mAdapetr = new BasePageAdapter(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(mAdapetr);
		// mViewPager.setOffscreenPageLimit(2);
		mViewPager.setOnPageChangeListener(pageListener);

	}

	/**
	 * ViewPager�л���������
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {

			setTabSelection(position);
			mViewPager.setCurrentItem(position);
		}
	};

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		rbUser = (RadioButton) findViewById(R.id.rbUser);
		rbPie = (RadioButton) findViewById(R.id.rbPie);
		rbBar = (RadioButton) findViewById(R.id.rbBar);
		rbList = (RadioButton) findViewById(R.id.rbList);
		rbUser.setOnClickListener(this);
		rbBar.setOnClickListener(this);
		rbPie.setOnClickListener(this);
		rbList.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rbUser:
			setTabSelection(0);
			break;
		case R.id.rbBar:
			setTabSelection(1);
			break;
		case R.id.rbPie:
			setTabSelection(2);
			break;
		case R.id.rbList:
			setTabSelection(3);
			break;

		}

	}

	/**
	 * ��ť����
	 */
	private void resetBtn() {
		setRodioButtonState(rbUser, R.drawable.stat_ry);
		setRodioButtonState(rbBar, R.drawable.stat_zzt);
		setRodioButtonState(rbPie, R.drawable.stat_bzt);
		setRodioButtonState(rbList, R.drawable.stat_wzlb);
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

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity,
				R.anim.move_right_out_activity);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isFav) getMenuInflater().inflate(R.menu.fav, menu); else getMenuInflater().inflate(R.menu.sort, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent = new Intent();
		// �������ͬ��menu item ��ִ�в�ͬ�Ĳ���
		switch (id) {
		case R.id.fav_edit:
			intent.setClass(this,FavoriteUserActivity.class);
		    startActivity(intent);
			this.finish();
			break;
			case R.id.sortRecord:
				DialogUtil.createSortCriteriaDialog(this,"��������", new DialogUtil.OnDialogParamResultListener <String>( ) {
					@Override
					public void OnDialogResult(String param) {
						sort = param;
						setTabSelection(0);
						initFragment();
					}
				});
				break;
			case R.id.addedSearch:
				if (andWhere!=null){
					if (sqlItems!=null) andWhere+= SQLUtils.sqlItemsToAndWhere(sqlItems);
					if(param!=null) andWhere += param.getWhereSql();
				}else{
					if (sqlItems!=null) andWhere= SQLUtils.sqlItemsToAndWhere(sqlItems);
					if(param!=null) andWhere = param.getWhereSql();
				}
				intent.setClass(this,UserSearchActivity.class);
				intent.putExtra("AndWhere",andWhere);
				startActivity(intent);
				break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
