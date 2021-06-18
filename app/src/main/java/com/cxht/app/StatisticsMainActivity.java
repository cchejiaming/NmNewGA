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
	 * ���ڶ�Fragment���й���
	 */
	private static FragmentManager fMgr;
	/**
	 * ����FragmentƬ��
	 */
	private StatisticsTableView sumTb;// �ܱ�
	private StatisticsTableView ageTb; // ����
	private StatisticsTableView eduTb; // ѧ��
	private StatisticsTableView jobTb; // ְ��ְ��

	/**
	 * �ײ���ť
	 */
	private RadioButton rbSum;
	private RadioButton rbAge;
	private RadioButton rbEdu;
	private RadioButton rbJd;
	private int id; // ��������
	private int type;// ������
	private String title;// ͳ�Ʊ���
	private String inWhere;// groupId�����ַ���

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.statistics_mian);
		ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
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
		// ���ð�ť
		resetBtn();
		// ����һ��Fragment����
		FragmentTransaction transaction = fMgr.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);

		switch (i) {
		case 0:
			setRodioButtonState(rbSum, R.drawable.stat_ry_h);
			if (sumTb == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				StatisticsTableView stv = new StatisticsTableView();
				sumTb = stv.newInstance(id, 1, title, inWhere);
				transaction.add(R.id.fragmentRoot, sumTb);

			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(sumTb);
			}
			break;

		case 1:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbAge, R.drawable.stat_wzlb_h);
			if (ageTb == null) {

				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				StatisticsTableView stv = new StatisticsTableView();
				ageTb = stv.newInstance(id, 2, title, inWhere);
				transaction.add(R.id.fragmentRoot, ageTb);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(ageTb);
			}

			break;
		case 2:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbEdu, R.drawable.stat_bzt_h);
			if (eduTb == null) {

				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				StatisticsTableView stv = new StatisticsTableView();
				eduTb = stv.newInstance(id, 3, title, inWhere);
				transaction.add(R.id.fragmentRoot, eduTb);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(eduTb);
			}

			break;
		case 3:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			setRodioButtonState(rbJd, R.drawable.stat_zzt_h);
			if (jobTb == null) {

				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				StatisticsTableView stv = new StatisticsTableView();
				jobTb = stv.newInstance(id, 4, title, inWhere);
				transaction.add(R.id.fragmentRoot, jobTb);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
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
	 * ��ť����
	 */
	private void resetBtn() {
		setRodioButtonState(rbSum, R.drawable.stat_ry);
		setRodioButtonState(rbAge, R.drawable.stat_wzlb);
		setRodioButtonState(rbEdu, R.drawable.stat_bzt);
		setRodioButtonState(rbJd, R.drawable.stat_zzt);
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

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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
