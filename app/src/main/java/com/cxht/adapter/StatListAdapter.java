package com.cxht.adapter;

import java.util.List;

import com.cxht.bean.StatResultBean;
import com.gongan.manage.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatListAdapter extends BaseAdapter {
	private String TAG = "InfoAdapter";
	private List<StatResultBean> data;
	private Context context;
	private LayoutInflater inflater;

	public StatListAdapter(Context context, List<StatResultBean> data) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		StatResultBean item = data.get(position);
		return item.getFlag();
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {

		InfoHolder viewHolder;
		Log.i(TAG, "position:" + position);
		if (position < data.size()) {

			if (view == null) {
				viewHolder = new InfoHolder();

				switch (getItemViewType(position)) {
				case 0:
					view = inflater
							.inflate(R.layout.user_info_item_title, null);
					viewHolder.titleText = (TextView) view
							.findViewById(R.id.titleText);
					viewHolder.descText = (TextView) view
							.findViewById(R.id.descText);
					// viewHolder.layout = (LinearLayout) view
					// .findViewById(R.id.itemLayout);
					// 设置tag
					view.setTag(viewHolder);
					break;

				case 1:
					view = inflater.inflate(R.layout.user_info_item_text, null);
					viewHolder.titleText = (TextView) view
							.findViewById(R.id.titleText);
					viewHolder.descText = (TextView) view
							.findViewById(R.id.descText);
					viewHolder.layout = (LinearLayout) view
							.findViewById(R.id.itemLayout);
					// 设置tag
					view.setTag(viewHolder);
					break;
				case 2:
					view = inflater
							.inflate(R.layout.user_info_item_text1, null);
					viewHolder.titleText = (TextView) view
							.findViewById(R.id.titleText);
					viewHolder.descText = (TextView) view
							.findViewById(R.id.descText);
					viewHolder.layout = (LinearLayout) view
							.findViewById(R.id.itemLayout);
					// viewHolder.layout.setOrientation(LinearLayout.VERTICAL);
					// 设置tag
					view.setTag(viewHolder);

					break;
				}

			} else {
				// 从convertView中得到我们的viewHolder
				viewHolder = (InfoHolder) view.getTag();
			}

			StatResultBean item = data.get(position);
			if (item != null) {

				viewHolder.titleText.setText(item.getName());
				viewHolder.descText.setText(String.valueOf(item.getCount()));
			}
		}

		return view;
	}

	public class InfoHolder {
		public LinearLayout layout;
		public TextView titleText;
		public TextView descText;

	}
}
