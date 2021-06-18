package com.cxht.app;

import com.cxht.config.GonganApplication;
import com.gongan.manage.R;
import com.wgs.jiesuo.LockPatternActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SystemSetActivity extends BaseActivity {
	private boolean isReg = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_set);
		if (GonganApplication.isRegKey(this)) isReg = false;
		//ScreenManager.getScreenManager().pushActivity(this); // 添加到Activity栈
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("系统设置");
		
		TextView versionTv = (TextView) findViewById(R.id.version_tv);
		versionTv.setText("版本：" + GonganApplication.getAppVerisonName());
		ListView mListView = (ListView) findViewById(R.id.more_ListView);
		// 添加ListItem，设置事件响应
		mListView.setAdapter(new ListAdapter());
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				onListItemClick(index);
			}
		});
	}

	private void onListItemClick(int index) {
		Intent intent = new Intent();
		switch (index) {
		case 0:
			GonganApplication.isExistsDataBase = false;
			intent.setClass(this, FileListActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent.setClass(this, EditPassActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent.setClass(this, LockPatternActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent.setClass(this, HistoryListActivity.class);
			startActivity(intent);
			break;
		case 4:
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			break;
	
		}

	}

	private static final MoreInfo[] mores = {

			new MoreInfo(R.drawable.set_sjgx, R.string.more_update,
					FileListActivity.class),
			new MoreInfo(R.drawable.set_ggmm, R.string.more_pass,
					FileListActivity.class),
			new MoreInfo(R.drawable.set_sssz, R.string.more_gesture,
					LockPatternActivity.class),
			new MoreInfo(R.drawable.set_sjgx, R.string.more_history,
					HistoryListActivity.class),
            new MoreInfo(R.drawable.set_gywm, R.string.more_about,
					LockPatternActivity.class),
		

	};

	private class ListAdapter extends BaseAdapter {
		public ListAdapter() {
			super();
		}

		public View getView(int index, View convertView, ViewGroup parent) {
			// Log.i("moreActivity","index:"+index);
			convertView = View.inflate(SystemSetActivity.this,
					R.layout.system_set_item, null);
			ImageView image = (ImageView) convertView
					.findViewById(R.id.image_moreitem);
			TextView title = (TextView) convertView
					.findViewById(R.id.text_moreitem);
			title.setText(mores[index].title);
			image.setBackgroundResource(mores[index].image);
			return convertView;
		}

		public int getCount() {
			return mores.length;
		}

		public Object getItem(int index) {
			return mores[index];
		}

		public long getItemId(int id) {
			return id;
		}
	}

	private static class MoreInfo {
		private final int image;
		private final int title;
		private final Class<? extends Activity> moreClass;

		public MoreInfo(int image, int title,
				Class<? extends Activity> moreClass) {
			this.image = image;
			this.title = title;
			this.moreClass = moreClass;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isReg) getMenuInflater().inflate(R.menu.reg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// 当点击不同的menu item 是执行不同的操作
		switch (id) {

		case R.id.reg_edit:
		    Intent intent = new Intent(this,RegActivity.class);
		    startActivity(intent);
		    break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
