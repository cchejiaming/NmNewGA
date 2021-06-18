package com.cxht.adapter;


import com.cxht.bean.StatColumnBean;
import com.gongan.manage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatisticsAdapter extends ArrayAdapter<StatColumnBean> {
	private LayoutInflater inflater;
	public StatisticsAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.statistics_item, null);
			viewHolder = new InfoHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.statistics_title);
		
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		StatColumnBean item = getItem(position);
		
		viewHolder.title.setText(item.getTitle());
	   
		return view;
	}

	public class InfoHolder {
		
		public TextView title;
	}
}
