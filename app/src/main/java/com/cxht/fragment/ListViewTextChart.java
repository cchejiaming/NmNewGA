package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.cxht.adapter.InfoAdapter;
import com.cxht.app.BaseFragmentActivity;
import com.cxht.app.UserListActivity;
import com.cxht.bean.InfoItem;
import com.cxht.bean.SearchParams;
import com.cxht.bean.StatColumnBean;
import com.cxht.bean.StatResultBean;
import com.cxht.config.Setting;
import com.cxht.dao.UserDao;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

public class ListViewTextChart extends Fragment {
	private ListView mListView;
	private TextView titleText;
	private View view;
	private Context context;
	private List<InfoItem> data;
	private InfoAdapter adapter;
	private String sqlItems = null;
	private SearchParams param = null;
	private String andWhere = null;
	List<StatResultBean> stat = null; // 统计结果

	public static ListViewTextChart newInstance(String sqlItems,
			SearchParams param, String andWhere) {
		Bundle bundle = new Bundle();
		bundle.putString("SqlItems", sqlItems);
		bundle.putString("AndWhere", andWhere);
		bundle.putSerializable("SearchParam", param); // 序列化
		ListViewTextChart fragment = new ListViewTextChart();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.sqlItems = args.getString("SqlItems");
		this.param = (SearchParams) args.getSerializable("SearchParam");
		this.andWhere = args.getString("AndWhere"); // sql语句Where参数
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_detail,
				null);
		mListView = (ListView) view.findViewById(R.id.basic_ListView);

		initData();
		if (data != null) {
			adapter = new InfoAdapter(context, data);
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					// 如果点击的不是标题
					if (data.get(position).getFlag() != 0) {
						// 跳转到人员列表页面
						String where =StringUtil.getWhere(data.get(position).getWhere());
						if (where!=null ){
								Intent intent = new Intent(getActivity(),UserListActivity.class);
								intent.putExtra("AndWhere", where);
								startActivity(intent);
						}

					}
				}

			});

		} else {
			TextView hintText = (TextView) view.findViewById(R.id.hintText);
			mListView.setVisibility(View.GONE);
			titleText.setVisibility(View.GONE);
			hintText.setVisibility(View.VISIBLE);
			hintText.setText("No list data available");
		}

		return view;
	}

	private void initData() {
		data = new ArrayList<InfoItem>();
		List<StatColumnBean> column = Setting.getStatisticsList(); // 统计项目


		for (int i = 0; i < column.size(); i++) {

			// 添加标题
			InfoItem columnTitle = new InfoItem(column.get(i).getTitle(), null);
			columnTitle.setFlag(0);
			data.add(columnTitle);
			// 获得统计信息
			if (sqlItems != null) {
				stat = UserDao.getDataList(column.get(i).getId(), sqlItems);

			}
			if (param != null) {
				stat = UserDao.getDataList1(column.get(i).getId(),
						param.toWhereSql());
			}
			if (param == null && andWhere != null) {
				String where = " where 1=1 " + andWhere;
				stat = UserDao.getDataList1(column.get(i).getId(), where);
			}
			if (stat != null) {
				for (StatResultBean item : stat) {
					// 添加统计内容
					InfoItem iItem = new InfoItem(item.getName(),
							String.valueOf(item.getCount()));
					iItem.setColumnTitle(columnTitle.getTitle());
					iItem.setWhere(item.getAndWhere());
					data.add(iItem);
				}
			}
		}


	}

}
