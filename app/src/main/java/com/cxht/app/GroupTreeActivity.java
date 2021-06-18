package com.cxht.app;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.cxht.config.Setting;
import com.gongan.manage.R;
import com.gov.tree.EduTreeAdapter;
import com.gov.tree.TreeNodeCheck;


public class GroupTreeActivity extends BaseActivity {
	private List<TreeNodeCheck> mDatas ;
	private Button okBtn;
	private ListView listView;
	private int id; // 地区分类
	private int type;//表类型
	private String title;// 统计标题
	private String inWhere;//groupId集合字符串
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_tree_nsjg);
		id = getIntent().getIntExtra("id", 0);
		type= getIntent().getIntExtra("type", 0);
		title = getIntent().getStringExtra("title");
		mDatas = Setting.getGroupTree(this);
		listView = (ListView) findViewById(R.id.group_lv);
		okBtn =(Button)findViewById(R.id.ok_btn);
		final EduTreeAdapter mAdapter;
		try {
			mAdapter = new EduTreeAdapter(listView, this, mDatas, 1);
			listView.setAdapter(mAdapter);
			okBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				 inWhere =getTreeParam(mAdapter.mAllNodes);
				 Intent intent = new Intent(GroupTreeActivity.this,StatisticsMainActivity.class);
			     intent.putExtra("id",id);
			     intent.putExtra("type",type);
			     intent.putExtra("title",title);
			     intent.putExtra("inWhere",inWhere);
			      startActivity(intent);
				// Toast.makeText(GroupTreeActivity.this, inWhere,
						//Toast.LENGTH_SHORT).show();
				}
				
			});
		

		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static String getTreeParam(List<TreeNodeCheck> l) {
	 String ret = "";
		if (l != null) {
		
			for (TreeNodeCheck et : l) {
				if (et.isCheck() && et.isParam()) {
					ret +="'"+et.getId()+"',";
				}
			}
		}
		if (ret.length() >1) ret = ret.substring(0,ret.length()-1);
		return ret;
	}
}
