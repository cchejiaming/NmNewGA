package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import com.cxht.adapter.HistoryAdapter;
import com.cxht.bean.HistoryBean;
import com.cxht.config.ScreenManager;
import com.cxht.dao.HistoryDao;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class HistoryListActivity extends BaseActivity {
	private ListView mlistView;
	private List<HistoryBean> data;
	private HistoryAdapter adapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();
	private String actionTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_list);
		ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
		actionTitle = getIntent().getStringExtra("actionTitle");
		if (actionTitle != null)
			actionBar.setTitle(actionTitle);
		mlistView = (ListView) findViewById(R.id.mlistView);
		initData();
		if (data != null) {
			adapter = new HistoryAdapter(this, data);
			mlistView.setAdapter(adapter);
			mlistView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					Intent intent = new Intent();
					if (data.get(position).getItemType() == 1) {
						// ��Ա��ѯ
						intent.setClass(HistoryListActivity.this,
								UserInfoActivity.class);
						intent.putExtra("UserId", data.get(position)
								.getItemId());
						intent.putExtra("Title", "��Ա��Ϣ");
						startActivity(intent);
					}
					if (data.get(position).getItemType() == 2) {
						// ��֯��ѯ
						intent.setClass(HistoryListActivity.this,
								OrganizationMainActivity.class);
						intent.putExtra("GroupId", data.get(position)
								.getItemId());
						intent.putExtra("Title", data.get(position)
								.getItemTitle());
						String items = StringUtil.getSqlInItems(
								String.valueOf(data.get(position).getItemId()),
								HistoryListActivity.this);
						intent.putExtra("SqlItems", items);
						startActivity(intent);

					}

				}

			});
			mlistView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							HistoryListActivity.this);
					builder.setTitle("ȷ��ɾ��������");
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

									HistoryDao.deleteHistory(
											HistoryListActivity.this,
											data.get(position).getId());
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
			mlistView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {

					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						if (mlistView.getLastVisiblePosition() >= (mlistView
								.getCount() - 1)) {

							if (!isLoadAll) {

								mHandler.postDelayed(new Runnable() {
									public void run() {

										loadData();
									}

								}, 1000);

							}
						}
					}

				}

			});
		} else {
			Toast.makeText(this, "δ��ѯ��������ݣ�", Toast.LENGTH_LONG).show();
		}
	}

	private void loadData() {

		if (data != null) {
			pageIndex++;
			List<HistoryBean> loadData = new ArrayList<HistoryBean>();
			loadData = HistoryDao.getHistoryList(this, pageIndex);
			if (loadData != null) {
				data.addAll(loadData);
				adapter.notifyDataSetChanged();
			} else {
				isLoadAll = true;
				Toast.makeText(this, "û�и�������", Toast.LENGTH_LONG).show();
			}
		}

	}

	private void initData() {
		data = HistoryDao.getHistoryList(this, pageIndex);
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
			histroyAll();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * �����ʷ��¼
	 */

	private void histroyAll() {
		if (data != null) {
			HistoryDao.deleteAllHistory(this);
			data.removeAll(data);
			adapter.notifyDataSetChanged();
		}

	}
}

	