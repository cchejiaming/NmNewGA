package com.cxht.adapter;

import java.util.List;

import com.cxht.bean.HistoryBean;
import com.cxht.config.Setting;
import com.gongan.manage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<HistoryBean> data;

	public HistoryAdapter(Context context, List<HistoryBean> data) {
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.history_item, null);
			viewHolder = new InfoHolder();
			viewHolder.titleText = (TextView) view
					.findViewById(R.id.title_Text);
			viewHolder.timeText = (TextView) view.findViewById(R.id.time_text);
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		HistoryBean item = data.get(position);
		String title = item.getItemTitle() + " ["
				+ Setting.getHistoryTypeName(item.getItemType()) + "]";
		viewHolder.titleText.setText(title);
		viewHolder.timeText.setText(item.getAddTime());
		return view;
	}

	public class InfoHolder {
		public TextView titleText;
		public TextView timeText;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
