package com.cxht.app;

import java.util.List;

import com.cxht.adapter.StatisticsAdapter;
import com.cxht.bean.StatColumnBean;
import com.cxht.config.ScreenManager;
import com.cxht.config.Setting;
import com.gongan.manage.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StatisticsClassActivity extends BaseActivity {
	private ListView mlistView;
	private List<StatColumnBean> data;
	private StatisticsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_list);
		ScreenManager.getScreenManager().pushActivity(this); // ÃÌº”µΩActivity’ª
		mlistView = (ListView) findViewById(R.id.mlistView);
		initData();
		adapter = new StatisticsAdapter(StatisticsClassActivity.this);
		adapter.addAll(data);
		mlistView.setAdapter(adapter);
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				StatColumnBean item = data.get(position);
				if (item.getTitle().equals(Setting.TABLE_ZS_TITLE)) {
					Intent intent = new Intent(StatisticsClassActivity.this,
							GroupTreeActivity.class);
					intent.putExtra("id", data.get(position).getId());
					intent.putExtra("type", position);
					intent.putExtra("title", data.get(position).getTitle());
					startActivity(intent);
				} else {
					Intent intent = new Intent(StatisticsClassActivity.this,
							StatisticsMainActivity.class);
					intent.putExtra("id", data.get(position).getId());
					intent.putExtra("type", position);
					intent.putExtra("title", data.get(position).getTitle());
					startActivity(intent);
				}

			}

		});

	}

	private void initData() {
		data = Setting.getTableStatisticsList();
	}

}
