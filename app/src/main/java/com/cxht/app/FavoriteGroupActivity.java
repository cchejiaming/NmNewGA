package com.cxht.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.cxht.adapter.FavoriteAdapter;
import com.cxht.config.ScreenManager;
import com.cxht.dao.FavoriteGroupDao;
import com.cxht.dao.GroupDao;
import com.cxht.entity.Group;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

public class FavoriteGroupActivity extends BaseActivity {
	private ListView mlistView;
	private List<Group> data;
	private FavoriteAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_list);
		ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
		mlistView = (ListView) findViewById(R.id.mlistView);
		initData();
		if (data != null) {

			adapter = new FavoriteAdapter(FavoriteGroupActivity.this,data);
			mlistView.setAdapter(adapter);
			mlistView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					String groupId = String.valueOf(data.get(position).getGroup_id());
					String items = StringUtil.getSqlInItems(groupId,FavoriteGroupActivity.this);
					Intent intent  = new Intent(FavoriteGroupActivity.this,OrganizationMainActivity.class);
					intent.putExtra("SqlItems",items);
					intent.putExtra("Title", data.get(position).getGroup_name());
					intent.putExtra("GroupId",data.get(position).getGroup_id());
					startActivity(intent);
				}

				private Context getActivity() {
					// TODO Auto-generated method stub
					return null;
				}

			});
			mlistView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FavoriteGroupActivity.this);
					builder.setTitle("确定删除数据吗？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

									GroupDao.deleteFavoriteGroup(
											data.get(position).getGroup_id(),
											FavoriteGroupActivity.this);
									data.remove(position);
									adapter.notifyDataSetChanged();
								}

							});
					builder.setNegativeButton("取消",
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
		}else {
			Toast.makeText(this, "没有收藏记录！", Toast.LENGTH_LONG).show();
		}
	}

	private void initData() {
		data = GroupDao.getFavoriteGroupList(this);
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
		// 当点击不同的menu item 是执行不同的操作
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
	 * 清除记录
	 */

	private void delAllData() {
		FavoriteGroupDao.deleteAll(this);
		if (data!=null){
			data.removeAll(data);
			adapter.notifyDataSetChanged();
		}
	}

}
