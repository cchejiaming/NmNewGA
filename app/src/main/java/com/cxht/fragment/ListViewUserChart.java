package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cxht.adapter.UserAdapter;
import com.cxht.app.GroupSearchActivity;
import com.cxht.app.UserInfoActivity;
import com.cxht.bean.SearchParams;
import com.cxht.config.ScreenManager;
import com.cxht.dao.JobDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.gongan.manage.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewUserChart extends Fragment {
	private List<User> data;
	private ListView listView;
	private UserAdapter adapter;
	private boolean isLoadAll = false;
	private int pageIndex = 0;
	private Handler mHandler = new Handler();
	private Context context;
	private View view;
	private String sqlItems = null;
	private SearchParams param = null;
	private String andWhere;
	private String sort;

	public static ListViewUserChart newInstance(String sqlItems,
			SearchParams param, String andWhere,String sort) {
		Bundle bundle = new Bundle();
		bundle.putString("SqlItems", sqlItems);
		bundle.putString("AndWhere", andWhere);
		bundle.putString("Sort",sort);
		bundle.putSerializable("SearchParam", param); // 序列化
		ListViewUserChart fragment = new ListViewUserChart();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.sqlItems = args.getString("SqlItems");
		this.param = (SearchParams) args.getSerializable("SearchParam");
		this.andWhere = args.getString("AndWhere"); // sql语句Where参数
		this.sort = args.getString("Sort"); //排序参数
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_userlist,
				null);
		initDatas();
		if (data != null) {
			listView = (ListView) view.findViewById(R.id.mlistView);
			adapter = new UserAdapter(context, data);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					User u = data.get(position);
					ScreenManager.getScreenManager().popActivityAppoint(
							GroupSearchActivity.class);
					Intent intent = new Intent(context, UserInfoActivity.class);
					intent.putExtra("UserId", u.getUser_id());
					startActivity(intent);

				}

			});
			listView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {

					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						if (listView.getLastVisiblePosition() >= (listView
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

				}

			});
		} else {
			Toast.makeText(context, "未查询到相关数据！", Toast.LENGTH_LONG).show();
		}

		return view;
	}

	private void loadData() {

		pageIndex++;
		List<User> loadData = new ArrayList<User>();
		loadData = getDatas();
		if (loadData != null) {
			data.addAll(loadData);
			adapter.notifyDataSetChanged();
		} else {
			isLoadAll = true;
			Toast.makeText(context, "没有更多啦！", Toast.LENGTH_LONG).show();
		}

	}

	private List<User> getDatas() {
		List<User> list = null;
		if (param != null && andWhere == null) {
			// 首页筛选查询过来的参数
			list = UserDao.queryUserList(param, pageIndex,sort);
		} else if (sqlItems != null && andWhere != null) {
			//机构信息+追加过滤条件
			list = UserDao.getUserList(sqlItems, andWhere, pageIndex,sort);
		} else if (param != null && andWhere != null) {
			// 查询参数 + 追加过滤条件
			list = UserDao.getUserList(param, andWhere, pageIndex,sort);
		} else if (sqlItems != null && andWhere == null) {
			//首页部门查询过来的参数
		   list = UserDao.getUserList(sqlItems,pageIndex,sort);

		} else if (andWhere!=null && param == null){
			//过滤条件
			list = UserDao.getUserList("", andWhere, pageIndex,sort);
		}
		return list;
	}

	private void initDatas() {
		pageIndex = 0;
		isLoadAll = false;
		data = getDatas();
	}
}
