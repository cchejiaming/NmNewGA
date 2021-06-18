package com.cxht.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cxht.bean.InfoItem;
import com.gongan.manage.R;

public class InfoArrayAdapter extends ArrayAdapter<InfoItem> {
	private LayoutInflater inflater;

	public InfoArrayAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.user_info_item_text, null);
			viewHolder = new InfoHolder();
			viewHolder.titleText = (TextView) view.findViewById(R.id.titleText);
			viewHolder.descText = (TextView) view.findViewById(R.id.descText);
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		InfoItem item = getItem(position);
		viewHolder.titleText.setText(item.getTitle());
		viewHolder.descText.setText(item.getDesc());
		return view;
	}

	public class InfoHolder {
		public TextView titleText;
		public TextView descText;

	}

}
