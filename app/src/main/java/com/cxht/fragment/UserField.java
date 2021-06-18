package com.cxht.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cxht.adapter.UserFieldAdapter;
import com.cxht.dao.UserFieldDao;
import com.gongan.manage.R;

public class UserField extends Fragment {
	private int user_id;
	private Context context;
	private View view;
	private ListView basicListView; // 视图列表
	private List<com.cxht.entity.UserField> data; // 数据列表
	private UserFieldAdapter adapter; // 适配
	private TextView topTv;

	/**
	 * 创建新实例
	 *
	 * @return
	 */
	public static UserField newInstance(int userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("UserId", userId);
		UserField fragment = new UserField();
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.user_id = args != null ? args.getInt("UserId", 0) : 0;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_resume,
				null);
		basicListView = (ListView) view.findViewById(R.id.resume_ListView);
		topTv = (TextView) view.findViewById(R.id.top_title_tv);
		topTv.setText("自定义");
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		data = UserFieldDao.getUserField(user_id);
		if (data != null) {

			adapter = new UserFieldAdapter(context);
			adapter.addAll(data);
			basicListView.setAdapter(adapter);
		}
	}
}
