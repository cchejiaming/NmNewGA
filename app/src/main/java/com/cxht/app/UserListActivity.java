package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cxht.adapter.UserAdapter;
import com.cxht.bean.SearchParams;
import com.cxht.config.ScreenManager;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.cxht.unit.SQLUtils;
import com.cxht.unit.StringUtil;
import com.gongan.horizontal.scrollview.bean.RecordColumn;
import com.gongan.manage.R;


public class UserListActivity extends BaseActivity {
	private List<User> data;
	private ListView listView;
	private UserAdapter adapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();
	private SearchParams param = null;
	private String sqlItems = null;
	private String actionTitle;
	private String andWhere;
	private RecordColumn rColumn = null;
	private int userCount;
	private String age,sql;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);
		ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
		param = (SearchParams) getIntent().getSerializableExtra("SearchParam"); // ����sql����
		rColumn = (RecordColumn) getIntent().getSerializableExtra(
				"recordColumn");// ��ȡͳ���б���ز�������
		sqlItems = getIntent().getStringExtra("SqlItems");// sql��֯ID�����ַ���
		andWhere = getIntent().getStringExtra("AndWhere"); // sql���Where����
		actionTitle = getTitile(); //������ƽ������
		if (actionTitle != null)
			actionBar.setTitle(actionTitle);
		initDatas();
		if (data != null) {
			listView = (ListView) findViewById(R.id.mlistView);
			adapter = new UserAdapter(UserListActivity.this, data);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					User u = data.get(position);
					Intent intent = new Intent(UserListActivity.this,
							UserInfoActivity.class);
					intent.putExtra("UserId", u.getUser_id());
					//ScreenManager.getScreenManager().popActivityAppoint(
							//UserSearchActivity.class);
					startActivity(intent);

				}

			});
			listView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {

					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						if (listView.getLastVisiblePosition() >= (listView
								.getCount() - 1)) {

							if (!isLoadAll) {

								mHandler.post(new Runnable() {
									public void run() {

										loadData();
									}

								});

							}
						}
					}

				}

			});
		} else {
			Toast.makeText(this, "δ��ѯ��������ݣ�", Toast.LENGTH_LONG).show();
		}

	}
	/**
	 * ������ƽ���������
	 * @return
	 */
	private String getTitile(){
		String title = null;
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
	private void loadData() {

		pageIndex++;
		List<User> loadData = new ArrayList<User>();
		loadData = getDatas();
		if (loadData != null) {
			data.addAll(loadData);
			adapter.notifyDataSetChanged();
		} else {
			isLoadAll = true;
			Toast.makeText(UserListActivity.this, "û�и�������", Toast.LENGTH_LONG)
					.show();
		}

	}

	private void initDatas() {

		data = getDatas();

	}

	private List<User> getDatas() {
		List<User> list = null;
		if (param != null && andWhere == null) {
			// ��ѯ�б�
			list = UserDao.queryUserList(param, pageIndex,null);
		} else if (sqlItems != null && andWhere != null) {
			list = UserDao.getUserList(sqlItems, andWhere, pageIndex,null);
		} else if (param != null && andWhere != null) {
			list = UserDao.getUserList(param, andWhere, pageIndex,null);
		} else if (rColumn != null) {
			list = UserDao.getUser(this, rColumn.getExecuteWhereSql(), pageIndex);

		}else if (andWhere!=null && param == null){
			list = UserDao.getUserList("", andWhere, pageIndex,null);
		}
		else {
			// �ղؼ��б�
			list = UserDao.getFavoriteUserList(this, pageIndex);
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (sqlItems == null && andWhere == null) {

			getMenuInflater().inflate(R.menu.userlist, menu);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// �������ͬ��menu item ��ִ�в�ͬ�Ĳ���
		switch (id) {

		case R.id.back_home:
			backHome();
			break;
		case R.id.list_stat:
			postStat();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void postStat() {
		Intent intent = new Intent(this, OrganizationMainActivity.class);
		intent.putExtra("Title", "��ѯ��Աͳ��");
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("SearchParam", param);
		intent.putExtra("andWhere", andWhere);
		intent.putExtras(mBundle);
		intent.putExtra("ShowPie", "show");
		startActivity(intent);
		this.finish();
	}

	private void backHome() {
		// �˳�����Actvity �Ʊ���MainActivity;
		ScreenManager.getScreenManager().popAllActivityExceptOne(
				MainActivity.class);
	}

}
