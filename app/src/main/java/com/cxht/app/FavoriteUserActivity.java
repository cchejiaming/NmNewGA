package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.cxht.adapter.UserAdapter;
import com.cxht.bean.SearchParams;
import com.cxht.dao.FavoriteUserDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.gongan.manage.R;

public class FavoriteUserActivity extends BaseActivity {
	private List<User> data;
	private ListView listView;
	private UserAdapter adapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();
	private SearchParams param = null;
	private String actionTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);
		//ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
		param = getIntent().getParcelableExtra("SearchParam");
		actionTitle = getIntent().getStringExtra("actionTitle");
		if (actionTitle != null)
			actionBar.setTitle(actionTitle);
		initDatas();
		initView();

	}

	private void initView() {
		if (data != null) {
			listView = (ListView) findViewById(R.id.mlistView);
			adapter = new UserAdapter(FavoriteUserActivity.this, data);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					User u = data.get(position);
					Intent intent = new Intent(FavoriteUserActivity.this,
							UserInfoActivity.class);
					intent.putExtra("UserId", u.getUser_id());
					startActivity(intent);

				}

			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FavoriteUserActivity.this);
					builder.setTitle("ȷ��ɾ��������");
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

									UserDao.deleteFavoriteUser(
											data.get(position).getUser_id(),
											FavoriteUserActivity.this);
									data.remove(position);
									adapter.notifyDataSetChanged();
								}

							});
					builder.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

								}

							});
					builder.show();
					return true;
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
			Toast.makeText(this, "û���ղؼ�¼��", Toast.LENGTH_LONG).show();
		}
	}

	private void loadData() {

		if (param != null) {
			pageIndex++;
			List<User> loadData = new ArrayList<User>();
			loadData = getDatas();
			if (loadData != null) {
				data.addAll(loadData);
				adapter.notifyDataSetChanged();
			} else {
				isLoadAll = true;
				Toast.makeText(FavoriteUserActivity.this, "û�и�������",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	private void initDatas() {

		data = getDatas();

	}

	private List<User> getDatas() {
		List<User> list = null;
		if (param != null) {
			// ��ѯ�б�
			list = UserDao.queryUserList(param, pageIndex,null);
		} else {
			// �ղؼ��б�
			list = UserDao.getFavoriteUserList(this, pageIndex);
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// �������ͬ��menu item ��ִ�в�ͬ�Ĳ���
		switch (id) {

		case R.id.history_all:
			delAllData();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * �����¼
	 */

	private void delAllData() {
		FavoriteUserDao.deleteAll(this);
		if (data != null) {
			data.removeAll(data);
			adapter.notifyDataSetChanged();
		}

	}
}
