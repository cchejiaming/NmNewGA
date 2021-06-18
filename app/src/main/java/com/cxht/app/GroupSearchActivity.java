package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.adapter.GroupTreeAdapter;
import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.config.ScreenManager;
import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.cxht.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import com.cxht.tree.TreeListViewAdapter.OnTreeNodeLongClickListener;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;


public class GroupSearchActivity extends BaseActivity {
	private List<GroupBean> mDatas = new ArrayList<GroupBean>();

	private ListView mTree;
	private TextView loadTv;
	private TreeListViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
		mTree = (ListView) findViewById(R.id.mlistView);
		 loadTv = (TextView) findViewById(R.id.loadTv);
		initDatas();
		
		try
		{
			mAdapter = new GroupTreeAdapter<GroupBean>(mTree, this, mDatas, 1);
			mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener(){
			    @Override
				public void onClick(Node node, int position) {
					// TODO Auto-generated method stub
			    	
			    	// String items = StringUtil.getSqlInItems(node.getId());
					if (node.isLeaf()) {
						
						String items = StringUtil.getSqlInItems(node.getId(),GroupSearchActivity.this);
						Toast.makeText(getApplicationContext(), node.getName(),
								Toast.LENGTH_SHORT).show();
						Intent intent  = new Intent(GroupSearchActivity.this,OrganizationMainActivity.class);
						intent.putExtra("SqlItems",items);
						intent.putExtra("Title", node.getName());
						intent.putExtra("GroupId",node.getId());
						startActivity(intent);
						
					}
			    	
			    
				}

			});
			mAdapter.setOnTreeNodeLongClickListener(new OnTreeNodeLongClickListener(){

				@Override
				public void onLongClick(Node node, int position) {
					// TODO Auto-generated method stub
                      if (!node.isLeaf()) {
						
						String items = String.valueOf(node.getId());
						Toast.makeText(getApplicationContext(), node.getName(),
								Toast.LENGTH_SHORT).show();
						Intent intent  = new Intent(GroupSearchActivity.this,OrganizationMainActivity.class);
						intent.putExtra("SqlItems",items);
						intent.putExtra("Title", node.getName());
						intent.putExtra("GroupId",node.getId());
						startActivity(intent);
						
					}
				}
				
			});

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		mTree.setAdapter(mAdapter);
	    
	    
	}
	private void initDatas() {
		 mDatas=GonganApplication.getGroupBeanList(GroupSearchActivity.this);
		 if (mDatas!= null){
				loadTv.setVisibility(View.GONE);
				mTree.setVisibility(View.VISIBLE);
			}else{
				loadTv.setText("没有收藏组织数据！");
			}
	}
}
