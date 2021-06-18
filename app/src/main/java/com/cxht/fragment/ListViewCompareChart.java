package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.cxht.adapter.RosterAdapter;
import com.cxht.adapter.RosterHeadAdapter;
import com.cxht.bean.RosterRow;
import com.cxht.bean.SearchParams;
import com.cxht.dao.JobDao;
import com.cxht.dao.UserDao;
import com.gongan.horizontal.scrollview.MyHScrollView.ScrollViewObserver;
import com.gongan.manage.R;

/**
 * ��Ա�Ա�
 */
public class ListViewCompareChart extends Fragment {
	private List<RosterRow> data;
	private ListView listView;
	private ListView mlistView;
	private RosterAdapter adapter;
	private RosterHeadAdapter mAdapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();

	private Context context;
	private View view;
	private String sqlItems = null;
	private SearchParams param = null;
	private String andWhere;
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();
	private HorizontalScrollView scroll;
	private String sort;

	public static ListViewCompareChart newInstance(String sqlItems,
			SearchParams param, String andWhere,String sort) {
		Bundle bundle = new Bundle();
		bundle.putString("SqlItems", sqlItems);
		bundle.putString("AndWhere", andWhere);
		bundle.putString("Sort",sort);
		bundle.putSerializable("SearchParam", param); // ���л�
		ListViewCompareChart fragment = new ListViewCompareChart();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.sqlItems = args.getString("SqlItems");
		this.param = (SearchParams) args.getSerializable("SearchParam");
		this.andWhere = args.getString("AndWhere");
		this.sort = args.getString("Sort"); //�������
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_compare,
				null);
		scroll = (HorizontalScrollView) view
				.findViewById(R.id.horizontalScrollView1);
		this.context = getActivity();
		//view = LayoutInflater.from(context).inflate(R.layout.fragment_compare,
			//	null);
		initDatas();
		if (data != null) {
			mlistView = (ListView) view.findViewById(R.id.tlistView);
			mAdapter = new RosterHeadAdapter(context, data);
			mlistView.setAdapter(mAdapter);
			getTotalHeightofListView(mlistView); // ��ȡ��ͷ�и�

			listView = (ListView) view.findViewById(R.id.mlistView);
			adapter = new RosterAdapter(context, data);
			listView.setAdapter(adapter);
			getTotalHeightofListView(listView); // ��ȡ��ͷ�и�

			mAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {

				}

			});
			setListViewOnTouchAndScrollListener(mlistView, listView);
		} else {
			Toast.makeText(context, "δ��ѯ��������ݣ�", Toast.LENGTH_LONG).show();
		}

		return view;
	}

	public void getTotalHeightofListView(ListView lv) {
		ListAdapter mAdapter = lv.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int scrollHeight = 0;
		int totalHeight = 0;
		for (int i = 0; i < data.size(); i++) {
			View mView = mAdapter.getView(i, null, lv);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight = mView.getMeasuredHeight();
			scrollHeight += totalHeight;
			data.get(i).setRowHight(totalHeight);
			Log.w("HEIGHThjm" + i, String.valueOf(totalHeight));
		}
		Log.i("HEIGHThjm", String.valueOf(scrollHeight));
		setScreetHeightToLayout(scroll, scrollHeight + 300);

	}

	private List<RosterRow> getDatas() {
		List<RosterRow> list = null;
		if (param != null && andWhere == null) {
			// ��ѯ�б�
			list = UserDao.queryRosterList(param, pageIndex,sort);
		} else if (sqlItems != null && andWhere != null) {
			list = UserDao.getRosterList(sqlItems, andWhere, pageIndex,sort);
		} else if (param != null && andWhere != null) {
			list = UserDao.getRosterList(param, andWhere, pageIndex,sort);
		} else if (sqlItems != null && andWhere == null) {
			//if(sort!=null)
				list = UserDao.getRosterList(sqlItems,pageIndex,sort);
			 	//else list = JobDao.getJobRosterList(sqlItems,pageIndex);
		} else if (andWhere!=null && param == null){
			list = UserDao.getRosterList("", andWhere, pageIndex,sort);
		}
		return list;
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
			Toast.makeText(context, "û�и�������", Toast.LENGTH_LONG).show();
		}

	}

	private void initDatas() {
		pageIndex = 0;
		isLoadAll = false;
		data = getDatas();
	}

	private void loadHandler(ListView lv) {

		if (lv.getLastVisiblePosition() >= (lv.getCount() - 1)) {

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
							// listView2.setSelectionFromTop(position, top);
						}
					}
				}

				if (scrollState == 0) {

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
	 * 
	 * @param ll
	 *            Ҫ��ӿհ�����ؼ�
	 * @param c
	 *            �ؼ��߶�
	 */
	private void setScreetHeightToLayout(HorizontalScrollView ll, int height) {
		DisplayMetrics metric = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
		int basicScreenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�
		if (height < basicScreenHeight) {
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll
					.getLayoutParams(); // ȡ�ؼ�textView��ǰ�Ĳ��ֲ���
			linearParams.height = basicScreenHeight;// �ؼ��ĸ߶�
			ll.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�
		}

	}
}
