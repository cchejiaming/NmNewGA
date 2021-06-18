package com.cxht.fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cxht.adapter.ResumeAdapter;
import com.cxht.dao.ResumeDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.Resume;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

public class UserResume extends Fragment {
	private int user_id;
	private Context context;
	private View view;
	private ListView basicListView; //视图列表
    private List<Resume> data; //数据列表
	private ResumeAdapter adapter; //适配
	/**
	 * 创建新实例
	 * 
	 * @param index
	 * @return
	 */
	public static UserResume newInstance(int userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("UserId", userId);
		UserResume fragment = new UserResume();
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
		view = LayoutInflater.from(context).inflate(R.layout.fragment_resume, null);
		basicListView = (ListView)view.findViewById(R.id.resume_ListView);
		initData();
		return view;
	}
	private List<Resume> getTimeSort(List<Resume> d){
	 
	   if (d!=null && d.size()>0){
		
		   for(Resume item:d){
			   String s_t = StringUtil.getShortDate(item.getStart_time(), "星");
			    s_t= s_t.trim();
			    item.setSort_time(StringUtil.getSortTime(s_t));
			    Log.i("TimeSort",item.getSort_time());
		   }
	   }
	   return d;
	}
	
	private void initData() {
		data=ResumeDao.getResumeList(UserDao.getUserCode(user_id));
		
		if (data!= null){
			data = getTimeSort(data);
			 sortClass sort = new sortClass();  
		     Collections.sort(data,sort);  
			adapter = new ResumeAdapter(context);
			adapter.addAll(data);
			basicListView.setAdapter(adapter);
		}
		
	}
	
	  
	public class sortClass implements Comparator{  
	    public int compare(Object arg0,Object arg1){  
	        Resume user0 = (Resume)arg0;  
	        Resume user1 = (Resume)arg1;  
	        int flag = user0.getSort_time().compareTo(user1.getSort_time());  
	        return flag;  
	    }  
	}
}
