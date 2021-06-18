package com.cxht.fragment;

import java.util.List;

import com.cxht.adapter.FamilyAdapter;
import com.cxht.dao.FamilyDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.Family;
import com.gongan.manage.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserFamily extends Fragment {
	private int user_id;
	private Context context;
	private View view;
	private ListView basicListView; //视图列表
    private List<Family> data; //数据列表
	private FamilyAdapter adapter; //适配
	/**
	 * 创建新实例
	 *
	 * @return
	 */
	public static UserFamily newInstance(int userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("UserId", userId);
		UserFamily fragment = new UserFamily();
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
		view = LayoutInflater.from(context).inflate(R.layout.fragment_family, null);
		basicListView = (ListView)view.findViewById(R.id.family_ListView);
		initData();
		return view;
	}
	private void initData() {
		// TODO Auto-generated method stub
		data = FamilyDao.getFamilyList(UserDao.getUserCode(user_id));
		if (data!=null){
			adapter = new FamilyAdapter(context);
			adapter.addAll(data);
			basicListView.setAdapter(adapter);
		}
	}
}
