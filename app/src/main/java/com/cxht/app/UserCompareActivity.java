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
		ScreenManager.getScreenManager().pushActivity(this); // 添加到Activity栈
		param = (SearchParams) getIntent().getSerializableExtra("search"); // 搜索sql参数
		actionTitle = getIntent().getStringExtra("actionTitle");// 页面标题
		sqlItems = getIntent().getStringExtra("SqlItems");//sql组织ID集合字符串
		andWhere = getIntent().getStringExtra("AndWhere"); // sql语句Where参数
		scroll =(HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		
		if (actionTitle != null)
			actionBar.setTitle(actionTitle);
		initDatas();
		if (data != null) {
			 orgDescTv = (TextView)findViewById(R.id.orgDescTv);
		
		
			mlistView = (ListView) findViewById(R.id.tlistView);
		    mAdapter = new RosterHeadAdapter(UserCompareActivity.this, data);
			mlistView.setAdapter(mAdapter);
			getTotalHeightofListView(mlistView); // 获取表头行高
			
			listView = (ListView) findViewById(R.id.mlistView);
			adapter = new RosterAdapter(UserCompareActivity.this, data);
			listView.setAdapter(adapter);
			getTotalHeightofListView(listView); // 获取表头行高
		    
			mAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
			
		
			listView.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, final int position, long id) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							UserCompareActivity.this);
					builder.setTitle("确定删除对比数据吗？");
					builder.setPositiveButton("确定",
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
			setListViewOnTouchAndScrollListener(mlistView,listView);
	 
			
		} else {
			Toast.makeText(this, "未查询到相关数据！", Toast.LENGTH_LONG).show();
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
			getTotalHeightofListView(mlistView); // 获取表头行高
			adapter.notifyDataSetChanged();
			getTotalHeightofListView(listView); // 获取表头行高
			mAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		} else {
			isLoadAll = true;
			Toast.makeText(UserCompareActivity.this, "没有更多啦！", Toast.LENGTH_LONG)
					.show();
		}

	}

	private void initDatas() {

		data = getDatas();

	}
	 private  List<RosterRow> getDatas(){
		 List<RosterRow> list = null;
		 if (param != null && andWhere == null){
			 //查询列表
		       list = UserDao.queryRosterList(param,pageIndex,null);
		  }else if (sqlItems!= null && andWhere != null){
			   list = UserDao.getRosterList(sqlItems, andWhere, pageIndex,null);
		  }else if (param!=null && andWhere !=null){
			    list = UserDao.getRosterList(param,andWhere,pageIndex,null);
		  } else{
			  //收藏夹列表
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

		// 设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
		listView2.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 如果停止滑动
				if (scrollState == 0 || scrollState == 1) {
					// 获得第一个子view
					View subView = view.getChildAt(0);

					if (subView != null) {
						final int top = subView.getTop();
						final int top1 = listView1.getChildAt(0).getTop();
						final int position = view.getFirstVisiblePosition();

						// 如果两个首个显示的子view高度不等
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

					// 如果两个首个显示的子view高度不等
					int top1 = listView1.getChildAt(0).getTop();
					if (!(top1 - 7 < top && top < top1 + 7)) {
						listView1.setSelectionFromTop(firstVisibleItem, top);
						listView2.setSelectionFromTop(firstVisibleItem, top);
					}

				}
			}
		});

		// 设置listview1列表的scroll监听，用于滑动过程中左右不同步时校正
		listView1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == 0 || scrollState == 1) {
					// 获得第一个子view
					View subView = view.getChildAt(0);

					if (subView != null) {
						final int top = subView.getTop();
						final int top1 = listView2.getChildAt(0).getTop();
						final int position = view.getFirstVisiblePosition();

						// 如果两个首个显示的子view高度不等
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
	 * 计算并设置下面空白区域
	 * @param ll 要添加空白区域控件
	 * @param c  控件高度
	 */
   private void setScreetHeightToLayout(HorizontalScrollView ll,int height){
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int basicScreenHeight = metric.heightPixels; // 屏幕高度（像素）
		if (height<basicScreenHeight){
			LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) ll.getLayoutParams(); //取控件textView当前的布局参数
			linearParams.height = basicScreenHeight;// 控件的高度
			ll.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
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
		FavoriteUserDao.deleteAll(this);
		if (data!=null){
			data.removeAll(data);
			adapter.notifyDataSetChanged();
		}
		
	}
	
}
