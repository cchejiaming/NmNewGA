package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import com.cxht.adapter.SimpleTreeAdapter;
import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.config.ScreenManager;
import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.cxht.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import com.gongan.manage.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class GroupListActivity extends BaseActivity {
	private List<GroupBean> mDatas = new ArrayList<GroupBean>();
	private ListView mTree;
	private TreeListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		ScreenManager.getScreenManager().pushActivity(GroupListActivity.this); // ÃÌº”µΩActivity’ª
		initDatas();
		mTree = (ListView) findViewById(R.id.mlistView);
		try {
			mAdapter = new SimpleTreeAdapter<GroupBean>(mTree, this, mDatas, 1);
			mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {

				public void onClick(Node node, int position) {
					if (node.isLeaf()) {
						Toast.makeText(getApplicationContext(), node.getName(),
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(GroupListActivity.this,
								OrganizationMainActivity.class);
						startActivity(intent);
					}
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		mTree.setAdapter(mAdapter);

	}

	private void initDatas() {

		mDatas = GonganApplication.getGroupBeanList(GroupListActivity.this);

	}

}
