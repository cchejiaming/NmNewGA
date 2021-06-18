package com.cxht.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cxht.entity.UserField;
import com.gongan.manage.R;

public class UserFieldAdapter extends ArrayAdapter<UserField> {

	private LayoutInflater inflater;
	public UserFieldAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Log.d("InfoAdapter1.java", "Code goes here...");
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
		UserField item = getItem(position);
		
		viewHolder.title.setText(item.getField_name());
	    viewHolder.subTitle.setText(item.getField_value());
		return view;
	}

	public class InfoHolder {
		public TextView title;
		public TextView subTitle;

	}

}
