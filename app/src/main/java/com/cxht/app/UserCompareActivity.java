package com.cxht.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.cxht.config.ScreenManager;
import com.gongan.horizontal.scrollview.MyHScrollView.ScrollViewObserver;
import com.gongan.manage.R;
import com.cxht.adapter.RosterAdapter;
import com.cxht.adapter.RosterHeadAdapter;
import com.cxht.bean.RosterRow;
import com.cxht.bean.SearchParams;
import com.cxht.dao.FavoriteUserDao;
import com.cxht.dao.UserDao;


public class UserCompareActivity extends BaseActivity {
	private List<RosterRow> data;
	private ListView listView;
	private ListView mlistView;
	private RosterAdapter adapter;
	private RosterHeadAdapter mAdapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();
	private SearchParams param = null;
	private String sqlItems = null;
	private String actionTitle;
	private String andWhere;
	private TextView orgDescTv;
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();
    private HorizontalScrollView scroll;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gov_user_list);
		ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
		param = (SearchParams) getIntent().getSerializableExtra("search"); // ����sql����
		actionTitle = getIntent().getStringExtra("actionTitle");// ҳ�����
		sqlItems = getIntent().getStringExtra("SqlItems");//sql��֯ID�����ַ���
		andWhere = getIntent().getStringExtra("AndWhere"); // sql���Where����
		scroll =(HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		
		if (actionTitle != null)
			actionBar.setTitle(actionTitle);
		initDatas();
		if (data != null) {
			 orgDescTv = (TextView)findViewById(R.id.orgDescTv);
		
		
			mlistView = (ListView) findViewById(R.id.tlistView);
		    mAdapter = new RosterHeadAdapter(UserCompareActivity.this, data);
			mlistView.setAdapter(mAdapter);
			getTotalHeightofListView(mlistView); // ��ȡ��ͷ�и�
			
			listView = (ListView) findViewById(R.id.mlistView);
			adapter = new RosterAdapter(UserCompareActivity.this, data);
			listView.setAdapter(adapter);
			getTotalHeightofListView(listView); // ��ȡ��ͷ�и�
		    
			mAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
			
		
			listView.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, final int position, long id) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							UserCompareActivity.this);
					builder.setTitle("ȷ��ɾ���Ա�������");
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

									UserDao.deleteFavoriteUser(
											data.get(position).getUser().getUser_id(),
											UserCompareActivity.this);
									data.remove(position);
									adapter.notifyDataSetChanged();
									mAdapter.notifyDataSetChanged();
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
			setListViewOnTouchAndScrollListener(mlistView,listView);
	 
			
		} else {
			Toast.makeText(this, "δ��ѯ��������ݣ�", Toast.LENGTH_LONG).show();
		}

	}
  
	public void getTotalHeightofListView(ListView lv) {
		ListAdapter mAdapter = lv.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int scrollHeight =0;
		int totalHeight = 0;
		for (int i = 0; i < data.size(); i++) {
			View mView = mAdapter.getView(i, null, lv);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight = mView.getMeasuredHeight();
			scrollHeight +=totalHeight;
			data.get(i).setRowHight(totalHeight);
			Log.w("HEIGHThjm" + i, String.valueOf(totalHeight));
		}
		Log.i("HEIGHThjm", String.valueOf(scrollHeight));
		setScreetHeightToLayout(scroll,scrollHeight+300);

	}

	private void loadData() {

		pageIndex++;
		List<RosterRow> loadData = new ArrayList<RosterRow>();
		loadData = getDatas();
		if (loadData != null) {
			data.addAll(loadData);
		    mAdapter.notifyDataSetChanged();
			getTotalHeightofListView(mlistView); // ��ȡ��ͷ�и�
			adapter.notifyDataSetChanged();
			getTotalHeightofListView(listView); // ��ȡ��ͷ�и�
			mAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		} else {
			isLoadAll = true;
			Toast.makeText(UserCompareActivity.this, "û�и�������", Toast.LENGTH_LONG)
					.show();
		}

	}

	private void initDatas() {

		data = getDatas();

	}
	 private  List<RosterRow> getDatas(){
		 List<RosterRow> list = null;
		 if (param != null && andWhere == null){
			 //��ѯ�б�
		       list = UserDao.queryRosterList(param,pageIndex,null);
		  }else if (sqlItems!= null && andWhere != null){
			   list = UserDao.getRosterList(sqlItems, andWhere, pageIndex,null);
		  }else if (param!=null && andWhere !=null){
			    list = UserDao.getRosterList(param,andWhere,pageIndex,null);
		  } else{
			  //�ղؼ��б�
			   list =UserDao.getFavoriteRosterList(this, pageIndex);
		  }
		 return list;
	 }


    private void loadHandler(ListView lv){
    	
		if (lv.getLastVisiblePosition() >= (lv
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
	public void setListViewOnTouchAndScrollListener(final ListView listView1,
			final ListView listView2) {

		// ����listview2�б��scroll���������ڻ������������Ҳ�ͬ��ʱУ��
		listView2.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// ���ֹͣ����
				if (scrollState == 0 || scrollState == 1) {
					// ��õ�һ����view
					View subView = view.getChildAt(0);

					if (subView != null) {
						final int top = subView.getTop();
						final int top1 = listView1.getChildAt(0).getTop();
						final int position = view.getFirstVisiblePosition();

						// ��������׸���ʾ����view�߶Ȳ���
						if (top != top1) {
							listView1.setSelectionFromTop(position, top);
							//listView2.setSelectionFromTop(position, top);
						}
					}
				}
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					loadHandler(listView2);
				}
			}

			public void onScroll(AbsListView view, final int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				View subView = view.getChildAt(0);
				if (subView != null) {
					final int top = subView.getTop();

					// ��������׸���ʾ����view�߶Ȳ���
					int top1 = listView1.getChildAt(0).getTop();
					if (!(top1 - 7 < top && top < top1 + 7)) {
						listView1.setSelectionFromTop(firstVisibleItem, top);
						listView2.setSelectionFromTop(firstVisibleItem, top);
					}

				}
			}
		});

		// ����listview1�б��scroll���������ڻ������������Ҳ�ͬ��ʱУ��
		listView1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == 0 || scrollState == 1) {
					// ��õ�һ����view
					View subView = view.getChildAt(0);

					if (subView != null) {
						final int top = subView.getTop();
						final int top1 = listView2.getChildAt(0).getTop();
						final int position = view.getFirstVisiblePosition();

						// ��������׸���ʾ����view�߶Ȳ���
						if (top != top1) {
							listView1.setSelectionFromTop(position, top);
							listView2.setSelectionFromTop(position, top);
						}
					}
				}
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					loadHandler(listView1);
				}
			}

			@Override
			public void onScroll(AbsListView view, final int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				View subView = view.getChildAt(0);
			
				
				
				if (subView != null) {
					final int top = subView.getTop();
					listView1.setSelectionFromTop(firstVisibleItem, top);
					listView2.setSelectionFromTop(firstVisibleItem, top);

				}
			}
		});
	}
	/**
	 * ���㲢��������հ�����
	 * @param ll Ҫ��ӿհ�����ؼ�
	 * @param c  �ؼ��߶�
	 */
   private void setScreetHeightToLayout(HorizontalScrollView ll,int height){
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int basicScreenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�
		if (height<basicScreenHeight){
			LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) ll.getLayoutParams(); //ȡ�ؼ�textView��ǰ�Ĳ��ֲ���
			linearParams.height = basicScreenHeight;// �ؼ��ĸ߶�
			ll.setLayoutParams(linearParams); //ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�
		}
		  
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
		if (data!=null){
			data.removeAll(data);
			adapter.notifyDataSetChanged();
		}
		
	}
	
}
