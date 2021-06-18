package com.cxht.app;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.cxht.config.ScreenManager;
import com.cxht.fragment.StatisticsTableView;
import com.gongan.manage.R;

public class StatisticsMainActivity extends BaseFragmentActivity implements
		OnClickListener {

	/**
	 * 用于对Fragment进行管理
	 */
	private static FragmentManager fMgr;
	/**
	 * 定义Fragment片段
	 */
	private StatisticsTableView sumTb;// 总表
	private StatisticsTableView ageTb; // 年龄
	private StatisticsTableView eduTb; // 学历
	private StatisticsTableView jobTb; // 职务、职级

	/**
	 * 底部按钮
	 */
	private RadioButton rbSum;
	private RadioButton rbAge;
	private RadioButton rbEdu;
	private RadioButton rbJd;
	private int id; // 地区分类
	private int type;// 表类型
	private String title;// 统计标题
	private String inWhere;// groupId集合字符串

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.statistics_mian);
		ScreenManager.getScreenManager().pushActivity(this); // 添加到Activity栈
		id = getIntent().getIntExtra("id", 0);
		type = getIntent().getIntExtra("type", 0);
		title = getIntent().getStringExtra("title");
		inWhere = getIntent().getStringExtra("inWhere");
		actionBar.setTitle(title);
		fMgr = getSupportFragmentManager();
		initFragment();
		setTabSelection(0);

	}

	private void setTabSelection(int i) {
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fMgr.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);

		switch (i) {
		case 0:
			setRodioButtonState(rbSum, R.drawable.stat_ry_h);
			if (sumTb == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				StatisticsTableView stv = new StatisticsTableView();
				sumTb = stv.newInstance(id, 1, title, inWhere);
				transaction.add(R.id.fragmentRoot, sumTb);

			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(sumTb);
			}
			break;

		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbAge, R.drawable.stat_wzlb_h);
			if (ageTb == null) {

				// 如果MessageFragment为空，则创建一个并添加到界面上
				StatisticsTableView stv = new StatisticsTableView();
				ageTb = stv.newInstance(id, 2, title, inWhere);
				transaction.add(R.id.fragmentRoot, ageTb);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(ageTb);
			}

			break;
		case 2:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbEdu, R.drawable.stat_bzt_h);
			if (eduTb == null) {

				// 如果MessageFragment为空，则创建一个并添加到界面上
				StatisticsTableView stv = new StatisticsTableView();
				eduTb = stv.newInstance(id, 3, title, inWhere);
				transaction.add(R.id.fragmentRoot, eduTb);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(eduTb);
			}

			break;
		case 3:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			setRodioButtonState(rbJd, R.drawable.stat_zzt_h);
			if (jobTb == null) {

				// 如果MessageFragment为空，则创建一个并添加到界面上
				StatisticsTableView stv = new StatisticsTableView();
				jobTb = stv.newInstance(id, 4, title, inWhere);
				transaction.add(R.id.fragmentRoot, jobTb);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(jobTb);
			}

			break;
		}
		transaction.commit();

	}

	private void initFragment() {
		rbSum = (RadioButton) findViewById(R.id.rbSum);
		rbAge = (RadioButton) findViewById(R.id.rbAge);
		rbEdu = (RadioButton) findViewById(R.id.rbEdu);
		rbJd = (RadioButton) findViewById(R.id.rbJd);
		rbSum.setOnClickListener(this);
		rbAge.setOnClickListener(this);
		rbEdu.setOnClickListener(this);
		rbJd.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rbSum:
			setTabSelection(0);
			break;

		case R.id.rbAge:
			setTabSelection(1);
			break;
		case R.id.rbEdu:
			setTabSelection(2);
			break;
		case R.id.rbJd:
			setTabSelection(3);
			break;
		}

	}

	/**
	 * 按钮重置
	 */
	private void resetBtn() {
		setRodioButtonState(rbSum, R.drawable.stat_ry);
		setRodioButtonState(rbAge, R.drawable.stat_wzlb);
		setRodioButtonState(rbEdu, R.drawable.stat_bzt);
		setRodioButtonState(rbJd, R.drawable.stat_zzt);
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

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (sumTb != null) {
			transaction.hide(sumTb);
		}
		if (ageTb != null) {
			transaction.hide(ageTb);
		}
		if (eduTb != null) {
			transaction.hide(eduTb);
		}
		if (jobTb != null) {
			transaction.hide(jobTb);
		}
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity,
				R.anim.move_right_out_activity);
	}
}
