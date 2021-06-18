package com.cxht.adapter;

import com.cxht.entity.Resume;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResumeAdapter extends ArrayAdapter<Resume> {
	private LayoutInflater inflater;

	public ResumeAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.user_info_item_text1, null);
			viewHolder = new InfoHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.titleText);
			viewHolder.subTitle = (TextView) view.findViewById(R.id.descText);
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		Resume item = getItem(position);

		String s_time = StringUtil.toShowData(item.getStart_time());
		s_time = s_time.trim();

		String e_time = StringUtil.toShowData(item.getOver_time());
		e_time = e_time.trim();

		String title = s_time + "-" + e_time;
		viewHolder.title.setText(title);
		viewHolder.subTitle.setText(item.getJob_desc());
		return view;
	}

	public class InfoHolder {
		public TextView title;
		public TextView subTitle;

	}
}
